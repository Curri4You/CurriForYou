//학점관리 메인페이지

package com.example.curriforyou;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    LinearLayout ll_listsemester;
    TextView tv_semester;
    AlertDialog.Builder builder;
    String[] semester;
    //Adapter 연결
    /*GmRecyclerAdapter adapter = null;*/
    // [RecyclerView] 리스트 출력을 위한 parameter
    private static final String TAG = "imagesearchexample";
    // URL - 학기별 학점요약 DB
    private String REQUEST_URL = "http://smlee099.dothome.co.kr/hakjum.php";
    public static final int LOAD_SUCCESS = 101;
    private ProgressDialog progressDialog = null;
    GmRecyclerAdapter adapt11 = null;
    GmRecyclerAdapter adapt12 = null;
    GmRecyclerAdapter adapt21 = null;
    GmRecyclerAdapter adapt22 = null;
    GmRecyclerAdapter adapt31 = null;
    GmRecyclerAdapter adapt32 = null;
    GmRecyclerAdapter adapt41 = null;
    GmRecyclerAdapter adapt42 = null;
    private ArrayList<HashMap<String, String>> gmSemesterList = null;

    double gradeToDouble = 0, totalGrade = 0, totalGrade11 = 0, totalGrade12 = 0, totalGrade21 = 0, totalGrade22 = 0, totalGrade31 = 0, totalGrade32 = 0, totalGrade41 = 0, totalGrade42 = 0;
    double majorGrade = 0, majorGrade11 = 0, majorGrade12 = 0, majorGrade21 = 0, majorGrade22 = 0, majorGrade31 = 0, majorGrade32 = 0, majorGrade41 = 0, majorGrade42 = 0;
    double liberalGrade = 0, liberalGrade11 = 0, liberalGrade12 = 0, liberalGrade21 = 0, liberalGrade22 = 0, liberalGrade31 = 0, liberalGrade32 = 0, liberalGrade41 = 0, liberalGrade42 = 0;
    int totalCredit = 0, credit11 = 0, credit12 = 0, credit21 = 0, credit22 = 0, credit31 = 0, credit32 = 0, credit41 = 0, credit42 = 0;
    int majorCredit = 0, majorCredit11 = 0, majorCredit12 = 0, majorCredit21 = 0, majorCredit22 = 0, majorCredit31 = 0, majorCredit32 = 0, majorCredit41 = 0, majorCredit42 = 0;
    int liberalCredit = 0, liberalCredit11 = 0, liberalCredit12 = 0, liberalCredit21 = 0, liberalCredit22 = 0, liberalCredit31 = 0, liberalCredit32 = 0, liberalCredit41 = 0, liberalCredit42 = 0;

    TextView tv_totalGrade, tv_majorGrade, tv_liberalGrade, tv_tillNowCredit;
    TextView tv_totalGrade11, tv_majorGrade11, tv_liberalGrade11;
    TextView tv_totalGrade12, tv_majorGrade12, tv_liberalGrade12;
    TextView tv_totalGrade21, tv_majorGrade21, tv_liberalGrade21;
    TextView tv_totalGrade22, tv_majorGrade22, tv_liberalGrade22;
    TextView tv_totalGrade31, tv_majorGrade31, tv_liberalGrade31;
    TextView tv_totalGrade32, tv_majorGrade32, tv_liberalGrade32;
    TextView tv_totalGrade41, tv_majorGrade41, tv_liberalGrade41;
    TextView tv_totalGrade42, tv_majorGrade42, tv_liberalGrade42;
    ProgressBar pb_total_grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gm_home);

        init();
        getData(REQUEST_URL);

        // 학점 평점 정리
        tv_totalGrade = (TextView) findViewById(R.id.totalGrade);
        tv_majorGrade = (TextView) findViewById(R.id.majorGrade);
        tv_liberalGrade = (TextView) findViewById(R.id.liberalGrade);
        tv_tillNowCredit = (TextView) findViewById(R.id.tillNowCredit);
        pb_total_grade = (ProgressBar) findViewById(R.id.pb_total_grade);
        // 학기별 평점 정리
        tv_totalGrade11 = (TextView) findViewById(R.id.gm_list_totalGrade11);
        tv_majorGrade11 = (TextView) findViewById(R.id.gm_list_majorGrade11);
        tv_liberalGrade11 = (TextView) findViewById(R.id.gm_list_liberalGrade11);
        tv_totalGrade12 = (TextView) findViewById(R.id.gm_list_totalGrade12);
        tv_majorGrade12 = (TextView) findViewById(R.id.gm_list_majorGrade12);
        tv_liberalGrade12 = (TextView) findViewById(R.id.gm_list_liberalGrade12);
        tv_totalGrade21 = (TextView) findViewById(R.id.gm_list_totalGrade21);
        tv_majorGrade21 = (TextView) findViewById(R.id.gm_list_majorGrade21);
        tv_liberalGrade21 = (TextView) findViewById(R.id.gm_list_liberalGrade21);
        tv_totalGrade22 = (TextView) findViewById(R.id.gm_list_totalGrade22);
        tv_majorGrade22 = (TextView) findViewById(R.id.gm_list_majorGrade22);
        tv_liberalGrade22 = (TextView) findViewById(R.id.gm_list_liberalGrade22);
        tv_totalGrade31 = (TextView) findViewById(R.id.gm_list_totalGrade31);
        tv_majorGrade31 = (TextView) findViewById(R.id.gm_list_majorGrade31);
        tv_liberalGrade31 = (TextView) findViewById(R.id.gm_list_liberalGrade31);
        tv_totalGrade32 = (TextView) findViewById(R.id.gm_list_totalGrade32);
        tv_majorGrade32 = (TextView) findViewById(R.id.gm_list_majorGrade32);
        tv_liberalGrade32 = (TextView) findViewById(R.id.gm_list_liberalGrade32);
        tv_totalGrade41 = (TextView) findViewById(R.id.gm_list_totalGrade41);
        tv_majorGrade41 = (TextView) findViewById(R.id.gm_list_majorGrade41);
        tv_liberalGrade41 = (TextView) findViewById(R.id.gm_list_liberalGrade41);
        tv_totalGrade42 = (TextView) findViewById(R.id.gm_list_totalGrade42);
        tv_majorGrade42 = (TextView) findViewById(R.id.gm_list_majorGrade42);
        tv_liberalGrade42 = (TextView) findViewById(R.id.gm_list_liberalGrade42);

        //[하단바] Button parameter 선언
        LinearLayout naviBtn_curriculum = (LinearLayout) findViewById(R.id.naviBtn_curriculum);
        LinearLayout naviBtn_jjimList = (LinearLayout) findViewById(R.id.naviBtn_jjimList);
        LinearLayout naviBtn_lectureRecommendation = (LinearLayout) findViewById(R.id.naviBtn_lectureRecommendation);
        LinearLayout naviBtn_gradeManagement = (LinearLayout) findViewById(R.id.naviBtn_gradeManagement);
        LinearLayout naviBtn_myPage = (LinearLayout) findViewById(R.id.naviBtn_myPage);

        //[RecyclerView] HashMap 사용
        gmSemesterList = new ArrayList<HashMap<String, String>>();
        //[RecyclerView] 각 필드값을 매핑, from -> to
        String[] from = new String[]{"course_id", "course_name", "credit", "grade"};
        int[] to = new int[]{R.id.tv_course_id, R.id.tv_course_name, R.id.tv_credit, R.id.rb_grade};

        //[RecyclerView] 로딩이 걸릴 경우 로딩 Dialog 출력
        progressDialog = new ProgressDialog(GmActivity.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        tv_totalGrade.setText(String.valueOf(totalGrade));
        tv_majorGrade.setText(String.valueOf(majorGrade));
        tv_liberalGrade.setText(String.valueOf(liberalGrade));
        tv_tillNowCredit.setText(String.valueOf(totalCredit));
        pb_total_grade.setProgress(totalCredit);
        tv_totalGrade11.setText(String.valueOf(totalGrade11));
        tv_majorGrade11.setText(String.valueOf(majorGrade11));
        tv_liberalGrade11.setText(String.valueOf(liberalGrade11));
        tv_totalGrade12.setText(String.valueOf(totalGrade12));
        tv_majorGrade12.setText(String.valueOf(majorGrade12));
        tv_liberalGrade12.setText(String.valueOf(liberalGrade12));
        tv_totalGrade21.setText(String.valueOf(totalGrade21));
        tv_majorGrade21.setText(String.valueOf(majorGrade21));
        tv_liberalGrade21.setText(String.valueOf(liberalGrade21));
        tv_totalGrade22.setText(String.valueOf(totalGrade22));
        tv_majorGrade22.setText(String.valueOf(majorGrade22));
        tv_liberalGrade22.setText(String.valueOf(liberalGrade22));
        tv_totalGrade31.setText(String.valueOf(totalGrade31));
        tv_majorGrade31.setText(String.valueOf(majorGrade31));
        tv_liberalGrade31.setText(String.valueOf(liberalGrade31));
        tv_totalGrade32.setText(String.valueOf(totalGrade32));
        tv_majorGrade32.setText(String.valueOf(majorGrade32));
        tv_liberalGrade32.setText(String.valueOf(liberalGrade32));
        tv_totalGrade41.setText(String.valueOf(totalGrade41));
        tv_majorGrade41.setText(String.valueOf(majorGrade41));
        tv_liberalGrade41.setText(String.valueOf(liberalGrade41));
        tv_totalGrade42.setText(String.valueOf(totalGrade42));
        tv_majorGrade42.setText(String.valueOf(majorGrade42));
        tv_liberalGrade42.setText(String.valueOf(liberalGrade42));

        naviBtn_curriculum.setOnClickListener(this);
        naviBtn_jjimList.setOnClickListener(this);
        naviBtn_lectureRecommendation.setOnClickListener(this);
        naviBtn_gradeManagement.setOnClickListener(this);
        naviBtn_myPage.setOnClickListener(this);
    }

    private void init() {
        Context context;

        RecyclerView semes11 = findViewById(R.id.rc_gm_course11);
        LinearLayoutManager lm11 = new LinearLayoutManager(this);
        semes11.setLayoutManager(lm11);
        adapt11 = new GmRecyclerAdapter(this);
        semes11.setAdapter(adapt11);
        RecyclerView semes12 = findViewById(R.id.rc_gm_course12);
        LinearLayoutManager lm12 = new LinearLayoutManager(this);
        semes12.setLayoutManager(lm12);
        adapt12 = new GmRecyclerAdapter(this);
        semes12.setAdapter(adapt12);
        RecyclerView semes21 = findViewById(R.id.rc_gm_course21);
        LinearLayoutManager lm21 = new LinearLayoutManager(this);
        semes21.setLayoutManager(lm21);
        adapt21 = new GmRecyclerAdapter(this);
        semes21.setAdapter(adapt21);
        RecyclerView semes22 = findViewById(R.id.rc_gm_course22);
        LinearLayoutManager lm22 = new LinearLayoutManager(this);
        semes22.setLayoutManager(lm22);
        adapt22 = new GmRecyclerAdapter(this);
        semes22.setAdapter(adapt22);
        RecyclerView semes31 = findViewById(R.id.rc_gm_course31);
        LinearLayoutManager lm31 = new LinearLayoutManager(this);
        semes31.setLayoutManager(lm31);
        adapt31 = new GmRecyclerAdapter(this);
        semes31.setAdapter(adapt31);
        RecyclerView semes32 = findViewById(R.id.rc_gm_course32);
        LinearLayoutManager lm32 = new LinearLayoutManager(this);
        semes32.setLayoutManager(lm32);
        adapt32 = new GmRecyclerAdapter(this);
        semes32.setAdapter(adapt32);
        RecyclerView semes41 = findViewById(R.id.rc_gm_course41);
        LinearLayoutManager lm41 = new LinearLayoutManager(this);
        semes41.setLayoutManager(lm41);
        adapt41 = new GmRecyclerAdapter(this);
        semes41.setAdapter(adapt41);
        RecyclerView semes42 = findViewById(R.id.rc_gm_course42);
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

                if (course_year.equals("2019") && course_semester.equals("1")) {
                    DataGmList data = new DataGmList(course_year, course_semester, major_division, course_id, course_name, credit, grade, category);
                    adapt11.addItem(data);
                    gradeToDouble = gradeToNum(grade);
                    if (category.equals("1") || category.equals("2")) {
                        majorCredit11 += Integer.parseInt(credit);
                        majorGrade11 += gradeToDouble * Double.parseDouble(credit);
                    } else if (category.equals("3") || category.equals("4")) {
                        liberalCredit11 += Integer.parseInt(credit);
                        liberalGrade11 += gradeToDouble * Double.parseDouble(credit);
                    }
                } else if (course_year.equals("2019") && course_semester.equals("2")) {
                    DataGmList data = new DataGmList(course_year, course_semester, major_division, course_id, course_name, credit, grade, category);
                    adapt12.addItem(data);
                } else if (course_year.equals("2020") && course_semester.equals("1")) {
                    DataGmList data = new DataGmList(course_year, course_semester, major_division, course_id, course_name, credit, grade, category);
                    adapt21.addItem(data);
                } else if (course_year.equals("2020") && course_semester.equals("2")) {
                    DataGmList data = new DataGmList(course_year, course_semester, major_division, course_id, course_name, credit, grade, category);
                    adapt22.addItem(data);
                } else if (course_year.equals("2021") && course_semester.equals("1")) {
                    DataGmList data = new DataGmList(course_year, course_semester, major_division, course_id, course_name, credit, grade, category);
                    adapt31.addItem(data);
                } else if (course_year.equals("2021") && course_semester.equals("2")) {
                    DataGmList data = new DataGmList(course_year, course_semester, major_division, course_id, course_name, credit, grade, category);
                    adapt32.addItem(data);
                } else if (course_year.equals("2022") && course_semester.equals("1")) {
                    DataGmList data = new DataGmList(course_year, course_semester, major_division, course_id, course_name, credit, grade, category);
                    adapt41.addItem(data);
                } else if (course_year.equals("2022") && course_semester.equals("2")) {
                    DataGmList data = new DataGmList(course_year, course_semester, major_division, course_id, course_name, credit, grade, category);
                    adapt42.addItem(data);
                }

            }

            credit11 = majorCredit11 + liberalCredit11;
            totalGrade11 = (majorGrade11 + liberalGrade11) / credit11;
            if (majorCredit11 != 0) {
                majorGrade11 /= majorCredit11;
            }
            if (liberalCredit != 0) {
                liberalGrade11 /= liberalCredit11;
            }

            majorCredit = majorCredit11 + majorCredit12 + majorCredit21 + majorCredit22 + majorCredit31 + majorCredit32 + majorCredit41 + majorCredit42;
            liberalCredit = liberalCredit11 + liberalCredit12 + liberalCredit21 + liberalCredit22 + liberalCredit31 + liberalCredit32 + liberalCredit41 + liberalCredit42;
            totalCredit = majorCredit + liberalCredit;

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


    @Override
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
