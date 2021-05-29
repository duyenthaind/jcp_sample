package com.thaind.jcp.enhance_threadsafed_object.modified_collections;

import java.util.Vector;

public class BetterVector<E> extends Vector<E> {
    public synchronized boolean putIfAbsent(E element) {
        boolean absent = !contains(element);
        if (absent) {
            add(element);
        }
        return absent;
    }
}
