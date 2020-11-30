package com.example.cook_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class Local_registration extends AppCompatActivity {
    TextInputLayout first_name, last_name, email_address, pass_w, confirm_pass;
    Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_registration);

        first_name = findViewById(R.id.fname);
        last_name = findViewById(R.id.lname);
        email_address = findViewById(R.id.email);
        pass_w = findViewById(R.id.password);
        confirm_pass = findViewById(R.id.cpassword);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        login.setOnClickListener(v -> {

            Intent intent = new Intent (Local_registration.this,Login_options.class);

            startActivity(intent);

            finish();

        });


    }
}