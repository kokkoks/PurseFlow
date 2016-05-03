package com.suttanan.kok.purseflow.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TabHost;

import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.adapters.MyFragmentPagerAdapter;
import com.suttanan.kok.purseflow.fragments.HomeFragment;
import com.suttanan.kok.purseflow.fragments.InformaionFragment;
import com.suttanan.kok.purseflow.fragments.VisualizationFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener{

    ViewPager viewPager;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPagers();
        initTabHost();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int selectedPage) {
        tabHost.setCurrentTab(selectedPage);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int selectedPage = tabHost.getCurrentTab();
        viewPager.setCurrentItem(selectedPage);
    }

    public class FakeContent implements TabHost.TabContentFactory{

        Context context;

        public FakeContent(Context mcontext) {
            context = mcontext;
        }

        @Override
        public View createTabContent(String tag) {
            View fakeView = new View(context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);
            return fakeView;
        }
    }

    private void initTabHost() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        String[] tabNames = {"Home", "Information", "Visual"};

        for(int i = 0; i < tabNames.length; i++){
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabNames[i]);
            tabSpec.setIndicator(tabNames[i]);
            tabSpec.setContent(new FakeContent(getApplicationContext()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
    }

    private void initViewPagers(){
        viewPager = (ViewPager)findViewById(R.id.view_pager);

        List<Fragment> listFragments =  new ArrayList<Fragment>();
        listFragments.add(new HomeFragment());
        listFragments.add(new InformaionFragment());
        listFragments.add(new VisualizationFragment());
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOnPageChangeListener(this);
    }
}
