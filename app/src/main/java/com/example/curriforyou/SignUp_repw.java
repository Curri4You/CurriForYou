package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp_repw extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_repw);

        //SIGNUP_REPW PAGE 의 BEFORE BUTTON 클릭 --> SIGNUP_PW PAGE 로 이동
        Button btn_before = (Button)findViewById(R.id.btn_signuprepw_before);
        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp_pw.class);
                startActivity(intent);
            }
        });

        //SIGNUP_ID PAGE 의 NEXT BUTTON 클릭 --> SIGNUP_PW PAGE 로 이동
        Button btn_next = (Button)findViewById(R.id.btn_signuprepw_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp_number.class);
                startActivity(intent);
            }
        });

    }
}
