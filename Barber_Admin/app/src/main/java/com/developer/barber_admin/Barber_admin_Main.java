package com.developer.barber_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Barber_admin_Main extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__main);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.getInstance(Barber_admin_Main.this).set("login", null);
                Intent i = new Intent(Barber_admin_Main.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        tabLayout = findViewById(R.id.admin_main_tablayout);
        viewPager = findViewById(R.id.admin_main_pager);
        viewPager.setAdapter(new Barber_pager_Adapter(getSupportFragmentManager(), this));

        tabLayout.addTab(tabLayout.newTab().setText("Booked Slots"));
        tabLayout.addTab(tabLayout.newTab().setText("ALL Slots"));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

       /* String type = getIntent().getStringExtra("ctype");
        if (type != null) {
            if (type.equals("student")) {
                viewPager.setCurrentItem(0);
                tabLayout.setupWithViewPager(viewPager);
            }else if (type.equals("teacher")) {
                viewPager.setCurrentItem(1);
                tabLayout.setupWithViewPager(viewPager);

            } else if (type.equals("course")) {
                viewPager.setCurrentItem(2);
                tabLayout.setupWithViewPager(viewPager);

            }
        }*/
    }
}