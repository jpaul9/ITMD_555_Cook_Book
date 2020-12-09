package com.example.cook_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Login_options extends AppCompatActivity {

    TextInputLayout user_name, pass;
    TextView signup;
    Button login;
    ImageView google_btn;
    String blank = "";

    //signup = (TextView) findViewById(R.id.sign_up);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        user_name = findViewById(R.id.username);
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



    private Boolean validateUsername(){

        String val = user_name.getEditText().getText().toString();

        if (val.isEmpty()){
            user_name.setError("Username is required");
            return false;
        }
        else{
            user_name.setError(null);
            user_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validate_password(){
        String val = pass.getEditText().getText().toString();

        if(val.isEmpty()){
            pass.setError("Password is required");
            return false;
        }

        else{
            pass.setError(null);
            pass.setErrorEnabled(false);
            return true;
        }
    }

    public void login(View view){

        if (!validate_password()|!validateUsername()){
            return;
        }
        else{
        validUser();
        }
    }

    private void validUser() {

        String enteredUsername = user_name.getEditText().getText().toString().trim();
        String enteredPassword = pass.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("username").equalTo(enteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    user_name.setError(null);
                    user_name.setErrorEnabled(false);

                    String passwordFromFDB = dataSnapshot.child(enteredUsername).child("password").getValue(String.class);

                    if (passwordFromFDB.equals(enteredPassword)){

                        pass.setError(null);
                        pass.setErrorEnabled(false);

                        String fnameFromFDB = dataSnapshot.child(enteredUsername).child("firstname").getValue(String.class);
                        String lnameFromFDB = dataSnapshot.child(enteredUsername).child("lastname").getValue(String.class);
                        String uNameFromFDB = dataSnapshot.child(enteredUsername).child("username").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Recipes_Dashboard.class);
                        intent.putExtra("firstname",fnameFromFDB);
                        intent.putExtra("lastname",lnameFromFDB);
                        intent.putExtra("username",uNameFromFDB);
                        intent.putExtra("password",passwordFromFDB);

                        startActivity(intent);
                        recreate();




                    }
                    else{
                        pass.setError("Incorrect password");
                        pass.requestFocus();

                    }
                }
                else{
                    user_name.setError("Username not found");
                    user_name.requestFocus();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}