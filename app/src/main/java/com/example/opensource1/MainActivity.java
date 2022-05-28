package com.example.opensource1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView tip;
    LinearLayout back;
    Button mdbtn, wtbtn, cotbtn, covidbtn;
    ImageView mainImg;
    String[] tipStr = new String[7];

    int y=0;
    int tip_num = 0;
    int sum = 0;
    int sky, dust;
    data input = new data();
    NowTime t = new NowTime();
    Timer timer;
    TimerTask timerTask;
    Thread setui = new Thread((new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if((sum/4)==0){
                        mainImg.setImageResource(R.drawable.emoji0);
                    }
                    else if((sum/4)==1)
                    {
                        mainImg.setImageResource(R.drawable.emoji1);
                    }
                    else if((sum/4)==2)
                    {
                        mainImg.setImageResource(R.drawable.emoji2);
                    }
                    else
                    {
                        mainImg.setImageResource(R.drawable.emoji3);
                    }
                    if(sky<1)
                    {
                        wtbtn.setBackgroundResource(R.drawable.sunny);
                        back.setBackgroundResource(R.drawable.background_sunny);
                    }
                    else if(sky==3||sky==7)
                    {
                        back.setBackgroundResource(R.drawable.background_snow);
                        wtbtn.setBackgroundResource(R.drawable.snow);

                    }
                    else
                    {
                        back.setBackgroundResource(R.drawable.background_rain);
                        wtbtn.setBackgroundResource(R.drawable.rain);
                    }
                    if(dust==0){
                        mdbtn.setBackgroundResource(R.drawable.dust0);
                    }
                    else if(dust==1)
                    {
                        mdbtn.setBackgroundResource(R.drawable.dust1);
                    }
                    else if(dust==2)
                    {
                        mdbtn.setBackgroundResource(R.drawable.dust2);
                    }
                    else
                    {
                        mdbtn.setBackgroundResource(R.drawable.dust3);
                    }
                }

            });
        }
    }));

    class getAPI extends Thread {
        @Override
        public void run() {
            super.run();

        }
    }

    class SetUI extends Thread {

        @Override
        public void run() {
            super.run();
        }

    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();// 액션바 삭제

        back = findViewById(R.id.back);
        tip = findViewById(R.id.tipText);
        mainImg = findViewById(R.id.mainImg);
        mdbtn = findViewById(R.id.mdButton);
        wtbtn = findViewById(R.id.weatherButton);
        covidbtn = findViewById(R.id.covidButton);
        cotbtn = findViewById(R.id.cotButton);
        timer = new Timer();
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

        //new getAPI().start();

        new Thread((new Runnable() {
            @Override
            public void run() {
                try {
                    get_Allergy_charmTree(input);
                    get_Allergy_SoTree(input);
                    get_Allergy_Jopcho(input);
                    getWeather(input);
                    getAirKor(input);
                    get_cold(input);
                    get_Allergy_Chunsik(input);
                } catch (IOException /*| JSONException*/ | ParseException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if((sum/4)==0){
                            mainImg.setImageResource(R.drawable.emoji0);
                        }
                        else if((sum/4)==1)
                        {
                            mainImg.setImageResource(R.drawable.emoji1);
                        }
                        else if((sum/4)==2)
                        {
                            mainImg.setImageResource(R.drawable.emoji2);
                        }
                        else
                        {
                            mainImg.setImageResource(R.drawable.emoji3);
                        }
                        if(sky<1)
                        {
                            wtbtn.setBackgroundResource(R.drawable.sunny);
                            back.setBackgroundResource(R.drawable.background_sunny);
                        }
                        else if(sky==3||sky==7)
                        {
                            back.setBackgroundResource(R.drawable.background_snow);
                            wtbtn.setBackgroundResource(R.drawable.snow);

                        }
                        else
                        {
                            back.setBackgroundResource(R.drawable.background_rain);
                            wtbtn.setBackgroundResource(R.drawable.rain);
                        }
                        if(dust==0){
                            mdbtn.setBackgroundResource(R.drawable.dust0);
                        }
                        else if(dust==1)
                        {
                            mdbtn.setBackgroundResource(R.drawable.dust1);
                        }
                        else if(dust==2)
                        {
                            mdbtn.setBackgroundResource(R.drawable.dust2);
                        }
                        else
                        {
                            mdbtn.setBackgroundResource(R.drawable.dust3);
                        }
                    }
                });
            }
        })).start();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(tip_num==0)
                    tipStr[tip_num++] = "오늘 날씨는 좋아요";
                tip.setText(tipStr[y]);
                if(y==tip_num) {
                    y = -1;
                }
                y++;
                if (setui.getState() == Thread.State.NEW)
                    setui.start();
            }
        };
        timer.schedule(timerTask,1000,1000);
    }
    void get_Allergy_charmTree(data in) throws IOException, ParseException {
        if (!in.charmTree_data.checkValid(t))
        {
            // 데이터를 제공하는 기간이 아닌 경우
            // 원래 데이터를 표시하는 오브젝트에 데이터 제공 기간이 아님을 표시하는 명령을 넣으면 됨
            return;
        }
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
            if(!item.get("today").toString().isEmpty())
                in.charmTree_data.setToday_val(item.get("today").toString());
            in.charmTree_data.setTomorrow_val(item.get("tomorrow").toString());
        }
        sum += Integer.parseInt(in.charmTree_data.today_val);
        if((Integer.parseInt(in.charmTree_data.today_val)>1))
            tipStr[tip_num++] = String.valueOf(R.string.chamtree_bad);
    }


    void get_Allergy_SoTree(data in) throws IOException, ParseException {
        if (!in.soTree_data.checkValid(t))
        {
            // 데이터를 제공하는 기간이 아닌 경우
            // 원래 데이터를 표시하는 오브젝트에 데이터 제공 기간이 아님을 표시하는 명령을 넣으면 됨
            return;
        }
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
            if(item.get("today").toString().isEmpty()) {

            }
            else {
                in.soTree_data.setToday_val(item.get("today").toString());
            }
            in.soTree_data.setTomorrow_val(item.get("tomorrow").toString());
        }
        sum += Integer.parseInt(in.soTree_data.today_val);
        if((Integer.parseInt(in.soTree_data.today_val)>1))
            tipStr[tip_num++] = String.valueOf(R.string.sotree_bad);
        System.out.println("소나무끝");
    }


    void get_Allergy_Jopcho(data in) throws IOException, ParseException {
        if (!in.jopcho_data.checkValid(t))
        {
            // 데이터를 제공하는 기간이 아닌 경우
            // 원래 데이터를 표시하는 오브젝트에 데이터 제공 기간이 아님을 표시하는 명령을 넣으면 됨
            return;
        }
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
            if(!item.get("today").toString().isEmpty())
                in.jopcho_data.setToday_val(item.get("today").toString());
            in.jopcho_data.setTomorrow_val(item.get("tomorrow").toString());
        }
        sum += Integer.parseInt(in.jopcho_data.today_val);
        if((Integer.parseInt(in.jopcho_data.today_val)>1))
        {
            tipStr[tip_num++] = String.valueOf(R.string.jabcho_bad);
        }
        System.out.println("잡초끝");
    }


    void getWeather(data in) throws IOException, ParseException {
        //초단기 실황 조회
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(t.getBase_date(), "UTF-8")); // 날짜를 가져오는 함수를 만들어두고 이를 통해 날짜를 용도에 맞는 형식의 String 변환이 필요
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));

        // 현 시간을 가져와서 가장 가까운 시간에 맞춰야함: 기준 시간은 30분마다 생성이며 기준 시간 + 10분이후부터 가능
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(t.getBase_time(), "UTF-8"));

        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("62", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("126", "UTF-8"));

        /*
        POP = 강수확률 % 단위
        PTY = 강수형태 0 = 없음 1 = 비 2 = 눈과 비 3 = 눈 4 = 소나기 5 = 빗방울 6 = 빗방울과 눈 날림 7 = 눈날림
        PCP 1시간 강수량 mm 단위
        REH 습도 % 단위
        SNO 1시간 적설량 cm 단위
        SKY 하늘상태 코드값 0 ~ 5 맑음 6 ~ 8 구름많음 9~10 흐림
        T1H 1시간 기온
        TMN 일 최저 기온
        TMX 일 최고 기온
         */

        URL url = new URL(urlBuilder.toString());

        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String result = bf.readLine();
        //파싱이 아직 안됨
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONObject response = (JSONObject)jsonObject.get("response");
        JSONObject body = (JSONObject)response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArr = (JSONArray)items.get("item");
        for (int i = 0; i < itemArr.size(); i++)
        {
            JSONObject item = (JSONObject) itemArr.get(i);

            in.weather_data.setValue(item.get("category").toString(), item.get("obsrValue").toString());
        }
        in.weather_data.set_dif_temp();
        if((Integer.parseInt(in.weather_data.pty))==0) {tipStr[tip_num++] = String.valueOf("오늘은 낡씨가 맑아요");}
        else if((Integer.parseInt(in.weather_data.pty))==4||(Integer.parseInt(in.weather_data.pty))==7)
            tipStr[tip_num++] = String.valueOf(R.string.is_snowing);
        else
            tipStr[tip_num++] = String.valueOf(R.string.is_raining);
        sky = Integer.parseInt(input.weather_data.pty);
        System.out.println(t.getBase_time());
        System.out.println(t.getBase_date());
    }


    void getAirKor(data in) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=T%2FL6AKT1fsWyadGE1j3QYFXgSmOWWV375pu6qQxuQ612F22wflZp0Ey%2BdjuPCZ8XeoShMs%2FaOPn3QJfpkbTlTw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*xml 또는 json*/
        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode("서울", "UTF-8")); /*통보시간 검색(조회 날짜 입력이 없을 경우 한달동안 예보통보 발령 날짜의 리스트 정보를 확인)*/
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); /*통보코드검색(PM10, PM25, O3)*/
        URL url = new URL(urlBuilder.toString());

        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String result = bf.readLine();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONObject response = (JSONObject)jsonObject.get("response");
        JSONObject body = (JSONObject)response.get("body");
        JSONObject totalCount = (JSONObject)jsonObject.get("totalCount");
        JSONArray item = (JSONArray)body.get("items");

        for (int i = 0; i < item.size(); i++)
        {
            JSONObject items = (JSONObject) item.get(i);
            String str = items.get("stationName").toString();
            String sung = "성동구";
            if(str.equals(sung)){
                int p1, p2;

                in.airKorDust_data.setPm10val(items.get("pm10Value").toString());
                in.airKorDust_data.setStationName(items.get("stationName").toString());
                in.airKorDust_data.setPm25val(items.get("pm25Value").toString());
                p1 = Integer.parseInt(in.airKorDust_data.pm10val);
                p2 = Integer.parseInt(in.airKorDust_data.pm25val);
                System.out.println(p1);
                if(p1<31)
                {
                    in.airKorDust_data.setPm10Grade1h("0");
                }
                else if(p1<81)
                {
                    in.airKorDust_data.setPm10Grade1h("1");
                }
                else if(p1<151)
                {
                    in.airKorDust_data.setPm10Grade1h("2");
                }
                else
                {
                    in.airKorDust_data.setPm10Grade1h("3");
                }
                if (p2 < 15)
                {
                    in.airKorDust_data.setPm25Grade1h("0");
                }
                else if(p1<51)
                {
                    in.airKorDust_data.setPm25Grade1h("1");
                }
                else if(p1<101)
                {
                    in.airKorDust_data.setPm25Grade1h("2");
                }
                else
                {
                    in.airKorDust_data.setPm25Grade1h("3");
                }

            }//성동구 미세먼지 할당
        }
        if (in.airKorDust_data.pm10Grade1h != null)
            sum += Integer.parseInt(in.airKorDust_data.pm10Grade1h);
        if (in.airKorDust_data.pm25Grade1h != null)
            sum += Integer.parseInt(in.airKorDust_data.pm25Grade1h);

        if(in.airKorDust_data.pm10Grade1h != null ) {
            if ((Integer.parseInt(in.airKorDust_data.pm10Grade1h)>1))
                tipStr[tip_num++] = String.valueOf(R.string.md_bad);
        }
        else
        {
            if(Integer.parseInt(in.airKorDust_data.pm10val)<31)
            {
                in.airKorDust_data.setPm10Grade1h("0");
            }
            else if(Integer.parseInt(in.airKorDust_data.pm10val)<81)
            {
                in.airKorDust_data.setPm10Grade1h("1");
            }
            else if(Integer.parseInt(in.airKorDust_data.pm10val)<151)
            {
                in.airKorDust_data.setPm10Grade1h("2");
            }
            else
            {
                in.airKorDust_data.setPm10Grade1h("3");
            }
            if (Integer.parseInt(in.airKorDust_data.pm25val) < 15)
            {
                in.airKorDust_data.setPm25Grade1h("0");
            }
            else if(Integer.parseInt(in.airKorDust_data.pm25val)<51)
            {
                in.airKorDust_data.setPm25Grade1h("1");
            }
            else if(Integer.parseInt(in.airKorDust_data.pm25val)<101)
            {
                in.airKorDust_data.setPm25Grade1h("2");
            }
            else
            {
                in.airKorDust_data.setPm25Grade1h("3");
            }
            if(in.airKorDust_data.pm10Grade1h != null && (Integer.parseInt(in.airKorDust_data.pm10Grade1h)>1))
                tipStr[tip_num++] = String.valueOf(R.string.md_bad);
        }
        dust = Integer.parseInt(input.airKorDust_data.pm10Grade1h);
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
            if(item.get("today").toString().isEmpty()) {

            }
            else {
                in.chunsik_data.setToday_val(item.get("today").toString());
            }
            in.chunsik_data.setTomorrow_val(item.get("tomorrow").toString());
        }
        sum += Integer.parseInt(in.chunsik_data.today_val);

        if((Integer.parseInt(in.chunsik_data.today_val)>1))
        {
            System.out.println("천식 알림 추가: " + R.string.chunsik_bad);
            tipStr[tip_num++] = String.valueOf("천식지수가 나쁘니 주의하세요");
        }


        System.out.println("천식끝");
    }

    void get_cold(data in) throws IOException, ParseException {
        if (!in.cold_data.checkValid(t))
        {
            // 데이터를 제공하는 기간이 아닌 경우
            // 원래 데이터를 표시하는 오브젝트에 데이터 제공 기간이 아님을 표시하는 명령을 넣으면 됨
            return;
        }
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/HealthWthrIdxServiceV2/getColdIdxV2"); /*URL*/
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
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONObject response = (JSONObject)jsonObject.get("response");
        JSONObject body = (JSONObject)response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray itemArr = (JSONArray)items.get("item");
        for (int i = 0; i < itemArr.size(); i++)
        {
            JSONObject item = (JSONObject) itemArr.get(i);

            in.cold_data.setDate(item.get("date").toString());

            if(!item.get("today").toString().isEmpty())
                in.cold_data.setToday_val(item.get("today").toString());

            in.cold_data.setTomorrow_val(item.get("tomorrow").toString());
        }
        this.sum += Integer.parseInt(in.cold_data.today_val);

        if((Integer.parseInt(in.cold_data.today_val)>1))
            tipStr[tip_num++] = String.valueOf(R.string.cold_bad);

        System.out.println("감기끝");
    }
}