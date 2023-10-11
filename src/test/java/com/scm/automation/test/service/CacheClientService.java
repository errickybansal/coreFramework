package com.scm.automation.test.service;

import com.google.common.collect.ImmutableMap;
import com.scm.automation.enums.CacheRequestType;
import com.scm.automation.service.AbstractCacheService;
import com.scm.automation.service.cache.CacheClient;
import com.scm.automation.service.cache.model.Request;
import com.scm.automation.service.cache.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.BaseUtil;
import java.util.Map;

/**
 * CacheClientService class.
 *
 * @author prakash.adak
 * @version 1.0.86
 * @since 1.0.86
 */
public class CacheClientService extends AbstractCacheService<CacheClientService> {

  /**
   * Constructor for CacheClientService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.cache.CacheClient} object
   */
  public CacheClientService(Settings settings, CacheClient client) {
    super(
        settings,
        client,
        Request.builder()
            .password(BaseUtil.getEnvProperty("service.cacheService.password").getAsString())
            .hostAndPort(
                ImmutableMap.ofEntries(
                    Map.entry(
                        BaseUtil.getEnvProperty("service.cacheService.host").getAsString(),
                        BaseUtil.getEnvProperty("service.cacheService.port").getAsInt())))
            .build());
  }

  /**
   * getCacheDetails.
   *
   * @param request a {@link com.scm.automation.service.cache.model.Request} object
   * @return a {@link com.scm.automation.service.cache.model.Response} object
   */
  public Response getCacheDetails(Request request) {
    request.setType(CacheRequestType.GET);
    return this.getClient().get(this.mergeRequest(request, Request.class));
  }

  /**
   * getAllCacheDetails.
   *
   * @param request a {@link com.scm.automation.service.cache.model.Request} object
   * @return a {@link com.scm.automation.service.cache.model.Response} object
   */
  public Response getAllCacheDetails(Request request) {
    request.setType(CacheRequestType.GET_ALL);
    return this.getClient().get(this.mergeRequest(request, Request.class));
  }

  /**
   * setCacheDetails.
   *
   * @param request a {@link com.scm.automation.service.cache.model.Request} object
   * @return a {@link com.scm.automation.service.cache.model.Response} object
   */
  public Response setCacheDetails(Request request) {
    request.setType(CacheRequestType.SET);
    return this.getClient().set(this.mergeRequest(request, Request.class));
  }

  /**
   * setAllCacheDetails.
   *
   * @param request a {@link com.scm.automation.service.cache.model.Request} object
   * @return a {@link com.scm.automation.service.cache.model.Response} object
   */
  public Response setAllCacheDetails(Request request) {
    request.setType(CacheRequestType.SET_ALL);
    return this.getClient().set(this.mergeRequest(request, Request.class));
  }
}
