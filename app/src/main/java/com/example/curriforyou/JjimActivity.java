package com.example.curriforyou;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class JjimActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView open_checkbox;
    TextView open_filtering;
    boolean open_only = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jjim_list);

        open_checkbox = (ImageView) findViewById(R.id.open_checkbox);
        open_filtering = (TextView) findViewById(R.id.open_filtering);
        LinearLayout naviBtn_curriculum = (LinearLayout) findViewById(R.id.naviBtn_curriculum);
        LinearLayout naviBtn_jjimList = (LinearLayout) findViewById(R.id.naviBtn_jjimList);
        LinearLayout naviBtn_lectureRecommendation = (LinearLayout) findViewById(R.id.naviBtn_lectureRecommendation);
        LinearLayout naviBtn_gradeManagement = (LinearLayout) findViewById(R.id.naviBtn_gradeManagement);
        LinearLayout naviBtn_myPage = (LinearLayout) findViewById(R.id.naviBtn_myPage);

        open_checkbox.setOnClickListener(this);
        open_filtering.setOnClickListener(this);
        naviBtn_curriculum.setOnClickListener(this);
        naviBtn_jjimList.setOnClickListener(this);
        naviBtn_lectureRecommendation.setOnClickListener(this);
        naviBtn_gradeManagement.setOnClickListener(this);
        naviBtn_myPage.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_checkbox:
            case R.id.open_filtering:
                if(open_only){
                    open_checkbox.setImageResource(R.drawable.checkbox_outline);
                    open_checkbox.setColorFilter(R.color.gray_4);
                    open_filtering.setTextColor(R.color.gray_4);
                    open_only = false;
                } else{
                    open_checkbox.setImageResource(R.drawable.checkbox_checked);
                    open_checkbox.setColorFilter(R.color.gray_3);
                    open_filtering.setTextColor(R.color.gray_3);
                    open_only = true;
                }
                break;
            case R.id.naviBtn_curriculum:
                Intent intent_curriculum = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_curriculum);
                break;
            case R.id.naviBtn_jjimList:
                Intent intent_jjimList = new Intent(getApplicationContext(), JjimActivity.class);
                startActivity(intent_jjimList);
                break;
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
