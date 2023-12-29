<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript"	src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ehjyv0iw4b&submodules=geocoder"></script>
<script type="text/javascript">
//<![CDATA[

	var companySeq = '${ sessionScope.ADMIN_SESSION.company_seq}';
	var authLevel = ${sessionScope.ADMIN_SESSION.auth_level };
	var selectWorkLatLng = '';
	var selectWorkName = '';
	var selectWorkSeq = '';
	var selectEmployerSeq = '';
	var selectEmployerName = '';
	var selectWorkSido = '';
	var selectWorkSigugun = '';
	var selectWorkDongmyun = '';
	var selectWorkManager = '';
	var selectCompanySeq = '';
	var detailSrh = false;
	var jobArray;
	var today = new Date();
	var toDay = getDateString(now);
	returnBool = 0;
	// 코드정보 저장
	var mCode = new Map();
	var oCode = new Object();

	var optJob  = "";                     // 직종, 전문분야 code_type = 400 : gender 0:남자 1:여자 2:기술인력  code_type :800 야간철야 900:장비보유
	var optBank = "";                     // 은행코드       code_type = 600

	var jobLimitCount = '${jobLimitCount }';
	
	$(function() {
		$("#detailSearchForm").hide();	//상세검색 기본적으로 감춘다
		
		$.ajax({
			type: "POST",
			url: "/admin/getJobCodeList",
			data: {"use_yn": "1", "del_yn": "0", "job_rank":1, "pagingYn":"N", "orderBy": "job_kind, job_order"},
			dataType: 'json',
			success: function(JobList) {
				if ( JobList != null && JobList.length > 0 ) {
					var tempJobKind = "1";
					
					jobArray = JobList;
					
					for ( var i = 0; i < JobList.length; i++ ) {
						if(tempJobKind != JobList[i].job_kind){
							optJob  += "<div style='clear: both;width:100%;height:10px;'></div>";
							tempJobKind = JobList[i].job_kind;
						}
							
						optJob  += "<div class=\""+ JobList[i].job_code +" bt\"><a href=\"JavaScript:chkCodeUpdate('job_layer', '<<WORKER_SEQ>>', 'worker_job_code', '"+  JobList[i].job_code +"', 'worker_job_name', '"+  JobList[i].job_name +"');\"> "+ JobList[i].job_name +" </a></div>";
					}
					
					optJob  += "<div style='clear: both;width:100%;height:10px;'></div>";
				}
				
				$.ajax({
					type: "POST",
					url: "/admin/getCommonCodeList",
					data: {"use_yn": "1", "code_type": "600,800,900"},
					dataType: 'json',
					success: function(data) {
						var tempCodeType = "000";
						var tempGender = "0";
							
						if ( data != null && data.length > 0 ) {
							for ( var i = 0; i < data.length; i++ ) {
								if ( data[i].code_type == 800 ) {   // 야간철야
									if(tempCodeType != data[i].code_type){
										tempCodeType = data[i].code_type;
										optJob  += "<div class= bt style='clear: both;width:100%;height:10px;'></div>";
									}
									
									optJob  += "<div class=\""+ data[i].code_value +" bt\"><a href=\"JavaScript:chkCodeUpdate('job_layer', '<<WORKER_SEQ>>', 'worker_job_code', '"+  data[i].code_value +"', 'worker_job_name', '"+  data[i].code_name +"');\"> "+ data[i].code_name +" </a></div>";
								}else if ( data[i].code_type == 900 ) {   // 장비보유
									if(tempCodeType != data[i].code_type){
										tempCodeType =data[i].code_type;
										optJob  += "<div class= bt style='clear: both;width:100%;height:10px;'></div>";
									}
									
									optJob  += "<div class=\""+ data[i].code_value +" bt\"><a href=\"JavaScript:chkCodeUpdate('job_layer', '<<WORKER_SEQ>>', 'worker_job_code', '"+  data[i].code_value +"', 'worker_job_name', '"+  data[i].code_name +"');\"> "+ data[i].code_name +" </a></div>";
			                		 
								}else if ( data[i].code_type == 600 ) {   //  은행코드
									optBank += "<div class=\""+ data[i].code_value +" bt\"><a href=\"JavaScript:chkCodeUpdate('bank_layer', '<<WORKER_SEQ>>', 'worker_bank_code', '"+  data[i].code_value +"', 'worker_bank_name', '"+  data[i].code_name +"');\"> "+  data[i].code_name +" </a></div>";
									bank_opt = ", "+ data[i].code_value +":"+ data[i].code_name +";";
								}
									
								oCode = new Object();
								oCode.bgcolor = data[i].code_bgcolor;
								oCode.color   = data[i].code_color;
									
								mCode.put(data[i].code_value, oCode);
							}
			                 
							optJob  += "<div class= bt style='clear: both;width:100%;height:10px;'></div>";
						}
					},
			 		beforeSend: function(xhr) {
			 			xhr.setRequestHeader("AJAX", true);
			 		},
			 		error: function(e) {
			 			alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
			 		}
				}); // end ajax
			},
 			beforeSend: function(xhr) {
 				xhr.setRequestHeader("AJAX", true);
 			},
 			error: function(e) {
 				alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
 			}
		}); // end ajax
	
		var lastsel;            //편집 sel
		// 사용유무 - 0:미사용 1:사용
		var use_opts = "1:사용;0:미사용";
		// 스캔유무 - 0:미사용 1:사용
		var scan_opts = "0:미스캔;1:스캔";
		// 일가자 APP 사용 유무(login 유무) -  0:승인 1:사용중(login) 2:미사용(logOut) 3:관리자중지  4:승인대기 -> 0:미설치 1:로그인 2:로그아웃', 
		var status_opts = "0:미설치;1:LOGIN;2:LOGOUT";
		// 일가자 작업 예약 상태 0:대기 1:사용 2:사중 3:관중
		//var ilbo_opts = "0:사중;1:예약;2:관중";
		var ilbo_opts = "0:대기;1:사용;2:사중;3:이용중지";
		//일반푸쉬 사용유무 -0:사용자중지 1:사용 2:관리자중지
		var push_opts = "0:미사용;1:사용";
		// 앱설치유무  -0:미설치 1설치
		var app_opts = "0:미설치;1:승인;2:탈퇴;3:이용중지;4:승인대기;5:수수료미납;6:도출자"; //0: 대기(미설치)  1:승인(전체사용가능) 2:APP탈퇴  3:관중(app로그인도 안됨) 4:승인대기 5:수수료미납 6:도출자
		var star_opts	= "0:미평가;6:A;5:B;4:C;3:D;2:E;1:F";	//평가 등급
		var join_route_opts = '${joinRouteOpts}';
	
		// jqGrid 생성
		$("#list").jqGrid({
			url: "/admin/getWorkerList",
			//editurl: "/test/updateTest",                 // 추가, 수정, 삭제 url
			datatype: "json",                               // 로컬그리드이용
			mtype: "POST",
			postData: {
//          	start_reg_date: getDateString(new Date(now.getFullYear(), now.getMonth() - 1, now.getDate())),
//          	end_reg_date: toDay,
				srh_use_yn: 1,
				srh_del_yn: 0,
                page: 1
			},
			//            datatype: "local",
			sortname: 'w.worker_seq',
			sortorder: "desc",
			rowList: [100, 200, 500, 1000],                         // 한페이지에 몇개씩 보여 줄건지?
			height: "100%",                               // 그리드 높이
			width: "auto",
//           autowidth: true,
			rowNum: 200,
			toppager: true,
			pager: '#paging',                            // 네비게이션 도구를 보여줄 div요소
			viewrecords: true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무
			multiselect: true,
			multiboxonly: true,
			caption: "구직자 목록",                        // 그리드타이틀

			rownumbers: true,                                 // 그리드 번호 표시
			rownumWidth: 40,                                   // 번호 표시 너비 (px)
			
			cellEdit: true ,
			cellsubmit: "remote" ,
			cellurl: "/admin/setWorkerInfo",              // 추가, 수정, 삭제 url
			jsonReader: {
				id: "worker_seq",
				repeatitems: false
			},

            // 컬럼명
			colNames: ['구직자 순번', '지점 순번', '지점', '회원번호', '이름', 
						'특징', '소장평가', '환산평점','평점', '평가수', '펑크','출역 횟수', 
						'일보 횟수', '출역', '또가요','완료', '대장','os', '같은번호수', 
						'핸드폰', '주민번호','주민주소', '좌표','지도', 
						'일주소 (모바일)', '좌표 (모바일)',  '전문분야 코드', '전문분야',  '안전교육', 
						/*'안전교육 히든',*/'혈압', '메모', '은행코드 순번', '은행명', 
						'계좌번호', '예금주', '스캔', '신입교육', '주민등록증', '통장사본', 
						'교육이수','구직신청서', '증명서','패스워드', '연락처1', 
						'연락처2', '연락처3', '마지막 출역 일자', '계정관리', 'Login상태', 
						'작업예약상태',  '구직자 푸쉬', '소장 푸쉬', '앱버젼', '가입경로','상태', '등록일시', '등록자', '소유자'],

            // 컬럼별 속성
			colModel: [
				{name: 'worker_seq', index: 'w.worker_seq', key: true, width: 60, hidden: ${sessionScope.ADMIN_SESSION.auth_level eq 0 ? false : true}, search: true, searchoptions: {sopt: ['cn','eq','nc','ge','le']}},
				{name: 'company_seq', index: 'w.company_seq', width: 60, hidden: ${sessionScope.ADMIN_SESSION.auth_level eq 0 ? false : true}, search: true, searchoptions: {sopt: ['cn','eq','nc','ge','le']}},
				<c:choose>
  					<c:when test="${sessionScope.ADMIN_SESSION.auth_level eq 0 || (sessionScope.ADMIN_SESSION.auth_level < 3 && sessionScope.ADMIN_SESSION.company_seq eq 13)}">
                 		{name: 'company_name', index: 'c.company_name', width: 120, align: "left", sortable: true, editable: ${sessionScope.ADMIN_SESSION.auth_level eq 0 ? true : false }
                        	, edittype: "text"
                        	, editrules: {edithidden: true, required: false}
                    		, searchoptions: {sopt: ['cn','eq','nc']}
                     		, editoptions: {
								size: 30,
                            	dataInit: function (e, cm) {
									$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택

									$(e).autocomplete({
//                                             source: "/admin/getCompanyNameList?srh_use_yn=1",
										source: function (request, response) {
											$.ajax({
												url: "/admin/getCompanyNameList", 
												type: "POST", 
												dataType: "json",
												data: { term: request.term, srh_use_yn: 1 },
												success: function (data) {
													response($.map(data, function (item) {
//                                                                                           return { label: item.code + ":" + item.name, value: item.code, id: item.id }
														return item;
													}))
												},
                                                beforeSend: function(xhr) {
                                                	xhr.setRequestHeader("AJAX", true);
												},
                                                error: function(e) {
                                                	if ( data.status == "901" ) {
														location.href = "/admin/login";
													} else {
														alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
													}
												}
											})
										},
										minLength: 1,
                                        focus: function (event, ui) {
											return false;
										},
                                        select: function (event, ui) {
											lastsel = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값

											$(e).val(ui.item.label);

											$("#list").jqGrid('setCell', lastsel, 'company_seq', ui.item.code, '', true);
											
											//Enter가 아니면
							                if( event.keyCode != "13" ){
							                	var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
							                	$("#list").jqGrid("saveCell", iRow, cm.iCol);
							                }

											return false;
										}
									});
                             	}
                        	}
                 		},
  					</c:when>
  					<c:otherwise>
                 		{name: 'company_name', index: 'c.company_name', hidden: false, editable: false, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
  					</c:otherwise>
				</c:choose>
				{name: 'worker_barcode', index: 'worker_barcode', align: "left", width: 100, hidden: true},
                {name: 'worker_name', index: 'worker_name', align: "left", width: 70, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'worker_feature', index: 'worker_feature', align: "left", width: 120, editable: true, sortable: true, edittype: "textarea", searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'worker_rating', index: 'worker_rating', align: "center", width: 70, search: true, searchoptions: {sopt: ['cn','eq','nc','ge','le']}, editable: true, sortable: true, edittype: "select", editoptions: {value: star_opts}, formatter: "select"
					, editrules: {custom:  true, custom_func: validWorkerRating}
                	, cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.worker_rating  == '0' ) {
							return 'style="color: #B300FF; text-align: center;"';
						}
					}
				},
				{name: 'convert_score', index: 'convert_score', width: 70, align: "center", search: true, searchoptions: {sopt: ['cn', 'eq', 'nc', 'ge', 'le']}, sortable: true, formatter: fn_convertScore},
				{name: 'rating_avg', index: 'rating_avg', width: 50, align: "center", formatter: fn_avgRating, search: false},
				{name: 'rating_avg_cnt', index: 'rating_avg_cnt', width: 50, align: "center", search: false},
				{name: 'punk_cnt', index: 'punk_cnt', width: 50, align: "center", search: false},
				{name: 'ilbo_output_count', index: 'ilbo_output_count', hidden: true},
                {name: 'ilbo_total_count', index: 'ilbo_total_count', hidden: true},
                {name: 'ilbo_count', index: 'ilbo_count', align: "left", width: 50, search: false, sortable: false, formatter: formatIlboCount},
                {name: 're_count', index: 're_count', align: "center", width: 50, search: false, editable: false },
                {name: 'complete_count', index: 'complete_count', align: "center", width: 50, search: false, editable: false },
                {name: 'worker_ilbo', index: 'worker_ilbo', align: "left", sortable: false, width: 30, search: false, formatter: formatWorkerIlbo},
                {name: 'os_type', index: 'os_type', align:"center", width: 70, search: true, searchoptions: {sopt: ['eq','nc','cn']}},
                {name: 'phone_ct', index: 'phone_ct', hidden: true},
                {name: 'worker_phone', index: 'worker_phone', align: "left", width: 100, editable: true, sortable: true, 
					editrules: {custom: true, custom_func: validWorkerPhone},
						formatter: formatPhoneStar,
                   		searchoptions: {sopt: ['cn','eq','nc']},
                   		cellattr: function(rowId, tv, rowObject, cm, rdata) {
							if(rowObject.phone_ct > 1){
								return 'style="background: #f9906c" title="' + formatPhoneStar(rowObject.worker_phone, null, rowObject)+'"'; //; color:#"'; 같은 폰번호가 중복으로 있으면
	                	   	}else{
	                          	return 'style="background: #f5ebe8" title="' + formatPhoneStar(rowObject.worker_phone, null, rowObject)+'"'; //; color:#"';
	                	   	}
                     	}
                 },
                 {name: 'worker_jumin', index: 'worker_jumin', align: "left", width: 120, editable: true, sortable: true,
					editrules: {custom: true, custom_func: validWorkerJumin}, editoptions: {maxlength: 14},
					formatter: formatJuminStar,
					searchoptions: {sopt: ['cn','eq','nc']},
                   	editoptions: {value: app_opts,
						dataEvents: [{
							type: 'change',
							fn: function(e) {                // 값 : this.value || e.target.val()
								var sex = this.value.substr(6, 1);     // 성별확인
                                	 
                                if ( sex == 1 || sex == 3 ) {               // 2000년 이전 출생자
                                	$(this).parent().css('background', '#e1ebf1');
								} else if ( sex == 2 || sex == 4 ) {        // 2000년 이후 출생자
                                	$(this).parent().css('background', '#f5ebe8');
								}else{
                                	$(this).parent().css('background', '');
								}
							},
						}]
					}, 
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						if(tv){
							var sex = tv.substr(6, 1);     // 성별확인
		                	   
		                	if ( sex == 1 || sex == 3 ) {               // 2000년 이전 출생자
		                		return 'style="background: #e1ebf1" title="' + formatJuminStar(rowObject.worker_jumin, null, rowObject)+'"'; //; color:#"';
							} else if ( sex == 2 || sex == 4 ) {        // 2000년 이후 출생자
		                	    return 'style="background: #f5ebe8" title="' + formatJuminStar(rowObject.worker_jumin, null, rowObject)+'"'; //; color:#"';
							}
							
							return 'title = "' + formatJuminStar(rowObject.worker_jumin, null, rowObject) + '"';
						}
					}
				},
                {name: 'worker_addr', index: 'worker_addr', align: "left", width: 180, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}}, //, formatter: formatGeocode, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'worker_latlng', index: 'worker_latlng', hidden: true, align: "left", editable: false, sortable: true, search: false},
                {name: 'ilgaja_addr_edit', index: 'ilgaja_addr_edit', align: "left", width: 30, sortable: false, search: false, formatter: formatIlgajaEdit},
                {name: 'worker_ilgaja_addr', index: 'worker_ilgaja_addr', width: 180, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}}, //, formatter: formatGeocode, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'worker_ilgaja_latlng', index: 'worker_ilgaja_latlng', hidden: true},
                {name: 'worker_job_code', index: 'worker_job_code', hidden: true},
                {name: 'worker_job_name', index: 'worker_job_name', align: "center", width: 200, sortable: true, formatter: formatJobCode, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'worker_OSH_yn', index: 'worker_OSH_yn', width: 100, sortable: true, formatter: formatOSH, searchoptions: {sopt: ['cn','eq','nc']}},
                //{name: 'worker_OSH_yn_hidden', index: 'worker_OSH_yn_hidden', hidden: true, searchoptions: {sopt: ['cn','eq','nc']}, sortable: true},
                {name: 'worker_blood_pressure', index: 'worker_blood_pressure', align: "left", width: 40, editable: true, sortable: true,
					editrules: {custom:  true, custom_func: validNum}, editoptions: {maxlength: 3},
                  	searchoptions: {sopt: ['cn','eq','nc']}
                },
                {name: 'worker_memo', index: 'worker_memo', align: "left", editable: true, sortable: true, edittype: "textarea", searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'worker_bank_code', index: 'worker_bank_code', hidden: true},
                {name: 'worker_bank_name', index: 'worker_bank_name', width: 80, sortable: true, formatter: formatBank, searchoptions: {sopt: ['cn','eq','nc']}},
                
                {name: 'worker_bank_account', index: 'worker_bank_account', align: "left", editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'worker_bank_owner', index: 'worker_bank_owner', align: "left", width: 80, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'worker_scan_file', index: 'worker_scan_file', align: "left", width: 200, search: false, sortable: false, formatter: formatScan},
                {name: 'worker_education_yn', index: 'worker_education_yn', align: "center", width: 80, sortable: true, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}, formatter: formatEdu},
 				
                 {name: 'worker_jumin_scan_yn', index: 'worker_jumin_scan_yn', hidden: true},
                 {name: 'worker_bankbook_scan_yn', index: 'worker_bankbook_scan_yn', hidden: true},
                 {name: 'worker_OSH_scan_yn', index: 'worker_OSH_scan_yn', hidden: true},
                 {name: 'worker_job_scan_yn', index: 'worker_job_scan_yn', hidden: true},
                 {name: 'worker_certificate_scan_yn', index: 'worker_certificate_scan_yn', hidden: true},

                 {name: 'worker_pass', index: 'worker_pass', align: "left", width: 150, editable: ${sessionScope.ADMIN_SESSION.auth_level eq 0 ? true : false}},
                 {name: 'worker_tel1', index: 'worker_tel1', align: "left", width: 50, editable: true, sortable: true, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}},
                 {name: 'worker_tel2', index: 'worker_tel2', align: "left", width: 50, editable: true, sortable: true, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}},
                 {name: 'worker_tel3', index: 'worker_tel3', align: "left", width: 50, editable: true, sortable: true, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}},
                 {name: 'ilbo_last_date', index: 'ilbo_last_date', align: "center", width: 100, search: false, editable: false},
                 //앱설치 유무
                 {name: 'worker_app_use_status', index: 'worker_app_use_status', align: "center", width: 80, editable: true, sortable: true, edittype: "select", formatter: "select", searchoptions: {sopt: ['cn','eq','nc']}, hidden: ${sessionScope.ADMIN_SESSION.worker_app eq '1'? 'false' : 'true' },
                	editrules: {custom: true, custom_func: validAppUseStatus}, 
					editoptions: {value: app_opts,
						dataEvents: [{
							type: 'change',
							fn: function(e) {                // 값 : this.value || e.target.val()
								if ( this.value == "0" ) {
									$(this).parent().css('color', '#B300FF');
								} else {
									$(this).parent().css('color', '#0774de');
								}
							},
						}]
					}, 
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( tv == '0' ) {
							return 'style="color: #B300FF; text-align: center;"';
						} else  if ( tv == '1' ) {
							return 'style="color: #0774de; text-align: center;"';  
						}
					}
				},
                // 일가자 APP login 유무 -  0:미설치 1:사용중(login) 2:미사용(logOut)  
                {name: 'worker_app_status', index: 'worker_app_status', align: "center", width: 100, editable: true, sortable: true, edittype: "select", formatter: "select", searchoptions: {sopt: ['cn','eq','nc']},
					editoptions: {value: status_opts,
						dataEvents: [{
							type: 'change',
							fn: function(e) {               // 값 : this.value || e.target.val()
								if ( this.value == "0" ) {
									$(this).parent().css('color', '#B300FF');
								} else if ( this.value == "1" ) {
                                	$(this).parent().css('color', '#0774de');
								} else if ( this.value == "2" ) {
									$(this).parent().css('color', '#cd0a0a');
								} else if ( this.value == "4" ) {
                                	$(this).parent().css('color', '#0ef341');
								} else {
									$(this).parent().css('color', '#000000');
								}
							},
						}]
					},
                    cellattr: function(rowId, tv, rowObject, cm, rdata) {
                    	// rowObject 변수로 그리드 데이터에 접근
                        if ( tv == '0' ) {
                        	return 'style="color: #B300FF; text-align: center;"';
						} else if ( tv == '1' ) {
                        	return 'style="color: #0774de; text-align: center;"';
						} else if ( tv == '2' ) {
                        	return 'style="color: #cd0a0a; text-align: center;"';
						} else if ( tv == '4' ) {
                        	return 'style="color: #0ef341; text-align: center;"';
						}
					}
				},
                {name: 'worker_reserve_push_status', index: 'worker_reserve_push_status', align: "center", width: 100, editable: true, sortable: true, edittype: "select", editoptions: {value: ilbo_opts}, formatter: "select", searchoptions: {sopt: ['cn','eq','nc']}, hidden: true,
					editoptions: {value: ilbo_opts,
						dataEvents: [{
							type: 'change',
							fn: function(e) {                // 값 : this.value || e.target.val()
								if ( this.value == "0" ) {             
									$(this).parent().css('color', '#B300FF');   // 대기
								} else if ( this.value == "1" ) {
									$(this).parent().css('color', '#0774de');   // 사용
								} else if ( this.value == "2" ) {
									$(this).parent().css('color', '#cd0a0a');   // 사중
								} else {
									$(this).parent().css('color', '#000000');   // 관중
								}
							},
						}]
					},
                    cellattr: function(rowId, tv, rowObject, cm, rdata) {
                    	// rowObject 변수로 그리드 데이터에 접근
                        if ( tv == '0' ) {
                        	return 'style="color: #B300FF; text-align: center;"';
						} else if( tv == '1' ) {
                        	return 'style="color: #0774de; text-align: center;"';
						} else if( tv == '2' ) {
                        	return 'style="color: #cd0a0a; text-align: center;"';
						}
					}
				},
                {name: 'worker_push_yn', index: 'worker_push_yn', align: "center", width: 70, editable: false, sortable: true, edittype: "select", formatter: "select",search: false, searchoptions: {sopt: ['cn','eq','nc']}, hidden: false,
					editoptions: {value: push_opts,
						dataEvents: [{
							type: 'change',
							fn: function(e) {               // 값 : this.value || e.target.val()
								if ( this.value == "0" ) {
									$(this).parent().css('color', '#B300FF');   // 보라
								} else  if(this.value == "1" ) {
									$(this).parent().css('color', '#0774de');   // 파랑
								} else {
									$(this).parent().css('color', '#000000');   // 검정
								}
							},
						}]
					},
                    cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( tv == '0' ) {
                        	return 'style="color: #B300FF; text-align: center;"';
						} else if ( tv == '1' ) {
                        	return 'style="color: #0774de; text-align: center;"';
						}
					}
				},
				{name: 'admin_push_yn', index: 'admin_push_yn', align: "center", width: 60, search: false, sortable: false, formatter: "select", editable: true, edittype: "select",
					editoptions: {value: push_opts,
						dataEvents: [{
							type: 'change',
							fn: function(e) {               // 값 : this.value || e.target.val()
								if ( this.value == "0" ) {
									$(this).parent().css('color', '#B300FF');   // 보라
								} else  if(this.value == "1" ) {
									$(this).parent().css('color', '#0774de');   // 파랑
								} else {
									$(this).parent().css('color', '#000000');   // 검정
								}
							},
						}]
					},
                    cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( tv == '0' ) {
                        	return 'style="color: #B300FF; text-align: center;"';
						} else if ( tv == '1' ) {
                        	return 'style="color: #0774de; text-align: center;"';
						}
					}
				},
                {name: 'app_version', index: 'app_version', align: "center", width: 80, search: true, searchoptions: {sopt: ['eq','ge','le', 'cn', 'nc']}, editable: false, sortable: true},
                {name: 'join_route', index: 'join_route', align: "center", width:60, editable: true, edittype: "select", editoptions : {value: join_route_opts}, formatter: "select"
					, stype:"select", searchoptions: {sopt: ['eq'], value: ":전체;" + join_route_opts}
				},
                {name: 'use_yn', index: 'use_yn', align: "center", width: 60, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: use_opts}, formatter: "select", hidden: ${sessionScope.ADMIN_SESSION.worker_app eq '1'? 'false' : 'true' }, 
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.use_yn == '1' ) {
							return 'style="color: #B300FF; text-align: center;"';
						}
					}
				},
                {name: 'reg_date', index: 'reg_date', align: "center", width: 100, search: false, editable: false, sortable:  true, formatoptions: {newformat: "y/m/d H:i"}},
                {name: 'reg_admin',	index: 'w.reg_admin', align: "left", width: 60, search: true, searchoptions: {sopt: ['cn','eq','nc']}, editable: false, sortable: true},
                {name: 'owner_id',	index: 'w.owner_id', align: "left", width: 60, search: true, searchoptions: {sopt: ['cn','eq','nc']}, editable: false, sortable: true}
			],
         	// row 를 선택 했을때 편집 할 수 있도록 한다.
         	onSelectRow: function(id) {
				if ( id && id !== lastsel ) {
					$("#list").jqGrid('restoreRow', lastsel);

                    $("#list").jqGrid('editRow', id, {
						keys: true,
						oneditfunc: function() {
						},
						successfunc: function() {
							lastsel = -1;

							return true;
						},
					});
                    
					lastsel = id;
				}
			},
        	// cell을 클릭 시
			onCellSelect: function(rowid, index, contents, event) {
				//row data 전체 가져 오기
                var list = $("#list").jqGrid('getRowData', rowid);
                selectCompanySeq = $("#list").jqGrid('getRowData', rowid).company_seq;
                lastsel = rowid;
            },
            // submit 전
    		beforeSubmitCell: function(rowid, cellname, value) {
    			var appUseStatus = $("#list").jqGrid('getRowData', rowid).worker_app_use_status;
    			if(cellname == 'company_name' && appUseStatus == '4' && selectCompanySeq == '13' ) {
    				//if(confirm("구직자에게 문자로 알림을 보내시겠습니까?")){
    					return {
    						cellname : cellname,
    						worker_seq : rowid,
    						company_seq : $("#list").jqGrid('getRowData', rowid).company_seq
    						//companyFlag : '1'
    					}	
    				//}
    			}
    			
				if ( cellname == "use_yn" && value == "0" ) {
					$("#"+rowid).hide();
				}
                       
                if(cellname == "worker_ilgaja_addr"){
                	fomateGeocodeNaver(rowid, cellname, value );
					return false;                	
				} 

                if (cellname == "worker_addr" ) {
                	$("#list").jqGrid('setCell', rowid,cellname, value, '', true);  //    바뀐 값을 먼저 cell에 넣어 둔다. 그렇치 않으면 ilgaja_addr_edit 에서 이상하게 인식 한다.
                    $("#list").jqGrid('setCell', rowid,'ilgaja_addr_edit', "1", '', true);  
                            
                    return {
                    	worker_seq: rowid,
					};
				} 
                        
                if (cellname == "worker_rating" ) {
                	return {
                    	cellname : cellname,
                    	worker_seq: rowid
                    };
				}
                
                return {
                	cellname : cellname,
                	worker_seq: rowid,
                	company_seq: $("#list").jqGrid('getRowData', rowid).company_seq, 
				};
			},
			afterSubmitCell: function(serverresponse, rowid, cellname, value, iRow, iCol) {
				var response = (serverresponse.statusText).trim();
				respText = serverresponse.responseText;
				
				if ( response == 'OK' || response == 'success') {
					var _responseJson = JSON.parse(respText);
					if( _responseJson.result == 0000 ){
						return [true,""];
					}
					
					return [false, _responseJson.message];
				}
				
				return [false,respText];
			}, 
        	// Grid 로드 완료 후
        	loadComplete: function(data) {
				//총 개수 표시
                $("#totalRecords").html(numberWithCommas( $("#list").getGridParam("records") ));
                if( $("#list").getGridParam("records") < 1 ){
			    	$("#btnExcel").click(function(){
			        	alert("출력할 결과물이 없습니다.");
					})
				}else{
			    	$("#btnExcel").click(function(){
			        	document.defaultFrm.action = "/admin/workerListExcel";
                        document.defaultFrm.submit();
					})
				}

				isGridLoad = true;
				$(".wrap-loading").hide();
			},
			afterInsertRow: function(rowId, rowdata, rowelem) {	//그리드를 그릴때 호출 되는 구나....
				fn_setEditable(rowId, rowdata.company_seq);
			},
			loadBeforeSend: function(xhr) {
            	isGridLoad = false;
                optHTML = "";
//                        closeOpt();
                xhr.setRequestHeader("AJAX", true);
                        
                $(".wrap-loading").show();
			},
			loadError: function(xhr, status, error) {
            	if ( xhr.status == "901" ) {
                	location.href = "/admin/login";
				}else if( xhr.status == "500" ){
					toastFail("시스템 오류입니다.");
				}
            	$(".wrap-loading").hide();
			},
                      
       		beforeSelectRow: selectRowid                
		});

  		$("#list").jqGrid('navGrid', "#paging", {edit: false, add: false, del: false, search: false, refresh: false, position: 'left'});
  		$("#list").jqGrid('filterToolbar', {searchOperators : true});
  
  		//헤더툴팁
  		setTooltipsOnColumnHeader($("#list"), 7, "A:6 B:5 C:4 D:3 E:2 F:1 미평가:0");
/*
  // jqgrid caption 클릭 시 접기/펼치기 기능
  $("div.ui-jqgrid-titlebar").click(function () {
    $("div.ui-jqgrid-titlebar a").trigger("click");
  });
*/
	
	function validAppUseStatus(appUseStatus, nm, valref){
		var _isChk = true; 	
		if( appUseStatus == 1 ){
			var _lastsel  = $("#list").jqGrid('getGridParam', "selrow" );
			var _worker_jumin = $("#list").jqGrid('getRowData', lastsel).worker_jumin; 
			var _worker_bank_code = $("#list").jqGrid('getRowData', lastsel).worker_bank_code;
			var _worker_bank_owner = $("#list").jqGrid('getRowData', lastsel).worker_bank_owner;
			var _worker_bank_account = $("#list").jqGrid('getRowData', lastsel).worker_bank_account;
			var _worker_jumin_scan_yn = $("#list").jqGrid('getRowData', lastsel).worker_jumin_scan_yn;
			var _worker_bankbook_scan_yn = $("#list").jqGrid('getRowData', lastsel).worker_bankbook_scan_yn;
			
			var juminValid = validWorkerJumin(replaceAll(_worker_jumin, '-', ''), null, null);
			
			if( _worker_jumin == "" || _worker_jumin.length != '14' && !juminValid[0]){
				//주민번호 입력확인
				_isChk = false;
			}else if( _worker_bank_code == "" || _worker_bank_owner == "" || _worker_bank_account == "" ){
				//계좌정보 입력확인
				_isChk = false;
			}else if( _worker_jumin_scan_yn == "0" ){
				//신분증 사본
				_isChk = false;
			}else if( _worker_bankbook_scan_yn == "0" ){
				//통장사본
				_isChk = false;
			}
		}
		
		if( _isChk ){
			return [true,""];	
		}else{
			return [false, "주민번호, 계좌번호, 첨부파일 등 필수 항목 등록 후 승인 가능합니다."];
		}
		
	}
	function smsPopAdd(tr_etc1) {
		var smsPopFrm = $("<form></form>");
		smsPopFrm.attr("name", "smsPopFrm");
		smsPopFrm.attr("id", "smsPopFrm");
		smsPopFrm.attr("method", "post");
		smsPopFrm.attr("enctype", "multipart/form-data");
		
		smsPopFrm.appendTo('body');
		
		var params = new Array();
		var idArray = $("#list").jqGrid('getDataIDs');
		var rowData;
		var paramData;
		for(var i = 0; i < idArray.length; i++) {
			if($("input:checkbox[id=jqg_list_" + idArray[i] + "]").is(":checked")) {
				rowData = $("#list").getRowData(idArray[i]);
				
				if((tr_etc1 == '7' && rowData.worker_phone.replace(/\s/g, "").length != 0 && rowData.ilbo_worker_phone != '?')) { 
					paramData = {
						worker_name : rowData.worker_name, 
						worker_phone : rowData.worker_phone,
						worker_seq : rowData.worker_seq,
						company_name : rowData.company_name,
						menuFlag : "W"
					}
					
					params.push(paramData);
				}
			}
		}
		
		$("#smsPopFrm").append('<input type="hidden" name="tr_etc1" value="' + tr_etc1 + '" />');
		$("#smsPopFrm").append('<input type="hidden" name="menuFlag" value="W" />');
		
		return params;
	}

	//구직자 SMS
	$("#btnWSMS").on("click", function() {
		$("#smsPopFrm").remove();
		
		var params = smsPopAdd('7');
		
	  	if(params.length == 0) {
	  		$.toast({title: '구직자의 번호가 없거나 선택된 구직자가 없습니다.', position: 'middle', backgroundColor: '#ef7220', duration:1000 });
		}else {
			var text = '';
			
			text += "<input type='hidden' name='data' value='" + JSON.stringify(params) + "' />";
			
			$("#smsPopFrm").append(text);
			
			$("#smsPopFrm").one("submit", function() {
				var option = 'width=1400, height=800, top=250, left=600, resizable=no, status=no, menubar=no, toolbar=no, location=no';
			
				window.open('','pop_target',option);
			 
				this.action = '/admin/sms/smsWrite';
				this.method = 'POST';
				this.target = 'pop_target';
			}).trigger("submit");	 
		}
	});
	
  	// 행 추가
  	$("#btnAdd").click( function() {
    	var params = "";

    	$.ajax({
            type: "POST",
            url: "/admin/setWorkerCell/",
            data: params,
        	dataType: 'json',
         	success: function(data) {
				$("#list").trigger("reloadGrid");
//                    $("#list").addRowData(seq, {}, "first");
         	},
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				if ( data.status == "901" ) {
					location.href = "/admin/login";
				} else {
					alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			}
		});
	});

	// 행 삭제
	$("#btnDel").click( function() {
		var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
		
		if( recs.length < 1 ){
			alert("최소 1개 이상 선택하셔야합니다.");
			return;
		}
		
		for (var i = 0; i < recs.length; i++) { //row id수만큼 실행    
			if(!checkMyWorker(recs[i])){
				alert("다른지점 구직자가 포함되어 삭제 할 수 없습니다.");
				return;
			}
		}
		
		if(!confirm("선택사항을 삭제하시겠습니까?")){
			return;
		}
		
		//var params = "sel_worker_seq=" + recs;
		var params = {
			sel_worker_seq : recs	
		};
		var rows = recs.length;
	
		$.ajax({
			type: "POST",
			url: "/admin/removeWorkerInfo",
			data: params,
			dataType: 'json',
			success: function(data) {
				$("#list").trigger("reloadGrid");
			},
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				if ( data.status == "901" ) {
					location.href = "/admin/login";
				} else {
					alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			}
	    });
	});

  	//새로고침
  	$("#btnRel").click( function() {
	    $("#list").trigger("reloadGrid");                       // 그리드 다시그리기
  	});
  
  	//알림 팝업
  	$("#btnAlim").on("click", function() {
		var params = new Array();
	  	var idArray = $("#list").jqGrid('getDataIDs');
	  	var rowData;
	  	for(var i = 0; i < idArray.length; i++) {
			if($("input:checkbox[id=jqg_list_" + idArray[i] + "]").is(":checked")) {
				rowData = $("#list").getRowData(idArray[i]);
			  
			  	params.push(rowData);
		  	}
	  	}
	  
		if(params.length == 0) {
			$.toast({title: '구직자를 선택해주세요.', position: 'middle', backgroundColor: '#ef7220', duration:1000 });
				
			return false;
		}
	  
	  	for(var i = 0; i < params.length; i++) {
		  	text = '<input type="hidden" name="worker_seq" value="' + params[i].worker_seq + '">';
		  	text += '<input type="hidden" name="worker_seq_array" value="' + params[i].worker_seq + '">';
		  
		  	$("#alimPopFrm").append(text);
	  	}
		  
	  	$("#alimPopFrm").one("submit", function() {
		 	var option = 'width=1200, height=800, top=300, left=200, resizable=no, status=no, menubar=no, toolbar=no, scrollbars=no, location=no';
		 
		 	window.open('','pop_target',option);
		 
		 	this.action = '/admin/alim/alimWrite';
		 	this.method = 'POST';
		 	this.target = 'pop_target';
	  	}).trigger("submit");
	  
	  	$("#alimPopFrm input[name=worker_seq]").remove();
	  	$("#alimPopFrm input[name=worker_seq_array]").remove();
  	});

	//일자리 알림
	$("#btnJobAlim").on("click", function() {
		if(detailSrh && $("input[name=worker_job_kind]:checked").length == '0') {
			toastFail("상세검색의 직종을 선택해주세요.");
			
			return false;
		}
		
		var params = new Array();
		var idArray = $("#list").jqGrid('getDataIDs');
		var rowData;
		var checkArr = $("#list").jqGrid('getGridParam', "selarrrow" );
		
		if(checkArr.length == 0) {
			alert("구직자 선택 후 발송 가능합니다.");
			
			return false;
		}
		
		for(var i = 0; i < idArray.length; i++) {
			if($("input:checkbox[id=jqg_list_" + idArray[i] + "]").is(":checked")) {
				if($("#list").getRowData(idArray[i]).worker_name) {
					rowData = $("#list").getRowData(idArray[i]);
				}
		  
				params.push(rowData);
			}
		}
		
		if(params.length == 0) {
			
			var _beforePostData = $("#list").jqGrid("getGridParam", "postData");
		
			var _searchJobName = $("input:radio[name='worker_job_kind'][value="+_beforePostData.search_job_kind+"]").next().text();
			var _searchWorkerAlias = "반장님";
			if(_beforePostData.search_worker_sex == 'F'){
				_searchWorkerAlias = "여사님";
			}
			
			$("#jobAlimPopFrm").append('<input type="hidden" name="queryFlag" value="W" />');
			
			var text = '';
			
			text += '<input type="hidden" name="workFlag" id="workFlag" value="">';
			text += '<input type="hidden" name="employer_seq" id="employer_seq" value="">';
			text += '<input type="hidden" name="employer_name" id="employer_name" value="">';
			text += '<input type="hidden" name="work_seq" id="work_seq" value="">';
			text += '<input type="hidden" name="work_name" id="work_name" value="">';
			text += '<input type="hidden" name="work_sido" id="work_sido" value="">';
			text += '<input type="hidden" name="work_sigugun" id="work_sigugun" value="">';
			text += '<input type="hidden" name="work_dongmyun" id="work_dongmyun" value="">';
			text += '<input type="hidden" name="work_manager" id="work_manager" value="">';
			text += '<input type="hidden" name="job_name" id="job_name" value="' + _searchJobName + '">';
			text += '<input type="hidden" name="worker_alias" id="worker_alias" value="' + _searchWorkerAlias + '">';
			
			$("#jobAlimPopFrm").append(text);
			 
			if(selectWorkLatLng != '') {
				$("#workFlag").val('0');
			}else {
				$("#workFlag").val('1');
			}
			
			$("#employer_seq").val(selectEmployerSeq);
			$("#employer_name").val(selectEmployerName);
			$("#work_seq").val(selectWorkSeq);
			$("#work_name").val(selectWorkName);
			$("#work_sido").val(selectWorkSido);
			$("#work_sigugun").val(selectWorkSigugun);
			$("#work_dongmyun").val(selectWorkDongmyun);
			$("#work_manager").val(selectWorkManager);
			
			$("#jobAlimPopFrm").one("submit", function() {
				var option = 'width=700, height=400, top=300, left=250, resizable=no, status=no, menubar=no, toolbar=no, scrollbars=no, location=no';
			
				window.open('','pop_target',option);
			 
				this.action = '/admin/jobAlim/jobAlimWrite';
				this.method = 'POST';
				this.target = 'pop_target';
			}).trigger("submit");	 
			
			$("input[name=queryFlag]").remove();
			$("input[name=workFlag]").remove();
			$("input[name=employer_seq]").remove();
			$("input[name=employer_name]").remove();
			$("input[name=work_seq]").remove();
			$("input[name=work_name]").remove();
			$("input[name=work_sido]").remove();
			$("input[name=work_sigugun]").remove();
			$("input[name=work_dongmyun]").remove();
			$("input[name=work_manager]").remove();
			$("input[name=job_name]").remove();
			$("input[name=worker_alias]").remove();
		}else {
			fn_writeReady(params);
		}
	});
	
	$("#workIlboBtn").on("click", function() {
		if(selectWorkSeq) {
			ilboView('i.work_seq', selectWorkSeq, '/admin/workIlbo', '구인대장');
		} 
	});
	
	//ajax 처리 시간 때문에 나눈 function
	function fn_writeReady(params) {
		
		var _beforePostData = $("#list").jqGrid("getGridParam", "postData");
		
		var _searchJobName = $("input:radio[name='worker_job_kind'][value="+_beforePostData.search_job_kind+"]").next().text();
		var _searchWorkerAlias = "반장님";
		if(_beforePostData.search_worker_sex == 'F'){
			_searchWorkerAlias = "여사님";
		}
		
		var text = '';
		
		for(var i = 0; i < params.length; i++) {
			text = '<input type="hidden" name="worker_seq" value="' + params[i].worker_seq + '">';
			text += '<input type="hidden" name="worker_seq_array" value="' + params[i].worker_seq + '">';
		  
			$("#jobAlimPopFrm").append(text);
		}
		 
		text = '<input type="hidden" name="worker_length" value="' + params.length + '">';
		text += '<input type="hidden" name="workFlag" id="workFlag" value="">';
		text += '<input type="hidden" name="employer_seq" id="employer_seq" value="">';
		text += '<input type="hidden" name="employer_name" id="employer_name" value="">';
		text += '<input type="hidden" name="work_seq" id="work_seq" value="">';
		text += '<input type="hidden" name="work_name" id="work_name" value="">';
		text += '<input type="hidden" name="work_sido" id="work_sido" value="">';
		text += '<input type="hidden" name="work_sigugun" id="work_sigugun" value="">';
		text += '<input type="hidden" name="work_dongmyun" id="work_dongmyun" value="">';
		text += '<input type="hidden" name="work_manager" id="work_manager" value="">';
		text += '<input type="hidden" name="job_name" id="job_name" value="' + _searchJobName + '">';
		text += '<input type="hidden" name="worker_alias" id="worker_alias" value="' + _searchWorkerAlias + '">';
		
		$("#jobAlimPopFrm").append(text);
		 
		if(selectWorkLatLng != '') {
			$("#workFlag").val('0');
		}else {
			$("#workFlag").val('1');
		}
		
		$("#employer_seq").val(selectEmployerSeq);
		$("#employer_name").val(selectEmployerName);
		$("#work_seq").val(selectWorkSeq);
		$("#work_name").val(selectWorkName);
		$("#work_sido").val(selectWorkSido);
		$("#work_sigugun").val(selectWorkSigugun);
		$("#work_dongmyun").val(selectWorkDongmyun);
		$("#work_manager").val(selectWorkManager);
		
		$("#jobAlimPopFrm").one("submit", function() {
			var option = 'width=700, height=500, top=0, left=0, resizable=no, status=no, menubar=no, toolbar=no, scrollbars=no, location=no';
		
			window.open('','pop_target',option);
		 
			this.action = '/admin/jobAlim/jobAlimWrite';
			this.method = 'POST';
			this.target = 'pop_target';
		}).trigger("submit");	  
		 
		$("input[name=worker_length]").remove();
		$("input[name=worker_seq]").remove();
		$("input[name=worker_seq_array]").remove();
		$("input[name=workFlag]").remove();
		$("input[name=employer_seq]").remove();
		$("input[name=employer_name]").remove();
		$("input[name=work_seq]").remove();
		$("input[name=work_name]").remove();
		$("input[name=work_sido]").remove();
		$("input[name=work_sigugun]").remove();
		$("input[name=work_dongmyun]").remove();
		$("input[name=work_manager]").remove();
		$("input[name=job_name]").remove();
		$("input[name=worker_alias]").remove();
	}
	
  	//검색
  	$("#btnSrh").click( function(e, i, a, b, c) {
    	// 검색어 체크
//    	if ( $("#srh_type option:selected").val() != "" && $("#srh_text").val() == "" ) {
//       		alert('검색어를 입력하세요.');
//       		$("#srh_text").focus();
	
//       		return false;
//     	}

    	$("#list").setGridParam({
//            page: pageNum,
//          rowNum: 15,
			postData: {
				start_reg_date: $("#start_reg_date").val(),
	        	end_reg_date: $("#end_reg_date").val(),
	          	srh_use_yn: $("input:radio[name=srh_use_yn]:checked").val(),
	            srh_type: $("#srh_type option:selected").val(),
	            srh_text: $("#srh_text").val()
	        }
		}).trigger("reloadGrid");

    	lastsel = -1;
  	});
  
  	//상세검색 보이기 안보이기
  	$("#btnSearch").click( function() {
	  	if($("#detailSearchForm").css("display") == "none"){
			$("#detailSearchForm").show();
		} else {
		    $("#detailSearchForm").hide();
		}
  	});
  
  	//상세검색 submit
	$("#btnDetailSrh").click( function() {
		var array_worker_rating = new Array();
		var cnt = 0;
		var chkBool = false;
		
		$('input:checkbox[name="worker_distance"]').each(function() {
	     	if(this.checked == true && selectWorkName == '') {
	     		chkBool = true;
	     		
	     		return;
	      	}
		});
		
		if(chkBool) {
			toastFail("일자리선택을 하신 후 거리를 체크해주세요.");
			
			return false;
		}
		
		if(selectWorkName != '' && $("input:checkbox[name=worker_distance]:checked").length == 0) {
			toastFail("거리를 선택한 후 검색해주세요.");
			
			return false;
		}
		
		if(selectWorkName != '' && $("input:radio[name=worker_job_kind]").length > 0 && $("input:radio[name=worker_job_kind]:checked").length == 0) {
			toastFail("직종을 선택한 후 검색해주세요.");
			
			return false;
		}
		
		$('input:checkbox[name="worker_rating"]').each(function() {
			if(this.checked == true){ 
		 		array_worker_rating[cnt++] = String(this.value);
			}
		});
	 
		var array_worker_age = new Array();
		cnt = 0;
	 
		$('input:checkbox[name="worker_age"]').each(function() {
	     	if(this.checked == true){
				array_worker_age[cnt++] = String(this.value);
	      	}
		});
	 
		var array_job_kind = new Array();
		cnt = 0;
	 
		$('input:radio[name="worker_job_kind"]').each(function() {
			if(this.checked == true){
				array_job_kind[cnt++] = String(this.value);
			}
		});
	
		var array_worker_car = new Array();
		cnt = 0;
	 
		$('input:checkbox[name="worker_car"]').each(function() {
	    	if(this.checked == true){
	    		array_worker_car[cnt++] = String(this.value);
			}
		});

		var search_worker_today = 0; 
		if($('input:checkbox[name="worker_search_today"]').is(":checked")){
	  		search_worker_today =1;
		}	
	
		var search_worker_tommorrow = 0;
		if($('input:checkbox[name="worker_search_tommorrow"]').is(":checked")){
	  		search_worker_tommorrow = 1;
		}
	
		var search_worker_etc = 0;
		if($('input:checkbox[name="worker_job"]').is(":checked")){
	  		search_worker_etc =1;
		}
	
		var search_ilbo_worker =  $('input[name="ilbo_worker"]:checked').val();
		var search_company = $("input[name=worker_company]:checked").val();
		
		var array_worker_latlng = new Array();
		cnt = 0;
		
		$("input:checkbox[name=worker_distance]").each(function() {
			if(this.checked == true) {
				array_worker_latlng[cnt++] = String(this.value);
			}
		});
		
		var worker_osh = $("input[name=worker_osh]:checked").val(); 
		if( worker_osh != 1 ){
			worker_osh = 0;
		}
		
		var worker_sex = $("input[name=worker_sex]:checked").val();
		
		var _data = '';
		
		if(selectWorkName != '') {
			_data = {
		  		detailSearch : 1,
				//search_worker_rating :array_worker_rating.toString(),
				//search_worker_age : array_worker_age.toString(),
				search_job_kind : array_job_kind.toString(),
				search_worker_car : array_worker_car,
				search_worker_today: search_worker_today,
				search_worker_tommorrow : search_worker_tommorrow,
				search_worker_etc: search_worker_etc, 
				search_ilbo_worker:search_ilbo_worker,
				search_company : search_company,
				search_work_latlng : selectWorkLatLng,
				search_worker_latlng : array_worker_latlng,
				search_worker_osh : worker_osh,
				search_worker_sex : worker_sex
			};	
		}else {
	  		_data = {
		  		detailSearch : 1,
				//search_worker_rating :array_worker_rating.toString(),
				//search_worker_age : array_worker_age.toString(),
				search_job_kind : array_job_kind.toString(),
				search_worker_car : array_worker_car,
				search_worker_today: search_worker_today,
				search_worker_tommorrow : search_worker_tommorrow,
				search_worker_etc: search_worker_etc, 
				search_ilbo_worker:search_ilbo_worker,
				search_company : search_company,
				search_worker_osh : worker_osh,
				search_worker_sex : worker_sex
			};
		}
		
		if(array_worker_rating.length > 0) {
			_data.search_worker_rating = array_worker_rating.toString();
		}
		
		if(array_worker_age.length > 0) {
			_data.search_worker_age = array_worker_age.toString();	
		}
		
		var worker_done = $("input[name=worker_done]:checked").val();
		if( worker_done == 1 ){
			_data["search_work_seq"] = selectWorkSeq
		}else{
			_data["search_work_seq"] = "";
		}
		
		// 초기화 전 grid filters 넣어주기
		var beforePostData = $("#list").jqGrid("getGridParam", "postData");
		
		_data.filters = beforePostData.filters;
		_data.sidx = beforePostData.sidx;
		_data.sord = beforePostData.sord;
		_data.page = beforePostData.page;
		_data.rows = beforePostData.rows;
		_data.srh_del_yn = 0;
		_data.srh_use_yn = 1;
		
		$('#list').setGridParam({ postData: ""});
		$("#list").setGridParam({
		  		postData: _data
		}).trigger("reloadGrid");
		lastsel = -1;
		
		detailSrh = true;
	});
});

	function fn_setEditable(rowId, workerCompanySeq){
		if(!checkMyWorker(rowId)){
			$("#list").jqGrid('setCell', rowId,  'worker_name', "", 'not-editable-cell'); // 특정 cell 수정 못하게
			$("#list").jqGrid('setCell', rowId,  'worker_rating', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_phone', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_jumin', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_addr', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_ilgaja_addr', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_blood_pressure', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_feature', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_memo', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_bank_account', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_bank_owner', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_pass', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_tel1', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_tel2', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_tel3', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'worker_app_status', "", 'not-editable-cell');
		}
		
		if(!isSuperAdmin()){
			$("#list").jqGrid('setCell', rowId, 'join_route', "", 'not-editable-cell');	
		}
	}

	// 출역 횟수
	function formatIlboCount(cellvalue, options, rowObject) {
		var str = "";
	
		if ( rowObject.worker_seq > 0 ) {
			if ( rowObject.ilbo_total_count > 0 && (rowObject.ilbo_output_count == rowObject.ilbo_total_count) ) {
				str += "<font color='#D9D9D9'> "+ rowObject.ilbo_output_count +" / "+ rowObject.ilbo_total_count +"</font>";
			} else {
				str += rowObject.ilbo_output_count +" / "+ rowObject.ilbo_total_count;
			}
		}
		
		return str;
	}

	// 구직대장
	function formatWorkerIlbo(cellvalue, options, rowObject) {
		var str = "";
	
		if ( rowObject.worker_seq > 0 ) {
			str += "<div class=\"bt_wrap\">";
			str += "<div class=\"bt_on\"><a href=\"JavaScript:ilboView('i.worker_seq', '"+ rowObject.worker_seq +"', '/admin/workerIlbo', '구직대장'); \"> > </a></div>";
			str += "</div>";
		}
		
		return str;
	}
	
	// 전문분야
	function formatJobCode(cellvalue, options, rowObject) {
		var str = "";
		
		if ( rowObject.worker_seq > 0 ) {
			str += "<div class='bt_wrap'>";
			
			if ( rowObject.worker_job_name != null && rowObject.worker_job_name != "" ) {
			   	str += "<div id=\"div_job_"+ rowObject.worker_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:checkOpt('job_layer', '"+ rowObject.worker_seq +"', '"+ rowObject.worker_job_code+"', event ); return false;\"> "+ rowObject.worker_job_name +" </a></div>";
	 		} else {
			   	str += "<div id=\"div_job_"+ rowObject.worker_seq +"\" class=\"bt\"><a onclick=\"JavaScript:checkOpt('job_layer', '"+ rowObject.worker_seq +"', 0, event); return false;\"> 없음 </a></div>";
	 		}
			 	
			str += "</div>";
		}
		
		return str;
	}
	
	//  안전교육 format
	function formatOSH(cellvalue, options, rowObject) {
		var str = "";
		
		// 건설업 기초안전보건교육 이수 여부 - 0:미이수 1:이수',
		str += "<div class=\"bt_wrap\">";
		str += "<div class=\"bt"+ (cellvalue == "0" ? "_on" : "") +"\"><a href=\"JavaScript:chkUpdate('"+ rowObject.worker_seq +"', 'worker_OSH_yn', '0');\"> 미이수 </a></div>";
		str += "<div class=\"bt"+ (cellvalue == "1" ? "_on" : "") +"\"><a href=\"JavaScript:chkUpdate('"+ rowObject.worker_seq +"', 'worker_OSH_yn', '1');\"> 이수 </a></div>";
		str += "</div>";
		
		return str;
	}

	// 은행코드
	function formatBank(cellvalue, options, rowObject) {
		var str = "";
		
		str += "<div class=\"bt_wrap\">";
		if ( rowObject.worker_bank_code != null && rowObject.worker_bank_code != "" && rowObject.worker_bank_code > 0 ) {
			str += "<div id=\"div_bank_"+ rowObject.worker_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:checkOpt('bank_layer', '"+ rowObject.worker_seq +"', '"+rowObject.worker_bank_code+"', event); return false;\"> "+ rowObject.worker_bank_name +" </a></div>";
		} else {
			str += "<div id=\"div_bank_"+ rowObject.worker_seq +"\" class=\"bt\"><a onclick=\"JavaScript:checkOpt('bank_layer', '"+ rowObject.worker_seq +"', '"+rowObject.worker_bank_code+" ', event); return false;\"> 선택 </a></div>";
		}
		str += "</div>";
		
		return str;
	}

	function formatEdu(cellvalue, options, rowObject){
		return rowObject.worker_education_yn == "1" ? "완료" : "대기";
	}
	// 스캔파일 첨부
	function formatScan(cellvalue, options, rowObject) {
		var str = "";
		str += "<div class=\"bt_wrap\">";
		// 내 사진 등록 여부 - 0:미등록 1: 등록
		str += "<div id=\"MYPHOTO_" + rowObject.worker_seq + "\" class=\"bt" + (rowObject.worker_myphoto_scan_yn == "1" ? "_on" : "") + "\"><a style='width: 25px;' href=\" " + (rowObject.worker_myphoto_scan_yn == "1" ? "javascript:popupImage('MYPHOTO', '" + rowObject.worker_seq + "');" : "#none") + "\">사</a></div>";
		// 주민등록증 스캔 여부 - 0:미스캔 1:스캔'
		str += "<div id=\"JUMIN_"+ rowObject.worker_seq +"\" class=\"bt"+ (rowObject.worker_jumin_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.worker_jumin_scan_yn == "1" ? "javascript:popupImage('JUMIN','"+ rowObject.worker_seq+"');" : "#none")  +" \"> 주 </a></div>";
		// 통장사본 스캔 여부 - 0:미스캔 1:스캔'
		str += "<div id=\"BANK_"+ rowObject.worker_seq +"\" class=\"bt"+ (rowObject.worker_bankbook_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.worker_bankbook_scan_yn == "1" ? "javascript:popupImage('BANK','"+ rowObject.worker_seq+"');" : "#none")  +" \"> 통 </a></div>";
		// 안전교육증 스캔 여부 - 0:미스캔 1:스캔'
		str += "<div id=\"OSH_"+ rowObject.worker_seq +"\" class=\"bt"+ (rowObject.worker_OSH_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.worker_OSH_scan_yn == "1" ? "javascript:popupImage('OSH','"+ rowObject.worker_seq+"');" : "#none")  +" \"> 안 </a></div>";
		// 구직신청서 스캔 여부 - 0:미스캔 1:스캔'
		str += "<div id=\"JOB_"+ rowObject.worker_seq +"\" class=\"bt"+ (rowObject.worker_job_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.worker_job_scan_yn == "1" ? "javascript:popupImage('JOB','"+ rowObject.worker_seq+"');" : "#none")  +" \"> 구 </a></div>";
		// 증명서 스캔 여부 - 0:미스캔 1:스캔'
		str += "<div id=\"CERT_"+ rowObject.worker_seq +"\" class=\"bt"+ (rowObject.worker_certificate_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.worker_certificate_scan_yn == "1" ? "javascript:popupImage('CERT','"+ rowObject.worker_seq+"');" : "#none")  +" \"> 증 </a></div>";
		// 파일 첨부
		str += "<div class=\"bt_t1\"><a href=\"JavaScript:scanAdd('"+ rowObject.worker_seq +"', '"+ rowObject.company_seq +"', '"+ options.rowId +"'); \"> + </a></div>";
		str += "</div>";
	
		return str;
	}
/* 
// 상태 format
function formatStatus(cellvalue, options, rowObject) {
	var str = "";
	
	// 일가자 탈퇴 유무 - 0:승인대기 1:승인완료 3:관리자중지 4:사용자중지 5:컨설팅 신청
	str += "<div class=\"bt_wrap\">";
	if ( rowObject.worker_app_status != null && rowObject.worker_app_status != "" ) {
		str += "<div id=\"div_status_"+ rowObject.worker_seq +"\" class=\"bt_on\"><a href=\"JavaScript:selectOpt('status_"+ rowObject.worker_seq +"', '"+ rowObject.worker_seq +"', '구직자를 선택 한 후 ');\"> "+ ((cellvalue == "0") ? "대기" : (cellvalue == "1") ? "승인" : (cellvalue == "3") ? "관중" : (cellvalue == "4") ? "사중" : (cellvalue == "5") ? "컨설팅" : "상태") +" </a></div>";
	} else {
		str += "<div id=\"div_status_"+ rowObject.worker_seq +"\" class=\"bt\"><a href=\"JavaScript:selectOpt('status_"+ rowObject.worker_seq +"', '"+ rowObject.worker_seq +"', '구직자를 선택 한 후 ');\"> 상태 </a></div>";
	}
	str += "</div>";

	return str;
}

// 예약 format
function formatIlbo(cellvalue, options, rowObject) {
	var str = "";
	
	// 일가자 작업 예약 상태 0:본인중지 1:예약받음 2:관리자가 중지 시킴
	str += "<div class=\"bt_wrap\">";
	if ( rowObject.worker_reserve_push_status != null && rowObject.worker_reserve_push_status != "" ) {
	  str += "<div id=\"div_ilbo_"+ rowObject.worker_seq +"\" class=\"bt_on\"><a href=\"JavaScript:selectOpt('ilbo_"+ rowObject.worker_seq +"', '"+ rowObject.worker_seq +"', '구직자를 선택 한 후 ');\"> "+ ((cellvalue == "0") ? "사중" : (cellvalue == "1") ? "예약" : (cellvalue == "2") ? "관중" : "상태") +" </a></div>";
	} else {
	  str += "<div id=\"div_ilbo_"+ rowObject.worker_seq +"\" class=\"bt\"><a href=\"JavaScript:selectOpt('ilbo_"+ rowObject.worker_seq +"', '"+ rowObject.worker_seq +"', '구직자를 선택 한 후 ');\"> 상태 </a></div>";
	}
	str += "</div>";
	
	return str;
}
 
// 앱사용 format
function formatApp(cellvalue, options, rowObject) {
	var str = "";
	
	// 0: 대기(미설치)  1:승인(전체사용가능) 2:APP탈퇴  3:관중(이용기준 위반) 4:승인대기 5:수수료미납  
	str += "<div class=\"bt_wrap\">";
	if ( rowObject.worker_app_use_status != null && rowObject.worker_app_use_status != "" ) {
		str += "<div id=\"div_app_"+ rowObject.worker_seq +"\" class=\"bt_on\"><a href=\"JavaScript:selectOpt('app_"+ rowObject.worker_seq +"', '"+ rowObject.worker_seq +"', '구직자를 선택 한 후 ');\"> "+ ((cellvalue == "-1") ? "미사용" : (cellvalue == "0") ? "대기" : (cellvalue == "1") ? "사용" : (cellvalue == "2") ? "관중" : (cellvalue == "3") ? "사중" : "상태") +" </a></div>";
	} else {
		str += "<div id=\"div_app_"+ rowObject.worker_seq +"\" class=\"bt\"><a href=\"JavaScript:selectOpt('app_"+ rowObject.worker_seq +"', '"+ rowObject.worker_seq +"', '구직자를 선택 한 후 ');\"> 상태 </a></div>";
	}
	str += "</div>";

	return str;
}

// 푸쉬 format
function formatPush(cellvalue, options, rowObject) {
	var str = "";
	
	// 일반푸쉬 사용유무 -0:사용자중지 1:사용 2:관리자중지
	str += "<div class=\"bt_wrap\">";
	if ( rowObject.worker_push_yn != null && rowObject.worker_push_yn != "" ) {
		str += "<div id=\"div_push_"+ rowObject.worker_seq +"\" class=\"bt_on\"><a href=\"JavaScript:selectOpt('push_"+ rowObject.worker_seq +"', '"+ rowObject.worker_seq +"', '구직자를 선택 한 후 ');\"> "+ ((cellvalue == "0") ? "중지" : (cellvalue == "1") ? "사용" : (cellvalue == "2") ? "관중" : "상태") +" </a></div>";
	} else {
		str += "<div id=\"div_push_"+ rowObject.worker_seq +"\" class=\"bt\"><a href=\"JavaScript:selectOpt('push_"+ rowObject.worker_seq +"', '"+ rowObject.worker_seq +"', '구직자를 선택 한 후 ');\"> 상태 </a></div>";
	}
	str += "</div>";
	
	return str;
}
*/

	function formatIlgajaEdit(cellvalue, options, rowObject,juminAddr) {
		var str = "";
		
		str += "<div class=\"bt_wrap\">";
		str += "<div class=\"bt"+ ((rowObject.worker_ilgaja_latlng) ? "_t1" : "") +"\"><a href=\"JavaScript:checkMapWindowOpen('WORKER', '"+ rowObject.worker_seq +"','"+ rowObject.worker_ilgaja_latlng +"','"+ rowObject.worker_addr+"'); \"  title='"+ rowObject.worker_ilgaja_latlng +"'> M </a></div>";
		str += "</div>";
		
		return str;
	}
	
	function checkMapWindowOpen(mode,seq,latlng,addr,comment){
		if(!checkMyWorker(seq)){
			return;
		}
		
		mapWindowOpen(mode,seq,latlng,addr,comment) ;
	}

	//주민 통장 등 이미지 팝업
	function popupImage(serviceCode, serviceSeq) {
		var popupX = (document.body.offsetWidth / 2) - (200 / 2);
		//&nbsp;만들 팝업창 좌우 크기의 1/2 만큼 보정값으로 빼주었음
	
		var popupY= (document.body.offsetHeight / 2) - (300 / 2);
		//&nbsp;만들 팝업창 상하 크기의 1/2 만큼 보정값으로 빼주었음
		
		var param = "vFlag=worker&service_type=WORKER&service_code="+ serviceCode +"&service_seq="+ serviceSeq;
		window.open("/admin/popup/imageCrop?"+ param, 'image', 'width=1100, height=800, toolbar=no, menubar=no, scrollbars=no, resizable=yes, left='+ popupX + ', top='+ popupY);
	}

	// 상태변경
	function chkUpdate(worker_seq, cellName, val) {
		if(!checkMyWorker(worker_seq)){
			return;
		}
		
		var str = '{"worker_seq": '+ worker_seq +', "'+ cellName +'": '+ val +'}';
	
	    var params = jQuery.parseJSON(str);
	    
	    $.ajax({
	    	type: "POST",
	        url: "/admin/setWorkerInfo",
	        data: params,
	     	dataType: 'json',
	      	success: function(data) {
	    		if(cellName == "worker_OSH_yn"){
	            	$("#list").jqGrid('setCell', worker_seq, cellName, val, '', true);  	 
				}else{
	            	$("#list").trigger("reloadGrid"); 
				}
			},
	    	beforeSend: function(xhr) {
	        	xhr.setRequestHeader("AJAX", true);
	        },
	        error: function(e) {
				if ( data.status == "901" ) {
	            	location.href = "/admin/login";
				} else {
	            	alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			}
		});
	}

	//구직/구인대장 화면으로 이동
	function ilboView(type, seq, url, menuText) {
		if(!checkMyWorker(seq)){
			return;
		}
			
		var frmId  = "defaultFrm";    // default Form
		
		var frm      = $("#"+ frmId);
		
		var startIlboDate 	= $("#start_ilbo_date").val();
		var endIlboDate  	= $("#end_ilbo_date").val();
		
		var ilboView	= $("#"+ frmId +" > input[name=ilboView]");        // 대장으로 이동
		var ilboType 	= $("#"+ frmId +" > input[name=srh_ilbo_type]");   // 대장으로 이동
		var ilboSeq  	= $("#"+ frmId +" > input[name=srh_seq]");         // 대장으로 이동
		
		var val = "Y";
		if ( jQuery.type(ilboView.val()) === "undefined" ) {
			frm.append("<input type='hidden' name='ilboView' value='"+ val +"' />");
		} else {
			ilboView.val(val);
		}
		if ( jQuery.type(ilboType.val()) === "undefined" ) {
			frm.append("<input type='hidden' name='srh_ilbo_type' value='"+ type +"' />");
		} else {
			ilboType.val(type);
		}
	
		if ( jQuery.type(ilboSeq.val()) === "undefined" ) {
			frm.append("<input type='hidden' name='srh_seq' value='"+ seq +"' />");
		} else {
			ilboSeq.val(seq);
		}
	
		$("#start_ilbo_date").val("");
		$("#end_ilbo_date").val("");
	
		frm.attr('target', '_blank');
		frm.attr('action', url).submit();
	
		frm.attr('target', '');
		frm.attr('action', '');
	
		$("#start_ilbo_date").val(startIlboDate);
		$("#end_ilbo_date").val(endIlboDate);
		$("#"+ frmId +" > input[name=ilboView]").val("");
	}

	//직종별 출역 횟수
	function getKindCount(obj, workerSeq){
		//이게 되어 야 하는데 .
		if( $(obj).attr("isLog") == 'display'){
			return;	
		}
		
		var _data = {
				worker_seq	: workerSeq
		};
		var _url = "<c:url value='/admin/getKindCount' />";
	
		commonAjax(_url, _data, true, function(data) {
			if (data.code == "0000") {
				var kindList = data.resultList;
				var kindCountHtml = "<div class='tb_contAdd'><dl>";
				for (var i = 0; i < kindList.length; i++) {
					var item = kindList[i];
					
					kindCountHtml += "<dt>"+item.code_name+" <em>"+ item.ct +" 회</em></dt>"
				}
				
				$(obj).parent().parent().append(kindCountHtml);
				
				$(obj).attr("isLog","display");
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						alert(data.message);
					} else {
						toastFail("오류가 발생했습니다.1");
					}
				} else {
					toastFail("오류가 발생했습니다.2");
				}
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.3");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	}

	// 스캔파일 등록 layer popup
	function scanAdd(worker_seq, company_seq, rowId) {
		if(!checkMyWorker(worker_seq)){
			return;
		}
		
		$("#frmPopup input:hidden[name='service_seq']").val(worker_seq);
		  	
		//from 초기화
		$(".upload-name").val("");
	  	$(".upload-hidden").val("");
		
		openIrPopup('popup-layer');
	}

	function checkOpt(layerName, seq, codes, e) {
		if(!checkMyWorker(seq)){
			return;
		}
		
		selectOpt(layerName, seq, codes, e);
	}

	//구직자 폰번호 검사
	function validWorkerPhone(phoneNum, nm, valref) {
		var result = true;
		var resultStr = "";
		
		//폰에서 - 빼기
		var RegNotNum  = /[^0-9]/g;
		phoneNum = phoneNum.replace(RegNotNum,'');
		
		if ( phoneNum == "" ) return [true, ""];
		
		var valiResult = validPhone(phoneNum, null, null);
		if ( !valiResult[0] ) return valiResult;
		
		var str = '{"worker_phone": "'+ phoneNum +'"}';
		
		var params = jQuery.parseJSON(str);
	
		$.ajax({
			type: "POST",
			url: "/admin/chkWorkerPhone",
			data: params,
			async: false,
			dataType: 'json',
			success: function(data) {
				if ( data  ) {//true
					result = true;
	                resultStr = "";
				} else {
	                result = false;
	                resultStr = "이미 등록된 폰 번호 입니다.";
				}
	        },
			beforeSend: function(xhr) {
	        	xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				if ( data.status == "901" ) {
	                location.href = "/admin/login";
				} else {
	                alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			}
		});
	
	 	return [result, resultStr];
	}

	function validWorkerJumin(juminNum, nm, valref) {
		var result = true;
		var resultStr = "";
		
		if ( juminNum == "" ) return [true, ""];
		
		var valiResult = validJumin(juminNum, null, null);
		if ( !valiResult[0] ) return valiResult;
		
		var str = '{"worker_jumin": "'+ juminNum +'"}';
		
		var params = jQuery.parseJSON(str);
	
		$.ajax({
			type: "POST",
			url: "/admin/chkWorkerJumin",
			data: params,
			async: false,
			dataType: 'json',
			success: function(data) {
				if ( data  ) {//true
	                result = true;
	                resultStr = "";
				} else {
	                result = false;
	                resultStr = "이미 등록된 주민등록 번호 입니다.";
				}
	        },
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				if ( data.status == "901" ) {
	                location.href = "/admin/login";
				} else {
	                alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			}
		});
	
		return [result, resultStr];
	}

	//주소변경
	function addrUpdate(rowid,cellname, worker_addr, worker_latlng) {
		var str = '{"worker_seq": '+ rowid +', "worker_ilgaja_addr": "'+ worker_addr +'", "worker_ilgaja_latlng": "'+ worker_latlng +'"}';
		var params = jQuery.parseJSON(str);
	
		$.ajax({
			type: "POST",
			url: "/admin/setWorkerInfo",
			async: false,
			data: params,
			dataType: 'json',
			success: function(data) {
				$("#list").jqGrid('setCell', rowid,'worker_ilgaja_addr', (worker_addr == "")?null:worker_addr, '', true);  
				$("#list").jqGrid('setCell', rowid,'worker_ilgaja_latlng', (worker_latlng == "")?null:worker_latlng, '', true);  
	
	            //addr_edit 의 상태 변화를 위해 아무값이나 넣어서 fometter  를 동작
				$("#list").jqGrid('setCell', rowid,'ilgaja_addr_edit', "1", '', true);  
			},
	 		beforeSend: function(xhr) {
	            xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
	            if ( data.status == "901" ) {
	            	location.href = "/admin/login";
				} else {
	                alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			}
		}); 
	}

	function fomateGeocodeNaver(rowid, cellname, value) {
		var myaddress = value;
		var cAddr = "";
		var cLatlng ="";
		
		if ( myaddress == "" ) {
			addrUpdate(rowid,cellname  , "", "");
		
		  	return;
		}
		
		naver.maps.Service.geocode({address: myaddress}, function(status, response) {
			if ( status !== naver.maps.Service.Status.OK ) {
				return alert(myaddress + '의 검색 결과가 없거나 기타 네트워크 에러');
			}
		  	var result = response.result;
		
		  	if(result.total == 0) {
		  		alert(myaddress + '의 검색 결과가 없습니다.');
		  		
		  		return false; 
		  	}
		  	
		  	cAddr = result.items[0].address;
		  	cLatlng = result.items[0].point.y + "," + result.items[0].point.x
		
		  	addrUpdate(rowid,cellname, cAddr , cLatlng);
		});
	}

	function mapResponse(rowid,cAddr,clatlng) {
		$("#list").jqGrid('setCell', rowid,'worker_ilgaja_addr', cAddr, '', true);  
	  	$("#list").jqGrid('setCell', rowid,'worker_ilgaja_latlng', clatlng, '', true);  
	  	$("#list").jqGrid('setCell', rowid,'ilgaja_addr_edit', '1', '', true);  
	}
//]]>
</script>
<%--
    <div class="title">
      <h3> 구직자 관리 </h3>
    </div>
--%>
	<article>
		<div class="inputUI_simple">
			<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
				<caption>등록일시, 상태, 직접검색 영역</caption>
				<colgroup>
					<col width="110px" />
					<col width="720px" />
					<col width="80px" />
					<col width="300px" />
					<col width="110px" />
					<col width="480px" />
					<col width="*" />
				</colgroup>
				<tr>
					<th scope="row">등록일시</th>
					<td >
						<p class="floatL">
							<input type="text" id="start_reg_date" name="start_reg_date" class="inp-field wid100 datepicker" /> 
							<span class="dateTxt">~</span>
							<input type="text" id="end_reg_date" name="end_reg_date" class="inp-field wid100 datepicker" />
						</p>
						<div class="inputUI_simple">
							<span class="contGp btnCalendar"> 
								<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'all'); $('#btnSrh').trigger('click');" checked="checked" />
								<label for="day_type_1" class="label-radio">전체</label>
								<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'today' ); $('#btnSrh').trigger('click');" />
								<label for="day_type_2" class="label-radio">오늘</label> 
								<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'week'  ); $('#btnSrh').trigger('click');" />
								<label for="day_type_3" class="label-radio">1주일</label> 
								<input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'month' ); $('#btnSrh').trigger('click');" />
								<label for="day_type_4" class="label-radio">1개월</label> 
								<input type="radio" id="day_type_5" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '3month'); $('#btnSrh').trigger('click');" />
								<label for="day_type_5" class="label-radio">3개월</label> 
								<input type="radio" id="day_type_6" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '6month'); $('#btnSrh').trigger('click');" />
								<label for="day_type_6" class="label-radio">6개월</label>
							</span> 
						</div>
					</td>
					<th scope="row" class="linelY pdlM">상태</th>
					<td>
						<input type="radio" id="srh_use_yn_1" name="srh_use_yn"	class="inputJo" value="" />
						<label for="srh_use_yn_1" class="label-radio">전체</label> 
						<input type="radio" id="srh_use_yn_2" name="srh_use_yn" class="inputJo" value="1" checked="checked" />
						<label for="srh_use_yn_2" class="label-radio">사용</label>
						<input type="radio" id="srh_use_yn_3" name="srh_use_yn" class="inputJo" value="0" />
						<label for="srh_use_yn_3" class="label-radio">미사용</label>
					</td>
					<th scope="row" class="linelY pdlM">직접검색</th>
					<td>
						<div class="select-inner">
							<select id="srh_type" name="srh_type"  class="styled02 floatL wid100" onChange="$('#srh_text').focus();">
<!-- 								<option value="">전체</option> -->
								<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
									<option value="c.company_name">지점</option>
								</c:if>
								<option value="w.worker_name">근로자명</option>
								<option value="w.worker_phone">핸드폰</option>
								<option value="w.worker_addr">주소</option>
								<option value="w.worker_jumin">주민번호</option>
								<option value="w.worker_job_name">전문분야</option>
								<option value="w.worker_feature">특징</option>
								<option value="w.worker_memo">메모</option>
							</select>
						</div> 
											
						<input type="text" id="srh_text" name="srh_text" class="inp-field wid200 mglS" onKeyDown="if ( event.keyCode == 13 ) $('#btnSrh').click();" />
						
						<div class="btn-module floatL mglS">
							<a href="#none" id="btnSrh" class="search">검색</a>
						</div>
					</td>
					<td></td>
				</tr>
			</table>
		</div>
	</article>

	<div class="btn-module mgtS mgbS">
		<div class="leftGroup">
			<a href="#none" id="btnAdd" class="btnStyle01">추가</a>
			<a href="#none" id="btnDel" class="btnStyle01">삭제</a>
			<a href="#none" id="btnAlim" class="btnStyle01">메시지Push</a>
			<a href="#none" id="btnJobAlim" class="btnStyle01">일자리Push</a>
	<!-- 		<a href="#none" id="btnWSMS" class="btnStyle01">구직자 SMS</a>  -->
			<a href="#none" id="btnRel"	class="btnStyle05">새로고침</a>
		<c:if test="${ sessionScope.ADMIN_SESSION.auth_level le 1 }">
			<a href="#none" id="btnExcel" class="excel">excel</a>
		</c:if>
			<a href="#none" id="btnSearch" class="btnStyle02">상세검색</a>
		</div>
	
		<div class="leftGroup" style="padding-left: 80px; padding-top: 10px">
			총 :&nbsp;&nbsp;&nbsp; <span id="totalRecords" style="color: #ef0606;"></span>
		</div>
	</div>

	<div class="inputUI_simple" id="detailSearchForm">
		<table class="bd-form" summary="상세검색">
			<caption>상새검색</caption>
			<colgroup>
				<col width="120px" />
				<col width="520px" />
				<col width="110px" />
				<col width="540px" />
				<col width="110px" />
				<col width="*" />
			</colgroup>
			<tr>
				<th scope="row">일자리선택</th>
				<td>
					<input type="text" class="inp-field wid200" placeholder="구인처명/현장명/매니저 전화번호 검색" id="standardSrh">
					<div class="btn-module floatL mglS" style="margin-right: 10px;">
       					<a href="#none" id="btnStandardReset" class="btnStyle05">초기화</a>
     				</div>
     				<input type="checkbox" id="worker_done" name="worker_done" 	class="inputJo" value="1" disabled="disabled" /><label for="worker_done" id="worker_done_label" class="checkTxt">기출역자</label>
     				<div class="btn-module floatR mglS" style="margin-right: 10px;">
       					<a href="#none" id="workIlboBtn" class="btnStyle05" style="width: 35px; background-color: gray;">></a>
     				</div>
				</td>
				<th scope="row" class="linelY">거리</th>
				<td style="width: 693px;">
					<div id="distanceDiv" style="display: none;">
						<input type="checkbox" id="worker_distance_2" name="worker_distance" class="inputJo" value="2" onclick="chkVali(this)" checked="checked" /><label for="worker_distance_2" class="checkTxt" id="worker_distance_2_label">~2km</label>
						<input type="checkbox" id="worker_distance_5" name="worker_distance" class="inputJo" value="5" onclick="chkVali(this)" checked="checked" /><label for="worker_distance_5" class="checkTxt" id="worker_distance_5_label">2~5km</label>
						<input type="checkbox" id="worker_distance_10" name="worker_distance" class="inputJo" value="10" onclick="chkVali(this)" /><label for="worker_distance_10" class="checkTxt" id="worker_distance_10_label">5~10km</label>
						<input type="checkbox" id="worker_distance_20" name="worker_distance" class="inputJo" value="20" onclick="chkVali(this)" /><label for="worker_distance_20" class="checkTxt" id="worker_distance_20_label">10~20km</label>
						<input type="checkbox" id="worker_distance_30" name="worker_distance" class="inputJo" value="30" onclick="chkVali(this)" /><label for="worker_distance_30" class="checkTxt" id="worker_distance_30_label">20~30km</label>
						<input type="checkbox" id="worker_distance_40" name="worker_distance" class="inputJo" value="40" onclick="chkVali(this)" /><label for="worker_distance_40" class="checkTxt" id="worker_distance_40_label">30~40km</label>
						<input type="checkbox" id="worker_distance_50" name="worker_distance" class="inputJo" value="50" onclick="chkVali(this)" /><label for="worker_distance_50" class="checkTxt" id="worker_distance_50_label">40~50km</label>
						<input type="checkbox" id="worker_distance_100" name="worker_distance" class="inputJo" value="100" onclick="chkVali(this)" /><label for="worker_distance_100" class="checkTxt" id="worker_distance_100_label">50~200km</label>
<!-- 					<input type="checkbox" id="worker_distance_0" name="worker_distance" class="inputJo" value="0" onclick="chkVali(this)" /><label for="worker_distance_0" class="checkTxt">100km 초과</label> -->
					</div>
				</td>
				<th scope="row" class="linelY pdlM">기공선택</th>
				<td class="pdlM">
					<input type="checkbox" id="worker_job_1" name="worker_job" 	class="inputJo" value="1" onclick="chkVali(this)" /><label for="worker_job_1" class="checkTxt">보통인부제외</label>
					<input type="checkbox" id="worker_job_kind_7" name="worker_job_kind" class="inputJo" value="800007" /><label for="worker_job_kind_7" class="checkTxt">준기공 검색</label>
				</td>
			</tr>
			<tr>
				<th scope="row">소장평가</th>
				<td class="pdlM">
					<input type="checkbox" id="worker_rating_1" name="worker_rating" 	class="inputJo" value="6" /><label for="worker_rating_1" class="checkTxt">A</label>
					<input type="checkbox" id="worker_rating_2" name="worker_rating" 	class="inputJo" value="5" /><label for="worker_rating_2" class="checkTxt">B</label>
					<input type="checkbox" id="worker_rating_3" name="worker_rating" 	class="inputJo" value="4" /><label for="worker_rating_3" class="checkTxt">C</label>
					<input type="checkbox" id="worker_rating_4" name="worker_rating" 	class="inputJo" value="3" /><label for="worker_rating_4" class="checkTxt">D</label>
					<input type="checkbox" id="worker_rating_5" name="worker_rating" 	class="inputJo" value="2" /><label for="worker_rating_5" class="checkTxt">E</label>
					<input type="checkbox" id="worker_rating_6" name="worker_rating" 	class="inputJo" value="1" /><label for="worker_rating_6" class="checkTxt">F</label>
<!-- 					<input type="checkbox" id="worker_rating_7" name="worker_rating" 	class="inputJo" value="0" /><label for="worker_rating_7" class="checkTxt">미평가</label> -->
				</td>
				<th scope="row" class="linelY pdlM">나이선택</th>
				<td class="pdlM">
					<input type="checkbox" id="worker_age_1" name="worker_age" 	class="inputJo" value="20" /><label for="worker_age_1"	class="checkTxt">20대</label>
					<input type="checkbox" id="worker_age_2" name="worker_age" 	class="inputJo" value="30" /><label for="worker_age_2"	class="checkTxt">30대</label>
					<input type="checkbox" id="worker_age_3" name="worker_age" 	class="inputJo" value="40" /><label for="worker_age_3"	class="checkTxt">40대</label>
					<input type="checkbox" id="worker_age_4" name="worker_age" 	class="inputJo" value="50" /><label for="worker_age_4"	class="checkTxt">50대</label>
					<input type="checkbox" id="worker_age_5" name="worker_age" 	class="inputJo" value="60" /><label for="worker_age_5"	class="checkTxt">60대</label>
					<input type="checkbox" id="worker_age_6" name="worker_age" 	class="inputJo" value="70" /><label for="worker_age_6"	class="checkTxt">70대 이상</label>
				</td>
				<th scope="row" class="linelY">성별</th>
				<td>
					<input type="radio" id="worker_sex_M" name="worker_sex" class="inputJo" value="M" checked="checked" /><label for="worker_sex_M" class="label-radio">남자</label>
					<input type="radio" id="worker_sex_F" name="worker_sex" class="inputJo" value="F" /><label for="worker_sex_F" class="label-radio">여자</label>
					<input type="radio" id="worker_sex_0" name="worker_sex" class="inputJo" value="0" /><label for="worker_sex_0" class="label-radio">구분 안함</label> 
				</td>
			</tr>
			<tr>
				<th scope="row" >검색제외</th>
				<td class="pdlM">
					<input type="checkbox" id="worker_search_today" name="worker_search_today" 	class="inputJo" value="1" /><label for="worker_search_today"	class="checkTxt">오늘출역자제외</label>
					<input type="checkbox" id="worker_search_tommorrow" name="worker_search_tommorrow" 	class="inputJo" value="1" checked="checked" /><label for="worker_search_tommorrow"	class="checkTxt">내일예약자제외</label>
				</td>
				<th scope="row" class="linelY pdlM">직종선택</th>
				<td class="pdlM" id="jobDiv">
<!-- 					<div id="jobDiv" style="display: none;"> -->
<!-- 						<input type="checkbox" id="worker_job_kind_1" name="worker_job_kind" class="inputJo" value="400030" /><label for="worker_job_kind_1" id="worker_job_kind_1_label" class="checkTxt">일반인부</label> -->
<!-- 						<input type="checkbox" id="worker_job_kind_2" name="worker_job_kind" class="inputJo" value="400060" /><label for="worker_job_kind_2" id="worker_job_kind_2_label" class="checkTxt">조공</label> -->
<!-- 						<input type="checkbox" id="worker_job_kind_3" name="worker_job_kind" class="inputJo" value="400136" /><label for="worker_job_kind_3" id="worker_job_kind_3_label" class="checkTxt">양중</label> -->
<!-- 						<input type="checkbox" id="worker_job_kind_4" name="worker_job_kind" class="inputJo" value="400250" /><label for="worker_job_kind_4" id="worker_job_kind_4_label" class="checkTxt">곰방</label> -->
<!-- 						<input type="checkbox" id="worker_job_kind_5" name="worker_job_kind" class="inputJo" value="400016" /><label for="worker_job_kind_5" id="worker_job_kind_5_label" class="checkTxt">이사짐</label> -->
<!-- 						<input type="checkbox" id="worker_job_kind_6" name="worker_job_kind" class="inputJo" value="400158" /><label for="worker_job_kind_6" id="worker_job_kind_6_label" class="checkTxt">물류창고</label> -->
<!-- 					</div> -->
				</td>
				
				<th scope="row" class="linelY pdlM">차량보유</th>
				<td class="pdlM">
					<div>
					<input type="checkbox" id="worker_car_1" name="worker_car" 	class="inputJo" value="900002" /><label for="worker_car_1"	class="checkTxt">3인트럭</label>
					<input type="checkbox" id="worker_car_2" name="worker_car" 	class="inputJo" value="900003" /><label for="worker_car_2"	class="checkTxt">5인승용</label>
					<input type="checkbox" id="worker_car_3" name="worker_car" 	class="inputJo" value="900004" /><label for="worker_car_3"	class="checkTxt">7인승합</label>
					<input type="checkbox" id="worker_car_4" name="worker_car" 	class="inputJo" value="900005" /><label for="worker_car_4"	class="checkTxt">9인봉고</label>
					<input type="checkbox" id="worker_car_5" name="worker_car" 	class="inputJo" value="900006" /><label for="worker_car_5"	class="checkTxt">오토바이</label>
					</div>
					<div>
					<input type="checkbox" id="worker_car_6" name="worker_car" 	class="inputJo" value="900007" /><label for="worker_car_6"	class="checkTxt">카풀제공가능</label>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row" class="linelY pdlM">구직신청자</th>
				<td>
					<input type="radio" id="ilbo_worker_1" name="ilbo_worker"	class="inputJo" value="0" 	checked="checked" /><label for="ilbo_worker_1"	class="label-radio">미사용</label> 
					<input type="radio" id="ilbo_worker_2" name="ilbo_worker" class="inputJo" value="1" /><label for="ilbo_worker_2" class="label-radio">오늘신청자</label>
					<input type="radio" id="ilbo_worker_3" name="ilbo_worker" class="inputJo" value="2" /><label for="ilbo_worker_3" class="label-radio">지금신청자</label>
					<input type="radio" id="ilbo_worker_4" name="ilbo_worker" class="inputJo" value="3" /><label for="ilbo_worker_4" class="label-radio">내일신청자</label>
				</td>
				<th scope="row" class="linelY pdlM">지점선택</th>
				<td>
					<input type="radio" id="worker_company_0" name="worker_company" class="inputJo" value="1" checked="checked" /><label for="worker_company_0" class="label-radio">내지점</label>
					<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq '0' || sessionScope.ADMIN_SESSION.share_yn eq '1'}">
						<input type="radio" id="worker_company_1" name="worker_company" class="inputJo" value="2" /><label for="worker_company_1" class="label-radio">타지점</label>
						<input type="radio" id="worker_company_2" name="worker_company" class="inputJo" value="0" /><label for="worker_company_2" class="label-radio">지점전체</label>
					</c:if>
				</td>
				<th scope="row" class="linelY pdlM">교육이수</th>
				<td>
					<input type="checkbox" checked id="worker_osh" name="worker_osh" class="inputJo" value="1" /><label for="worker_osh" class="checkTxt">기초안전보건교육증 보유자만 검색</label> 
				</td>
			</tr>
			<tr>
				<td scope="row"  colspan=6  class="linelY pdlM">
					<div class="btn-module floatL">
						<a href="#none" id="btnDetailSrh" class="search2">상세검색</a>
					</div>				
				</td>
			</tr>
		</table>
	</div>
	<table id="list"></table>
	<div id="paging"></div>
	</form>
	
	<div id="opt_layer" style="position: absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b;"></div>

	<!-- 팝업 : 스캔 첨부파일 등록 -->
	<div id="popup-layer">
		<header class="header">
			<h1 class="tit">스캔파일 첨부.</h1>
			<a href="#none" class="close" onclick="javascript:closeIrPopup('popup-layer');">
				<img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" />
			</a>
		</header>
	
		<form id="frmPopup" name="frmPopup" action="/admin/workerUploadFile" method="post" enctype="multipart/form-data">
			<input type="hidden" id="service_seq" name="service_seq" />
	
			<section>
				<div class="cont-box">
					<article>
						<table class="bd-form inputUI_simple" summary="첨부파일 등록 영역입니다.">
							<caption>주민등록증 통장사본 교육이수증 증명서 등록 영역</caption>
							<colgroup>
								<col width="120px" />
								<col width="*" />
							</colgroup>
							<tbody>
								<tr>
									<th>내 사진</th>
									<td>
										<div class="btn-module filebox floatL">
											<input type="text" id="text_myPhoto" name="text_myPhoto" class="upload-name wid250" disabled="disabled"> 
											<label for="file_myPhoto">파일 첨부</label> 
											<input type="file" id="file_myPhoto" name="file_myPhoto" class="upload-hidden">
										</div>
									</td>
								</tr>
								<tr>
									<th>주민등록증</th>
									<td>
										<div class="btn-module filebox floatL">
											<input type="text" id="text_jumin" name="text_jumin" class="upload-name wid250" disabled="disabled"> 
											<label for="file_jumin">파일 첨부</label> 
											<input type="file" id="file_jumin" name="file_jumin" class="upload-hidden">
										</div>
									</td>
								</tr>
								<tr>
									<th>통장사본</th>
									<td>
										<div class="btn-module filebox floatL">
											<input type="text" id="text_bank" name="text_bank" class="upload-name wid250" disabled="disabled"> 
											<label for="file_bank">파일 첨부</label> 
											<input type="file" id="file_bank" name="file_bank" class="upload-hidden">
										</div>
									</td>
								</tr>
								<tr>
									<th>교육이수증</th>
									<td>
										<div class="btn-module filebox floatL">
											<input type="text" id="text_OSH" name="text_OSH" class="upload-name wid250" disabled="disabled"> 
											<label for="file_OSH">파일 첨부</label> 
											<input type="file" id="file_OSH" name="file_OSH" class="upload-hidden" />
										</div>
									</td>
								</tr>
								<tr>
									<th>구직신청서</th>
									<td>
										<div class="btn-module filebox floatL">
											<input type="text" id="text_job" name="text_job" class="upload-name wid250" disabled="disabled" /> 
											<label for="file_Job">파일 첨부</label> 
											<input type="file" id="file_Job" name="file_Job" class="upload-hidden" />
										</div>
									</td>
								</tr>
								<tr>
									<th>증명서</th>
									<td>
										<div class="btn-module filebox floatL">
											<input type="text" id="text_cert" name="text_cert" class="upload-name wid250" disabled="disabled" /> 
											<label for="file_cert">파일 첨부</label> 
											<input type="file" id="file_cert" name="file_cert" class="upload-hidden" />
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</article>
	
					<div class="btn-module mgtS">
						<div class="rightGroup">
							<a href="#popup-layer" id="btnScanAdd" class="btnStyle01">등록</a>
						</div>
					</div>
				</div>
			</section>
		</form>
		<form id="alimPopFrm" name="alimPopFrm" method="post"></form>
		<form id="jobAlimPopFrm" name="jobAlimPopFrm" method="post"></form>
	</div>
	<!-- // 팝업 : 스캔 첨부파일 등록  -->
<script type="text/javascript">
//<![CDATA[
	$(window).load(function() {
  		setDayType('start_reg_date', 'end_reg_date', 'all');

  		$(function() {
    		$("input:radio[name=srh_use_yn]").click( function() {
      			$("input:radio[name=srh_use_yn]").removeAttr('checked');
      			$("input:radio[name=srh_use_yn]").prop('checked', false);

      			$(this).prop('checked', true);
    		});
  		});
	});

	// 스캔파일 추가
	$("#btnScanAdd").click( function() {
		$('#frmPopup').ajaxForm({
        	url: "/admin/workerUploadFile",
        	enctype: "multipart/form-data", // 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
       		success: function(result) {
				alert('업로드 하였습니다.');
                $("#list").trigger("reloadGrid");
          	} ,
      		beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
        	},
        	error: function(e) {
        		if ( data.status == "901" ) {
            		location.href = "/admin/login";
            	} else {
            		alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			}
		});

		$('#frmPopup').submit();

		closeIrPopup('popup-layer');
	});
	
	$("#standardSrh").autocomplete({
		source	: function (request, response) {
			// ' replace 치기(500error)
			var replaceStandardSrh = replaceAll($("#standardSrh").val(), '\'', '');
			
	    	$.ajax({
	        	url: "/admin/getWorkNameList4", 
	        	type: "POST", 
	        	dataType: "json",
	            data: { 
	            	term: replaceStandardSrh, 
	            	srh_use_yn: 1, 
	            	srh_del_yn: 0, 
	            	srh_distinct_yn: 1 
	            },
	            success: function (data) {
	            	selectWorkerName = "";

	                response($.map(data, function (item) {
	                	return item;
	                }));
	            },
	            beforeSend: function(xhr) {
	            	xhr.setRequestHeader("AJAX", true);
	            },
	            error: function(e) {
	            	if ( data.status == "901" ) {
	                	location.href = "/admin/login";
	                } else {
	                	alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
	           		}
				}
			});
		},
	    minLength: 2,
	    focus: function (event, ui) {
	    	return false;
	    },
	    select: function (event, ui) {
	        fn_jobReset();
			fn_distanceReset();
			
	        selectWorkLatLng = ui.item.work_latlng;
	        selectWorkName = ui.item.work_name;
	        selectEmployerSeq = ui.item.employer_seq;
	        selectEmployerName = ui.item.employer_name;
	        selectWorkSeq = ui.item.work_seq;
	        selectWorkSido = ui.item.work_sido;
	        selectWorkSigugun = ui.item.work_sigugun;
	        selectWorkDongmyun = ui.item.work_dongmyun;
	        selectWorkManager = ui.item.manager_name;
	        
	        $("#distanceDiv").show();
	        $("#jobDiv").show();
	        
	        if(selectWorkSido == '서울특별시') {
	        	fn_distanceHide();
	        }else if(selectWorkSido == '경기도' || selectWorkSido == '인천광역시') {
	        	fn_distanceHide();
	        	
	        	if(!$("#worker_distance_30").hasClass("inputJo")) {
	        		$("#worker_distance_30").addClass("inputJo");
		        	$("#worker_distance_30").show();
		        	$("#worker_distance_30_label").addClass("checkTxt");
		        	$("#worker_distance_30_label").show();
	        	}
	        }else {
	        	fn_distanceReset();
	        	
	        	$("#distanceDiv").show();
	        }
	        
	        $("#workIlboBtn").css("background-color", "#5aa5da");
	        $("#worker_done").attr("disabled", false);
	        $("#worker_done_label").removeClass("off");
	        returnBool = 0;
	        
	        var nowTime = today.getHours() + '';
	        var nowMinutes = today.getMinutes() + '';
	        
	        if(nowMinutes.length == 1) {
	        	nowMinutes = '0' + nowMinutes;
	        }
	        
	        nowTime += nowMinutes; 
	        
	        $.ajax({
	        	url: "/admin/getIlboList", 
	        	type: "POST", 
	        	dataType: "json",
	            data: { 
	            	where: "AND i.use_yn = '1' AND i.work_seq = " + ui.item.work_seq + " AND (i.ilbo_date >= '" + toDay + "' OR (i.ilbo_date = '" + toDay + "' AND ilbo_work_arrival >= " + nowTime + ")) AND (i.ilbo_status_code != 'STA002' AND i.ilbo_status_code != 'STA007' AND i.ilbo_status_code != 'STA008' AND i.ilbo_status_code != 'STA009') AND (i.worker_seq IS NULL OR i.worker_seq = 0 OR (i.output_status_code = '100009' OR i.output_status_code = '100010' OR i.output_status_code = '100002' OR i.output_status_code = '0'))"
	            	//, start_ilbo_date: toDay
	            	, sidx: 'ilbo_date'
	            	, sord: 'ASC'
	            	, search: 'WORKER'
	            },
	            success: function (data) {
	            	var ilboList = data.rows;
            		var html = '';
	            	
            		fn_jobReset();
	            	for(var i = 0; i < ilboList.length; i++) {
	            		if(ilboList[i].ilbo_kind_code != null){
	            			//html += '<input type="radio" onclick="chkJobVali(this)" id="worker_job_kind_' + i + '" name="worker_job_kind" class="inputJo" value="' + ilboList[i].ilbo_kind_code + '" /><label for="worker_job_kind_' + i + '" id="worker_job_kind_' + i + '_label" class="checkTxt label-checkbox worker_job_kind">' + ilboList[i].ilbo_kind_name + '</label>';
	            			html += '<input type="radio" onclick="chkJobVali(this)" id="worker_job_kind_' + i + '" name="worker_job_kind" class="inputJo" value="' + ilboList[i].ilbo_kind_code + '" /><label for="worker_job_kind_' + i + '" id="worker_job_kind_' + i + '_label" class="label-radio">' + ilboList[i].ilbo_kind_name + '</label>';
	            		}
            				
	            	}

					$("#jobDiv").html(html);
					
					var firstJobObj = $("input:radio[name='worker_job_kind']").eq(0);
					firstJobObj.click();
	            },
	            beforeSend: function(xhr) {
	            	xhr.setRequestHeader("AJAX", true);
	            },
	            error: function(e) {
	            	if ( data.status == "901" ) {
	                	location.href = "/admin/login";
	                } else {
	                	alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
	           		}
				}
			});
		},
		close : function() {
			$("#standardSrh").val(selectWorkName);
		}
	});
	
	$("#btnStandardReset").on("click", function() {
		$("#standardSrh").val('');
		
		$("input[name=worker_distance]").each(function() {
			$(this).next().removeClass("on");
			$(this).prop("checked", false);
		});
		
		selectWorkName = '';
		selectWorkLatLng = '';
		selectWorkSeq = '';
		selectEmployerSeq = '';
		selectEmployerName = '';
		selectWorkSido = '';
		selectWorkSigugun = '';
		selectWorkDongmyun = '';
		selectWorkManager = '';
		
		$("#workIlboBtn").css("background-color", "gray");
		
		$("#worker_done").attr("disabled", true);
		$("#worker_done_label").addClass("off");
        
		fn_jobReset();
		fn_distanceReset();
		
		returnBool = 1;
	});
	
	function fn_jobReset() {
		$("#jobDiv").empty();
	}
	
	function fn_distanceReset() {
		$("#distanceDiv").hide();
		
		$("#worker_distance_2").addClass("inputJo");
		$("#worker_distance_2").removeClass("on");
		//$("#worker_distance_2").removeClass("on");
		$("#worker_distance_2").addClass("on");
		$("#worker_distance_2").prop("checked", true);
    	$("#worker_distance_2").show();
    	$("#worker_distance_2_label").addClass("checkTxt");
		//$("#worker_distance_2_label").removeClass("on");
		$("#worker_distance_2_label").addClass("on");
    	$("#worker_distance_2_label").show();
		$("#worker_distance_5").addClass("inputJo");
		//$("#worker_distance_5").removeClass("on");
		$("#worker_distance_5").addClass("on");
		$("#worker_distance_5").prop("checked", true);
    	$("#worker_distance_5").show();
    	$("#worker_distance_5_label").addClass("checkTxt");
		//$("#worker_distance_5_label").removeClass("on");
		$("#worker_distance_5_label").addClass("on");
    	$("#worker_distance_5_label").show();
    	$("#worker_distance_10").addClass("inputJo");
		$("#worker_distance_10").removeClass("on");
    	$("#worker_distance_10").show();
    	$("#worker_distance_10_label").addClass("checkTxt");
		$("#worker_distance_10_label").removeClass("on");
    	$("#worker_distance_10_label").show();
    	$("#worker_distance_20").addClass("inputJo");
		$("#worker_distance_20").removeClass("on");
    	$("#worker_distance_20").show();
    	$("#worker_distance_20_label").addClass("checkTxt");
		$("#worker_distance_20_label").removeClass("on");
    	$("#worker_distance_20_label").show();
		$("#worker_distance_30").addClass("inputJo");
		$("#worker_distance_30").removeClass("on");
    	$("#worker_distance_30").show();
    	$("#worker_distance_30_label").addClass("checkTxt");
		$("#worker_distance_30_label").removeClass("on");
    	$("#worker_distance_30_label").show();
    	$("#worker_distance_40").addClass("inputJo");
    	$("#worker_distance_40").removeClass("on");
    	$("#worker_distance_40").show();
    	$("#worker_distance_40_label").addClass("checkTxt");
		$("#worker_distance_40_label").removeClass("on");
    	$("#worker_distance_40_label").show();
    	$("#worker_distance_50").addClass("inputJo");
    	$("#worker_distance_50").removeClass("on");
    	$("#worker_distance_50").show();
    	$("#worker_distance_50_label").addClass("checkTxt");
		$("#worker_distance_50_label").removeClass("on");
    	$("#worker_distance_50_label").show();
    	$("#worker_distance_100").addClass("inputJo");
    	$("#worker_distance_100").removeClass("on");
    	$("#worker_distance_100").show();
    	$("#worker_distance_100_label").addClass("checkTxt");
		$("#worker_distance_100_label").removeClass("on");
    	$("#worker_distance_100_label").show();
	}
	
	function fn_distanceHide() {
		$("#worker_distance_30").removeClass("inputJo");
    	$("#worker_distance_30").hide();
    	$("#worker_distance_30_label").removeClass("checkTxt");
    	$("#worker_distance_30_label").hide();
    	$("#worker_distance_40").removeClass("inputJo");
    	$("#worker_distance_40").hide();
    	$("#worker_distance_40_label").removeClass("checkTxt");
    	$("#worker_distance_40_label").hide();
    	$("#worker_distance_50").removeClass("inputJo");
    	$("#worker_distance_50").hide();
    	$("#worker_distance_50_label").removeClass("checkTxt");
    	$("#worker_distance_50_label").hide();
    	$("#worker_distance_100").removeClass("inputJo");
    	$("#worker_distance_100").hide();
    	$("#worker_distance_100_label").removeClass("checkTxt");
    	$("#worker_distance_100_label").hide();
	}
	
	function fnReloadGrid(){
		$("#list").trigger("reloadGrid");
	}
	
	function chkVali(e) {
		if($(e)[0].id == 'worker_job_1') {
			if(!$(this).is(":checked")) {
				$("#worker_distance_100").attr("checked", false);
				
				$("label[for=worker_distance_100]").removeClass("on");
			}
		}else if($(e)[0].id == 'worker_distance_100') {
			if(!$("#worker_job_1").is(":checked")) {
				alert("해당 옵션은 보통인부제외 체크 후 선택할 수 있습니다.");
				
				$(e).attr("checked", false);
				
				returnBool = 1;
				
				return false;
			}else {
				returnBool = 0;				
			}
		}else {
			returnBool = 0;
		}
	}
	
	function fn_avgRating(cellvalue, options, rowObject) {
		if(cellvalue.length > 3) {
			cellvalue = cellvalue.substring(0, 3);
		}

		var tmpVal = cellvalue.substring(1, cellvalue.length);
		
		if(cellvalue == '0.0') {
			return '0';
		}else if(tmpVal == '.0') {
			cellvalue = cellvalue.substring(0, 1); 
		
			cellvalue *= 1;
			cellvalue -= 1;
			
			if(cellvalue < 1) {
				cellvalue = 0;
			}
			
			return cellvalue;
		}else {
			if(cellvalue.length >= 4 || cellvalue.length == 3) {
				cellvalue = cellvalue.substring(0, 3);
			}else {
				cellvalue = cellvalue.substring(0, 1);
			}
			
			cellvalue *= 1;
			cellvalue -= 1;
			
			if(cellvalue < 1) {
				cellvalue = 0;
			}
			
			cellvalue += '';
			
			if(cellvalue.length >= 4) {
				cellvalue = cellvalue.substring(0, 3);
			}
			
			return cellvalue;
		}
			
		if(cellvalue < 1) {
			cellvalue = 0;
		}
		
		cellvalue += '';
		
		if(cellvalue.length >= 4) {
			cellvalue = cellvalue.substring(0, 3);
		}
		
		return cellvalue;		
	} 
	
	function fn_convertScore(cellValue, option, rowObject) {
		if(cellValue >= 500) {
			return 'A';
		}else if(cellValue >= 300) {
			return 'B';
		}else if(cellValue >= 200) {
			return 'C';
		}else if(cellValue >= 100) {
			return 'D';
		}else if(cellValue >= 0){
			return 'E';
		}else {
			return 'F';
		}
	}
	
	function chkJobVali(e) {
		$("#jobDiv label").removeClass("on");
		$(e).next().addClass("on");
	}
</script>
