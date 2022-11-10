package com.example.curriforyou;

public class DataRecomList {
    String course_name, credit, data1, data2, data3;

    public DataRecomList(String course_name, String credit, String data1,
                         String data2, String data3){
        this.course_name = course_name;
        this.credit = credit;
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }
}
