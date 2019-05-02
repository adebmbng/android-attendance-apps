package com.debam.attendance.models;

import com.google.gson.annotations.SerializedName;

public class ScoreModel {
    @SerializedName("student")
    String nik;
    @SerializedName("score")
    int score;

    public ScoreModel() {
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
