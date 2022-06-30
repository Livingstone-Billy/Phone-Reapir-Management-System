package com.phonecare.phonecaresystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sales {

    private SimpleStringProperty date;
    private SimpleStringProperty itemName;
    private SimpleStringProperty quantity;
    private SimpleStringProperty pricePer;
    private SimpleStringProperty totalCost;
    private SimpleStringProperty amountPaid;
    private SimpleStringProperty profit;

    public  Sales(String _date, String _itemName, String _quantity, String _pricePer, String  _totalCost, String
                  _amountPaid, String _profit){
        this.date = new SimpleStringProperty(_date);
        this.itemName = new SimpleStringProperty(_itemName);
        this.quantity = new SimpleStringProperty(_quantity);
        this.pricePer = new SimpleStringProperty(_pricePer);
        this.totalCost = new SimpleStringProperty(_totalCost);
        this.amountPaid = new SimpleStringProperty(_amountPaid);
        this.profit = new SimpleStringProperty(_profit);
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

    public SimpleStringProperty pricePerProperty(){
        return pricePer;
    }

    public StringProperty totalCostProperty(){
        return totalCost;
    }

    public StringProperty amountPaidProperty(){
        return amountPaid;
    }

    public StringProperty profitProperty(){
        return profit;
    }
}
