package com.nemo.kr.controller.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.JobDTO;
import com.nemo.kr.service.JobService;
import com.nemo.kr.service.WorkerService;
//import com.nemo.kr.util.StringUtil;

import com.ilgajaComm.util.StringUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/job")
public class JobController {
	private static final Logger logger = LoggerFactory.getLogger(JobController.class);
	
	@Autowired JobService jobService;
	@Autowired WorkerService workerService;
	
	// Session 설정
	private JobDTO setSessionToDTO(HttpSession session, JobDTO jobDTO) throws Exception {
		// Session 정보
		int auth_level = 0;
		String admin_id = "GIGPAY";

		// Session 설정
		jobDTO.setAdminLevel(""+ auth_level);
		jobDTO.setReg_admin(admin_id);
		
		return jobDTO;
	}
	
	@RequestMapping("/jobList")
	public String jobList(HttpSession session, Model model, JobDTO jobDTO) {
		//JobDTO jobDTO = new JobDTO();
		jobDTO.getPaging().setPageSize(15);
		
		int cnt = jobService.selectJobListCnt(jobDTO);
		
		if(cnt > 0) {
			jobDTO.setDel_yn("0");
			List<JobDTO> jobList = jobService.selectJobList(jobDTO);
			model.addAttribute("jobList", jobList);
		}
		
		model.addAttribute("jobDTO", jobDTO);
		model.addAttribute("totalCnt", cnt);
		model.addAttribute("htmlHeader", "직종");
		
		return "/admin/job/jobList.leftAdmin";
	}
	
	@RequestMapping("/changeJobList")
	@ResponseBody
	public Map<String, Object> changeJobList(HttpServletRequest request, HttpServletResponse response, HttpSession session, JobDTO jobDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			jobDTO.setJob_rank("1");
			jobDTO.setPaging(null);
			List<JobDTO> changeJobList = jobService.selectJobList(jobDTO);
			
			resultMap.put("changeJobList", changeJobList);
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);	
		}
		
		return resultMap;
	}
	
	@RequestMapping("/getJobList")
	@ResponseBody
	public Map<String, Object> getJobList(HttpServletRequest request, HttpServletResponse response, JobDTO jobDTO) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		if(!jobDTO.getJob_rank().equals("1")) {
			jobDTO.setJob_kind(null);
		}
		
		try {
			int cnt = jobService.selectJobListCnt(jobDTO);
			
			if(cnt > 0) {
				if(jobDTO.getJob_rank().equals("1"))
					jobDTO.getPaging().setPageSize(15);
				else
					jobDTO.setPaging(null);

				jobDTO.setDel_yn("0");
				List<JobDTO> jobList = jobService.selectJobList(jobDTO);
				resultMap.put("jobList", jobList);
			}

			resultMap.put("jobDTO", jobDTO);
			resultMap.put("totalCnt", cnt);
			resultMap.put("code", Const.CODE_SUCCESS);
		} catch(Exception e) {
			logger.error("getJobList ERR : {}", e.getMessage());
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("msg", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/jobWrite")
	public String jobWrite(HttpSession session, Model model, JobDTO jobDTO) throws Exception {
		jobDTO = setSessionToDTO(session, jobDTO);
		
		model.addAttribute("htmlHeader", "세부 직종 추가");
		
		return "/admin/job/jobWrite.leftAdmin";
	}
	
	@RequestMapping("/insertJob")
	@ResponseBody
	public Map<String, Object> insertJob(HttpServletRequest request, HttpServletResponse response, HttpSession session, JobDTO jobDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			jobDTO = setSessionToDTO(session, jobDTO);
			
			jobService.insertJob(jobDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/jobEdit")
	public String jobEdit(HttpSession session, Model model, JobDTO jobDTO) throws Exception {
		jobDTO = setSessionToDTO(session, jobDTO);
		
		JobDTO jobInfo = jobService.selectInfo(jobDTO);
		
		model.addAttribute("jobDTO", jobDTO);
		model.addAttribute("htmlHeader", "직종 수정");
		model.addAttribute("jobInfo", jobInfo);
		
		return "/admin/job/jobEdit.leftAdmin";
	}
	
	@RequestMapping("updateJob")
	@ResponseBody
	@Transactional
	public Map<String, Object> updateJob(HttpServletRequest request, HttpServletResponse response, HttpSession session, JobDTO jobDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String beforeJobName = jobDTO.getBefore_job_name();
		
		try {
			jobDTO = setSessionToDTO(session, jobDTO);
			
			jobService.updateJob(jobDTO);

			JobDTO paramDTO = new JobDTO();
			
			paramDTO.setJob_name(jobDTO.getJob_name());
			paramDTO.setBefore_job_name(beforeJobName);
			
			if(!beforeJobName.equals(paramDTO.getJob_name())) {
				jobService.updateWorkerJobName(paramDTO);
			}
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("deleteJob")
	@ResponseBody
	public Map<String, Object> deleteJob(HttpServletRequest request, HttpServletResponse response, HttpSession session, JobDTO jobDTO) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			jobDTO = setSessionToDTO(session, jobDTO);
			
			jobService.deleteJob(jobDTO);
			
			resultMap.put("code", Const.CODE_SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/updateJobDetail")
	@ResponseBody
	public Map<String, Object> updateJobDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		JobDTO jobDTO = new JobDTO();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			for(int i = 0; i < paramList.size(); i++) {
				jobDTO = gson.fromJson(paramList.get(i).toString(), JobDTO.class);
				jobDTO = setSessionToDTO(session, jobDTO);
				
				if(jobDTO.getJob_flag().equals("I")) {
					jobService.insertJobDetail(jobDTO);
				}else if(jobDTO.getJob_flag().equals("D") && jobDTO.getJob_seq() != null) {
					jobService.deleteJobDetail(jobDTO);
				}else if(jobDTO.getJob_flag().equals("U")) {
					jobService.updateJobDetail(jobDTO);
				}
			}
			
			jobDTO.setJob_rank(paramList.get(0).get("job_rank").toString());
			
			int cnt = jobService.selectJobListCnt(jobDTO);
			
			resultMap.put("totalCnt", cnt);
			
			if(cnt > 0) {
				jobDTO.setPaging(null);
				jobDTO.setDel_yn("0");
				jobDTO.setUse_yn(null);
				
				List<JobDTO> jobList = jobService.selectJobList(jobDTO);
				
				resultMap.put("jobList", jobList);
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/updateAddJobChoose")
	@ResponseBody
	public Map<String, Object> updateAddJobChoose(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		JobDTO jobDTO = new JobDTO();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			for(int i = 0; i < paramList.size(); i++) {
				jobDTO = gson.fromJson(paramList.get(i).toString(), JobDTO.class);
				jobDTO = setSessionToDTO(session, jobDTO);
				
				if(jobDTO.getJob_flag().equals("I")) {
					jobService.insertAddJobChoose(jobDTO);
				}else if(jobDTO.getJob_flag().equals("D") && jobDTO.getJob_seq() != null) {
					jobService.deleteAddJobChoose(jobDTO);
				}else if(jobDTO.getJob_flag().equals("U")) {
					jobService.updateAddJobChoose(jobDTO);
				}
			}
			
			jobDTO.setJob_rank(paramList.get(0).get("job_rank").toString());
			
			int cnt = jobService.selectJobListCnt(jobDTO);
			
			resultMap.put("totalCnt", cnt);
			
			if(cnt > 0) {
				jobDTO.setUse_yn(null);
				jobDTO.setDel_yn("0");
				jobDTO.setPaging(null);
				
				List<JobDTO> jobList = jobService.selectJobList(jobDTO);
				
				resultMap.put("jobList", jobList);
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
	
	@RequestMapping("/updateJobOrder")
	@ResponseBody
	public Map<String, Object> updateJobOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		JobDTO jobDTO = new JobDTO();
		
		try {
			resultMap.put("code", Const.CODE_SUCCESS);
			
			for(int i = 0; i < paramList.size(); i++) {
				jobDTO = gson.fromJson(paramList.get(i).toString(), JobDTO.class);
				
				jobDTO.setJob_order((i + 1) + "");
				
				jobService.updateJobOrder(jobDTO);
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			resultMap.put("code", Const.CODE_SYSTEM_ERROR);
			resultMap.put("message", Const.MSG_SYSTEM_ERROR);
		}
		
		return resultMap;
	}
}
