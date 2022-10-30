//// 학점관리 학기별 요약 페이지
//
//package com.example.curriforyou;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//
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
//public class GmSemesterActivity extends AppCompatActivity {
//
//    //Adapter 연결
//    GmRecyclerAdapter adapter;
//    // [RecyclerView] 리스트 출력을 위한 parameter
//    private static final String TAG = "imagesearchexample";
//    // URL - 학기별 학점요약 DB
//    private  String REQUEST_URL = "http://smlee099.dothome.co.kr/practice_avg_grade.php";
//    public static final int LOAD_SUCCESS = 101;
//    private ProgressDialog progressDialog = null;
//    GmRecyclerAdapter rc_adapter = null;
//    private ArrayList<HashMap<String, String>> gmSemesterList = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gm_semester);
//        init();
//        getData(REQUEST_URL);
//
//        //[RecyclerView] HashMap 사용
//        gmSemesterList = new ArrayList<HashMap<String, String>>();
//        //[RecyclerView] 각 필드값을 매핑, from -> to
//        String[] from = new String[]{"semester", "totalGrade", "majorGrade", "liberalGrade"};
//        int[] to = new int[]{R.id.gm_list_semester, R.id.gm_list_totalGrade, R.id.gm_list_majorGrade, R.id.gm_list_liberalGrade};
//
//        //[RecyclerView] 로딩이 걸릴 경우 로딩 Dialog 출력
//        progressDialog = new ProgressDialog(GmSemesterActivity.this);
//        progressDialog.setMessage("Please wait ...");
//        progressDialog.show();
//
//        //뒤로가기 버튼
//        ImageView btn_infoModification = (ImageView) findViewById(R.id.navigate_before);
//        btn_infoModification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), GmActivity.class);
//                startActivity(intent); //액티비티 이동
//            }
//        });
//    }
//
//    // [RecyclerView] 연결
//    private void init(){
//        RecyclerView rc_gm_semester = findViewById(R.id.rc_gm_semester);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rc_gm_semester.setLayoutManager(linearLayoutManager);
//        rc_adapter = new GmRecyclerAdapter();
//        rc_gm_semester.setAdapter(rc_adapter);
//    }
//
//    //[RecyclerView]
//    private final RC_MyHandler RC_mHandler = new RC_MyHandler(this);
//    //[RecyclerView]
//    private static class RC_MyHandler extends Handler {
//        private final WeakReference<GmSemesterActivity> weakReference;
//
//        public RC_MyHandler(GmSemesterActivity gmSemesterActivity){
//            weakReference = new WeakReference<GmSemesterActivity>(gmSemesterActivity);
//        }
//
//        public void handleMessage(Message msg){
//            GmSemesterActivity gmSemesterActivity = weakReference.get();
//
//            if(gmSemesterActivity != null){
//                switch (msg.what){
//                    case LOAD_SUCCESS:
//                        gmSemesterActivity.progressDialog.dismiss();
//                        gmSemesterActivity.rc_adapter.notifyDataSetChanged();
//                        break;
//                }
//            }
//        }
//    }
//
//    //데이터 넣어주기
//    private void getData(String Request_url){
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String result;
//
//                try{
//                    Log.d(TAG, Request_url);
//                    URL url = new URL(Request_url);
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setReadTimeout(3000);
//                    httpURLConnection.setConnectTimeout(3000);
//                    httpURLConnection.setDoOutput(true);
//                    httpURLConnection.setDoInput(true);
//                    httpURLConnection.setRequestMethod("GET");
//                    httpURLConnection.setUseCaches(false);
//                    httpURLConnection.connect();
//
//                    int responseStatusCode = httpURLConnection.getResponseCode();
//
//                    InputStream inputStream;
//                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {
//                        inputStream = httpURLConnection.getInputStream();
//                    } else {
//                        inputStream = httpURLConnection.getErrorStream();
//                    }
//
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                    StringBuilder sb = new StringBuilder();
//                    String line;
//
//                    while ((line = bufferedReader.readLine()) != null) {
//                        sb.append(line);
//                    }
//
//                    bufferedReader.close();
//                    httpURLConnection.disconnect();
//
//                    result = sb.toString().trim();
//                } catch (Exception e){
//                    result = e.toString();
//                }
//                if (RC_jsonParser(result)){     // 파싱 기능 사용
//                    Message message = RC_mHandler.obtainMessage(LOAD_SUCCESS);
//                    RC_mHandler.sendMessage(message);
//                }
//            }
//        });
//        thread.start();
//    }
//
//    //[RecyclerView] jsonParser 함수
//    //웹사이트 화면을 파싱하는 함수
//    public boolean RC_jsonParser(String jsonString) {
//
//        if (jsonString == null) return false;
//
//        try {
//            JSONObject jsonObject = new JSONObject(jsonString);
//
//            //전체 행렬 중 DB 내용 부분을 jsonArray 형태로 저장
//            JSONArray gmSemester = jsonObject.getJSONArray("practice_avg_grade");
//
//            gmSemesterList.clear();
//
//            //gmSemester 길이만큼 반복해서 Mapping
//            for (int i = 0; i < gmSemester.length(); i++) {
//                JSONObject gmSemesterInfo = gmSemester.getJSONObject(i);
//
//                String semester = gmSemesterInfo.getString("semester");
//                String totalGrade = gmSemesterInfo.getString("totalGrade");
//                String majorGrade = gmSemesterInfo.getString("majorGrade");
//                String liberalGrade = gmSemesterInfo.getString("liberalGrade");
//
//                DataGmSemester data = new DataGmSemester(semester, totalGrade, majorGrade, liberalGrade);
//                rc_adapter.addItem(data);
//
//            }
//            return true;
//        } catch (JSONException e) {
//            Log.d(TAG, e.toString());
//        }
//        return false;
//    }
//
//}
