package com.example.quizapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Parcelable {
    private String title;
    private String startDate;
    private String endDate;
    private String category;
    private String type;
    private String difficulty;
    private List<Question> questions;
    private String quizId;
    private int likes;
    private List<String> likedByUserIds;




    public Quiz(String title, String startDate, String endDate, String category, String type, String difficulty, List<Question> questions, String quizId, int likes, List<String> likedByUserIds) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.questions = questions;
        this.quizId = quizId;
        this.likes = likes;
        this.likedByUserIds = likedByUserIds;
    }
    public Quiz() {

    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getLikedByUserIds() {
        return likedByUserIds;
    }

    public void setLikedByUserIds(List<String> likedByUserIds) {
        this.likedByUserIds = likedByUserIds;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getStartDate() {

        return startDate;
    }

    public void setStartDate(String startDate) {

        this.startDate = startDate;
    }

    public String getEndDate() {

        return endDate;
    }

    public void setEndDate(String endDate) {

        this.endDate = endDate;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getDifficulty() {

        return difficulty;
    }

    public void setDifficulty(String difficulty) {

        this.difficulty = difficulty;
    }

    public List<Question> getQuestions() {

        return questions;
    }

    public void setQuestions(List<Question> questions) {

        this.questions = questions;
    }

    protected Quiz(Parcel in) {
        title = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        category = in.readString();
        type = in.readString();
        difficulty = in.readString();
        questions = in.createTypedArrayList(Question.CREATOR);
        quizId = in.readString();
        likes = in.readInt();
        likedByUserIds = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(category);
        dest.writeString(type);
        dest.writeString(difficulty);
        dest.writeTypedList(questions);
        dest.writeString(quizId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel source) {
            return new Quiz(source);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };
}
