package com.example.curriforyou;

public class DataGmList {
    // course+year(수강연도), course_semester(수강학기), course_id(학수번호), course_name(강좌이름)
    // major_division(전공구분), credit(학점), grade(성적), category(전필,전선,교필,교선 구분)
    String course_year, course_semester, major_division, course_id, course_name, credit, grade, category;

    public DataGmList(String course_year, String course_semester, String major_division, String course_id, String course_name, String credit, String grade, String category) {
        this.course_semester = course_semester;
        this.major_division = major_division;
        this.course_id = course_id;
        this.course_name = course_name;
        this.credit = credit;
        this.grade = grade;
        this.category = category;
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

    public String getMajor_division() {
        return major_division;
    }

    public void setMajor_division(String major_division) {
        this.major_division = major_division;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
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
}