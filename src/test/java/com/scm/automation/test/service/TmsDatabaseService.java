package com.scm.automation.test.service;

import com.scm.automation.service.AbstractDatabaseService;
import com.scm.automation.service.database.DatabaseClient;
import com.scm.automation.service.database.model.Request;
import com.scm.automation.service.database.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.BaseUtil;

/**
 * TMSDatabaseService class.
 *
 * @author salil.devanshi
 * @version 1.0.38
 * @since 1.0.38
 */
public class TmsDatabaseService extends AbstractDatabaseService<TmsDatabaseService> {

  /**
   * Constructor for TMSDatabaseService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.database.DatabaseClient} object
   * @since 1.0.86
   */
  public TmsDatabaseService(Settings settings, DatabaseClient client) {
    super(
        settings,
        client,
        Request.builder()
            .host(BaseUtil.getEnvProperty("service.tmsDatabaseService.host").getAsString())
            .port(BaseUtil.getEnvProperty("service.tmsDatabaseService.port").getAsString())
            .username(BaseUtil.getEnvProperty("service.tmsDatabaseService.username").getAsString())
            .password(BaseUtil.getEnvProperty("service.tmsDatabaseService.password").getAsString())
            .keyspace(BaseUtil.getEnvProperty("service.tmsDatabaseService.keyspace").getAsString())
            .datacenter(
                BaseUtil.getEnvProperty("service.tmsDatabaseService.datacenter").getAsString())
            .build());
  }

  /**
   * getTMSDatabaseInvoiceDetails.
   *
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @return a {@link com.scm.automation.service.database.model.Response} object
   * @since 1.0.86
   */
  public Response getTmsDatabaseInvoiceDetails(Request request) {
    String query =
        BaseUtil.getEnvProperty("service.tmsDatabaseService.getInvoiceDetails").getAsString();
    request.setQuery(query);
    return this.fetch(request);
  }
}
