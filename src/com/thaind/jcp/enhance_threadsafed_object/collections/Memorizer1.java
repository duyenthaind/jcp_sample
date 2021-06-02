package com.thaind.jcp.enhance_threadsafed_object.collections;

import java.util.HashMap;
import java.util.Map;

public class Memorizer1<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> computable;

    public Memorizer1(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = computable.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
