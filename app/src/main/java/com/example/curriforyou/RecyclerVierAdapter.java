package com.example.curriforyou;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerVierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //adapter에 들어갈 list
    private ArrayList<DataCourseList> listData = new ArrayList<>();
    private Context context;
    //item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selecteditems = new SparseBooleanArray();
    //직전에 클릭됐던 item의 position
    private int prePosition = -1;
    private int prePosition2 = -1;
    ////////////
    int CHECK_NUM = 0;

    //
    private SparseBooleanArray selectedhearts = new SparseBooleanArray();

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

    //Recycler의 핵심; ViewHolder
    //subView를 setting
    class ViewHolderCourseList extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_course_name, tv_subject_id, tv_gpa, tv_is_english;
        private LinearLayout tv_item_open;
        private DataCourseList data;
        private int position;
        private ImageButton ib_heart;
        /*ImageView iv_heart;*/
        boolean i = true;

        public ViewHolderCourseList(@NonNull View itemView) {
            super(itemView);

            tv_course_name = itemView.findViewById(R.id.tv_course_name);
            tv_subject_id = itemView.findViewById(R.id.tv_subject_id);
            tv_gpa = itemView.findViewById(R.id.tv_gpa);
            tv_is_english = itemView.findViewById(R.id.tv_is_english);

            tv_item_open = itemView.findViewById(R.id.tv_item_open);
            ib_heart = itemView.findViewById(R.id.ib_heart);
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
            tv_subject_id.setText(data.getSubject_id());
            tv_gpa.setText(data.getGpa());
            tv_is_english.setText(data.getIs_english());

            changeVisibility(selecteditems.get(position));
            /*changeImageView(selectedhearts.get(position));*/

            itemView.setOnClickListener(this);
            tv_course_name.setOnClickListener(this);
            tv_subject_id.setOnClickListener(this);
            ib_heart.setOnClickListener(this);
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
                case R.id.tv_subject_id:
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
                case R.id.tv_course_name:
                    Toast.makeText(context, data.getCourse_name(), Toast.LENGTH_SHORT).show();
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
