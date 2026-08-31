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

/**
 * The PuzzleData class keeps track of all the pieces in the puzzle, as well as their positions and completion status.
 * @author Ewelina Gren
 * @version 1.0
 */
public class PuzzleData {

    private static final Logger LOG = LoggerFactory.getLogger(PuzzleData.class);

    private Image image;
    private Piece[][] pieces;
    private Cluster finalisedCluster;
    private final List<Cluster> activeClusters;
    private final Map<Integer, Point> currentPositions;

    /**
     * Creates a new {@code PuzzleData} instance with no {@code Pieces} and no {@code Image}.
     */
    public PuzzleData() {
        this.currentPositions = new HashMap<>();
        this.activeClusters = new ArrayList<>();
    }

    /**
     * Sets the puzzle's {@code Image}.
     * @param image an {@code Image} to be assigned to the puzzle
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Returns the puzzle's {@code Image}.
     * @return the {@code Image} assigned to the puzzle or {@code null}
     */
    public Image getImage() {
        return image;
    }

    /**
     * Splits the puzzle into specified array of {@code Pieces}.
     * @param pieces an array of puzzle {@code Pieces}; the number of its rows and columns has to be consistent, otherwise an exception is thrown
     */
    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
        currentPositions.clear();
        finalisedCluster = new Cluster(-1);

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

    /**
     * Returns an array of all the {@code Pieces} in the puzzle.
     * @return an array of puzzle {@code Pieces}
     */
    public Piece[][] getPieces() {
        return pieces;
    }

    /**
     * Returns the individual puzzle according to the specified ordinal.
     * @param ordinal an int value specifying which {@code Piece} is to be returned
     * @return a puzzle {@code Piece} associated with the provided ordinal
     */
    public Piece getPiece(int ordinal) {
        return Arrays.stream(pieces).flatMap(Arrays::stream)
                .filter(piece -> piece.getOrdinal() == ordinal).findAny().orElse(null);
    }

    /**
     * Sets the {@code Piece's} position in the game.
     * @param ordinal an int value associated with the specific {@code Piece}
     * @param x the X coordinate of the {@code Piece's} position
     * @param y the Y coordinate of the {@code Piece's} position
     */
    public void setPiecePosition(int ordinal, int x, int y) {
        currentPositions.put(ordinal, new Point(x, y));
        LOG.debug("Position of piece {} changed to {}x{}.", ordinal, x, y);
    }

    /**
     * Returns the {@code Piece's} current position in the game
     * @param ordinal an int value associated with the specific {@code Piece}
     * @return the {@code Piece's} current position as a {@code Point}
     */
    public Point getPiecePosition(int ordinal) {
        return currentPositions.get(ordinal);
    }

    /**
     * Returns an array of all the groups of joined {@code Pieces} in the game, as well as the individual {@code Pieces} which haven't been joined yet.
     * @return an array of all the {@code Clusters} of {@code Pieces} in the puzzle
     */
    public Cluster[] getClusters() {
        List<Cluster> clusters = new ArrayList<>(activeClusters);
        clusters.add(finalisedCluster);
        return clusters.toArray(new Cluster[0]);
    }

    /**
     * Returns an array of all the {@code Clusters} which still need to be moved to their right position and therefore haven't been finalised yet.
     * @return an array of all active {@code Clusters}
     */
    public Cluster[] getActiveClusters() {
        return activeClusters.toArray(new Cluster[0]);
    }

    /**
     * Returns a specific {@code Cluster} that the {@code Piece} belongs to.
     * @param piece a {@code Piece} to be found in the {@code Cluster}
     * @return a {@code Cluster} containing the {@code Piece} provided
     */
    public Cluster getParentCluster(Piece piece) {
        return Arrays.stream(getClusters()).filter(cluster -> cluster.containsPiece(piece)).findAny().orElse(null);
    }

    /**
     * Returns the current position of the specified {@code Cluster} in the game.
     * @param cluster the {@code Cluster} to be located
     * @return the {@code Cluster's} position as a {@code Point} defining its top left corner's coordinates
     */
    public Point getClusterPosition(Cluster cluster) {
        Piece[] pieces = cluster.getPieces();
        int x = Arrays.stream(pieces).map(p -> getPiecePosition(p.getOrdinal()).x).mapToInt(n -> n).min().orElse(0);
        int y = Arrays.stream(pieces).map(p -> getPiecePosition(p.getOrdinal()).y).mapToInt(n -> n).min().orElse(0);
        return new Point(x, y);
    }

    /**
     * Sets the {@code Cluster's} position in the game.
     * @param cluster the {@code Cluster} to be moved
     * @param x the X coordinate of the {@code Cluster's} new position
     * @param y the Y coordinate of the {@code Cluster's} new position
     */
    public void changeClusterPosition(Cluster cluster, int x, int y) {
        Piece[] pieces = cluster.getPieces();
        Point targetNWCorner = cluster.getNWCorner();

        Point difference = new Point(x - targetNWCorner.x, y - targetNWCorner.y);
        for (Piece piece : pieces) {
            Point pieceNWCorner = piece.getNWCorner();
            setPiecePosition(piece.getOrdinal(), pieceNWCorner.x + difference.x , pieceNWCorner.y + difference.y);
        }
    }

    /**
     * Counts all the rows of {@code Pieces} in the puzzle
     * @return the number of rows in the puzzle
     */
    public int countRows() {
        return pieces != null ? pieces.length : 0;
    }

    /**
     * Counts all the columns of {@code Pieces} in the puzzle
     * @return the number of columns in the puzzle
     */
    public int countColumns() {
        return pieces != null ? pieces[0].length : 0;
    }

    /**
     * Counts all the {@code Pieces} in the puzzle
     * @return the number of {@code Pieces} in the puzzle
     */
    public int countPieces() {
        return countRows() * countColumns();
    }

    /**
     * Counts all the {@code Pieces} in the puzzle that reached their intended position.
     * @return the number of finalised {@code Pieces} in the puzzle
     */
    public int countFinalisedPieces() {
        return finalisedCluster.countPieces();
    }

    /**
     * Joins two {@code Clusters} together.
     * @param mainCluster the main {@code Cluster}
     * @param clusterToBeMerged a {@code Cluster} to be added to the main one
     */
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
        changeClusterPosition(mainCluster, clusterPosition.x, clusterPosition.y);
    }

    /**
     * Removes an active {@code Cluster} from the list after it has been integrated into another one.
     * @param cluster a {@code Cluster} to be removed
     */
    public void removeCluster(Cluster cluster) {
        activeClusters.remove(cluster);
        LOG.debug("{} removed from data.", cluster);
    }

    /**
     * Finalises a {@code Cluster} by adding it to the {@code Cluster} of all finalised {@code Pieces}, once it reaches it's intended position.
     * @param cluster a {@code Cluster} to be finalised
     */
    public void finalise(Cluster cluster) {
        mergeClusters(finalisedCluster, cluster);
    }

    /**
     * Checks whether the specified {@code Piece} has assumed its intended position and therefore has been finalised.
     * @param piece a puzzle {@code Piece} to be checked
     * @return {@code true} if the {@code Piece} has been finalised, {@code false} otherwise
     */
    public boolean isFinalised(Piece piece) {
        return finalisedCluster.containsPiece(piece);
    }

    @Override
    public String toString() {
        return String.format("PuzzleData{rows=%d, columns=%d}", countRows(), countColumns());
    }

}
