package com.example.curriforyou;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPageRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DataMpList> listData = new ArrayList<>();

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_mpbutton, parent, false);
        return new ViewHolderMpList(view);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderMpList) holder).onBind(listData.get(position));
    }

    public int getItemCount() {
        return listData.size();
    }

    void addItem(DataMpList data) {
        listData.add(data);
    }

    class ViewHolderMpList extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout ll_majorButton;
        Button btn_major;
        ImageView iv_delMajor;
        private DataMpList data;

        public ViewHolderMpList(@NonNull View itemView) {
            super(itemView);

            ll_majorButton = itemView.findViewById(R.id.ll_majorButton);
            btn_major = itemView.findViewById(R.id.btn_major);
            iv_delMajor = itemView.findViewById(R.id.iv_delMajor);
        }

        public void onBind(DataMpList data) {
            this.data = data;

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







