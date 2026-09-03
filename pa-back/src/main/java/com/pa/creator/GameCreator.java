package com.pa.creator;

import com.pa.creator.factory.PuzzleFactory;
import com.pa.puzzle.PuzzleData;

import java.awt.Image;

/**
 * Class used to generate PuzzleData based on parameters.
 * @author Ewelina Gren
 * @version 1.0
 */
public class GameCreator {

    /**
     * Generates PuzzleData using a specific factory instance.
     * @param image an {@code Image} displayed on the puzzle
     * @param rows the number of rows in the puzzle, must be a positive number
     * @param columns the number of columns in the puzzle, must be a positive number
     * @param pieceShape expected shape style of individual puzzle {@code Pieces}
     * @return complete {@code PuzzleData} instance with all the information needed to start a game
     */
    public static PuzzleData generatePuzzleData(Image image, int rows, int columns, PieceShape pieceShape) {
        if (image == null || rows < 1 || columns < 1 || rows * columns <= 1) {
            throw new IllegalArgumentException(String.format("Cannot generate PuzzleData with image='%s', rows=%d, columns=%d.", image, rows, columns));
        }

        PuzzleFactory factory = PuzzleFactory.getFactory(pieceShape);
        return factory.generatePuzzle(rows, columns, image);
    }

}
