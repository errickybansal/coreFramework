package com.scm.automation.ui.locator;

import com.scm.automation.annotation.Loggable;
import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.util.BaseUtil;
import com.scm.automation.util.PageUtil;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * PageLocator class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public abstract class AbstractPageLocator {
  private final @Getter AppDriver appDriver;
  private final @Getter WebDriver driver;
  private static final List<String> keyList;

  static {
    String env = BaseUtil.getConfig().getEnv().name().toLowerCase();
    String channel = PageUtil.getConfig().getChannel().name().toLowerCase();
    String platform = PageUtil.getConfig().getPlatform().name().toLowerCase();

    keyList = new ArrayList<>();
    keyList.add(MessageFormat.format("{0}_{1}_{2}", env, channel, platform));
    keyList.add(MessageFormat.format("{0}_{1}", channel, platform));
    keyList.add(MessageFormat.format("{0}_{1}", env, platform));
    keyList.add(MessageFormat.format("{0}_{1}", env, channel));
    keyList.add(MessageFormat.format("{0}", platform));
    keyList.add(MessageFormat.format("{0}", channel));
    keyList.add(MessageFormat.format("{0}", env));
  }

  /**
   * Constructor for PageLocator.
   *
   * @param driver a {@link com.scm.automation.ui.driver.AppDriver} object
   * @since 1.0.75
   */
  protected AbstractPageLocator(AppDriver driver) {
    this.appDriver = driver;
    this.driver = driver.getDriver();
  }

  /**
   * findElement.
   *
   * @param map a {@link java.util.Map} object
   * @return a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.75
   */
  @Loggable
  public WebElement findElement(Map<String, By> map) {
    By locator = getLocator(map);
    this.getAppDriver().waitUntilElementPresent(locator);
    return this.getDriver().findElement(locator);
  }

  /**
   * findElement.
   *
   * @param parent a {@link org.openqa.selenium.WebElement} object
   * @param map a {@link java.util.Map} object
   * @return a {@link org.openqa.selenium.WebElement} object
   * @since 1.0.75
   */
  @Loggable
  public WebElement findElement(WebElement parent, Map<String, By> map) {
    By locator = getLocator(map);
    return parent.findElement(locator);
  }

  /**
   * findElements.
   *
   * @param map a {@link java.util.Map} object
   * @return a {@link java.util.List} object
   * @since 1.0.75
   */
  @Loggable
  public List<WebElement> findElements(Map<String, By> map) {
    By locator = getLocator(map);
    this.getAppDriver().waitUntilElementPresent(locator);
    return this.getDriver().findElements(locator);
  }

  /**
   * findElements.
   *
   * @param parent a {@link org.openqa.selenium.WebElement} object
   * @param map a {@link java.util.Map} object
   * @return a {@link java.util.List} object
   * @since 1.0.75
   */
  @Loggable
  public List<WebElement> findElements(WebElement parent, Map<String, By> map) {
    By locator = getLocator(map);
    return parent.findElements(locator);
  }

  /**
   * getLocator.
   *
   * @param map a {@link java.util.Map} object
   * @return a {@link org.openqa.selenium.By} object
   * @since 1.0.75
   */
  @Loggable
  public static By getLocator(Map<String, By> map) {
    for (String key : keyList) {
      if (map.containsKey(key)) {
        return map.get(key);
      }
    }
    return map.get("default");
  }
}
