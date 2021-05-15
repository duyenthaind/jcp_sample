package com.thaind.design_pattern.factory;

public class BankMain {
    public static void main(String[] args) {
        Bank bank = BankFactory.getBank(BankType.VIETCOMBANK);

        System.out.println(bank.getBankName());
    }
}
