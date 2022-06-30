package com.phonecare.phonecaresystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDate;

public class PhoneCareDashboard {

    PhoneCare care = new PhoneCare();
    PhoneCareCustomer customer_ = new PhoneCareCustomer();
    PhoneCareView view = new PhoneCareView();
    PhoneCareDatabase database = new PhoneCareDatabase();

    PhoneCareReports reports = new PhoneCareReports();

    public void dashboardUI(Stage window){

        AnchorPane root = new AnchorPane();

        AnchorPane sideBarLayout = new AnchorPane();
        sideBarLayout.setPrefWidth(300);
        sideBarLayout.setBackground(Background.fill(Color.grayRgb(35)));
        AnchorPane.setTopAnchor(sideBarLayout, 0.0);
        AnchorPane.setLeftAnchor(sideBarLayout, 0.0);
        AnchorPane.setBottomAnchor(sideBarLayout, 0.0);

        Label title = new Label("Phone Care");
        title.setFont(Font.font(null, FontWeight.BOLD, 20));
        title.setContentDisplay(ContentDisplay.LEFT);
        AnchorPane titleTextLayout = new AnchorPane();
        titleTextLayout.getChildren().add(title);
        titleTextLayout.setBackground(Background.fill(Color.RED));
        AnchorPane.setLeftAnchor(title, 60.0);
        AnchorPane.setTopAnchor(title, 10.0);
        AnchorPane.setTopAnchor(titleTextLayout, 0.0);
        AnchorPane.setRightAnchor(titleTextLayout, 0.0);
        AnchorPane.setLeftAnchor(titleTextLayout, 0.0);
        titleTextLayout.setPrefHeight(60);

        Button home = new Button("Home");
        home.setFont(Font.font(null, FontWeight.MEDIUM, 15));
        home.setPadding(new Insets(17.0,17.0,17.0,17.0));
        home.setTextFill(Color.WHITE);
        home.setBackground(Background.fill(Color.grayRgb(40)));
        home.setPrefWidth(300);
        home.setOnAction(e-> care.homePageUI(window));

        Button phones = new Button("Phones");
        phones.setFont(Font.font(null, FontWeight.MEDIUM, 15));
        phones.setPadding(new Insets(17.0,17.0,17.0,17.0));
        phones.setBackground(Background.fill(Color.grayRgb(40)));
        phones.setTextFill(Color.WHITE);
        phones.setPrefWidth(300);
        phones.setOnAction(e-> view.viewPhoneRepaired(window));

        Button stock = new Button("Stock");
        stock.setFont(Font.font(null, FontWeight.MEDIUM, 15));
        stock.setPadding(new Insets(17.0,17.0,17.0,17.0));
        stock.setTextFill(Color.WHITE);
        stock.setBackground(Background.fill(Color.grayRgb(40)));
        stock.setOnAction(e->view.viewStockData(window));
        stock.setPrefWidth(300);

        Button sales = new Button("Sales");
        sales.setFont(Font.font(null, FontWeight.MEDIUM, 15));
        sales.setPadding(new Insets(17.0,17.0,17.0,17.0));
        sales.setTextFill(Color.WHITE);
        sales.setBackground(Background.fill(Color.grayRgb(40)));
        sales.setOnAction(e-> view.viewSales(window));
        sales.setPrefWidth(300);

        Button returns = new Button("Returns");
        returns.setFont(Font.font(null, FontWeight.MEDIUM, 15));
        returns.setPadding(new Insets(17.0,17.0,17.0,17.0));
        returns.setTextFill(Color.WHITE);
        returns.setBackground(Background.fill(Color.grayRgb(40)));
        returns.setOnAction(e-> view.viewReturns(window));
        returns.setPrefWidth(300);

        Button expenses = new Button("Expenses");
        expenses.setFont(Font.font(null, FontWeight.MEDIUM, 15));
        expenses.setPadding(new Insets(17.0,17.0,17.0,17.0));
        expenses.setTextFill(Color.WHITE);
        expenses.setBackground(Background.fill(Color.grayRgb(40)));
        expenses.setOnAction(e-> view.viewExpense(window));
        expenses.setPrefWidth(300);

        Button customers = new Button("Customer");
        customers.setFont(Font.font(null, FontWeight.MEDIUM, 15));
        customers.setPadding(new Insets(17.0,17.0,17.0,17.0));
        customers.setTextFill(Color.WHITE);
        customers.setBackground(Background.fill(Color.grayRgb(40)));
        customers.setOnAction(e-> customer_.customerUserInterface(window));
        customers.setPrefWidth(300);

        //Filtering results by date
        GridPane filterGridLayout = new GridPane();
        filterGridLayout.setHgap(15);
        filterGridLayout.setVgap(15);
        filterGridLayout.setPadding(new Insets(15.0,15.0,15.0,15.0));
        Label filter_by = new Label("Filter Results By");
        filter_by.setFont(Font.font(null, FontWeight.MEDIUM, 15));
        filter_by.setTextFill(Color.WHITE);
        Label date = new Label("Date");
        date.setFont(Font.font(null, FontWeight.MEDIUM, 15));
        date.setTextFill(Color.WHITE);
        DatePicker datePicker = new DatePicker();
        datePicker.setBackground(Background.fill(Color.ALICEBLUE));
        datePicker.setMinHeight(41);
        datePicker.setMinWidth(50);
        datePicker.setValue(LocalDate.parse(view.text_date));
        HBox btn_lyt = new HBox(30);
        Button filter = new Button("Filter");
        filter.setFont(Font.font(18.0));
        Button de_filter = new Button("Refresh");
        de_filter.setFont(Font.font(18.0));
        btn_lyt.getChildren().addAll(filter, de_filter);
        filterGridLayout.add(filter_by, 1,0);
        filterGridLayout.add(date, 0,1);
        filterGridLayout.add(datePicker, 1,1);
        filterGridLayout.add(btn_lyt, 1,2);

        VBox button_layout = new VBox(10);
        AnchorPane.setTopAnchor(button_layout, 70.0);
        AnchorPane.setLeftAnchor(button_layout, 0.0);
        AnchorPane.setRightAnchor(button_layout, 0.0);
        button_layout.getChildren().addAll(home, phones, stock, sales, returns, expenses
        , customers, filterGridLayout);
        sideBarLayout.getChildren().addAll(titleTextLayout, button_layout);

        MenuBar info = new MenuBar();
        AnchorPane.setLeftAnchor(info, 300.0);
        AnchorPane.setRightAnchor(info, 0.0);
        AnchorPane.setTopAnchor(info, 0.0);
        info.setPadding(new Insets(20.0,20.0,20.0,20.0));

        Menu more = new Menu("More");
        MenuItem Home = new MenuItem("Home");
        home.setOnAction(e->care.homePageUI(window));
        MenuItem users = new MenuItem("View users");
        more.getItems().addAll(Home, users);

        Menu close = new Menu("Exit");
        MenuItem close_app = new MenuItem("Close Application");
        close_app.setOnAction(e->window.close());
        close.getItems().add(close_app);

        Menu account = new Menu("Account");
        MenuItem log_out = new MenuItem("Log Out");
        log_out.setOnAction(e-> {
            try {
                care.start(window);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        MenuItem change_pass = new MenuItem("Change Password");
        change_pass.setOnAction(e->care.changePasswordUI(window));
        account.getItems().addAll(log_out, change_pass);

        Menu statement = new Menu("Statement");
        MenuItem dailyReport = new MenuItem("Daily Report");
        dailyReport.setOnAction(e->reports.generateDailyReport());
        MenuItem weeklyReport = new MenuItem("Weekly Report");
        MenuItem monthlyReport = new MenuItem("Monthly Report");
        statement.getItems().addAll(dailyReport, weeklyReport, monthlyReport);

        info.getMenus().addAll(more, close, account, statement);
        info.setBackground(Background.fill(Color.WHEAT));

        Label dashboard_title = new Label("Dashboard");
        dashboard_title.setFont(Font.font(null, FontWeight.BOLD, 25));
        AnchorPane.setTopAnchor(dashboard_title, 80.0);
        AnchorPane.setLeftAnchor(dashboard_title, 350.0);

        AnchorPane revenue_layout = new AnchorPane();
        revenue_layout.setPrefHeight(140);
        revenue_layout.setPrefWidth(170);
        int totalPhoneRevenue = database.getTotalPhoneRevenue(String.valueOf(datePicker.getValue()));
        int totalSalesRevenue = database.getTotalSalesRevenue(String.valueOf(datePicker.getValue()));
        int totalValue = totalPhoneRevenue + totalSalesRevenue;
        Label total_revenue = new Label(String.valueOf(totalValue)); //database value
        total_revenue.setTextFill(Color.WHITE);
        total_revenue.setFont(Font.font(null, FontWeight.BOLD, 20));
        Label revenue =new Label("REVENUE");
        revenue.setTextFill(Color.WHITE);
        revenue.setFont(Font.font(null, FontWeight.BOLD, 15));
        revenue_layout.setBackground(Background.fill(Color.ORANGERED));
        AnchorPane.setTopAnchor(total_revenue, 20.0);
        AnchorPane.setLeftAnchor(total_revenue, 50.0);
        AnchorPane.setLeftAnchor(revenue, 50.0);
        AnchorPane.setBottomAnchor(revenue, 20.0);
        revenue_layout.getChildren().addAll(total_revenue, revenue);

        AnchorPane sales_layout = new AnchorPane();
        sales_layout.setPrefWidth(170);
        sales_layout.setPrefHeight(140);
        sales_layout.setBackground(Background.fill(Color.ORANGE));
        Label total_sales = new Label(String.valueOf(totalSalesRevenue)); //database value
        total_sales.setTextFill(Color.WHITE);
        total_sales.setFont(Font.font(null, FontWeight.BOLD, 20));
        Label Sales = new Label("SALES");
        Sales.setFont(Font.font(null, FontWeight.BOLD, 15));
        Sales.setTextFill(Color.WHITE);
        AnchorPane.setTopAnchor(total_sales, 20.0);
        AnchorPane.setLeftAnchor(total_sales, 50.0);
        AnchorPane.setBottomAnchor(Sales, 20.0);
        AnchorPane.setLeftAnchor(Sales, 50.0);
        sales_layout.getChildren().addAll(total_sales, Sales);

        AnchorPane product_layout = new AnchorPane();
        product_layout.setPrefHeight(140);
        product_layout.setPrefWidth(170);
        product_layout.setBackground(Background.fill(Color.DODGERBLUE));
        int totalStock = database.getTotalStockAvailable();
        Label total_product = new Label(String.valueOf(totalStock)); //database analysis
        total_product.setFont(Font.font(null, FontWeight.BOLD, 20));
        total_product.setTextFill(Color.WHITE);
        Label product = new Label("PRODUCTS");
        product.setTextFill(Color.WHITE);
        product.setFont(Font.font(null, FontWeight.BOLD, 15));
        AnchorPane.setTopAnchor(total_product, 20.0);
        AnchorPane.setLeftAnchor(total_product, 50.0);
        AnchorPane.setBottomAnchor(product, 20.0);
        AnchorPane.setLeftAnchor(product, 50.0);
        product_layout.getChildren().addAll(product, total_product);

        AnchorPane phones_layout = new AnchorPane();
        phones_layout.setPrefWidth(170);
        phones_layout.setPrefHeight(140);
        phones_layout.setBackground(Background.fill(Color.LIMEGREEN));
        int totalPhones = database.totalPhonesRepaired(String.valueOf(datePicker.getValue()));
        Label total_phones = new Label(String.valueOf(totalPhones));
        total_phones.setTextFill(Color.WHITE);
        total_phones.setFont(Font.font(null, FontWeight.BOLD, 20));
        Label Phones = new Label("PHONES");
        Phones.setFont(Font.font(null, FontWeight.BOLD, 15));
        Phones.setTextFill(Color.WHITE);
        AnchorPane.setTopAnchor(total_phones, 20.0);
        AnchorPane.setLeftAnchor(total_phones, 50.0);
        AnchorPane.setBottomAnchor(Phones, 20.0);
        AnchorPane.setLeftAnchor(Phones, 50.0);
        phones_layout.getChildren().addAll(Phones, total_phones);

        AnchorPane expense_layout = new AnchorPane();
        expense_layout.setPrefWidth(170);
        expense_layout.setPrefHeight(140);
        expense_layout.setBackground(Background.fill(Color.MEDIUMPURPLE));
        int totalExpenses = database.getTotalExpenses(String.valueOf(datePicker.getValue()));
        Label total_expense = new Label(String.valueOf(totalExpenses));
        total_expense.setTextFill(Color.WHITE);
        total_expense.setFont(Font.font(null, FontWeight.BOLD, 20));
        Label Expenses = new Label("EXPENSES");
        Expenses.setFont(Font.font(null, FontWeight.BOLD, 15));
        Expenses.setTextFill(Color.WHITE);
        AnchorPane.setTopAnchor(total_expense, 20.0);
        AnchorPane.setLeftAnchor(total_expense, 50.0);
        AnchorPane.setBottomAnchor(Expenses, 20.0);
        AnchorPane.setLeftAnchor(Expenses, 50.0);
        expense_layout.getChildren().addAll(Expenses, total_expense);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(30);
        gridPane.setPadding(new Insets(20.0,20.0,20.0,20.0));
        AnchorPane.setTopAnchor(gridPane, 100.0);
        AnchorPane.setLeftAnchor(gridPane, 340.0);

        HBox data_layout = new HBox(30);
        data_layout.getChildren().addAll(revenue_layout, sales_layout, product_layout,
                phones_layout, expense_layout);
        gridPane.add(data_layout, 0,0);

        HBox progressLayout = new HBox(30);
        gridPane.add(progressLayout, 0,1);

        AnchorPane revenueProgress = new AnchorPane();
        revenueProgress.setPrefHeight(200);
        revenueProgress.setPrefWidth(170);
        revenueProgress.setBackground(Background.fill(Color.WHITE));
        Label profit = new Label("PROFIT");
        AnchorPane.setTopAnchor(profit, 30.0);
        AnchorPane.setLeftAnchor(profit, 50.0);
        profit.setFont(Font.font(null, FontWeight.BOLD, 15));
        ProgressIndicator profitProgress = new ProgressIndicator();
        AnchorPane.setTopAnchor(profitProgress, 60.0);
        AnchorPane.setLeftAnchor(profitProgress, 30.0);
        double profit_Prg = database.getProfitProgress(String.valueOf(datePicker.getValue()));
        profitProgress.setProgress(profit_Prg);
        profitProgress.setTooltip(new Tooltip("Total Profit"));
        profitProgress.setMinSize(100, 80);
        revenueProgress.getChildren().addAll(profit, profitProgress);

        AnchorPane salesProgressLayout = new AnchorPane();
        salesProgressLayout.setPrefHeight(200);
        salesProgressLayout.setPrefWidth(170);
        salesProgressLayout.setBackground(Background.fill(Color.WHITE));
        Label sales_label = new Label("SALES");
        AnchorPane.setTopAnchor(sales_label, 30.0);
        AnchorPane.setLeftAnchor(sales_label, 50.0);
        sales_label.setFont(Font.font(null, FontWeight.BOLD, 15));
        ProgressIndicator salesProgress = new ProgressIndicator();
        AnchorPane.setTopAnchor(salesProgress, 60.0);
        AnchorPane.setLeftAnchor(salesProgress, 30.0);
        double totalStockCost = database.getTotalCostOfStock(String.valueOf(datePicker.getValue()));
        double sales_Prg = Double.valueOf(totalSalesRevenue) / totalStockCost;
        salesProgress.setProgress(sales_Prg);
        salesProgress.setTooltip(new Tooltip("Total Sales"));
        salesProgress.setMinSize(100, 80);
        ColorAdjust adjust = new ColorAdjust();
        adjust.setHue(0.9);
        salesProgress.setEffect(adjust);
        salesProgressLayout.getChildren().addAll(sales_label, salesProgress);
        progressLayout.getChildren().addAll(revenueProgress, salesProgressLayout);

        Group phoneChartLayout = new Group();
        AnchorPane.setRightAnchor(phoneChartLayout, 0.0);
        AnchorPane.setBottomAnchor(phoneChartLayout, 90.0);
        double overallCost = database.getPhoneRepairCost(String.valueOf(datePicker.getValue()))
                + database.getTotalExpenses(String.valueOf(datePicker.getValue()))
                + database.getTotalCostOfStock(String.valueOf(datePicker.getValue()));

        ObservableList<PieChart.Data> phoneValueList = FXCollections.observableArrayList(
                new PieChart.Data("Total Profit", totalValue),
                new PieChart.Data("Total Cost", overallCost),
                new PieChart.Data("Total Sales", totalSalesRevenue),
                new PieChart.Data("Total Expenses", totalExpenses)
        );
        PieChart phonePieChart = new PieChart(phoneValueList);
        phonePieChart.setTitle("PhoneCare Performance Analysis");
        phonePieChart.setPrefSize(450,350);
        phonePieChart.setLegendSide(Side.LEFT);
        phonePieChart.setLabelLineLength(10);

        phonePieChart.getData().forEach(data -> {
            String percentage = String.format("%.2f%%", data.getPieValue() / 100);
            Tooltip tooltip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), tooltip);
        });
        phoneChartLayout.getChildren().add(phonePieChart);

        filter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int totalPhoneRevenue_ = database.getTotalPhoneRevenue(String.valueOf(datePicker.getValue()));
                int totalSalesRevenue_ = database.getTotalSalesRevenue(String.valueOf(datePicker.getValue()));
                total_revenue.setText(String.valueOf(totalPhoneRevenue_ + totalSalesRevenue_));
                total_sales.setText(String.valueOf(totalSalesRevenue_));
                total_product.setText(String.valueOf(database.getTotalStockAvailable()));
                total_phones.setText(String.valueOf(database.totalPhonesRepaired(
                        String.valueOf(datePicker.getValue())
                )));
                total_expense.setText(String.valueOf(database.getTotalExpenses(String.valueOf(
                        datePicker.getValue()
                ))));
                profitProgress.setProgress(database.getProfitProgress(String.valueOf(datePicker.getValue())));

                double totalStockCost = database.getTotalCostOfStock(String.valueOf(datePicker.getValue()));
                double sales_Prg = Double.valueOf(totalSalesRevenue_) / totalStockCost;
                salesProgress.setProgress(sales_Prg);

                double overallCost_ = database.getPhoneRepairCost(String.valueOf(datePicker.getValue()))
                        + database.getTotalExpenses(String.valueOf(datePicker.getValue()))
                        + database.getTotalCostOfStock(String.valueOf(datePicker.getValue()));

                double total_expenses = database.getTotalExpenses(String.valueOf(
                        datePicker.getValue()
                ));

                ObservableList<PieChart.Data> phoneValueList_ = FXCollections.observableArrayList(
                        new PieChart.Data("Total Profit", totalPhoneRevenue_ + totalSalesRevenue_),
                        new PieChart.Data("Total Cost", overallCost_),
                        new PieChart.Data("Total Sales", totalSalesRevenue_),
                        new PieChart.Data("Total Expenses", total_expenses)
                );
                phonePieChart.setData(phoneValueList_);
                phonePieChart.getData().forEach(data -> {
                    String percentage = String.format("%.2f%%", data.getPieValue() / 100);
                    Tooltip tooltip = new Tooltip(percentage);
                    Tooltip.install(data.getNode(), tooltip);
                });
            }
        });

        de_filter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                datePicker.setValue(LocalDate.parse(view.text_date));

                total_revenue.setText(String.valueOf(totalValue));
                total_sales.setText(String.valueOf(totalSalesRevenue));
                total_product.setText(String.valueOf(database.getTotalStockAvailable()));
                total_phones.setText(String.valueOf(database.totalPhonesRepaired(
                        String.valueOf(datePicker.getValue())
                )));
                total_expense.setText(String.valueOf(database.getTotalExpenses(String.valueOf(
                        datePicker.getValue()
                ))));
                profitProgress.setProgress(database.getProfitProgress(String.valueOf(datePicker.getValue())));

                double totalStockCost = database.getTotalCostOfStock(String.valueOf(datePicker.getValue()));
                double sales_Prg = Double.valueOf(totalSalesRevenue) / totalStockCost;
                salesProgress.setProgress(sales_Prg);

                phonePieChart.getData().forEach(data -> {
                    String percentage = String.format("%.2f%%", data.getPieValue() / 100);
                    Tooltip tooltip = new Tooltip(percentage);
                    Tooltip.install(data.getNode(), tooltip);
                });
                phonePieChart.setData(phoneValueList);
            }
        });

        root.getChildren().addAll(sideBarLayout, info, dashboard_title, gridPane, phoneChartLayout);
        root.setBackground(Background.fill(Color.DIMGRAY));

        Scene scene = new Scene(root, 1300,690, true);
        window.setScene(scene);
        window.setTitle("PhoneCare Dashboard");
    }
}