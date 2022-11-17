package com.example.curriforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //sidebar를 출력하기 위한 DrawerLayout
    private DrawerLayout drawerLayout;
    private View drawerView;

    //btn_list 클릭 시 사용될 큐
    static RequestQueue requestQueue;

    //[RecyclerView] 리스트 출력을 위한 parameter
    private static final String TAG = "imagesearchexample";
    //URL1 - 전공필수 카테고리 CourseList
    private  String REQUEST_URL1 = "http://smlee099.dothome.co.kr/CourseListTest.php";
    //URL2 - 전공선택 카테고리 CourseList
    private  String REQUEST_URL2 = "http://smlee099.dothome.co.kr/CourseListTest2.php";
    //URL3 - 교양필수 카테고리 CourseList
    private  String REQUEST_URL3 = "http://smlee099.dothome.co.kr/CourseListTest3.php";
    //URL4 - 교양선택 카테고리 CourseList
    private  String REQUEST_URL4 = "http://smlee099.dothome.co.kr/CourseListTest4.php";
    public static final int LOAD_SUCCESS = 101;
    private ProgressDialog progressDialog = null;
    RecyclerVierAdapter rc_adapter = null;
    private ArrayList<HashMap<String, String>> courseList = null;

    //[Dialog]
    TextView tv_dialog;
    AlertDialog.Builder builder;
    String[] course_category;

    //[Search]
    ArrayList<DataCourseList> original_list = new ArrayList<>();
    ArrayList<DataCourseList> search_list = new ArrayList<>();
    EditText et_search;

    //[Filter]
    ArrayList<DataCourseList> filter_list = new ArrayList<>();
    ArrayList<DataCourseList> filter_list2 = new ArrayList<>();
    ArrayList<DataCourseList> filter_list3 = new ArrayList<>();
    ArrayList<DataCourseList> filter_list4 = new ArrayList<>();
    Button btn_filter;

    //[Detail Category]
    /*private LinearLayout ll_detail_category1;
    private LinearLayout ll_detail_category2;
    private LinearLayout ll_detail_category3;
    private LinearLayout ll_detail_category4;*/

    //////////testing
    public static Context context_main;
    public int var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);

        //////////////
        context_main = this;

        ///////////
        /*for(int a=0; a < original_list.size(); a++){
            if(original_list.get(a).course_name.toLowerCase().contains(search_text.toLowerCase())){
                search_list.add(original_list.get(a));
            }
            rc_adapter.setItems(search_list);
        }*/


        //[Detail Category]
        LinearLayout ll_detail_category[] = new LinearLayout[12];
        for (int i = 0; i < 12; i ++){
            int res_id2 = getResources().getIdentifier("ll_detail_category"+i, "id", getPackageName());
            ll_detail_category[i] = findViewById(res_id2);
        }

        LayoutInflater detail_inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater.inflate(R.layout.detail_category_layout, ll_detail_category[0], true);
        LayoutInflater detail_inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater2.inflate(R.layout.detail_category_layout, ll_detail_category[1], true);
        LayoutInflater detail_inflater3 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater3.inflate(R.layout.detail_category_layout, ll_detail_category[2], true);
        LayoutInflater detail_inflater4 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater4.inflate(R.layout.detail_category_layout, ll_detail_category[3], true);
        LayoutInflater detail_inflater5 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater5.inflate(R.layout.detail_category_layout, ll_detail_category[4], true);
        LayoutInflater detail_inflater6 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater6.inflate(R.layout.detail_category_layout, ll_detail_category[5], true);
        LayoutInflater detail_inflater7 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater7.inflate(R.layout.detail_category_layout, ll_detail_category[6], true);
        LayoutInflater detail_inflater8 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater8.inflate(R.layout.detail_category_layout, ll_detail_category[7], true);
        LayoutInflater detail_inflater9 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater9.inflate(R.layout.detail_category_layout, ll_detail_category[8], true);
        LayoutInflater detail_inflater10 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater10.inflate(R.layout.detail_category_layout, ll_detail_category[9], true);
        LayoutInflater detail_inflater11 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater11.inflate(R.layout.detail_category_layout, ll_detail_category[10], true);
        LayoutInflater detail_inflater12 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater12.inflate(R.layout.detail_category_layout, ll_detail_category[11], true);

        TextView tv_detail_category[] = new TextView[12];
        TextView tv_detail_standard[] = new TextView[12];
        TextView tv_detail_major[] = new TextView[12];
        for (int i = 0; i < 12; i ++){
            tv_detail_major[i] = ll_detail_category[i].findViewById(R.id.tv_detail_major);
            tv_detail_category[i] = ll_detail_category[i].findViewById(R.id.tv_detail_category);
            tv_detail_standard[i] = ll_detail_category[i].findViewById(R.id.tv_detail_standard);
            tv_detail_major[i].setText(i+"");
            tv_detail_category[i].setText("영역"+i);
            tv_detail_standard[i].setText("이수"+i);
        }


        //[Detail Category]
        /*ll_detail_category1 = findViewById(R.id.ll_detail_category1);
        LayoutInflater detail_inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater.inflate(R.layout.detail_category_layout, ll_detail_category1, true);
        TextView tv_detail_category = ll_detail_category1.findViewById(R.id.tv_detail_category);
        tv_detail_category.setText("영역1");

        ll_detail_category2 = findViewById(R.id.ll_detail_category2);
        LayoutInflater detail_inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater2.inflate(R.layout.detail_category_layout, ll_detail_category2, true);
        TextView tv_detail_category2 = ll_detail_category2.findViewById(R.id.tv_detail_category);
        tv_detail_category2.setText("영역2");

        ll_detail_category3 = findViewById(R.id.ll_detail_category3);
        LayoutInflater detail_inflater3 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater3.inflate(R.layout.detail_category_layout, ll_detail_category3, true);
        TextView tv_detail_category3 = ll_detail_category3.findViewById(R.id.tv_detail_category);
        tv_detail_category3.setText("영역3");

        ll_detail_category4 = findViewById(R.id.ll_detail_category4);
        LayoutInflater detail_inflater4 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        detail_inflater4.inflate(R.layout.detail_category_layout, ll_detail_category4, true);
        TextView tv_detail_category4 = ll_detail_category4.findViewById(R.id.tv_detail_category);
        tv_detail_category4.setText("영역4");*/



        //[Filter]
        RadioButton rb_all = findViewById(R.id.rb_all);
        RadioButton rb_this_semester = findViewById(R.id.rb_this_semester);
        String filter_all = rb_all.getText().toString().toUpperCase();
        String filter_this_semester = rb_this_semester.getText().toString().toUpperCase();

        CheckBox cb_credit1 = findViewById(R.id.cb_credit1);
        CheckBox cb_credit2 = findViewById(R.id.cb_credit2);
        CheckBox cb_credit3 = findViewById(R.id.cb_credit3);
        CheckBox cb_credit4 = findViewById(R.id.cb_credit4);

        Switch swit_taken = findViewById(R.id.swit_taken);

        CheckBox cb_major1 = findViewById(R.id.cb_major1);
        CheckBox cb_major2 = findViewById(R.id.cb_major2);
        CheckBox cb_major3 = findViewById(R.id.cb_major3);

        btn_filter = findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_list.clear();
                filter_list2.clear();
                filter_list3.clear();
                filter_list4.clear();

                //개설여부
                if (rb_all.isChecked()){
                    /*Toast.makeText(getApplicationContext(), filter_all, Toast.LENGTH_SHORT).show();*/
                    for(int a = 0; a < original_list.size(); a++){
                        filter_list.add(original_list.get(a));
                    }
                } else {
                    /*Toast.makeText(getApplicationContext(), filter_this_semester, Toast.LENGTH_SHORT).show();*/
                    for(int a = 0; a < original_list.size(); a++){
                        if(original_list.get(a).is_open.toLowerCase().contains("X".toLowerCase())){
                            filter_list.add(original_list.get(a));
                        }
                    }
                }

                //학점
                String[] credit_list = {"0", "0", "0", "0"};
                if (cb_credit1.isChecked()){
                    credit_list[0] = "1";
                }
                if (cb_credit2.isChecked()){
                    credit_list[1] = "1";
                }
                if (cb_credit3.isChecked()){
                    credit_list[2] = "1";
                }
                if (cb_credit4.isChecked()){
                    credit_list[3] = "1";
                }
                for (int i = 0; i < 4; i++){
                    if (credit_list[i].equals("1")){
                        /*Toast.makeText(getApplicationContext(), (i+1)+"", Toast.LENGTH_SHORT).show();*/
                        for(int a = 0; a < filter_list.size(); a++){
                            if(filter_list.get(a).credit.equals((i+1)+"")){
                                filter_list2.add(filter_list.get(a));
                            }
                        }
                    }
                }

                //수강과목 --> ERROR
                if (swit_taken.isChecked()){
                    for(int a = 0; a < filter_list2.size(); a++){
                        if(!filter_list2.get(a).course_year.equals("미정")){
                            filter_list3.add(filter_list2.get(a));
                        }
                    }
                } else {
                    filter_list3 = filter_list2;
                }

                //전공사항
                String[] major_list = {"0", "0", "0"};
                if (cb_major1.isChecked()){
                    major_list[0] = "1";
                }
                if (cb_major2.isChecked()){
                    major_list[1] = "1";
                }
                if (cb_major3.isChecked()){
                    major_list[2] = "1";
                }
                for (int i = 0; i < 3; i++){
                    if (major_list[i].equals("1")){
                        for(int a = 0; a < filter_list3.size(); a++){
                            if(filter_list3.get(a).major_division.equals((i+1)+"")){
                                filter_list4.add(filter_list3.get(a));
                            }
                        }
                    }
                }
                /*Toast.makeText(getApplicationContext(), ""+credit_list[0]+credit_list[1]+credit_list[2]+credit_list[3], Toast.LENGTH_SHORT).show();*/
                rc_adapter.setItems(filter_list4);
            }
        });

        //-------------------------------------------------//
        //[Search] edittext에 입력 시 해당 리스트로 변경
        et_search = findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search_text = et_search.getText().toString();
                search_list.clear();

                if(search_text.equals("")){
                    rc_adapter.setItems(original_list);
                } else {
                    for(int a=0; a < original_list.size(); a++){
                        if(original_list.get(a).course_name.toLowerCase().contains(search_text.toLowerCase())){
                            search_list.add(original_list.get(a));
                        }
                        rc_adapter.setItems(search_list);
                    }
                }
            }
        });

        //-------------------------------------------------//
        //[Dialog] 카테고리 TextView 선택 시 showDialog 함수 호출
        tv_dialog = findViewById(R.id.tv_dialog);
        tv_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CateShowDialog();
            }
        });

        //-------------------------------------------------//
        //[RecyclerView] Adapter 연결 & 리스트 초기화
        init();
        //[RecyclerView] HTTP address로부터 response값 파싱 -> jsonParser 함수 사용 -> DataCourseList에 저장
        getData(REQUEST_URL1);

        //[RecyclerView] HashMap 사용
        courseList = new ArrayList<HashMap<String, String>>();
        //[RecyclerView] 각 필드값을 매핑, from -> to
        /*String[] from = new String[]{"course_name", "course_id", "is_open", "credit"};
        int[] to = new int[]{R.id.tv_course_name, R.id.tv_course_id, R.id.tv_is_open, R.id.tv_credit};*/

        //[RecyclerView] 로딩이 걸릴 경우 로딩 Dialog 출력
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

//        //-------------------------------------------------//
//        //[페이지 이동] LOGIN PAGE BUTTON 클릭 --> LOGIN PAGE 로 이동
//        Button btn_login_page = (Button)findViewById(R.id.btn_login_page);
//        btn_login_page.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        //-------------------------------------------------//
        //[SideBar] CURRICULUM PAGE에서 햄버거 클릭 --> 사이드바 화면 출력
        drawerLayout = (DrawerLayout)findViewById(R.id.curriculum_layout);
        drawerView = (View)findViewById(R.id.curriculum_sidebar);

        Button btn_open = (Button)findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        Button btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //-------------------------------------------------//
        //[하단바] Button parameter 선언
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

    /////////////////
    /*사용자 지정 함수*/
    /////////////////

    //-------------------------------------------------//
    //[Dialog] 팝업창 띄우고 선택 시 OnClick 함수 적용
    public void CateShowDialog(){
        course_category = getResources().getStringArray(R.array.course_category);

        LinearLayout ll_detail_category[] = new LinearLayout[12];
        for (int i = 0; i < 12; i ++){
            int res_id2 = getResources().getIdentifier("ll_detail_category"+i, "id", getPackageName());
            ll_detail_category[i] = findViewById(res_id2);
        }

        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select Your Course Category");    //제목

        builder.setItems(course_category, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "선택된 카테고리는 "+course_category[which], Toast.LENGTH_SHORT).show();
                tv_dialog.setText(course_category[which]);

                TextView tv_detail_category[] = new TextView[12];
                TextView tv_detail_standard[] = new TextView[12];
                TextView tv_detail_major[] = new TextView[12];
                for (int i = 0; i < 12; i ++){
                    tv_detail_category[i] = ll_detail_category[i].findViewById(R.id.tv_detail_category);
                    tv_detail_standard[i] = ll_detail_category[i].findViewById(R.id.tv_detail_standard);
                    tv_detail_major[i] = ll_detail_category[i].findViewById(R.id.tv_detail_major);
                    tv_detail_major[i].setText("[주]");
                }
                //선택된 카테고리에 따라 다른 URL에서 파싱
                switch (which){
                    case 0:     //전공필수
                        init();
                        getData(REQUEST_URL1);

                        /*이수구분을 나타내는 LinearLayout 모두 초기화(GONE)*/
                        for (int i = 0; i < 12; i ++){
                            ll_detail_category[i].setVisibility(View.GONE);
                        }
                        /*전공필수 - 4개 영역으로 분류*/
                        for (int i = 0; i < 4; i ++){
                            ll_detail_category[i].setVisibility(View.VISIBLE);
                        }

                        String str_major0[] = {"[주/복]", "[주]", "[부]", "[주/복]"};
                        String str_category0[] = {"전공기초(필수)", "전공기초(선택)", "전공기초", "전공필수"};
                        String str_standard0[] = {"[7과목 19학점 이수]", "[1과목 1학점 이수]", "[4과목 6학점 이수]",
                                "[11과목 33학점 이수]"};

                        /*4개의 각 영역에 category와 standard 값 적용*/
                        for (int i = 0; i < 4; i ++){
                            tv_detail_major[i].setText(str_major0[i]);
                            tv_detail_category[i].setText(str_category0[i]);
                            tv_detail_standard[i].setText(str_standard0[i]);
                        }



                        /*ll_detail_category1.setVisibility(View.VISIBLE);
                        ll_detail_category1 = findViewById(R.id.ll_detail_category1);
                        LayoutInflater detail_inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        detail_inflater2.inflate(R.layout.detail_category_layout, ll_detail_category2, true);
                        TextView tv_detail_category2 = ll_detail_category2.findViewById(R.id.tv_detail_category);
                        tv_detail_category2.setText("second");*/
                        break;
                    case 1:     //전공선택
                        init();
                        getData(REQUEST_URL2);

                        /*이수구분을 나타내는 LinearLayout 모두 초기화(INVISIBLE)*/
                        for (int i = 0; i < 12; i ++){
                            ll_detail_category[i].setVisibility(View.GONE);
                        }
                        /*전공선택 - 6개 영역으로 분류*/
                        for (int i = 0; i < 6; i ++){
                            ll_detail_category[i].setVisibility(View.VISIBLE);
                        }

                        String str_major1[] = {"[주/복]", "[주/복]", "[주/복]", "[주/복]", "[복]", "[전체]"};
                        String str_category1[] = {"전공기초(선택필1)", "전공기초(선택필1)", "전공기초(선택필1)",
                                "전공기초(선택필1)", "전공기초(선택필2)", "전공선택"};
                        String str_standard1[] = {"[1과목 3학점 이수]", "[1과목 3학점 이수]", "[1과목 3학점 이수]",
                                "[1과목 3학점 이수]", "[2과목 6학점 이수]", "[1과목 3학점 이수]"};

                        /*6개의 각 영역에 category와 standard 값 적용*/
                        for (int i = 0; i < 6; i ++){
                            tv_detail_major[i].setText(str_major1[i]);
                            tv_detail_category[i].setText(str_category1[i]);
                            tv_detail_standard[i].setText(str_standard1[i]);
                        }

                        /*ll_detail_category2.setVisibility(View.VISIBLE);
                        ll_detail_category2 = findViewById(R.id.ll_detail_category2);
                        detail_inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        detail_inflater2.inflate(R.layout.detail_category_layout, ll_detail_category2, true);
                        tv_detail_category2 = ll_detail_category2.findViewById(R.id.tv_detail_category);
                        tv_detail_category2.setText("second");*/
                        break;
                    case 2:     //교양필수
                        init();
                        getData(REQUEST_URL3);

                        /*이수구분을 나타내는 LinearLayout 모두 초기화(INVISIBLE)*/
                        for (int i = 0; i < 12; i ++){
                            ll_detail_category[i].setVisibility(View.GONE);
                        }
                        /*교양필수 - 3개 영역으로 분류*/
                        for (int i = 0; i < 3; i ++){
                            ll_detail_category[i].setVisibility(View.VISIBLE);
                        }

                        String str_category2[] = {"기독교와 세계", "사고와 표현", "대학영어"};
                        String str_standard2[] = {"[1과목 3학점 이수]", "[2과목 5학점 이수]", "[1과목 3학점 이수]"};

                        /*3개의 각 영역에 category와 standard 값 적용*/
                        for (int i = 0; i < 3; i ++){
                            tv_detail_category[i].setText(str_category2[i]);
                            tv_detail_standard[i].setText(str_standard2[i]);
                        }

                        //성공했던 코드
                        /*ll_detail_category2.setVisibility(View.VISIBLE);
                        ll_detail_category2 = findViewById(R.id.ll_detail_category2);
                        tv_detail_category2 = ll_detail_category2.findViewById(R.id.tv_detail_category);
                        tv_detail_category2.setText("third");*/
                        break;
                    case 3:     //교양선택
                        init();
                        getData(REQUEST_URL4);

                        /*이수구분을 나타내는 LinearLayout 모두 초기화(INVISIBLE)*/
                        for (int i = 0; i < 12; i ++){
                            ll_detail_category[i].setVisibility(View.GONE);
                        }
                        /*교양필수 - 12개 영역으로 분류*/
                        for (int i = 0; i < 12; i ++){
                            ll_detail_category[i].setVisibility(View.VISIBLE);
                        }

                        String str_category3[] = {"나눔리더십 외", "중국어", "프랑스어", "독일어", "일본어", "스페인어", "러시아어", "고급영어선택", "융합기초", "큐브(인문학)", "큐브(콘텐츠)", "큐브(디자인)"};
                        String str_standard3[] = {"[1과목 2학점 이수]", "[2과목 4학점 이수]", "[2과목 4학점 이수]",
                                "[2과목 4학점 이수]", "[2과목 4학점 이수]", "[2과목 4학점 이수]", "[2과목 4학점 이수]",
                                "[1과목 3학점 이수]", "[1과목 3학점 이수]", "[1과목 3학점 이수]", "[1과목 3학점 이수]",
                                "[1과목 3학점 이수]"};

                        /*12개의 각 영역에 category와 standard 값 적용*/
                        for (int i = 0; i < 12; i ++){
                            tv_detail_category[i].setText(str_category3[i]);
                            tv_detail_standard[i].setText(str_standard3[i]);
                        }
                        break;
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //-------------------------------------------------//
    //[RecyclerView] 커리큘럼 화면의 RecyclerView에 Adapter를 연결하는 함수 & 초기화 함수
    private void init(){
        RecyclerView rc_curriculum_course1 = findViewById(R.id.rc_curriculum_course1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rc_curriculum_course1.setLayoutManager(linearLayoutManager);

        original_list.clear();
        filter_list.clear();
        filter_list2.clear();
        filter_list3.clear();
        filter_list4.clear();
        rc_adapter = new RecyclerVierAdapter(original_list);
        rc_curriculum_course1.setAdapter(rc_adapter);
    }

    //[RecyclerView]
    private final RC_MyHandler RC_mHandler = new RC_MyHandler(this);
    //[RecyclerView]
    private static class RC_MyHandler extends Handler{
        private final WeakReference<MainActivity> weakReference;

        public RC_MyHandler(MainActivity mainActivity){
            weakReference = new WeakReference<MainActivity>(mainActivity);
        }

        public void handleMessage(Message msg){
            MainActivity mainActivity = weakReference.get();

            if(mainActivity != null){
                switch (msg.what){
                    case LOAD_SUCCESS:
                        mainActivity.progressDialog.dismiss();
                        mainActivity.rc_adapter.notifyDataSetChanged();
                        break;
                }
            }
        }
    }

    //[RecyclerView] HTTP address로부터 Log 내용을 가져오는 함수; jsonParser 함수 사용
    private void getData(String Request_url){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result;

                try{
                    Log.d(TAG, Request_url);
                    URL url = new URL(Request_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setReadTimeout(3000);
                    httpURLConnection.setConnectTimeout(3000);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.connect();

                    int responseStatusCode = httpURLConnection.getResponseCode();

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    } else {
                        inputStream = httpURLConnection.getErrorStream();
                    }

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    bufferedReader.close();
                    httpURLConnection.disconnect();

                    result = sb.toString().trim();
                } catch (Exception e){
                    result = e.toString();
                }
                if (RC_jsonParser(result)){     // 파싱 기능 사용
                    Message message = RC_mHandler.obtainMessage(LOAD_SUCCESS);
                    RC_mHandler.sendMessage(message);
                }
            }
        });
        thread.start();
    }

    //[RecyclerView] jsonParser 함수
    //웹사이트 화면을 파싱하는 함수
    public boolean RC_jsonParser(String jsonString) {

        if (jsonString == null) return false;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            //전체 행렬 중 DB 내용 부분을 jsonArray 형태로 저장
            JSONArray course = jsonObject.getJSONArray("CourseListTest");

            courseList.clear();

            //course의 길이만큼 반복해서 Mapping
            for (int i = 0; i < course.length(); i++) {
                JSONObject courseInfo = course.getJSONObject(i);

                String major_division = courseInfo.getString("major_division");
                String course_name = courseInfo.getString("course_name");
                String course_id = courseInfo.getString("course_id");

                String is_open = courseInfo.getString("is_open");
                if(is_open.equals("0")){
                    is_open = "X";
                } else {
                    is_open = "O";
                }

                String credit = courseInfo.getString("credit");
                String jjim = courseInfo.getString("jjim");
                String course_year = courseInfo.getString("course_year");

                String course_semester = courseInfo.getString("course_semester");
                if (!course_semester.equals("null")){
                    course_semester = course_semester + "학기";
                }


                String grade = courseInfo.getString("grade");

                String category = courseInfo.getString("category");


                String pre_course_id = courseInfo.getString("pre_course_name");
                String pre_course_name = courseInfo.getString("pre_course_id");
                String pre_is_open = courseInfo.getString("pre_is_open");
                String pre_credit = courseInfo.getString("pre_credit");
                String pre_jjim = courseInfo.getString("pre_jjim");

                if(course_semester.equals("null")){
                    course_year = "미정";
                    course_semester = "미정";
                    grade = "미정";
                } else{

                }


                DataCourseList data = new DataCourseList(major_division, course_name,
                        course_id, is_open, credit, jjim, course_year, course_semester,
                        grade, category, pre_course_name, pre_course_id, pre_is_open,
                        pre_credit, pre_jjim);
                rc_adapter.addItem(data);
                /*HashMap<String, String> photoinfoMap = new HashMap<String, String>();
                photoinfoMap.put("course_name", course_name);
                photoinfoMap.put("subject_id", subject_id);
                photoinfoMap.put("gpa", gpa);
                photoinfoMap.put("is_english", is_english);
                courseList.add(photoinfoMap);*/

            }
            return true;
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
        return false;
    }

    //-------------------------------------------------//
    //[SideBar] Drawer 사용
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    //-------------------------------------------------//
    //[하단바]
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

    ///////////////////////////
    //테스트용 - 버튼 누르면 토스트 문구 띄우기
    public void CourseList(){
        String URL = "http://smlee099.dothome.co.kr/CourseList.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "응답:"+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "에러:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                return param;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }
    ///////////////////////////
}