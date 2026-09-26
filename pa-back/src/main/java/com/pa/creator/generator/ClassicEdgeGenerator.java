package com.pa.creator.generator;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class ClassicEdgeGenerator extends SingleBumpEdgeGenerator {

    private static final Random random = new Random();

    @Override
    public Path2D generateEdge(Point2D start, Point2D end) {
        Point2D diff = new Point2D.Double(end.getX() - start.getX(), end.getY() - start.getY());
        double startEndDistance = Math.hypot(diff.getX(), diff.getY());

        if (startEndDistance == 0) {
            throw new IllegalArgumentException("The start point cannot be the same as the end point.");
        }

        double bumpDepthRatio = generateBumpDepthRatio(0.1, 0.24);

        double bumpStartRatio = random.nextDouble(0.35, 0.4);
        Point2D bumpStart = new Point2D.Double(
                start.getX() + bumpStartRatio * diff.getX(),
                start.getY() + bumpStartRatio * diff.getY()
        );

        double bumpMidRatio = 0.5;
        Point2D bumpMid = new Point2D.Double(
                start.getX() + bumpMidRatio * diff.getX() + bumpDepthRatio * diff.getY(),
                start.getY() + bumpMidRatio * diff.getY() + bumpDepthRatio * diff.getX()
        );

        double bumpEndRatio = random.nextDouble(0.6, 0.65);
        Point2D bumpEnd = new Point2D.Double(
                start.getX() + bumpEndRatio * diff.getX(),
                start.getY() + bumpEndRatio * diff.getY()
        );

        double controlRatio1 = bumpStartRatio * 0.8;
        Point2D controlPoint1a = new Point2D.Double(
                start.getX() + controlRatio1 * diff.getX() + bumpDepthRatio * diff.getY() * 0.5,
                start.getY() + controlRatio1 * diff.getY() + bumpDepthRatio * diff.getX() * 0.5
        );
        Point2D controlPoint1b = new Point2D.Double(
                start.getX() + controlRatio1 * diff.getX() + bumpDepthRatio * diff.getY(),
                start.getY() + controlRatio1 * diff.getY() + bumpDepthRatio * diff.getX()
        );

        double controlRatio2 = (1 - bumpEndRatio) * 0.8;
        Point2D controlPoint2a = new Point2D.Double(
                end.getX() - controlRatio2 * diff.getX() + bumpDepthRatio * diff.getY() * 0.5,
                end.getY() - controlRatio2 * diff.getY() + bumpDepthRatio * diff.getX() * 0.5
        );
        Point2D controlPoint2b = new Point2D.Double(
                end.getX() - controlRatio2 * diff.getX() + bumpDepthRatio * diff.getY(),
                end.getY() - controlRatio2 * diff.getY() + bumpDepthRatio * diff.getX()
        );

        Point2D controlPoint3 = new Point2D.Double(
                start.getX() + controlRatio1 * diff.getX() - bumpDepthRatio * diff.getY() * 0.4,
                start.getY() + controlRatio1 * diff.getY() - bumpDepthRatio * diff.getX() * 0.4
        );

        Point2D controlPoint4 = new Point2D.Double(
                end.getX() - controlRatio2 * diff.getX() - bumpDepthRatio * diff.getY() * 0.4,
                end.getY() - controlRatio2 * diff.getY() - bumpDepthRatio * diff.getX() * 0.4
        );

        Path2D path = new Path2D.Double();
        path.moveTo(start.getX(), start.getY());
        path.curveTo(controlPoint3.getX(), controlPoint3.getY(), controlPoint3.getX(), controlPoint3.getY(), bumpStart.getX(), bumpStart.getY());
        path.curveTo(controlPoint1a.getX(), controlPoint1a.getY(), controlPoint1b.getX(), controlPoint1b.getY(), bumpMid.getX(), bumpMid.getY());
        path.curveTo(controlPoint2a.getX(), controlPoint2a.getY(), controlPoint2b.getX(), controlPoint2b.getY(), bumpEnd.getX(), bumpEnd.getY());
        path.curveTo(controlPoint4.getX(), controlPoint4.getY(), controlPoint4.getX(), controlPoint4.getY(), end.getX(), end.getY());

        return path;
    }

}
