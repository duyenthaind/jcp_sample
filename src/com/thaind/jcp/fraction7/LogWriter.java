package com.thaind.jcp.fraction7;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogWriter {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private static final int CAPACITY = 1000;

    public LogWriter(LoggerThread loggerThread) {
        this.queue = new LinkedBlockingQueue<>(CAPACITY);
        this.loggerThread = loggerThread;
    }

    public void start(){
        loggerThread.start();
    }

    public void log(String msg) throws InterruptedException{
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter printWriter;

        public LoggerThread(PrintWriter printWriter) {
            this.printWriter = new PrintWriter(printWriter, true);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    printWriter.println(queue.take());
                }
            } catch (Exception ex) {
                System.err.println(ex.toString());
            } finally {
                printWriter.close();
            }
        }
    }

}
