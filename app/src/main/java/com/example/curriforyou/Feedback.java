package com.example.curriforyou;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Feedback extends AppCompatActivity implements View.OnClickListener {

    ImageView navigate_before, star1, star2, star3, star4, star5;
    Button btn_fb_category, btn_fb_submit;
    EditText et_feedback;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        navigate_before = (ImageView) findViewById(R.id.navigate_before);
        btn_fb_category = (Button) findViewById(R.id.btn_fb_category);
        btn_fb_submit = (Button) findViewById(R.id.btn_fb_submit);
        star1 = (ImageView) findViewById(R.id.star1);
        star2 = (ImageView) findViewById(R.id.star2);
        star3 = (ImageView) findViewById(R.id.star3);
        star4 = (ImageView) findViewById(R.id.star4);
        star5 = (ImageView) findViewById(R.id.star5);
        et_feedback = (EditText) findViewById(R.id.et_feedback);

        navigate_before.setOnClickListener(this);
        btn_fb_category.setOnClickListener(this);
        btn_fb_submit.setOnClickListener(this);
        star1.setOnClickListener(this);
        star2.setOnClickListener(this);
        star3.setOnClickListener(this);
        star4.setOnClickListener(this);
        star5.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fb_submit:
                star1.setImageResource(R.drawable.star_outline);
                star2.setImageResource(R.drawable.star_outline);
                star3.setImageResource(R.drawable.star_outline);
                star4.setImageResource(R.drawable.star_outline);
                star5.setImageResource(R.drawable.star_outline);
                et_feedback.setText("");
                Toast fb_submit = Toast.makeText(getApplicationContext(), "제출이 완료되었습니다.", Toast.LENGTH_SHORT);
                fb_submit.setGravity(Gravity.CENTER, 0, 0);
                fb_submit.show();
            case R.id.navigate_before:
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
                break;
            case R.id.star1:
                star1.setImageResource(R.drawable.star_filled);
                star2.setImageResource(R.drawable.star_outline);
                star3.setImageResource(R.drawable.star_outline);
                star4.setImageResource(R.drawable.star_outline);
                star5.setImageResource(R.drawable.star_outline);
                break;
            case R.id.star2:
                star1.setImageResource(R.drawable.star_filled);
                star1.setImageResource(R.drawable.star_filled);
                star3.setImageResource(R.drawable.star_outline);
                star4.setImageResource(R.drawable.star_outline);
                star5.setImageResource(R.drawable.star_outline);
                break;
            case R.id.star3:
                star1.setImageResource(R.drawable.star_filled);
                star2.setImageResource(R.drawable.star_filled);
                star3.setImageResource(R.drawable.star_filled);
                star4.setImageResource(R.drawable.star_outline);
                star5.setImageResource(R.drawable.star_outline);
                break;
            case R.id.star4:
                star1.setImageResource(R.drawable.star_filled);
                star2.setImageResource(R.drawable.star_filled);
                star3.setImageResource(R.drawable.star_filled);
                star4.setImageResource(R.drawable.star_filled);
                star5.setImageResource(R.drawable.star_outline);
                break;
            case R.id.star5:
                star1.setImageResource(R.drawable.star_filled);
                star2.setImageResource(R.drawable.star_filled);
                star3.setImageResource(R.drawable.star_filled);
                star4.setImageResource(R.drawable.star_filled);
                star5.setImageResource(R.drawable.star_filled);
                break;
            case R.id.btn_fb_category:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("카테고리 선택");
                builder.setItems(R.array.feedback_category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] items = getResources().getStringArray(R.array.feedback_category);
                        btn_fb_category.setText(items[i]);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
    }

}
