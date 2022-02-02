package com.developer.barber_user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class User_Main extends AppCompatActivity {

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
                Preferences.getInstance(User_Main.this).set("login", null);
                Intent i = new Intent(User_Main.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        tabLayout = findViewById(R.id.admin_main_tablayout);
        viewPager = findViewById(R.id.admin_main_pager);
        viewPager.setAdapter(new User_pager_Adapter(getSupportFragmentManager(), this));

        tabLayout.addTab(tabLayout.newTab().setText("Available Slots"));
        tabLayout.addTab(tabLayout.newTab().setText("Your Bookings"));

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



    private String getAlphaNumericString() {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}