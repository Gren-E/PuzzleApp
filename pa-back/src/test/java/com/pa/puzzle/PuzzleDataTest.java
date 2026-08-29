package com.pa.puzzle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Arrays;

public class PuzzleDataTest {

    private static Image image;
    private static Piece[][] pieces;

    @BeforeAll
    public static void setUp() throws IOException {
        image = ImageIO.read(PuzzleDataTest.class.getResource("TestImage1.jpg"));

        pieces = new Piece[][] {
                {
                        new Piece(1, new Integer[]{null, null, 3, 2}, new Rectangle(0, 0, 10, 10)),
                        new Piece(2, new Integer[]{null, 1, 4, null}, new Rectangle(10, 0, 10, 10))
                },
                {
                        new Piece(3, new Integer[]{1, null, 5, 4}, new Rectangle(0, 10, 10, 10)),
                        new Piece(4, new Integer[]{2, 3, 6, null}, new Rectangle(10, 10, 10, 10))
                },
                {
                        new Piece(5, new Integer[]{3, null, null, 6}, new Rectangle(0, 20, 10, 10)),
                        new Piece(6, new Integer[]{4, 5, null, null}, new Rectangle(10, 20, 10, 10))
                }
        };
    }

    @Test
    public void puzzleDataTest() {
        PuzzleData puzzleData = new PuzzleData();
        puzzleData.setImage(image);

        Assertions.assertEquals(0, puzzleData.countColumns());
        Assertions.assertEquals(0, puzzleData.countRows());

        puzzleData.setPieces(pieces);

        Assertions.assertEquals(image, puzzleData.getImage());
        Assertions.assertArrayEquals(pieces, puzzleData.getPieces());
        Assertions.assertEquals(2, puzzleData.countColumns());
        Assertions.assertEquals(3, puzzleData.countRows());
        Assertions.assertEquals(6, puzzleData.countPieces());
        Assertions.assertEquals(0, puzzleData.countFinalizedPieces());
        Assertions.assertEquals(7, puzzleData.getClusters().length);
        Assertions.assertEquals(6, puzzleData.getActiveClusters().length);
        Assertions.assertArrayEquals(new Integer[]{null, null, 3, 2}, puzzleData.getPiece(1).getNeighbouringOrdinals());
        Assertions.assertNull(puzzleData.getPiece(7));
        Assertions.assertTrue(puzzleData.toString().startsWith("PuzzleData{"));
    }

    @Test
    public void invalidPiecesTest() {
        PuzzleData puzzleData = new PuzzleData();
        Piece[][] invalidPieces = Arrays.copyOf(pieces, 4);
        invalidPieces[3] = new Piece[] {new Piece(7, new Integer[]{5, null, null, null}, new Rectangle(0, 30, 10, 10))};

        Assertions.assertThrows(IllegalArgumentException.class, () -> puzzleData.setPieces(invalidPieces));
    }

    @Test
    public void piecePositionTest() {
        PuzzleData puzzleData = new PuzzleData();
        puzzleData.setPieces(pieces);
        puzzleData.setPiecePosition(1, 50, 30);

        Assertions.assertEquals(new Point(50, 30), puzzleData.getPiecePosition(1));
    }

    @Test
    public void clustersTest() {
        PuzzleData puzzleData = new PuzzleData();
        puzzleData.setPieces(pieces);
        Cluster cluster1 = puzzleData.getParentCluster(pieces[0][0]);
        Cluster cluster2 = puzzleData.getParentCluster(pieces[0][1]);
        puzzleData.mergeClusters(cluster1, cluster2);

        Assertions.assertThrows(IllegalArgumentException.class, () -> puzzleData.mergeClusters(cluster1, cluster1));
        Assertions.assertEquals(cluster1, puzzleData.getParentCluster(pieces[0][1]));
    }

    @Test
    public void finalizingTest() {
        PuzzleData puzzleData = new PuzzleData();
        puzzleData.setPieces(pieces);
        Cluster cluster1 = puzzleData.getParentCluster(pieces[0][0]);

        puzzleData.finalize(cluster1);
        Assertions.assertTrue(puzzleData.isFinalized(pieces[0][0]));
        Assertions.assertEquals(1, puzzleData.countFinalizedPieces());
        Assertions.assertNotEquals(cluster1, puzzleData.getParentCluster(pieces[0][0]));
    }

}
