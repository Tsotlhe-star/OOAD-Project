package com.bank.controller;

import dao.AccountDAO;
import dao.TransactionDAO;
import com.bank.model.*;
import com.bank.view.*;

import java.util.List;

public class TransactionController {
    private Bank bank;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public TransactionController(Bank bank) {
        this.bank = bank;
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    public void showDepositView() {
        DepositView depositView = new DepositView();

        depositView.getDepositBtn().setOnAction(e -> {
            handleDeposit(depositView);
        });

        depositView.getCancelBtn().setOnAction(e -> {
            depositView.close();
        });

        depositView.show();
    }

    public void showWithdrawView() {
        WithdrawView withdrawView = new WithdrawView();

        withdrawView.getWithdrawBtn().setOnAction(e -> {
            handleWithdraw(withdrawView);
        });

        withdrawView.getCancelBtn().setOnAction(e -> {
            withdrawView.close();
        });

        withdrawView.show();
    }

    public void showTransactionHistoryView() {
        TransactionHistoryView historyView = new TransactionHistoryView();

        historyView.getSearchBtn().setOnAction(e -> {
            handleViewHistory(historyView);
        });

        historyView.getCloseBtn().setOnAction(e -> {
            historyView.close();
        });

        historyView.show();
    }

    private void handleDeposit(DepositView view) {
        String accountNumber = view.getAccountNumber();
        String amountStr = view.getAmount();

        if (accountNumber.isEmpty() || amountStr.isEmpty()) {
            view.setMessage("Please fill in all fields", true);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            Account account = findAccount(accountNumber);

            if (account == null) {
                view.setMessage("Account not found", true);
                return;
            }

            if (amount <= 0) {
                view.setMessage("Amount must be positive", true);
                return;
            }

            int transactionsBefore = account.getTransactions().size();
            account.deposit(amount);

            // ✅ SAVE TO FILES
            accountDAO.updateAccountBalance(accountNumber, account.getBalance());

            // Save the new transaction
            List<Transaction> transactions = account.getTransactions();
            if (transactions.size() > transactionsBefore) {
                transactionDAO.saveTransaction(transactions.get(transactions.size() - 1));
            }

            view.setMessage("✅ Successfully deposited BWP " + String.format("%.2f", amount) +
                    "\nNew balance: BWP " + String.format("%.2f", account.getBalance()) +
                    "\nSaved to file!", false);

            // Clear fields after 2 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(() -> view.clearFields());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (NumberFormatException e) {
            view.setMessage("Invalid amount format", true);
        }
    }

    private void handleWithdraw(WithdrawView view) {
        String accountNumber = view.getAccountNumber();
        String amountStr = view.getAmount();

        if (accountNumber.isEmpty() || amountStr.isEmpty()) {
            view.setMessage("Please fill in all fields", true);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            Account account = findAccount(accountNumber);

            if (account == null) {
                view.setMessage("Account not found", true);
                return;
            }

            if (amount <= 0) {
                view.setMessage("Amount must be positive", true);
                return;
            }

            if (!account.canWithdraw()) {
                view.setMessage("Withdrawals not allowed on this account type", true);
                return;
            }

            // Special check for Investment Account minimum balance
            if (account instanceof InvestmentAccount) {
                InvestmentAccount investmentAccount = (InvestmentAccount) account;
                double maxWithdrawable = investmentAccount.getMaxWithdrawableAmount();

                if (amount > maxWithdrawable) {
                    view.setMessage(String.format(
                            "Investment accounts must maintain BWP 500.00 minimum balance.\n\n" +
                                    "Current Balance: BWP %.2f\n" +
                                    "Maximum Withdrawable: BWP %.2f\n\n" +
                                    "Please enter a smaller amount.",
                            account.getBalance(),
                            maxWithdrawable
                    ), true);
                    return;
                }
            }

            if (account.getBalance() < amount) {
                view.setMessage("Insufficient funds. Balance: BWP " +
                        String.format("%.2f", account.getBalance()), true);
                return;
            }

            int transactionsBefore = account.getTransactions().size();
            account.withdraw(amount);

            // Check if withdrawal actually happened (might have been blocked by minimum balance)
            if (account.getTransactions().size() == transactionsBefore) {
                view.setMessage("Withdrawal failed. Please check account requirements.", true);
                return;
            }

            //  SAVE TO FILES
            accountDAO.updateAccountBalance(accountNumber, account.getBalance());

            // Save the new transaction
            List<Transaction> transactions = account.getTransactions();
            if (transactions.size() > transactionsBefore) {
                transactionDAO.saveTransaction(transactions.get(transactions.size() - 1));
            }

            view.setMessage("✅ Successfully withdrew BWP " + String.format("%.2f", amount) +
                    "\nNew balance: BWP " + String.format("%.2f", account.getBalance()) +
                    "\nSaved to file!", false);

            // Clear fields after 2 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(() -> view.clearFields());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (NumberFormatException e) {
            view.setMessage("Invalid amount format", true);
        }
    }

    private void handleViewHistory(TransactionHistoryView view) {
        String accountNumber = view.getAccountNumber();

        if (accountNumber.isEmpty()) {
            view.setAccountInfo("Please enter an account number");
            return;
        }

        Account account = findAccount(accountNumber);

        if (account == null) {
            view.setAccountInfo("Account not found");
            view.setHistoryText("");
            return;
        }

        view.setAccountInfo("Account: " + accountNumber + " | Holder: " +
                account.getCustomer().getFullName() + " | Balance: BWP " +
                String.format("%.2f", account.getBalance()));

        if (account.getTransactions().isEmpty()) {
            view.setHistoryText("No transactions found for this account.");
            return;
        }

        StringBuilder history = new StringBuilder();
        history.append("═══════════════════════════════════════════════════════════\n");
        history.append("                    TRANSACTION HISTORY\n");
        history.append("═══════════════════════════════════════════════════════════\n\n");

        int count = 1;
        for (Transaction t : account.getTransactions()) {
            history.append(String.format("Transaction #%d\n", count++));
            history.append("───────────────────────────────────────────────────────────\n");
            history.append(t.getTransactionDetails()).append("\n");
            history.append("───────────────────────────────────────────────────────────\n\n");
        }

        view.setHistoryText(history.toString());
    }

    private Account findAccount(String accountNumber) {
        for (Account account : bank.getAllAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}