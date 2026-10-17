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

public final class InternalDatabase {

    private static final HikariDataSource DATA_SOURCE = createDataSource();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(DATA_SOURCE::close, "internal-database-shutdown"));
    }

    private InternalDatabase() {}

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }

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

    private static Path resolveRoot() {
        return Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
    }

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

    private static Properties extractHikariProperties(Properties properties) {
        Properties hikari = new Properties();
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith("db.hikari.")) {
                hikari.setProperty(key.substring("db.hikari.".length()), properties.getProperty(key).strip());
            }
        }

        return hikari;
    }

    private static String buildJdbcUrl(Path dbDir, Properties properties) {
        String path = dbDir.resolve("internal-database").toString().replace("\\", "/");
        String options = properties.getProperty("db.h2.options", "").strip();
        if (!options.isEmpty() && !options.startsWith(";")) {
            options = ";" + options;
        }

        return "jdbc:h2:file:" + path + options;
    }

}
