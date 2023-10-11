package com.scm.automation.service;

import com.scm.automation.settings.Settings;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Abstract AbstractWorkflow class.
 *
 * @author vipin.v
 * @version 1.0.57
 * @since 1.0.57
 */
public abstract class AbstractWorkflow<T> extends AbstractBaseService<T> {
  /**
   * Constructor for AbstractWorkflow.
   *
   * @since 1.0.62
   */
  protected AbstractWorkflow() {
    super(Settings.builder().retry(false).throwOnError(true).build());
  }

  /** {@inheritDoc} */
  @Override
  public <R> R mergeRequest(R newRequest, Class<R> type) {
    throw new NotImplementedException();
  }
}
