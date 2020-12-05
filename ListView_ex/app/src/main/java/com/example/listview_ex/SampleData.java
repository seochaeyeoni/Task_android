package com.example.listview_ex;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class SampleData {
    private int profile;
    private String name;
    private String grade;
    private String content;

    public SampleData(int profile, String name, String grade, String content){
        this.profile = profile;
        this.name = name;
        this.grade = grade;
        this.content = content;
    }

    public int getProfile() { return this.profile; }

    public String getName()
    {
        return this.name;
    }

    public String getGrade()
    {
        return this.grade;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setContent(String content) {
        this.content = content;
    }
}