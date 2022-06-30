package com.phonecare.phonecaresystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Phone {
    private SimpleStringProperty date;
    private SimpleStringProperty phoneName;
    private SimpleStringProperty modelNo;
    private SimpleStringProperty problem;
    private SimpleStringProperty cost;
    private SimpleStringProperty paid;
    private SimpleStringProperty profit;
    private SimpleStringProperty vendor;

    public Phone(String date, String phone_name, String model_no, String problem, String cost, String paid, String profit, String vendor){
        this.date = new SimpleStringProperty(date);
        this.phoneName = new SimpleStringProperty(phone_name);
        this.modelNo = new SimpleStringProperty(model_no);
        this.problem = new SimpleStringProperty(problem);
        this.cost = new SimpleStringProperty(cost);
        this.paid = new SimpleStringProperty(paid);
        this.profit = new SimpleStringProperty(profit);
        this.vendor = new SimpleStringProperty(vendor);
    }

    public StringProperty dateProperty() { return date; }

    public StringProperty phoneNameProperty() {
        return phoneName;
    }

    public StringProperty modelNoProperty() {
        return modelNo;
    }

    public  StringProperty problemProperty(){
        return problem;
    }

    public StringProperty costProperty(){
        return cost;
    }

    public StringProperty paidProperty(){
        return paid;
    }

    public StringProperty profitProperty(){
        return profit;
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

    public String getPhoneName(){
        return phoneName.get();
    }

    public void setPhoneName(String phoneName) {
        this.phoneName.set(phoneName);
    }

    public String getModelNo(){
        return modelNo.get();
    }

    public void setModelNo(String modelNo) {
        this.modelNo.set(modelNo);
    }

    public String getProblem(){
        return problem.get();
    }

    public void setProblem(String problem) {
        this.problem.set(problem);
    }

    public String getCost(){
        return cost.get();
    }

    public void setCost(String cost) {
        this.cost.set(cost);
    }

    public String getPaid(){
        return paid.get();
    }

    public void setPaid(String paid) {
        this.paid.set(paid);
    }

    public String getProfit(){
        return profit.get();
    }

    public void setProfit(String profit) {
        this.profit.set(profit);
    }

    public String getVendor(){
        return vendor.get();
    }

    public void setVendor(String vendor) {
        this.vendor.set(vendor);
    }
}
