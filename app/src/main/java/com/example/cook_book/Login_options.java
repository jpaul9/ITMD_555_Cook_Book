package com.example.cook_book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login_options extends AppCompatActivity {

    EditText username, pass;
    TextView signup;
    Button login;
    ImageView google_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        username = findViewById(R.id.editText_Name);
        pass = findViewById(R.id.editText_Password);
        signup = findViewById(R.id.sign_up);
        login = findViewById(R.id.sign_in);
        google_btn = findViewById(R.id.google_icon);

        
    }
}