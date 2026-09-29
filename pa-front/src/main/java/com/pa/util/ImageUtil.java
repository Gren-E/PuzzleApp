package com.pa.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A utility class providing tools for easier use of images.
 * @author Ewelina Gren
 * @version 1.0
 */
public class ImageUtil {

    /**
     * Generates a blank image of specified dimensions and filled with a color.
     * @param color the {@code Color} that fills the image
     * @param width the width of the image
     * @param height the height of the image
     * @return a blank {@code BufferedImage} filled with one color
     */
    public static BufferedImage getBlankImage(Color color, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();

        try {
            g2d.setColor(color);
            g2d.fillRect(0, 0, width, height);
        } finally {
            g2d.dispose();
        }

        return image;
    }

}
