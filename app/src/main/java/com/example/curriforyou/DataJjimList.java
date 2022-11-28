package com.example.curriforyou;

public class DataJjimList {
    String major_division, course_name, course_id, is_open, credit, jjim, category;

    public DataJjimList(String major_division, String course_name, String course_id,
                        String is_open, String credit, String jjim, String category) {
        this.major_division = major_division;
        this.course_name = course_name;
        this.course_id = course_id;
        this.is_open = is_open;
        this.credit = credit;
        this.jjim = jjim;
        this.category = category;
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

    public String getJjim() {
        return jjim;
    }

    public void setJjim(String jjim) {
        this.jjim = jjim;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
