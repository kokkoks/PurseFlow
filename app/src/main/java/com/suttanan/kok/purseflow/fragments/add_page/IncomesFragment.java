package com.suttanan.kok.purseflow.fragments.add_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suttanan.kok.purseflow.R;

/**
 * Created by KOKKOK on 5/8/2016.
 */
public class IncomesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.adding_income_layout, container, false);
        return v;
    }
}
