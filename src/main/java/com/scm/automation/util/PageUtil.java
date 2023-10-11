package com.scm.automation.util;

import com.scm.automation.config.PageConfig;
import com.scm.automation.enums.Browser;
import com.scm.automation.enums.Channel;
import com.scm.automation.enums.Execution;
import com.scm.automation.enums.Platform;
import lombok.Getter;

/**
 * PageUtil class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class PageUtil {
  @Getter private static final PageConfig config = PageConfig.configure();

  private PageUtil() {}

  /**
   * isDesktop.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isDesktop() {
    return getConfig().getChannel() == Channel.DESKTOP;
  }

  /**
   * isMweb.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isMweb() {
    return getConfig().getChannel() == Channel.MWEB;
  }

  /**
   * isApp.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isApp() {
    return getConfig().getChannel() == Channel.APP;
  }

  /**
   * isChrome.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isChrome() {
    return getConfig().getBrowser() == Browser.CHROME;
  }

  /**
   * isFirefox.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isFirefox() {
    return getConfig().getBrowser() == Browser.FIREFOX;
  }

  /**
   * isWindows.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isWindows() {
    return getConfig().getPlatform() == Platform.WINDOWS;
  }

  /**
   * isMac.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isMac() {
    return getConfig().getPlatform() == Platform.MAC;
  }

  /**
   * isLinux.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isLinux() {
    return getConfig().getPlatform() == Platform.LINUX;
  }

  /**
   * isAndroid.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isAndroid() {
    return getConfig().getPlatform() == Platform.ANDROID;
  }

  /**
   * isIOS.
   *
   * @return a boolean
   * @since 1.0.75
   */
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public static boolean isIOS() {
    return getConfig().getPlatform() == Platform.IOS;
  }

  /**
   * isLocal.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isLocal() {
    return getConfig().getExecution() == Execution.LOCAL;
  }

  /**
   * isSelenoid.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isSelenoid() {
    return getConfig().getExecution() == Execution.SELENOID;
  }

  /**
   * isMobilab.
   *
   * @return a boolean
   * @since 1.0.75
   */
  public static boolean isMobilab() {
    return getConfig().getExecution() == Execution.MOBILAB;
  }
}
