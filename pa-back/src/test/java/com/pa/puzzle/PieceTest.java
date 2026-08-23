package com.pa.puzzle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PieceTest {

    @Test
    public void pieceTest() {
        Rectangle shape = new Rectangle(0, 0, 10, 10);

        Piece piece1 = new Piece(23, new Integer[] {2, 3, 5, 6}, shape);
        Piece piece2 = new Piece(23, new Integer[] {2, 3, 5, 6}, shape);
        Piece piece3 = new Piece(25, new Integer[] {2, 3, 5, 6}, shape);

        Assertions.assertNotNull(piece1);
        Assertions.assertEquals(23, piece1.getOrdinal());
        Assertions.assertArrayEquals(new Integer[] {2, 3, 5, 6}, piece1.getNeighbouringOrdinals());
        Assertions.assertEquals(shape, piece1.getShape());
        Assertions.assertEquals(new Point(0, 0), piece1.getNWCorner());
        Assertions.assertEquals(piece2, piece1);
        Assertions.assertTrue(piece1.toString().startsWith("Piece{"));

        Set<Piece> s = new HashSet<>(List.of(piece1, piece2, piece3));
        Assertions.assertEquals(2, s.size());

        Assertions.assertNotEquals(piece3, piece1);
        Assertions.assertNotEquals(piece1, shape);
    }

    @Test
    public void invalidPieceTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Piece(1, null, new Rectangle(10, 10)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Piece(1, new Integer[] {3, 4, 5}, new Rectangle(10, 10)));
        Assertions.assertThrows(NullPointerException.class, () -> new Piece(2, new Integer[] {3, 4, 5, null}, null));
    }

}
