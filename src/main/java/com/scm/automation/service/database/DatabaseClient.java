package com.scm.automation.service.database;

import com.scm.automation.service.database.model.Request;
import com.scm.automation.service.database.model.Response;

/**
 * DatabaseClient interface.
 *
 * @author salil.devanshi
 * @version 1.0.32
 * @since 1.0.32
 */
public interface DatabaseClient {

  /**
   * fetch.
   *
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @return a {@link com.scm.automation.service.database.model.Response} object
   * @since 1.0.41
   */
  Response fetch(Request request);

  /**
   * add.
   *
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @return a {@link com.scm.automation.service.database.model.Response} object
   * @since 1.0.41
   */
  Response add(Request request);

  /**
   * update.
   *
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @return a {@link com.scm.automation.service.database.model.Response} object
   * @since 1.0.41
   */
  Response update(Request request);

  /**
   * delete.
   *
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @return a {@link com.scm.automation.service.database.model.Response} object
   * @since 1.0.41
   */
  Response delete(Request request);
}
