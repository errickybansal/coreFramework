package com.scm.automation.test.service;

import com.google.common.io.Resources;
import com.scm.automation.enums.QueueClientType;
import com.scm.automation.service.queue.QueueClient;
import com.scm.automation.service.queue.QueueClientFactory;
import com.scm.automation.service.queue.model.Response;
import com.scm.automation.util.DataUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.testng.annotations.Test;

/**
 * KafkaClientTest class.
 *
 * @author vipin.v
 * @version 1.0.91
 * @since 1.0.91
 */
public class KafkaClientTest {

  /** testKafka. */
  @Test
  public void testKafkaProducer() {
    QueueClient client = QueueClientFactory.getClient(QueueClientType.KAFKA);
    Properties producerProperties = new Properties();
    producerProperties.put("bootstrap.servers", "gcstscmkafdb01.jio.com:9093");
    producerProperties.put("security.protocol", "SSL");
    producerProperties.put(
        "ssl.truststore.location",
        Resources.getResource("com.scm.automation.test/sitKafkaTrustStore.jks").getPath());
    producerProperties.put(
        "ssl.truststore.password", "VLtttjuOMhV0ncfGxSuhJ4t71L6vJ8iNySY1RmdtEkO");
    producerProperties.put(
        "key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    producerProperties.put(
        "value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    com.scm.automation.service.queue.model.Request request =
        com.scm.automation.service.queue.model.Request.builder()
            .producerProperties(producerProperties)
            .topic("jio.scm.ajiob2c.fg.reschedule.response.uat")
            .key("FO1000003755")
            .value("{ \"key\": \"value\"}")
            .build();

    Response response = client.sendMessage(request);
    System.out.println(response);
  }

  /** testKafkaConsumer. */
  @Test
  public void testKafkaConsumer() {
    QueueClient client = QueueClientFactory.getClient(QueueClientType.KAFKA);
    Properties consumerProperties = new Properties();
    consumerProperties.put("bootstrap.servers", "gcstscmkafdb01.jio.com:9093");
    consumerProperties.put("security.protocol", "SSL");
    consumerProperties.put(
        "ssl.truststore.location",
        Resources.getResource("com.scm.automation.test/sitKafkaTrustStore.jks").getPath());
    consumerProperties.put(
        "ssl.truststore.password", "VLtttjuOMhV0ncfGxSuhJ4t71L6vJ8iNySY1RmdtEkO");
    consumerProperties.put(
        "key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    consumerProperties.put(
        "value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    consumerProperties.put("group.id", "GROUP-ID-1");
    consumerProperties.put("enable.auto.commit", "false");
    consumerProperties.put("auto.commit.interval.ms", "1000");
    consumerProperties.put("auto.offset.reset", "latest");
    consumerProperties.put("max.poll.records", 100);

    com.scm.automation.service.queue.model.Request request =
        com.scm.automation.service.queue.model.Request.builder()
            .consumerProperties(consumerProperties)
            .topic("jio.scm.ajiob2c.fg.reschedule.response.uat")
            .recordCounts(10)
            .build();

    Response response = client.receiveMessage(request);
    System.out.println(response);
  }

  /** testKafkaProducerConsumer. */
  @Test
  public void testKafkaProducerConsumer() {
    Properties producerProperties = new Properties();
    producerProperties.put("bootstrap.servers", "gcsitgpeapp05.jio.com:9091");
    producerProperties.put("security.protocol", "SSL");
    producerProperties.put(
        "ssl.truststore.location",
        Resources.getResource("com.scm.automation.test/kafka.truststore.jks").getPath());
    producerProperties.put("ssl.truststore.password", "changeit");
    producerProperties.put(
        "ssl.keystore.location",
        Resources.getResource("com.scm.automation.test/gcsitgpeapp05.jio.com.jks").getPath());
    producerProperties.put("ssl.keystore.password", "changeit");
    producerProperties.put(
        "key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    producerProperties.put(
        "value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    Properties consumerProperties = new Properties();
    consumerProperties.put("bootstrap.servers", "gcsitgpeapp05.jio.com:9091");
    consumerProperties.put("security.protocol", "SSL");
    consumerProperties.put(
        "ssl.truststore.location",
        Resources.getResource("com.scm.automation.test/kafka.truststore.jks").getPath());
    consumerProperties.put("ssl.truststore.password", "changeit");
    consumerProperties.put(
        "ssl.keystore.location",
        Resources.getResource("com.scm.automation.test/gcsitgpeapp05.jio.com.jks").getPath());
    consumerProperties.put("ssl.keystore.password", "changeit");
    consumerProperties.put(
        "key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    consumerProperties.put(
        "value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    consumerProperties.put("group.id", "GROUP-ID-1");
    consumerProperties.put("enable.auto.commit", "false");
    consumerProperties.put("auto.commit.interval.ms", "1000");
    consumerProperties.put("auto.offset.reset", "latest");
    consumerProperties.put("max.poll.records", 100);

    com.scm.automation.service.queue.model.Request request =
        com.scm.automation.service.queue.model.Request.builder()
            .producerProperties(producerProperties)
            .consumerProperties(consumerProperties)
            .topic("ajob2c.herald.shipmentService.inputRequest.uat")
            .key("TestKey001")
            .value("TestValue001")
            .recordCounts(1)
            .build();

    QueueClient client = QueueClientFactory.getClient(QueueClientType.KAFKA);
    Response response = client.sendMessage(request);
    System.out.println(response);

    response = client.receiveMessage(request);
    System.out.println(DataUtil.toJsonObject(response.getBody()));
  }

  /** testKafkaProducer_shipmentService. */
  @Test
  public void testKafkaProducer_shipmentService() {

    Properties producerProperties = new Properties();
    producerProperties.put(
        "bootstrap.servers",
        "gcsitgpeapp05.jio.com:9091,gcsitgpeapp06.jio.com:9091,gcsitgpeapp07.jio.com:9091");
    producerProperties.put("security.protocol", "SSL");
    producerProperties.put(
        "ssl.truststore.location",
        Resources.getResource("com.scm.automation.test/shipmentService/kafka.truststore.jks")
            .getPath());
    producerProperties.put("ssl.truststore.password", "changeit");
    producerProperties.put(
        "ssl.keystore.location",
        Resources.getResource("com.scm.automation.test/shipmentService/gcsitgpeapp05.jks")
            .getPath());
    producerProperties.put("ssl.keystore.password", "changeit");
    producerProperties.put(
        "key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    producerProperties.put(
        "value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    Map<String, String> headers = new HashMap<>();

    headers.put(
        "spring_json_header_types",
        "{\"FlowName\":\"java.lang.String\", \"source\":\"java.lang.String\"}");
    headers.put("FlowName", "CREATE_SHIPMENT");
    headers.put("source", "createShipment");

    com.scm.automation.service.queue.model.Request request =
        com.scm.automation.service.queue.model.Request.builder()
            .producerProperties(producerProperties)
            .topic("ajob2c.herald.shipmentService.inputRequest.uat")
            .headers(headers)
            .value(
                """
                 {
                   "orderNo": "FLA00047891225",
                   "orderDate": "2023-07-28",
                   "fulfilmentId": "FO10546771225",
                   "shipmentType": "FORWARD",
                   "shipmentSubType": "FORWARD",
                   "tenantId": "AJIOB2C",
                   "source": "FS",
                   "paymentType": "COD",
                   "totalAmount": 500,
                   "amountToBeCollected": 500,
                   "nodeId": "8044",
                   "nodeType": "STORE",
                   "receivingNodeId": "",
                   "trackingId": "9459201225",
                   "enterpriseCode": "",
                   "requestId": "",
                   "carrierServiceCode": "ECOM",
                   "carrierServiceName": "ECOM",
                   "vendorCode": "",
                   "parentShipmentNo": "",
                   "parentTrackingId": "",
                   "status": "SHIPMENT_DRAFT_CREATED",
                   "pregenAWBRequired": true,
                   "logisticDetail": {
                       "shipmentMethod": "SURFACE",
                       "deliveryMethod": "SHIP",
                       "priorityCode": 0,
                       "nodeTaxPayerId": "TY696969",
                       "qrCode": "dXBpOi8vcGF5P3Zlcj0wMSIyMTEwMDAwNzQ1MjUmcXJNZWRpdW09MDI=",
                       "shipmentWeight": 28.99,
                       "weightUom": "KILOGRAM, GRAM",
                       "destinationCityCode": "HLG",
                       "sourceCityCode": "HLG",
                       "tat": 9,
                       "routingCode": "HLG/HLG-1",
                       "movementType": "Last Mile",
                       "shipMode": "PARCEL"
                   },
                   "shipmentLines": [
                       {
                           "shipmentLineNo": "",
                           "itemId": "460407276002",
                           "itemDescription": "PUMA Shoes",
                           "orderLineNo": "1",
                           "originalQty": 3,
                           "status": "SHIPMENT_DRAFT_CREATED"
                       },
                       {
                           "shipmentLineNo": "",
                           "itemId": "460407276004",
                           "itemDescription": "PUMA Shoes",
                           "orderLineNo": "2",
                           "originalQty": 1,
                           "status": "SHIPMENT_DRAFT_CREATED"
                       }
                   ],
                   "shipmentDates": [
                       {
                           "dateType": "PROMISED_SHIPMENT_DATE",
                           "dateTimestamp": "2022-07-28",
                           "reason": "Capacity breach, Non sellable item, etc"
                       }
                   ],
                   "address": [
                       {
                           "addressType": "SHIP_TO",
                           "firstName": "Renuka",
                           "middleName": "",
                           "lastName": "Yannam",
                           "line1": "TestFlat Test Building",
                           "line2": "Test Area",
                           "line3": "",
                           "line4": "",
                           "landmark": "",
                           "city": "BANGALORE",
                           "district": "Tumkur",
                           "state": "KAR",
                           "postalCode": "560099",
                           "country": "IN",
                           "phoneNo": "9886975886",
                           "otherPhoneNo": "",
                           "company": "",
                           "emailId": "renuka1.yannam@ril.com",
                           "gstin": "",
                           "vendor": ""
                       },
                       {
                           "addressType": "BILL_TO",
                           "firstName": "Renuka",
                           "middleName": "",
                           "lastName": "Yannam",
                           "line1": "TestFlat Test Building",
                           "line2": "Test Area",
                           "line3": "",
                           "line4": "",
                           "landmark": "",
                           "city": "BANGALORE",
                           "district": "Tumkur",
                           "state": "KAR",
                           "postalCode": "560099",
                           "country": "IN",
                           "phoneNo": "9886975864",
                           "otherPhoneNo": "",
                           "company": "",
                           "emailId": "renuka1.yannam@ril.com",
                           "gstin": "",
                           "vendor": ""
                       },
                       {
                           "addressType": "FROM",
                           "firstName": "Reliance",
                           "middleName": "Retail",
                           "lastName": "Ltd.",
                           "line1": "Survey No. 54/1",
                           "line2": "Nandihalli village 55th Km stone NH-4 Tumkur road",
                           "line3": "Oordigree Hobli Taluka",
                           "line4": "",
                           "landmark": "",
                           "city": "Tumkur",
                           "district": "Tumkur",
                           "state": "KAR",
                           "postalCode": "560099",
                           "country": "IN",
                           "phoneNo": "9886975864",
                           "otherPhoneNo": "",
                           "company": "RIL",
                           "emailId": "shipper@reliance.com",
                           "gstin": "",
                           "vendor": ""
                       }
                   ]
                   }
                            """)
            .build();
    QueueClient client = QueueClientFactory.getClient(QueueClientType.KAFKA);
    Response response = client.sendMessage(request);
    System.out.println(response);
  }
}
