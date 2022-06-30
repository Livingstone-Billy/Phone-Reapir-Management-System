package com.phonecare.phonecaresystem;


import com.mongodb.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PhoneCareDatabase {

    String text_date = "2022-01-01";

    static final String DB_name = "PhoneCareDB";

    DialogWindow dialog = new DialogWindow();
    public Boolean signUpOperation(String UserName, String phoneNo, String password, String userType){

        boolean status = false;
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            //create phone care database

            DB db = mongoClient.getDB(DB_name);
            System.out.println("Database Connection Status: [OK], Database name "+db.getName());

            //collection operation
            DBCollection collection = db.getCollection("UserDetails");
            System.out.println("Collection Selection [OK]");

            DBObject query = new BasicDBObject();
            query.put("UserName", UserName);
            DBObject result = collection.findOne(query);
            if (result == null){
                //inserting documents
                BasicDBObject doc = new BasicDBObject("UserName", UserName).append("Phone No", phoneNo)
                        .append("Password", password).append("UserType", userType);

                collection.insert(doc);
                status = true;
                System.out.println("Documents Insertion: [OK]");
            }else {
                status = false;
                System.out.println("Documents Insertion: [BAD]");
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+ e.getMessage());
        }
        return status;
    }

    public boolean LogInOperation(String username, String password){
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);
            System.out.println("Database Connection: [OK]");

            DBCollection collection = db.getCollection("UserDetails");
            System.out.println("Collection selection Access: [OK]");

            boolean isCollection = db.collectionExists("LoggedInUsers");

            //accessing the database and reading it
            DBObject query = new BasicDBObject();
            query.put("UserName", username);

            DBObject result = collection.findOne(query);

            if (result == null){
                return false;
            }
            if (result.get("Password").equals(password)){
                return true;
            }
            DBCollection collection1;
            if (isCollection){
                collection1 = db.getCollection("LoggedInUsers");

            }else {
                collection1 = db.createCollection("LoggedInUsers", new BasicDBObject());

            }
            BasicDBObject doc = new BasicDBObject().append("User Name", username).
                    append("Date", LocalDate.now()).append("Time", LocalTime.now());
            collection1.insert(doc);
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+ e.getMessage());
        }
        return false;
    }

    public Boolean forgotPassword(String username, String newPassword){
        boolean isUpdate = false;
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);
            System.out.println("Database Connection Status: [OK]");

            DBCollection collection = db.getCollection("UserDetails");
            System.out.println("Collection Selection Status: [OK]");

            BasicDBObject new_Password = new BasicDBObject();
            new_Password.append("$set", new BasicDBObject().append("Password", newPassword));

            DBObject query = new BasicDBObject();
            query.put("UserName", username);
            DBObject result = collection.findOne(query);
            if (result != null){
                BasicDBObject searchQuery = new BasicDBObject().append("UserName", username);
                collection.update(searchQuery, new_Password);
                isUpdate = true;
                System.out.println("Detail Update Status: [OK]");
            }
            else {
                isUpdate = false;
                System.out.println("Details Update Status: [BAD]");
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+e.getMessage());
        }
        return isUpdate;
    }

    public Boolean repairedPhoneData(
            String phoneName, String ModelNo,String problem, int cost,int paid, int profit, String date,
            String vendor
    ){
        boolean isInserted = false;

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);
            System.out.println("Database Connection Status: [OK]");

            boolean phoneCollection = db.collectionExists("Repaired Phones");

            if (phoneCollection){
                DBCollection collection = db.getCollection("Repaired Phones");
                BasicDBObject doc = new BasicDBObject().append("Phone Name", phoneName)
                        .append("ModelNo", ModelNo).append("Problem", problem).append("Cost", cost)
                        .append("Paid",paid).append("Profit", profit).append("Date", date).append("Vendor", vendor);
                collection.insert(doc);
                isInserted = true;
                System.out.println("Phone Details Insertion Status:[OK]");
            }
            if (!phoneCollection){
                DBCollection collection = db.createCollection("Repaired Phones", new BasicDBObject());
                BasicDBObject doc = new BasicDBObject().append("Phone Name", phoneName)
                        .append("ModelNo", ModelNo).append("Problem", problem).append("Cost", cost)
                        .append("Paid",paid).append("Profit", profit).append("Date", date).append("Vendor", vendor);
                collection.insert(doc);
                isInserted = true;
                System.out.println("Phone Details Insertion Status:[OK]");
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+e.getMessage());
        }
        return isInserted;
    }

    public boolean insertSales(
            String itemName, int itemQuantity, int pricePer, int totalCost,
            int amountPaid, int profitGenerated, String date
    ){
        boolean isSold = false;
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);
            System.out.println("Database Connection Status: [OK]");

            boolean collection = db.collectionExists("Sales");
            DBCollection stockCollection = db.getCollection("Stock");

            if (!collection){
                DBCollection salesCollection = db.createCollection("Sales", new BasicDBObject());
                DBObject query = new BasicDBObject();
                query.put("Item Name", itemName);
                DBObject result = stockCollection.findOne(query);
                if ((int) result.get("Quantity") >= itemQuantity){
                    BasicDBObject doc = new BasicDBObject().append("ItemName", itemName).
                            append("Item Quantity", itemQuantity).append("Price Per", pricePer).
                            append("Total Cost", totalCost).append("Amount Paid", amountPaid).
                            append("Profit", profitGenerated).append("Date", date);
                    salesCollection.insert(doc);
                    isSold = true;
                    System.out.println("Data Insertion Status: [OK]");
                    int newQuantity = (int) result.get("Quantity") - itemQuantity;
                    BasicDBObject updateQuantity = new BasicDBObject();
                    updateQuantity.append("$set", new BasicDBObject().append("Quantity", newQuantity));

                    BasicDBObject searchQuery = new BasicDBObject().append("Item Name", itemName);
                    stockCollection.update(searchQuery, updateQuantity);
                }else {
                    isSold = false;
                    System.out.println("Item "+itemName+" not sold due to shortage");
                }
            }
            if (collection){
                DBCollection salesCollection = db.getCollection("Sales");
                DBObject query = new BasicDBObject();
                query.put("Item Name", itemName);
                DBObject result = stockCollection.findOne(query);
                if ((int)result.get("Quantity") >= itemQuantity){
                    BasicDBObject doc = new BasicDBObject().append("ItemName", itemName).
                            append("Item Quantity", itemQuantity).append("Price Per", pricePer).
                            append("Total Cost", totalCost).append("Amount Paid", amountPaid).
                            append("Profit", profitGenerated).append("Date", date);
                    salesCollection.insert(doc);
                    isSold = true;
                    System.out.println("Item sale Status: [OK]");
                    int newQuantity = (int) result.get("Quantity") - itemQuantity;
                    BasicDBObject updateQuantity = new BasicDBObject();
                    updateQuantity.append("$set", new BasicDBObject().append("Quantity", newQuantity));

                    BasicDBObject searchQuery = new BasicDBObject().append("Item Name", itemName);
                    stockCollection.update(searchQuery, updateQuantity);
                }else {
                    isSold = false;
                    System.out.println("Item "+itemName+" not sold due to shortage or unavailability");
                }
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+e.getMessage());
        }
        return isSold;
    }

    public Boolean insertAddedStock(
            String itemName, int quantity, int costPer, int total, String vendor, String date
    ){
        boolean isAdded= false;
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);
            System.out.println("Database Connection Status: [OK]");

            boolean collection = db.collectionExists("Stock");

            DBObject query = new BasicDBObject();
            query.put("Item Name", itemName);

            if (!collection){
                DBCollection stockCollection = db.createCollection("Stock", new BasicDBObject());
                DBObject result = stockCollection.findOne(query);
                if (result == null){
                    BasicDBObject doc = new BasicDBObject().append("Item Name", itemName).append("Quantity", quantity).
                            append("Cost Per", costPer).append("Total", total).append("Vendor", vendor)
                            .append("Date", date);
                    stockCollection.insert(doc);
                    isAdded = true;
                    System.out.println(itemName+" Inserted Successfully");
                }
                if (result != null){
                    BasicDBObject newAmount = new BasicDBObject();
                    newAmount.append("$inc", new BasicDBObject().append("Quantity", quantity));
                    BasicDBObject searchQuery = new BasicDBObject().append("Item Name", itemName);
                    stockCollection.update(searchQuery, newAmount);
                    isAdded = true;
                    System.out.println(itemName+" Updated Successfully");
                    BasicDBObject newData = new BasicDBObject();
                    int new_total = costPer * (int) result.get("Quantity");
                    newData.append("$set", new BasicDBObject().append("Cost Per", costPer)
                            .append("Total", new_total).append("Vendor", vendor)
                            .append("Date", String.valueOf(LocalDate.now())));
                    stockCollection.update(searchQuery, newData);
                }
            }
            if (collection){
                DBCollection stockCollection = db.getCollection("Stock");
                DBObject result = stockCollection.findOne(query);
                if (result == null){
                    BasicDBObject doc = new BasicDBObject().append("Item Name", itemName).append("Quantity", quantity).
                            append("Cost Per", costPer).append("Total", total).append("Vendor", vendor)
                            .append("Date", date);
                    stockCollection.insert(doc);
                    isAdded = true;
                    System.out.println(itemName+" Inserted Successfully");
                }
                if (result != null){
                    BasicDBObject newQuantity = new BasicDBObject();
                    newQuantity.append("$inc", new BasicDBObject().append("Quantity", quantity));
                    BasicDBObject searchQuery = new BasicDBObject().append("Item Name", itemName);
                    stockCollection.update(searchQuery, newQuantity);
                    isAdded = true;
                    System.out.println(itemName+" Updated Successfully");
                    BasicDBObject newData = new BasicDBObject();
                    newData.append("$set", new BasicDBObject().append("Cost Per", costPer)
                            .append("Vendor", vendor).append("Date", date));
                    newData.append("$inc", new BasicDBObject("Total", total));
                    stockCollection.update(searchQuery, newData);
                }
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName()+" : "+e.getMessage());
            isAdded = false;
        }
        return isAdded;
    }

    public Boolean insertAddedReturn(
            String itemName, String itemType, String problem, String return_On, int cost, String date
    ){
        boolean isInserted = false;
        try {
            MongoClient mongoClient = new MongoClient("localhost",27017);

            DB db = mongoClient.getDB(DB_name);
            System.out.println("Database Connection Status(Return): [OK]");

            boolean returnCollection = db.collectionExists("Returns");
            if (!returnCollection){
                DBCollection return_collection = db.createCollection("Returns", new BasicDBObject());
                BasicDBObject doc = new BasicDBObject().append("Item Name", itemName).append("Item Type", itemType)
                        .append("Problem", problem).append("Return On", return_On).append("Cost", cost).
                        append("Date", date);
                return_collection.insert(doc);
                isInserted = true;
                System.out.println("Return Data Insertion Status: [OK]");
            }if (returnCollection){
                DBCollection return_collection = db.getCollection("Returns");
                BasicDBObject doc = new BasicDBObject().append("Item Name", itemName).append("Item Type", itemType)
                        .append("Problem", problem).append("Return On", return_On).append("Cost", cost).
                        append("Date", date);
                return_collection.insert(doc);
                isInserted = true;
                System.out.println("Return Data Insertion Status: [OK]");
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+e.getMessage());
            isInserted = false;
        }
        return  isInserted;
    }

    public boolean insertAddedExpense(String expenseName, int totalCost, String date){
        boolean isAdded = false;
        try{
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);
            System.out.println("Database Connection: [OK]->Expense Task");

            boolean expenseCollection = db.collectionExists("Expenses");
            if (!expenseCollection){
                DBCollection expense_collection = db.createCollection("Expenses", new BasicDBObject());
                BasicDBObject doc = new BasicDBObject().append("Expense Name", expenseName).append("Cost", totalCost)
                        .append("Date", date);
                expense_collection.insert(doc);
                isAdded = true;
                System.out.println("Expense "+expenseName+" added successfully");
            }if (expenseCollection){
                DBCollection expense_collection = db.getCollection("Expenses");
                BasicDBObject doc = new BasicDBObject().append("Expense Name", expenseName).append("Cost", totalCost)
                        .append("Date", date);
                expense_collection.insert(doc);
                isAdded = true;
                System.out.println("Expense "+expenseName+" added successfully");
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName()+" : "+e.getMessage());
            isAdded = false;
        }
        return  isAdded;
    }

    public ObservableList<Phone> repairedPhoneData(String key){
        String collectionName = "Repaired Phones";
        ObservableList<Phone> phoneData = FXCollections.observableArrayList();

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB database = mongoClient.getDB(DB_name);

            boolean isCollection = database.collectionExists(collectionName);

            if (!isCollection) {
                DBCollection collection = database.createCollection(collectionName, new BasicDBObject());
                DBObject query = new BasicDBObject();
                query.put("Date", key);
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()) {
                        phoneData.add(
                                new Phone(
                                        (String) doc.get("Date"), (String) doc.get("Phone Name"), (String) doc.get("ModelNo"), (String) doc.get("Problem"),
                                        String.valueOf(doc.get("Cost")), String.valueOf(doc.get("Paid")), String.valueOf(doc.get("Profit")),
                                        (String) doc.get("Vendor")
                                )
                        );
                    }
                }
                else {
                    for (DBObject doc : collection.find(query)) {
                        phoneData.add(
                                new Phone(
                                        (String) doc.get("Date"), (String) doc.get("Phone Name"), (String) doc.get("ModelNo"), (String) doc.get("Problem"),
                                        String.valueOf(doc.get("Cost")), String.valueOf(doc.get("Paid")), String.valueOf(doc.get("Profit")),
                                        (String) doc.get("Vendor")
                                )
                        );
                    }
                }
            }
            if (isCollection){
                DBCollection collection = database.getCollection(collectionName);
                DBObject query = new BasicDBObject();
                query.put("Date", key);
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        phoneData.add(
                                new Phone(
                                        (String) doc.get("Date"), (String) doc.get("Phone Name"), (String) doc.get("ModelNo"), (String) doc.get("Problem"),
                                        String.valueOf(doc.get("Cost")), String.valueOf(doc.get("Paid")), String.valueOf(doc.get("Profit")),
                                        (String) doc.get("Vendor")
                                )
                        );
                    }
                }
                else {
                    for (DBObject doc : collection.find(query)){
                        phoneData.add(
                                new Phone(
                                        (String) doc.get("Date"), (String) doc.get("Phone Name"), (String) doc.get("ModelNo"), (String) doc.get("Problem"),
                                        String.valueOf(doc.get("Cost")), String.valueOf(doc.get("Paid")), String.valueOf(doc.get("Profit")),
                                        (String) doc.get("Vendor")
                                )
                        );
                    }
                }
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName()+" : "+e.getMessage());
        }
        return phoneData;
    }

    public ObservableList<Stock> addedStockData(String key){

        String collectionName = "Stock";
        ObservableList<Stock> stockData = FXCollections.observableArrayList();

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);


            boolean isCollection = db.collectionExists("Stock");

            if (!isCollection) {
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                DBObject query = new BasicDBObject();
                query.put("Date", key);
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()) {
                        stockData.add(
                                new Stock(
                                        (String) doc.get("Date"), (String) doc.get("Item Name"), String.valueOf(doc.get("Quantity")), String.valueOf(doc.get("Cost Per")),
                                        String.valueOf(doc.get("Total")), (String) doc.get("Vendor")
                                )
                        );
                    }
                }
                else {
                    for (DBObject doc : collection.find(query)) {
                        stockData.add(
                                new Stock(
                                        (String) doc.get("Date"), (String) doc.get("Item Name"), String.valueOf(doc.get("Quantity")), String.valueOf(doc.get("Cost Per")),
                                        String.valueOf(doc.get("Total")), (String) doc.get("Vendor")
                                )
                        );
                    }
                }
            }
            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                DBObject query = new BasicDBObject();
                query.put("Date", key);
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        stockData.add(
                                new Stock(
                                        (String) doc.get("Date"), (String) doc.get("Item Name"), String.valueOf(doc.get("Quantity")), String.valueOf(doc.get("Cost Per")),
                                        String.valueOf(doc.get("Total")), (String) doc.get("Vendor")
                                )
                        );
                    }
                }
                else {
                    for (DBObject doc : collection.find(query)){
                        stockData.add(
                                new Stock(
                                        (String) doc.get("Date"), (String) doc.get("Item Name"), String.valueOf(doc.get("Quantity")), String.valueOf(doc.get("Cost Per")),
                                        String.valueOf(doc.get("Total")), (String) doc.get("Vendor")
                                )
                        );
                    }
                }
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+e.getMessage());
        }
        return stockData;
    }

    public ObservableList<Expense> expenseObservableList(String key){
        String collectionName = "Expenses";

        ObservableList<Expense> expenseData = FXCollections.observableArrayList();

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            DBObject query = new BasicDBObject();
            query.put("Date", key);

            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        expenseData.add(new Expense(
                                (String) doc.get("Date"), (String) doc.get("Expense Name"), String.valueOf(doc.get("Cost"))
                        ));
                    }
                }
                else {
                    for (DBObject doc : collection.find(query)){
                        expenseData.add(new Expense(
                                (String) doc.get("Date"), (String) doc.get("Expense Name"), String.valueOf(doc.get("Cost"))
                        ));
                    }
                }
            }if (!isCollection){
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        expenseData.add(new Expense(
                                (String) doc.get("Date"), (String) doc.get("Expense Name"), String.valueOf(doc.get("Cost"))
                        ));
                    }
                }
                else {
                    for (DBObject doc : collection.find(query)){
                        expenseData.add(new Expense(
                                (String) doc.get("Date"), (String) doc.get("Expense Name"), String.valueOf(doc.get("Cost"))
                        ));
                    }
                }
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+e.getMessage());
        }
        return expenseData;
    }

    public ObservableList<Returns> returnsObservableList(String _key){
        String collectionName = "Returns";
        ObservableList<Returns> returnsData = FXCollections.observableArrayList();

        try {

            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            DBObject query = new BasicDBObject();
            query.put("Date", _key);

            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                if (_key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject _doc : collection.find()){
                        returnsData.add(new Returns(
                                (String) _doc.get("Date"), (String) _doc.get("Item Name"), (String) _doc.get("Item Type"),
                                (String) _doc.get("Problem"), (String) _doc.get("Return On"), String.valueOf(_doc.get("Cost"))
                        ));
                    }
                }
                else {
                    for (DBObject _doc : collection.find(query)){
                        returnsData.add(new Returns(
                                (String) _doc.get("Date"), (String) _doc.get("Item Name"), (String) _doc.get("Item Type"),
                                (String) _doc.get("Problem"), (String) _doc.get("Return On"), String.valueOf(_doc.get("Cost"))
                        ));
                    }
                }
            }if (!isCollection){
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                if (_key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject _doc : collection.find()){
                        returnsData.add(new Returns(
                                (String) _doc.get("Date"), (String) _doc.get("Item Name"), (String) _doc.get("Item Type"),
                                (String) _doc.get("Problem"), (String) _doc.get("Return On"), String.valueOf(_doc.get("Cost"))
                        ));
                    }
                }
                else {
                    for (DBObject _doc : collection.find(query)){
                        returnsData.add(new Returns(
                                (String) _doc.get("Date"), (String) _doc.get("Item Name"), (String) _doc.get("Item Type"),
                                (String) _doc.get("Problem"), (String) _doc.get("Return On"), String.valueOf(_doc.get("Cost"))
                        ));
                    }
                }
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+ e.getMessage());
        }
        return returnsData;
    }

    public ObservableList<Sales> salesObservableList(String key){
        String collectionName = "Sales";
        ObservableList<Sales> salesData = FXCollections.observableArrayList();

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            DBObject query = new BasicDBObject();
            query.put("Date", key);

            if (!isCollection) {
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()) {
                        salesData.add(
                                new Sales(
                                        (String) doc.get("Date"), (String) doc.get("ItemName"), String.valueOf(doc.get("Item Quantity")),String.valueOf(doc.get("Price Per")),
                                        String.valueOf(doc.get("Total Cost")), String.valueOf(doc.get("Amount Paid")), String.valueOf(doc.get("Profit"))
                                )
                        );
                    }
                }
                else {
                    for (DBObject doc : collection.find(query)) {
                        salesData.add(
                                new Sales(
                                        (String) doc.get("Date"), (String) doc.get("ItemName"), String.valueOf(doc.get("Item Quantity")),String.valueOf(doc.get("Price Per")),
                                        String.valueOf(doc.get("Total Cost")), String.valueOf(doc.get("Amount Paid")), String.valueOf(doc.get("Profit"))
                                )
                        );
                    }
                }
            }
            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        salesData.add(
                                new Sales(
                                        (String) doc.get("Date"), (String) doc.get("ItemName"), String.valueOf(doc.get("Item Quantity")),String.valueOf(doc.get("Price Per")),
                                        String.valueOf(doc.get("Total Cost")), String.valueOf(doc.get("Amount Paid")), String.valueOf(doc.get("Profit"))
                                )
                        );
                    }
                }
                else {
                    for (DBObject doc : collection.find(query)){
                        salesData.add(
                                new Sales(
                                        (String) doc.get("Date"), (String) doc.get("ItemName"), String.valueOf(doc.get("Item Quantity")),String.valueOf(doc.get("Price Per")),
                                        String.valueOf(doc.get("Total Cost")), String.valueOf(doc.get("Amount Paid")), String.valueOf(doc.get("Profit"))
                                )
                        );
                    }
                }
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName()+" : "+e.getMessage());
        }
        return salesData;
    }

    //database root for customer

    public boolean customerDataInserted(
            String date,String name, String phoneNo, String assetLeft, String reason,
            String id_no, String paid, String receiptNo
    ){
        boolean isInserted = false;

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            String collectionName = "Customers";

            boolean isCollection = db.collectionExists(collectionName);

            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                DBObject query = new BasicDBObject();
                query.put("ID No", id_no);
                DBObject result = collection.findOne(query);
                if (result == null){
                    BasicDBObject doc = new BasicDBObject().append("Date", date)
                            .append("Name", name).append("Phone No", phoneNo)
                            .append("Asset Left", assetLeft).append("Reason", reason)
                            .append("ID No", id_no).append("Paid", paid).append("Receipt No", receiptNo);
                    collection.insert(doc);
                    isInserted = true;
                }
                if (result != null){
                    isInserted = false;
                }
            }
            if (!isCollection){
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                DBObject query = new BasicDBObject();
                query.put("ID No", id_no);
                DBObject result = collection.findOne(query);
                if (result == null){
                    BasicDBObject doc = new BasicDBObject().append("Name", name).append("Phone No", phoneNo)
                            .append("Asset Left", assetLeft).append("Reason", reason)
                            .append("ID No", id_no).append("Paid", paid).append("Receipt No", receiptNo);
                    collection.insert(doc);
                    isInserted = true;
                }
                if (result != null){
                    isInserted = false;
                }
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+e.getMessage());
        }
        return isInserted;
    }

    public ObservableList<Customer> customerObservableList(String key_id){

        String collectionName = "Customers";
        ObservableList<Customer> customerData = FXCollections.observableArrayList();

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            DBObject query = new BasicDBObject();
            query.put("ID No", key_id);

            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                if (key_id.length() < 1){
                    for (DBObject doc : collection.find()){
                        customerData.add(new Customer(
                                (String) doc.get("Date"), (String) doc.get("Name"), (String) doc.get("Phone No"),
                                (String) doc.get("Asset Left"), (String) doc.get("Reason"), (String) doc.get("ID No"),
                                (String) doc.get("Paid"), (String) doc.get("Receipt No")
                        ));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        customerData.add(new Customer(
                                (String) doc.get("Date"), (String) doc.get("Name"), (String) doc.get("Phone No"),
                                (String) doc.get("Asset Left"), (String) doc.get("Reason"), (String) doc.get("ID No"),
                                (String) doc.get("Paid"), (String) doc.get("Receipt No")
                        ));
                    }
                }
            }if (!isCollection){
                DBCollection collection =  db.createCollection(collectionName, new BasicDBObject());
                if (key_id.length() < 1){
                    for (DBObject doc : collection.find()){
                        customerData.add(new Customer(
                                (String) doc.get("Date"), (String) doc.get("Name"), (String) doc.get("Phone No"),
                                (String) doc.get("Asset Left"), (String) doc.get("Reason"), (String) doc.get("ID No"),
                                (String) doc.get("Paid"), (String) doc.get("Receipt No")
                        ));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        customerData.add(new Customer(
                                (String) doc.get("Date"), (String) doc.get("Name"), (String) doc.get("Phone No"),
                                (String) doc.get("Asset Left"), (String) doc.get("Reason"), (String) doc.get("ID No"),
                                (String) doc.get("Paid"), (String) doc.get("Receipt No")
                        ));
                    }
                }
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName()+" : "+e.getMessage());
        }
        return customerData;
    }

    public int getTotalPhoneRevenue(String key){
        ArrayList<Integer> revenueList = new ArrayList<>();

        String phoneCollectionName = "Repaired Phones";

        int total_ = 0;

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(phoneCollectionName);

            DBObject query = new BasicDBObject();
            query.put("Date", key);

            if (isCollection){
                DBCollection collection = db.getCollection(phoneCollectionName);
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        revenueList.add((int) doc.get("Profit"));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        revenueList.add((int) doc.get("Profit"));
                    }
                }
            }if (!isCollection){
                DBCollection collection = db.createCollection(phoneCollectionName, new BasicDBObject());
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        revenueList.add((int) doc.get("Profit"));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        revenueList.add((int) doc.get("Profit"));
                    }
                }
            }
            for (int i = 0; i < revenueList.size(); i++){
                total_ += revenueList.get(i);
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() + " : "+e.getMessage());
        }
        return total_;
    }

    public int getTotalSalesRevenue(String key){

        ArrayList<Integer> salesList = new ArrayList<>();
        int totalSales = 0;
        String collectionName = "Sales";

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            DBObject query = new BasicDBObject();
            query.put("Date", key);

            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                if (key.equals(String.valueOf(
                        LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        salesList.add((int) doc.get("Profit"));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        salesList.add((int) doc.get("Profit"));
                    }
                }
            }if (!isCollection){
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        salesList.add((int) doc.get("Profit"));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        salesList.add((int) doc.get("Profit"));
                    }
                }
            }

            for (int i = 0; i < salesList.size(); i++){
                totalSales += salesList.get(i);
            }

        }catch (Exception e){
            System.err.println(e.getClass().getName() +" : "+ e.getMessage());
        }
        return totalSales;
    }

    public int getTotalStockAvailable(){

        ArrayList<Integer> stockList = new ArrayList<>();

        int totalStock = 0;

        String collectionName = "Stock";

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                for (DBObject doc : collection.find()){
                    stockList.add((int) doc.get("Quantity"));
                }
            }if (!isCollection){
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                for (DBObject doc : collection.find()){
                    stockList.add((int) doc.get("Quantity"));
                }
            }

            for (int i = 0; i < stockList.size(); i++){
                totalStock += stockList.get(i);
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() +" : "+e.getMessage());
        }
        return totalStock;
    }

    public int totalPhonesRepaired(String key){

        String collectionName = "Repaired Phones";

        int totalPhones = 0;

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                totalPhones = (int) collection.count();
            }
            if (!isCollection){
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                totalPhones = (int) collection.count();
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() +" : "+ e.getMessage());
        }
        return totalPhones;
    }

    public int getTotalExpenses(String key){

        ArrayList<Integer> expenseList = new ArrayList<>();

        int totalExpense = 0;

        String collectionName = "Expenses";

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            DBObject query = new BasicDBObject();
            query.put("Date", key);

            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                if (key.equals(String.valueOf(
                        LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        expenseList.add((int) doc.get("Cost"));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        expenseList.add((int) doc.get("Cost"));
                    }
                }
            }if (!isCollection){
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                if (key.equals(String.valueOf(
                        LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        expenseList.add((int) doc.get("Cost"));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        expenseList.add((int) doc.get("Cost"));
                    }
                }
            }

            for (int i = 0; i < expenseList.size(); i++){
                totalExpense += expenseList.get(i);
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName() +" : "+e.getMessage());
        }
        return totalExpense;
    }

    public int getTotalCostOfStock(String key){

        ArrayList<Integer> costPerList = new ArrayList<>();
        ArrayList<Integer> quantityList = new ArrayList<>();

        String collectionName = "Stock";

        int totalCost = 0;

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            DBObject query = new BasicDBObject();
            query.put("Date", key);

            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                if (key.equals(String.valueOf(
                        LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        costPerList.add((int) doc.get("Cost Per"));
                        quantityList.add((int) doc.get("Quantity"));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        costPerList.add((int) doc.get("Cost Per"));
                        quantityList.add((int) doc.get("Quantity"));
                    }
                }
            }if (!isCollection){
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                if (key.equals(String.valueOf(
                        LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        costPerList.add((int) doc.get("Cost Per"));
                        quantityList.add((int) doc.get("Quantity"));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        costPerList.add((int) doc.get("Cost Per"));
                        quantityList.add((int) doc.get("Quantity"));
                    }
                }
            }
            for (int i = 0; i < costPerList.size(); i++){
                totalCost += (costPerList.get(i) * quantityList.get(i));
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName()+" : "+e.getMessage());
        }
        return totalCost;
    }

    public double getPhoneRepairCost(String key){

        ArrayList<Integer> phoneRepairCostList = new ArrayList<>();

        String collectionName = "Repaired Phones";

        int totalCostOfPhoneRepaired = 0;

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            DBObject query = new BasicDBObject();
            query.put("Date", key);

            if (isCollection){
                DBCollection collection = db.getCollection(collectionName);
                if (key.equals(String.valueOf(
                        LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        phoneRepairCostList.add((int) doc.get("Cost"));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        phoneRepairCostList.add((int) doc.get("Cost"));
                    }
                }
            }
            if (!isCollection){
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                if (key.equals(String.valueOf(LocalDate.parse(text_date)))){
                    for (DBObject doc : collection.find()){
                        phoneRepairCostList.add((int) doc.get("Cost"));
                    }
                }else {
                    for (DBObject doc : collection.find(query)){
                        phoneRepairCostList.add((int) doc.get("Cost"));
                    }
                }
            }

            for (int i = 0; i < phoneRepairCostList.size(); i++){
                totalCostOfPhoneRepaired += phoneRepairCostList.get(i);
            }
        }catch (Exception e){
            System.err.println(e.getClass().getName()+" : "+e.getMessage());
        }
        return totalCostOfPhoneRepaired;
    }

    public double getProfitProgress(String key){
        double overallCost = getTotalCostOfStock(key) + getTotalExpenses(key) + getPhoneRepairCost(key);

        double overallRevenue = getTotalPhoneRevenue(key) + getTotalSalesRevenue(key);

        double profitProgress = overallRevenue / overallCost;
        System.out.println("overall Cost: "+overallCost);
        System.out.println("Revenue "+overallRevenue);
        System.out.println("Profit progress "+profitProgress);
        return profitProgress;
    }

    public ArrayList<Integer> DailyPhoneRepairedData(String key) {

        ArrayList<Integer> amountSpent = new ArrayList<>();

        ArrayList<Integer> amountReceived = new ArrayList<>();

        ArrayList<Integer> profit = new ArrayList<>();

        int totalAmountSpent = 0;
        int totalAmountReceived = 0;
        int totalProfitMade = 0;
        int noOfPhonesMade = 0;

        ArrayList<Integer> dailyRepairedPhoneReportData = new ArrayList<>();

        String collectionName = "Repaired Phones";

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            boolean isCollection = db.collectionExists(collectionName);

            DBObject query = new BasicDBObject();
            query.put("Date", key);

            DBCollection collection = db.getCollection(collectionName);
            if (isCollection) {
                for (DBObject doc : collection.find(query)) {
                    amountSpent.add((int) doc.get("Cost"));
                    amountReceived.add((int) doc.get("Paid"));
                    profit.add((int) doc.get("Profit"));
                }
            } else {
                for (DBObject doc : collection.find(query)) {
                    amountSpent.add((int) doc.get("Cost"));
                    amountReceived.add((int) doc.get("Paid"));
                    profit.add((int) doc.get("Profit"));
                }
            }
            noOfPhonesMade = collection.find(query).count();

            for (int i = 0; i < amountSpent.size(); i++){
                totalAmountSpent += amountSpent.get(i);
            }

            for (int j = 0; j < amountReceived.size(); j++){
                totalAmountReceived += amountReceived.get(j);
            }

            for (int k = 0; k < profit.size(); k++){
                totalProfitMade += profit.get(k);
            }
            dailyRepairedPhoneReportData.add(noOfPhonesMade);
            dailyRepairedPhoneReportData.add(totalAmountSpent);
            dailyRepairedPhoneReportData.add(totalAmountReceived);
            dailyRepairedPhoneReportData.add(totalProfitMade);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dailyRepairedPhoneReportData;
    }

    public ArrayList<Integer> DailySalesData(String key){

        ArrayList<Integer> amountPaid = new ArrayList<>();
        ArrayList<Integer> profitData = new ArrayList<>();
        ArrayList<Integer> dailySalesCost = new ArrayList<>();

        int totalAmountPaid = 0;
        int totalProfitMade = 0;
        int totalCost = 0;

        ArrayList<Integer> salesDailyData = new ArrayList<>();

        String collectionName = "Sales";

        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            DB db = mongoClient.getDB(DB_name);

            DBObject query = new BasicDBObject();
            query.put("Date", key);

            boolean isCollection = db.collectionExists(collectionName);

            if (isCollection) {
                DBCollection collection = db.getCollection(collectionName);
                for (DBObject doc : collection.find(query)) {
                    amountPaid.add((int) doc.get("Amount Paid"));
                    profitData.add((int) doc.get("Profit"));
                    dailySalesCost.add((int) doc.get("Total Cost"));
                }
            } else {
                DBCollection collection = db.createCollection(collectionName, new BasicDBObject());
                for (DBObject doc : collection.find(query)) {
                    amountPaid.add((int) doc.get("Amount Paid"));
                    profitData.add((int) doc.get("Profit"));
                    dailySalesCost.add((int) doc.get("Total Cost"));
                }
            }

            for (int j = 0; j < amountPaid.size(); j++){
                totalAmountPaid += amountPaid.get(j);
            }

            for (int k = 0; k < profitData.size(); k++){
                totalProfitMade += profitData.get(k);
            }

            for (int i = 0; i < dailySalesCost.size(); i++){
                totalCost += dailySalesCost.get(i);
            }

            salesDailyData.add(getTotalSalesRevenue(key));
            salesDailyData.add(totalAmountPaid);
            salesDailyData.add(totalProfitMade);
            salesDailyData.add(totalCost);
        }catch (Exception e){
            System.err.println(e.getClass().getName()+" : "+e.getMessage());
        }
        return salesDailyData;
    }
}