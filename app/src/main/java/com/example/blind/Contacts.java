package com.example.blind;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class Contacts extends AppCompatActivity {

    TabLayout tabsLays;
    ViewPager viewsPages;
    consViewPagerAdapter adapter;
    TabItem callingTab;
    TabItem callLogTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        tabsLays = findViewById(R.id.ctabs);
        viewsPages = findViewById(R.id.pager);
        callingTab = findViewById(R.id.tabc1);
        callLogTab = findViewById(R.id.tabc2);

        adapter = new consViewPagerAdapter(getSupportFragmentManager(),tabsLays.getTabCount());
        viewsPages.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setElevation(0);

        tabsLays.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewsPages.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewsPages.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabsLays));


    }





}





