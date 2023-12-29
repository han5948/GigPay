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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AccountDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.ReservesDTO;
import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.excel.AccountExcel;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.ReservesService;
import com.nemo.kr.service.SmsService;
import com.nemo.kr.service.StaticService;
//import com.nemo.kr.util.StringUtil;

@Controller
@RequestMapping("/admin/reserves")
public class ReservesController {
	private static final Logger logger = LoggerFactory.getLogger(EmployerController.class);
	
	@Autowired CompanyService companyService;
	@Autowired ReservesService reservesService;
	@Autowired StaticService staticService;
	@Autowired SmsService smsService;
	
	// Session 설정
	private AccountDTO setSessionToDTO(HttpSession session, AccountDTO accountDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
	
		// Session 설정
		accountDTO.setAdminLevel(""+ auth_level);
		accountDTO.setReg_admin(admin_id);
		if(auth_level != 0)
			accountDTO.setCompany_seq(company_seq);
		
		return accountDTO;
	}
	
	// Session 설정
	private ReservesDTO setSessionToReservesDTO(HttpSession session, ReservesDTO reservesDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
	
		// Session 설정
		reservesDTO.setAdminLevel(""+ auth_level);
		reservesDTO.setReg_admin(admin_id);
		
		if(auth_level != 0)
			reservesDTO.setCompany_seq(company_seq);
		
		return reservesDTO;
	}
	
	@RequestMapping("")
	public String reserves(Model model) throws Exception {
		CompanyDTO companyDTO = new CompanyDTO();
		
		companyDTO.setSidx("company_seq");	
		companyDTO.setSrh_use_yn("1");
		companyDTO.setPaging(null);
		
		List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
		
		model.addAttribute("companyList", companyList);
		model.addAttribute("htmlHeader" , "적립금");
		
		return "/admin/reserves.admin";
	}
	
	@RequestMapping("/getCompanyList")
	@ResponseBody
	public Map<String, Object> getCompanyList(HttpServletRequest request, HttpServletResponse response, HttpSession session, CompanyDTO companyDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		companyDTO.setPaging(null);
		
		List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
		
		resultMap.put("companyList", companyList);
		
		return resultMap; 
	}
	
	@RequestMapping("/getAccountList")
	@ResponseBody
	public JSONObject getAccountList(HttpSession session, AccountDTO accountDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		if(auth_level != 0)
			if(company_seq.equals("13")) {
				accountDTO.setAdminLevel("0");
			}else {
				accountDTO = setSessionToDTO(session, accountDTO);
			}
		else
			accountDTO.setAdminLevel(auth_level + "");
		
		List<AccountDTO> resultList = reservesService.selectAccountList(accountDTO);
		
		AccountDTO totalAcc = reservesService.selectAccountTotalAcc(accountDTO);
		
		int price = 0;
		
		for(int i = 0; i < resultList.size(); i++) {
			if(resultList.get(i).getAccount_type().equals("0")) {
				price += Integer.parseInt(resultList.get(i).getAccount_price());
			}else {
				price -= Integer.parseInt(resultList.get(i).getAccount_price());
			}
		}
		
		jsonObj.put("rows", resultList);
		jsonObj.put("totalAcc", totalAcc.getTotalAcc());
		
		return jsonObj;
	}
	
	@RequestMapping("/getAccountDetailList")
	@ResponseBody
	public JSONObject getReservesDetailList(HttpSession session, AccountDTO accountDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		if(auth_level != 0)
			if(company_seq.equals("13")) {
				accountDTO.setAdminLevel("0");
			}else {
				accountDTO = setSessionToDTO(session, accountDTO);
			}
		else
			accountDTO.setAdminLevel(auth_level + "");
		
		List<AccountDTO> resultList = reservesService.selectAccountDetailList(accountDTO);
		
		AccountDTO resultTotal = new AccountDTO();
		
		resultTotal.setAccount_seq("0");
		resultTotal.setAccount_price("");
		
		jsonObj.put("rows", resultList);
		
		return jsonObj;
	}
	
	@RequestMapping("/getReservesList")
	@ResponseBody
	public JSONObject getReservesList(HttpSession session, ReservesDTO reservesDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		if(auth_level != 0)
			if(company_seq.equals("13")) {
				reservesDTO.setAdminLevel("0");
			}else {
				reservesDTO = setSessionToReservesDTO(session, reservesDTO);
			}
		else
			reservesDTO.setAdminLevel(auth_level + "");
		
//		if(auth_level != 0)
//			reservesDTO = setSessionToReservesDTO(session, reservesDTO);
//		else
//			reservesDTO.setAdminLevel(auth_level + "");
		
		List<ReservesDTO> result = reservesService.selectReservesList(reservesDTO);
		
		if(result.size() == 0) {
			result = null;
		}
		
		jsonObj.put("rows", result);
		
		return jsonObj;
	}
	
	@RequestMapping("/insertReserves")
	@ResponseBody
	public Map<String, Object> insertReserves(HttpSession session, ReservesDTO reservesDTO, SmsDTO smsDTO) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			reservesDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
			reservesDTO.setReserves_req_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			reservesDTO.setReg_admin(reservesDTO.getReserves_req_admin());
			
			if(reservesDTO.getReserves_type().equals("0") || reservesDTO.getReserves_type().equals("1")) {
				reservesDTO.setDest_company_seq("0");
			}
			
			reservesService.insertReserves(reservesDTO);
			
			//CompanyDTO companyDTO = new CompanyDTO();
			
			//companyDTO = companyService.getCompanyInfo(companyDTO);
			
			String tr_msg = "";
			String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
			String req_phone = smsDTO.getTr_phone();
			
			if(reservesDTO.getReserves_type().equals("0")) {
				tr_msg = "[적립금 충전접수]" + company_name + "/" + reservesDTO.getReserves_price() + "원 / " + reservesDTO.getReserves_req_memo() + " / " + req_phone + " / " + reservesDTO.getReserves_req_admin();
				smsDTO.setTr_etc1("12");
			}else {
				tr_msg = "[적립금 출금접수]" + company_name + "/" + reservesDTO.getReserves_price() + "원 / " + reservesDTO.getReserves_req_memo() + " / " + req_phone + " / " + reservesDTO.getReserves_req_admin();
				smsDTO.setTr_etc1("13");
			}
			
			smsDTO.setTr_msg(tr_msg);
			
			//smsService.insertInfo(smsDTO);
		} catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/updateReserves")
	@ResponseBody
	public Map<String, Object> updateAccount(HttpSession session, ReservesDTO reservesDTO, SmsDTO smsDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			reservesDTO.setReserves_res_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
			
			reservesService.updateReserves(reservesDTO);
			
			String tr_msg = "";
			
			AccountDTO accountDTO = new AccountDTO();
			
			if(reservesDTO.getCellname().equals("reserves_process_status")) {
				if(reservesDTO.getReserves_type().equals("0")) {
					if(reservesDTO.getReserves_process_status().equals("2")) {
						tr_msg = "[일가자알림] 적립금 충전이 승인되었습니다. 충전 : " + reservesDTO.getReserves_price() + "원";
						
						accountDTO.setAccount_type("0");
						accountDTO.setAccount_flag("0");
						accountDTO.setCompany_seq("1");
						accountDTO.setDest_company_seq(reservesDTO.getCompany_seq());
						accountDTO.setAccount_price(reservesDTO.getReserves_price().replaceAll(",", ""));
						accountDTO.setReg_admin(reservesDTO.getReserves_res_admin());
						
						reservesService.insertAccount(accountDTO);
					}else {
						tr_msg = "[일가자알림] 적립금 충전이 미승인되었습니다.";
					}
					
					smsDTO.setTr_etc1("12");
					
				}else {
					if(reservesDTO.getReserves_process_status().equals("2")) {
						tr_msg = "[일가자알림] 적립금 출금이 승인되었습니다. 출금 : " + reservesDTO.getReserves_price() + "원";
						
						accountDTO.setAccount_type("1");
						accountDTO.setAccount_flag("1");
						accountDTO.setCompany_seq("1");
						accountDTO.setDest_company_seq(reservesDTO.getCompany_seq());
						accountDTO.setAccount_price(reservesDTO.getReserves_price().replaceAll(",", ""));
						accountDTO.setReg_admin(reservesDTO.getReserves_res_admin());
						
						reservesService.insertAccount(accountDTO);
					}else {
						tr_msg = "[일가자알림] 적립금 출금이 미승인되었습니다.";
					}
					
					smsDTO.setTr_etc1("13");
				}
				
				smsDTO.setTr_msg(tr_msg);
				
				smsService.insertInfo(smsDTO);
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}	
	
	/**
	  * @Method Name : getAccountExcel
	  * @작성일 : 2020. 12. 29.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 : 입출금 내역 엑셀 다운
	  */
	@RequestMapping("/getAccountExcel")
	public View getAccountExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model, AccountDTO accountDTO) throws Exception {
		setSessionToDTO(session, accountDTO);
		
		accountDTO.setStartDate(accountDTO.getStart_date());
		accountDTO.setEndDate(accountDTO.getEnd_date());
		
		int fileDate = Integer.parseInt(accountDTO.getEnd_date().substring(0, 7).replaceAll("-", ""));
		
		model.addAttribute("endDate", accountDTO.getEnd_date());
		model.addAttribute("fileName", "일가자_계산서_" + fileDate + ".xlsx");
		model.addAttribute("resultList", reservesService.selectAccountExcelList(accountDTO));
		
		return new AccountExcel();
	}
}
