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
import java.util.Locale;
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
    /*RecyclerVierAdapter rc_adapter = null;*/

    //[Dialog]
    TextView tv_dialog;
    AlertDialog.Builder builder;
    String[] course_category;

    //[Search]
    ArrayList<DataCourseList> original_list = new ArrayList<>();
    /*ArrayList<DataCourseList> search_list = new ArrayList<>();*/
    EditText et_search;
    ArrayList<DataCourseList> search_list0 = new ArrayList<>(), search_list1 = new ArrayList<>(), search_list2 = new ArrayList<>(), search_list3 = new ArrayList<>(), search_list4 = new ArrayList<>(),
            search_list5 = new ArrayList<>(), search_list6 = new ArrayList<>(), search_list7 = new ArrayList<>(), search_list8 = new ArrayList<>(), search_list9 = new ArrayList<>(),
            search_list10 = new ArrayList<>(), search_list11 = new ArrayList<>(), search_list12 = new ArrayList<>(), search_list13 = new ArrayList<>(), search_list14 = new ArrayList<>(),
            search_list15 = new ArrayList<>(), search_list16 = new ArrayList<>(), search_list17 = new ArrayList<>(), search_list18 = new ArrayList<>(), search_list19 = new ArrayList<>(),
            search_list20 = new ArrayList<>(), search_list21 = new ArrayList<>(), search_list22 = new ArrayList<>(), search_list23 = new ArrayList<>(), search_list24 = new ArrayList<>();
    ArrayList<DataCourseList> search_list_list[] = new ArrayList[]{search_list0, search_list1, search_list2, search_list3, search_list4,
            search_list5, search_list6, search_list7, search_list8, search_list9,
            search_list10, search_list11, search_list12, search_list13, search_list14,
            search_list15, search_list16, search_list17, search_list18, search_list19,
            search_list20, search_list21, search_list22, search_list23, search_list24};

    //[Filter]
    ArrayList<DataCourseList> filter_list = new ArrayList<>();
    ArrayList<DataCourseList> filter_list2 = new ArrayList<>();
    ArrayList<DataCourseList> filter_list3 = new ArrayList<>();
    ArrayList<DataCourseList> filter_list4 = new ArrayList<>();
    ArrayList<DataCourseList> filter_list000 = new ArrayList<>(), filter_list001 = new ArrayList<>(), filter_list002 = new ArrayList<>(), filter_list003 = new ArrayList<>(), filter_list004 = new ArrayList<>(),
            filter_list005 = new ArrayList<>(), filter_list006 = new ArrayList<>(), filter_list007 = new ArrayList<>(), filter_list008 = new ArrayList<>(), filter_list009 = new ArrayList<>(),
            filter_list010 = new ArrayList<>(), filter_list011 = new ArrayList<>(), filter_list012 = new ArrayList<>(), filter_list013 = new ArrayList<>(), filter_list014 = new ArrayList<>(),
            filter_list015 = new ArrayList<>(), filter_list016 = new ArrayList<>(), filter_list017 = new ArrayList<>(), filter_list018 = new ArrayList<>(), filter_list019 = new ArrayList<>(),
            filter_list020 = new ArrayList<>(), filter_list021 = new ArrayList<>(), filter_list022 = new ArrayList<>(), filter_list023 = new ArrayList<>(), filter_list024 = new ArrayList<>();
    ArrayList<DataCourseList> filter_list100 = new ArrayList<>(), filter_list101 = new ArrayList<>(), filter_list102 = new ArrayList<>(), filter_list103 = new ArrayList<>(), filter_list104 = new ArrayList<>(),
            filter_list105 = new ArrayList<>(), filter_list106 = new ArrayList<>(), filter_list107 = new ArrayList<>(), filter_list108 = new ArrayList<>(), filter_list109 = new ArrayList<>(),
            filter_list110 = new ArrayList<>(), filter_list111 = new ArrayList<>(), filter_list112 = new ArrayList<>(), filter_list113 = new ArrayList<>(), filter_list114 = new ArrayList<>(),
            filter_list115 = new ArrayList<>(), filter_list116 = new ArrayList<>(), filter_list117 = new ArrayList<>(), filter_list118 = new ArrayList<>(), filter_list119 = new ArrayList<>(),
            filter_list120 = new ArrayList<>(), filter_list121 = new ArrayList<>(), filter_list122 = new ArrayList<>(), filter_list123 = new ArrayList<>(), filter_list124 = new ArrayList<>();
    ArrayList<DataCourseList> filter_list200 = new ArrayList<>(), filter_list201 = new ArrayList<>(), filter_list202 = new ArrayList<>(), filter_list203 = new ArrayList<>(), filter_list204 = new ArrayList<>(),
            filter_list205 = new ArrayList<>(), filter_list206 = new ArrayList<>(), filter_list207 = new ArrayList<>(), filter_list208 = new ArrayList<>(), filter_list209 = new ArrayList<>(),
            filter_list210 = new ArrayList<>(), filter_list211 = new ArrayList<>(), filter_list212 = new ArrayList<>(), filter_list213 = new ArrayList<>(), filter_list214 = new ArrayList<>(),
            filter_list215 = new ArrayList<>(), filter_list216 = new ArrayList<>(), filter_list217 = new ArrayList<>(), filter_list218 = new ArrayList<>(), filter_list219 = new ArrayList<>(),
            filter_list220 = new ArrayList<>(), filter_list221 = new ArrayList<>(), filter_list222 = new ArrayList<>(), filter_list223 = new ArrayList<>(), filter_list224 = new ArrayList<>();
    ArrayList<DataCourseList> filter_list300 = new ArrayList<>(), filter_list301 = new ArrayList<>(), filter_list302 = new ArrayList<>(), filter_list303 = new ArrayList<>(), filter_list304 = new ArrayList<>(),
            filter_list305 = new ArrayList<>(), filter_list306 = new ArrayList<>(), filter_list307 = new ArrayList<>(), filter_list308 = new ArrayList<>(), filter_list309 = new ArrayList<>(),
            filter_list310 = new ArrayList<>(), filter_list311 = new ArrayList<>(), filter_list312 = new ArrayList<>(), filter_list313 = new ArrayList<>(), filter_list314 = new ArrayList<>(),
            filter_list315 = new ArrayList<>(), filter_list316 = new ArrayList<>(), filter_list317 = new ArrayList<>(), filter_list318 = new ArrayList<>(), filter_list319 = new ArrayList<>(),
            filter_list320 = new ArrayList<>(), filter_list321 = new ArrayList<>(), filter_list322 = new ArrayList<>(), filter_list323 = new ArrayList<>(), filter_list324 = new ArrayList<>();
    ArrayList<DataCourseList> filter_list_list0[] = new ArrayList[]{
            filter_list000, filter_list001, filter_list002, filter_list003, filter_list004,
            filter_list005, filter_list006, filter_list007, filter_list008, filter_list009,
            filter_list010, filter_list011, filter_list012, filter_list013, filter_list014,
            filter_list015, filter_list016, filter_list017, filter_list018, filter_list019,
            filter_list020, filter_list021, filter_list022, filter_list023, filter_list024};
    ArrayList<DataCourseList> filter_list_list1[] = new ArrayList[]{
            filter_list100, filter_list101, filter_list102, filter_list103, filter_list104,
            filter_list105, filter_list106, filter_list107, filter_list108, filter_list109,
            filter_list110, filter_list111, filter_list112, filter_list113, filter_list114,
            filter_list115, filter_list116, filter_list117, filter_list118, filter_list119,
            filter_list120, filter_list121, filter_list122, filter_list123, filter_list124};
    ArrayList<DataCourseList> filter_list_list2[] = new ArrayList[]{
            filter_list200, filter_list201, filter_list202, filter_list203, filter_list204,
            filter_list205, filter_list206, filter_list207, filter_list208, filter_list209,
            filter_list210, filter_list211, filter_list212, filter_list213, filter_list214,
            filter_list215, filter_list216, filter_list217, filter_list218, filter_list219,
            filter_list220, filter_list221, filter_list222, filter_list223, filter_list224};
    ArrayList<DataCourseList> filter_list_list3[] = new ArrayList[]{
            filter_list300, filter_list301, filter_list302, filter_list303, filter_list304,
            filter_list305, filter_list306, filter_list307, filter_list308, filter_list309,
            filter_list310, filter_list311, filter_list312, filter_list313, filter_list314,
            filter_list315, filter_list316, filter_list317, filter_list318, filter_list319,
            filter_list320, filter_list321, filter_list322, filter_list323, filter_list324};
    Button btn_filter;

    //////////testing
    public static Context context_main;
    public int var;

    //[Detail Category]
    RecyclerVierAdapter rc_adapter0, rc_adapter1, rc_adapter2, rc_adapter3, rc_adapter4, rc_adapter5, rc_adapter6, rc_adapter7, rc_adapter8, rc_adapter9,
            rc_adapter10, rc_adapter11, rc_adapter12, rc_adapter13, rc_adapter14, rc_adapter15, rc_adapter16, rc_adapter17, rc_adapter18, rc_adapter19,
            rc_adapter20, rc_adapter21, rc_adapter22, rc_adapter23, rc_adapter24;
    RecyclerVierAdapter rc_adapter_list[] = {rc_adapter0, rc_adapter1, rc_adapter2, rc_adapter3, rc_adapter4, rc_adapter5, rc_adapter6, rc_adapter7, rc_adapter8, rc_adapter9,
            rc_adapter10, rc_adapter11, rc_adapter12, rc_adapter13, rc_adapter14, rc_adapter15, rc_adapter16, rc_adapter17, rc_adapter18, rc_adapter19,
            rc_adapter20, rc_adapter21, rc_adapter22, rc_adapter23, rc_adapter24};

    ArrayList<DataCourseList> course_list0 = new ArrayList<>(), course_list1 = new ArrayList<>(), course_list2 = new ArrayList<>(), course_list3 = new ArrayList<>(), course_list4 = new ArrayList<>(),
            course_list5 = new ArrayList<>(), course_list6 = new ArrayList<>(), course_list7 = new ArrayList<>(), course_list8 = new ArrayList<>(), course_list9 = new ArrayList<>(),
            course_list10 = new ArrayList<>(), course_list11 = new ArrayList<>(), course_list12 = new ArrayList<>(), course_list13 = new ArrayList<>(), course_list14 = new ArrayList<>(),
            course_list15 = new ArrayList<>(), course_list16 = new ArrayList<>(), course_list17 = new ArrayList<>(), course_list18 = new ArrayList<>(), course_list19 = new ArrayList<>(),
            course_list20 = new ArrayList<>(), course_list21 = new ArrayList<>(), course_list22 = new ArrayList<>(), course_list23 = new ArrayList<>(), course_list24 = new ArrayList<>();
    ArrayList<DataCourseList> course_list_list[] = new ArrayList[]{course_list0, course_list1, course_list2, course_list3, course_list4, course_list5, course_list6, course_list7, course_list8, course_list9, course_list10, course_list11,
            course_list12, course_list13, course_list14, course_list15, course_list16, course_list17, course_list18, course_list19, course_list20,
            course_list21, course_list22, course_list23, course_list24};

    TextView tv_detail_major[] = new TextView[25];
    TextView tv_detail_category[] = new TextView[25];
    TextView tv_detail_standard[] = new TextView[25];

    String detail_major_list[] = {"[주]", "[주]", "[주]", "[주]", "[주]",
            "[주]", "[주]", "[주]", "[주]", "[주]",
            "[주]", "[주]", "[주]", "[주]", "[주]",
            "[주/복]", "[주/복]", "[주/복]", "[주/복]", "[주/복]",
            "[주]", "[복]", "[부]", "[주/복]", "[전체]"};
    String detail_category_list[] = {"기독교와 세계", "나눔리더십 외", "사고와 표현", "대학영어", "중국어",
            "프랑스어", "독일어", "일본어", "스페인어", "러시아어",
            "고급영어선택", "융합기초", "큐브(인문학)", "큐브(콘텐츠)", "큐브(디자인)",
            "전공기초(필수)", "전공기초(선택필1)", "전공기초(선택필1)", "전공기초(선택필1)", "전공기초(선택필1)",
            "전공기초(선택)", "전공기초(선택필2)", "전공기초(뇌인지)", "전공필수", "전공선택"};
    String detail_standard_list[] = {"1과목 3학점 이수", "1과목 2학점 이수", "2과목 5학점 이수", "1과목 3학점 이수", "2과목 4학점 이수",
            "2과목 4학점 이수", "2과목 4학점 이수", "2과목 4학점 이수", "2과목 4학점 이수", "2과목 4학점 이수",
            "1과목 3학점 이수", "1과목 3학점 이수", "1과목 3학점 이수", "1과목 3학점 이수", "1과목 3학점 이수",
            "7과목 19학점 이수", "1과목 3학점 이수", "1과목 3학점 이수", "1과목 3학점 이수", "1과목 3학점 이수",
            "1과목 1학점 이수", "2과목 6학점 이수", "4과목 12학점 이수", "11과목 33학점 이수", "1과목 3학점 이수"};

    LayoutInflater detail_inflater0, detail_inflater1, detail_inflater2, detail_inflater3, detail_inflater4,
            detail_inflater5, detail_inflater6, detail_inflater7, detail_inflater8, detail_inflater9,
            detail_inflater10, detail_inflater11, detail_inflater12, detail_inflater13, detail_inflater14,
            detail_inflater15, detail_inflater16, detail_inflater17, detail_inflater18, detail_inflater19,
            detail_inflater20, detail_inflater21, detail_inflater22, detail_inflater23, detail_inflater24;
    LayoutInflater detail_inflater_list[] = {detail_inflater0, detail_inflater1, detail_inflater2, detail_inflater3, detail_inflater4,
            detail_inflater5, detail_inflater6, detail_inflater7, detail_inflater8, detail_inflater9,
            detail_inflater10, detail_inflater11, detail_inflater12, detail_inflater13, detail_inflater14,
            detail_inflater15, detail_inflater16, detail_inflater17, detail_inflater18, detail_inflater19,
            detail_inflater20, detail_inflater21, detail_inflater22, detail_inflater23, detail_inflater24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);

        //////////////
        context_main = this;

        //-------------------------------------------------//
        //[Detail Category]
        LinearLayout ll_detail_category[] = new LinearLayout[25];
        for (int i = 0; i < 25; i ++){
            int res_id2 = getResources().getIdentifier("ll_detail_category"+i, "id", getPackageName());
            ll_detail_category[i] = findViewById(res_id2);
            detail_inflater_list[i] = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            detail_inflater_list[i].inflate(R.layout.detail_category_layout, ll_detail_category[i], true);
        }

        //-------------------------------------------------//
        //[Filter]
        RadioButton rb_all = findViewById(R.id.rb_all);
        RadioButton rb_this_semester = findViewById(R.id.rb_this_semester);
        /*[TESTING]*/
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
                for (int i=0; i<25; i++){
                    filter_list_list0[i].clear();
                    filter_list_list1[i].clear();
                    filter_list_list2[i].clear();
                    filter_list_list3[i].clear();
                }

                //개설여부
                if (rb_all.isChecked()){
                    /*Toast.makeText(getApplicationContext(), filter_all, Toast.LENGTH_SHORT).show();*/
                    /*for(int a = 0; a < course_list_list[15].size(); a++){
                        filter_list.add(course_list_list[15].get(a));
                    }*/
                    for (int i=0; i<25; i++){
                        for (int j=0; j<course_list_list[i].size(); j++){
                            filter_list_list0[i].add(course_list_list[i].get(j));
                        }
                    }
                } else {
                    /*Toast.makeText(getApplicationContext(), filter_this_semester, Toast.LENGTH_SHORT).show();*/
                    /*for(int a = 0; a < course_list_list[15].size(); a++){
                        if(course_list_list[15].get(a).is_open.toLowerCase().contains("O".toLowerCase())){
                            filter_list.add(course_list_list[15].get(a));
                        }
                    }*/
                    for (int i=0; i<25; i++){
                        for (int j=0; j<course_list_list[i].size(); j++){
                            if(course_list_list[i].get(j).is_open.toLowerCase().contains("O".toLowerCase())){
                                filter_list_list0[i].add(course_list_list[i].get(j));
                            }
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
                for (int k = 0; k < 4; k++){
                    if (credit_list[k].equals("1")){
                        /*Toast.makeText(getApplicationContext(), (i+1)+"", Toast.LENGTH_SHORT).show();*/
                        /*for(int a = 0; a < filter_list_list0[15].size(); a++){
                            if(filter_list_list0[15].get(a).credit.equals((k+1)+"")){
                                filter_list_list1[15].add(filter_list_list0[15].get(a));
                            }
                        }*/
                        for (int i=0; i<25; i++){
                            for (int j=0; j<filter_list_list0[i].size(); j++){
                                if(filter_list_list0[i].get(j).credit.equals((k+1)+"")){
                                    filter_list_list1[i].add(filter_list_list0[i].get(j));
                                }
                            }
                        }
                    }
                }

                //수강과목 --> ERROR
                if (swit_taken.isChecked()){
                    /*for(int a = 0; a < filter_list2.size(); a++){
                        if(!filter_list2.get(a).course_year.equals("미정")){
                            filter_list3.add(filter_list2.get(a));
                        }
                    }*/
                } else {
                    /*filter_list3 = filter_list2;*/
                    for (int i=0; i<25; i++){
                        filter_list_list2[i] = filter_list_list1[i];
                    }
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
                for (int k = 0; k < 3; k++){
                    if (major_list[k].equals("1")){
                        /*for(int a = 0; a < filter_list3.size(); a++){
                            if(filter_list3.get(a).major_division.equals((k+1)+"")){
                                filter_list4.add(filter_list.get(a));
                            }
                        }*/
                        for (int i=0; i<25; i++){
                            for (int j=0; j<filter_list_list2[i].size(); j++){
                                if(filter_list_list2[i].get(j).credit.equals((k+1)+"")){
                                    filter_list_list3[i].add(filter_list_list2[i].get(j));
                                }
                            }
                        }
                    }
                }
                /*Toast.makeText(getApplicationContext(), ""+credit_list[0]+credit_list[1]+credit_list[2]+credit_list[3], Toast.LENGTH_SHORT).show();*/
                /*rc_adapter.setItems(filter_list4);*/
                /*rc_adapter_list[15].setItems(filter_list_list1[15]);*/
                for (int i=0; i<25; i++){
                    rc_adapter_list[i].setItems(filter_list_list3[i]);
                }
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
                /*search_list.clear();*/
                for (int i=0; i<25; i++){
                    search_list_list[i].clear();
                }

                if(search_text.equals("")){
                    /*rc_adapter.setItems(original_list);*/
                    for (int i=0; i<25; i++){
                        rc_adapter_list[i].setItems(course_list_list[i]);
                    }
                } else {
                    /*for(int a=0; a < original_list.size(); a++){
                        if(original_list.get(a).course_name.toLowerCase().contains(search_text.toLowerCase())){
                            search_list.add(original_list.get(a));
                        }
                        rc_adapter.setItems(search_list);
                    }*/
                    for (int i=0; i<25; i++){
                        for (int j=0; j<course_list_list[i].size(); j++){
                            if(course_list_list[i].get(j).course_name.toLowerCase().contains(search_text.toLowerCase())){
                                search_list_list[i].add(course_list_list[i].get(j));
                            }
                            rc_adapter_list[i].setItems(search_list_list[i]);
                        }
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

        for (int i=0; i<25; i++){
            tv_detail_major[i] = ll_detail_category[i].findViewById(R.id.tv_detail_major);
            tv_detail_category[i] = ll_detail_category[i].findViewById(R.id.tv_detail_category);
            tv_detail_standard[i] = ll_detail_category[i].findViewById(R.id.tv_detail_standard);
            tv_detail_major[i].setText(detail_major_list[i]);
            tv_detail_category[i].setText(detail_category_list[i]);
            tv_detail_standard[i].setText(detail_standard_list[i]);
        }

        /*이수구분을 나타내는 LinearLayout 모두 초기화(GONE)*/
        for (int i = 0; i < 25; i ++){
            ll_detail_category[i].setVisibility(View.GONE);
        }
        /*전공필수 - 4개 영역으로 분류(16번, 21번, 23번, 24번 적용)*/
        ll_detail_category[15].setVisibility(View.VISIBLE);
        ll_detail_category[20].setVisibility(View.VISIBLE);
        ll_detail_category[22].setVisibility(View.VISIBLE);
        ll_detail_category[23].setVisibility(View.VISIBLE);

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

        //[Detail Category]
        LinearLayout ll_detail_category[] = new LinearLayout[25];
        for (int i = 0; i < 25; i ++){
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

                //[Detail Category]
                for (int i = 0; i < 25; i ++){
                    tv_detail_category[i] = ll_detail_category[i].findViewById(R.id.tv_detail_category);
                    tv_detail_standard[i] = ll_detail_category[i].findViewById(R.id.tv_detail_standard);
                    tv_detail_major[i] = ll_detail_category[i].findViewById(R.id.tv_detail_major);
                    tv_detail_major[i].setText(detail_major_list[i]);
                    tv_detail_category[i].setText(detail_category_list[i]);
                    tv_detail_standard[i].setText(detail_standard_list[i]);
                }

                //선택된 카테고리에 따라 다른 URL에서 파싱
                switch (which){
                    case 0:     //전공필수
                        init();
                        getData(REQUEST_URL1);

                        //[Detail Category]
                        /*이수구분을 나타내는 LinearLayout 모두 초기화(GONE)*/
                        for (int i = 0; i < 25; i ++){
                            ll_detail_category[i].setVisibility(View.GONE);
                        }
                        /*전공필수 - 4개 영역으로 분류*/
                        ll_detail_category[15].setVisibility(View.VISIBLE);
                        ll_detail_category[20].setVisibility(View.VISIBLE);
                        ll_detail_category[22].setVisibility(View.VISIBLE);
                        ll_detail_category[23].setVisibility(View.VISIBLE);
                        break;
                    case 1:     //전공선택
                        init();
                        getData(REQUEST_URL2);

                        //[Detail Category]
                        /*이수구분을 나타내는 LinearLayout 모두 초기화(INVISIBLE)*/
                        for (int i = 0; i < 25; i ++){
                            ll_detail_category[i].setVisibility(View.GONE);
                        }
                        /*전공필수 - 6개 영역으로 분류*/
                        ll_detail_category[16].setVisibility(View.VISIBLE);
                        ll_detail_category[17].setVisibility(View.VISIBLE);
                        ll_detail_category[18].setVisibility(View.VISIBLE);
                        ll_detail_category[19].setVisibility(View.VISIBLE);
                        ll_detail_category[21].setVisibility(View.VISIBLE);
                        ll_detail_category[24].setVisibility(View.VISIBLE);
                        break;
                    case 2:     //교양필수
                        init();
                        getData(REQUEST_URL3);

                        //[Detail Category]
                        /*이수구분을 나타내는 LinearLayout 모두 초기화(INVISIBLE)*/
                        for (int i = 0; i < 25; i ++){
                            ll_detail_category[i].setVisibility(View.GONE);
                        }
                        /*전공필수 - 3개 영역으로 분류*/
                        ll_detail_category[0].setVisibility(View.VISIBLE);
                        ll_detail_category[2].setVisibility(View.VISIBLE);
                        ll_detail_category[3].setVisibility(View.VISIBLE);
                        break;
                    case 3:     //교양선택
                        init();
                        getData(REQUEST_URL4);

                        //[Detail Category]
                        /*이수구분을 나타내는 LinearLayout 모두 초기화(INVISIBLE)*/
                        for (int i = 0; i < 25; i ++){
                            ll_detail_category[i].setVisibility(View.GONE);
                        }
                        /*전공필수 - 12개 영역으로 분류*/
                        ll_detail_category[1].setVisibility(View.VISIBLE);
                        ll_detail_category[4].setVisibility(View.VISIBLE);
                        ll_detail_category[5].setVisibility(View.VISIBLE);
                        ll_detail_category[6].setVisibility(View.VISIBLE);
                        ll_detail_category[7].setVisibility(View.VISIBLE);
                        ll_detail_category[8].setVisibility(View.VISIBLE);
                        ll_detail_category[9].setVisibility(View.VISIBLE);
                        ll_detail_category[10].setVisibility(View.VISIBLE);
                        ll_detail_category[11].setVisibility(View.VISIBLE);
                        ll_detail_category[12].setVisibility(View.VISIBLE);
                        ll_detail_category[13].setVisibility(View.VISIBLE);
                        ll_detail_category[14].setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //-------------------------------------------------//
    //[Detail Category]
    RecyclerView recyclerView_list[] = new RecyclerView[25];
    LinearLayoutManager linearLayoutManager0 = new LinearLayoutManager(this), linearLayoutManager1 = new LinearLayoutManager(this), linearLayoutManager2 = new LinearLayoutManager(this), linearLayoutManager3 = new LinearLayoutManager(this), linearLayoutManager4 = new LinearLayoutManager(this),
            linearLayoutManager5 = new LinearLayoutManager(this), linearLayoutManager6 = new LinearLayoutManager(this), linearLayoutManager7 = new LinearLayoutManager(this), linearLayoutManager8 = new LinearLayoutManager(this), linearLayoutManager9 = new LinearLayoutManager(this),
            linearLayoutManager10 = new LinearLayoutManager(this), linearLayoutManager11 = new LinearLayoutManager(this), linearLayoutManager12 = new LinearLayoutManager(this), linearLayoutManager13 = new LinearLayoutManager(this), linearLayoutManager14 = new LinearLayoutManager(this),
            linearLayoutManager15 = new LinearLayoutManager(this), linearLayoutManager16 = new LinearLayoutManager(this), linearLayoutManager17 = new LinearLayoutManager(this), linearLayoutManager18 = new LinearLayoutManager(this), linearLayoutManager19 = new LinearLayoutManager(this),
            linearLayoutManager20 = new LinearLayoutManager(this), linearLayoutManager21 = new LinearLayoutManager(this), linearLayoutManager22 = new LinearLayoutManager(this), linearLayoutManager23 = new LinearLayoutManager(this), linearLayoutManager24 = new LinearLayoutManager(this);
    LinearLayoutManager linearLayoutManager[] = {linearLayoutManager0, linearLayoutManager1, linearLayoutManager2, linearLayoutManager3, linearLayoutManager4, linearLayoutManager5, linearLayoutManager6, linearLayoutManager7, linearLayoutManager8, linearLayoutManager9, linearLayoutManager10,
            linearLayoutManager11, linearLayoutManager12, linearLayoutManager13, linearLayoutManager14, linearLayoutManager15, linearLayoutManager16, linearLayoutManager17, linearLayoutManager18, linearLayoutManager19, linearLayoutManager20,
            linearLayoutManager21, linearLayoutManager22, linearLayoutManager23, linearLayoutManager24};

    //[RecyclerView] 커리큘럼 화면의 RecyclerView에 Adapter를 연결하는 함수 & 초기화 함수
    //[Detail Category]
    private void init(){
        /*RecyclerView rc_curriculum_course1 = findViewById(R.id.rc_curriculum_course1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rc_curriculum_course1.setLayoutManager(linearLayoutManager);

        original_list.clear();
        filter_list.clear();
        filter_list2.clear();
        filter_list3.clear();
        filter_list4.clear();
        rc_adapter = new RecyclerVierAdapter(original_list);
        rc_curriculum_course1.setAdapter(rc_adapter);*/

        for(int i=0; i<25; i++){
            course_list_list[i].clear();
            /*filter_list_list0[i].clear();
            filter_list_list1[i].clear();
            filter_list_list2[i].clear();
            filter_list_list3[i].clear();*/
        }
        /*filter_list.clear();
        filter_list2.clear();
        filter_list3.clear();
        filter_list4.clear();*/
        for (int i=0; i<25; i++){
            int res_id = getResources().getIdentifier("rc_curriculum_course"+i, "id", getPackageName());
            recyclerView_list[i] = findViewById(res_id);
            recyclerView_list[i].setLayoutManager(linearLayoutManager[i]);
            rc_adapter_list[i] = new RecyclerVierAdapter(course_list_list[i]);
            recyclerView_list[i].setAdapter(rc_adapter_list[i]);
        }
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
                        //[Detail Category]
                        for (int i=0; i<25; i++){
                            mainActivity.rc_adapter_list[i].notifyDataSetChanged();
                        }
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

            //course의 길이만큼 반복해서 Mapping
            for (int i = 0; i < course.length(); i++) {
                JSONObject courseInfo = course.getJSONObject(i);

                String major_division = courseInfo.getString("major_division");
                String detail_category = courseInfo.getString("detail_category");
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

                if(course_year.equals("null")){
                    course_year = "미정";
                    course_semester = "미정";
                    grade = "미정";
                }

                DataCourseList data = new DataCourseList(major_division, detail_category, course_name,
                        course_id, is_open, credit, jjim, course_year, course_semester,
                        grade, category, pre_course_name, pre_course_id, pre_is_open,
                        pre_credit, pre_jjim);
                //[Detail Category]
                for (int j=0; j<25; j++){
                    if (detail_category.equals((j+1)+"")){
                        rc_adapter_list[j].addItem(data);
                    }
                }
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