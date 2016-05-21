package com.suttanan.kok.purseflow.fragments.main_page;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.Profile;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.others.Transaction;
import com.suttanan.kok.purseflow.others.TransactionType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by K.K.K on 4/30/2016.
 */
public class VisualizationFragment extends Fragment {
    final private String unautherizeUser = "Unautherize";

    private Context context;

    private HashMap<String, ArrayList<Transaction>> hashdatas;
    private Firebase ref;
    private Firebase myFirebaseRef;

    private ArrayList<Transaction>[] dateTransaction;
    private List<String> dateStrings;

    private int sum;

    //    @BindView(R.id.visual_dateTextView) TextView dateText;
    @BindView(R.id.visual_graphView) GraphView graph;
    @BindView(R.id.visual_categorySpinner) Spinner categorySpinner;
    @BindView(R.id.visual_monthSpinner) Spinner monthSpinner;

    private Calendar calendar;
    private int year, month, day;
    private String category;

    private int checkInit = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.visualizaion_layout, container, false);
        context = container.getContext();
        Firebase.setAndroidContext(v.getContext());
        ButterKnife.bind(this, v);

        initComponents();
//        getDataFromFirebase();
//        createGrahpView();

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
                        if(category.equals("Total")) {
                            createGrahpView();
                        } else {
                            createCategoryGrahpView();
                        }
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

        graph.getViewport().setMinX(1.0);
        graph.getViewport().setMaxX(31.0);
        graph.getViewport().setXAxisBoundsManual(true);

        setMonthSpinner();
        setCategorySpinner();

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX) {
//                return super.formatLabel(value, isValueX);
                    return ""+(int)value;
                }

                return super.formatLabel(value, isValueX);
            }
        });
//        showDate(year, month + 1, day);
        dateStrings = new ArrayList<String>();
        hashdatas = new HashMap<String, ArrayList<Transaction>>();

        String user = retrieveUser();
        ref = new Firebase("https://purseflow.firebaseio.com/");
        myFirebaseRef = ref.child("users").child(user);

//        getDataFromFirebase();
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
                arrayLists[thisDay-1] = hashdatas.get(dateStrings.get(i));
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
//        monthSpinner.setOnItemSelectedListener(context);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = position;
                if(checkInit > 0) {
                    getDataFromFirebase();
                }
//                createGrahpView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCategorySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.category_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
//        categorySpinner.setOnItemSelectedListener(this);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] categorys = getResources().getStringArray(R.array.category_name);
                category = categorys[position];
                if(checkInit == 0) {
                    getDataFromFirebase();
                    checkInit++;
                } else {
                    getDataFromFirebase();
                }
//                createCategoryGrahpView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setDateTime(int year, int month) {
        this.year = year;
        this.month = month;
    }

    private void createGrahpView(){
        graph.removeAllSeries();
        if(dateTransaction != null) {
            DataPoint[] dataPoints = new DataPoint[dateTransaction.length];
            for (int i = 0; i < dataPoints.length; i++) {
                ArrayList<Transaction> transactions = dateTransaction[i];

                if (transactions == null) {
                    dataPoints[i] = new DataPoint(i, 0);
                } else {
                    sum = 0;
                    for (int j = 0; j < transactions.size(); j++) {
                        if (transactions.get(j).getType().equals(String.valueOf(TransactionType.EXPENSES))) {
                            sum += transactions.get(j).getValue();
                        }
                    }
                    dataPoints[i] = new DataPoint(i, sum);
                }
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
            graph.addSeries(series);
        }
    }

    private void createCategoryGrahpView(){
        graph.removeAllSeries();
        if(dateTransaction != null) {
            DataPoint[] dataPoints = new DataPoint[dateTransaction.length];
            for (int i = 0; i < dataPoints.length; i++) {
                ArrayList<Transaction> transactions = dateTransaction[i];

                if (transactions == null) {
                    dataPoints[i] = new DataPoint(i, 0);
                } else {
                    int sum = 0;
                    for (int j = 0; j < transactions.size(); j++) {
                        if (checkCategoryAndDate(transactions.get(j))) {
                            sum += transactions.get(j).getValue();
                        }
                    }
                    dataPoints[i] = new DataPoint(i, sum);
                }
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
            graph.addSeries(series);
        }

//        graph.getViewport().setMinX(1.0);
//        graph.getViewport().setMaxX(31.0);
//        graph.getViewport().setXAxisBoundsManual(true);
    }

    private boolean checkCategoryAndDate(Transaction tran){
        boolean checkDate = tran.getType().equals(String.valueOf(TransactionType.EXPENSES ));
        boolean checkCategory = tran.getCategory().equalsIgnoreCase(category);
        return checkCategory && checkDate;
    }

}
