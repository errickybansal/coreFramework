package com.scm.automation.test;

import com.epam.reportportal.annotations.Step;
import com.github.underscore.U;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.scm.automation.annotation.Runnable;
import com.scm.automation.config.BaseConfig;
import com.scm.automation.enums.DataType;
import com.scm.automation.enums.EncodingType;
import com.scm.automation.enums.QueueClientType;
import com.scm.automation.env.EnvBuilder;
import com.scm.automation.runner.RunnableFactory;
import com.scm.automation.service.queue.QueueClientFactory;
import com.scm.automation.service.rest.model.Request;
import com.scm.automation.settings.Settings;
import com.scm.automation.settings.SettingsBuilder;
import com.scm.automation.test.service.KafkaQueueService;
import com.scm.automation.test.service.RequestBuilder;
import com.scm.automation.test.testng.Assert;
import com.scm.automation.ui.page.model.Response;
import com.scm.automation.util.BaseUtil;
import com.scm.automation.util.DataUtil;
import com.scm.automation.util.SshUtil;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import net.javacrumbs.jsonunit.core.Option;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * CoreTest class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("checkstyle:lineLength")
public class CoreTest {

  /** beforeMethod. */
  @BeforeMethod
  public void beforeMethod() {
    System.out.println("Starting CoreTest");
  }

  /** afterMethod. */
  @AfterMethod
  public void afterMethod() {
    System.out.println("Finished CoreTest");
  }

  /** testAspect. */
  @Test
  public void testAspect() {
    this.callAspect();
  }

  /** callAspect. */
  @Runnable
  public void callAspect() {
    System.out.println("Executing CoreTest Aspect function");
    Assert.assertEquals("Test", "Test");
  }

  /** testSettingsBuilder. */
  @Test
  public void testSettingsBuilder() {
    Settings settings = SettingsBuilder.build();
    Assert.assertNotNull(settings);
    Assert.assertEquals(settings.getMaxRetries(), 2);
    Assert.assertEquals(settings.getRetryDelay(), 1000);
    Assert.assertEquals(settings.getInitialDelay(), 0);
    Assert.assertEquals(settings.getTimeout().getSleepTimeout(), 100);
    Assert.assertEquals(settings.getTimeout().getScriptTimeout(), 60000);
    Assert.assertEquals(settings.getTimeout().getPageLoadTimeout(), 60000);
    Assert.assertEquals(settings.getTimeout().getImplicitlyWait(), 5000);
    Assert.assertEquals(settings.getTimeout().getWaitTimeout(), 60000);
    Assert.assertEquals(settings.getTimeout().getWaitInterval(), 2000);
  }

  /** testBaseConfig. */
  @Test
  public void testBaseConfig() {
    BaseConfig.configure(
        new Gson()
            .fromJson(
                """
                      {"env": "dev", "context": ["default"]}
                    """,
                JsonObject.class));
  }

  /** testEnvBuilder. */
  @Test
  public void testEnvBuilder() {
    JsonObject envObject = EnvBuilder.build();
    Assert.assertNotNull(envObject);
  }

  /** testGetEnvProperty. */
  @Test
  public void testGetEnvProperty() {
    JsonElement property = BaseUtil.getEnvProperty("service.test");
    Assert.assertNotNull(property);
  }

  /** testGetEnvPropertyInvalid. */
  @Test
  public void testGetEnvPropertyInvalid() {
    JsonElement property = BaseUtil.getEnvProperty("service.invalid.property");
    Assert.assertEquals(property, JsonNull.INSTANCE);
  }

  /** testGetUrlEnvProperty. */
  @Test
  public void testGetEnvPropertyAsString() {
    String property = BaseUtil.getEnvPropertyAsString("service.loginService.url");
    Assert.assertNotNull(property);
  }

  /** testJava17TextBlock. */
  @Test
  public void testJava17TextBlock() {
    String json =
        """
          {
            "name": "JSON",
            "integer": 1,
            "double": 2.0,
            "boolean": true,
            "nested": { "id": 55 },
            "array": [1, 2, 3]
          }
        """;
    JsonElement jsonObject = DataUtil.getGson().toJsonTree(json);
    System.out.println(jsonObject);
  }

  /** testJsonToXML. */
  @Test
  public void testJsonToXml() {
    String json =
        """
          {
            "name": "JSON",
            "integer": 1,
            "double": 2.0,
            "boolean": true,
            "nested": { "id": 55 },
            "array": [1, 2, 3]
          }
        """;

    JSONObject jsonObject = new JSONObject(json);
    String xml = XML.toString(jsonObject);
    System.out.println(xml);
  }

  /** testDataUtilJsonToXml. */
  @Test
  public void testDataUtilJsonToXml() {
    String json =
        """
            {
               "mobileNumber": "+916677889904",
               "buyerId": "SFPLMID667788993",
               "paymentType": "PREPAID",
               "itemList": [
                 {
                   "productCode": "460415943",
                   "jioCode": "485006382",
                   "quantity": "3",
                   "hsnCode": "50072010",
                   "fulfilmentType":"JIT",
                   "productName": "pack1_30May_003",
                   "pobIb":"DV00306897",
                   "nodeId":"R285",
                   "unitOfMeasurement":"PACK"
                 }
               ]
             }
        """;
    String xmlString = DataUtil.jsonToXml(json, U.Mode.FORCE_REMOVE_ARRAY_ATTRIBUTE_JSON_TO_XML);
    System.out.println(xmlString);
  }

  /**
   * testMessage.
   *
   * @return a {@link java.lang.String} object
   */
  public String testMessage() {
    return """
          <?xml version="1.0"?>
          <Order></Order>
        """;
  }

  /** testExcelToJsonConversion. */
  @Test
  public void testExcelToJsonConversion() {
    JsonObject data = DataUtil.read("com.scm.automation.test/ExcelToJson.xlsx", DataType.XLSX);
    Assert.assertNotNull(data);
  }

  /** testXmlToJsonConversion. */
  @Test
  public void testXmlToJsonConversion() {
    JsonObject data = DataUtil.read("com.scm.automation.test/input.xml", DataType.XML);
    Assert.assertNotNull(data);
  }

  /** testPdfToJsonConversion. */
  @Test
  public void testPdfToJsonConversion() {
    JsonObject data = DataUtil.read("com.scm.automation.test/sample.pdf", DataType.PDF);
    Assert.assertNotNull(data);
  }

  /** testCsvToJsonConversion. */
  @Test
  public void testCsvToJsonConversion() {
    JsonObject data = DataUtil.read("com.scm.automation.test/industry.csv", DataType.CSV);
    Assert.assertNotNull(data);
  }

  /** testRestCreateRequest. */
  @Test
  public void testRestCreateRequest() {
    String json =
        """
          {
            "body": {
              "username": "username",
              "password": "password"
            }
          }
        """;

    Request request =
        new RequestBuilder().createRequest(DataUtil.toJsonObject(json), Request.class);
    Assert.assertNotNull(request.getBody());
  }

  /** testRestXmlCreateRequest. */
  @Test
  public void testRestXmlCreateRequest() {
    String json =
        """
          {
            "body": {
              "username": "username",
              "password": "password"
            }
          }
        """;

    Request request =
        new RequestBuilder()
            .createRequest(DataUtil.toJsonObject(json), Request.class, ContentType.XML);
    Assert.assertNotNull(request.getBody());
  }

  /** testQueueCreateRequest. */
  @Test
  public void testQueueCreateRequest() {
    String json =
        """
          {
            "message": {
              "username": "username",
              "password": "password"
            }
          }
        """;

    com.scm.automation.service.queue.model.Request request =
        new RequestBuilder()
            .createRequest(
                DataUtil.toJsonObject(json),
                com.scm.automation.service.queue.model.Request.class,
                ContentType.XML);
    Assert.assertNotNull(request.getMessage());
  }

  /** testKafkaQueueCreateRequest. */
  @Test
  public void testKafkaQueueCreateRequest() {
    String json =
        """
          {
            "value": {
              "username": "username",
              "password": "password"
            }
          }
        """;

    com.scm.automation.service.queue.model.Request request =
        new RequestBuilder()
            .createRequest(
                DataUtil.toJsonObject(json), com.scm.automation.service.queue.model.Request.class);
    Assert.assertNotNull(request.getValue());
  }

  /** testRestArrayCreateRequest. */
  @Test
  public void testRestArrayCreateRequest() {
    String json =
        """
          {
            "body": [{
                "username": "username",
                "password": "password"
            },
            {
                "username": "username",
                "password": "password"
            }]
        }
        """;

    Request request =
        new RequestBuilder().createRequest(DataUtil.toJsonObject(json), Request.class);
    Assert.assertNotNull(request.getBody());
  }

  /** testRestXmlCreateRequest. */
  @Test
  public void testRestArrayXmlCreateRequest() {
    String json =
        """
          {
            "body": [{
                "username": "username",
                "password": "password"
            },
            {
                "username": "username",
                "password": "password"
            }]
        }
        """;

    Request request =
        new RequestBuilder()
            .createRequest(DataUtil.toJsonObject(json), Request.class, ContentType.XML);
    Assert.assertNotNull(request.getBody());
  }

  /** testQueueCreateRequest. */
  @Test
  public void testQueueArrayCreateRequest() {
    String json =
        """
          {
            "message": [{
                "username": "username",
                "password": "password"
            },
            {
                "username": "username",
                "password": "password"
            }],
            "value": {
              "username": "username",
              "password": "password"
            }
        }
        """;

    com.scm.automation.service.queue.model.Request request =
        new RequestBuilder()
            .createRequest(
                DataUtil.toJsonObject(json),
                com.scm.automation.service.queue.model.Request.class,
                ContentType.XML);
    Assert.assertNotNull(request.getMessage());
    Assert.assertNotNull(request.getValue());
  }

  /** testFindPatternMatch. */
  @Test
  @SuppressWarnings("checkstyle:LineLength")
  public void testFindPatternMatch() {

    final String regex =
        "\\{[a-zA-Z\\\\\":,0-9.]+\\\\\"orderNo\\\\\":\\\\\"04102114\\\\\"[a-zA-Z\\\\\":,0-9.]+\\}";
    final String input =
        "\"{\\\"success\\\":true,\\\"message\\\":\\\"Outstanding/Upcoming payment details of buyer\\\",\\\"statusCode\\\":200,\\\"result\\\":{\\\"buyerId\\\":\\\"321321321998788\\\",\\\"upcomingPaymentAmount\\\":6151255.14,\\\"outstandingPaymentAmount\\\":903530.56,\\\"outstandingCreditAmount\\\":0,\\\"buyerStatus\\\":\\\"ACTIVE\\\",\\\"payToSOEnabled\\\":false,\\\"creditAvailed\\\":true,\\\"wholesaler\\\":false,\\\"outstandingDetails\\\":[{\\\"id\\\":50143826,\\\"requested\\\":false,\\\"orderNo\\\":\\\"04102102\\\",\\\"requestType\\\":\\\"Advance\\\",\\\"sellerOrderNo\\\":\\\"Y138565504\\\",\\\"amount\\\":3324.37,\\\"age\\\":0,\\\"createAt\\\":1678865625000,\\\"outstandingSince\\\":1678818600000,\\\"gstComputationInitiated\\\":false,\\\"preBook\\\":false,\\\"cod\\\":false,\\\"partialPayment\\\":false},{\\\"id\\\":50143857,\\\"requested\\\":false,\\\"orderNo\\\":\\\"04102106\\\",\\\"requestType\\\":\\\"Advance\\\",\\\"sellerOrderNo\\\":\\\"Y138565774\\\",\\\"amount\\\":3307.5,\\\"age\\\":0,\\\"createAt\\\":1678865790000,\\\"outstandingSince\\\":1678818600000,\\\"gstComputationInitiated\\\":false,\\\"preBook\\\":false,\\\"cod\\\":false,\\\"partialPayment\\\":false},{\\\"id\\\":50143902,\\\"requested\\\":false,\\\"orderNo\\\":\\\"04102114\\\",\\\"requestType\\\":\\\"Advance\\\",\\\"sellerOrderNo\\\":\\\"Y138566055\\\",\\\"amount\\\":519.7,\\\"age\\\":0,\\\"createAt\\\":1678865912000,\\\"outstandingSince\\\":1678818600000,\\\"gstComputationInitiated\\\":false,\\\"preBook\\\":false,\\\"cod\\\":false,\\\"partialPayment\\\":false},{\\\"id\\\":50144050,\\\"requested\\\":false,\\\"orderNo\\\":\\\"04102123\\\",\\\"requestType\\\":\\\"Advance\\\",\\\"sellerOrderNo\\\":\\\"Y138567445\\\",\\\"amount\\\":19845,\\\"age\\\":0,\\\"createAt\\\":1678866533000,\\\"outstandingSince\\\":1678818600000,\\\"gstComputationInitiated\\\":false,\\\"preBook\\\":false,\\\"cod\\\":false,\\\"partialPayment\\\":false}]";

    List<String> matches = DataUtil.find(input, regex);
    Assert.assertTrue(matches.size() != 0);
  }

  /** testSshConnection. */
  @Test(enabled = false)
  public void testSshConnection() {
    String command = """
        echo hello SSH;
        """.trim();

    String result = SshUtil.execute("10.159.26.173", "si_cisadmin", "DEFGHIK123a#b", command);
    Assert.assertEquals("hello SSH", result.trim());
  }

  /** testYamlInheritence. */
  @Test
  public void testYamlInheritence() {
    JsonObject data = DataUtil.read("com.scm.automation.test/derived.yaml", DataType.YAML);
    Assert.assertTrue(data.has("name"));
  }

  /** testToObject. */
  @Test
  public void testToObject() {
    String json =
        """
          {
            "request": {
              "args": [
                "hello", "world"
              ]
            },
            "result" : {
              "data": "success"
            }
          }
        """;

    Response response = DataUtil.toObject(json, Response.class);
    Assert.assertNotNull(response);
  }

  /** testToObjectArray. */
  @Test
  public void testToObjectArray() {
    String jsonArray =
        """
          [
            {
              "request": {
                "args": [
                  "hello", "world"
                ]
              },
              "result" : {
                "data": "success"
              }
            },
            {
              "request": {
                "args": [
                  "hello", "world"
                ]
              },
              "result" : {
                "data": "success"
              }
            }
          ]
        """;

    List<Response> response = DataUtil.toObjectArray(jsonArray, Response.class);
    Assert.assertNotNull(response);
  }

  /** testGetRequestWithoutArgs. */
  @Test
  public void testGetRequestWithoutArgs() {
    Request request = new RequestBuilder().createTestRequest();
    Assert.assertNotNull(request);
  }

  /** testXmlCompare. */
  @Test
  public void testXmlCompare() {
    String actual =
        """
          <employees>
            <employee id="101">
              <title>Author</title>
              <name>Lokesh Gupta</name>
            </employee>
            <employee id="100">
              <title>Cricketer</title>
              <name>Brian Lara</name>
            </employee>
          </employees>
        """;

    String expected =
        """
          <employees>
            <employee id="100">
              <name>Brian Lara</name>
              <title>Cricketer</title>
            </employee>
            <employee id="101">
              <name>Lokesh Gupta</name>
              <title>Author</title>
            </employee>
          </employees>
        """;

    JsonAssertions.assertThatJson(DataUtil.xmlToJson(actual))
        .when(Option.IGNORING_ARRAY_ORDER)
        .isEqualTo(DataUtil.xmlToJson(expected));
  }

  /** testJsonCompare. */
  @Test
  public void testJsonCompare() {
    String actual =
        """
          {
            "name": "xyz",
            "address": [
              {
                "street": "avenida",
                "number": "41414-44141",
                "code": "33ll",
                "moreFields": "some data"
              },
              {
                "street": "fshdh",
                "number": "41423764-44141",
                "code": "887",
                "moreFields": "test"
              }
            ],
            "moreFields": {
              "timeStamp": "1689315824"
            }
          }
        """;

    String expected =
        """
          {
            "name": "xyz",
            "address": [
              {
                "street": "fshdh",
                "number": "41423764-44141",
                "code": "887",
                "moreFields": "test"
              },
              {
                "street": "avenida",
                "number": "41414-44141",
                "code": "33ll",
                "moreFields": "some data"
              }
            ],
            "moreFields": {
              "timeStamp": "1689315825"
            }
          }
        """;

    JsonAssertions.assertThatJson(actual)
        .when(Option.IGNORING_ARRAY_ORDER)
        .whenIgnoringPaths("moreFields.timeStamp")
        .isEqualTo(expected);
  }

  /** testFetchXmlValue. */
  @Test
  public void testFetchXmlValue() {
    String xml =
        """
          <employees>
            <employee id="101">
              <title>Author</title>
              <name>Lokesh Gupta</name>
            </employee>
            <employee id="100">
              <title>Cricketer</title>
              <name>Brian Lara</name>
            </employee>
          </employees>
        """;

    String value =
        DataUtil.getAsJsonElement(xml, "employees.employee[0].-id", DataType.XML).getAsString();
    Assert.assertEquals(value, "101");
  }

  /** testDefaultPropertySetInRequest. */
  @Test
  public void testDefaultPropertySetInRequest() {
    String requestJsonOne =
        """
        {
          "message": "message",
          "topic": "topic",
          "key": "key"
        }
        """;

    KafkaQueueService service =
        new KafkaQueueService(
            Settings.builder().build(), QueueClientFactory.getClient(QueueClientType.KAFKA));

    com.scm.automation.service.queue.model.Request request =
        service.mergeRequest(
            DataUtil.toObject(requestJsonOne, com.scm.automation.service.queue.model.Request.class),
            com.scm.automation.service.queue.model.Request.class);
    Assert.assertEquals(request.getPollTime(), 10000);

    String requestJsonTwo =
        """
        {
          "message": "message",
          "topic": "topic",
          "key": "key",
          "pollTime": 5000
        }
        """;

    request =
        service.mergeRequest(
            DataUtil.toObject(requestJsonTwo, com.scm.automation.service.queue.model.Request.class),
            com.scm.automation.service.queue.model.Request.class);
    Assert.assertEquals(request.getPollTime(), 5000);
  }

  /** testSoapXmlConversion. */
  @Test
  @SuppressWarnings("LineLength")
  public void testSoapXmlConversion() {
    String xml =
        """
        <ns:__caret_reply_caret_ZWM__ECOM__MDN__REQ__RESP_caret_ZWM__ECOM__MDN__REQ__RESP
          xmlns:ns="http://www.tibco.com/xmlns/ae2xsd/2002/05/ae/750/basic/functionModules"
          xmlns:ae="http://www.tibco.com/xmlns/ae2xsd/2002/05"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:type="ns:__caret_reply_caret_ZWM__ECOM__MDN__REQ__RESP_caret_ZWM__ECOM__MDN__REQ__RESP">
          <__caret_hasException_caret_>false</__caret_hasException_caret_>
            <T__HYBRIS__ECOMM>
              <item>
                <ORDERNO>${orderNo}</ORDERNO>
                <DOC__TYPE>FS08</DOC__TYPE>
                <ENTERPRISE__CD>FnL</ENTERPRISE__CD>
                <ORDERDTTM>2023-05-02</ORDERDTTM>
                <GIFT__WRAP>N</GIFT__WRAP>
                <ORD__LINE__KEY>202307181729510069443005</ORD__LINE__KEY>
                <PLINE__NO>1</PLINE__NO>
                <ORDERED__QTY>1</ORDERED__QTY>
                <ORDLINE__SCH__KEY>202307181729510069443005</ORDLINE__SCH__KEY>
                <SHIP__NODE>S402</SHIP__NODE>
                <ITEM__ID>420173592001</ITEM__ID>
                <CHANNEL__ID>3</CHANNEL__ID>
                <LOGSYS>TIBCOFNL</LOGSYS>
              </item>
            </T__HYBRIS__ECOMM>
            <T__RETURN>
              <item>
                <ORDERNO>${orderNo}</ORDERNO>
                <DOC__TYPE>FS08</DOC__TYPE>
                <ENTERPRISE__CD>FnL</ENTERPRISE__CD>
                <ORD__LINE__KEY>202307181729510069443005</ORD__LINE__KEY>
                <EXTN__ACTION xsi:nil="true"/>
                <PLINE__NO>1</PLINE__NO>
                <ORDERED__QTY>1</ORDERED__QTY>
                <ORDLINE__SCH__KEY>202307181729510069443005</ORDLINE__SCH__KEY>
                <SHIP__NODE>S402</SHIP__NODE>
                <MEINS xsi:nil="true"/>
                <MDNNO xsi:nil="true"/>
                <MDN__QTY>1</MDN__QTY>
                <REASONCODE>001</REASONCODE>
                <ITEM__ID>420173592001</ITEM__ID>
                <REASONTEXT>Requested Qty fully ordered</REASONTEXT>
                <LOGSYS>TIBCOFNL</LOGSYS>
              </item>
            </T__RETURN>
          </ns:__caret_reply_caret_ZWM__ECOM__MDN__REQ__RESP_caret_ZWM__ECOM__MDN__REQ__RESP>
        """;

    String xmlUnderscoreReplaced = xml.replaceAll("__", "___");
    JsonObject data = DataUtil.xmlToJsonObject(xmlUnderscoreReplaced);

    String jsonToXml = DataUtil.jsonObjectToXml(data);
    jsonToXml = jsonToXml.replaceAll("___", "__");

    Assert.assertTrue(
        xml.trim()
                .startsWith(
                    "<ns:__caret_reply_caret_ZWM__ECOM__MDN__REQ__RESP_caret_ZWM__ECOM__MDN__REQ__RESP")
            && jsonToXml
                .trim()
                .startsWith(
                    "<ns:__caret_reply_caret_ZWM__ECOM__MDN__REQ__RESP_caret_ZWM__ECOM__MDN__REQ__RESP"));
  }

  @Test
  public void testGetDataType() {

    String jsonString =
        """
        {
          "message": "message",
          "topic": "topic",
          "key": "key",
          "pollTime": 5000
        }
        """;

    String xmlString =
        """
          <?xml version="1.0" encoding="UTF-8" ?>
          <InsuranceCompanies>
             <Top_Insurance_Companies>
              <Name>Berkshire Hathaway ( BRK.A)</Name>
              <Capitalization>$655 billion</Capitalization>
             </Top_Insurance_Companies>
          </InsuranceCompanies>
        """;

    Assert.assertEquals(
        DataUtil.getDataType(jsonString), DataType.JSON, "Input string is not a JSON String");
    Assert.assertEquals(
        DataUtil.getDataType(xmlString), DataType.XML, "Input string is not a XML String");
  }

  @Test
  public void testStepAnnotation() {
    loginStep();
  }

  @Step("Verify if the user is able to login")
  public void loginStep() {
    Assert.assertEquals("login", "login", "Login should be successful");
  }

  @Test
  public void testJsonArrayToXml() {
    String json =
        """
          [{
            "car": {
              "features": [{
                "code": "1"
              }, {
                "code": "2"
              }]
            }
          },
          {
            "car": {
              "features": [{
                "code": "3"
              }, {
                "code": "2"
              }]
            }
          }]
        """;
    JsonArray jsonArray = DataUtil.toJsonArray(json);
    String xml = DataUtil.jsonArrayToXml(jsonArray);
    Assert.assertNotNull(xml);
  }

  @Test
  public void testRunnerUtilService() {
    String input = "input";
    String result =
        RunnableFactory.get()
            .withSettings(Settings.builder().maxRetries(3).build())
            .withValidator(
                validator -> {
                  Assert.assertEquals(
                      validator.getActual().get("data").getAsString(),
                      "output",
                      "Output should match ");
                })
            .run(
                "Fetch name and run validation",
                () -> {
                  System.out.println(input);
                  return "output";
                });

    Assert.assertEquals(result, "output", "Output should match ");
  }

  @Test
  public void testBase64Yaml() {
    JsonObject object =
        DataUtil.read("com.scm.automation.test/base64yaml.txt", DataType.YAML, EncodingType.BASE64);
    Assert.assertNotNull(object);
  }

  @Test
  public void testBase64Pdf() {
    JsonObject object =
        DataUtil.read("com.scm.automation.test/base64Pdf.txt", DataType.PDF, EncodingType.BASE64);
    Assert.assertNotNull(object);
  }

  @Test
  public void testBase64Excel() {
    JsonObject object =
        DataUtil.read(
            "com.scm.automation.test/base64Excel.txt", DataType.XLSX, EncodingType.BASE64);
    Assert.assertNotNull(object);
  }

  @Test
  public void testBase64ExcelEncoding() throws IOException {
    URL url = Resources.getResource("com.scm.automation.test/ExcelToJson.xlsx");
    byte[] bytes = Resources.toByteArray(url);
    String base64 = DataUtil.encodeBase64(bytes);
  }

  @Test
  public void testBase64ExcelStream() {
    String base64Input =
        "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";

    JsonObject object =
        DataUtil.read(IOUtils.toInputStream(base64Input), DataType.XLSX, EncodingType.BASE64);
    Assert.assertNotNull(object);
  }
}
