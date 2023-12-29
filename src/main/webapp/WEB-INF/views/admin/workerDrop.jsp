<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript"	src="<c:url value='/resources/cms/js/ilbo.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/ilbo_fomat.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/jobCal.js?ver=1.0' />"	charset="utf-8"></script>

<script type="text/javascript">
	var mCode = new Map();
	var oCode = new Object();
	var selectWorkerName = ''; 	// 선택된 사람
	var ilbotype_opts = ':ALL;O:사무실;R:일요청;A:APP;P:PUSH;C:일자리;S:시스템';
	var optWorkOrder = ':ALL';
	var optOupput = "";                   // 출역상태       code_type = 100
	var bf_opts	= '0:조식없음;1:조식제공';	//조식유무 - 0:없음 1:있음
	var srh_seq = "<c:out value='${param.srh_seq}' />";
	var authLevel = "<c:out value='${authLevel}' />";
	var star_opts = '0:미평가;6:A;5:B;4:C;3:D;2:E;1:F';	//구인처가 구직자평가 등급
	var grade_opts = '0:미평가;5:A;4:B;3:C;2:D;1:F';		//구직자가 구인처 평가 등급
	var use_opts = '1:사용;0:미사용';		//사용유무 - 0:미사용 1:사용
	
	$(function() {	
		$("#start_ilbo_date").val("");
		$("#end_ilbo_date").val("");
		
		$("#btnSrh").on("click", function() {
			//검색어 체크
			if($("#srh_type option:selected").val() != '' && $("#srh_text").val() == '') {
				alert("검색어를 입력하세요.");
				
				$("srh_text").focus();
					
				return false;
			}
			
			$("#list").setGridParam({
				postData : {
					start_ilbo_date : $("#start_ilbo_date").val(),
					end_ilbo_date : $("#end_ilbo_date").val(),
					srh_type : $("#srh_type option:selected").val(),
					srh_text : $("#srh_text").val()
				}
			}).trigger("reloadGrid");
			
			lastsel = -1;
		});
		
					$.ajax({
						type	: "POST",
						url		: "/admin/getCommonCodeList",
						data	: {"use_yn": "1", "code_type": "100, 200, 300, 500, 'USE', 'STA', 'WSC', 'ORD'"},
						dataType: 'json',
						success	: function(data) {
							var _style = "";
						    var tempCode = 400;
						    var code400br = "0";
						   		 
						    if ( data != null && data.length > 0 ) {
						    	for ( var i = 0; i < data.length; i++ ) {
						        	if ( data[i].code_type == 100 ) {          // 출역상태
						            	optOupput += "<div class=\""+ data[i].code_value +" bt\"><a href=\"JavaScript:void(0);\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
						        	}
						        	
						            oCode = new Object();
						
						            oCode.bgcolor	= data[i].code_bgcolor;
						            oCode.color   	= data[i].code_color;
						            oCode.name   = data[i].code_name;
						
						            mCode.put(data[i].code_value, oCode);
								}
							}
						},
						beforeSend: function(xhr) {
							xhr.setRequestHeader("AJAX", true);
						},
						error: function(e) {
							if ( data.status == "901" ) {
						    	location.href = "/admin/login";
						    }
						}
					});
					
		$("#list").jqGrid({
			url : "/admin/getWorkerDrop",
			datatype : "json",
			mtype : "POST",
			height : 800,
			rownum : 10,
			rowList : [25, 50, 100],
			pager : '#paging',
			caption : '탈퇴회원 근로이력',
			rownumbers : true,
			jsonReader : {
				id : "ilbo_seq",
				repeatitems : false
			},
			postData : {
				start_ilbo_date : $("#start_ilbo_date").val(),  
				end_ilbo_date : $("#end_ilbo_date").val(),
				srh_type : $("#srh_type option:selected").val(),
				srh_text : $("#srh_text").val()
			},
			contentType : "application/json;charset=utf-8", 
			colNames : [
				'일자', '일일대장지점', '구직자소속지점', '구직자소유', '구직자', '특징', '구직자 메모', '배정', 
				'동반자','구직자정보','상태', '일보메모', '구인처', '특징', '매니저지점', '소유', 
				'현장매니저명', '현장매니저 번호', '현장', '순서', '상태', '오더종류', '공개', '현장조식', 
				'도착시간', '마감시간', '직종', '직종상세명', '직종옵션명', '작업설명', '소장메모', '최저나이', 
				'최고나이', '혈압', '현장담당', '현장전화', '노임수령', '수령일자', '단가', '수수료', 
				'쉐어료', '상담사', '구직자', '노임지급', '지급일자', '입출금 메모', '본사평가', '현장평가', 
				'구직자 평가', '상태', '등록일시', '등록자', '소유자'
			],
			colModel : [
				{name : 'ilbo_date', index : 'ilbo_date', align : "center", width : ${ilboSettingDTO.ilbo_date_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_date eq '1' ? false : true }},
				
			    {name : 'company_name', index : 'company_name', width : 80, align : "left"},
				
				{name : 'worker_company_name', index : 'worker_company_name', align : "left", width : ${ilboSettingDTO.worker_company_name_width} 
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.worker_company_name eq '1' ? shareYn eq "0" ? true : false : true}
				, cellattr : cellattrWorkerCompanyColor},
				
				{name : 'worker_owner', index : 'worker_owner', align : "center", width : ${ilboSettingDTO.worker_owner_width}
				, cellattr : cellattrWorkerCompanyColor, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.worker_owner eq '1' ? false : true }},	//구직자 소유
				
				{name : 'ilbo_worker_name', index : 'ilbo_worker_name', align : "left", width : ${ilboSettingDTO.ilbo_worker_name_width}
				, cellattr : function(rowId, tv, rowObject, cm, rdata) {
					return getCodeBGStyle(rowObject.ilbo_pay_code, false);	/* 수수료 입금 여부에따라 배경색 달라짐. 흰색은 받았다는 뜻 */
				}
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_name eq '1' ? false : true}},
				
				{name : 'ilbo_worker_feature', index : 'ilbo_worker_feature', align : "left", width : ${ilboSettingDTO.ilbo_worker_feature_width}
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_feature eq '1' ? false : true}},
				
				{name : 'ilbo_worker_memo'          , index : 'ilbo_worker_memo'          , align : "left", width : ${ilboSettingDTO.ilbo_worker_memo_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_memo eq '1' ? false : true}	},
				
				{name : 'ilbo_assign_type'    , index : 'ilbo_assign_type'    , align : "center", width : ${ilboSettingDTO.ilbo_assign_type_width}
					, editable: false
					, edittype: "select"
					, editoptions: {value: ilbotype_opts}   
			    	, formatter: "select"
			        , cellattr : function(rowId, tv, rowObject, cm, rdata) {
			        	// rowObject 변수로 그리드 데이터에 접근
			            if(tv == 'O') {
			            	return 'style="background : #ffffff; text-align : center;"';
						}else if(tv == 'A') {
			            	return 'style="background : #5aa5da; text-align : center;"';
						}else if(tv == 'S') {
			            	return 'style="background : #81d80c; text-align : center;"';
						}else if(tv == 'P') {
			            	return 'style="background : #ffc1c1; text-align : center;"';
						}else if(tv == 'C') {
			            	return 'style="background : #1DE9B6; text-align : center;"';
						}else if(tv == 'R') {
			            	return 'style="background : #FFFF00; text-align : center;"';
						}
					}
					, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_assign_type eq '1' ? false : true}},
				
				{name : 'ilbo_companion_info', index : 'ilbo_companion_info', align : "left", sortable: false, formatter : formatCompanion, width : 30}, /* 동반자 정보 */
				
				{name : 'ilbo_worker_info', index : 'ilbo_worker_info', sortable: false, align : "left", width : ${ilboSettingDTO.ilbo_worker_info_width}, formatter : formatWorkerInfoBtn
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_info eq '1' ? false : true}},
				
			    {name : 'ilbo_worker_status_info', index : 'ilbo_worker_status_info', align : "center", width : ${ilboSettingDTO.ilbo_worker_status_info_width}, formatter : formatWorkerStatusInfoBtn, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_status_info eq '1' ? false : true}},
				    
				{name : 'ilbo_memo', index : 'ilbo_memo', align : "left", width : ${ilboSettingDTO.ilbo_memo_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_memo eq '1' ? false : true}},
					
				{name : 'employer_name', index : 'employer_name', align : "left", width : ${ilboSettingDTO.employer_name_width}
				, cellattr : function(rowId, tv, rowObject, cm, rdata) {
					return getCodeBGStyle(rowObject.ilbo_income_code, false);
				}
				, formatter : formatSelectName
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.employer_name eq '1' ? false : true}},
					
				{name : 'ilbo_employer_feature', index : 'ilbo_employer_feature', align : "left", width : ${ilboSettingDTO.ilbo_employer_feature_width}
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_employer_feature eq '1' ? false : true}},
				    
				{name : 'work_company_name', index : 'work_company_name', align : "left", width : ${ilboSettingDTO.work_company_name_width} 
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.work_company_name eq '1' ? shareYn eq "0" ? true : false : true}
				, cellattr : cellattrWorkerCompanyColor},

				{name : 'work_owner', index : 'work_owner', align : "center", width : ${ilboSettingDTO.work_owner_width} 
				, cellattr : cellattrWorkerCompanyColor
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.work_owner eq '1' ? false : true}},
					
				{name : 'manager_name', index : 'manager_name', width : ${ilboSettingDTO.manager_name_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.manager_name eq '1' ? false : true}},
					
				{name : 'manager_phone', index : 'manager_phone', width : ${ilboSettingDTO.manager_phone_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.manager_phone eq '1' ? false : true}},
					
				{name : 'ilbo_work_name', index : 'ilbo_work_name', align : "left", width : ${ilboSettingDTO.work_name_width} 
				, cellattr : function(rowId, tv, rowObject, cm, rdata) {
					//현장의 출석 일수 에 따라 배경색을 다르게 한다.
					var count = rowObject.day_count;
					
					if(count == 2) {
						return 'style="background-color : #a7e67b"'; //; color:#"';	 
					}else if(count >= 3) {
						return 'style="background-color : #e6c57b"'; //; color:#"';
					}else {
						return false;
					}
				}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.work_name eq '1' ? false : true}},
					
				{name : 'ilbo_work_info', index : 'ilbo_work_info', align : "left", width : ${ilboSettingDTO.ilbo_work_info_width}, formatter : formatWorkInfoBtn, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_info eq '1' ? false : true}},
				    
				{name : 'ilbo_status_info', index : 'ilbo_status_info', align : "left", width : ${ilboSettingDTO.ilbo_status_info_width}, formatter : formatStaInfoBtn, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_status_info eq '1' ? false : true}},
				    
				{name : 'ilbo_work_order_name', index : 'ilbo_work_order_name', align : "left", cellattr : cellattrWorkOrderColor}, // 오더종류
				    
				{name : 'ilbo_use_info', index : 'ilbo_use_info', align : "left", width : ${ilboSettingDTO.ilbo_use_info_width}		
				, formatter : formatUseInfoBtn
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_use_info eq '1' ? false : true}},
					
				{name : 'ilbo_work_breakfast_yn', index : 'ilbo_work_breakfast_yn', align : "center", width : ${ilboSettingDTO.ilbo_work_breakfast_yn_width},
				cellattr : function(rowId, tv, rowObject, cm, rdata) {
					// rowObject 변수로 그리드 데이터에 접근
					if(tv == '1') {
						return 'style="background : #5aa5da; text-align : center;"';
					}
				}
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_breakfast_yn eq '1' ? false : true}},
					
				{name : 'ilbo_work_arrival', index : 'ilbo_work_arrival', align : "left", width : ${ilboSettingDTO.ilbo_work_arrival_width}
				, formatter : formatTime
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_arrival eq '1' ? false : true}},
					
			    {name : 'ilbo_work_finish', index : 'ilbo_work_finish', align : "left", width : ${ilboSettingDTO.ilbo_work_finish_width}
				, formatter : formatTime 
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_finish eq '1' ? false : true}},
					
				{name : 'ilbo_kind_name', index : 'ilbo_kind_name', align : "center", width : ${ilboSettingDTO.ilbo_kind_name_width}
				, formatter : formatKindCodeBtn
				, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_kind_name eq '1' ? false : true}},
						
				{name : 'ilbo_job_detail_name', index : 'ilbo_job_detail_name',	width : 100},
					
				{name : 'ilbo_job_add_name', index : 'ilbo_job_add_name', width : 150},							    
					
				{name : 'ilbo_job_comment', index : 'ilbo_job_comment', align : "left", width : ${ilboSettingDTO.ilbo_job_comment_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_job_comment eq '1' ? false : true}},
				    
			    {name : 'ilbo_chief_memo', index : 'ilbo_chief_memo', align : "left", width : ${ilboSettingDTO.ilbo_chief_memo_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_chief_memo eq '1' ? false : true}},
				
				{name : 'ilbo_work_age_min', index : 'ilbo_work_age_min', align : "center", width : ${ilboSettingDTO.ilbo_work_age_min_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_age_min eq '1' ? false : true}},
				
			    {name : 'ilbo_work_age', index : 'ilbo_work_age', align : "center", width : ${ilboSettingDTO.ilbo_work_age_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_age eq '1' ? false : true}},
			    
			    {name : 'ilbo_work_blood_pressure', index : 'ilbo_work_blood_pressure', width : ${ilboSettingDTO.ilbo_work_blood_pressure_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_blood_pressure eq '1' ? false : true}},
			    
				{name : 'ilbo_work_manager_name', index : 'ilbo_work_manager_name', width : ${ilboSettingDTO.ilbo_work_manager_name_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_manager_name eq '1' ? false : true}},
				
			    {name : 'ilbo_work_manager_phone', index : 'ilbo_work_manager_phone', width : ${ilboSettingDTO.ilbo_work_manager_phone_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_manager_phone eq '1' ? false : true}},
				
			    {name : 'ilbo_income_name', index : 'ilbo_income_name', align : "center", width : ${ilboSettingDTO.ilbo_income_name_width}, formatter : formatIncomeCodeBtn, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_income_name eq '1' ? false : true}},
			    
			    {name : 'ilbo_income_time', index : 'ilbo_income_time', align : "center", width : ${ilboSettingDTO.ilbo_income_time_width}, formatoptions : {newformat : "y/m/d H:i"}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_income_time eq '1' ? false : true}},
				    
			    {name : 'ilbo_unit_price', index : 'ilbo_unit_price', align : "right", width : ${ilboSettingDTO.ilbo_unit_price_width} 
			    , summaryType : "priceSum"
			    , formatter : 'integer', formatoption : {thousandsSeparator : ",", decimalPlace : 0}
			    , cellattr : function(rowId, tv, rowObject, cm, rdata) {
					var price = parseInt(rowObject.ilbo_unit_price);
	
					if(rowObject.ilbo_seq >= 0) {
		            	if(price <= 110000) return 'style="background : #FFFFFF"'; 
						else if(price <= 160000) return 'style="background : #FFE599"'; 
		                else if(price <= 190000) return 'style="background : #A2C4C9"'; 
		                else if(price >= 200000) return 'style="background : #D5A6BD"'; 
		                else return 'style="background : #FFFFFF"'; 
					}
				}
			    , hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_unit_price eq '1' ? false : true}},
					
				{name : 'ilbo_fee', index : 'ilbo_fee', align : "right", width : ${ilboSettingDTO.ilbo_fee_width}, 
			    summaryType : "sum",
			    formatter : 'integer', formatoption : {thousandsSeparator : ",", decimalPlace : 0},
			    hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_fee eq '1' ? false : true}},
					
				{name : 'share_fee', index : 'share_fee', align : "right", width : ${ilboSettingDTO.share_fee_width}, 
			    hidden : ${shareYn eq "0" ? true : false},
			    summaryType : "sum",
			    formatter : 'integer', formatoption : {thousandsSeparator : ",", decimalPlace : 0},
			    hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.share_fee eq '1' ? false : true}},
					
				{name : 'counselor_fee', index : 'counselor_fee', align : "right", width :  70,
			    summaryType : "sum",
			    formatter : 'integer', formatoption : {thousandsSeparator : ",", decimalPlace : 0},
			    hidden: false},
				
			    {name : 'ilbo_pay', index : 'ilbo_pay', align : "right", width : ${ilboSettingDTO.ilbo_pay_width}, 
			    summaryType : "sum",
			    formatter : 'integer', formatoption : {thousandsSeparator : ",", decimalPlace : 0},
			    hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_pay eq '1' ? false : true}},
				
			    {name : 'ilbo_pay_name', index : 'ilbo_pay_name', align : "center", width : ${ilboSettingDTO.ilbo_pay_name_width}, formatter : formatPayCodeBtn, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_pay_name eq '1' ? false : true}},
			    
			    {name : 'ilbo_pay_time', index : 'ilbo_pay_time', align : "center", width : ${ilboSettingDTO.ilbo_pay_time_width}, formatoptions : {newformat : "y/m/d H:i"}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_pay_time eq '1' ? false : true}},
			    
				{name : 'ilbo_income_memo', index : 'ilbo_income_memo', align : "left", width : ${ilboSettingDTO.ilbo_income_memo_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_income_memo eq '1' ? false : true}},
				
				{name : 'employer_rating', index : 'employer_rating', align : "center", width : ${ilboSettingDTO.employer_rating_width}
				, editoptions: {value: star_opts}   
				, formatter: "select"
				, editable:false
		        , cellattr : function(rowId, tv, rowObject, cm, rdata) {
					// rowObject 변수로 그리드 데이터에 접근
			        if(rowObject.employer_rating  == '0') {
			            return 'style="color : #B300FF; text-align : center;"';
					}
				}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.employer_rating eq '1' ? false : true}},
				
				{name : 'manager_rating', index : 'manager_rating', align : "center", width : ${ilboSettingDTO.employer_rating_width}
				, editoptions: {value: star_opts}   
				, formatter: "select"
				, editable:false
			    , cellattr : function(rowId, tv, rowObject, cm, rdata) {
			        // rowObject 변수로 그리드 데이터에 접근
			        if(rowObject.manager_rating  == '0') {
			            return 'style="color: #B300FF; text-align :  center;"';
					}
				}, hidden : false},
				
				{name : 'evaluate_grade', index : 'evaluate_grade', align : "center", width : ${ilboSettingDTO.evaluate_grade_width}
				, editoptions: {value: grade_opts}   
				, formatter: "select"
				, editable:false
				, cellattr : function(rowId, tv, rowObject, cm, rdata) {
					var text = 'class="evaluate_grade"';
					
			        // rowObject 변수로 그리드 데이터에 접근
			        if(rowObject.evaluate_grade  == '0') {
			            text += ' style="color: #B300FF; text-align :  center;"';
			        }	
			        
			        text += ' title="' + rowObject.evaluate_comment+'"';
			        							
			        return text;
				}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.evaluate_grade eq '1' ? false : true}},
				
			    {name : 'use_yn', index : 'use_yn', align : "center", width : ${ilboSettingDTO.use_yn_width}
				, editoptions: {value: use_opts}   
				, formatter: "select"
				, editable:false
				, cellattr : function(rowId, tv, rowObject, cm, rdata) {
			        // rowObject 변수로 그리드 데이터에 접근
			        if(rowObject.use_yn == '1') {
			            return 'style="color: #B300FF; text-align :  center;"';
					}
				}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.use_yn eq '1' ? false : true}},
				
			    {name : 'reg_date', index : 'reg_date', align : "center", width : ${ilboSettingDTO.reg_date_width}, formatoptions : {newformat : "y/m/d H:i"}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.reg_date eq '1' ? false : true}},
			    
			    {name : 'reg_admin', index : 'i.reg_admin', align : "left", width : ${ilboSettingDTO.reg_admin_width},  hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.reg_admin eq '1' ? false : true}},
			    
			    {name : 'owner_id', index : 'i.owner_id', align : "left", width : ${ilboSettingDTO.owner_id_width},  hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.owner_id eq '1' ? false : true}}
			],
			loadComplete : function(data) {
				$(".wrap-loading").hide();
			},
			loadBeforeSend : function(xhr) {
				xhr.setRequestHeader("AJAX", true);
				$(".wrap-loading").show();
			},
			loadError : function(xhr, status, error) {
				$(".wrap-loading").hide();
				
				if ( xhr.status == "901" ) {
			    	location.href = "/admin/login";
				}
			}
		});

		$("#list").jqGrid('setGroupHeaders', {
			useColSpanStyle : true,
			groupHeaders : [
				{startColumnName : 'worker_company_name', numberOfColumns : 9, titleText : '<div style="margin:0; padding:0;width:100%;border-spacing:0px;background-color:#e6977b">구직자</div>'}
				, {startColumnName : 'employer_name', numberOfColumns : 2, 	titleText : '<div style="margin:0; padding:0;width:100%;border-spacing:0px;background-color:#7be6aa">구인처</div>'}
			    , {startColumnName : 'work_company_name', numberOfColumns : 20, 	titleText : '<div style="margin:0; padding:0;width :100%;border-spacing:0px;background-color:#e6c57b">현장</div>'}
			    , {startColumnName : 'ilbo_income_name', numberOfColumns : 10, 	titleText : '<div style="margin:0; padding:0;width:100%;border-spacing:0px;background-color:#a7e67b">금액</div>'}
			]
		});	
	});
	
	function formatKindCodeBtn(cellvalue, options, rowObject) {
		var str = '';
		var gId = options.gid;
		var rowId = options.rowId;
		
	    str += '<div class="bt_wrap">';

		if(rowObject.ilbo_kind_code != null && rowObject.ilbo_kind_code != '') {
			var _style = getCodeStyle(rowObject.ilbo_kind_code);
			
			str += '<div id="div_kind_"' + rowObject.ilbo_seq + 'class="bt_on"><a onclick="javascript:void(0);"' + _style + '>' + cellvalue +'</a></div>';
		}else {
			str += '<div id="div_kind_"' + rowObject.ilbo_seq + 'class="bt"><a onclick="javascript:void(0);">선택 </a></div>';
		}
		
		str += '</div>';
	
	  	return str;
	}
	
	function formatWorkerInfoBtn(cellvalue, options, rowObject) {
		var str = '';
		var rowId = options.rowId;
	
		if(typeof(rowObject.ilbo_seq) == 'undefined') {
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
		
		str += '<div class="bt_wrap">';
		
		// 출역상태
		if ( rowObject.output_status_code != null && rowObject.output_status_code != "" && rowObject.output_status_code > 0 ) {
			var _style = getCodeStyle(rowObject.output_status_code);
				
			str += "<div title ='"+rowObject.mod_output_date+"' id=\"div_output_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_workerInfoOptBtn('"+ rowObject.ilbo_seq +"', '"+ rowObject.worker_seq +"', '구직자를 매칭 시킨 후 ', event, '" + rowObject.output_status_code + "'); return false;\""+ _style +"> "+ rowObject.output_status_name +" </a></div>";
		} else if ( rowObject.ilbo_seq < 0 && rowObject.worker_seq > 0 ) {
			str += "<div title ='"+rowObject.mod_date+"' id=\"div_output_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_workerInfoOptBtn('"+ rowObject.ilbo_seq +"', '"+ rowObject.worker_seq +"', '구직자를 매칭 시킨 후 ', event, '" + rowObject.output_status_code + "'); return false;\"> 출근 </a></div>";
		} else {
			str += "<div title ='"+rowObject.mod_date+"' id=\"div_output_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_workerInfoOptBtn('"+ rowObject.ilbo_seq +"', '"+ rowObject.worker_seq +"', '구직자를 매칭 시킨 후 ', event, '" + rowObject.output_status_code + "'); return false;\"> 상태 </a></div>";
		}
		
		// 나이
		if(rowObject.ilbo_worker_sex == 'F') {
			str += '<div class="bt"><a href="javascript:void(0);" title="' + formatRegNo(rowObject.ilbo_worker_jumin, null, null) + '"><font color="blue"> ' + rowObject.ilbo_worker_age + ' 세 </font></a></div>';
		}else {
			str += '<div class="bt"><a href="javascript:void(0);" title="' + formatRegNo(rowObject.ilbo_worker_jumin, null, null) + '"> ' + rowObject.ilbo_worker_age + ' 세 </a></div>';
		}
	
		// 앱 설치 유무
		var _appStyle ="";
	
		// 승인
		if(rowObject.ilbo_worker_app_use_status == '1') {	
			// 로그인
			if(rowObject.ilbo_worker_app_status == '1') {	
				_appStyle = '_on';
			}else {														
				//로그 아웃
				_appStyle = '_green';
			}
		}else if(rowObject.ilbo_worker_app_use_status == '5') {	
			// 수수료 미납
			_appStyle = '_t1';
		}else {
			_appStyle = '';
		}
		
		str += '<div class="bt"' + _appStyle + '"><a href="javascript:void(0);"> 앱 </a></div>';
		
		// 신분증 스캔 여부
		if(rowObject.worker_seq == 0) {
			str += '<div class="bt"><a href="javascript:void(0);"> 신 </a></div>';
			str += '<div class="bt"><a href="javascript:void(0);"> 교 </a></div>';
		}else {
		    str += '<div class="bt' + ((rowObject.ilbo_worker_jumin_scan_yn == '1') ? '_on' : '') + '"><a href="javaScript:popupImage(\'JUMIN\',' + rowObject.worker_seq + ');"> 신 </a></div>';
		
			// 교육증 스캔 여부
			if(rowObject.ilbo_worker_OSH_yn == '1') {
			  	str += '<div class="bt' + ((rowObject.ilbo_worker_OSH_scan_yn == '1') ? '_on' : '_green') + '"><a href="javaScript:popupImage(\'OSH\',' + rowObject.worker_seq + ');"> 교 </a></div>';
			}else {
			  	str += '<div class="bt' + ((rowObject.ilbo_worker_OSH_scan_yn == '1') ? '_yellow' : '') + '"><a href="javaScript:popupImage(\'OSH\',' + rowObject.worker_seq + ');"> 교 </a></div>';
			}
		}
	
		// 계좌가 있는 경우
		if(rowObject.ilbo_worker_bank_code != null && rowObject.ilbo_worker_bank_owner != '') {             
		  	var btnTag = '';
		
		  	if(rowObject.ilbo_worker_bank_owner == rowObject.ilbo_worker_name || rowObject.ilbo_worker_bank_owner == selectWorkerName) {
		        if(rowObject.ilbo_worker_bankbook_scan_yn == '1') {
		        	// blue
		        	btnTag = '_on';    
		        }else {
		        	btnTag = '_green';
		        }
		  	}else {
		        if(rowObject.ilbo_worker_bankbook_scan_yn == '1') {
		      		btnTag = '_yellow';
		    	} else {
		      		btnTag = '_t1';
		    	}
		  	}
		
		  	str += '<div class="bt' + btnTag + '"><a href="javascript:void(0);" title="' + rowObject.ilbo_worker_bank_owner + ' ' + rowObject.ilbo_worker_bank_name + ' ' + rowObject.ilbo_worker_bank_account  + '"> 통 </a></div>';
		}else {    
			// 계좌가 없는 경우
			
		  	if(rowObject.ilbo_worker_bankbook_scan_yn == '1') {
		  		str += '<div class="bt_purple"><a href="javascript:void(0);"> 통 </a></div>';
		  	}else {
				str += '<div class="bt"><a href="javascript:void(0);"> 통 </a></div>';
		  	}
		}
		
		str += '</div>';
		
		selectWorkerName = '';
		
		return str;
	}
	
	function formatWorkerStatusInfoBtn(cellvalue, options, rowObject) {
		var str = '';
		var rowId = options.rowId;
		
		if(typeof(rowObject.ilbo_seq) == 'undefined') {
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
	
		str += '<div class="bt_wrap">';
		
		// 출역상태
		if(rowObject.ilbo_worker_status_code != '0' && rowObject.ilbo_worker_status_code != null && rowObject.ilbo_worker_status_code != '') {
			var _style = getCodeStyle(rowObject.ilbo_worker_status_code);
	
		    str += '<div title="' + rowObject.mod_output_date + '" id="div_wsc_"' + rowObject.ilbo_seq + '" class="bt_on"><a onclick="javaScript:void(0);"' + _style + '> ' + rowObject.ilbo_worker_status_name + '</a></div>';
		}else {
			str += '<div title="' + rowObject.mod_date + '" id="div_wsc_' + rowObject.ilbo_seq + '" class="bt"><a onclick="javaScript:void(0);"> 상태 </a></div>';
		}
	
		str += '</div>';
	
		selectWorkerName = '';
	
		return str;
	}
	
	function formatWorkInfoBtn(cellvalue, options, rowObject) {
		var str = '';
		var rowId = options.rowId;
		
		if(typeof(rowObject.ilbo_seq) == 'undefined') {
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
		
		if(rowObject.ilbo_seq > 0) {
			str += '<div class="bt_wrap">';
			
			// 현장시간
			if(rowObject.ilbo_order_name != null && rowObject.ilbo_order_name != '') {
				var _style = getCodeStyle(rowObject.ilbo_order_code);
				
				str += '<div id="div_order_' + rowObject.ilbo_seq + '" class="bt_on"><a onclick="javascript:void(0);"' + _style + '> ' + rowObject.ilbo_order_name + ' </a></div>';
			} else {
				str += '<div id="div_order_' + rowObject.ilbo_seq + '" class="bt"><a onclick="javascript:void(0);">순서</a></div>';
					
			}
		}

		return str;
	}
	
	function formatStaInfoBtn(cellvalue, options, rowObject) {
		var str = '';
		var rowId = options.rowId;
			
		if(typeof(rowObject.ilbo_seq) == 'undefined') {
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
	
		if(rowObject.ilbo_seq > 0) {
			str += '<div class="bt_wrap">';
	  		
			//현장 상태
		  	if(rowObject.ilbo_status_name != null && rowObject.ilbo_status_name != '') {
		      	var _style = getCodeStyle(rowObject.ilbo_status_code);
		      	
		      	str += '<div id="div_sta_' + rowObject.ilbo_seq + '" class="bt_on"><a onclick="javascript:void(0);"' + _style + '> ' + rowObject.ilbo_status_name + ' </a></div>';
			} else {
		      	str += '<div id="div_sta_' + rowObject.ilbo_seq + '" class="bt"><a onclick="javascript:void(0);">상태</a></div>';
		    }
		}
	
		return str;
	}
	
	function formatUseInfoBtn(cellvalue, options, rowObject) {
		var str = '';
		var rowId = options.rowId;
		
		if(typeof(rowObject.ilbo_seq) == 'undefined') {
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}

		if(rowObject.ilbo_seq > 0) {
			str += '<div class="bt_wrap">';
  		
			//현장 공개여부
			if(rowObject.ilbo_use_name != null && rowObject.ilbo_use_name != '') {
				var _style = getCodeStyle(rowObject.ilbo_use_code);
      		
				str += '<div id="div_use_' + rowObject.ilbo_seq + '" class="bt_on"><a onclick="javascript:void(0);"' + _style + '> ' + rowObject.ilbo_use_name + ' </a></div>';
			}else {
				str += '<div id="div_use_' + rowObject.ilbo_seq + '" class="bt"><a onclick="javascript:void(0);">미공개</a></div>';
			}
		}

		return str;
	}
	
	var optHTML = '';
	
	function fn_workerInfoOptBtn(ilbo_seq, work_seq, txt, event, output_status_code) {
		optHTML = "<div id=\"output_layer\" class=\"bt_wrap single\" style=\"width: 240px; display: none; background-color: #fff;\">";
		optHTML += optOupput.replace(/<<ILBO_SEQ>>/gi, ilbo_seq).replace(output_status_code +" bt", output_status_code +" bt_on");
		optHTML += clsHTML;
		optHTML += "<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('output_layer', '"+ ilbo_seq +"', 'output_status_code', 0, 'output_status_name', '' ,'0', '100');\"> 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ ilbo_seq +"', '100', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
		
		fn_selectOptIlboList('output_layer', work_seq, txt, event, optHTML);
	}
	
	function fn_selectOptIlboList(optId, seq, msg, e , optHTML) {
		var $opt_layer = $('#opt_layer');
		var _display;
	
		if ( !e ) e = window.Event;
		var pos = abspos(e);
	
		if ( $("#"+ optId).css("display") == 'block' ){
			closeOpt();
		}
		
		writeOpt2(optHTML);
	
		$opt_layer.css('left', (pos.x - 300) +"px");
		$opt_layer.css('top', (pos.y-20) +"px");
	
		$opt_layer.css('display', 'block');
		$("#"+ optId).css('display', 'block');
		
		$('#code_log_layer').html("");
		$('#code_log_layer').css('display', 'none');
	}
	
	function formatIncomeCodeBtn(cellvalue, options, rowObject) {
		var str = '';
		var rowId = options.rowId;
	  
		if(typeof(rowObject.ilbo_seq) == 'undefined') {
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
		
		if(rowObject.ilbo_seq > 0) {
			str += '<div class="bt_wrap">';
			
	    	if(rowObject.ilbo_income_code != null && rowObject.ilbo_income_code != '' && rowObject.ilbo_income_code > 0) {
	    		var _style = getCodeStyle(rowObject.ilbo_income_code);
	
	    		str += '<div id="div_income_' + rowObject.ilbo_seq + '" class="bt_on"><a onclick="javascript:void(0);"' + _style + '> ' + cellvalue + ' </a></div>';
			} else {
				str += '<div id="div_income_' + rowObject.ilbo_seq + '" class="bt"><a onclick="javascript:void(0);"> 상태 </a></div>';
	    	}
	
	    	str += '</div>';
		}
	
		return str;
	}
	
	function formatPayCodeBtn(cellvalue, options, rowObject) {
		var str = '';
		var rowId = options.rowId;
			
		if(typeof(rowObject.ilbo_seq) == 'undefined') {
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
			
		if(rowObject.ilbo_seq > 0) {
			str += '<div class="bt_wrap">';
		    	
			if(rowObject.ilbo_pay_code != null && rowObject.ilbo_pay_code != '' && rowObject.ilbo_pay_code > 0) {
				var _style = getCodeStyle(rowObject.ilbo_pay_code);
				
				str += '<div id="div_pay_' + rowObject.ilbo_seq + '" class="bt_on"><a onclick="javascript:void(0);"' + _style + '> ' + cellvalue + ' </a></div>';
			}else {
				str += '<div id="div_pay_' + rowObject.ilbo_seq + '" class="bt"><a onclick="javascript:void(0);"> 선택 </a></div>';
			}
				
			str += '</div>';
		}
		
		return str;
	}
</script>

<article>
	<div class="inputUI_simple mgbS">
		<table class="bd-form s-form" summary="등록일시, 직접검색 영역 입니다.">
			<caption>등록일시, 직접검색 영역</caption>
			<colgroup>
				<col width="150px" />
				<col width="750px" />
				<col width="120px" />
				<col width="*" />
			</colgroup>
			<tr>
				<th scope="row">출역일자</th>
				<td>
					<p class="floatL">
						<input type="text" id="start_ilbo_date" name="start_ilbo_date" class="inp-field wid100 datepicker" /> 
						<span class="dateTxt">~</span>
						<input type="text" id="end_ilbo_date" name="end_ilbo_date" class="inp-field wid100 datepicker" />
					</p>
					<div class="inputUI_simple">
						<span class="contGp btnCalendar"> 
							<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', 'all'); $('#btnSrh').trigger('click');" checked="checked"/>
							<label for="day_type_1" class="label-radio">전체</label>
							<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', 'today' ); $('#btnSrh').trigger('click');"/>
							<label for="day_type_2" class="label-radio">오늘</label> 
							<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', 'week'  ); $('#btnSrh').trigger('click');" />
							<label for="day_type_3" class="label-radio">1주일</label> 
							<input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', 'month' ); $('#btnSrh').trigger('click');" />
							<label for="day_type_4" class="label-radio">1개월</label> 
							<input type="radio" id="day_type_5" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', '3month'); $('#btnSrh').trigger('click');" />
							<label for="day_type_5" class="label-radio">3개월</label> 
							<input type="radio" id="day_type_6" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', '6month'); $('#btnSrh').trigger('click');" />
							<label for="day_type_6" class="label-radio">6개월</label>
						</span>
					</div>
				</td>
				<th scope="row" class="linelY pdlM">직접검색</th>
				<td>
					<div class="select-inner">
						<select id="srh_type" name="srh_type" class="styled02 floatL wid100">
							<option value="ilbo_worker_name">이름</option>
							<option value="ilbo_worker_phone">휴대폰</option>
						</select>
					</div> 											
					<input type="text" id="srh_text" name="srh_text" class="inp-field wid200 mglS" onKeyDown="if(event.keyCode == 13) $('#btnSrh').click();"/>
					<div class="btn-module floatL mglS">
						<a href="#none" id="btnSrh" class="search">검색</a>
					</div>
				</td>
			</tr>
		</table>
	</div>
</article>

<table id="list"></table>
<div id="opt_layer" style="position : absolute; display : none; z-index : 1; border : 1px solid #d7d7d7; background : #fcfcfc; color : #9b9b9b;"></div>
<div id="code_log_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b; padding:5px">NO Data</div>