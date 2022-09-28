package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage_addMajor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_add_major);
        ImageView btn_infoModification = (ImageView) findViewById(R.id.navigate_before);
        btn_infoModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });

    }

}
