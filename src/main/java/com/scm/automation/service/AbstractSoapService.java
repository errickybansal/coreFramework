package com.scm.automation.service;

import com.google.gson.JsonObject;
import com.scm.automation.service.soap.SoapClient;
import com.scm.automation.service.soap.model.Request;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.DataUtil;
import lombok.Getter;

/**
 * Abstract AbstractRestService class.
 *
 * @author vipin.v
 * @version 1.0.34
 * @since 1.0.34
 */
public abstract class AbstractSoapService<T> extends AbstractBaseService<T> {
  private final Request baseRequest;
  @Getter private final SoapClient client;

  /**
   * Constructor for AbstractSoapService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.soap.SoapClient} object
   * @param request a {@link com.scm.automation.service.soap.model.Request} object
   * @since 1.0.86
   */
  protected AbstractSoapService(Settings settings, SoapClient client, Request request) {
    super(settings);
    this.client = client;
    this.baseRequest = this.mergeRequest(request, Request.class);
  }

  /** {@inheritDoc} */
  @Override
  public <R> R mergeRequest(R newRequest, Class<R> type) {
    JsonObject source = DataUtil.toJsonObject(newRequest);
    JsonObject target = DataUtil.toJsonObject(Request.builder().build());
    if (this.baseRequest != null) {
      target = DataUtil.toJsonObject(this.baseRequest);
    }

    if (source.has("ignoreBaseHeaders") && source.get("ignoreBaseHeaders").getAsBoolean()) {
      target.remove("headers");
    }

    JsonObject mergedRequest = DataUtil.deepMerge(source, target);
    return DataUtil.getGson().fromJson(mergedRequest, type);
  }
}
