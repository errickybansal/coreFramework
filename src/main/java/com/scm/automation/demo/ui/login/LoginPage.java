package com.scm.automation.demo.ui.login;

import com.scm.automation.annotation.Loggable;
import com.scm.automation.annotation.Runnable;
import com.scm.automation.settings.Settings;
import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.page.AbstractPage;
import com.scm.automation.ui.page.Properties;
import com.scm.automation.util.BaseUtil;

/**
 * LoginPage class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class LoginPage extends AbstractPage<LoginPage, LoginPageLocator, LoginPageValidator> {
  /**
   * Constructor for LoginPage.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param appDriver a {@link com.scm.automation.ui.driver.AppDriver} object
   */
  public LoginPage(Settings settings, AppDriver appDriver) {
    super(
        settings,
        appDriver,
        Properties.builder().baseUrl(BaseUtil.getEnvPropertyAsString("ui.jioConnect.url")).build());
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void navigate() {
    this.navigateToUrl();
  }

  /**
   * login.
   *
   * @param username a {@link java.lang.String} object
   * @param password a {@link java.lang.String} object
   * @since 1.0.77
   */
  @Runnable
  public void login(String username, String password) {
    this.getPageLocator().usernameInput().sendKeys(username);
    this.getPageLocator().passwordInput().sendKeys(password);
    this.getPageLocator().submitButton().click();
  }
}
