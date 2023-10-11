package com.scm.automation.test.testng;

import com.github.underscore.Underscore;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.scm.automation.config.BaseConfig;
import com.scm.automation.config.PageConfig;
import com.scm.automation.enums.DataType;
import com.scm.automation.test.model.TestData;
import com.scm.automation.ui.driver.AppDriverFactory;
import com.scm.automation.util.DataUtil;
import com.scm.automation.util.LoggerUtil;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.testng.ITest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

/**
 * Abstract TestCase class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class TestCase implements ITest {
  private final InheritableThreadLocal<String> testDataResourcePath =
      new InheritableThreadLocal<>();
  private final InheritableThreadLocal<JsonObject> suiteTestData = new InheritableThreadLocal<>();
  private final InheritableThreadLocal<String> testcaseName = new InheritableThreadLocal<>();

  /**
   * Setter for the field <code>testDataResourcePath</code>.
   *
   * @param testDataResourcePath a {@link java.lang.String} object
   */
  public void setTestDataResourcePath(String testDataResourcePath) {
    this.testDataResourcePath.set(testDataResourcePath);
  }

  /**
   * Getter for the field <code>suiteTestData</code>.
   *
   * @return a {@link com.google.gson.JsonObject} object
   */
  public JsonObject getSuiteTestData() {
    if (this.suiteTestData.get() == null) {
      this.suiteTestData.set(DataUtil.read(this.testDataResourcePath.get(), DataType.YAML));
    }
    return this.suiteTestData.get();
  }

  /**
   * {@inheritDoc}
   *
   * <p>The name of test instance(s).
   */
  @Override
  public String getTestName() {
    return this.testcaseName.get();
  }

  /**
   * testcaseBeforeSuite.
   *
   * @since 1.0.0
   */
  @BeforeSuite
  public void testcaseBeforeSuite() {
    BaseConfig.configure();
    PageConfig.configure();
  }

  /**
   * testcaseAfterSuite.
   *
   * @since 1.0.0
   */
  @AfterSuite
  public void testcaseAfterSuite() {
    AppDriverFactory.quit();
    this.suiteTestData.remove();
    this.testDataResourcePath.remove();
  }

  /**
   * testcaseBeforeClass.
   *
   * @since 1.0.0
   */
  @BeforeClass
  public void testcaseBeforeClass() {
    if (this.testDataResourcePath.get() == null) {
      throw new InvalidParameterException("testDataResourcePath must not be null");
    }
    this.suiteTestData.set(DataUtil.read(this.testDataResourcePath.get(), DataType.YAML));
  }

  /** testcaseAfterClass. */
  @AfterClass
  public void testcaseAfterClass() {
    AppDriverFactory.quit();
    this.suiteTestData.remove();
  }

  /**
   * beforeMethod.
   *
   * @param method a {@link java.lang.reflect.Method} object
   * @param testDataObject an array of {@link java.lang.Object} objects
   * @since 1.0.0
   */
  @BeforeMethod
  public void testcaseBeforeMethod(Method method, Object[] testDataObject) {
    LoggerUtil.reconfigure();

    if (testDataObject != null
        && testDataObject.length != 0
        && testDataObject[0] instanceof TestData singleTestData) {
      this.testcaseName.set(
          singleTestData.getTestcaseId().replaceAll("\\s+", "_")
              + "__"
              + singleTestData.getTestcaseName().replaceAll("\\s+", "_")
              + "__"
              + method.getName());
    }
  }

  /**
   * testcaseAfterMethod.
   *
   * @since 1.0.0
   */
  @AfterMethod
  public void testcaseAfterMethod() {
    this.testcaseName.remove();
  }

  /**
   * dataProvider.
   *
   * @param method a {@link java.lang.reflect.Method} object
   * @return a {@link java.util.Iterator} object
   * @since 1.0.0
   */
  @SuppressWarnings("DataProviderReturnType")
  @DataProvider(name = "dataProvider")
  public Iterator<TestData> dataProvider(Method method) {
    if (this.getSuiteTestData().has(method.getName())
        && this.getSuiteTestData().get(method.getName()).isJsonArray()) {
      Type testDataType = new TypeToken<List<TestData>>() {}.getType();
      List<TestData> testDataList =
          DataUtil.getGson()
              .fromJson(this.getSuiteTestData().getAsJsonArray(method.getName()), testDataType);

      String testcaseId = System.getProperty("testcaseId");
      if (testcaseId == null) {
        return testDataList.iterator();
      }

      List<TestData> filteredTestDataList =
          Underscore.filter(
              testDataList,
              singleTestData -> singleTestData.getTestcaseId().equalsIgnoreCase(testcaseId));
      return filteredTestDataList.iterator();
    }
    return Collections.emptyIterator();
  }
}
