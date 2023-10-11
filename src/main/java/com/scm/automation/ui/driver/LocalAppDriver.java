package com.scm.automation.ui.driver;

import com.scm.automation.exception.CoreException;
import com.scm.automation.util.PageUtil;
import io.appium.java_client.AppiumDriver;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

class LocalAppDriver {

  private LocalAppDriver() {}

  /**
   * start.
   *
   * @param serverUrl a {@link java.net.URL} object
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @return a {@link org.openqa.selenium.WebDriver} object
   * @since 1.0.84
   */
  public static WebDriver start(URL serverUrl, DesiredCapabilities capabilities) {
    switch (PageUtil.getConfig().getChannel()) {
      case DESKTOP -> {
        switch (PageUtil.getConfig().getBrowser()) {
          case CHROME -> {
            ChromeOptions options = new ChromeOptions();
            options = options.merge(capabilities);
            return new ChromeDriver(options);
          }
          case FIREFOX -> {
            FirefoxOptions options = new FirefoxOptions();
            options.setAcceptInsecureCerts(true);
            return new FirefoxDriver(options);
          }
          default -> throw new CoreException("Unknown browser");
        }
      }
      case MWEB, APP -> {
        return new AppiumDriver(serverUrl, capabilities);
      }
      default -> throw new CoreException("Unknown channel");
    }
  }
}
