package com.scm.automation.runner;

import com.epam.reportportal.annotations.Step;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.scm.automation.service.AbstractBaseService;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.DataUtil;
import java.util.function.Supplier;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Runnable class.
 *
 * @author vipin.v
 * @version 1.1.22
 * @since 1.1.22
 */
public class Runnable extends AbstractBaseService<Runnable> {

  /**
   * Constructor for Runnable.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   */
  public Runnable(Settings settings) {
    super(settings);
  }

  /** {@inheritDoc} */
  @Override
  public <R> R mergeRequest(R newRequest, Class<R> type) {
    throw new NotImplementedException();
  }

  /**
   * run.
   *
   * @param message a {@link java.lang.String} object
   * @param method a {@link java.util.function.Supplier} object
   * @param <R> a R class
   * @return a R object
   */
  @Step("{message}")
  @com.scm.automation.annotation.Runnable
  public <R> R run(String message, Supplier<R> method) {
    return method.get();
  }

  /**
   * createResponse.
   *
   * @param result a {@link java.lang.Object} object
   * @return a {@link com.google.gson.JsonObject} object
   */
  public JsonObject createResponse(Object result) {
    JsonObject response = new JsonObject();

    try {
      JsonElement resultElement;
      if (result instanceof String stringResult) {
        resultElement = JsonParser.parseString(stringResult);
      } else {
        resultElement = DataUtil.getGson().toJsonTree(result);
      }

      if (resultElement.isJsonArray()) {
        response.add("data", resultElement.getAsJsonArray());
      } else if (resultElement.isJsonObject()) {
        response.add("data", resultElement.getAsJsonObject());
      } else if (resultElement.isJsonPrimitive()) {
        response.addProperty("data", result.toString());
      }
    } catch (JsonSyntaxException ex) {
      response.addProperty("data", result.toString());
    }

    return response;
  }

  // region private methods

  // endregion

}
