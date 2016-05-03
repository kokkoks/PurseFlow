package com.suttanan.kok.purseflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.suttanan.kok.purseflow.R;

/**
 * Created by K.K.K on 4/30/2016.
 */
public class HomeFragment extends Fragment {

    TextView userNameTxt;
    Button currencyBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout, container, false);

        userNameTxt = (TextView) v.findViewById(R.id.userNameText);
        currencyBtn = (Button) v.findViewById(R.id.currency);

        currencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }
}
