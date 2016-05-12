package com.suttanan.kok.purseflow.fragments.main_page;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.adapters.InformationRowAdapter;
import com.suttanan.kok.purseflow.others.Transaction;

import java.util.ArrayList;
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
    Firebase ref;
    Firebase myFirebaseRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.information_layout, container, false);
        context = container.getContext();
        Firebase.setAndroidContext(v.getContext());


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
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Toast.makeText(getContext(), dataSnapshot.getKey() + " test", Toast.LENGTH_SHORT).show();
//                String text = dataSnapshot.getKey();
                dateStrings.add(dataSnapshot.getKey());
                Firebase childRef = myFirebaseRef.child(dataSnapshot.getKey());
                Query queryChildRef = childRef.orderByKey();
                queryChildRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Transaction tran = dataSnapshot.getValue(Transaction.class);
//                            Toast.makeText(getContext(), tran.getCategory(), Toast.LENGTH_SHORT).show();
                        CreateListview();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) { }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) { }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });

//        String[] keys = {"kok", "kak", "kook", "kai"};
//        Toast.makeText(v.getContext(), keys.length+" testnaja", Toast.LENGTH_SHORT).show();
        int[] testInt = {0,1,2,3,4,};


        test_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), dateStrings.size()+"", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private String RetrieveUser() {
        Profile profile = Profile.getCurrentProfile();
        if(profile != null){
            return profile.getId();
        }
        return unautherizeUser;
    }

    private void CreateListview(){

        String[] keys = new String[dateStrings.size()];
        keys = dateStrings.toArray(keys);
        InformationRowAdapter informationRowAdapter = new InformationRowAdapter(context, keys);

        listView.setAdapter(informationRowAdapter);
    }
}
