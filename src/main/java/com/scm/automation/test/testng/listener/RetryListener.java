package com.scm.automation.test.testng.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;
import org.apache.commons.lang3.math.NumberUtils;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

/**
 * RetryListener class.
 *
 * @author vipin.v
 * @version 1.1.1
 * @since 1.1.1
 */
public class RetryListener implements IAnnotationTransformer {
  /** {@inheritDoc} */
  @Override
  public void transform(
      ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
    annotation.setRetryAnalyzer(RetryAnalyzer.class);
  }

  /** RetryAnalyzer class. */
  public static class RetryAnalyzer implements IRetryAnalyzer {
    private static final String RETRY_COUNT_PROPERTY = "surefire.rerunFailingTestsCount";
    private static final int DEFAULT_RETRY_COUNT = 0;
    private static final int MAX_RETRY_COUNT =
        System.getProperty(RETRY_COUNT_PROPERTY) != null
                && NumberUtils.isParsable(System.getProperty(RETRY_COUNT_PROPERTY))
            ? Integer.parseInt(System.getProperty(RETRY_COUNT_PROPERTY))
            : DEFAULT_RETRY_COUNT;
    private final AtomicInteger retryCount = new AtomicInteger(0);

    /** {@inheritDoc} */
    @SneakyThrows
    @Override
    public boolean retry(ITestResult result) {
      TimeUnit.MILLISECONDS.sleep(500);
      return retryCount.incrementAndGet() <= MAX_RETRY_COUNT;
    }
  }
}
