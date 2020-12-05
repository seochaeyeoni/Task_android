package com.example.task05.MovieInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actor {
    @SerializedName("peopleNm")
    @Expose
    private String peopleNm;
    @SerializedName("peopleNmEn")
    @Expose
    private String peopleNmEn;
    @SerializedName("cast")
    @Expose
    private String cast;
    @SerializedName("castEn")
    @Expose
    private String castEn;

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

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getCastEn() {
        return castEn;
    }

    public void setCastEn(String castEn) {
        this.castEn = castEn;
    }
}
