package com.example.curriforyou;

public class DataCourseList {
    String major_division, course_name, course_id,
            is_open, credit, jjim, course_year, course_semester,
            grade, category, pre_course_name, pre_course_id, pre_is_open,
            pre_credit, pre_jjim;

    public DataCourseList(String major_division, String course_name, String course_id,
                          String is_open, String credit, String jjim, String course_year,
                          String course_semester, String grade, String category,
                          String pre_course_name, String pre_course_id,
                          String pre_is_open, String pre_credit, String pre_jjim){
        this.major_division = major_division;
        this.course_name = course_name;
        this.course_id = course_id;
        this.is_open = is_open;
        this.credit = credit;
        this.jjim = jjim;
        this.course_year = course_year;
        this.course_semester = course_semester;
        this.grade = grade;
        this.category = category;
        this.pre_course_name = pre_course_name;
        this.pre_course_id = pre_course_id;
        this.pre_is_open = pre_is_open;
        this.pre_credit = pre_credit;
        this.pre_jjim = pre_jjim;
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

    public String getCourse_year() {
        return course_year;
    }

    public void setCourse_year(String course_year) {
        this.course_year = course_year;
    }

    public String getCourse_semester() {
        return course_semester;
    }

    public void setCourse_semester(String course_semester) {
        this.course_semester = course_semester;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPre_course_name() {
        return pre_course_name;
    }

    public void setPre_course_name(String pre_course_name) {
        this.pre_course_name = pre_course_name;
    }

    public String getPre_course_id() {
        return pre_course_id;
    }

    public void setPre_course_id(String pre_course_id) {
        this.pre_course_id = pre_course_id;
    }

    public String getPre_is_open() {
        return pre_is_open;
    }

    public void setPre_is_open(String pre_is_open) {
        this.pre_is_open = pre_is_open;
    }

    public String getPre_credit() {
        return pre_credit;
    }

    public void setPre_credit(String pre_credit) {
        this.pre_credit = pre_credit;
    }

    public String getPre_jjim() {
        return pre_jjim;
    }

    public void setPre_jjim(String pre_jjim) {
        this.pre_jjim = pre_jjim;
    }

}
