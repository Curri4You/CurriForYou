package com.example.curriforyou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GradeRequest extends StringRequest {

    final static private String URL = "http://smlee099.dothome.co.kr/hakjum_click.php";
    private Map<String, String> map;

    public GradeRequest(String course_id, String grade, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("course_id", course_id);
        map.put("grade", grade);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
