//마이페이지
//        !!해야할 일!!
//        전공버튼 리스트로 만들기
//        하단네비 마이페이지 버튼 다시 누르면 user정보 사라지는 오류 해결
//        로그아웃 버튼 클릭시 기능구현

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
import java.util.ArrayList;
import java.util.HashMap;

public class MyPageActivity extends AppCompatActivity implements View.OnClickListener {
    final static private String URL = "http://smlee099.dothome.co.kr/my_change_ok.php";
    private ArrayList<HashMap<String, String>> userDBlist = null;
    private TextView tv_user_name, tv_student_id;
    private Button btn_mainMajor, btn_doubleMajor, btn_minor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_student_id = (TextView) findViewById(R.id.tv_student_id);
        btn_mainMajor = (Button) findViewById(R.id.btn_mainMajor);
        btn_doubleMajor = (Button) findViewById(R.id.btn_doubleMajor);
        btn_minor = (Button) findViewById(R.id.btn_minor);

        //[DB] 이름, 학번, 주전공 이름 띄우기
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("user_name");
        String student_id = intent.getStringExtra("student_id");
        String major_name = intent.getStringExtra("major_name");
        tv_user_name.setText(user_name);
        tv_student_id.setText(student_id);
        btn_mainMajor.setText(major_name);

        // 프로필 사진
        ImageView user_photo = (ImageView) findViewById(R.id.user_photo);
        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "프로필 사진 변경", Toast.LENGTH_SHORT).show();
            }
        });

        // 전공수정버튼 (연필)
        ImageView btn_infoModification = (ImageView) findViewById(R.id.btn_infoModification);
        btn_infoModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_infoModification.class);
                startActivity(intent);
            }
        });

        // 주전공버튼
        Button btn_mainMajor = (Button) findViewById(R.id.btn_mainMajor);
        btn_mainMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
                startActivity(intent); //액티비티 이동
            }
        });

        // 복수전공버튼
        Button btn_doubleMajor = (Button) findViewById(R.id.btn_doubleMajor);
        btn_doubleMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
                startActivity(intent); //액티비티 이동
            }
        });

        // 부전공버튼
        Button btn_minor = (Button) findViewById(R.id.btn_minor);
        btn_minor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
                startActivity(intent);
            }
        });
        // 전공추가 버튼
        ImageView btn_addMajor = (ImageView) findViewById(R.id.btn_addMajor);
        btn_addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_addMajor.class);
                startActivity(intent);
            }
        });
        // 로그아웃 버튼
        TextView logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent); //액티비티 이동만 구현해둔 상태
            }
        });

        //[하단네비] 버튼 정의
        LinearLayout naviBtn_curriculum = (LinearLayout) findViewById(R.id.naviBtn_curriculum);
        LinearLayout naviBtn_jjimList = (LinearLayout) findViewById(R.id.naviBtn_jjimList);
        LinearLayout naviBtn_lectureRecommendation = (LinearLayout) findViewById(R.id.naviBtn_lectureRecommendation);
        LinearLayout naviBtn_gradeManagement = (LinearLayout) findViewById(R.id.naviBtn_gradeManagement);
        LinearLayout naviBtn_myPage = (LinearLayout) findViewById(R.id.naviBtn_myPage);
        //[하단네비]
        naviBtn_curriculum.setOnClickListener(this);
        naviBtn_jjimList.setOnClickListener(this);
        naviBtn_lectureRecommendation.setOnClickListener(this);
        naviBtn_gradeManagement.setOnClickListener(this);
        naviBtn_myPage.setOnClickListener(this);

    }

    //[하단네비] 클릭시 Activity 이동
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