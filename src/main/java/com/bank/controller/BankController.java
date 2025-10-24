// ============================================
// BankController.java
// ============================================
package com.bank.controller;

import com.bank.model.*;

public class BankController {
    private Bank bank;

    public BankController(Bank bank) {
        this.bank = bank;
    }

    // Process monthly interest for all accounts
    public void processMonthlyInterest() {
        System.out.println("\n=== Processing Monthly Interest Payments ===");

        int accountsProcessed = 0;
        for (Account account : bank.getAllAccounts()) {
            double balanceBefore = account.getBalance();
            account.payInterest();
            double balanceAfter = account.getBalance();
            double interestPaid = balanceAfter - balanceBefore;

            if (interestPaid > 0) {
                System.out.println("Account: " + account.getAccountNumber());
                System.out.println("Interest Paid: BWP " + String.format("%.2f", interestPaid));
                System.out.println("New Balance: BWP " + String.format("%.2f", balanceAfter));
                System.out.println("-----------------------------------");
                accountsProcessed++;
            }
        }

        if (accountsProcessed == 0) {
            System.out.println("No interest payments made.");
        } else {
            System.out.println("Total accounts processed: " + accountsProcessed);
        }
    }

    // Generate bank summary report
    public void generateBankSummary() {
        System.out.println("\n========================================");
        System.out.println("         " + bank.getName() + " - Summary Report");
        System.out.println("========================================");

        System.out.println("Total Customers: " + bank.getAllCustomers().size());
        System.out.println("Total Accounts: " + bank.getAllAccounts().size());

        double totalBalance = 0;
        int savingsCount = 0;
        int investmentCount = 0;
        int chequeCount = 0;

        for (Account account : bank.getAllAccounts()) {
            totalBalance += account.getBalance();

            if (account instanceof SavingsAccount) {
                savingsCount++;
            } else if (account instanceof InvestmentAccount) {
                investmentCount++;
            } else if (account instanceof ChequeAccount) {
                chequeCount++;
            }
        }

        System.out.println("\nAccount Types:");
        System.out.println("  Savings Accounts: " + savingsCount);
        System.out.println("  Investment Accounts: " + investmentCount);
        System.out.println("  Cheque Accounts: " + chequeCount);

        System.out.println("\nTotal Bank Holdings: BWP " + String.format("%.2f", totalBalance));
        System.out.println("========================================\n");
    }

    // Get bank name
    public String getBankName() {
        return bank.getName();
    }
}

