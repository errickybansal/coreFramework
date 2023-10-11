package com.scm.automation.test.service;

import com.scm.automation.service.AbstractQueueService;
import com.scm.automation.service.queue.QueueClient;
import com.scm.automation.service.queue.model.Request;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.DataUtil;

/**
 * KafkaQueueService class.
 *
 * @author vipin.v
 * @version 1.0.98
 * @since 1.0.98
 */
public class KafkaQueueService extends AbstractQueueService<KafkaQueueService> {

  static String baseRequestJson =
      """
        {
          "queueName": "test",
          "username": "username",
          "password": "password",
          "producerProperties": {
            "key": "value"
          }
        }
        """;

  /**
   * Constructor for KafkaQueueService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.queue.QueueClient} object
   */
  public KafkaQueueService(Settings settings, QueueClient client) {
    super(settings, client, DataUtil.toObject(baseRequestJson, Request.class));
  }
}
