package com.thaind.design_pattern.builder;

public class CarMain {
    public static void main(String[] args) {
        Car car = new Car.Builder().size(15).name("car").build();
        System.out.println(car.getSize());
        System.out.println(car.getName());
    }
}
