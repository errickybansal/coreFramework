package com.scm.automation.ui.driver;

import com.google.common.collect.ImmutableMap;
import com.scm.automation.enums.Execution;
import com.scm.automation.exception.CoreException;
import com.scm.automation.settings.Settings;
import com.scm.automation.settings.SettingsBuilder;
import com.scm.automation.ui.capability.CapabilityBuilder;
import com.scm.automation.util.BaseUtil;
import com.scm.automation.util.PageUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * AppDriverFactory class.
 *
 * @author vipin.v
 * @version 1.0.79
 * @since 1.0.79
 */
public class AppDriverFactory {
  private static final InheritableThreadLocal<AppDriver> appDriver = new InheritableThreadLocal<>();
  private static final InheritableThreadLocal<List<AppDriver>> appDriverList =
      new InheritableThreadLocal<>();

  private AppDriverFactory() {}

  /**
   * getDriver.
   *
   * @return a {@link com.scm.automation.ui.driver.AppDriver} object
   * @since 1.0.79
   */
  public static AppDriver getDriver() {
    return getDriver(CapabilityBuilder.build());
  }

  /**
   * getDriver.
   *
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @return a {@link com.scm.automation.ui.driver.AppDriver} object
   * @since 1.0.79
   */
  public static AppDriver getDriver(DesiredCapabilities capabilities) {
    return getDriver(capabilities, SettingsBuilder.build());
  }

  /**
   * getDriver.
   *
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @return a {@link com.scm.automation.ui.driver.AppDriver} object
   * @since 1.0.79
   */
  public static AppDriver getDriver(DesiredCapabilities capabilities, Settings settings) {
    return getDriver(getServerUrl(), capabilities, settings);
  }

  /**
   * getDriver.
   *
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @param serverUrl a {@link java.net.URL} object
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @return a {@link com.scm.automation.ui.driver.AppDriver} object
   * @since 1.0.79
   */
  public static AppDriver getDriver(
      URL serverUrl, DesiredCapabilities capabilities, Settings settings) {
    if (appDriver.get() == null) {
      appDriver.set(createDriver(serverUrl, capabilities, settings));
    }
    return appDriver.get();
  }

  /**
   * createDriver.
   *
   * @return a {@link com.scm.automation.ui.driver.AppDriver} object
   * @since 1.0.79
   */
  public static AppDriver createDriver() {
    return createDriver(CapabilityBuilder.build());
  }

  /**
   * createDriver.
   *
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @return a {@link com.scm.automation.ui.driver.AppDriver} object
   * @since 1.0.79
   */
  public static AppDriver createDriver(DesiredCapabilities capabilities) {
    return createDriver(capabilities, SettingsBuilder.build());
  }

  /**
   * createDriver.
   *
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @return a {@link com.scm.automation.ui.driver.AppDriver} object
   * @since 1.0.79
   */
  public static AppDriver createDriver(DesiredCapabilities capabilities, Settings settings) {
    return createDriver(getServerUrl(), capabilities, settings);
  }

  /**
   * createDriver.
   *
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @param serverUrl a {@link java.net.URL} object
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @return a {@link com.scm.automation.ui.driver.AppDriver} object
   * @since 1.0.79
   */
  public static AppDriver createDriver(
      URL serverUrl, DesiredCapabilities capabilities, Settings settings) {
    if (serverUrl == null) {
      serverUrl = getServerUrl();
    }

    capabilities = CapabilityBuilder.build().merge(capabilities);
    AppDriver driver;

    switch (PageUtil.getConfig().getChannel()) {
      case DESKTOP -> driver = new DesktopWebDriver(serverUrl, capabilities, settings);
      case MWEB -> driver = new MobileWebDriver(serverUrl, capabilities, settings);
      case APP -> driver = new MobileAppDriver(serverUrl, capabilities, settings);
      default -> throw new CoreException("Unknown channel");
    }

    if (appDriverList.get() == null) {
      appDriverList.set(new ArrayList<>());
    }
    appDriverList.get().add(driver);

    if (appDriver.get() == null) {
      appDriver.set(driver);
    }

    return driver;
  }

  /**
   * quit.
   *
   * @since 1.0.79
   */
  public static void quit() {
    if (appDriver.get() != null) {
      appDriver.get().quit();
      appDriver.remove();
    }

    if (appDriverList.get() != null) {
      for (AppDriver driver : appDriverList.get()) {
        driver.quit();
      }
      appDriverList.remove();
    }
  }

  // region private methods

  private static final Map<Execution, String> seleniumServerMap =
      ImmutableMap.ofEntries(
          Map.entry(Execution.LOCAL, "http://127.0.0.1:4444/wd/hub"),
          Map.entry(Execution.SELENOID, BaseUtil.getEnvPropertyAsString("ui.selenoid.hub")));

  private static final Map<Execution, String> appiumServerMap =
      ImmutableMap.ofEntries(
          Map.entry(Execution.LOCAL, "http://127.0.0.1:4723"),
          Map.entry(Execution.MOBILAB, BaseUtil.getEnvPropertyAsString("ui.mobilab.hub")));

  private static URL getServerUrl() {
    try {
      if (PageUtil.getConfig().getServer() != null) {
        return new URL(PageUtil.getConfig().getServer());
      }
      return switch (PageUtil.getConfig().getChannel()) {
        case DESKTOP -> new URL(seleniumServerMap.get(PageUtil.getConfig().getExecution()));
        case MWEB, APP -> new URL(appiumServerMap.get(PageUtil.getConfig().getExecution()));
      };
    } catch (MalformedURLException ex) {
      throw new CoreException(ex);
    }
  }

  // endregion
}
