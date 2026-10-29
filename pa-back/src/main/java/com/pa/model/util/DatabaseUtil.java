package com.pa.model.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class DatabaseUtil {

    /**
     * Creates a Hikari object that manages connections with the database.
     * @return a Hikari data source
     */
    public static HikariDataSource createDataSource() {
        Path root = FileUtil.resolveRoot();
        Path dbDir = root.resolve("db/");
        try {
            Files.createDirectory(dbDir);
        } catch (IOException ignored) {}

        Properties properties = FileUtil.loadProperties(root.resolve("app.properties"));
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
