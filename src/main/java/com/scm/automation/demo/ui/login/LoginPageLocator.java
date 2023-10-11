package com.scm.automation.demo.ui.login;

import com.google.common.collect.ImmutableMap;
import com.scm.automation.annotation.Loggable;
import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.locator.AbstractPageLocator;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * LoginPageLocator class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class LoginPageLocator extends AbstractPageLocator {

  /**
   * Constructor for LoginPageLocator.
   *
   * @param driver a {@link com.scm.automation.ui.driver.AppDriver} object
   */
  public LoginPageLocator(AppDriver driver) {
    super(driver);
  }

  /**
   * usernameInput.
   *
   * @return a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.77
   */
  @Loggable
  public WebElement usernameInput() {
    return this.findElement(
        ImmutableMap.ofEntries(
            Map.entry("sit_app_android", By.xpath("//input[@type='text' and @name ='username']")),
            Map.entry("uat_android", By.xpath("//input[@type='text' and @name ='username']")),
            Map.entry("dev_desktop", By.xpath("//input[@type='text' and @name ='username']")),
            Map.entry("mweb", By.xpath("//input[@type='text' and @name ='username']")),
            Map.entry("android", By.xpath("//input[@type='text' and @name ='username']")),
            Map.entry("dev", By.xpath("//input[@type='text' and @name ='username']")),
            Map.entry("desktop", By.xpath("//input[@type='text' and @name ='username']")),
            Map.entry("default", By.xpath("//input[@type='text' and @name ='username']"))));
  }

  /**
   * passwordInput.
   *
   * @return a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.77
   */
  @Loggable
  public WebElement passwordInput() {
    return this.findElement(
        ImmutableMap.ofEntries(
            Map.entry("default", By.xpath("//input[@type='password' and @name ='password']"))));
  }

  /**
   * submitButton.
   *
   * @return a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.77
   */
  @Loggable
  public WebElement submitButton() {
    return this.findElement(
        ImmutableMap.ofEntries(
            Map.entry("default", By.xpath("//input[@type='submit' and @value ='Login']"))));
  }

  /**
   * invalidCredentialsText.
   *
   * @return a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.77
   */
  @Loggable
  public WebElement invalidCredentialsText() {
    return this.findElement(
        ImmutableMap.ofEntries(
            Map.entry("default", By.xpath("//*[@id=\"content-inner\"]/form/p"))));
  }

  /**
   * forgotPasswordLink.
   *
   * @return a {@link org.openqa.selenium.WebElement} object
   */
  @Loggable
  public WebElement forgotPasswordLink() {
    return this.findElement(
        ImmutableMap.ofEntries(Map.entry("default", By.xpath("//*[@id=\"link\"]/span[1]/a[1]"))));
  }
}
