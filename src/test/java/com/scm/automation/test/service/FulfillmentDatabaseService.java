package com.scm.automation.test.service;

import com.scm.automation.service.AbstractDatabaseService;
import com.scm.automation.service.database.DatabaseClient;
import com.scm.automation.service.database.model.Request;
import com.scm.automation.service.database.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.BaseUtil;

/**
 * FSDatabaseService class.
 *
 * @author salil.devanshi
 * @version 1.0.38
 * @since 1.0.38
 */
public class FulfillmentDatabaseService
    extends AbstractDatabaseService<FulfillmentDatabaseService> {

  /**
   * Constructor for FSDatabaseService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.database.DatabaseClient} object
   * @since 1.0.53
   */
  public FulfillmentDatabaseService(Settings settings, DatabaseClient client) {
    super(
        settings,
        client,
        Request.builder()
            .host(BaseUtil.getEnvProperty("service.fsDatabaseService.host").getAsString())
            .port(BaseUtil.getEnvProperty("service.fsDatabaseService.port").getAsString())
            .username(BaseUtil.getEnvProperty("service.fsDatabaseService.username").getAsString())
            .password(BaseUtil.getEnvProperty("service.fsDatabaseService.password").getAsString())
            .dbName(BaseUtil.getEnvProperty("service.fsDatabaseService.dbName").getAsString())
            .build());
  }

  /**
   * getFulfillmentLineStatusAudit.
   *
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @return a {@link com.scm.automation.service.database.model.Response} object
   * @since 1.0.53
   */
  public Response getFulfillmentLineStatusAudit(Request request) {
    String query =
        BaseUtil.getEnvProperty("service.fsDatabaseService.getFulfillmentLineStatusAudit")
            .getAsString()
            .replace("${orderNumber}", request.getParams().get("orderNumber").getAsString());
    request.setQuery(query);
    return this.getClient().fetch(this.mergeRequest(request, Request.class));
  }
}
