package com.scm.automation.demo.ui.password;

import com.scm.automation.annotation.Loggable;
import com.scm.automation.annotation.Runnable;
import com.scm.automation.settings.Settings;
import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.page.AbstractPage;
import com.scm.automation.ui.page.Properties;
import com.scm.automation.util.BaseUtil;

/**
 * ForgotPasswordPage class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class ForgotPasswordPage
    extends AbstractPage<
        ForgotPasswordPage, ForgotPasswordPageLocator, ForgotPasswordPageValidator> {
  /**
   * Constructor for ForgotPasswordPage.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param appDriver a {@link com.scm.automation.ui.driver.AppDriver} object
   */
  public ForgotPasswordPage(Settings settings, AppDriver appDriver) {
    super(
        settings,
        appDriver,
        Properties.builder()
            .baseUrl(BaseUtil.getEnvPropertyAsString("ui.jioConnect.url"))
            .path("/oam-forgot-password/forgotPassword.jsp")
            .build());
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void navigate() {
    this.getPageLocator().forgotPasswordLink().click();
  }

  /**
   * resetPassword.
   *
   * @param username a {@link java.lang.String} object
   */
  @Runnable
  public void resetPassword(String username) {
    this.getPageLocator().usernameInput().sendKeys(username);
    this.getPageLocator().continueButton().click();
  }
}
