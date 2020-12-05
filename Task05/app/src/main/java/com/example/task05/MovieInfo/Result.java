package com.example.task05.MovieInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("movieInfoResult")
    @Expose
    private MovieInfoResult movieInfoResult;

    public MovieInfoResult getMovieInfoResult() {
        return movieInfoResult;
    }

    public void setMovieInfoResult(MovieInfoResult movieInfoResult) {
        this.movieInfoResult = movieInfoResult;
    }
}
