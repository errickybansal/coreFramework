package com.scm.automation.test;

import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.driver.AppDriverFactory;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

/**
 * AppiumTest class.
 *
 * @author vipin.v
 * @version 1.0.83
 * @since 1.0.83
 */
public class AppiumTest {
  /** testAndroidApp. */
  @Test
  public void testAndroidApp() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("appPackage", "com.ril.ajio");
    capabilities.setCapability("pCloudy_ApplicationName", "ajio.apk");
    AppDriver driver = AppDriverFactory.getDriver(capabilities);
    System.out.println(driver.toString());
    driver.quit();
  }

  /** testAndroidWeb. */
  @Test
  public void testAndroidWeb() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    AppDriver driver = AppDriverFactory.getDriver(capabilities);
    System.out.println(driver.toString());
    driver.navigateToUrl("https://www.google.com");
    driver.waitForPageLoad();
    driver.refresh();
    driver.quit();
  }
}
