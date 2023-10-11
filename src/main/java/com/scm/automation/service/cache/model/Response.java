package com.scm.automation.service.cache.model;

import lombok.Builder;
import lombok.Data;

/**
 * Response class.
 *
 * @author prakash.adak
 * @version 1.0.85
 * @since 1.0.85
 */
@Data
@Builder
public class Response {
  private Request request;
  private String body;
  private String exception;
}
