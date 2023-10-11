package com.scm.automation.test.testng;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Assert class.
 *
 * @author vipin.v
 * @version 1.1.7
 * @since 1.1.7
 */
@SuppressWarnings("checkstyle:JavadocMethod")
public class Assert extends org.testng.Assert {

  /** Constant <code>log</code>. */
  public static final Logger log = LoggerFactory.getLogger(Assert.class);

  /** Constructor for Assert. */
  protected Assert() {}

  /**
   * {@inheritDoc}
   *
   * <p>assertTrue.
   */
  public static void assertTrue(boolean condition) {
    assertTrue(condition, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertTrue.
   */
  public static void assertTrue(boolean condition, String message) {
    log.info("assertTrue - actual: {}, message: {}", condition, message);
    org.testng.Assert.assertTrue(condition, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertFalse.
   */
  public static void assertFalse(boolean condition) {
    assertFalse(condition, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertFalse.
   */
  public static void assertFalse(boolean condition, String message) {
    log.info("assertFalse - actual: {}, message: {}", condition, message);
    org.testng.Assert.assertFalse(condition, message);
  }

  /**
   * assertEquals.
   *
   * @param actual a {@link java.lang.Object} object
   * @param expected a {@link java.lang.Object} object
   */
  public static void assertEquals(Object actual, Object expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(Object actual, Object expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual an array of {@link byte} objects
   * @param expected an array of {@link byte} objects
   */
  public static void assertEquals(byte[] actual, byte[] expected) {
    assertEquals(actual, expected);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(byte[] actual, byte[] expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual an array of {@link short} objects
   * @param expected an array of {@link short} objects
   */
  public static void assertEquals(short[] actual, short[] expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(short[] actual, short[] expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual an array of {@link int} objects
   * @param expected an array of {@link int} objects
   */
  public static void assertEquals(int[] actual, int[] expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(int[] actual, int[] expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual an array of {@link boolean} objects
   * @param expected an array of {@link boolean} objects
   */
  public static void assertEquals(boolean[] actual, boolean[] expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(boolean[] actual, boolean[] expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual an array of {@link char} objects
   * @param expected an array of {@link char} objects
   */
  public static void assertEquals(char[] actual, char[] expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(char[] actual, char[] expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual an array of {@link float} objects
   * @param expected an array of {@link float} objects
   */
  public static void assertEquals(float[] actual, float[] expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(float[] actual, float[] expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual an array of {@link double} objects
   * @param expected an array of {@link double} objects
   */
  public static void assertEquals(double[] actual, double[] expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(double[] actual, double[] expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual an array of {@link long} objects
   * @param expected an array of {@link long} objects
   */
  public static void assertEquals(long[] actual, long[] expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(long[] actual, long[] expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual a double
   * @param expected a double
   */
  public static void assertEquals(double actual, double expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(double actual, double expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual a float
   * @param expected a float
   */
  public static void assertEquals(float actual, float expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(float actual, float expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual a boolean
   * @param expected a boolean
   */
  public static void assertEquals(boolean actual, boolean expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(boolean actual, boolean expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   *
   * @param actual a int
   * @param expected a int
   */
  public static void assertEquals(int actual, int expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(int actual, int expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual a {@link java.lang.String} object
   * @param expected a {@link java.lang.String} object
   */
  public static void assertEquals(String actual, String expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(String actual, String expected, String message) {
    assertEquals((Object) actual, (Object) expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual a {@link java.util.Collection} object
   * @param expected a {@link java.util.Collection} object
   */
  public static void assertEquals(Collection<?> actual, Collection<?> expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(Collection<?> actual, Collection<?> expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual a {@link java.util.Iterator} object
   * @param expected a {@link java.util.Iterator} object
   */
  public static void assertEquals(Iterator<?> actual, Iterator<?> expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(Iterator<?> actual, Iterator<?> expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual a {@link java.lang.Iterable} object
   * @param expected a {@link java.lang.Iterable} object
   */
  public static void assertEquals(Iterable<?> actual, Iterable<?> expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(Iterable<?> actual, Iterable<?> expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual an array of {@link java.lang.Object} objects
   * @param expected an array of {@link java.lang.Object} objects
   */
  public static void assertEquals(Object[] actual, Object[] expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(Object[] actual, Object[] expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * assertEquals.
   *
   * @param actual a {@link java.util.Set} object
   * @param expected a {@link java.util.Set} object
   */
  public static void assertEquals(Set<?> actual, Set<?> expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(Set<?> actual, Set<?> expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   *
   * @param actual a {@link java.util.Map} object
   * @param expected a {@link java.util.Map} object
   */
  public static void assertEquals(Map<?, ?> actual, Map<?, ?> expected) {
    assertEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEquals.
   */
  public static void assertEquals(Map<?, ?> actual, Map<?, ?> expected, String message) {
    log.info("assertEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEquals.
   *
   * @param actual a {@link java.lang.Object} object
   * @param expected a {@link java.lang.Object} object
   */
  public static void assertNotEquals(Object actual, Object expected) {
    assertNotEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEquals.
   */
  public static void assertNotEquals(Object actual, Object expected, String message) {
    log.info("assertNotEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertNotEquals(actual, expected, message);
  }

  /**
   * assertNotEquals.
   *
   * @param actual an array of {@link java.lang.Object} objects
   * @param expected an array of {@link java.lang.Object} objects
   */
  public static void assertNotEquals(Object[] actual, Object[] expected) {
    assertNotEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEquals.
   */
  public static void assertNotEquals(Object[] actual, Object[] expected, String message) {
    log.info("assertNotEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertNotEquals(actual, expected, message);
  }

  /**
   * assertNotEquals.
   *
   * @param actual a {@link java.util.Iterator} object
   * @param expected a {@link java.util.Iterator} object
   */
  public static void assertNotEquals(Iterator<?> actual, Iterator<?> expected) {
    assertNotEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEquals.
   */
  public static void assertNotEquals(Iterator<?> actual, Iterator<?> expected, String message) {
    log.info("assertNotEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertNotEquals(actual, expected, message);
  }

  /**
   * assertNotEquals.
   *
   * @param actual a {@link java.util.Collection} object
   * @param expected a {@link java.util.Collection} object
   */
  public static void assertNotEquals(Collection<?> actual, Collection<?> expected) {
    assertNotEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEquals.
   */
  public static void assertNotEquals(Collection<?> actual, Collection<?> expected, String message) {
    log.info("assertNotEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertNotEquals(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEquals.
   *
   * @param actual a {@link java.util.Set} object
   * @param expected a {@link java.util.Set} object
   */
  public static void assertNotEquals(Set<?> actual, Set<?> expected) {
    assertNotEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEquals.
   */
  public static void assertNotEquals(Set<?> actual, Set<?> expected, String message) {
    log.info("assertNotEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertNotEquals(actual, expected, message);
  }

  /**
   * assertNotEquals.
   *
   * @param actual a {@link java.util.Map} object
   * @param expected a {@link java.util.Map} object
   */
  public static void assertNotEquals(Map<?, ?> actual, Map<?, ?> expected) {
    assertNotEquals(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEquals.
   */
  public static void assertNotEquals(Map<?, ?> actual, Map<?, ?> expected, String message) {
    log.info("assertNotEquals - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertNotEquals(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotNull.
   */
  public static void assertNotNull(Object object) {
    assertNotNull(object, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotNull.
   */
  public static void assertNotNull(Object object, String message) {
    log.info("assertNotNull - actual: {}, message: {}", object, message);
    org.testng.Assert.assertNotNull(object, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNull.
   */
  public static void assertNull(Object object) {
    assertNull(object, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNull.
   */
  public static void assertNull(Object object, String message) {
    log.info("assertNull - actual: {}, message: {}", object, message);
    org.testng.Assert.assertNull(object, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertSame.
   */
  public static void assertSame(Object actual, Object expected) {
    assertSame(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertSame.
   */
  public static void assertSame(Object actual, Object expected, String message) {
    log.info("assertSame - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertSame(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotSame.
   */
  public static void assertNotSame(Object actual, Object expected) {
    assertNotSame(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotSame.
   */
  public static void assertNotSame(Object actual, Object expected, String message) {
    log.info("assertNotSame - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertNotSame(actual, expected, message);
  }

  /**
   * assertEqualsNoOrder.
   *
   * @param actual an array of {@link java.lang.Object} objects
   * @param expected an array of {@link java.lang.Object} objects
   */
  public static void assertEqualsNoOrder(Object[] actual, Object[] expected) {
    assertEqualsNoOrder(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEqualsNoOrder.
   */
  public static void assertEqualsNoOrder(Object[] actual, Object[] expected, String message) {
    log.info(
        "assertEqualsNoOrder - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEqualsNoOrder(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEqualsNoOrder.
   *
   * @param actual a {@link java.util.Collection} object
   * @param expected a {@link java.util.Collection} object
   */
  public static void assertEqualsNoOrder(Collection<?> actual, Collection<?> expected) {
    assertEqualsNoOrder(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEqualsNoOrder.
   */
  public static void assertEqualsNoOrder(
      Collection<?> actual, Collection<?> expected, String message) {
    log.info(
        "assertEqualsNoOrder - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEqualsNoOrder(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEqualsNoOrder.
   */
  public static void assertEqualsNoOrder(Iterator<?> actual, Iterator<?> expected) {
    assertEqualsNoOrder(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEqualsNoOrder.
   */
  public static void assertEqualsNoOrder(Iterator<?> actual, Iterator<?> expected, String message) {
    log.info(
        "assertEqualsNoOrder - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEqualsNoOrder(actual, expected, message);
  }

  /**
   * assertEqualsDeep.
   *
   * @param actual a {@link java.util.Set} object
   * @param expected a {@link java.util.Set} object
   */
  public static void assertEqualsDeep(Set<?> actual, Set<?> expected) {
    assertEqualsDeep(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEqualsDeep.
   */
  public static void assertEqualsDeep(Set<?> actual, Set<?> expected, String message) {
    log.info("assertEqualsDeep - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEqualsDeep(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEqualsDeep.
   */
  public static void assertEqualsDeep(Map<?, ?> actual, Map<?, ?> expected) {
    assertEqualsDeep(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertEqualsDeep.
   */
  public static void assertEqualsDeep(Map<?, ?> actual, Map<?, ?> expected, String message) {
    log.info("assertEqualsDeep - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertEquals(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEqualsDeep.
   *
   * @param actual a {@link java.util.Set} object
   * @param expected a {@link java.util.Set} object
   */
  public static void assertNotEqualsDeep(Set<?> actual, Set<?> expected) {
    assertNotEqualsDeep(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEqualsDeep.
   */
  public static void assertNotEqualsDeep(Set<?> actual, Set<?> expected, String message) {
    log.info(
        "assertNotEqualsDeep - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertNotEqualsDeep(actual, expected, message);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEqualsDeep.
   */
  public static void assertNotEqualsDeep(Map<?, ?> actual, Map<?, ?> expected) {
    assertNotEqualsDeep(actual, expected, null);
  }

  /**
   * {@inheritDoc}
   *
   * <p>assertNotEqualsDeep.
   */
  public static void assertNotEqualsDeep(Map<?, ?> actual, Map<?, ?> expected, String message) {
    log.info(
        "assertNotEqualsDeep - actual: {}, expected: {}, message: {}", actual, expected, message);
    org.testng.Assert.assertNotEqualsDeep(actual, expected, message);
  }
}
