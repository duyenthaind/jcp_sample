package com.thaind.jcp.fraction7;

import com.thaind.jcp.future_executors.LaunderThrowable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedRun2 {
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);

    public static void timedRun(final Runnable runnable, long timeout, TimeUnit timeUnit) throws InterruptedException {
        class RethrowableTask implements Runnable {

            private volatile Throwable throwable;

            @Override
            public void run() {
                try {
                    runnable.run();
                    //this run as a function in class, run outside with .start plz
                } catch (Throwable throwable) {
                    this.throwable = throwable;
                }
            }

            void rethrow() {
                if (throwable != null) {
                    throw LaunderThrowable.launderThrowable(throwable);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread threadTask = new Thread(task);
        threadTask.start();
        SCHEDULED_EXECUTOR_SERVICE.schedule(new Runnable() {
            @Override
            public void run() {
                threadTask.interrupt();
            }
        }, timeout, timeUnit);
        threadTask.join(timeUnit.toMillis(timeout));
        task.rethrow();
    }
}
