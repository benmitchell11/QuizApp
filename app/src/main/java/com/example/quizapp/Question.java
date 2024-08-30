package com.example.quizapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Question implements Parcelable {
    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;

    public Question() {

    }

    public Question(String question, String correct_answer, List<String> incorrect_answers) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }

    public String getQuestionText() {
        return question;
    }

    public void setQuestionText(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(List<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }
    protected Question(Parcel in) {
        question = in.readString();
        correct_answer = in.readString();
        incorrect_answers = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(correct_answer);
        dest.writeStringList(incorrect_answers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question: ").append(question).append("\n");
        sb.append("Correct Answer: ").append(correct_answer).append("\n");
        sb.append("Incorrect Answers: ").append(incorrect_answers).append("\n");
        return sb.toString();
    }
}
