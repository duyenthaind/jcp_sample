package com.thaind.jcp.fraction7;

import com.thaind.annotation.DontUse;
import com.thaind.annotation.NotGood;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@NotGood
@DontUse
public class TimedRun1 {
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    public static void timedRun(Runnable runnable, long timeout, TimeUnit timeUnit) {
        final Thread task = Thread.currentThread();
        SCHEDULED_EXECUTOR_SERVICE.schedule(new Runnable() {
            @Override
            public void run() {
                task.interrupt();
            }
        }, timeout, timeUnit);
        runnable.run();
    }
}
