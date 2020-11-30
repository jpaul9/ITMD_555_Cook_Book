package com.example.cook_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;


public class Login_options extends AppCompatActivity {

    TextInputLayout username, pass;
    TextView signup;
    Button login;
    ImageView google_btn;
    //signup = (TextView) findViewById(R.id.sign_up);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        username = findViewById(R.id.username);
        pass =  findViewById(R.id.password);
        signup = findViewById(R.id.sign_up);
        login = findViewById(R.id.sign_in);
        google_btn = findViewById(R.id.google_icon);

        signup.setOnClickListener(v -> {
            Intent intent = new Intent (Login_options.this, Registration.class);
            startActivity(intent);
            finish();
        });



    }



}