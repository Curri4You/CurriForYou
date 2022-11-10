package com.example.curriforyou;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RcRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DataRecomList> listData = new ArrayList<>();
    private SparseBooleanArray selecteditems = new SparseBooleanArray();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_recom, parent, false);
        return new ViewHolderRecomList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RcRecylerAdapter.ViewHolderRecomList)holder).onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(DataRecomList data){
        //외부에서 item을 추가시킬 함수
        listData.add(data);
    }

    class ViewHolderRecomList extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_recom_course_name, tv_recom_credit, tv_recom_data1, tv_recom_data2, tv_recom_data3;
        private DataRecomList data;
        private int position;

        public ViewHolderRecomList(@NonNull View itemView) {
            super(itemView);

            tv_recom_course_name = itemView.findViewById(R.id.tv_recom_course_name);
            tv_recom_credit = itemView.findViewById(R.id.tv_recom_credit);
            tv_recom_data1 = itemView.findViewById(R.id.tv_recom_data1);
            tv_recom_data2 = itemView.findViewById(R.id.tv_recom_data2);
            tv_recom_data3 = itemView.findViewById(R.id.tv_recom_data3);

        }

        public void onBind(DataRecomList data, int position){
            this.data = data;
            this.position = position;

            tv_recom_course_name.setText(data.getCourse_name());
            tv_recom_credit.setText(data.getCredit());
            tv_recom_data1.setText(data.getData1());
            tv_recom_data2.setText(data.getData2());
            tv_recom_data3.setText(data.getData3());

        }

        @Override
        public void onClick(View v) {

        }
    }
}
