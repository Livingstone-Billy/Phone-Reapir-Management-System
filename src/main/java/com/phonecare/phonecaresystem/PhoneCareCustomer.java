package com.phonecare.phonecaresystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class PhoneCareCustomer {

    PhoneCareLogic logic = new PhoneCareLogic();
    PhoneCareView view = new PhoneCareView();

    PhoneCareDatabase database = new PhoneCareDatabase();
    PhoneCare care = new PhoneCare();

    DialogWindow dialog = new DialogWindow();

    public void customerUserInterface(Stage window){

        AnchorPane root = new AnchorPane();

        MenuBar customerMenuBar = new MenuBar();

        Menu phones = new Menu("Phones");
        MenuItem repair_phone = new MenuItem("Repair Phone"); // get details of phone to be repaired
        MenuItem phones_repaired = new MenuItem("View Phones Repaired"); //show list of phones repaired
        phones_repaired.setOnAction(e->view.viewPhoneRepaired(window));
        MenuItem home = new MenuItem("Home");
        home.setOnAction(e-> care.homePageUI(window));
        MenuItem statement = new MenuItem("Generate Statement");
        repair_phone.setOnAction(e-> logic.repairPhone());
        MenuItem close_application = new MenuItem("Exit");
        close_application.setOnAction(e-> window.close());
        phones.getItems().addAll(repair_phone,phones_repaired,home, statement,close_application);

        Menu stock = new Menu("Stock");
        MenuItem add_stock = new MenuItem("Add Stock");
        add_stock.setOnAction(e-> logic.addStock());
        MenuItem check_stock = new MenuItem("View Stock");
        check_stock.setOnAction(e->view.viewStockData(window));
        stock.getItems().addAll(add_stock, check_stock);

        Menu sales = new Menu("Sales");
        MenuItem sell_item = new MenuItem("Sell Item");
        sell_item.setOnAction(e-> logic.sellStock());
        MenuItem view_sales = new MenuItem("View Sales");
        sales.getItems().addAll(sell_item,view_sales);

        Menu returns = new Menu("Returns");
        MenuItem add_returns = new MenuItem("Add Return");
        add_returns.setOnAction(event -> logic.addReturn());
        MenuItem check_returns = new MenuItem("View Returns");
        check_returns.setOnAction(e->view.viewReturns(window));
        returns.getItems().addAll(add_returns, check_returns);

        Menu expenses = new Menu("Expenses");
        MenuItem add_expenses = new MenuItem("Add Expense");
        add_expenses.setOnAction(event -> logic.addExpense());
        MenuItem view_expenses = new MenuItem("View Expenses");
        view_expenses.setOnAction(e->view.viewExpense(window));
        expenses.getItems().addAll(add_expenses, view_expenses);

        Menu customer = new Menu("Customer");
        MenuItem add_customer = new MenuItem("Add Customer");
        add_customer.setOnAction(e->addCustomerDetails());
        customer.getItems().add(add_customer);

        customerMenuBar.getMenus().addAll(phones, stock, sales, returns, expenses, customer);
        customerMenuBar.setPadding(new Insets(15.0,15.0,15.0,15.0));
        customerMenuBar.setBackground(Background.fill(Color.DODGERBLUE));
        AnchorPane.setTopAnchor(customerMenuBar, 0.0);
        AnchorPane.setLeftAnchor(customerMenuBar, 0.0);
        AnchorPane.setRightAnchor(customerMenuBar, 0.0);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(25.0,25.0,25.0,25.0));

        Label search = new Label("Search Customer");
        search.setFont(Font.font(18.0));
        Label id_no = new Label("ID No");
        id_no.setFont(Font.font(18.0));
        TextField IDNo = new TextField();
        IDNo.setFont(Font.font(18.0));
        IDNo.setOnMouseClicked(e->IDNo.clear());
        Button find = new Button("Find");
        find.setFont(Font.font(18.0));

        grid.add(search, 1,0);
        grid.add(id_no, 0, 1);
        grid.add(IDNo, 1, 1);
        grid.add(find, 1, 2);

        AnchorPane.setTopAnchor(grid, 50.0);
        AnchorPane.setRightAnchor(grid, 70.0);

        TableView<Customer> customerTable = new TableView<>();

        TableColumn<Customer, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory((e)->e.getValue().dateProperty());

        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory((e)->e.getValue().nameProperty());

        TableColumn<Customer, String> phoneNoColumn = new TableColumn<>("Phone No");
        phoneNoColumn.setCellValueFactory((e)->e.getValue().phoneNoProperty());

        TableColumn<Customer, String> assetColumn = new TableColumn<>("Asset Left");
        assetColumn.setCellValueFactory((e)->e.getValue().assetLeftProperty());

        TableColumn<Customer, String> reasonColumn = new TableColumn<>("Reason");
        reasonColumn.setCellValueFactory((e)->e.getValue().reasonProperty());
        reasonColumn.setPrefWidth(100);

        TableColumn<Customer, String> IDColumn = new TableColumn<>("ID No");
        IDColumn.setCellValueFactory((e)->e.getValue().id_noProperty());

        TableColumn<Customer, String> paidColumn = new TableColumn<>("Paid");
        paidColumn.setCellValueFactory((e)->e.getValue().paidProperty());
        paidColumn.setPrefWidth(70);

        TableColumn<Customer, String> receiptNoColumn = new TableColumn<>("Receipt No");
        receiptNoColumn.setCellValueFactory((e)->e.getValue().receiptNoProperty());

        customerTable.getColumns().addAll(dateColumn, nameColumn, phoneNoColumn, assetColumn, reasonColumn
        , IDColumn, paidColumn, receiptNoColumn);

        customerTable.setMinSize(700,600);
        AnchorPane.setTopAnchor(customerTable, 50.0);
        AnchorPane.setLeftAnchor(customerTable, 100.0);
        customerTable.itemsProperty().setValue(database.customerObservableList(IDNo.getText()));
        find.setOnAction(e->customerTable.itemsProperty().setValue(database.customerObservableList(IDNo.getText())));

        root.getChildren().addAll(grid, customerTable, customerMenuBar);

        Scene scene = new Scene(root, 1300, 690, true);
        window.setScene(scene);
        window.setTitle("Customers Available Information");
    }

    public void addCustomerDetails(){

        AnchorPane root = new AnchorPane();

        Stage window = new Stage(StageStyle.UTILITY);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(25.0,25.0,25.0,25.0));

        Label name = new Label("Name");
        name.setFont(Font.font(18.0));
        TextField nameField = new TextField();
        nameField.setFont(Font.font(20.0));
        grid.add(name, 0,0);
        grid.add(nameField, 1, 0);

        Label phoneNo = new Label("Phone No");
        phoneNo.setFont(Font.font(18.0));
        TextField phoneNoField = new TextField();
        phoneNoField.setFont(Font.font(20.0));
        grid.add(phoneNo, 0,1);
        grid.add(phoneNoField, 1,1);

        Label asset = new Label("Asset Left");
        asset.setFont(Font.font(18.0));
        TextField assetField = new TextField();
        assetField.setFont(Font.font(20.0));
        grid.add(asset,0,2);
        grid.add(assetField, 1,2);

        Label reason = new Label("Reason");
        reason.setFont(Font.font(18.0));
        TextField reasonField = new TextField();
        reasonField.setFont(Font.font(20.0));
        grid.add(reason, 0,3);
        grid.add(reasonField, 1,3);

        Label idNo = new Label("ID No");
        idNo.setFont(Font.font(18.0));
        TextField id_noField = new TextField();
        id_noField.setFont(Font.font(20.0));
        grid.add(idNo,0,4);
        grid.add(id_noField, 1,4);

        Label paid = new Label("Paid");
        paid.setFont(Font.font(18.0));
        TextField paidField = new TextField();
        paidField.setFont(Font.font(20.0));
        grid.add(paid,0,5);
        grid.add(paidField, 1,5);

        Label receiptNo = new Label("Receipt No");
        receiptNo.setFont(Font.font(18.0));
        TextField receiptField = new TextField();
        receiptField.setFont(Font.font(20.0));
        grid.add(receiptNo,0,6);
        grid.add(receiptField, 1,6);

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setMinWidth(50);
        datePicker.setMinHeight(41);
        datePicker.setValue(LocalDate.now());
        grid.add(date, 0, 7);
        grid.add(datePicker, 1,7);

        HBox btnLayout = new HBox(30);
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean isInserted = database.customerDataInserted(
                        String.valueOf(datePicker.getValue()),nameField.getText(), phoneNoField.getText(),
                        assetField.getText(),reasonField.getText(),id_noField.getText(),paidField.getText(),
                        receiptField.getText()
                );
                if (isInserted){
                    dialog.showDialog("Customer Data", nameField.getText()+" has been added successfully");
                    window.close();
                }else {
                    dialog.showDialog("Customer Data", nameField.getText()+" has not been added, try later");
                }
            }
        });
        submit.setFont(Font.font(20.0));
        Button cancel = new Button("Cancel");
        cancel.setFont(Font.font(20.0));
        cancel.setOnAction(e->window.close());
        btnLayout.getChildren().addAll(submit, cancel);
        grid.add(btnLayout, 1, 9);

        root.getChildren().add(grid);

        Scene scene = new Scene(root, 587, 518, true);
        window.setTitle("Customer Details");
        window.setScene(scene);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }
}