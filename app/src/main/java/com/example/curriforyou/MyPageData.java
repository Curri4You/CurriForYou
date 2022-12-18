package com.example.curriforyou;

public class MyPageData {
    int major_division;
    String major_name;

    public MyPageData(int major_division, String major_name) {
        this.major_division = major_division;
        this.major_name = major_name;
    }

    public int getMajor_division() {
        return major_division;
    }

    public void setMajor_division(int major_division) {
        this.major_division = major_division;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }
}
