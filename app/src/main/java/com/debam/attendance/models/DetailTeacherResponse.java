package com.debam.attendance.models;

import com.google.gson.annotations.SerializedName;

public class DetailTeacherResponse {

    @SerializedName("user")
    TeacherModel user;

    public DetailTeacherResponse() {
    }

    public TeacherModel getUser() {
        return user;
    }

    public void setUser(TeacherModel user) {
        this.user = user;
    }
}
