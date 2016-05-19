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

import com.facebook.Profile;
import com.firebase.client.Firebase;
import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.others.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by KOKKOK on 5/10/2016.
 */
public class AddingDescriptionActivity extends Activity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    SimpleDateFormat dateFormat;

    private TextView valueTextView;
    private EditText descriptionTextField;
    private String value;

    private String[] transaction;
    private Firebase ref;
    private Profile profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_description_layout);
        Firebase.setAndroidContext(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            transaction = new String[5];
        } else {
            transaction = extras.getStringArray("transaction");
        }

//        showTransaction();
        initComponents();
        mappingComponents();
        GetDateFromCalendar();

        showDate(year, month + 1, day);
    }

    private void mappingComponents() {
        value = transaction[2];

        valueTextView.setText(Integer.parseInt(value)+"");
    }

    private void showTransaction() {
        Toast.makeText(this, transaction[0] + ","
                + transaction[1] + "," + transaction[2] + ","
                + transaction[3] + "," + transaction[4], Toast.LENGTH_SHORT).show();
    }

    private void initComponents() {
        valueTextView = (TextView) findViewById(R.id.adding_description_value);
        dateView = (TextView) findViewById(R.id.test_timepicker);
        descriptionTextField = (EditText) findViewById(R.id.adding_description_textField);

        ref = new Firebase("https://purseflow.firebaseio.com/");

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

    public void finishTransaction(View view) throws ParseException {
        Date date = new Date(year, month, day);
        Firebase ref = new Firebase("https://purseflow.firebaseio.com/");
        transaction[0] = year + "-" + (month + 1) + "-" + day;
        transaction[4] = descriptionTextField.getText().toString();
        showTransaction();

        Firebase fireRef = CreateFirebaseRef(ref);
        Transaction tran = new Transaction(date, transaction[1],
                Integer.parseInt(transaction[2]), transaction[3], transaction[4]);
        fireRef.push().setValue(tran);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private Firebase CreateFirebaseRef(Firebase ref){
        Firebase fireRef;
        profile = Profile.getCurrentProfile();
        if(profile != null){
            Profile profile = Profile.getCurrentProfile();
            fireRef = ref.child("users").child(profile.getId()).child(transaction[0]);
        } else {
            fireRef = ref.child("users").child("Unautherize").child(transaction[0]);
        }
        return fireRef;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
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
            setDateTime(arg1, arg2, arg3);
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void setDateTime(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
