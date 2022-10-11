package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage_majorModification extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_major_modification);
        Button btn_choice_mainMajor = (Button) findViewById(R.id.btn_choice_mainMajor);
        Button btn_choice_doubleMajor = (Button) findViewById(R.id.btn_choice_doubleMajor);
        Button btn_choice_minor = (Button) findViewById(R.id.btn_choice_minor);

        btn_choice_mainMajor.setOnClickListener(this);
        btn_choice_doubleMajor.setOnClickListener(this);
        btn_choice_minor.setOnClickListener(this);

        ImageView xButton = (ImageView) findViewById(R.id.xButton);
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choice_mainMajor:
                Intent intent_curriculum = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent_curriculum);
                break;
            case R.id.btn_choice_doubleMajor:
                Intent intent_lectureRecommendation = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent_lectureRecommendation);
                break;
            case R.id.btn_choice_minor:
                Intent intent_gradeManagement = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent_gradeManagement);
                break;
        }
    }
}