package com.scm.automation.test;

import com.google.gson.JsonObject;
import com.scm.automation.enums.CacheClientType;
import com.scm.automation.enums.RestClientType;
import com.scm.automation.service.cache.CacheClientFactory;
import com.scm.automation.service.database.CassandraClient;
import com.scm.automation.service.database.MongoDbClient;
import com.scm.automation.service.database.OracleClient;
import com.scm.automation.service.database.PostgresClient;
import com.scm.automation.service.queue.TibcoClient;
import com.scm.automation.service.rest.RestClientFactory;
import com.scm.automation.service.rest.UnirestClient;
import com.scm.automation.service.rest.model.Request;
import com.scm.automation.service.rest.model.Response;
import com.scm.automation.service.soap.RestAssuredClient;
import com.scm.automation.settings.Settings;
import com.scm.automation.settings.SettingsBuilder;
import com.scm.automation.test.model.TestData;
import com.scm.automation.test.service.CacheClientService;
import com.scm.automation.test.service.FsMongoDatabaseService;
import com.scm.automation.test.service.FulfillmentDatabaseService;
import com.scm.automation.test.service.HybrisToOmsService;
import com.scm.automation.test.service.ImageUploadService;
import com.scm.automation.test.service.LoginRestService;
import com.scm.automation.test.service.OmsDatabaseService;
import com.scm.automation.test.service.PaymentDetailsService;
import com.scm.automation.test.service.SoapService;
import com.scm.automation.test.service.TmsDatabaseService;
import com.scm.automation.test.service.XmlRestService;
import com.scm.automation.test.testng.Assert;
import com.scm.automation.test.testng.TestCase;
import com.scm.automation.util.DataUtil;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * ServiceTest class.
 *
 * @author vipin.v
 * @version 1.0.29
 * @since 1.0.29
 */
public class ServiceTest extends TestCase {
  /**
   * beforeTestSuite.
   *
   * @since 1.0.49
   */
  @BeforeSuite
  public void beforeTestSuite() {
    this.setTestDataResourcePath("com.scm.automation.test/serviceTestData.yaml");
  }

  /**
   * testLoginPositive.
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider")
  public void testRestApiService(TestData data) {
    Request request = DataUtil.getGson().fromJson(data.getRequest(), Request.class);
    Response response =
        new LoginRestService(
                SettingsBuilder.build(), new com.scm.automation.service.rest.RestAssuredClient())
            .withSettings(Settings.builder().initialDelay(5000).build())
            .login(request);
    JsonObject body = DataUtil.toJsonObject(response.getBody());
    Assert.assertNotNull(body);
  }

  /**
   * testTibcoService.
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider")
  public void testTibcoService(TestData data) {
    com.scm.automation.service.queue.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.queue.model.Request.class);
    new HybrisToOmsService(SettingsBuilder.build(), new TibcoClient()).createOrder(request);
  }

  /**
   * testXmRestApiService. - RestAssuredClient
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider")
  public void testXmlRestApiService(TestData data) {
    Request request = DataUtil.getGson().fromJson(data.getRequest(), Request.class);
    Response response =
        new XmlRestService(
                SettingsBuilder.build(), new com.scm.automation.service.rest.RestAssuredClient())
            .createNewTraveller(request);
    Assert.assertEquals((int) response.getStatusCode(), 400, "Status code should be 400");
  }

  /**
   * testXmRestApiService. - UniRest Client
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider")
  public void testXmlRestApiServiceUniRest(TestData data) {
    Request request = DataUtil.getGson().fromJson(data.getRequest(), Request.class);
    Response response =
        new XmlRestService(SettingsBuilder.build(), new UnirestClient())
            .createNewTraveller(request);
    Assert.assertEquals((int) response.getStatusCode(), 400, "Status code should be 400");
  }

  /**
   * testImageUploadService. - RestAssured Client
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider", enabled = false)
  public void testImageUploadService(TestData data) {
    Request request = DataUtil.getGson().fromJson(data.getRequest(), Request.class);
    Response response =
        new ImageUploadService(
                SettingsBuilder.build(), new com.scm.automation.service.rest.RestAssuredClient())
            .uploadImage(request);
    Assert.assertEquals((int) response.getStatusCode(), 200, "Status code should be 200");
  }

  /**
   * testImageUploadServiceUsingUnirest.
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider", enabled = false)
  public void testImageUploadServiceUsingUnirest(TestData data) {
    Request request = DataUtil.getGson().fromJson(data.getRequest(), Request.class);
    Response response =
        new ImageUploadService(SettingsBuilder.build(), new UnirestClient()).uploadImage(request);
    Assert.assertEquals((int) response.getStatusCode(), 200, "Status code should be 200");
  }

  /**
   * testFulfillmentDatabaseFulfillmentLineStatus.
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider")
  public void testFulfillmentDatabaseFulfillmentLineStatus(TestData data) {
    com.scm.automation.service.database.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.database.model.Request.class);
    com.scm.automation.service.database.model.Response response =
        new FulfillmentDatabaseService(SettingsBuilder.build(), new PostgresClient())
            .getFulfillmentLineStatusAudit(request);
    Assert.assertNotNull(response.getResult().getData());
  }

  /**
   * testOmsDatabaseYfsShipment.
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider", enabled = false)
  public void testOmsDatabaseYfsShipment(TestData data) {
    com.scm.automation.service.database.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.database.model.Request.class);
    com.scm.automation.service.database.model.Response response =
        new OmsDatabaseService(SettingsBuilder.build(), new OracleClient())
            .getOmsDatabaseYfsShipment(request);
    Assert.assertNotNull(response.getResult().getData());
  }

  /**
   * testTMSDatabaseInvoiceDetails.
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider", enabled = false)
  public void testTmsDatabaseInvoiceDetails(TestData data) {
    com.scm.automation.service.database.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.database.model.Request.class);
    com.scm.automation.service.database.model.Response response =
        new TmsDatabaseService(SettingsBuilder.build(), new CassandraClient())
            .getTmsDatabaseInvoiceDetails(request);
    Assert.assertNotNull(response.getResult().getData());
  }

  /**
   * testFsMongoDatabaseFetchDetails.
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider")
  public void testFsMongoDatabaseFetchDetails(TestData data) {
    com.scm.automation.service.database.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.database.model.Request.class);
    com.scm.automation.service.database.model.Response response =
        new FsMongoDatabaseService(SettingsBuilder.build(), new MongoDbClient())
            .getFsMongoDatabaseFetchDetails(request);
    Assert.assertNotNull(response.getResult().getData());
  }

  /**
   * testFsMongoDatabaseFetchDetailsForParticularFields.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testFsMongoDatabaseFetchDetailsForParticularFields(TestData data) {
    com.scm.automation.service.database.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.database.model.Request.class);
    com.scm.automation.service.database.model.Response response =
        new FsMongoDatabaseService(SettingsBuilder.build(), new MongoDbClient())
            .getFsMongoDatabaseFetchDetails(request);
    Assert.assertNotNull(response.getResult().getData());
  }

  /**
   * testFsMongoDatabaseUpdateDetails.
   *
   * @param data a {@link com.google.gson.JsonObject} object
   * @since 1.0.49
   */
  @Test(dataProvider = "dataProvider")
  public void testFsMongoDatabaseUpdateDetails(TestData data) {
    com.scm.automation.service.database.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.database.model.Request.class);
    com.scm.automation.service.database.model.Response response =
        new FsMongoDatabaseService(SettingsBuilder.build(), new MongoDbClient())
            .getFsMongoDatabaseUpdateDetails(request);
    Assert.assertNotNull(response.getResult().getMetaData());
  }

  /**
   * testPaymentDetailsReceiveMessageDetails.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testPaymentDetailsReceiveMessageDetails(TestData data) {
    com.scm.automation.service.queue.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.queue.model.Request.class);
    com.scm.automation.service.queue.model.Response response =
        new PaymentDetailsService(SettingsBuilder.build(), new TibcoClient())
            .receiveMessage(request);
    Assert.assertNotNull(response);
  }

  /**
   * testSoapApiService.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testSoapApiService(TestData data) {
    com.scm.automation.service.soap.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.soap.model.Request.class);
    com.scm.automation.service.soap.model.Response response =
        new SoapService(SettingsBuilder.build(), new RestAssuredClient())
            .createEwayBillGeneration(request);
    Assert.assertNotNull(response);
  }

  /**
   * testXmlRestAssuredApi.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider")
  public void testXmlRestAssuredApi(TestData data) {
    Request request = DataUtil.getGson().fromJson(data.getRequest(), Request.class);
    Response response =
        new XmlRestService(
                SettingsBuilder.build(), new com.scm.automation.service.rest.RestAssuredClient())
            .getOrderDetails(request);
    Assert.assertEquals((int) response.getStatusCode(), 200, "Status code should be 200");
  }

  /** testErrorResponse. */
  @Test
  public void testErrorResponse() {
    Response errorResponse =
        new LoginRestService(
                SettingsBuilder.build(), RestClientFactory.getClient(RestClientType.UNIREST))
            .withErrorResponse(500, "Error while logging in")
            .login(Request.builder().build());
    Assert.assertNotNull(errorResponse);
    Assert.assertNotNull(errorResponse.getBody());
  }

  /**
   * testCacheService.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   */
  @Test(dataProvider = "dataProvider", enabled = true)
  public void testCacheService(TestData data) {
    com.scm.automation.service.cache.model.Request request =
        DataUtil.getGson()
            .fromJson(data.getRequest(), com.scm.automation.service.cache.model.Request.class);
    com.scm.automation.service.cache.model.Response response =
        new CacheClientService(
                SettingsBuilder.build(), CacheClientFactory.getClient(CacheClientType.REDIS))
            .getCacheDetails(request);
    Assert.assertTrue(!response.getBody().isEmpty());
  }
}
