package com.sys.system;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class easy3 extends AppCompatActivity {
    TextView username, points;
    ImageView userimg,rank;
    CardView easy, hards,setC;
    private PopupWindow tooltipPopup;
    TextView  normal23,  total, total4, total3;
    int image[];
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_firemess, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; show dialog to confirm going back to menu
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to go back to the menu?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(easy3.this, category.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
                return true;
            case R.id.action_refresh:
                // refresh button clicked; implement refresh logic here
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(easy3.this, easy3.class);
                overridePendingTransition(0,0);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy3);

        // Set the title of the action bar
        getSupportActionBar().setTitle("Python");

        Drawable homeButton = getResources().getDrawable(R.drawable.ic_home);
        homeButton.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(homeButton);


        SpannableString text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(text);
        image = new int[]{R.drawable.silver,R.drawable.gold,R.drawable.bronze2};
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFBB86FC")));
        easy = findViewById(R.id.Easy);
        total = findViewById(R.id.total_questions);
        normal23 = findViewById(R.id.normal23);
        total4 = findViewById(R.id.total_questions4);
        hards = findViewById(R.id.hard);
        username = findViewById(R.id.user_name);
        points = findViewById(R.id.points);
        userimg = findViewById(R.id.user_img);
        rank = findViewById(R.id.rank);
      //  fabs23 = findViewById(R.id.fab);
        setC = findViewById(R.id.setC);
        retrieveStudentDetails();
        retrieveScore();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView rankImageView = findViewById(R.id.rank);
        rankImageView.setOnClickListener(v -> showTooltip(rankImageView));
/*
        fabs23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(easy3.this);
                builder.setTitle("Confirm Action");
                builder.setMessage("Are you sure you want to go back to the main menu?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(easy3.this, login.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
*/
        setC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Python_quiz3.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Python_quiz1.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();

// Check the third item (index 2)
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        // Handle Home item click

                        Intent homeIntent = new Intent(getApplicationContext(), student_page.class);
                        startActivity(homeIntent);
                        finish();
                        break;

                    case R.id.forum:
                        // Handle Quiz item click

                        Intent quizIntent = new Intent(getApplicationContext(), announce_view2.class);
                        startActivity(quizIntent);
                        finish();
                        break;

                    case R.id.message:
                        Intent intent = new Intent(getApplicationContext(), chat_view.class);
                        startActivity(intent);
                        finish();
                        break;


                }
                return true;
            }
        });


        DatabaseReference easymode = FirebaseDatabase.getInstance().getReference().child("Java_Easy");
        DatabaseReference normal = FirebaseDatabase.getInstance().getReference().child("Java_normal");
        DatabaseReference hard = FirebaseDatabase.getInstance().getReference().child("Java_hard");

        DatabaseReference python1 = FirebaseDatabase.getInstance().getReference().child("Python_Easy");
        DatabaseReference python2 = FirebaseDatabase.getInstance().getReference().child("Python_Normal");
        DatabaseReference python3 = FirebaseDatabase.getInstance().getReference().child("Python_Hard");

        DatabaseReference picture = FirebaseDatabase.getInstance().getReference().child("4pics");

        easymode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long easyCount = dataSnapshot.exists() ? 1 : 0; // Check if "Java_Easy" node exists and set count accordingly

                normal.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long normalCount = dataSnapshot.exists() ? 1 : 0; // Check if "Java_normal" node exists and set count accordingly

                        hard.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                long hardCount = dataSnapshot.exists() ? 1 : 0; // Check if "Java_hard" node exists and set count accordingly

                                python1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        long python1Count = dataSnapshot.exists() ? 1 : 0; // Check if "Python_Easy" node exists and set count accordingly

                                        python2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                long python2Count = dataSnapshot.exists() ? 1 : 0; // Check if "Python_normal" node exists and set count accordingly

                                                python3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        long python3Count = dataSnapshot.exists() ? 1 : 0; // Check if "Python_hard" node exists and set count accordingly

                                                        picture.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                long pictureCounts = dataSnapshot.exists() ? 1 : 0; // Check if "4pics" node exists and set count accordingly

                                                                // Calculate the total count for all categories and add a badge
                                                                int totalCount = (int) (easyCount + normalCount + hardCount + python1Count + python2Count + python3Count + pictureCounts);
/*
                                                                // Add a badge to the "Quiz" menu item
                                                                MenuItem quizMenuItem = bottomNavigationView.getMenu().findItem(R.id.forums);
                                                                BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(quizMenuItem.getItemId());
                                                                badgeDrawable.setVisible(true);
                                                                badgeDrawable.setNumber(totalCount);

 */
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                // Handle any errors that occur during the database operation
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        // Handle any errors that occur during the database operation
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                // Handle any errors that occur during the database operation
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Handle any errors that occur during the database operation
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle any errors that occur during the database operation
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors that occur during the database operation
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during the database operation
            }
        });

        //easy
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        //easy total questions
        DatabaseReference SetA = FirebaseDatabase.getInstance().getReference().child("Python_Easy");

        SetA.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int totalQuestions = (int) dataSnapshot.getChildrenCount(); // Get the count of child nodes directly

                total.setText("Items = " + totalQuestions);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });


        DatabaseReference SetC = FirebaseDatabase.getInstance().getReference().child("Python_Normal");

        SetC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int totalQuestions = (int) dataSnapshot.getChildrenCount(); // Get the count of child nodes directly

                normal23.setText("Items = " + totalQuestions);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });

        DatabaseReference SetD = FirebaseDatabase.getInstance().getReference().child("Python_Hard");

        SetD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int totalQuestions = (int) dataSnapshot.getChildrenCount(); // Get the count of child nodes directly

                total4.setText("Items = " + totalQuestions);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });


        hards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Python_quiz2.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();

            }
        });




    }


    private void showTooltip(View anchorView) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View tooltipView = inflater.inflate(R.layout.tooltip_layout, null);
        int score = Integer.parseInt(points.getText().toString().trim());
        // Customize tooltip text or perform other actions as needed
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tooltipText = tooltipView.findViewById(R.id.tooltipText);
        if(score>10&&score<25){
            tooltipText.setText("Rank/Bronze : with "+score+" points");
        }else if(score>=25&&score<40){
            tooltipText.setText("Rank/Silver : with "+score+" points");

        }else if(score>=40){
            tooltipText.setText("Rank/Gold : with "+score+" points");
        }else{
            tooltipText.setText("Rank/Newbie : with "+score+" points");
        }


        tooltipPopup = new PopupWindow(tooltipView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        tooltipPopup.showAsDropDown(anchorView, 0, -anchorView.getHeight());
    }

    public void ranking(int score){
        if(score>10&&score<25){
            rank.setImageResource(image[2]);
        }else if(score>=25&&score<40){
            rank.setImageResource(image[0]);

        }else if(score>=40){
            rank.setImageResource(image[1]);
        }
    }

    private void cancelNotification(int notificationId) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

    private void clearNotification2() {
        // Create an object of NotificationManager class to notify the user
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
    }

    private void clearNotification() {
        // Create a NotificationManager instance
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Cancel the notification by its unique identifier
        notificationManager.cancel("Java_Easy".hashCode());
        notificationManager.cancel("Java_normal".hashCode());
        notificationManager.cancel("Java_hard".hashCode());
        notificationManager.cancel("4pics".hashCode());

        // Stop playing the sound

    }

    private void retrieveStudentDetails() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference().child("Student").child(userId);
        studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String usernameValue = dataSnapshot.child("name").getValue(String.class);
                    String imageUrl = dataSnapshot.child("image").getValue(String.class);

                    if (usernameValue != null) {
                        username.setText(usernameValue);
                    }

                    // Load the image into the ImageView using a library like Picasso or Glide
                    // For example, using Picasso:
                    if (imageUrl != null) {
                        Picasso.get().load(imageUrl).into(userimg);
                    }
                } else {
                    // Handle the case if student data does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error if necessary
            }
        });
    }

    private void retrieveScore() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference easyScoreRef = rootRef.child("Python_easy_scores").child(userId);
        DatabaseReference hardScoreRef = rootRef.child("Python_hard_scores").child(userId);
        DatabaseReference picScoreRef = rootRef.child("Python_normal_scores").child(userId);

        final int[] totalScore = {0}; // Initialize the total score variable
        final int[] nodesRetrieved = {0}; // Counter for tracking the number of nodes retrieved

        ValueEventListener scoreListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Integer scoreValue = dataSnapshot.getValue(Integer.class);
                    if (scoreValue != null) {
                        totalScore[0] += scoreValue; // Add the score to the total score
                    }
                }

                // Increase the counter
                nodesRetrieved[0]++;

                // Check if all nodes have been retrieved
                if (nodesRetrieved[0] == 3) {
                    // All nodes have been retrieved, update the UI or perform additional operations
                    points.setText(String.valueOf(" "+totalScore[0])); // Example: Set total score in a TextView
                }
                ranking(Integer.parseInt(points.getText().toString().trim()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error if necessary
            }
        };

        easyScoreRef.addListenerForSingleValueEvent(scoreListener);
        hardScoreRef.addListenerForSingleValueEvent(scoreListener);
        picScoreRef.addListenerForSingleValueEvent(scoreListener);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(easy3.this,student_page.class);
        startActivity(intent);
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

