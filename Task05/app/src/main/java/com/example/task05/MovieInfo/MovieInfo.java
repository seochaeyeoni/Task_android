package com.example.task05.MovieInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieInfo {
    @SerializedName("movieCd")
    @Expose
    private String movieCd;
    @SerializedName("movieNm")
    @Expose
    private String movieNm;
    @SerializedName("movieNmEn")
    @Expose
    private String movieNmEn;
    @SerializedName("movieNmOg")
    @Expose
    private String movieNmOg;
    @SerializedName("showTm")
    @Expose
    private String showTm;
    @SerializedName("prdtYear")
    @Expose
    private String prdtYear;
    @SerializedName("openDt")
    @Expose
    private String openDt;
    @SerializedName("prdtStatNm")
    @Expose
    private String prdtStatNm;
    @SerializedName("typeNm")
    @Expose
    private String typeNm;
    @SerializedName("nations")
    @Expose
    private List<Nation> nations = null;
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;
    @SerializedName("directors")
    @Expose
    private List<Director> directors = null;
    @SerializedName("actors")
    @Expose
    private List<Actor> actors = null;
    @SerializedName("audits")
    @Expose
    private List<Audit> audits = null;


    public String getMovieCd() {
        return movieCd;
    }

    public void setMovieCd(String movieCd) {
        this.movieCd = movieCd;
    }

    public String getMovieNm() {
        return movieNm;
    }

    public void setMovieNm(String movieNm) {
        this.movieNm = movieNm;
    }

    public String getMovieNmEn() {
        return movieNmEn;
    }

    public void setMovieNmEn(String movieNmEn) {
        this.movieNmEn = movieNmEn;
    }

    public String getMovieNmOg() {
        return movieNmOg;
    }

    public void setMovieNmOg(String movieNmOg) {
        this.movieNmOg = movieNmOg;
    }

    public String getShowTm() {
        return showTm;
    }

    public void setShowTm(String showTm) {
        this.showTm = showTm;
    }

    public String getPrdtYear() {
        return prdtYear;
    }

    public void setPrdtYear(String prdtYear) {
        this.prdtYear = prdtYear;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getPrdtStatNm() {
        return prdtStatNm;
    }

    public void setPrdtStatNm(String prdtStatNm) {
        this.prdtStatNm = prdtStatNm;
    }

    public String getTypeNm() {
        return typeNm;
    }

    public void setTypeNm(String typeNm) {
        this.typeNm = typeNm;
    }

    public List<Nation> getNations() {
        return nations;
    }

    public void setNations(List<Nation> nations) {
        this.nations = nations;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }


    public List<Audit> getAudits() {
        return audits;
    }

    public void setAudits(List<Audit> audits) {
        this.audits = audits;
    }
}
