package com.scm.automation.service;

import com.epam.reportportal.annotations.Step;
import com.google.gson.JsonObject;
import com.scm.automation.annotation.Runnable;
import com.scm.automation.service.queue.QueueClient;
import com.scm.automation.service.queue.model.Request;
import com.scm.automation.service.queue.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.DataUtil;
import lombok.Getter;

/**
 * Abstract AbstractQueueService class.
 *
 * @author vipin.v
 * @version 1.0.34
 * @since 1.0.34
 */
public abstract class AbstractQueueService<T> extends AbstractBaseService<T> {
  private final Request baseRequest;
  @Getter private final QueueClient client;

  /**
   * Constructor for AbstractQueueService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.queue.QueueClient} object
   * @param request a {@link com.scm.automation.service.queue.model.Request} object
   * @since 1.0.62
   */
  protected AbstractQueueService(Settings settings, QueueClient client, Request request) {
    super(settings);
    this.client = client;
    this.baseRequest = this.mergeRequest(request, Request.class);
  }

  /** {@inheritDoc} */
  @Override
  public <R> R mergeRequest(R newRequest, Class<R> type) {
    JsonObject source = DataUtil.toJsonObject(newRequest);
    JsonObject target = DataUtil.toJsonObject(Request.builder().build());
    if (this.baseRequest != null) {
      target = DataUtil.toJsonObject(this.baseRequest);
    }

    JsonObject mergedRequest = DataUtil.deepMerge(source, target);
    return DataUtil.getGson().fromJson(mergedRequest, type);
  }

  /**
   * push.
   *
   * @param request a {@link com.scm.automation.service.queue.model.Request} object
   * @return a {@link com.scm.automation.service.queue.model.Response} object
   * @since 1.1.24
   */
  @Runnable
  @Step
  public Response push(Request request) {
    return this.getClient().sendMessage(this.mergeRequest(request, Request.class));
  }
}
