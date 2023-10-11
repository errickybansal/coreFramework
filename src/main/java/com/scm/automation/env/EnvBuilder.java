package com.scm.automation.env;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.scm.automation.client.api.ConfigEntityControllerApi;
import com.scm.automation.client.model.EntityModelConfig;
import com.scm.automation.enums.DataType;
import com.scm.automation.enums.Env;
import com.scm.automation.exception.CoreException;
import com.scm.automation.util.BaseUtil;
import com.scm.automation.util.DataUtil;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

/**
 * EnvBuilder class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public class EnvBuilder {
  private static final Map<Env, String> envMap =
      ImmutableMap.ofEntries(
          Map.entry(Env.DEV, "com.scm.automation.env/dev.yaml"),
          Map.entry(Env.SIT, "com.scm.automation.env/sit.yaml"),
          Map.entry(Env.UAT, "com.scm.automation.env/uat.yaml"),
          Map.entry(Env.PROD, "com.scm.automation.env/prod.yaml"),
          Map.entry(Env.REPLICA, "com.scm.automation.env/replica.yaml"));

  private EnvBuilder() {}

  /**
   * build.
   *
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.0
   */
  public static JsonObject build() {
    try {
      Env env = BaseUtil.getConfig().getEnv();
      String resourcePath = envMap.get(env);
      ArrayList<URL> resourcesUrls =
          Collections.list(EnvBuilder.class.getClassLoader().getResources(resourcePath));

      JsonObject targetEnv = null;
      for (URL url : resourcesUrls) {
        JsonObject sourceEnv = DataUtil.read(url, DataType.YAML);
        targetEnv =
            DataUtil.deepMerge(sourceEnv, Optional.ofNullable(targetEnv).orElse(new JsonObject()));
      }
      return overrideConfig(targetEnv);
    } catch (Exception ex) {
      throw new CoreException(ex);
    }
  }

  private static JsonObject overrideConfig(JsonObject targetEnv) {
    try {
      if (targetEnv != null) {
        ConfigEntityControllerApi configApi = new ConfigEntityControllerApi();

        configApi
            .getApiClient()
            .setHttpClient(
                new ResteasyClientBuilder()
                    .establishConnectionTimeout(30, TimeUnit.SECONDS)
                    .connectionCheckoutTimeout(30, TimeUnit.SECONDS)
                    .socketTimeout(30, TimeUnit.SECONDS)
                    .build());
        configApi
            .getApiClient()
            .setBasePath(DataUtil.getAsString(targetEnv, "service.qaService.url"));

        EntityModelConfig entityModelConfigData =
            configApi.getItemResourceConfigGet(
                DataUtil.getAsString(targetEnv, "service.qaService.id"));
        if (entityModelConfigData != null && entityModelConfigData.getConfig() != null) {
          targetEnv =
              DataUtil.deepMerge(
                  DataUtil.toJsonObject(entityModelConfigData.getConfig()), targetEnv);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return targetEnv;
  }
}
