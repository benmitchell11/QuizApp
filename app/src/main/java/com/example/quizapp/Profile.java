package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView userEmailTextView, usernameTextView, ageTextView, countryTextView;
    private TextInputEditText editUsername, editAge, editCountry;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button editButton, saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Log.d("ProfileActivity", "Current User: " + currentUser);

        userEmailTextView = findViewById(R.id.email);
        usernameTextView = findViewById(R.id.username);
        ageTextView = findViewById(R.id.age);
        countryTextView = findViewById(R.id.country);

        editButton = findViewById(R.id.btn_edit);
        editUsername = findViewById(R.id.editUsername);
        editAge = findViewById(R.id.editAge);
        editCountry = findViewById(R.id.editCountry);
        saveButton = findViewById(R.id.btn_save);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                userEmailTextView.setVisibility(View.GONE);
                editAge.setVisibility(View.VISIBLE);
                editCountry.setVisibility(View.VISIBLE);
                editUsername.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.GONE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = currentUser.getUid();

                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String existingEmail = dataSnapshot.child("email").getValue(String.class);
                        String newUsername = editUsername.getText().toString().trim();
                        String newAge = editAge.getText().toString().trim();
                        String newCountry = editCountry.getText().toString().trim();

                        String newEmail = (existingEmail != null) ? existingEmail : "";
                        Boolean isAdmin = dataSnapshot.child("admin").getValue(Boolean.class);

                        if (newUsername.isEmpty()) {
                            newUsername = dataSnapshot.child("username").getValue(String.class);
                        }
                        if (newAge.isEmpty()) {
                            newAge = dataSnapshot.child("age").getValue(String.class);
                        }
                        if (newCountry.isEmpty()) {
                            newCountry = dataSnapshot.child("country").getValue(String.class);
                        }

                        User updatedUser = new User(existingEmail, newUsername, newAge, newCountry, isAdmin);

                        userRef.setValue(updatedUser)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Profile.this, "Details Updated", Toast.LENGTH_SHORT).show();

                                        editUsername.setVisibility(View.GONE);
                                        editAge.setVisibility(View.GONE);
                                        editCountry.setVisibility(View.GONE);
                                        saveButton.setVisibility(View.GONE);
                                        editButton.setVisibility(View.VISIBLE);
                                        userEmailTextView.setVisibility(View.VISIBLE);
                                        finish(); // Finish the current activity
                                        startActivity(getIntent());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Profile.this, "Details Not Updated", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });




        if (currentUser == null) {
            Intent intent = new Intent(Profile.this, SignIn.class);
            startActivity(intent);
            finish();
        } else {
            String userId = currentUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("ProfileActivity", "Method Triggered");
                    Log.d("ProfileActivity", "DataSnapshot: " + dataSnapshot.toString());
                    if (dataSnapshot.exists()) {
                        Log.d("ProfileActivity", "DataSnapshot: " + dataSnapshot.toString());
                        // User data found, update the UI elements
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String username = dataSnapshot.child("username").getValue(String.class);
                        String age = dataSnapshot.child("age").getValue(String.class);
                        String country = dataSnapshot.child("country").getValue(String.class);


                        Log.d("ProfileActivity", "Email: " + email);
                        Log.d("ProfileActivity", "Username: " + username);
                        Log.d("ProfileActivity", "Age: " + age);
                        Log.d("ProfileActivity", "Country: " + country);

                        userEmailTextView.setText("Email: " + email);
                        usernameTextView.setText("Username: " + username);
                        ageTextView.setText("Age: " + age);
                        countryTextView.setText("Country: " + country);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
