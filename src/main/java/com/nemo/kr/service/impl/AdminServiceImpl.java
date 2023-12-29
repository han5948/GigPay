package com.nemo.kr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.ilgajaComm.util.StringUtil;
import com.nemo.kr.common.Const;
import com.nemo.kr.controller.LoginManager;
import com.nemo.kr.dto.AccountDTO;
import com.nemo.kr.dto.AdminDTO;
import com.nemo.kr.dto.AdminLogDTO;
import com.nemo.kr.dto.CompanyDTO;
import com.nemo.kr.dto.SmsDTO;
import com.nemo.kr.mapper.ilgaja.write.AdminLogMapper;
import com.nemo.kr.mapper.ilgaja.write.AdminMapper;
import com.nemo.kr.mapper.ilgaja.write.CompanyMapper;
import com.nemo.kr.mapper.ilgaja.write.ReservesMapper;
import com.nemo.kr.mapper.sms.SmsMapper;
import com.nemo.kr.service.AdminService;
import com.nemo.kr.util.CommonUtil;


/**
 * 관리자 서비스 구현체
 * @author nemo
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired AdminMapper adminMapper;
	@Autowired SmsMapper smsMapper;
	@Autowired AdminLogMapper adminLogMapper;
	@Autowired ReservesMapper reservesMapper;
	@Autowired CompanyMapper companyMapper;

	@Override
	public Map adminLoginProc(HttpServletRequest request, AdminDTO adminDTO) throws Exception {
		// TODO Auto-generated method stub

		Map resultMap = new HashMap();
		resultMap.put("code", "0000");

		// 관리자정보 조회  (아이디로 조회)
		AdminDTO paramDTO = new AdminDTO();
		paramDTO.setAdmin_id(adminDTO.getAdmin_id());
		paramDTO.setAdmin_pass(adminDTO.getAdmin_pass());           // 2017.04.26 추가
		paramDTO.setUse_yn("1");
		
		AdminDTO result = adminMapper.selectInfo(paramDTO);

		HttpSession session = request.getSession();
		
		if ( result == null ) {
			throw new NoSuchElementException(Const.MSG_USER_0001);
			//return resultMap;
		}
		
		String adminCompanySeq = result.getCompany_seq();
		CompanyDTO companyParam = new CompanyDTO();
		companyParam.setCompany_seq(adminCompanySeq);
		CompanyDTO companyResult = companyMapper.getCompanyInfo(companyParam);
		//계정상태
		if( companyResult.getCompany_status().equals("2") ) {
			throw new AccessDeniedException("귀 지점은 IMS 로그인이 제한되었습니다.");
		}else if( companyResult.getCompany_status().equals("3") ) {
			throw new AccessDeniedException("귀 지점은 적립금부족으로 로그인이 제한되었습니다.");
		}

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setCompany_seq(adminCompanySeq);
		AccountDTO resultAccount = reservesMapper.selectAccountTotalAcc(accountDTO);
		int totalAcc = Integer.parseInt(resultAccount.getTotalAcc());
		
		//최소적립금
		int companyMinReserves = Integer.parseInt(companyResult.getMinimum_reserves());
		if( companyMinReserves > 0 && companyMinReserves > totalAcc ) {
			String minimumReservesMessage = "귀 지점은 최소 유지 적립금이 부족합니다."
					+ "\n적립금>[충전] 접수 후 해당 금액을 입금하여 주세요."
					+ "\n"
					+ "\n▶현재적립금 : " + StringUtil.strComma(totalAcc+"") + "원"
					+ "\n▶최소적립금 : " + StringUtil.strComma(companyMinReserves+"") + "원"
					+ "\n"
					+ "\n※ 입금자명은 반드시 [지점명]으로 기입해 주세요.";
			
			//resultMap.put("code", Const.CODE_USER_0003);
			if( !result.getAuth_level().equals("1") ) {
				throw new AccessDeniedException(minimumReservesMessage);
			}
			
			result.setMinimumReservesFlag("1");
			
			resultMap.put("message", minimumReservesMessage);
			resultMap.put("minimumReservesFlag", "1");
			//return resultMap;
		}else if( !adminCompanySeq.equals("13") && !adminCompanySeq.equals("1") ) {
			//적립금부족 안내 본점, 스마트하우스는 예외
			if( totalAcc <= 0 ) {
				String msg = "귀 지점은 적립금 긴급 충전이 필요한 상태로 로그인이 일시 중지될 수 있습니다. 적립금을 즉시 충전하세요.";
				resultMap.put("message", msg);
			}else if( totalAcc < 2000000 ) {
				// 서울점, 서부점, 세종점, 서부1은 예외
				if( !adminCompanySeq.equals("2") && !adminCompanySeq.equals("14") && !adminCompanySeq.equals("8") && !adminCompanySeq.equals("56") ) {
					String msg = "귀 지점은 최소 유지 적립금을 충족하지 못하고 있습니다. 적립금 충전이 안될 경우 로그인이 중지될 수 있습니다.";
					resultMap.put("message", msg);
				}
			}
		}
		/*
    // 비밀번호 검증
    if ( !adminDTO.getAdminPwd().equals(result.getAdminPwd()) ) {

      resultMap.put("code", Const.CODE_USER_0001);
      resultMap.put("message", Const.MSG_USER_0001);

      return resultMap;
    }
		 */
		LoginManager loginManager = LoginManager.getInstance();

		if ( loginManager.isUsing(adminDTO.getAdmin_id()) ) {
			loginManager.removeSession(adminDTO.getAdmin_id());
			session = request.getSession();
		}

		loginManager.setSession(session, adminDTO.getAdmin_id());

		session.setAttribute(Const.adminSession, result);

		//로그인 로그 쌓기
		//AdminLogDTO adminLogDTO = new AdminLogDTO();
		//adminLogDTO.setAdmin_seq(result.getAdmin_seq());
		//adminLogDTO.setAdmin_id(result.getAdmin_id());
		//adminLogMapper.insertAdminLog(adminLogDTO);
		
		//System.out.println("=========:::"+loginManager.getUserCount());
		//LoginManager.getInstance().setSession(session, result);
		session.removeAttribute("__rsaPrivateKeyADMIN__"); 
		
		return resultMap;
	}

	@Override
	public List<AdminDTO> selectList(AdminDTO adminDTO) {
		// TODO Auto-generated method stub
		return adminMapper.selectList(adminDTO);
	}

	@Override
	public AdminDTO selectInfo(AdminDTO adminDTO) {
		// TODO Auto-generated method stub
		return adminMapper.selectInfo(adminDTO);
	}

	@Override
	public void updateInfo(AdminDTO adminDTO) {
		// TODO Auto-generated method stub
		adminMapper.updateInfo(adminDTO);
	}

	@Override
	public List<AdminDTO> selectCounselorList(AdminDTO adminDTO) {
		// TODO Auto-generated method stub
		return adminMapper.selectCounselorList(adminDTO);
	}

	@Override
	public Map<String, String> getAuthNum(AdminDTO adminDTO)  throws Exception {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap();

		String authNum = "";
		List<AdminDTO> resultList = adminMapper.selectList(adminDTO);

		if ( resultList.size() ==0 ) {
			throw new Exception(Const.CODE_USER_0007+"!;!"+ Const.MSG_USER_0007);
		}else if(resultList.size() > 1) {
			throw new Exception(Const.CODE_USER_0022+"!;!"+ Const.MSG_USER_0022);
		}

		AdminDTO result = resultList.get(0);
		
		authNum = CommonUtil.getOrderNum(4);

		//sms 보내기=====================================
		String message = "[일가자] 소장 인증을 위한 인증번호 : " + authNum;
		SmsDTO smsDTO = new SmsDTO();
		smsDTO.setTr_msg(message);
		smsDTO.setTr_etc1("15");	//쇼장용앱 회원인증
		smsDTO.setTr_phone(adminDTO.getAdmin_phone());
		smsMapper.insertInfo(smsDTO);

		resultMap.put("smsAuth", authNum);
		resultMap.put("adminSeq", result.getAdmin_seq());
		
		
		return resultMap;
	}
		
	@Override
	public int getAdminTotalCnt(AdminDTO adminDTO) {
		// TODO Auto-generated method stub

	    return adminMapper.getAdminTotalCnt(adminDTO);
	}

	@Override
	public List<AdminDTO> getAdminList(AdminDTO adminDTO) {
		// TODO Auto-generated method stub

	    return (List<AdminDTO>) adminMapper.getAdminList(adminDTO);
	}

	@Override
	public void setAdminCell(AdminDTO adminDTO) {
	    // TODO Auto-generated method stub

	    adminMapper.setAdminCell(adminDTO);
	}

	@Override
	public void removeAdminInfo(AdminDTO adminDTO) {
	    // TODO Auto-generated method stub

		adminMapper.removeAdminInfo(adminDTO);
	}

	@Override
	public int selectListCnt(AdminDTO adminDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int duplicationCheckAdminId(AdminDTO adminDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertInfo(AdminDTO adminDTO) {
		// TODO Auto-generated method stub
	}

	@Override
	public void insertIlgajaLog(AdminLogDTO adminLogDTO) {
		// TODO Auto-generated method stub
		AdminLogDTO resultDTO = adminMapper.selectIlgajaMenu(adminLogDTO);
		
		if(resultDTO != null) {
			adminLogDTO.setMenu_code(resultDTO.getMenu_code());
			adminLogDTO.setMenu_name(resultDTO.getMenu_name());
		}
		adminMapper.insertIlgajaLog(adminLogDTO);
	}
}
