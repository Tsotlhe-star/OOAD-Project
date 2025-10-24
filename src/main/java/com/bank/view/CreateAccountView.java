package com.bank.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CreateAccountView {
    private Stage stage;
    private Scene scene;

    private TextField firstNameField;
    private TextField surnameField;
    private TextField addressField;
    private TextField phoneField;
    private ComboBox<String> accountTypeBox;
    private TextField initialDepositField;
    private TextField companyNameField;
    private TextField companyAddressField;

    private Button createBtn;
    private Button cancelBtn;

    private Label companyNameLabel;
    private Label companyAddressLabel;
    private Label initialDepositLabel;
    private Label messageLabel;

    public CreateAccountView() {
        this.stage = new Stage();
        createView();
    }

    private void createView() {
        Label titleLabel = new Label("Create New Account");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Customer Information
        Label customerLabel = new Label("Customer Information");
        customerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #34495e;");
        grid.add(customerLabel, 0, 0, 2, 1);

        grid.add(new Label("First Name:"), 0, 1);
        firstNameField = new TextField();
        firstNameField.setPromptText("Enter first name");
        grid.add(firstNameField, 1, 1);

        grid.add(new Label("Surname:"), 0, 2);
        surnameField = new TextField();
        surnameField.setPromptText("Enter surname");
        grid.add(surnameField, 1, 2);

        grid.add(new Label("Address:"), 0, 3);
        addressField = new TextField();
        addressField.setPromptText("Enter address");
        grid.add(addressField, 1, 3);

        grid.add(new Label("Phone:"), 0, 4);
        phoneField = new TextField();
        phoneField.setPromptText("Enter phone number");
        grid.add(phoneField, 1, 4);

        // Account Information
        Label accountLabel = new Label("Account Information");
        accountLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #34495e;");
        grid.add(accountLabel, 0, 5, 2, 1);

        grid.add(new Label("Account Type:"), 0, 6);
        accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll("SAVINGS", "INVESTMENT", "CHEQUE");
        accountTypeBox.setValue("SAVINGS");
        accountTypeBox.setPrefWidth(200);
        grid.add(accountTypeBox, 1, 6);

        // Initial Deposit (for Investment account)
        initialDepositLabel = new Label("Initial Deposit (BWP):");
        grid.add(initialDepositLabel, 0, 7);
        initialDepositField = new TextField();
        initialDepositField.setPromptText("Min BWP 500.00");
        grid.add(initialDepositField, 1, 7);
        initialDepositLabel.setVisible(false);
        initialDepositField.setVisible(false);

        // Company Info (for Cheque account)
        companyNameLabel = new Label("Company Name:");
        grid.add(companyNameLabel, 0, 8);
        companyNameField = new TextField();
        companyNameField.setPromptText("Enter company name");
        grid.add(companyNameField, 1, 8);
        companyNameLabel.setVisible(false);
        companyNameField.setVisible(false);

        companyAddressLabel = new Label("Company Address:");
        grid.add(companyAddressLabel, 0, 9);
        companyAddressField = new TextField();
        companyAddressField.setPromptText("Enter company address");
        grid.add(companyAddressField, 1, 9);
        companyAddressLabel.setVisible(false);
        companyAddressField.setVisible(false);

        // Message Label
        messageLabel = new Label("");
        messageLabel.setWrapText(true);
        grid.add(messageLabel, 0, 10, 2, 1);

        // Buttons
        createBtn = new Button("Create Account");
        createBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white;");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(createBtn, cancelBtn);
        grid.add(buttonBox, 0, 11, 2, 1);

        scene = new Scene(grid, 500, 550);
        stage.setTitle("Create Account");
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public String getFirstName() {
        return firstNameField.getText().trim();
    }

    public String getSurname() {
        return surnameField.getText().trim();
    }

    public String getAddress() {
        return addressField.getText().trim();
    }

    public String getPhone() {
        return phoneField.getText().trim();
    }

    public String getAccountType() {
        return accountTypeBox.getValue();
    }

    public String getInitialDeposit() {
        return initialDepositField.getText().trim();
    }

    public String getCompanyName() {
        return companyNameField.getText().trim();
    }

    public String getCompanyAddress() {
        return companyAddressField.getText().trim();
    }

    public Button getCreateBtn() {
        return createBtn;
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }

    public ComboBox<String> getAccountTypeBox() {
        return accountTypeBox;
    }

    public void showInvestmentFields() {
        initialDepositLabel.setVisible(true);
        initialDepositField.setVisible(true);
        companyNameLabel.setVisible(false);
        companyNameField.setVisible(false);
        companyAddressLabel.setVisible(false);
        companyAddressField.setVisible(false);
    }

    public void showChequeFields() {
        initialDepositLabel.setVisible(false);
        initialDepositField.setVisible(false);
        companyNameLabel.setVisible(true);
        companyNameField.setVisible(true);
        companyAddressLabel.setVisible(true);
        companyAddressField.setVisible(true);
    }

    public void hideExtraFields() {
        initialDepositLabel.setVisible(false);
        initialDepositField.setVisible(false);
        companyNameLabel.setVisible(false);
        companyNameField.setVisible(false);
        companyAddressLabel.setVisible(false);
        companyAddressField.setVisible(false);
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
        firstNameField.clear();
        surnameField.clear();
        addressField.clear();
        phoneField.clear();
        initialDepositField.clear();
        companyNameField.clear();
        companyAddressField.clear();
        accountTypeBox.setValue("SAVINGS");
        messageLabel.setText("");
        hideExtraFields();
    }
}