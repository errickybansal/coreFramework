package com.scm.automation.ui.driver;

import com.scm.automation.settings.Settings;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * MobileWebDriver class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class MobileWebDriver extends AbstractDriver {

  /**
   * Constructor for MobileWebDriver.
   *
   * @param serverUrl a {@link java.net.URL} object
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @since 1.0.75
   */
  public MobileWebDriver(URL serverUrl, DesiredCapabilities capabilities, Settings settings) {
    super(serverUrl, capabilities, settings);
  }
}
