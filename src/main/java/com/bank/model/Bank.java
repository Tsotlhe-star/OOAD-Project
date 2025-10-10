package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private List<Account> accounts;
    private List<Customer> customers;

    public Bank(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public Account createAccount(Customer customer, String accountType, String accountNumber, 
                                 double initialDeposit, String companyName, String companyAddress) {
        Account account = null;
        
        switch (accountType.toUpperCase()) {
            case "SAVINGS":
                account = new SavingsAccount(accountNumber, customer);
                break;
            case "INVESTMENT":
                account = new InvestmentAccount(accountNumber, customer, initialDeposit);
                break;
            case "CHEQUE":
                account = new ChequeAccount(accountNumber, customer, companyName, companyAddress);
                break;
            default:
                System.out.println("Invalid account type");
                return null;
        }
        
        accounts.add(account);
        if (!customers.contains(customer)) {
            customers.add(customer);
        }
        
        return account;
    }

    public void processInterestPayments() {
        for (Account account : accounts) {
            account.payInterest();
        }
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public String getName() {
        return name;
    }
}