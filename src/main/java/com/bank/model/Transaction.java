package com.bank.model;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    private String transactionId;
    private TransactionType transactionType;
    private double amount;
    private Date date;
    private double newBalance;
    private Account account;

    public Transaction(Account account, TransactionType transactionType, double amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = new Date();
        this.newBalance = account.getBalance();
    }

    public String getTransactionDetails() {
        return "Transaction ID: " + transactionId + "\n" +
               "Type: " + transactionType + "\n" +
               "Amount: BWP " + String.format("%.2f", amount) + "\n" +
               "Date: " + date + "\n" +
               "New Balance: BWP " + String.format("%.2f", newBalance);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public Account getAccount() {
        return account;
    }
}