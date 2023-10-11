package com.scm.automation.service.rest;

import com.scm.automation.service.rest.model.Request;
import com.scm.automation.service.rest.model.Response;

/**
 * RestClient interface.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public interface RestClient {
  /**
   * resolveRequest.
   *
   * @param request a {@link com.scm.automation.service.rest.model.Request} object
   * @return a {@link com.scm.automation.service.rest.model.Response} object
   * @since 1.0.0
   */
  Response resolveRequest(Request request);
}
