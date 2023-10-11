package com.scm.automation.service.cache;

import com.scm.automation.service.cache.model.Request;
import com.scm.automation.service.cache.model.Response;

/**
 * CacheClient interface.
 *
 * @author prakash.adak
 * @version 1.0.86
 * @since 1.0.86
 */
public interface CacheClient {

  /**
   * get.
   *
   * @param request a {@link com.scm.automation.service.cache.model.Request} object
   * @return a {@link com.scm.automation.service.cache.model.Response} object
   * @since 1.0.86
   */
  Response get(Request request);

  /**
   * contains.
   *
   * @param request a {@link com.scm.automation.service.cache.model.Request} object
   * @return a {@link com.scm.automation.service.cache.model.Response} object
   * @since 1.0.86
   */
  Response contains(Request request);

  /**
   * set.
   *
   * @param request a {@link com.scm.automation.service.cache.model.Request} object
   * @return a {@link com.scm.automation.service.cache.model.Response} object
   * @since 1.0.86
   */
  Response set(Request request);

  /**
   * delete.
   *
   * @param request a {@link com.scm.automation.service.cache.model.Request} object
   * @return a {@link com.scm.automation.service.cache.model.Response} object
   * @since 1.0.86
   */
  Response delete(Request request);
}
