package com.pa.model.creator.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class ClassicEdgeGeneratorTest {

    @Test
    public void generateEdgeTest() {
        ClassicEdgeGenerator generator = new ClassicEdgeGenerator();
        Point2D start = new Point(10, 30);
        Point2D end = new Point(50, 50);

        Assertions.assertThrows(IllegalArgumentException.class, () -> generator.generateEdge(start, start));

        Path2D edge = generator.generateEdge(start, end);

        Assertions.assertNotNull(edge);
        Assertions.assertEquals(end, edge.getCurrentPoint());
    }

}
