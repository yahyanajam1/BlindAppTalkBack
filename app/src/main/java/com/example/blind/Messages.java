package com.example.blind;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;




public class Messages  extends AppCompatActivity {

    TabLayout tabLay;
    ViewPager viewPage;
    msgViewPagerAdapter adapter;
    TabItem messagingTab;
    TabItem inboxTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        tabLay = findViewById(R.id.mtabs);
        viewPage = findViewById(R.id.pager);
        messagingTab = findViewById(R.id.tbm1);
        inboxTab = findViewById(R.id.tbm2);

        adapter = new msgViewPagerAdapter(getSupportFragmentManager(),tabLay.getTabCount());
        viewPage.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setElevation(0);



                tabLay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPage.setCurrentItem(tab.getPosition());


                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });


                viewPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLay));

    }



}





