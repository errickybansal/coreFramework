package com.scm.automation.service.soap;

import com.scm.automation.enums.SoapClientType;
import java.security.InvalidParameterException;

/**
 * SoapClientFactory class.
 *
 * @author prakash.adak
 * @version 1.0.62
 * @since 1.0.62
 */
public class SoapClientFactory {

  private SoapClientFactory() {}

  /**
   * getClient.
   *
   * @param clientType a {@link com.scm.automation.enums.RestClientType} object
   * @return a {@link com.scm.automation.service.rest.RestClient} object
   * @since 1.0.86
   */
  public static SoapClient getClient(SoapClientType clientType) {
    SoapClient client;
    switch (clientType) {
      case RESTASSURED -> client = new RestAssuredClient();
      default -> throw new InvalidParameterException(
          "Unsupported client type: " + clientType.name());
    }
    return client;
  }
}
