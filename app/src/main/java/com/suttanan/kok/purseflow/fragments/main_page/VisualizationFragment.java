package com.suttanan.kok.purseflow.fragments.main_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suttanan.kok.purseflow.R;

/**
 * Created by K.K.K on 4/30/2016.
 */
public class VisualizationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.visualizaion_layout, container, false);
        return v;
    }
}
