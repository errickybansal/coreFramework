package com.scm.automation.demo.ui.password;

import com.google.common.collect.ImmutableMap;
import com.scm.automation.annotation.Loggable;
import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.locator.AbstractPageLocator;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * ForgotPasswordPageLocator class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class ForgotPasswordPageLocator extends AbstractPageLocator {

  /**
   * Constructor for ForgotPasswordPageLocator.
   *
   * @param driver a {@link com.scm.automation.ui.driver.AppDriver} object
   */
  public ForgotPasswordPageLocator(AppDriver driver) {
    super(driver);
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

  /**
   * usernameInput.
   *
   * @return a {@link org.openqa.selenium.WebElement} object
   */
  @Loggable
  public WebElement usernameInput() {
    return this.findElement(
        ImmutableMap.ofEntries(
            Map.entry("default", By.xpath("//input[@type='text' and @name ='username']"))));
  }

  /**
   * continueButton.
   *
   * @return a {@link org.openqa.selenium.WebElement} object
   */
  @Loggable
  public WebElement continueButton() {
    return this.findElement(
        ImmutableMap.ofEntries(
            Map.entry("default", By.xpath("//input[@type='submit' and @value ='Continue']"))));
  }
}
