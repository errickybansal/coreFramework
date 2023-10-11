package com.scm.automation.service;

import com.google.gson.JsonObject;
import com.scm.automation.service.util.Validator;
import com.scm.automation.settings.Settings;
import java.util.function.Consumer;

/**
 * BaseService interface.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BaseService<T> {
  /**
   * getSettings.
   *
   * @return a {@link com.scm.automation.settings.Settings} object
   * @since 1.0.0
   */
  Settings getSettings();

  /**
   * getUpdatedSettings.
   *
   * @return a {@link com.scm.automation.settings.Settings} object
   * @since 1.0.0
   */
  Settings getUpdatedSettings();

  /**
   * getValidator.
   *
   * @return a {@link java.util.function.Consumer} object
   * @since 1.0.0
   */
  Consumer<Validator> getValidator();

  /**
   * getExpectedResult.
   *
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.0
   */
  JsonObject getExpectedResult();

  /**
   * getErrorResponseBody.
   *
   * @return a {@link com.scm.automation.service.AbstractBaseService.ErrorResponseBody} object
   * @since 1.0.86
   */
  AbstractBaseService.ErrorResponseBody getErrorResponseBody();

  /**
   * reset.
   *
   * @since 1.0.0
   */
  void reset();

  /**
   * withSettings.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @return a T object
   * @since 1.0.0
   */
  T withSettings(Settings settings);

  /**
   * withExpectedResult.
   *
   * @param expected a {@link com.google.gson.JsonObject} object
   * @return a T object
   * @since 1.0.0
   */
  T withExpectedResult(JsonObject expected);

  /**
   * withValidator.
   *
   * @param validator a {@link java.util.function.Consumer} object
   * @return a T object
   * @since 1.0.0
   */
  T withValidator(Consumer<Validator> validator);

  /**
   * withErrorResponse.
   *
   * @param statusCode a {@link java.lang.Integer} object
   * @param description a {@link java.lang.String} object
   * @return a T object
   * @since 1.0.86
   */
  T withErrorResponse(Integer statusCode, String description);

  /**
   * mergeRequest.
   *
   * @param newRequest a R object
   * @param type a {@link java.lang.Class} object
   * @param <R> a R class
   * @return a R object
   * @since 1.0.38
   */
  <R> R mergeRequest(R newRequest, Class<R> type);
}
