package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizResults extends AppCompatActivity {

    private List<Question> questions;
    private HashMap<Integer, List<String>> userSelectedChoices;

    private TextView questionOneTextView, questionTwoTextView, questionThreeTextView,
            questionFourTextView, questionFiveTextView, questionSixTextView, questionSevenTextView,
            questionEightTextView, questionNineTextView, questionTenTextView, questionElevenTextView,
            questionTwelveTextView, questionThirteenTextView, questionFourteenTextView,
            questionFifteenTextView, questionSixteenTextView, questionSeventeenTextView,
            questionEighteenTextView, questionNineteenTextView, questionTwentyTextView;
    private TextView questionCorrectIncorrectOneTextView, questionCorrectIncorrectTwoTextView, questionCorrectIncorrectThreeTextView,
            questionCorrectIncorrectFourTextView, questionCorrectIncorrectFiveTextView, questionCorrectIncorrectSixTextView,
            questionCorrectIncorrectSevenTextView, questionCorrectIncorrectEightTextView, questionCorrectIncorrectNineTextView,
            questionCorrectIncorrectTenTextView, questionCorrectIncorrectElevenTextView, questionCorrectIncorrectTwelveTextView,
            questionCorrectIncorrectThirteenTextView, questionCorrectIncorrectFourteenTextView, questionCorrectIncorrectFifteenTextView,
            questionCorrectIncorrectSixteenTextView, questionCorrectIncorrectSeventeenTextView, questionCorrectIncorrectEighteenTextView,
            questionCorrectIncorrectNineteenTextView, questionCorrectIncorrectTwentyTextView;
    private TextView quizScore;
    private Question question;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizResults.this, BrowseQuizzes.class);
                startActivity(intent);
            }
        });

        questionOneTextView = findViewById(R.id.question1);
        questionTwoTextView = findViewById(R.id.question2);
        questionThreeTextView = findViewById(R.id.question3);
        questionFourTextView = findViewById(R.id.question4);
        questionFiveTextView = findViewById(R.id.question5);
        questionSixTextView = findViewById(R.id.question6);
        questionSevenTextView = findViewById(R.id.question7);
        questionEightTextView = findViewById(R.id.question8);
        questionNineTextView = findViewById(R.id.question9);
        questionTenTextView = findViewById(R.id.question10);
        questionElevenTextView = findViewById(R.id.question11);
        questionTwelveTextView = findViewById(R.id.question12);
        questionThirteenTextView = findViewById(R.id.question13);
        questionFourteenTextView = findViewById(R.id.question14);
        questionFifteenTextView = findViewById(R.id.question15);
        questionSixteenTextView = findViewById(R.id.question16);
        questionSeventeenTextView = findViewById(R.id.question17);
        questionEighteenTextView = findViewById(R.id.question18);
        questionNineteenTextView = findViewById(R.id.question19);
        questionTwentyTextView = findViewById(R.id.question20);
        questionCorrectIncorrectOneTextView = findViewById(R.id.questionCorrectIncorrect1);
        questionCorrectIncorrectTwoTextView = findViewById(R.id.questionCorrectIncorrect2);
        questionCorrectIncorrectThreeTextView = findViewById(R.id.questionCorrectIncorrect3);
        questionCorrectIncorrectFourTextView = findViewById(R.id.questionCorrectIncorrect4);
        questionCorrectIncorrectFiveTextView = findViewById(R.id.questionCorrectIncorrect5);
        questionCorrectIncorrectSixTextView = findViewById(R.id.questionCorrectIncorrect6);
        questionCorrectIncorrectSevenTextView = findViewById(R.id.questionCorrectIncorrect7);
        questionCorrectIncorrectEightTextView = findViewById(R.id.questionCorrectIncorrect8);
        questionCorrectIncorrectNineTextView = findViewById(R.id.questionCorrectIncorrect9);
        questionCorrectIncorrectTenTextView = findViewById(R.id.questionCorrectIncorrect10);
        questionCorrectIncorrectElevenTextView = findViewById(R.id.questionCorrectIncorrect11);
        questionCorrectIncorrectTwelveTextView = findViewById(R.id.questionCorrectIncorrect12);
        questionCorrectIncorrectThirteenTextView = findViewById(R.id.questionCorrectIncorrect13);
        questionCorrectIncorrectFourteenTextView = findViewById(R.id.questionCorrectIncorrect14);
        questionCorrectIncorrectFifteenTextView = findViewById(R.id.questionCorrectIncorrect15);
        questionCorrectIncorrectSixteenTextView = findViewById(R.id.questionCorrectIncorrect16);
        questionCorrectIncorrectSeventeenTextView = findViewById(R.id.questionCorrectIncorrect17);
        questionCorrectIncorrectEighteenTextView = findViewById(R.id.questionCorrectIncorrect18);
        questionCorrectIncorrectNineteenTextView = findViewById(R.id.questionCorrectIncorrect19);
        questionCorrectIncorrectTwentyTextView = findViewById(R.id.questionCorrectIncorrect20);
        quizScore = findViewById(R.id.quizScore);

        String quizId = getIntent().getStringExtra("QUIZ_ID");

      displayResults();

    }


    private void displayResults() {

        List<Question> questions = (List<Question>) getIntent().getSerializableExtra("QUESTIONS");
        userSelectedChoices = (HashMap<Integer, List<String>>) getIntent().getSerializableExtra("USER_SELECTED_CHOICES");

        Log.d("QuizResults", "UserSelectedChoicesObject" + userSelectedChoices);
        Log.d("QuizResults", "Questions" + questions.toString());
        if (questions != null && !questions.isEmpty()) {
            // Get the first question


            int correctAnswers = 0;
            Question firstQuestion = questions.get(0);
            Question secondQuestion = questions.get(1);
            Question thirdQuestion = questions.get(2);
            Question fourthQuestion = questions.get(3);
            Question fifthQuestion = questions.get(4);
            Question sixthQuestion = questions.get(5);
            Question seventhQuestion = questions.get(6);
            Question eighthQuestion = questions.get(7);
            Question ninthQuestion = questions.get(8);
            Question tenthQuestion = questions.get(9);
            Object choice1 = userSelectedChoices.get(0);
            Object choice2 = userSelectedChoices.get(1);
            Object choice3 = userSelectedChoices.get(2);
            Object choice4 = userSelectedChoices.get(3);
            Object choice5 = userSelectedChoices.get(4);
            Object choice6 = userSelectedChoices.get(5);
            Object choice7 = userSelectedChoices.get(6);
            Object choice8 = userSelectedChoices.get(7);
            Object choice9 = userSelectedChoices.get(8);
            Object choice10 = userSelectedChoices.get(9);

            boolean isCorrect = false;

            if (choice1.equals(firstQuestion.getCorrect_answer())) {
                isCorrect = true;
            }
            questionOneTextView.setText("Question 1: " + firstQuestion.getQuestionText());
            if (isCorrect) {
                questionCorrectIncorrectOneTextView.setText("Chosen Answer - " + choice1 + ": Correct");
                correctAnswers ++;
            } else {
                questionCorrectIncorrectOneTextView.setText("Chosen Answer - " + choice1 + ": Incorrect. Correct Answer: " + firstQuestion.getCorrect_answer());
            }

            isCorrect = false;

            if (choice2.equals(secondQuestion.getCorrect_answer())) {
                isCorrect = true;
            }
            questionTwoTextView.setText("Question 2: " + secondQuestion.getQuestionText());
            if (isCorrect) {
                questionCorrectIncorrectTwoTextView.setText("Chosen Answer - " + choice2 + ": Correct");
                correctAnswers ++;
            } else {
                questionCorrectIncorrectTwoTextView.setText("Chosen Answer - " + choice2 + ": Incorrect. Correct Answer: " + secondQuestion.getCorrect_answer());
            }

            isCorrect = false;

            if (choice3.equals(thirdQuestion.getCorrect_answer())) {
                isCorrect = true;
            }
            questionThreeTextView.setText("Question 3: " + thirdQuestion.getQuestionText());
            if (isCorrect) {
                questionCorrectIncorrectThreeTextView.setText("Chosen Answer - " + choice3 + ": Correct");
                correctAnswers++;
            } else {
                questionCorrectIncorrectThreeTextView.setText("Chosen Answer - " + choice3 + ": Incorrect. Correct Answer: " + thirdQuestion.getCorrect_answer());
            }

            isCorrect = false;

            if (choice4.equals(fourthQuestion.getCorrect_answer())) {
                isCorrect = true;
            }
            questionFourTextView.setText("Question 4: " + fourthQuestion.getQuestionText());
            if (isCorrect) {
                questionCorrectIncorrectFourTextView.setText("Chosen Answer - " + choice4 + ": Correct");
                correctAnswers++;
            } else {
                questionCorrectIncorrectFourTextView.setText("Chosen Answer - " + choice4 + ": Incorrect. Correct Answer: " + fourthQuestion.getCorrect_answer());
            }

            isCorrect = false;

            if (choice5.equals(fifthQuestion.getCorrect_answer())) {
                isCorrect = true;
            }
            questionFiveTextView.setText("Question 5: " + fifthQuestion.getQuestionText());
            if (isCorrect) {
                questionCorrectIncorrectFiveTextView.setText("Chosen Answer - " + choice5 + ": Correct");
                correctAnswers++;
            } else {
                questionCorrectIncorrectFiveTextView.setText("Chosen Answer - " + choice5 + ": Incorrect. Correct Answer: " + fifthQuestion.getCorrect_answer());
            }

            isCorrect = false;

            if (choice6.equals(sixthQuestion.getCorrect_answer())) {
                isCorrect = true;
            }
            questionSixTextView.setText("Question 6: " + sixthQuestion.getQuestionText());
            if (isCorrect) {
                questionCorrectIncorrectSixTextView.setText("Chosen Answer - " + choice6 + ": Correct");
                correctAnswers++;
            } else {
                questionCorrectIncorrectSixTextView.setText("Chosen Answer - " + choice6 + ": Incorrect. Correct Answer: " + sixthQuestion.getCorrect_answer());
            }

            isCorrect = false;

            if (choice7.equals(seventhQuestion.getCorrect_answer())) {
                isCorrect = true;
            }
            questionSevenTextView.setText("Question 7: " + seventhQuestion.getQuestionText());
            if (isCorrect) {
                questionCorrectIncorrectSevenTextView.setText("Chosen Answer - " + choice7 + ": Correct");
                correctAnswers++;
            } else {
                questionCorrectIncorrectSevenTextView.setText("Chosen Answer - " + choice7 + ": Incorrect. Correct Answer: " + seventhQuestion.getCorrect_answer());
            }

            isCorrect = false;

            if (choice8.equals(eighthQuestion.getCorrect_answer())) {
                isCorrect = true;
            }
            questionEightTextView.setText("Question 8: " + eighthQuestion.getQuestionText());
            if (isCorrect) {
                questionCorrectIncorrectEightTextView.setText("Chosen Answer - " + choice8 + ": Correct");
                correctAnswers++;
            } else {
                questionCorrectIncorrectEightTextView.setText("Chosen Answer - " + choice8 + ": Incorrect. Correct Answer: " + eighthQuestion.getCorrect_answer());
            }

            isCorrect = false;

            if (choice9.equals(ninthQuestion.getCorrect_answer())) {
                isCorrect = true;
            }
            questionNineTextView.setText("Question 9: " + ninthQuestion.getQuestionText());
            if (isCorrect) {
                questionCorrectIncorrectNineTextView.setText("Chosen Answer - " + choice9 + ": Correct");
                correctAnswers++;
            } else {
                questionCorrectIncorrectNineTextView.setText("Chosen Answer - " + choice9 + ": Incorrect. Correct Answer: " + ninthQuestion.getCorrect_answer());
            }

            isCorrect = false;

            if (choice10.equals(tenthQuestion.getCorrect_answer())) {
                isCorrect = true;
            }
            questionTenTextView.setText("Question 10: " + tenthQuestion.getQuestionText());
            if (isCorrect) {
                questionCorrectIncorrectTenTextView.setText("Chosen Answer - " + choice10 + ": Correct");
                correctAnswers++;
            } else {
                questionCorrectIncorrectTenTextView.setText("Chosen Answer - " + choice10 + ": Incorrect. Correct Answer: " + tenthQuestion.getCorrect_answer());
            }

            isCorrect = false;

            if (questions.size() == 11) {
                Question eleventhQuestion = questions.get(10);
                Object choice11 = userSelectedChoices.get(10);
                questionElevenTextView.setVisibility(View.VISIBLE);
                questionCorrectIncorrectElevenTextView.setVisibility(View.VISIBLE);

                if (choice11.equals(eleventhQuestion.getCorrect_answer())) {
                    isCorrect = true;
                }
                questionElevenTextView.setText("Question 11: " + eleventhQuestion.getQuestionText());
                if (isCorrect) {
                    questionCorrectIncorrectElevenTextView.setText("Chosen Answer - " + choice11 + ": Correct");
                    correctAnswers++;
                } else {
                    questionCorrectIncorrectElevenTextView.setText("Chosen Answer - " + choice11 + ": Incorrect. Correct Answer: " + eleventhQuestion.getCorrect_answer());
                }

                isCorrect = false;
            } else if (questions.size() == 12) {
                Question twelfthQuestion = questions.get(11);
                Object choice12 = userSelectedChoices.get(11);
                questionTwelveTextView.setVisibility(View.VISIBLE);
                questionCorrectIncorrectTwelveTextView.setVisibility(View.VISIBLE);

                if (choice12.equals(twelfthQuestion.getCorrect_answer())) {
                    isCorrect = true;
                }
                questionTwelveTextView.setText("Question 12: " + twelfthQuestion.getQuestionText());
                if (isCorrect) {
                    questionCorrectIncorrectTwelveTextView.setText("Chosen Answer - " + choice12 + ": Correct");
                    correctAnswers++;
                } else {
                    questionCorrectIncorrectTwelveTextView.setText("Chosen Answer - " + choice12 + ": Incorrect. Correct Answer: " + twelfthQuestion.getCorrect_answer());
                }

                isCorrect = false;
            } else if (questions.size() == 13) {
                Question thirteenthQuestion = questions.get(12);
                Object choice13 = userSelectedChoices.get(12);
                questionThirteenTextView.setVisibility(View.GONE);

                if (choice13.equals(thirteenthQuestion.getCorrect_answer())) {
                    isCorrect = true;
                }
                questionThirteenTextView.setText("Question 13: " + thirteenthQuestion.getQuestionText());
                if (isCorrect) {
                    questionCorrectIncorrectThirteenTextView.setText("Chosen Answer - " + choice13 + ": Correct");
                    correctAnswers++;
                } else {
                    questionCorrectIncorrectThirteenTextView.setText("Chosen Answer - " + choice13 + ": Incorrect. Correct Answer: " + thirteenthQuestion.getCorrect_answer());
                }

                isCorrect = false;

            } else if (questions.size() == 14) {
                Question fourteenthQuestion = questions.get(13);
                Object choice14 = userSelectedChoices.get(13);
                questionFourteenTextView.setVisibility(View.VISIBLE);
                questionCorrectIncorrectFourteenTextView.setVisibility(View.VISIBLE);

                if (choice14.equals(fourteenthQuestion.getCorrect_answer())) {
                    isCorrect = true;
                }
                questionFourteenTextView.setText("Question 14: " + fourteenthQuestion.getQuestionText());
                if (isCorrect) {
                    questionCorrectIncorrectFourteenTextView.setText("Chosen Answer - " + choice14 + ": Correct");
                    correctAnswers++;
                } else {
                    questionCorrectIncorrectFourteenTextView.setText("Chosen Answer - " + choice14 + ": Incorrect. Correct Answer: " + fourteenthQuestion.getCorrect_answer());
                }

                isCorrect = false;
            } else if (questions.size() == 15) {
                Question fifteenthQuestion = questions.get(14);
                Object choice15 = userSelectedChoices.get(14);
                questionFifteenTextView.setVisibility(View.VISIBLE);
                questionCorrectIncorrectFifteenTextView.setVisibility(View.VISIBLE);

                if (choice15.equals(fifteenthQuestion.getCorrect_answer())) {
                    isCorrect = true;
                }
                questionFifteenTextView.setText("Question 15: " + fifteenthQuestion.getQuestionText());
                if (isCorrect) {
                    questionCorrectIncorrectFifteenTextView.setText("Chosen Answer - " + choice15 + ": Correct");
                    correctAnswers++;
                } else {
                    questionCorrectIncorrectFifteenTextView.setText("Chosen Answer - " + choice15 + ": Incorrect. Correct Answer: " + fifteenthQuestion.getCorrect_answer());
                }

                isCorrect = false;

            } else if (questions.size() == 16) {
                Question sixteenthQuestion = questions.get(15);
                Object choice16 = userSelectedChoices.get(15);
                questionSixteenTextView.setVisibility(View.VISIBLE);
                questionCorrectIncorrectSixteenTextView.setVisibility(View.VISIBLE);

                if (choice16.equals(sixteenthQuestion.getCorrect_answer())) {
                    isCorrect = true;
                }
                questionSixteenTextView.setText("Question 16: " + sixteenthQuestion.getQuestionText());
                if (isCorrect) {
                    questionCorrectIncorrectSixteenTextView.setText("Chosen Answer - " + choice16 + ": Correct");
                    correctAnswers++;
                } else {
                    questionCorrectIncorrectSixteenTextView.setText("Chosen Answer - " + choice16 + ": Incorrect. Correct Answer: " + sixteenthQuestion.getCorrect_answer());
                }

                isCorrect = false;

            } else if (questions.size() == 17) {
                Question seventeenthQuestion = questions.get(16);
                Object choice17 = userSelectedChoices.get(16);
                questionSeventeenTextView.setVisibility(View.VISIBLE);
                questionCorrectIncorrectSeventeenTextView.setVisibility(View.VISIBLE);

                if (choice17.equals(seventeenthQuestion.getCorrect_answer())) {
                    isCorrect = true;
                }
                questionSeventeenTextView.setText("Question 17: " + seventeenthQuestion.getQuestionText());
                if (isCorrect) {
                    questionCorrectIncorrectSeventeenTextView.setText("Chosen Answer - " + choice17 + ": Correct");
                    correctAnswers++;
                } else {
                    questionCorrectIncorrectSeventeenTextView.setText("Chosen Answer - " + choice17 + ": Incorrect. Correct Answer: " + seventeenthQuestion.getCorrect_answer());
                }

                isCorrect = false;

            } else if (questions.size() == 18) {
                Question eighteenthQuestion = questions.get(17);
                Object choice18 = userSelectedChoices.get(17);
                questionEighteenTextView.setVisibility(View.VISIBLE);
                questionCorrectIncorrectEighteenTextView.setVisibility(View.VISIBLE);

                if (choice18.equals(eighteenthQuestion.getCorrect_answer())) {
                    isCorrect = true;
                }
                questionEighteenTextView.setText("Question 18: " + eighteenthQuestion.getQuestionText());
                if (isCorrect) {
                    questionCorrectIncorrectEighteenTextView.setText("Chosen Answer - " + choice18 + ": Correct");
                    correctAnswers++;
                } else {
                    questionCorrectIncorrectEighteenTextView.setText("Chosen Answer - " + choice18 + ": Incorrect. Correct Answer: " + eighteenthQuestion.getCorrect_answer());
                }

                isCorrect = false;

            } else if (questions.size() == 19) {
                Question nineteenthQuestion = questions.get(18);
                Object choice19 = userSelectedChoices.get(18);
                questionNineteenTextView.setVisibility(View.VISIBLE);
                questionCorrectIncorrectNineteenTextView.setVisibility(View.VISIBLE);

                if (choice19.equals(nineteenthQuestion.getCorrect_answer())) {
                    isCorrect = true;
                }
                questionNineteenTextView.setText("Question 19: " + nineteenthQuestion.getQuestionText());
                if (isCorrect) {
                    questionCorrectIncorrectNineteenTextView.setText("Chosen Answer - " + choice19 + ": Correct");
                    correctAnswers++;
                } else {
                    questionCorrectIncorrectNineteenTextView.setText("Chosen Answer - " + choice19 + ": Incorrect. Correct Answer: " + nineteenthQuestion.getCorrect_answer());
                }

                isCorrect = false;

            } else if (questions.size() == 20) {
                Question twentiethQuestion = questions.get(19);
                Object choice20 = userSelectedChoices.get(19);
                questionTwentyTextView.setVisibility(View.VISIBLE);
                questionCorrectIncorrectTwentyTextView.setVisibility(View.VISIBLE);

                if (choice20.equals(twentiethQuestion.getCorrect_answer())) {
                    isCorrect = true;
                }
                questionTwentyTextView.setText("Question 20: " + twentiethQuestion.getQuestionText());
                if (isCorrect) {
                    questionCorrectIncorrectTwentyTextView.setText("Chosen Answer - " + choice20 + ": Correct");
                    correctAnswers++;
                } else {
                    questionCorrectIncorrectTwentyTextView.setText("Chosen Answer - " + choice20 + ": Incorrect. Correct Answer: " + twentiethQuestion.getCorrect_answer());
                }

                isCorrect = false;

            } else {
                questionElevenTextView.setVisibility(View.GONE);
                questionTwelveTextView.setVisibility(View.GONE);
                questionThirteenTextView.setVisibility(View.GONE);
                questionFourteenTextView.setVisibility(View.GONE);
                questionFifteenTextView.setVisibility(View.GONE);
                questionSixteenTextView.setVisibility(View.GONE);
                questionSeventeenTextView.setVisibility(View.GONE);
                questionEighteenTextView.setVisibility(View.GONE);
                questionNineteenTextView.setVisibility(View.GONE);
                questionTwentyTextView.setVisibility(View.GONE);
                questionCorrectIncorrectElevenTextView.setVisibility(View.GONE);
                questionCorrectIncorrectTwelveTextView.setVisibility(View.GONE);
                questionCorrectIncorrectThirteenTextView.setVisibility(View.GONE);
                questionCorrectIncorrectFourteenTextView.setVisibility(View.GONE);
                questionCorrectIncorrectFifteenTextView.setVisibility(View.GONE);
                questionCorrectIncorrectSixteenTextView.setVisibility(View.GONE);
                questionCorrectIncorrectSeventeenTextView.setVisibility(View.GONE);
                questionCorrectIncorrectEighteenTextView.setVisibility(View.GONE);
                questionCorrectIncorrectNineteenTextView.setVisibility(View.GONE);
                questionCorrectIncorrectTwentyTextView.setVisibility(View.GONE);
            }

            quizScore.setText("Score: " + correctAnswers + " / " + questions.size());


        }
    }
}
