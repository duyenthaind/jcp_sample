package com.thaind.jcp.fraction7;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger bigInteger = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(bigInteger = bigInteger.nextProbablePrime());
            }
        } catch (InterruptedException exception) {
            /*Allow thread to exit*/
        }
    }

    public void cancel() {
        interrupt();
    }
}
