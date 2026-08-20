package com.pa.puzzle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PuzzlePieceTest {

    @Test
    public void puzzlePieceTest() {
        Rectangle shape = new Rectangle(0, 0, 10, 10);

        PuzzlePiece piece1 = new PuzzlePiece(23, new int[] {2, 3, 5, 6}, shape);
        PuzzlePiece piece2 = new PuzzlePiece(23, new int[] {2, 3, 5, 6}, shape);
        PuzzlePiece piece3 = new PuzzlePiece(25, new int[] {2, 3, 5, 6}, shape);

        Assertions.assertNotNull(piece1);
        Assertions.assertEquals(23, piece1.getOrdinal());
        Assertions.assertArrayEquals(new int[] {2, 3, 5, 6}, piece1.getNeighbouringOrdinals());
        Assertions.assertEquals(shape, piece1.getShape());
        Assertions.assertEquals(new Point(0, 0), piece1.getNWCorner());
        Assertions.assertEquals(piece2, piece1);
        Assertions.assertTrue(piece1.toString().startsWith("PuzzlePiece{"));

        Set<PuzzlePiece> s = new HashSet<>(List.of(piece1, piece2, piece3));
        Assertions.assertEquals(2, s.size());

        Assertions.assertNotEquals(piece3, piece1);
        Assertions.assertNotEquals(piece1, shape);
    }

    @Test
    public void invalidPuzzlePieceTest() {
        Assertions.assertThrows(NullPointerException.class, () -> new PuzzlePiece(1, null, new Rectangle(10, 10)));
        Assertions.assertThrows(NullPointerException.class, () -> new PuzzlePiece(2, new int[] {3, 4, 5}, null));
    }

}
