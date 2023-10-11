package com.scm.automation.service.rest;

import com.scm.automation.enums.RestClientType;
import java.security.InvalidParameterException;

/**
 * RestClientFactory class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public class RestClientFactory {
  private RestClientFactory() {}

  /**
   * getClient.
   *
   * @param clientType a {@link com.scm.automation.enums.RestClientType} object
   * @return a {@link com.scm.automation.service.rest.RestClient} object
   * @since 1.0.38
   */
  public static RestClient getClient(RestClientType clientType) {
    RestClient client;
    switch (clientType) {
      case RESTASSURED -> client = new RestAssuredClient();
      case UNIREST -> client = new UnirestClient();
      default -> throw new InvalidParameterException(
          "Unsupported client type: " + clientType.name());
    }
    return client;
  }
}
