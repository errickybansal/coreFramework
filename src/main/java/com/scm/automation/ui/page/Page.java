package com.scm.automation.ui.page;

import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.locator.AbstractPageLocator;
import com.scm.automation.ui.page.model.Response;
import com.scm.automation.ui.validator.AbstractPageValidator;
import org.openqa.selenium.WebDriver;

/**
 * Page interface.
 *
 * @author vipin.v
 * @version 1.0.80
 * @since 1.0.80
 */
public interface Page<
    T extends AbstractPage, L extends AbstractPageLocator, V extends AbstractPageValidator> {
  /**
   * withProperties.
   *
   * @param properties a {@link com.scm.automation.ui.page.Properties} object
   * @return a T object
   */
  T withProperties(Properties properties);

  /**
   * getProperties.
   *
   * @return a {@link com.scm.automation.ui.page.Properties} object
   */
  Properties getProperties();

  /**
   * getUpdatedProperties.
   *
   * @return a {@link com.scm.automation.ui.page.Properties} object
   */
  Properties getUpdatedProperties();

  /** resetProperties. */
  void resetProperties();

  /**
   * getPageLocator.
   *
   * @return a L object
   */
  L getPageLocator();

  /**
   * getPageValidator.
   *
   * @return a V object
   */
  V getPageValidator();

  /**
   * getAppDriver.
   *
   * @return a {@link com.scm.automation.ui.driver.AppDriver} object
   */
  AppDriver getAppDriver();

  /**
   * getDriver.
   *
   * @return a {@link org.openqa.selenium.WebDriver} object
   */
  WebDriver getDriver();

  /** navigate. */
  void navigate();

  /** navigateToUrl. */
  void navigateToUrl();

  /** refresh. */
  void refresh();

  /**
   * createResponse.
   *
   * @param args an array of {@link java.lang.Object} objects
   * @param result a {@link java.lang.Object} object
   * @return a {@link com.scm.automation.ui.page.model.Response} object
   */
  Response createResponse(Object[] args, Object result);
}
