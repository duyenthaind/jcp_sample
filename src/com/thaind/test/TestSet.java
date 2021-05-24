package com.thaind.test;

import java.util.HashSet;
import java.util.Set;

public class TestSet {
    public static void main(String[] args) {
        Set<TestObj> test=  new HashSet<>();
        test.add(new TestObj("test","test2"));
        test.add(new TestObj("test","test"));
        System.out.println(test.size());
    }
}
