package com.scm.automation.demo.ui;

import com.scm.automation.demo.ui.login.LoginPage;
import com.scm.automation.demo.ui.password.ForgotPasswordPage;
import com.scm.automation.settings.Settings;
import com.scm.automation.settings.SettingsBuilder;
import com.scm.automation.ui.capability.CapabilityBuilder;
import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.driver.AppDriverFactory;
import java.net.URL;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * JioConnectPages class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class JioConnectPages {
  private final @Getter LoginPage loginPage;
  private final @Getter ForgotPasswordPage forgotPasswordPage;

  /** Constructor for JioConnectPages. */
  public JioConnectPages() {
    this(SettingsBuilder.build());
  }

  /**
   * Constructor for JioConnectPages.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   */
  public JioConnectPages(Settings settings) {
    this(null, CapabilityBuilder.build(), settings);
  }

  /**
   * Constructor for JioConnectPages.
   *
   * @param remoteWebDriverUrl a {@link java.net.URL} object
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @param settings a {@link com.scm.automation.settings.Settings} object
   */
  public JioConnectPages(
      URL remoteWebDriverUrl, DesiredCapabilities capabilities, Settings settings) {
    if (capabilities == null) {
      capabilities = CapabilityBuilder.build();
    }

    if (settings == null) {
      settings = SettingsBuilder.build();
    }

    AppDriver appDriver = AppDriverFactory.createDriver(remoteWebDriverUrl, capabilities, settings);
    this.loginPage = new LoginPage(settings, appDriver);
    this.forgotPasswordPage = new ForgotPasswordPage(settings, appDriver);
  }

  /**
   * getDriver.
   *
   * @return a {@link org.openqa.selenium.WebDriver} object
   */
  public WebDriver getDriver() {
    return this.getLoginPage().getDriver();
  }

  /** quit. */
  public void quit() {
    if (this.getDriver() != null) {
      this.getDriver().quit();
    }
  }
}
