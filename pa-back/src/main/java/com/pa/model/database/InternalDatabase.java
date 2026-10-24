package com.pa.model.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class used for connecting with the internal database.
 * @author Ewelina Gren
 * @version 1.0
 */
public final class InternalDatabase {

    private static final HikariDataSource DATA_SOURCE = createDataSource();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(DATA_SOURCE::close, "internal-database-shutdown"));
    }

    private InternalDatabase() {}

    /**
     * Returns a connection with the internal database.
     * @return {@code Connection} with the database
     * @throws SQLException if Connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }

    /**
     * Creates a Hikari object that manages connections with the database.
     * @return a Hikari data source
     */
    private static HikariDataSource createDataSource() {
        Path root = resolveRoot();
        Path dbDir = root.resolve("db/");
        try {
            Files.createDirectory(dbDir);
        } catch (IOException ignored) {}

        Properties properties = loadProperties(root.resolve("app.properties"));
        HikariConfig config = new HikariConfig(extractHikariProperties(properties));
        config.setJdbcUrl(buildJdbcUrl(dbDir, properties));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));
        if (config.getPoolName() == null) {
            config.setPoolName("InternalDatabasePool");
        }

        return new HikariDataSource(config);
    }

    /**
     * Returns the project root.
     * @return the root {@code Path}
     */
    private static Path resolveRoot() {
        return Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
    }

    /**
     * Loads all properties from the {@code Path}
     * @param path a {@code Path} to the properties {@code File}
     * @return {@code Properties}
     */
    private static Properties loadProperties(Path path) {
        if (!Files.isRegularFile(path)) {
            throw new IllegalStateException("Cannot read the file: " + path);
        }

        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(path)) {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    /**
     * Loads Hikari properties from {@code Properties}
     * @param properties all Properties
     * @return Hikari {@code Properties}
     */
    private static Properties extractHikariProperties(Properties properties) {
        Properties hikari = new Properties();
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith("db.hikari.")) {
                hikari.setProperty(key.substring("db.hikari.".length()), properties.getProperty(key).strip());
            }
        }

        return hikari;
    }

    /**
     * Creates the database's URL that can be read by JDBC
     * @param dbDir the database directory
     * @param properties general {@code Properties}
     * @return database's URL
     */
    private static String buildJdbcUrl(Path dbDir, Properties properties) {
        String path = dbDir.resolve("internal-database").toString().replace("\\", "/");
        String options = properties.getProperty("db.h2.options", "").strip();
        if (!options.isEmpty() && !options.startsWith(";")) {
            options = ";" + options;
        }

        return "jdbc:h2:file:" + path + options;
    }

}
