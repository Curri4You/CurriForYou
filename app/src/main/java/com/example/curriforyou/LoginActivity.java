package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    ///////////
    public String user_id;
    private EditText et_user_id, et_user_password;
    private Button btn_login, btn_register;

    ////////////
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //////////////////////////////
        et_user_id = findViewById(R.id.et_user_id);
        et_user_password = findViewById(R.id.et_user_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp_id.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = et_user_id.getText().toString();
                String user_password = et_user_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                String user_id = jsonObject.getString("user_id");
                                String user_password = jsonObject.getString("user_password");
                                String user_name = jsonObject.getString("user_name");
                                String student_id = jsonObject.getString("student_id");
                                String major_name = jsonObject.getString("major_name");

                                Toast.makeText(getApplicationContext(), "로그인에 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("user_password", user_password);
                                intent.putExtra("user_name", user_name);
                                intent.putExtra("student_id", student_id);
                                intent.putExtra("major_name", major_name);

                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인에 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(user_id, user_password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
        ////////////////////////////

        //CURRICULUM PAGE BUTTON 클릭 --> CURRICULUM PAGE 로 이동
        Button btn_curriculum_page = (Button) findViewById(R.id.btn_curriculum_page);
        btn_curriculum_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //SIGNUP BUTTON 클릭 --> SIGNUP_ID PAGE 로 이동
        Button btn_signup = (Button)findViewById(R.id.btn_register);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp_id.class);
                startActivity(intent);
            }
        });
    }
}