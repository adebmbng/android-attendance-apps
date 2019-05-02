package com.debam.attendance.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DetailKBMResponse {
    @SerializedName("kbm")KbmModels kbm;
    @SerializedName("students")ArrayList<StudentsModel> stds;
    @SerializedName("hadir")int hadir;
    @SerializedName("izin")int izin;
    @SerializedName("alpha")int alpha;
    @SerializedName("sakit")int sakit;

    public int getHadir() {
        return hadir;
    }

    public void setHadir(int hadir) {
        this.hadir = hadir;
    }

    public int getIzin() {
        return izin;
    }

    public void setIzin(int izin) {
        this.izin = izin;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getSakit() {
        return sakit;
    }

    public void setSakit(int sakit) {
        this.sakit = sakit;
    }

    public KbmModels getKbm() {
        return kbm;
    }

    public void setKbm(KbmModels kbm) {
        this.kbm = kbm;
    }

    public ArrayList<StudentsModel> getStds() {
        return stds;
    }

    public void setStds(ArrayList<StudentsModel> stds) {
        this.stds = stds;
    }

    public DetailKBMResponse() {

    }
}
