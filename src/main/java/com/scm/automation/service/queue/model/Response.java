package com.scm.automation.service.queue.model;

import lombok.Builder;
import lombok.Data;

/**
 * Response class.
 *
 * @author vipin.v
 * @version 1.0.44
 * @since 1.0.44
 */
@Data
@Builder
public class Response {
  private Request request;
  private String body;
  private String exception;
}
