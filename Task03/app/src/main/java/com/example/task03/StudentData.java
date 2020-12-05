package com.example.task03;

public class StudentData {

    private int iv_profile;
    private String tv_name;
    private String tv_grade;
    private String tv_subject;
    private String tv_place;

    public StudentData(int iv_profile, String tv_name, String tv_grade, String tv_subject, String tv_place) {
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
        this.tv_grade = tv_grade;
        this.tv_subject = tv_subject;
        this.tv_place = tv_place;
    }

    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_grade() {
        return tv_grade;
    }

    public void setTv_grade(String tv_grade) {
        this.tv_grade = tv_grade;
    }

    public String getTv_subject() {
        return tv_subject;
    }

    public void setTv_subject(String tv_subject) {
        this.tv_subject = tv_subject;
    }

    public String getTv_place() {
        return tv_place;
    }

    public void setTv_place(String tv_place) {
        this.tv_place = tv_place;
    }
}
