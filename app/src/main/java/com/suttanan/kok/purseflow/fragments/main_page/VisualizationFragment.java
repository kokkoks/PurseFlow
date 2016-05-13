package com.suttanan.kok.purseflow.fragments.main_page;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.suttanan.kok.purseflow.others.Transaction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by K.K.K on 4/30/2016.
 */
public class VisualizationFragment extends Fragment {
    final private String unautherizeUser = "Unautherize";
    private Context context;

    private ArrayList<Transaction> transaction;
    private HashMap<String, ArrayList<Transaction>> hashdatas;
    Firebase ref;
    Firebase myFirebaseRef;

    private GraphView graph;
    private List<String> dateStrings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.visualizaion_layout, container, false);
        context = container.getContext();
        Firebase.setAndroidContext(v.getContext());

        hashdatas = new HashMap<String, ArrayList<Transaction>>();
        dateStrings = new ArrayList<String>();

        graph = (GraphView) v.findViewById(R.id.visual_testGraph);
        String user = RetrieveUser();
        ref = new Firebase("https://purseflow.firebaseio.com/");
        myFirebaseRef = ref.child("users").child(user);
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
                        CreateGraph();
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
        return v;
    }

    private String RetrieveUser() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            return profile.getId();
        }
        return unautherizeUser;
    }

    private void CreateGraph(){
        DataPoint[] datasPoint = new DataPoint[31];
        for(int i = 0; i < datasPoint.length; i++){
            datasPoint[i] = new DataPoint(i,i*100000);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(datasPoint);
        graph.addSeries(series);
    }
}
