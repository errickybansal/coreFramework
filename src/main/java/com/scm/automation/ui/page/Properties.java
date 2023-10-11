package com.scm.automation.ui.page;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * Properties class.
 *
 * @author vipin.v
 * @version 1.0.79
 * @since 1.0.79
 */
@Data
@Builder
public class Properties {
  private String baseUrl;
  @Builder.Default private String path = "";
  @Builder.Default private Map<String, String> params = new HashMap<>();
}
