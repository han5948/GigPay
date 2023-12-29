package com.nemo.kr.controller.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.dto.SmsTemplateDTO;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.IlboService;
import com.nemo.kr.service.SmsService;
import com.nemo.kr.service.SmsTemplateService;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/sms")
public class SmsController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
	
	@Autowired SmsService smsService;
	@Autowired SmsTemplateService smsTemplateService;
	@Autowired IlboService ilboService;
	@Autowired CompanyService companyService;
	
	// Session 설정
	private SmsDTO setSessionToDTO(HttpSession session, SmsDTO smsDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		String company_seq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		CompanyDTO companyDTO = new CompanyDTO();
		
		companyDTO.setCompany_seq(company_seq);
		
		companyDTO = companyService.getCompanyInfo(companyDTO);
		
		if(!company_seq.equals("13"))
			smsDTO.setTr_callback(companyDTO.getCompany_tel());
		// Session 설정
		smsDTO.setAdminLevel(""+ auth_level);
		smsDTO.setReg_admin(admin_id);
		smsDTO.setTr_etc4(company_seq);
		
		return smsDTO;
	}
	
	/**
	 * 
	  * @throws IOException 
	 * @Method Name : smsWrite
	  * @작성일 : 2020. 6. 29.
	  * @작성자 : Park YunSoo
	  * @필수 param :  
	  * @선택 param : data
	  * @Method 설명 : SMS 작성 팝업 
	 */
	@RequestMapping("/smsWrite")
	public String smsWrite(HttpServletRequest request, HttpServletResponse response, Model model, SmsDTO smsDTO, HttpSession session) throws IOException {
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		
		BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String buffer;
        while ((buffer = input.readLine()) != null) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(buffer);
        }
		
        String data = builder.toString();
        
		paramList = JSONArray.fromObject(data);
		
		SmsTemplateDTO smsTemplateDTO = new SmsTemplateDTO();
		
		smsTemplateDTO.setUse_yn("1");
		smsTemplateDTO.setPaging(null);
		smsTemplateDTO.setCompany_seq(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq());
		smsTemplateDTO.setAdminLevel("" + StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1));
		
		CompanyDTO companyDTO = new CompanyDTO();
		
		companyDTO.setCompany_seq(smsTemplateDTO.getCompany_seq());
		
		companyDTO = companyService.getCompanyInfo(companyDTO);
		
		model.addAttribute("smsTemplateList", smsTemplateService.selectList(smsTemplateDTO));
		model.addAttribute("smsDTO", smsDTO);
		model.addAttribute("sendList", paramList);
		model.addAttribute("leftMenu", "SMS");
		model.addAttribute("companyDTO", companyDTO);
		
		return "/admin/sms/smsWrite.popup_admin";
	}

	/**
	 * 
	  * @Method Name : smsTemplateInfo
	  * @작성일 : 2020. 6. 29.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 : SMS template List
	 */
	@RequestMapping("/smsTemplateInfo")
	@ResponseBody
	public Map<String, Object> smsTemplateInfo(HttpSession session, SmsTemplateDTO smsTemplateDTO) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("smsTemplateInfo", smsTemplateService.selectInfo(smsTemplateDTO));
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	  * @throws Exception 
	 * @Method Name : insertSms
	  * @작성일 : 2020. 6. 29.
	  * @작성자 : Park YunSoo
	  * @필수 param : data
	  * @선택 param : 
	  * @Method 설명 : SMS 작성 로직
	 */
	@RequestMapping("/insertSms")
	@ResponseBody
	public Map<String, Object> insertSms(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String buffer;
        while ((buffer = input.readLine()) != null) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(buffer);
        }
		
        String data = builder.toString();
        
		paramList = JSONArray.fromObject(data);
		Gson gson = new Gson();
		SmsDTO smsDTO = new SmsDTO();
		IlboDTO ilboDTO = new IlboDTO();
		int failCnt = 0;
		
		smsDTO.setReg_admin(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id());
		
		for(int i = 0; i < paramList.size(); i++) {
			String tr_msg = paramList.get(i).get("tr_msg").toString();
			String breakfast = "";
			String pay_type = "";
			
			if(paramList.get(i).get("menuFlag").equals("I")) {
				ilboDTO = ilboService.selectSmsInfo(paramList.get(i).get("ilbo_seq").toString());
				
				if(ilboDTO.getIlbo_work_breakfast_yn().equals("1"))
					breakfast = "조식 있음";
				else 
					breakfast = "조식 없음";
					
				if (ilboDTO.getIlbo_income_code().equals("300006")) 
					pay_type = "현장현금수령, ";
                else if (ilboDTO.getIlbo_income_code().equals("300007"))        
                	pay_type = "본인통장수령, ";
                else if (ilboDTO.getIlbo_income_code().equals("300008"))        
                	pay_type = "사무실통장입금 후 지급, ";
                else if (ilboDTO.getIlbo_income_code().equals("300009") || ilboDTO.getIlbo_income_code().equals("300010"))  
                	pay_type = "작업확인서 증빙 사무실 지급, ";
                else          
                	pay_type = ", ";
				
				tr_msg = tr_msg.replaceAll("\\{worker_name\\}", StringUtil.isNullToString(ilboDTO.getIlbo_worker_name()));
				tr_msg = tr_msg.replaceAll("\\{employer_name\\}", StringUtil.isNullToString(ilboDTO.getEmployer_name()));
				tr_msg = tr_msg.replaceAll("\\{work_name\\}", StringUtil.isNullToString(ilboDTO.getIlbo_work_name()));
				tr_msg = tr_msg.replaceAll("\\{ilbo_date\\}", StringUtil.isNullToString(ilboDTO.getIlbo_date()));
				tr_msg = tr_msg.replaceAll("\\{work_time\\}", StringUtil.isNullToString(ilboDTO.getIlbo_work_arrival()));
				tr_msg = tr_msg.replaceAll("\\{breakfast\\}", breakfast);
				tr_msg = tr_msg.replaceAll("\\{job_kind\\}", StringUtil.isNullToString(ilboDTO.getIlbo_kind_name()));
				tr_msg = tr_msg.replaceAll("\\{job_comment\\}", StringUtil.isNullToString(ilboDTO.getIlbo_job_comment()));
				tr_msg = tr_msg.replaceAll("\\{price\\}", StringUtil.isNullToString(ilboDTO.getIlbo_unit_price()));
				tr_msg = tr_msg.replaceAll("\\{pay_type\\}", pay_type);
				tr_msg = tr_msg.replaceAll("\\{work_manager_name\\}", StringUtil.isNullToString(ilboDTO.getIlbo_work_manager_name()));
				tr_msg = tr_msg.replaceAll("\\{work_manager_phone\\}", StringUtil.isNullToString(ilboDTO.getIlbo_work_manager_phone()));
			}else if(paramList.get(i).get("menuFlag").equals("M")) {
				tr_msg = tr_msg.replaceAll("\\{manager_name\\}", paramList.get(i).get("tr_name").toString());
			}else if(paramList.get(i).get("menuFlag").equals("W")) {
				tr_msg = tr_msg.replaceAll("\\{worker_name\\}", paramList.get(i).get("tr_name").toString());
			}
			
			tr_msg = tr_msg.replaceAll("\\{company_name\\}", paramList.get(i).get("company_name").toString());
			
			smsDTO = gson.fromJson(paramList.get(i).toString(), SmsDTO.class);
			smsDTO = setSessionToDTO(session, smsDTO);
			
			smsDTO.setTr_phone(paramList.get(i).get("tr_phone").toString());
			smsDTO.setTr_msg(tr_msg);
			
			int byteLength = smsDTO.getTr_msg().getBytes().length;
			
			if(byteLength > 90)
				smsService.insertMmsInfo(smsDTO);
			else 
				smsService.insertInfo(smsDTO);
			
			if(smsDTO.getResultCnt() != 1) {
				failCnt++;
			}
		}
		
		resultMap.put("cnt", paramList.size());
		resultMap.put("failCnt", failCnt);
		resultMap.put("code", Const.CODE_SUCCESS);
		
		return resultMap;
	}
	
	/**
	 * 
	  * @Method Name : selectList
	  * @작성일 : 2020. 7. 3.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 : SMS List
	 */
	@RequestMapping("/smsList")
	public String selectList(HttpSession session, Model model, SmsDTO smsDTO) throws Exception {
		smsDTO = setSessionToDTO(session, smsDTO);
		
		smsDTO.getPaging().setPageSize(15);
		
		CompanyDTO companyDTO = new CompanyDTO();
		String where = "";
		
		where += "AND use_yn = '1'";
		
		companyDTO.setWhere(where);
		companyDTO.setPaging(null);
		companyDTO.setSidx("company_seq");
		companyDTO.setSord("ASC");
		
		List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
		
		if(smsDTO.getStartDate() != null) {
			smsDTO.setTableName(smsDTO.getStartDate().replace("-", ""));
		}else {
			Calendar cal = Calendar.getInstance();
		
			String month = cal.get(cal.MONTH) + 1 + "";
	
			if(month.length() == 1)
				month = "0" + month;
			
			String tableName = cal.get(cal.YEAR) + month;
			
			smsDTO.setTableName(tableName);
			smsDTO.setStartDate(cal.get(cal.YEAR) + "-" + month);
		}
		
		int cnt = smsService.selectSmsListCnt(smsDTO);
		
		if(cnt > 0) {
			List<SmsDTO> smsList = smsService.selectSmsList(smsDTO);
			
			model.addAttribute("smsList", smsList);
		}
		
		model.addAttribute("htmlHeader", "SMS 이력 관리");
		model.addAttribute("companyList", companyList);
		model.addAttribute("totalCnt", cnt);
		model.addAttribute("smsDTO", smsDTO);
		model.addAttribute("leftMenu", "SMS");
		
		return "/admin/sms/smsList.leftAdmin";
	}
	
	/**
	 * 
	  * @Method Name : getSmsList
	  * @작성일 : 2020. 7. 3.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 : SMS List ajax
	 */
	@RequestMapping("/getSmsList")
	@ResponseBody
	public Map<String, Object> getSmsList(HttpSession session, SmsDTO smsDTO) throws Exception {
		smsDTO = setSessionToDTO(session, smsDTO);
		smsDTO.getPaging().setPageSize(15);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			if(!smsDTO.getStartDate().equals("") || smsDTO.getStartDate() != null) {
				smsDTO.setTableName(smsDTO.getStartDate().replace("-", ""));
			}else {
				Calendar cal = Calendar.getInstance();
			
				String month = cal.get(cal.MONTH) + 1 + "";
				String tableName = "";
				
				if(month.length() == 1)
					month = "0" + month;
				
				if(smsDTO.getStartDate() != null && !smsDTO.getStartDate().equals("")) {
					tableName +=  smsDTO.getStartDate().replaceAll(" ", "");
					
					tableName = tableName.replaceAll("-", "");
				}else {
					tableName = cal.get(cal.YEAR) + month;
				}
				
				smsDTO.setTableName(tableName);
			}
			
			int cnt = smsService.selectSmsListCnt(smsDTO);
			
			List<SmsDTO> smsList = smsService.selectSmsList(smsDTO);
		
			resultMap.put("smsList", smsList);
			resultMap.put("totalCnt", cnt);
			resultMap.put("smsDTO", smsDTO);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	  * @Method Name : smsSend
	  * @작성일 : 2020. 7. 21.
	  * @작성자 : Park YunSoo
	  * @필수 param : 
	  * @선택 param :
	  * @Method 설명 : sms send 화면
	 */
	@RequestMapping("/smsSend")
	public String smsSend(Model model) {
		model.addAttribute("htmlHeader", "SMS 보내기");
		model.addAttribute("leftMenu", "SMS");
		
		return "/admin/sms/smsSend.leftAdmin";
	}
}
