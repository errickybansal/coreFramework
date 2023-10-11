package com.scm.automation.test;

import com.google.common.io.Resources;
import com.scm.automation.enums.DataType;
import com.scm.automation.enums.DatabaseClientType;
import com.scm.automation.enums.QueueClientType;
import com.scm.automation.enums.RestClientType;
import com.scm.automation.service.database.DatabaseClient;
import com.scm.automation.service.database.DatabaseClientFactory;
import com.scm.automation.service.queue.QueueClient;
import com.scm.automation.service.queue.QueueClientFactory;
import com.scm.automation.service.rest.RestClient;
import com.scm.automation.service.rest.RestClientFactory;
import com.scm.automation.service.rest.model.Request;
import com.scm.automation.service.rest.model.Response;
import com.scm.automation.test.model.TestData;
import com.scm.automation.test.testng.Assert;
import com.scm.automation.test.testng.TestCase;
import com.scm.automation.util.DataUtil;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * ServiceDebugTest class.
 *
 * @author vipin.v
 * @version 1.0.89
 * @since 1.0.89
 */
public class ServiceDebugTest extends TestCase {
  /**
   * beforeTestSuite.
   *
   * @since 1.0.49
   */
  @BeforeSuite
  public void beforeTestSuite() {
    this.setTestDataResourcePath("com.scm.automation.test/serviceDebugTestData.yaml");
  }

  /**
   * testPdfFromByteArray.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testPdfFromByteArray(TestData data) {
    Request request = DataUtil.getGson().fromJson(data.getRequest(), Request.class);
    RestClient client = RestClientFactory.getClient(RestClientType.RESTASSURED);
    Response response = client.resolveRequest(request);
    Assert.assertEquals(response.getStatusCode(), 200);
    System.out.println(DataUtil.read(response.getStream(), DataType.PDF));
  }

  /**
   * testGetCall.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testGetCall(TestData data) {
    Request request = DataUtil.getGson().fromJson(data.getRequest(), Request.class);
    RestClient client = RestClientFactory.getClient(RestClientType.RESTASSURED);
    Response response = client.resolveRequest(request);
    Assert.assertEquals(response.getStatusCode(), 200);
    System.out.println(response);
  }

  /**
   * testMongoQueryParams.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testMongoQueryParams(TestData data) {
    com.scm.automation.service.database.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.database.model.Request.class);
    DatabaseClient client = DatabaseClientFactory.getClient(DatabaseClientType.MONGODB);
    com.scm.automation.service.database.model.Response response = client.fetch(request);
    System.out.println(response);
  }

  /**
   * testKafkaReceiveMessage.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testKafkaReceiveMessage(TestData data) {
    com.scm.automation.service.queue.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.queue.model.Request.class);
    QueueClient client = QueueClientFactory.getClient(QueueClientType.KAFKA);
    com.scm.automation.service.queue.model.Response response = client.receiveMessage(request);
    System.out.println(response);
  }

  /**
   * testKafkaSendMessage.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testKafkaSendMessage(TestData data) {
    com.scm.automation.service.queue.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.queue.model.Request.class);
    QueueClient client = QueueClientFactory.getClient(QueueClientType.KAFKA);
    com.scm.automation.service.queue.model.Response response = client.sendMessage(request);
    System.out.println(response);
  }

  /**
   * testKafkaSendMessageWithHeaders.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testKafkaSendMessageWithHeaders(TestData data) {
    data.getRequest()
        .getAsJsonObject("producerProperties")
        .addProperty(
            "ssl.truststore.location",
            Resources.getResource(
                    data.getRequest()
                        .getAsJsonObject("producerProperties")
                        .get("ssl.truststore" + ".location")
                        .getAsString())
                .getPath());
    data.getRequest()
        .getAsJsonObject("producerProperties")
        .addProperty(
            "ssl.keystore.location",
            Resources.getResource(
                    data.getRequest()
                        .getAsJsonObject("producerProperties")
                        .get("ssl.keystore.location")
                        .getAsString())
                .getPath());
    com.scm.automation.service.queue.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.queue.model.Request.class);
    QueueClient client = QueueClientFactory.getClient(QueueClientType.KAFKA);
    com.scm.automation.service.queue.model.Response response = client.sendMessage(request);
    System.out.println(response);
  }

  /**
   * testMongoAuthenticationDatabase.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testMongoAuthenticationDatabase(TestData data) {
    com.scm.automation.service.database.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.database.model.Request.class);
    DatabaseClient client = DatabaseClientFactory.getClient(DatabaseClientType.MONGODB);
    com.scm.automation.service.database.model.Response response = client.fetch(request);
    System.out.println(response);
  }

  /**
   * testMongoQueryParamsWithStringId.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testMongoQueryParamsWithStringId(TestData data) {
    com.scm.automation.service.database.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.database.model.Request.class);
    DatabaseClient client = DatabaseClientFactory.getClient(DatabaseClientType.MONGODB);
    com.scm.automation.service.database.model.Response response = client.fetch(request);
    System.out.println(response);
  }

  /**
   * testOracleInsertQuery.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testOracleInsertQuery(TestData data) {
    com.scm.automation.service.database.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.database.model.Request.class);
    DatabaseClient client = DatabaseClientFactory.getClient(DatabaseClientType.ORACLE);
    com.scm.automation.service.database.model.Response response = client.add(request);
    System.out.println(response);
  }
}
