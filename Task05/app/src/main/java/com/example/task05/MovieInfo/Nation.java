package com.example.task05.MovieInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nation {
    @SerializedName("nationNm")
    @Expose
    private String nationNm;

    public String getNationNm() {
        return nationNm;
    }

    public void setNationNm(String nationNm) {
        this.nationNm = nationNm;
    }
}
