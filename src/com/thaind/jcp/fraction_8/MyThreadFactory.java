package com.thaind.jcp.fraction_8;

import java.util.concurrent.ThreadFactory;

/**
 * @author duyenthai
 */
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    public Thread newThread(Runnable runnable) {
        return new MyAppThread(runnable, poolName);
    }
}
