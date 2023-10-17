package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appfood.R;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    Button btnstart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initControl();

    }

    private void initControl() {
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent start = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(start);

            }
        });
    }

    private void initView() {
        btnstart = findViewById(R.id.btnstart);
    }
}