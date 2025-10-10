package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String firstName;
    private String surname;
    private String address;
    private String phoneNumber;
    private List<Account> accounts;

    public Customer(String firstName, String surname, String address, String phoneNumber) {
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getFullName() {
        return firstName + " " + surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}