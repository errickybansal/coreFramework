package com.scm.automation.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.scm.automation.config.BaseConfig;
import com.scm.automation.enums.Context;
import com.scm.automation.enums.Env;
import com.scm.automation.env.EnvBuilder;
import com.scm.automation.service.rest.model.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

/**
 * BaseUtil class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public class BaseUtil {
  @Getter private static final BaseConfig config = BaseConfig.configure();
  private static JsonObject envProperties;

  private BaseUtil() {}

  /**
   * isSit.
   *
   * @return a boolean
   * @since 1.0.0
   */
  public static boolean isSit() {
    return getConfig().getEnv() == Env.SIT;
  }

  /**
   * isUat.
   *
   * @return a boolean
   * @since 1.0.0
   */
  public static boolean isUat() {
    return getConfig().getEnv() == Env.UAT;
  }

  /**
   * isProd.
   *
   * @return a boolean
   * @since 1.0.0
   */
  public static boolean isProd() {
    return getConfig().getEnv() == Env.PROD;
  }

  /**
   * isDefaultContext.
   *
   * @return a boolean
   * @since 1.0.0
   */
  public static boolean isDefaultContext() {
    return getConfig().getContext().size() == 1
        && getConfig().getContext().contains(Context.DEFAULT);
  }

  /**
   * Getter for the field <code>envProperties</code>.
   *
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.0
   */
  public static JsonObject getEnvProperties() {
    if (envProperties == null) {
      envProperties = EnvBuilder.build();
    }
    return envProperties;
  }

  /**
   * getEnvProperty.
   *
   * @param path a {@link java.lang.String} object
   * @return a {@link com.google.gson.JsonElement} object
   * @since 1.0.0
   */
  public static JsonElement getEnvProperty(String path) {
    return DataUtil.getAsJsonElement(getEnvProperties(), path);
  }

  /**
   * getEnvPropertyAsString.
   *
   * @param path a {@link java.lang.String} object
   * @return a {@link java.lang.String} object
   * @since 1.0.0
   */
  public static String getEnvPropertyAsString(String path) {
    return getEnvProperty(path).getAsString();
  }

  /**
   * createErrorResponse.
   *
   * @param statusCode a {@link java.lang.Integer} object
   * @param description a {@link java.lang.String} object
   * @param exception a {@link java.lang.Throwable} object
   * @return a {@link com.scm.automation.service.rest.model.Response} object
   * @since 1.0.0
   */
  public static Response createErrorResponse(
      Integer statusCode, String description, Throwable exception) {

    List<String> stackTrace = new ArrayList<>();

    if (exception != null && exception.getStackTrace() != null) {
      Arrays.stream(exception.getStackTrace())
          .toList()
          .forEach(stackTraceElement -> stackTrace.add(stackTraceElement.toString()));
    }

    Response.ErrorResponseBody.Error error =
        Response.ErrorResponseBody.Error.builder()
            .description(description)
            .exceptionMessage(
                exception != null && exception.getStackTrace() != null
                    ? exception.getMessage()
                    : null)
            .stackTrace(stackTrace)
            .build();

    Response.ErrorResponseBody errorResponseBody =
        Response.ErrorResponseBody.builder().status(statusCode).errors(List.of(error)).build();

    return Response.builder()
        .statusCode(statusCode)
        .body(DataUtil.getGson().toJson(DataUtil.toJsonObject(errorResponseBody)))
        .build();
  }

  // region private methods

  // endregion
}
