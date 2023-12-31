package com.sys.system;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private AppCompatButton loginButton;
    private TextView forgotPasswordButton;
    Handler handler;
    private String email;
    private FirebaseAuth mAuth;


    private TextView create;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.plogin_password);
        loginButton = findViewById(R.id.btn_login);
        forgotPasswordButton = findViewById(R.id.txtforgot);
        create = findViewById(R.id.create);
        firebaseAuth = FirebaseAuth.getInstance();
        handler =  new Handler();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
            mDatabase = FirebaseDatabase.getInstance().getReference();


          //  create.setOnClickListener(new View.OnClickListener() {
             //   @Override
              //  public void onClick(View v) {
                   // showLoginPrompt();
              //  }
         //   });

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    forgotYourPassword();
                }
            });


            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),signup.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    private void showLoginPrompt() {
        // Create a custom login prompt dialog using AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(login.this);
        LayoutInflater li = LayoutInflater.from(login.this);
        View promptsView = li.inflate(R.layout.activity_login_prompt, null);
        alertDialogBuilder.setView(promptsView);
        Button buttonCancel = promptsView.findViewById(R.id.buttonCancel);
        EditText editTextEmail = promptsView.findViewById(R.id.editTextEmail);
        EditText editTextPassword = promptsView.findViewById(R.id.editTextPassword);
        Button buttonLogin = promptsView.findViewById(R.id.buttonLogin);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        // Handle the login button click inside the prompt
        buttonLogin.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                loginUser(email, password, alertDialog);
            } else {
                Toast.makeText(login.this, "Please enter your Admin email and password", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
    }

    private void loginUser(String email, String password, AlertDialog alertDialog) {
        progressDialog = new ProgressDialog(login.this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // Login successful, check if the user is an admin or student
                        checkUserType();
                        alertDialog.dismiss();
                    } else {
                        // Login failed
                        Toast.makeText(login.this, "Login failed. Please check your credentials " + email, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUserType() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Reference to the "ADMIN" node in your database
            DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference().child("ADMIN").child(userId);

            // Check if the user is an admin
            ValueEventListener adminListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // User is an admin, navigate to AdminPageActivity
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(login.this, admin_create.class));
                        finish();
                    } else {
                        // User is neither a student nor an admin
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(login.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error if necessary
                }
            };

            adminRef.addListenerForSingleValueEvent(adminListener);
        } else {
            Toast.makeText(login.this, "Please verify your email before logging in " + email, Toast.LENGTH_SHORT).show();
        }
    }



    private void forgotYourPassword() {LayoutInflater li = LayoutInflater.from(login.this);
        View promptsView = li.inflate(R.layout.activity_english_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(login.this);
// set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        final EditText userMail = (EditText) promptsView.findViewById(R.id.forgot_pass);
        Button Ok_button = promptsView.findViewById(R.id.Send_email);
        ImageView close = promptsView.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss(); // Close the AlertDialog
            }
        });

        Ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userMail.getText().toString().trim();

                // Check if the email is empty
                if (email.isEmpty()) {
                    Toast.makeText(login.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        try {
                            if (task.isSuccessful()) {

                                Toast.makeText(login.this, "Password reset email sent to " + email, Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else {
                                Toast.makeText(login.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        alertDialog.show();
    }
        @Override
        protected void onStart() {
            super.onStart();

            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null && currentUser.isEmailVerified()) {
                String userId = currentUser.getUid();
                DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference().child("Student").child(userId);
                DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference().child("ADMIN").child(userId);

                // Create and show the progress dialog
                ProgressDialog progressDialog = ProgressDialog.show(login.this, "", "Loading...", true);

                ValueEventListener studentListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Dismiss the progress dialog
                        progressDialog.dismiss();

                        if (dataSnapshot.exists()) {
                            // User is a student, navigate to StudentPageActivity
                            startActivity(new Intent(login.this, student_page.class));
                            finish();
                        } else {
                            // User is not a student, check if user is an admin
                            ValueEventListener adminListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // Dismiss the progress dialog
                                    progressDialog.dismiss();

                                    if (dataSnapshot.exists()) {
                                        // User is an admin, navigate to AdminPageActivity
                                        startActivity(new Intent(login.this, Admin_page.class));
                                        finish();
                                    } else {
                                        // User is neither a student nor an admin
                                        Toast.makeText(login.this, "User not found", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle database error if necessary
                                }
                            };

                            adminRef.addListenerForSingleValueEvent(adminListener);
                        }
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle database error if necessary
                    }
                };

                studentRef.addListenerForSingleValueEvent(studentListener);
            }
        }


    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Please enter your email");
            emailEditText.requestFocus();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Remove the error message after 5 seconds
                    emailEditText.setError(null);
                }
            }, 1000);
            return;

        }

        if (password.isEmpty()) {
            passwordEditText.setError("Please enter your password");
            passwordEditText.requestFocus();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Remove the error message after 5 seconds
                    passwordEditText.setError(null);
                }
            }, 1000);
            return;
        }

        // Show progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(login.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            // Hide progress dialog
            progressDialog.dismiss();

            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    if (user.isEmailVerified()) {
                        String userId = user.getUid();
                        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference().child("Student").child(userId);
                        DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference().child("ADMIN").child(userId);

                        ValueEventListener studentListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // User is a student, navigate to StudentPageActivity
                                    startActivity(new Intent(login.this, student_page.class));
                                    finish();
                                } else {
                                    // User is not a student, check if user is an admin
                                    ValueEventListener adminListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                // User is an admin, navigate to AdminPageActivity
                                                startActivity(new Intent(login.this, Admin_page.class));
                                                finish();
                                            } else {
                                                // User is neither a student nor an admin
                                                Toast.makeText(login.this, "User not found", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Handle database error if necessary
                                        }
                                    };

                                    adminRef.addListenerForSingleValueEvent(adminListener);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle database error if necessary
                            }
                        };

                        studentRef.addListenerForSingleValueEvent(studentListener);
                    } else {
                        Toast.makeText(login.this, "Please verify your email before logging in "+email, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // User is null, handle the case if needed
                }
            } else {
                Toast.makeText(login.this, "Login failed. Please check your credentials "+email, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
        builder.setMessage("Are you sure you want to close the application?")
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}






