package com.scm.automation.ui.page.model;

import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Data;

/**
 * Response class.
 *
 * @author vipin.v
 * @version 1.0.92
 * @since 1.0.92
 */
@Data
@Builder
public class Response {
  private Request request;
  private JsonObject result;
}
