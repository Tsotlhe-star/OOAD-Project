package com.bank.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account implements InterestCalculator {
    private String accountNumber;
    private double balance;
    private Date dateCreated;
    private Customer customer;
    private List<Transaction> transactions;

    public Account(String accountNumber, Customer customer) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = 0.0;
        this.dateCreated = new Date();
        this.transactions = new ArrayList<>();
        customer.addAccount(this);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            Transaction transaction = new Transaction(this, TransactionType.DEPOSIT, amount);
            transactions.add(transaction);
        }
    }

    public abstract boolean canWithdraw();

    public void withdraw(double amount) {
        if (canWithdraw() && amount > 0 && balance >= amount) {
            balance -= amount;
            Transaction transaction = new Transaction(this, TransactionType.WITHDRAWAL, amount);
            transactions.add(transaction);
        }
    }

    public void payInterest() {
        double interest = calculateInterest();
        if (interest > 0) {
            balance += interest;
            Transaction transaction = new Transaction(this, TransactionType.INTEREST_PAYMENT, interest);
            transactions.add(transaction);
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}