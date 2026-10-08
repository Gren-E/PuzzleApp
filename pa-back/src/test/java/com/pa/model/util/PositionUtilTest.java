package com.pa.model.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.Point;

public class PositionUtilTest {

    @Test
    public void equalsWithinToleranceTest() {
        Point point1 = new Point(10, 50);
        Point point2 = new Point(15, 40);
        Point point3 = new Point(50, 60);

        Assertions.assertTrue(PositionUtil.equalsWithinTolerance(point1, point2, 10));
        Assertions.assertTrue(PositionUtil.equalsWithinTolerance(point1, point3, 40));
        Assertions.assertTrue(PositionUtil.equalsWithinTolerance(point2, point3, 45));

        Assertions.assertFalse(PositionUtil.equalsWithinTolerance(point1, point2, 9));
        Assertions.assertFalse(PositionUtil.equalsWithinTolerance(point1, point3, 39));
        Assertions.assertFalse(PositionUtil.equalsWithinTolerance(point2, point3, 1));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3, 6, 8, 23, 5, 17",
            "-24, 0, 40, -35, 64, -35",
            "0, 0, 253, 15, 253, 15"
    })
    public void subtractPointTest(int xExpected, int yExpected, int x1, int y1, int x2, int y2) {
        Point point1 = new Point(x1, y1);
        Point point2 = new Point(x2, y2);

        Assertions.assertEquals(new Point(xExpected, yExpected), PositionUtil.subtractPoint(point1, point2));
    }

}
