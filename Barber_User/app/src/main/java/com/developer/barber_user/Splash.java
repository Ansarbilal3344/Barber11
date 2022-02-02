package com.developer.barber_user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;



public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                final String check = Preferences.getInstance(Splash.this).get("login");

                if (check == null) {
                    Intent intent = new Intent(Splash.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else if(check.equals("user")) {
                    Intent intent = new Intent(Splash.this, User_Main.class);
                    startActivity(intent);
                    finish();
                    return;
                }

            }
        }, 1500);
    }
}
