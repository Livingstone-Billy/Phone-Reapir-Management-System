package com.phonecare.phonecaresystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Expense {

    private SimpleStringProperty expenseName;
    private SimpleStringProperty cost;
    private SimpleStringProperty date;

    public Expense(String date, String expenseName, String cost){
        this.expenseName = new SimpleStringProperty(expenseName);
        this.cost = new SimpleStringProperty(cost);
        this.date = new SimpleStringProperty(date);
    }

    public StringProperty expenseNameProperty(){
        return expenseName;
    }

    public StringProperty costProperty(){
        return cost;
    }

    public StringProperty dateProperty(){
        return date;
    }

    public String getExpenseName(){
        return expenseName.get();
    }

    public void setExpenseName(String expenseName) {
        this.expenseName.set(expenseName);
    }

    public String getCost(){
        return cost.get();
    }

    public void setCost(String cost) {
        this.cost.set(cost);
    }

    public String getDate(){
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
