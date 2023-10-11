package com.scm.automation.service.rest;

import com.google.gson.JsonElement;
import com.scm.automation.annotation.Loggable;
import com.scm.automation.exception.CoreException;
import com.scm.automation.service.rest.model.Request;
import com.scm.automation.service.rest.model.Response;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

/**
 * RestAssuredClient class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public class RestAssuredClient implements RestClient {

  static {
    EncoderConfig encoderConfig =
        RestAssured.config()
            .getEncoderConfig()
            .appendDefaultContentCharsetToContentTypeIfUndefined(false);
    RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);
  }

  /** {@inheritDoc} */
  @Override
  public Response resolveRequest(Request request) {
    RequestSpecification requestSpecification = buildRequest(request);

    io.restassured.response.Response response;
    switch (request.getMethod()) {
      case GET -> response = RestAssured.given().spec(requestSpecification).get();
      case POST -> response = RestAssured.given().spec(requestSpecification).post();
      case PUT -> response = RestAssured.given().spec(requestSpecification).put();
      case PATCH -> response = RestAssured.given().spec(requestSpecification).patch();
      case DELETE -> response = RestAssured.given().spec(requestSpecification).delete();
      default -> response = new RestAssuredResponseImpl();
    }

    return buildResponse(request, response);
  }

  // region private methods

  @Loggable
  private static RequestSpecification buildRequest(Request request) {
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    requestSpecBuilder.setBaseUri(request.getBaseUri());
    requestSpecBuilder.setBasePath(request.getBasePath());

    if (!request.isVerifySsl()) {
      requestSpecBuilder.setRelaxedHTTPSValidation();
    }

    if (request.getHeaders() != null) {
      requestSpecBuilder.addHeaders(request.getHeaders());
    }

    if (request.getAccept() != null) {
      requestSpecBuilder.setAccept(request.getAccept());
    }

    if (request.getBody() != null) {
      requestSpecBuilder.setBody(request.getBody());
    } else if (request.getMultiPart() != null) {
      return buildMultiPartRequest(requestSpecBuilder, request);
    }

    try {
      String url = request.getBaseUri() + request.getBasePath();
      List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), StandardCharsets.UTF_8);
      for (NameValuePair param : params) {
        requestSpecBuilder.addQueryParam(param.getName(), param.getValue());
      }
    } catch (URISyntaxException ex) {
      throw new CoreException(ex);
    }

    return requestSpecBuilder.build();
  }

  @Loggable
  private static RequestSpecification buildMultiPartRequest(
      RequestSpecBuilder requestSpecBuilder, Request request) {
    for (Map.Entry<String, JsonElement> entry : request.getMultiPart().entrySet()) {
      if (entry.getValue().isJsonArray()) {
        for (JsonElement filePath : entry.getValue().getAsJsonArray()) {
          requestSpecBuilder.addMultiPart(entry.getKey(), new File(filePath.getAsString()));
        }
      } else {
        requestSpecBuilder.addMultiPart(entry.getKey(), entry.getValue().getAsString());
      }
    }

    return requestSpecBuilder.build();
  }

  @Loggable
  private static Response buildResponse(
      Request request, io.restassured.response.Response restassuredResponse) {
    Response response =
        Response.builder()
            .request(request)
            .statusCode(restassuredResponse.getStatusCode())
            .statusLine(restassuredResponse.getStatusLine())
            .contentType(restassuredResponse.getContentType())
            .build();

    Map<String, String> headers = new HashMap<>();
    restassuredResponse
        .getHeaders()
        .forEach(header -> headers.put(header.getName(), header.getValue()));
    response.setHeaders(headers);

    if (restassuredResponse.getBody() != null) {
      response.setBody(restassuredResponse.getBody().prettyPrint());
    }

    if (request.getResponseType() != null
        && request.getResponseType().equals(Request.ResponseType.BYTE_ARRAY)) {
      response.setBytes(restassuredResponse.getBody().asByteArray());
      response.setStream(restassuredResponse.getBody().asInputStream());
    }

    if (restassuredResponse.getCookies() != null) {
      response.setCookies(restassuredResponse.getCookies());
    }

    return response;
  }

  // endregion
}
