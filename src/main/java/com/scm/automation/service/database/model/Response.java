package com.scm.automation.service.database.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Data;

/**
 * Response class.
 *
 * @author salil.devanshi
 * @version 1.0.32
 * @since 1.0.32
 */
@Data
@Builder
public class Response {
  private Request request;
  private Result result;

  /**
   * Result class.
   *
   * @author salil.devanshi
   * @version 1.0.32
   * @since 1.0.32
   */
  @Data
  @Builder
  public static class Result {
    private JsonObject metaData;
    private JsonArray data;
  }
}
