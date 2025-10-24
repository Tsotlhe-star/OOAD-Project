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

    private Button createAccountBtn;
    private Button depositBtn;
    private Button withdrawBtn;
    private Button viewHistoryBtn;
    private Button processInterestBtn;
    private Button exitBtn;

    public MainMenuView(Stage stage) {
        this.stage = stage;
        createView();
    }

    private void createView() {
        Label titleLabel = new Label("Banking System");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label subtitleLabel = new Label("Welcome to First National Bank");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        createAccountBtn = new Button("Create New Account");
        depositBtn = new Button("Deposit Money");
        withdrawBtn = new Button("Withdraw Money");
        viewHistoryBtn = new Button("View Transaction History");
        processInterestBtn = new Button("Process Monthly Interest");
        exitBtn = new Button("Exit");

        // Style buttons
        String buttonStyle = "-fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5;";
        createAccountBtn.setStyle(buttonStyle + "-fx-background-color: #3498db; -fx-text-fill: white;");
        depositBtn.setStyle(buttonStyle + "-fx-background-color: #2ecc71; -fx-text-fill: white;");
        withdrawBtn.setStyle(buttonStyle + "-fx-background-color: #e74c3c; -fx-text-fill: white;");
        viewHistoryBtn.setStyle(buttonStyle + "-fx-background-color: #9b59b6; -fx-text-fill: white;");
        processInterestBtn.setStyle(buttonStyle + "-fx-background-color: #f39c12; -fx-text-fill: white;");
        exitBtn.setStyle(buttonStyle + "-fx-background-color: #95a5a6; -fx-text-fill: white;");

        createAccountBtn.setPrefWidth(250);
        depositBtn.setPrefWidth(250);
        withdrawBtn.setPrefWidth(250);
        viewHistoryBtn.setPrefWidth(250);
        processInterestBtn.setPrefWidth(250);
        exitBtn.setPrefWidth(250);

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #ecf0f1;");
        layout.getChildren().addAll(
                titleLabel,
                subtitleLabel,
                createAccountBtn,
                depositBtn,
                withdrawBtn,
                viewHistoryBtn,
                processInterestBtn,
                exitBtn
        );

        scene = new Scene(layout, 450, 550);
        stage.setTitle("Banking System - Main Menu");
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

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

    public Button getProcessInterestBtn() {
        return processInterestBtn;
    }

    public Button getExitBtn() {
        return exitBtn;
    }
}