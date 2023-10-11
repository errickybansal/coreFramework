package com.scm.automation.test.service;

import com.scm.automation.annotation.Loggable;
import com.scm.automation.enums.DataType;
import com.scm.automation.service.rest.model.Request;
import com.scm.automation.service.util.AbstractRequestBuilder;

/**
 * RequestBuilder class.
 *
 * @author vipin.v
 * @version 1.0.53
 * @since 1.0.53
 */
public class RequestBuilder extends AbstractRequestBuilder {
  /**
   * Constructor for RequestBuilder.
   *
   * @since 1.0.86
   */
  public RequestBuilder() {
    super("com.scm.automation.test/serviceTestData.yaml", DataType.YAML);
  }

  /**
   * createTestRequest.
   *
   * @return a {@link com.scm.automation.service.rest.model.Request} object
   */
  @Loggable
  public Request createTestRequest() {
    return this.createRequest(this.getRequest(), Request.class);
  }
}
