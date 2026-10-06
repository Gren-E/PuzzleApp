package com.pa.model.executors;

import com.pa.model.puzzle.Cluster;
import com.pa.model.puzzle.Piece;
import com.pa.model.puzzle.PuzzleData;
import com.pa.model.util.PositionUtil;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PieceMovementExecutor {

    private final PuzzleData puzzleData;
    private int mergeRange;

    public PieceMovementExecutor(PuzzleData puzzleData) {
        this.puzzleData = puzzleData;
        mergeRange = 10;
    }

    public void handlePositionChange(Cluster cluster, Point newPosition) {
        puzzleData.changeClusterPosition(cluster, newPosition.x, newPosition.y);

        if (canClusterBeFinalised(cluster)) {
            puzzleData.finalise(cluster);
            return;
        }

        tryMerging(cluster);
    }

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

    public void setMergeRange(int mergeRange) {
        this.mergeRange = mergeRange;
    }

    private boolean canClusterBeFinalised(Cluster cluster) {
        Piece piece = cluster.getPieces()[0];
        Point currentPosition = puzzleData.getPiecePosition(piece.getOrdinal());
        return PositionUtil.equalsWithinTolerance(currentPosition, piece.getNWCorner(), mergeRange);
    }

    private Piece findPieceAvailableToMerge(Cluster currentCluster, Piece neighbouringPiece) {
        int ordinal = Arrays.stream(neighbouringPiece.getNeighbouringOrdinals()).filter(currentCluster::containsPiece)
                .findFirst().orElseThrow(() -> new IllegalStateException("No pieces available for merge."));
        return puzzleData.getPiece(ordinal);
    }

}
