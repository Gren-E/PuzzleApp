package com.pa.creator;

import com.pa.puzzle.PuzzleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class GameCreatorTest {

    @Test
    public void invalidGameCreatorTest() {
        Image image = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> GameCreator.generatePuzzleData(null, 2, 2, PieceShape.RECTANGULAR));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> GameCreator.generatePuzzleData(image, 0, 2, PieceShape.RECTANGULAR));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> GameCreator.generatePuzzleData(image, 2, -2, PieceShape.RECTANGULAR));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> GameCreator.generatePuzzleData(image, 1, 1, PieceShape.RECTANGULAR));
    }

    @Test
    public void validGameCreatorTest() {
        Image image = new BufferedImage(1000, 1000, BufferedImage.TYPE_3BYTE_BGR);

        PuzzleData puzzleData = GameCreator.generatePuzzleData(image, 10, 20, PieceShape.RECTANGULAR);
        Assertions.assertEquals(10, puzzleData.countRows());
        Assertions.assertEquals(20, puzzleData.countColumns());
    }

}
