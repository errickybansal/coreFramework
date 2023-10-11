package com.scm.automation.ui.driver;

import com.google.gson.JsonObject;
import com.scm.automation.annotation.Loggable;
import com.scm.automation.exception.CoreException;
import com.scm.automation.settings.Settings;
import com.scm.automation.util.BaseUtil;
import com.scm.automation.util.PageUtil;
import io.appium.java_client.AppiumDriver;
import java.net.URL;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

class AbstractDriver implements AppDriver {
  @Getter WebDriver driver;
  @Getter JavascriptExecutor executor;
  @Getter Actions action;
  @Getter WebDriverWait webDriverWait;
  @Getter FluentWait<WebDriver> fluentWait;
  @Getter Settings.Timeout defaultTimeout;

  /**
   * Constructor for AbstractDriver.
   *
   * @param serverUrl a {@link java.net.URL} object
   * @param capabilities a {@link org.openqa.selenium.remote.DesiredCapabilities} object
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @since 1.0.80
   */
  protected AbstractDriver(URL serverUrl, DesiredCapabilities capabilities, Settings settings) {
    switch (PageUtil.getConfig().getExecution()) {
      case LOCAL -> this.driver = LocalAppDriver.start(serverUrl, capabilities);
      case SELENOID -> this.driver = new RemoteWebDriver(serverUrl, capabilities);
      case MOBILAB -> this.driver = new AppiumDriver(serverUrl, capabilities);
      default -> throw new CoreException("Unknown execution");
    }

    this.applyTimeout(settings.getTimeout());
    this.defaultTimeout = settings.getTimeout();

    this.executor = (JavascriptExecutor) this.getDriver();
    this.action = new Actions(this.getDriver());

    if (PageUtil.isDesktop()) {
      this.getDriver().manage().window().maximize();
    }
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void quit() {
    if (this.getDriver() != null) {
      this.getDriver().quit();
      this.driver = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void applyTimeout(Settings.Timeout timeout) {
    if (this.getDefaultTimeout() != null && this.getDefaultTimeout().equals(timeout)
        || timeout == null) {
      return;
    }

    this.getDriver()
        .manage()
        .timeouts()
        .implicitlyWait(Duration.ofMillis(timeout.getImplicitlyWait()));

    this.webDriverWait =
        new WebDriverWait(
            this.getDriver(),
            Duration.ofMillis(timeout.getWaitTimeout()),
            Duration.ofMillis(timeout.getWaitInterval()));

    this.fluentWait =
        new FluentWait<>(this.getDriver())
            .withTimeout(Duration.ofMillis(timeout.getWaitTimeout()))
            .pollingEvery(Duration.ofMillis(timeout.getWaitInterval()));

    if (PageUtil.isDesktop() || PageUtil.isMweb()) {
      this.getDriver()
          .manage()
          .timeouts()
          .pageLoadTimeout(Duration.ofMillis(timeout.getPageLoadTimeout()));

      this.getDriver()
          .manage()
          .timeouts()
          .scriptTimeout(Duration.ofMillis(timeout.getScriptTimeout()));
    }
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void navigateToUrl(String url) {
    this.getDriver().get(url);
    this.waitForPageLoad();
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void refresh() {
    this.getDriver().navigate().refresh();
    this.waitForPageLoad();
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public String getUrl() {
    this.waitForPageLoad();
    return this.getDriver().getCurrentUrl();
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public By getLocator(WebElement element) {
    String[] by = (element.toString().split("->")[1].replaceFirst("(?s)(.*)]", "$1")).split(":");
    String locator = by[0].trim();
    String value = by[1].trim();

    return switch (locator) {
      case "id" -> By.id(value);
      case "linkText" -> By.linkText(value);
      case "partialLinkText" -> By.partialLinkText(value);
      case "name" -> By.name(value);
      case "tagName" -> By.tagName(value);
      case "xpath" -> By.xpath(value);
      case "className" -> By.className(value);
      case "cssSelector" -> By.cssSelector(value);
      default -> throw new IllegalStateException("Unexpected value: " + locator);
    };
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void waitForPageLoad() {
    this.getWebDriverWait()
        .withMessage("Page load failed")
        .until(
            ExpectedConditions.jsReturnsValue("return document.readyState")
                .andThen(state -> state.equals("complete")));
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void waitUntilUrlChangedTo(String url) {
    this.getWebDriverWait()
        .withMessage("Page url did not change to " + url)
        .until(ExpectedConditions.urlContains(url));
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void waitUntilUrlChangedFrom(String url) {
    this.getWebDriverWait()
        .withMessage("Page url did not change from " + url)
        .until(ExpectedConditions.not(ExpectedConditions.urlContains(url)));
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void waitUntilElementPresent(By locator) {
    this.getWebDriverWait()
        .withMessage("Element is not present")
        .until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void waitUntilElementVisible(WebElement element) {
    this.getWebDriverWait()
        .withMessage("Element is not visible")
        .until(ExpectedConditions.visibilityOf(element));
  }

  /** {@inheritDoc} */
  @Override
  public void waitUntilElementNotVisible(WebElement element) {
    this.getWebDriverWait()
        .withMessage("Element is visible")
        .until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void waitUntilElementClickable(WebElement element) {
    this.getWebDriverWait()
        .withMessage("Element is not clickable")
        .until(ExpectedConditions.elementToBeClickable(element));
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void scrollToElement(WebElement element) {
    this.waitUntilElementVisible(element);
    this.getExecutor().executeScript("arguments[0].scrollIntoView(true);", element);
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void moveToElement(WebElement element) {
    this.waitUntilElementVisible(element);
    this.getAction().moveToElement(element).perform();
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void clickElement(WebElement element) {
    this.waitUntilElementClickable(element);
    this.scrollToElement(element);
    element.click();
    this.waitForPageLoad();
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void clickElementJS(WebElement element) {
    this.waitUntilElementClickable(element);
    this.scrollToElement(element);
    this.getExecutor().executeScript("arguments[0].click();", element);
    this.waitForPageLoad();
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void clickElementAction(WebElement element) {
    this.waitUntilElementClickable(element);
    this.moveToElement(element);
    this.getAction().click(element);
    this.waitForPageLoad();
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public String getAttribute(WebElement element, String attribute) {
    this.waitUntilElementPresent(this.getLocator(element));
    this.scrollToElement(element);
    return element.getAttribute(attribute);
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void setText(WebElement element, String value) {
    this.clearText(element);
    this.scrollToElement(element);
    element.sendKeys(value);
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void clearText(WebElement element) {
    this.waitUntilElementVisible(element);
    this.scrollToElement(element);
    element.clear();
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public String getText(WebElement element) {
    this.waitUntilElementVisible(element);
    this.scrollToElement(element);
    return element.getText();
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void selectDropdown(WebElement element, String valueOrText) {
    this.waitUntilElementVisible(element);
    this.scrollToElement(element);
    Select select = new Select(element);
    try {
      select.selectByVisibleText(valueOrText);
    } catch (Exception ex) {
      select.selectByValue(valueOrText);
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>selectDropdown.
   */
  @Override
  @Loggable
  public void selectDropdown(WebElement element, Integer index) {
    this.waitUntilElementVisible(element);
    this.scrollToElement(element);
    Select select = new Select(element);
    select.selectByIndex(index);
  }

  /**
   * {@inheritDoc}
   *
   * <p>switchToWindow.
   */
  @Override
  @Loggable
  public void switchToWindow(URL url) {
    this.getWebDriverWait()
        .withMessage("switchToWindow using url failed, there is only one tab")
        .until(ExpectedConditions.not(ExpectedConditions.numberOfWindowsToBe(1)));

    String currentWindow = this.getDriver().getWindowHandle();
    Set<String> windowHandles = this.getDriver().getWindowHandles();

    for (String windowHandle : windowHandles) {
      this.getDriver().switchTo().window(windowHandle);
      String windowUrl = this.getUrl();
      if (windowUrl.equalsIgnoreCase(url.toString())) {
        return;
      }
    }

    this.getDriver().switchTo().window(currentWindow);
    throw new CoreException("Could not find window with url " + url.toString());
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void switchToWindow(String title) {
    this.getWebDriverWait()
        .withMessage("switchToWindow using title failed, there is only one tab")
        .until(ExpectedConditions.not(ExpectedConditions.numberOfWindowsToBe(1)));

    String currentWindow = this.getDriver().getWindowHandle();
    Set<String> windowHandles = this.getDriver().getWindowHandles();

    for (String windowHandle : windowHandles) {
      this.getDriver().switchTo().window(windowHandle);
      String windowTitle = this.getDriver().getTitle();
      if (windowTitle.equalsIgnoreCase(title)
          || windowTitle.toLowerCase().contains(title.toLowerCase())) {
        return;
      }
    }

    this.getDriver().switchTo().window(currentWindow);
    throw new CoreException("Could not find window with title " + title);
  }

  /**
   * {@inheritDoc}
   *
   * <p>switchToWindow.
   */
  @Override
  @Loggable
  public void switchToWindow(Integer index) {
    this.getWebDriverWait()
        .withMessage("switchToWindow using index failed, there is only one tab")
        .until(ExpectedConditions.not(ExpectedConditions.numberOfWindowsToBe(1)));

    List<String> windowHandles = this.getDriver().getWindowHandles().stream().toList();
    if (index >= windowHandles.size()) {
      throw new CoreException("Could not find window with index " + index);
    }

    this.getDriver().switchTo().window(windowHandles.get(index));
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void switchToFrame(String name) {
    if (name == null) {
      this.getDriver().switchTo().defaultContent();
    } else {
      this.getDriver().switchTo().frame(name);
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>switchToFrame.
   */
  @Override
  @Loggable
  public void switchToFrame(WebElement element) {
    this.scrollToElement(element);
    if (element == null) {
      this.getDriver().switchTo().defaultContent();
    } else {
      this.getDriver().switchTo().frame(element);
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>switchToFrame.
   */
  @Override
  @Loggable
  public void switchToFrame(Integer index) {
    if (index == null) {
      this.getDriver().switchTo().defaultContent();
    } else {
      this.getDriver().switchTo().frame(index);
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>switchToParentFrame.
   */
  @Override
  @Loggable
  public void switchToParentFrame() {
    this.getDriver().switchTo().parentFrame();
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public void switchToAlert() {
    this.getDriver().switchTo().alert();
  }

  /**
   * {@inheritDoc}
   *
   * <p>getScreenshot.
   */
  @Override
  @Loggable
  public <T> T getScreenshot(OutputType<T> type) {
    return ((TakesScreenshot) this.getDriver()).getScreenshotAs(type);
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public JsonObject getSessionDetails() {
    String sessionId = ((RemoteWebDriver) this.getDriver()).getSessionId().toString();

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("sessionId", sessionId);

    if (PageUtil.isSelenoid()) {
      jsonObject.addProperty(
          "log",
          MessageFormat.format(
              "{0}/logs/{1}.log ", BaseUtil.getEnvPropertyAsString("ui.selenoid.hub"), sessionId));
      jsonObject.addProperty(
          "video",
          MessageFormat.format(
              "{0}/video/{1}.mp4 ", BaseUtil.getEnvPropertyAsString("ui.selenoid.ui"), sessionId));
    }

    return jsonObject;
  }
}
