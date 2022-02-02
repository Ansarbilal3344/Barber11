package com.developer.barber_admin;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.developer.barber_admin.fragments.Booking_list_Fragement;
import com.developer.barber_admin.fragments.Your_Booking_Fragement;


public class Barber_pager_Adapter extends FragmentStatePagerAdapter {
Context context;

    public Barber_pager_Adapter(FragmentManager fm, Context c) {
        super(fm);
        context = c;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        if (position==0){
            fragment=new Your_Booking_Fragement(context);
        }else if (position==1){
            fragment=new Booking_list_Fragement(context);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
