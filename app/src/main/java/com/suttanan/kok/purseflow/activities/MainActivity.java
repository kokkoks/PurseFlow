package com.suttanan.kok.purseflow.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.adapters.SupportFragmentTabListener;
import com.suttanan.kok.purseflow.fragments.HomeFragment;
import com.suttanan.kok.purseflow.fragments.InformaionFragment;
import com.suttanan.kok.purseflow.fragments.VisualizationFragment;

public class MainActivity extends AppCompatActivity{

    ViewPager viewPager;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabs();
    }

    private void initTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab homeTab = actionBar.newTab().setText("Home").setTabListener(new SupportFragmentTabListener<HomeFragment>(this, "Home", HomeFragment.class));
        ActionBar.Tab infoTab = actionBar.newTab().setText("Information").setTabListener(new SupportFragmentTabListener<InformaionFragment>(this, "Information", InformaionFragment.class));
        ActionBar.Tab visualTab = actionBar.newTab().setText("Visual").setTabListener(new SupportFragmentTabListener<VisualizationFragment>(this, "Visual", VisualizationFragment.class));

        actionBar.addTab(homeTab);
        actionBar.addTab(infoTab);
        actionBar.addTab(visualTab);
        actionBar.selectTab(homeTab);

    }
}
