package com.example.task05.MovieInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("genreNm")
    @Expose
    private String genreNm;

    public String getGenreNm() {
        return genreNm;
    }

    public void setGenreNm(String genreNm) {
        this.genreNm = genreNm;
    }
}
