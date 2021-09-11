package com.thaind.jcp.fraction7;

import com.thaind.annotation.GuardedBy;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private final PrintWriter writer;
    @GuardedBy("this")
    private boolean isShutdown;
    @GuardedBy("this")
    private int reservations;

    public LogService(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<>();
        this.logger = new LoggerThread();
        this.writer = writer;
    }

    public void start() {
        logger.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        logger.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) throw new IllegalStateException();
            ++reservations;
            queue.put(msg);
        }
    }

    public void stopAllService() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    LogService.this.stop();
                } catch (Exception ignored) {
                }
            }
        });
    }

    private class LoggerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogService.this) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                            String message = queue.take();
                            synchronized (LogService.this) {
                                --reservations;
                            }
                            writer.println(message);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.toString() + "\nretry");
                    }

                }
            } finally {
                writer.close();
            }

        }
    }
}
