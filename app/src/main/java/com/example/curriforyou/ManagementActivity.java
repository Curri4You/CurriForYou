package com.example.curriforyou;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ManagementActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        TextView userListTextView = (TextView) findViewById(R.id.userListTextView);

        Intent intent = getIntent();
        userListTextView.setText(intent.getStringExtra("userList"));
    }


}
