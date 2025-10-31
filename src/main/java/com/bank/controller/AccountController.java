package com.bank.controller;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.TransactionDAO;
import com.bank.model.*;
import com.bank.view.CreateAccountView;

import java.util.List;

public class AccountController {
    private Bank bank;
    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private int accountCounter = 1;

    public AccountController(Bank bank) {
        this.bank = bank;
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    public void showCreateAccountView() {
        CreateAccountView view = new CreateAccountView();

        // Handle account type changes
        view.getAccountTypeBox().setOnAction(e -> {
            String accountType = view.getAccountType();
            if (accountType.equals("INVESTMENT")) {
                view.showInvestmentFields();
            } else if (accountType.equals("CHEQUE")) {
                view.showChequeFields();
            } else {
                view.hideExtraFields();
            }
        });

        // Handle create button
        view.getCreateBtn().setOnAction(e -> {
            handleCreateAccount(view);
        });

        // Handle cancel button
        view.getCancelBtn().setOnAction(e -> {
            view.close();
        });

        view.show();
    }

    private void handleCreateAccount(CreateAccountView view) {
        try {
            // Get customer information
            String firstName = view.getFirstName();
            String surname = view.getSurname();
            String address = view.getAddress();
            String phone = view.getPhone();
            String accountType = view.getAccountType();

            // Validate customer info
            if (firstName.isEmpty() || surname.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                view.setMessage("Please fill in all customer information fields.", true);
                return;
            }

            // Create customer
            Customer customer = new Customer(firstName, surname, address, phone);

            // Generate account number
            String accountNumber = generateAccountNumber(accountType);

            // Create account based on type
            Account account = null;

            if (accountType.equals("SAVINGS")) {
                account = bank.createAccount(customer, accountType, accountNumber, 0, null, null);

            } else if (accountType.equals("INVESTMENT")) {
                String depositStr = view.getInitialDeposit();
                if (depositStr.isEmpty()) {
                    view.setMessage("Please enter initial deposit (minimum BWP 500.00).", true);
                    return;
                }

                double initialDeposit = Double.parseDouble(depositStr);
                if (initialDeposit < 500.00) {
                    view.setMessage("Investment account requires minimum BWP 500.00 initial deposit.", true);
                    return;
                }

                account = bank.createAccount(customer, accountType, accountNumber, initialDeposit, null, null);

            } else if (accountType.equals("CHEQUE")) {
                String companyName = view.getCompanyName();
                String companyAddress = view.getCompanyAddress();

                if (companyName.isEmpty() || companyAddress.isEmpty()) {
                    view.setMessage("Please enter employment information for Cheque account.", true);
                    return;
                }

                account = bank.createAccount(customer, accountType, accountNumber, 0, companyName, companyAddress);
            }

            if (account != null) {
                // ✅ SAVE TO FILES
                customerDAO.saveCustomer(customer);
                accountDAO.saveAccount(account);

                // Save initial deposit transaction if exists
                for (Transaction trans : account.getTransactions()) {
                    transactionDAO.saveTransaction(trans);
                }

                view.setMessage("✅ Account created successfully!\nAccount Number: " + accountNumber +
                        "\nData saved to files.", false);

                // Clear fields after 2 seconds
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        javafx.application.Platform.runLater(() -> view.clearFields());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }

        } catch (NumberFormatException ex) {
            view.setMessage("Invalid amount format. Please enter a valid number.", true);
        } catch (IllegalArgumentException ex) {
            view.setMessage(ex.getMessage(), true);
        } catch (Exception ex) {
            view.setMessage("Error creating account: " + ex.getMessage(), true);
        }
    }

    private String generateAccountNumber(String accountType) {
        String prefix = "";
        switch (accountType) {
            case "SAVINGS":
                prefix = "SAV";
                break;
            case "INVESTMENT":
                prefix = "INV";
                break;
            case "CHEQUE":
                prefix = "CHQ";
                break;
        }
        return prefix + String.format("%04d", accountCounter++);
    }

    // Create a new account (console version)
    public Account createNewAccount(Customer customer, String accountType, String accountNumber,
                                    double initialDeposit, String companyName, String companyAddress) {
        try {
            Account account = bank.createAccount(customer, accountType, accountNumber,
                    initialDeposit, companyName, companyAddress);
            if (account != null) {
                // ✅ SAVE TO FILES
                customerDAO.saveCustomer(customer);
                accountDAO.saveAccount(account);

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

        double oldBalance = account.getBalance();
        account.deposit(amount);

        // ✅ SAVE TO FILES
        accountDAO.updateAccountBalance(accountNumber, account.getBalance());
        // Save the latest transaction
        List<Transaction> transactions = account.getTransactions();
        if (!transactions.isEmpty()) {
            transactionDAO.saveTransaction(transactions.get(transactions.size() - 1));
        }

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

        // ✅ SAVE TO FILES
        accountDAO.updateAccountBalance(accountNumber, account.getBalance());
        // Save the latest transaction
        List<Transaction> transactions = account.getTransactions();
        if (!transactions.isEmpty()) {
            transactionDAO.saveTransaction(transactions.get(transactions.size() - 1));
        }

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
}