package com.example.audiocheck.audiocheck;


public class DbModel {
    String date;
    int score;

    public DbModel() {
    }

    public DbModel(String date, int score) {
        this.date = date;
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
