package com.nemo.kr.dto;

public class JobDTO extends DefaultDTO {
	private static final long serialVersionUID = 3944304017057338288L;
	
	private String job_seq;
	private String job_kind;
	private String job_field;
	private String job_worker_fee;
	private String job_work_fee;
	private String job_image_name;
	private String job_code;
	private String job_name;
	private String job_rank;
	private String job_background;
	private String job_color;
	private String basic_price;
	private String short_price;
	private String short_price_night;
	private String add_day_time;
	private String add_night_time;
	private String detail_use_yn;
	private String job_flag;
	private Object job_seq_arr;
	private String job_order;
	private String before_job_name;
	
	public String getJob_worker_fee() {
		return job_worker_fee;
	}
	public void setJob_worker_fee(String job_worker_fee) {
		this.job_worker_fee = job_worker_fee;
	}
	public String getJob_work_fee() {
		return job_work_fee;
	}
	public void setJob_work_fee(String job_work_fee) {
		this.job_work_fee = job_work_fee;
	}
	public String getJob_field() {
		return job_field;
	}
	public void setJob_field(String job_field) {
		this.job_field = job_field;
	}
	public String getBefore_job_name() {
		return before_job_name;
	}
	public void setBefore_job_name(String before_job_name) {
		this.before_job_name = before_job_name;
	}
	public String getJob_order() {
		return job_order;
	}
	public void setJob_order(String job_order) {
		this.job_order = job_order;
	}
	public String getJob_image_name() {
		return job_image_name;
	}
	public void setJob_image_name(String job_image_name) {
		this.job_image_name = job_image_name;
	}
	public String getDetail_use_yn() {
		return detail_use_yn;
	}
	public void setDetail_use_yn(String detail_use_yn) {
		this.detail_use_yn = detail_use_yn;
	}
	public Object getJob_seq_arr() {
		return job_seq_arr;
	}
	public void setJob_seq_arr(Object object) {
		this.job_seq_arr = object;
	}
	public String getJob_kind() {
		return job_kind;
	}
	public void setJob_kind(String job_kind) {
		this.job_kind = job_kind;
	}
	public String getJob_flag() {
		return job_flag;
	}
	public void setJob_flag(String job_flag) {
		this.job_flag = job_flag;
	}
	public String getJob_seq() {
		return job_seq;
	}
	public void setJob_seq(String job_seq) {
		this.job_seq = job_seq;
	}
	public String getJob_code() {
		return job_code;
	}
	public void setJob_code(String job_code) {
		this.job_code = job_code;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getJob_rank() {
		return job_rank;
	}
	public void setJob_rank(String job_rank) {
		this.job_rank = job_rank;
	}
	public String getBasic_price() {
		return basic_price;
	}
	public void setBasic_price(String basic_price) {
		this.basic_price = basic_price;
	}
	public String getShort_price() {
		return short_price;
	}
	public void setShort_price(String short_price) {
		this.short_price = short_price;
	}
	public String getAdd_day_time() {
		return add_day_time;
	}
	public void setAdd_day_time(String add_day_time) {
		this.add_day_time = add_day_time;
	}
	public String getAdd_night_time() {
		return add_night_time;
	}
	public void setAdd_night_time(String add_night_time) {
		this.add_night_time = add_night_time;
	}
	public String getShort_price_night() {
		return short_price_night;
	}
	public void setShort_price_night(String short_price_night) {
		this.short_price_night = short_price_night;
	}
	public String getJob_background() {
		return job_background;
	}
	public void setJob_background(String job_background) {
		this.job_background = job_background;
	}
	public String getJob_color() {
		return job_color;
	}
	public void setJob_color(String job_color) {
		this.job_color = job_color;
	}
}
