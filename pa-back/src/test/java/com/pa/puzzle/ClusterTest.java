package com.pa.puzzle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClusterTest {

    @Test
    public void emptyClusterTest() {
        Cluster cluster = new Cluster(1);

        Assertions.assertArrayEquals(new Piece[0], cluster.getPieces());
        Assertions.assertFalse(cluster.containsPiece(new Piece(1, new Integer[] {null, 2, 3, 4}, new Rectangle(10, 20))));
        Assertions.assertEquals(0, cluster.countPieces());
        Assertions.assertNotNull(cluster.getConsolidatedShape());
        Assertions.assertEquals(0, cluster.getConsolidatedShape().getBounds().getWidth());
        Assertions.assertEquals(0, cluster.getConsolidatedShape().getBounds().getHeight());
        Assertions.assertArrayEquals(new int[0], cluster.getNeighbouringPiecesOrdinals());
    }

    @Test
    public void onePieceClusterTest() {
        Cluster cluster = new Cluster(1);
        Piece piece = new Piece(1, new Integer[] {null, 2, 3, 4}, new Rectangle(10, 20));
        cluster.addPiece(piece);
        cluster.addPiece(piece);

        Assertions.assertArrayEquals(new Piece[] {piece}, cluster.getPieces());
        Assertions.assertTrue(cluster.containsPiece(piece));
        Assertions.assertEquals(1, cluster.countPieces());
        Assertions.assertNotNull(cluster.getConsolidatedShape());
        Assertions.assertEquals(10, cluster.getConsolidatedShape().getBounds().getWidth());
        Assertions.assertEquals(20, cluster.getConsolidatedShape().getBounds().getHeight());
        Assertions.assertArrayEquals(new int[] {2, 3, 4}, cluster.getNeighbouringPiecesOrdinals());
    }

    @Test
    public void compareClustersTest() {
        Cluster cluster1 = new Cluster(1);
        Cluster cluster2 = new Cluster(1);
        Cluster cluster3 = new Cluster(3);

        Assertions.assertEquals(cluster1, cluster2);
        Assertions.assertNotEquals(cluster1, cluster3);
        Assertions.assertFalse(cluster1.equals(null));

        Set<Cluster> clusterSet = new HashSet<>(List.of(cluster1, cluster2, cluster3));

        Assertions.assertEquals(2, clusterSet.size());
    }

    @Test
    public void cornerTest() {
        Cluster cluster = new Cluster(1);

        Assertions.assertEquals(new Point(0, 0), cluster.getNWCorner());

        cluster.addPiece(new Piece(1, new Integer[] {3, 4, 5, 6}, new Rectangle(10, 20, 10, 10)));
        Assertions.assertEquals(new Point(10, 20), cluster.getNWCorner());

        cluster.addPiece(new Piece(12, new Integer[] {23, 24, 25, 26}, new Rectangle(50, 10, 10, 10)));
        Assertions.assertEquals(new Point(10, 10), cluster.getNWCorner());
    }

}
