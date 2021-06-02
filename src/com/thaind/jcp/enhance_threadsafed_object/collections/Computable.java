package com.thaind.jcp.enhance_threadsafed_object.collections;

public interface Computable<A, V>{
    V compute(A arg) throws InterruptedException;
}