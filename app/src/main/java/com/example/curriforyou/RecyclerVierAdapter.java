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
import java.util.Locale;

public class RecyclerVierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //adapter에 들어갈 list
    /*private ArrayList<DataCourseList> listData = new ArrayList<>();*/
    private ArrayList<DataCourseList> listData = null;
    private Context context;

    //item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selecteditems = new SparseBooleanArray();

    //직전에 클릭됐던 item의 position
    private int prePosition = -1;

    ////////////////
    int CHECK_NUM = 0;

    //
    private SparseBooleanArray selectedhearts = new SparseBooleanArray();

    //생성자
    RecyclerVierAdapter(ArrayList<DataCourseList> list){
        listData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater를 이용하여 item.xml을 inflate시킴
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        return new ViewHolderCourseList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //item을 하나하나 보여주는(bind되는) 함수
        ((ViewHolderCourseList)holder).onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        //RecyclerView의 총 개수
        return listData.size();
    }

    void addItem(DataCourseList data){
        //외부에서 item을 추가시킬 함수
        listData.add(data);
    }

    //[Search]
    public void setItems(ArrayList<DataCourseList> listData1){
        listData = listData1;
        notifyDataSetChanged();
    }

    //Recycler의 핵심; ViewHolder
    //subView를 setting
    class ViewHolderCourseList extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_course_name, tv_course_id, tv_is_open, tv_credit,
                tv_course_year, tv_course_semester, tv_grade;
        ImageView iv_major_division;
        CheckBox cb_taken;
        private LinearLayout tv_item_open, ll_listitem;
        private DataCourseList data;
        private int position;
        private ImageButton ib_heart;
        /*ImageView iv_heart;*/
        boolean i = true;

        public ViewHolderCourseList(@NonNull View itemView) {
            super(itemView);

            tv_course_name = itemView.findViewById(R.id.tv_course_name);
            tv_course_id = itemView.findViewById(R.id.tv_course_id);
            tv_is_open = itemView.findViewById(R.id.tv_is_open);
            tv_credit = itemView.findViewById(R.id.tv_credit);
            ///
            tv_course_year = itemView.findViewById(R.id.tv_course_year);
            tv_course_semester = itemView.findViewById(R.id.tv_course_semester);
            tv_grade = itemView.findViewById(R.id.tv_grade);
            iv_major_division = itemView.findViewById(R.id.iv_major_division);
            cb_taken = itemView.findViewById(R.id.cb_taken);

            tv_item_open = itemView.findViewById(R.id.tv_item_open);
            ib_heart = itemView.findViewById(R.id.ib_heart);
            ll_listitem = itemView.findViewById(R.id.ll_listitem);
            /*iv_heart = itemView.findViewById(R.id.iv_heart);*/

            /*iv_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if (i == true){
                            iv_heart.setImageResource(R.drawable.filled_heart);
                            i = false;
                        } else {
                            iv_heart.setImageResource(R.drawable.empty_heart);
                            i = true;
                        }
                    }
                }
            });*/
        }

        public void onBind(DataCourseList data, int position) {

            this.data = data;
            this.position = position;

            tv_course_name.setText(data.getCourse_name());
            tv_course_id.setText(data.getCourse_id());
            tv_is_open.setText(data.getIs_open());
            tv_credit.setText(data.getCredit());
            ///
            tv_course_year.setText(data.getCourse_year());
            tv_course_semester.setText(data.getCourse_semester());
            tv_grade.setText(data.getGrade());

            //major_division에 따른 바 색깔 변경
            if(data.getMajor_division().equals("1")){
                iv_major_division.setImageResource(R.drawable.rectangle_blue5);
            } else if (data.getMajor_division().equals("2")){
                iv_major_division.setImageResource(R.drawable.rectangle_blue3);
            } else if (data.getMajor_division().equals("3")){
                iv_major_division.setImageResource(R.drawable.rectangle_blue1);
            }

            //수강&미수강 버튼 변경
            if (!data.getCourse_year().equals("미정")){
                cb_taken.setText("수강");
                cb_taken.setChecked(true);
            }

            //

            changeVisibility(selecteditems.get(position));
            /*changeImageView(selectedhearts.get(position));*/

            itemView.setOnClickListener(this);
            ib_heart.setOnClickListener(this);
            ll_listitem.setOnClickListener(this);
            /*iv_heart.setOnClickListener(this);*/
        }

        /*ImageView iv_heart = (ImageView)findViewById(R.id.iv_heart);
        iv_heart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                iv_heart.setSelected(event.getAction()==MotionEvent.ACTION_DOWN);
                return true;
            }
        });*/

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_listitem:
                    if(selecteditems.get(position)){
                        //펼쳐진 item을 클릭 시
                        selecteditems.delete(position);
                    } else {
                        //직전의 클릭했던 item의 클릭상태를 지움
                        selecteditems.delete(prePosition);
                        //클릭한 item의 position을 저장
                        selecteditems.put(position, true);
                    }
                    //해당 포지션의 변화를 알림
                    if (prePosition != -1)
                        notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    //클릭된 position 저장
                    prePosition = position;
                    break;
                case R.id.ib_heart:
                    if(CHECK_NUM == 0){
                        ib_heart.setSelected(false);
                        CHECK_NUM = 1;
                    } else {
                        ib_heart.setSelected(true);
                        CHECK_NUM = 0;
                    }
            }
        }

        /*
         * 클릭된 Item의 상태 변경
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            //height 값을 dp로 지정해서 넣고 싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int)(dpValue*d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView가 실제로 사라지게하는 부분
                    tv_item_open.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            //Animation start
            va.start();
        }


        /*private void changeImageView(final boolean isExpanded){
            //height 값을 dp로 지정해서 넣고 싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int)(dpValue*d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(200000000);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView가 실제로 사라지게하는 부분
                    iv_heart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            iv_heart.setImageResource(R.drawable.filled_heart);
                        }
                    });
                    iv_heart.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            iv_heart.setSelected(event.getAction()==MotionEvent.ACTION_DOWN);
                            return true;
                        }
                    });
                }
            });
            //Animation start
            va.start();
        }*/
    }

}
