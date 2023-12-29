package com.nemo.kr.dto;

public class CityDTO extends DefaultDTO{
	private String city_seq;
	private String city_name;
	private String city_code;
	private String nx;
	private String ny;
	private String longitude;
	private String latitude;
	private String query;
	
	public String getCity_seq() {
		return city_seq;
	}
	public void setCity_seq(String city_seq) {
		this.city_seq = city_seq;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getNx() {
		return nx;
	}
	public void setNx(String nx) {
		this.nx = nx;
	}
	public String getNy() {
		return ny;
	}
	public void setNy(String ny) {
		this.ny = ny;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}

}
