package com.scm.automation.settings;

import com.scm.automation.annotation.Loggable;
import com.scm.automation.util.BaseUtil;

/**
 * SettingsBuilder class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public class SettingsBuilder {

  private SettingsBuilder() {}

  /**
   * build.
   *
   * @return a {@link com.scm.automation.settings.Settings} object
   * @since 1.0.0
   */
  @Loggable
  public static Settings build() {
    return switch (BaseUtil.getConfig().getEnv()) {
      case DEV -> devSettings();
      case SIT -> sitSettings();
      case UAT -> uatSettings();
      case PROD -> prodSettings();
      case REPLICA -> replicaSettings();
    };
  }

  // region private methods

  private static Settings devSettings() {
    return Settings.builder().build();
  }

  private static Settings sitSettings() {
    return Settings.builder().build();
  }

  private static Settings uatSettings() {
    return Settings.builder().build();
  }

  private static Settings prodSettings() {
    return Settings.builder().build();
  }

  private static Settings replicaSettings() {
    return Settings.builder().build();
  }

  // endregion
}
