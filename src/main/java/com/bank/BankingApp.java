package com.bank;

import com.bank.controller.MainMenuController;
import com.bank.model.Bank;
import com.bank.view.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class BankingApp extends Application {

    private Bank bank;

    @Override
    public void start(Stage primaryStage) {
        // Create the bank
        bank = new Bank("First National Bank");

        // Create main menu view
        MainMenuView mainMenuView = new MainMenuView(primaryStage);

        // Create controllers and pass them the bank and views
        MainMenuController mainMenuController = new MainMenuController(bank, mainMenuView);

        // Show the main menu
        mainMenuView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}