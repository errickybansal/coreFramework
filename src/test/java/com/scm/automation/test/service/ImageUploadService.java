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
 * ImageUploadService class.
 *
 * @author n.srinivas
 * @version 1.0.33
 * @since 1.0.33
 */
public class ImageUploadService extends AbstractRestService<ImageUploadService> {

  /**
   * Constructor for ImageUploadService.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param client a {@link com.scm.automation.service.rest.RestClient} object
   * @since 1.0.38
   */
  public ImageUploadService(Settings settings, RestClient client) {
    super(
        settings,
        client,
        Request.builder()
            .baseUri(BaseUtil.getEnvPropertyAsString("service.multiPartService.url"))
            .headers(
                ImmutableMap.ofEntries(
                    Map.entry("Content-Type", "multipart/form-data"),
                    Map.entry("Accept", "application/json")))
            .build());
  }

  /**
   * uploadImage.
   *
   * @param request a {@link com.scm.automation.service.rest.model.Request} object
   * @return a {@link com.scm.automation.service.rest.model.Response} object
   * @since 1.0.38
   */
  @Runnable
  public Response uploadImage(Request request) {
    request.setBasePath(
        BaseUtil.getEnvProperty("service.multiPartService.uploadImageApi").getAsString());
    request.setMethod(HttpMethod.POST);
    return this.getClient().resolveRequest(this.mergeRequest(request, Request.class));
  }
}
