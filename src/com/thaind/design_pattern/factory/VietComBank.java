package com.thaind.design_pattern.factory;

public class VietComBank implements Bank{

    @Override
    public String getBankName() {
        return BankType.VIETCOMBANK.getName();
    }
    
}
