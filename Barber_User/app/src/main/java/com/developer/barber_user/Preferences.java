package com.developer.barber_user;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Abdul Qadir on 9/26/2017.
 */

public class Preferences {

    private static Preferences mInstance;
    private static SharedPreferences sharedPreferences;
    private Context context;


    public static synchronized Preferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Preferences(context);
        }
        return mInstance;
    }

    private Preferences(Context context) {
        this.context = context;
        String prefs = context.getResources().getString(R.string.app_name);
        sharedPreferences = context.getSharedPreferences(prefs, Context.MODE_PRIVATE);
    }

    public void set(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void setstatus(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getstatus(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void set(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void set(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public String get(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getint(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public void setint() {
        // sharedPreferences.se(key,-1);
        sharedPreferences.edit().putInt("1", 1).apply();
    }


    public boolean has(String key) {
        return sharedPreferences.contains(key);
    }


    public boolean remove(String key) {
        return sharedPreferences.edit().remove(key).commit();
    }

    public boolean getbool(String key) {
        return sharedPreferences.getBoolean(key, true);
    }
}
