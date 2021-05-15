package com.thaind.test;

public class Test {

    //ThreadSafe
    public void test(){
        synchronized(this){
        }
    }

    public static void main(String[] args) {
        System.out.println("Test this out");
    }
}
