package com.scm.automation.service;

import com.google.gson.JsonObject;
import com.scm.automation.service.util.Validator;
import com.scm.automation.settings.Settings;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract AbstractBaseService class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractBaseService<T> implements BaseService<T> {
  @Getter private final Settings settings;
  @Getter private Settings updatedSettings;
  @Getter private JsonObject expectedResult;

  @Getter
  @Setter(AccessLevel.PROTECTED)
  private Consumer<Validator> validator;

  /** ErrorResponseBody. */
  public record ErrorResponseBody(Integer statusCode, String description) {}

  @Getter private ErrorResponseBody errorResponseBody;

  /**
   * Constructor for AbstractBaseService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @since 1.0.0
   */
  protected AbstractBaseService(Settings settings) {
    this.settings = settings;
    this.reset();
  }

  /** {@inheritDoc} */
  @Override
  public void reset() {
    this.updatedSettings = null;
    this.expectedResult = null;
    this.validator = null;
    this.errorResponseBody = null;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public T withSettings(Settings settings) {
    this.updatedSettings = settings;
    return (T) this;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public T withValidator(Consumer<Validator> validator) {
    this.validator = validator;
    return (T) this;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public T withExpectedResult(JsonObject expected) {
    this.expectedResult = expected;
    return (T) this;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public T withErrorResponse(Integer statusCode, String description) {
    this.errorResponseBody = new ErrorResponseBody(statusCode, description);
    return (T) this;
  }

  // region private methods

  // endregion
}
