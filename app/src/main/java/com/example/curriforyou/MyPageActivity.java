//마이페이지
//        !!해야할 일!!
//        전공버튼 리스트로 만들기
//        하단네비 마이페이지 버튼 다시 누르면 user정보 사라지는 오류 해결
//        로그아웃 버튼 클릭시 기능구현

package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class MyPageActivity extends AppCompatActivity implements View.OnClickListener {
//    final static private String URL = "http://smlee099.dothome.co.kr/my_change_ok.php";
//    private ArrayList<HashMap<String, String>> userDBlist = null;
    private TextView tv_user_name, tv_student_id;
    private Button btn_mainMajor, btn_doubleMajor, btn_minor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_student_id = (TextView) findViewById(R.id.tv_student_id);
        btn_mainMajor = (Button) findViewById(R.id.btn_mainMajor);
        btn_doubleMajor = (Button) findViewById(R.id.btn_doubleMajor);
        btn_minor = (Button) findViewById(R.id.btn_minor);

//        //[DB] 이름, 학번, 주전공 이름 띄우기
//        Intent intent = getIntent();
//        String user_name = intent.getStringExtra("user_name");
//        String student_id = intent.getStringExtra("student_id");
//        String major_name = intent.getStringExtra("major_name");
//        tv_user_name.setText(user_name);
//        tv_student_id.setText(student_id);
//        btn_mainMajor.setText(major_name);

//        // 프로필 사진
//        ImageView user_photo = (ImageView) findViewById(R.id.user_photo);
//        user_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "프로필 사진 변경", Toast.LENGTH_SHORT).show();
//            }
//        });

        // 전공수정버튼 (연필)
        ImageView btn_infoModification = (ImageView) findViewById(R.id.btn_infoModification);
        btn_infoModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_infoModification.class);
                startActivity(intent);
            }
        });

        // 주전공버튼
        Button btn_mainMajor = (Button) findViewById(R.id.btn_mainMajor);
        btn_mainMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
                startActivity(intent); //액티비티 이동
            }
        });

        // 복수전공버튼
        Button btn_doubleMajor = (Button) findViewById(R.id.btn_doubleMajor);
        btn_doubleMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
                startActivity(intent); //액티비티 이동
            }
        });

        // 부전공버튼
        Button btn_minor = (Button) findViewById(R.id.btn_minor);
        btn_minor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
                startActivity(intent);
            }
        });
        // 전공추가 버튼
        ImageView btn_addMajor = (ImageView) findViewById(R.id.btn_addMajor);
        btn_addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPage_addMajor.class);
                startActivity(intent);
            }
        });
        // 로그아웃 버튼
        TextView logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent); //액티비티 이동만 구현해둔 상태
            }
        });

        //[하단네비] 버튼 정의
        LinearLayout naviBtn_curriculum = (LinearLayout) findViewById(R.id.naviBtn_curriculum);
        LinearLayout naviBtn_jjimList = (LinearLayout) findViewById(R.id.naviBtn_jjimList);
        LinearLayout naviBtn_lectureRecommendation = (LinearLayout) findViewById(R.id.naviBtn_lectureRecommendation);
        LinearLayout naviBtn_gradeManagement = (LinearLayout) findViewById(R.id.naviBtn_gradeManagement);
        LinearLayout naviBtn_myPage = (LinearLayout) findViewById(R.id.naviBtn_myPage);
        //[하단네비]
        naviBtn_curriculum.setOnClickListener(this);
        naviBtn_jjimList.setOnClickListener(this);
        naviBtn_lectureRecommendation.setOnClickListener(this);
        naviBtn_gradeManagement.setOnClickListener(this);
        naviBtn_myPage.setOnClickListener(this);

    }

    //[하단네비] 클릭시 Activity 이동
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

//package com.example.curriforyou;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.lang.ref.WeakReference;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class MyPageActivity extends AppCompatActivity implements View.OnClickListener {
//    // [RecyclerView] 리스트 출력을 위한 parameter
////    private static final String TAG = "imagesearchexample";
////    final static private String URL = "http://smlee099.dothome.co.kr/my_page.php";
////    public static final int LOAD_SUCCESS = 101;
////    private ProgressDialog progressDialog = null;
////    private ArrayList<HashMap<String, String>> userDBlist = null;
//    private TextView tv_user_name, tv_student_id;
////    private Button btn_major;
//    MpRecyclerAdapter rc_adapter = null;
//    private Button btn_mainMajor, btn_doubleMajor, btn_minor;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mypage);
////        init();
////        getData(URL);
//
//        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
//        tv_student_id = (TextView) findViewById(R.id.tv_student_id);
//        btn_mainMajor = (Button) findViewById(R.id.btn_mainMajor);
//        btn_doubleMajor = (Button) findViewById(R.id.btn_doubleMajor);
//        btn_minor = (Button) findViewById(R.id.btn_minor);
//
////        //[RecyclerView] HashMap 사용
////        userDBlist = new ArrayList<HashMap<String, String>>();
////        //[RecyclerView] 각 필드값을 매핑, from -> to
////        String[] from = new String[]{"user_name", "student_id", "major_name"};
////        int[] to = new int[]{R.id.tv_user_name, R.id.tv_student_id, R.id.btn_major};
////
////        //[RecyclerView] 로딩이 걸릴 경우 로딩 Dialog 출력
////        progressDialog = new ProgressDialog(MyPageActivity.this);
////        progressDialog.setMessage("Please wait ...");
////        progressDialog.show();
//
//        //[DB] 이름, 학번, 주전공 이름 띄우기
//        Intent intent = getIntent();
//        String user_name = intent.getStringExtra("이화연");
//        String student_id = intent.getStringExtra("1901866");
//        String major_name = intent.getStringExtra("사이버보안전공");
//        tv_user_name.setText(user_name);
//        tv_student_id.setText(student_id);
//        btn_mainMajor.setText(major_name);
////        btn_major.setText(major_name);
//        DataMp data = new DataMp(user_name, student_id, major_name);
//        rc_adapter.addItem(data);
//
//        // 프로필 사진
//        ImageView user_photo = (ImageView) findViewById(R.id.user_photo);
//        user_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "프로필 사진 변경", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // 전공수정버튼 (연필)
//        ImageView btn_infoModification = (ImageView) findViewById(R.id.btn_infoModification);
//        btn_infoModification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MyPage_infoModification.class);
//                startActivity(intent);
//            }
//        });
//
////        // 주전공버튼
////        Button btn_major = (Button) findViewById(R.id.btn_major);
////        btn_major.setOnClickListener(new View.OnClickListener() {
////            @Override
//
//        Button btn_mainMajor = (Button) findViewById(R.id.btn_mainMajor);
//        btn_mainMajor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
//                startActivity(intent); //액티비티 이동
//            }
//        });
//
//        // 복수전공버튼
//        Button btn_doubleMajor = (Button) findViewById(R.id.btn_doubleMajor);
//        btn_doubleMajor.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
//                startActivity(intent); //액티비티 이동
//            }
//        });
//
//        // 전공추가 버튼
//        ImageView btn_addMajor = (ImageView) findViewById(R.id.btn_addMajor);
//        btn_addMajor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MyPage_addMajor.class);
//                startActivity(intent);
//            }
//        });
//
//        // 부전공버튼
//        Button btn_minor = (Button) findViewById(R.id.btn_minor);
//        btn_minor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MyPage_majorModification.class);
//                startActivity(intent);
//            }
//        });
//
//        // 로그아웃 버튼
//        TextView logout = (TextView) findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent); //액티비티 이동만 구현해둔 상태
//            }
//        });
//
//        //[하단네비] 버튼 정의
//        LinearLayout naviBtn_curriculum = (LinearLayout) findViewById(R.id.naviBtn_curriculum);
//        LinearLayout naviBtn_jjimList = (LinearLayout) findViewById(R.id.naviBtn_jjimList);
//        LinearLayout naviBtn_lectureRecommendation = (LinearLayout) findViewById(R.id.naviBtn_lectureRecommendation);
//        LinearLayout naviBtn_gradeManagement = (LinearLayout) findViewById(R.id.naviBtn_gradeManagement);
//        LinearLayout naviBtn_myPage = (LinearLayout) findViewById(R.id.naviBtn_myPage);
//        //[하단네비]
//        naviBtn_curriculum.setOnClickListener(this);
//        naviBtn_jjimList.setOnClickListener(this);
//        naviBtn_lectureRecommendation.setOnClickListener(this);
//        naviBtn_gradeManagement.setOnClickListener(this);
//        naviBtn_myPage.setOnClickListener(this);
//
//    }
//
////    // [RecyclerView] 연결
////    private void init(){
////        RecyclerView rc_majorlist = findViewById(R.id.rc_majorlist);
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
////        rc_majorlist.setLayoutManager(linearLayoutManager);
////        rc_adapter = new MpRecyclerAdapter();
////        rc_majorlist.setAdapter(rc_adapter);
////    }
//
////    //[RecyclerView]
////    private final RC_MyHandler RC_mHandler = new RC_MyHandler(this);
////    //[RecyclerView]
////    private static class RC_MyHandler extends Handler {
////        private final WeakReference<MyPageActivity> weakReference;
////
////        public RC_MyHandler(MyPageActivity myPageActivity){
////            weakReference = new WeakReference<MyPageActivity>(myPageActivity);
////        }
////
////        public void handleMessage(Message msg){
////            MyPageActivity myPageActivity = weakReference.get();
////
////            if(myPageActivity != null){
////                switch (msg.what){
////                    case LOAD_SUCCESS:
////                        myPageActivity.progressDialog.dismiss();
////                        myPageActivity.rc_adapter.notifyDataSetChanged();
////                        break;
////                }
////            }
////        }
////    }
//
////    //데이터 넣어주기
////    private void getData(String Request_url){
////        Thread thread = new Thread(new Runnable() {
////            @Override
////            public void run() {
////                String result;
////
////                try{
////                    Log.d(TAG, Request_url);
////                    URL url = new URL(Request_url);
////                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
////                    httpURLConnection.setReadTimeout(3000);
////                    httpURLConnection.setConnectTimeout(3000);
////                    httpURLConnection.setDoOutput(true);
////                    httpURLConnection.setDoInput(true);
////                    httpURLConnection.setRequestMethod("GET");
////                    httpURLConnection.setUseCaches(false);
////                    httpURLConnection.connect();
////
////                    int responseStatusCode = httpURLConnection.getResponseCode();
////
////                    InputStream inputStream;
////                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {
////                        inputStream = httpURLConnection.getInputStream();
////                    } else {
////                        inputStream = httpURLConnection.getErrorStream();
////                    }
////
////                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
////                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
////
////                    StringBuilder sb = new StringBuilder();
////                    String line;
////
////                    while ((line = bufferedReader.readLine()) != null) {
////                        sb.append(line);
////                    }
////
////                    bufferedReader.close();
////                    httpURLConnection.disconnect();
////
////                    result = sb.toString().trim();
////                } catch (Exception e){
////                    result = e.toString();
////                }
////                if (RC_jsonParser(result)){     // 파싱 기능 사용
////                    Message message = RC_mHandler.obtainMessage(LOAD_SUCCESS);
////                    RC_mHandler.sendMessage(message);
////                }
////            }
////        });
////        thread.start();
////    }
////
////    //[RecyclerView] jsonParser 함수
////    //웹사이트 화면을 파싱하는 함수
////    public boolean RC_jsonParser(String jsonString) {
////
////        if (jsonString == null) return false;
////
////        try {
////            JSONObject jsonObject = new JSONObject(jsonString);
////
////            //전체 행렬 중 DB 내용 부분을 jsonArray 형태로 저장
////            JSONArray userDB = jsonObject.getJSONArray("my_page");
////
////            userDBlist.clear();
////
////            //gmSemester 길이만큼 반복해서 Mapping
////            for (int i = 0; i < userDB.length(); i++) {
////                JSONObject userInfo = userDB.getJSONObject(i);
////
////                String user_name = userInfo.getString("user_name");
////                String student_id = userInfo.getString("student_id");
////                String major_name = userInfo.getString("major_name");
////
////                DataMp data = new DataMp(user_name, student_id, major_name);
////                rc_adapter.addItem(data);
////
////            }
////            return true;
////        } catch (JSONException e) {
////            Log.d(TAG, e.toString());
////        }
////        return false;
////    }
//
//    //[하단네비] 클릭시 Activity 이동
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.naviBtn_curriculum:
//                Intent intent_curriculum = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent_curriculum);
//                break;
//            /*case R.id.naviBtn_jjimList:
//                Intent intent_jjimList = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent_jjimList);
//                break;*/
//            case R.id.naviBtn_lectureRecommendation:
//                Intent intent_lectureRecommendation = new Intent(getApplicationContext(), RecomActivity.class);
//                startActivity(intent_lectureRecommendation);
//                break;
//            case R.id.naviBtn_gradeManagement:
//                Intent intent_gradeManagement = new Intent(getApplicationContext(), GmActivity.class);
//                startActivity(intent_gradeManagement);
//                break;
//            case R.id.naviBtn_myPage:
//                Intent intent_myPage = new Intent(getApplicationContext(), MyPageActivity.class);
//                startActivity(intent_myPage);
//                break;
//        }
//    }
//
//}