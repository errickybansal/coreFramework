package com.scm.automation.service.queue;

import com.google.gson.JsonObject;
import com.scm.automation.annotation.Loggable;
import com.scm.automation.exception.CoreException;
import com.scm.automation.service.queue.model.Request;
import com.scm.automation.service.queue.model.Response;
import com.scm.automation.util.DataUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;

/**
 * KafkaClient class.
 *
 * @author prakash.adak
 * @version 1.0.69
 * @since 1.0.69
 */
public class KafkaClient implements QueueClient {

  private static final String CERTIFICATE_PATH = "target/certs/";

  /** {@inheritDoc} */
  @Loggable
  public Response sendMessage(Request request) {

    Response response = Response.builder().build();
    JsonObject responseBody = new JsonObject();

    try (Producer<String, String> producer = new KafkaProducer<>(request.getProducerProperties())) {
      ProducerRecord<String, String> producerRecord;
      if (request.getPartitionIdList() == null || request.getPartitionIdList().isEmpty()) {

        producerRecord =
            new ProducerRecord<>(request.getTopic(), request.getKey(), request.getValue());

      } else {

        producerRecord =
            new ProducerRecord<>(
                request.getTopic(),
                request.getPartitionIdList().get(0),
                request.getKey(),
                request.getValue());
      }
      if (!request.getHeaders().isEmpty()) {
        request
            .getHeaders()
            .forEach((key, value) -> producerRecord.headers().add(key, value.getBytes()));
      }
      producer.send(producerRecord);
      responseBody.addProperty("status", "success");
    } catch (Exception ex) {
      responseBody.addProperty("status", "failed");
      response.setException(ex.getMessage());
    }
    response.setRequest(request);
    response.setBody(DataUtil.getGson().toJson(responseBody));
    return response;
  }

  /** {@inheritDoc} */
  @Loggable
  public Response receiveMessage(Request request) {
    Response response = Response.builder().build();
    JsonObject responseBody = new JsonObject();

    try (KafkaConsumer<String, String> consumer =
        new KafkaConsumer<>(request.getConsumerProperties())) {
      ArrayList<String> consumerValues = new ArrayList<>();
      Map<String, String> consumerHeaders = new HashMap<>();
      consumer.subscribe(Collections.singleton(request.getTopic()));
      if (request.getPartitionIdList() == null) {
        request.setPartitionIdList(new ArrayList<>());
      }
      if (request.getPartitionIdList().isEmpty()) {

        consumer
            .partitionsFor(request.getTopic())
            .forEach(partitionInfo -> request.getPartitionIdList().add(partitionInfo.partition()));
      }

      for (int partitionId : request.getPartitionIdList()) {
        consumer.poll(Duration.ofMillis(request.getPollTime()));
        long offset = consumer.position(new TopicPartition(request.getTopic(), partitionId));
        consumer.seek(
            new TopicPartition(request.getTopic(), partitionId),
            offset - request.getRecordCounts());
        int recordCount = 0;
        int retry = 0;
        int maxRetry = 3;
        while (recordCount < request.getRecordCounts() && retry < maxRetry) {
          ConsumerRecords<String, String> consumerRecords =
              consumer.poll(Duration.ofMillis(request.getPollTime()));
          recordCount += consumerRecords.count();
          retry++;
          for (ConsumerRecord<String, String> records : consumerRecords) {

            if (StringUtils.isEmpty(request.getValue())
                || records.value().contains(request.getValue())) {
              consumerValues.add(records.value());
            }
            if (records.headers() != null && records.headers().iterator().hasNext()) {
              for (Header header : records.headers()) {
                consumerHeaders.put(
                    header.key(), new String(header.value(), StandardCharsets.UTF_8));
              }
            }
          }
        }
      }
      responseBody.addProperty("status", "success");
      responseBody.add("message", DataUtil.toJsonArray(consumerValues));
      responseBody.add("headers", DataUtil.toJsonObject(consumerHeaders));
    } catch (Exception ex) {
      responseBody.addProperty("status", "failed");
      response.setException(ex.getMessage());
    }
    response.setRequest(request);
    response.setBody(DataUtil.getGson().toJson(responseBody));
    return response;
  }

  /**
   * copyCertificate.
   *
   * @param sourceUrl a {@link java.net.URL} object
   * @return a {@link java.lang.String} object
   * @since 1.1.24
   */
  @Loggable
  public static String copyCertificate(URL sourceUrl) {
    if (!sourceUrl.toString().startsWith("jar:")) {
      return sourceUrl.getPath();
    }
    File destinationFile = new File(CERTIFICATE_PATH + new File(sourceUrl.getPath()).getName());
    try {
      FileUtils.copyURLToFile(sourceUrl, destinationFile);
    } catch (IOException ex) {
      throw new CoreException(ex);
    }
    return destinationFile.getAbsolutePath();
  }
}
