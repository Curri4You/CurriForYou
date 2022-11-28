package com.example.curriforyou;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JjimRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DataJjimList> listData = null;
    private Context context;
    private SparseBooleanArray selecteditems = new SparseBooleanArray();
    private SparseBooleanArray selectedhearts = new SparseBooleanArray();
    private int prePosition = -1;
    int CHECK_NUM = 0;
    Activity activity;

    //생성자
    JjimRecyclerAdapter(ArrayList<DataJjimList> list) {
        listData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater를 이용하여 item.xml을 inflate시킴
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_jjim, parent, false);
        return new ViewHolderJjimList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //item을 하나하나 보여주는(bind되는) 함수
        ((ViewHolderJjimList) holder).onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        //RecyclerView의 총 개수
        return listData.size();
    }

    void addItem(DataJjimList data) {
        //외부에서 item을 추가시킬 함수
        listData.add(data);
    }

    public void setItems(ArrayList<DataJjimList> listData1){
        listData = listData1;
        notifyDataSetChanged();
    }

    class ViewHolderJjimList extends RecyclerView.ViewHolder {

        TextView tv_course_name, tv_course_id, tv_is_open, tv_credit;
        ImageView iv_major_division;
        private DataJjimList data;
        private int position;
        private ImageButton ib_heart;
        boolean i = true;

        public ViewHolderJjimList(@NonNull View itemView) {
            super(itemView);

            tv_course_name = itemView.findViewById(R.id.tv_course_name);
            tv_course_id = itemView.findViewById(R.id.tv_course_id);
            tv_is_open = itemView.findViewById(R.id.tv_is_open);
            tv_credit = itemView.findViewById(R.id.tv_credit);
            iv_major_division = itemView.findViewById(R.id.iv_major_division);
            ib_heart = itemView.findViewById(R.id.ib_heart);
        }

        public void onBind(DataJjimList data, int position) {
            this.data = data;
            this.position = position;

            tv_course_name.setText(data.getCourse_name());
            tv_course_id.setText(data.getCourse_id());
            tv_is_open.setText(data.getIs_open());
            tv_credit.setText(data.getCredit());

            //major_division에 따른 바 색깔 변경
            if (data.getMajor_division().equals("1")) {
                iv_major_division.setImageResource(R.drawable.rectangle_blue5);
            } else if (data.getMajor_division().equals("2")) {
                iv_major_division.setImageResource(R.drawable.rectangle_blue3);
            } else if (data.getMajor_division().equals("3")) {
                iv_major_division.setImageResource(R.drawable.rectangle_blue1);
            }

//            changeVisibility(selecteditems.get(position));

//            itemView.setOnClickListener(this);
//            ib_heart.setOnClickListener(this);
        }

//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.ll_listitem:
//                    if (selecteditems.get(position)) {
//                        //펼쳐진 item을 클릭 시
//                        selecteditems.delete(position);
//                    } else {
//                        //직전의 클릭했던 item의 클릭상태를 지움
//                        selecteditems.delete(prePosition);
//                        //클릭한 item의 position을 저장
//                        selecteditems.put(position, true);
//                    }
//                    //해당 포지션의 변화를 알림
//                    if (prePosition != -1)
//                        notifyItemChanged(prePosition);
//                    notifyItemChanged(position);
//                    //클릭된 position 저장
//                    prePosition = position;
//                    break;
//                case R.id.ib_heart:
//                    if (CHECK_NUM == 0) {
//                        ib_heart.setSelected(false);
//                        CHECK_NUM = 1;
//                    } else {
//                        ib_heart.setSelected(true);
//                        CHECK_NUM = 0;
//                    }
//            }
//        }

        /*
         * 클릭된 Item의 상태 변경
         * @param isExpanded Item을 펼칠 것인지 여부
         */
//        private void changeVisibility(final boolean isExpanded) {
//            //height 값을 dp로 지정해서 넣고 싶으면 아래 소스를 이용
//            int dpValue = 150;
//            float d = context.getResources().getDisplayMetrics().density;
//            int height = (int) (dpValue * d);
//
//            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
//            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
//            // Animation이 실행되는 시간, n/1000초
//            va.setDuration(600);
//            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    // value는 height 값
//                    int value = (int) animation.getAnimatedValue();
//                    // imageView가 실제로 사라지게하는 부분
//                    tv_item_open.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
//                }
//            });
//            //Animation start
//            va.start();
//        }

    }

}