package com.thaind.jcp.enhance_threadsafed_object.collections;

import java.math.BigInteger;

public class ExpensiveFuntion implements Computable<String, BigInteger>{

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        return new BigInteger(arg);
    }
    
}