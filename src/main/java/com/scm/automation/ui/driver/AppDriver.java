package com.scm.automation.ui.driver;

import com.google.gson.JsonObject;
import com.scm.automation.settings.Settings;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * AppDriver interface.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public interface AppDriver {
  /**
   * getDriver.
   *
   * @return a {@link org.openqa.selenium.WebDriver} object
   * @since 1.0.80
   */
  WebDriver getDriver();

  /**
   * getExecutor.
   *
   * @return a {@link org.openqa.selenium.JavascriptExecutor} object
   * @since 1.0.80
   */
  JavascriptExecutor getExecutor();

  /**
   * getAction.
   *
   * @return a {@link org.openqa.selenium.interactions.Actions} object
   * @since 1.0.80
   */
  Actions getAction();

  /**
   * getWebDriverWait.
   *
   * @return a {@link org.openqa.selenium.support.ui.WebDriverWait} object
   * @since 1.0.80
   */
  WebDriverWait getWebDriverWait();

  /**
   * getFluentWait.
   *
   * @return a {@link org.openqa.selenium.support.ui.FluentWait} object
   * @since 1.0.80
   */
  FluentWait<WebDriver> getFluentWait();

  /**
   * quit.
   *
   * @since 1.0.80
   */
  void quit();

  /**
   * getDefaultTimeout.
   *
   * @return a {@link com.scm.automation.settings.Settings.Timeout} object
   * @since 1.0.80
   */
  Settings.Timeout getDefaultTimeout();

  /**
   * applyTimeout.
   *
   * @param timeout a {@link com.scm.automation.settings.Settings.Timeout} object
   * @since 1.0.80
   */
  void applyTimeout(Settings.Timeout timeout);

  /**
   * navigateToUrl.
   *
   * @param url a {@link java.lang.String} object
   * @since 1.0.80
   */
  void navigateToUrl(String url);

  /**
   * refresh.
   *
   * @since 1.0.80
   */
  void refresh();

  /**
   * getUrl.
   *
   * @return a {@link java.lang.String} object
   * @since 1.0.80
   */
  String getUrl();

  /**
   * getLocator.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @return a {@link org.openqa.selenium.By} object
   * @since 1.0.80
   */
  By getLocator(WebElement element);

  /**
   * waitForPageLoad.
   *
   * @since 1.0.80
   */
  void waitForPageLoad();

  /**
   * waitUntilUrlChangedTo.
   *
   * @param url a {@link java.lang.String} object
   * @since 1.0.80
   */
  void waitUntilUrlChangedTo(String url);

  /**
   * waitUntilUrlChangedFrom.
   *
   * @param url a {@link java.lang.String} object
   * @since 1.0.80
   */
  void waitUntilUrlChangedFrom(String url);

  /**
   * waitUntilElementPresent.
   *
   * @param locator a {@link org.openqa.selenium.By} object
   * @since 1.0.80
   */
  void waitUntilElementPresent(By locator);

  /**
   * waitUntilElementVisible.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.80
   */
  void waitUntilElementVisible(WebElement element);

  /**
   * waitUntilElementNotVisible.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.80
   */
  void waitUntilElementNotVisible(WebElement element);

  /**
   * waitUntilElementClickable.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.80
   */
  void waitUntilElementClickable(WebElement element);

  /**
   * scrollToElement.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.80
   */
  void scrollToElement(WebElement element);

  /**
   * moveToElement.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.80
   */
  void moveToElement(WebElement element);

  /**
   * clickElement.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.80
   */
  void clickElement(WebElement element);

  /**
   * clickElementJS.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.80
   */
  @SuppressWarnings("AbbreviationAsWordInName")
  void clickElementJS(WebElement element);

  /**
   * clickElementAction.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.80
   */
  void clickElementAction(WebElement element);

  /**
   * getAttribute.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @param attribute a {@link java.lang.String} object
   * @return a {@link java.lang.String} object
   * @since 1.0.80
   */
  String getAttribute(WebElement element, String attribute);

  /**
   * setText.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @param value a {@link java.lang.String} object
   * @since 1.0.80
   */
  void setText(WebElement element, String value);

  /**
   * clearText.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.80
   */
  void clearText(WebElement element);

  /**
   * getText.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @return a {@link java.lang.String} object
   * @since 1.0.80
   */
  String getText(WebElement element);

  /**
   * selectDropdown.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @param valueOrText a {@link java.lang.String} object
   * @since 1.0.80
   */
  void selectDropdown(WebElement element, String valueOrText);

  /**
   * selectDropdown.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @param index a {@link java.lang.Integer} object
   * @since 1.0.80
   */
  void selectDropdown(WebElement element, Integer index);

  /**
   * switchToWindow.
   *
   * @param url a {@link java.net.URL} object
   * @since 1.0.80
   */
  void switchToWindow(URL url);

  /**
   * switchToWindow.
   *
   * @param title a {@link java.lang.String} object
   * @since 1.0.80
   */
  void switchToWindow(String title);

  /**
   * switchToWindow.
   *
   * @param index a {@link java.lang.Integer} object
   * @since 1.0.80
   */
  void switchToWindow(Integer index);

  /**
   * switchToFrame.
   *
   * @param name a {@link java.lang.String} object
   * @since 1.0.80
   */
  void switchToFrame(String name);

  /**
   * switchToFrame.
   *
   * @param element a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.80
   */
  void switchToFrame(WebElement element);

  /**
   * switchToFrame.
   *
   * @param index a {@link java.lang.Integer} object
   * @since 1.0.80
   */
  void switchToFrame(Integer index);

  /**
   * switchToParentFrame.
   *
   * @since 1.0.80
   */
  void switchToParentFrame();

  /**
   * switchToAlert.
   *
   * @since 1.0.80
   */
  void switchToAlert();

  /**
   * getScreenshot.
   *
   * @param type a {@link org.openqa.selenium.OutputType} object
   * @param <T> a T class
   * @return a T object
   * @since 1.0.80
   */
  <T> T getScreenshot(OutputType<T> type);

  /**
   * getSessionDetails.
   *
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.80
   */
  JsonObject getSessionDetails();
}
