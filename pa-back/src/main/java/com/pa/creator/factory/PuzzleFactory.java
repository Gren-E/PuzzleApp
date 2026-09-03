package com.pa.creator.factory;

import com.pa.creator.PieceShape;
import com.pa.puzzle.PuzzleData;

import java.awt.Image;

public abstract class PuzzleFactory {

    public static PuzzleFactory getFactory(PieceShape shape) {
        return null; //TODO
    }

    public abstract PuzzleData generatePuzzle(int rows, int columns, Image image);

}
