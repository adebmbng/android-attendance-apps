package com.debam.attendance.models;

import com.google.gson.annotations.SerializedName;

public class PresenceModel {
    @SerializedName("student")
    String nik;
    @SerializedName("presence")
    int presence;

    public PresenceModel() {
    }

    public PresenceModel(int presence) {
        this.presence = presence;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public int getPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }


}
