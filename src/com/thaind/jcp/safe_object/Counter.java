package com.thaind.jcp.safe_object;

public class Counter {
    //constrain bases on value, negative values are not allowed
    private long value = 0;

    public synchronized long getValue() {
        return this.value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalStateException("Counter is overflow");
        }
        return ++value;
    }
}
