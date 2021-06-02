package com.thaind.jcp.future_executors.self_script_samples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
    public static void main(String[] args) {
        List<Future<Integer>> list = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        Callable<Integer> callable;
        Future<Integer> future;
        for (int index = 0; ++index <= 10;) {
            callable = new CallableWorker(index);
            // submit Callable tasks to be executed by threadpool
            future = executor.submit(callable);
            // add Future tot he list to get the return value using Future
            list.add(future);
        }

        // shutdown the executor service now
        executor.shutdown();

        // wait until all thread are finished
        while (!executor.isTerminated()) {
            // Running ...
        }

        int sum = 0;
        for (Future<Integer> futureIndex : list) {
            try {
                sum += futureIndex.get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error travel around the future list, trace: " + e);
                e.printStackTrace();
            }
        }

        System.out.println("Finish all threads: ");
        System.out.println("Sum all = " + sum);
    }
}
