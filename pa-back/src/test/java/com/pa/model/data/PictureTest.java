package com.pa.model.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;

public class PictureTest {

    @Test
    public void pictureTest() {
        Picture picture = new Picture(1, new File("image.jpg"), LocalDateTime.now(), new HashSet<>());
        picture.addTag("testTag");

        Assertions.assertTrue(picture.containsTag("testTag"));
        Assertions.assertEquals("Picture{id=1, sourceFile='image.jpg'}", picture.toString());
    }

}
