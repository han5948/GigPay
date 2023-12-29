package com.nemo.kr.dto;

public class WeatherDTO extends DefaultDTO{
	
	private String weather_seq;			// 순번
	private String base_date;			// 발표 날짜
	private String fcst_date;			// 예측 날짜
	private String fcst_time;			// 예측 시간
	private String city_code;			// 도시 코드
	private String pptn_pct;			// 강수 확률 단위:%
	private String pptn_form;			// 강수형태 코드 값 : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4), 빗방울(5), 빗방울/눈날림(6), 눈날림(7)
	private String six_hours_pptn;		// 6시간 강수량 단위:mm[api 버젼1 에서 적용]
	private String hours_pptn;			// 시간당 강수량 단위:mm[api 버젼2 에서 적용]
	private String humidity;			// 습도 단위:%
	private String six_hours_snowfall;	// 6시간 신적설 단위:cm[api 버젼1 에서 적용]
	private String hours_snowfall;		// 시간당 신적설 단위:cm[api 버젼2 에서 적용]
	private String sky_status;			// 하늘상태 코드 값: 맑음(1), 구름많음(3), 흐림(4)
	private String three_hours_temp;	// 3시간기온 단위:℃[api 버젼1 에서 적용]
	private String hours_temp;			// 시간당 기온 단위:℃[api 버젼2 에서 적용]
	private String min_temp;			// 최저기온 단위:℃
	private String high_temp;			// 최고기온 단위:℃
	private String wind_speedEw;		// 풍속(동서성분) 단위:m/s
	private String wind_speedSn;		// 풍속(남북성분) 단위:m/s
	private String wave;				// 파고 단위:M
	private String wind_dir;			// 풍향 단위:m/s
	private String wind_speed;			// 풍속 단위:m/s
	private String job_id;				// 잡 아이디
	private String version;				// api 버젼
	private String reg_Date;			// 등록일자
	
	
	public String getHours_pptn() {
		return hours_pptn;
	}
	public void setHours_pptn(String hours_pptn) {
		this.hours_pptn = hours_pptn;
	}
	public String getHours_snowfall() {
		return hours_snowfall;
	}
	public void setHours_snowfall(String hours_snowfall) {
		this.hours_snowfall = hours_snowfall;
	}
	public String getHours_temp() {
		return hours_temp;
	}
	public void setHours_temp(String hours_temp) {
		this.hours_temp = hours_temp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getWeather_seq() {
		return weather_seq;
	}
	public void setWeather_seq(String weather_seq) {
		this.weather_seq = weather_seq;
	}
	public String getBase_date() {
		return base_date;
	}
	public void setBase_date(String base_date) {
		this.base_date = base_date;
	}
	public String getFcst_date() {
		return fcst_date;
	}
	public void setFcst_date(String fcst_date) {
		this.fcst_date = fcst_date;
	}
	public String getFcst_time() {
		return fcst_time;
	}
	public void setFcst_time(String fcst_time) {
		this.fcst_time = fcst_time;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getPptn_pct() {
		return pptn_pct;
	}
	public void setPptn_pct(String pptn_pct) {
		this.pptn_pct = pptn_pct;
	}
	public String getPptn_form() {
		return pptn_form;
	}
	public void setPptn_form(String pptn_form) {
		this.pptn_form = pptn_form;
	}
	public String getSix_hours_pptn() {
		return six_hours_pptn;
	}
	public void setSix_hours_pptn(String six_hours_pptn) {
		this.six_hours_pptn = six_hours_pptn;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getSix_hours_snowfall() {
		return six_hours_snowfall;
	}
	public void setSix_hours_snowfall(String six_hours_snowfall) {
		this.six_hours_snowfall = six_hours_snowfall;
	}
	public String getSky_status() {
		return sky_status;
	}
	public void setSky_status(String sky_status) {
		this.sky_status = sky_status;
	}
	public String getThree_hours_temp() {
		return three_hours_temp;
	}
	public void setThree_hours_temp(String three_hours_temp) {
		this.three_hours_temp = three_hours_temp;
	}
	public String getMin_temp() {
		return min_temp;
	}
	public void setMin_temp(String min_temp) {
		this.min_temp = min_temp;
	}
	public String getHigh_temp() {
		return high_temp;
	}
	public void setHigh_temp(String high_temp) {
		this.high_temp = high_temp;
	}
	public String getWind_speedEw() {
		return wind_speedEw;
	}
	public void setWind_speedEw(String wind_speedEw) {
		this.wind_speedEw = wind_speedEw;
	}
	public String getWind_speedSn() {
		return wind_speedSn;
	}
	public void setWind_speedSn(String wind_speedSn) {
		this.wind_speedSn = wind_speedSn;
	}
	public String getWave() {
		return wave;
	}
	public void setWave(String wave) {
		this.wave = wave;
	}
	public String getWind_dir() {
		return wind_dir;
	}
	public void setWind_dir(String wind_dir) {
		this.wind_dir = wind_dir;
	}
	public String getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(String wind_speed) {
		this.wind_speed = wind_speed;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getReg_Date() {
		return reg_Date;
	}
	public void setReg_Date(String reg_Date) {
		this.reg_Date = reg_Date;
	}
	
		
}
