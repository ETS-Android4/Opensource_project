package com.part1.practice;

import androidx.annotation.RequiresApi;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long now = System.currentTimeMillis();
        Date dateSys = new Date(now);
        String dateSys_Str = dateSys.toString();
        //System.out.println(dateSys_Str);
        String year = dateSys_Str.substring(0, 4);
        String month = dateSys_Str.substring(5, 7);
        String day = dateSys_Str.substring(8, 10);
        new Thread(()-> {
            try {
                //getAirKor();
                //getWeather_flower();
                //getCorona();
                //getWeather_chunsik();
                getWeather();
            } catch (IOException /*| JSONException*/ | ParseException e) {
                e.printStackTrace();
            }
        }).start();
    }

    void getAirKor() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*xml 또는 json*/
        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode("서울", "UTF-8")); /*통보시간 검색(조회 날짜 입력이 없을 경우 한달동안 예보통보 발령 날짜의 리스트 정보를 확인)*/
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); /*통보코드검색(PM10, PM25, O3)*/
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
            System.out.println("pm10Val: " + items.get("pm10Value").toString());
        }
    }

    void getWeather_flower() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/HealthWthrIdxServiceV2/getOakPollenRiskIdxV2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*통보시간 검색(조회 날짜 입력이 없을 경우 한달동안 예보통보 발령 날짜의 리스트 정보를 확인)*/
        urlBuilder.append("&" + URLEncoder.encode("areaNo","UTF-8") + "=" + URLEncoder.encode("1100000000", "UTF-8")); //서울 코드: 1100000000
        urlBuilder.append("&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode("2022052106", "UTF-8")); /*통보코드검색(PM10, PM25, O3)*/
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
            System.out.println("날짜: " + item.get("date").toString());
            System.out.println("오늘 위험도: " + item.get("today").toString());
            System.out.println("내일 위험도: " + item.get("tomorrow").toString());
        }

    }

    void getWeather_chunsik() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/HealthWthrIdxServiceV2/getAsthmaIdxV2"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*통보시간 검색(조회 날짜 입력이 없을 경우 한달동안 예보통보 발령 날짜의 리스트 정보를 확인)*/
        urlBuilder.append("&" + URLEncoder.encode("areaNo","UTF-8") + "=" + URLEncoder.encode("1100000000", "UTF-8")); //서울 코드: 1100000000
        urlBuilder.append("&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode("2022052106", "UTF-8")); /*통보코드검색(PM10, PM25, O3)*/
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
            System.out.println("날짜: " + item.get("date").toString());
            System.out.println("오늘 위험도: " + item.get("today").toString());
            System.out.println("내일 위험도: " + item.get("tomorrow").toString());
        }

    }

    void getWeather() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20220522", "UTF-8")); // 날짜를 가져오는 함수를 만들어두고 이를 통해 날짜를 용도에 맞는 형식의 String 변환이 필요
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));

        // 현 시간을 가져와서 가장 가까운 시간에 맞춰야함: 기준 시간은 30분마다 생성이며 기준 시간 + 10분이후부터 가능
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0100", "UTF-8"));

        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("62", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("126", "UTF-8"));

        /*
        POP = 강수확률 % 단위
        PTY = 강수형태 0 = 없음 1 = 비 2 = 눈과 비 3 = 눈 4 = 소나기 5 = 빗방울 6 = 빗방울과 눈 날림 7 = 눈날림
        PCP 1시간 강수량 mm 단위
        REH 습도 % 단위
        SNO 1시간 적설량 cm 단위
        SKY 하늘상태 코드값 0 ~ 5 맑음 6 ~ 8 구름많음 9~10 흐림
        TMP 1시간 기온
        TMN 일 최저 기온
        TMX 일 최고 기온
         */

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
            System.out.println("날짜: " + item.get("baseDate").toString());
            System.out.println("기준 시간: " + item.get("baseTime").toString());
            System.out.println("날씨: " + item.get("category").toString());
            System.out.println("실황 값: " + item.get("obsrValue"));
        }
    }


}
