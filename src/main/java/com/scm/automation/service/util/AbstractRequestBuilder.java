package com.scm.automation.service.util;

import com.github.underscore.U;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.scm.automation.enums.DataType;
import com.scm.automation.util.DataUtil;
import io.restassured.http.ContentType;

/**
 * Abstract AbstractRequestBuilder class.
 *
 * @author vipin.v
 * @version 1.0.43
 * @since 1.0.43
 */
public abstract class AbstractRequestBuilder {
  private final JsonObject request;

  /**
   * Constructor for AbstractRequestBuilder.
   *
   * @param resourceUrl a {@link java.lang.String} object
   * @param dataType a {@link com.scm.automation.enums.DataType} object
   * @since 1.0.86
   */
  protected AbstractRequestBuilder(String resourceUrl, DataType dataType) {
    this.request = DataUtil.read(resourceUrl, dataType);
  }

  /**
   * Getter for the field <code>request</code>.
   *
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.1.24
   */
  public JsonObject getRequest() {
    StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    StackWalker.StackFrame frame =
        stackWalker.walk(stream -> stream.skip(1).findFirst().orElse(null));
    return this.getRequest(frame.getMethodName());
  }

  /**
   * Getter for the field <code>request</code>.
   *
   * @param name a {@link java.lang.String} object
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.86
   */
  public JsonObject getRequest(String name) {
    return DataUtil.getAsJsonObject(this.request, name);
  }

  /**
   * createRequest.
   *
   * @param request a {@link com.google.gson.JsonObject} object
   * @param type a {@link java.lang.Class} object
   * @param <R> a Request class
   * @return a R object
   * @since 1.0.86
   */
  public <R> R createRequest(JsonObject request, Class<R> type) {
    return this.createRequest(request, type, ContentType.JSON);
  }

  /**
   * createRequest.
   *
   * @param request a {@link com.google.gson.JsonObject} object
   * @param type a {@link java.lang.Class} object
   * @param contentType a {@link io.restassured.http.ContentType} object
   * @param <R> a Request class
   * @return a R object
   * @since 1.0.86
   */
  public <R> R createRequest(JsonObject request, Class<R> type, ContentType contentType) {
    String convertedPayload = this.convertPayload(request, type, contentType);

    if (convertedPayload != null) {
      if ((type == com.scm.automation.service.rest.model.Request.class
          || type == com.scm.automation.service.soap.model.Request.class)) {
        request.addProperty("body", convertedPayload);
      } else if (type == com.scm.automation.service.queue.model.Request.class) {
        if (request.get("message") != null) {
          request.addProperty("message", convertedPayload);
        }
        if (request.get("value") != null) {
          request.addProperty("value", convertedPayload);
        }
      }
    }
    return DataUtil.toObject(request, type);
  }

  // region private methods

  private <R> String convertPayload(JsonObject request, Class<R> type, ContentType contentType) {
    JsonElement payload = null;

    if ((type == com.scm.automation.service.rest.model.Request.class
        || type == com.scm.automation.service.soap.model.Request.class)) {
      payload = DataUtil.getAsJsonElement(request, "body");
    } else if (type == com.scm.automation.service.queue.model.Request.class) {
      if (request.has("message")) {
        payload = DataUtil.getAsJsonElement(request, "message");
      } else if (request.has("value")) {
        payload = DataUtil.getAsJsonElement(request, "value");
      }
    }

    if (payload instanceof JsonObject jsonObject) {
      if (contentType == ContentType.XML) {
        return DataUtil.jsonObjectToXml(
            jsonObject, U.Mode.FORCE_REMOVE_ARRAY_ATTRIBUTE_JSON_TO_XML);
      }
      return DataUtil.getGson().toJson(jsonObject);
    } else if (payload instanceof JsonArray jsonArray) {
      if (contentType == ContentType.XML) {
        return DataUtil.jsonArrayToXml(jsonArray, U.Mode.FORCE_REMOVE_ARRAY_ATTRIBUTE_JSON_TO_XML);
      }
      return DataUtil.getGson().toJson(jsonArray);
    }
    return null;
  }

  // endregion
}
