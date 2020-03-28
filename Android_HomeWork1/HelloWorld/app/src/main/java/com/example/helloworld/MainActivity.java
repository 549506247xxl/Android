package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1 = findViewById(R.id.btn1); // 找到布局中的控件
        final TextView tv1 = findViewById(R.id.tv1);
        final ImageButton ib1 = findViewById(R.id.ib1);
        Log.d("MainActivity","this a dubug log for MainActivity"); // 日志功能
        btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                tv1.setText("What a wonderful world!");
                Log.d("btn1","you press the button btn1");
            }
        });
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ib1","you press the ImageButton ib1");
                ib1.setVisibility(View.INVISIBLE);
            }
        });


    }
}
