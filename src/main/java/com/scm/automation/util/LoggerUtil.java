package com.scm.automation.util;

import com.google.common.io.Resources;
import com.scm.automation.exception.CoreException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;

/**
 * LoggerUtil class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoggerUtil {
  private static Logger log = null;

  static {
    try {
      URL log4j2XmlUrl = Resources.getResource("log4j2.xml");
      System.setProperty("log4j2.configurationFile", log4j2XmlUrl.toURI().toString());
    } catch (URISyntaxException e) {
      throw new CoreException(e);
    }
  }

  private LoggerUtil() {}

  /**
   * getLogger.
   *
   * @param loggerName a {@link java.lang.String} object
   * @return a {@link org.apache.logging.log4j.Logger} object
   * @since 1.0.0
   */
  public static Logger getLogger(String loggerName) {
    log = LogManager.getLogger(loggerName);
    return log;
  }

  /**
   * reconfigure.
   *
   * @since 1.0.0
   */
  public static void reconfigure() {
    try {
      ((org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false)).reconfigure();
      getLogger(LoggerUtil.class.getName());
      log.debug("Logger initialized");
      log.debug(getLogFileName());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * getLogFileName.
   *
   * @return a {@link java.lang.String} object
   * @since 1.0.0
   */
  public static String getLogFileName() {
    try {
      FileAppender appender =
          (FileAppender)
              LoggerContext.getContext().getConfiguration().getAppenders().get("FileAppender");
      return appender.getFileName();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  // region private methods

  // endregion
}
