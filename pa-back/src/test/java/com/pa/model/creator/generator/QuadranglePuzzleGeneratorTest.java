package com.pa.model.creator.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuadranglePuzzleGeneratorTest {

    @Test
    public void quadranglePuzzleFactoryTest() {
        QuadranglePuzzleGenerator factory = new QuadranglePuzzleGenerator(new EdgeGenerator());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> factory.generatePointsOnGrid(20, 20, 50, 2000));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> factory.generatePointsOnGrid(20, 20, 500, 20));
    }
}
