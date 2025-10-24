package com.bank.controller;

import com.bank.model.*;
import com.bank.view.*;
import javafx.scene.control.Alert;

public class TransactionController {
    private Bank bank;

    public TransactionController(Bank bank) {
        this.bank = bank;
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

            account.deposit(amount);
            view.setMessage("Successfully deposited BWP " + String.format("%.2f", amount) +
                    "\nNew balance: BWP " + String.format("%.2f", account.getBalance()), false);

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

            if (account.getBalance() < amount) {
                view.setMessage("Insufficient funds. Balance: BWP " +
                        String.format("%.2f", account.getBalance()), true);
                return;
            }

            account.withdraw(amount);
            view.setMessage("Successfully withdrew BWP " + String.format("%.2f", amount) +
                    "\nNew balance: BWP " + String.format("%.2f", account.getBalance()), false);

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
        for (Transaction t : account.getTransactions()) {
            history.append(t.getTransactionDetails()).append("\n\n");
            history.append("=====================================\n\n");
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