package com.sys.system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import pl.droidsonroids.gif.GifImageView;

public class easy_score extends AppCompatActivity {
    TextView user_id, score, totalQuestions, passedOrFailed, percentage;
    GifImageView trophy_logo;
    Button exit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
            setContentView(R.layout.activity_easy_score);
            exit = findViewById(R.id.exits2);
            user_id = findViewById(R.id.user_id);
            score = findViewById(R.id.score);
            percentage = findViewById(R.id.percentage);
            passedOrFailed = findViewById(R.id.pass);
            totalQuestions = findViewById(R.id.total_questions);
            trophy_logo = findViewById(R.id.trophy_logo);

            // Set the title of the action bar

            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(easy_score.this, "Back to QUIZ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), easy2.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                }
            });

            // Retrieve user ID from FirebaseAuth
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();
                user_id.setText(userId);
            }

// Retrieve score and total questions from Firebase Realtime Database
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            databaseRef.child("Java_score").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        int quizScore = dataSnapshot.getValue(Integer.class);
                        score.setText("Score: " + quizScore);

                        // Center the score vertically
                        score.setGravity(Gravity.CENTER_VERTICAL);

                        // Retrieve total questions from Firebase Realtime Database
                        databaseRef.child("Java_quiz").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    int total = (int) dataSnapshot.getChildrenCount();
                                    totalQuestions.setText("Total questions: " + total);

                                    // Calculate percentage passed
                                    double percentagePassed = ((double) quizScore / total) * 100;
                                    if (percentagePassed >= 50) {
                                        passedOrFailed.setText("Passed");
                                    } else {
                                        passedOrFailed.setText("Failed");
                                        trophy_logo.setImageResource(R.drawable.cry);
                                        percentage.setTextColor(Color.RED);
                                    }

                                    // Format percentage passed as an integer
                                    DecimalFormat df = new DecimalFormat("#");
                                    percentage.setText("Percentage: " + df.format(percentagePassed) + "%");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e("scoreview_breeding", "Failed to read value.", databaseError.toException());
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("scoreview_breeding", "Failed to read value.", databaseError.toException());
                }
            });

        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(easy_score.this, easy2.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        super.onBackPressed();
    }
}
