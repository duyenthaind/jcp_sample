package com.thaind.jcp.fraction15;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author duyenthai
 */
public class CasNumberRange {

    private final AtomicReference<IntPair> values = new AtomicReference<>(new IntPair(0, 0));

    public int getLower() {
        return this.values.get().lower;
    }

    public int getUpper() {
        return this.values.get().upper;
    }

    public void setLower(int i) {
        // todo add breaking policy to stop this loop
        while (true) {
            IntPair oldV = values.get();
            if (i > oldV.upper) {
                throw new IllegalArgumentException("Cant set lower to " + i + " > upper");
            }
            IntPair newV = new IntPair(i, oldV.upper);
            if (values.compareAndSet(oldV, newV)) {
                return;
            }
        }
    }

    public void setUpper(int i) {
        while (true) {
            IntPair oldV = values.get();
            if (i < oldV.lower) {
                throw new IllegalArgumentException("Cant set upper to " + i + " < lower");
            }
            IntPair newV = new IntPair(oldV.lower, i);
            if (values.compareAndSet(oldV, newV)) {
                return;
            }
        }
    }

    private static class IntPair {
        final int lower;
        final int upper;

        public IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }
}
