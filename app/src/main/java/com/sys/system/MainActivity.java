package com.sys.system;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {
    // LottieAnimationView lottie;
    AppCompatButton button1;
    Handler handler;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //lottie = findViewById(R.id.lottie23);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // lottie.animate().setDuration(3000).setStartDelay(200);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();

        }

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,login.class));
                finish();
            }
        },2000);
    }
}
