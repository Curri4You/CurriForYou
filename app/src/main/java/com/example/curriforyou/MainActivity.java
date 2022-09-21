package com.example.curriforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;

    /////////
    static RequestQueue requestQueue;

    private static final String TAG = "imagesearchexample";
    private  String REQUEST_URL = "http://smlee099.dothome.co.kr/CourseList.php";
    public static final int LOAD_SUCCESS = 101;
    private ProgressDialog progressDialog = null;
    private SimpleAdapter adapter = null;
    private ArrayList<HashMap<String, String>> courseList = null;

    /////////////
    private TextView tv_id, tv_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);

        //////////////////
        //커리큘럼 화면이 켜지면서 리스트 출력되도록 JSON
        ListView lv_curriculum_course = (ListView)findViewById(R.id.lv_curriculum_course);
        courseList = new ArrayList<HashMap<String, String>>();
        String[] from = new String[]{"course_name", "subject_id", "gpa", "is_english"};
        int[] to = new int[]{R.id.tv_course_name, R.id.tv_subject_id, R.id.tv_gpa, R.id.tv_is_english};
        adapter = new SimpleAdapter(this, courseList, R.layout.listview_item, from, to);
        lv_curriculum_course.setAdapter(adapter);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        getJSON();

        //////////////////
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

        tv_id = findViewById(R.id.tv_id);
        tv_pass = findViewById(R.id.tv_pass);
        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String user_password = intent.getStringExtra("user_password");
        tv_id.setText(user_id);
        tv_pass.setText(user_password);
        ///////////////////



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

    ////////
    //JSON 함수
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

    public void getJSON(){
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

    //////////
    public boolean jsonParser(String jsonString) {


        if (jsonString == null) return false;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray photo = jsonObject.getJSONArray("course");

            courseList.clear();

            for (int i = 0; i < photo.length(); i++) {
                JSONObject photoInfo = photo.getJSONObject(i);

                String course_name = photoInfo.getString("course_name");
                String subject_id = photoInfo.getString("subject_id");
                String gpa = photoInfo.getString("gpa");
                String is_english = photoInfo.getString("is_english");

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
}
