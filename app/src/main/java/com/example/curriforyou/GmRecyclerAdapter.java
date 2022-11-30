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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

                    String str_grade = "";

                    switch (v.getId()) {
                        case R.id.btn_grade_confirm:
                            if (rb_A.isChecked() && rb_plus.isChecked()) {
                                str_grade = "A+";
                                rb_grade.setText("A+");
                                grade_dialog.dismiss();
                            } else if (rb_A.isChecked() && rb_zero.isChecked()) {
                                str_grade = "A";
                                rb_grade.setText("A");
                                grade_dialog.dismiss();
                            } else if (rb_A.isChecked() && rb_minus.isChecked()) {
                                str_grade = "A-";
                                rb_grade.setText("A-");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_plus.isChecked()) {
                                str_grade = "B+";
                                rb_grade.setText("B+");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_zero.isChecked()) {
                                str_grade = "B";
                                rb_grade.setText("B");
                                grade_dialog.dismiss();
                            } else if (rb_B.isChecked() && rb_minus.isChecked()) {
                                str_grade = "B-";
                                rb_grade.setText("B-");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_plus.isChecked()) {
                                str_grade = "C+";
                                rb_grade.setText("C+");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_zero.isChecked()) {
                                str_grade = "C";
                                rb_grade.setText("C");
                                grade_dialog.dismiss();
                            } else if (rb_C.isChecked() && rb_minus.isChecked()) {
                                str_grade = "C-";
                                rb_grade.setText("C-");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_plus.isChecked()) {
                                str_grade = "D+";
                                rb_grade.setText("D+");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_zero.isChecked()) {
                                str_grade = "D";
                                rb_grade.setText("D");
                                grade_dialog.dismiss();
                            } else if (rb_D.isChecked() && rb_minus.isChecked()) {
                                str_grade = "D-";
                                rb_grade.setText("D-");
                                grade_dialog.dismiss();
                            } else {
                                str_grade = "F";
                                rb_grade.setText("F");
                                grade_dialog.dismiss();
                            }
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        if(success) {
                                            Toast.makeText(context.getApplicationContext(),"등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context.getApplicationContext(),"등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            };
                            Context mainActivity = MainActivity.context_main;
                            GradeRequest gradeRequest = new GradeRequest(listData.get(position).course_id, str_grade, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(mainActivity);
                            queue.add(gradeRequest);
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
