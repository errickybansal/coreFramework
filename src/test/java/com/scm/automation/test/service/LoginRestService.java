package com.scm.automation.test.service;

import com.google.common.collect.ImmutableMap;
import com.scm.automation.annotation.Runnable;
import com.scm.automation.enums.HttpMethod;
import com.scm.automation.service.AbstractRestService;
import com.scm.automation.service.rest.RestClient;
import com.scm.automation.service.rest.model.Request;
import com.scm.automation.service.rest.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.BaseUtil;
import java.util.Map;

/**
 * DemoService class.
 *
 * @author vipin.v
 * @version 1.0.29
 * @since 1.0.29
 */
public class LoginRestService extends AbstractRestService<LoginRestService> {

  /**
   * Constructor for DemoService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.rest.RestClient} object
   * @since 1.0.35
   */
  public LoginRestService(Settings settings, RestClient client) {
    super(
        settings,
        client,
        Request.builder()
            .baseUri(BaseUtil.getEnvPropertyAsString("service.loginService.url"))
            .headers(
                ImmutableMap.ofEntries(
                    Map.entry("Content-Type", "application/json"),
                    Map.entry("Accept", "application/json"),
                    Map.entry("realm", "scps"),
                    Map.entry("clsType", "2")))
            .build());
  }

  /**
   * login.
   *
   * @param request a {@link com.scm.automation.service.rest.model.Request} object
   * @return a {@link com.scm.automation.service.rest.model.Response} object
   * @since 1.0.35
   */
  @Runnable
  public Response login(Request request) {
    request.setBasePath(BaseUtil.getEnvProperty("service.loginService.loginApi").getAsString());
    request.setMethod(HttpMethod.POST);
    return this.getClient().resolveRequest(this.mergeRequest(request, Request.class));
  }
}
