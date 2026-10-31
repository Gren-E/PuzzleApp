package com.pa.model.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DatebaseUtilTest {

    @Test
    public void extractHikariPropertiesTest() throws URISyntaxException {
        String path = Paths.get(FileUtilTest.class.getResource("test.properties").toURI()).toString();
        Path propertiesPath = Paths.get(path);
        Properties properties = FileUtil.loadProperties(propertiesPath);

        Properties hikariProperties = DatabaseUtil.extractHikariProperties(properties);
        Assertions.assertEquals(2, hikariProperties.size());
        Assertions.assertEquals("20", hikariProperties.getProperty("maximumPoolSize"));
        Assertions.assertEquals("15", hikariProperties.getProperty("minimumIdle"));
    }

    @Test
    public void buildH2JdbcUrlTest() {
        Properties properties = new Properties();
        properties.put("db.h2.options", "testOptions");

        String url = DatabaseUtil.buildH2JdbcUrl(Paths.get("test\\url"), "testDatabase", properties);
        Assertions.assertEquals("jdbc:h2:file:test/url/testDatabase;testOptions", url);
    }

    @Test
    public void buildH2JdbcUrlWithSemicolonTest() {
        Properties properties = new Properties();
        properties.put("db.h2.options", ";testOptions");

        String url = DatabaseUtil.buildH2JdbcUrl(Paths.get("test\\url"), "testDatabase", properties);
        Assertions.assertEquals("jdbc:h2:file:test/url/testDatabase;testOptions", url);
    }

    @Test
    public void buildH2JdbcUrlWithoutOptionsTest() {
        Properties properties = new Properties();

        String url = DatabaseUtil.buildH2JdbcUrl(Paths.get("test\\url"), "testDatabase", properties);
        Assertions.assertEquals("jdbc:h2:file:test/url/testDatabase", url);
    }

}
