package com.bank.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransactionHistoryView {
    private Stage stage;
    private Scene scene;

    private TextField accountNumberField;
    private Button searchBtn;
    private Button closeBtn;
    private TextArea historyArea;
    private Label accountInfoLabel;

    public TransactionHistoryView() {
        this.stage = new Stage();
        createView();
        setupEventHandlers();
    }

    private void createView() {
        Label titleLabel = new Label("Transaction History");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(10));

        Label searchLabel = new Label("Account Number:");
        accountNumberField = new TextField();
        accountNumberField.setPromptText("Enter account number");
        searchBtn = new Button("Search");

        searchBox.getChildren().addAll(searchLabel, accountNumberField, searchBtn);

        accountInfoLabel = new Label("");
        accountInfoLabel.setStyle("-fx-font-weight: bold; -fx-padding: 10;");

        historyArea = new TextArea();
        historyArea.setEditable(false);
        historyArea.setWrapText(true);
        historyArea.setPrefHeight(300);
        historyArea.setPromptText("Enter account number and click Search to view transactions");

        closeBtn = new Button("Close");
        HBox buttonBox = new HBox(closeBtn);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        VBox topBox = new VBox(10);
        topBox.getChildren().addAll(titleLabel, searchBox, accountInfoLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));

        BorderPane layout = new BorderPane();
        layout.setTop(topBox);
        layout.setCenter(historyArea);
        layout.setBottom(buttonBox);
        layout.setPadding(new Insets(10));

        scene = new Scene(layout, 600, 450);
        stage.setTitle("Transaction History");
        stage.setScene(scene);
    }

    private void setupEventHandlers() {
        closeBtn.setOnAction(e -> {
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

    public Button getSearchBtn() {
        return searchBtn;
    }

    public Button getCloseBtn() {
        return closeBtn;
    }

    public void setAccountInfo(String info) {
        accountInfoLabel.setText(info);
    }

    public void setHistoryText(String text) {
        historyArea.setText(text);
    }

    public void clearHistory() {
        historyArea.clear();
        accountInfoLabel.setText("");
    }
}