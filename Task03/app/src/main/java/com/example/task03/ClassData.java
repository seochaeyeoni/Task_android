package com.example.task03;

public class ClassData {

    private int iv_profile;
    private String tv_name;
    private String tv_grade;
    private String tv_memo;


    public ClassData(int iv_profile, String tv_name, String tv_grade, String tv_memo) {
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
        this.tv_grade = tv_grade;
        this.tv_memo = tv_memo;
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

    public String getTv_memo() { return tv_memo; }

    public void setTv_memo(String tv_memo) { this.tv_memo = tv_memo; }
}

