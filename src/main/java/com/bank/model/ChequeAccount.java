package com.bank.model;

public class ChequeAccount extends Account {
    private String companyName;
    private String companyAddress;

    public ChequeAccount(String accountNumber, Customer customer, String companyName, String companyAddress) {
        super(accountNumber, customer);
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    @Override
    public double calculateInterest() {
        return 0.0; // Cheque accounts don't earn interest
    }

    @Override
    public boolean canWithdraw() {
        return true;
    }

    public String getEmploymentInfo() {
        return "Company: " + companyName + ", Address: " + companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }
}