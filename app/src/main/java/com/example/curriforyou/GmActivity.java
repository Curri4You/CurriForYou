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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    public static final int LOAD_SUCCESS = 101;
    private ProgressDialog progressDialog = null;
    GmRecyclerAdapter adapt11 = null, adapt12 = null, adapt21 = null, adapt22 = null,
            adapt31 = null, adapt32 = null, adapt41 = null, adapt42 = null;
    GmRecyclerAdapter adapt_list[] = {adapt11, adapt12, adapt21, adapt22, adapt31, adapt32, adapt41, adapt42};
    private ArrayList<HashMap<String, String>> gmSemesterList = null;
    private Context context;

    double gradeToDouble, totalGrade, totalGrade11, totalGrade12, totalGrade21, totalGrade22, totalGrade31, totalGrade32, totalGrade41, totalGrade42;
    double majorGrade, majorGrade11, majorGrade12, majorGrade21, majorGrade22, majorGrade31, majorGrade32, majorGrade41, majorGrade42;
    double liberalGrade, liberalGrade11, liberalGrade12, liberalGrade21, liberalGrade22, liberalGrade31, liberalGrade32, liberalGrade41, liberalGrade42;
    int totalCredit, credit11, credit12, credit21, credit22, credit31, credit32, credit41, credit42;
    int majorCredit, majorCredit11, majorCredit12, majorCredit21, majorCredit22, majorCredit31, majorCredit32, majorCredit41, majorCredit42;
    int liberalCredit, liberalCredit11, liberalCredit12, liberalCredit21, liberalCredit22, liberalCredit31, liberalCredit32, liberalCredit41, liberalCredit42;
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

    //////////
    LinearLayoutManager lm_list[] = new LinearLayoutManager[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gm_home);

        init();
        getData(REQUEST_URL);

        // 학점 평점 정리
        tv_totalGrade = findViewById(R.id.totalGrade);
        tv_majorGrade = findViewById(R.id.majorGrade);
        tv_liberalGrade = findViewById(R.id.liberalGrade);
        tv_tillNowCredit = findViewById(R.id.tillNowCredit);
        pb_total_grade = findViewById(R.id.pb_total_grade);

        // 학기별 평점 정리
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

        //[하단바] Button parameter 선언
        LinearLayout naviBtn_curriculum = findViewById(R.id.naviBtn_curriculum);
        LinearLayout naviBtn_jjimList = findViewById(R.id.naviBtn_jjimList);
        LinearLayout naviBtn_lectureRecommendation = findViewById(R.id.naviBtn_lectureRecommendation);
        LinearLayout naviBtn_gradeManagement = findViewById(R.id.naviBtn_gradeManagement);
        LinearLayout naviBtn_myPage = findViewById(R.id.naviBtn_myPage);

        //[RecyclerView] HashMap 사용
        gmSemesterList = new ArrayList<HashMap<String, String>>();

        //[RecyclerView] 로딩이 걸릴 경우 로딩 Dialog 출력
        progressDialog = new ProgressDialog(GmActivity.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        tv_totalGrade.setText(String.valueOf(totalGrade));
        tv_majorGrade.setText(String.valueOf(majorGrade));
        tv_liberalGrade.setText(String.valueOf(liberalGrade));
        tv_tillNowCredit.setText(String.valueOf(totalCredit));
        pb_total_grade.setProgress(totalCredit);

        for (int i=0; i<8; i++){
            tv_totalGrade_list[i].setText("0");
            tv_majorGrade_list[i].setText("0");
            tv_liberalGrade_list[i].setText("0");
        }

        for (int i=0; i<8; i++){
            open_gm_arrow_list[i].setOnClickListener(this);
            open_gm_semester_list[i].setOnClickListener(this);
        }

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

            credit11 = majorCredit11 + liberalCredit11;
            credit12 = majorCredit12 + liberalCredit12;
            credit21 = majorCredit21 + liberalCredit21;
            credit22 = majorCredit22 + liberalCredit22;
            credit31 = majorCredit31 + liberalCredit31;
            credit32 = majorCredit32 + liberalCredit32;
            credit41 = majorCredit41 + liberalCredit41;
            credit42 = majorCredit42 + liberalCredit42;
            totalCredit = credit11 + credit12 + credit21 + credit22 + credit31 + credit32 + credit41 + credit42;
            majorGrade = (majorGrade11 + majorGrade12 + majorGrade21 + majorGrade22 + majorGrade31 + majorGrade32 + majorGrade41 + majorGrade42);
            liberalGrade = (liberalGrade11 + liberalGrade12 + liberalGrade21 + liberalGrade22 + liberalGrade31 + liberalGrade32 + liberalGrade41 + liberalGrade42);
            totalGrade = majorGrade + liberalGrade;

            return true;
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
        return false;
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