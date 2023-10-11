package com.scm.automation.test;

import com.scm.automation.demo.ui.JioConnectPages;
import com.scm.automation.settings.Settings;
import com.scm.automation.test.model.TestData;
import com.scm.automation.test.testng.TestCase;
import com.scm.automation.ui.page.Properties;
import com.scm.automation.util.DataUtil;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * SeleniumTest class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class SeleniumTest extends TestCase {

  /**
   * beforeTestSuite.
   *
   * @since 1.0.77
   */
  @BeforeSuite
  public void beforeTestSuite() {
    this.setTestDataResourcePath("com.scm.automation.test/uiTestData.yaml");
  }

  /**
   * testLogin.
   *
   * @param data a {@link com.scm.automation.test.model.TestData} object
   * @since 1.0.77
   */
  @Test(dataProvider = "dataProvider")
  public void testLogin(TestData data) {
    JioConnectPages jio = new JioConnectPages();
    JioConnectPages newJio = new JioConnectPages();

    jio.getLoginPage()
        .withSettings(
            Settings.builder()
                .enableUrlNavigation(true)
                .timeout(
                    Settings.Timeout.builder()
                        .pageLoadTimeout(10000)
                        .waitTimeout(5000)
                        .waitInterval(1000)
                        .build())
                .build())
        .withProperties(Properties.builder().baseUrl("https://idm.jioconnect.com").build())
        .withValidator(jio.getLoginPage().getPageValidator()::validateLoginFailure)
        .withExpectedResult(data.getResponse().getAsJsonObject("login"))
        .login(
            DataUtil.getAsString(data.getRequest(), "login.username"),
            DataUtil.getAsString(data.getRequest(), "login.password"));

    newJio
        .getLoginPage()
        .login(
            DataUtil.getAsString(data.getRequest(), "login.username"),
            DataUtil.getAsString(data.getRequest(), "login.password"));

    jio.getForgotPasswordPage()
        .withSettings(Settings.builder().enableUrlNavigation(true).build())
        .resetPassword(DataUtil.getAsString(data.getRequest(), "resetPassword.username"));
  }
}
