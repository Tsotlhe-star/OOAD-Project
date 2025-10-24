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
    }

    private void createView() {
        Label titleLabel = new Label("Withdraw Money");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Account Number:"), 0, 0);
        accountNumberField = new TextField();
        accountNumberField.setPromptText("Enter account number");
        accountNumberField.setPrefWidth(200);
        grid.add(accountNumberField, 1, 0);

        grid.add(new Label("Amount (BWP):"), 0, 1);
        amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.setPrefWidth(200);
        grid.add(amountField, 1, 1);

        messageLabel = new Label("");
        messageLabel.setWrapText(true);
        messageLabel.setPrefWidth(350);
        grid.add(messageLabel, 0, 2, 2, 1);

        withdrawBtn = new Button("Withdraw");
        withdrawBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20;");
        cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-padding: 8 20;");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(withdrawBtn, cancelBtn);
        grid.add(buttonBox, 0, 3, 2, 1);

        scene = new Scene(grid, 400, 250);
        stage.setTitle("Withdraw Money");
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public String getAccountNumber() {
        return accountNumberField.getText().trim();
    }

    public String getAmount() {
        return amountField.getText().trim();
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
            messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        } else {
            messageLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
        }
    }

    public void clearFields() {
        accountNumberField.clear();
        amountField.clear();
        messageLabel.setText("");
    }
}