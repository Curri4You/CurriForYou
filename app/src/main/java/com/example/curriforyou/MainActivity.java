package com.example.curriforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

    //[ListView] 리스트 출력을 위한 parameter
    private static final String TAG = "imagesearchexample";
    private  String REQUEST_URL = "http://smlee099.dothome.co.kr/CourseList.php";
    public static final int LOAD_SUCCESS = 101;
    private ProgressDialog progressDialog = null;
    private SimpleAdapter adapter = null;
    private ArrayList<HashMap<String, String>> courseList = null;

    //Recycler
    RecyclerVierAdapter rc_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);


        //[하단바] parameter 선언
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

        //Recycler
        init();
        getData();

        //[ListView] 커리큘럼 화면이 켜짐과 동시에 리스트가 출력되도록 getJSON 함수 사용
        ListView lv_curriculum_course = (ListView)findViewById(R.id.lv_curriculum_course);
        courseList = new ArrayList<HashMap<String, String>>();
        String[] from = new String[]{"course_name", "subject_id", "gpa", "is_english"};
        int[] to = new int[]{R.id.tv_course_name, R.id.tv_subject_id, R.id.tv_gpa, R.id.tv_is_english};
        adapter = new SimpleAdapter(this, courseList, R.layout.listview_item, from, to);
        lv_curriculum_course.setAdapter(adapter);

        //[ListView] 로딩이 걸릴 경우 로딩 Dialog 출력
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        //[ListView] ListView 출력하는 사용자 지정 함수
        getJSON();

        //////////////////
        //testing 용 - 무시해도 됨
        //TextView에 리스트에 들어갈 내용 나열출력
        Button btn_list = (Button) findViewById(R.id.btn_list);

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //요청 큐가 없으면 요청 큐 생성하기
                if(requestQueue == null){
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                }
                //course table 정보 토스트값으로 출력
                CourseList();
            }
        });
        //////////////////

        //LOGIN PAGE BUTTON 클릭 --> LOGIN PAGE 로 이동
        Button btn_login_page = (Button)findViewById(R.id.btn_login_page);
        btn_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //CURRICULUM PAGE에서 햄버거 클릭 --> 사이드바 화면 출력
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

    }


    //RC
    private void init(){
        RecyclerView rc_curriculum_course = findViewById(R.id.rc_curriculum_course);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rc_curriculum_course.setLayoutManager(linearLayoutManager);

        rc_adapter = new RecyclerVierAdapter();
        rc_curriculum_course.setAdapter(rc_adapter);
    }



    //RC
    private final RC_MyHandler RC_mHandler = new RC_MyHandler(this);

    //[RC] MyHandler 함수
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

    private void getData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result;

                try{
                    Log.d(TAG, REQUEST_URL);
                    URL url = new URL(REQUEST_URL);
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
                if (RC_jsonParser(result)){
                    Message message = RC_mHandler.obtainMessage(LOAD_SUCCESS);
                    RC_mHandler.sendMessage(message);
                }
            }
        });
        thread.start();
    }

    //[ListView] jsonParser 함수
    //웹사이트 화면을 파싱
    public boolean RC_jsonParser(String jsonString) {

        if (jsonString == null) return false;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            //전체 행렬 중 DB 내용 부분을 jsonArray 형태로 저장
            JSONArray course = jsonObject.getJSONArray("course");

            courseList.clear();

            //course의 길이만큼 반복해서 Mapping
            for (int i = 0; i < course.length(); i++) {
                JSONObject courseInfo = course.getJSONObject(i);

                String course_name = courseInfo.getString("course_name");
                String subject_id = courseInfo.getString("subject_id");
                String gpa = courseInfo.getString("gpa");
                String is_english = courseInfo.getString("is_english");

                DataCourseList data = new DataCourseList(course_name, subject_id, gpa, is_english);
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











    /*
    //////////////////////////////
    */

    //[ListView] MyHandler 함수
    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler{
        private final WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity mainActivity){
            weakReference = new WeakReference<MainActivity>(mainActivity);
        }

        public void handleMessage(Message msg){
            MainActivity mainActivity = weakReference.get();

            if(mainActivity != null){
                switch (msg.what){
                    case LOAD_SUCCESS:
                        mainActivity.progressDialog.dismiss();
                        mainActivity.adapter.notifyDataSetChanged();
                        break;
                }
            }
        }
    }

    //[ListView] getJSON 함수
    //주어진 HTTP 웹사이트로부터 결과를 JSON으로 불러옴
    public void getJSON() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result;

                try{
                    Log.d(TAG, REQUEST_URL);
                    URL url = new URL(REQUEST_URL);
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
                if (jsonParser(result)){

                    Message message = mHandler.obtainMessage(LOAD_SUCCESS);
                    mHandler.sendMessage(message);
                }
            }
        });
        thread.start();
    }

    //[ListView] jsonParser 함수
    //웹사이트 화면을 파싱
    public boolean jsonParser(String jsonString) {

        if (jsonString == null) return false;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            //전체 행렬 중 DB 내용 부분을 jsonArray 형태로 저장
            JSONArray course = jsonObject.getJSONArray("course");

            courseList.clear();

            //course의 길이만큼 반복해서 Mapping
            for (int i = 0; i < course.length(); i++) {
                JSONObject courseInfo = course.getJSONObject(i);

                String course_name = courseInfo.getString("course_name");
                String subject_id = courseInfo.getString("subject_id");
                String gpa = courseInfo.getString("gpa");
                String is_english = courseInfo.getString("is_english");

                HashMap<String, String> photoinfoMap = new HashMap<String, String>();
                photoinfoMap.put("course_name", course_name);
                photoinfoMap.put("subject_id", subject_id);
                photoinfoMap.put("gpa", gpa);
                photoinfoMap.put("is_english", is_english);

                courseList.add(photoinfoMap);

            }
            return true;
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
        return false;
    }

    ////////
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.naviBtn_curriculum:
                Intent intent_curriculum = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_curriculum);
                break;
            /*case R.id.naviBtn_jjimList:
                Intent intent_jjimList = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_jjimList);
                break;
            case R.id.naviBtn_lectureRecommendation:
                Intent intent_lectureRecommendation = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_lectureRecommendation);
                break;
            case R.id.naviBtn_gradeManagement:
                Intent intent_gradeManagement = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_gradeManagement);
                break;*/
            case R.id.naviBtn_myPage:
                Intent intent_myPage = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent_myPage);
                break;

        }

    }
}