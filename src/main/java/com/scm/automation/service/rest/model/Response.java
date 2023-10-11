package com.scm.automation.service.rest.model;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * Response class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
public class Response {
  private Request request;
  private Integer statusCode;
  private String statusLine;
  private String contentType;
  private Map<String, String> headers;
  private String body;
  private byte[] bytes;
  private InputStream stream;
  private String sessionId;
  private Map<String, String> cookies;

  /**
   * ErrorResponseBody class.
   *
   * @author vipin.v
   * @version 1.0.82
   * @since 1.0.82
   */
  @Data
  @Builder
  public static class ErrorResponseBody {
    Integer status;
    List<com.scm.automation.service.rest.model.Response.ErrorResponseBody.Error> errors;

    /**
     * Error class.
     *
     * @author vipin.v
     * @version 1.0.82
     * @since 1.0.82
     */
    @Data
    @Builder
    public static class Error {
      String description;
      String exceptionMessage;
      List<String> stackTrace;
    }
  }
}
