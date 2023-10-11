package com.scm.automation.ui.driver;

import com.scm.automation.settings.Settings;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * MobileAppDriver class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class MobileAppDriver extends AbstractDriver {

  /**
   * Constructor for MobileAppDriver.
   *
   * @param serverUrl a {@link java.net.URL} object
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @since 1.0.75
   */
  public MobileAppDriver(URL serverUrl, DesiredCapabilities capabilities, Settings settings) {
    super(serverUrl, capabilities, settings);
  }
}
