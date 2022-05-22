package com.example.opensource1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.simple.JSONObject;

import android.os.Build;
import android.os.Bundle;
import org.json.simple.JSONArray;
import android.widget.TextView;
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

public class Microdust extends AppCompatActivity {
    TextView pm10, pm25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microdust);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();// 액션바 삭제

        pm10 = findViewById(R.id.textView);
        pm25 = findViewById(R.id.textView2);
        //할당

        new Thread(()-> {
            try {
                getAirKor();
                //getWeather_flower();
                //getCorona();
                //getWeather_chunsik();
                //getWeather();
            } catch (IOException /*| JSONException*/ | ParseException e) {
                e.printStackTrace();
            }
        }).start();


    }

    void getAirKor() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); //URL
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); //Service Key
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); //xml 또는 json
        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode("서울", "UTF-8")); //통보시간 검색(조회 날짜 입력이 없을 경우 한달동안 예보통보 발령 날짜의 리스트 정보를 확인)
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); //통보코드검색(PM10, PM25, O3)
        URL url = new URL(urlBuilder.toString());


        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String result = bf.readLine();
        //파싱이 아직 안됨
        System.out.println(result);


        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONObject response = (JSONObject)jsonObject.get("response");
        JSONObject body = (JSONObject)response.get("body");
        System.out.println("body = " + body);
        JSONObject totalCount = (JSONObject)jsonObject.get("totalCount");
        JSONArray item = (JSONArray)body.get("items");
        for (int i = 0; i < item.size(); i++)
        {
            JSONObject items = (JSONObject) item.get(i);
            System.out.println("시도명: " + items.get("sidoName").toString());
            System.out.println("지역명: " + items.get("stationName").toString());
            System.out.println("pm25Val: " + items.get("pm25Value").toString());
            pm25.setText(items.get("pm25Value").toString());
            System.out.println("pm10Val: " + items.get("pm10Value").toString());
            pm10.setText(items.get("pm10Value").toString());
        }
    }

}