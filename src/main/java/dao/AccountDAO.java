package dao;

import com.bank.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String FILE_PATH = "data/accounts.txt";

    // Create - Save account to file
    public void saveAccount(Account account) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String line = formatAccount(account);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving account: " + e.getMessage());
        }
    }

    // Read - Get all accounts
    public List<String[]> getAllAccountsData() {
        List<String[]> accountsData = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return accountsData;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split("\\|");
                    accountsData.add(data);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts: " + e.getMessage());
        }

        return accountsData;
    }

    // Find account by account number
    public String[] findAccountByNumber(String accountNumber) {
        List<String[]> accounts = getAllAccountsData();
        for (String[] account : accounts) {
            if (account[0].equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    // Update account balance
    public void updateAccountBalance(String accountNumber, double newBalance) {
        List<String[]> accounts = getAllAccountsData();

        for (String[] account : accounts) {
            if (account[0].equals(accountNumber)) {
                account[2] = String.valueOf(newBalance); // Update balance
                break;
            }
        }

        updateAllAccounts(accounts);
    }

    // Update all accounts (rewrite file)
    private void updateAllAccounts(List<String[]> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] account : accounts) {
                writer.write(String.join("|", account));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating accounts: " + e.getMessage());
        }
    }

    // Delete account
    public boolean deleteAccount(String accountNumber) {
        List<String[]> accounts = getAllAccountsData();
        boolean removed = accounts.removeIf(a -> a[0].equals(accountNumber));

        if (removed) {
            updateAllAccounts(accounts);
        }
        return removed;
    }

    // Format account for file storage
    // Format: accountNumber|accountType|balance|customerFirstName|customerSurname|dateCreated|companyName|companyAddress
    private String formatAccount(Account account) {
        Customer customer = account.getCustomer();
        String accountType = account.getClass().getSimpleName();

        String companyName = "";
        String companyAddress = "";

        if (account instanceof ChequeAccount) {
            ChequeAccount cheque = (ChequeAccount) account;
            companyName = cheque.getCompanyName();
            companyAddress = cheque.getCompanyAddress();
        }

        return String.join("|",
                account.getAccountNumber(),
                accountType,
                String.valueOf(account.getBalance()),
                customer.getFirstName(),
                customer.getSurname(),
                account.getDateCreated().toString(),
                companyName,
                companyAddress
        );
    }

    // Clear all accounts (for testing)
    public void clearAllAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Just open and close to clear file
        } catch (IOException e) {
            System.err.println("Error clearing accounts: " + e.getMessage());
        }
    }

    // Get accounts count
    public int getAccountsCount() {
        return getAllAccountsData().size();
    }
}
