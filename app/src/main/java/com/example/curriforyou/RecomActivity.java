package com.example.curriforyou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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

public class RecomActivity extends AppCompatActivity implements View.OnClickListener {

    //[RecyclerView] 리스트 출력을 위한 parameter
    private static final String TAG = "imagesearchexample";
    //URL
    private  String REQUEST_URL = "http://smlee099.dothome.co.kr/recommendation.php";

    public static final int LOAD_SUCCESS = 101;
    private ProgressDialog progressDialog = null;
    RcRecylerAdapter rc_adapter, rc_adapter2, rc_adapter3 = null;

    ArrayList<DataRecomList> recom_list1 = new ArrayList<>();
    ArrayList<DataRecomList> recom_list2 = new ArrayList<>();
    ArrayList<DataRecomList> recom_list3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recom);

        init();
        getData(REQUEST_URL);

        //[RecyclerView] 로딩이 걸릴 경우 로딩 Dialog 출력
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        //[하단바]
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
    //[RecyclerView] 커리큘럼 화면의 RecyclerView에 Adapter를 연결하는 함수 & 초기화 함수
    private void init(){
        recom_list1.clear();
        recom_list2.clear();
        recom_list3.clear();

        /*교양(융복합) 어댑터*/
        RecyclerView recyclerView = findViewById(R.id.rc_recom1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        rc_adapter = new RcRecylerAdapter(recom_list1);
        recyclerView.setAdapter(rc_adapter);

        /*교양(융합기초) 어댑터*/
        RecyclerView recyclerView2 = findViewById(R.id.rc_recom2);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        rc_adapter2 = new RcRecylerAdapter(recom_list2);
        recyclerView2.setAdapter(rc_adapter2);

        /*전공(선택) 어댑터*/
        RecyclerView recyclerView3 = findViewById(R.id.rc_recom3);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(linearLayoutManager3);
        rc_adapter3 = new RcRecylerAdapter(recom_list3);
        recyclerView3.setAdapter(rc_adapter3);
    }

    //[RecyclerView]
    private final RC_MyHandler RC_mHandler = new RC_MyHandler(this);
    //[RecyclerView]
    private static class RC_MyHandler extends Handler {
        private final WeakReference<RecomActivity> weakReference;

        public RC_MyHandler(RecomActivity mainActivity){
            weakReference = new WeakReference<RecomActivity>(mainActivity);
        }

        public void handleMessage(Message msg){
            RecomActivity mainActivity = weakReference.get();

            if(mainActivity != null){
                switch (msg.what){
                    case LOAD_SUCCESS:
                        mainActivity.progressDialog.dismiss();
                        mainActivity.rc_adapter.notifyDataSetChanged();
                        mainActivity.rc_adapter2.notifyDataSetChanged();
                        mainActivity.rc_adapter3.notifyDataSetChanged();
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
            JSONArray course = jsonObject.getJSONArray("recommendation");


            //course의 길이만큼 반복해서 Mapping
            for (int i = 0; i < course.length(); i++) {
                JSONObject courseInfo = course.getJSONObject(i);

                String category = courseInfo.getString("category");
                String course_name = courseInfo.getString("course_name");
                String open_major = courseInfo.getString("open_major");
                String credit = courseInfo.getString("credit");
                String pre_course_name = courseInfo.getString("pre_course_name");
                String jjim = courseInfo.getString("jjim");

                DataRecomList data = new DataRecomList(category, course_name,
                        open_major, credit, pre_course_name, jjim);

                /*분류값에 따라 다른 RecyclerView 어댑터에 추가*/
                if (category.equals("1")){
                    rc_adapter.addItem(data);
                } else if (category.equals("2")){
                    rc_adapter2.addItem(data);
                } else {
                    rc_adapter3.addItem(data);
                }

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
