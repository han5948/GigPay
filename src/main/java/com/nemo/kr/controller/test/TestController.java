package com.nemo.kr.controller.test;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nemo.kr.dto.AccountDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.FileDTO;
import com.nemo.kr.dto.IlboDTO;
import com.nemo.kr.dto.ManagerDTO;
import com.nemo.kr.dto.WorkDTO;
import com.nemo.kr.dto.WorkerDTO;
import com.nemo.kr.service.CompanyService;
import com.nemo.kr.service.TestService;
import com.nemo.kr.util.FileUtil;
import com.nemo.kr.util.test2;


@Controller
@Transactional
@RequestMapping("/test")
public class TestController {
	@Resource(name="commonProperties")	  private Properties commonProperties;
	
	@Autowired TestService testService;
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	CompanyService companyService;
	
//	@RequestMapping(value="/workerListDelete")
//	public void workerListDelete() {
//		List<WorkerDTO> workerList = testService.findDeleteWorker();
//		for(WorkerDTO worker : workerList) {
//			System.out.println(worker.getWorker_name());
//			FileDTO paramFileDTO = new FileDTO();
//			paramFileDTO.setService_type("WORKER");
//			paramFileDTO.setService_seq(worker.getWorker_seq());
//			List<FileDTO> fileList = testService.selectFileList(paramFileDTO);
//
//			for (FileDTO fileDTO : fileList) {
//				// 파일삭제
//				File file = new File(fileDTO.getFile_path() + fileDTO.getFile_name());
//				if (file.exists()) {
//					if( !file.delete() ) {
//						System.out.println(worker.getWorker_name() + "[" + fileDTO.getFile_path() + fileDTO.getFile_name() + "] 파일 삭제실패!");
//					};
//				}
//
//				// 썸네일 파일 삭제
//				File thumFile = new File(fileDTO.getFile_path() + fileDTO.getFile_thum_name());
//				if (thumFile.exists()) {
//					if( !thumFile.delete() ) {
//						System.out.println(worker.getWorker_name() + "[" + fileDTO.getFile_path() + fileDTO.getFile_thum_name() + "] 썸네일 파일 삭제실패!");
//					};
//				}
//
//				// 파일테이블 삭제
//				testService.deleteFileInfo(fileDTO);
//			}
//		}
//	}
	
	@RequestMapping(value="/pdfTest")
	public String pdfTest() {
		
		return "";
	}
	
	@RequestMapping(value="/popTest")
	public String popTest() {
		return "/admin/test/popupTest";
	}
	
	@RequestMapping(value="/fcmTest")
	@ResponseBody
	public void fcmTest(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        
		try {    
            
            String token = "dsBLhzuCluE:APA91bF0V5Zzo771gxsw6jgzVfgO14b2qYoCnTNh50q9b5xtBAbr0NKMJUru675Vm7cvRqLab_F7u4X_PVsPb5uO85BD2rD5cpuiNCXEq9pZFeFvd0fFwPA4WfimMu_VZvPXj4OrgUK0";
            
            JSONObject notification = new JSONObject();
            notification.put("title", "TEST Server");
            notification.put("body", "TEST body");
            
            JSONObject data = new JSONObject();
            data.put("pushType", "I");
            data.put("sendSeq", "1");
            
            test2 test = new test2();
            test.pro();
			
			/*  
			String path = commonProperties.getProperty("fcmJsonPath");
	
			FcmMessageUtil fcm = new FcmMessageUtil(path);
			PushSendDTO pushDTO = fcm.send("A", token, notification, data, new PushSendDTO());
			
			System.out.println(pushDTO.getFcm_message());
			*/
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        
    }
	
	
	//매니져 정리
	/*
	  DELETE FROM manager WHERE manager_phone IS NULL;
	  DELETE FROM manager WHERE manager_phone = '379D43A967BD5C3C02B5B8E12D2E6C11';
	  DELETE FROM manager WHERE manager_phone = '39FC2CA4AF0BD892EB347A5B503F5FAB';
	  DELETE FROM manager WHERE manager_phone = 'B4C7B5E317F99D05A8E91C2309B0C841';
	  DELETE FROM manager WHERE manager_phone = 'C3DCF06F0EA5F6E14E2F30D7DE215FF7';
	  DELETE FROM manager WHERE manager_phone = 'C19EEDE5FC164081DE76CC2D34001324F843FB0693B1466BA9B0A6A72209D387';
	  
	  부모가 2개인 경우 어떤것을 적용을 해야 하나? use_yn =1 인 것을 적용 해야 되남?
	  
	 */
		@RequestMapping(value="/setManager")
		@ResponseBody
		public JSONObject setManager(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception {

			JSONObject jsonObj = new JSONObject();

			try {
		
				testService.setManager(managerDTO);
	
				jsonObj.put("code", "0000");
			} catch (Exception e) {
				// TODO: handle exception
				jsonObj.put("code", "9999");
				e.printStackTrace();
			}

			return jsonObj;
		}
	
		@RequestMapping(value="/setManagerSeq")
		@ResponseBody
		public void setManagerSeq(HttpServletRequest request, HttpServletResponse response, HttpSession session, ManagerDTO managerDTO) throws Exception {

			try {
		
				testService.setManagerSeq();
	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		
		@RequestMapping("/updateJobName")
		@ResponseBody
		public void updateJobName(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
			try {
				testService.updateJobName();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		@RequestMapping("/updateReserves")
		public void updateReserves() {
			try {
				List<CompanyDTO> companyList = testService.selectCompanyList();
				
				for(CompanyDTO companyInfoDTO : companyList) {
					AccountDTO accountDTO = new AccountDTO();
					
					accountDTO.setCompany_seq(companyInfoDTO.getCompany_seq());
					
					AccountDTO resultDTO = testService.selectBalance(accountDTO);
					
					if(resultDTO != null) {
						int balance = Integer.parseInt(resultDTO.getAccount_balance());
						
						
						List<AccountDTO> accountList = testService.selectAccountList(accountDTO);
						
						for(int i = 0; i < accountList.size(); i++) {
							AccountDTO accountInfoDTO = new AccountDTO();
							
							accountInfoDTO = accountList.get(i);
							
							System.out.println("balance :: " + balance);
							System.out.println("account_balance :: " + accountInfoDTO.getAccount_price());
							
							if(accountInfoDTO.getAccount_type().equals("0")) {
								balance += Integer.parseInt(accountInfoDTO.getAccount_price()); 
							}else {
								balance -= Integer.parseInt(accountInfoDTO.getAccount_price());
							}
							
							accountInfoDTO.setAccount_balance(balance + "");
							
							testService.updateAccount(accountInfoDTO);
							
							System.out.println("resultBalance :: " + balance);
						}
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		@RequestMapping("/updateWorkerSex")
		@ResponseBody
		@Transactional(propagation = Propagation.SUPPORTS) 
		public void updateWorkerSex(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			TransactionStatus status = transactionManager.getTransaction(def);
			
			try {
				List<WorkerDTO> workerList = testService.selectWorkerList();
				
				for(int i = 0; i < workerList.size(); i++) {
					String worker_sex = "";
					
					WorkerDTO workerDTO = new WorkerDTO();
					
					if(workerList.get(i).getWorker_jumin().substring(6, 7).equals("1")) {
						worker_sex = "M";
						
						workerDTO.setWorker_seq(workerList.get(i).getWorker_seq());
						workerDTO.setWorker_sex(worker_sex);
						
						testService.updateWorkerSex(workerDTO);
					}else if(workerList.get(i).getWorker_jumin().substring(6, 7).equals("2")) {
						worker_sex = "F";
						
						workerDTO.setWorker_seq(workerList.get(i).getWorker_seq());
						workerDTO.setWorker_sex(worker_sex);
						testService.updateWorkerSex(workerDTO);
					}
//					String worker_sex = workerList.get(i).getWorker_jumin().substring(6, 1).equals("1") ? "M" : "F";
//					
//					System.out.println("worker_sex :: " + worker_sex);
//					
//					WorkerDTO workerDTO = new WorkerDTO();
//					
//					workerDTO.setWorker_seq(workerList.get(i).getWorker_seq());
//					workerDTO.setWorker_sex(worker_sex);
				}
				
				transactionManager.commit(status);
			}catch(Exception e) {
				e.printStackTrace();
				
				transactionManager.rollback(status);
			}
		}
		
		@RequestMapping("/updateWorkNewAddr")
		@ResponseBody
		public void updateWorkNewAddr() throws Exception {
			
			List<WorkDTO> workList = testService.selectWorkList();
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			TransactionStatus status = transactionManager.getTransaction(def);
			for(WorkDTO workDTO : workList) {
				if( workDTO.getWork_latlng() != null ) {
					String[] latlng = workDTO.getWork_latlng().split(",");
					String coords = latlng[1] + "," + latlng[0];
					System.out.println("좌표 ::" + coords);
					
					try {
						HttpClient client = HttpClientBuilder.create().build();
						HttpGet getRequest = new HttpGet("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?output=json&orders=addr" + "&coords=" + coords);
						getRequest.setHeader("X-NCP-APIGW-API-KEY-ID", "ehjyv0iw4b");
						getRequest.setHeader("X-NCP-APIGW-API-KEY", "rmv6UK8trG3aVDRyuqjNhnvtP9ReJGgWEmw0lmMF");

						HttpResponse response = client.execute(getRequest);

						//Response 출력
						if (response.getStatusLine().getStatusCode() == 200) {
							ResponseHandler<String> handler = new BasicResponseHandler();
							String body = handler.handleResponse(response);

							ObjectMapper mapper = new ObjectMapper();
							JsonNode bodyJson = mapper.readTree(body);
							
							workDTO.setWork_sido(bodyJson.findValue("results").findValue("region").findValue("area1").findValue("name").toString().replaceAll("\"", ""));	//시,도
							workDTO.setWork_sigugun(bodyJson.findValue("results").findValue("region").findValue("area2").findValue("name").toString().replaceAll("\"", ""));	//시,군,구
							workDTO.setWork_dongmyun(bodyJson.findValue("results").findValue("region").findValue("area3").findValue("name").toString().replaceAll("\"", ""));//읍,면,동
							
							String rest = "";
							if( !"".equals(bodyJson.findValue("results").findValue("land").findValue("number1").toString().replaceAll("\"", "")) ) {
								rest = bodyJson.findValue("results").findValue("land").findValue("number1").toString().replaceAll("\"", "");
							}
							
							if( !"".equals(bodyJson.findValue("results").findValue("land").findValue("number2").toString().replaceAll("\"", "")) ) {
								rest += "-" + bodyJson.findValue("results").findValue("land").findValue("number2").toString().replaceAll("\"", "");
							}
									
							
							workDTO.setWork_rest(rest);
							
							testService.updateNewAddr(workDTO);
						} else {
							System.out.println("response is error: " + response.getStatusLine().getStatusCode());
						}
						
					} catch (Exception e) {
						transactionManager.rollback(status);
						e.printStackTrace();
					}
				}
			}
			transactionManager.commit(status);
		}
		
		@RequestMapping("/updateIlboNewAddr")
		@ResponseBody
		@Transactional(propagation = Propagation.SUPPORTS) 
		public void updateIlboNewAddr() throws Exception {
			
			List<IlboDTO> ilboList = testService.selectIlboList();
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			TransactionStatus status = transactionManager.getTransaction(def);
			for(IlboDTO ilboDTO : ilboList) {
				if( ilboDTO.getIlbo_work_latlng() != null ) {
					String[] latlng = ilboDTO.getIlbo_work_latlng().split(",");
					String coords = latlng[1] + "," + latlng[0];
					System.out.println("좌표 ::" + coords);
					
					try {
						HttpClient client = HttpClientBuilder.create().build();
						HttpGet getRequest = new HttpGet("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?output=json&orders=addr" + "&coords=" + coords);
						getRequest.setHeader("X-NCP-APIGW-API-KEY-ID", "ehjyv0iw4b");
						getRequest.setHeader("X-NCP-APIGW-API-KEY", "rmv6UK8trG3aVDRyuqjNhnvtP9ReJGgWEmw0lmMF");

						HttpResponse response = client.execute(getRequest);

						//Response 출력
						if (response.getStatusLine().getStatusCode() == 200) {
							ResponseHandler<String> handler = new BasicResponseHandler();
							String body = handler.handleResponse(response);

							ObjectMapper mapper = new ObjectMapper();
							JsonNode bodyJson = mapper.readTree(body);
							
							ilboDTO.setWork_sido(bodyJson.findValue("results").findValue("region").findValue("area1").findValue("name").toString().replaceAll("\"", ""));	//시,도
							ilboDTO.setWork_sigugun(bodyJson.findValue("results").findValue("region").findValue("area2").findValue("name").toString().replaceAll("\"", ""));	//시,군,구
							ilboDTO.setWork_dongmyun(bodyJson.findValue("results").findValue("region").findValue("area3").findValue("name").toString().replaceAll("\"", ""));//읍,면,동
							
							String rest = "";
							if( !"".equals(bodyJson.findValue("results").findValue("land").findValue("number1").toString().replaceAll("\"", "")) ) {
								rest = bodyJson.findValue("results").findValue("land").findValue("number1").toString().replaceAll("\"", "");
							}
							
							if( !"".equals(bodyJson.findValue("results").findValue("land").findValue("number2").toString().replaceAll("\"", "")) ) {
								rest += "-" + bodyJson.findValue("results").findValue("land").findValue("number2").toString().replaceAll("\"", "");
							}
									
							
							ilboDTO.setWork_rest(rest);
							
							testService.updateNewAddrIlbo(ilboDTO);
						} else {
							System.out.println("response is error: " + response.getStatusLine().getStatusCode());
						}
							
					} catch (Exception e) {
						transactionManager.rollback(status);
						e.printStackTrace();
					}
				}
			}
			transactionManager.commit(status);
		}
}
