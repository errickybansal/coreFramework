package com.scm.automation.config;

import com.scm.automation.annotation.Loggable;
import com.scm.automation.enums.Browser;
import com.scm.automation.enums.Channel;
import com.scm.automation.enums.Execution;
import com.scm.automation.enums.Platform;
import lombok.Getter;

/**
 * PageConfig class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public class PageConfig {
  @Getter private final Channel channel;
  @Getter private final Browser browser;
  @Getter private final Platform platform;
  @Getter private final Execution execution;
  @Getter private final String server;

  private static PageConfig config = null;

  private PageConfig(
      Channel channel, Browser browser, Platform platform, Execution execution, String server) {
    this.channel = channel;
    this.browser = browser;
    this.platform = platform;
    this.execution = execution;
    this.server = server;
  }

  /**
   * configure.
   *
   * @return a {@link com.scm.automation.config.PageConfig} object
   * @since 1.0.0
   */
  public static PageConfig configure() {
    if (config == null) {
      return reconfigure();
    }
    return config;
  }

  /**
   * reconfigure.
   *
   * @return a {@link com.scm.automation.config.PageConfig} object
   * @since 1.0.0
   */
  @Loggable
  public static PageConfig reconfigure() {
    Channel channel =
        System.getProperty("channel") != null
            ? Channel.valueOf(System.getProperty("channel").toUpperCase())
            : Channel.DESKTOP;

    Browser browser =
        System.getProperty("browser") != null
            ? Browser.valueOf(System.getProperty("browser").toUpperCase())
            : Browser.CHROME;

    Platform platform =
        System.getProperty("platform") != null
            ? Platform.valueOf(System.getProperty("platform").toUpperCase())
            : Platform.LINUX;

    Execution execution =
        System.getProperty("execution") != null
            ? Execution.valueOf(System.getProperty("execution").toUpperCase())
            : Execution.LOCAL;

    String server = System.getProperty("server");

    config = new PageConfig(channel, browser, platform, execution, server);
    return config;
  }
}
