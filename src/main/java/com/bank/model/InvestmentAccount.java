package com.bank.model;

public class InvestmentAccount extends Account {
    private static final double INTEREST_RATE = 0.05; // 5% monthly
    private static final double MINIMUM_DEPOSIT = 500.00;

    public InvestmentAccount(String accountNumber, Customer customer, double initialDeposit) {
        super(accountNumber, customer);
        if (initialDeposit >= MINIMUM_DEPOSIT) {
            deposit(initialDeposit);
        } else {
            throw new IllegalArgumentException("Initial deposit must be at least BWP " + MINIMUM_DEPOSIT);
        }
    }

    @Override
    public double calculateInterest() {
        return getBalance() * INTEREST_RATE;
    }

    @Override
    public boolean canWithdraw() {
        return true;
    }
}