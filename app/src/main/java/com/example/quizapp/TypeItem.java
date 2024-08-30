package com.example.quizapp;

public class TypeItem {

    private String displayText;
    private String apiValue;

    public TypeItem(String displayItem, String apiValue) {
        this.displayText = displayItem;
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
