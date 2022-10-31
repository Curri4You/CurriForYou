package com.example.curriforyou;

public class DataCourseList {
    String major_division, course_name, course_id,
            is_open, credit, course_id_true_false;

    public DataCourseList(String major_division, String course_name, String course_id,
                          String is_open, String credit, String course_id_true_false){
        this.major_division = major_division;
        this.course_name = course_name;
        this.course_id = course_id;
        this.is_open = is_open;
        this.credit = credit;
        this.course_id_true_false = course_id_true_false;
    }

    public String getMajor_division() {
        return major_division;
    }

    public void setMajor_division(String major_division) {
        this.major_division = major_division;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCourse_id_true_false() {
        return course_id_true_false;
    }

    public void setCourse_id_true_false(String course_id_true_false) {
        this.course_id_true_false = course_id_true_false;
    }
}
