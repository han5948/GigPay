package com.nemo.kr.dto;

/**
 * 실시간 출역현확 
 * 
 * @author: nemojjang
 * @date: 2019. 8. 5.
 * @desc: 
 *
 */
public class OutputDTO {

	private String company_seq;
	private long readySum;
	private long reserveSum;
	private long startSum;
	private long arriveSum;
	private long complteSum;
	private long punkSum;
	private long zzemSum;
	private long daemaSum;
	
	public String getCompany_seq() {
		return company_seq;
	}
	public void setCompany_seq(String company_seq) {
		this.company_seq = company_seq;
	}
	public long getReadySum() {
		return readySum;
	}
	public void setReadySum(long readySum) {
		this.readySum = readySum;
	}
	public long getReserveSum() {
		return reserveSum;
	}
	public void setReserveSum(long reserveSum) {
		this.reserveSum = reserveSum;
	}
	public long getStartSum() {
		return startSum;
	}
	public void setStartSum(long startSum) {
		this.startSum = startSum;
	}
	public long getArriveSum() {
		return arriveSum;
	}
	public void setArriveSum(long arriveSum) {
		this.arriveSum = arriveSum;
	}
	public long getComplteSum() {
		return complteSum;
	}
	public void setComplteSum(long complteSum) {
		this.complteSum = complteSum;
	}
	public long getPunkSum() {
		return punkSum;
	}
	public void setPunkSum(long punkSum) {
		this.punkSum = punkSum;
	}
	public long getZzemSum() {
		return zzemSum;
	}
	public void setZzemSum(long zzemSum) {
		this.zzemSum = zzemSum;
	}
	public long getDaemaSum() {
		return daemaSum;
	}
	public void setDaemaSum(long daemaSum) {
		this.daemaSum = daemaSum;
	}
	

}
