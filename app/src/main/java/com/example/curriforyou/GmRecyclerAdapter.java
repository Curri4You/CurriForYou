// 학점관리 학기별 요약을 위한 RecyclerAdapter & ViewHolder

package com.example.curriforyou;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GmRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // adapter에 들어갈 list
    private ArrayList<DataGmSemester> listData = new ArrayList<>();
    private Context context;
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_semester, parent, false);
        return new GmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((GmViewHolder) holder).onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        //RecyclerView의 총 개수
        return listData.size();
    }

    void addItem(DataGmSemester data) {
        // 외부에서 item을 추가시킬 함수
        listData.add(data);
    }

    class GmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView gm_list_semester, gm_list_totalGrade, gm_list_majorGrade, gm_list_liberalGrade;
        private LinearLayout open_gm_semester, gm_semester_subject;
        private ImageView open_gm_arrow;
        private DataGmSemester data;
        private int position;

        public GmViewHolder(@NonNull View itemView) {
            super(itemView);
            gm_list_semester = itemView.findViewById(R.id.gm_list_semester);
            gm_list_totalGrade = itemView.findViewById(R.id.gm_list_totalGrade);
            gm_list_majorGrade = itemView.findViewById(R.id.gm_list_majorGrade);
            gm_list_liberalGrade = itemView.findViewById(R.id.gm_list_liberalGrade);
            open_gm_semester = itemView.findViewById(R.id.open_gm_semester);
            gm_semester_subject = itemView.findViewById(R.id.gm_semester_subject);
            open_gm_arrow = itemView.findViewById(R.id.open_gm_arrow);
        }

        //데이터 이용해서 text 변경
        void onBind(DataGmSemester data, int position) {
            this.data = data;
            this.position = position;

            gm_list_semester.setText(data.getSemester());
            gm_list_totalGrade.setText(data.getTotalGrade());
            gm_list_majorGrade.setText(data.getMajorGrade());
            gm_list_liberalGrade.setText(data.getLiberalGrade());

            changeVisibility(selectedItems.get(position));

            open_gm_semester.setOnClickListener(this);
            open_gm_arrow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.open_gm_semester:
                case R.id.open_gm_arrow:
//                    open_gm_arrow.setImageResource(R.drawable.arrow_up);
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
//                        open_gm_arrow.setImageResource(R.drawable.arrow_down);
                    } else {
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1)
                        notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
            }
        }

        //         클릭된 Item의 상태 변경
//         @param isExpanded Item을 펼칠 것인지 여부
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

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
                    gm_semester_subject.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }
}
