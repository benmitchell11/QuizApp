package com.example.quizapp;

public class User {
    private String email;
    private String username;
    private String country;
    private String age;
    private boolean isAdmin;

    // Constructor
    public User(String email, String username, String country, String age, boolean isAdmin) {
        this.email = email;
        this.username = username;
        this.country = country;
        this.age = age;
        this.isAdmin = isAdmin;
    }

    // Getters and setters
    // ...
}
