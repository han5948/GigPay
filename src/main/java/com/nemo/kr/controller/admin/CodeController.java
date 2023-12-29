package com.nemo.kr.controller.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.service.CodeService;
import com.nemo.kr.service.GroupCodeService;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/code")
public class CodeController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);
	
	@Autowired GroupCodeService groupCodeService;
	@Autowired CodeService codeService;
	
	// Session 설정
	private CodeDTO setSessionToDTO(HttpSession session, CodeDTO codeDTO) throws Exception {
		// Session 정보
		int auth_level      = StringUtil.isNullToInt(((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAuth_level(), -1);
		String admin_id     = ((com.nemo.kr.dto.AdminDTO) session.getAttribute(Const.adminSession)).getAdmin_id();

		// Session 설정
		codeDTO.setAdminLevel(""+ auth_level);
		codeDTO.setReg_admin(admin_id);
		
		return codeDTO;
	}
	
	/**
	 * 
	  * @Method Name : getCodeList
	  * @작성일 : 2020. 6. 17.
	  * @작성자 : Park YunSoo
	  * @필수 param : code_type
	  * @선택 param :
	  * @Method 설명 :
	 */
	@RequestMapping("/getCodeList")
	@ResponseBody
	public Map<String, Object> getCodeList(HttpSession session, CodeDTO codeDTO) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			List<CodeDTO> codeList = codeService.getCodeList(codeDTO);
			
			resultMap.put("codeList", codeList);
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	/**
	 * 
	  * @Method Name : updateCode
	  * @작성일 : 2020. 6. 18.
	  * @작성자 : Park YunSoo
	  * @필수 param : CodeDTO 
	  * @선택 param :
	  * @Method 설명 : 코드 insert, update
	 */
	@RequestMapping("/updateCode")
	@ResponseBody
	public Map<String, Object> updateCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		CodeDTO codeDTO = new CodeDTO();

		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			for(int i = 0; i < paramList.size(); i++) {
				codeDTO = gson.fromJson(paramList.get(i).toString(), CodeDTO.class);
				codeDTO = setSessionToDTO(session, codeDTO);
				
				codeDTO.setCode_rank((i + 1) + "");
				
				if(codeDTO.getCode_flag().equals("I")) {
					codeService.insertCode(codeDTO);
				}else if(codeDTO.getCode_flag().equals("D") && codeDTO.getCode_seq() != null) {
					codeService.deleteCode(codeDTO);
				}else if(codeDTO.getCode_flag().equals("U")) {
					codeService.updateCode(codeDTO);
				}
			}
			
			codeDTO.setUse_yn(null);
			
			resultMap.put("codeList", codeService.getCodeList(codeDTO));
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
