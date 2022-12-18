package com.example.curriforyou;

import static android.view.View.GONE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPageRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MyPageData> listData = new ArrayList<>();

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_majorbtn, parent, false);
        return new ViewHolderMpList(view);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderMpList) holder).onBind(listData.get(position));
    }

    public int getItemCount() {
        return listData.size();
    }

    void addItem(MyPageData data) {
        listData.add(data);
    }

    class ViewHolderMpList extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout ll_majorButton;
        TextView tv_major_division;
        Button btn_major;
        ImageView iv_delMajor;
        private MyPageData data;

        public ViewHolderMpList(@NonNull View itemView) {
            super(itemView);

            ll_majorButton = itemView.findViewById(R.id.ll_majorButton);
            tv_major_division = itemView.findViewById(R.id.tv_major_division);
            btn_major = itemView.findViewById(R.id.btn_major);
            iv_delMajor = itemView.findViewById(R.id.iv_delMajor);
        }

        public void onBind(MyPageData data) {
            this.data = data;

            if(data.getMajor_division() == 1){
                tv_major_division.setText("주전공");
            } else if (data.getMajor_division() == 2){
                tv_major_division.setText("복수전공");
            } else if (data.getMajor_division() == 3){
                tv_major_division.setText("부전공");
            } else if (data.getMajor_division() == 4){
                tv_major_division.setText("연계전공");
            } else if (data.getMajor_division() == 5){
                tv_major_division.setText("심화전공");
            } else{
                tv_major_division.setText("ERROR");
            };
            btn_major.setText(data.getMajor_name());

            btn_major.setOnClickListener(this);
            iv_delMajor.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_delMajor:
                    ll_majorButton.setVisibility(GONE);
                    break;
            }
        }
    }
}







