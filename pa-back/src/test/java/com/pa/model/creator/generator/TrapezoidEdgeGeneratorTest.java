package com.pa.model.creator.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class TrapezoidEdgeGeneratorTest {

    @Test
    public void generateEdgeTest() {
        TrapezoidEdgeGenerator generator = new TrapezoidEdgeGenerator();
        Point2D start = new Point(80, 100);
        Point2D end = new Point(30, 60);

        Assertions.assertThrows(IllegalArgumentException.class, () -> generator.generateEdge(start, start));

        Path2D edge = generator.generateEdge(start, end);

        Assertions.assertNotNull(edge);
        Assertions.assertEquals(end, edge.getCurrentPoint());
    }

}
