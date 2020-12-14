package com.example.cook_book.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cook_book.R;
import com.example.cook_book.login.Login_options;
import com.example.cook_book.main.Recipes_Dashboard;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Splash_Screen extends AppCompatActivity {

    private static int delayTime = 2000;

    FirebaseAuth fAuth;



    // Variables for animation logic
    Animation top_anima, bottom_anima;
    ImageView logo_image, logo_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_startup);

        fAuth = FirebaseAuth.getInstance();

        //Animation calls
        top_anima = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_anima = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo_image = findViewById(R.id.logo_image);
        logo_text = findViewById(R.id.logo_title);

        logo_image.setAnimation(top_anima);
        logo_text.setAnimation(bottom_anima);


        new Handler().postDelayed(() -> {

            if(fAuth.getCurrentUser()!=null){
                Intent intent = new Intent(Splash_Screen.this, Recipes_Dashboard.class);
                startActivity(intent);
                finish();
            }
            else{
                fAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Splash_Screen.this, "Welcome new user", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Splash_Screen.this, Recipes_Dashboard.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }

        },delayTime);


    }
}