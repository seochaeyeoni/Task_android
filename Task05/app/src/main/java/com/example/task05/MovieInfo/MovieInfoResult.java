package com.example.task05.MovieInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieInfoResult {
    @SerializedName("movieInfo")
    @Expose
    private MovieInfo movieInfo;
    @SerializedName("source")
    @Expose
    private String source;

//    아무것도 바꾸지 말고~ 갖다 쓰기만 해~
//    @SerializedName("movieInfoList")
//    @Expose
//    private List<MovieInfoList> movieInfoList = null;
//
//    public List<MovieInfoList> getMovieInfoList() {
//        return movieInfoList;
//    }
    public MovieInfo getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
    }

//    public void setMovieInfoList(List<MovieInfoList> movieInfoList) {
//        this.movieInfoList = movieInfoList;
//    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
