package com.pa.model.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FileUtil {

    /**
     * Returns the project root.
     * @return the root {@code Path}
     */
    public static Path resolveRoot() {
        return Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
    }

    /**
     * Loads all properties from the {@code Path}
     * @param path a {@code Path} to the properties {@code File}
     * @return {@code Properties}
     */
    public static Properties loadProperties(Path path) {
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

}
