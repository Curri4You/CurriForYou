package com.example.curriforyou;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderCourseList extends RecyclerView.ViewHolder {

    TextView tv_course_name, tv_subject_id, tv_gpa, tv_is_english;

    public ViewHolderCourseList(@NonNull View itemView) {
        super(itemView);

        tv_course_name = itemView.findViewById(R.id.tv_course_name);
        tv_subject_id = itemView.findViewById(R.id.tv_subject_id);
        tv_gpa = itemView.findViewById(R.id.tv_gpa);
        tv_is_english = itemView.findViewById(R.id.tv_is_english);
    }

    public void onBind(DataCourseList data) {
        tv_course_name.setText(data.getCourse_name());
        tv_subject_id.setText(data.getSubject_id());
        tv_gpa.setText(data.getGpa());
        tv_is_english.setText(data.getIs_english());
    }
}
