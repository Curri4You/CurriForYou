package com.example.curriforyou;

public class DataRecomList {
    String category, course_name, open_major, credit, pre_course_name, jjim;

    public DataRecomList(String category, String course_name, String open_major,
                         String credit, String pre_course_name,
                         String jjim){
        this.category = category;
        this.course_name = course_name;
        this.open_major = open_major;
        this.credit = credit;
        this.pre_course_name = pre_course_name;
        this.jjim = jjim;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getOpen_major() {
        return open_major;
    }

    public void setOpen_major(String open_major) {
        this.open_major = open_major;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getPre_course_name() {
        return pre_course_name;
    }

    public void setPre_course_name(String pre_course_name) {
        this.pre_course_name = pre_course_name;
    }

    public String getJjim() {
        return jjim;
    }

    public void setJjim(String jjim) {
        this.jjim = jjim;
    }

}
