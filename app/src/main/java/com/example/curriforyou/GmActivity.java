package com.example.curriforyou;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class GmActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ll_listsemester;
    TextView tv_semester;
    AlertDialog.Builder builder;
    String[] semester;
    //Adapter 연결
    GmRecyclerAdapter adapter;
    // [RecyclerView] 리스트 출력을 위한 parameter
    private static final String TAG = "imagesearchexample";
    // URL - 학기별 학점요약 DB
    private  String REQUEST_URL = "http://smlee099.dothome.co.kr/practice_avg_grade.php";
    public static final int LOAD_SUCCESS = 101;
    private ProgressDialog progressDialog = null;
    GmRecyclerAdapter rc_adapter = null;
    private ArrayList<HashMap<String, String>> gmSemesterList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gm_home);

        init();
        getData(REQUEST_URL);

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

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.rc_gm_course);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Context context;
        adapter = new GmRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void getData(String Request_url){

        DataGmList data = new DataGmList("1학년 1학기", "3.77", "3.75", "3.80",
                "10023", "기독교와세계", "3", "A+",
                "10098", "대학영어", "3", "B+",
                "11208", "IT융합설계개론", "3", "A",
                "38403", "Python프로그래밍및실습", "3", "A-",
                "20409", "일반물리학1", "3", "B+",
                "20406", "미분적분학", "3", "A");
        adapter.addItem(data);
        data = new DataGmList("1학년 2학기", "3.77", "3.33", "4.20",
                "11095", "나눔리더십", "3", "A+",
                "10021", "우리말과글쓰기", "3", "A+",
                "10099", "고급영어", "3", "A",
                "20441", "선형대수학1", "3", "A-",
                "34980", "확률및통계학", "3", "B+",
                "20415", "일반화학", "3", "B");
        adapter.addItem(data);
        data = new DataGmList("2학년 1학기", "3.53", "3.77", "3.30",
                "11095", "나눔리더십", "3", "A+",
                "10021", "우리말과글쓰기", "3", "A+",
                "10099", "고급영어", "3", "A",
                "20441", "선형대수학1", "3", "A-",
                "34980", "확률및통계학", "3", "B+",
                "20415", "일반화학", "3", "B");
        adapter.addItem(data);

        RecyclerView recyclerView = findViewById(R.id.rc_gm_course);

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
