package com.example.curriforyou;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVGmSemesterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // adapter에 들어갈 list
    private ArrayList<DataGmList> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item2, parent, false);
        return new ViewHolderGmList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderGmList)holder).onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(DataGmList data){
        listData.add(data);
    }

    class ViewHolderGmList extends RecyclerView.ViewHolder{

        TextView tv_syllabus_id, tv_course_name2, tv_credit;
        RadioButton rb_grade;

        public ViewHolderGmList(@NonNull View itemView) {
            super(itemView);

            tv_syllabus_id = itemView.findViewById(R.id.tv_syllabus_id);
            tv_course_name2 = itemView.findViewById(R.id.tv_course_name2);
            tv_credit = itemView.findViewById(R.id.tv_credit);
            //일단 rb는 생략
        }

        public void onBind(DataGmList data){
            tv_syllabus_id.setText(data.getSyllabus_id());
            tv_course_name2.setText(data.getCourse_name());
            tv_credit.setText(data.getCredit());
        }
    }
}
