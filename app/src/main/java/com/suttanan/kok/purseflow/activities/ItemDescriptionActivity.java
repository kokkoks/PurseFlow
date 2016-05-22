package com.suttanan.kok.purseflow.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.others.Transaction;

import java.util.Date;

/**
 * Created by KOKKOK on 5/20/2016.
 */
public class ItemDescriptionActivity extends AppCompatActivity {

    private Transaction transaction;
    private TextView valueText;
    private TextView categoryText;
    private TextView descriptoinText;
    private TextView dateText;
    private TextView typeText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_description_layout);
        transaction = (Transaction) getIntent().getSerializableExtra("transaction");

        initComponents();
        mapValue();
    }

    private void mapValue() {
        valueText.setText(String.valueOf(transaction.getValue()) + " THB");
        categoryText.setText(transaction.getCategory());
        descriptoinText.setText(transaction.getDescription());
        typeText.setText(transaction.getType());

        Date date = transaction.getDate();

        dateText.setText(date.getDate() + "-" + date.getMonth() + "-" + date.getYear());
    }

    private void initComponents() {
        valueText = (TextView) findViewById(R.id.item_description_value);
        categoryText = (TextView) findViewById(R.id.item_description_category);
        descriptoinText = (TextView) findViewById(R.id.item_description_textDescription);
        dateText = (TextView) findViewById(R.id.item_description_date);
        typeText = (TextView) findViewById(R.id.item_description_type);
    }
}
