package com.pa.puzzle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PuzzleData {

    private static final Logger LOG = LoggerFactory.getLogger(PuzzleData.class);

    private Image image;
    private Piece[][] pieces;
    private Cluster finalizedCluster;
    private final List<Cluster> activeClusters;
    private final Map<Integer, Point> currentPositions;

    public PuzzleData() {
        this.currentPositions = new HashMap<>();
        this.activeClusters = new ArrayList<>();
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
        currentPositions.clear();
        finalizedCluster = new Cluster(-1);

        for (int row = 0; row < countRows(); row++) {
            if (pieces[row].length != countColumns()) {
                throw new IllegalArgumentException("PuzzleData class does not supported puzzles with inconsistent number of columns");
            }

            for (int column = 0; column < countColumns(); column++) {
                Piece piece = pieces[row][column];
                currentPositions.put(piece.getOrdinal(), piece.getNWCorner());

                Cluster cluster = new Cluster(piece.getOrdinal());
                cluster.addPiece(piece);
                activeClusters.add(cluster);
            }
        }
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public Piece getPiece(int ordinal) {
        return Arrays.stream(pieces).flatMap(Arrays::stream)
                .filter(piece -> piece.getOrdinal() == ordinal).findAny().orElse(null);
    }

    public void setPiecePosition(int pieceOrdinal, int x, int y) {
        currentPositions.put(pieceOrdinal, new Point(x, y));
        LOG.debug("Position of piece {} changed to {}x{}.", pieceOrdinal, x, y);
    }

    public Point getPiecePosition(int ordinal) {
        return currentPositions.get(ordinal);
    }

    public Cluster[] getClusters() {
        List<Cluster> clusters = new ArrayList<>(activeClusters);
        clusters.add(finalizedCluster);
        return clusters.toArray(new Cluster[0]);
    }

    public Cluster[] getActiveClusters() {
        return activeClusters.toArray(new Cluster[0]);
    }

    public Cluster getParentCluster(Piece piece) {
        return Arrays.stream(getClusters()).filter(cluster -> cluster.containsPiece(piece)).findAny().orElse(null);
    }

    public Point getClusterPosition(Cluster cluster) {
        Piece[] pieces = cluster.getPieces();
        int x = Arrays.stream(pieces).map(p -> getPiecePosition(p.getOrdinal()).x).mapToInt(n -> n).min().orElse(0);
        int y = Arrays.stream(pieces).map(p -> getPiecePosition(p.getOrdinal()).y).mapToInt(n -> n).min().orElse(0);
        return new Point(x, y);
    }

    public void changeClusterPosition(Cluster cluster, Point clusterPosition) {
        Piece[] pieces = cluster.getPieces();
        Point targetNWCorner = cluster.getNWCorner();

        Point difference = new Point(clusterPosition.x - targetNWCorner.x, clusterPosition.y - targetNWCorner.y);
        for (Piece piece : pieces) {
            Point pieceNWCorner = piece.getNWCorner();
            setPiecePosition(piece.getOrdinal(), pieceNWCorner.x + difference.x , pieceNWCorner.y + difference.y);
        }
    }

    public int countRows() {
        return pieces != null ? pieces.length : 0;
    }

    public int countColumns() {
        return pieces != null ? pieces[0].length : 0;
    }

    public int countPieces() {
        return countRows() * countColumns();
    }

    public int countFinalizedPieces() {
        return finalizedCluster.countPieces();
    }

    public void mergeClusters(Cluster mainCluster, Cluster clusterToBeMerged) {
        if (Objects.equals(mainCluster, clusterToBeMerged)) {
            throw new IllegalArgumentException("Cannot merge a cluster with itself.");
        }

        for (Piece piece : clusterToBeMerged.getPieces()) {
            mainCluster.addPiece(piece);
        }

        LOG.debug("{} with {} piece(-s) was merged into {}.", clusterToBeMerged, clusterToBeMerged.countPieces(), mainCluster);
        removeCluster(clusterToBeMerged);

        Point clusterPosition = getClusterPosition(mainCluster);
        changeClusterPosition(mainCluster, clusterPosition);
    }

    public void removeCluster(Cluster cluster) {
        activeClusters.remove(cluster);
        LOG.debug("{} removed from data.", cluster);
    }

    public void finalize(Cluster cluster) {
        mergeClusters(finalizedCluster, cluster);
    }

    public boolean isFinalized(Piece piece) {
        return finalizedCluster.containsPiece(piece);
    }

    @Override
    public String toString() {
        return String.format("PuzzleData{rows=%d, columns=%d}", countRows(), countColumns());
    }

}
