package com.scm.automation.test.service;

import com.scm.automation.service.AbstractDatabaseService;
import com.scm.automation.service.database.DatabaseClient;
import com.scm.automation.service.database.model.Request;
import com.scm.automation.service.database.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.BaseUtil;

/**
 * FsMongoDatabaseService class.
 *
 * @author nishita.shetty
 * @version 1.0.38
 * @since 1.0.38
 */
public class FsMongoDatabaseService extends AbstractDatabaseService<FsMongoDatabaseService> {

  /**
   * Constructor for FsMongoDatabaseService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.database.DatabaseClient} object
   * @since 1.0.53
   */
  public FsMongoDatabaseService(Settings settings, DatabaseClient client) {
    super(
        settings,
        client,
        Request.builder()
            .host(BaseUtil.getEnvProperty("service.fsMongoDatabaseService.host").getAsString())
            .port(BaseUtil.getEnvProperty("service.fsMongoDatabaseService.port").getAsString())
            .username(
                BaseUtil.getEnvProperty("service.fsMongoDatabaseService.username").getAsString())
            .password(
                BaseUtil.getEnvProperty("service.fsMongoDatabaseService.password").getAsString())
            .dbName(BaseUtil.getEnvProperty("service.fsMongoDatabaseService.dbName").getAsString())
            .collection(
                BaseUtil.getEnvProperty("service.fsMongoDatabaseService.collection").getAsString())
            .build());
  }

  /**
   * getFsMongoDatabaseFetchDetails.
   *
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @return a {@link com.scm.automation.service.database.model.Response} object
   * @since 1.0.53
   */
  public Response getFsMongoDatabaseFetchDetails(Request request) {
    return this.getClient().fetch(this.mergeRequest(request, Request.class));
  }

  /**
   * getFsMongoDatabaseUpdateDetails.
   *
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @return a {@link com.scm.automation.service.database.model.Response} object
   * @since 1.0.53
   */
  public Response getFsMongoDatabaseUpdateDetails(Request request) {
    return this.getClient().update(this.mergeRequest(request, Request.class));
  }
}
