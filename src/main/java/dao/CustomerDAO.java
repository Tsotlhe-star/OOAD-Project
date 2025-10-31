package dao;

import com.bank.model.Customer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static final String FILE_PATH = "data/customers.txt";

    // Create - Save customer to file
    public void saveCustomer(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String line = formatCustomer(customer);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving customer: " + e.getMessage());
        }
    }

    // Read - Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return customers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Customer customer = parseCustomer(line);
                    if (customer != null) {
                        customers.add(customer);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading customers: " + e.getMessage());
        }

        return customers;
    }

    // Find customer by name
    public Customer findCustomerByName(String firstName, String surname) {
        List<Customer> customers = getAllCustomers();
        for (Customer customer : customers) {
            if (customer.getFirstName().equalsIgnoreCase(firstName) &&
                    customer.getSurname().equalsIgnoreCase(surname)) {
                return customer;
            }
        }
        return null;
    }

    // Update - Update all customers (rewrite file)
    public void updateCustomers(List<Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Customer customer : customers) {
                writer.write(formatCustomer(customer));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating customers: " + e.getMessage());
        }
    }

    // Delete - Delete customer by name
    public boolean deleteCustomer(String firstName, String surname) {
        List<Customer> customers = getAllCustomers();
        boolean removed = customers.removeIf(c ->
                c.getFirstName().equalsIgnoreCase(firstName) &&
                        c.getSurname().equalsIgnoreCase(surname));

        if (removed) {
            updateCustomers(customers);
        }
        return removed;
    }

    // Format customer for file storage
    // Format: firstName|surname|address|phoneNumber
    private String formatCustomer(Customer customer) {
        return String.join("|",
                customer.getFirstName(),
                customer.getSurname(),
                customer.getAddress(),
                customer.getPhoneNumber()
        );
    }

    // Parse customer from file line
    private Customer parseCustomer(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length == 4) {
                return new Customer(parts[0], parts[1], parts[2], parts[3]);
            }
        } catch (Exception e) {
            System.err.println("Error parsing customer: " + e.getMessage());
        }
        return null;
    }

    // Clear all customers (for testing)
    public void clearAllCustomers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Just open and close to clear file
        } catch (IOException e) {
            System.err.println("Error clearing customers: " + e.getMessage());
        }
    }
}
