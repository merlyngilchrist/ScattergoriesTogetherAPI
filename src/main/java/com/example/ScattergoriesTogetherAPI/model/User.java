package com.example.ScattergoriesTogetherAPI.model;

public class User {
    private String username;
    private String password;
    private int recordScore;
    private int numberOfWins;

    public User(){}

    public User(String username, String password, int recordScore, int numberOfWins) {
        this.username = username;
        this.password = password;
        this.recordScore = recordScore;
        this.numberOfWins = numberOfWins;

    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRecordScore() {
        return recordScore;
    }

    public void setRecordScore(int recordScore) {
        this.recordScore = recordScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
