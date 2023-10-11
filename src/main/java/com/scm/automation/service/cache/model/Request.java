package com.scm.automation.service.cache.model;

import com.scm.automation.enums.CacheRequestType;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * Request class.
 *
 * @author prakash.adak
 * @version 1.0.85
 * @since 1.0.85
 */
@Data
@Builder
public class Request {

  // Redis
  @Builder.Default private Map<String, Integer> hostAndPort = new HashMap<>();
  @Builder.Default private String password = "";
  @Builder.Default private String userName = "";
  private Body body;
  private CacheRequestType type;

  /**
   * Request class.
   *
   * @author prakash.adak
   * @version 1.0.85
   * @since 1.0.85
   */
  @Data
  @Builder
  public static class Body {
    private String key;
    private String field;
    private String value;
    private Map<String, String> valueMap;
  }
}
