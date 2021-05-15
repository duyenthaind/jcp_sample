package com.thaind.design_pattern.factory;

public class BankFactory {
    private BankFactory() {
    }

    public static final Bank getBank(BankType bankType) {
        switch (bankType) {
            case TPBANK:
                return new TPBank();
            case VIETCOMBANK:
                return new VietComBank();
            default:
                throw new IllegalArgumentException("This bank type is not supported!");
        }
    }
}
