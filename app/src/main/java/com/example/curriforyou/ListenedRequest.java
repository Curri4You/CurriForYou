package com.example.curriforyou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ListenedRequest extends StringRequest {

    final static private String URL = "http://smlee099.dothome.co.kr/sugang.php";
    private Map<String, String> map;

    public ListenedRequest(String course_id, String course_year, String course_semester, String grade, String already_listened, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("course_id", course_id);
        map.put("course_year", course_year);
        map.put("course_semester", course_semester);
        map.put("grade", grade);
        map.put("already_listened", already_listened);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}