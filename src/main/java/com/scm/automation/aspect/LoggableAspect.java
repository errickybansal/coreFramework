package com.scm.automation.aspect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scm.automation.util.DataUtil;
import com.scm.automation.util.LoggerUtil;
import groovy.json.StringEscapeUtils;
import java.text.MessageFormat;
import java.util.Arrays;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * LoggableAspect class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Aspect
public class LoggableAspect {

  private Logger log;
  private static final Gson gson =
      new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

  /**
   * beforeMethodAop.
   *
   * @param joinPoint a {@link org.aspectj.lang.JoinPoint} object
   * @since 1.0.0
   */
  @Before("@annotation(com.scm.automation.annotation.Loggable) && execution(* *(..))")
  public void beforeMethodAop(JoinPoint joinPoint) {
    log = LoggerUtil.getLogger(getMethodSignature(joinPoint));

    if (joinPoint.getArgs().length != 0) {
      String message =
          MessageFormat.format("args: {0}", formatMethodArguments(joinPoint.getArgs()));
      log.trace(message);
    }
  }

  /**
   * afterMethodAop.
   *
   * @param result a {@link java.lang.Object} object
   * @since 1.0.0
   */
  @AfterReturning(
      pointcut = "@annotation(com.scm.automation.annotation.Loggable) && execution(* *(..))",
      returning = "result")
  public void afterMethodAop(Object result) {

    if (result != null) {
      String message = MessageFormat.format("result: {0}", formatMethodReturnType(result));
      log.trace(message);
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
      pointcut = "@annotation(com.scm.automation.annotation.Loggable) && execution(* *(..))",
      throwing = "ex")
  public void afterThrowAop(JoinPoint joinPoint, Throwable ex) {
    log.debug(ExceptionUtils.getMessage(ex));
  }

  /**
   * getMethodSignature.
   *
   * @param joinPoint a {@link org.aspectj.lang.JoinPoint} object
   * @return a {@link java.lang.String} object
   * @since 1.0.0
   */
  public static String getMethodSignature(JoinPoint joinPoint) {
    return joinPoint.getSignature().getDeclaringTypeName()
        + "."
        + joinPoint.getSignature().getName()
        + "()";
  }

  /**
   * formatMethodArguments.
   *
   * @param args an array of {@link java.lang.Object} objects
   * @return a {@link java.lang.String} object
   * @since 1.0.0
   */
  public static String formatMethodArguments(Object[] args) {
    try {
      return StringEscapeUtils.unescapeJava(DataUtil.jsonToYaml(gson.toJson(args)));
    } catch (Exception ex) {
      return Arrays.toString(args);
    }
  }

  /**
   * formatMethodReturnType.
   *
   * @param result a {@link java.lang.Object} object
   * @return a {@link java.lang.String} object
   * @since 1.0.0
   */
  public static String formatMethodReturnType(Object result) {
    try {
      return StringEscapeUtils.unescapeJava(DataUtil.jsonToYaml(gson.toJson(result)));
    } catch (Exception ex) {
      return MessageFormat.format("{0}", result);
    }
  }
}
