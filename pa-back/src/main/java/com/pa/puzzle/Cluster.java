package com.pa.puzzle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class defining a cluster of puzzle pieces.
 * @author Ewelina Gren
 * @version 1.0
 */
public class Cluster {

    private static final Logger LOG = LoggerFactory.getLogger(Cluster.class);

    private final int id;
    private final Set<Piece> pieces;
    private final Area consolidatedShape;

    /**
     * Creates a new empty {@code Cluster} that doesn't contain any {@code Pieces}.
     * @param id the Cluster's id
     */
    public Cluster(int id) {
        this.id = id;
        pieces = new HashSet<>();
        consolidatedShape = new Area();
    }

    /**
     * Adds a {@code Piece} to the {@code Cluster}.
     * @param piece a {@code Piece} to be added
     */
    public void addPiece(Piece piece) {
        if (pieces.contains(piece)) {
            LOG.warn("{} already contains the piece: {}", this, piece);
            return;
        }

        pieces.add(piece);
        consolidatedShape.add(new Area(piece.getShape()));
        LOG.info("{} added to {}.", piece, this);
    }

    /**
     * Returns an array of all the {@code Pieces} it the {@code Cluster}.
     * @return an array of {@code Pieces}
     */
    public Piece[] getPieces() {
        return pieces.toArray(new Piece[0]);
    }

    /**
     * Checks if the {@code Cluster} contains the given {@code Piece}.
     * @param piece {@code Piece} to be verified
     * @return true if the {@code Piece} was found in the {@code Cluster}, false otherwise
     */
    public boolean containsPiece(Piece piece) {
        return pieces.contains(piece);
    }

    /**
     * Returns the total number of {@code Pieces} in the {@code Cluster}.
     * @return the total number of {@code Pieces}
     */
    public int countPieces() {
        return pieces.size();
    }

    /**
     * Returns the shape of the {@code Cluster} consisting of all the joined {@code Pieces}.
     * @return the consolidated {@code Shape} of the {@code Cluster}
     */
    public Shape getConsolidatedShape() {
        return consolidatedShape;
    }

    /**
     * Returns the location of the cluster's top left corner.
     * @return the corner's location as a {@code Point}
     */
    public Point getNWCorner() {
        Rectangle bounds = getConsolidatedShape().getBounds();
        return new Point(bounds.x, bounds.y);
    }

    /**
     * Returns the ordinals of all the {@code Pieces} which share an edge with the {@code Cluster} and therefore can be joined.
     * @return the ordinals of all the neighbouring {@code Pieces}
     */
    public int[] getNeighbouringPiecesOrdinals() {
        Set<Integer> neighbouringPieces = new HashSet<>();
        for (Piece piece : pieces) {
            neighbouringPieces.addAll(Arrays.asList(piece.getNeighbouringOrdinals()));
        }

        for (Piece piece : pieces) {
            neighbouringPieces.remove(piece.getOrdinal());
        }

        return neighbouringPieces.stream().filter(Objects::nonNull).mapToInt(i -> i).toArray();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Cluster other && id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Cluster{id=%d, pieces=%d}", id, countPieces());
    }

}
