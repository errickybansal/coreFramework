package com.scm.automation.runner;

import com.scm.automation.settings.Settings;
import com.scm.automation.settings.SettingsBuilder;

/**
 * RunnableFactory class.
 *
 * @author vipin.v
 * @version 1.1.22
 * @since 1.1.22
 */
public class RunnableFactory {
  private static ThreadLocal<Runnable> runnable = new ThreadLocal<>();

  private RunnableFactory() {}

  /**
   * get.
   *
   * @return a {@link com.scm.automation.runner.Runnable} object
   */
  public static Runnable get() {
    return get(SettingsBuilder.build(), false);
  }

  /**
   * get.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param newInstance a {@link java.lang.Boolean} object
   * @return a {@link com.scm.automation.runner.Runnable} object
   */
  public static Runnable get(Settings settings, Boolean newInstance) {
    if (runnable.get() == null || newInstance) {
      runnable.remove();
      runnable.set(new Runnable(settings));
    }

    return runnable.get();
  }
}
