package com.phonecare.phonecaresystem;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogWindow {
    public void showDialog(String title, String message){
        Stage window = new Stage(StageStyle.DECORATED);

        AnchorPane root = new AnchorPane();

        Label Message = new Label(message);
        Message.setPrefWidth(272);
        Message.setPrefHeight(30);
        Message.setFont(Font.font(17.0));
        Message.setTextFill(Color.RED);
        HBox labelLayout = new HBox(10);
        labelLayout.getChildren().add(Message);
        AnchorPane.setTopAnchor(labelLayout, 50.0);
        AnchorPane.setLeftAnchor(labelLayout, 50.0);
        Message.setMinWidth(500);

        Button closebtn = new Button("CLOSE");
        closebtn.setOnAction(e-> window.close());
        closebtn.setBackground(Background.fill(Color.RED));
        closebtn.setPrefWidth(111);
        closebtn.setPrefHeight(37);
        AnchorPane.setLeftAnchor(closebtn, 170.0);
        AnchorPane.setBottomAnchor(closebtn, 11.0);
        closebtn.setFont(Font.font(18.0));

        root.getChildren().addAll(labelLayout, closebtn);
        root.setBackground(Background.fill(Color.grayRgb(35)));
        Scene dialogScene = new Scene(root, 600, 154, true);
        window.setScene(dialogScene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setResizable(false);
        window.showAndWait();
    }
}
