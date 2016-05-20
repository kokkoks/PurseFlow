package com.suttanan.kok.purseflow.fragments.main_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.activities.AddingActivity;
import com.suttanan.kok.purseflow.activities.LoginActivity;
import com.suttanan.kok.purseflow.others.Transaction;
import com.suttanan.kok.purseflow.others.TransactionType;

import java.util.ArrayList;

/**
 * Created by K.K.K on 4/30/2016.
 */
public class HomeFragment extends Fragment {
    final private String unautherizeUser = "Unautherize";

    private TextView userNameTxt;
    private Button currencyBtn;
    private Firebase ref;
    private Firebase myFirebaseRef;

    private int sum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout, container, false);
        Firebase.setAndroidContext(v.getContext());

        initComponents(v);
        getDataFromFirebase();

        userNameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        currencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddingActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    private void initComponents(View v) {
        userNameTxt = (TextView) v.findViewById(R.id.userNameText);
        currencyBtn = (Button) v.findViewById(R.id.currency);

        String user = retrieveUser();
        ref = new Firebase("https://purseflow.firebaseio.com/");
        myFirebaseRef = ref.child("users").child(user);

        sum = 0;
        setSumText();
    }

    @Override
    public void onResume() {
        super.onResume();
        showUserName();
    }

    private void showUserName() {
        String userName = "User Name : ";
        if (Profile.getCurrentProfile() != null) {
            Profile profile = Profile.getCurrentProfile();
            userName += profile.getFirstName() + " " + profile.getLastName();
        } else {
            userName = "Tap to Login";
        }
        userNameTxt.setText(userName);
    }

    private void getDataFromFirebase() {

        Query keyRef = myFirebaseRef.orderByKey();

        keyRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dateSnapshot, String s) {
                String text = dateSnapshot.getKey();

                Firebase childRef = myFirebaseRef.child(text);
                Query queryChildRef = childRef.orderByKey();

                queryChildRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Transaction tran = dataSnapshot.getValue(Transaction.class);
                        if (tran.getType().equals(String.valueOf(TransactionType.EXPENSES))) {
                            sum -= tran.getValue();
                        } else {
                            sum += tran.getValue();
                        }
                        setSumText();
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

    private void setSumText() {

        currencyBtn.setText(sum + " THB");
        if(sum >= 0){
            currencyBtn.setTextColor(getResources().getColor(R.color.colorPlusValue));
        } else {
            currencyBtn.setTextColor(getResources().getColor(R.color.colorMinusValue));
        }
    }

    private String retrieveUser() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            return profile.getId();
        }
        return unautherizeUser;
    }

}
