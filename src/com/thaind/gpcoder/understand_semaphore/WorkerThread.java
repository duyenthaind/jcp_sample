package com.thaind.gpcoder.understand_semaphore;

import java.util.concurrent.Semaphore;

public class WorkerThread extends Thread {

    private static final long SLEEP_TIME = 100;

    private final Semaphore semaphore;
    private String name;

    public WorkerThread(Semaphore semaphore, String name) {
        this.semaphore = semaphore;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + ": acquiring lock...");
            System.out.println(name + ": available Semaphore permits now: " + semaphore.availablePermits());
            // Acquire the lock
            semaphore.acquire();
            System.out.println(name + " acquire the permit!");
            try{
                System.out.println(name + ": is performing operation, available Semaphore permits: " + semaphore.availablePermits());
                Thread.sleep(SLEEP_TIME); // simulate time to work
            } finally {
                // Calling release() after a successful acquire()
                System.out.println(name + ": releasing lock...");
                semaphore.release();
                System.out.println(name + ": available Semaphore: " + semaphore.availablePermits());
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
