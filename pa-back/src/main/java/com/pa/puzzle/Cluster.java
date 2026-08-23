package com.pa.puzzle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Shape;
import java.awt.geom.Area;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cluster {

    private static final Logger LOG = LoggerFactory.getLogger(Cluster.class);

    private final int id;
    private final Set<Piece> pieces;
    private final Area consolidatedShape;

    public Cluster(int id) {
        this.id = id;
        pieces = new HashSet<>();
        consolidatedShape = new Area();
    }

    public void addPiece(Piece piece) {
        if (pieces.contains(piece)) {
            LOG.warn("{} already contains the piece: {}", this, piece);
            return;
        }

        pieces.add(piece);
        consolidatedShape.add(new Area(piece.getShape()));
        LOG.info("{} added to {}.", piece, this);
    }

    public Piece[] getPieces() {
        return pieces.toArray(new Piece[0]);
    }

    public int countPieces() {
        return pieces.size();
    }

    public Shape getConsolidatedShape() {
        return consolidatedShape;
    }

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
