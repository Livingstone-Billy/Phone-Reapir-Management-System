package com.phonecare.phonecaresystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;

public class PhoneCareView {

    PhoneCareDatabase database = new PhoneCareDatabase();
    PhoneCareLogic logic = new PhoneCareLogic();
    PhoneCare care = new PhoneCare();

    String text_date = "2022-01-01";
    public void viewPhoneRepaired(Stage window) {
        AnchorPane root = new AnchorPane();

        Menu phones = new Menu("Phones");
        MenuItem repair_phone = new MenuItem("Repair Phone"); // get details of phone to be repaired
        MenuItem home = new MenuItem("Home");
        home.setOnAction(e-> care.homePageUI(window));
        MenuItem statement = new MenuItem("Generate Statement");
        repair_phone.setOnAction(e-> logic.repairPhone());
        MenuItem close_application = new MenuItem("Exit");
        phones.getItems().addAll(repair_phone,home, statement,close_application);

        //logic for items in phones menu
        close_application.setOnAction(e-> window.close());

        Menu stock = new Menu("Stock");
        MenuItem add_stock = new MenuItem("Add Stock");
        add_stock.setOnAction(e-> logic.addStock());
        MenuItem check_stock = new MenuItem("View Stock");
        check_stock.setOnAction(e->viewStockData(window));
        stock.getItems().addAll(add_stock, check_stock);

        Menu sales = new Menu("Sales");
        MenuItem sell_item = new MenuItem("Sell Item");
        sell_item.setOnAction(e-> logic.sellStock());
        MenuItem view_sales = new MenuItem("View Sales");
        view_sales.setOnAction(e->viewSales(window));
        sales.getItems().addAll(sell_item,view_sales);

        Menu returns = new Menu("Returns");
        MenuItem add_returns = new MenuItem("Add Return");
        add_returns.setOnAction(event -> logic.addReturn());
        MenuItem check_returns = new MenuItem("View Returns");
        check_returns.setOnAction(e->viewReturns(window));
        returns.getItems().addAll(add_returns, check_returns);

        Menu expenses = new Menu("Expenses");
        MenuItem add_expenses = new MenuItem("Add Expense");
        add_expenses.setOnAction(event -> logic.addExpense());
        MenuItem view_expenses = new MenuItem("View Expenses");
        view_expenses.setOnAction(e->viewExpense(window));
        expenses.getItems().addAll(add_expenses, view_expenses);

        MenuBar phoneViewMenuBar = new MenuBar();
        phoneViewMenuBar.getMenus().addAll(phones, stock, sales, returns, expenses);
        phoneViewMenuBar.setPadding(new Insets(15.0,15.0,15.0,15.0));
        phoneViewMenuBar.setBackground(Background.fill(Color.DODGERBLUE));
        AnchorPane.setTopAnchor(phoneViewMenuBar, 0.0);
        AnchorPane.setLeftAnchor(phoneViewMenuBar, 0.0);
        AnchorPane.setRightAnchor(phoneViewMenuBar, 0.0);

        Label sort = new Label("Sort By");
        sort.setFont(Font.font(18.0));

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setMinWidth(50);
        datePicker.setMinHeight(41);
        datePicker.setValue(LocalDate.parse(text_date));
        Button refresh = new Button("Refresh");
        refresh.setFont(Font.font(18.0));
        Button view_all = new Button("View All");
        view_all.setFont(Font.font(18.0));
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20.0,20.0,20.0,20.0));
        grid.add(sort, 1,0);
        grid.add(date, 0,1);
        grid.add(datePicker, 1,1);
        grid.add(refresh, 1,2);
        grid.add(view_all, 2,2);

        AnchorPane.setRightAnchor(grid, 20.0);
        AnchorPane.setTopAnchor(grid, 50.0);

        TableView<Phone> phoneTable = new TableView<>();

        TableColumn<Phone, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory((e)->e.getValue().dateProperty());

        TableColumn<Phone, String> phoneNameColumn = new TableColumn<>("Phone Name");
        phoneNameColumn.setCellValueFactory((e)->e.getValue().phoneNameProperty());

        TableColumn<Phone, String> modelNoColumn = new TableColumn<>("Model No");
        modelNoColumn.setCellValueFactory((e)->e.getValue().modelNoProperty());

        TableColumn<Phone, String> problemColumn = new TableColumn<>("Problem");
        problemColumn.setCellValueFactory((e)->e.getValue().problemProperty());

        TableColumn<Phone, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory((e)->e.getValue().costProperty());
        costColumn.setPrefWidth(100);

        TableColumn<Phone, String> paidColumn = new TableColumn<>("Paid");
        paidColumn.setCellValueFactory((e)->e.getValue().paidProperty());
        paidColumn.setPrefWidth(100);

        TableColumn<Phone, String> profitColumn = new TableColumn<>("Profit");
        profitColumn.setCellValueFactory((e)->e.getValue().profitProperty());
        profitColumn.setPrefWidth(100);

        TableColumn<Phone, String> vendorColumn = new TableColumn<>("Vendor");
        vendorColumn.setCellValueFactory((e)->e.getValue().vendorProperty());

        phoneTable.getColumns().addAll(dateColumn,phoneNameColumn, modelNoColumn, problemColumn, costColumn, paidColumn,profitColumn,
                vendorColumn);
        phoneTable.itemsProperty().setValue(database.repairedPhoneData(String.valueOf(datePicker.getValue())));
        AnchorPane.setLeftAnchor(phoneTable, 50.0);
        AnchorPane.setTopAnchor(phoneTable, 50.0);
        refresh.setOnAction(e->phoneTable.itemsProperty().setValue(database.repairedPhoneData(
                String.valueOf(datePicker.getValue())
        )));
        view_all.setOnAction(event -> phoneTable.itemsProperty().setValue(database.repairedPhoneData(String.valueOf(
                LocalDate.parse(text_date)
        ))));
        phoneTable.setMinSize(850,600);
        root.getChildren().addAll(grid, phoneTable,phoneViewMenuBar);

        Scene scene = new Scene(root, 1300, 690, true);
        window.setScene(scene);
        window.setTitle("Phones Repaired Information");
    }
    public void viewStockData(Stage window){

        AnchorPane root = new AnchorPane();

        Menu phones = new Menu("Phones");
        MenuItem repair_phone = new MenuItem("Repair Phone"); // get details of phone to be repaired
        MenuItem phones_repaired = new MenuItem("View Phones Repaired"); //show list of phones repaired
        phones_repaired.setOnAction(e->viewPhoneRepaired(window));
        MenuItem home = new MenuItem("Home");
        home.setOnAction(e-> care.homePageUI(window));
        MenuItem statement = new MenuItem("Generate Statement");
        repair_phone.setOnAction(e-> logic.repairPhone());
        MenuItem close_application = new MenuItem("Exit");
        phones.getItems().addAll(repair_phone,phones_repaired,home, statement,close_application);

        //logic for items in phones menu
        close_application.setOnAction(e-> window.close());

        Menu stock = new Menu("Stock");
        MenuItem add_stock = new MenuItem("Add Stock");
        add_stock.setOnAction(e-> logic.addStock());
        stock.getItems().add(add_stock);

        Menu sales = new Menu("Sales");
        MenuItem sell_item = new MenuItem("Sell Item");
        sell_item.setOnAction(e-> logic.sellStock());
        MenuItem view_sales = new MenuItem("View Sales");
        view_sales.setOnAction(e->viewSales(window));
        sales.getItems().addAll(sell_item,view_sales);

        Menu returns = new Menu("Returns");
        MenuItem add_returns = new MenuItem("Add Return");
        add_returns.setOnAction(event -> logic.addReturn());
        MenuItem check_returns = new MenuItem("View Returns");
        check_returns.setOnAction(e->viewReturns(window));
        returns.getItems().addAll(add_returns, check_returns);

        Menu expenses = new Menu("Expenses");
        MenuItem add_expenses = new MenuItem("Add Expense");
        add_expenses.setOnAction(event -> logic.addExpense());
        MenuItem view_expenses = new MenuItem("View Expenses");
        view_expenses.setOnAction(e->viewExpense(window));
        expenses.getItems().addAll(add_expenses, view_expenses);

        MenuBar stockViewMenuBar = new MenuBar();
        stockViewMenuBar.getMenus().addAll(phones, stock, sales, returns, expenses);
        stockViewMenuBar.setPadding(new Insets(15.0,15.0,15.0,15.0));
        stockViewMenuBar.setBackground(Background.fill(Color.DODGERBLUE));
        AnchorPane.setTopAnchor(stockViewMenuBar, 0.0);
        AnchorPane.setLeftAnchor(stockViewMenuBar, 0.0);
        AnchorPane.setRightAnchor(stockViewMenuBar, 0.0);

        Label sort = new Label("Sort By");
        sort.setFont(Font.font(18.0));

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setMinWidth(50);
        datePicker.setMinHeight(41);
        datePicker.setValue(LocalDate.parse(text_date));
        Button refresh = new Button("Refresh");
        refresh.setFont(Font.font(18.0));
        Button view_all = new Button("View All");
        view_all.setFont(Font.font(18.0));
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20.0,20.0,20.0,20.0));
        grid.add(sort, 1,0);
        grid.add(date, 0,1);
        grid.add(datePicker, 1,1);
        grid.add(refresh, 1,2);
        grid.add(view_all, 2,2);

        AnchorPane.setRightAnchor(grid, 30.0);
        AnchorPane.setTopAnchor(grid, 50.0);

        TableView<Stock> stockTable = new TableView<>();

        TableColumn<Stock, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory((e)->e.getValue().dateProperty());
        dateColumn.setPrefWidth(100);

        TableColumn<Stock, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory((e)->e.getValue().itemNameProperty());
        itemNameColumn.setPrefWidth(150);

        TableColumn<Stock, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory((e)->e.getValue().quantityProperty());

        TableColumn<Stock, String> costPerColumn = new TableColumn<>("Cost Per");
        costPerColumn.setCellValueFactory((e)->e.getValue().costPerProperty());

        TableColumn<Stock, String> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory((e)->e.getValue().totalProperty());

        TableColumn<Stock, String> vendorColumn = new TableColumn<>("Vendor");
        vendorColumn.setCellValueFactory((e)->e.getValue().vendorProperty());
        vendorColumn.setPrefWidth(100);

        stockTable.getColumns().addAll(dateColumn, itemNameColumn, quantityColumn, costPerColumn, totalColumn, vendorColumn);

        refresh.setOnAction(e->stockTable.itemsProperty().setValue(database.addedStockData(
                String.valueOf(datePicker.getValue())
        )));
        view_all.setOnAction(event -> stockTable.itemsProperty().setValue(database.addedStockData(String.valueOf(
                LocalDate.parse(text_date)
        ))));

        AnchorPane.setLeftAnchor(stockTable, 200.0);
        AnchorPane.setTopAnchor(stockTable, 50.0);

        stockTable.setMinSize(650,600);
        root.getChildren().addAll(grid, stockTable,stockViewMenuBar);

        Scene scene = new Scene(root, 1300, 690, true);
        window.setScene(scene);
        window.setTitle("Available Stock Information");
    }

    public void viewExpense(Stage window){

        AnchorPane root = new AnchorPane();

        Menu phones = new Menu("Phones");
        MenuItem repair_phone = new MenuItem("Repair Phone"); // get details of phone to be repaired
        MenuItem phones_repaired = new MenuItem("View Phones Repaired"); //show list of phones repaired
        phones_repaired.setOnAction(e->viewPhoneRepaired(window));
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
        check_stock.setOnAction(e->viewStockData(window));
        stock.getItems().addAll(add_stock, check_stock);

        Menu sales = new Menu("Sales");
        MenuItem sell_item = new MenuItem("Sell Item");
        sell_item.setOnAction(e-> logic.sellStock());
        MenuItem view_sales = new MenuItem("View Sales");
        view_sales.setOnAction(e->viewSales(window));
        sales.getItems().addAll(sell_item,view_sales);

        Menu returns = new Menu("Returns");
        MenuItem add_returns = new MenuItem("Add Return");
        add_returns.setOnAction(event -> logic.addReturn());
        MenuItem check_returns = new MenuItem("View Returns");
        check_returns.setOnAction(e->viewReturns(window));
        returns.getItems().addAll(add_returns, check_returns);

        Menu expenses = new Menu("Expenses");
        MenuItem add_expenses = new MenuItem("Add Expense");
        add_expenses.setOnAction(event -> logic.addExpense());
        expenses.getItems().add(add_expenses);

        MenuBar expenseMenuBar = new MenuBar();
        expenseMenuBar.getMenus().addAll(phones, stock, sales, returns, expenses);
        expenseMenuBar.setPadding(new Insets(15.0,15.0,15.0,15.0));
        expenseMenuBar.setBackground(Background.fill(Color.DODGERBLUE));
        AnchorPane.setTopAnchor(expenseMenuBar, 0.0);
        AnchorPane.setLeftAnchor(expenseMenuBar, 0.0);
        AnchorPane.setRightAnchor(expenseMenuBar, 0.0);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20.0,20.0,20.0,20.0));

        Label sort_by = new Label("Sort by");
        sort_by.setFont(Font.font(18.0));
        grid.add(sort_by, 1, 0);

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setMinSize(50, 41);
        datePicker.setValue(LocalDate.parse(text_date));
        grid.add(date, 0, 1);
        grid.add(datePicker, 1,1);

        Button refresh = new Button("Refresh");
        refresh.setFont(Font.font(20.0));
        grid.add(refresh, 1,2 );

        Button view_all = new Button("View All");
        view_all.setFont(Font.font(20.0));
        grid.add(view_all, 2,2);

        AnchorPane.setRightAnchor(grid, 30.0);
        AnchorPane.setTopAnchor(grid, 50.0);

        TableView<Expense> expenseTable = new TableView<>();

        TableColumn<Expense, String> expenseNameColumn = new TableColumn<>("Expense Name");
        expenseNameColumn.setCellValueFactory((e)->e.getValue().expenseNameProperty());

        TableColumn<Expense, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory((e)->e.getValue().costProperty());

        TableColumn<Expense, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory((e)->e.getValue().dateProperty());

        AnchorPane.setLeftAnchor(expenseTable, 200.0);
        AnchorPane.setTopAnchor(expenseTable, 50.0);
        expenseTable.setMinSize(400,600);

        expenseTable.getColumns().addAll(dateColumn, expenseNameColumn, costColumn);

        expenseNameColumn.setPrefWidth(150);
        costColumn.setPrefWidth(100);
        dateColumn.setPrefWidth(150);

        refresh.setOnAction(e->expenseTable.itemsProperty().setValue(database.expenseObservableList(
                String.valueOf(datePicker.getValue())
        )));
        view_all.setOnAction(event -> expenseTable.itemsProperty().setValue(database.expenseObservableList(String.valueOf(
                LocalDate.parse(text_date)
        ))));

        root.getChildren().addAll(grid, expenseTable, expenseMenuBar);

        Scene scene = new Scene(root, 1300, 690,true);
        window.setScene(scene);
        window.setTitle("Expense Information");
    }

    public void viewReturns(Stage window){

        AnchorPane root = new AnchorPane();

        Menu phones = new Menu("Phones");
        MenuItem repair_phone = new MenuItem("Repair Phone"); // get details of phone to be repaired
        MenuItem phones_repaired = new MenuItem("View Phones Repaired"); //show list of phones repaired
        phones_repaired.setOnAction(e->viewPhoneRepaired(window));
        MenuItem home = new MenuItem("Home");
        home.setOnAction(e-> care.homePageUI(window));
        MenuItem statement = new MenuItem("Generate Statement");
        repair_phone.setOnAction(e-> logic.repairPhone());
        MenuItem close_application = new MenuItem("Exit");
        close_application.setOnAction(e->window.close());
        phones.getItems().addAll(repair_phone,phones_repaired,home, statement,close_application);

        Menu stock = new Menu("Stock");
        MenuItem add_stock = new MenuItem("Add Stock");
        add_stock.setOnAction(e-> logic.addStock());
        MenuItem check_stock = new MenuItem("View Stock");
        check_stock.setOnAction(e->viewStockData(window));
        stock.getItems().addAll(add_stock, check_stock);

        Menu sales = new Menu("Sales");
        MenuItem sell_item = new MenuItem("Sell Item");
        sell_item.setOnAction(e-> logic.sellStock());
        MenuItem view_sales = new MenuItem("View Sales");
        view_sales.setOnAction(e->viewSales(window));
        sales.getItems().addAll(sell_item,view_sales);

        Menu returns = new Menu("Returns");
        MenuItem add_returns = new MenuItem("Add Return");
        add_returns.setOnAction(event -> logic.addReturn());
        returns.getItems().addAll(add_returns);

        Menu expenses = new Menu("Expenses");
        MenuItem add_expenses = new MenuItem("Add Expense");
        add_expenses.setOnAction(event -> logic.addExpense());
        MenuItem view_expenses = new MenuItem("View Expenses");
        view_expenses.setOnAction(e->viewExpense(window));
        expenses.getItems().addAll(add_expenses, view_expenses);

        MenuBar returnMenuBar = new MenuBar();

        returnMenuBar.getMenus().addAll(phones, stock, sales, returns, expenses);
        returnMenuBar.setPadding(new Insets(15.0,15.0,15.0,15.0));
        returnMenuBar.setBackground(Background.fill(Color.DODGERBLUE));
        AnchorPane.setTopAnchor(returnMenuBar, 0.0);
        AnchorPane.setLeftAnchor(returnMenuBar, 0.0);
        AnchorPane.setRightAnchor(returnMenuBar, 0.0);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20.0,20.0,20.0,20.0));

        Label sort_by = new Label("Sort by");
        sort_by.setFont(Font.font(18.0));
        grid.add(sort_by, 1, 0);

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setMinSize(50, 41);
        datePicker.setValue(LocalDate.parse(text_date));
        grid.add(date, 0, 1);
        grid.add(datePicker, 1,1);

        Button refresh = new Button("Refresh");
        refresh.setFont(Font.font(20.0));
        grid.add(refresh, 1,2 );

        Button view_all = new Button("View All");
        view_all.setFont(Font.font(20.0));
        grid.add(view_all, 2,2);

        AnchorPane.setRightAnchor(grid, 30.0);
        AnchorPane.setTopAnchor(grid, 50.0);

        TableView<Returns> returnsTable = new TableView<>();

        TableColumn<Returns, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory((e)->e.getValue().dateProperty());
        dateColumn.setPrefWidth(100);

        TableColumn<Returns, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory((e)->e.getValue().itemNameProperty());
        itemNameColumn.setPrefWidth(150);

        TableColumn<Returns, String> itemTypeColumn = new TableColumn<>("ItemType");
        itemTypeColumn.setCellValueFactory((e)->e.getValue().itemTypeProperty());
        itemTypeColumn.setPrefWidth(120);

        TableColumn<Returns, String> problemColumn = new TableColumn<>("Problem");
        problemColumn.setCellValueFactory((e)->e.getValue().problemProperty());
        problemColumn.setPrefWidth(150);

        TableColumn<Returns, String> returnOnColumn = new TableColumn<>("Return On");
        returnOnColumn.setCellValueFactory((e)->e.getValue().return_onProperty());
        returnOnColumn.setPrefWidth(100);

        TableColumn<Returns, String> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory((e)->e.getValue().costProperty());

        returnsTable.getColumns().addAll(dateColumn, itemNameColumn, itemTypeColumn, problemColumn, returnOnColumn, costColumn);

        refresh.setOnAction(e->returnsTable.itemsProperty().setValue(database.returnsObservableList(
                String.valueOf(datePicker.getValue())
        )));
        view_all.setOnAction(event -> returnsTable.itemsProperty().setValue(database.returnsObservableList(String.valueOf(
                LocalDate.parse(text_date)
        ))));

        AnchorPane.setLeftAnchor(returnsTable, 200.0);
        AnchorPane.setTopAnchor(returnsTable, 50.0);

        returnsTable.setMinSize(700,600);
        root.getChildren().addAll(grid, returnsTable, returnMenuBar);

        Scene scene = new Scene(root, 1300,690,true);
        window.setScene(scene);
        window.setTitle("Information On Returns Made");
    }

    public void viewSales(Stage window){
        AnchorPane root = new AnchorPane();

        Menu phones = new Menu("Phones");
        MenuItem repair_phone = new MenuItem("Repair Phone"); // get details of phone to be repaired
        MenuItem phones_repaired = new MenuItem("View Phones Repaired"); //show list of phones repaired
        phones_repaired.setOnAction(e->viewPhoneRepaired(window));
        MenuItem home = new MenuItem("Home");
        home.setOnAction(e-> care.homePageUI(window));
        MenuItem statement = new MenuItem("Generate Statement");
        repair_phone.setOnAction(e-> logic.repairPhone());
        MenuItem close_application = new MenuItem("Exit");
        phones.getItems().addAll(repair_phone,phones_repaired,home, statement,close_application);


        Menu stock = new Menu("Stock");
        MenuItem add_stock = new MenuItem("Add Stock");
        add_stock.setOnAction(e-> logic.addStock());
        MenuItem check_stock = new MenuItem("View Stock");
        check_stock.setOnAction(e->viewStockData(window));
        stock.getItems().addAll(add_stock, check_stock);

        Menu sales = new Menu("Sales");
        MenuItem sell_item = new MenuItem("Sell Item");
        sell_item.setOnAction(e-> logic.sellStock());
        sales.getItems().addAll(sell_item);

        Menu returns = new Menu("Returns");
        MenuItem add_returns = new MenuItem("Add Return");
        add_returns.setOnAction(event -> logic.addReturn());
        MenuItem check_returns = new MenuItem("View Returns");
        check_returns.setOnAction(e->viewReturns(window));
        returns.getItems().addAll(add_returns, check_returns);

        Menu expenses = new Menu("Expenses");
        MenuItem add_expenses = new MenuItem("Add Expense");
        add_expenses.setOnAction(event -> logic.addExpense());
        MenuItem view_expenses = new MenuItem("View Expenses");
        view_expenses.setOnAction(e->viewExpense(window));
        expenses.getItems().addAll(add_expenses, view_expenses);

        MenuBar salesMenuBar = new MenuBar();

        salesMenuBar.getMenus().addAll(phones, stock, sales, returns, expenses);
        salesMenuBar.setPadding(new Insets(15.0,15.0,15.0,15.0));
        salesMenuBar.setBackground(Background.fill(Color.DODGERBLUE));
        AnchorPane.setTopAnchor(salesMenuBar, 0.0);
        AnchorPane.setLeftAnchor(salesMenuBar , 0.0);
        AnchorPane.setRightAnchor(salesMenuBar , 0.0);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20.0,20.0,20.0,20.0));

        Label sort_by = new Label("Sort Sales by");
        sort_by.setFont(Font.font(18.0));
        grid.add(sort_by, 1, 0);

        Label date = new Label("Date");
        date.setFont(Font.font(18.0));
        DatePicker datePicker = new DatePicker();
        datePicker.setMinSize(50, 41);
        datePicker.setValue(LocalDate.parse(text_date));
        grid.add(date, 0, 1);
        grid.add(datePicker, 1,1);

        Button refresh = new Button("Refresh");
        refresh.setFont(Font.font(20.0));
        grid.add(refresh, 1,2 );

        Button view_all = new Button("View All");
        view_all.setFont(Font.font(20.0));
        grid.add(view_all, 2,2);

        AnchorPane.setRightAnchor(grid, 30.0);
        AnchorPane.setTopAnchor(grid, 50.0);

        TableView<Sales> salesTable = new TableView<>();

        TableColumn<Sales, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory((e)->e.getValue().dateProperty());
        dateColumn.setPrefWidth(100);

        TableColumn<Sales, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory((e)->e.getValue().itemNameProperty());
        itemNameColumn.setPrefWidth(150);

        TableColumn<Sales, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory((e)->e.getValue().quantityProperty());
        quantityColumn.setPrefWidth(120);

        TableColumn<Sales, String> pricePerColumn = new TableColumn<>("Price Per");
        pricePerColumn.setCellValueFactory((e)->e.getValue().pricePerProperty());
        pricePerColumn.setPrefWidth(150);

        TableColumn<Sales, String> totalCostColumn = new TableColumn<>("Total Cost");
        totalCostColumn.setCellValueFactory((e)->e.getValue().totalCostProperty());
        totalCostColumn.setPrefWidth(100);

        TableColumn<Sales, String> amountPaidColumn = new TableColumn<>("Amount Paid");
        amountPaidColumn.setCellValueFactory((e)->e.getValue().amountPaidProperty());

        TableColumn<Sales, String> profitColumn = new TableColumn<>("Profit");
        profitColumn.setCellValueFactory((e)->e.getValue().profitProperty());

        salesTable.getColumns().addAll(dateColumn, itemNameColumn, quantityColumn, pricePerColumn,
                totalCostColumn, amountPaidColumn, profitColumn);

        refresh.setOnAction(e->salesTable.itemsProperty().setValue(database.salesObservableList(
                String.valueOf(datePicker.getValue())
        )));
        view_all.setOnAction(event -> salesTable.itemsProperty().setValue(database.salesObservableList(String.valueOf(
                LocalDate.parse(text_date)))));

        AnchorPane.setLeftAnchor(salesTable, 100.0);
        AnchorPane.setTopAnchor(salesTable, 50.0);

        salesTable.setMinSize(700,600);
        root.getChildren().addAll(grid, salesTable, salesMenuBar);

        Scene scene = new Scene(root, 1300,690,true);
        window.setScene(scene);
        window.setTitle("Information On Sales Made");
    }
}
