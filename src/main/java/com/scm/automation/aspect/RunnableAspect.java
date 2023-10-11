package com.scm.automation.aspect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.scm.automation.exception.CoreException;
import com.scm.automation.runner.Runnable;
import com.scm.automation.service.AbstractBaseService;
import com.scm.automation.service.BaseService;
import com.scm.automation.service.util.Validator;
import com.scm.automation.settings.Settings;
import com.scm.automation.ui.page.Page;
import com.scm.automation.ui.page.Properties;
import com.scm.automation.ui.page.model.Request;
import com.scm.automation.ui.page.model.Response;
import com.scm.automation.util.BaseUtil;
import com.scm.automation.util.DataUtil;
import com.scm.automation.util.LoggerUtil;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.OutputType;

/**
 * RunnableAspect class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Aspect
public class RunnableAspect {

  private Logger log;
  private static final Gson gson =
      new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

  /**
   * beforeMethodAop.
   *
   * @param joinPoint a {@link org.aspectj.lang.JoinPoint} object
   * @since 1.0.0
   */
  @Before("@annotation(com.scm.automation.annotation.Runnable) && execution(* *(..))")
  public void beforeMethodAop(JoinPoint joinPoint) {
    log = LoggerUtil.getLogger(LoggableAspect.getMethodSignature(joinPoint));

    if (joinPoint.getArgs().length != 0) {
      String message =
          MessageFormat.format(
              "args: {0}", LoggableAspect.formatMethodArguments(joinPoint.getArgs()));
      log.debug(message);
    }
  }

  /**
   * aroundMethodAop.
   *
   * @param proceedingJoinPoint a {@link org.aspectj.lang.ProceedingJoinPoint} object
   * @return a {@link java.lang.Object} object
   * @throws java.lang.Throwable if any.
   * @since 1.0.0
   */
  @Around("@annotation(com.scm.automation.annotation.Runnable) && execution(* *(..))")
  public Object aroundMethodAop(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    StopWatch timer = StopWatch.create();
    timer.start();

    Settings settings = getSettings(log, proceedingJoinPoint);
    Consumer<Validator> validator = getValidator(proceedingJoinPoint);
    JsonObject expectedResult = getExpectedResult(proceedingJoinPoint);
    AbstractBaseService.ErrorResponseBody errorResponseBody =
        getErrorResponseBody(proceedingJoinPoint);
    Properties properties = getProperties(log, proceedingJoinPoint);
    reset(proceedingJoinPoint);

    Object object = null;
    int retryCount = 0;
    boolean isRetry = false;
    Throwable exception;
    do {
      try {
        wait(log, settings, isRetry, retryCount);
        navigateToPage(proceedingJoinPoint, settings);
        object = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        runValidation(settings, validator, expectedResult, object, proceedingJoinPoint);
        exception = null;
      } catch (Throwable error) {
        log.warn(ExceptionUtils.getMessage(error));
        exception = error;
        isRetry = true;
      }
      retryCount++;
    } while (settings != null
        && settings.isRetry()
        && retryCount <= settings.getMaxRetries()
        && exception != null);

    resetPageProperties(proceedingJoinPoint);

    timer.stop();
    log.trace("Execution time - {}", timer);
    if (exception != null && (settings == null || settings.isThrowOnError())) {
      if (errorResponseBody != null) {
        return BaseUtil.createErrorResponse(
            errorResponseBody.statusCode(), errorResponseBody.description(), exception);
      }
      throw exception;
    }

    return object;
  }

  /**
   * afterMethodAop.
   *
   * @param result a {@link java.lang.Object} object
   * @since 1.0.0
   */
  @AfterReturning(
      pointcut = "@annotation(com.scm.automation.annotation.Runnable) && execution(* *(..))",
      returning = "result")
  public void afterMethodAop(Object result) {
    if (result != null) {
      String message =
          MessageFormat.format("result: {0}", LoggableAspect.formatMethodReturnType(result));
      log.debug(message);
    }
  }

  /**
   * afterThrowAop.
   *
   * @param joinPoint a {@link org.aspectj.lang.JoinPoint} object
   * @param ex a {@link java.lang.Throwable} object
   * @since 1.0.0
   */
  @AfterThrowing(
      pointcut = "@annotation(com.scm.automation.annotation.Runnable) && execution(* *(..))",
      throwing = "ex")
  public void afterThrowAop(JoinPoint joinPoint, Throwable ex) {
    if (joinPoint.getThis() instanceof Page<?, ?, ?> page && page.getAppDriver() != null) {

      JsonObject sessionDetails = page.getAppDriver().getSessionDetails();
      sessionDetails.addProperty("operation", LoggableAspect.getMethodSignature(joinPoint));
      sessionDetails.addProperty("errorMessage", ex.getMessage());

      log.error(
          "RP_MESSAGE#BASE64#{}#{}",
          page.getAppDriver().getScreenshot(OutputType.BASE64),
          gson.toJson(sessionDetails));
    }
  }

  // region private methods

  private static void runValidation(
      Settings settings,
      Consumer<Validator> validator,
      JsonObject expectedResult,
      Object result,
      ProceedingJoinPoint proceedingJoinPoint) {
    result = buildResultObject(result, proceedingJoinPoint);
    if (settings != null && settings.isEnableValidator() && validator != null) {
      JsonObject actualResult = result == null ? new JsonObject() : DataUtil.toJsonObject(result);
      Validator input = Validator.builder().actual(actualResult).expected(expectedResult).build();
      Objects.requireNonNull(validator).accept(input);
    }
  }

  private static Settings getSettings(Logger log, ProceedingJoinPoint proceedingJoinPoint) {
    if (proceedingJoinPoint.getThis() instanceof BaseService<?> service) {
      Settings settings = getUpdatedSettings(proceedingJoinPoint);
      if (settings != null) {
        setPageDriverTimeout(proceedingJoinPoint, settings.getTimeout());
        String message =
            MessageFormat.format(
                "Settings overridden: {0}", LoggableAspect.formatMethodReturnType(settings));
        log.trace(message);
        return settings;
      }
      return getDefaultSettings(proceedingJoinPoint);
    }
    return null;
  }

  private static Settings getDefaultSettings(ProceedingJoinPoint proceedingJoinPoint) {
    if (proceedingJoinPoint.getThis() instanceof BaseService<?> service) {
      return service.getSettings();
    }
    return null;
  }

  private static Settings getUpdatedSettings(ProceedingJoinPoint proceedingJoinPoint) {
    if (proceedingJoinPoint.getThis() instanceof BaseService<?> service) {
      return service.getUpdatedSettings();
    }
    return null;
  }

  private static Consumer<Validator> getValidator(ProceedingJoinPoint proceedingJoinPoint) {
    if (proceedingJoinPoint.getThis() instanceof BaseService<?> service) {
      return service.getValidator();
    }
    return null;
  }

  private static JsonObject getExpectedResult(ProceedingJoinPoint proceedingJoinPoint) {
    if (proceedingJoinPoint.getThis() instanceof BaseService<?> service) {
      return service.getExpectedResult();
    }
    return null;
  }

  private static AbstractBaseService.ErrorResponseBody getErrorResponseBody(
      ProceedingJoinPoint proceedingJoinPoint) {
    if (proceedingJoinPoint.getThis() instanceof BaseService<?> service) {
      return service.getErrorResponseBody();
    }
    return null;
  }

  private static void reset(ProceedingJoinPoint proceedingJoinPoint) {
    if (proceedingJoinPoint.getThis() instanceof BaseService<?> service) {
      service.reset();
    }
  }

  private static void wait(Logger log, Settings settings, boolean isRetry, int retryCount) {
    waitIfInitialDelay(log, settings, isRetry);
    waitIfRetry(log, settings, isRetry, retryCount);
  }

  private static void waitIfRetry(Logger log, Settings settings, boolean isRetry, int retryCount) {
    if (settings != null && isRetry) {
      log.trace("Sleeping for {} ms", settings.getRetryDelay());
      try {
        TimeUnit.MILLISECONDS.sleep(settings.getRetryDelay());
      } catch (InterruptedException e) {
        throw new CoreException(e);
      }

      log.warn("Retry - {}", retryCount);
    }
  }

  private static void waitIfInitialDelay(Logger log, Settings settings, boolean isRetry) {
    if (settings != null && !isRetry && settings.getInitialDelay() > 0) {
      log.trace("Sleeping for {} ms initial delay", settings.getInitialDelay());
      try {
        TimeUnit.MILLISECONDS.sleep(settings.getInitialDelay());
      } catch (InterruptedException e) {
        throw new CoreException(e);
      }
    }
  }

  // endregion

  // region private page methods

  private static Properties getProperties(Logger log, ProceedingJoinPoint proceedingJoinPoint) {
    if (proceedingJoinPoint.getThis() instanceof Page<?, ?, ?> page
        && page.getAppDriver() != null) {
      Properties properties = page.getUpdatedProperties();
      if (properties != null) {
        String message =
            MessageFormat.format(
                "Page properties overridden: {0}",
                LoggableAspect.formatMethodReturnType(properties));
        log.trace(message);
        return properties;
      }
      return page.getProperties();
    }
    return null;
  }

  private static void setPageDriverTimeout(
      ProceedingJoinPoint proceedingJoinPoint, Settings.Timeout timeout) {
    if (proceedingJoinPoint.getThis() instanceof Page<?, ?, ?> page
        && page.getAppDriver() != null
        && page.getAppDriver().getDefaultTimeout() != null
        && !page.getAppDriver().getDefaultTimeout().equals(timeout)) {
      page.getAppDriver().applyTimeout(timeout);
    }
  }

  private static void resetPageProperties(ProceedingJoinPoint proceedingJoinPoint) {
    if (proceedingJoinPoint.getThis() instanceof Page<?, ?, ?> page
        && page.getAppDriver() != null) {
      page.resetProperties();
    }
  }

  private static void navigateToPage(ProceedingJoinPoint proceedingJoinPoint, Settings settings) {
    if (proceedingJoinPoint.getThis() instanceof Page<?, ?, ?> page
        && page.getAppDriver() != null
        && settings.isNavigateToPage()) {
      if (settings.isEnableUrlNavigation()) {
        page.navigateToUrl();
      } else {
        page.navigate();
      }
    }
  }

  private static Object buildResultObject(Object result, ProceedingJoinPoint proceedingJoinPoint) {
    if (proceedingJoinPoint.getThis() instanceof Page<?, ?, ?> page) {
      if (result instanceof Response uiResponse) {
        uiResponse.setRequest(Request.builder().args(proceedingJoinPoint.getArgs()).build());
        return uiResponse;
      } else {
        return page.createResponse(proceedingJoinPoint.getArgs(), result);
      }
    } else if (proceedingJoinPoint.getThis() instanceof Runnable runnable) {
      return runnable.createResponse(result);
    }
    return result;
  }

  // endregion
}
