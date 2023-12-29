package com.nemo.kr.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.EmDTO;
import com.nemo.kr.dto.EmployerDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.excel.ManagerListExcel;
import com.nemo.kr.service.CodeService;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.EmployerService;
import com.nemo.kr.service.ManagerService;
import com.nemo.kr.service.WorkService;
//import com.nemo.kr.util.StringUtil;


/**
 * 일가자 구인처 관리 Controller
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-14
 */
@Controller
@RequestMapping("/admin")
public class ManagerController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@Autowired ManagerService managerService;
	@Autowired EmployerService employerService;
	@Autowired WorkService workService;
	@Autowired CompanyService companyService;
	@Autowired CodeService codeService;

	// Session 설정
	private ManagerDTO setSessionToDTO(HttpSession session, ManagerDTO managerDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();
		String get_company_seq = StringUtil.isNullToString(managerDTO.getCompany_seq());

		// Session 설정
		managerDTO.setAdminLevel(""+ auth_level);
		if ( auth_level != 0 ) {  // 전체최고 관리자가 아닌 경우 해당 회사 정보만 조회 가능하도록
			managerDTO.setSrh_company_seq(company_seq);
			if ( "".equals(get_company_seq) ) {
				managerDTO.setCompany_seq(company_seq);
			}
		}

		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			managerDTO.setOwner_id( admin_id);
		}

		managerDTO.setReg_admin(admin_id);

		return managerDTO;
	}

	// 매니져 구인처 현장 트리 (JSP)
	@RequestMapping(value="/managerEWTreeViewJSP")
	public String managerEWTreeViewJSP(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmDTO emDTO, ModelMap model) throws Exception {
		List<EmDTO>  employerList = managerService.getManagerEWTreeList(emDTO); 
				
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsonInString = gson.toJson(employerList);
		
		model.addAttribute("jsonData", jsonInString);
		
		return "/admin/managerEWTree.layout_admin";
	}
	
	@RequestMapping(value="/getManagerEWTree")
	@ResponseBody
	public String getManagerEWTree(HttpServletRequest request, HttpServletResponse response, HttpSession session, EmDTO emDTO) throws Exception {
		List<EmDTO>  employerList = managerService.getManagerEWTreeList(emDTO); 

		//DTO 를 json 으로 바꿈
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsonInString = gson.toJson(employerList);

		
		//jsonInString = jsonInString.substring(1, jsonInString.length() - 1);
		
		return jsonInString;
	}

	
	private ManagerDTO setSessionToManagerDTO(HttpSession session, ManagerDTO managerDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String company_seq  = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_seq();
		String company_name = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getCompany_name();
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();

		//파트너 일 경우는 파트너 인것만 가져 온다.
		if(auth_level == 3 ){
			managerDTO.setOwner_id( admin_id);
		}

		managerDTO.setReg_admin(admin_id);

		return managerDTO;
	}

	/**
	 * @author : nemojjang
	 * @date   : 2019. 6. 22.
	 * @dec    : 현장 매지져 manager_type 이 O 가 넘어오도록 한다.
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/managerList")
	public String managerList(HttpSession session, Model model) {
		model.addAttribute("htmlHeader" , "현장매니저 관리");
		
		CodeDTO codeDTO = new CodeDTO();
		codeDTO.setCode_type("MJC");
		model.addAttribute("joinRouteOpts", codeService.getCodeJqgridSelectFormat(codeDTO));
		
		return "/admin/managerList.admin";
	}

	/**
	 * @author : nemojjang
	 * @date   : 2019. 6. 22.
	 * @dec    : 본사 매니저
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/eManagerList")
	public String eManagerList(HttpSession session, Model model) {
		model.addAttribute("htmlHeader" , "본사매니저 관리");
		
		CodeDTO codeDTO = new CodeDTO();
		codeDTO.setCode_type("MJC");
		model.addAttribute("joinRouteOpts", codeService.getCodeJqgridSelectFormat(codeDTO));
		
		return "/admin/eManagerList.admin";
	}

	@RequestMapping(value="/getManagerList")
	@ResponseBody
	public JSONObject getManagerList(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception {
		int auth_level;

		String where    = managerDTO.getWhere();
		String srh_type = managerDTO.getSrh_type();
		String srh_text = managerDTO.getSrh_text();

		// Session 설정
		managerDTO = setSessionToManagerDTO(session, managerDTO);

		if ( srh_text != null && !"".equals(srh_text) ) {
//			if ( "".equals(where) || (managerDTO.getFilters() != null && !managerDTO.getFilters().equals(""))) {
//				where = " AND ";
//			}
			
			// filter 검색 했을 때도 AND 절이 추가 되게 
			if ( "".equals(where) || (managerDTO.getFilters() != null && !managerDTO.getFilters().equals(""))) {
				where += " AND ";
			}

			if ( srh_type != null && !"".equals(srh_type) ) {
				 
		    	  if("manager_name".equals(srh_type) || "manager_phone".equals(srh_type) ) {
		    		  where += "fn_decrypt(" + srh_type +")" +" LIKE '%"+ srh_text +"%' ";
		    	  }else {
		    		  where += srh_type +" LIKE '%"+ srh_text +"%' ";
		    	  }
			} else {
				auth_level = StringUtil.isNullToInt(managerDTO.getAdminLevel());

				if ( "".equals(where)) {
					where += " AND ";
				}
				
				where += " (";
				if ( auth_level == 0 ) {
					where += " company_name LIKE '%"+ srh_text +"%' OR";
				}

				if("O".equals(managerDTO.getManager_type()) ){
					where += " fn_decrypt(manager_name)     LIKE '%"+ srh_text +"%'";
					where += " OR fn_decrypt(manager_phone)    LIKE '%"+ srh_text +"%'";
					where += " )";
				}else{
					where += " employer_name     LIKE '%"+ srh_text +"%'";
					where += " OR employer_num    LIKE '%"+ srh_text +"%'";
					where += " )";
				}
			}

			managerDTO.setWhere(where);
		}

		//본사매니저 요청 일때는 company_seq 를 없애야 한다.
		if("E".equals(managerDTO.getManager_type()) ){
			managerDTO.setSrh_company_seq("");
		}

		int rowCount = managerService.getManagerTotalCnt(managerDTO);

		managerDTO.getPaging().setRowCount(rowCount);
		List<ManagerDTO> result = managerService.getManagerList(managerDTO);
		
		JSONObject jsonObj = new JSONObject();

		jsonObj.put("rows",    result);                                             						// 현재페이지의 목록데이터
		jsonObj.put("page",    managerDTO.getPaging().getPageNo());                   	// 현재페이지
		jsonObj.put("total",   managerDTO.getPaging().getTotalPageCnt());             	// 총페이지수
		jsonObj.put("records", managerDTO.getPaging().getRowCount());                 // 총글목록수

		return jsonObj;
	}

	// 행 추가
	@RequestMapping(value="/setManagerCell")
	@ResponseBody
	public JSONObject setManagerCell(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		managerDTO = setSessionToDTO(session, managerDTO);
		managerDTO.setJoin_route("MJC001");	//가입경로 : 사무실
		managerService.setManagerCell(managerDTO);

		jsonObj.put("manager_seq", managerDTO.getManager_seq());                          // 성공

		return jsonObj;
	}

	// 변경된 행 저장
	@RequestMapping(value="/setManagerInfo")
	@ResponseBody
	public JSONObject setManagerInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO, EmployerDTO employerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		managerDTO = setSessionToManagerDTO(session, managerDTO);

		if("manager_type".equals(managerDTO.getCellname())) {
			if("E".equals(managerDTO.getManager_type())) {
				EmDTO emDTO = new EmDTO();
				emDTO.setManager_seq(managerDTO.getManager_seq());;
				List<EmDTO> emList = managerService.getManagerEWTreeList(emDTO);
				
				if(emList.size() > 0) {
					jsonObj.put("code", "9999");
					jsonObj.put("message", "현장매니져가 추가한 구인처가 있습니다. 구인처를 모두 삭제 후 변경 할 수 있습니다.");
					
					return jsonObj;
				}
			}
		}
		managerService.setManagerInfo(managerDTO);

		jsonObj.put("code", "0000");
		jsonObj.put("message", "변경성공");                 // 성공

		return jsonObj;
	}

	// 행 삭제
	@RequestMapping(value="/removeManagerInfo")
	@ResponseBody
	public JSONObject removeManagerInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		// Session 설정
		managerDTO = setSessionToDTO(session, managerDTO);

		managerService.removeManagerInfo(managerDTO);

		return jsonObj;
	}

	// 현장매니저 등록 폼 저장
	@RequestMapping(value="/setManager")
	@ResponseBody
	public JSONObject setManager(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();
		
		try {
			//현장등록에서 현장매니저를 등록한경우
			String work_yn = StringUtil.isNullToString(request.getParameter("work_yn"));
	
			if(StringUtil.isNullNZero(managerDTO.getWork_seq())) throw new Exception("파라메터 누락 : 현장 순번 ");
			if(StringUtil.isNullNZero(managerDTO.getCompany_seq())) throw new Exception("파라메터 누락 : 지점 순번 ");
			if(StringUtil.isNullNZero(managerDTO.getEmployer_seq())) { throw new Exception("파라메터 누락 : 구인처 순번 ");}
			if(StringUtil.isNullNZero(managerDTO.getManager_name())) throw new Exception("파라메터 누락 : 매니져 이름  ");
			if(StringUtil.isNullNZero(managerDTO.getManager_phone())) throw new Exception("파라메터 누락 : 매니져 폰  ");
			
			// Session 설정
			managerDTO = setSessionToDTO(session, managerDTO);
			managerDTO.setParent_company_seq("0");
			managerDTO.setUse_yn("1");

			managerService.setManager(managerDTO);// 현장매니저 추가
			
			if ( "Y".equals(work_yn) ) {        // 현장등록에서 현장매니저를 등록한 경우, 현장 정보 변경
					String work_seq = StringUtil.isNullToString(request.getParameter("work_seq"));
		
					WorkDTO workDTO = new WorkDTO();
		
					workDTO.setWork_seq(work_seq);
					workDTO.setManager_seq(managerDTO.getManager_seq());
					workDTO.setManager_name(managerDTO.getManager_name());
					workDTO.setManager_phone(managerDTO.getManager_phone());
					workDTO.setMod_admin(managerDTO.getReg_admin());
		
					workService.setWorkInfo(workDTO);
			}
	
			jsonObj.put("code", Const.CODE_SUCCESS);
			jsonObj.put("manager_seq", managerDTO.getManager_seq());                 // 성공
			jsonObj.put("wm_seq",managerDTO.getWm_seq());

		} catch (Exception e) {
			// TODO: handle exception
			jsonObj.put("code", "9999");
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	/**
	  * @Method Name : setManagerAdd
	  * @작성일 : 2021. 5. 13.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 현장매니져 등록
	
	  */
	@RequestMapping(value="/setManagerReg")
	@ResponseBody
	public JSONObject setManagerReg(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO)  {
		JSONObject jsonObj = new JSONObject();
		
		try {
			if(StringUtil.isNullNZero(managerDTO.getWork_seq())) throw new Exception("파라메터 누락 : 현장 순번 ");
			if(StringUtil.isNullNZero(managerDTO.getCompany_seq())) throw new Exception("파라메터 누락 : 지점 순번 ");
			if(StringUtil.isNullNZero(managerDTO.getEmployer_seq())) { throw new Exception("파라메터 누락 : 구인처 순번 ");}
			
			// Session 설정
			managerDTO = setSessionToDTO(session, managerDTO);
			managerDTO.setParent_company_seq("0");
			managerDTO.setUse_yn("1");

			managerService.setManager(managerDTO);// 현장매니저 등록
			
			jsonObj.put("code", Const.CODE_SUCCESS);
			jsonObj.put("manager_seq", managerDTO.getManager_seq());                 // 성공
			jsonObj.put("wm_seq",managerDTO.getWm_seq());

		} catch (Exception e) {
			// TODO: handle exception
			jsonObj.put("code", "9999");
			jsonObj.put("message", e.getMessage());
			e.printStackTrace();
		}
		return jsonObj;
	}
		
	/**
	  * @Method Name : setManagerAdd
	  * @작성일 : 2021. 5. 13.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 현장매니저 추가
	
	  */
	@RequestMapping(value="/setManagerAdd")
	@ResponseBody
	public JSONObject setManagerAdd(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO)  {
		JSONObject jsonObj = new JSONObject();
		
		try {
			if(StringUtil.isNullNZero(managerDTO.getWork_seq())) throw new Exception("파라메터 누락 : 현장 순번 ");
			if(StringUtil.isNullNZero(managerDTO.getCompany_seq())) throw new Exception("파라메터 누락 : 지점 순번 ");
			if(StringUtil.isNullNZero(managerDTO.getEmployer_seq())) { throw new Exception("파라메터 누락 : 구인처 순번 ");}
			if(StringUtil.isNullNZero(managerDTO.getManager_seq())) { throw new Exception("파라메터 누락 : 매니져 순번 ");}
			
			// Session 설정
			managerDTO = setSessionToDTO(session, managerDTO);
			managerDTO.setParent_company_seq("0");
			managerDTO.setUse_yn("1");

			managerService.insertRelationM(managerDTO);// 현장매니저 추가
			
			jsonObj.put("code", Const.CODE_SUCCESS);
			jsonObj.put("manager_seq", managerDTO.getManager_seq());                 // 성공
			jsonObj.put("wm_seq",managerDTO.getWm_seq());

		} catch (Exception e) {
			// TODO: handle exception
			jsonObj.put("code", "9999");
			jsonObj.put("message", e.getMessage());
			e.printStackTrace();
		}
		return jsonObj;
	}

	@RequestMapping(value="/getManagerInfo")
	@ResponseBody
	public JSONObject getManagerInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		ManagerDTO resultDTO = managerService.selectManagerInfo(managerDTO);

		jsonObj.put("result", "0000");
		jsonObj.put("resultDTO", resultDTO);                 // 성공

		return jsonObj;
	}

	//해당 work 를 등록한 메니져 목록
	@RequestMapping(value="/getWorkManagerList")
	@ResponseBody
	public JSONObject getWorkManagerList(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		List<ManagerDTO> managerList = managerService.getWorkManagerList(managerDTO);

		jsonObj.put("code", "0000");
		jsonObj.put("managerList", managerList);                 // 성공

		return jsonObj;
	}
	
	//매니져 검색
	@RequestMapping(value="/getSearchManagerList")
	@ResponseBody
	public JSONObject getSearchManagerList(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception {
		JSONObject jsonObj = new JSONObject();

		List<ManagerDTO> managerList = managerService.getSearchManagerList(managerDTO);

		jsonObj.put("code", "0000");
		jsonObj.put("managerList", managerList);                 // 성공

		return jsonObj;
	}
	
	//Em등록
	@RequestMapping(value="/insertEm")
	@ResponseBody
	public Map<String, String> insertEm(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO, EmDTO emDTO) throws Exception {
		managerDTO = setSessionToDTO(session, managerDTO);
		
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap = managerService.insertEm(managerDTO);
		
		String jsonInString = getTreeList(emDTO);
		
		resultMap.put("jsonData", jsonInString);
		
		return resultMap;
	}
	
	//Em삭제
	@RequestMapping(value="/deleteEm")
	@ResponseBody
	public Map<String, String> deleteEm(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO, EmDTO emDTO) throws Exception {
		managerDTO = setSessionToDTO(session, managerDTO);
		
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap = managerService.deleteEm(managerDTO);
		
		String jsonInString = getTreeList(emDTO);
		
		resultMap.put("jsonData", jsonInString);
		
		return resultMap;
	}
	
	//Wm등록
	@RequestMapping(value="/insertWm")
	@ResponseBody
	public Map<String, String> insertWm(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO, EmDTO emDTO) throws Exception {
		managerDTO = setSessionToDTO(session, managerDTO);
		
		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap = managerService.insertWm(managerDTO);
		
		String jsonInString = getTreeList(emDTO);
		
		resultMap.put("jsonData", jsonInString);
		
		return resultMap;
	}
	
	//Wm삭제
	@RequestMapping(value="/deleteWm")
	@ResponseBody
	public Map<String, String> deleteWm(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO, EmDTO emDTO) throws Exception {
		managerDTO = setSessionToDTO(session, managerDTO);

		Map<String, String> resultMap = new HashMap<String, String>();

		resultMap = managerService.deleteWm(managerDTO);
		
		String jsonInString = getTreeList(emDTO);
		
		resultMap.put("jsonData", jsonInString);

		return resultMap;
	}
	
	/**
	 * 본사관리 메니져에 등록 되어 져 있는 구인처인지 확인한다.
	 */
	@RequestMapping("/checkEmployer")
	@ResponseBody
	public Map<String, String> checkEmployer(HttpServletRequest request,HttpServletResponse response , HttpSession session,  ManagerDTO managerDTO ){
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			// Session 정보
			AdminDTO adminSession = (AdminDTO) session.getAttribute(Const.adminSession);
			
			EmployerDTO paramDTO = new EmployerDTO();
			paramDTO.setEmployer_seq(managerDTO.getEmployer_seq());
			EmployerDTO employerDTO = employerService.getEmployerView(paramDTO);
			
			if(StringUtil.isNull( employerDTO.getEmployer_num())  ){
				resultMap.put("code", "2222");
				resultMap.put("message", "사업자번호가 없는 구인처입니다.\n구인처관리에서 사업자 번호를 등록 하고 사용하세요.");
				return resultMap;
			}else{
			 	
				String employerNum = employerDTO.getEmployer_num();
				resultMap.put("employer_num", employerNum);
				
				managerDTO.setEmployer_num(employerNum);
				
		    	List<ManagerDTO> resultList = managerService.getCheckManagerEmployerNumList(managerDTO);
		    	
		    	if(resultList.size() > 0){
		    		ManagerDTO resultDTO = resultList.get(0);
		    		resultMap.put("code", "1111");
		    		resultMap.put("message", "[" + resultDTO.getCompany_name() +"] 에서 이미 등록된 사업자 번호 입니다.");
		     	    
		     	    return resultMap;
		    	}
			}

			resultMap.put("code", "0000");
		} catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", "9999");
		}

		return resultMap;
	}
	
	@RequestMapping(value="/managerListExcel")
	public View managerListExcel(Model model, HttpServletRequest request,HttpServletResponse response , HttpSession session, ManagerDTO managerDTO) throws Exception{		
		String fileName = "";
		String companyName = "";
		
		int auth_level;

		String where    = managerDTO.getWhere();
		String srh_type = managerDTO.getSrh_type();
		String srh_text = managerDTO.getSrh_text();
		managerDTO.setManager_type("O");
		managerDTO.setUse_yn("1");
		managerDTO.setSidx("manager_seq");
		managerDTO.setSord("desc");
		// Session 설정
		managerDTO = setSessionToDTO(session, managerDTO);
		if( !managerDTO.getCompany_seq().equals("") || !managerDTO.getCompany_seq().isEmpty() ) {
			CompanyDTO com = new CompanyDTO();
			com.setCompany_seq(managerDTO.getCompany_seq());
			com = companyService.getCompanyInfo(com);
			companyName = com.getCompany_name() + "_";
		}

		if ( srh_text != null && !"".equals(srh_text) ) {
			if ( "".equals(where) ) {
				where = " AND ";
			}

			if ( srh_type != null && !"".equals(srh_type) ) {
				where += srh_type +" LIKE '%"+ srh_text +"%'";

			} else {
				auth_level = StringUtil.isNullToInt(managerDTO.getAdminLevel());

				where += " (";
				if ( auth_level == 0 ) {
					where += " c.company_name LIKE '%"+ srh_text +"%' OR";
				}

				if("O".equals(managerDTO.getManager_type()) ){
					where += " m.manager_name     LIKE '%"+ srh_text +"%'";
					where += " OR m.manager_phone    LIKE '%"+ srh_text +"%'";
					where += " )";
				}else{
					where += " e.employer_name     LIKE '%"+ srh_text +"%'";
					where += " OR e.employer_num    LIKE '%"+ srh_text +"%'";
					where += " )";

				}
			}

			managerDTO.setWhere(where);
		}

		//본사매니저 요청 일때는 company_seq 를 없애야 한다.
		if("E".equals(managerDTO.getManager_type()) ){
			managerDTO.setSrh_company_seq("");
		}

		//모든 데이터를 가져오기위해 paging 제거
		managerDTO.setPaging( null ); 
		managerDTO.setManager_phone(null);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		fileName = companyName + "현장 매니저 관리_" + format.format(today) + ".xlsx";
		List<ManagerDTO> resultList = managerService.selectManagerList(managerDTO);
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("companyName", companyName);
		model.addAttribute("managerDTO", managerDTO);
		model.addAttribute("resultList", resultList);
		
		return new ManagerListExcel();
	}
	
	public String getTreeList(EmDTO emDTO) {
		emDTO.setEmployer_seq(null);
		
		List<EmDTO>  employerList = managerService.getManagerEWTreeList(emDTO);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsonInString = gson.toJson(employerList);
		
		return jsonInString;
	}
}
