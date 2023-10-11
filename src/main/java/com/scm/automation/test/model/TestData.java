package com.scm.automation.test.model;

import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Data;

/**
 * TestData class.
 *
 * @author vipin.v
 * @version 1.0.49
 * @since 1.0.49
 */
@Builder
@Data
public class TestData {
  private String testcaseId;
  private String testcaseName;
  private JsonObject request;
  private JsonObject response;
}
