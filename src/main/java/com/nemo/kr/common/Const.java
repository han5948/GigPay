package com.nemo.kr.common;


import com.pionnet.conf.Config;


public class Const {
	public static final String ACCESS_INFO = "accessLogInfo";
	public static final String APP_WORKER_INFO = "appWorkerInfo";
	public static final String APP_EMPLOYER_INFO = "appEmployerInfo";

	public static final int PAGE_SIZE = Integer.parseInt(Config.get("page_size"));    // 페이지 사이즈

	public static final int MOBILE_PAGE_SIZE = Integer.parseInt(Config.get("mobile_page_size"));    // 모바일 페이지 사이즈

	public static final String ENC_SESSION_KEY = "encKey";

	public static final String AES256_KEY = "kv281080hi5jm8mhd9u3";

	/**관리자 기본 세션 */
	public static final String adminSession = "ADMIN_SESSION";

	/**구인처 기본 세션 */
	public static final String managerSession = "MANAGER_SESSION";


	/**회원 기본 세션 */
	public static final String userSession = "USER_SESSION";

	public static final String JOBNPARTNER_SEQ = Config.get("JOBNPARTNER_SEQ");


	/**관리자 메뉴코드1 세션 */
	public static final String adminDept1code = "ADMIN_DEPT1_CODE";

	/** 관리자 메뉴코드2 세션 */
	public static final String adminDept2code = "ADMIN_DEPT2_CODE";


	/**정상처리 되었습니다.. : 0000*/
	public static final String MSG_SUCCESS = Config.get("MSG_SUCCESS");
	/**정상처리 되었습니다.. : 0000*/
	public static final String CODE_SUCCESS = Config.get("CODE_SUCCESS");


	/**필수 입력 값이 입력 되지 않았습니다. : 9000*/
	public static final String MSG_INVALID_PARAMETER = Config.get("MSG_INVALID_PARAMETER");
	/**필수 입력 값이 입력 되지 않았습니다. : 9000*/
	public static final String CODE_INVALID_PARAMETER = Config.get("CODE_INVALID_PARAMETER");

	/**지원하지 않는 파라메터 입니다. : 9003*/
	public static final String MSG_NOVALID_PARAMETER = Config.get("MSG_NOVALID_PARAMETER");
	/**지원하지 않는 파라메터 입니다. : 9003*/
	public static final String CODE_NOVALID_PARAMETER = Config.get("CODE_NOVALID_PARAMETER");
	
	/**인증세션이 만료되었습니다. 다시 시도해 주시기 바랍니다.. : 9001*/
	public static final String MSG_INVALID_SESSION_PARAMETER = Config.get("MSG_INVALID_SESSION_PARAMETER");
	/**인증세션이 만료되었습니다. 다시 시도해 주시기 바랍니다.. : 9000*/
	public static final String CODE_INVALID_SESSION_PARAMETER = Config.get("CODE_INVALID_SESSION_PARAMETER");

	/**결과값이 없습니다. : 9002*/
	public static final String MSG_NO_DATA = Config.get("MSG_NO_DATA");
	/**결과값이 없습니다. : 9002*/
	public static final String CODE_NO_DATA = Config.get("CODE_NO_DATA");

	/**시스템 장애입니다. : 9999*/
	public static final String MSG_SYSTEM_ERROR = Config.get("MSG_SYSTEM_ERROR");
	/**시스템 장애입니다. : 9999*/
	public static final String CODE_SYSTEM_ERROR = Config.get("CODE_SYSTEM_ERROR");


	/**아이디 또는 패스워드를 확인하세요. :0001*/
	public static final String MSG_USER_0001 = Config.get("MSG_USER_0001");
	/**아이디 또는 패스워드를 확인하세요. : E_U0001*/
	public static final String CODE_USER_0001 = Config.get("CODE_USER_0001");

	/**다시 로그인 해 주세요. : E_U0002*/
	public static final String MSG_USER_0002 = Config.get("MSG_USER_0002");
	/**중복 로그인으로 로그아웃 처리 됩니다.*/
	public static final String MSG_USER_2002 = Config.get("MSG_USER_2002");
	/**구직자정보가 존재하지않습니다. 로그아웃처리 됩니다.*/
	public static final String MSG_USER_2003 = Config.get("MSG_USER_2003");

	/**다시 로그인 해 주세요. : E_U0002*/
	public static final String CODE_USER_0002 = Config.get("CODE_USER_0002");

	/**로그인 권한이 없습니다.관리자에게 문의하세요. : E_U0003*/
	public static final String MSG_USER_0003 = Config.get("MSG_USER_0003");
	/**로그인 권한이 없습니다.관리자에게 문의하세요. : E_U0003*/
	public static final String CODE_USER_0003 = Config.get("CODE_USER_0003");

	/**해당폰은 이미 회원 가입 되어 있습니다. */
	public static final String MSG_USER_0004 = Config.get("MSG_USER_0004");
	/** 해당폰은 이미 회원 가입 되어 있습니다. */
	public static final String CODE_USER_0004 = Config.get("CODE_USER_0004");

	/** 이미 존재하는 아이디 입니다.*/
	public static final String MSG_USER_0005 = Config.get("MSG_USER_0005");
	/** 이미 존재하는 아이디 입니다.*/
	public static final String CODE_USER_0005 = Config.get("CODE_USER_0005");

	/** 일반 사용자 가입 Key 가 일치 하지 않습니다.*/
	public static final String MSG_USER_0006 = Config.get("MSG_USER_0006");
	/** 일반 사용자 가입 Key 가 일치 하지 않습니다.*/
	public static final String CODE_USER_0006 = Config.get("CODE_USER_0006");


	/** 회원정보가 존재 하지 않습니다.*/
	public static final String MSG_USER_0007 = Config.get("MSG_USER_0007");
	/** 회원정보가 존재 하지 않습니다.*/
	public static final String CODE_USER_0007 = Config.get("CODE_USER_0007");


	/** 현장매니저정보가 존재 하지 않습니다.*/
	public static final String MSG_MANAGER_0007 = Config.get("MSG_MANAGER_0007");
	/** 회원정보가 존재 하지 않습니다.*/
	public static final String CODE_MANAGER_0007 = Config.get("CODE_MANAGER_0007");


	/** 해당 권한이 없습니다.*/
	public static final String MSG_USER_0008 = Config.get("MSG_USER_0008");
	/** 해당 권한이 없습니다.*/
	public static final String CODE_USER_0008 = Config.get("CODE_USER_0008");

	/**회원 정보와 요청 정보가 일치 하지 않습니다.. : 009*/
	public static final String MSG_USER_0009 = Config.get("MSG_USER_0009");
	/**요청 정보가 일치 하지 않습니다.. : 0009*/
	public static final String CODE_USER_0009= Config.get("CODE_USER_0009");



	/** 이미 가입된 email 입니다.*/
	public static final String MSG_USER_0010 = Config.get("MSG_USER_0010");
	/** 이미 가입된 email 입니다.*/
	public static final String CODE_USER_0010 = Config.get("CODE_USER_0010");


	/** 본인 인증을 거치지 않았습니다. */
	public static final String MSG_USER_0011 = Config.get("MSG_USER_0011");
	/** 이미 가입된 email 입니다.*/
	public static final String CODE_USER_0011 = Config.get("CODE_USER_0011");


	/** 로그인 실패5회로 계정이 잠겼습니다.\n내일 다시 시도하거나 관리자에게 문의하세요. */
	public static final String MSG_USER_0012 = Config.get("MSG_USER_0012");
	/** 로그인 실패5회로 계정이 잠겼습니다.\n내일 다시 시도하거나 관리자에게 문의하세요.*/
	public static final String CODE_USER_0012 = Config.get("CODE_USER_0012");

	/** 접속이 가능한 PC가 아닙니다.\n관리자에게 문의하세요. */
	public static final String MSG_USER_0013 = Config.get("MSG_USER_0013");
	/** 접속이 가능한 PC가 아닙니다.\n관리자에게 문의하세요. */
	public static final String CODE_USER_0013 = Config.get("CODE_USER_0013");

	/** 패스워드 변경에 실패 하였습니다. */
	public static final String MSG_USER_0014 = Config.get("MSG_USER_0014");
	/** 패스워드 변경에 실패 하였습니다. */
	public static final String CODE_USER_0014 = Config.get("CODE_USER_0014");

	/** 이미 등록 된 폰 번호 입니다. */
	public static final String MSG_USER_0015 = Config.get("MSG_USER_0015");
	/** 이미 등록 된 폰 번호 입니다. */
	public static final String CODE_USER_0015 = Config.get("CODE_USER_0015");

	/** 일가자 이용 승인된 폰 번호가 아닙니다. */
	public static final String MSG_USER_0016 = Config.get("MSG_USER_0016");
	public static final String CODE_USER_0016 = Config.get("CODE_USER_0016");

	/** 일가자 앱 사용이 승인된 폰번호가 아닙니다. */
	public static final String MSG_USER_0017 = Config.get("MSG_USER_0017");
	public static final String CODE_USER_0017 = Config.get("CODE_USER_0017");


	/** SMS 발송을 실패 하였습니다. */
	public static final String MSG_USER_0018 = Config.get("MSG_USER_0018");
	public static final String CODE_USER_0018 = Config.get("CODE_USER_0018");

	/** 이미 등록된 단말기 입니다.*/
	public static final String MSG_USER_0019 = Config.get("MSG_USER_0019");
	public static final String CODE_USER_0019 = Config.get("CODE_USER_0019");

	/** 단말기가 일치 하지 않습니다. */
	public static final String MSG_USER_0020 = Config.get("MSG_USER_0020");
	public static final String CODE_USER_0020 = Config.get("CODE_USER_0020");

	public static final String MSG_USER_0021 = Config.get("MSG_USER_0021");
	public static final String CODE_USER_0021 = Config.get("CODE_USER_0021");

	/* 중복된 번호 입니다.*/
	public static final String MSG_USER_0022 = Config.get("MSG_USER_0022");
	public static final String CODE_USER_0022 = Config.get("CODE_USER_0022");
	
	public static final String MSG_USER_0023 = Config.get("MSG_USER_0023");
	public static final String CODE_USER_0023 = Config.get("CODE_USER_0023");

	/** 다른 구직자에게  예약 완료 된 작업 입니다.. */
	public static final String MSG_ILBO_0101 = Config.get("MSG_ILBO_0101");
	public static final String CODE_ILBO_0101 = Config.get("CODE_ILBO_0101");

	/** 예약 취소 된 작업 입니다.. */
	public static final String MSG_ILBO_0102 = Config.get("MSG_ILBO_0102");
	public static final String CODE_ILBO_0102 = Config.get("CODE_ILBO_0102");

	/** 사무실에서 예약 취소된  작업 입니다.. */
	public static final String MSG_ILBO_0103 = Config.get("MSG_ILBO_0103");
	public static final String CODE_ILBO_0103 = Config.get("CODE_ILBO_0103");

	/** 시간이 지난 작업 입니다.. */
	public static final String MSG_ILBO_0104 = Config.get("MSG_ILBO_0104");
	public static final String CODE_ILBO_0104 = Config.get("CODE_ILBO_0104");

	/** 다른 작업이 예약 되어 있습니다.\n예약할 수 없습니다.  **/
	public static final String MSG_ILBO_0105 = Config.get("MSG_ILBO_0105");
	public static final String CODE_ILBO_0105 = Config.get("CODE_ILBO_0105");
	
	/** 일자리 주소 설정을 하신 후 사용 하실 수 있습니다. **/
	public static final String MSG_ILBO_0106 = Config.get("MSG_ILBO_0106");
	public static final String CODE_ILBO_0106 = Config.get("CODE_ILBO_0106");
	
	
	/** 일자리 정보가 변경 되었습니다. \n예약하실 수 없습니다. **/
	public static final String MSG_ILBO_0107 = Config.get("MSG_ILBO_0107");
	public static final String CODE_ILBO_0107 = Config.get("CODE_ILBO_0107");
	
	
	/** 이미 요청 접수가 되어 있습니다. */
	public static final String MSG_ILBO_0201 = Config.get("MSG_ILBO_0201");
	public static final String CODE_ILBO_0201 = Config.get("CODE_ILBO_0201");

	/** 현장출발은 당일에만 할 수 있습니다. */
	public static final String MSG_ILBO_0301 = Config.get("MSG_ILBO_0301");
	public static final String CODE_ILBO_0301 = Config.get("CODE_ILBO_0301");
	
	/** 정산 수수료*/
	public static final String work_fees = Config.get("work_fees");
	public static final String worker_fees = Config.get("worker_fees");
	
	public static final String SEARCHOPTION = "SEARCH_OPT";
	
	/*이미지 temp 폴더*/
	public static final String TEMP_IMAGE_PATH = "/data2/ilgajawebdata/temp/";
}















