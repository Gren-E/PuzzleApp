package com.pa.creator.generator;

import com.pa.creator.PieceShape;
import com.pa.puzzle.PuzzleData;

import java.awt.Image;
import java.util.function.BiFunction;

/**
 * A generic puzzle generator class to be extended by a specific generator for each distinct puzzle shape style.
 * @author Ewelina Gren
 * @version 1.0
 */
public abstract class PuzzleGenerator {

    /**
     * Returns a specific PuzzleGenerator subclass with the chosen edge type, that's associated with the {@code PieceShape} provided.
     * @param shape a {@code PieceShape} defining the style of the puzzle {@code Pieces}
     */
    public static PuzzleGenerator getGenerator(PieceShape shape) {
        return switch (shape) {
            case RECTANGLE -> new QuadranglePuzzleGenerator(new EdgeGenerator());
            case TRAPEZOID -> new QuadranglePuzzleGenerator(new TrapezoidEdgeGenerator());
            case CLASSIC -> new QuadranglePuzzleGenerator(new ClassicEdgeGenerator());
        };
    }

    /**
     * Splits an {@code Image} into {@code Pieces} of the chosen shape style, with a specified number of rows and columns.
     * @param rows how many rows of {@code Pieces} should there be in the puzzle
     * @param columns how many columns of {@code Pieces} should there be in the puzzle
     * @param image what is the final {@code Image} of the complete puzzle
     * @return a {@code PuzzleData} instance storing information about the puzzle {@code Pieces}
     */
    public abstract PuzzleData generatePuzzle(int rows, int columns, Image image);

    /**
     * Sets the value of each element in an array, according to the provided {@code BiFunction} operation.
     * @param array an array of elements to be overwritten
     * @param function an operation that determines the new value of each element
     * @param <T> the data type of the array's elements
     */
    protected static <T> void setEach(T[][] array, BiFunction<Integer, Integer, T> function) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = function.apply(i, j);
            }
        }
    }

}
