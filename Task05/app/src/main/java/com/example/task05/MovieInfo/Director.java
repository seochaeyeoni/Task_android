package com.example.task05.MovieInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Director {
    @SerializedName("peopleNm")
    @Expose
    private String peopleNm;
    @SerializedName("peopleNmEn")
    @Expose
    private String peopleNmEn;

    public String getPeopleNm() {
        return peopleNm;
    }

    public void setPeopleNm(String peopleNm) {
        this.peopleNm = peopleNm;
    }

    public String getPeopleNmEn() {
        return peopleNmEn;
    }

    public void setPeopleNmEn(String peopleNmEn) {
        this.peopleNmEn = peopleNmEn;
    }
}
