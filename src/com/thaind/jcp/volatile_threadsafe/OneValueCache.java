package com.thaind.jcp.volatile_threadsafe;

import java.math.BigInteger;
import java.util.Arrays;

@Immutable
public class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger bigInteger, BigInteger[] factors) {
        this.lastNumber = bigInteger;
        this.lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger bigInteger) {
        if (lastNumber == null || !lastNumber.equals(bigInteger)) {
            return null;
        } else {
            return Arrays.copyOf(this.lastFactors, this.lastFactors.length);
        }
    }
}
