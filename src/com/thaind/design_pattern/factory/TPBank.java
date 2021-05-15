package com.thaind.design_pattern.factory;

public class TPBank implements Bank {

    @Override
    public String getBankName() {
        return BankType.TPBANK.getName();
    }
    
}
