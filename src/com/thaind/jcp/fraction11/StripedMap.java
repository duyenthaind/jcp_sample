package com.thaind.jcp.fraction11;

import com.thaind.race_condition.lazyinit.ThreadSafe;

import java.util.Objects;

/**
 * @author duyenthai
 */
@ThreadSafe
public class StripedMap {
    // Synchronized policy: buckets[n] guarded by locks[n%N_LOCKS]
    private static final int N_LOCKS = 16;
    private final Node[] buckets;
    private final Object[] locks;

    private static class Node {
        Node next;
        Object key;
        Object value;
    }

    public StripedMap(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[N_LOCKS];
        for (int index = -1; ++index < N_LOCKS; ) {
            locks[index] = new Object();
        }
    }

    private int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    public Object get(Object key) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node node = buckets[hash]; Objects.nonNull(node); node = node.next) {
                if (node.key.equals(key)) {
                    return node.value;
                }
            }
        }
        return null;
    }

    public void clear() {
        for (int index = -1; ++index < buckets.length; ) {
            synchronized (locks[index % N_LOCKS]) {
                buckets[index] = null;
            }
        }
    }
}
