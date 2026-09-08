package com.pa.creator.factory;

import com.pa.creator.PieceShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PuzzleFactoryTest {

    @Test
    public void puzzleFactoryTest() {
        for (PieceShape shape : PieceShape.values()) {
            Assertions.assertNotNull(PuzzleFactory.getFactory(shape));
        }
    }

    @Test
    public void setEachTest() {
        Integer[][] array = new Integer[2][3];
        PuzzleFactory.setEach(array, Integer::sum);

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                Assertions.assertEquals(i + j, array[i][j]);
            }
        }
    }

}
