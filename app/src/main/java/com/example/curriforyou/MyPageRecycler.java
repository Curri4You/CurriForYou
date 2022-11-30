package com.example.curriforyou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPageRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DataMpList> listData = null;
    private Context context;

    MyPageRecycler(ArrayList<DataMpList> list) {
        listData = list;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_mpbutton, parent, false);
        return new ViewHolderMpList(view);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderMpList) holder).onBind(listData.get(position), position);
    }

    public int getItemCount() {
        return listData.size();
    }

    void addItem(DataMpList data) {
        listData.add(data);
    }

    class ViewHolderMpList extends RecyclerView.ViewHolder {

        Button btn_major;
        private DataMpList data;
        private int position;

        public ViewHolderMpList(@NonNull View itemView) {
            super(itemView);

            btn_major = itemView.findViewById(R.id.btn_major);
        }

        public void onBind(DataMpList data, int position) {
            this.data = data;
            this.position = position;

            btn_major.setText(data.getMajor_name());
        }
    }
}







