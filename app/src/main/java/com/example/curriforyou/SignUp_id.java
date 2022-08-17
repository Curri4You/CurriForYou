package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp_id extends AppCompatActivity {

    private EditText et_user_id, et_user_password, et_user_name, et_student_id,
            et_university_name, et_college_name, et_major_name, et_major_division;
    private Button btn_register;

    ArrayAdapter<CharSequence> spin_uniAdapter, spin_colAdapter;
    String choice_uni="";
    String choice_col="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_id);

        ///구성///
        //LoginActivity - LoginRequest ||  SignUp_id - RegisterRequest
        ///////
        et_user_id = findViewById(R.id.et_user_id);
        et_user_password = findViewById(R.id.et_user_password);
        et_user_name = findViewById(R.id.et_user_name);
        et_student_id = findViewById(R.id.et_student_id);
        et_university_name = findViewById(R.id.et_university_name);
        et_college_name = findViewById(R.id.et_college_name);
        et_major_name = findViewById(R.id.major_name);
        et_major_division = findViewById(R.id.et_major_division);

        //Spinner - university
        final Spinner spin_university_name = (Spinner)findViewById(R.id.spin_university_name);
        final Spinner spin_college_name = (Spinner)findViewById(R.id.spin_college_name);

        spin_uniAdapter = ArrayAdapter.createFromResource(SignUp_id.this, R.array.spin_uni, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spin_uniAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spin_university_name.setAdapter(spin_uniAdapter);

        spin_university_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //인문과학대학을 선택할 경우
                if (spin_uniAdapter.getItem(position).equals("인문과학대학")){
                    choice_uni = spin_uniAdapter.getItem(position).toString();
                    spin_colAdapter = ArrayAdapter.createFromResource(SignUp_id.this, R.array.spin_col_in, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    spin_colAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    spin_college_name.setAdapter(spin_colAdapter);
                    spin_college_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_col = spin_colAdapter.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (spin_uniAdapter.getItem(position).equals("사회과학대학")){
                    choice_uni = "사회과학대학";
                    spin_colAdapter = ArrayAdapter.createFromResource(SignUp_id.this, R.array.spin_col_sagwa, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    spin_colAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    spin_college_name.setAdapter(spin_colAdapter);
                    spin_college_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            choice_col = spin_colAdapter.getItem(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = et_user_id.getText().toString();
                String user_password = et_user_password.getText().toString();
                String user_name = et_user_name.getText().toString();
                String student_id = et_student_id.getText().toString();
                String university_name = et_university_name.getText().toString();
                String college_name = et_college_name.getText().toString();
                String major_name = et_major_name.getText().toString();
                String major_division = et_major_division.getText().toString();

                Toast.makeText(SignUp_id.this, choice_uni + "&" + choice_col, Toast.LENGTH_SHORT).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp_id.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원가입 실패!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(user_id, user_password, user_name,
                        student_id, university_name, college_name, major_name, major_division, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUp_id.this);
                queue.add(registerRequest);

            }
        });

        ///////

        //SIGNUP_ID PAGE 의 BEFORE BUTTON 클릭 --> LOGIN PAGE 로 이동
        Button btn_before = (Button)findViewById(R.id.btn_signupid_before);
        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //SIGNUP_ID PAGE 의 NEXT BUTTON 클릭 --> SIGNUP_PW PAGE 로 이동
        Button btn_next = (Button)findViewById(R.id.btn_signupid_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp_pw.class);
                startActivity(intent);
            }
        });
    }
}
