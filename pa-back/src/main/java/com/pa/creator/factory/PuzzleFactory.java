package com.pa.creator.factory;

import com.pa.creator.PieceShape;
import com.pa.puzzle.PuzzleData;

import java.awt.Image;
import java.util.function.BiFunction;

public abstract class PuzzleFactory {

    public static PuzzleFactory getFactory(PieceShape shape) {
        return switch (shape) {
            case RECTANGULAR -> new QuadranglePuzzleFactory();
            default -> throw new IllegalArgumentException("Unexpected value: " + shape);
        };
    }

    public abstract PuzzleData generatePuzzle(int rows, int columns, Image image);

    protected static <T> void setEach(T[][] array, BiFunction<Integer, Integer, T> function) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = function.apply(i, j);
            }
        }
    }

}
