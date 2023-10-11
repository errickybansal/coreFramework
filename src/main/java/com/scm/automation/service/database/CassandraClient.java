package com.scm.automation.service.database;

import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scm.automation.annotation.Loggable;
import com.scm.automation.exception.CoreException;
import com.scm.automation.service.database.model.Request;
import com.scm.automation.service.database.model.Response;
import com.scm.automation.util.DataUtil;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;

/**
 * CassandraClient class.
 *
 * @author salil.devanshi
 * @version 1.0.32
 * @since 1.0.32
 */
public class CassandraClient implements DatabaseClient {
  private CqlSession session;

  /** {@inheritDoc} */
  @Override
  @Loggable
  public Response fetch(Request request) {
    try {
      List<Row> rows =
          this.getConnection(request)
              .execute(SimpleStatement.builder(request.getQuery()).build())
              .all();
      ColumnDefinitions columnDefinitions = rows.get(0).getColumnDefinitions();
      List<Map<String, String>> result =
          rows.stream()
              .map(
                  row -> {
                    Map<String, String> colValue = new LinkedHashMap<>();
                    columnDefinitions.forEach(
                        columnDefinition ->
                            colValue.put(
                                String.valueOf(columnDefinition.getName()),
                                String.valueOf(row.getObject(columnDefinition.getName()))));
                    return colValue;
                  })
              .collect(Collectors.toList());
      JsonArray jsonArray = DataUtil.getGson().toJsonTree(result).getAsJsonArray();
      return Response.builder()
          .request(request)
          .result(Response.Result.builder().data(jsonArray).build())
          .build();
    } catch (InvalidQueryException se) {
      throw new CoreException("Couldn't query the database.", se);
    } finally {
      this.closeConnection();
    }
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public Response add(Request request) {
    throw new NotImplementedException("Function not in use!");
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public Response update(Request request) {
    try {
      ResultSet result =
          this.getConnection(request).execute(SimpleStatement.builder(buildQuery(request)).build());
      JsonObject metaData = new JsonObject();
      metaData.addProperty("wasApplied", result.wasApplied());
      return Response.builder()
          .request(request)
          .result(Response.Result.builder().metaData(metaData).build())
          .build();
    } catch (InvalidQueryException se) {
      throw new CoreException("Update query failed.", se);
    } finally {
      this.closeConnection();
    }
  }

  /** {@inheritDoc} */
  @Override
  @Loggable
  public Response delete(Request request) {
    try {
      ResultSet result =
          this.getConnection(request).execute(SimpleStatement.builder(buildQuery(request)).build());
      JsonObject metaData = new JsonObject();
      metaData.addProperty("wasApplied", result.wasApplied());
      return Response.builder()
          .request(request)
          .result(Response.Result.builder().metaData(metaData).build())
          .build();
    } catch (InvalidQueryException se) {
      throw new CoreException("Delete query failed.", se);
    } finally {
      this.closeConnection();
    }
  }

  // region private methods
  @Loggable
  private CqlSession getConnection(Request request) {
    DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
    session =
        CqlSession.builder()
            .withConfigLoader(loader)
            .addContactPoint(
                new InetSocketAddress(request.getHost(), Integer.parseInt(request.getPort())))
            .withAuthCredentials(request.getUsername(), request.getPassword())
            .withKeyspace(request.getKeyspace())
            .withLocalDatacenter(request.getDatacenter())
            .build();
    return session;
  }

  private void closeConnection() {
    if (this.session != null && !this.session.isClosed()) {
      this.session.close();
    }
  }

  @Loggable
  private String buildQuery(Request request) {
    return Boolean.TRUE.equals(request.getExecuteIfPresent())
        ? request.getQuery() + " IF EXISTS"
        : request.getQuery();
  }
  // endregion

}
