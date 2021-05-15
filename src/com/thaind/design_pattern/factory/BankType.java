package com.thaind.design_pattern.factory;

public enum BankType {
    VIETCOMBANK("VIETCOMBANK"), TPBANK("TPBANK");

    private final String name;

    BankType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
