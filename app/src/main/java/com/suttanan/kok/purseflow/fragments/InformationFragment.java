package com.suttanan.kok.purseflow.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.adapters.InformationRowAdapter;

/**
 * Created by K.K.K on 4/30/2016.
 */
public class InformationFragment extends Fragment {

    ListView listView;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.information_layout, container, false);
        context = container.getContext();
        String[] test = {"mild", "auan", "circle", "dum", "lom"};
        int[] testInt = {0,1,2,3,4,};

        InformationRowAdapter informationRowAdapter = new InformationRowAdapter(context, test, testInt);

        listView = (ListView) v.findViewById(R.id.listView);

        listView.setAdapter(informationRowAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return v;
    }
}
