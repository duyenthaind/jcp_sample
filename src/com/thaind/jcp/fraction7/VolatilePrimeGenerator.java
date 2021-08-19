package com.thaind.jcp.fraction7;

import com.thaind.annotation.GuardedBy;
import com.thaind.annotation.ThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@ThreadSafe
public class VolatilePrimeGenerator implements Runnable {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    @GuardedBy("this")
    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean cancelled;

    @Override
    public void run() {
        BigInteger bigInteger = BigInteger.ONE;
        while (!cancelled) {
            bigInteger = bigInteger.nextProbablePrime();
            synchronized (this) {
                primes.add(bigInteger);
            }
        }
    }

    public void cancel() {
        this.cancelled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<>(primes);
    }

    public static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        VolatilePrimeGenerator generator = new VolatilePrimeGenerator();
        EXECUTOR_SERVICE.execute(generator);
        try {
            TimeUnit.SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
        return generator.get();
    }
}
