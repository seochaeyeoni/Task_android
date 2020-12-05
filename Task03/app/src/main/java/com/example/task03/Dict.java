package com.example.task03;

public class Dict {
    public int getN;
    private String name;
    private String grade;
    private String memo;

    public Dict(String name, String grade, String memo) {
        this.name = name;
        this.grade = grade;
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
