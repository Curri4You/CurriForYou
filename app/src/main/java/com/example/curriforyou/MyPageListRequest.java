package com.example.curriforyou;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MyPageListRequest extends StringRequest {

    final static private String URL = "";
    private Map<String, String> map;

    public MyPageListRequest(String major_division, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("major_division", major_division);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
