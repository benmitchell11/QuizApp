package com.example.quizapp;

public class DifficultyItem {

    private String displayText;
    private String apiValue;

    public DifficultyItem(String displayText, String apiValue) {
        this.displayText = displayText;
        this.apiValue = apiValue;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getApiValue() {
        return apiValue;
    }

    public void setApiValue(String apiValue) {
        this.apiValue = apiValue;
    }

    @Override
    public String toString() {
        return displayText;
    }
}
