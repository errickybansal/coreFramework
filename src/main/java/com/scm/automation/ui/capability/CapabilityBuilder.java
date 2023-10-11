package com.scm.automation.ui.capability;

import com.scm.automation.annotation.Loggable;
import com.scm.automation.exception.CoreException;
import com.scm.automation.util.PageUtil;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * CapabilityBuilder class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class CapabilityBuilder {

  private CapabilityBuilder() {}

  /**
   * build.
   *
   * @return a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @since 1.0.75
   */
  @Loggable
  public static DesiredCapabilities build() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities = capabilities.merge(channelCapabilities());
    capabilities = capabilities.merge(platformCapabilities());
    capabilities = capabilities.merge(executionCapabilities());
    if (PageUtil.isDesktop() || PageUtil.isMweb()) {
      capabilities = capabilities.merge(browserCapabilities());
    }
    return capabilities;
  }

  // region private methods

  private static DesiredCapabilities channelCapabilities() {
    DesiredCapabilities capabilities;
    switch (PageUtil.getConfig().getChannel()) {
      case DESKTOP -> {
        capabilities = new DesiredCapabilities();
      }
      case MWEB, APP -> {
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:newCommandTimeout", 3600);
        capabilities.setCapability("launchTimeout", 120000);
        capabilities.setCapability("appium:ensureWebviewsHavePages", true);
        capabilities.setCapability("appium:nativeWebScreenshot", true);
        capabilities.setCapability("appium:connectHardwareKeyboard", true);
      }
      default -> throw new CoreException("Channel not supported");
    }
    ;

    return capabilities;
  }

  private static DesiredCapabilities browserCapabilities() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    switch (PageUtil.getConfig().getBrowser()) {
      case CHROME -> {
        capabilities.setBrowserName("chrome");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--allow-running-insecure-content");
        options.setAcceptInsecureCerts(true);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
      }
      case FIREFOX -> {
        capabilities.setBrowserName("firefox");
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
      }
      default -> throw new CoreException("Browser not supported");
    }
    return capabilities;
  }

  private static DesiredCapabilities platformCapabilities() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    switch (PageUtil.getConfig().getPlatform()) {
      case LINUX -> capabilities.setPlatform(Platform.LINUX);
      case WINDOWS -> capabilities.setPlatform(Platform.WINDOWS);
      case MAC -> capabilities.setPlatform(Platform.MAC);
      case ANDROID -> {
        capabilities.setPlatform(Platform.ANDROID);
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("uiautomator2ServerInstallTimeout", 120000);
      }
      case IOS -> capabilities.setPlatform(Platform.IOS);
      default -> throw new CoreException("Platform not supported");
    }
    return capabilities;
  }

  private static DesiredCapabilities executionCapabilities() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    switch (PageUtil.getConfig().getExecution()) {
      case SELENOID -> {
        Map<String, Object> options = new HashMap<>();
        options.put("name", "Test run");
        options.put("sessionTimeout", "30m");
        options.put("enableVideo", true);
        options.put("enableVNC", true);
        options.put("enableLog", true);
        capabilities.setCapability("selenoid:options", options);
      }
      case MOBILAB -> {
        capabilities.setCapability("pCloudy_Username", "rama.kollamsetti@ril.com");
        capabilities.setCapability("pCloudy_ApiKey", "54fdm4fkbt4bp2ptzzs6jzdp");
        capabilities.setCapability("pCloudy_DeviceVersion", "11.0.0");
        capabilities.setCapability("platformVersion", "11.0.0");
        capabilities.setCapability("pCloudy_WildNet", "true");
        capabilities.setCapability("pCloudy_EnableVideo", "true");
        capabilities.setCapability("appiumVersion", "1.21.0");
        capabilities.setCapability("pCloudy_DurationInMinutes", 30);
      }
      case LOCAL -> {
        switch (PageUtil.getConfig().getPlatform()) {
          case WINDOWS, MAC, LINUX -> capabilities.setPlatform(Platform.ANY);
          case ANDROID -> capabilities.setPlatform(Platform.ANDROID);
          case IOS -> capabilities.setPlatform(Platform.IOS);
          default -> throw new CoreException("Platform not supported");
        }
      }
      default -> throw new CoreException("Execution platform not supported");
    }
    return capabilities;
  }

  // endregion
}
