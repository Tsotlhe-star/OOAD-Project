package com.bank.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WithdrawView {
    private Stage stage;
    private Scene scene;

    private TextField accountNumberField;
    private TextField amountField;
    private Button withdrawBtn;
    private Button cancelBtn;
    private Label messageLabel;

    public WithdrawView() {
        this.stage = new Stage();
        createView();
        setupEventHandlers();
    }

    private void createView() {
        Label titleLabel = new Label("Withdraw Money");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Account Number:"), 0, 0);
        accountNumberField = new TextField();
        accountNumberField.setPromptText("Enter account number");
        grid.add(accountNumberField, 1, 0);

        grid.add(new Label("Amount (BWP):"), 0, 1);
        amountField = new TextField();
        amountField.setPromptText("Enter amount");
        grid.add(amountField, 1, 1);

        messageLabel = new Label("");
        messageLabel.setStyle("-fx-text-fill: green;");
        grid.add(messageLabel, 0, 2, 2, 1);

        withdrawBtn = new Button("Withdraw");
        cancelBtn = new Button("Cancel");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(withdrawBtn, cancelBtn);
        grid.add(buttonBox, 0, 3, 2, 1);

        scene = new Scene(grid, 400, 250);
        stage.setTitle("Withdraw Money");
        stage.setScene(scene);
    }

    private void setupEventHandlers() {
        cancelBtn.setOnAction(e -> {
            close();
        });
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public String getAccountNumber() {
        return accountNumberField.getText();
    }

    public String getAmount() {
        return amountField.getText();
    }

    public Button getWithdrawBtn() {
        return withdrawBtn;
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }

    public void setMessage(String message, boolean isError) {
        messageLabel.setText(message);
        if (isError) {
            messageLabel.setStyle("-fx-text-fill: red;");
        } else {
            messageLabel.setStyle("-fx-text-fill: green;");
        }
    }

    public void clearFields() {
        accountNumberField.clear();
        amountField.clear();
        messageLabel.setText("");
    }
}