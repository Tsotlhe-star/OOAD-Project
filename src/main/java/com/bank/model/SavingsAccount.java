package com.bank.model;

public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.00025; // 0.025% monthly

    public SavingsAccount(String accountNumber, Customer customer) {
        super(accountNumber, customer);
    }

    @Override
    public double calculateInterest() {
        return getBalance() * INTEREST_RATE;
    }

    @Override
    public boolean canWithdraw() {
        return false; // Savings accounts don't allow withdrawals
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Withdrawals are not allowed on Savings Accounts.");
    }
}