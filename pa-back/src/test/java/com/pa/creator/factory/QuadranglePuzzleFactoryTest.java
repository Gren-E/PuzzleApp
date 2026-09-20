package com.pa.creator.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuadranglePuzzleFactoryTest {

    @Test
    public void quadranglePuzzleFactoryTest() {
        QuadranglePuzzleFactory factory = new QuadranglePuzzleFactory();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> factory.generatePointsOnGrid(20, 20, 50, 2000));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> factory.generatePointsOnGrid(20, 20, 500, 20));
    }
}
