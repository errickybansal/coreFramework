package com.scm.automation.test.cucumber;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.scm.automation.config.BaseConfig;
import com.scm.automation.test.testng.Assert;
import com.scm.automation.util.BaseUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * CoreSteps class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public class CoreSteps {

  static JsonElement propertyValue = null;

  /**
   * baseIsConfiguredWithEnvAndContext.
   *
   * @param env a {@link java.lang.String} object
   * @param context a {@link java.lang.String} object
   * @since 1.0.0
   */
  @Given("Base is configured with env {string} and context {string}")
  public void baseIsConfiguredWithEnvAndContext(String env, String context) {
    JsonObject json = new JsonObject();
    json.addProperty("env", env);
    json.addProperty("context", context);
    BaseConfig.configure(json);
  }

  /**
   * fetchedEnvProperty.
   *
   * @param property a {@link java.lang.String} object
   * @since 1.0.0
   */
  @When("Fetched env property {string}")
  public void fetchedEnvProperty(String property) {
    propertyValue = BaseUtil.getEnvProperty(property);
  }

  /**
   * verifyThePropertyIsNotNull.
   *
   * @since 1.0.0
   */
  @Then("Verify the url property is not null")
  public void verifyThePropertyIsNotNull() {
    Assert.assertNotNull(propertyValue);
    Assert.assertNotNull(propertyValue.getAsJsonObject().get("url"));
  }

  /** verifyThePropertyIsNull. */
  @Then("Verify the url property is null")
  public void verifyThePropertyIsNull() {
    Assert.assertEquals(propertyValue, JsonNull.INSTANCE);
  }
}
