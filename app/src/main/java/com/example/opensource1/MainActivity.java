package com.example.opensource1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.simple.JSONObject;

import android.os.Build;
import org.json.simple.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.JSONParser;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Text;

import java.time.ZoneId;

import java.sql.Date;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView tip;
    Button mdbtn, wtbtn, cotbtn, covidbtn, testbtn;
    String[] tipStr = new String[4];
    int x=0;
    int y=0;
    Timer t;
    TimerTask timerTask;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();// 액션바 삭제

        tip = findViewById(R.id.tipText);
        mdbtn = findViewById(R.id.mdButton);
        wtbtn = findViewById(R.id.weatherButton);
        covidbtn = findViewById(R.id.covidButton);
        cotbtn = findViewById(R.id.cotButton);
        testbtn = findViewById(R.id.testButton);
        tipStr[0] = "1번 팁";
        tipStr[1] = "2번 팁";
        tipStr[2] = "3번 팁";
        tipStr[3] = "4번 팁";
        t = new Timer();

        long now = System.currentTimeMillis();
        Date dateSys = new Date(now);
        String dateSys_Str = dateSys.toString();
        //System.out.println(dateSys_Str);
        String year = dateSys_Str.substring(0, 4);
        String month = dateSys_Str.substring(5, 7);
        String day = dateSys_Str.substring(8, 10);


        // 할당
        timerTask = new TimerTask() {
            @Override
            public void run() {
                changeTip(y);
                if(y==3) {
                    y = -1;
                }
                y++;
            }

        };
        t.schedule(timerTask,1000,1000);

        mdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Microdust.class);
                startActivity(intent);
            }
        });

        cotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Cotgaru.class);
                startActivity(intent);
            }
        });

        wtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Weather.class);
                startActivity(intent);
            }
        });

        covidbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Covid.class);
                startActivity(intent);
            }
        });

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x++;
                if(x==1){
                    changeTip(0);
                }
                else if(x==2)
                {
                    changeTip(1);
                }
                else if(x==3)
                {
                    changeTip(2);
                }
                else
                {
                    changeTip(3);
                    x=0;
                }

            }
        });//함수 구현 부분

    }

    private void changeTip(int x){
        tip.setText(tipStr[x]);
        //t.cancel();
    }


}