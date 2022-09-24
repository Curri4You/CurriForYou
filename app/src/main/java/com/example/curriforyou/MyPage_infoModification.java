package com.example.curriforyou;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage_infoModification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_info_modification);
        TextView tv_infoModification = (TextView) findViewById(R.id.tv_infoModification);
    }
}