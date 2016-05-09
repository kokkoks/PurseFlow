package com.suttanan.kok.purseflow.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suttanan.kok.purseflow.R;

import java.util.Calendar;

/**
 * Created by KOKKOK on 5/10/2016.
 */
public class AddingDescriptionActivity extends Activity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    private TextView valueTextView;
    private EditText descriptionTextField;
    private String value;

    private String[] transaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_description_layout);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            transaction = new String[5];
        } else {
            transaction = extras.getStringArray("transaction");
        }

        showTransaction();
        initComponents();
        GetDateFromCalendar();

        showDate(year, month + 1, day);
    }

    private void showTransaction() {
        Toast.makeText(this, transaction[0] + "," + transaction[1] + "," + transaction[2] + "," + transaction[3] + "," + transaction[4], Toast.LENGTH_SHORT).show();
    }

    private void initComponents() {
        valueTextView = (TextView) findViewById(R.id.adding_description_value);
        dateView = (TextView) findViewById(R.id.test_timepicker);
        descriptionTextField = (EditText) findViewById(R.id.adding_description_textField);
    }

    private void GetDateFromCalendar() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    public void finishTransaction(View view) {
        transaction[3] = year + "/" + month + "/" + day;
        transaction[4] = descriptionTextField.getText().toString();
        showTransaction();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };
}
