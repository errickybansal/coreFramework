package com.scm.automation.service;

import com.epam.reportportal.annotations.Step;
import com.google.gson.JsonObject;
import com.scm.automation.annotation.Runnable;
import com.scm.automation.service.rest.RestClient;
import com.scm.automation.service.rest.model.Request;
import com.scm.automation.service.rest.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.DataUtil;
import lombok.Getter;

/**
 * Abstract AbstractRestService class.
 *
 * @author vipin.v
 * @version 1.0.34
 * @since 1.0.34
 */
public abstract class AbstractRestService<T> extends AbstractBaseService<T> {
  private final Request baseRequest;
  @Getter private final RestClient client;

  /**
   * Constructor for AbstractRestService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.rest.RestClient} object
   * @param request a {@link com.scm.automation.service.rest.model.Request} object
   * @since 1.0.86
   */
  protected AbstractRestService(Settings settings, RestClient client, Request request) {
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

    if (source.has("ignoreBaseHeaders") && source.get("ignoreBaseHeaders").getAsBoolean()) {
      target.remove("headers");
    }

    JsonObject mergedRequest = DataUtil.deepMerge(source, target);
    return DataUtil.getGson().fromJson(mergedRequest, type);
  }

  /**
   * resolveRequest.
   *
   * @param request a {@link com.scm.automation.service.rest.model.Request} object
   * @return a {@link com.scm.automation.service.rest.model.Response} object
   * @since 1.1.24
   */
  @Runnable
  @Step
  public Response resolveRequest(Request request) {
    return this.getClient().resolveRequest(this.mergeRequest(request, Request.class));
  }
}
