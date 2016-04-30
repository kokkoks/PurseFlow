package com.suttanan.kok.purseflow.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.adapters.MyFragmentPagerAdapter;
import com.suttanan.kok.purseflow.fragments.HomeFragment;
import com.suttanan.kok.purseflow.fragments.InformaionFragment;
import com.suttanan.kok.purseflow.fragments.VisualizationFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity{

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.view_pager);

        List<Fragment> listFragments =  new ArrayList<Fragment>();
        listFragments.add(new HomeFragment());
        listFragments.add(new InformaionFragment());
        listFragments.add(new VisualizationFragment());

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
    }
}
