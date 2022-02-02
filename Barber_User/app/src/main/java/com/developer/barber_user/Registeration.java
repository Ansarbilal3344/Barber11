package com.developer.barber_user;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registeration extends AppCompatActivity implements View.OnClickListener {
    EditText name, email, password,phone;
    TextView btn_go_login,btn_register;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    final static int SELECT_IMAGE = 123;
    final static int Request_Camera_Code = 321;
    public static final int PERMISSIONS_CODE_STORAGE = 843;
    public static final int PERMISSIONS_CODE_CAMERA = 844;
    public static  String[] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        name = findViewById(R.id.register_name_user);
        email = findViewById(R.id.register_email_user);
        password = findViewById(R.id.register_password_user);
        phone = findViewById(R.id.register_phone_user);
        btn_register = findViewById(R.id.btn_register_user);
        btn_go_login = findViewById(R.id.btn_go_login_user);
        btn_register.setOnClickListener(this);
        btn_go_login.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go_login_user:
                startActivity(new Intent(Registeration.this, LoginActivity.class));
                finish();
                break;
            case R.id.btn_register_user:
                regiteruser();
                break;
//            case R.id.img_user_profile_register:
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), PERMISSION[0]) != PackageManager.PERMISSION_GRANTED
//                        || ContextCompat.checkSelfPermission(getApplicationContext(), PERMISSION[1]) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(PERMISSION, PERMISSIONS_CODE_STORAGE);
//                } else if (ContextCompat.checkSelfPermission(getApplicationContext(), PERMISSION[2]) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(PERMISSION, PERMISSIONS_CODE_CAMERA);
//                } else
//                    imageDialoge();
//                break;
        }
    }

    private void regiteruser() {
        boolean valid = validate();
        if (valid) {
            progressDialog.show();
            final String user_name = name.getText().toString().trim();
            final String email_Add = email.getText().toString().trim();
            final String pass = password.getText().toString().trim();
            final String phone_nmber = phone.getText().toString().trim();
            String url = "https://barber-bookings-c1932-default-rtdb.firebaseio.com";
            FirebaseApp.initializeApp(this);
            mAuth.createUserWithEmailAndPassword(email_Add, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String userID = mAuth.getCurrentUser().getUid();
                        DatabaseReference db = FirebaseDatabase.getInstance(url).getReference().child("user").child(userID);
                        db.child("name").setValue(user_name);
                        db.child("email").setValue(email_Add);
                        db.child("password").setValue(pass);
                        db.child("phone").setValue(phone_nmber);

                        progressDialog.dismiss();
                        startActivity(new Intent(Registeration.this,User_Main.class));
                        finish();
                    }else {
                        progressDialog.dismiss();
                      //  Toast.makeText(Registeration.this, "Error! failed to register", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Registeration.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public boolean validate() {
        boolean valid = true;

        String username = name.getText().toString();
        String emailaddress = email.getText().toString();
        String pasword = password.getText().toString();


        if (username.isEmpty()) {
            name.setError("please enter name");
            valid = false;
        } else if (username.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }


        if (pasword.isEmpty() || pasword.length() <= 5) {
            password.setError("your password contain atleast 6 characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

}
