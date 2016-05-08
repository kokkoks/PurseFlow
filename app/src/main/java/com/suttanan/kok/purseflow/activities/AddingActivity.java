package com.suttanan.kok.purseflow.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.suttanan.kok.purseflow.R;
import com.suttanan.kok.purseflow.adapters.SupportFragmentTabListener;
import com.suttanan.kok.purseflow.fragments.add_page.ExpansesFragment;
import com.suttanan.kok.purseflow.fragments.add_page.IncomesFragment;


/**
 * Created by KOKKOK on 5/8/2016.
 */
public class AddingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_layout);
        initTabs();
    }

    private void initTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab expanseTab = actionBar.newTab().setText("Expenses").setTabListener(new SupportFragmentTabListener<ExpansesFragment>(this, "Expenses", ExpansesFragment.class));
        ActionBar.Tab incomeTab = actionBar.newTab().setText("Incomes").setTabListener(new SupportFragmentTabListener<IncomesFragment>(this, "Information", IncomesFragment.class));


        actionBar.addTab(expanseTab);
        actionBar.addTab(incomeTab);
        actionBar.selectTab(expanseTab);
    }
}
