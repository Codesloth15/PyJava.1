package com.sys.system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Quiz_wrong_A extends AppCompatActivity {
   RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase db;

    Quiz_wrong_adaptor quiz_wrong_adaptor;
    String pyjava;
    ArrayList<Quiz_wrong_constructor> arrayList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_wrong);
        initializing();
        loadData();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFBB86FC")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Wrong Answer");
        SpannableString text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(text);

    }
    private void initializing(){

        arrayList =new ArrayList<>();
        recyclerView= findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        db = FirebaseDatabase.getInstance();
        quiz_wrong_adaptor = new Quiz_wrong_adaptor(this,arrayList);
        recyclerView.setAdapter(quiz_wrong_adaptor);
    }





    public void loadData(){
        Intent getIntents = getIntent();
        pyjava = getIntents.getStringExtra("easy");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        assert pyjava != null;
        DatabaseReference scoresWrong = FirebaseDatabase.getInstance().getReference().child(pyjava).child(userId);
      scoresWrong.addValueEventListener(new ValueEventListener() {
          @SuppressLint("NotifyDataSetChanged")
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              for (DataSnapshot snapshots : snapshot.getChildren()) {
                  String answer = snapshots.child("answer").getValue(String.class);
                  String question = snapshots.child("question").getValue(String.class);
                  String selectedOption = snapshots.child("selectedOption").getValue(String.class);
                  Quiz_wrong_constructor constructor = new Quiz_wrong_constructor(answer, selectedOption, question);
                  arrayList.add(constructor);
                  quiz_wrong_adaptor.notifyDataSetChanged();

              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
              Toast.makeText(getApplicationContext(), "Data loading failed.", Toast.LENGTH_SHORT).show();
          }
      });
    }

    @Override
    public void onBackPressed() {

        if(Objects.equals(pyjava, "Java_Easy_wrong")) {
            startActivity(new Intent(Quiz_wrong_A.this, easy2.class));
        }else{
            startActivity(new Intent(Quiz_wrong_A.this, easy3.class));
        }
    }
}