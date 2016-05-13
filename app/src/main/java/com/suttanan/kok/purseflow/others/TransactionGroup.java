package com.suttanan.kok.purseflow.others;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by K.K.K on 5/14/2016.
 */
public class TransactionGroup {
    private String category;
    private ArrayList<Transaction> transactions;

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public ArrayList<Transaction> getTransactions(){
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }
}
