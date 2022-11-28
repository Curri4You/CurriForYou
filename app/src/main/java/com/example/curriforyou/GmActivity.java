//학점관리 메인페이지

package com.example.curriforyou;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class GmActivity extends AppCompatActivity implements View.OnClickListener {

    // [RecyclerView] 리스트 출력을 위한 parameter
    private static final String TAG = "imagesearchexample";
    // URL - 학기별 학점요약 DB
    private String REQUEST_URL = "http://smlee099.dothome.co.kr/hakjum.php";
    private String REQUEST_URL_GRADE0 = "http://smlee099.dothome.co.kr/2019_1.php";
    private String REQUEST_URL_GRADE1 = "http://smlee099.dothome.co.kr/2019_2.php";
    private String REQUEST_URL_GRADE2 = "http://smlee099.dothome.co.kr/2020_1.php";
    private String REQUEST_URL_GRADE3 = "http://smlee099.dothome.co.kr/2020_2.php";
    private String REQUEST_URL_GRADE4 = "http://smlee099.dothome.co.kr/2021_1.php";
    private String REQUEST_URL_GRADE5 = "http://smlee099.dothome.co.kr/2021_2.php";
    private String REQUEST_URL_GRADE6 = "http://smlee099.dothome.co.kr/2022_1.php";
    private String REQUEST_URL_TOTAL = "http://smlee099.dothome.co.kr/total_semesters.php";
    String REQUEST_URL_GRADE_LIST[] = {REQUEST_URL_GRADE0, REQUEST_URL_GRADE1, REQUEST_URL_GRADE2, REQUEST_URL_GRADE3, REQUEST_URL_GRADE4, REQUEST_URL_GRADE5, REQUEST_URL_GRADE6};
    public static final int LOAD_SUCCESS = 101;
    private ProgressDialog progressDialog = null;
    GmRecyclerAdapter adapt11, adapt12, adapt21, adapt22, adapt31, adapt32, adapt41, adapt42;
    GmRecyclerAdapter adapt_list[] = {adapt11, adapt12, adapt21, adapt22, adapt31, adapt32, adapt41, adapt42};
    private ArrayList<HashMap<String, String>> gmSemesterList = null;
    private Context context;

    boolean open_or_not = false;

    //[Grade] 총평점, 전공평점, 교양평점 TextView들을 담을 list 정의
    TextView tv_totalGrade_list[] = new TextView[8];
    TextView tv_majorGrade_list[] = new TextView[8];
    TextView tv_liberalGrade_list[] = new TextView[8];
    TextView tv_totalGrade, tv_majorGrade, tv_liberalGrade, tv_tillNowCredit;

    ProgressBar pb_total_grade;

    //[Grade] 화살표 및 리사이클러뷰를 담을 list 정의
    LinearLayout open_gm_semester_list[] = new LinearLayout[8];
    ImageView open_gm_arrow_list[] = new ImageView[8];
    RecyclerView rc_gm_course_list[] = new RecyclerView[8];

    //[Change Grade] 만점 변환하는 라디오 그룹
    RadioGroup rg_grade_version;
    //[Change Grade] 현재 4.3 / 4.5 / 100 중 어떤 상태인지를 저장하는 변수
    int version = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gm_home);

        //-------------------------------------------------//
        //[Listened Course] 수강한 리스트를 리사이클러뷰로 띄움
        init();
        getData(REQUEST_URL);

        //-------------------------------------------------//
        //[Grade] 평점 계산해서 띄우는 함수 매 semester마다 적용
        for (int i=0; i<7; i++){
            getData_Grade(REQUEST_URL_GRADE_LIST[i], i, 1);
        }
        getData_Grade(REQUEST_URL_TOTAL, 0, 1);

        //[Grade] 학점 평점 정리
        tv_totalGrade = findViewById(R.id.totalGrade);
        tv_majorGrade = findViewById(R.id.majorGrade);
        tv_liberalGrade = findViewById(R.id.liberalGrade);
        tv_tillNowCredit = findViewById(R.id.tillNowCredit);
        pb_total_grade = findViewById(R.id.pb_total_grade);

        //[Grade] 학기별 평점 정리
        for (int i=0; i<8; i++){
            int grade_res_id = getResources().getIdentifier("gm_list_totalGrade"+i, "id", getPackageName());
            int major_res_id = getResources().getIdentifier("gm_list_majorGrade"+i, "id", getPackageName());
            int liberal_res_id = getResources().getIdentifier("gm_list_liberalGrade"+i, "id", getPackageName());
            int arrow_res_id = getResources().getIdentifier("open_gm_arrow"+i, "id", getPackageName());
            int semester_res_id = getResources().getIdentifier("open_gm_semester"+i, "id", getPackageName());
            int course_res_id = getResources().getIdentifier("rc_gm_course"+i, "id", getPackageName());
            tv_totalGrade_list[i] = findViewById(grade_res_id);
            tv_majorGrade_list[i] = findViewById(major_res_id);
            tv_liberalGrade_list[i] = findViewById(liberal_res_id);
            open_gm_arrow_list[i] = findViewById(arrow_res_id);
            open_gm_semester_list[i] = findViewById(semester_res_id);
            rc_gm_course_list[i] = findViewById(course_res_id);
            rc_gm_course_list[i].setVisibility(View.GONE);
        }

        //-------------------------------------------------//
        //[Change Grade] 버튼 변경 시 동작
        rg_grade_version = findViewById(R.id.rg_grade_version);
        rg_grade_version.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){

                //[Change Grade] 총 평점 변환
                double grade_pre_all0=0, grade_pre_all1=0, grade_pre_all2=0, grade_pre_all3=0, grade_pre_all4=0, grade_pre_all5=0, grade_pre_all6=0;
                double grade_post_all0=0, grade_post_all1=0, grade_post_all2=0, grade_post_all3=0, grade_post_all4=0, grade_post_all5=0, grade_post_all6=0;
                double grade_pre_all[] = {grade_pre_all0, grade_pre_all1, grade_pre_all2, grade_pre_all3, grade_pre_all4, grade_pre_all5, grade_pre_all6};
                double grade_post_all[] = {grade_post_all0, grade_post_all1, grade_post_all2, grade_post_all3, grade_post_all4, grade_post_all5, grade_post_all6};

                //[Change Grade] 전공 평점 변환
                double grade_pre_major0=0, grade_pre_major1=0, grade_pre_major2=0, grade_pre_major3=0, grade_pre_major4=0, grade_pre_major5=0, grade_pre_major6=0;
                double grade_post_major0=0, grade_post_major1=0, grade_post_major2=0, grade_post_major3=0, grade_post_major4=0, grade_post_major5=0, grade_post_major6=0;
                double grade_pre_major[] = {grade_pre_major0, grade_pre_major1, grade_pre_major2, grade_pre_major3, grade_pre_major4, grade_pre_major5, grade_pre_major6};
                double grade_post_major[] = {grade_post_major0, grade_post_major1, grade_post_major2, grade_post_major3, grade_post_major4, grade_post_major5, grade_post_major6};

                //[Change Grade] 교양 평점 변환
                double grade_pre_liber0=0, grade_pre_liber1=0, grade_pre_liber2=0, grade_pre_liber3=0, grade_pre_liber4=0, grade_pre_liber5=0, grade_pre_liber6=0;
                double grade_post_liber0=0, grade_post_liber1=0, grade_post_liber2=0, grade_post_liber3=0, grade_post_liber4=0, grade_post_liber5=0, grade_post_liber6=0;
                double grade_pre_liber[] = {grade_pre_liber0, grade_pre_liber1, grade_pre_liber2, grade_pre_liber3, grade_pre_liber4, grade_pre_liber5, grade_pre_liber6};
                double grade_post_liber[] = {grade_post_liber0, grade_post_liber1, grade_post_liber2, grade_post_liber3, grade_post_liber4, grade_post_liber5, grade_post_liber6};

                //
                double grade_pre_all_total = 0, grade_pre_major_total=0, grade_pre_liber_total;
                double grade_post_all_total = 0, grade_post_major_total=0, grade_post_liber_total=0;

                //[Change Grade] 평점 초기값 저장
                for (int i=0; i<7; i++){
                    grade_pre_all[i] = Double.parseDouble(tv_totalGrade_list[i].getText().toString());
                    grade_pre_major[i] = Double.parseDouble(tv_majorGrade_list[i].getText().toString());
                    grade_pre_liber[i] = Double.parseDouble(tv_liberalGrade_list[i].getText().toString());
                }

                grade_pre_all_total = Double.parseDouble(tv_totalGrade.getText().toString());
                grade_pre_major_total = Double.parseDouble(tv_majorGrade.getText().toString());
                grade_pre_liber_total = Double.parseDouble(tv_liberalGrade.getText().toString());

                switch (checkedId){
                    case R.id.rb_grade_version0:
                        if (version == 2){
                            for (int i=0; i<7; i++){
                                grade_post_all[i] = grade_pre_all[i] * 4.3 / 4.5;
                                grade_post_major[i] = grade_pre_major[i] * 4.3 / 4.5;
                                grade_post_liber[i] = grade_pre_liber[i] * 4.3 / 4.5;
                                tv_totalGrade_list[i].setText(String.format("%.2f", grade_post_all[i]));
                                tv_majorGrade_list[i].setText(String.format("%.2f", grade_post_major[i]));
                                tv_liberalGrade_list[i].setText(String.format("%.2f", grade_post_liber[i]));
                                grade_post_all_total = grade_pre_all_total * 4.3 / 4.5;
                                grade_post_major_total = grade_pre_major_total * 4.3 / 4.5;
                                grade_post_liber_total = grade_pre_liber_total * 4.3 / 4.5;
                            }
                        } else if (version == 3){
                            for (int i=0; i<7; i++){
                                grade_post_all[i] = grade_pre_all[i] * 4.3 / 100;
                                grade_post_major[i] = grade_pre_major[i] * 4.3 / 100;
                                grade_post_liber[i] = grade_pre_liber[i] * 4.3 / 100;
                                tv_totalGrade_list[i].setText(String.format("%.2f", grade_post_all[i]));
                                tv_majorGrade_list[i].setText(String.format("%.2f", grade_post_major[i]));
                                tv_liberalGrade_list[i].setText(String.format("%.2f", grade_post_liber[i]));
                                grade_post_all_total = grade_pre_all_total * 4.3 / 100;
                                grade_post_major_total = grade_pre_major_total * 4.3 / 100;
                                grade_post_liber_total = grade_pre_liber_total * 4.3 / 100;
                            }
                        }
                        tv_totalGrade.setText(String.format("%.2f", grade_post_all_total));
                        tv_majorGrade.setText(String.format("%.2f", grade_post_major_total));
                        tv_liberalGrade.setText(String.format("%.2f", grade_post_liber_total));
                        version = 1;
                        break;
                    case R.id.rb_grade_version1:    // 4.5 버튼을 클릭했을 경우
                        if (version == 1){
                            for (int i=0; i<7; i++){
                                grade_post_all[i] = grade_pre_all[i] * 4.5 / 4.3;
                                grade_post_major[i] = grade_pre_major[i] * 4.5 / 4.3;
                                grade_post_liber[i] = grade_pre_liber[i] * 4.5 / 4.3;
                                tv_totalGrade_list[i].setText(String.format("%.2f", grade_post_all[i]));
                                tv_majorGrade_list[i].setText(String.format("%.2f", grade_post_major[i]));
                                tv_liberalGrade_list[i].setText(String.format("%.2f", grade_post_liber[i]));
                                grade_post_all_total = grade_pre_all_total * 4.5 / 4.3;
                                grade_post_major_total = grade_pre_major_total * 4.5 / 4.3;
                                grade_post_liber_total = grade_pre_liber_total * 4.5 / 4.3;
                            }
                        } else if (version == 3){
                            for (int i=0; i<7; i++){
                                grade_post_all[i] = grade_pre_all[i] * 4.5 / 100;
                                grade_post_major[i] = grade_pre_major[i] * 4.5 / 100;
                                grade_post_liber[i] = grade_pre_liber[i] * 4.5 / 100;
                                tv_totalGrade_list[i].setText(String.format("%.2f", grade_post_all[i]));
                                tv_majorGrade_list[i].setText(String.format("%.2f", grade_post_major[i]));
                                tv_liberalGrade_list[i].setText(String.format("%.2f", grade_post_liber[i]));
                                grade_post_all_total = grade_pre_all_total * 4.5 / 100;
                                grade_post_major_total = grade_pre_major_total * 4.5 / 100;
                                grade_post_liber_total = grade_pre_liber_total * 4.5 / 100;
                            }
                        }
                        tv_totalGrade.setText(String.format("%.2f", grade_post_all_total));
                        tv_majorGrade.setText(String.format("%.2f", grade_post_major_total));
                        tv_liberalGrade.setText(String.format("%.2f", grade_post_liber_total));
                        version = 2;
                        break;
                    case R.id.rb_grade_version2:
                        if (version == 1){
                            for (int i=0; i<7; i++){
                                grade_post_all[i] = grade_pre_all[i] * 100 / 4.3;
                                grade_post_major[i] = grade_pre_major[i] * 100 / 4.3;
                                grade_post_liber[i] = grade_pre_liber[i] * 100 / 4.3;
                                tv_totalGrade_list[i].setText(String.format("%.2f", grade_post_all[i]));
                                tv_majorGrade_list[i].setText(String.format("%.2f", grade_post_major[i]));
                                tv_liberalGrade_list[i].setText(String.format("%.2f", grade_post_liber[i]));
                                grade_post_all_total = grade_pre_all_total * 100 / 4.3;
                                grade_post_major_total = grade_pre_major_total * 100 / 4.3;
                                grade_post_liber_total = grade_pre_liber_total * 100 / 4.3;
                            }
                        } else if (version == 2){
                            for (int i=0; i<7; i++){
                                grade_post_all[i] = grade_pre_all[i] * 100 / 4.5;
                                grade_post_major[i] = grade_pre_major[i] * 100 / 4.5;
                                grade_post_liber[i] = grade_pre_liber[i] * 100 / 4.5;
                                tv_totalGrade_list[i].setText(String.format("%.2f", grade_post_all[i]));
                                tv_majorGrade_list[i].setText(String.format("%.2f", grade_post_major[i]));
                                tv_liberalGrade_list[i].setText(String.format("%.2f", grade_post_liber[i]));
                                grade_post_all_total = grade_pre_all_total * 100 / 4.5;
                                grade_post_major_total = grade_pre_major_total * 100 / 4.5;
                                grade_post_liber_total = grade_pre_liber_total * 100 / 4.5;
                            }
                        }
                        tv_totalGrade.setText(String.format("%.2f", grade_post_all_total));
                        tv_majorGrade.setText(String.format("%.2f", grade_post_major_total));
                        tv_liberalGrade.setText(String.format("%.2f", grade_post_liber_total));
                        version = 3;
                        break;
                }
            }
        });

        //-------------------------------------------------//
        //[RecyclerView] HashMap 사용
        gmSemesterList = new ArrayList<HashMap<String, String>>();

        //[RecyclerView] 로딩이 걸릴 경우 로딩 Dialog 출력
        progressDialog = new ProgressDialog(GmActivity.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        for (int i=0; i<8; i++){
            open_gm_arrow_list[i].setOnClickListener(this);
            open_gm_semester_list[i].setOnClickListener(this);
        }

        //-------------------------------------------------//
        //[하단바] Button parameter 선언
        LinearLayout naviBtn_curriculum = findViewById(R.id.naviBtn_curriculum);
        LinearLayout naviBtn_jjimList = findViewById(R.id.naviBtn_jjimList);
        LinearLayout naviBtn_lectureRecommendation = findViewById(R.id.naviBtn_lectureRecommendation);
        LinearLayout naviBtn_gradeManagement = findViewById(R.id.naviBtn_gradeManagement);
        LinearLayout naviBtn_myPage = findViewById(R.id.naviBtn_myPage);

        naviBtn_curriculum.setOnClickListener(this);
        naviBtn_jjimList.setOnClickListener(this);
        naviBtn_lectureRecommendation.setOnClickListener(this);
        naviBtn_gradeManagement.setOnClickListener(this);
        naviBtn_myPage.setOnClickListener(this);
    }

    private void init() {
        Context context;

        /*for (int i=0; i<8; i++){
            int course_res_id = getResources().getIdentifier("rc_gm_course"+i, "id", getPackageName());
            rc_gm_course_list[i] = findViewById(course_res_id);
            lm_list[i] = new LinearLayoutManager(this);
            rc_gm_course_list[i].setLayoutManager(lm_list[i]);
            adapt_list[i] = new GmRecyclerAdapter(this);
            rc_gm_course_list[i].setAdapter(adapt_list[i]);
        }*/

        RecyclerView semes11 = findViewById(R.id.rc_gm_course0);
        LinearLayoutManager lm11 = new LinearLayoutManager(this);
        semes11.setLayoutManager(lm11);
        adapt11 = new GmRecyclerAdapter(this);
        semes11.setAdapter(adapt11);

        RecyclerView semes12 = findViewById(R.id.rc_gm_course1);
        LinearLayoutManager lm12 = new LinearLayoutManager(this);
        semes12.setLayoutManager(lm12);
        adapt12 = new GmRecyclerAdapter(this);
        semes12.setAdapter(adapt12);
        RecyclerView semes21 = findViewById(R.id.rc_gm_course2);
        LinearLayoutManager lm21 = new LinearLayoutManager(this);
        semes21.setLayoutManager(lm21);
        adapt21 = new GmRecyclerAdapter(this);
        semes21.setAdapter(adapt21);
        RecyclerView semes22 = findViewById(R.id.rc_gm_course3);
        LinearLayoutManager lm22 = new LinearLayoutManager(this);
        semes22.setLayoutManager(lm22);
        adapt22 = new GmRecyclerAdapter(this);
        semes22.setAdapter(adapt22);
        RecyclerView semes31 = findViewById(R.id.rc_gm_course4);
        LinearLayoutManager lm31 = new LinearLayoutManager(this);
        semes31.setLayoutManager(lm31);
        adapt31 = new GmRecyclerAdapter(this);
        semes31.setAdapter(adapt31);
        RecyclerView semes32 = findViewById(R.id.rc_gm_course5);
        LinearLayoutManager lm32 = new LinearLayoutManager(this);
        semes32.setLayoutManager(lm32);
        adapt32 = new GmRecyclerAdapter(this);
        semes32.setAdapter(adapt32);
        RecyclerView semes41 = findViewById(R.id.rc_gm_course6);
        LinearLayoutManager lm41 = new LinearLayoutManager(this);
        semes41.setLayoutManager(lm41);
        adapt41 = new GmRecyclerAdapter(this);
        semes41.setAdapter(adapt41);
        RecyclerView semes42 = findViewById(R.id.rc_gm_course7);
        LinearLayoutManager lm42 = new LinearLayoutManager(this);
        semes42.setLayoutManager(lm42);
        adapt42 = new GmRecyclerAdapter(this);
        semes42.setAdapter(adapt42);
    }

    //[RecyclerView]
    private final RC_MyHandler RC_mHandler = new RC_MyHandler(this);
    //[RecyclerView]
    private static class RC_MyHandler extends Handler {
        private final WeakReference<GmActivity> weakReference;

        public RC_MyHandler(GmActivity gmActivity) {
            weakReference = new WeakReference<GmActivity>(gmActivity);
        }

        public void handleMessage(Message msg) {
            GmActivity gmActivity = weakReference.get();

            if (gmActivity != null) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        gmActivity.progressDialog.dismiss();
                        gmActivity.adapt11.notifyDataSetChanged();
                        gmActivity.adapt12.notifyDataSetChanged();
                        gmActivity.adapt21.notifyDataSetChanged();
                        gmActivity.adapt22.notifyDataSetChanged();
                        gmActivity.adapt31.notifyDataSetChanged();
                        gmActivity.adapt32.notifyDataSetChanged();
                        gmActivity.adapt41.notifyDataSetChanged();
                        gmActivity.adapt42.notifyDataSetChanged();
                        break;
                }
            }
        }
    }

    private void getData(String Request_url) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result;
                try {
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
                } catch (Exception e) {
                    result = e.toString();
                }
                if (RC_jsonParser(result)) {     // 파싱 기능 사용
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
            JSONArray gmSemester = jsonObject.getJSONArray("hakjum");

            gmSemesterList.clear();

            //gmSemester 길이만큼 반복해서 Mapping
            for (int i = 0; i < gmSemester.length(); i++) {
                JSONObject gmSemesterInfo = gmSemester.getJSONObject(i);

                String course_year = gmSemesterInfo.getString("course_year");
                String course_semester = gmSemesterInfo.getString("course_semester");
                String major_division = gmSemesterInfo.getString("major_division");
                String course_id = gmSemesterInfo.getString("course_id");
                String course_name = gmSemesterInfo.getString("course_name");
                String credit = gmSemesterInfo.getString("credit");
                String grade = gmSemesterInfo.getString("grade");
                String category = gmSemesterInfo.getString("category");

                DataGmList data = new DataGmList(course_year, course_semester, major_division, course_id, course_name, credit, grade, category);

                if (course_year.equals("2019") && course_semester.equals("1")) {
                    adapt11.addItem(data);
                } else if (course_year.equals("2019") && course_semester.equals("2")) {
                    adapt12.addItem(data);
                } else if (course_year.equals("2020") && course_semester.equals("1")) {
                    adapt21.addItem(data);
                } else if (course_year.equals("2020") && course_semester.equals("2")) {
                    adapt22.addItem(data);
                } else if (course_year.equals("2021") && course_semester.equals("1")) {
                    adapt31.addItem(data);
                } else if (course_year.equals("2021") && course_semester.equals("2")) {
                    adapt32.addItem(data);
                } else if (course_year.equals("2022") && course_semester.equals("1")) {
                    adapt41.addItem(data);
                } else if (course_year.equals("2022") && course_semester.equals("2")) {
                    adapt42.addItem(data);
                }
            }

            return true;
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
        return false;
    }

    //-------------------------------------------------//
    //[Grade] DB에서 값 가져와서 setText하는 함수
    private void getData_Grade(String Request_url, int num_semester, int num_version) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result;
                try {
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
                } catch (Exception e) {
                    result = e.toString();
                }
                //파싱 기능 사용
                RC_jsonParser_Grade(result, num_semester, num_version);
            }
        });
        thread.start();
    }

    //웹사이트 화면을 파싱하는 함수
    public void RC_jsonParser_Grade(String jsonString, int num_semester, int num_version) {

        /*if (jsonString == null) return false;*/

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            //전체 행렬 중 DB 내용 부분을 jsonArray 형태로 저장
            JSONArray gmSemester = jsonObject.getJSONArray("result");

            //gmSemester 길이만큼 반복해서 Mapping
            for (int i = 0; i < gmSemester.length(); i++) {
                JSONObject gmSemesterInfo = gmSemester.getJSONObject(i);

                String course_year = gmSemesterInfo.getString("course_year");
                String course_semester = gmSemesterInfo.getString("course_semester");

                double sum_grade_ver1_all, sum_grade_ver1_major, sum_grade_ver1_liber;
                double sum_credit_all, sum_credit_major, sum_credit_liber;
                double avg_ver1_all, avg_ver1_major, avg_ver1_liber;

                if (!course_year.equals("course_year")) {
                    for (int j = 0; j < 8; j++) {
                        int grade_res_id = getResources().getIdentifier("gm_list_totalGrade" + j, "id", getPackageName());
                        int major_res_id = getResources().getIdentifier("gm_list_majorGrade" + j, "id", getPackageName());
                        int liberal_res_id = getResources().getIdentifier("gm_list_liberalGrade" + j, "id", getPackageName());
                        tv_totalGrade_list[j] = findViewById(grade_res_id);
                        tv_majorGrade_list[j] = findViewById(major_res_id);
                        tv_liberalGrade_list[j] = findViewById(liberal_res_id);
                    }

                    //[총 평점]
                    sum_grade_ver1_all = Double.parseDouble(gmSemesterInfo.getString("sum_grade_ver" + num_version + "_all"));
                    sum_credit_all = Double.parseDouble(gmSemesterInfo.getString("sum_credit_all"));
                    avg_ver1_all = sum_grade_ver1_all / sum_credit_all;
                    tv_totalGrade_list[num_semester].setText(String.format("%.2f", avg_ver1_all));

                    //[전공 평점]
                    sum_grade_ver1_major = Double.parseDouble(gmSemesterInfo.getString("sum_grade_ver" + num_version + "_major"));
                    sum_credit_major = Double.parseDouble(gmSemesterInfo.getString("sum_credit_major"));
                    avg_ver1_major = sum_grade_ver1_major / sum_credit_major;
                    tv_majorGrade_list[num_semester].setText(String.format("%.2f", avg_ver1_major));

                    //[교양 평점]
                    sum_grade_ver1_liber = Double.parseDouble(gmSemesterInfo.getString("sum_grade_ver" + num_version + "_liber"));
                    sum_credit_liber = Double.parseDouble(gmSemesterInfo.getString("sum_credit_liber"));
                    avg_ver1_liber = sum_grade_ver1_liber / sum_credit_liber;
                    tv_liberalGrade_list[num_semester].setText(String.format("%.2f", avg_ver1_liber));
                } else {
                    tv_totalGrade = findViewById(R.id.totalGrade);
                    tv_majorGrade = findViewById(R.id.majorGrade);
                    tv_liberalGrade = findViewById(R.id.liberalGrade);

                    //[총 평점]
                    sum_grade_ver1_all = Double.parseDouble(gmSemesterInfo.getString("sum_grade_ver" + num_version + "_all"));
                    sum_credit_all = Double.parseDouble(gmSemesterInfo.getString("sum_credit_all"));
                    avg_ver1_all = sum_grade_ver1_all / sum_credit_all;
                    tv_totalGrade.setText(String.format("%.2f", avg_ver1_all));

                    //[전공 평점]
                    sum_grade_ver1_major = Double.parseDouble(gmSemesterInfo.getString("sum_grade_ver" + num_version + "_major"));
                    sum_credit_major = Double.parseDouble(gmSemesterInfo.getString("sum_credit_major"));
                    avg_ver1_major = sum_grade_ver1_major / sum_credit_major;
                    tv_majorGrade.setText(String.format("%.2f", avg_ver1_major));

                    //[교양 평점]
                    sum_grade_ver1_liber = Double.parseDouble(gmSemesterInfo.getString("sum_grade_ver" + num_version + "_liber"));
                    sum_credit_liber = Double.parseDouble(gmSemesterInfo.getString("sum_credit_liber"));
                    avg_ver1_liber = sum_grade_ver1_liber / sum_credit_liber;
                    tv_liberalGrade.setText(String.format("%.2f", avg_ver1_liber));
                }

            }
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
    }

    private double gradeToNum(String grade) {
        if (grade.equals("A+")) {
            return 4.3;
        } else if (grade.equals("A")) {
            return 4.0;
        } else if (grade.equals("A-")) {
            return 3.7;
        } else if (grade.equals("B+")) {
            return 3.3;
        } else if (grade.equals("B")) {
            return 3.0;
        } else if (grade.equals("B-")) {
            return 2.7;
        } else if (grade.equals("C+")) {
            return 2.3;
        } else if (grade.equals("C")) {
            return 2.0;
        } else if (grade.equals("C-")) {
            return 1.7;
        } else if (grade.equals("D+")) {
            return 1.3;
        } else if (grade.equals("D")) {
            return 1.0;
        } else if (grade.equals("D-")) {
            return 0.7;
        } else if (grade.equals("F")) {
            return 0.0;
        }
        return 0.0;
    }

    public void onClick(View view) {
        for (int i=0; i<8; i++){
            int course_res_id = getResources().getIdentifier("rc_gm_course"+i, "id", getPackageName());
            rc_gm_course_list[i] = findViewById(course_res_id);
        }

        switch (view.getId()) {
            case R.id.open_gm_semester0:
            case R.id.open_gm_arrow0:
                if(open_or_not){
                    rc_gm_course_list[0].setVisibility(View.GONE);
                    open_gm_arrow_list[0].setImageResource(R.drawable.arrow_down);
                    open_or_not = false;
                } else{
                    rc_gm_course_list[0].setVisibility(View.VISIBLE);
                    open_gm_arrow_list[0].setImageResource(R.drawable.arrow_up);
                    open_or_not = true;
                }
                    break;
            case R.id.open_gm_semester1:
            case R.id.open_gm_arrow1:
                if(open_or_not){
                    rc_gm_course_list[1].setVisibility(View.GONE);
                    open_gm_arrow_list[1].setImageResource(R.drawable.arrow_down);
                    open_or_not = false;
                } else{
                    rc_gm_course_list[1].setVisibility(View.VISIBLE);
                    open_gm_arrow_list[1].setImageResource(R.drawable.arrow_up);
                    open_or_not = true;
                }
                break;
            case R.id.open_gm_semester2:
            case R.id.open_gm_arrow2:
                if(open_or_not){
                    rc_gm_course_list[2].setVisibility(View.GONE);
                    open_gm_arrow_list[2].setImageResource(R.drawable.arrow_down);
                    open_or_not = false;
                } else{rc_gm_course_list[2].setVisibility(View.VISIBLE);
                    open_gm_arrow_list[2].setImageResource(R.drawable.arrow_up);
                    open_or_not = true;
                }
                break;
            case R.id.open_gm_semester3:
            case R.id.open_gm_arrow3:
                if(open_or_not){
                    rc_gm_course_list[3].setVisibility(View.GONE);
                    open_gm_arrow_list[3].setImageResource(R.drawable.arrow_down);
                    open_or_not = false;
                } else{
                    rc_gm_course_list[3].setVisibility(View.VISIBLE);
                    open_gm_arrow_list[3].setImageResource(R.drawable.arrow_up);
                    open_or_not = true;
                }
                break;
            case R.id.open_gm_semester4:
            case R.id.open_gm_arrow4:
                if(open_or_not){
                    rc_gm_course_list[4].setVisibility(View.GONE);
                    open_gm_arrow_list[4].setImageResource(R.drawable.arrow_down);
                    open_or_not = false;
                } else{
                    rc_gm_course_list[4].setVisibility(View.VISIBLE);
                    open_gm_arrow_list[4].setImageResource(R.drawable.arrow_up);
                    open_or_not = true;
                }
                break;
            case R.id.open_gm_semester5:
            case R.id.open_gm_arrow5:
                if(open_or_not){
                    rc_gm_course_list[5].setVisibility(View.GONE);
                    open_gm_arrow_list[5].setImageResource(R.drawable.arrow_down);
                    open_or_not = false;
                } else{
                    rc_gm_course_list[5].setVisibility(View.VISIBLE);
                    open_gm_arrow_list[5].setImageResource(R.drawable.arrow_up);
                    open_or_not = true;
                }
                break;
            case R.id.open_gm_semester6:
            case R.id.open_gm_arrow6:
                if(open_or_not){
                    rc_gm_course_list[6].setVisibility(View.GONE);
                    open_gm_arrow_list[6].setImageResource(R.drawable.arrow_down);
                    open_or_not = false;
                } else{
                    rc_gm_course_list[6].setVisibility(View.VISIBLE);
                    open_gm_arrow_list[6].setImageResource(R.drawable.arrow_up);
                    open_or_not = true;
                }
                break;
            case R.id.open_gm_semester7:
            case R.id.open_gm_arrow7:
                if(open_or_not){
                    rc_gm_course_list[7].setVisibility(View.GONE);
                    open_gm_arrow_list[7].setImageResource(R.drawable.arrow_down);
                    open_or_not = false;
                } else{
                    rc_gm_course_list[7].setVisibility(View.VISIBLE);
                    open_gm_arrow_list[7].setImageResource(R.drawable.arrow_up);
                    open_or_not = true;
                }
                break;

                //[하단네비]
            case R.id.naviBtn_curriculum:
                Intent intent_curriculum = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_curriculum);
                break;
            case R.id.naviBtn_jjimList:
                Intent intent_jjimList = new Intent(getApplicationContext(), JjimActivity.class);
                startActivity(intent_jjimList);
                break;
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