package com.phonecare.phonecaresystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Stock {

    private SimpleStringProperty date;
    private SimpleStringProperty itemName;
    private SimpleStringProperty quantity;
    private SimpleStringProperty costPer;
    private SimpleStringProperty total;
    private SimpleStringProperty vendor;

    public Stock(String date,String itemName, String quantity, String costPer, String total, String vendor){
        this.date = new SimpleStringProperty(date);
        this.itemName = new SimpleStringProperty(itemName);
        this.quantity = new SimpleStringProperty(quantity);
        this.costPer = new SimpleStringProperty(costPer);
        this.total = new SimpleStringProperty(total);
        this.vendor = new SimpleStringProperty(vendor);
    }

    public StringProperty dateProperty(){
        return date;
    }

    public StringProperty itemNameProperty(){
        return itemName;
    }

    public StringProperty quantityProperty(){
        return quantity;
    }

    public StringProperty costPerProperty(){
        return costPer;
    }

    public StringProperty totalProperty(){
        return total;
    }

    public StringProperty vendorProperty(){
        return vendor;
    }

    public String getDate(){
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getItemName(){
        return itemName.get();
    }

    public void setItemName(String itemName){
        this.date.set(itemName);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public void setQuantity(String quantity){
        this.date.set(quantity);
    }

    public String getCostPer(){
        return costPer.get();
    }

    public void setCostPer(String costPer) {
        this.costPer.set(costPer);
    }

    public String getTotal() {
        return total.get();
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public String getVendor() {
        return vendor.get();
    }

    public void setVendor(String vendor) {
        this.vendor.set(vendor);
    }
}
