package com.developer.barber_user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText input_email, input_password;
    TextView btn_go_register, btn_login;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    String type;
    ImageView show_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        input_email = findViewById(R.id.login_email_user);
        show_password = findViewById(R.id.show_pwd);
        input_password = findViewById(R.id.login_password_user);
        btn_login = findViewById(R.id.login_user);
        btn_go_register = findViewById(R.id.btn_go_register_user);
        btn_login.setOnClickListener(this);
        btn_go_register.setOnClickListener(this);


        boolean check = true;

        show_password.setImageResource(R.drawable.eye_icon);


        show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_password.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    show_password.setImageResource(R.drawable.unhide);
                    input_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    show_password.setImageResource(R.drawable.eye_icon);
                    input_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go_register_user:
                startActivity(new Intent(LoginActivity.this, Registeration.class));
                finish();
                break;
            case R.id.login_user:
                loginuser();
                break;
        }
    }

    private void loginuser() {
        progressDialog.show();
        String email = input_email.getText().toString().trim();
        String pasword = input_password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Preferences.getInstance(LoginActivity.this).set("login", "user");
                    startActivity(new Intent(LoginActivity.this, User_Main.class));
                    finish();


                } else {
                    progressDialog.dismiss();
                    // Toast.makeText(LoginActivity.this, "Error! unable to login", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
