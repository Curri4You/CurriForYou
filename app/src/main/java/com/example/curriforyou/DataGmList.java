package com.example.curriforyou;

public class DataGmList {
    //syllabus_id(학수번호), course_name(강좌명), credit(학점), grade(성적)
    String syllabus_id, course_name;
    String credit;
    String grade;

    public DataGmList(String syllabus_id, String course_name, String credit, String grade){
        this.syllabus_id = syllabus_id;
        this.course_name = course_name;
        this.credit = credit;
        this.grade = grade;
    }

    public String getSyllabus_id() {
        return syllabus_id;
    }

    public void setSyllabus_id(String syllabus_id) {
        this.syllabus_id = syllabus_id;
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
}
