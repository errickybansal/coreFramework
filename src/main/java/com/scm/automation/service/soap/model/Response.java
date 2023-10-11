package com.scm.automation.service.soap.model;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * Response class.
 *
 * @author prakash.adak
 * @version 1.0.62
 * @since 1.0.62
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
  private String sessionId;
  private Map<String, String> cookies;
}
