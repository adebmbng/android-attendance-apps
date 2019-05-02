package com.debam.attendance.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScheduleTodayResponse {
    @SerializedName("total_data")int totalData;
    @SerializedName("total_page")int totalPage;
    @SerializedName("kbm")ArrayList<KbmModels> kbm;
    @SerializedName("recent")ArrayList<KbmModels> recent;

    public ArrayList<KbmModels> getRecent() {
        return recent;
    }

    public void setRecent(ArrayList<KbmModels> recent) {
        this.recent = recent;
    }

    public int getTotalData() {
        return totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<KbmModels> getKbm() {
        return kbm;
    }

    public void setKbm(ArrayList<KbmModels> kbm) {
        this.kbm = kbm;
    }

    public ScheduleTodayResponse() {

    }
}
