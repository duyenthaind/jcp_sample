package com.thaind.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duyenthai
 */
public class TestFilter {
    public static void main(String[] args) {
        Integer[] values = {1, 2, 3, 5, 4, 6};
        List<Integer> list = Arrays.asList(values);
        list = list.stream().filter(val -> val > 4).collect(Collectors.toList());
        list.forEach(t -> System.out.print(t + "\t"));
    }
}
