package com.pa.model.database;

import com.pa.model.util.DatabaseUtil;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class used for connecting with the internal database.
 * @author Ewelina Gren
 * @version 1.0
 */
public final class InternalDatabase {

    private static InternalDatabase instance;

    private final HikariDataSource dataSource;

    private InternalDatabase() {
        dataSource = DatabaseUtil.createDataSource();
        Runtime.getRuntime().addShutdownHook(new Thread(dataSource::close, "internal-database-shutdown"));
    }

    /**
     * Returns the single instance of {@code InternalDatabase} class according to the Singleton design pattern.
     * @return single instance of {@code InternalDatabase}
     */
    public static synchronized InternalDatabase getInstance() {
        if (instance == null) {
            instance = new InternalDatabase();
        }

        return instance;
    }

    /**
     * Returns a connection with the internal database.
     * @return {@code Connection} with the database
     * @throws SQLException if Connection cannot be established
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
