package com.scm.automation.service.soap.model;

import com.scm.automation.enums.HttpMethod;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * Request class.
 *
 * @author prakash.adak
 * @version 1.0.62
 * @since 1.0.62
 */
@Data
@Builder
public class Request {
  private String baseUri;
  private String basePath;
  private HttpMethod method;
  @Builder.Default private Map<String, String> headers = new HashMap<>();
  private boolean ignoreBaseHeaders;
  private String body;
  private String responseType;
}
