package com.phonecare.phonecaresystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class PhoneCareLogic {

    PhoneCareDatabase database = new PhoneCareDatabase();

    DialogWindow dialog = new DialogWindow();
    public void repairPhone(){
        Stage window = new Stage(StageStyle.UTILITY);

        AnchorPane root = new AnchorPane();

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(15);
        grid.setPadding(new Insets(20.0,20.0,20.0,20.0));

        Label name = new Label("Phone Name");
        name.setFont(Font.font(18.0));
        TextField phoneName = new TextField();
        phoneName.setFont(Font.font(20.0));
        grid.add(name, 0, 0);
        grid.add(phoneName,1,0);

        Label model_No = new Label("Model No");
        model_No.setFont(Font.font(18.0));
        TextField modelNo = new TextField();
        modelNo.setFont(Font.font(20.0));
        grid.add(model_No, 0,1);
        grid.add(modelNo, 1,1);

        Label issue = new Label("Problem");
        issue.setFont(Font.font(18.0));
        ComboBox<String> problems = new ComboBox<>();
        problems.getItems().addAll(
                "Charging Port", "Charging Plate", "Flex Cable",
                "MouthPiece", "Earpiece", "Speaker", "Network Issues", "Water Damage",
                "Motherboard Issues", "Screen Replacement", "FRP", "Password Recovery", "Software problems",
                "Camera Lens", "Inflamed ICs", "Service", "Other"
        );
        problems.setValue("Charging Port");
        problems.setEditable(true);
        problems.setMinWidth(57);
        problems.setMinHeight(41);
        grid.add(issue,0,2);
        grid.add(problems, 1,2);

        Label cost = new Label("Cost");
        cost.setFont(Font.font(18.0));
        TextField T_cost = new TextField();
        T_cost.appendText("0");
        T_cost.setOnMouseClicked(e->T_cost.clear());
        T_cost.setPromptText("Enter cost to repair phone");
        T_cost.setFont(Font.font(20.0));
        grid.add(cost, 0,3);
        grid.add(T_cost, 1,3);

        Label paid = new Label("Paid");
        paid.setFont(Font.font(18.0));
        TextField amountPaid = new TextField();
        amountPaid.appendText("0");
        amountPaid.setOnMouseClicked(e->amountPaid.clear());
        amountPaid.setPromptText("amount paid by the client");
        amountPaid.setFont(Font.font(20.0));
        grid.add(paid, 0,4);
        grid.add(amountPaid, 1,4);

        Label profit = new Label("Profit");
        profit.setFont(Font.font(18.0));
        TextField profitGenerated = new TextField();
        profitGenerated.setFont(Font.font(18.0));
        profitGenerated.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                profitGenerated.clear();
                int amount_paid = Integer.parseInt(amountPaid.getText());
                int totalCost = Integer.parseInt(T_cost.getText());
                int profit = amount_paid - totalCost;
                profitGenerated.appendText(String.valueOf(profit));
            }
        });
        grid.add(profit, 0,5);
        grid.add(profitGenerated, 1,5);

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setMinWidth(50);
        datePicker.setMinHeight(41);
        grid.add(date, 0,6);
        grid.add(datePicker, 1,6);

        Label vendor = new Label("Vendor");
        vendor.setFont(Font.font(18.0));
        ComboBox<String> vendorList = new ComboBox<>();
        vendorList.getItems().addAll(
                "Delju Spares",
                "Jascom Spares",
                "HQ",
                "Changa Electronics",
                "Midlands",
                "Sam Ventures",
                "Arafat Ventures",
                "None",
                "Other"
        );
        vendorList.setValue("Delju Spares");
        vendorList.setMinWidth(57);
        vendorList.setMinHeight(41);
        vendorList.setEditable(true);
        grid.add(vendor, 0,7);
        grid.add(vendorList,1,7);

        HBox btnLayout = new HBox(30);
        Button cancel = new Button("Cancel");
        cancel.setFont(Font.font(20.0));
        cancel.setOnAction(e-> window.close());
        Button submit = new Button("Submit");
        submit.setFont(Font.font(20.0));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Boolean insert = database.repairedPhoneData(
                        phoneName.getText(), modelNo.getText(),problems.getValue(),
                        Integer.parseInt(T_cost.getText()),Integer.parseInt(amountPaid.getText()),
                        Integer.parseInt(profitGenerated.getText()), String.valueOf(datePicker.getValue()),vendorList.getValue()
                );
                if (insert){
                    dialog.showDialog("Database Operations", phoneName.getText()+" Has been Inserted Successfully");
                    window.close();
                }else {
                    dialog.showDialog("Database Operation", "Database Operation Fail, Try Later");
                }
            }
        });
        btnLayout.getChildren().addAll(submit,cancel);
        grid.add(btnLayout,1,9);

        root.getChildren().add(grid);
        AnchorPane.setLeftAnchor(grid, 80.0);

        Scene scene = new Scene(root, 587, 518,true);
        window.setScene(scene);
        window.setTitle("Phone Details");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.showAndWait();
    }

    public void sellStock(){
        Stage window = new Stage(StageStyle.UTILITY);

        AnchorPane root = new AnchorPane();

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20.0,20.0,20.0,20.0));

        Label name = new Label("Item Name");
        name.setFont(Font.font(18.0));
        ComboBox<String> itemName = new ComboBox<>();
        itemName.getItems().addAll(
                "Screen Protector Normal",
                "Screen Protector 3D",
                "Screen Protector Mirror",
                "Cover",
                "Touch Screen/Sensor",
                "LCD",
                "Kaduda Screen",
                "Complete Screen",
                "Charger",
                "Charging Plate",
                "Charging port",
                "Mouth Piece",
                "Speaker",
                "Laptop",
                "Earphone",
                "Computer Accessory",
                "Other"
        );
        itemName.setMinWidth(50);
        itemName.setMinHeight(41);
        itemName.setValue("Screen Protector Normal");
        grid.add(name,0,0);
        grid.add(itemName, 1,0);

        Label quantity = new Label("Quantity");
        quantity.setFont(Font.font(18.0));
        TextField itemQuantity = new TextField();
        itemQuantity.appendText("0");
        itemQuantity.setOnMouseClicked(e->itemQuantity.clear());
        itemQuantity.setFont(Font.font(20.0));
        grid.add(quantity, 0,1);
        grid.add(itemQuantity, 1,1);

        Label price_per = new Label("Price Per");
        price_per.setFont(Font.font(18.0));
        TextField pricePer = new TextField();
        pricePer.appendText("0");
        pricePer.setOnMouseClicked(e->pricePer.clear());
        pricePer.setFont(Font.font(20.0));
        pricePer.setPromptText("Wholesale Price Per Item");
        grid.add(price_per, 0,2);
        grid.add(pricePer, 1,2);

        Label total = new Label("Total Cost");
        total.setFont(Font.font(18.0));
        TextField totalCost = new TextField();
        totalCost.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int item_bought = Integer.parseInt(itemQuantity.getText());
                int price_per = Integer.parseInt(pricePer.getText());
                int t_cost = item_bought * price_per;
                totalCost.appendText(String.valueOf(t_cost));
            }
        });
        totalCost.setFont(Font.font(20.0));
        grid.add(total, 0,3);
        grid.add(totalCost, 1,3);

        Label amount_paid = new Label("Amount Paid");
        amount_paid.setFont(Font.font(18.0));
        TextField amountPaid = new TextField();
        amountPaid.appendText("0");
        amountPaid.setOnMouseClicked(e->amountPaid.clear());
        amountPaid.setFont(Font.font(20.0));
        amountPaid.setPromptText("Amount paid by buyer");
        grid.add(amount_paid, 0,4);
        grid.add(amountPaid, 1,4);

        Label profit = new Label("Profit");
        profit.setFont(Font.font(18.0));
        TextField profitGenerated = new TextField();
        profitGenerated.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int profit = Integer.parseInt(amountPaid.getText()) - Integer.parseInt(totalCost.getText());
                profitGenerated.appendText(String.valueOf(profit));
            }
        });
        profitGenerated.setFont(Font.font(20.0));
        grid.add(profit, 0,5);
        grid.add(profitGenerated, 1,5);

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setMinHeight(41);
        datePicker.setMinWidth(50);
        grid.add(date, 0,6);
        grid.add(datePicker, 1,6);

        HBox btnLayout = new HBox(30);
        Button submit = new Button("Submit");
        submit.setFont(Font.font(20.0));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean isSold = database.insertSales(
                        itemName.getValue(), Integer.parseInt(itemQuantity.getText()), Integer.parseInt(pricePer.getText()),
                        Integer.parseInt(totalCost.getText()), Integer.parseInt(amountPaid.getText()), Integer.parseInt(profitGenerated.getText()),
                        String.valueOf(datePicker.getValue())
                );
                if (isSold){
                    dialog.showDialog("Sell Item Status", itemQuantity.getText()+" "+itemName.getValue()+" Sold Successfully");
                    window.close();
                }else {
                    dialog.showDialog("FAIL", itemName.getValue()+" not stocked yet, please stock it  and try again");
                    window.close();
                }
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setFont(Font.font(20.0));
        cancel.setOnAction(e->window.close());
        btnLayout.getChildren().addAll(submit,cancel);
        grid.add(btnLayout, 1,9);

        root.getChildren().add(grid);
        AnchorPane.setLeftAnchor(grid, 80.0);

        Scene scene = new Scene(root, 587,518,true);
        window.setResizable(false);
        window.setTitle("Sell Item");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    public void addStock(){
        Stage window = new Stage(StageStyle.UTILITY);

        AnchorPane root = new AnchorPane();

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(15);
        grid.setPadding(new Insets(20.0,20.0,20.0,20.0));

        Label name = new Label("Item Name");
        name.setFont(Font.font(18.0));
        ComboBox<String> itemName = new ComboBox<>();
        itemName.getItems().addAll(
                "Screen Protector Normal",
                "Screen Protector 3D",
                "Screen Protector Mirror",
                "Cover",
                "Touch Screen/Sensor",
                "LCD",
                "Kaduda Screen",
                "Complete Screen",
                "Charger",
                "Charging Plate",
                "Charging port",
                "Mouth Piece",
                "Speaker",
                "Laptop",
                "Earphone",
                "Computer Accessory",
                "Other"
        );
        itemName.setEditable(true);
        itemName.setMinWidth(50);
        itemName.setMinHeight(41);
        itemName.setValue("Screen Protector Normal");
        grid.add(name, 0,0);
        grid.add(itemName,1,0);

        Label quantity = new Label("Quantity");
        quantity.setFont(Font.font(18.0));
        TextField itemQuantity = new TextField();
        itemQuantity.setFont(Font.font(20.0));
        itemQuantity.appendText("0");
        itemQuantity.setOnMouseClicked(e->itemQuantity.clear());
        itemQuantity.setPromptText("Number Bought");
        grid.add(quantity, 0,1);
        grid.add(itemQuantity, 1,1);

        Label cost = new Label("Cost Per");
        cost.setFont(Font.font(18.0));
        TextField costPer = new TextField();
        costPer.setFont(Font.font(20.0));
        costPer.appendText("0");
        costPer.setOnMouseClicked(e->costPer.clear());
        costPer.setPromptText("Cost Per Item");
        grid.add(cost, 0, 2);
        grid.add(costPer, 1, 2);

        Label total = new Label("Total");
        total.setFont(Font.font(18.0));
        TextField totalCost = new TextField();
        totalCost.setFont(Font.font(20.0));
        totalCost.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                totalCost.clear();
                int quantity_bought = Integer.parseInt(itemQuantity.getText());
                int cost_per_item = Integer.parseInt(costPer.getText());
                int total_cost = cost_per_item * quantity_bought;
                totalCost.appendText(String.valueOf(total_cost));
            }
        });
        grid.add(total, 0,3);
        grid.add(totalCost,1,3);

        Label vendor = new Label("Vendor");
        vendor.setFont(Font.font(18.0));
        ComboBox<String> vendorList = new ComboBox<>();
        vendorList.getItems().addAll(
                "Jascom Spares",
                "Sam Ventures",
                "Delju Spares",
                "Arafat Ventures",
                "Other"
        );
        vendorList.setValue("Jascom Spares");
        vendorList.setEditable(true);
        vendorList.setMinWidth(50);
        vendorList.setMinHeight(41);
        grid.add(vendor,0,4);
        grid.add(vendorList, 1,4);

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setMinWidth(50);
        datePicker.setMinHeight(41);
        datePicker.setValue(LocalDate.now());
        grid.add(date, 0,5);
        grid.add(datePicker, 1,5);

        HBox btnLayout = new HBox(30);
        Button submit = new Button("Submit");
        submit.setFont(Font.font(20.0));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Boolean isStockAdded = database.insertAddedStock(
                        itemName.getValue(), Integer.parseInt(itemQuantity.getText()),Integer.parseInt(costPer.getText()),
                        Integer.parseInt(totalCost.getText()),vendorList.getValue(), String.valueOf(datePicker.getValue())
                );
                if (isStockAdded){
                    dialog.showDialog("Stock Addition OK", itemQuantity.getText()+" "+itemName.getValue()+" has been added successfully");
                    window.close();
                }else {
                    dialog.showDialog("Stock Addition FAIL", itemName.getValue() + " not added, try later");
                    window.close();
                }
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setFont(Font.font(20.0));
        cancel.setOnAction(e-> window.close());
        btnLayout.getChildren().addAll(submit, cancel);
        grid.add(btnLayout, 1,7);

        root.getChildren().add(grid);
        AnchorPane.setLeftAnchor(grid, 100.0);

        Scene scene = new Scene(root, 587, 480,true);
        window.setTitle("Add Stock");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    public void addReturn(){
        Stage window = new Stage(StageStyle.UTILITY);

        AnchorPane root = new AnchorPane();

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20.0,20.0,20.0,20.0));

        Label name = new Label("Item Name");
        name.setFont(Font.font(18.0));
        TextField itemName = new TextField();
        itemName.setFont(Font.font(20.0));
        grid.add(name,0,0);
        grid.add(itemName,1,0);

        Label type = new Label("Item Type");
        type.setFont(Font.font(18.0));
        ComboBox<String> itemType = new ComboBox<>();
        itemType.getItems().addAll(
                "Mobile Phone",
                "Computer",
                "Accessory",
                "Spare Part",
                "Other"
        );
        itemType.setEditable(true);
        itemType.setMinHeight(41);
        itemType.setMinWidth(50);
        itemType.setValue("Mobile Phone");
        grid.add(type,0,1);
        grid.add(itemType,1,1);

        Label reason = new Label("Problem");
        reason.setFont(Font.font(18.0));
        ComboBox<String> problem = new ComboBox<>();
        problem.getItems().addAll(
                "Charging Port", "Charging Plate", "Flex Cable",
                "MouthPiece", "Earpiece", "Speaker", "Network Issues", "Water Damage",
                "Motherboard Issues", "Screen Replacement", "FRP", "Password Recovery", "Software problems",
                "Camera Lens", "Inflamed ICs", "Service", "Other"
        );
        problem.setEditable(true);
        problem.setValue("Failed System");
        problem.setMinHeight(41);
        problem.setMinWidth(50);
        grid.add(reason, 0,2);
        grid.add(problem, 1,2);

        Label return_on = new Label("Return On");
        return_on.setFont(Font.font(18.0));
        ComboBox<String> returnOn = new ComboBox<>();
        returnOn.getItems().addAll("Full Warranty", "Partial Warranty", "No Warranty", "Other");
        returnOn.setValue("Full Warranty");
        returnOn.setMinWidth(50);
        returnOn.setMinHeight(41);
        returnOn.setEditable(true);
        grid.add(return_on, 0,3);
        grid.add(returnOn, 1,3);

        Label cost = new Label("Cost");
        cost.setFont(Font.font(18.0));
        TextField totalCost = new TextField();
        totalCost.setFont(Font.font(20.0));
        totalCost.setOnMouseClicked(e->totalCost.clear());
        totalCost.appendText("0");
        grid.add(cost,0,4);
        grid.add(totalCost, 1,4);

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setMinWidth(50);
        datePicker.setMinHeight(41);
        grid.add(date, 0,5);
        grid.add(datePicker,1,5);

        HBox btnLayout = new HBox(30);
        Button submit = new Button("Submit");
        submit.setFont(Font.font(20.0));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Boolean isAdded = database.insertAddedReturn(
                        itemName.getText(), itemType.getValue(), problem.getValue(), returnOn.getValue(),
                        Integer.parseInt(totalCost.getText()), String.valueOf(datePicker.getValue())
                );
                if (isAdded){
                    dialog.showDialog("Returns Addition", "Return of "+itemName.getText()+" has been added Successfully ");
                    window.close();
                }else {
                    dialog.showDialog("Returns Addition", "Return of "+itemName.getText()+" addition, FAIL!");
                    window.close();
                }
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setFont(Font.font(20.0));
        cancel.setOnAction(e-> window.close());
        btnLayout.getChildren().addAll(submit, cancel);
        grid.add(btnLayout, 1,6);

        AnchorPane.setLeftAnchor(grid, 50.0);
        root.getChildren().add(grid);
        Scene scene = new Scene(root,499,400,true);
        window.setTitle("Add Returns");
        window.setScene(scene);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    public void addExpense(){
        Stage window = new Stage(StageStyle.UTILITY);

        AnchorPane root = new AnchorPane();

        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(10);
        grid.setPadding(new Insets(20.0,20.0,20.0,20.0));

        Label name = new Label("Expense Name");
        name.setFont(Font.font(18.0));
        ComboBox<String> expenseName = new ComboBox<>();
        expenseName.getItems().addAll(
                "Rent",
                "Breakfast",
                "Lunch",
                "Wages",
                "Fuel",
                "Electricity",
                "License Fees",
                "KRA Fees",
                "Fines and Legal Fees",
                "Debt",
                "Miscellaneous",
                "Other"
        );
        expenseName.setEditable(true);
        expenseName.setMinHeight(41);
        expenseName.setMinWidth(50);
        expenseName.setValue("Rent");
        grid.add(name,0,0);
        grid.add(expenseName,1,0);

        Label cost = new Label("Cost");
        cost.setFont(Font.font(18.0));
        TextField totalCost = new TextField();
        totalCost.appendText("0");
        totalCost.setOnMouseClicked(e->totalCost.clear());
        totalCost.setFont(Font.font(20.0));
        grid.add(cost,0,1);
        grid.add(totalCost, 1,1);

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setMinWidth(50);
        datePicker.setMinHeight(41);
        grid.add(date, 0,2);
        grid.add(datePicker, 1,2);

        HBox btnLayout = new HBox(30);
        Button submit = new Button("Submit");
        submit.setFont(Font.font(20.0));
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Boolean isAdded = database.insertAddedExpense(expenseName.getValue(), Integer.parseInt(totalCost.getText()),
                        String.valueOf(datePicker.getValue()));
                if (isAdded){
                    dialog.showDialog("Expenses Addition", expenseName.getValue()+" expense has been added successfully");
                    window.close();
                }else {
                    dialog.showDialog("Expense Addition", expenseName.getValue()+" expense addition FAIL!");
                    window.close();
                }
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setFont(Font.font(20.0));
        cancel.setOnAction(e-> window.close());
        btnLayout.getChildren().addAll(submit, cancel);
        grid.add(btnLayout, 1,3);

        AnchorPane.setLeftAnchor(grid, 40.0);
        root.getChildren().add(grid);
        Scene scene = new Scene(root,499,300,true);
        window.setTitle("Add Expenses");
        window.setScene(scene);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }
}
