package com.example.curriforyou;

import android.app.AlertDialog;
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
            et_major_division;
    private Button btn_register, btn_check;
    private AlertDialog dialog;
    private boolean validate = false;

    ArrayAdapter<CharSequence> spin_uniAdapter, spin_colAdapter, spin_majAdapter, spin_subAdapter, spin_numAdapter;
    String choice_uni="";
    String choice_col="";
    String choice_maj="";
    String choice_sub="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_id);

        ///구성///
        //LoginActivity - LoginRequest ||  SignUp_id - RegisterRequest

        //아이디값 찾아주기
        et_user_id = findViewById(R.id.et_user_id);
        et_user_password = findViewById(R.id.et_user_password);
        et_user_name = findViewById(R.id.et_user_name);
        et_student_id = findViewById(R.id.et_student_id);
        /*et_major_division = findViewById(R.id.et_major_division);*/

        //아이디 중복 체크
        btn_check = findViewById(R.id.btn_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = et_user_id.getText().toString();
                if(validate){
                    return; //검증 완료
                }
                if(user_id.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_id.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success2 = jsonResponse.getBoolean("success2");

                            if(success2){
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_id.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                et_user_id.setEnabled(false);
                                validate = true;    //검증 완료
                                btn_check.setBackgroundColor(getResources().getColor(R.color.blue_4));
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp_id.this);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(user_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUp_id.this);
                queue.add(validateRequest);
            }
        });


        //Spinner - university
        final Spinner spin_university_name = (Spinner)findViewById(R.id.spin_university_name);
        final Spinner spin_college_name = (Spinner)findViewById(R.id.spin_college_name);
        final Spinner spin_major_name = (Spinner)findViewById(R.id.spin_major_name);

        spin_uniAdapter = ArrayAdapter.createFromResource(SignUp_id.this, R.array.spin_uni, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spin_uniAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spin_university_name.setAdapter(spin_uniAdapter);

        spin_university_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //선택한 item의 index가 0 ~ 17 인 경우
                if (position >= 0 && position <= 17){     //spin_uniAdapter.getItem(position).equals("인문과학대학")
                    int uni = position;
                    choice_uni = spin_uniAdapter.getItem(position).toString();

                    spin_colAdapter = ArrayAdapter.createFromResource(SignUp_id.this, spin_uni_col_major[(uni+1)][0][0], androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    spin_colAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    spin_college_name.setAdapter(spin_colAdapter);

                    spin_college_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int col = position;
                            if(col < spin_uni_col_major[(uni+1)][1].length) {
                                choice_col = spin_colAdapter.getItem(position).toString();

                                spin_majAdapter = ArrayAdapter.createFromResource(SignUp_id.this, spin_uni_col_major[(uni + 1)][1][col], androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                                spin_majAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                                spin_major_name.setAdapter(spin_majAdapter);

                                spin_major_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        choice_maj = spin_majAdapter.getItem(position).toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else {
                                spin_majAdapter = ArrayAdapter.createFromResource(SignUp_id.this, R.array.spin_major_all, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                                spin_majAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                                spin_major_name.setAdapter(spin_majAdapter);

                                spin_major_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        choice_maj = spin_majAdapter.getItem(position).toString();
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner - 복수전공&부전공&연계전공
        final Spinner spin_sub_major = (Spinner)findViewById(R.id.spin_sub_major);
        spin_subAdapter = ArrayAdapter.createFromResource(SignUp_id.this, R.array.spin_sub_major, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spin_subAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spin_sub_major.setAdapter(spin_subAdapter);

        spin_sub_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_sub = spin_subAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner - 추가전공개수
        final Spinner spin_sub_major_num = (Spinner)findViewById(R.id.spin_sub_major_num);
        spin_numAdapter = ArrayAdapter.createFromResource(SignUp_id.this, R.array.spin_sub_major_num, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spin_numAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spin_sub_major_num.setAdapter(spin_numAdapter);

        spin_sub_major_num.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_sub = spin_numAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //회원가입 버튼 클릭 시
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = et_user_id.getText().toString();
                String user_password = et_user_password.getText().toString();
                String user_name = et_user_name.getText().toString();
                String student_id = et_student_id.getText().toString();
                String university_name = choice_uni;
                String college_name = choice_col;
                String major_name = choice_maj;
                //getText().toString은 회원가입 버튼을 클릭했을 시에만 가져올 수 있기 때문에 다른 함수에서 제대로 된 값을 찾아올 수 없음 --> 이 함수 내에서 해결해야 함
                String major_division = choice_sub + "&" + et_major_division.getText().toString();

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

    }

    int spin_uni_col_major[][][] = {        //z축, x축, y축
            {{R.array.spin_uni}},
            {{R.array.spin_col_in}, {R.array.spin_major_inin, R.array.spin_major_ingi}},
            {{R.array.spin_col_sagwa}, {R.array.spin_major_sagwasagwa, R.array.spin_major_sagwakeo}},
            {{R.array.spin_col_ja}, {R.array.spin_major_jasu, R.array.spin_major_jahwa}},
            {{R.array.spin_col_el}, {R.array.spin_major_elso, R.array.spin_major_elcha, R.array.spin_major_elmi}},
            {{R.array.spin_col_gong}, {R.array.spin_major_gonggun, R.array.spin_major_gonghwan, R.array.spin_major_gonghwa}},
            {{R.array.spin_col_umm}, {R.array.spin_major_ummumm}},
            {{R.array.spin_col_jo}, {R.array.spin_major_jojo, R.array.spin_major_joseom, R.array.spin_major_jodi}},
            {{R.array.spin_col_sabeom}, {R.array.spin_major_sabeomteug, R.array.spin_major_sabeomsa, R.array.spin_major_sabeomgwa}},
            {{R.array.spin_col_kyung}, {R.array.spin_major_kyungkyung}},
            {{R.array.spin_col_sin}, {R.array.spin_major_sinche}},
            {{R.array.spin_col_ui}},
            {{R.array.spin_col_gan}, {R.array.spin_major_gangan}},
            {{R.array.spin_col_yag}, {R.array.spin_major_yagyag}},
            {{R.array.spin_col_seu}, {R.array.spin_major_seuseu, R.array.spin_major_seuyung, R.array.spin_major_seugug}},
            {{R.array.spin_col_gyo}},
            {{R.array.spin_col_gun}, {R.array.spin_major_gunche, R.array.spin_major_gungan}},
            {{R.array.spin_col_ho}},
            {{R.array.spin_col_AI}, {R.array.spin_major_AIAI}}
    };

}
