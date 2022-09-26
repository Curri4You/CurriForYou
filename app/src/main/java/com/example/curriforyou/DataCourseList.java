package com.example.curriforyou;

public class DataCourseList {
    String course_name, subject_id, gpa, is_english;

    public DataCourseList(String course_name, String subject_id, String gpa, String is_english){
        this.course_name = course_name;
        this.subject_id = subject_id;
        this.gpa = gpa;
        this.is_english = is_english;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getIs_english() {
        return is_english;
    }

    public void setIs_english(String is_english) {
        this.is_english = is_english;
    }
}
