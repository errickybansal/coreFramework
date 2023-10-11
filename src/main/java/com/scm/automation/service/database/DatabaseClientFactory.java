package com.scm.automation.service.database;

import com.scm.automation.enums.DatabaseClientType;
import java.security.InvalidParameterException;

/**
 * DatabaseClientFactory class.
 *
 * @author salil.devanshi
 * @version 1.0.52
 * @since 1.0.52
 */
public class DatabaseClientFactory {
  private DatabaseClientFactory() {}

  /**
   * getClient.
   *
   * @param clientType a {@link com.scm.automation.enums.DatabaseClientType} object
   * @return a {@link com.scm.automation.service.database.DatabaseClient} object
   * @since 1.0.52
   */
  public static DatabaseClient getClient(DatabaseClientType clientType) {
    DatabaseClient client;
    switch (clientType) {
      case CASSANDRA -> client = new CassandraClient();
      case ORACLE -> client = new OracleClient();
      case POSTGRES -> client = new PostgresClient();
      case MONGODB -> client = new MongoDbClient();
      default -> throw new InvalidParameterException(
          "Unsupported client type: " + clientType.name());
    }
    return client;
  }
}
