package com.scm.automation.service.cache;

import com.scm.automation.enums.CacheClientType;
import java.security.InvalidParameterException;
import org.apache.commons.lang3.NotImplementedException;

/**
 * CacheClientFactory class.
 *
 * @author prakash.adak
 * @version 1.0.86
 * @since 1.0.86
 */
public class CacheClientFactory {
  private CacheClientFactory() {}

  /**
   * getClient.
   *
   * @param clientType a {@link com.scm.automation.enums.CacheClientType} object
   * @return a {@link com.scm.automation.service.cache.CacheClient} object
   * @since 1.0.86
   */
  public static CacheClient getClient(CacheClientType clientType) {
    CacheClient client;
    switch (clientType) {
      case REDIS -> client = new RedisClient();
      case VARNIS -> throw new NotImplementedException();
      default -> throw new InvalidParameterException("Unsupported client type: ");
    }
    return client;
  }
}
