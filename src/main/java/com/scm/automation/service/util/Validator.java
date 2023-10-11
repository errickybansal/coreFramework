package com.scm.automation.service.util;

import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Data;

/**
 * Validator class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
public class Validator {
  private JsonObject actual;
  private JsonObject expected;
  private String message;
}
