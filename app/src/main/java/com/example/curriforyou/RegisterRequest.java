package com.example.curriforyou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://smlee099.dothome.co.kr/Register2.php";
    //private Map<String, String>parameters;
    private Map<String, String> map;

    public RegisterRequest(String user_id, String user_password, String user_name,
                           String student_id, String university_name, String college_name,
                           String major_name, String major_division, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("user_password", user_password);
        map.put("user_name", user_name);
        map.put("student_id", student_id);
        map.put("university_name", university_name);
        map.put("college_name", college_name);
        map.put("major_name", major_name);
        map.put("major_division", major_division);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
