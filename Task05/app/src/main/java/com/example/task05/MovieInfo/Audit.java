package com.example.task05.MovieInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Audit {
    @SerializedName("auditNo")
    @Expose
    private String auditNo;
    @SerializedName("watchGradeNm")
    @Expose
    private String watchGradeNm;

    public String getAuditNo() {
        return auditNo;
    }

    public void setAuditNo(String auditNo) {
        this.auditNo = auditNo;
    }

    public String getWatchGradeNm() {
        return watchGradeNm;
    }

    public void setWatchGradeNm(String watchGradeNm) {
        this.watchGradeNm = watchGradeNm;
    }
}
