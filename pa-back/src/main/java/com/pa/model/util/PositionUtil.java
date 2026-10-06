package com.pa.model.util;

import java.awt.Point;

public final class PositionUtil {

    private PositionUtil() {}

    public static boolean equalsWithinTolerance(Point first, Point second, int tolerance) {
        return Math.abs(first.x - second.x) <= tolerance && Math.abs(first.y - second.y) <= tolerance;
    }

    public static Point subtractPoint(Point first, Point second) {
        return new Point(first.x - second.x, first.y - second.y);
    }

}
