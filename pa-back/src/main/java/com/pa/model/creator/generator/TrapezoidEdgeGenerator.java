package com.pa.model.creator.generator;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * A {@code SingleBumpEdgeGenerator} child class associated with the TRAPEZOID {@code PieceShape},
 * where the piece side is a straight line with a single trapezoid shaped bump.
 * @author Ewelina Gren
 * @version 1.0
 */
public class TrapezoidEdgeGenerator extends SingleBumpEdgeGenerator {

    /**
     * Generates a straight piece edge with a single trapezoid bump turned inward or outward.
     * @param start a point where the edge starts
     * @param end a point where the edge ends
     * @return a {@code Path2D} instance representing one side of a puzzle {@code Piece}
     */
    @Override
    public Path2D generateEdge(Point2D start, Point2D end) {
        Point2D diff = new Point2D.Double(end.getX() - start.getX(), end.getY() - start.getY());
        double startEndDistance = Math.hypot(diff.getX(), diff.getY());

        if (startEndDistance == 0) {
            throw new IllegalArgumentException("The start point cannot be the same as the end point.");
        }

        double bumpDepthRatio = generateBumpDepthRatio(0.05, 0.14);

        double bumpStartRatio = random.nextDouble(0.16, 0.35);
        Point2D bumpStart = new Point2D.Double(
                start.getX() + bumpStartRatio * diff.getX(),
                start.getY() + bumpStartRatio * diff.getY()
        );

        double bumpMidStartRatio = random.nextDouble(0.35, 0.45);
        Point2D bumpMidStart = new Point2D.Double(
                start.getX() + bumpMidStartRatio * diff.getX() + bumpDepthRatio * diff.getY(),
                start.getY() + bumpMidStartRatio * diff.getY() + bumpDepthRatio * diff.getX()
        );

        double bumpMidEndRatio = random.nextDouble(0.55, 0.65);
        Point2D bumpMidEnd = new Point2D.Double(
                start.getX() + bumpMidEndRatio * diff.getX() + bumpDepthRatio * diff.getY(),
                start.getY() + bumpMidEndRatio * diff.getY() + bumpDepthRatio * diff.getX()
        );

        double bumpEndRatio = random.nextDouble(0.65, 0.84);
        Point2D bumpEnd = new Point2D.Double(
                start.getX() + bumpEndRatio * diff.getX(),
                start.getY() + bumpEndRatio * diff.getY()
        );

        Path2D path = new Path2D.Double();
        path.moveTo(start.getX(), start.getY());
        path.lineTo(bumpStart.getX(), bumpStart.getY());
        path.lineTo(bumpMidStart.getX(), bumpMidStart.getY());
        path.lineTo(bumpMidEnd.getX(), bumpMidEnd.getY());
        path.lineTo(bumpEnd.getX(), bumpEnd.getY());
        path.lineTo(end.getX(), end.getY());

        return path;
    }

}