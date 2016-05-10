package com.suttanan.kok.purseflow.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.suttanan.kok.purseflow.R;

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
    Firebase ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_description_layout);
        Firebase.setAndroidContext(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            transaction = new String[6];
        } else {
            transaction = extras.getStringArray("transaction");
        }

        showTransaction();
        initComponents();
        GetDateFromCalendar();

        showDate(year, month + 1, day);
    }

    private void showTransaction() {
        Toast.makeText(this, transaction[0] + ","
                + transaction[1] + "," + transaction[2] + ","
                + transaction[3] + "," + transaction[4] + ","
                + transaction[5], Toast.LENGTH_SHORT).show();
    }

    private void initComponents() {
        valueTextView = (TextView) findViewById(R.id.adding_description_value);
        dateView = (TextView) findViewById(R.id.test_timepicker);
        descriptionTextField = (EditText) findViewById(R.id.adding_description_textField);

        dateFormat = new SimpleDateFormat("yyyy/MM/dd");;
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
        transaction[1] = year + "/" + month + "/" + day;
        transaction[5] = descriptionTextField.getText().toString();
//        showTransaction();

        Date date = new Date(year, month,day);
        Toast.makeText(this, date.getMonth()+"" , Toast.LENGTH_SHORT).show();
//        Firebase test = ref.child("users").child("kok");
//        Transaction tran = new Transaction("kok", date, transaction[2],
//                Integer.parseInt(transaction[3]), transaction[4], transaction[5]);
//        test.push().setValue(tran);

//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
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
