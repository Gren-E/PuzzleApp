package com.pa.creator.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

public class SingleBumpEdgeGeneratorTest {

    @RepeatedTest(10)
    public void generateBumpDepthRatioTest() {
        double ratio = Math.abs(new TestSingleBumpEdgeGenerator().generateBumpDepthRatio(5, 30));
        Assertions.assertTrue(ratio >= 5 && ratio < 30);
    }

    static class TestSingleBumpEdgeGenerator extends SingleBumpEdgeGenerator {}

}
