package com.scm.automation.ui.validator;

import com.scm.automation.exception.CoreException;
import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.locator.AbstractPageLocator;
import java.lang.reflect.ParameterizedType;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

/**
 * Abstract AbstractPageValidator class.
 *
 * @author vipin.v
 * @version 1.0.79
 * @since 1.0.79
 */
public abstract class AbstractPageValidator<L extends AbstractPageLocator> {
  private final @Getter AppDriver appDriver;
  private final @Getter WebDriver driver;
  private final @Getter L pageLocator;

  /**
   * Constructor for AbstractPageValidator.
   *
   * @param driver a {@link com.scm.automation.ui.driver.AppDriver} object
   * @since 1.0.79
   */
  @SuppressWarnings("unchecked")
  protected AbstractPageValidator(AppDriver driver) {
    this.appDriver = driver;
    this.driver = driver.getDriver();

    try {
      Class<L> pageLocatorClass =
          (Class<L>)
              ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      this.pageLocator =
          pageLocatorClass.getDeclaredConstructor(AppDriver.class).newInstance(this.appDriver);
    } catch (Exception ex) {
      throw new CoreException();
    }
  }
}
