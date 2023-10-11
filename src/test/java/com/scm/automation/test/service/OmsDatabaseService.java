package com.scm.automation.test.service;

import com.scm.automation.service.AbstractDatabaseService;
import com.scm.automation.service.database.DatabaseClient;
import com.scm.automation.service.database.model.Request;
import com.scm.automation.service.database.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.BaseUtil;

/**
 * OMSDatabaseService class.
 *
 * @author salil.devanshi
 * @version 1.0.38
 * @since 1.0.38
 */
public class OmsDatabaseService extends AbstractDatabaseService<OmsDatabaseService> {

  /**
   * Constructor for OMSDatabaseService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.database.DatabaseClient} object
   */
  public OmsDatabaseService(Settings settings, DatabaseClient client) {
    super(
        settings,
        client,
        Request.builder()
            .host(BaseUtil.getEnvProperty("service.omsDatabaseService.host").getAsString())
            .port(BaseUtil.getEnvProperty("service.omsDatabaseService.port").getAsString())
            .username(BaseUtil.getEnvProperty("service.omsDatabaseService.username").getAsString())
            .password(BaseUtil.getEnvProperty("service.omsDatabaseService.password").getAsString())
            .dbName(BaseUtil.getEnvProperty("service.omsDatabaseService.dbName").getAsString())
            .build());
  }

  /**
   * getOMSDatabaseYFSShipment.
   *
   * @param request a {@link com.scm.automation.service.database.model.Request} object
   * @return a {@link com.scm.automation.service.database.model.Response} object
   */
  public Response getOmsDatabaseYfsShipment(Request request) {
    String query =
        BaseUtil.getEnvProperty("service.omsDatabaseService.getYFSShipment")
            .getAsString()
            .replace("${shipmentKey}", request.getParams().get("shipmentKey").getAsString());
    request.setQuery(query);
    return this.fetch(request);
  }
}
