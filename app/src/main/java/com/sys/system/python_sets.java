package com.sys.system;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class python_sets extends AppCompatActivity {
    CardView setA, setB,setC;
    ImageView fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_python_sets);
    //    getSupportActionBar().setTitle("Python Quiz Sets");
     //   Drawable homeButton = getResources().getDrawable(R.drawable.ic_home);
       // homeButton.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
       // getSupportActionBar().setHomeAsUpIndicator(homeButton);

      //  SpannableString text = new SpannableString(getSupportActionBar().getTitle());
     //   text.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
       // getSupportActionBar().setTitle(text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // lottie.animate().setDuration(3000).setStartDelay(200);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();

        }


        //   ActionBar actionBar = getSupportActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFBB86FC")));

        setA = findViewById(R.id.setA);
        setB = findViewById(R.id.Setb);
        setC = findViewById(R.id.setC);

        fab = findViewById(R.id.fab);


        setA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Python Easy",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),Easy_python.class);
                overridePendingTransition(0,0);
                startActivity(i);
                finish();
            }
        });

        setB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Normal",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),Normal_pyhon.class);
                overridePendingTransition(0,0);
                startActivity(i);
                finish();
            }
        });


        setC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Hard",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),Python_hard23.class);
                overridePendingTransition(0,0);
                startActivity(i);
                finish();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Closed",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),quiz_category.class);
                overridePendingTransition(0,0);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),quiz_category.class);
        startActivity(i);
        overridePendingTransition(0,0);
        finish();
        super.onBackPressed();
    }
}