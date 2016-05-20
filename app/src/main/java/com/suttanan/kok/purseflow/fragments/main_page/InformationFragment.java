package com.suttanan.kok.purseflow.fragments.main_page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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
import com.suttanan.kok.purseflow.activities.ItemDescriptionActivity;
import com.suttanan.kok.purseflow.adapters.ExpandListAdapter;
import com.suttanan.kok.purseflow.others.Transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
    private ExpandableListAdapter expAdapter;
    private ExpandableListView expList;

    private Firebase ref;
    private Firebase myFirebaseRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_information_layout, container, false);
        context = container.getContext();
        Firebase.setAndroidContext(v.getContext());

        initComponents(v);
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
                        Toast.makeText(getContext(), tran.getValue()+"", Toast.LENGTH_SHORT).show();
                        if(dateSnapshot.getKey() != null) {
                            hashdatas.get(dateSnapshot.getKey()).add(tran);
                            createListview();
                        }
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

//                CreateListview();
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

//        test_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (String key : hashdatas.keySet()) {
//                    Toast.makeText(v.getContext(), key, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(view.getContext(), hashdatas.get(dateStrings.get(position)).toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        return v;
    }

    private void initComponents(View v) {
        hashdatas = new HashMap<String, ArrayList<Transaction>>();
        datas = new ArrayList<ArrayList<Transaction>>();
        dateStrings = new ArrayList<String>();

        String user = retrieveUser();
        ref = new Firebase("https://purseflow.firebaseio.com/");
        myFirebaseRef = ref.child("users").child(user);

        expList = (ExpandableListView) v.findViewById(R.id.information_exp_list);
    }

    private String retrieveUser() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            return profile.getId();
        }
        return unautherizeUser;
    }

    private void createListview() {
        String[] keys = new String[dateStrings.size()];
        keys = dateStrings.toArray(keys);
        sortDate();
        int[] resId = new int[keys.length];

        for (int i = 0; i < keys.length; i++) {
            resId[i] = i;
        }

        expAdapter = new ExpandListAdapter(getContext(), hashdatas, dateStrings);
        expList.setAdapter(expAdapter);
        showExpandList(expAdapter, expList);

        expList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Transaction transaction = hashdatas.get(dateStrings.get(groupPosition)).get(childPosition);

                Intent intent = new Intent(getContext(), ItemDescriptionActivity.class);
                intent.putExtra("transaction", transaction);
                startActivity(intent);
                return false;
            }
        });
//        InformationRowAdapter informationRowAdapter = new InformationRowAdapter(context, keys, hashdatas, resId);
//        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.information_row,R.id.information_textView, keys) );
//        listView.setAdapter(informationRowAdapter);
//        informationRowAdapter.notifyDataSetChanged();
    }

    private void sortDate() {
        Collections.sort(dateStrings, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o2).compareTo(f.parse(o1));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    private void showExpandList(ExpandableListAdapter adapter, ExpandableListView expList) {
        for(int i = 0; i < adapter.getGroupCount(); i++){
            expList.expandGroup(i);
        }
    }
}
