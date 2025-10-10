package com.bank;

import com.bank.model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Banking System Test ===\n");
        
        // Create Bank
        Bank myBank = new Bank("First National Bank");
        System.out.println("Bank Created: " + myBank.getName());
        
        // Create Customer
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "71234567");
        System.out.println("\nCustomer Created: " + customer1.getFullName());
        
        // Create Savings Account
        System.out.println("\n--- Creating Savings Account ---");
        Account savings = myBank.createAccount(customer1, "SAVINGS", "SAV001", 0, null, null);
        savings.deposit(1000.00);
        System.out.println("Savings Account Created: " + savings.getAccountNumber());
        System.out.println("Balance: BWP " + String.format("%.2f", savings.getBalance()));
        
        // Create Investment Account
        System.out.println("\n--- Creating Investment Account ---");
        Account investment = myBank.createAccount(customer1, "INVESTMENT", "INV001", 500.00, null, null);
        investment.deposit(500.00);
        System.out.println("Investment Account Created: " + investment.getAccountNumber());
        System.out.println("Balance: BWP " + String.format("%.2f", investment.getBalance()));
        
        // Create Cheque Account
        System.out.println("\n--- Creating Cheque Account ---");
        Account cheque = myBank.createAccount(customer1, "CHEQUE", "CHQ001", 0, "ABC Corp", "456 Business Rd");
        cheque.deposit(2000.00);
        cheque.withdraw(500.00);
        System.out.println("Cheque Account Created: " + cheque.getAccountNumber());
        System.out.println("Balance: BWP " + String.format("%.2f", cheque.getBalance()));
        
        // Test Interest Payment
        System.out.println("\n--- Processing Interest Payments ---");
        myBank.processInterestPayments();
        
        // Display All Account Information
        System.out.println("\n=== Customer Account Summary ===");
        System.out.println("Customer: " + customer1.getFullName());
        System.out.println("Total Accounts: " + customer1.getAccounts().size());
        
        for (Account acc : customer1.getAccounts()) {
            System.out.println("\n" + acc.getAccountNumber() + ":");
            System.out.println("  Type: " + acc.getClass().getSimpleName());
            System.out.println("  Balance: BWP " + String.format("%.2f", acc.getBalance()));
            System.out.println("  Transactions: " + acc.getTransactions().size());
        }
        
        // Display Transaction History for Savings Account
        System.out.println("\n=== Transaction History (Savings Account) ===");
        for (Transaction trans : savings.getTransactions()) {
            System.out.println(trans.getTransactionDetails());
            System.out.println("---");
        }
    }
}