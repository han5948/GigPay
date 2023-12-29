package com.nemo.kr.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AccountListDTO;
import com.nemo.kr.dto.AccountingDTO;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.StaticDTO;
import com.nemo.kr.service.AdminService;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.StaticService;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;



/**
 * @author  : nemojjang
 * @date    : 2018. 8. 29.
 * @desc    : 
 *
 */
@Controller
@RequestMapping("/admin")
public class StaticController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);

	@Autowired StaticService staticService;
	@Autowired CompanyService companyService;
	@Autowired AdminService adminService;

	// Session 설정
	private StaticDTO setSessionToDTO(HttpSession session, StaticDTO staticDTO) throws Exception {
		// Session 정보
		int auth_level      		= StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name 	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     		= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();

		String get_company_seq = StringUtil.isNullToString(staticDTO.getCompany_seq());

		// Session 설정
		staticDTO.setAdminLevel(""+ auth_level);

		//최고관리자가 아니면....
		if(auth_level > 0){
			staticDTO.setCompany_seq(company_seq);
		}
		//    companyDTO.setSrh_company_seq(company_seq);
		staticDTO.setReg_admin(admin_id);

		return staticDTO;
	}

	@RequestMapping(value="/staticList")
	public String companyList(HttpSession session, Model model) {
		int authLevel = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String companySeq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setSidx("company_seq");
		companyDTO.setPaging(null);
		companyDTO.setSrh_use_yn("1");
		int rowCount = companyService.getCompanyTotalCnt(companyDTO);

		if(authLevel ==0 || authLevel == 1 && companySeq.equals("13")) {
			List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
			model.addAttribute("companyList" , companyList);
		}
		
		if(authLevel == 1 && !companySeq.equals("13")) {
			String where = "";
			
			where += "AND company_seq = " + ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();;
			companyDTO.setWhere(where);
			
			List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
			
			model.addAttribute("companyList", companyList);
		}
		
		AdminDTO adminDTO = new AdminDTO();
		adminDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		adminDTO.setUse_yn("1");
		
		List<AdminDTO> counselorList = adminService.selectCounselorList(adminDTO);
		
		model.addAttribute("counselorList", counselorList);
		model.addAttribute("htmlHeader" , "기간통계");
		model.addAttribute("authLevel" , authLevel);	// 관리자 등급
		
		return "/admin/staticList.admin";
	}

	@RequestMapping(value="/getStaticList")
	@ResponseBody
	public JSONObject getStaticList(HttpServletRequest request, HttpServletResponse response, HttpSession session, StaticDTO staticDTO) throws Exception {
		// Session 설정
		int authLevel      		= StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		if(authLevel ==0) { //최고관리자의 경우.
			if( StringUtil.isNull(staticDTO.getCompany_seq())) {
				staticDTO.setCompany_seq("13");	//본사거..
			}
		}else {
			String company_seq  	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
			staticDTO.setCompany_seq(company_seq);
		}
		
		String adminId = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		
		staticDTO.setAdminLevel(authLevel + "");
		
		if(authLevel == 3) {
			staticDTO.setAdmin_id(adminId);
		}

		int rowCount = 0;
		List<StaticDTO> result = null;

		result = staticService.getStaticList(staticDTO);
		
		//본점일 경우 수수료 + 지점 쉐어료
//		result.stream()
//			.filter(s -> s.getCompany_seq().equals("13"))
//			//.forEach(s -> s.setFeeSum(s.getFeeSum() * 2));
//			.forEach(s -> s.setFeeSum(s.getFeeSum() + s.getShareSum()));
		
//		for(int i = 0; i < result.size(); i++) {
//			if( result.get(i).getCompany_seq().equals("13") ) {
//				result.get(i).setFeeSum( result.get(i).getFeeSum() * 2 );
//			}
//		}
			
			
			
		/*
		if(staticDTO.getStaticMode().equals("SALES")){// 매출 통계	DOT에 기본값으로 정해 있음.
			
			if("D".equals( staticDTO.getSearchMode()) ){	//일별
				//rowCount = staticService.selectSalesDayListCnt(staticDTO);
				//staticDTO.getPaging().setRowCount(rowCount);
				result = staticService.selectSalesDayList(staticDTO);
		
			}else if("M".equals( staticDTO.getSearchMode()) ){ //월별
				//rowCount = staticService.selectSalesMonthListCnt(staticDTO);
				//staticDTO.getPaging().setRowCount(rowCount);
				result = staticService.selectSalesMonthList(staticDTO);
			}else {//년별
				//rowCount = staticService.selectSalesYearListCnt(staticDTO);
				//staticDTO.getPaging().setRowCount(rowCount);
				result = staticService.selectSalesYearList(staticDTO);
			}
		
		
		}else if(staticDTO.getStaticMode().equals("WORKER")){// 근로자 통계
		
		}else if(staticDTO.getStaticMode().equals("WORK")){// 구인자 통계
		
		}
		
		*/		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("rows",    result);                                             // 현재페이지의 목록데이터
		jsonObj.put("page",    staticDTO.getPaging().getPageNo());                 // 현재페이지
		jsonObj.put("total",   staticDTO.getPaging().getTotalPageCnt());           // 총페이지수
		jsonObj.put("records", staticDTO.getPaging().getRowCount());               // 총글목록수

		return jsonObj;
	}
	
	@RequestMapping(value="/companyStatic")
	public String companyStatic(HttpSession session, Model model) {
		int authLevel = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String companySeq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setSidx("company_seq");
		companyDTO.setPaging(null);
		companyDTO.setSrh_use_yn("1");
		int rowCount = companyService.getCompanyTotalCnt(companyDTO);
		

		if(authLevel ==0 || (authLevel == 1 && companySeq.equals("13"))) {
			List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
			
			model.addAttribute("companyList" , companyList);
		}
		
		model.addAttribute("htmlHeader" , "정산");
		model.addAttribute("authLevel" , authLevel);	// 관리자 등급
		
		
		return "/admin/companyStatic.admin";
	}
	
	@RequestMapping(value="/getCompanyStatic")
	@ResponseBody
	private Map<String, Object> getCompanyStatic(AccountingDTO accountingDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			List<AccountListDTO> resultList = staticService.getCompanyStatic(accountingDTO);
			resultMap.put("resultList", resultList);
			
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR +  e);
		
			e.printStackTrace();
		}
		return resultMap;
	}
}
