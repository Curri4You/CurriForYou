package com.example.curriforyou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JjimRequest extends StringRequest {

    final static private String URL = "http://smlee099.dothome.co.kr/Like.php";
    private Map<String, String> map;

    public JjimRequest(String course_id, String jjim, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("course_id", course_id);
        map.put("jjim", jjim);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}