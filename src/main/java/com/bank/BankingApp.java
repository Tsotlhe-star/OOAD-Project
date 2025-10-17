package com.bank;

import com.bank.view.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class BankingApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create and show main menu
        MainMenuView mainMenu = new MainMenuView(primaryStage);
        mainMenu.show();

        // NOTE: Controllers will be added in Week Oct 20-24
        // For now, this just displays the GUI
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// True