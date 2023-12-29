package com.nemo.kr.dto;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.ibatis.io.Resources;

import com.nemo.kr.util.DateUtil;
import com.nemo.kr.util.FileUtil;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;


/**
 * @author  : nemojjang
 * @date    : 2019. 9. 6.
 * @desc    : 공통 DTO 
 *
 */
public class DefaultDTO implements Serializable {

	@Resource(name="commonProperties")	  private Properties commonProperties;
	
	private static final long serialVersionUID = -5786641609538363632L;

	private String secretKey;
	
	private String searchType = null;
	private String searchKey = null;
	private String searchKey2 = null;
	
	private String searchValue = null;

	private String pagingYn = null;
	private PagingDTO paging = new PagingDTO();

	private String day_type = null;
	
	private String startDate = null;
	private String endDate = null;
	private String startDate2 = null;
	private String endDate2 = null;

	private String useYn = null;

	private String adminLevel = null;
	private String adminSeq = null;
	private String adminType;

	private String regSeq = null;
	private String regDate = null;
	
	private String owner_id;
	
	private String code;
	private String text;
	private String label;                                     // text
	
	private String a_seq;									// 접속로그 순번
	
	// 일가자 공통추가
	private String use_yn;                                    // 사용유무 - 0:정지 1:사용
	private String del_yn;
	private String reg_date;
	private String reg_admin;
	private String mod_date;
	private String mod_admin;

	private String login_type;
	private String srh_option;
	
	private String orderBy;	//order by 절에서 사용.
	
	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getSearchKey2() {
		return searchKey2;
	}

	public void setSearchKey2(String searchKey2) {
		this.searchKey2 = searchKey2;
	}

	public String getDay_type() {
		return day_type;
	}

	public void setDay_type(String day_type) {
		this.day_type = day_type;
	}

	/**
	 * @return the searchKey
	 */
	public String getSearchKey() {
		return searchKey;
	}

	/**
	 * @param searchKey the searchKey to set
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * @param searchValue the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * @return the pagingYn
	 */
	public String getPagingYn() {
		return pagingYn;
	}

	/**
	 * @param pagingYn the pagingYn to set
	 */
	public void setPagingYn(String pagingYn) {
		this.pagingYn = pagingYn;
	}

	/**
	 * @return the paging
	 */
	public PagingDTO getPaging() {
		return paging;
	}

	/**
	 * @param paging the paging to set
	 */
	public void setPaging(PagingDTO paging) {
		this.paging = paging;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate2() {
		return startDate2;
	}

	public void setStartDate2(String startDate2) {
		this.startDate2 = startDate2;
	}

	public String getEndDate2() {
		return endDate2;
	}

	public void setEndDate2(String endDate2) {
		this.endDate2 = endDate2;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getAdminLevel() {
		return adminLevel;
	}

	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}

	public String getAdminSeq() {
		return adminSeq;
	}

	public void setAdminSeq(String adminSeq) {
		this.adminSeq = adminSeq;
	}

	public String getAdminType() {
		return adminType;
	}

	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}

	public String getRegSeq() {
		return regSeq;
	}

	public void setRegSeq(String regSeq) {
		this.regSeq = regSeq;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getA_seq() {
		return a_seq;
	}

	public void setA_seq(String a_seq) {
		this.a_seq = a_seq;
	}



	public String getLogin_type() {
		return login_type;
	}

	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public String getReg_date() {
		String rtn = "";

		try {
			rtn = DateUtil.dateToFormat(reg_date, "yyyy-MM-dd HH:mm");
		} catch (Exception e) {
		}

		return rtn;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getReg_admin() {
		return reg_admin;
	}
	public void setReg_admin(String reg_admin) {
		this.reg_admin = reg_admin;
	}
	public String getMod_date() {
		String rtn = "";

		try {
			rtn = DateUtil.dateToFormat(mod_date, "yyyy-MM-dd HH:mm");
		} catch (Exception e) {
		}

		return rtn;
	}
	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}
	public String getMod_admin() {
		return mod_admin;
	}
	public void setMod_admin(String mod_admin) {
		this.mod_admin = mod_admin;
	}

	public String getDel_yn() {
		return del_yn;
	}

	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}

	public String getSrh_option() {
		return srh_option;
	}

	public void setSrh_option(String srh_option) {
		this.srh_option = srh_option;
	}
	
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String keyPath) {
		System.out.println("set===========");
		this.secretKey = FileUtil.readLine(keyPath);
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
