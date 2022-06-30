package com.phonecare.phonecaresystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
    private SimpleStringProperty date;
    private SimpleStringProperty name;
    private SimpleStringProperty phoneNo;
    private SimpleStringProperty assetLeft;
    private SimpleStringProperty reason;
    private SimpleStringProperty id_no;

    private SimpleStringProperty paid;
    private SimpleStringProperty receiptNo;


    public Customer(String _date, String _name, String _phoneNo, String _assetLeft, String _reason, String _id_no,
                    String _paid, String _receiptNo){
        this.date = new SimpleStringProperty(_date);
        this.name = new SimpleStringProperty(_name);
        this.phoneNo = new SimpleStringProperty(_phoneNo);
        this.assetLeft = new SimpleStringProperty(_assetLeft);
        this.reason = new SimpleStringProperty(_reason);
        this.id_no = new SimpleStringProperty(_id_no);
        this.paid = new SimpleStringProperty(_paid);
        this.receiptNo = new SimpleStringProperty(_receiptNo);
    }

    public StringProperty dateProperty(){
        return date;
    }

    public StringProperty nameProperty(){
        return  name;
    }

    public StringProperty phoneNoProperty(){
        return phoneNo;
    }

    public StringProperty assetLeftProperty(){
        return assetLeft;
    }

    public StringProperty reasonProperty(){
        return reason;
    }

    public StringProperty id_noProperty(){
        return id_no;
    }

    public StringProperty paidProperty(){
        return paid;
    }

    public StringProperty receiptNoProperty(){
        return receiptNo;
    }
}
