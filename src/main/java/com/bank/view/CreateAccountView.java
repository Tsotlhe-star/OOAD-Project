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

    public CreateAccountView() {
        this.stage = new Stage();
        createView();
        setupEventHandlers();
    }

    private void createView() {
        Label titleLabel = new Label("Create New Account");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label customerLabel = new Label("Customer Information");
        customerLabel.setStyle("-fx-font-weight: bold;");
        grid.add(customerLabel, 0, 0, 2, 1);

        grid.add(new Label("First Name:"), 0, 1);
        firstNameField = new TextField();
        grid.add(firstNameField, 1, 1);

        grid.add(new Label("Surname:"), 0, 2);
        surnameField = new TextField();
        grid.add(surnameField, 1, 2);

        grid.add(new Label("Address:"), 0, 3);
        addressField = new TextField();
        grid.add(addressField, 1, 3);

        grid.add(new Label("Phone:"), 0, 4);
        phoneField = new TextField();
        grid.add(phoneField, 1, 4);

        Label accountLabel = new Label("Account Information");
        accountLabel.setStyle("-fx-font-weight: bold;");
        grid.add(accountLabel, 0, 5, 2, 1);

        grid.add(new Label("Account Type:"), 0, 6);
        accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll("Savings", "Investment", "Cheque");
        accountTypeBox.setValue("Savings");
        grid.add(accountTypeBox, 1, 6);

        initialDepositLabel = new Label("Initial Deposit:");
        grid.add(initialDepositLabel, 0, 7);
        initialDepositField = new TextField();
        initialDepositField.setPromptText("Min BWP 500 for Investment");
        grid.add(initialDepositField, 1, 7);
        initialDepositLabel.setVisible(false);
        initialDepositField.setVisible(false);

        companyNameLabel = new Label("Company Name:");
        grid.add(companyNameLabel, 0, 8);
        companyNameField = new TextField();
        grid.add(companyNameField, 1, 8);
        companyNameLabel.setVisible(false);
        companyNameField.setVisible(false);

        companyAddressLabel = new Label("Company Address:");
        grid.add(companyAddressLabel, 0, 9);
        companyAddressField = new TextField();
        grid.add(companyAddressField, 1, 9);
        companyAddressLabel.setVisible(false);
        companyAddressField.setVisible(false);

        createBtn = new Button("Create Account");
        cancelBtn = new Button("Cancel");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(createBtn, cancelBtn);
        grid.add(buttonBox, 0, 10, 2, 1);

        scene = new Scene(grid, 450, 500);
        stage.setTitle("Create Account");
        stage.setScene(scene);
    }

    private void setupEventHandlers() {
        accountTypeBox.setOnAction(e -> {
            String selectedType = accountTypeBox.getValue();
            if (selectedType != null) {
                switch (selectedType) {
                    case "Investment":
                        showInvestmentFields();
                        break;
                    case "Cheque":
                        showChequeFields();
                        break;
                    default:
                        hideExtraFields();
                        break;
                }
            }
        });

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

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getSurname() {
        return surnameField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getAccountType() {
        return accountTypeBox.getValue();
    }

    public String getInitialDeposit() {
        return initialDepositField.getText();
    }

    public String getCompanyName() {
        return companyNameField.getText();
    }

    public String getCompanyAddress() {
        return companyAddressField.getText();
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

    public void clearFields() {
        firstNameField.clear();
        surnameField.clear();
        addressField.clear();
        phoneField.clear();
        initialDepositField.clear();
        companyNameField.clear();
        companyAddressField.clear();
        accountTypeBox.setValue("Savings");
    }
}