package com.scm.automation.demo.ui.password;

import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.validator.AbstractPageValidator;

/**
 * LoginPageValidator class.
 *
 * @author vipin.v
 * @version 1.0.77
 * @since 1.0.77
 */
public class ForgotPasswordPageValidator extends AbstractPageValidator<ForgotPasswordPageLocator> {

  /**
   * Constructor for ForgotPasswordPageValidator.
   *
   * @param driver a {@link com.scm.automation.ui.driver.AppDriver} object
   */
  public ForgotPasswordPageValidator(AppDriver driver) {
    super(driver);
  }
}
