package com.scm.automation.config;

import com.google.gson.JsonObject;
import com.scm.automation.annotation.Loggable;
import com.scm.automation.enums.Context;
import com.scm.automation.enums.Env;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

/**
 * BaseConfig class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public class BaseConfig {

  @Getter private final Env env;
  @Getter private final Set<Context> context;

  private static BaseConfig config = null;

  private BaseConfig(Env env, Set<Context> contextList) {
    this.env = env;
    this.context = new HashSet<>();
    this.context.add(Context.DEFAULT);
    this.context.addAll(contextList);
  }

  /**
   * configure.
   *
   * @return a {@link com.scm.automation.config.BaseConfig} object
   * @since 1.0.0
   */
  public static BaseConfig configure() {
    Env env =
        System.getProperty("env") != null
            ? Env.valueOf(System.getProperty("env").toUpperCase())
            : Env.SIT;
    Set<Context> contextList = new HashSet<>();
    if (System.getProperty("context") == null) {
      contextList.add(Context.DEFAULT);
    } else {
      Arrays.stream(System.getProperty("context").split("\\s+"))
          .forEach(context -> contextList.add(Context.valueOf(context.toUpperCase())));
    }

    return configure(env, contextList);
  }

  /**
   * configure.
   *
   * @param json a {@link com.google.gson.JsonObject} object
   * @return a {@link com.scm.automation.config.BaseConfig} object
   * @since 1.0.0
   */
  public static BaseConfig configure(JsonObject json) {
    if (json == null) {
      json = new JsonObject();
    }

    Env env = json.has("env") ? Env.valueOf(json.get("env").getAsString().toUpperCase()) : Env.SIT;
    Set<Context> contextList = new HashSet<>();

    if (json.has("context")) {
      json.get("context")
          .getAsJsonArray()
          .forEach(
              context -> contextList.add(Context.valueOf(context.getAsString().toUpperCase())));
    } else {
      contextList.add(Context.DEFAULT);
    }

    return configure(env, contextList);
  }

  /**
   * configure.
   *
   * @param env a {@link com.scm.automation.enums.Env} object
   * @param context a {@link java.util.Set} object
   * @return a {@link com.scm.automation.config.BaseConfig} object
   * @since 1.0.0
   */
  public static BaseConfig configure(Env env, Set<Context> context) {
    if (config == null) {
      return reconfigure(env, context);
    }
    return config;
  }

  /**
   * reconfigure.
   *
   * @param env a {@link com.scm.automation.enums.Env} object
   * @param context a {@link java.util.Set} object
   * @return a {@link com.scm.automation.config.BaseConfig} object
   * @since 1.0.0
   */
  @Loggable
  public static BaseConfig reconfigure(Env env, Set<Context> context) {
    config = new BaseConfig(env, context);
    return config;
  }

  // region private methods

  // endregion
}
