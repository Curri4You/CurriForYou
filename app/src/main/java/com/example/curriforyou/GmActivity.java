//학점관리 메인페이지

package com.example.curriforyou;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    GmRecyclerAdapter rc_adapter = null;
    private ArrayList<HashMap<String, String>> gmSemesterList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gm_home);

        init();
        getData(REQUEST_URL);

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

        naviBtn_curriculum.setOnClickListener(this);
        naviBtn_jjimList.setOnClickListener(this);
        naviBtn_lectureRecommendation.setOnClickListener(this);
        naviBtn_gradeManagement.setOnClickListener(this);
        naviBtn_myPage.setOnClickListener(this);
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.rc_gm_course);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Context context;
        rc_adapter = new GmRecyclerAdapter(this);
        recyclerView.setAdapter(rc_adapter);
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
                        gmActivity.rc_adapter.notifyDataSetChanged();
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

                String course_semester = gmSemesterInfo.getString("course_semester");
                String major_division = gmSemesterInfo.getString("major_division");
                String course_id = gmSemesterInfo.getString("course_id");
                String course_name = gmSemesterInfo.getString("course_name");
                String credit = gmSemesterInfo.getString("credit");
                String grade = gmSemesterInfo.getString("grade");
                String category = gmSemesterInfo.getString("category");

                DataGmList data = new DataGmList(course_semester, major_division, course_id, course_name, credit, grade, category);
                rc_adapter.addItem(data);

            }
            return true;
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
        return false;
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
