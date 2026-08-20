package com.pa.puzzle;

import java.awt.Point;
import java.awt.Shape;
import java.util.Objects;

/**
 * Class defining a single puzzle piece.
 * @author Ewelina Gren
 * @version 1.0
 */
public class PuzzlePiece {

    private final int ordinal;
    private final int[] neighbouringOrdinals;

    private final Shape shape;

    /**
     * Creates a puzzle piece and assigns its ordinal, neighbouring ordinals and shape.
     * @param ordinal ordinal of the piece
     * @param neighbouringOrdinals an array of the neighbouring piece's ordinals, cannot be {@code null}
     * @param shape shape of the piece, cannot be {@code null}
     */
    public PuzzlePiece(int ordinal, int[] neighbouringOrdinals, Shape shape) {
        if (shape == null) {
            throw new NullPointerException("Piece's shape cannot be null");
        }

        if (neighbouringOrdinals == null) {
            throw new NullPointerException("Array of neighbouring ordinals cannot be null");
        }

        this.ordinal = ordinal;
        this.neighbouringOrdinals = neighbouringOrdinals;
        this.shape = shape;
    }

    /**
     * Returns the piece's ordinal, describing its position in the sequence of all the pieces.
     * @return the piece's ordinal as an {@code int} type value
     */
    public int getOrdinal() {
        return ordinal;
    }

    /**
     * Returns the ordinals of the neighbouring puzzle pieces
     * which inform about the piece's left, top, right or bottom edge.
     * @return the neighbouring piece's ordinals as an array of {@code int} type values
     */
    public int[] getNeighbouringOrdinals() {
        return neighbouringOrdinals;
    }

    /**
     * Returns the piece's shape.
     * @return the {@code Shape} of the puzzle piece
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Returns the location of the piece's top left corner.
     * @return the corner's location as a {@code Point}
     */
    public Point getNWCorner() {
        return new Point(getShape().getBounds().x, getShape().getBounds().y);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PuzzlePiece other && ordinal == other.ordinal;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ordinal);
    }

    @Override
    public String toString() {
        return String.format("PuzzlePiece{ordinal=%d}", ordinal);
    }

}
