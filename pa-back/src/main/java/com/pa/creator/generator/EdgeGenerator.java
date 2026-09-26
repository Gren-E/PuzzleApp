package com.pa.creator.generator;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class EdgeGenerator {

    public Path2D generateEdge(Point2D start, Point2D end) {
        Path2D path = new Path2D.Double();
        path.moveTo(start.getX(), start.getY());

        path.lineTo(end.getX(), end.getY());
        return path;
    }

}
