package com.scm.automation.demo.ui.login;

import com.scm.automation.annotation.Loggable;
import com.scm.automation.service.util.Validator;
import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.validator.AbstractPageValidator;
import com.scm.automation.util.DataUtil;
import org.testng.Assert;

/**
 * LoginPageValidator class.
 *
 * @author vipin.v
 * @version 1.0.77
 * @since 1.0.77
 */
public class LoginPageValidator extends AbstractPageValidator<LoginPageLocator> {

  /**
   * Constructor for LoginPageValidator.
   *
   * @param driver a {@link com.scm.automation.ui.driver.AppDriver} object
   */
  public LoginPageValidator(AppDriver driver) {
    super(driver);
  }

  /**
   * validateLoginFailure.
   *
   * @param validator a {@link com.scm.automation.service.util.Validator} object
   */
  @Loggable
  public void validateLoginFailure(Validator validator) {
    String errorMessage = this.getPageLocator().invalidCredentialsText().getText();
    Assert.assertNotNull(errorMessage);
    if (DataUtil.getAsString(validator.getExpected(), "errorMessage") != null) {
      Assert.assertEquals(
          errorMessage, DataUtil.getAsString(validator.getExpected(), "errorMessage"));
    }
  }
}
