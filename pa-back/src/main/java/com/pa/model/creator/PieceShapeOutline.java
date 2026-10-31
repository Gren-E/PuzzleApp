package com.pa.model.creator;

import java.awt.Shape;
import java.awt.geom.Path2D;

/**
 * A builder class for creating the shape of an individual puzzle piece.
 * @author Ewelina Gren
 * @version 1.0
 */
public class PieceShapeOutline {

    private Path2D north;
    private Path2D west;
    private Path2D south;
    private Path2D east;

    private final boolean buildClockwise;

    /**
     * Creates a blank instance of a piece shape outline. It needs all four edges to be added before the piece shape can be created.
     * The outline can be built clockwise - starting from the top left corner, or counter-clockwise, starting from the top right corner.
     * @param buildClockwise should the outline's path run clockwise or counter-clockwise
     */
    public PieceShapeOutline(boolean buildClockwise) {
        this.buildClockwise = buildClockwise;
    }

    /**
     * Sets the outline's top edge.
     * @param north a {@code Path2D} instance representing the outline's top edge
     */
    public void setNorth(Path2D north) {
        this.north = north;
    }

    /**
     * Sets the outline's left edge.
     * @param west a {@code Path2D} instance representing the outline's left edge
     */
    public void setWest(Path2D west) {
        this.west = west;
    }

    /**
     * Sets the outline's bottom edge.
     * @param south a {@code Path2D} instance representing the outline's bottom edge
     */
    public void setSouth(Path2D south) {
        this.south = south;
    }

    /**
     * Sets the outline's right edge.
     * @param east a {@code Path2D} instance representing the outline's right edge
     */
    public void setEast(Path2D east) {
        this.east = east;
    }

    /**
     * Creates the piece's shape by adding all four edges of the outline.
     * @return the {@code Shape} of the puzzle piece
     */
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
