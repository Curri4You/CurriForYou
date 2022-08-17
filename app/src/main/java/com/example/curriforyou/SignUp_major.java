package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp_major extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_major);

        //SIGNUP_REPW PAGE 의 BEFORE BUTTON 클릭 --> SIGNUP_PW PAGE 로 이동
        Button btn_before = (Button)findViewById(R.id.btn_signupmajor_before);
        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp_sch.class);
                startActivity(intent);
            }
        });

    }
}
