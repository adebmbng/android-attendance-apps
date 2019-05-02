package com.debam.attendance.models;

import com.google.gson.annotations.SerializedName;

public class KbmModels {
    @SerializedName("id") String id;
    @SerializedName("name")String name;
    @SerializedName("day") String day;
    @SerializedName("start_time")String startTime;
    @SerializedName("end_time")String endTime;
    @SerializedName("major")String major;
    @SerializedName("grade")String grade;
    @SerializedName("pic")String pic;
    @SerializedName("NIK")String nik;
    @SerializedName("SubjectName")String subjectName;
    @SerializedName("duration")int duration;
    @SerializedName("duration_value")String durationValue;
    @SerializedName("param") String param;
    @SerializedName("SubjectDescription") String description;
    @SerializedName("presenceTime") String presenceTime;
    @SerializedName("overall") String overall;
    @SerializedName("keterangan") String keterangan;
    @SerializedName("created_at")String timeAttendance;
    @SerializedName("photo1") String photo1;
    @SerializedName("photo2") String photo2;
    @SerializedName("photo3") String photo3;
    @SerializedName("students") int students;

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getTimeAttendance() {
        return timeAttendance;
    }

    public void setTimeAttendance(String timeAttendance) {
        this.timeAttendance = timeAttendance;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getOverall() {
        return overall;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }

    public String getPresenceTime() {
        return presenceTime;
    }

    public void setPresenceTime(String presenceTime) {
        this.presenceTime = presenceTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public KbmModels() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationValue() {
        return durationValue;
    }

    public void setDurationValue(String durationValue) {
        this.durationValue = durationValue;
    }
}
