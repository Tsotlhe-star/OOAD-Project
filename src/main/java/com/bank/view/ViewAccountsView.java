package com.bank.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewAccountsView {
    private Stage stage;
    private TextArea accountsArea;
    private Button closeBtn;

    public ViewAccountsView() {
        this.stage = new Stage();
        createView();
    }

    private void createView() {
        Label titleLabel = new Label("All Bank Accounts");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        accountsArea = new TextArea();
        accountsArea.setEditable(false);
        accountsArea.setWrapText(true);
        accountsArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        accountsArea.setPrefHeight(400);

        closeBtn = new Button("Close");
        closeBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-padding: 8 30;");

        VBox topBox = new VBox(10);
        topBox.getChildren().add(titleLabel);
        topBox.setAlignment(javafx.geometry.Pos.CENTER);
        topBox.setPadding(new Insets(10));

        VBox bottomBox = new VBox(10);
        bottomBox.getChildren().add(closeBtn);
        bottomBox.setAlignment(javafx.geometry.Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        BorderPane layout = new BorderPane();
        layout.setTop(topBox);
        layout.setCenter(accountsArea);
        layout.setBottom(bottomBox);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 600, 500);
        stage.setTitle("View All Accounts");
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public void setAccountsText(String text) {
        accountsArea.setText(text);
    }

    public Button getCloseBtn() {
        return closeBtn;
    }
}