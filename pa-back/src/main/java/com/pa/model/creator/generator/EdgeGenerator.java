package com.pa.model.creator.generator;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * A simple edge generating class used when the edge is supposed to be a straight line,
 * usually associated with the outer edges of a puzzle.
 * @author Ewelina Gren
 * @version 1.0
 */
public class EdgeGenerator {

    /**
     * Generates a straight line that connects the start point and the end point of a piece edge,
     * especially useful while creating the outer edges of a puzzle.
     * @param start a point where the edge starts
     * @param end a point where the edge ends
     * @return a {@code Path2D} instance defining one side of a puzzle {@code Piece}
     *
     */
    public Path2D generateEdge(Point2D start, Point2D end) {
        Path2D path = new Path2D.Double();
        path.moveTo(start.getX(), start.getY());

        path.lineTo(end.getX(), end.getY());
        return path;
    }

}
