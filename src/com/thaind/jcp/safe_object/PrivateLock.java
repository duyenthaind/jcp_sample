package com.thaind.jcp.safe_object;

public class PrivateLock {
    private final Object MY_LOCK = new Object();

    public void someMethod() {
        synchronized (MY_LOCK) {
            // Access or modify the state of widget
        }
    }
}
