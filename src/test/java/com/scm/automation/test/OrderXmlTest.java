package com.scm.automation.test;

import com.google.gson.JsonObject;
import com.scm.automation.test.model.TestData;
import com.scm.automation.test.testng.Assert;
import com.scm.automation.test.testng.TestCase;
import com.scm.automation.util.DataUtil;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * OrderXmlTest class.
 *
 * @author vipin.v
 * @version 1.0.49
 * @since 1.0.49
 */
public class OrderXmlTest extends TestCase {

  /**
   * beforeTestSuite.
   *
   * @since 1.0.62
   */
  @BeforeSuite
  public void beforeTestSuite() {
    this.setTestDataResourcePath("com.scm.automation.test/orderXmlTestData.yaml");
  }

  /**
   * testUsingXml.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   * @since 1.0.62
   */
  @Test(dataProvider = "dataProvider")
  public void testUsingXml(TestData data) {
    JsonObject json =
        DataUtil.getGson()
            .fromJson(
                DataUtil.xmlToJson(data.getRequest().get("body").getAsString()), JsonObject.class);
    Assert.assertNotNull(json);

    String convertedXml = DataUtil.jsonToXml(DataUtil.getGson().toJson(json));
    Assert.assertNotNull(convertedXml);
  }

  /**
   * testUsingJson.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   * @since 1.0.62
   */
  @Test(dataProvider = "dataProvider")
  public void testUsingJson(TestData data) {
    JsonObject json = data.getRequest().getAsJsonObject("body");
    Assert.assertNotNull(json);

    String convertedXml = DataUtil.jsonToXml(DataUtil.getGson().toJson(json));
    Assert.assertNotNull(convertedXml);
  }

  /**
   * testUsingYaml.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   * @since 1.0.62
   */
  @Test(dataProvider = "dataProvider")
  public void testUsingYaml(TestData data) {
    JsonObject json = data.getRequest().getAsJsonObject("body");
    Assert.assertNotNull(json);

    String convertedXml = DataUtil.jsonToXml(DataUtil.getGson().toJson(json));
    Assert.assertNotNull(convertedXml);
  }
}
