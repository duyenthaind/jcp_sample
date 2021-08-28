package com.thaind.jcp.fraction7;

import com.thaind.annotation.UnReliable;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@UnReliable
public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;

    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger bigInteger = BigInteger.ONE;
            while (!cancelled) {
                queue.put(bigInteger = bigInteger.nextProbablePrime());
            }
        } catch (InterruptedException exception) {
            System.err.println(exception);
        }
    }

    public void cancel() {
        cancelled = true;
    }
}

class Consumer {
    void consumePrime() throws InterruptedException {
        BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<>(10);

        BrokenPrimeProducer brokenPrimeProducer = new BrokenPrimeProducer(queue);
        brokenPrimeProducer.start();
        try {
            while (needMorePrime()) {
                consume(queue.take());
            }
        } finally {
            //If the producer is blocked by put method of queue => this is not gonna work
            brokenPrimeProducer.cancel();
        }
    }

    boolean needMorePrime() {
        return System.currentTimeMillis() % 12 == 0;
    }

    void consume(BigInteger prime) {

    }
}
