package com.scm.automation.ui.page;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.scm.automation.exception.CoreException;
import com.scm.automation.service.AbstractBaseService;
import com.scm.automation.settings.Settings;
import com.scm.automation.ui.driver.AppDriver;
import com.scm.automation.ui.locator.AbstractPageLocator;
import com.scm.automation.ui.page.model.Request;
import com.scm.automation.ui.page.model.Response;
import com.scm.automation.ui.validator.AbstractPageValidator;
import com.scm.automation.util.DataUtil;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;

/**
 * Abstract AbstractPage class.
 *
 * @author vipin.v
 * @version 1.0.75
 * @since 1.0.75
 */
public abstract class AbstractPage<
        T extends AbstractPage, L extends AbstractPageLocator, V extends AbstractPageValidator>
    extends AbstractBaseService<T> implements Page<T, L, V> {
  private final @Getter AppDriver appDriver;
  private final @Getter L pageLocator;
  private final @Getter V pageValidator;
  @Getter private Properties updatedProperties;
  @Getter private final Properties properties;

  /**
   * Constructor for AbstractPage.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param appDriver a {@link com.scm.automation.ui.driver.AppDriver} object
   * @param properties a {@link com.scm.automation.ui.page.Properties} object
   * @since 1.0.80
   */
  @SuppressWarnings("unchecked")
  protected AbstractPage(Settings settings, AppDriver appDriver, Properties properties) {

    super(settings);
    this.appDriver = appDriver;
    this.properties = properties;

    try {
      Class<L> pageLocatorClass =
          (Class<L>)
              ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
      this.pageLocator =
          pageLocatorClass.getDeclaredConstructor(AppDriver.class).newInstance(this.getAppDriver());

      Class<V> pageValidatorClass =
          (Class<V>)
              ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
      this.pageValidator =
          pageValidatorClass
              .getDeclaredConstructor(AppDriver.class)
              .newInstance(this.getAppDriver());
    } catch (Exception ex) {
      throw new CoreException();
    }
  }

  /**
   * Constructor for AbstractPage.
   *
   * @param settings a {@link com.scm.automation.settings.Settings} object
   * @param appDriver a {@link com.scm.automation.ui.driver.AppDriver} object
   * @param properties a {@link com.scm.automation.ui.page.Properties} object
   * @param pageLocator a O object
   * @param pageValidator a K object
   * @param <O> a O class
   * @param <K> a K class
   */
  @SuppressWarnings("unchecked")
  protected <O extends AbstractPageLocator, K extends AbstractPageValidator> AbstractPage(
      Settings settings,
      AppDriver appDriver,
      Properties properties,
      O pageLocator,
      K pageValidator) {

    super(settings);
    this.appDriver = appDriver;
    this.properties = properties;
    this.pageLocator = (L) pageLocator;
    this.pageValidator = (V) pageValidator;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public T withProperties(Properties properties) {
    JsonObject source = DataUtil.toJsonObject(properties);
    JsonObject target = DataUtil.toJsonObject(this.properties);
    JsonObject mergedProperties = DataUtil.deepMerge(source, target);
    this.updatedProperties = DataUtil.getGson().fromJson(mergedProperties, Properties.class);
    return (T) this;
  }

  /** {@inheritDoc} */
  @Override
  public Properties getProperties() {
    return this.getUpdatedProperties() != null ? this.getUpdatedProperties() : this.properties;
  }

  /** {@inheritDoc} */
  @Override
  public void resetProperties() {
    if (this.getUpdatedProperties() != null) {
      this.updatedProperties = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  public <R> R mergeRequest(R newRequest, Class<R> type) {
    throw new NotImplementedException();
  }

  /** {@inheritDoc} */
  @Override
  public WebDriver getDriver() {
    return getAppDriver().getDriver();
  }

  /** {@inheritDoc} */
  @Override
  public void navigateToUrl() {
    URL url;
    try {
      url = new URL(this.getProperties().getPath());
    } catch (MalformedURLException ex) {
      try {
        url = new URL(this.getProperties().getBaseUrl() + this.getProperties().getPath());
      } catch (MalformedURLException urlException) {
        throw new CoreException(urlException);
      }
    }

    if (!this.getAppDriver().getUrl().contains(url.toString())) {
      this.getAppDriver().navigateToUrl(url.toString());
    }
  }

  /**
   * {@inheritDoc}
   *
   * <p>refresh.
   */
  @Override
  public void refresh() {
    this.getAppDriver().refresh();
  }

  /** {@inheritDoc} */
  @Override
  public Response createResponse(Object[] args, Object result) {
    JsonObject data = new JsonObject();
    if (result == null) {
      return Response.builder().request(Request.builder().args(args).build()).result(null).build();
    }

    try {
      JsonElement resultElement;
      if (result instanceof String stringResult) {
        resultElement = JsonParser.parseString(stringResult);
      } else {
        resultElement = DataUtil.getGson().toJsonTree(result);
      }

      if (resultElement.isJsonArray()) {
        data.add("data", resultElement.getAsJsonArray());
      } else if (resultElement.isJsonObject()) {
        data.add("data", resultElement.getAsJsonObject());
      } else if (resultElement.isJsonPrimitive()) {
        data.addProperty("data", result.toString());
      }
    } catch (JsonSyntaxException ex) {
      data.addProperty("data", result.toString());
    }

    return Response.builder().request(Request.builder().args(args).build()).result(data).build();
  }
}
