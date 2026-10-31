package com.pa.model.util;

import java.awt.Point;

/**
 * Utility class providing tools for easier management of {@code Points} and their coordinates.
 * @author Ewelina Gren
 * @version 1.0
 */
public final class PositionUtil {

    private PositionUtil() {}

    /**
     * Checks if the x and y coordinates of two {@code Points} are withing maximum acceptable range from each other
     * as specified by the user.
     * @param first the first {@code Point} to be compared
     * @param second the second {@code Point} to be compared
     * @param tolerance the maximum acceptable difference between two coordinates
     * @return true if two points are close enough from each other, false otherwise
     */
    public static boolean equalsWithinTolerance(Point first, Point second, int tolerance) {
        return Math.abs(first.x - second.x) <= tolerance && Math.abs(first.y - second.y) <= tolerance;
    }

    /**
     * Creates a {@code Point} by subtracting the values of the x and y coordinates of two Points.
     * @param first the {@code Point} whose coordinates are the minuend
     * @param second the {@code Point} whose coordinates are the subtrahend
     * @return a new {@code Point} representing the result of subtracting coordinates of two Points
     */
    public static Point subtractPoint(Point first, Point second) {
        return new Point(first.x - second.x, first.y - second.y);
    }

}
