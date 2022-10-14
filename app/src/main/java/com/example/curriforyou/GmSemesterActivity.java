// 학점관리 학기별 요약 페이지

package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GmSemesterActivity extends AppCompatActivity {

    //Adapter 연결
    GmRecyclerVierAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gm_semester);
        init();
        getData();

        //뒤로가기 버튼
        ImageView btn_infoModification = (ImageView) findViewById(R.id.navigate_before);
        btn_infoModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GmActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });
    }

    private void init(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new GmRecyclerVierAdapter();
        recyclerView.setAdapter(adapter);
    }

    //데이터 넣어주기
    private void getData(){
        //semester(학기), totalGrade(총 학점), majorGrade(전공학점), liberalGrade(교양학점)
        DataGmSemester data = new DataGmSemester("1학년 1학기", "3.25", "3.15", "3.38");
        adapter.addItem(data);
        data = new DataGmSemester("1학년 2학기", "3.53", "3.77", "3.30");
        adapter.addItem(data);
        data = new DataGmSemester("2학년 1학기", "3.48", "3.48", "3.48");
        adapter.addItem(data);
        data = new DataGmSemester("2학년 2학기", "3.39", "3.28", "4.00");
        adapter.addItem(data);
    }

}
