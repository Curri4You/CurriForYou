package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecomActivity extends AppCompatActivity implements View.OnClickListener {

    RcRecylerAdapter rc_adapter, rc_adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom);

        init();
        getData();

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
        RecyclerView recyclerView = findViewById(R.id.rc_recom1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        rc_adapter = new RcRecylerAdapter();
        recyclerView.setAdapter(rc_adapter);

        /**/
        RecyclerView recyclerView2 = findViewById(R.id.rc_recom2);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        rc_adapter2 = new RcRecylerAdapter();
        recyclerView2.setAdapter(rc_adapter2);
    }

    private void getData(){
        DataRecomList data = new DataRecomList("전공명1", "1", "개설전공", "선수과목1", "선수과목2");
        rc_adapter.addItem(data);
        data = new DataRecomList("전공명1", "1", "개설전공", "선수과목1", "선수과목2");
        rc_adapter.addItem(data);
        data = new DataRecomList("전공명2", "1", "개설전공", "선수과목1", "선수과목2");
        rc_adapter.addItem(data);
        data = new DataRecomList("전공명3", "1", "개설전공", "선수과목1", "선수과목2");
        rc_adapter.addItem(data);
        data = new DataRecomList("전공명4", "1", "개설전공", "선수과목1", "선수과목2");
        rc_adapter.addItem(data);

        DataRecomList data2 = new DataRecomList("전공명1", "1", "개설전공", "선수과목1", "선수과목2");
        rc_adapter2.addItem(data2);
        data2 = new DataRecomList("전공명1", "1", "개설전공", "선수과목1", "선수과목2");
        rc_adapter2.addItem(data2);
        data2 = new DataRecomList("전공명2", "1", "개설전공", "선수과목1", "선수과목2");
        rc_adapter2.addItem(data2);
        data2 = new DataRecomList("전공명3", "1", "개설전공", "선수과목1", "선수과목2");
        rc_adapter2.addItem(data2);
        data2 = new DataRecomList("전공명4", "1", "개설전공", "선수과목1", "선수과목2");
        rc_adapter2.addItem(data2);

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
