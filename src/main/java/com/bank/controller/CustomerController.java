// ============================================
// CustomerController.java
// ============================================
package com.bank.controller;

import com.bank.model.*;
        import java.util.List;

public class CustomerController {
    private Bank bank;

    public CustomerController(Bank bank) {
        this.bank = bank;
    }

    // Create a new customer
    public Customer createNewCustomer(String firstName, String surname,
                                      String address, String phoneNumber) {
        if (firstName == null || firstName.trim().isEmpty()) {
            System.out.println("First name is required.");
            return null;
        }

        if (surname == null || surname.trim().isEmpty()) {
            System.out.println("Surname is required.");
            return null;
        }

        Customer customer = new Customer(firstName, surname, address, phoneNumber);
        System.out.println("Customer created successfully!");
        System.out.println("Name: " + customer.getFullName());
        return customer;
    }

    // View customer details
    public void viewCustomerDetails(String firstName, String surname) {
        Customer customer = findCustomer(firstName, surname);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("\n=== Customer Details ===");
        System.out.println("Name: " + customer.getFullName());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Phone: " + customer.getPhoneNumber());
        System.out.println("Number of Accounts: " + customer.getAccounts().size());

        if (!customer.getAccounts().isEmpty()) {
            System.out.println("\nAccounts:");
            for (Account acc : customer.getAccounts()) {
                System.out.println("  - " + acc.getAccountNumber() +
                        " (" + acc.getClass().getSimpleName() +
                        ") - BWP " + String.format("%.2f", acc.getBalance()));
            }
        }
    }

    // List all customers
    public void listAllCustomers() {
        List<Customer> customers = bank.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        System.out.println("\n=== All Customers ===");
        for (Customer customer : customers) {
            System.out.println("Name: " + customer.getFullName());
            System.out.println("Phone: " + customer.getPhoneNumber());
            System.out.println("Accounts: " + customer.getAccounts().size());
            System.out.println("-----------------------------------");
        }
    }

    // Helper method to find customer
    private Customer findCustomer(String firstName, String surname) {
        for (Customer customer : bank.getAllCustomers()) {
            if (customer.getFirstName().equalsIgnoreCase(firstName) &&
                    customer.getSurname().equalsIgnoreCase(surname)) {
                return customer;
            }
        }
        return null;
    }
}





