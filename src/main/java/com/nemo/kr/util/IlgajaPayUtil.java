package com.nemo.kr.util;


public class IlgajaPayUtil {
	/*
	ilbo_unit_price                  int(11)       (NULL)           YES             0                                                   select,insert,update,references  단가                                                                                                                               
	ilbo_gongsu                      varchar(100)  utf8_general_ci  YES             (NULL)                                              select,insert,update,references  공수                                                                                                                               
	ilbo_fee                         int(11)       (NULL)           YES     MUL     0                                                   select,insert,update,references  수수료                                                                                                                            
	share_fee                        int(11)       (NULL)           YES             0                                                   select,insert,update,references  쉐어수수료(구직자를 보낸쪽의 수수료)                                                                                 
	ilbo_extra_pay                   int(11)       (NULL)           YES             0                                                   select,insert,update,references  추가지불                                                                                                                         
	ilbo_deduction                   int(11)       (NULL)           YES             0                                                   select,insert,update,references  공제금                                                                                                                            
	counselor_fee                    int(11)       (NULL)           YES             0                                                   select,insert,update,references  상담사수수료                         
	*/
	
	private int ilbo_unit_price = 0;
	private int ilbo_fee = 0;
	private int share_fee = 0;
	private int counselor_fee =0;
	private int ilbo_pay = 0;
	private String fee_info = "";
	private String pay_info = "";
	//private String[] feeKind = {"400016", "400158", "400153", "400164", "400168", "400024", "400792", "400027"};
	
	public IlgajaPayUtil(String ilboUnitPrice, String workCompanySeq, String workerCompanySeq, String counselorRate, String kindCode, double workFeeRate, double workerFeeRate) {
		this.ilbo_unit_price = Integer.parseInt(ilboUnitPrice);
		
		this.ilbo_pay = (int)( this.ilbo_unit_price / ((100 + workFeeRate) / 100 ) );
		//백원 이하단위 올림처리
		this.ilbo_pay = (int)Math.ceil(this.ilbo_pay / 1000.0) * 1000;
		
		//수수료 자동계산
		if( workerFeeRate > 0 ) {
			int workFee = this.ilbo_unit_price - this.ilbo_pay;
			int workerFee = (int)(this.ilbo_pay * (workerFeeRate / 100));
			workerFee = (int)(Math.floor(workerFee / 100) * 100);
			
			this.fee_info = "(구인자 " + StringUtil.strComma(workFee+"") + "원+구직자 " + StringUtil.strComma(workerFee+"") + "원)";
			this.pay_info = "(구직자 소개요금 최대 1% 포함)";
			this.ilbo_fee = workFee + workerFee;
		}else {
			this.ilbo_fee = (int) (this.ilbo_unit_price - this.ilbo_pay);
			this.fee_info = "(구인자 " + StringUtil.strComma(this.ilbo_fee+"") + "원)";
			this.pay_info = "";
		}
		
		//다른지점과 공유한 일자리면 수수료를 나눈다.
		if( !workCompanySeq.equals(workerCompanySeq) ) {
			this.ilbo_fee = this.ilbo_fee / 2;
			this.share_fee = ilbo_fee;
		}
//		if(workCompanySeq.equals(workerCompanySeq)) {
//			
//			if( workerFeeRate > 0 ) {
//				int workFee = this.ilbo_unit_price - this.ilbo_pay;
//				int workerFee = (int)(this.ilbo_pay * (workerFeeRate / 100));
//				workerFee = (int)(Math.floor(workerFee / 100) * 100);
//				
////				int workFee = (int)(this.ilbo_pay * (workFeeRate / 100));
////				workFee = (int)(Math.floor(workFee / 1000) * 1000);
////				int workerFee = (int)(this.ilbo_pay * (workerFeeRate / 100));
////				workerFee = (int)(Math.floor(workerFee / 100) * 100);
//				
//				this.ilbo_fee = workFee + workerFee;
//			}else {
//
//				this.ilbo_fee = (int) (this.ilbo_unit_price - this.ilbo_pay);
//			}
//			
//		}else {
////			if(Arrays.asList(feeKind).contains(kindCode)) {
////				this.ilbo_fee = (int) (this.ilbo_unit_price * 0.15 / 2);
////			}else {
////				this.ilbo_fee = (int) (this.ilbo_unit_price * 0.1 / 2);
////			}
//			
//			if( workerFeeRate > 0 ) {
//				int workFee = this.ilbo_unit_price - this.ilbo_pay;
//				int workerFee = (int)(this.ilbo_pay * (workerFeeRate / 100));
//				workerFee = (int)(Math.floor(workerFee / 100) * 100);
//				
////				int workFee = (int)(this.ilbo_pay * (workFeeRate / 100));
////				workFee = (int)(Math.floor(workFee / 1000) * 1000);
////				int workerFee = (int)(this.ilbo_pay * (workerFeeRate / 100));
////				workerFee = (int)(Math.floor(workerFee / 100) * 100);
//				
//				this.ilbo_fee = (workFee + workerFee) / 2;
//			}else {
//				this.ilbo_fee = (int) (this.ilbo_unit_price - this.ilbo_pay) / 2;
//			}
//			this.share_fee = ilbo_fee;
//		}
		
		if("0".equals(counselorRate)) {
			this.counselor_fee = 0;
		}else {
			counselor_fee = (int) ((this.ilbo_fee + this.share_fee) * Integer.parseInt(counselorRate) / 100);
		}
		
		//this.ilbo_pay = this.ilbo_unit_price - this.ilbo_fee - this.share_fee;
	}
	
	public String getIlboUnitPrice() {
		return Integer.toString(this.ilbo_unit_price);
	}
	
	public String getIlboFee() {
		return Integer.toString(this.ilbo_fee);
	}
	
	public String getShareFee() {
		return Integer.toString(this.share_fee);
	}
	
	public String getCounselorFee() {
		return Integer.toString(this.counselor_fee);
	}
	
	public String getIlboPay() {
		return Integer.toString(this.ilbo_pay);
	}
	
	public String getFee_info() {
		return fee_info;
	}

	public void setFee_info(String fee_info) {
		this.fee_info = fee_info;
	}

	public String getPay_info() {
		return pay_info;
	}

	public void setPay_info(String pay_info) {
		this.pay_info = pay_info;
	}
	
	public static void main(String[] args) {
		//IlgajaPayUtil payUtil = new IlgajaPayUtil("10000", "2", "3", "20");
	}

}
