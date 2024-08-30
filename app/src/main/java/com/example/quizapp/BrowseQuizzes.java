package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseQuizzes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_quizzes);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("quizzes");
        List<Quiz> quizzes = new ArrayList<>();

        QuizAdapter.OnItemClickListener onItemClickListener = new QuizAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Quiz quiz) {
                int questionsSize = quiz.getQuestions().size();
                Intent intent = new Intent(BrowseQuizzes.this, StartQuiz.class);
                intent.putExtra("QUIZ_ID", quiz.getQuizId());
                Log.d("PushedId", "PushedID: " + quiz.getQuizId()); // Use the unique ID obtained from the Quiz object
                intent.putExtra("QUIZ_TITLE", quiz.getTitle());
                intent.putExtra("QUIZ_CATEGORY", quiz.getCategory());
                intent.putExtra("QUIZ_DIFFICULTY", quiz.getDifficulty());
                intent.putExtra("QUIZ_TYPE", quiz.getType());
                intent.putExtra("QUIZ_START_DATE", quiz.getStartDate());
                intent.putExtra("QUIZ_END_DATE", quiz.getEndDate());
                intent.putExtra("QUIZ_AMOUNT", questionsSize);
                intent.putExtra("QUIZ_LIKES", quiz.getLikes());
                if (quiz.getLikedByUserIds() == null) {
                    quiz.setLikedByUserIds(new ArrayList<>());
                }
                intent.putStringArrayListExtra("QUIZ_LIKED_BY", new ArrayList<>(quiz.getLikedByUserIds()));
                startActivity(intent);
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                quizzes.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Quiz quiz = snapshot.getValue(Quiz.class);
                    quiz.setQuizId(snapshot.getKey());
                    quizzes.add(quiz);
                }

                quizAdapter = new QuizAdapter(quizzes, onItemClickListener);
                recyclerView.setAdapter(quizAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

