package com.scm.automation.test.service;

import com.scm.automation.service.AbstractQueueService;
import com.scm.automation.service.queue.QueueClient;
import com.scm.automation.service.queue.model.Request;
import com.scm.automation.service.queue.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.BaseUtil;

/**
 * PaymentDetailsService class.
 *
 * @author nishita.shetty
 * @version 1.0.60
 * @since 1.0.60
 */
public class PaymentDetailsService extends AbstractQueueService<PaymentDetailsService> {
  /**
   * Constructor for AbstractQueueService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.queue.QueueClient} object
   * @since 1.0.62
   */
  public PaymentDetailsService(Settings settings, QueueClient client) {
    super(
        settings,
        client,
        Request.builder()
            .url(BaseUtil.getEnvPropertyAsString("service.tibcoPaymentDetails.url"))
            .username(BaseUtil.getEnvPropertyAsString("service.tibcoPaymentDetails.username"))
            .password(BaseUtil.getEnvPropertyAsString("service.tibcoPaymentDetails.password"))
            .build());
  }

  /**
   * receiveMessage.
   *
   * @param request a {@link com.scm.automation.service.queue.model.Request} object
   * @return a {@link com.scm.automation.service.queue.model.Response} object
   * @since 1.0.62
   */
  public Response receiveMessage(Request request) {
    request.setQueueName(request.getQueueName());
    return this.getClient().receiveMessage(this.mergeRequest(request, Request.class));
  }
}
