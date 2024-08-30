package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Question;
import com.example.quizapp.QuizResults;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class QuizQuestions extends AppCompatActivity {

    private TextView questionTextView, questionNumberTextView;
    private RadioGroup radioGroup;
    private RadioButton radioChoice1, radioChoice2, radioChoice3, radioChoice4;
    private Button submitButton;
    private int currentQuestionIndex = 0;
    private HashMap<Integer, String> userSelectedChoices = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        questionTextView = findViewById(R.id.questionTextView);
        radioGroup = findViewById(R.id.radioGroup);
        radioChoice1 = findViewById(R.id.radioChoice1);
        radioChoice2 = findViewById(R.id.radioChoice2);
        radioChoice3 = findViewById(R.id.radioChoice3);
        radioChoice4 = findViewById(R.id.radioChoice4);
        submitButton = findViewById(R.id.submitButton);


        String quizId = getIntent().getStringExtra("QUIZ_ID");
        Log.d("QuizQuestions", "Quiz ID: " + quizId);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("quizzes").child(quizId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Question> questions = new ArrayList<>();
                for (DataSnapshot questionSnapshot : dataSnapshot.child("questions").getChildren()) {
                    String questionText = questionSnapshot.child("questionText").getValue(String.class);
                    String correctAnswer = questionSnapshot.child("correct_answer").getValue(String.class);

                    List<String> incorrectAnswers = new ArrayList<>();
                    for (DataSnapshot incorrectAnswerSnapshot : questionSnapshot.child("incorrect_answers").getChildren()) {
                        incorrectAnswers.add(incorrectAnswerSnapshot.getValue(String.class));
                    }

                    Log.d("QuizQuestions", "Choices before shuffle: " + incorrectAnswers.toString());

                    Question question = new Question(questionText, correctAnswer, incorrectAnswers);
                    questions.add(question);
                    initializeUI(questions);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("QuizQuestions", "DatabaseError: " + databaseError.getMessage());
            }
        });


    }

    private void initializeUI(List<Question> questions) {



        Button nextButton = findViewById(R.id.nextButton);
        Button previousButton = findViewById(R.id.previousButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(QuizQuestions.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }
                radioGroup.clearCheck();

                currentQuestionIndex++;

                if (currentQuestionIndex < questions.size()) {
                    displayQuestion(questions.get(currentQuestionIndex));

                    updateQuestionNumberText(currentQuestionIndex, questions.size());

                    previousButton.setVisibility(View.VISIBLE);
                }

                if (currentQuestionIndex == questions.size() - 1) {
                    nextButton.setVisibility(View.GONE);
                    submitButton.setVisibility(View.VISIBLE);
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex--;

                displayQuestion(questions.get(currentQuestionIndex));

                updateQuestionNumberText(currentQuestionIndex, questions.size());

                nextButton.setVisibility(View.VISIBLE);

                submitButton.setVisibility(View.GONE);
            }
        });


        radioChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelectedChoices.put(currentQuestionIndex, radioChoice1.getText().toString());
                logUserSelections();
            }
        });

        radioChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelectedChoices.put(currentQuestionIndex, radioChoice2.getText().toString());
                logUserSelections();
            }
        });

        radioChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelectedChoices.put(currentQuestionIndex, radioChoice3.getText().toString());
                logUserSelections();
            }
        });

        radioChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelectedChoices.put(currentQuestionIndex, radioChoice4.getText().toString());
                logUserSelections();
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSelectedChoices.size() < questions.size()) {
                    Toast.makeText(QuizQuestions.this, "Please answer all questions.", Toast.LENGTH_SHORT).show();
                } else {
                    userSelectedChoices.put(currentQuestionIndex, getSelectedChoice());

                    boolean allChoicesSerializable = true;
                    for (String choice : userSelectedChoices.values()) {
                        if (!(choice instanceof Serializable)) {
                            allChoicesSerializable = false;
                            break;
                        }
                    }

                    if (allChoicesSerializable) {
                        Intent intent = new Intent(QuizQuestions.this, QuizResults.class);
                        Log.d("QuizQuestions", "Selected Choice: " + getSelectedChoice());
                        Log.d("QuizQuestions", "userSelectedChoices before put: " + userSelectedChoices.toString());
                        userSelectedChoices.put(currentQuestionIndex, String.valueOf(getSelectedChoice()));
                        Log.d("QuizQuestions", "userSelectedChoices after put: " + userSelectedChoices.toString());
                        if (userSelectedChoices != null) {
                            intent.putExtra("USER_SELECTED_CHOICES", userSelectedChoices);
                            intent.putExtra("QUESTIONS", (Serializable) questions);
                            Bundle extras = intent.getExtras();
                            if (extras != null) {
                                for (String key : extras.keySet()) {
                                      Log.d("IntentPassed", "Key: " + key + ", Value: " + extras.get(key));
                                }
                            }
                            startActivity(intent);
                        } else {
                            Log.e("QuizQuestions", "userSelectedChoices is null.");
                        }
                    } else {
                        Toast.makeText(QuizQuestions.this, "Some choices are not serializable.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        displayQuestion(questions.get(currentQuestionIndex));
        updateQuestionNumberText(currentQuestionIndex, questions.size());
    }

    private String getSelectedChoice() {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        if (selectedRadioButton != null) {
            String choice = selectedRadioButton.getText().toString();
            Log.d("QuizQuestions", "Selected Choice: " + choice);
            return choice;
        }
        return "";
    }

    private void logUserSelections() {
        StringBuilder selections = new StringBuilder();
        for (Integer questionIndex : userSelectedChoices.keySet()) {
            String selectedChoice = userSelectedChoices.get(questionIndex);
            selections.append("Question ").append(questionIndex).append(": ").append(selectedChoice).append("\n");
        }
        Log.d("UserSelections", "User Selections:\n" + selections.toString());
    }

    private void displayQuestion(Question question) {
        questionTextView.setText(question.getQuestionText());

        List<String> choices = new ArrayList<>(question.getIncorrect_answers());
        choices.add(question.getCorrect_answer());

        radioChoice3.setVisibility(View.GONE);
        radioChoice4.setVisibility(View.GONE);

        radioChoice1.setText("True");
        radioChoice2.setText("False");

        if (choices.size() > 2) {
            radioChoice1.setText(choices.get(0));
            radioChoice2.setText(choices.get(1));
            radioChoice3.setText(choices.get(2));
            radioChoice3.setVisibility(View.VISIBLE);
            radioChoice4.setText(choices.get(3));
            radioChoice4.setVisibility(View.VISIBLE);
        }

        if (userSelectedChoices.containsKey(currentQuestionIndex)) {
            String selectedChoice = userSelectedChoices.get(currentQuestionIndex);

            if (selectedChoice.equals(radioChoice1.getText().toString())) {
                radioChoice1.setChecked(true);
            } else if (selectedChoice.equals(radioChoice2.getText().toString())) {
                radioChoice2.setChecked(true);
            } else if (selectedChoice.equals(radioChoice3.getText().toString())) {
                radioChoice3.setChecked(true);
            } else if (selectedChoice.equals(radioChoice4.getText().toString())) {
                radioChoice4.setChecked(true);
            }
        } else {
            radioGroup.clearCheck();
        }
    }

    private void updateQuestionNumberText(int currentQuestionIndex, int totalQuestions) {
        int questionNumber = currentQuestionIndex + 1;
        String questionNumberText = "Question " + questionNumber + " of " + totalQuestions;
        questionNumberTextView.setText(questionNumberText);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentQuestionIndex", currentQuestionIndex);
    }
}
