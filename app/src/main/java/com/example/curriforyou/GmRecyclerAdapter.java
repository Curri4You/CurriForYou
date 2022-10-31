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

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;

public class GmRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // adapter에 들어갈 list
    private ArrayList<DataGmList> listData = new ArrayList<>();
    private Context context;
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    // adapter에 들어갈 activity 정보
    Activity activity;

    public GmRecyclerAdapter(Activity activity) {
        //Adapter를 만들 때 해당 Activity 정보 불러오기
        this.activity = activity;
    }

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

    void addItem(DataGmList data) {
        // 외부에서 item을 추가시킬 함수
        listData.add(data);
    }

    class GmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView gm_list_semester, gm_list_totalGrade, gm_list_majorGrade, gm_list_liberalGrade;
        TextView tv_syllabus_id1, tv_course_name1, tv_credit1;
        TextView tv_syllabus_id2, tv_course_name2, tv_credit2;
        TextView tv_syllabus_id3, tv_course_name3, tv_credit3;
        TextView tv_syllabus_id4, tv_course_name4, tv_credit4;
        TextView tv_syllabus_id5, tv_course_name5, tv_credit5;
        TextView tv_syllabus_id6, tv_course_name6, tv_credit6;
        RadioButton rb_grade1, rb_grade2, rb_grade3, rb_grade4, rb_grade5, rb_grade6;
        private LinearLayout open_gm_semester, gm_semester_subject1, gm_semester_subject2,
        gm_semester_subject3, gm_semester_subject4, gm_semester_subject5, gm_semester_subject6;
        private ImageView open_gm_arrow;
        private DataGmList data;
        private int position;
        //[Dialog] rb_grade 버튼 누르면 뜨는 dialog(팝업창)
        Dialog grade_dialog;

        public GmViewHolder(@NonNull View itemView) {
            super(itemView);
            gm_list_semester = itemView.findViewById(R.id.gm_list_semester);
            gm_list_totalGrade = itemView.findViewById(R.id.gm_list_totalGrade);
            gm_list_majorGrade = itemView.findViewById(R.id.gm_list_majorGrade);
            gm_list_liberalGrade = itemView.findViewById(R.id.gm_list_liberalGrade);
            open_gm_semester = itemView.findViewById(R.id.open_gm_semester);
            open_gm_arrow = itemView.findViewById(R.id.open_gm_arrow);
            gm_semester_subject1 = itemView.findViewById(R.id.gm_semester_subject1);
            tv_syllabus_id1 = itemView.findViewById(R.id.tv_syllabus_id1);
            tv_course_name1 = itemView.findViewById(R.id.tv_course_name1);
            tv_credit1 = itemView.findViewById(R.id.tv_credit1);
            rb_grade1 = itemView.findViewById(R.id.rb_grade1);
            gm_semester_subject2 = itemView.findViewById(R.id.gm_semester_subject2);
            tv_syllabus_id2 = itemView.findViewById(R.id.tv_syllabus_id2);
            tv_course_name2 = itemView.findViewById(R.id.tv_course_name2);
            tv_credit2 = itemView.findViewById(R.id.tv_credit2);
            rb_grade2 = itemView.findViewById(R.id.rb_grade2);
            gm_semester_subject3 = itemView.findViewById(R.id.gm_semester_subject3);
            tv_syllabus_id3 = itemView.findViewById(R.id.tv_syllabus_id3);
            tv_course_name3 = itemView.findViewById(R.id.tv_course_name3);
            tv_credit3 = itemView.findViewById(R.id.tv_credit3);
            rb_grade3 = itemView.findViewById(R.id.rb_grade3);
            gm_semester_subject4 = itemView.findViewById(R.id.gm_semester_subject4);
            tv_syllabus_id4 = itemView.findViewById(R.id.tv_syllabus_id4);
            tv_course_name4 = itemView.findViewById(R.id.tv_course_name4);
            tv_credit4 = itemView.findViewById(R.id.tv_credit4);
            rb_grade4 = itemView.findViewById(R.id.rb_grade4);
            gm_semester_subject5 = itemView.findViewById(R.id.gm_semester_subject5);
            tv_syllabus_id5 = itemView.findViewById(R.id.tv_syllabus_id5);
            tv_course_name5 = itemView.findViewById(R.id.tv_course_name5);
            tv_credit5 = itemView.findViewById(R.id.tv_credit5);
            rb_grade5 = itemView.findViewById(R.id.rb_grade5);
            gm_semester_subject6 = itemView.findViewById(R.id.gm_semester_subject6);
            tv_syllabus_id6 = itemView.findViewById(R.id.tv_syllabus_id6);
            tv_course_name6 = itemView.findViewById(R.id.tv_course_name6);
            tv_credit6 = itemView.findViewById(R.id.tv_credit6);
            rb_grade6 = itemView.findViewById(R.id.rb_grade6);

            //[Dialog] GmActivity에서 초기화
            grade_dialog = new Dialog(activity);
            //[Dialog] 팝업 화면의 기본 Title 없애기
            grade_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //[Dialog] 사용자가 만든 layout으로 Custom 팝업창 설정
            grade_dialog.setContentView(R.layout.rb_grade);
        }

        //데이터 이용해서 text 변경
        void onBind(DataGmList data, int position) {
            this.data = data;
            this.position = position;

            gm_list_semester.setText(data.getSemester());
            gm_list_totalGrade.setText(data.getTotalGrade());
            gm_list_majorGrade.setText(data.getMajorGrade());
            gm_list_liberalGrade.setText(data.getLiberalGrade());
            tv_syllabus_id1.setText(data.getSyllabus_id1());
            tv_course_name1.setText(data.getCourse_name1());
            tv_credit1.setText(data.getCredit1());
            rb_grade1.setText(data.getGrade1());
            tv_syllabus_id2.setText(data.getSyllabus_id2());
            tv_course_name2.setText(data.getCourse_name2());
            tv_credit2.setText(data.getCredit2());
            rb_grade2.setText(data.getGrade2());
            tv_syllabus_id3.setText(data.getSyllabus_id3());
            tv_course_name3.setText(data.getCourse_name3());
            tv_credit3.setText(data.getCredit3());
            rb_grade3.setText(data.getGrade3());
            tv_syllabus_id4.setText(data.getSyllabus_id4());
            tv_course_name4.setText(data.getCourse_name4());
            tv_credit4.setText(data.getCredit4());
            rb_grade4.setText(data.getGrade4());
            tv_syllabus_id5.setText(data.getSyllabus_id5());
            tv_course_name5.setText(data.getCourse_name5());
            tv_credit5.setText(data.getCredit5());
            rb_grade5.setText(data.getGrade5());
            tv_syllabus_id6.setText(data.getSyllabus_id6());
            tv_course_name6.setText(data.getCourse_name6());
            tv_credit6.setText(data.getCredit6());
            rb_grade6.setText(data.getGrade6());

            changeVisibility(selectedItems.get(position));

            open_gm_semester.setOnClickListener(this);
            open_gm_arrow.setOnClickListener(this);
            rb_grade1.setOnClickListener(this);
            rb_grade2.setOnClickListener(this);
            rb_grade3.setOnClickListener(this);
            rb_grade4.setOnClickListener(this);
            rb_grade5.setOnClickListener(this);
            rb_grade6.setOnClickListener(this);
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
                case R.id.rb_grade1:
                    showGradeDialog();
            }
        }

        public void showGradeDialog() {
            grade_dialog.show();
            // 확인 버튼
            Button btn_grade_confirm = grade_dialog.findViewById(R.id.btn_grade_confirm);
            // [Grade] A, B, C, D, E 버튼
            RadioButton rb_A = grade_dialog.findViewById(R.id.rb_A);
            RadioButton rb_B = grade_dialog.findViewById(R.id.rb_B);
            RadioButton rb_C = grade_dialog.findViewById(R.id.rb_C);
            RadioButton rb_D = grade_dialog.findViewById(R.id.rb_D);
            RadioButton rb_F = grade_dialog.findViewById(R.id.rb_F);
            // [Grade] +, 0, - 버튼
            RadioButton rb_plus = grade_dialog.findViewById(R.id.rb_plus);
            RadioButton rb_zero = grade_dialog.findViewById(R.id.rb_zero);
            RadioButton rb_minus = grade_dialog.findViewById(R.id.rb_minus);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btn_grade_confirm:
                            if (rb_A.isChecked() && rb_plus.isChecked()) {
                                rb_grade1.setText("A+");
                                grade_dialog.dismiss();
                            } else if (rb_A.isChecked() && rb_zero.isChecked()) {
                                rb_grade1.setText("A");
                                grade_dialog.dismiss();
                            } else if (rb_A.isChecked() && rb_minus.isChecked()) {
                                rb_grade1.setText("A-");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_plus.isChecked()) {
                                rb_grade1.setText("B+");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_zero.isChecked()) {
                                rb_grade1.setText("B");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_minus.isChecked()) {
                                rb_grade1.setText("B-");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_plus.isChecked()) {
                                rb_grade1.setText("C+");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_zero.isChecked()) {
                                rb_grade1.setText("C");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_minus.isChecked()) {
                                rb_grade1.setText("C-");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_plus.isChecked()) {
                                rb_grade1.setText("D+");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_zero.isChecked()) {
                                rb_grade1.setText("D");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_minus.isChecked()) {
                                rb_grade1.setText("D-");
                                grade_dialog.dismiss();
                            } else {
                                rb_grade1.setText("F");
                                grade_dialog.dismiss();
                            }
                            break;
                    }
                }
            };
            // 확인 버튼 클릭 리스터 추가
            btn_grade_confirm.setOnClickListener(onClickListener);
            // [Grade] A, B, C, D, E 버튼 클릭 리스터 추가
            rb_A.setOnClickListener(onClickListener);
            rb_B.setOnClickListener(onClickListener);
            rb_C.setOnClickListener(onClickListener);
            rb_D.setOnClickListener(onClickListener);
            rb_F.setOnClickListener(onClickListener);
            // [Grade] +, 0, - 버튼 클릭 리스너 추가
            rb_plus.setOnClickListener(onClickListener);
            rb_zero.setOnClickListener(onClickListener);
            rb_minus.setOnClickListener(onClickListener);
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
                    gm_semester_subject1.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    gm_semester_subject2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    gm_semester_subject3.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    gm_semester_subject4.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    gm_semester_subject5.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    gm_semester_subject6.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }

}
