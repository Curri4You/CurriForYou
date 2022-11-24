// 학점관리 학기별 요약을 위한 RecyclerAdapter & ViewHolder

package com.example.curriforyou;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public GmRecyclerAdapter(Activity activity){
        this. activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_gmcourse, parent, false);
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

        ImageView major_division;
        TextView tv_course_id, tv_course_name, tv_credit;
        RadioButton rb_grade;
        RecyclerView rc_gm_course;
        private DataGmList data;
        private int position;
        //[Dialog] rb_grade 버튼 누르면 뜨는 dialog(팝업창)
        Dialog grade_dialog;

        public GmViewHolder(@NonNull View itemView) {
            super(itemView);
            major_division = itemView.findViewById(R.id.major_division);
            tv_course_id = itemView.findViewById(R.id.tv_course_id);
            tv_course_name = itemView.findViewById(R.id.tv_course_name);
            tv_credit = itemView.findViewById(R.id.tv_credit);
            rb_grade = itemView.findViewById(R.id.rb_grade);

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

            tv_course_id.setText(data.getCourse_id());
            tv_course_name.setText(data.getCourse_name());
            tv_credit.setText(data.getCredit());
            rb_grade.setText(data.getGrade());

            rb_grade.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rb_grade:
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
                                rb_grade.setText("A+");
                                grade_dialog.dismiss();
                            } else if (rb_A.isChecked() && rb_zero.isChecked()) {
                                rb_grade.setText("A");
                                grade_dialog.dismiss();
                            } else if (rb_A.isChecked() && rb_minus.isChecked()) {
                                rb_grade.setText("A-");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_plus.isChecked()) {
                                rb_grade.setText("B+");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_zero.isChecked()) {
                                rb_grade.setText("B");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_minus.isChecked()) {
                                rb_grade.setText("B-");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_plus.isChecked()) {
                                rb_grade.setText("C+");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_zero.isChecked()) {
                                rb_grade.setText("C");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_minus.isChecked()) {
                                rb_grade.setText("C-");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_plus.isChecked()) {
                                rb_grade.setText("D+");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_zero.isChecked()) {
                                rb_grade.setText("D");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_minus.isChecked()) {
                                rb_grade.setText("D-");
                                grade_dialog.dismiss();
                            } else {
                                rb_grade.setText("F");
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

    }

}
