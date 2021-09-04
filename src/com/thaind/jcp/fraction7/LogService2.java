package com.thaind.jcp.fraction7;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LogService2 {

    private final PrintWriter writer;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LogService2(PrintWriter writer) {
        this.writer = writer;
    }

    public void start() {
        System.out.println("Starting service!!!");
    }

    public void stop() throws InterruptedException {
        try {
            executorService.shutdown();
            executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } finally {
            writer.close();
        }
    }

    public void log(String msg){
        try{
            executorService.execute(new WriteTask(msg));
        } catch (Exception ex){
            System.err.println(ex.toString());
        }
    }

    private class WriteTask extends Thread {

        private String msg;

        public WriteTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                writer.println(msg);
            } catch (Exception ex) {
                System.err.println(ex.toString());
            }
        }
    }
}
