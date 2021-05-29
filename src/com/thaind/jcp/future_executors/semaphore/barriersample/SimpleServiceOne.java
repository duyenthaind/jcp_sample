package com.thaind.jcp.future_executors.semaphore.barriersample;

import java.util.concurrent.CyclicBarrier;

public class SimpleServiceOne implements Runnable {

    private final CyclicBarrier cyclicBarrier;
    private final String name;
    private final long interrupt;

    public SimpleServiceOne(CyclicBarrier cyclicBarrier, String name, long interrupt) {
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
        this.interrupt = interrupt;
    }

    @Override
    public void run() {
        System.out.printf("Starting service %s ...%n", name);
        try {
            Thread.sleep(interrupt);
        } catch (Exception ex) {
            System.err.println("Error, trace: " + ex);
            ex.printStackTrace();
        }
        System.out.printf("Service %s has finished its work.. waiting for others... %n", name);
        try {
            cyclicBarrier.await();
        } catch (Exception ex) {
            System.out.printf("Service %s interrupted %n", name);
            ex.printStackTrace();
        }
        System.out.printf("The wait is over, lets complete the service %s%n", name);
    }

}