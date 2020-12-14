package com.example.cook_book.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cook_book.R;
import com.example.cook_book.registration.Registration;
import com.example.cook_book.main.Recipes_Dashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class Login_options extends AppCompatActivity {

    TextInputLayout user_name, pass;
    TextView signup;
    Button login, forgot;
    ImageView google_btn;
    String blank = "";

    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseFirestore fStore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        user_name = findViewById(R.id.username);
        pass =  findViewById(R.id.password);
        signup = findViewById(R.id.sign_up);
        login = findViewById(R.id.sign_in);
        google_btn = findViewById(R.id.google_icon);
        forgot = findViewById(R.id.forgotpass);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();




        signup.setOnClickListener(v -> {
            Intent intent = new Intent (Login_options.this, Registration.class);
            startActivity(intent);
            finish();
        });

    }



    public void login(View view){

        String mEmail = user_name.getEditText().getText().toString();
        String mPassword = pass.getEditText().getText().toString();

        if(mPassword.isEmpty()){
            pass.setError("Password is required");
            return;
        }
        if (mEmail.isEmpty()) {
            user_name.setError("Email is required");
            return;
        }

        if(mEmail.isEmpty() || mPassword.isEmpty()){
            Toast.makeText(Login_options.this, "Fields Are Required.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fAuth.getCurrentUser().isAnonymous()){
            user = fAuth.getCurrentUser();
            fStore.collection("recipes").document(user.getUid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Login_options.this, "All Temp files deleted", Toast.LENGTH_SHORT).show();

                }
            });

            user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Login_options.this, "Temp user profile deleted", Toast.LENGTH_SHORT).show();
                }
            });

        }


        fAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(Login_options.this, "Logged in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Recipes_Dashboard.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Login_options.this, "Login Faulted" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
