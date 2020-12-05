package com.example.task05.DailyBoxOffice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyBoxOfficeList {

    @SerializedName("rnum")
    @Expose
    private String rnum;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("rankInten")
    @Expose
    private String rankInten;
    @SerializedName("rankOldAndNew")
    @Expose
    private String rankOldAndNew;
    @SerializedName("movieCd")
    @Expose
    private String movieCd;
    @SerializedName("movieNm")
    @Expose
    private String movieNm;
    @SerializedName("openDt")
    @Expose
    private String openDt;
    @SerializedName("salesAmt")
    @Expose
    private String salesAmt;
    @SerializedName("salesShare")
    @Expose
    private String salesShare;
    @SerializedName("salesInten")
    @Expose
    private String salesInten;
    @SerializedName("salesChange")
    @Expose
    private String salesChange;
    @SerializedName("salesAcc")
    @Expose
    private String salesAcc;
    @SerializedName("audiCnt")
    @Expose
    private String audiCnt;
    @SerializedName("audiInten")
    @Expose
    private String audiInten;
    @SerializedName("audiChange")
    @Expose
    private String audiChange;
    @SerializedName("audiAcc")
    @Expose
    private String audiAcc;
    @SerializedName("scrnCnt")
    @Expose
    private String scrnCnt;
    @SerializedName("showCnt")
    @Expose
    private String showCnt;

    public String getRnum() {
        return rnum;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRankInten() {
        return rankInten;
    }

    public void setRankInten(String rankInten) {
        this.rankInten = rankInten;
    }

    public String getRankOldAndNew() {
        return rankOldAndNew;
    }

    public void setRankOldAndNew(String rankOldAndNew) {
        this.rankOldAndNew = rankOldAndNew;
    }

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

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getSalesAmt() {
        return salesAmt;
    }

    public void setSalesAmt(String salesAmt) {
        this.salesAmt = salesAmt;
    }

    public String getSalesShare() {
        return salesShare;
    }

    public void setSalesShare(String salesShare) {
        this.salesShare = salesShare;
    }

    public String getSalesInten() {
        return salesInten;
    }

    public void setSalesInten(String salesInten) {
        this.salesInten = salesInten;
    }

    public String getSalesChange() {
        return salesChange;
    }

    public void setSalesChange(String salesChange) {
        this.salesChange = salesChange;
    }

    public String getSalesAcc() {
        return salesAcc;
    }

    public void setSalesAcc(String salesAcc) {
        this.salesAcc = salesAcc;
    }

    public String getAudiCnt() {
        return audiCnt;
    }

    public void setAudiCnt(String audiCnt) {
        this.audiCnt = audiCnt;
    }

    public String getAudiInten() {
        return audiInten;
    }

    public void setAudiInten(String audiInten) {
        this.audiInten = audiInten;
    }

    public String getAudiChange() {
        return audiChange;
    }

    public void setAudiChange(String audiChange) {
        this.audiChange = audiChange;
    }

    public String getAudiAcc() {
        return audiAcc;
    }

    public void setAudiAcc(String audiAcc) {
        this.audiAcc = audiAcc;
    }

    public String getScrnCnt() {
        return scrnCnt;
    }

    public void setScrnCnt(String scrnCnt) {
        this.scrnCnt = scrnCnt;
    }

    public String getShowCnt() {
        return showCnt;
    }

    public void setShowCnt(String showCnt) {
        this.showCnt = showCnt;
    }

}