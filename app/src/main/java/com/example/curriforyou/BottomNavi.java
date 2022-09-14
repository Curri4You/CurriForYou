package com.example.curriforyou;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavi extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Navi_Curriculum naviCurriculum;
    // 추후에 찜리스트, 강의추천, 학점관리 추가
    // private MyPage myPage;
    private frag1 frag1;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navi_curriculum:
                        setFrag(0);
                        break;
                    case R.id.navi_jjim_list:
                        setFrag(1);
                        break;
                    case R.id.navi_lecture_recommendation:
                        setFrag(2);
                        break;
                    case R.id.navi_grade_management:
                        setFrag(3);
                        break;
                    case R.id.navi_mypage:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });
        naviCurriculum = new Navi_Curriculum();
        // 추후에 찜리스트, 강의추천, 학점관리 추가
        //myPage = new MyPage();
        frag1 = new frag1();
        setFrag(0); // 첫 fragment 화면 curriculum으로 설정

    }

    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n){
            case 0:
                ft.replace(R.id.main_frame, naviCurriculum);
                ft.commit();
                break;
            /*case 1:
                ft.replace(R.id.main_frame, jjim_list);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, lecture_recommendation);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, grade_management);
                ft.commit();
                break;*/
            case 4:
                ft.replace(R.id.main_frame, frag1);
                ft.commit();
                break;
        }
    }

}
