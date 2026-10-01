package com.pa.creator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class PieceShapeOutlineTest {

    @Test
    public void clockwiseOutlineTest() {
        PieceShapeOutline outline = new PieceShapeOutline(true);
        outline.setNorth(createPath2D(new Point(0,0), new Point(10,0)));
        outline.setEast(createPath2D(new Point(10,0), new Point(10,10)));
        outline.setSouth(createPath2D(new Point(10,10), new Point(0,10)));
        outline.setWest(createPath2D(new Point(0,10), new Point(0,0)));
        Shape shape = outline.createShape();
        PathIterator iterator = shape.getPathIterator(null);

        Assertions.assertTrue(verifyNextCoordinates(new Point(0,0), iterator));
        Assertions.assertTrue(verifyNextCoordinates(new Point(10,0), iterator));
        Assertions.assertTrue(verifyNextCoordinates(new Point(10,10), iterator));
        Assertions.assertTrue(verifyNextCoordinates(new Point(0,10), iterator));
        Assertions.assertTrue(verifyNextCoordinates(new Point(0,0), iterator));
    }

    @Test
    public void counterclockwiseOutlineTest() {
        PieceShapeOutline outline = new PieceShapeOutline(false);
        outline.setNorth(createPath2D(new Point(20,30), new Point(5,30)));
        outline.setEast(createPath2D(new Point(20,40), new Point(20,30)));
        outline.setSouth(createPath2D(new Point(5,40), new Point(20,40)));
        outline.setWest(createPath2D(new Point(5,30), new Point(5,40)));
        Shape shape = outline.createShape();
        PathIterator iterator = shape.getPathIterator(null);

        Assertions.assertTrue(verifyNextCoordinates(new Point(20,30), iterator));
        Assertions.assertTrue(verifyNextCoordinates(new Point(5,30), iterator));
        Assertions.assertTrue(verifyNextCoordinates(new Point(5,40), iterator));
        Assertions.assertTrue(verifyNextCoordinates(new Point(20,40), iterator));
        Assertions.assertTrue(verifyNextCoordinates(new Point(20,30), iterator));
    }

    private Path2D createPath2D(Point2D start, Point2D end) {
        Path2D path = new Path2D.Double();
        path.moveTo(start.getX(), start.getY());
        path.lineTo(end.getX(), end.getY());
        return path;
    }

    private boolean verifyNextCoordinates(Point expectedValue, PathIterator iterator) {
        double[] coordinates = new double[2];
        iterator.currentSegment(coordinates);
        iterator.next();
        return new Point((int) coordinates[0], (int) coordinates[1]).equals(expectedValue);
    }

}
