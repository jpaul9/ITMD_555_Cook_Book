package com.example.cook_book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 2000;

    // Variables for animation logic
    Animation top_anima, bottom_anima;
    ImageView logo_image, logo_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation calls
        top_anima = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_anima = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo_image = findViewById(R.id.logo_image);
        logo_text = findViewById(R.id.logo_title);

        logo_image.setAnimation(top_anima);
        logo_text.setAnimation(bottom_anima);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, Login_options.class);
            startActivity(intent);
            finish();
        },SPLASH_SCREEN);


    }
}