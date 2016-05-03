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

//        initViewPagers();
//        initTabHost();
        initTabs();
    }

//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int selectedPage) {
//        tabHost.setCurrentTab(selectedPage);
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
//
//    @Override
//    public void onTabChanged(String tabId) {
//        int selectedPage = tabHost.getCurrentTab();
//        viewPager.setCurrentItem(selectedPage);
//    }

//    public class FakeContent implements TabHost.TabContentFactory{
//
//        Context context;
//
//        public FakeContent(Context mcontext) {
//            context = mcontext;
//        }
//
//        @Override
//        public View createTabContent(String tag) {
//            View fakeView = new View(context);
//            fakeView.setMinimumHeight(0);
//            fakeView.setMinimumWidth(0);
//            return fakeView;
//        }
//    }

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

//    private void initTabHost() {
//        tabHost = (TabHost) findViewById(R.id.tabHost);
//        tabHost.setup();
//
//        String[] tabNames = {"Home", "Information", "Visual"};
//
//        for(int i = 0; i < tabNames.length; i++){
//            TabHost.TabSpec tabSpec;
//            tabSpec = tabHost.newTabSpec(tabNames[i]);
//            tabSpec.setIndicator(tabNames[i]);
//            tabSpec.setContent(new FakeContent(getApplicationContext()));
//            tabHost.addTab(tabSpec);
//        }
//        tabHost.setOnTabChangedListener(this);
//    }

//    private void initViewPagers(){
//        viewPager = (ViewPager)findViewById(R.id.view_pager);
//
//        List<Fragment> listFragments =  new ArrayList<Fragment>();
//        listFragments.add(new HomeFragment());
//        listFragments.add(new InformaionFragment());
//        listFragments.add(new VisualizationFragment());
//        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), listFragments);
//        viewPager.setAdapter(myFragmentPagerAdapter);
//        viewPager.setOnPageChangeListener(this);
//    }
}
