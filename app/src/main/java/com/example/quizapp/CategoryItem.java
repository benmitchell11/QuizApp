package com.example.quizapp;

public class CategoryItem {
    private String displayText;
    private int apiValue;

    public CategoryItem(String displayText, int apiValue) {
        this.displayText = displayText;
        this.apiValue = apiValue;
    }

    public String getDisplayText() {
        return displayText;
    }

    public int getApiValue() {
        return apiValue;
    }

    @Override
    public String toString() {
        return displayText;
    }
}

