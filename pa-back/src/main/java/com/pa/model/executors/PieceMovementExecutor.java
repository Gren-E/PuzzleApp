package com.pa.model.executors;

import com.pa.model.puzzle.Cluster;
import com.pa.model.puzzle.Piece;
import com.pa.model.puzzle.PuzzleData;
import com.pa.model.util.PositionUtil;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Executor class responsible for movement, merging and finalising of puzzle {@code Pieces}.
 * @author Ewelina Gren
 * @version 1.0
 */
public class PieceMovementExecutor {

    private final PuzzleData puzzleData;
    private final int mergeRange;

    /**
     * Creates an instance of the executor with assigned {@code PuzzleData}
     * @param puzzleData to be assigned to the executor
     */
    public PieceMovementExecutor(PuzzleData puzzleData) {
        this.puzzleData = puzzleData;
        mergeRange = 10;
    }

    /**
     * Changes the position of a cluster and finalises it or merges it with another cluster, if the new position allows it.
     * @param cluster a {@code Cluster} of puzzle {@code Pieces} to be moved
     * @param newPosition a {@code Point} describing the new position of the {@code Cluster's} top left corner
     */
    public void handlePositionChange(Cluster cluster, Point newPosition) {
        puzzleData.changeClusterPosition(cluster, newPosition.x, newPosition.y);

        if (canClusterBeFinalised(cluster)) {
            puzzleData.finalise(cluster);
            return;
        }

        tryMerging(cluster);
    }

    /**
     * Tries to merge the current {@code Cluster} with any available {@code Pieces}
     * by checking if the current positions of the neighbouring Pieces are withing the merge range
     * of the Pieces within the Cluster that match them.
     * @param cluster the {@code Cluster} that is currently being moved
     */
    public void tryMerging(Cluster cluster) {
        Set<Cluster> checkedClusters = new HashSet<>();

        int[] clusterNeighbouringOrdinals = cluster.getNeighbouringPiecesOrdinals();
        for (int neighbouringOrdinal : clusterNeighbouringOrdinals) {
            Piece neighbouringPiece = puzzleData.getPiece(neighbouringOrdinal);
            Cluster neighbouringCluster = puzzleData.getParentCluster(neighbouringPiece);

            if (!checkedClusters.add(neighbouringCluster)) {
                continue;
            }

            Point neighbourTargetPosition = neighbouringPiece.getNWCorner();
            Point neighbourCurrentPosition = puzzleData.getPiecePosition(neighbouringOrdinal);

            Piece availablePiece = findPieceAvailableToMerge(cluster, neighbouringPiece);
            Point availablePieceTargetPosition = availablePiece.getNWCorner();
            Point availablePieceCurrentPosition = puzzleData.getPiecePosition(availablePiece.getOrdinal());

            Point targetDiff = PositionUtil.subtractPoint(neighbourTargetPosition, availablePieceTargetPosition);
            Point currentDiff = PositionUtil.subtractPoint(neighbourCurrentPosition, availablePieceCurrentPosition);

            if (PositionUtil.equalsWithinTolerance(targetDiff, currentDiff, mergeRange)) {
                puzzleData.mergeClusters(neighbouringCluster, cluster);
                return;
            }
        }
    }

    /**
     * Checks if the {@code Cluster's} position is close enough to its target position in the puzzle, allowing the Cluster to be finalised.
     * @param cluster the {@code Cluster} to be checked
     * @return {@code true} if the {@code Cluster} can be finalised, {@code false} otherwise
     */
    private boolean canClusterBeFinalised(Cluster cluster) {
        Piece piece = cluster.getPieces()[0];
        Point currentPosition = puzzleData.getPiecePosition(piece.getOrdinal());
        return PositionUtil.equalsWithinTolerance(currentPosition, piece.getNWCorner(), mergeRange);
    }

    /**
     * Finds a {@code Piece} within the current {@code Cluster} that can be joined to the specific neighbouring Piece.
     * @param currentCluster the {@code Cluster} that is currently being moved
     * @param neighbouringPiece a {@code Piece} available for merging with the current {@code Cluster}
     * @return the first available {@code Piece} withing the current {@code Cluster} that can be joined with the specific neighbouring Piece
     */
    private Piece findPieceAvailableToMerge(Cluster currentCluster, Piece neighbouringPiece) {
        int ordinal = Arrays.stream(neighbouringPiece.getNeighbouringOrdinals())
                .filter(o -> o != null && currentCluster.containsPiece(o))
                .findFirst().orElseThrow(() -> new IllegalStateException("No pieces available for merge."));
        return puzzleData.getPiece(ordinal);
    }

}
