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

import com.firebase.client.Firebase;
import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.activities.AddingActivity;
import com.suttanan.kok.purseflow.activities.LoginActivity;

/**
 * Created by K.K.K on 4/30/2016.
 */
public class HomeFragment extends Fragment {

    private TextView userNameTxt;
    private Button currencyBtn;
    Firebase myFireBaseRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout, container, false);

        userNameTxt = (TextView) v.findViewById(R.id.userNameText);
        currencyBtn = (Button) v.findViewById(R.id.currency);

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
}
