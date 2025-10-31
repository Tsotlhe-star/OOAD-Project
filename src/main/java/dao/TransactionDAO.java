package dao;

import com.bank.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private static final String FILE_PATH = "data/transactions.txt";

    // Create - Save transaction to file
    public void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String line = formatTransaction(transaction);
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }

    // Read - Get all transactions
    public List<String[]> getAllTransactionsData() {
        List<String[]> transactionsData = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return transactionsData;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split("\\|");
                    transactionsData.add(data);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading transactions: " + e.getMessage());
        }

        return transactionsData;
    }

    // Get transactions by account number
    public List<String[]> getTransactionsByAccount(String accountNumber) {
        List<String[]> accountTransactions = new ArrayList<>();
        List<String[]> allTransactions = getAllTransactionsData();

        for (String[] transaction : allTransactions) {
            if (transaction.length > 5 && transaction[5].equals(accountNumber)) {
                accountTransactions.add(transaction);
            }
        }

        return accountTransactions;
    }

    // Get transaction by ID
    public String[] findTransactionById(String transactionId) {
        List<String[]> transactions = getAllTransactionsData();
        for (String[] transaction : transactions) {
            if (transaction[0].equals(transactionId)) {
                return transaction;
            }
        }
        return null;
    }

    // Format transaction for file storage
    // Format: transactionId|type|amount|date|newBalance|accountNumber
    private String formatTransaction(Transaction transaction) {
        return String.join("|",
                transaction.getTransactionId(),
                transaction.getTransactionType().toString(),
                String.valueOf(transaction.getAmount()),
                transaction.getDate().toString(),
                String.valueOf(transaction.getNewBalance()),
                transaction.getAccount().getAccountNumber()
        );
    }

    // Clear all transactions (for testing)
    public void clearAllTransactions() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Just open and close to clear file
        } catch (IOException e) {
            System.err.println("Error clearing transactions: " + e.getMessage());
        }
    }

    // Get total transactions count
    public int getTransactionsCount() {
        return getAllTransactionsData().size();
    }

    // Get transactions count by type
    public int getTransactionsCountByType(TransactionType type) {
        int count = 0;
        List<String[]> transactions = getAllTransactionsData();

        for (String[] transaction : transactions) {
            if (transaction.length > 1 && transaction[1].equals(type.toString())) {
                count++;
            }
        }

        return count;
    }
}