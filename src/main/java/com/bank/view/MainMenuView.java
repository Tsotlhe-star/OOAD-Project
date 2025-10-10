package com.bank.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView {
    private Stage stage;
    private Scene scene;

    // Buttons for main menu
    private Button createAccountBtn;
    private Button depositBtn;
    private Button withdrawBtn;
    private Button viewHistoryBtn;
    private Button exitBtn;

    public MainMenuView(Stage stage) {
        this.stage = stage;
        createView();
        setupEventHandlers();
    }

    private void createView() {
        // Create title label
        Label titleLabel = new Label("Banking System");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create buttons
        createAccountBtn = new Button("Create New Account");
        depositBtn = new Button("Deposit Money");
        withdrawBtn = new Button("Withdraw Money");
        viewHistoryBtn = new Button("View Transaction History");
        exitBtn = new Button("Exit");

        // Set button sizes
        createAccountBtn.setPrefWidth(200);
        depositBtn.setPrefWidth(200);
        withdrawBtn.setPrefWidth(200);
        viewHistoryBtn.setPrefWidth(200);
        exitBtn.setPrefWidth(200);

        // Create layout
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                titleLabel,
                createAccountBtn,
                depositBtn,
                withdrawBtn,
                viewHistoryBtn,
                exitBtn
        );

        // Create scene
        scene = new Scene(layout, 400, 400);
        stage.setTitle("Banking System - Main Menu");
        stage.setScene(scene);
    }

    private void setupEventHandlers() {
        // Create Account button handler
        createAccountBtn.setOnAction(e -> {
            CreateAccountView createAccountView = new CreateAccountView();
            createAccountView.show();
        });

        // Deposit button handler
        depositBtn.setOnAction(e -> {
            DepositView depositView = new DepositView();
            depositView.show();
        });

        // Withdraw button handler
        withdrawBtn.setOnAction(e -> {
            WithdrawView withdrawView = new WithdrawView();
            withdrawView.show();
        });

        // View History button handler
        viewHistoryBtn.setOnAction(e -> {
            TransactionHistoryView historyView = new TransactionHistoryView();
            historyView.show();
        });

        // Exit button handler
        exitBtn.setOnAction(e -> {
            stage.close();
        });
    }

    public void show() {
        stage.show();
    }

    // Getters for buttons (controllers will use these)
    public Button getCreateAccountBtn() {
        return createAccountBtn;
    }

    public Button getDepositBtn() {
        return depositBtn;
    }

    public Button getWithdrawBtn() {
        return withdrawBtn;
    }

    public Button getViewHistoryBtn() {
        return viewHistoryBtn;
    }

    public Button getExitBtn() {
        return exitBtn;
    }
}