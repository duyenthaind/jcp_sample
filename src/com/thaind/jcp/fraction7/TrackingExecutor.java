package com.thaind.jcp.fraction7;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author duyenthai
 */
public class TrackingExecutor extends AbstractExecutorService {

    private final ExecutorService executorService;
    private final Set<Runnable> taskCancelledAtShutdown = Collections.synchronizedSet(new HashSet<>());

    public TrackingExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return executorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return executorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executorService.awaitTermination(timeout, unit);
    }

    @Override
    public void execute(Runnable runnable) {
        executorService.execute(() -> {
            try {
                runnable.run();
            } finally {
                if (isShutdown() && Thread.currentThread().isInterrupted()) {
                    taskCancelledAtShutdown.add(runnable);
                }
            }
        });
    }

    public List<Runnable> getCancelledTask() {
        if (!executorService.isTerminated()) {
            throw new IllegalStateException();
        }
        return new ArrayList<>(taskCancelledAtShutdown);
    }
}
