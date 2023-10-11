package com.scm.automation.service.database.model;

import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Data;

/**
 * Request class.
 *
 * @author salil.devanshi
 * @version 1.0.32
 * @since 1.0.32
 */
@Data
@Builder
public class Request {
  private String host;
  private String port;
  private String username;
  private String password;
  private String dbName;
  private String keyspace;
  private String collection;
  private String query;
  @Builder.Default private JsonObject params = new JsonObject();
  @Builder.Default private String datacenter = "datacenter1";
  @Builder.Default private Boolean ssl = false;
  @Builder.Default private Boolean executeIfPresent = true;
  private String authenticationDatabase;
}
