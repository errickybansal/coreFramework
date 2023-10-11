package com.scm.automation.test.service;

import com.google.common.collect.ImmutableMap;
import com.scm.automation.annotation.Loggable;
import com.scm.automation.annotation.Runnable;
import com.scm.automation.enums.HttpMethod;
import com.scm.automation.service.AbstractSoapService;
import com.scm.automation.service.soap.SoapClient;
import com.scm.automation.service.soap.model.Request;
import com.scm.automation.service.soap.model.Response;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.BaseUtil;
import java.util.Map;

/**
 * SoapService class.
 *
 * @author prakash.adak
 * @version 1.0.62
 * @since 1.0.62
 */
public class SoapService extends AbstractSoapService<SoapService> {

  /**
   * Constructor for SoapService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.soap.SoapClient} object
   * @since 1.0.86
   */
  public SoapService(Settings settings, SoapClient client) {
    super(
        settings,
        client,
        Request.builder()
            .baseUri(BaseUtil.getEnvPropertyAsString("service.xmlRestService.url"))
            .headers(
                ImmutableMap.ofEntries(
                    Map.entry("Content-Type", "application/soap+xml; charset=utf-8")))
            .build());
  }

  /**
   * createEwayBillGeneration.
   *
   * @param request a {@link com.scm.automation.service.soap.model.Request} object
   * @return a {@link com.scm.automation.service.soap.model.Response} object
   * @since 1.0.86
   */
  @Runnable
  @Loggable
  public Response createEwayBillGeneration(Request request) {
    request.setBasePath(BaseUtil.getEnvProperty("service.soapService.travelerApi").getAsString());
    request.setMethod(HttpMethod.POST);
    // return this.getClient().resolveRequest(this.mergeRequest(request, Request.class));
    return this.getClient().resolveRequest(this.mergeRequest(request, Request.class));
  }
}
