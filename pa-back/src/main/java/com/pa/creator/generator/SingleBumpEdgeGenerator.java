package com.pa.creator.generator;

import java.util.Random;

/**
 * An abstract parent class of all edge types that contain a single bump turned inward or outward.
 * @author Ewelina Gren
 * @version 1.0
 */
public abstract class SingleBumpEdgeGenerator extends EdgeGenerator {

    protected static final Random random = new Random();

    /**
     * Generates the value of the bump depth, as a percentage of the length of the edge it belongs to.
     * @param origin the least value that can be returned
     * @param bound the upper bound (exclusive) for the returned value
     * @return a randomly assigned value of the bump depth between the origin (inclusive) and the bound (exclusive)
     */
    protected double generateBumpDepthRatio(double origin, double bound) {
        double bumpDepthRatio = random.nextDouble(origin, bound);
        return random.nextBoolean() ? bumpDepthRatio * -1 : bumpDepthRatio;
    }

}
