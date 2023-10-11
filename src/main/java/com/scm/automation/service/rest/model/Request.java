package com.scm.automation.service.rest.model;

import com.google.gson.JsonObject;
import com.scm.automation.enums.HttpMethod;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * Request class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
public class Request {
  private String baseUri;
  private String basePath;
  private HttpMethod method;
  private String accept;
  @Builder.Default private Map<String, String> headers = new HashMap<>();
  private boolean ignoreBaseHeaders;
  private JsonObject params;
  private String body;
  private JsonObject multiPart;
  private Integer timeout;
  private Boolean withCredentials;
  private Authorization authorization;
  private ResponseType responseType;
  private Integer maxContentLength;
  private Integer maxBodyLength;
  @Builder.Default private boolean verifySsl = false;

  /**
   * Authorization class.
   *
   * @author vipin.v
   * @version 1.0.0
   */
  @Data
  @Builder
  public static class Authorization {
    private String username;
    private String password;
  }

  /** ResponseType class. */
  public enum ResponseType {
    TEXT,
    BYTE_ARRAY,
  }
}
