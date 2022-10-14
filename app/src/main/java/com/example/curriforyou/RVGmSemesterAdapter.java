package com.example.curriforyou;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RVGmSemesterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // adapter에 들어갈 list
    private ArrayList<DataGmList> listData = new ArrayList<>();
    // adapter에 들어갈 activity 정보
    Activity activity;

    public RVGmSemesterAdapter(Activity activity){
        //Adapter를 만들 때 해당 Activity 정보 불러오기
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item2, parent, false);
        return new ViewHolderGmList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderGmList)holder).onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(DataGmList data){
        listData.add(data);
    }


    // ViewHolder
    class ViewHolderGmList extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_syllabus_id, tv_course_name2, tv_credit;
        RadioButton rb_grade;

        //[Dialog] rb_grade 버튼 누르면 뜨는 dialog(팝업창)
        Dialog grade_dialog;

        public ViewHolderGmList(@NonNull View itemView) {
            super(itemView);

            tv_syllabus_id = itemView.findViewById(R.id.tv_syllabus_id);
            tv_course_name2 = itemView.findViewById(R.id.tv_course_name2);
            tv_credit = itemView.findViewById(R.id.tv_credit);
            rb_grade = itemView.findViewById(R.id.rb_grade);

            //[Dialog] GmActivity에서 초기화
            grade_dialog = new Dialog(activity);
            //[Dialog] 팝업 화면의 기본 Title 없애기
            grade_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //[Dialog] 사용자가 만든 layout으로 Custom 팝업창 설정
            grade_dialog.setContentView(R.layout.rb_grade);

        }

        public void onBind(DataGmList data){

            tv_syllabus_id.setText(data.getSyllabus_id());
            tv_course_name2.setText(data.getCourse_name());
            tv_credit.setText(data.getCredit());

            rb_grade.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // [listview_item2.xml] rb_grade 버튼 클릭 시 함수 호출
                case R.id.rb_grade:
                    showGradeDialog();
            }
        }

        public void showGradeDialog(){
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
                    switch (v.getId()){
                        case R.id.btn_grade_confirm:
                            if(rb_A.isChecked() && rb_plus.isChecked()){
                                rb_grade.setText("A+");
                                grade_dialog.dismiss();
                            } else if (rb_A.isChecked() && rb_zero.isChecked()){
                                rb_grade.setText("A0");
                                grade_dialog.dismiss();
                            } else if (rb_A.isChecked() && rb_minus.isChecked()){
                                rb_grade.setText("A-");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_plus.isChecked()){
                                rb_grade.setText("B+");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_zero.isChecked()){
                                rb_grade.setText("B0");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_minus.isChecked()){
                                rb_grade.setText("B-");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_plus.isChecked()){
                                rb_grade.setText("C+");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_zero.isChecked()){
                                rb_grade.setText("C0");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_minus.isChecked()){
                                rb_grade.setText("C-");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_plus.isChecked()){
                                rb_grade.setText("D+");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_zero.isChecked()){
                                rb_grade.setText("D0");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_minus.isChecked()){
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
