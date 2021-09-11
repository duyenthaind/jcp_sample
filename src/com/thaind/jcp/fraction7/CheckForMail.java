package com.thaind.jcp.fraction7;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author duyenthai
 */
public class CheckForMail {

    public boolean checkMail(Set<String> hosts, long timeout, TimeUnit timeUnit) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final AtomicBoolean hasNewMail = new AtomicBoolean(false);
        try {
            try {
                for (final String host : hosts) {
                    executorService.execute(() -> {
                        if (checkMail(host)) {
                            hasNewMail.set(true);
                        }
                    });
                }
            } catch (Exception ex1) {
                System.err.println(ex1.toString());
            }
        } finally {
            try {
                executorService.shutdown();
                executorService.awaitTermination(timeout, timeUnit);
            } catch (InterruptedException ex) {
                System.err.println(ex.toString());
                Thread.currentThread().interrupt();
            }

        }
        return hasNewMail.get();
    }

    public boolean checkMail(String host) {
        // Check mail for instance
        return false;
    }
}
