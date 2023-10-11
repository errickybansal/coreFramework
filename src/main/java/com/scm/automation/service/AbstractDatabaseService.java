package com.scm.automation.service;

import com.epam.reportportal.annotations.Step;
import com.google.gson.JsonObject;
import com.scm.automation.annotation.Runnable;
import com.scm.automation.service.database.DatabaseClient;
import com.scm.automation.service.database.model.Request;
import com.scm.automation.service.database.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.DataUtil;
import lombok.Getter;

/**
 * Abstract AbstractDatabaseService class.
 *
 * @author salil.devanshi
 * @version 1.0.38
 * @since 1.0.38
 */
public abstract class AbstractDatabaseService<T> extends AbstractBaseService<T> {
  private final Request baseRequest;
  @Getter private final DatabaseClient client;

  /**
   * Constructor for AbstractDatabaseService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.database.DatabaseClient} object
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @since 1.0.62
   */
  protected AbstractDatabaseService(Settings settings, DatabaseClient client, Request request) {
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
   * fetch.
   *
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @return a {@link com.scm.automation.service.database.model.Response} object
   * @since 1.1.24
   */
  @Runnable
  @Step
  public Response fetch(Request request) {
    return this.getClient().fetch(this.mergeRequest(request, Request.class));
  }
}
