package com.thaind.jcp.fraction7;

import com.thaind.jcp.future_executors.LaunderThrowable;

import java.util.concurrent.*;

public class TimedRun {
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    public static void timedRun(Runnable runnable, long timeout, TimeUnit timeUnit) {
        Future<?> task = SCHEDULED_EXECUTOR_SERVICE.submit(runnable);
        try {
            task.get(timeout, timeUnit);
        } catch (TimeoutException timeoutException) {
            //cancel below
        } catch (InterruptedException e) {
            //cancel below
        } catch (ExecutionException executionException) {
            //exception in class, rethrow
            throw LaunderThrowable.launderThrowable(executionException);
        } finally {
            task.cancel(true); //interrupt if running
        }
    }
}
