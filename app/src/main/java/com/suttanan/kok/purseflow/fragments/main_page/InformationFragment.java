package com.suttanan.kok.purseflow.fragments.main_page;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.adapters.InformationRowAdapter;
import com.suttanan.kok.purseflow.others.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by K.K.K on 4/30/2016.
 */
public class InformationFragment extends Fragment {

    final private String unautherizeUser = "Unautherize";
    private ListView listView;
    private TextView test_text;
    private Context context;
    private List<ArrayList<Transaction>> datas;
    private List<String> dateStrings;

    private ArrayList<Transaction> transaction;
    private HashMap<String, ArrayList<Transaction>> hashdatas;
    Firebase ref;
    Firebase myFirebaseRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.information_layout, container, false);
        context = container.getContext();
        Firebase.setAndroidContext(v.getContext());

        hashdatas = new HashMap<String, ArrayList<Transaction>>();
        datas = new ArrayList<ArrayList<Transaction>>();
        dateStrings = new ArrayList<String>();

        String user = RetrieveUser();
        ref = new Firebase("https://purseflow.firebaseio.com/");
        myFirebaseRef = ref.child("users").child(user);
        Query keyRef = myFirebaseRef.orderByKey();

        listView = (ListView) v.findViewById(R.id.listView);
        test_text = (TextView) v.findViewById(R.id.test_text_info);

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

                CreateListview();
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

        test_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String key : hashdatas.keySet()) {
                    Toast.makeText(v.getContext(), key, Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), hashdatas.get(dateStrings.get(position)).toString(), Toast.LENGTH_SHORT).show();
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

    private void CreateListview() {
        String[] keys = new String[dateStrings.size()];
        keys = dateStrings.toArray(keys);
        int[] resId = new int[keys.length];

        for (int i = 0; i < keys.length; i++) {
            resId[i] = i;
        }

        InformationRowAdapter informationRowAdapter = new InformationRowAdapter(context, keys, hashdatas, resId);
//        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.information_row,R.id.information_textView, keys) );
        listView.setAdapter(informationRowAdapter);
    }
}
