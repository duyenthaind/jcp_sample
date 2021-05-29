package com.thaind.jcp.future_executors.semaphore.barriersample;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierSample {
    public static void main(String[] args) {
        // 4 thread are part of the barrier, service 1 -> 3 and the main thread calls these threads
        final CyclicBarrier barrier = new CyclicBarrier(4);

        Thread service1 = new Thread(new SimpleServiceOne(barrier, "one", 3000L));
        Thread service2 = new Thread(new SimpleServiceOne(barrier, "two", 5000L));
        Thread service3 = new Thread(new SimpleServiceOne(barrier, "three", 1000L));

        System.out.println("Starting all the services at: " + new Date());

        service1.start();
        service2.start();
        service3.start();

        try {
            barrier.await();
        } catch (Exception ex) {
            System.err.println("Error, interrupted main thread, trace: " + ex);
            ex.printStackTrace();
        }
        System.out.println("Ending all the services at: " + new Date());
    }
}
