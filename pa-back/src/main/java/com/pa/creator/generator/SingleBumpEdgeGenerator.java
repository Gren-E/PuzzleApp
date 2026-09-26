package com.pa.creator.generator;

import java.util.Random;

public abstract class SingleBumpEdgeGenerator extends EdgeGenerator {

    protected static final Random random = new Random();

    protected double generateBumpDepthRatio(double origin, double bound) {
        double bumpDepthRatio = random.nextDouble(origin, bound);
        return random.nextBoolean() ? bumpDepthRatio * -1 : bumpDepthRatio;
    }

}
