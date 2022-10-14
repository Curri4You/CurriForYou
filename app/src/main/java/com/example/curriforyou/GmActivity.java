//학점관리 메인페이지

package com.example.curriforyou;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GmActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ll_listsemester;
    TextView tv_semester;
    AlertDialog.Builder builder;
    String[] semester;

    //
    RVGmSemesterAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gm_home);

        //
        init();
        getData();



        //[Dialog]
        tv_semester = findViewById(R.id.tv_semester);
        ll_listsemester = findViewById(R.id.ll_listsemester);
        ll_listsemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GmShowDialog();
            }
        });

        //gm_semester 페이지로 이동하는 버튼
        ImageView goto_gm_semester = (ImageView) findViewById(R.id.goto_gm_semester);
        goto_gm_semester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GmSemesterActivity.class);
                startActivity(intent);
            }
        });

        //[하단바] Button parameter 선언
        LinearLayout naviBtn_curriculum = (LinearLayout) findViewById(R.id.naviBtn_curriculum);
        LinearLayout naviBtn_jjimList = (LinearLayout) findViewById(R.id.naviBtn_jjimList);
        LinearLayout naviBtn_lectureRecommendation = (LinearLayout) findViewById(R.id.naviBtn_lectureRecommendation);
        LinearLayout naviBtn_gradeManagement = (LinearLayout) findViewById(R.id.naviBtn_gradeManagement);
        LinearLayout naviBtn_myPage = (LinearLayout) findViewById(R.id.naviBtn_myPage);

        naviBtn_curriculum.setOnClickListener(this);
        naviBtn_jjimList.setOnClickListener(this);
        naviBtn_lectureRecommendation.setOnClickListener(this);
        naviBtn_gradeManagement.setOnClickListener(this);
        naviBtn_myPage.setOnClickListener(this);
    }

    //////
    private void init(){
        RecyclerView recyclerView = findViewById(R.id.rc_gm_course);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Context context;
        adapter = new RVGmSemesterAdapter(GmActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        DataGmList data = new DataGmList("syllabus_id1", "강좌명1", "2", "1.0");
        adapter.addItem(data);
        data = new DataGmList("syllabus_id2", "강좌명2", "2", "1.0");
        adapter.addItem(data);
        data = new DataGmList("syllabus_id3", "강좌명3", "2", "1.0");
        adapter.addItem(data);
        data = new DataGmList("syllabus_id4", "강좌명4", "2", "1.0");
        adapter.addItem(data);
        data = new DataGmList("syllabus_id5", "강좌명5", "2", "1.0");
        adapter.addItem(data);
        data = new DataGmList("syllabus_id5", "강좌명5", "2", "1.0");
        adapter.addItem(data);
        data = new DataGmList("syllabus_id5", "강좌명5", "2", "1.0");
        adapter.addItem(data);
        data = new DataGmList("syllabus_id5", "강좌명5", "2", "1.0");
        adapter.addItem(data);
        data = new DataGmList("syllabus_id5", "강좌명5", "2", "1.0");
        adapter.addItem(data);

        //
        RecyclerView recyclerView = findViewById(R.id.rc_gm_course);

    }

    //[Dialog] 팝업창 띄우고 선택 시 OnClick 함수 적용
    public void GmShowDialog(){
        semester = getResources().getStringArray(R.array.semester);
        builder = new AlertDialog.Builder(GmActivity.this);
        builder.setTitle("Select Your Course Category");    //제목

        builder.setItems(semester, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_semester.setText(semester[which]);
                //선택된 카테고리에 따라 다른 URL에서 파싱
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.naviBtn_curriculum:
                Intent intent_curriculum = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_curriculum);
                break;
            /*case R.id.naviBtn_jjimList:
                Intent intent_jjimList = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_jjimList);
                break;*/
            case R.id.naviBtn_lectureRecommendation:
                Intent intent_lectureRecommendation = new Intent(getApplicationContext(), RecomActivity.class);
                startActivity(intent_lectureRecommendation);
                break;
            case R.id.naviBtn_gradeManagement:
                Intent intent_gradeManagement = new Intent(getApplicationContext(), GmActivity.class);
                startActivity(intent_gradeManagement);
                break;
            case R.id.naviBtn_myPage:
                Intent intent_myPage = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent_myPage);
                break;
        }
    }
}
