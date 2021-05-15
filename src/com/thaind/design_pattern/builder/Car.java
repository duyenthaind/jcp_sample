package com.thaind.design_pattern.builder;

public class Car {
    private int size;
    private String name;

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public Car(Builder builder) {
        this.size = builder.size;
        this.name = builder.name;
    }

    public static class Builder {
        public int size;
        public String name;

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}
