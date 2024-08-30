package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateQuiz extends AppCompatActivity {



    private int amount = 10;
    private Button startDatePickerButton, endDatePickerButton;

    private TextInputEditText startDateEditText, endDateEditText, titleEditText, numberInput;



    CategoryItem[] categories = {
            new CategoryItem("Any Category", 0),
            new CategoryItem("General Knowledge", 9),
            new CategoryItem("Entertainment: Books", 10),
            new CategoryItem("Entertainment: Film", 11),
            new CategoryItem("Entertainment: Music", 12),
            new CategoryItem("Entertainment: Musicals & Theatres", 13),
            new CategoryItem("Entertainment: Television", 14),
            new CategoryItem("Entertainment: Video Games", 15),
            new CategoryItem("Entertainment: Board Games", 16),
            new CategoryItem("Science & Nature", 17),
            new CategoryItem("Science: Computers", 18),
            new CategoryItem("Science: Mathematics", 19),
            new CategoryItem("Mythology", 20),
            new CategoryItem("Sports", 21),
            new CategoryItem("Geography", 22),
            new CategoryItem("History", 23),
            new CategoryItem("Politics", 24),
            new CategoryItem("Art", 25),
            new CategoryItem("Celebrities", 26),
            new CategoryItem("Animals", 27),
            new CategoryItem("Vehicles", 28),
            new CategoryItem("Entertainment: Comics", 29),
            new CategoryItem("Science: Gadgets", 30),
            new CategoryItem("Entertainment: Japanese Anime & Manga", 31),
            new CategoryItem("Entertainment: Cartoon & Animations", 32)
    };

    DifficultyItem [] difficulties = {
            new DifficultyItem("Any Difficulty", "0"),
            new DifficultyItem("Easy", "easy"),
            new DifficultyItem("Medium", "medium"),
            new DifficultyItem("Hard", "hard")
    };

    TypeItem [] types = {
            new TypeItem("Any Type", "0"),
            new TypeItem("Multiple Choice", "multiple"),
            new TypeItem("True / False", "boolean")
    };

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        startDateEditText = findViewById(R.id.startDate);
        endDateEditText = findViewById(R.id.endDate);
        titleEditText = findViewById(R.id.title);
        numberInput = findViewById(R.id.numberInput);

        startDatePickerButton = findViewById(R.id.startDatePickerButton);
        startDatePickerButton.setOnClickListener(view -> showStartDatePickerDialog());

        endDatePickerButton = findViewById(R.id.endDatePickerButton);
        endDatePickerButton.setOnClickListener(view -> showEndDatePickerDialog());



        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        Spinner difficultySpinner = findViewById(R.id.difficultySpinner);
        Spinner typeSpinner = findViewById(R.id.typeSpinner);

        ArrayAdapter<CategoryItem> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<DifficultyItem> difficultyAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                difficulties
        );
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);

        ArrayAdapter<TypeItem> typeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                types
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);



        Button createQuizButton = findViewById(R.id.btn_create);
        createQuizButton.setOnClickListener(view -> {
            String title = titleEditText.getText().toString();
            String startDate = startDateEditText.getText().toString();
            String endDate = endDateEditText.getText().toString();
            CategoryItem selectedCategory = (CategoryItem) categorySpinner.getSelectedItem();
            DifficultyItem selectedDifficulty = (DifficultyItem) difficultySpinner.getSelectedItem();
            TypeItem selectedType = (TypeItem) typeSpinner.getSelectedItem();
            String amountString = numberInput.getText().toString();
            int amount = Integer.parseInt(amountString);


            if (selectedCategory.getApiValue() == 0 && selectedDifficulty.getApiValue() == "any" && selectedType.getApiValue() == "any") {
                showToast("Please select at least a category, a type, or a difficulty.");
                return;
            }

            String apiUrl = "https://opentdb.com/api.php?" +
                    "amount=" + amount;

            int selectedCategoryValue = selectedCategory.getApiValue();
            if (selectedCategoryValue != 0) {
                apiUrl += "&category=" + selectedCategoryValue;
            }

            String selectedDifficultyValue = selectedDifficulty.getApiValue();
            if (selectedDifficultyValue != "any") {
                apiUrl += "&difficulty=" + selectedDifficultyValue;
            }

            String selectedTypeValue = selectedType.getApiValue();
            Log.d("selectedType", "selectedType: " + selectedTypeValue);
            if (selectedTypeValue != "any") {
                apiUrl += "&type=" + selectedTypeValue;
            }

            Log.d("ApiUrl", "ApiUrl: " + apiUrl);


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://opentdb.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            QuizApiService apiService = retrofit.create(QuizApiService.class);
            Call<QuizApiResponse> call = apiService.getQuizzes(amount, selectedCategory.getApiValue(), selectedDifficulty.getApiValue(), selectedType.getApiValue());
            call.enqueue(new Callback<QuizApiResponse>() {
                @Override
                public void onResponse(Call<QuizApiResponse> call, Response<QuizApiResponse> response) {
                    Log.d("APIResponse", "Response: " + response.toString());

                    if (response.isSuccessful() && response.body() != null) {
                        QuizApiResponse quizApiResponse = response.body();
                        List<Question> apiQuestions = quizApiResponse.getResults();
                        List<Question> questions = new ArrayList<>();

                        for (Question apiQuestion : apiQuestions) {
                            String questionText = apiQuestion.getQuestionText();
                            String correct_answer = apiQuestion.getCorrect_answer();
                            List<String> incorrect_answers = apiQuestion.getIncorrect_answers();

                            Log.d("QuestionDetails", "Question: " + questionText);
                            Log.d("QuestionDetails", "Correct Answer: " + correct_answer);
                            Log.d("QuestionDetails", "Incorrect Answers: " + incorrect_answers);
                            String decodedText = Html.fromHtml(questionText, Html.FROM_HTML_MODE_LEGACY).toString();
                            Question question = new Question(decodedText, correct_answer, incorrect_answers);
                            questions.add(question);
                        }
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference quizzesRef = database.getReference("quizzes");

                        String quizId = quizzesRef.push().getKey();
                        List<String> likedByUserIdsList = new ArrayList<>();

                        Quiz quiz = new Quiz(title, startDate, endDate, selectedCategory.getDisplayText(), selectedType.getDisplayText(), selectedDifficulty.getDisplayText(), questions, quizId, 0, likedByUserIdsList);

                        quizzesRef.child(quizId).setValue(quiz);

                        showToast("Quiz created successfully!");
                    } else {
                        showToast("Failed to create quiz. Please try again.");
                    }
                }

                @Override
                public void onFailure(Call<QuizApiResponse> call, Throwable t) {
                    showToast("Network error. Please check your internet connection and try again.");
                }
            });
        });
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
                        startDateEditText.setText(formattedDate);
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
                        endDateEditText.setText(formattedDate);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


}
