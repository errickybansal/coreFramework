package com.scm.automation.test.service;

import com.scm.automation.annotation.Runnable;
import com.scm.automation.service.AbstractQueueService;
import com.scm.automation.service.queue.QueueClient;
import com.scm.automation.service.queue.model.Request;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.BaseUtil;

/**
 * HybrisToOmsService class.
 *
 * @author prakash.adak
 * @version 1.0.35
 * @since 1.0.35
 */
public class HybrisToOmsService extends AbstractQueueService<HybrisToOmsService> {

  /**
   * Constructor for HybrisToOmsService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.queue.QueueClient} object
   * @since 1.0.38
   */
  public HybrisToOmsService(Settings settings, QueueClient client) {
    super(
        settings,
        client,
        Request.builder()
            .url(BaseUtil.getEnvProperty("service.tibcoHybrisToOms.url").getAsString())
            .username(BaseUtil.getEnvProperty("service.tibcoHybrisToOms.username").getAsString())
            .password(BaseUtil.getEnvProperty("service.tibcoHybrisToOms.password").getAsString())
            .build());
  }

  /**
   * createOrder.
   *
   * @param request a {@link com.scm.automation.service.queue.model.Request} object
   * @since 1.0.38
   */
  @Runnable
  public void createOrder(Request request) {
    request.setQueueName(
        BaseUtil.getEnvProperty("service.tibcoHybrisToOms.createOrderQueueName").getAsString());
    this.getClient().sendMessage(this.mergeRequest(request, Request.class));
  }
}
