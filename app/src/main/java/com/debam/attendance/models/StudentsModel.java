package com.debam.attendance.models;

import com.google.gson.annotations.SerializedName;

public class StudentsModel {
    @SerializedName("userID")
    int id;
    @SerializedName("nik")
    String nik;
    @SerializedName("first_name")
    String fname;
    @SerializedName("last_name")
    String lname;
    @SerializedName("presenceID")
    int presenceID;
    @SerializedName("score")
    int score;
    boolean presence, presence_status;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPresenceID() {
        return presenceID;
    }

    public void setPresenceID(int presenceID) {
        this.presenceID = presenceID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPresence_status() {
        return presence_status;
    }

    public void setPresence_status(boolean presence_status) {
        this.presence_status = presence_status;
    }

    public boolean isPresence() {
        return presence;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public StudentsModel() {

    }
}
