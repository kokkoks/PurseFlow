package com.suttanan.kok.purseflow.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.others.Transaction;

/**
 * Created by KOKKOK on 5/20/2016.
 */
public class ItemDescriptionActivity extends AppCompatActivity {

    private Transaction transaction;
    TextView valueText;
    TextView categoryText;
    TextView descriptoinText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_description_layout);
        transaction = (Transaction) getIntent().getSerializableExtra("transaction");

        initComponents();
        mapValue();
        Toast.makeText(this, transaction.getCategory(), Toast.LENGTH_SHORT).show();
    }

    private void mapValue() {
        valueText.setText(String.valueOf(transaction.getValue()));
        categoryText.setText(transaction.getCategory());
        descriptoinText.setText(transaction.getDescription());
    }

    private void initComponents() {
        valueText = (TextView) findViewById(R.id.item_description_value);
        categoryText = (TextView) findViewById(R.id.item_description_category);
        descriptoinText = (TextView) findViewById(R.id.item_description_textDescription);
    }
}
