package com.bank.controller;

import com.bank.model.*;
import java.util.List;

public class AccountController {
    private Bank bank;

    public AccountController(Bank bank) {
        this.bank = bank;
    }

    // Create a new account
    public Account createNewAccount(Customer customer, String accountType, String accountNumber,
                                    double initialDeposit, String companyName, String companyAddress) {
        try {
            Account account = bank.createAccount(customer, accountType, accountNumber,
                    initialDeposit, companyName, companyAddress);
            if (account != null) {
                System.out.println("Account created successfully!");
                System.out.println("Account Number: " + account.getAccountNumber());
                return account;
            } else {
                System.out.println("Failed to create account.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
            return null;
        }
    }

    // Deposit money
    public boolean depositMoney(String accountNumber, double amount) {
        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return false;
        }

        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }

        account.deposit(amount);
        System.out.println("Successfully deposited BWP " + String.format("%.2f", amount));
        System.out.println("New balance: BWP " + String.format("%.2f", account.getBalance()));
        return true;
    }

    // Withdraw money
    public boolean withdrawMoney(String accountNumber, double amount) {
        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return false;
        }

        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }

        if (!account.canWithdraw()) {
            System.out.println("Withdrawals are not allowed on this account type.");
            return false;
        }

        if (account.getBalance() < amount) {
            System.out.println("Insufficient funds. Current balance: BWP " +
                    String.format("%.2f", account.getBalance()));
            return false;
        }

        account.withdraw(amount);
        System.out.println("Successfully withdrew BWP " + String.format("%.2f", amount));
        System.out.println("New balance: BWP " + String.format("%.2f", account.getBalance()));
        return true;
    }

    // Check balance
    public void checkBalance(String accountNumber) {
        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Holder: " + account.getCustomer().getFullName());
        System.out.println("Current Balance: BWP " + String.format("%.2f", account.getBalance()));
    }

    // View transaction history
    public void viewTransactionHistory(String accountNumber) {
        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        List<Transaction> transactions = account.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
            return;
        }

        System.out.println("\n=== Transaction History for Account " + accountNumber + " ===");
        for (Transaction t : transactions) {
            System.out.println("\n" + t.getTransactionDetails());
            System.out.println("-----------------------------------");
        }
    }

    // Helper method to find account
    private Account findAccountByNumber(String accountNumber) {
        for (Account account : bank.getAllAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    // List all accounts
    public void listAllAccounts() {
        List<Account> accounts = bank.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        System.out.println("\n=== All Accounts ===");
        for (Account acc : accounts) {
            String accountType = acc.getClass().getSimpleName();
            System.out.println("Account Number: " + acc.getAccountNumber());
            System.out.println("Type: " + accountType);
            System.out.println("Holder: " + acc.getCustomer().getFullName());
            System.out.println("Balance: BWP " + String.format("%.2f", acc.getBalance()));
            System.out.println("-----------------------------------");
        }
    }

    public void showCreateAccountView() {
    }
}
