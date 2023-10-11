package com.scm.automation.test;

import com.epam.reportportal.annotations.Step;
import com.scm.automation.test.testng.Assert;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * StepTest class.
 *
 * @author vipin.v
 * @version 1.1.7
 * @since 1.1.7
 */
public class StepTest {
  /** Constant <code>LOGGER</code>. */
  public static final Logger LOGGER = LoggerFactory.getLogger(StepTest.class);

  @Test
  void orderProductsTest() {

    final Integer productCount = 5;
    final Double price = 3.0;
    final Double totalPrice = price * productCount;

    navigateToMainPage();
    login();
    navigateToProductsPage();
    addProductToCart(productCount);
    pay(totalPrice);
    logout();
  }

  /** navigateToMainPage. */
  @Step
  public void navigateToMainPage() {
    LOGGER.info("Main page displayed");
    Assert.assertEquals(10, 10, "navigateToMainPage");
  }

  /** login. */
  @Step
  public void login() {
    OrderingSimulator.logIn();
    LOGGER.info("User logged in");
    Assert.assertEquals(10, 10, "login");
  }

  /** navigateToProductsPage. */
  @Step
  public void navigateToProductsPage() {
    List<String> products = OrderingSimulator.getProducts();
    LOGGER.info("Products page opened");
    Assert.assertEquals(10, 10, "navigateToProductsPage");
  }

  /**
   * addProductToCart.
   *
   * @param count a {@link java.lang.Integer} object
   */
  @Step("Add {count} products to the cart")
  public void addProductToCart(Integer count) {
    String product = clickOnProduct();
    selectProductsCount(count);
    clickCartButton(product, count);
    Assert.assertEquals(10, 10, "addProductToCart");
  }

  @Step
  private String clickOnProduct() {
    LOGGER.info("Product click event");
    Assert.assertEquals(10, 10, "clickOnProduct");
    return OrderingSimulator.chooseProduct();
  }

  @Step("{method} with {count} products")
  private void selectProductsCount(Integer count) {
    LOGGER.info(count + " products selected");
    Assert.assertEquals(10, 10, "selectProductsCount");
  }

  @Step("{productCount} products added")
  private void clickCartButton(String product, Integer productCount) {
    OrderingSimulator.addProduct(product, productCount);
    LOGGER.info(productCount + " products added to the cart");
    Assert.assertEquals(5, productCount.intValue());
    Assert.assertEquals(10, 10, "clickCartButton");
  }

  /**
   * pay.
   *
   * @param totalPrice a {@link java.lang.Double} object
   */
  @Step("Payment step with price = {totalPrice}")
  public void pay(Double totalPrice) {
    OrderingSimulator.doPayment(totalPrice);
    LOGGER.info("Successful payment");
    Assert.assertEquals(10, 10, "pay");
  }

  /** logout. */
  @Step
  public void logout() {
    OrderingSimulator.logOut();
    LOGGER.info("User logged out");
    Assert.assertEquals(10, 10, "logout");
  }

  private static class OrderingSimulator {

    public static void logIn() {
      initChromeDriver();
    }

    private static void initChromeDriver() {}

    public static List<String> getProducts() {
      return new ArrayList<>();
    }

    public static String chooseProduct() {
      return "productName";
    }

    public static void addProduct(String name, Integer count) {}

    public static void doPayment(Double price) {}

    public static void logOut() {}
  }
}
