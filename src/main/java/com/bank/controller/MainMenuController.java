package com.bank.controller;

import com.bank.model.Bank;
import com.bank.view.*;
import com.bank.controller.MainMenuController;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class MainMenuController {
    private Bank bank;
    private MainMenuView mainMenuView;
    private com.bank.controller.AccountController accountController;
    private TransactionController transactionController;

    public MainMenuController(Bank bank, MainMenuView mainMenuView) {
        this.bank = bank;
        this.mainMenuView = mainMenuView;
        this.accountController = new AccountController(bank);
        this.transactionController = new TransactionController(bank);

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Create Account button
        mainMenuView.getCreateAccountBtn().setOnAction(e -> {
            accountController.showCreateAccountView();
        });

        // View All Accounts button
        mainMenuView.getViewAccountsBtn().setOnAction(e -> {
            accountController.showViewAccountsView();
        });

        // Deposit button
        mainMenuView.getDepositBtn().setOnAction(e -> {
            transactionController.showDepositView();
        });

        // Withdraw button
        mainMenuView.getWithdrawBtn().setOnAction(e -> {
            transactionController.showWithdrawView();
        });

        // View History button
        mainMenuView.getViewHistoryBtn().setOnAction(e -> {
            transactionController.showTransactionHistoryView();
        });

        // Process Interest button
        mainMenuView.getProcessInterestBtn().setOnAction(e -> {
            processInterest();
        });

        // Exit button
        mainMenuView.getExitBtn().setOnAction(e -> {
            Platform.exit();
        });
    }

    private void processInterest() {
        bank.processInterestPayments();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Interest Processed");
        alert.setHeaderText("Monthly Interest Payment");
        alert.setContentText("Interest has been paid to all eligible accounts!");
        alert.showAndWait();
    }
}
