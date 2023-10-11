package com.scm.automation.service.soap;

import com.scm.automation.service.soap.model.Request;
import com.scm.automation.service.soap.model.Response;

/**
 * SoapClient interface.
 *
 * @author prakash.adak
 * @version 1.0.62
 * @since 1.0.62
 */
public interface SoapClient {

  /**
   * resolveRequest.
   *
   * @param request a {@link com.scm.automation.service.soap.model.Request} object
   * @return a {@link com.scm.automation.service.soap.model.Response} object
   * @since 1.0.86
   */
  Response resolveRequest(Request request);
}
