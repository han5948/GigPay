package com.nemo.kr.controller.admin;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AccountDTO;
import com.nemo.kr.dto.AccountListDTO;
import com.nemo.kr.dto.AccountingDTO;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.StaticDTO;
import com.nemo.kr.excel.AccountConsultingExpensesExcel;
import com.nemo.kr.excel.AccountingAdListExcel;
import com.nemo.kr.excel.AccountingExpensesExcel;
import com.nemo.kr.excel.CompanyAccountingExpensesExcel;
import com.nemo.kr.excel.CompanyAccountingExpensesExcel2;
import com.nemo.kr.excel.JnpTaxExcel;
import com.nemo.kr.service.AdminService;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.StaticService;
//import com.nemo.kr.util.StringUtil;




/**
  * @FileName : AccountingController.java
  * @Project : ilgaja
  * @Date : 2020. 3. 3. 
  * @작성자 : Jangjaeho
  * @변경이력 :
  * @프로그램 설명 : 정산 컨트롤러 ( 본사에서만 들어 온다. )
  */
@Controller
@RequestMapping("/admin")
public class AccountingController {
	private static final Logger logger = LoggerFactory.getLogger(AccountingController.class);

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

	/**
	  * @Method Name : accountingList
	  * @작성일 : 2020. 4. 2.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 관리자 정산관리
	
	  */
	@RequestMapping(value="/accountingList")
	public String accountingList(HttpSession session, Model model) {
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
		
		
		return "/admin/accountingList.admin";
	}
	
	@RequestMapping(value="/getAccountingList")
	@ResponseBody
	private Map<String, Object> getAccountingData(AccountingDTO accountingDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			List<AccountListDTO> resultList = staticService.selectAccountingList(accountingDTO);
			resultMap.put("resultList", resultList);
			
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR +  e);
		
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	  * @Method Name : accountingExcel
	  * @작성일 : 2020. 3. 5.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 정산관련 엑셀다운로드
	
	  */
	@RequestMapping(value="/accountingExcel")
	public View accountingExcel(Model model, HttpServletRequest request,HttpServletResponse response , HttpSession session, AccountingDTO accountingDTO) throws Exception{		
		String fileName = "";
		String companyName ="";
		
		List<AccountingDTO> resultList = new ArrayList<AccountingDTO>();
		
		if("0".equals(accountingDTO.getCompany_seq())) {	
			fileName = accountingDTO.getStartDate().substring(0, 7) + "_전체_accountingList.xlsx";
			companyName = "전체";
			resultList = staticService.selectAccountAll(accountingDTO);
		}else {
			CompanyDTO paramDTO = new CompanyDTO();
			paramDTO.setCompany_seq(accountingDTO.getCompany_seq());
			
			CompanyDTO companyDTO = companyService.getCompanyInfo(paramDTO);
			companyName = companyDTO.getCompany_name();
			fileName = accountingDTO.getStartDate().substring(0, 7) + "_"+ companyDTO.getCompany_name()+"_accountingList.xlsx";
			
			//본사일때는 worker_company_seq 값이 없어야 된다.
			if(Const.JOBNPARTNER_SEQ.contentEquals(accountingDTO.getCompany_seq())) {
				accountingDTO.setCompany_seq(null);
			}
		
			resultList = staticService.selectAccountDayList(accountingDTO);
		}
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("companyName", companyName);
		model.addAttribute("accountingDTO", accountingDTO);
		model.addAttribute("resultList", resultList);
		
		return new AccountingExpensesExcel();
	}
	
	
	/**
	  * @Method Name : companyAccountingList
	  * @작성일 : 2020. 12. 16.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 지점매출
	
	  */
	@RequestMapping(value="/companyAccountingList")
	public String companyAccountingList(HttpSession session, Model model) {
		int authLevel = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setSidx("company_seq");
		companyDTO.setPaging(null);
		companyDTO.setSrh_use_yn("1");
		
		List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
			
		model.addAttribute("companyList" , companyList);
		model.addAttribute("htmlHeader" , "정산");
		model.addAttribute("authLevel" , authLevel);	// 관리자 등급
		
		return "/admin/companyAccountingList.admin";
	}
	
	@RequestMapping("/getCompanyAccountingList")
	@ResponseBody
	private Map<String, Object> getCompanyAccountingList(HttpSession session, AccountingDTO accountingDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String company_seq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			CompanyDTO companyDTO = new CompanyDTO();
			companyDTO.setSidx("company_seq");
			companyDTO.setPaging(null);
			companyDTO.setSrh_use_yn("1");
			
			List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
			List<AccountingDTO> resultData = new ArrayList<AccountingDTO>();
			//HashMap<String, Object> dataMap = new HashMap<String, Object>();
			
			if(!company_seq.equals("1")) {
				if(accountingDTO.getCompany_seq().equals("0")) {
					accountingDTO.setCompany_seq(company_seq);
				
					resultData = staticService.selectCompanyAccountDayList2(accountingDTO);
				}else {
					accountingDTO.setWorker_company_seq(accountingDTO.getCompany_seq());
					accountingDTO.setCompany_seq(company_seq);
					
					resultData = staticService.selectCompanyAccountDayList(accountingDTO);
				}
			}else {
				if(!accountingDTO.getCompany_seq().equals("0")) {
					if(accountingDTO.getWorker_company_seq().equals("0")) {
						resultData = staticService.selectCompanyAccountDayList2(accountingDTO);
					}else {
						resultData = staticService.selectCompanyAccountDayList(accountingDTO);
					}
				}else {
					if(accountingDTO.getWorker_company_seq().equals("0")) {
						//resultData = staticService.selectSmartList(accountingDTO);
						resultData = staticService.selectAllCompanyAccountDayList(accountingDTO);
					}else {
						//resultData = staticService.selectSmartList2(accountingDTO);
						resultData = staticService.selectCompanyAccountDayList(accountingDTO);
					}
				}
			}
			
			//dataMap.put("resultData", resultData);
			
			resultMap.put("companyList", companyList);
			resultMap.put("resultList", resultData);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR +  e);
		
			e.printStackTrace();
		}
		return resultMap;
	}

	@RequestMapping(value="/companyAccountingExcel")
	public View companyAccountingExcel(Model model, HttpServletRequest request,HttpServletResponse response , HttpSession session, AccountingDTO accountingDTO) throws Exception{		
		String fileName = "";
		String companyName ="";
		
		String company_seq = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		List<AccountingDTO> resultData = new ArrayList<AccountingDTO>();
		List<AccountingDTO> resultList = new ArrayList<AccountingDTO>();
		
		companyName = "전체";
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setSidx("company_seq");
		companyDTO.setPaging(null);
		companyDTO.setSrh_use_yn("1");
		
		List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
		
		if(!company_seq.equals("1")) {
			CompanyDTO companyResult = companyList.stream().filter(companyInfo -> companyInfo.getCompany_seq().equals(company_seq))
					.findFirst().orElseThrow(() -> new IllegalAccessException());
			accountingDTO.setWork_fee_rate(companyResult.getWork_fee_rate());
			accountingDTO.setWorker_fee_rate(companyResult.getWorker_fee_rate());
			
			if(accountingDTO.getCompany_seq().equals("0")) {
				fileName = accountingDTO.getStartDate().substring(0, 7) + "_전체_companyAccountingList.xlsx";
				accountingDTO.setCompany_seq(company_seq);
			
				resultData = staticService.selectCompanyAccountDayList2(accountingDTO);
				
				for(int i = 0; i < companyList.size(); i++) {
					if(!company_seq.equals(companyList.get(i).getCompany_seq()) && !companyList.get(i).getCompany_seq().equals("1")) {
						AccountingDTO tmpDTO = new AccountingDTO();
					
						tmpDTO.setWorker_company_seq(company_seq);
						tmpDTO.setCompany_seq(companyList.get(i).getCompany_seq());
						//tmpDTO.setIlbo_fee("0");
						//tmpDTO.setShare_fee("0");
						tmpDTO.setCompany_name(companyList.get(i).getCompany_name());
						tmpDTO.setIlbo_date(accountingDTO.getStartDate().substring(0, 7));
						
						tmpDTO.setWork_income("0");
						tmpDTO.setWork_payment("0");
						tmpDTO.setWork_payment_worker_fee("0");
						tmpDTO.setWorker_income("0");
						tmpDTO.setWorker_payment("0");
						tmpDTO.setWorker_income_worker_fee("0");
						
						resultList.add(tmpDTO);
					}
				}

				for(int i = 0; i < resultList.size(); i++) {
					for(int j = 0; j < resultData.size(); j++) {
						if(resultData.get(j).getCompany_seq().equals(resultList.get(i).getCompany_seq())) {
							//지출
							//resultList.get(i).setIlbo_fee(resultData.get(j).getIlbo_fee());
							//수입
							//resultList.get(i).setShare_fee(resultData.get(j).getShare_fee());
							
							resultList.get(i).setWork_income(resultData.get(j).getWork_income());
							resultList.get(i).setWork_payment(resultData.get(j).getWork_payment());
							resultList.get(i).setWork_payment_worker_fee(resultData.get(j).getWork_payment_worker_fee());
							resultList.get(i).setWorker_income(resultData.get(j).getWorker_income());
							resultList.get(i).setWorker_income_worker_fee(resultData.get(j).getWorker_income_worker_fee());
							resultList.get(i).setWorker_payment(resultData.get(j).getWorker_payment());
						}
					}
				}
			}else {
				CompanyDTO paramDTO = new CompanyDTO();
				paramDTO.setCompany_seq(accountingDTO.getCompany_seq());
				
				CompanyDTO companyDTO2 = companyService.getCompanyInfo(paramDTO);
				fileName = accountingDTO.getStartDate().substring(0, 7) + "_"+ companyDTO2.getCompany_name()+"_companyAccountingList.xlsx";
				
				accountingDTO.setCompany_name(companyDTO2.getCompany_name());
				accountingDTO.setWorker_company_seq(accountingDTO.getCompany_seq());
				accountingDTO.setCompany_seq(company_seq);
				
				resultList = staticService.selectCompanyAccountDayList(accountingDTO);
				
				for(int i = 0; i < resultList.size(); i++) {
					resultList.get(i).setCompany_name(companyDTO2.getCompany_name());
				}
			}
		}else {
			if(!accountingDTO.getWorker_company_seq().equals("0")) {
				CompanyDTO companyResult = companyList.stream().filter(companyInfo -> companyInfo.getCompany_seq().equals(accountingDTO.getWorker_company_seq()))
						.findFirst().orElseThrow(() -> new IllegalAccessException());
				accountingDTO.setWork_fee_rate(companyResult.getWork_fee_rate());
				accountingDTO.setWorker_fee_rate(companyResult.getWorker_fee_rate());
				
				if(accountingDTO.getCompany_seq().equals("0")) {
					fileName = accountingDTO.getStartDate().substring(0, 7) + "_전체_companyAccountingList.xlsx";
					
					String tmpCompanySeq = accountingDTO.getWorker_company_seq();
					accountingDTO.setCompany_seq(tmpCompanySeq);
					
					resultData = staticService.selectCompanyAccountDayList2(accountingDTO);
					
					for(int i = 0; i < companyList.size(); i++) {
						if(!accountingDTO.getWorker_company_seq().equals(companyList.get(i).getCompany_seq()) && !companyList.get(i).getCompany_seq().equals("1")) {
							AccountingDTO tmpDTO = new AccountingDTO();
						
							tmpDTO.setWorker_company_seq(accountingDTO.getWorker_company_seq());
							tmpDTO.setCompany_seq(companyList.get(i).getCompany_seq());
//							tmpDTO.setIlbo_fee("0");
//							tmpDTO.setShare_fee("0");
							tmpDTO.setCompany_name(companyList.get(i).getCompany_name());
							tmpDTO.setIlbo_date(accountingDTO.getStartDate().substring(0, 7));
							tmpDTO.setWork_income("0");
							tmpDTO.setWork_payment("0");
							tmpDTO.setWork_payment_worker_fee("0");
							tmpDTO.setWorker_income("0");
							tmpDTO.setWorker_payment("0");
							tmpDTO.setWorker_income_worker_fee("0");
							resultList.add(tmpDTO);
						}
					}
					
					for(int i = 0; i < resultList.size(); i++) {
						for(int j = 0; j < resultData.size(); j++) {
							if(resultData.get(j).getCompany_seq().equals(resultList.get(i).getCompany_seq())) {
								//지출
								//resultList.get(i).setIlbo_fee(resultData.get(j).getIlbo_fee());
								//수입
								//resultList.get(i).setShare_fee(resultData.get(j).getShare_fee());
								resultList.get(i).setWork_income(resultData.get(j).getWork_income());
								resultList.get(i).setWork_payment(resultData.get(j).getWork_payment());
								resultList.get(i).setWork_payment_worker_fee(resultData.get(j).getWork_payment_worker_fee());
								resultList.get(i).setWorker_income(resultData.get(j).getWorker_income());
								resultList.get(i).setWorker_income_worker_fee(resultData.get(j).getWorker_income_worker_fee());
								resultList.get(i).setWorker_payment(resultData.get(j).getWorker_payment());
							}
						}
					}
				}else {
					CompanyDTO paramDTO = new CompanyDTO();
					paramDTO.setCompany_seq(accountingDTO.getCompany_seq());
					
					CompanyDTO companyDTO2 = companyService.getCompanyInfo(paramDTO);
					fileName = accountingDTO.getStartDate().substring(0, 7) + "_"+ companyDTO2.getCompany_name()+"_companyAccountingList.xlsx";
					
					String tmpCompanySeq = accountingDTO.getCompany_seq();
					
					accountingDTO.setCompany_name(companyDTO2.getCompany_name());
					accountingDTO.setCompany_seq(accountingDTO.getWorker_company_seq());
					accountingDTO.setWorker_company_seq(tmpCompanySeq);
					
					resultList = staticService.selectCompanyAccountDayList(accountingDTO);
				}
			}
		}
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("companyName", companyName);
		model.addAttribute("accountingDTO", accountingDTO);
		model.addAttribute("resultList", resultList);
		
		return new CompanyAccountingExpensesExcel();
	}
	
	@RequestMapping(value="/companyAccountingExcel2")
	public View companyAccountingExcel2(Model model, HttpServletRequest request,HttpServletResponse response , HttpSession session, AccountingDTO accountingDTO) throws Exception{
		String fileName = "";
		String companyName ="";
		
		List<AccountingDTO> resultData = new ArrayList<AccountingDTO>();
		List<AccountingDTO> resultList = new ArrayList<AccountingDTO>();
		
		companyName = "전체";
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setSidx("company_seq");
		companyDTO.setPaging(null);
		companyDTO.setSrh_use_yn("1");
		
		List<CompanyDTO> companyList = companyService.getCompanyList(companyDTO);
		
		if(!accountingDTO.getWorker_company_seq().equals("0")) {
			
			CompanyDTO companyResult = companyList.stream().filter(companyInfo -> companyInfo.getCompany_seq().equals(accountingDTO.getWorker_company_seq()))
					.findFirst().orElseThrow(() -> new IllegalAccessException());
			String workFeeRate = companyResult.getWork_fee_rate();
			String workerFeeRate = companyResult.getWorker_fee_rate();
			
			if(accountingDTO.getCompany_seq().equals("0")) {
				fileName = accountingDTO.getStartDate().substring(0, 7) + "_전체_companyAccountingList.xlsx";
				
				String tmpCompanySeq = accountingDTO.getWorker_company_seq();
				accountingDTO.setCompany_seq(tmpCompanySeq);
				
				//resultData = staticService.selectSmartList(accountingDTO);
				resultData = staticService.selectCompanyAccountDayList2(accountingDTO);
				
				for(int i = 0; i < companyList.size(); i++) {
					if(!accountingDTO.getWorker_company_seq().equals(companyList.get(i).getCompany_seq()) && !companyList.get(i).getCompany_seq().equals("1")) {
						AccountingDTO tmpDTO = new AccountingDTO();
					
						//tmpDTO.setWorker_company_seq(accountingDTO.getWorker_company_seq());
						tmpDTO.setCompany_seq(companyList.get(i).getCompany_seq());
						//tmpDTO.setIlbo_fee("0");
						//tmpDTO.setShare_fee("0");
						tmpDTO.setCompany_name(companyList.get(i).getCompany_name());
						tmpDTO.setIlbo_date(accountingDTO.getStartDate().substring(0, 7));
						tmpDTO.setWork_income("0");
						tmpDTO.setWork_payment("0");
						tmpDTO.setWorker_income("0");
						tmpDTO.setWorker_payment("0");
						tmpDTO.setWork_fee_rate(workFeeRate);
						tmpDTO.setWorker_fee_rate(workerFeeRate);
						
						resultList.add(tmpDTO);
					}
				}
				
				for(int i = 0; i < resultList.size(); i++) {
					for(int j = 0; j < resultData.size(); j++) {
						if(resultData.get(j).getCompany_seq().equals(resultList.get(i).getCompany_seq())) {
							//지출
							//resultList.get(i).setShare_fee(resultData.get(j).getIlbo_fee());
							//수입
							//resultList.get(i).setIlbo_fee(resultData.get(j).getShare_fee());
							
							resultList.get(i).setWork_income(resultData.get(j).getWork_income());
							resultList.get(i).setWork_payment(resultData.get(j).getWork_payment());
							resultList.get(i).setWorker_income(resultData.get(j).getWorker_income());
							resultList.get(i).setWorker_payment(resultData.get(j).getWorker_payment());
						}
					}
				}
			}else {
				CompanyDTO paramDTO = new CompanyDTO();
				paramDTO.setCompany_seq(accountingDTO.getCompany_seq());
				
				CompanyDTO companyDTO2 = companyService.getCompanyInfo(paramDTO);
				fileName = accountingDTO.getStartDate().substring(0, 7) + "_"+ companyDTO2.getCompany_name()+"_companyAccountingList.xlsx";
				
				String tmpCompanySeq = accountingDTO.getCompany_seq();

				//accountingDTO.setCompany_name(companyDTO2.getCompany_name());
				accountingDTO.setCompany_seq(accountingDTO.getWorker_company_seq());
				accountingDTO.setWorker_company_seq(tmpCompanySeq);
				
				resultList = staticService.selectCompanyAccountDayList(accountingDTO);
				for (int i = 0; i < resultList.size(); i++) {
					resultList.get(i).setCompany_name(companyDTO2.getCompany_name());
					resultList.get(i).setWork_fee_rate(workFeeRate);
					resultList.get(i).setWorker_fee_rate(workerFeeRate);
				}
			}
		}else {
			fileName = accountingDTO.getStartDate().substring(0, 7) + "_전체_companyAccountingList.xlsx";
			
			//resultData = staticService.selectSmartList(accountingDTO);
			resultData = staticService.selectAllCompanyAccountDayList(accountingDTO);
			
			for(int i = 0; i < companyList.size(); i++) {
				if(!companyList.get(i).getCompany_seq().equals("1")) {
					AccountingDTO tmpDTO = new AccountingDTO();
				
					//tmpDTO.setWorker_company_seq(accountingDTO.getWorker_company_seq());
					tmpDTO.setCompany_seq(companyList.get(i).getCompany_seq());
					//tmpDTO.setIlbo_fee("0");
					//tmpDTO.setShare_fee("0");
					tmpDTO.setWork_income("0");
					tmpDTO.setWork_payment("0");
					tmpDTO.setWorker_income("0");
					tmpDTO.setWorker_payment("0");
					
					tmpDTO.setCompany_name(companyList.get(i).getCompany_name());
					tmpDTO.setIlbo_date(accountingDTO.getStartDate().substring(0, 7));
					tmpDTO.setWork_fee_rate(companyList.get(i).getWork_fee_rate());
					tmpDTO.setWorker_fee_rate(companyList.get(i).getWorker_fee_rate());
					
					resultList.add(tmpDTO);
				}
			}
			
			for(int i = 0; i < resultList.size(); i++) {
				for(int j = 0; j < resultData.size(); j++) {
					if(resultData.get(j).getCompany_seq().equals(resultList.get(i).getCompany_seq())) {
						//지출
						//resultList.get(i).setIlbo_fee(resultData.get(j).getIlbo_fee());
						//수입
						//resultList.get(i).setShare_fee(resultData.get(j).getShare_fee());
						
						resultList.get(i).setWork_income(resultData.get(j).getWork_income());
						resultList.get(i).setWork_payment(resultData.get(j).getWork_payment());
						resultList.get(i).setWorker_income(resultData.get(j).getWorker_income());
						resultList.get(i).setWorker_payment(resultData.get(j).getWorker_payment());
					}
				}
				
			}
		}
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("companyName", companyName);
		model.addAttribute("accountingDTO", accountingDTO);
		model.addAttribute("resultList", resultList);
		
		return new CompanyAccountingExpensesExcel2();
	}
	
	/**
	  * @Method Name : accountConsultingList
	  * @작성일 : 2020. 12. 16.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 상담사 통계
	  */
	@RequestMapping(value="/accountConsultingList")
	public String accountConsultingList(HttpSession session, Model model) {
		String company_seq  	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		int authLevel = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		
		AdminDTO adminDTO = new AdminDTO();
		
		adminDTO.setPaging(null);
		adminDTO.setSrh_use_yn("1");
		adminDTO.setSrh_company_seq(company_seq);
		adminDTO.setAuth_level("3");
		adminDTO.setSidx("admin_seq");
		adminDTO.setSord("ASC");
		
		int rowCount = adminService.getAdminTotalCnt(adminDTO);
		
		if(rowCount > 0) {
			List<AdminDTO> adminList = adminService.getAdminList(adminDTO);
			
			model.addAttribute("adminList" , adminList);
		}
		
		model.addAttribute("htmlHeader" , "상담사통계");
		model.addAttribute("authLevel" , authLevel);	// 관리자 등급
		
		
		return "/admin/accountConsultingList.admin";
	}

	@RequestMapping(value="/getAccountConsultingList")
	@ResponseBody
	private Map<String, Object> getAccountConsultingList(HttpSession session, AccountingDTO accountingDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
				
		try {
			String company_seq  	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
			
			accountingDTO.setCompany_seq(company_seq);
			List<AccountListDTO> resultList = staticService.getAccountConsultingList(accountingDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
			resultMap.put("resultList", resultList);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR +  e);
		
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	/**
	  * @Method Name : accountConsultingExcel
	  * @작성일 : 2020. 12. 16.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 상담사 통계
	
	  */
	@RequestMapping(value="/accountConsultingExcel")
	public View accountConsultingExcel(Model model, HttpServletRequest request,HttpServletResponse response , HttpSession session, AccountingDTO accountingDTO) throws Exception{		
		String fileName = "";
		String adminName ="";
		
		List<AccountingDTO> resultList = new ArrayList<AccountingDTO>();
		String company_seq  	= ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		
		accountingDTO.setCompany_seq(company_seq);
		
		if("0".equals(accountingDTO.getAdmin_id())) {	
			
			fileName = accountingDTO.getStartDate().substring(0, 7) + "_전체_accountConsulting.xlsx";
			adminName = "전체";
			resultList = staticService.selectAccountConsultingAll(accountingDTO);
		}else {
			
			AdminDTO paramDTO = new AdminDTO();
			paramDTO.setAdmin_id(accountingDTO.getAdmin_id());
			
			AdminDTO adminDTO = adminService.selectInfo(paramDTO);
			adminName= adminDTO.getAdmin_name();
			fileName = accountingDTO.getStartDate().substring(0, 7) + "_"+adminName+"_accountingConsulting.xlsx";
			
			
			resultList = staticService.selectAccountConsultingDayList(accountingDTO);
		}
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("adminName", adminName);
		model.addAttribute("accountingDTO", accountingDTO);
		model.addAttribute("resultList", resultList);
		
		return new AccountConsultingExpensesExcel();
	}

	@RequestMapping(value="/accountingAdList")
	public String accountAdList(HttpSession session, Model model) {
		model.addAttribute("htmlHeader" , "정산");
		
		return "/admin/accountingAdList.admin";
	}
	
	@RequestMapping(value="/getAccountingAdList")
	@ResponseBody
	public Map<String, Object> getAccountAdList(HttpSession session, AccountingDTO accountingDTO){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			List<AccountingDTO> accountAdList = staticService.selectAccountingAdList(accountingDTO);
			resultMap.put("accountAdList", accountAdList);
		}catch(Exception e) {
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	@RequestMapping(value="/accountingAdListExcel")
	public View accountingAdListExcel(Model model, HttpServletRequest request,HttpServletResponse response , HttpSession session, AccountingDTO accountingDTO) throws Exception{		
		String fileName = accountingDTO.getStartDate() + "_광고료통계.xlsx";
		
		List<AccountingDTO> resultList = staticService.selectAccountingAdList(accountingDTO);
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("accountingDTO", accountingDTO);
		model.addAttribute("resultList", resultList);
		
		return new AccountingAdListExcel();
	}
	
	@RequestMapping(value="/jnpTaxExcel")
	public View smartHouseTaxExcel(Model model, HttpServletRequest request,HttpServletResponse response , HttpSession session, AccountDTO accountDTO) throws Exception{		
		String fileName = "세금계산서(" + accountDTO.getSrh_tax_type_kr() + ") - " + accountDTO.getStartDate().replace("-", "") + ".xlsx";
		model.addAttribute("fileName", fileName);
		model.addAttribute("accountDTO", accountDTO);
		
		List<AccountDTO> resultList = staticService.getJnpTaxExcel(accountDTO);
		model.addAttribute("resultList", resultList);
		return new JnpTaxExcel();
	}
}
