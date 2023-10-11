package com.scm.automation.service.queue;

import com.scm.automation.service.queue.model.Request;
import com.scm.automation.service.queue.model.Response;

/**
 * QueueClient interface.
 *
 * @author prakash.adak
 * @version 1.0.25
 * @since 1.0.25
 */
public interface QueueClient {
  /**
   * sendMessage.
   *
   * @param request a {@link com.scm.automation.service.queue.model.Request} object
   * @return a {@link com.scm.automation.service.queue.model.Response} object
   * @since 1.0.62
   */
  Response sendMessage(Request request);

  /**
   * receiveMessage.
   *
   * @param request a {@link com.scm.automation.service.queue.model.Request} object
   * @return a {@link com.scm.automation.service.queue.model.Response} object
   * @since 1.0.62
   */
  Response receiveMessage(Request request);
}
