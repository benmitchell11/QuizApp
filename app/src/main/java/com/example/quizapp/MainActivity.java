package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button createButton;
    private User user;

    private static final String BASE_URL = "https://opentdb.com/";
    private QuizApiService quizApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnBrowse = findViewById(R.id.btn_browse);
        Button createButton = findViewById(R.id.btn_create);
        Button profileButton = findViewById(R.id.btn_profile);
        Button signOutButton = findViewById(R.id.btn_logout);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                    Button createButton = findViewById(R.id.btn_create);
                    if (user != null && user.isAdmin()) {
                        createButton.setVisibility(View.VISIBLE);
                    } else {
                        createButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out the user
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });



        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateQuiz.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to CreateQuiz activity
                Intent intent = new Intent(MainActivity.this, BrowseQuizzes.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        quizApiService = retrofit.create(QuizApiService.class);

        Call<QuizApiResponse> call = quizApiService.getQuizzes(10, 20, "easy", "multiple");
        call.enqueue(new Callback<QuizApiResponse>() {
            @Override
            public void onResponse(Call<QuizApiResponse> call, Response<QuizApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    QuizApiResponse quizApiResponse = response.body();
                    Log.d("API Response", quizApiResponse.toString());
                } else {
                    Log.e("API Error", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<QuizApiResponse> call, Throwable t) {
                Log.e("API Failure", t.getMessage());
            }
        });
    }
}
