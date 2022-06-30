package com.phonecare.phonecaresystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PhoneCare extends Application {

    public static void main(String args[]){launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane rootLayout = new AnchorPane();
        rootLayout.setPrefHeight(589);
        rootLayout.setPrefWidth(779);
//        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
        rootLayout.setBackground(new Background(
                new BackgroundImage(
                        new Image("C:\\Users\\ADMIN\\Downloads\\phonerepair4.jpg"),
                        BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT,0,true,Side.BOTTOM,0,true),
                        new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,true,true,false,true)
                )
        ));

        //login layout
        AnchorPane loginLayout = new AnchorPane();
        loginLayout.setBackground(Background.fill(Color.grayRgb(35)));
        loginLayout.setPrefWidth(491);
        loginLayout.setPrefHeight(510);
        AnchorPane.setTopAnchor(loginLayout, 60.0);
        AnchorPane.setLeftAnchor(loginLayout,400.0);

        GridPane grid = new GridPane();
        grid.setVgap(40);
        grid.setHgap(10);
        grid.setPadding(new Insets(25.0,25.0,25.0,25.0));

        //fields
        Label username = new Label("UserName");
        username.setFont(Font.font(18.0));
        username.setTextFill(Color.WHEAT);
        TextField userName = new TextField();
        userName.setFont(Font.font(20.0));
        grid.add(username, 0,0);
        grid.add(userName,1,0);

        Label password = new Label("Password");
        password.setTextFill(Color.WHEAT);
        password.setFont(Font.font(18.0));
        PasswordField passWord = new PasswordField();
        passWord.setFont(Font.font(20.0));
        grid.add(password, 0,1);
        grid.add(passWord,1,1);

        AnchorPane.setTopAnchor(grid, 130.0);
        AnchorPane.setLeftAnchor(grid, 50.0);

        //Button layout
        HBox btn_Layout = new HBox(10);
        btn_Layout.setPrefHeight(59);
        btn_Layout.setPrefWidth(269);
        btn_Layout.setLayoutX(122);
        btn_Layout.setLayoutY(429);
        Button login = new Button("LOGIN");
        login.setFont(Font.font(20.0));
        login.setPrefHeight(50);
        login.setPrefWidth(271);
        login.setAlignment(Pos.BOTTOM_CENTER);
        login.setBackground(Background.fill(Color.GREEN));
        btn_Layout.getChildren().add(login);
        AnchorPane.setTopAnchor(btn_Layout,429.0);
        AnchorPane.setLeftAnchor(btn_Layout, 122.0);
        //firing event for login button
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PhoneCareDatabase care = new PhoneCareDatabase();
                boolean isLogIn = care.LogInOperation(userName.getText(), passWord.getText());
                DialogWindow dialog = new DialogWindow();
                if (isLogIn){
                    homePageUI(primaryStage);
                }else {
                    dialog.showDialog("Login Status", "Incorrect Credentials, Try Again");
                }
            }
        });

        MenuBar menuBar = new MenuBar();
        menuBar.setPrefHeight(40.0);
        Menu menu = new Menu();
        menuBar.setBackground(Background.fill(Color.DODGERBLUE));
        menuBar.getMenus().add(menu);
        AnchorPane.setTopAnchor(menuBar,0.0);
        AnchorPane.setLeftAnchor(menuBar,0.0);
        AnchorPane.setRightAnchor(menuBar,0.0);

        Hyperlink forgot_password = new Hyperlink("Forgot Password?");
        AnchorPane.setBottomAnchor(forgot_password, 180.0);
        AnchorPane.setRightAnchor(forgot_password, 100.0);
        forgot_password.setOnAction(e->changePasswordUI(primaryStage));


        Hyperlink new_user = new Hyperlink("New User? Create An Account");
        AnchorPane.setBottomAnchor(new_user, 180.0);
        AnchorPane.setLeftAnchor(new_user, 70.0);
        new_user.setOnAction(e->SignUpSceneUI(primaryStage));

        loginLayout.getChildren().addAll(grid, btn_Layout, menuBar, forgot_password, new_user);
        rootLayout.getChildren().add(loginLayout);

        Scene scene = new Scene(rootLayout, 1300,690,true);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Phone Care Centre");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void SignUpSceneUI(Stage window){
        AnchorPane rootLayout = new AnchorPane();
        rootLayout.setPrefHeight(589);
        rootLayout.setPrefWidth(779);
        rootLayout.setBackground(new Background(
                new BackgroundImage(
                        new Image("C:\\Users\\ADMIN\\Downloads\\phonerepair4.jpg"),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT,0,true,Side.BOTTOM,0,true),
                        new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,true,true,false,true)
                )
        ));

        //signup layout
        AnchorPane signupLayout = new AnchorPane();
        signupLayout.setBackground(Background.fill(Color.grayRgb(35)));
        signupLayout.setPrefWidth(491);
        signupLayout.setPrefHeight(510);
        AnchorPane.setTopAnchor(signupLayout, 60.0);
        AnchorPane.setLeftAnchor(signupLayout,400.0);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(25.0,25.0,25.0,25.0));

        //fields
        Label username = new Label("UserName");
        username.setFont(Font.font(18.0));
        username.setTextFill(Color.WHEAT);
        TextField userName = new TextField();
        userName.setFont(Font.font(20.0));
        grid.add(username, 0,0);
        grid.add(userName,1,0);

        Label tel_no = new Label("Phone No");
        tel_no.setFont(Font.font(18.0));
        tel_no.setTextFill(Color.WHEAT);
        TextField phoneNo = new TextField();
        phoneNo.setFont(Font.font(20.0));
        grid.add(tel_no, 0,1);
        grid.add(phoneNo, 1,1);

        Label password = new Label("Password");
        password.setTextFill(Color.WHEAT);
        password.setFont(Font.font(18.0));
        PasswordField passWord = new PasswordField();
        passWord.setFont(Font.font(20.0));
        grid.add(password, 0,2);
        grid.add(passWord,1,2);

        Label signup_as = new Label("SignUp As");
        signup_as.setFont(Font.font(18.0));
        signup_as.setTextFill(Color.WHEAT);
        ComboBox<String> signup_As = new ComboBox<>();
        signup_As.getItems().addAll("Admin", "User");
        signup_As.setPrefHeight(45);
        signup_As.setPrefWidth(200);
        signup_As.setPromptText("Select One");
        grid.add(signup_as, 0,3);
        grid.add(signup_As, 1,3);
        AnchorPane.setTopAnchor(grid, 130.0);
        AnchorPane.setLeftAnchor(grid, 50.0);

        //Button layout
        HBox btn_Layout = new HBox(10);
        btn_Layout.setPrefHeight(59);
        btn_Layout.setPrefWidth(269);
        btn_Layout.setLayoutX(122);
        btn_Layout.setLayoutY(429);
        Button login = new Button("SIGNUP");
        login.setFont(Font.font(20.0));
        login.setPrefHeight(50);
        login.setPrefWidth(271);
        login.setAlignment(Pos.BOTTOM_CENTER);
        login.setBackground(Background.fill(Color.GREEN));
        btn_Layout.getChildren().add(login);
        AnchorPane.setTopAnchor(btn_Layout,429.0);
        AnchorPane.setLeftAnchor(btn_Layout, 122.0);
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PhoneCareDatabase care = new PhoneCareDatabase();
                Boolean Status = care.signUpOperation(
                        userName.getText(),phoneNo.getText(),passWord.getText(),
                        signup_As.getValue()
                );
                DialogWindow dialog = new DialogWindow();
                if (Status){
                    dialog.showDialog("SignUp Status", userName.getText()+" Signed Up Successfully");
                }else {
                    dialog.showDialog("SignUp Status", "User with that name already exists");
                }
            }
        });

        MenuBar menuBar = new MenuBar();
        menuBar.setPrefHeight(40.0);
        Menu menu = new Menu();
        menuBar.setBackground(Background.fill(Color.DODGERBLUE));
        menuBar.getMenus().add(menu);
        AnchorPane.setTopAnchor(menuBar,0.0);
        AnchorPane.setLeftAnchor(menuBar,0.0);
        AnchorPane.setRightAnchor(menuBar,0.0);

        Hyperlink already_user = new Hyperlink("Already A User? Log In "+ userName.getText());
        AnchorPane.setBottomAnchor(already_user, 110.0);
        AnchorPane.setLeftAnchor(already_user, 170.0);
        PhoneCare care = new PhoneCare();
        already_user.setOnAction(e-> {
            try {
                care.start(window);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        signupLayout.getChildren().addAll(grid, btn_Layout, menuBar, already_user);
        rootLayout.getChildren().add(signupLayout);

        Scene scene = new Scene(rootLayout, 1300,690,true);

        window.setScene(scene);
    }

    public void changePasswordUI(Stage primaryStage){

        Stage window = new Stage();

        AnchorPane root = new AnchorPane();
        root.setBackground(Background.fill(Color.grayRgb(35)));
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(25.0,25.0,25.0,25.0));
        AnchorPane.setTopAnchor(grid, 50.0);
        AnchorPane.setLeftAnchor(grid, 10.0);

        //fields
        Label username = new Label("UserName");
        username.setFont(Font.font(18.0));
        username.setTextFill(Color.WHEAT);
        TextField userName = new TextField();
        userName.setFont(Font.font(20.0));
        grid.add(username, 0,0);
        grid.add(userName, 1,0);

        Label password = new Label("New Password");
        password.setFont(Font.font(18.0));
        password.setTextFill(Color.WHEAT);
        PasswordField passwordField = new PasswordField();
        passwordField.setFont(Font.font(20.0));
        grid.add(password, 0,1);
        grid.add(passwordField, 1,1);

        Button update = new Button("CHANGE PASSWORD");
        update.setPrefHeight(44);
        update.setPrefWidth(167);
        update.setBackground(Background.fill(Color.GREEN));
        AnchorPane.setBottomAnchor(update, 30.0);
        AnchorPane.setRightAnchor(update, 140.0);
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PhoneCareDatabase database = new PhoneCareDatabase();
                Boolean ifUpdate = database.forgotPassword(userName.getText(), passwordField.getText());
                DialogWindow dialog = new DialogWindow();
                if (ifUpdate){
                    dialog.showDialog("Change Password", "Password Changed Successfully");
                    window.close();
                }else {
                    dialog.showDialog("Change Password", "FAIL!, Check Credentials & Try Again");
                }
            }
        });

        root.getChildren().addAll(grid, update);

        Scene scene = new Scene(root, 450, 330, true);
        window.setScene(scene);
        window.setTitle("Change Password");

        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.showAndWait();
    }

    public void homePageUI(Stage window){

        AnchorPane root = new AnchorPane();

        MenuBar phoneCareMenuBar = new MenuBar();

        PhoneCareLogic logic = new PhoneCareLogic();

        PhoneCareView view = new PhoneCareView();

        PhoneCareCustomer customer_ = new PhoneCareCustomer();

        PhoneCareDashboard dashboard = new PhoneCareDashboard();

        Menu phones = new Menu("Phones");
        MenuItem repair_phone = new MenuItem("Repair Phone"); // get details of phone to be repaired
        repair_phone.setOnAction(e-> logic.repairPhone());
        MenuItem phones_repaired = new MenuItem("View Phones Repaired"); //show list of phones repaired
        phones_repaired.setOnAction(e->view.viewPhoneRepaired(window));
        MenuItem close_application = new MenuItem("Exit");
        phones.getItems().addAll(repair_phone, phones_repaired,close_application);

        //logic for items in phones menu
        close_application.setOnAction(e-> window.close());

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

        phoneCareMenuBar.getMenus().addAll(phones, stock, sales, returns, expenses);
        phoneCareMenuBar.setPadding(new Insets(15.0,15.0,15.0,15.0));
        phoneCareMenuBar.setBackground(Background.fill(Color.DODGERBLUE));
        AnchorPane.setTopAnchor(phoneCareMenuBar, 0.0);
        AnchorPane.setLeftAnchor(phoneCareMenuBar, 0.0);
        AnchorPane.setRightAnchor(phoneCareMenuBar, 0.0);

        root.setBackground(new Background(
                new BackgroundImage(
                        new Image("C:\\Users\\ADMIN\\Downloads\\phonerepair.jpg"),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT, 0, true,Side.BOTTOM,0,true),
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,true,true, false,true)
                )
        ));

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(25.0,25.0,25.0,25.0));

        ImageView dashboard_img = new ImageView("C:\\Users\\ADMIN\\Downloads\\home-button.png");
        dashboard_img.setFitHeight(41);
        dashboard_img.setFitWidth(53);
        Button Dashboard = new Button("Dashboard", dashboard_img);
        Dashboard.setOnAction(e->dashboard.dashboardUI(window));
        Dashboard.setPrefWidth(150);
        Dashboard.setPrefHeight(120);
        Dashboard.setContentDisplay(ContentDisplay.TOP);

        ImageView Stock_img =  new ImageView("C:\\Users\\ADMIN\\Downloads\\cubes.png");
        Stock_img.setFitWidth(53);
        Stock_img.setFitHeight(41);
        Button Stock = new Button("Stock", Stock_img);
        Stock.setOnAction(e->view.viewStockData(window));
        Stock.setPrefWidth(150);
        Stock.setPrefHeight(120);
        Stock.setContentDisplay(ContentDisplay.TOP);

        ImageView sales_img = new ImageView("C:\\Users\\ADMIN\\Downloads\\sales.png");
        sales_img.setFitHeight(41);
        sales_img.setFitWidth(53);
        Button Sales = new Button("Sales", sales_img);
        Sales.setOnAction(e->view.viewSales(window));
        Sales.setPrefHeight(120);
        Sales.setPrefWidth(150);
        Sales.setContentDisplay(ContentDisplay.TOP);

        ImageView phone_img = new ImageView("C:\\Users\\ADMIN\\Downloads\\smartphone.png");
        phone_img.setFitHeight(41);
        phone_img.setFitWidth(53);
        Button Phone = new Button("Phones", phone_img);
        Phone.setOnAction(e->view.viewPhoneRepaired(window));
        Phone.setPrefHeight(120);
        Phone.setPrefWidth(150);
        Phone.setContentDisplay(ContentDisplay.TOP);

        ImageView  statement_img = new ImageView("C:\\Users\\ADMIN\\Pictures\\Saved Pictures\\th-2583821276.jpg");
        statement_img.setFitHeight(41);
        statement_img.setFitWidth(53);
        Button Statement = new Button("Statement", statement_img);
        Statement.setPrefHeight(120);
        Statement.setPrefWidth(150);
        Statement.setContentDisplay(ContentDisplay.TOP);

        ImageView expense_img = new ImageView("C:\\Users\\ADMIN\\Downloads\\accounting.png");
        expense_img.setFitWidth(53);
        expense_img.setFitHeight(41);
        Button Expenses = new Button("Expenses", expense_img);
        Expenses.setOnAction(e->view.viewExpense(window));
        Expenses.setPrefHeight(120);
        Expenses.setPrefWidth(150);
        Expenses.setContentDisplay(ContentDisplay.TOP);

        ImageView website_img = new ImageView("C:\\Users\\ADMIN\\Downloads\\images.png");
        website_img.setFitWidth(53);
        website_img.setFitHeight(41);
        Button website = new Button("Website", website_img);
        website.setPrefHeight(120);
        website.setPrefWidth(150);
        website.setContentDisplay(ContentDisplay.TOP);

        ImageView customer_img = new ImageView("C:\\Users\\ADMIN\\Downloads\\customers-icon-12.png");
        customer_img.setFitWidth(53);
        customer_img.setFitHeight(41);
        Button customer = new Button("Customers", customer_img);
        customer.setOnAction(e->customer_.customerUserInterface(window));
        customer.setPrefHeight(120);
        customer.setPrefWidth(150);
        customer.setContentDisplay(ContentDisplay.TOP);

        ImageView returns_img = new ImageView("C:\\Users\\ADMIN\\Downloads\\images (1).png");
        returns_img.setFitWidth(53);
        returns_img.setFitHeight(41);
        Button returnBtn = new Button("Returns", returns_img);
        returnBtn.setOnAction(e->view.viewReturns(window));
        returnBtn.setPrefHeight(120);
        returnBtn.setPrefWidth(150);
        returnBtn.setContentDisplay(ContentDisplay.TOP);

        grid.add(Dashboard, 0,0);
        grid.add(Stock, 1,0);
        grid.add(Sales, 2,0);
        grid.add(returnBtn, 0,1);
        grid.add(Expenses, 1,1);
        grid.add(Phone, 2, 1);
        grid.add(customer, 0,2);
        grid.add(Statement, 1,2);
        grid.add(website, 2,2);

        AnchorPane.setTopAnchor(grid, 150.0);
        AnchorPane.setLeftAnchor(grid, 400.0);
        root.getChildren().addAll(phoneCareMenuBar, grid);

        Scene scene = new Scene(root, 1300, 690,true);
        window.setResizable(true);
        window.setScene(scene);
    }
}
