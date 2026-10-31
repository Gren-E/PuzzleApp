package com.pa.model.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FileUtilTest {

    @Test
    public void resolveRootTest() {
        System.setProperty("user.dir", System.getProperty("user.home"));
        Path expectedRoot = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        Assertions.assertEquals(expectedRoot, FileUtil.resolveRoot());
    }

    @Test
    public void loadPropertiesTest() throws URISyntaxException {
        String path = Paths.get(FileUtilTest.class.getResource("test.properties").toURI()).toString();
        Path propertiesPath = Paths.get(path);
        Properties properties = FileUtil.loadProperties(propertiesPath);

        Assertions.assertEquals("username", properties.getProperty("db.username"));
        Assertions.assertEquals("20", properties.getProperty("db.hikari.maximumPoolSize"));
        Assertions.assertEquals("15", properties.getProperty("db.hikari.minimumIdle"));
        Assertions.assertEquals("45000", properties.getProperty("hikari.idleTimeout"));

        Assertions.assertNull(properties.getProperty("db.url"));
        Assertions.assertNull(properties.getProperty("#db.url"));
    }

    @Test
    public void invalidPropertiesFileTest() throws URISyntaxException {
        String path = Paths.get(FileUtilTest.class.getResource(".").toURI()).toString();
        Path propertiesPath = Paths.get(path);

        Assertions.assertThrows(IllegalStateException.class, () -> FileUtil.loadProperties(propertiesPath));
    }

}
