package com.suttanan.kok.purseflow.fragments.main_page;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.Profile;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.activities.MainActivity;
import com.suttanan.kok.purseflow.others.ExpensesCategory;
import com.suttanan.kok.purseflow.others.Transaction;
import com.suttanan.kok.purseflow.others.TransactionType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by K.K.K on 4/30/2016.
 */
public class VisualizationFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    final private String unautherizeUser = "Unautherize";
    private Context context;

    private HashMap<String, ArrayList<Transaction>> hashdatas;
    private Firebase ref;
    private Firebase myFirebaseRef;

    private List<Transaction>[] dateTransaction;
    private List<String> dateStrings;

    //    @BindView(R.id.visual_dateTextView) TextView dateText;
    @BindView(R.id.visual_graphView)
    GraphView graph;
    @BindView(R.id.visual_monthSpinner)
    Spinner monthSpinner;
    private Calendar calendar;
    private int year, month, day;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.visualizaion_layout, container, false);
        context = container.getContext();
        Firebase.setAndroidContext(v.getContext());
        ButterKnife.bind(this, v);

        initComponents();
        getDataFromFirebase();
        createGrahpView();

        return v;
    }

    private void getDataFromFirebase() {

        Query keyRef = myFirebaseRef.orderByKey();

        keyRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dateSnapshot, String s) {
                String text = dateSnapshot.getKey();
                dateStrings.add(text);

                Firebase childRef = myFirebaseRef.child(text);
                Query queryChildRef = childRef.orderByKey();


                ArrayList<Transaction> transaction = new ArrayList<Transaction>();
                hashdatas.put(text, transaction);

                queryChildRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Transaction tran = dataSnapshot.getValue(Transaction.class);
                        hashdatas.get(dateSnapshot.getKey()).add(tran);
                        dateTransaction = filterQueryData();
//                        Toast.makeText(getContext(), tran.getCategory(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    private void initComponents() {
        getDateFromCalendar();
        setMonthSpinner();
//        showDate(year, month + 1, day);
        dateStrings = new ArrayList<String>();
        hashdatas = new HashMap<String, ArrayList<Transaction>>();

        String user = retrieveUser();
        ref = new Firebase("https://purseflow.firebaseio.com/");
        myFirebaseRef = ref.child("users").child(user);

    }

    private boolean checkDateToQuery(String dateKey, int currentMonth) {
        String[] date = dateKey.split("-");
        int month = Integer.parseInt(date[1]);
        return month == currentMonth;
    }

    private int getDayOfMonth(String dateString) {
        String[] date = dateString.split("-");
        return Integer.parseInt(date[2]);
    }

    private String retrieveUser() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            return profile.getId();
        }
        return unautherizeUser;
    }

    private void getDateFromCalendar() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
    }

    private ArrayList<Transaction>[] filterQueryData(){
        ArrayList<Transaction>[] arrayLists = new ArrayList[31];

        for(int i = 0;i < dateStrings.size(); i++){
            String[] date = dateStrings.get(i).split("-");
            int thisYear = Integer.parseInt(date[0]);
            int thisMonth = Integer.parseInt(date[1]);
            int thisDay = Integer.parseInt(date[2]);
            if((month+1) == thisMonth){
                arrayLists[thisDay] = hashdatas.get(dateStrings.get(i));
            }
        }

        return arrayLists;
    }

//    private void showDate(int year, int month, int day) {
//        dateText.setText(new StringBuilder().append(month).append("/").append(year));
//    }

    private void setMonthSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.month_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);
        monthSpinner.setSelection(month);
        monthSpinner.setOnItemSelectedListener(this);
    }

    private void setDateTime(int year, int month) {
        this.year = year;
        this.month = month;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        month = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void createGrahpView(){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }

    @OnClick(R.id.button)
    void toastSomething(){
        String test = "";
        for(int i= 0; i < dateTransaction.length; i++){
            if(dateTransaction[i] == null){
                test += "0-";
            } else {
                test += "1-";
            }
        }

//        for(int i = 0; i < dateStrings.size(); i++){
//            test += dateStrings.get(i) + "-";
//        }

        Toast.makeText(this.getContext(), test, Toast.LENGTH_SHORT).show();
    }

}
