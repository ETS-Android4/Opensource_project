package com.example.opensource1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;import androidx.annotation.RequiresApi;
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

import android.os.Bundle;

public class Cotgaru extends AppCompatActivity {
    TextView soTree, jabcho, chamTree, soTreeTv, jabchoTv, chamTreeTv;
    data input = new data();
    NowTime t = new NowTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotgaru);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();// 액션바 삭제

        soTree = findViewById(R.id.nowSoTree);
        jabcho = findViewById(R.id.nowJabcho);
        chamTree = findViewById(R.id.nowChamTree);
        soTreeTv = findViewById(R.id.soTreetv);
        jabchoTv = findViewById(R.id.jabchotv);
        chamTreeTv = findViewById(R.id.chamTreetv);


        new Thread((new Runnable() {
            @Override
            public void run() {
                try {
                    get_Allergy_charmTree(input);
                    get_Allergy_SoTree(input);
                    get_Allergy_Jopcho(input);
                } catch (IOException /*| JSONException*/ | ParseException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        soTree.setText(input.soTree_data.today_val);
                        jabcho.setText(input.jopcho_data.today_val);
                        chamTree.setText(input.charmTree_data.today_val);
                    }
                });
            }
        })).start();
    }
    void get_Allergy_charmTree(data in) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/HealthWthrIdxServiceV2/getOakPollenRiskIdxV2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*통보시간 검색(조회 날짜 입력이 없을 경우 한달동안 예보통보 발령 날짜의 리스트 정보를 확인)*/
        urlBuilder.append("&" + URLEncoder.encode("areaNo","UTF-8") + "=" + URLEncoder.encode("1100000000", "UTF-8")); //서울 코드: 1100000000
        urlBuilder.append("&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode(t.getTime(), "UTF-8")); /*통보코드검색(PM10, PM25, O3)*/
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
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArr = (JSONArray)items.get("item");
        for (int i = 0; i < itemArr.size(); i++)
        {
            JSONObject item = (JSONObject) itemArr.get(i);
            in.charmTree_data.setDate(item.get("date").toString());
            in.charmTree_data.setToday_val(item.get("today").toString());
            in.charmTree_data.setTomorrow_val(item.get("tomorrow").toString());
        }
    }

    void get_Allergy_SoTree(data in) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/HealthWthrIdxServiceV2/getPinePollenRiskIdxV2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*통보시간 검색(조회 날짜 입력이 없을 경우 한달동안 예보통보 발령 날짜의 리스트 정보를 확인)*/
        urlBuilder.append("&" + URLEncoder.encode("areaNo","UTF-8") + "=" + URLEncoder.encode("1100000000", "UTF-8")); //서울 코드: 1100000000
        urlBuilder.append("&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode(t.getTime(), "UTF-8")); // 하루 예측 시간은 06시 기준
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("30", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        URL url = new URL(urlBuilder.toString());

        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String result = bf.readLine();

        System.out.println(result);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONObject response = (JSONObject)jsonObject.get("response");
        JSONObject body = (JSONObject)response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArr = (JSONArray)items.get("item");
        for (int i = 0; i < itemArr.size(); i++)
        {
            JSONObject item = (JSONObject) itemArr.get(i);

            in.soTree_data.setDate(item.get("date").toString());
            in.soTree_data.setToday_val(item.get("today").toString());
            in.soTree_data.setTomorrow_val(item.get("tomorrow").toString());

        }
    }

    void get_Allergy_Jopcho(data in) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/HealthWthrIdxServiceV2/getWeedsPollenRiskndxV2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("areaNo","UTF-8") + "=" + URLEncoder.encode("1100000000", "UTF-8")); //서울 코드: 1100000000
        urlBuilder.append("&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode(t.getTime(), "UTF-8")); // 하루 예측 시간은 06시 기준
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("30", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
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
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArr = (JSONArray)items.get("item");
        for (int i = 0; i < itemArr.size(); i++)
        {
            JSONObject item = (JSONObject) itemArr.get(i);

            in.jopcho_data.setDate(item.get("date").toString());
            in.jopcho_data.setToday_val(item.get("today").toString());
            in.jopcho_data.setTomorrow_val(item.get("tomorrow").toString());

        }
    }

    void get_Allergy_Chunsik(data in) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/HealthWthrIdxServiceV2/getAsthmaIdxV2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*통보시간 검색(조회 날짜 입력이 없을 경우 한달동안 예보통보 발령 날짜의 리스트 정보를 확인)*/
        urlBuilder.append("&" + URLEncoder.encode("areaNo","UTF-8") + "=" + URLEncoder.encode("1100000000", "UTF-8")); //서울 코드: 1100000000
        urlBuilder.append("&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode(t.getTime(), "UTF-8")); /*통보코드검색(PM10, PM25, O3)*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
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
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArr = (JSONArray)items.get("item");
        for (int i = 0; i < itemArr.size(); i++)
        {
            JSONObject item = (JSONObject) itemArr.get(i);

            in.chunsik_data.setDate(item.get("date").toString());
            in.chunsik_data.setToday_val(item.get("today").toString());
            in.chunsik_data.setTomorrow_val(item.get("tomorrow").toString());

            //System.out.println("날짜: " + item.get("date").toString());
            //System.out.println("오늘 위험도: " + item.get("today").toString());
            //System.out.println("내일 위험도: " + item.get("tomorrow").toString());
        }

    }
}