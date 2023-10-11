package com.scm.automation.service.queue;

import com.scm.automation.enums.QueueClientType;
import java.security.InvalidParameterException;

/**
 * QueueClientFactory class.
 *
 * @author prakash.adak
 * @version 1.0.26
 * @since 1.0.26
 */
public class QueueClientFactory {
  private QueueClientFactory() {}

  /**
   * getClient.
   *
   * @param clientType a {@link com.scm.automation.enums.QueueClientType} object
   * @return a {@link com.scm.automation.service.queue.QueueClient} object
   * @since 1.0.38
   */
  public static QueueClient getClient(QueueClientType clientType) {
    QueueClient client;
    switch (clientType) {
      case TIBCO -> client = new TibcoClient();
      case KAFKA -> client = new KafkaClient();
      default -> throw new InvalidParameterException(
          "Unsupported client type: " + clientType.name());
    }
    return client;
  }
}
