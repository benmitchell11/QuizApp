package com.example.quizapp;
import com.example.quizapp.Quiz;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StartQuiz extends AppCompatActivity {
    private Quiz quiz;
    private User user;
    private Button deleteButton, editButton, saveButton, startQuizButton, startDatePickerButton, endDatePickerButton, likeButton, dislikeButton;
    private TextInputEditText editName, editStartDate, editEndDate;
    private LinearLayout editStartDateLayout, editEndDateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        deleteButton = findViewById(R.id.btn_delete);
        editButton = findViewById(R.id.btn_update);
        saveButton = findViewById(R.id.btn_save);
        startQuizButton = findViewById(R.id.btn_start);
        editName = findViewById(R.id.editName);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        editStartDateLayout = findViewById(R.id.editStartDateLayout);
        editEndDateLayout = findViewById(R.id.editEndDateLayout);
        TextView titleTextView = findViewById(R.id.quizName);
        TextView categoryTextView = findViewById(R.id.quizCategory);
        TextView difficultyTextView = findViewById(R.id.quizDifficulty);
        TextView typeTextView = findViewById(R.id.quizType);
        TextView startDateTextView = findViewById(R.id.quizStartDate);
        TextView endDateTextView = findViewById(R.id.quizEndDate);
        TextView questionsTextView = findViewById(R.id.quizQuestions);
        TextView startQuizText = findViewById(R.id.textStartQuiz);
        likeButton = findViewById(R.id.likeButton);
        dislikeButton = findViewById(R.id.dislikeButton);
        TextView txtLikes = findViewById(R.id.txtLikes);



        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("UserIdCheck", "UserID = " + currentUserId);










        startDatePickerButton = findViewById(R.id.startDatePickerButton);
        startDatePickerButton.setOnClickListener(view -> showStartDatePickerDialog());

        endDatePickerButton = findViewById(R.id.endDatePickerButton);
        endDatePickerButton.setOnClickListener(view -> showEndDatePickerDialog());

        String quizId = getIntent().getStringExtra("QUIZ_ID");
        Log.d("QuizID", "QuizID = " + quizId);
        String quizTitle = getIntent().getStringExtra("QUIZ_TITLE");
        String quizCategory = getIntent().getStringExtra("QUIZ_CATEGORY");
        String quizDifficulty = getIntent().getStringExtra("QUIZ_DIFFICULTY");
        String quizType = getIntent().getStringExtra("QUIZ_TYPE");
        String quizStartDate = getIntent().getStringExtra("QUIZ_START_DATE");
        String quizEndDate = getIntent().getStringExtra("QUIZ_END_DATE");
        int quizQuestions = getIntent().getIntExtra("QUIZ_AMOUNT", 0);
        int quizLikes = getIntent().getIntExtra("QUIZ_LIKES", 0);
        ArrayList<String> likedByUserIds = getIntent().getStringArrayListExtra("QUIZ_LIKED_BY");
        List<String> likedByList = new ArrayList<>(likedByUserIds);

        DatabaseReference quizRef = FirebaseDatabase.getInstance().getReference("quizzes").child(quizId);
        quizRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String quizTitle = dataSnapshot.child("title").getValue(String.class);
                String quizCategory = dataSnapshot.child("category").getValue(String.class);
                String quizDifficulty = dataSnapshot.child("difficulty").getValue(String.class);
                String quizType = dataSnapshot.child("type").getValue(String.class);
                String quizStartDate = dataSnapshot.child("startDate").getValue(String.class);
                String quizEndDate = dataSnapshot.child("endDate").getValue(String.class);
                String quizId = dataSnapshot.child("quizId").getValue(String.class);



                titleTextView.setText(quizTitle);
                startDateTextView.setText(quizStartDate);
                endDateTextView.setText(quizEndDate);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Boolean isLikedByUser = false;
        if (likedByList.contains(currentUserId)) {
            isLikedByUser = true;
        } else {
            isLikedByUser = false;
        }

        if(isLikedByUser == true) {
            dislikeButton.setVisibility(View.VISIBLE);
            likeButton.setVisibility(View.GONE);
        } else {
            dislikeButton.setVisibility(View.GONE);
            likeButton.setVisibility(View.VISIBLE);
        }




        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButton.setVisibility(View.GONE);
                startQuizButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.VISIBLE);
                editName.setVisibility(View.VISIBLE);
                editStartDateLayout.setVisibility(View.VISIBLE);
                editEndDateLayout.setVisibility(View.VISIBLE);
                categoryTextView.setVisibility(View.GONE);
                difficultyTextView.setVisibility(View.GONE);
                typeTextView.setVisibility(View.GONE);
                questionsTextView.setVisibility(View.GONE);
                editButton.setVisibility(View.GONE);
                startQuizText.setText("Edit Quiz");

            }
        });

        Button deleteButton = findViewById(R.id.btn_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteQuizClicked(quizId);
            }
        });


        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the quiz using the quizId
                Log.d("StartQuiz", "Start quiz button clicked for quizId: " + quizId);
                Intent intent = new Intent(StartQuiz.this, QuizQuestions.class);
                intent.putExtra("QUIZ_ID", quizId);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQuizName = editName.getText().toString().trim();
                String newStartDate = editStartDate.getText().toString().trim();
                String newEndDate = editEndDate.getText().toString().trim();

                if (newQuizName.isEmpty()) {
                    newQuizName = quizTitle;
                }
                if (newStartDate.isEmpty()) {
                    newStartDate = quizStartDate;
                }
                if (newEndDate.isEmpty()) {
                    newEndDate = quizEndDate;
                }

                updateQuizDetails(quizId, newQuizName, newStartDate, newEndDate);

            }
        });



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("quizzes");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot quizSnapshot : dataSnapshot.getChildren()) {
                    Quiz quiz = quizSnapshot.getValue(Quiz.class);
                    String quizId = quizSnapshot.getKey(); // Get the quiz ID

                    Log.d("StartQuiz", "Quiz Snapshot: " + quizSnapshot.toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();



        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                    Button deleteButton = findViewById(R.id.btn_delete);
                    if (user != null && user.isAdmin()) {
                        deleteButton.setVisibility(View.VISIBLE);
                        editButton.setVisibility(View.VISIBLE);
                    } else {
                        deleteButton.setVisibility(View.GONE);
                        editButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference quizRef = FirebaseDatabase.getInstance().getReference("quizzes").child(quizId);
                quizRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        Quiz quiz = mutableData.getValue(Quiz.class);

                        if (quiz == null) {
                            return Transaction.success(mutableData);
                        }

                        // Check if likedByUserIds list is null or empty
                        if (quiz.getLikedByUserIds() != null && !quiz.getLikedByUserIds().isEmpty()) {
                            // Remove the userId from the likedByUserIds list
                            quiz.getLikedByUserIds().remove(currentUserId);

                            // If there are still items in the list, update the data
                            if (!quiz.getLikedByUserIds().isEmpty()) {
                                mutableData.setValue(quiz);
                            } else {
                                // If the list is empty, set likedByUserIds to null or an empty list
                                quiz.setLikedByUserIds(null); // or quiz.setLikedByUserIds(new ArrayList<>());
                                mutableData.setValue(quiz);
                            }
                        }

                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot currentData) {
                        if (committed) {
                            // Transaction successful
                            // Update UI or handle the completion as needed
                        } else {
                            // Transaction failed
                            if (databaseError != null) {
                                Log.d("StartQuiz", "Transaction failed: " + databaseError.getMessage());
                            }
                        }
                    }
                });
            }
        });


        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference quizRef = FirebaseDatabase.getInstance().getReference("quizzes").child(quizId);

                quizRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        Quiz quiz = mutableData.getValue(Quiz.class);

                        if (quiz == null) {
                            // Quiz does not exist
                            return Transaction.success(mutableData);
                        }

                        if (quiz.getLikedByUserIds() == null) {
                            // Initialize the likedByUserIds list if it's null
                            quiz.setLikedByUserIds(new ArrayList<>());
                        }

                        if (!quiz.getLikedByUserIds().contains(currentUserId)) {
                            // User has not liked the quiz, add the like
                            quiz.getLikedByUserIds().add(currentUserId);
                            quiz.setLikes(quiz.getLikes() + 1); // Increment like count
                        }

                        mutableData.setValue(quiz);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot currentData) {
                        if (committed) {
                            // Transaction successful
                            // Update UI based on the new likedByUserIds list and like count
                        } else {
                            // Transaction failed
                            if (databaseError != null) {
                                Log.d("StartQuiz", "Transaction failed: " + databaseError.getMessage());
                            }
                        }
                    }
                });
            }
        });







        titleTextView.setText(quizTitle);
        if(quizCategory.equals("Any Category")){
            categoryTextView.setText("Category: Multiple Categories");
        } else {
            categoryTextView.setText("Category: " + quizCategory);
        }
        if(quizDifficulty.equals("Any Difficulty")) {
            difficultyTextView.setText("Difficulty: Multiple Difficulties");
        } else {
            difficultyTextView.setText("Difficulty: " + quizDifficulty);
        }
        if(quizType.equals("Any Type")) {
            typeTextView.setText("Type: Multiple Types");
        } else {
            typeTextView.setText("Type: " + quizType);
        }
        startDateTextView.setText("Start Date: " + quizStartDate);
        endDateTextView.setText("End Date: " + quizEndDate);
        questionsTextView.setText("Number of Questions: " + quizQuestions);
        txtLikes.setText("Likes: " + quizLikes);




    }



    private void updateQuizDetails(String quizId, String newQuizName, String newStartDate, String newEndDate) {
        Log.d("StartQuiz", "updateQuizDetails: Updating quiz details");
        DatabaseReference quizRef = FirebaseDatabase.getInstance().getReference("quizzes").child(quizId);
        quizRef.child("title").setValue(newQuizName);
        quizRef.child("startDate").setValue(newStartDate);
        quizRef.child("endDate").setValue(newEndDate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(StartQuiz.this, "Quiz details updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StartQuiz.this, "Failed to update quiz details", Toast.LENGTH_SHORT).show();
                        Log.e("StartQuiz", "updateQuizDetails: Failed to update quiz details: " + e.getMessage());
                    }
                });
        finish();
        startActivity(getIntent());
    }
    private void showStartDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Month is zero-based, so add 1 to match the actual month
                        month = month + 1;
                        String formattedDate = year + "-" + (month < 10 ? "0" + month : month) + "-" +
                                (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        editStartDate.setText(formattedDate);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showEndDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Month is zero-based, so add 1 to match the actual month
                        month = month + 1;
                        String formattedDate = year + "-" + (month < 10 ? "0" + month : month) + "-" +
                                (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        editEndDate.setText(formattedDate);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }



    private void onDeleteQuizClicked(String quizId) {
        // Handle delete button click with the correct quizId
        DatabaseReference quizRef = FirebaseDatabase.getInstance().getReference("quizzes").child(quizId);
        quizRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Quiz deleted successfully
                        Log.d("DeleteQuiz", "Quiz deleted successfully");
                        finish(); // Finish the activity after deleting the quiz
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to delete the quiz
                        // Handle the error, e.g., show an error message
                        Log.e("DeleteQuiz", "Error deleting quiz: " + e.getMessage());
                    }
                });
    }


    private void updateLikeButtonState(String quizId) {
        DatabaseReference quizRef = FirebaseDatabase.getInstance().getReference("quizzes").child(quizId);

        quizRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Quiz quiz = dataSnapshot.getValue(Quiz.class);

                if (quiz != null) {
                    List<String> likedByUserIds = quiz.getLikedByUserIds();
                    String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    if (likedByUserIds != null && likedByUserIds.contains(currentUserId)) {
                        // User has liked the quiz, show the "Dislike" button
                        likeButton.setText("Dislike");
                    } else {
                        // User has not liked the quiz, show the "Like" button
                        likeButton.setText("Like");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors...
            }
        });
    }






}

