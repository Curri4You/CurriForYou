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

    private ImageView user_photo;
    private TextView user_nickname;
    private TextView user_position;
    private Button btn_infoModification;
    private Button btn_doubleMajor;
    private Button btn_minor;
    private Button btn_addMajor;
    private TextView user_phoneNum;
    private TextView user_email;
    //private EditText et_id;
    private String str;
    private ListView list;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        user_photo = findViewById(R.id.user_photo);
        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "프로필 사진 변경", Toast.LENGTH_SHORT).show();
            }
        });

        user_nickname = findViewById(R.id.user_nickname);
        user_position = findViewById(R.id.user_position);
        //et_id = findViewById(R.id.et_id);

        btn_infoModification = findViewById(R.id.btn_infoModification);
        btn_infoModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPage.this, MyPage_infoModification.class);
                startActivity(intent); //액티비티 이동
            }
        });
        btn_doubleMajor = findViewById(R.id.btn_doubleMajor);
        btn_minor = findViewById(R.id.btn_minor);

    }
}