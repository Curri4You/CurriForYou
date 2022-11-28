package com.example.curriforyou;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RcRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DataRecomList> listData = null;
    private SparseBooleanArray selecteditems = new SparseBooleanArray();
    boolean jjim = false;

    //생성자
    RcRecylerAdapter(ArrayList<DataRecomList> list) {
        listData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_recom, parent, false);
        return new ViewHolderRecomList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RcRecylerAdapter.ViewHolderRecomList) holder).onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(DataRecomList data) {
        //외부에서 item을 추가시킬 함수
        listData.add(data);
    }

    public void setItems(ArrayList<DataRecomList> listData1) {
        listData = listData1;
        notifyDataSetChanged();
    }

    class ViewHolderRecomList extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_recom_course_name, tv_recom_open_major, tv_recom_credit, tv_recom_pre_course_name;
        ImageButton ib_recom_heart;
        private DataRecomList data;
        private int position;

        public ViewHolderRecomList(@NonNull View itemView) {
            super(itemView);

            tv_recom_course_name = itemView.findViewById(R.id.tv_recom_course_name);
            tv_recom_open_major = itemView.findViewById(R.id.tv_recom_open_major);
            tv_recom_credit = itemView.findViewById(R.id.tv_recom_credit);
            tv_recom_pre_course_name = itemView.findViewById(R.id.tv_recom_pre_course_name);
            ib_recom_heart = itemView.findViewById(R.id.ib_recom_heart);

        }

        public void onBind(DataRecomList data, int position) {
            this.data = data;
            this.position = position;

            tv_recom_course_name.setText(data.getCourse_name());
            tv_recom_open_major.setText(data.getOpen_major());
            tv_recom_credit.setText(data.getCredit());
            tv_recom_pre_course_name.setText(data.getPre_course_name());

            ib_recom_heart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_recom_heart:
                    if(jjim){
                        ib_recom_heart.setImageResource(R.drawable.empty_heart);
                        jjim = false;
                    } else{
                        ib_recom_heart.setImageResource(R.drawable.filled_heart);
                        jjim = true;
                    }
                    break;
            }
        }
    }
}
