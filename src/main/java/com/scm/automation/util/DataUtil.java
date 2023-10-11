package com.scm.automation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.github.underscore.U;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.scm.automation.enums.DataType;
import com.scm.automation.enums.EncodingType;
import com.scm.automation.exception.CoreException;
import io.restassured.path.json.JsonPath;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.CDL;
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.yaml.snakeyaml.Yaml;

/**
 * DataUtil class.
 *
 * @author vipin.v
 * @version 1.0.0
 * @since 1.0.0
 */
public class DataUtil<T> extends U<T> {
  @Getter
  private static final Gson gson =
      new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

  /**
   * Constructor for DataUtil.
   *
   * @param iterable a {@link java.lang.Iterable} object
   * @since 1.0.0
   */
  public DataUtil(Iterable<T> iterable) {
    super(iterable);
  }

  /**
   * Constructor for DataUtil.
   *
   * @param string a {@link java.lang.String} object
   * @since 1.0.0
   */
  public DataUtil(String string) {
    super(string);
  }

  /**
   * read.
   *
   * @param resourcePath a {@link java.lang.String} object
   * @param dataType a {@link com.scm.automation.enums.DataType} object
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.0
   */
  public static JsonObject read(String resourcePath, DataType dataType) {
    return read(resourcePath, dataType, EncodingType.STANDARD);
  }

  /**
   * read.
   *
   * @param resourcePath a {@link java.lang.String} object
   * @param dataType a {@link com.scm.automation.enums.DataType} object
   * @param encodingType a {@link com.scm.automation.enums.EncodingType} object
   * @return a {@link com.google.gson.JsonObject} object
   */
  public static JsonObject read(String resourcePath, DataType dataType, EncodingType encodingType) {
    URL url = Resources.getResource(resourcePath);
    return read(url, dataType, encodingType);
  }

  /**
   * read.
   *
   * @param resourceUrl a {@link java.net.URL} object
   * @param dataType a {@link com.scm.automation.enums.DataType} object
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.0
   */
  public static JsonObject read(URL resourceUrl, DataType dataType) {
    return read(resourceUrl, dataType, EncodingType.STANDARD);
  }

  /**
   * read.
   *
   * @param resourceUrl a {@link java.net.URL} object
   * @param dataType a {@link com.scm.automation.enums.DataType} object
   * @param encodingType a {@link com.scm.automation.enums.EncodingType} object
   * @return a {@link com.google.gson.JsonObject} object
   */
  public static JsonObject read(URL resourceUrl, DataType dataType, EncodingType encodingType) {
    try {
      String payloadString = Resources.toString(resourceUrl, StandardCharsets.UTF_8);

      if (encodingType.equals(EncodingType.BASE64)) {
        return read(IOUtils.toInputStream(payloadString), dataType, encodingType);
      }
      JsonObject jsonObject;
      switch (dataType) {
        case YAML -> {
          Object data = new Yaml().load(payloadString);
          jsonObject = gson.toJsonTree(data).getAsJsonObject();
        }
        case JSON -> jsonObject = gson.fromJson(payloadString, JsonObject.class);
        case XML -> {
          String jsonString = xmlToJson(payloadString);
          jsonObject = gson.fromJson(jsonString, JsonObject.class);
        }
        case CSV -> {
          String jsonString = csvToJson(payloadString);
          jsonObject = gson.fromJson(jsonString, JsonObject.class);
        }
        case XLSX -> jsonObject = excelToJson(resourceUrl.getPath());
        case PDF -> jsonObject = pdfToJson(resourceUrl);
        default -> jsonObject = null;
      }

      return extendData(resourceUrl, jsonObject, dataType);
    } catch (IOException ex) {
      throw new CoreException(ex);
    }
  }

  /**
   * read.
   *
   * @param stream a {@link java.io.InputStream} object
   * @param dataType a {@link com.scm.automation.enums.DataType} object
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.0
   */
  public static JsonObject read(InputStream stream, DataType dataType) {
    return read(stream, dataType, EncodingType.STANDARD);
  }

  /**
   * read.
   *
   * @param stream a {@link java.io.InputStream} object
   * @param dataType a {@link com.scm.automation.enums.DataType} object
   * @param encodingType a {@link com.scm.automation.enums.EncodingType} object
   * @return a {@link com.google.gson.JsonObject} object
   */
  public static JsonObject read(InputStream stream, DataType dataType, EncodingType encodingType) {
    try {
      JsonObject jsonObject;
      if (encodingType.equals(EncodingType.BASE64)) {
        stream =
            new ByteArrayInputStream(
                decodeBase64(IOUtils.toString(stream, StandardCharsets.UTF_8)));
      }

      switch (dataType) {
        case YAML -> {
          Object data = new Yaml().load(stream);
          jsonObject = gson.toJsonTree(data).getAsJsonObject();
        }
        case JSON -> jsonObject =
            gson.fromJson(IOUtils.toString(stream, StandardCharsets.UTF_8), JsonObject.class);
        case XML -> {
          String jsonString = xmlToJson(IOUtils.toString(stream, StandardCharsets.UTF_8));
          jsonObject = gson.fromJson(jsonString, JsonObject.class);
        }
        case CSV -> {
          String jsonString = csvToJson(IOUtils.toString(stream, StandardCharsets.UTF_8));
          jsonObject = gson.fromJson(jsonString, JsonObject.class);
        }
        case XLSX -> jsonObject = excelToJson(stream);
        case PDF -> jsonObject = pdfToJson(ByteStreams.toByteArray(stream));

        default -> jsonObject = null;
      }

      return extendData(null, jsonObject, dataType);
    } catch (IOException ex) {
      throw new CoreException(ex);
    }
  }

  /**
   * getAsJsonElement.
   *
   * @param jsonString a {@link java.lang.String} object
   * @param path a {@link java.lang.String} object
   * @return a {@link com.google.gson.JsonElement} object
   * @since 1.0.0
   */
  public static JsonElement getAsJsonElement(String jsonString, String path) {
    JsonPath jsonPath = JsonPath.from(jsonString);
    Object property = jsonPath.get(path);
    return null != property ? gson.toJsonTree(property) : JsonNull.INSTANCE;
  }

  /**
   * getAsJsonElement.
   *
   * @param jsonObject a {@link com.google.gson.JsonObject} object
   * @param path a {@link java.lang.String} object
   * @return a {@link com.google.gson.JsonElement} object
   * @since 1.0.0
   */
  public static JsonElement getAsJsonElement(JsonObject jsonObject, String path) {
    String jsonString = gson.toJson(jsonObject);
    return getAsJsonElement(jsonString, path);
  }

  /**
   * getAsJsonElement.
   *
   * @param data a {@link java.lang.String} object
   * @param path a {@link java.lang.String} object
   * @param dataType a {@link com.scm.automation.enums.DataType} object
   * @return a {@link com.google.gson.JsonElement} object
   */
  public static JsonElement getAsJsonElement(String data, String path, DataType dataType) {
    JsonObject jsonObject = read(IOUtils.toInputStream(data, Charset.defaultCharset()), dataType);
    return getAsJsonElement(jsonObject, path);
  }

  /**
   * getAsJsonObject.
   *
   * @param jsonObject a {@link com.google.gson.JsonObject} object
   * @param path a {@link java.lang.String} object
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.0
   */
  public static JsonObject getAsJsonObject(JsonObject jsonObject, String path) {
    return getAsJsonElement(jsonObject, path).getAsJsonObject();
  }

  /**
   * getAsJsonArray.
   *
   * @param jsonObject a {@link com.google.gson.JsonObject} object
   * @param path a {@link java.lang.String} object
   * @return a {@link com.google.gson.JsonArray} object
   * @since 1.0.0
   */
  public static JsonArray getAsJsonArray(JsonObject jsonObject, String path) {
    return getAsJsonElement(jsonObject, path).getAsJsonArray();
  }

  /**
   * getAsString.
   *
   * @param jsonObject a {@link com.google.gson.JsonObject} object
   * @param path a {@link java.lang.String} object
   * @return a {@link java.lang.String} object
   * @since 1.0.0
   */
  public static String getAsString(JsonObject jsonObject, String path) {
    return getAsJsonElement(jsonObject, path).getAsString();
  }

  /**
   * toJsonElement.
   *
   * @param object a {@link java.lang.Object} object
   * @return a {@link com.google.gson.JsonElement} object
   * @since 1.0.0
   */
  public static JsonElement toJsonElement(Object object) {
    return Optional.ofNullable(object)
        .map(
            data ->
                data instanceof String json
                    ? gson.fromJson(json, JsonElement.class)
                    : gson.toJsonTree(data))
        .orElse(JsonNull.INSTANCE);
  }

  /**
   * toJsonObject.
   *
   * @param object a {@link java.lang.Object} object
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.0
   */
  public static JsonObject toJsonObject(Object object) {
    return toJsonElement(object).getAsJsonObject();
  }

  /**
   * toJsonArray.
   *
   * @param object a {@link java.lang.Object} object
   * @return a {@link com.google.gson.JsonArray} object
   * @since 1.0.0
   */
  public static JsonArray toJsonArray(Object object) {
    return toJsonElement(object).getAsJsonArray();
  }

  /**
   * toObject.
   *
   * @param jsonString a {@link java.lang.String} object
   * @param classType a {@link java.lang.Class} object
   * @param <O> a O class
   * @return a O object
   */
  public static <O> O toObject(String jsonString, Class<O> classType) {
    return toObject(toJsonObject(jsonString), classType);
  }

  /**
   * toObject.
   *
   * @param jsonObject a {@link com.google.gson.JsonObject} object
   * @param classType a {@link java.lang.Class} object
   * @param <O> a O class
   * @return a O object
   */
  public static <O> O toObject(JsonObject jsonObject, Class<O> classType) {
    return getGson().fromJson(jsonObject, classType);
  }

  /**
   * toObjectArray.
   *
   * @param jsonString a {@link java.lang.String} object
   * @param classType a {@link java.lang.Class} object
   * @param <O> a O class
   * @return a {@link java.util.List} object
   */
  public static <O> List<O> toObjectArray(String jsonString, Class<O> classType) {
    return toObjectArray(toJsonArray(jsonString), classType);
  }

  /**
   * toObjectArray.
   *
   * @param jsonArray a {@link com.google.gson.JsonArray} object
   * @param classType a {@link java.lang.Class} object
   * @param <O> a O class
   * @return a {@link java.util.List} object
   */
  public static <O> List<O> toObjectArray(JsonArray jsonArray, Class<O> classType) {
    Type arrayType = TypeToken.getParameterized(ArrayList.class, classType).getType();
    return getGson().fromJson(jsonArray, arrayType);
  }

  /**
   * xmlToJsonObject.
   *
   * @param xml a {@link java.lang.String} object
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.0
   */
  public static JsonObject xmlToJsonObject(String xml) {
    return getGson().fromJson(xmlToJson(xml), JsonObject.class);
  }

  /**
   * jsonObjectToXml.
   *
   * @param jsonObject a {@link com.google.gson.JsonObject} object
   * @return a {@link java.lang.String} object
   * @since 1.0.0
   */
  public static String jsonObjectToXml(JsonObject jsonObject) {
    return jsonToXml(getGson().toJson(jsonObject));
  }

  /**
   * jsonObjectToXml.
   *
   * @param jsonObject a {@link com.google.gson.JsonObject} object
   * @param mode a Mode object
   * @return a {@link java.lang.String} object
   * @since 1.0.0
   */
  public static String jsonObjectToXml(JsonObject jsonObject, Mode mode) {
    return jsonToXml(getGson().toJson(jsonObject), mode);
  }

  /**
   * jsonArrayToXml.
   *
   * @param jsonArray a {@link com.google.gson.JsonArray} object
   * @return a {@link java.lang.String} object
   */
  public static String jsonArrayToXml(JsonArray jsonArray) {
    return jsonToXml(getGson().toJson(jsonArray));
  }

  /**
   * jsonArrayToXml.
   *
   * @param jsonArray a {@link com.google.gson.JsonArray} object
   * @param mode a Mode object
   * @return a {@link java.lang.String} object
   */
  public static String jsonArrayToXml(JsonArray jsonArray, Mode mode) {
    return jsonToXml(getGson().toJson(jsonArray), mode);
  }

  /**
   * deepMerge.
   *
   * @param source a {@link com.google.gson.JsonObject} object
   * @param target a {@link com.google.gson.JsonObject} object
   * @return a {@link com.google.gson.JsonObject} object
   * @since 1.0.0
   */
  public static JsonObject deepMerge(JsonObject source, JsonObject target) {
    if (source == null || target == null) {
      return target;
    }

    for (String key : source.getAsJsonObject().keySet()) {
      JsonElement sourceValue = source.get(key);
      if (!target.has(key)) {
        target.add(key, sourceValue);
      } else {
        JsonElement targetValue = target.get(key);
        if (sourceValue instanceof JsonArray && targetValue instanceof JsonArray) {
          target.get(key).getAsJsonArray().addAll(sourceValue.getAsJsonArray());
        } else if (sourceValue instanceof JsonObject && targetValue instanceof JsonObject) {
          target
              .getAsJsonObject()
              .add(key, deepMerge(sourceValue.getAsJsonObject(), targetValue.getAsJsonObject()));
        } else {
          target.add(key, sourceValue);
        }
      }
    }
    return target;
  }

  /**
   * jsonToYaml.
   *
   * @param jsonString a {@link java.lang.String} object
   * @return a {@link java.lang.String} object
   * @since 1.0.0
   */
  public static String jsonToYaml(String jsonString) {
    try {
      JsonNode json = new ObjectMapper().readTree(jsonString);
      return new YAMLMapper(
              new YAMLFactory()
                  .enable(YAMLGenerator.Feature.LITERAL_BLOCK_STYLE)
                  .enable(YAMLGenerator.Feature.SPLIT_LINES))
          .writeValueAsString(json);
    } catch (JsonProcessingException jsonProcessingException) {
      throw new CoreException(jsonProcessingException);
    }
  }

  /**
   * csvToJson.
   *
   * @param input a {@link java.lang.String} object
   * @return a {@link java.lang.String} object
   * @since 1.0.0
   */
  public static String csvToJson(String input) {
    JSONArray json = CDL.toJSONArray(input);
    return getGson().toJson(json);
  }

  /**
   * toHtmlDocument.
   *
   * @param html a {@link java.lang.String} object
   * @return a {@link org.jsoup.nodes.Document} object
   * @since 1.0.0
   */
  public static Document toHtmlDocument(String html) {
    return Jsoup.parse(html);
  }

  /**
   * find.
   *
   * @param input a {@link java.lang.String} object
   * @param regex a {@link java.lang.String} object
   * @return a {@link java.util.List} object
   * @since 1.0.0
   */
  public static List<String> find(String input, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(input);

    List<String> matches = new ArrayList<>();
    while (matcher.find()) {
      matches.add(matcher.group());
    }
    return matches;
  }

  /**
   * getDataType.
   *
   * @param inputString a {@link java.lang.String} object
   * @return a {@link com.scm.automation.enums.DataType} object
   */
  public static DataType getDataType(String inputString) {
    if (StringUtils.isNotEmpty(inputString)) {
      for (DataType type : DataType.values()) {
        try {
          read(IOUtils.toInputStream(inputString.trim()), type);
          return type;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    return null;
  }

  /**
   * decodeBase64.
   *
   * @param inputBase64Format a {@link java.lang.String} object
   * @return an array of {@link byte} objects
   */
  public static byte[] decodeBase64(String inputBase64Format) {
    try {
      return BaseEncoding.base64().decode(inputBase64Format);
    } catch (Exception e) {
      return BaseEncoding.base64Url().decode(inputBase64Format);
    }
  }

  /**
   * encodeBase64.
   *
   * @param bytes an array of {@link byte} objects
   * @return a {@link java.lang.String} object
   */
  public static String encodeBase64(byte[] bytes) {

    try {
      return BaseEncoding.base64().encode(bytes);
    } catch (Exception e) {
      return BaseEncoding.base64Url().encode(bytes);
    }
  }

  // region private Methods

  private static JsonObject excelToJson(String filePath) throws IOException {
    try (FileInputStream excelFile = new FileInputStream(filePath)) {
      XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
      return excelToJson(workbook);
    } catch (IOException ex) {
      throw new CoreException(ex);
    }
  }

  private static JsonObject excelToJson(InputStream stream) {
    try {
      XSSFWorkbook workbook = new XSSFWorkbook(stream);
      return excelToJson(workbook);
    } catch (IOException ex) {
      throw new CoreException(ex);
    }
  }

  private static JsonObject excelToJson(XSSFWorkbook workbook) {
    FormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator(workbook);
    JsonObject workbookJson = new JsonObject();

    for (Sheet sheet : workbook) {
      JsonArray sheetJson = new JsonArray();
      int lastRowNum = sheet.getLastRowNum();
      int lastColumnNum = sheet.getRow(0).getLastCellNum();
      Row firstRowAsKeys = sheet.getRow(0); // first row as a json keys

      for (int i = 1; i <= lastRowNum; i++) {
        JsonObject rowJson = new JsonObject();
        Row row = sheet.getRow(i);

        if (row != null) {
          for (int j = 0; j < lastColumnNum; j++) {
            formulaEvaluator.evaluate(row.getCell(j));
            rowJson.addProperty(
                firstRowAsKeys.getCell(j).getStringCellValue(),
                new DataFormatter().formatCellValue(row.getCell(j), formulaEvaluator));
          }
          sheetJson.add(rowJson);
        }
      }
      workbookJson.add(sheet.getSheetName(), sheetJson);
    }

    return workbookJson;
  }

  private static JsonObject pdfToJson(URL filePath) throws IOException {
    File file = new File(filePath.getPath());
    return pdfToJson(PDDocument.load(file));
  }

  private static JsonObject pdfToJson(byte[] bytes) throws IOException {
    return pdfToJson(PDDocument.load(bytes));
  }

  private static JsonObject pdfToJson(PDDocument document) throws IOException {
    PDFTextStripper pdfStripper = new PDFTextStripper();
    String text = pdfStripper.getText(document);
    JsonObject pdfData = new JsonObject();
    pdfData.addProperty("data", text);
    return pdfData;
  }

  private static JsonObject extendData(URL resourceUrl, JsonObject data, DataType dataType) {
    String extendsKey = "$extends";
    if (data != null && data.has(extendsKey)) {

      JsonObject baseData;
      if (resourceUrl != null && resourceUrl.getProtocol().equals("jar")) {
        try {
          String jarPath = resourceUrl.toString().split("!")[0];
          String resourcePath = jarPath + "!" + File.separator + data.get(extendsKey).getAsString();
          baseData = read(new URL(resourcePath), dataType);
        } catch (MalformedURLException ex) {
          throw new CoreException(ex);
        }
      } else {
        baseData = read(data.get(extendsKey).getAsString(), dataType);
      }

      data.remove(extendsKey);
      data = deepMerge(data, baseData);
    }

    return data;
  }

  // endregion
}
