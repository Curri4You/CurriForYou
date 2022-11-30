package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage_addMajor extends AppCompatActivity implements View.OnClickListener {

    ArrayAdapter<CharSequence> spin_uniAdapter, spin_colAdapter, spin_majAdapter;
    String choice_uni = "";
    String choice_col = "";
    String choice_maj = "";
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_add_major);

        //Spinner - university
        final Spinner spin_university_name = (Spinner) findViewById(R.id.spin_university_name);
        final Spinner spin_college_name = (Spinner) findViewById(R.id.spin_college_name);
        final Spinner spin_major_name = (Spinner) findViewById(R.id.spin_major_name);

        spin_uniAdapter = ArrayAdapter.createFromResource(MyPage_addMajor.this, R.array.spin_uni, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spin_uniAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spin_university_name.setAdapter(spin_uniAdapter);

        ImageView btn_infoModification = (ImageView) findViewById(R.id.navigate_before);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        btn_infoModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });

        spin_university_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //선택한 item의 index가 0 ~ 17 인 경우
                if (position >= 0 && position <= 17){     //spin_uniAdapter.getItem(position).equals("인문과학대학")
                    int uni = position;
                    choice_uni = spin_uniAdapter.getItem(position).toString();

                    spin_colAdapter = ArrayAdapter.createFromResource(MyPage_addMajor.this, spin_uni_col_major[(uni+1)][0][0], androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    spin_colAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    spin_college_name.setAdapter(spin_colAdapter);

                    spin_college_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int col = position;
                            if(col < spin_uni_col_major[(uni+1)][1].length) {
                                choice_col = spin_colAdapter.getItem(position).toString();

                                spin_majAdapter = ArrayAdapter.createFromResource(MyPage_addMajor.this, spin_uni_col_major[(uni + 1)][1][col], androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
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
                                spin_majAdapter = ArrayAdapter.createFromResource(MyPage_addMajor.this, R.array.spin_major_all, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_submit:
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                intent.putExtra("col_name", choice_col);
                intent.putExtra("major_name", choice_maj);
                intent.putExtra("check_addMajor", true);
                startActivity(intent);
                break;
        }
    }
}
