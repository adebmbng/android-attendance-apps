package com.debam.attendance.models;

import com.google.gson.annotations.SerializedName;

public class TeacherModel {
    @SerializedName("nis")
    String nis;
    @SerializedName("first_name")
    String fname;
    @SerializedName("last_name")
    String lname;
    @SerializedName("email")
    String email;
    @SerializedName("address")
    String address;
    @SerializedName("phone1")
    String phone1;
    @SerializedName("birthday")
    String birthday;
    @SerializedName("school")
    String school;

    public TeacherModel() {
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
