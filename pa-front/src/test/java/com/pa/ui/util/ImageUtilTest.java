package com.pa.ui.util;

import com.pa.ui.util.ImageUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageUtilTest {

    @Test
    public void imageUtilTest() {
        BufferedImage image = ImageUtil.getBlankImage(Color.BLUE, 100, 300);

        Assertions.assertEquals(100, image.getWidth(null));
        Assertions.assertEquals(300, image.getHeight(null));
        Assertions.assertEquals(Color.BLUE.getRGB(), image.getRGB(getRandomInt(100), getRandomInt(300)));
    }

    private int getRandomInt(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }
    
}
