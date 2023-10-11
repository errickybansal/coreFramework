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
 * TravellerXmlService class.
 *
 * @author n.srinivas
 * @version 1.0.33
 * @since 1.0.33
 */
public class XmlRestService extends AbstractRestService<XmlRestService> {

  /**
   * Constructor for TravellerXmlService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.rest.RestClient} object
   * @since 1.0.38
   */
  public XmlRestService(Settings settings, RestClient client) {
    super(
        settings,
        client,
        Request.builder()
            .baseUri(BaseUtil.getEnvPropertyAsString("service.xmlRestService.url"))
            .headers(
                ImmutableMap.ofEntries(
                    Map.entry("Content-Type", "application/xml"),
                    Map.entry("Accept", "application/xml")))
            .build());
  }

  /**
   * createNewTraveller.
   *
   * @param request a {@link com.scm.automation.service.rest.model.Request} object
   * @return a {@link com.scm.automation.service.rest.model.Response} object
   * @since 1.0.38
   */
  @Runnable
  public Response createNewTraveller(Request request) {
    request.setBasePath(
        BaseUtil.getEnvProperty("service.xmlRestService.travelerApi").getAsString());
    request.setMethod(HttpMethod.POST);
    return this.getClient().resolveRequest(this.mergeRequest(request, Request.class));
  }

  /**
   * getOrderDetails.
   *
   * @param request a {@link com.scm.automation.service.rest.model.Request} object
   * @return a {@link com.scm.automation.service.rest.model.Response} object
   * @since 1.0.86
   */
  @Runnable
  public Response getOrderDetails(Request request) {
    request.setBaseUri(BaseUtil.getEnvProperty("service.getOrderDetails.url").getAsString());
    request.setBasePath(BaseUtil.getEnvProperty("service.getOrderDetails.endpoint").getAsString());
    request.setMethod(HttpMethod.POST);
    return this.getClient().resolveRequest(this.mergeRequest(request, Request.class));
  }
}
