package com.example.cook_book.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cook_book.R;
import com.example.cook_book.login.Login_options;
import com.example.cook_book.main.Recipes_Dashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    TextInputLayout email_address,pass_w,vpass_w;
    Button register, login;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_registration);


        email_address = findViewById(R.id.email);
        pass_w = findViewById(R.id.password);
        vpass_w = findViewById(R.id.vpassword);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(v -> {
            Intent intent = new Intent (Registration.this, Login_options.class);
            startActivity(intent);
            finish();

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email_address.getEditText().getText().toString();
                String pw = pass_w.getEditText().getText().toString();
                String pwc = vpass_w.getEditText().getText().toString();;

                if(em.isEmpty()||pw.isEmpty()||pwc.isEmpty()){

                    email_address.setError("Please complete all fields");
                    pass_w.setError("Please complete all fields");
                    vpass_w.setError("Please complete all fields");
                    return;
                }

                if(!pw.equals(pwc)){
                    vpass_w.setError("Passwords do not match");
                    return;

                }

                AuthCredential credential = EmailAuthProvider.getCredential(em,pwc);

                fAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Registration.this, "Thank you for registering", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registration.this, Recipes_Dashboard.class);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registration.this, "Error. " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }//end of onclick
        });//end of register.onClick listener
    }
}