package com.pa.creator;

import java.awt.Shape;
import java.awt.geom.Path2D;

public class PieceShapeOutline {

    private Path2D north;
    private Path2D west;
    private Path2D south;
    private Path2D east;

    private final boolean buildClockwise;

    public PieceShapeOutline(boolean buildClockwise) {
        this.buildClockwise = buildClockwise;
    }

    public void setNorth(Path2D north) {
        this.north = north;
    }

    public void setWest(Path2D west) {
        this.west = west;
    }

    public void setSouth(Path2D south) {
        this.south = south;
    }

    public void setEast(Path2D east) {
        this.east = east;
    }

    public Shape createShape() {
        Path2D result = new Path2D.Double();
        if (!buildClockwise) {
            result.append(north, true);
            result.append(west, true);
            result.append(south, true);
            result.append(east, true);
        } else {
            result.append(north, true);
            result.append(east, true);
            result.append(south, true);
            result.append(west, true);
        }

        result.closePath();
        return result;
    }

}
