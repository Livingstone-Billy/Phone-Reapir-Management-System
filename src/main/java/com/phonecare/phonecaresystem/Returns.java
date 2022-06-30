package com.phonecare.phonecaresystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Returns {

    private SimpleStringProperty date;
    private SimpleStringProperty itemName;
    private SimpleStringProperty itemType;
    private SimpleStringProperty problem;
    private SimpleStringProperty return_on;
    private SimpleStringProperty cost;


    public Returns(String date, String itemName, String itemType, String problem, String return_on, String cost){
        this.date = new SimpleStringProperty(date);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemType = new SimpleStringProperty(itemType);
        this.problem = new SimpleStringProperty(problem);
        this.return_on = new SimpleStringProperty(return_on);
        this.cost = new SimpleStringProperty(cost);
    }

    public StringProperty dateProperty(){
        return date;
    }

    public StringProperty itemNameProperty(){
        return itemName;
    }

    public StringProperty itemTypeProperty(){
        return itemType;
    }

    public StringProperty problemProperty(){
        return problem;
    }

    public StringProperty return_onProperty(){
        return return_on;
    }

    public StringProperty costProperty(){
        return cost;
    }

    public String getDate(){
        return this.date.get();
    }

    public void setDate(String _date){
        this.date.set(_date);
    }

    public String getItemName() {
        return itemName.get();
    }

    public void setItemName(String _itemName) {
        this.itemName.set(_itemName);
    }

    public String getItemType(){
        return this.itemType.get();
    }

    public void setItemType(String _itemType){
        this.itemType.set(_itemType);
    }

    public String getProblem(){
        return this.problem.get();
    }

    public void setProblem(String _problem){
        this.problem.set(_problem);
    }

    public String getReturnOn(){
        return return_on.get();
    }

    public void setReturnOn(String _returnOn){
        this.return_on.set(_returnOn);
    }

    public String getCost() {
        return cost.get();
    }

    public void setCost(String cost) {
        this.cost.set(cost);
    }
}
