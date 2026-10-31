package com.pa.model.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameStatusTest {

    @Test
    public void gameStatusTest() {
        GameStatus[] values = GameStatus.values();
        Assertions.assertEquals(3, values.length);
    }

}
