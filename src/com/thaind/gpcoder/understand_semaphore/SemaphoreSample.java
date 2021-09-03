package com.thaind.gpcoder.understand_semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreSample {
    private static Semaphore semaphore = new Semaphore(4);

    public static void main(String[] args) {
        System.out.println("Total Semaphore permits: " + semaphore.availablePermits());
        for (int index = -1; ++index < 5; ) {
            WorkerThread worker = new WorkerThread(semaphore, "worker_" + index);
            worker.start();
        }
    }
}
