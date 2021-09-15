package com.thaind.jcp.fraction_8;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author duyenthai
 */
public class CallerRunExecutorManager {

    public static ThreadPoolExecutor getCallerRunExecutor(int numThread, int queueSize, long timeout) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(numThread, numThread + 10, timeout, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueSize));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
