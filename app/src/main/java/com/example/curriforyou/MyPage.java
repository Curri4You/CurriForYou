package com.example.curriforyou;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage extends AppCompatActivity {

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
        Button btn_addMajor = (Button) findViewById(R.id.btn_addMajor);
        Button btn_doubleMajor = (Button) findViewById(R.id.btn_doubleMajor);
        Button btn_minor = (Button) findViewById(R.id.btn_minor);

        ImageView btn_infoModification = (ImageView) findViewById(R.id.btn_infoModification);
        btn_infoModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_myPage_infoModification = new Intent(getApplicationContext(), MyPage_infoModification.class);
                startActivity(intent_myPage_infoModification); //액티비티 이동
            }
        });

    }
}