package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MyPageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ImageView user_photo = (ImageView) findViewById(R.id.user_photo);
        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "프로필 사진 변경", Toast.LENGTH_SHORT).show();
            }
        });

        TextView user_nickname = (TextView) findViewById(R.id.user_nickname);
        TextView user_position = (TextView) findViewById(R.id.user_position);

        ImageView btn_infoModification = (ImageView) findViewById(R.id.btn_infoModification);
        btn_infoModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_infoModification.class);
                startActivity(intent); //액티비티 이동
            }
        });

        Button btn_mainMajor = (Button) findViewById(R.id.btn_mainMajor);
        btn_mainMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
                startActivity(intent); //액티비티 이동
            }
        });

        Button btn_doubleMajor = (Button) findViewById(R.id.btn_doubleMajor);
        btn_doubleMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
                startActivity(intent); //액티비티 이동
            }
        });

        Button btn_minor = (Button) findViewById(R.id.btn_minor);
        btn_minor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
                startActivity(intent); //액티비티 이동
            }
        });

        ImageView btn_addMajor = (ImageView) findViewById(R.id.btn_addMajor);
        btn_addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_addMajor.class);
                startActivity(intent); //액티비티 이동
            }
        });

        TextView logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });

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