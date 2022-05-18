package com.example.opensource1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Cotgaru extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotgaru);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();// 액션바 삭제
    }
}