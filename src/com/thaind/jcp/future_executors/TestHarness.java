package com.thaind.jcp.future_executors;

import java.util.concurrent.CountDownLatch;

public class TestHarness {
    public long timeTask(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch signalStart = new CountDownLatch(1);
        final CountDownLatch signalEnd = new CountDownLatch(nThreads);

        for (int i = -1; ++i < nThreads;) {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        signalStart.await();
                        try {
                            System.out.println("wakeup thread: " + Thread.currentThread().getName() + " at: " +  System.currentTimeMillis());
                            task.run();
                        } finally {
                            signalEnd.countDown();
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                };

            };
            thread.start();
        }

        long start = System.currentTimeMillis();
        signalStart.countDown();
        signalEnd.await();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Running thread: " + Thread.currentThread().getName());
            }
        };
        try {
            System.out.println((new TestHarness()).timeTask(5, runnable));

        } catch (Exception ex) {
            System.err.println("Error consume entity, trace: " + ex);
        }
    }
}
