package com.example.curriforyou;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MpRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    // adapter에 들어갈 list
    private ArrayList<DataMp> listData = new ArrayList<>();
    private Context context;
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listbutton_major, parent, false);
        return new MpRecyclerAdapter.MpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MpRecyclerAdapter.MpViewHolder) holder).onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        //RecyclerView의 총 개수
        return listData.size();
    }

    void addItem(DataMp data) {
        // 외부에서 item을 추가시킬 함수
        listData.add(data);
    }

    class MpViewHolder extends RecyclerView.ViewHolder {

        Button btn_major;
        private DataMp data;
        private int position;

        public MpViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_major = itemView.findViewById(R.id.btn_major);
        }

        //데이터 이용해서 text 변경
        void onBind(DataMp data, int position) {
            this.data = data;
            this.position = position;

            btn_major.setText(data.getMajor_name());

        }
    }
}
