package com.thaind.jcp.future_executors.semaphore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int permits) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(permits);
    }

    public boolean add(T object) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(object);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                sem.release();
            }
        }
    }

    public boolean remove(Object object) {
        boolean wasRemoved = set.remove(object);
        if (wasRemoved) {
            sem.release();
        }
        return wasRemoved;
    }
}
