package com.pa.model.data.puzzle;

import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;

public class PuzzleDataBuilder {

    private final Map<Integer, Cluster> clusters;
    private final Map<Integer, Point> piecePositions;

    private int rows;
    private int columns;

    private Image image;

    public PuzzleDataBuilder() {
        clusters = new HashMap<>();
        piecePositions = new HashMap<>();
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void addPiece(int pieceOrdinal, int clusterId, int pieceX, int pieceY, int orientation, Integer[] neighbouringOrdinals, Shape shape) {
        Piece piece = new Piece(pieceOrdinal, neighbouringOrdinals, shape);
        piecePositions.put(pieceOrdinal, new Point(pieceX, pieceY));
        if (!clusters.containsKey(clusterId)) {
            Cluster cluster = new Cluster(clusterId);
            clusters.put(clusterId, cluster);
        }

        clusters.get(clusterId).addPiece(piece);
    }

    public PuzzleData build() {
        if (rows < 1 || columns < 1) {
            throw new IllegalStateException("Rows and columns must be bigger than 0. Rows: " + rows + ", columns: " + columns);
        }
        if (image == null) {
            throw new IllegalStateException("Image cannot be null");
        }

        PuzzleData puzzleData = new PuzzleData();
        puzzleData.loadClusters(clusters.values().toArray(new Cluster[0]), rows, columns);
        for (Cluster cluster : clusters.values()) {
            for (Piece piece : cluster.getPieces()) {
                int ordinal = piece.getOrdinal();
                puzzleData.setPiecePosition(ordinal, piecePositions.get(ordinal).x, piecePositions.get(ordinal).y);
            }
        }

        puzzleData.setImage(image);

        return puzzleData;
    }

}
