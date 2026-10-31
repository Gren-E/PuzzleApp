package com.pa.model.executors;

import com.pa.model.puzzle.Cluster;
import com.pa.model.puzzle.Piece;
import com.pa.model.puzzle.PuzzleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.Rectangle;

public class PieceMovementExecutorTest {

    private PieceMovementExecutor executor;
    private PuzzleData puzzleData;

    @BeforeEach
    public void setUp() {
        Piece[][] pieces = new Piece[][] {
                {
                        new Piece(1, new Integer[]{null, null, 3, 2}, new Rectangle(0, 0, 10, 10)),
                        new Piece(2, new Integer[]{null, 1, 4, null}, new Rectangle(10, 0, 10, 10))
                },
                {
                        new Piece(3, new Integer[]{1, null, null, 4}, new Rectangle(0, 10, 10, 10)),
                        new Piece(4, new Integer[]{2, 3, null, null}, new Rectangle(10, 10, 10, 10))
                }
        };

        puzzleData = new PuzzleData();
        puzzleData.setPieces(pieces);
        puzzleData.setPiecePosition(1, 10, 10);
        puzzleData.setPiecePosition(2, 20, 20);
        puzzleData.setPiecePosition(3, 30, 30);
        puzzleData.setPiecePosition(4, 40, 40);
        puzzleData.setPiecePosition(5, 50, 50);
        puzzleData.setPiecePosition(6, 60, 60);

        executor = new PieceMovementExecutor(puzzleData);
    }

    @Test
    public void clusterFinalisationTest() {
        Cluster cluster1 = puzzleData.getParentCluster(puzzleData.getPiece(1));
        executor.handlePositionChange(cluster1, new Point(0, 0));

        Assertions.assertTrue(puzzleData.isFinalised(puzzleData.getPiece(1)));
        Assertions.assertEquals(3, puzzleData.getActiveClusters().length);
        Assertions.assertEquals(1, puzzleData.countFinalisedPieces());
        Assertions.assertEquals(0, cluster1.countPieces());
    }

    @Test
    public void clusterMergingTest() {
        Cluster cluster1 = puzzleData.getParentCluster(puzzleData.getPiece(1));
        Cluster cluster3 = puzzleData.getParentCluster(puzzleData.getPiece(3));
        executor.handlePositionChange(cluster1, new Point(31, 19));

        Assertions.assertFalse(puzzleData.isFinalised(puzzleData.getPiece(1)));
        Assertions.assertEquals(3, puzzleData.getActiveClusters().length);
        Assertions.assertEquals(0, puzzleData.countFinalisedPieces());
        Assertions.assertEquals(0, cluster1.countPieces());
        Assertions.assertTrue(cluster3.containsPiece(1));
        Assertions.assertTrue(cluster3.containsPiece(3));
    }

    @Test
    public void clusterMultipleMergingTest() {
        Cluster cluster1 = puzzleData.getParentCluster(puzzleData.getPiece(1));
        Cluster cluster2 = puzzleData.getParentCluster(puzzleData.getPiece(2));
        Cluster cluster3 = puzzleData.getParentCluster(puzzleData.getPiece(3));
        Cluster cluster4 = puzzleData.getParentCluster(puzzleData.getPiece(4));
        executor.handlePositionChange(cluster1, new Point(31, 19));
        executor.handlePositionChange(cluster2, new Point(39, 20));

        Assertions.assertEquals(2, puzzleData.getActiveClusters().length);

        executor.handlePositionChange(cluster4, new Point(70, 0));

        Assertions.assertEquals(2, puzzleData.getActiveClusters().length);

        executor.handlePositionChange(cluster4, new Point(41, 29));

        Assertions.assertEquals(1, puzzleData.getActiveClusters().length);
        Assertions.assertEquals(0, puzzleData.countFinalisedPieces());

        executor.handlePositionChange(cluster3, new Point(0, 0));
        Assertions.assertEquals(0, puzzleData.getActiveClusters().length);
        Assertions.assertEquals(4, puzzleData.countFinalisedPieces());
    }

    @Test
    public void clusterChangingPositionWithoutMergeTest() {
        Cluster cluster4 = puzzleData.getParentCluster(puzzleData.getPiece(4));
        executor.handlePositionChange(cluster4, new Point(130, 20));

        Assertions.assertFalse(puzzleData.isFinalised(puzzleData.getPiece(4)));
        Assertions.assertEquals(4, puzzleData.getActiveClusters().length);
        Assertions.assertEquals(0, puzzleData.countFinalisedPieces());
        Assertions.assertEquals(1, cluster4.countPieces());
        Assertions.assertTrue(cluster4.containsPiece(4));
    }

}
