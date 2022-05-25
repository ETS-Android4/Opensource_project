package com.example.opensource1;

public class data {
    air_kor_dust airKorDust_data;
    chunsik chunsik_data;
    soTree soTree_data;
    charmTree charmTree_data;
    jopcho jopcho_data;
    weather weather_data;
    cold cold_data;
    String testStr = new String("teststr");

    data(){
        airKorDust_data = new air_kor_dust();
        chunsik_data = new chunsik();
        soTree_data = new soTree();
        charmTree_data = new charmTree();
        jopcho_data = new jopcho();
        weather_data = new weather();
        cold_data = new cold();
    }

    public class air_kor_dust {
        //Air Kor의 데이터는 가장 최신의 데이터만 전해주기에 time에 대한 data 필요 없음
        String sidoName;
        String stationName;
        String pm25val;
        String pm10val;

        public void setSidoName(String sidoName) {
            this.sidoName = sidoName;
        }
        public void setStationName(String stationName) {
            this.stationName = stationName;
        }
        public void setPm25val(String pm25val) {
            this.pm25val = pm25val;
        }
        public void setPm10val(String pm10val) {
            this.pm10val = pm10val;
        }
    }

    public class chunsik {
        String date;
        String today_val;
        String tomorrow_val;

        public void setDate(String date) {
            this.date = date;
        }
        public void setToday_val(String today_val) {
            this.today_val = today_val;
        }
        public void setTomorrow_val(String tomorrow_val) {
            this.tomorrow_val = tomorrow_val;
        }
    }

    //각 알러지 데이터는 위험도가 0 1 2 3 으로 0이 낮음 1이 보통 2가 높음 3이 매우높음임
    public class soTree {
        String date;
        String today_val;
        String tomorrow_val;

        public boolean checkValid(NowTime t){
            int month_int = Integer.parseInt(t.getMonth());
            if (month_int < 4 || month_int > 6)
                return false;
            return true;
        }

        public void setDate(String date) {
            this.date = date;
        }
        public void setToday_val(String today_val) {
            this.today_val = today_val;
        }
        public void setTomorrow_val(String tomorrow_val) {
            this.tomorrow_val = tomorrow_val;
        }
    }

    public class charmTree {
        String date;
        String today_val;
        String tomorrow_val;


        public boolean checkValid(NowTime t){
            int month_int = Integer.parseInt(t.getMonth());
            if (month_int < 4 || month_int > 6)
                return false;
            return true;
        }

        public void setDate(String date) {
            this.date = date;
        }
        public void setToday_val(String today_val) {
            this.today_val = today_val;
        }
        public void setTomorrow_val(String tomorrow_val) {
            this.tomorrow_val = tomorrow_val;
        }
    }

    public class jopcho {
        String date;
        String today_val;
        String tomorrow_val;


        public boolean checkValid(NowTime t){
            int month_int = Integer.parseInt(t.getMonth());
            if (month_int < 8 || month_int > 10)
                return false;
            return true;
        }

        public void setDate(String date) {
            this.date = date;
        }
        public void setToday_val(String today_val) {
            this.today_val = today_val;
        }
        public void setTomorrow_val(String tomorrow_val) {
            this.tomorrow_val = tomorrow_val;
        }
    }

    public class cold {
        String date;
        String today_val;
        String tomorrow_val;

        public void setDate(String date) {
            this.date = date;
        }
        public void setToday_val(String today_val) {
            this.today_val = today_val;
        }
        public void setTomorrow_val(String tomorrow_val) {
            this.tomorrow_val = tomorrow_val;
        }
    }

    //기상청 데이터
    //입력 받는 category에 따라 데이터를 입력 받아옴.
    public class weather {
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
        String base_date;
        String pop = "-1";
        String pty = "-1";
        String pcp = "-1";
        String reh = "-1";
        String sno = "-1";
        String sky = "-1";
        String t1h = "-1";
        String tmn = "-1";
        String tmx = "-1";
        String dif_temp = "-1";

        public void set_dif_temp(){
            int max, min;
            max = Integer.parseInt(tmx);
            min = Integer.parseInt(tmn);
            this.dif_temp = Integer.toString(max - min);
        }

        public void setValue(String type, String val) {
            switch (type)
            {
                case "POP":
                    pop = val;
                    break;
                case "PTY":
                    pty = val;
                    break;
                case "PCP":
                    pcp = val;
                    break;
                case "REH":
                    reh = val;
                    break;
                case "SNO":
                    sno = val;
                    break;
                case "SKY":
                    sky = val;
                    break;
                case "T1H":
                    t1h = val;
                    break;
                default: break;
            }
        }
    }

}
