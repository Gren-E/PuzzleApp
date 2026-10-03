package com.pa.model.creator.generator;

import com.pa.model.creator.PieceShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PuzzleGeneratorTest {

    @Test
    public void puzzleFactoryTest() {
        for (PieceShape shape : PieceShape.values()) {
            Assertions.assertNotNull(PuzzleGenerator.getGenerator(shape));
        }
    }

    @Test
    public void setEachTest() {
        Integer[][] array = new Integer[2][3];
        PuzzleGenerator.setEach(array, Integer::sum);

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                Assertions.assertEquals(i + j, array[i][j]);
            }
        }
    }

}
