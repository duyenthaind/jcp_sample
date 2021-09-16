package com.thaind.test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author duyenthai
 */
public class TestFilter {
    public static void main(String[] args) {
        Integer[] values = {1, 2, 3, 5, 4, 6};
        List<Integer> list = Arrays.asList(values);
        list = list.stream().filter(val -> val > 3).collect(Collectors.toList());
        list.forEach(t -> System.out.print(t + "\t"));
        System.out.println();

        int equal5 = list.stream().filter(t -> t == 5).findAny().orElse(0);
        System.out.println(equal5);

        Optional<Integer> equal4 = list.stream().filter(t -> t == 4).findFirst();
        equal4.ifPresent(System.out::println);
    }
}
