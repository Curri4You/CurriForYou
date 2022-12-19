package com.example.curriforyou;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
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

public class JjimActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_jjim_num;
    ImageView open_checkbox;
    TextView open_filtering;
    boolean open_only = false;

    private static final String TAG = "imagesearchexample";
    private String REQUEST_URL = "http://smlee099.dothome.co.kr/jjim_list.php";
    public static final int LOAD_SUCCESS = 101;
    private ProgressDialog progressDialog = null;
    JjimRecyclerAdapter rc_adapter1 = null;
    JjimRecyclerAdapter rc_adapter2 = null;
    JjimRecyclerAdapter rc_adapter3 = null;
    JjimRecyclerAdapter rc_adapter4 = null;
    JjimRecyclerAdapter rc_adapter = null;
    ArrayList<DataJjimList> jjimList1 = new ArrayList<>();
    ArrayList<DataJjimList> jjimList2 = new ArrayList<>();
    ArrayList<DataJjimList> jjimList3 = new ArrayList<>();
    ArrayList<DataJjimList> jjimList4 = new ArrayList<>();
    ArrayList<DataJjimList> jjimList = new ArrayList<>();
    public static Context context_jjim;
    private ArrayList<HashMap<String, String>> jjimArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jjim_list);
        context_jjim = this;

        tv_jjim_num = (TextView) findViewById(R.id.tv_jjim_num);
        open_checkbox = (ImageView) findViewById(R.id.open_checkbox);
        open_filtering = (TextView) findViewById(R.id.open_filtering);
        LinearLayout naviBtn_curriculum = (LinearLayout) findViewById(R.id.naviBtn_curriculum);
        LinearLayout naviBtn_jjimList = (LinearLayout) findViewById(R.id.naviBtn_jjimList);
        LinearLayout naviBtn_lectureRecommendation = (LinearLayout) findViewById(R.id.naviBtn_lectureRecommendation);
        LinearLayout naviBtn_gradeManagement = (LinearLayout) findViewById(R.id.naviBtn_gradeManagement);
        LinearLayout naviBtn_myPage = (LinearLayout) findViewById(R.id.naviBtn_myPage);

        init();
        getData(REQUEST_URL);

        //[RecyclerView] HashMap 사용
        jjimArrayList = new ArrayList<HashMap<String, String>>();
        String[] from = new String[]{"course_name", "course_id", "is_open", "credit", "jjim"};
        int[] to = new int[]{R.id.tv_course_name, R.id.tv_course_id, R.id.tv_is_open, R.id.tv_credit, R.id.tv_credit};

        //[RecyclerView] 로딩이 걸릴 경우 로딩 Dialog 출력
        progressDialog = new ProgressDialog(JjimActivity.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        open_checkbox.setOnClickListener(this);
        open_filtering.setOnClickListener(this);
        naviBtn_curriculum.setOnClickListener(this);
        naviBtn_jjimList.setOnClickListener(this);
        naviBtn_lectureRecommendation.setOnClickListener(this);
        naviBtn_gradeManagement.setOnClickListener(this);
        naviBtn_myPage.setOnClickListener(this);
    }

    private void init() {
        RecyclerView rv_jjimList1 = findViewById(R.id.rc_jjimList1);
        LinearLayoutManager jl1 = new LinearLayoutManager(this);
        rv_jjimList1.setLayoutManager(jl1);
        jjimList1.clear();
        rc_adapter1 = new JjimRecyclerAdapter(jjimList1);
        rv_jjimList1.setAdapter(rc_adapter1);
        RecyclerView rv_jjimList2 = findViewById(R.id.rc_jjimList2);
        LinearLayoutManager jl2 = new LinearLayoutManager(this);
        rv_jjimList2.setLayoutManager(jl2);
        jjimList2.clear();
        rc_adapter2 = new JjimRecyclerAdapter(jjimList2);
        rv_jjimList2.setAdapter(rc_adapter2);
        RecyclerView rv_jjimList3 = findViewById(R.id.rc_jjimList3);
        LinearLayoutManager jl3 = new LinearLayoutManager(this);
        rv_jjimList3.setLayoutManager(jl3);
        jjimList3.clear();
        rc_adapter3 = new JjimRecyclerAdapter(jjimList3);
        rv_jjimList3.setAdapter(rc_adapter3);
        RecyclerView rv_jjimList4 = findViewById(R.id.rc_jjimList4);
        LinearLayoutManager jl4 = new LinearLayoutManager(this);
        rv_jjimList4.setLayoutManager(jl4);
        jjimList4.clear();
        rc_adapter4 = new JjimRecyclerAdapter(jjimList4);
        rv_jjimList4.setAdapter(rc_adapter4);
        RecyclerView rv_jjimList = findViewById(R.id.rc_jjimList);
        LinearLayoutManager jl = new LinearLayoutManager(this);
        rv_jjimList.setLayoutManager(jl);
        jjimList.clear();
        rc_adapter = new JjimRecyclerAdapter(jjimList);
        rv_jjimList.setAdapter(rc_adapter);
    }

    //[RecyclerView]
    private final RC_MyHandler RC_mHandler = new RC_MyHandler(this);

    //[RecyclerView]
    private static class RC_MyHandler extends Handler {
        private final WeakReference<JjimActivity> weakReference;

        public RC_MyHandler(JjimActivity jjimActivity) {
            weakReference = new WeakReference<JjimActivity>(jjimActivity);
        }

        public void handleMessage(Message msg) {
            JjimActivity jjimActivity = weakReference.get();

            if (jjimActivity != null) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        jjimActivity.progressDialog.dismiss();
                        jjimActivity.rc_adapter.notifyDataSetChanged();
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
        int jjim_num = 0;

        if (jsonString == null) return false;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            //전체 행렬 중 DB 내용 부분을 jsonArray 형태로 저장
            JSONArray jjimArray = jsonObject.getJSONArray("jjim_folder_list");

            jjimList.clear();

            //gmSemester 길이만큼 반복해서 Mapping
            for (int i = 0; i < jjimArray.length(); i++) {
                JSONObject jjimInfo = jjimArray.getJSONObject(i);

                String is_open = jjimInfo.getString("is_open");
                String jjim = jjimInfo.getString("jjim");
                String major_division = jjimInfo.getString("major_division");
                String course_id = jjimInfo.getString("course_id");
                String course_name = jjimInfo.getString("course_name");
                String credit = jjimInfo.getString("credit");
                String category = jjimInfo.getString("category");

                DataJjimList data = new DataJjimList(major_division, course_name, course_id, is_open, credit, jjim, category);
                jjim_num += 1;

                if (category.equals("1")) {
                    rc_adapter1.addItem(data);
                } else if (category.equals("2")) {
                    rc_adapter2.addItem(data);
                } else if (category.equals("3")) {
                    rc_adapter3.addItem(data);
                } else if (category.equals("4")) {
                    rc_adapter4.addItem(data);
                }
            }
            tv_jjim_num.setText(String.valueOf(jjim_num));
            return true;
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
        return false;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_checkbox:
            case R.id.open_filtering:
                if (open_only) {
                    open_checkbox.setImageResource(R.drawable.checkbox_outline);
                    open_checkbox.setColorFilter(Color.parseColor("#BCC2D0"));
                    open_filtering.setTextColor(Color.parseColor("#BCC2D0"));
                    open_only = false;
                    rc_adapter1.getFilter().filter("all");
                    rc_adapter2.getFilter().filter("all");
                    rc_adapter3.getFilter().filter("all");
                    rc_adapter4.getFilter().filter("all");
                } else {
                    open_checkbox.setImageResource(R.drawable.checkbox_checked);
                    open_checkbox.setColorFilter(Color.parseColor("#2D4788"));
                    open_filtering.setTextColor(Color.parseColor("#2D4788"));
                    open_only = true;
                    rc_adapter1.getFilter().filter("open_only");
                    rc_adapter2.getFilter().filter("open_only");
                    rc_adapter3.getFilter().filter("open_only");
                    rc_adapter4.getFilter().filter("open_only");
                }
                break;
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
