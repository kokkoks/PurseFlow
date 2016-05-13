package com.suttanan.kok.purseflow.others;

import java.util.Date;

/**
 * Created by KOKKOK on 5/10/2016.
 */
public class Transaction {
    /*
       1 = Type (expenses or incomes)
       2 = value
       3 = category
       4 = date
       5 = description
       */
    private String type;
    private int value;
    private String category;
    private Date date;
    private String description;

    public Transaction(){
        //empty constructor for fire base
    }
    public Transaction(Date date, String type, int value, String category, String description){
        this.type = type;
        this.value = value;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {

        return value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
