package com.example.quizapp;

public class User {
    private String email;
    private String username;
    private String age;
    private String country;
    private boolean isAdmin;

    public User() {

    }
    public User(String email, String username, String country, String age, boolean isAdmin) {
        this.email = email;
        this.username = username;
        this.country = country;
        this.age = age;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

}
