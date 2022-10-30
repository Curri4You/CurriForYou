package com.example.curriforyou;

public class DataGmList {
    // semester(학기), totalGrade(총 학점), majorGrade(전공학점), liberalGrade(교양학점)
    // syllabus_id(학수번호), course_name(강좌명), credit(학점), grade(성적)
    String semester, totalGrade, majorGrade, liberalGrade, syllabus_id, course_name, credit, grade;

    public DataGmList(String semester, String totalGrade, String majorGrade, String liberalGrade, String syllabus_id, String course_name, String credit, String grade){
        this.semester = semester;
        this.totalGrade = totalGrade;
        this.majorGrade = majorGrade;
        this.liberalGrade = liberalGrade;
        this.syllabus_id = syllabus_id;
        this.course_name = course_name;
        this.credit = credit;
        this.grade = grade;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(String totalGrade) {
        this.totalGrade = totalGrade;
    }

    public String getMajorGrade() {
        return majorGrade;
    }

    public void setMajorGrade(String majorGrade) {
        this.majorGrade = majorGrade;
    }

    public String getLiberalGrade() {
        return liberalGrade;
    }

    public void setLiberalGrade(String liberalGrade) {
        this.liberalGrade = liberalGrade;
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
