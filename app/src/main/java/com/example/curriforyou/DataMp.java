package com.example.curriforyou;

public class DataMp {
    //major_name (전공이름)
    String user_name, student_id, major_name;

    public DataMp(String user_name, String student_id, String major_name){
        this.user_name = user_name;
        this.student_id = student_id;
        this.major_name = major_name;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
}
