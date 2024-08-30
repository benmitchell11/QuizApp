package com.example.quizapp;

import java.util.List;

public class QuizApiResponse {
    private int response_code;
    private List<Question> results;

    public int getResponseCode() {
        return response_code;
    }

    public List<Question> getResults() {

        return results;
    }
}