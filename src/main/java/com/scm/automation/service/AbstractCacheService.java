package com.scm.automation.service;

import com.google.gson.JsonObject;
import com.scm.automation.service.cache.CacheClient;
import com.scm.automation.service.cache.model.Request;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.DataUtil;
import lombok.Getter;

/**
 * Abstract AbstractCacheService class.
 *
 * @author prakash.adak
 * @version 1.0.86
 * @since 1.0.86
 */
public abstract class AbstractCacheService<T> extends AbstractBaseService<T> {
  private final Request baseRequest;
  @Getter private final CacheClient client;

  /**
   * Constructor for AbstractCacheService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.cache.CacheClient} object
   * @param request a {@link com.scm.automation.service.cache.model.Request} object
   * @since 1.0.86
   */
  protected AbstractCacheService(Settings settings, CacheClient client, Request request) {
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
}
