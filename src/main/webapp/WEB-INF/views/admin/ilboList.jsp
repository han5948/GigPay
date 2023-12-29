<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<style>
.scrolltbody {
    display: block;
    width: 100%;
}
.scrolltbody tbody {
    display: block;
    height: 168px;
    overflow: auto;
}
.scrolltbody td:nth-of-type(1) { width: 8.1%; }
.scrolltbody td:nth-of-type(2) { width: 7%; }
.scrolltbody td:nth-of-type(3) { width: 7.9%; padding-left: 3%; }
.scrolltbody td:nth-of-type(4) { width: 10%; }
.scrolltbody td:nth-of-type(5) { width: 10%; padding-left: 3%; }
.scrolltbody td:nth-of-type(6) { width: 11.9%; padding-left: 3%; }
.scrolltbody td:nth-of-type(7) { width: 8.9%; padding-left: 5%; }
.scrolltbody td:nth-of-type(8) { width: 11.9%; padding-left: 3%; }
.scrolltbody td:nth-of-type(9) { width: 10.1%; padding-left: 0%; }
.scrolltbody td:nth-of-type(10) { width: 9.9%; padding-left: 0%; }
.scrolltbody td:nth-of-type(11) { width: 7.3%; padding-left: 1%; }

</style>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/ilbo.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/ilbo_fomat.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/jobCal.js?ver=1.0' />"	charset="utf-8"></script>

<script type="text/javascript">
	$(window).resize(function() {
    	$("#list").setGridHeight($(window).height() - optHeight);
	});

	var feeArray = ['400016', '400158', '400153', '400164', '400168', '400024', '400792', '400027'];

	var companySeq = "<c:out value='${companySeq}' />";
	var shareYn = "<c:out value='${shareYn}' />";
	var authLevel = "<c:out value='${authLevel}' />";
	var sessionCompanyName = "<c:out value='${sessionScope.ADMIN_SESSION.company_name }' />";
	var sessionCompanySeq = "<c:out value='${sessionScope.ADMIN_SESSION.company_seq }' />";
	var lastElement = '';
	
	// 코드정보 저장
	var mCode = new Map();
	var oCode = new Object();
//   oCode = mCode.get("500009");
	var isSelect = false;
	var srh_seq	= "<c:out value='${param.srh_seq}' />";
	var selectWorkerName = ""; 						//선택된 사람
	var confirm_opts = "0:미승인;1:승인";		// 사용유무 - 0:미사용 1:사용
	var use_opts = "1:사용;0:미사용";		//사용유무 - 0:미사용 1:사용
	var bf_opts = "0:미설정;1:조식;2:중식;3:석식;4:조식/중식;5:중식/석식;6:조식/중식/석식";	//조식유무 - 0:없음 1:있음
	var ilbotype_opts = ":ALL;O:사무실;R:일요청;A:APP;P:PUSH;C:일자리;S:시스템";
	var star_opts = "0:미평가;6:A;5:B;4:C;3:D;2:E;1:F";	//구인처가 구직자평가 등급
	var grade_opts = "0:미평가;5:A;4:B;3:C;2:D;1:F";		//구직자가 구인처 평가 등급
	var work_days_opts = "1:1일;2:2일;3:3일;4:4일;5:5일;6:6일;7:7일;8:8일 이상";
	var optOupput = "";                   // 출역상태       code_type = 100
	var optWorkerStatus = "";					// 구직자 상태 code_type = WSC
	var optKind = "";                   // 직종, 전문분야 code_type = 400
	var optIncome = "";                   // 노임수령       code_type = 300
	var optPay = "";                   // 노임지급       code_type = 200
	var optOrder = "";                   // 현장시간       code_type = 500
	var optWorkOrder = ":ALL";
	var optUse = "";	//공개여부 code_type = 'USE'
	var optSta = ""; 	//상태여부 code_type = 'STA'
	var param = "<c:out value='${srh_type}' />";
	var laborContract = '${laborContract }';
  	var receiveContract = '${receiveContract }';
	var careerOptions = '${careerOptions }';
	var careerDTO = '${careerDTO }';
	var selCareer = '';
	var selCareerName = '';
	var beforeWorkerSeq = '';
	var beforeWorkerCompanySeq = '';
	var parking_opts = "0:주차불가;1:주차가능;2:주차비지급;3:유류비지급";
	
	//간략보기 에서 사용 된다.
	if(authLevel == '0') {
		var orgWidth = 4470 + 740;
		var smallWidth = '${smallWidth}' * 1;
		smallWidth += 740;
	}else {
		var orgWidth 	= 4470;
		var smallWidth	= '${smallWidth}' * 1;
	}

	var optHeight = 270;

	$(function() {
		$("#end_ilbo_date").val(toDay);
		setDayType(1, 'to_ilbo_date', 'day');
	  
/* 	$("input:radio[name=srh_use_yn]").click( function() {
		$("input:radio[name=srh_use_yn]").removeAttr('checked');
		$("input:radio[name=srh_use_yn]").prop('checked', false);

		$(this).prop('checked', true);
	});
	   */
		$("#copy_worker").change( function() {
			$("input[name=copy_rows]").val("1");
			$("input[name=copy_rows]").attr("readonly", false);
			$("input[name=copy_rows]").css("background-color", "#FFF");
			$("input[name=copy_rows]").css("color", "#444");
		});
	  
		<%-- // 일일대장 / 구인대장 / 구작대장 구분 --%>
		<c:if test="${param.ilboView eq 'Y'}">
			//전후 1개월
			$("#day_type_7").trigger('click');

			$("#srh_text").autocomplete({
				source : function (request, response) {
		    		$.ajax({
		        		url: "${(param.srh_ilbo_type eq 'i.worker_seq') ? '/admin/getWorkerNameList' : '/admin/getWorkNameList'}", type: "POST", dataType: "json",
		            	data: { term: $("#srh_text").val(), srh_use_yn: 1, srh_del_yn: 0, srh_distinct_yn: 1 },
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
			  		<c:if test="${param.srh_ilbo_type eq 'i.worker_seq'}">
						srh_seq = ui.item.worker_seq;
		  			</c:if>
		  			<c:if test="${param.srh_ilbo_type eq 'i.work_seq'}">
						srh_seq = ui.item.work_seq;
		  			</c:if>
		  			<c:if test="${param.srh_ilbo_type eq 'i.employer_seq'}">
						srh_seq = ui.item.employer_seq;
		  			</c:if>
					$("#srh_seq").val(srh_seq);
		        	$("#srh_text").val(ui.item.label);
		        	selectWorkerName = ui.item.label;
				}
			});
		</c:if>
	
		var jobArray;
		//코드 데이터 가져오기
		$.ajax({
			type : "POST",
			url	: "/admin/getJobCodeList",
			data : {"use_yn": "1", "del_yn": "0", "job_rank": 1, "pagingYn": "N", "orderBy": "job_kind, job_order"},
			dataType: 'json',
			success: function(JobList) {
				if ( JobList != null && JobList.length > 0 ) {
					var tempJobKind = "1";
			
					jobArray = JobList;
					jobListCode = JobList;
					
					for ( var i = 0; i < JobList.length; i++ ) {
						if(tempJobKind != JobList[i].job_kind){
							optKind  += "<div style='clear: both;width:100%;height:10px;'></div>";
							tempJobKind = JobList[i].job_kind;
						}
							
						optKind  += "<div class=\""+ JobList[i].job_code +" bt\"><a href=\"JavaScript:chkCodeUpdate('kind_layer', '<<ILBO_SEQ>>', 'ilbo_kind_code', '"+  JobList[i].job_code +"', 'ilbo_kind_name', '"+  JobList[i].job_name +"', '" +JobList[i].job_kind+"', '400');\"> "+ JobList[i].job_name +"</a></div>";
						mCode.put(JobList[i].job_code, {name:JobList[i].job_name});
						
					}
					
					optKind  += "<div style='clear: both;width:100%;height:10px;'></div>";
				}

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
					            	optOupput += "<div class=\""+ data[i].code_value +" bt\"><a href=\"JavaScript:chkCodeUpdate('output_layer', '<<ILBO_SEQ>>', 'output_status_code', '"+  data[i].code_value +"', 'output_status_name', '"+  data[i].code_name +"', '" +data[i].code_group+"', '" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
					            }else if(data[i].code_type == 'WSC') {
					            	optWorkerStatus += "<div class=\""+ data[i].code_value +" bt\"><a href=\"JavaScript:chkCodeUpdate('wsc_layer', '<<ILBO_SEQ>>', 'ilbo_worker_status_code', '"+  data[i].code_value +"', 'ilbo_worker_status_name', '"+  data[i].code_name +"', '" +data[i].code_group+"', '" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
								
								} else if ( data[i].code_type == 300 ) {   // 노임수령
					            	optIncome += "<div class=\"bt\" style=\"width: 50px;\"><a href=\"JavaScript:chkCodeUpdate('income_layer', '<<ILBO_SEQ>>', 'ilbo_income_code', '"+  data[i].code_value +"', 'ilbo_income_name', '"+  data[i].code_name + "', '" +data[i].code_group+"', '" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
					//              optIncome += "<div class=\""+ data[i].code_value +" bt\"><a href=\"JavaScript:chkCodeUpdate('income', '<<ILBO_SEQ>>', 'ilbo_income_code', '"+  data[i].code_value +"', 'ilbo_income_name', '"+  data[i].code_name +"');\"> "+ data[i].code_name +" </a></div>";
					            } else if ( data[i].code_type == 200 ) {   // 노임지급
					            	optPay    += "<div class=\"bt\"  style=\"width: 50px;\"><a href=\"JavaScript:chkCodeUpdate('pay_layer', '<<ILBO_SEQ>>', 'ilbo_pay_code', '"+  data[i].code_value +"', 'ilbo_pay_name', '"+  data[i].code_name +"', '" +data[i].code_group+"', '" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
								} else if ( data[i].code_type == 500 ) {   // 현장시간
					            	optOrder  += "<div class=\"bt\"><a href=\"JavaScript:chkCodeUpdate('order_layer', '<<ILBO_SEQ>>', 'ilbo_order_code', '"+  data[i].code_value +"', 'ilbo_order_name', '"+  data[i].code_name +"', '" +data[i].code_group+"', '" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
					//              optOrder  += "<div class=\""+ data[i].code_value +" bt\"><a href=\"JavaScript:chkCodeUpdate('order', '<<ILBO_SEQ>>', 'ilbo_order_code', '"+  data[i].code_value +"', 'ilbo_order_name', '"+  data[i].code_name +"');\"> "+ data[i].code_name +" </a></div>";
								}else if(data[i].code_type == 'USE') {	
					            	optUse += "<div id =\""+data[i].code_value+"\" class=\"bt\"><a href=\"JavaScript:chkCodeUpdate('use_layer', '<<ILBO_SEQ>>', 'ilbo_use_code', '"+  data[i].code_value +"', 'ilbo_use_name', '"+  data[i].code_name +"', '" +data[i].code_group+"', '" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
								}else if(data[i].code_type == 'STA') {
					            	optSta += "<div class=\"bt\"><a href=\"JavaScript:chkCodeUpdate('sta_layer', '<<ILBO_SEQ>>', 'ilbo_status_code', '"+  data[i].code_value +"', 'ilbo_status_name', '"+  data[i].code_name +"', '" +data[i].code_group+"', '" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
								}else if(data[i].code_type == 'ORD') {	// 오더 종류
									/* 레이어로 띄울떼 */			
					            	// optWorkOrder += "<div class=\"bt\"><a href=\"JavaScript:chkCodeUpdate('workOrder', '<<ILBO_SEQ>>', 'ilbo_work_order_code', '"+  data[i].code_value +"', 'ilbo_work_order_name', '"+  data[i].code_name +"' ,'" +data[i].code_group+"','" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
					            	/* selectBox로*/
					            	if(optWorkOrder != ""){
					            		optWorkOrder += ";"
					            	}
					            	optWorkOrder +=  data[i].code_value +":"+  data[i].code_name;
									
					            
								}
					        	
					            oCode = new Object();
					
					            oCode.bgcolor	= data[i].code_bgcolor;
					            oCode.color   	= data[i].code_color;
					            oCode.name   = data[i].code_name;
					
					            mCode.put(data[i].code_value, oCode);
							}
					            	
					        optKind  += "<div class= bt style='clear: both;width:100%;height:15px;'></div>";
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
				}).done(function() {
					var lastsel = -1;            //편집 sel
					var ilboStatus = '${sessionScope.SEARCH_OPT.ilboStatusOpt }';
					var ilboOpt = '${sessionScope.SEARCH_OPT.ilboOpt }';
					var ilboSelOpt = '${sessionScope.SEARCH_OPT.ilboSelOpt }';
					var ilboSearchText = '${sessionScope.SEARCH_OPT.ilboSearchText }';
					
						// jqGrid 생성
						$("#list").jqGrid({
							url	: "/admin/getIlboList",
							datatype : "json",                               // 로컬그리드이용
							mtype : "POST",
							postData : {
								<%-- // 일일대장 / 구인대장 / 구작대장 구분 --%>
								<c:choose>
									<c:when test="${param.ilboView eq 'Y'}">
										start_ilbo_date : $("#start_ilbo_date").val(),
								        end_ilbo_date : $("#end_ilbo_date").val(),
								        srh_type : "<c:out value='${param.srh_ilbo_type}' />",
								        srh_seq	: srh_seq,
										srh_use_yn : 1,
								    </c:when>
								    <c:otherwise>
								    	start_ilbo_date : toDay,
								        end_ilbo_date : toDay,
								        srh_type : $("#srh_type option:selected").val(),
								        srh_option : $("#srh_option option:selected").val(),
								        srh_use_yn : $("#srh_use_yn option:selected").val(),
								        srh_worker_request : $("#srh_worker_request").is(":checked") ? "1" : "0", 
								        srh_text : $("#srh_text").val(),
								    </c:otherwise>
								</c:choose>
								page: 1
							},
							<c:choose>
								<c:when test="${param.ilboView eq 'Y'}">
							  		sortname : "ilbo_date",
							  	</c:when>
							  	<c:otherwise>
								    sortname : "work_company_seq, ilbo_order_code, employer_seq, work_seq, ilbo_seq ",
							  	</c:otherwise>
							</c:choose>
							gridview : true,
							sortorder : "desc",
							//width			: smallWidth,
							height : $(window).height() - optHeight,
							scrollerbar	: true,
							//  rowList		: [20, 30, 50],                         // 한페이지에 몇개씩 보여 줄건지?               
							//  autowidth	: true,
							footerrow : true,
							userDataOnFooter : true,
							rowNum : -1,
							pager : '#paging',                            // 네비게이션 도구를 보여줄 div요소
							viewrecords : true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무
							multiselect	: true,
							multiboxonly : true,
							caption	: "${(param.ilboView ne 'Y') ? '일일대장' : (param.srh_ilbo_type eq 'i.worker_seq') ? '구직대장' : '구인대장'}",                 // 그리드타이틀
							rownumbers : true,                                 // 그리드 번호 표시
							rownumWidth : 40,                                   // 번호 표시 너비 (px)
							cellEdit : true ,
							
							cellsubmit : "remote" ,
							cellurl	: "/admin/setIlboInfo",              // 추가, 수정, 삭제 url
							jsonReader : {
								id : "ilbo_seq",
							    repeatitems : false
							},
							// 컬럼명
							colNames: ['일보순번', '대장소유지점', '구직자소유지점', '현장소유지점', '구직자순번', '매니저순번', '구인처순번', '현장순번'
								, '일자', '일일대장지점'
								// 구직자 정보
							    , '대장', '구직자소속지점', '구직자소유', '상세', '전체', '구직자', '특징', '추가 특징', '출역상태 코드', '출역상태 명'
							    , '구직자 성별', '구직자 핸드폰', '구직자 주소', '구직자 주소좌표', '구직자 주소 (모바일)', '구직자 주소좌표 (모바일)', '구직자 주민번호'
							    , '구직자 전문분야 코드', '구직자 전문분야 명', '구직자 바코드', '구직자 메모', '배정', '동반자'
							    , '구직자 은행코드', '구직자 은행명', '구직자 계좌번호', '구직자 예금주'
							    , '구직자 혈압', '구직자 교육이수 여부', '구직자 주민등록증 스캔 여부', '구직자 이수증 스캔 여부', '통장 스캔 여부'
							    , '구직자 일가자 탈퇴 유무', '구직자 작업 예약 상태', '구직자 앱 사용 유무', '구직자 나이'
							    , '구직자 정보', '상태', '일보메모'
							    , '대장', '상세', '구인처', '특징' 
							    , '순서코드', '순서명', '공개코드', '공개명', '상태코드', '상태명', '구직자상태코드', '구직자상태명'
							    , '매니저지점', '소유', '현장매니저명', '현장매니저 번호'
							    , '대장', '현장', '출역일수', '지도', '위치', '현장 주소', '현장 시/도', '현장 시/구/군', '현장 읍/면/동', '현장 번지수', '현장 좌표', '주소설명', '순서', '상태', '오더종류명', '오더종류', '공개', 'AI배정 코드', 'AI배정 명', 'AI배정','식사제공', '작업일수', '도착시간', '마감시간', '휴게시간', '직종', '직종 코드', '직종별 구인자 수수료', '직종별 구인처 수수료', '직종상세명', '직종상세코드', '직종옵션명', '직종옵션코드', '직종상세', '구인상세', '소장메모', '경력옵션명', '경력옵션 사용 순번', '최저나이', '최고나이', '혈압', '주차여부', '대리수령 양식', '대리수령 양식명', '근로계약 양식', '근로계약 양식명'
							    , '현장담당', '현장 담당자 팩스', '현장전화', '현장 담당자 이메일'
							    , '노임수령 코드', '노임수령', '수령일자', '단가', '수수료', '쉐어료', '공제금', '상담사', '상담사수수료율', '구직자', '구직자코드'
							    , '공제액 계', '상세', '임금수령액'
							    , '소개비수령', '지급일자', '구인자 소개요금', '구직자 소개요금', '소개요금 정보', '구직자 소개요금'
							    , '입출금 메모', '승인', '본사 평가', '현장 평가', '구직자 평가', '대리수령', '근로계약', '구직자 코멘트', '구직자 순번', '상태', '등록일시', '등록자', '소유자', '수정일자', '상태수정일자', '서명 플래그 히든', '메니져접속타입'],
							colModel: [
								{name: 'ilbo_seq', index: 'ilbo_seq', key: true, width: 80,	hidden: authLevel == 0 ? false : true },
							    {name: 'company_seq', index: 'company_seq', width: 80, hidden: authLevel == 0 ? false : true, editable: authLevel == 0 ? true : false },
							    {name: 'worker_company_seq', index: 'worker_company_seq', width: 100, hidden: authLevel == 0 ? false : true },
							    {name: 'work_company_seq', index: 'work_company_seq', width: 100, hidden: authLevel == 0 ? false : true },
							    {name: 'worker_seq', index: 'worker_seq', width: 80, hidden: authLevel == 0 ? false : true},
							    {name: 'manager_seq', index: 'manager_seq', width: 80, hidden: authLevel == 0 ? false : true},
							    {name: 'employer_seq', index: 'employer_seq', width: 80, hidden: authLevel == 0 ? false : true},
							    {name: 'work_seq', index: 'work_seq', width: 80, hidden: authLevel == 0 ? false : true},
								{name: 'ilbo_date', index: 'ilbo_date', align: "center", width: ${ilboSettingDTO.ilbo_date_width}, sortable: true, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_date eq '1' ? false : true }
									, editable: true, edittype: 'text', editrules: { required: true },
							        	editoptions: {
							            	size: 15, maxlength: 10
							                , dataInit: function(element) {
							                	$(element).datepicker({ dateFormat: 'yy-mm-dd', constraintInput: false }); // ,showOn: 'button', buttonText: 'calendar'
							                }
							            }
							            , searchoptions: {sopt: ['cn', 'eq', 'nc']}
							    },
							    {name: 'company_name', index: 'company_name', width: ${ilboSettingDTO.company_name_width} , sortable: true, align: "left", hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.company_name eq '1' ? false : true}
							    	, editable: true, edittype: "text"
							        , editoptions: {size: 30, dataInit: fn_editOtions_companyName }
							        , editrules: {edithidden: true, required: false, custom: true, custom_func: validCompanyName}
							        , searchoptions: {sopt: ['cn', 'eq', 'nc']}
								},
							    
							    {name: 'worker_ilbo', index: 'worker_ilbo', sortable: false, align: "left", width: ${ilboSettingDTO.worker_ilbo_width}, search: false, formatter: formatWorkerIlbo
									/* ,cellattr: cellattrWorkerCompanyColor */, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.worker_ilbo eq '1' ? false : true }		 	 
								},
								{name: 'worker_company_name', index: 'worker_company_name', align: "left", width: ${ilboSettingDTO.worker_company_name_width}, sortable: true, search: true
							    	, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.worker_company_name eq '1' ? shareYn eq "0" ? true : false : true}
							        , editable: true
							        , edittype: "text"
							        , editoptions: {size: 30, dataInit: fn_editOtions_workerCompanyName	}
									, editrules: {custom: true, custom_func: validWorkerCompanyName}
									, searchoptions: {sopt: ['cn', 'eq', 'nc']}
									, cellattr: cellattrWorkerCompanyColor	 
								},
								{name: 'worker_owner', index: 'worker_owner', align: "center", width: ${ilboSettingDTO.worker_owner_width}, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
									, cellattr: cellattrWorkerCompanyColor, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.worker_owner eq '1' ? false : true }	 
								},	//구직자 소유
								{name: 'ilbo_worker_view', index: 'ilbo_worker_view', sortable: false, align: "left", width: ${ilboSettingDTO.ilbo_worker_view_width}
									, formatter: formatWorkerView
									, cellattr: cellattrWorkerCompanyColor
									, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_view eq '1' ? false : true }		 	 
								},	//상세
								{name: 'worker_all', index: 'worker_all', hidden: true}, 
								{name: 'ilbo_worker_name', index: 'ilbo_worker_name', align: "left", width: ${ilboSettingDTO.ilbo_worker_name_width}, sortable: true, search: true
									, editable: true, edittype: "text"
							        , editoptions: {size: 30, dataInit: fn_editOtions_ilboWorkerName}
							        , editrules: {custom: true, custom_func: validWorkerName}
							        , searchoptions: {sopt: ['cn', 'eq', 'nc']}
							        , cellattr: function(rowId, tv, rowObject, cm, rdata) {
							        	return getCodeBGStyle(rowObject.ilbo_pay_code, false);	/* 수수료 입금 여부에따라 배경색 달라짐. 흰색은 받았다는 뜻 */
									}
							        , hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_name eq '1' ? false : true}	
								},
								{name: 'ilbo_worker_feature', index: 'ilbo_worker_feature', align: "left", width: ${ilboSettingDTO.ilbo_worker_feature_width}
									, editable: true, sortable: false, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}, edittype: "textarea", hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_feature eq '1' ? false : true}	},
								{name: 'ilbo_worker_add_memo', index: 'ilbo_worker_add_memo', editable: false, sortable: false, search: false},
								{name: 'output_status_code', index: 'output_status_code', hidden: true }, // align:  "left", search: false}, //,
							    {name: 'output_status_name', index: 'output_status_name', hidden: true},
							    // 구직자 정보
							    {name: 'ilbo_worker_sex', index: 'ilbo_worker_sex', hidden: true},
							    {name: 'ilbo_worker_phone', index: 'ilbo_worker_phone', hidden: true},
							    {name: 'ilbo_worker_addr', index: 'ilbo_worker_addr', hidden: true},
							    {name: 'ilbo_worker_latlng', index: 'ilbo_worker_latlng', hidden: true},
							    {name: 'ilbo_worker_ilgaja_addr', index: 'ilbo_worker_ilgaja_addr', hidden: true},
							    {name: 'ilbo_worker_ilgaja_latlng', index: 'ilbo_worker_ilgaja_latlng', hidden: true},
							    {name: 'ilbo_worker_jumin', index: 'ilbo_worker_jumin', hidden: true},
							    {name: 'ilbo_worker_job_code', index: 'ilbo_worker_job_code', hidden: true},
							    {name: 'ilbo_worker_job_name', index: 'ilbo_worker_job_name', hidden: true},
							    {name: 'ilbo_worker_barcode', index: 'ilbo_worker_barcode', hidden: true},
								{name: 'ilbo_worker_memo', index: 'ilbo_worker_memo', align: "left", width: ${ilboSettingDTO.ilbo_worker_memo_width}, editable: true, sortable: false, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}, edittype: "textarea", hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_memo eq '1' ? false : true}	},
							    {name: 'ilbo_assign_type', index: 'ilbo_assign_type', align: "center", width: ${ilboSettingDTO.ilbo_assign_type_width}
									, stype:"select"
									, editable: authLevel == 0 ? true : false	/* nemojjang 최고 관리자만 수정 가능 하도록 */
									, sortable: true
									, edittype: "select"
									, editoptions: {value: ilbotype_opts}   
							    	, formatter: "select"
							    	, editoptions: {value: ilbotype_opts, dataEvents: [{
										type: 'change',
							            fn: function(e) {               // 값 : this.value || e.target.val()
											if ( this.value == "O" ) {
							                	$(this).parent().css('background', '');
											} else if ( this.value == "A" ) {
							                	$(this).parent().css('background', '#5aa5da');
											} else if ( this.value == "S" ) {
							                	$(this).parent().css('background', '#81d80c');
											} else if ( this.value == "P" ) {
							                   	$(this).parent().css('background', '#ffc1c1');
											} else if ( this.value == "C" ) {
							                   	$(this).parent().css('background', '#1DE9B6');
											} else if ( this.value == "R" ) {
							                   	$(this).parent().css('background', '#FFFF00');
											}
										},
									}]}
							        , cellattr: function(rowId, tv, rowObject, cm, rdata) {
							        	// rowObject 변수로 그리드 데이터에 접근
							            if ( tv == 'O' ) {
							            	return 'style="background: #ffffff; text-align: center;"';
										}else if ( tv == 'A' ) {
							            	return 'style="background: #5aa5da; text-align: center;"';
										}else if ( tv == 'S' ) {
							            	return 'style="background: #81d80c; text-align: center;"';
										}else if ( tv == 'P' ) {
							            	return 'style="background: #ffc1c1; text-align: center;"';
										}else if ( tv == 'C' ) {
							            	return 'style="background: #1DE9B6; text-align: center;"';
										}else if ( tv == 'R' ) {
							            	return 'style="background: #FFFF00; text-align: center;"';
										}
									}
									, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_assign_type eq '1' ? false : true}	
								},
								{name: 'ilbo_companion_info', index: 'ilbo_companion_info', align:"left", sortable: false, search: false, formatter: formatCompanion, width: 30}, /* 동반자 정보 */
							    {name: 'ilbo_worker_bank_code', index: 'ilbo_worker_bank_code', hidden: true},
							    {name: 'ilbo_worker_bank_name', index: 'ilbo_worker_bank_name', hidden: true},
							    {name: 'ilbo_worker_bank_account', index: 'ilbo_worker_bank_account', hidden: true},
							    {name: 'ilbo_worker_bank_owner', index: 'ilbo_worker_bank_owner', hidden: true},
							    {name: 'ilbo_worker_blood_pressure', index: 'ilbo_worker_blood_pressure', hidden: true},
							    {name: 'ilbo_worker_OSH_yn', index: 'ilbo_worker_OSH_yn', hidden: true},
							    {name: 'ilbo_worker_jumin_scan_yn', index: 'ilbo_worker_jumin_scan_yn', hidden: true},
							    {name: 'ilbo_worker_OSH_scan_yn', index: 'ilbo_worker_OSH_scan_yn', hidden: true},
							    {name: 'ilbo_worker_bankbook_scan_yn', index: 'ilbo_worker_bankbook_scan_yn', hidden: true},
							    {name: 'ilbo_worker_app_status', index: 'ilbo_worker_app_status', hidden: true},
							    {name: 'ilbo_worker_reserve_push_status', index: 'ilbo_worker_reserve_push_status', hidden: true},
							    {name: 'ilbo_worker_app_use_status', index: 'ilbo_worker_app_use_status', hidden: true},
							    {name: 'ilbo_worker_age', index: 'ilbo_worker_age', hidden: true},
								{name: 'ilbo_worker_info', index: 'output_status_name', align: "left"
							    	, width: ${ilboSettingDTO.ilbo_worker_info_width}
							    	, search: true
							    	, searchoptions: {sopt: ['eq']}
							    	, sortable: false
							    	/* , stype: "select"
								    , editoptions:{value:":ALL;미공개:미공개;전체:전체;지점:지점"} */
									, formatter: formatWorkerInfo
									, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_info eq '1' ? false : true}},
							    {name: 'ilbo_worker_status_info', index: 'ilbo_worker_status_info', align: "center", width: ${ilboSettingDTO.ilbo_worker_status_info_width}, sortable: false, search: false, formatter: formatWorkerStatusInfo, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_worker_status_info eq '1' ? false : true} },
								{name: 'ilbo_memo', index: 'ilbo_memo', align: "left", width: ${ilboSettingDTO.ilbo_memo_width}, editable: true, sortable: false, edittype: "textarea", search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_memo eq '1' ? false : true}},
								// 구직자 정보 끝
								
								{name: 'employer_ilbo', index: 'employer_ilbo', align: "left", width: ${ilboSettingDTO.employer_ilbo_width}, sortable: false, search: false, formatter: formatEmployerIlbo
							    	/* ,cellattr: cellattrWorkerCompanyColor */, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.employer_ilbo eq '1' ? false : true}		 
								},
								{name: 'ilbo_employer_view', index: 'ilbo_employer_view', align: "left", width: ${ilboSettingDTO.ilbo_employer_view_width}, sortable: false, search: false, formatter: formatEmployerView
							    	/* ,cellattr: cellattrWorkerCompanyColor */, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_employer_view eq '1' ? false : true}			 
								},
							    
							    {name: 'employer_name', index: 'employer_name', align: "left", width: ${ilboSettingDTO.employer_name_width}, sortable: true
									, editable: true, edittype: "text"
									, editoptions: {size: 30, dataInit: fn_editOtions_employerName	}
							    	, editrules: {custom: true, custom_func: validWorkName}
							    	, searchoptions: {sopt: ['cn', 'eq', 'nc']}
							    	, cellattr: function(rowId, tv, rowObject, cm, rdata) {
										return getCodeBGStyle(rowObject.ilbo_income_code, false);
									}
							    	, formatter: formatSelectName
							        , hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.employer_name eq '1' ? false : true}	
								},
								{name: 'ilbo_employer_feature', index: 'ilbo_employer_feature', align: "left", width: ${ilboSettingDTO.ilbo_employer_feature_width}
									, editable: false, sortable: false, search: true, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_employer_feature eq '1' ? false : true}
								},
								
								{name: 'ilbo_order_code', index: 'ilbo_order_code ', hidden: true},
							    {name: 'ilbo_order_name', index: 'ilbo_order_name ', hidden: true},
							    
							    {name: 'ilbo_use_code', index: 'ilbo_use_code ', hidden: true},
							    {name: 'ilbo_use_name', index: 'ilbo_use_name ', hidden: true},
							    
							    {name: 'ilbo_status_code', index: 'ilbo_status_code ', hidden: true},
							    {name: 'ilbo_status_name', index: 'ilbo_status_name ', hidden: true},
							    
							    {name: 'ilbo_worker_status_code', index: 'ilbo_worker_status_code', hidden: true},
							    {name: 'ilbo_worker_status_name', index: 'ilbo_worker_status_name', hidden: true},
							    
							    
							    {name: 'work_company_name', index: 'work_company_name', align: "left", width: ${ilboSettingDTO.work_company_name_width}, sortable: true, search: false
							    	, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.work_company_name eq '1' ? shareYn eq "0" ? true : false : true}
							    	/* editable: true,
							        edittype: "text",
							        editoptions: {size: 30, dataInit: fn_editOtions_workCompanyName	},
									editrules: {custom:  true, custom_func: validWorkCompanyName}, */
									, search: true
									, searchoptions: {sopt: ['cn', 'eq', 'nc']}
							        , cellattr: cellattrWorkerCompanyColor	 
								},

								{name: 'work_owner', index: 'work_owner', align: "center", width: ${ilboSettingDTO.work_owner_width}, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
							    	, cellattr: cellattrWorkerCompanyColor , hidden: ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.work_owner eq '1' ? false : true}		
								},
								{name: 'manager_name', index: 'manager_name', search: false, width: ${ilboSettingDTO.manager_name_width}, editable: false, hidden: ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.manager_name eq '1' ? false : true}
								, cellattr: function(rowId, tv, rowObject, cm, rdata) {
							        if(rowObject.manager_use_status > 0 ){
							        	return "style='color:red;'";
							        }
								}},
								{name: 'manager_phone', index: 'manager_phone', search: false, width: ${ilboSettingDTO.manager_phone_width}, editable: false, hidden: ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.manager_phone eq '1' ? false : true}},
							    
								// 현장정보
							    {name: 'work_ilbo', index: 'work_ilbo', align: "left", width: ${ilboSettingDTO.work_ilbo_width}, sortable: false, search: false, formatter: formatWorkIlbo, hidden : ${ilboSettingDTO.work_ilbo eq '1' ? false : true}},
							    
							    {name: 'ilbo_work_name', index: 'ilbo_work_name', align: "left", width: ${ilboSettingDTO.work_name_width}, sortable: true,	editable: true, edittype: "text"
									, editoptions: {size: 30, dataInit: fn_editOtions_workName }
							        , editrules: {custom: true, custom_func: validWorkName}
							        , searchoptions: {sopt: ['cn', 'eq', 'nc']}
							        , cellattr: function(rowId, tv, rowObject, cm, rdata) {
								    	//현장의 출석 일수 에 따라 배경색을 다르게 한다.
								        var count =  rowObject.day_count;
								        if(count ==  2){
								        	return 'style="background-color: #a7e67b"'; //; color:#"';	 
										}else if(count >= 3){
								        	return 'style="background-color: #e6c57b"'; //; color:#"';
										}else{
								        	return false;
										}
									}, hidden: ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.work_name eq '1' ? false : true}	
								},
								{name: 'day_count', index: 'day_count', hidden: true},
								
							    {name: 'addr_edit', index: 'addr_edit', align: "left", width: ${ilboSettingDTO.addr_edit_width}, sortable: false, search: false, formatter: formatAddrEdit, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.addr_edit eq '1' ? false : true}},
							    {name: 'addr_location', index: 'addr_location', align: "left", width: ${ilboSettingDTO.addr_location_width}, sortable: false, search: false, formatter: formatAddrLocation, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.addr_location eq '1' ? false : true}},
							    {name: 'ilbo_work_addr', index: 'ilbo_work_addr', hidden: true},
							    {name: 'work_sido', index: 'work_sido', hidden: true},
							    {name: 'work_sigugun', index: 'work_sigugun', hidden: true},
							    {name: 'work_dongmyun', index: 'work_dongmyun', hidden: true},
							    {name: 'work_rest', index: 'work_rest', hidden: true},
							    {name: 'ilbo_work_latlng', index: 'ilbo_work_latlng', hidden: true},
							    {name: 'ilbo_work_addr_comment', index: 'ilbo_work_addr_comment', hidden: true },
							    {name: 'ilbo_work_info', index: 'ilbo_work_info', align: "left", width: ${ilboSettingDTO.ilbo_work_info_width}, search: false, formatter: formatWorkInfo, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_info eq '1' ? false : true}},
							    {name: 'ilbo_status_info', index: 'ilbo_status_info', align: "left", width: ${ilboSettingDTO.ilbo_status_info_width}, search: false, formatter: formatStaInfo, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_status_info eq '1' ? false : true}},
							    
							    
							    {name: 'ilbo_work_order_name', index: 'ilbo_work_order_name', hidden: true },
							    
							
							    {name: 'ilbo_work_order_code', index: 'ilbo_work_order_code', align: "center", width: ${ilboSettingDTO.ilbo_work_order_name_width}
									, stype: "select"
							    	, cellattr: cellattrWorkOrderColor
							    	, editable: true
							    	, edittype: "select"
							    	, formatter: "select" //formatWorkOrderInfo
							    	, editoptions: {value: optWorkOrder, dataEvents: [{
										type: 'change'
										, fn: function(e) {               // 값 : this.value || e.target.val()
											$(this).parent().css('background', getCodeBgColor(this.value));
								            $(this).parent().css('color', getCodeColor(this.value));
							    		
								            $("#list").jqGrid('setCell', lastsel, 'ilbo_work_order_name', event.target.selectedOptions[0].text,	'',true);
								             //   	$("#list").jqGrid("saveCell",lastsel,'ilbo_work_order_name');
										},
									}]}
									, sortable: true
							    	, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_order_name eq '1' ? false : true}	 
							    },
							    
								{name: 'ilbo_use_info', index: 'ilbo_use_info', align: "left", width: ${ilboSettingDTO.ilbo_use_info_width}		
							    		/* , search: true
							    		, searchoptions: {sopt: ['cn','eq','nc']} */
									, stype: "select"
								    , editoptions:{value:":ALL;미공개:미공개;전체:전체;지점:지점"}
							    	, formatter: formatUseInfo
							    	, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_use_info eq '1' ? false : true}	
								},
								{name: 'auto_status', index: 'auto_status', hidden: true}
								, {name: 'auto_status_name', index: 'auto_status_name', hidden: true}
								, {name: 'auto_status_info', index: 'auto_status_info', align: "left", stype:"select", width: ${ilboSettingDTO.ilbo_use_info_width}		
									, searchoptions:{value:":전체;null:대기◎;0:시작▶;1:배정중◐;2:완료●;3:중지▣;4:실패X"}
 									//, editoptions:{value:"중지▣:중지▣;시작▶:시작▶;배정중◐:배정중◐;완료●:완료●;실패X:실패X;"}
							    	, formatter: formatAutoStatusInfo
							    	, hidden: ${sessionScope.ADMIN_SESSION.auto_company_use_yn } == '1' ? false : true
								},
							    {name: 'ilbo_work_breakfast_yn', index: 'ilbo_work_breakfast_yn', align: "center", width: ${ilboSettingDTO.ilbo_work_breakfast_yn_width}
									, search: false
									, editable: true
									, sortable: false
									, edittype: "select", editoptions: {value: bf_opts}   , formatter: "select"
									, editoptions: {value: bf_opts, dataEvents: [{
										type: 'change'
										, fn: function(e) {               // 값 : this.value || e.target.val()
							            	if ( this.value == "0" ) {
							                	$(this).parent().css('background', '');
											} else if ( this.value == "1" ) {
							                	$(this).parent().css('background', '#5aa5da');
											}
										},}]
									}
									, cellattr: function(rowId, tv, rowObject, cm, rdata) {
							        	// rowObject 변수로 그리드 데이터에 접근
							            if ( tv == '1' ) {
							            	return 'style="background: #5aa5da; text-align: center;"';
										}
									}
									, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_breakfast_yn eq '1' ? false : true}	
								},
								{name: 'work_day', index: 'work_day', search: true, width: 70, align: "center", sortable: true, editable: true
									, searchoptions: {sopt: ['eq', 'le', 'ge']}
									, edittype: "select", editoptions: {value:work_days_opts}, formatter: "select"
									, editoptions: {value: work_days_opts, dataEvents: [{
										type: 'change'
										, fn: function(e) {               // 값 : this.value || e.target.val()
							            	if ( this.value > 1 ) {
							                	$(this).parent().css('background', '#ffc1c1');
											} else {
							                	$(this).parent().css('background', '');
											}
										}}]
									}
									, cellattr: function(rowId, tv, rowObject, cm, rdata) {
							        	// rowObject 변수로 그리드 데이터에 접근
							            if ( tv > 1 ) {
							            	return 'style="background: #ffc1c1;"';
										}
									}
								},
								{name: 'ilbo_work_arrival', index: 'ilbo_work_arrival', align: "left", width: ${ilboSettingDTO.ilbo_work_arrival_width}, editable: true, sortable: true, search: false, searchoptions: {sopt: ['cn', 'eq', 'nc']}
									, editrules: {custom: true, custom_func: validWorkTime}
							        , formatter: formatTime
							        , hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_arrival eq '1' ? false : true}	
								},
							    {name: 'ilbo_work_finish', index: 'ilbo_work_finish', align: "left", width: ${ilboSettingDTO.ilbo_work_finish_width}, editable: true, sortable: true, search: false, searchoptions: {sopt: ['cn', 'eq', 'nc']}
							    	, editrules: {custom: true, custom_func: validWorkTime}
							        , formatter: formatTime 
							        , hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_finish eq '1' ? false : true}	
								},
								{name: 'ilbo_work_breaktime', index: 'ilbo_work_breaktime', search: false, align: "center", width: ${ilboSettingDTO.ilbo_work_breaktime_width}, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}
									, editrules: {custom: true, custom_func: fn_validWorkBreaktime}
									, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_breaktime eq '1' ? false : true}
								},
								{name: 'ilbo_kind_name', index: 'ilbo_kind_name', align: "center", width: ${ilboSettingDTO.ilbo_kind_name_width}, sortable: true
									, formatter: formatKindCode
									, searchoptions: {sopt: ['cn', 'eq', 'nc']} , hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_kind_name eq '1' ? false : true}	},
								{name: 'ilbo_kind_code', index: 'ilbo_kind_code', hidden: true},
								{name: 'job_work_fee', index: 'job_work_fee', hidden: true},
								{name: 'job_worker_fee', index: 'job_worker_fee', hidden: true},
								{name: 'ilbo_job_detail_name', index: 'ilbo_job_detail_name',width:100,	editable: false},
								{name: 'ilbo_job_detail_code', index: 'ilbo_job_detail_code', hidden: true},
								{name: 'ilbo_job_add_name', index: 'ilbo_job_add_name', width:150, editable: false},							    
							    {name: 'ilbo_job_add_code', index: 'ilbo_job_add_code', hidden: true},
							    {name: 'ilbo_job_comment', index: 'ilbo_job_comment', align: "left", width: ${ilboSettingDTO.ilbo_job_comment_width}, editable: true, sortable: false, edittype: "textarea", searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_job_comment eq '1' ? false : true}},
							    {name: 'employer_detail', index: 'employer_detail', align: "left", width: ${ilboSettingDTO.employer_detail_width}, editable: true, sortable: false, edittype: "textarea", searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_job_comment eq '1' ? false : true}},
							    {name: 'ilbo_chief_memo', index: 'ilbo_chief_memo', align: "left", width: ${ilboSettingDTO.ilbo_chief_memo_width}, editable: true, sortable: false, edittype: "textarea", searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_chief_memo eq '1' ? false : true} },
								
								{name: 'ilbo_career_name', index: 'ilbo_career_name', search: false, align: "center", width: ${ilboSettingDTO.ilbo_career_name_width}
							    	, editrules: {custom: true, custom_func: validCareer}, editable: true, edittype: "select", formatter: "select"
							    	, editoptions: {
							    		value: careerOptions,
							    		dataEvents: [{
							    			type: 'change',
							    			fn: function(e) {
							    				for(var i = 0; i < $(this)[0].children.length; i++) {
							    					if(this.value == $($(this)[0].children[i]).text()) {
							    						if(i < 2) {
							    							selCareer = i + 1;
							    						}else if(i > 2) {
							    							selCareer = i;
							    						}
							    						
							    						selCareerName = $($($(this)[0].children[i])[0]).text();
							    					}else if(this.value == '0') {
							    						if($($(this)[0].children[i])[0].attributes[0].value == '0') {
							    							selCareerName = $($($(this)[0].children[i])[0]).text();
							    						}
							    						
							    						selCareer = '0';
							    					}
							    				}
							    			}
							    		}]
							    	} 
							    	, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_career_name eq '1' ? false : true}
								},
								{name: 'ilbo_career_use_seq', index: 'ilbo_career_use_seq', hidden: true },
								
								{name: 'ilbo_work_age_min', index: 'ilbo_work_age_min', search: false, align: "center", width: ${ilboSettingDTO.ilbo_work_age_min_width}, editable: true, editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 3}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_age_min eq '1' ? false : true}	},
							    {name: 'ilbo_work_age', index: 'ilbo_work_age', search: false, align: "center", width: ${ilboSettingDTO.ilbo_work_age_width}, editable: true, editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 3}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_age eq '1' ? false : true}	},
							    {name: 'ilbo_work_blood_pressure', index: 'ilbo_work_blood_pressure',search: false, width: ${ilboSettingDTO.ilbo_work_blood_pressure_width} , editable: true , editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 3}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_blood_pressure eq '1' ? false : true}	},
							    {name: 'parking_option', index: 'parking_option', search: false, editable:true, width: ${ilboSettingDTO.parking_option_width}, align:"center"
							    	, formatter: "select", edittype: "select", editoptions:{value: parking_opts}},
							    {name: 'receive_contract_seq', index: 'receive_contract_seq', align: "center", width: ${ilboSettingDTO.receive_contract_seq_width}, search: false, sortable: true, edittype: "select"
							    	, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.receive_contract_seq eq '1' ? false : true}
							    	, editoptions: {
							    		value: receiveContract
							    		, dataEvents:[
											//	{type:'change', fn:function(e){ 필요한처리할 함수명();}},   //onchange Event 
											{type:'keydown', fn:function(e) {                   //keydown Event
												if(e.keyCode == 9 || e.keyCode == 13) {
													lastElement = e.currentTarget;
												}else {
													var p = $("#list").jqGrid("getGridParam");
					                        		var iCol = p.iColByName["receive_contract_name"];
					                        		var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
					
					                        		$("#list").jqGrid('restoreCell', iRow, iCol);
					                        		
													alert("항목을 선택 후 엔터를 쳐야 됩니다.");
												}
											}} //end keydown}
										]    //dataEvents 종료
							    	}, formatter: fn_receiveName, editable: true },
							    {name: 'receive_contract_name', index: 'receive_contract_name', hidden: true},
								{name: 'labor_contract_seq', index: 'labor_contract_seq', align: "center", width: ${ilboSettingDTO.labor_contract_seq_width}, search: false, sortable: true, edittype: "select"
										, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.labor_contract_seq eq '1' ? false : true}
							    		, editoptions: {
							    			value: laborContract 
							    			, dataEvents:[
												//	{type:'change', fn:function(e){ 필요한처리할 함수명();}},   //onchange Event 
												{type:'keydown', fn:function(e) {                   //keydown Event
														if(e.keyCode == 9 || e.keyCode == 13) {
															lastElement = e.currentTarget;
														}else {
															var p = $("#list").jqGrid("getGridParam");
							                        		var iCol = p.iColByName["labor_contract_name"];
							                        		var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
							
							                        		$("#list").jqGrid('restoreCell', iRow, iCol);
							                        		
															alert("항목을 선택 후 엔터를 쳐야 됩니다.");
														}
													}
												} //end keydown}
											]    //dataEvents 종료
							    		}, formatter: fn_laborName, editable: true },
							    {name: 'labor_contract_name', index: 'labor_contract_name', hidden: true},
								{name: 'ilbo_work_manager_name', index: 'ilbo_work_manager_name', search: false, width: ${ilboSettingDTO.ilbo_work_manager_name_width}, editable: true, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_manager_name eq '1' ? false : true} },
							    {name: 'ilbo_work_manager_fax', index: 'ilbo_work_manager_fax', hidden: true},
							    {name: 'ilbo_work_manager_phone', index: 'ilbo_work_manager_phone', search: false, width:${ilboSettingDTO.ilbo_work_manager_phone_width}, editable: true, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_manager_phone eq '1' ? false : true}	 },
							    {name: 'ilbo_work_manager_email', index: 'ilbo_work_manager_email', hidden: true},
							    //{name: 'ilbo_work_name'            , index: 'ilbo_work_name'            , hidden: true},
							    // 현장정보 끝
							                 
								
							    {name: 'ilbo_income_code', index: 'ilbo_income_code', hidden: true},
							    {name: 'ilbo_income_name', index: 'ilbo_income_name', align: "center", width: ${ilboSettingDTO.ilbo_income_name_width}, sortable: true, formatter: formatIncomeCode, searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_income_name eq '1' ? false : true}	},
							    {name: 'ilbo_income_time', index: 'ilbo_income_time', align: "center", width: ${ilboSettingDTO.ilbo_income_time_width}, editable: false, sortable: true, formatoptions: {newformat: "y/m/d H:i"}, searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_income_time eq '1' ? false : true}	},
							    {name: 'ilbo_unit_price', index: 'ilbo_unit_price', align: "right", width: ${ilboSettingDTO.ilbo_unit_price_width}, editable: true, sortable: true
							    	, summaryType: "priceSum"
							        , formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}
							        , editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 11}
							        , cellattr: function(rowId, tv, rowObject, cm, rdata) {
										var price = parseInt(rowObject.ilbo_unit_price);
					
										if ( rowObject.ilbo_seq >= 0 ) {
							            	if ( price <= 110000 )      return 'style="background: #FFFFFF"'; 
											else if ( price <= 160000 ) return 'style="background: #FFE599"'; 
							                else if ( price <= 190000 ) return 'style="background: #A2C4C9"'; 
							                else if ( price >= 200000 ) return 'style="background: #D5A6BD"'; 
							                else                        return 'style="background: #FFFFFF"'; 
										}
									},
									searchoptions: {sopt: ['cn', 'eq', 'nc', 'ge', 'le']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_unit_price eq '1' ? false : true}	
								},
							    {name: 'ilbo_fee', index: 'ilbo_fee', align: "right", width: ${ilboSettingDTO.ilbo_fee_width}, editable: true, sortable: true
							    	, summaryType: "sum"
							        , formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}
							        , editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 11}
							        , searchoptions: {sopt: ['cn', 'eq', 'nc', 'ge', 'le']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_fee eq '1' ? false : true}	
								},
								{name: 'share_fee', index: 'share_fee', align: "right", width: ${ilboSettingDTO.share_fee_width}, editable: true, sortable: true
							    	, hidden:${ shareYn eq "0" ? true : false}
							        , summaryType: "sum"
							        , formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}
							        , editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 11}
							        , searchoptions: {sopt: ['cn', 'eq', 'nc', 'ge', 'le']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.share_fee eq '1' ? false : true}	
								},
							    {name: 'ilbo_deduction', index: 'ilbo_deduction', align: "right", width: ${ilboSettingDTO.ilbo_deduction_width}, editable: false, sortable: true
									, summaryType: "sum"
							        , formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}
							        , editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 11}
							        , searchoptions: {sopt: ['ne', 'cn', 'eq', 'nc']}, hidden: true	
								},
								{name: 'counselor_fee', index: 'counselor_fee', align: "right", width: 70, editable: true, sortable: true
							    	, summaryType: "sum"
							        , formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}
							        , editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 11}
							        , searchoptions: {sopt: ['cn', 'eq', 'nc', 'ge', 'le']}, hidden: false	
								},
								
								{name: 'counselor_rate', index: 'counselor_rate', editable: false, hidden: true},
								
							    {name: 'ilbo_pay', index: 'ilbo_pay', align: "right", width: ${ilboSettingDTO.ilbo_pay_width}, editable: true, sortable: true
							    	, summaryType: "sum"
							        , formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}
							        , searchoptions: {sopt: ['cn', 'eq', 'nc', 'ge', 'le']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_pay eq '1' ? false : true}	
								},
							    {name: 'ilbo_pay_code', index: 'ilbo_pay_code', editable: false, hidden: true},
							    {name: 'deductible_sum', index: 'deductible_sum', editable: false, width: ${ilboSettingDTO.deductible_sum_width}, align: "right"
							    	, formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}
							    	, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.deductible_sum eq '1' ? false : true}
							    },
							    {name: 'deductible_info', index: 'deductible_info', sortable: false, align: "left", width: ${ilboSettingDTO.deductible_info_width}, search: false, formatter: formatDeductibleInfo, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.deductible_info eq '1' ? false : true} },
// 							    {name: 'wages_received', index: 'wages_received', editable: false, width: 70, align: "right", formatter: fn_wages_received },
							    {name: 'wages_received', index: 'wages_received', editable: false, width: ${ilboSettingDTO.wages_received_width}, align: "right"
							    	, formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}
							    	, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.wages_received eq '1' ? false : true}
							    },
							    {name: 'ilbo_pay_name', index: 'ilbo_pay_name', align: "center", width: ${ilboSettingDTO.ilbo_pay_name_width}, sortable: true, formatter: formatPayCode, searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_pay_name eq '1' ? false : true}	},
							    {name: 'ilbo_pay_time', index: 'ilbo_pay_time', align: "center", width: ${ilboSettingDTO.ilbo_pay_time_width}, editable: false, sortable: true, formatoptions: {newformat: "y/m/d H:i"}, searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_pay_time eq '1' ? false : true}	},
							    {name: 'work_fee', index: 'work_fee', editable: false, width: ${ilboSettingDTO.work_fee_width}, align: "right"
							    	, formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}
							    	, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.work_fee eq '1' ? false : true}
							    },
							    {name: 'worker_fee', index: 'worker_fee', editable: false, width: ${ilboSettingDTO.worker_fee_width}, align: "right" 
							    	, formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}
							    	, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.worker_fee eq '1' ? false : true}
							    },
							    {name: 'fee_info', index: 'fee_info', width: ${ilboSettingDTO.fee_info_width}, search: false, sortable: false, editable: true, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.fee_info eq '1' ? false : true}},
							    {name: 'pay_info', index: 'pay_info', width: ${ilboSettingDTO.pay_info_width}, search: false, sortable: false, editable: true, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.pay_info eq '1' ? false : true}},
								{name: 'ilbo_income_memo', index: 'ilbo_income_memo', align: "left", width: ${ilboSettingDTO.ilbo_income_memo_width}, editable: true, sortable: false, edittype: "textarea", searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_income_memo eq '1' ? false : true}	},
								{name: 'ilbo_confirm', index: 'ilbo_confirm', align: "center", width: 60, search: true, searchoptions: {sopt: ['eq']}
									, editable: true
									, sortable: true
									, edittype: "select"
									, editoptions: {value: confirm_opts}
									, formatter: "select"
									, hidden: true
									, cellattr: function(rowId, tv, rowObject, cm, rdata) {
							        	// rowObject 변수로 그리드 데이터에 접근
							            if ( rowObject.ilbo_confirm == '0' ) {
							            	return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
										}
									}
								},
								{name: 'employer_rating', index: 'employer_rating', align: "center", width: ${ilboSettingDTO.employer_rating_width}, search: false, editable: true, sortable: true, edittype: "select"
									, editoptions: {value: star_opts}   
									, formatter: "select"
							    	, editrules: {custom: true, custom_func: validIlboStar}
							        , cellattr: function(rowId, tv, rowObject, cm, rdata) {
							        	// rowObject 변수로 그리드 데이터에 접근
							            if ( rowObject.employer_rating  == '0' ) {
							            	return 'style="color: #B300FF; text-align: center;"';
										}
									}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.employer_rating eq '1' ? false : true}	
								},
								{name: 'manager_rating', index: 'manager_rating', align: "center", width: ${ilboSettingDTO.manager_rating_width}, search: false, editable: true, sortable: true, edittype: "select"
									, editoptions: {value: star_opts}   
									, formatter: "select"
							    	, editrules: {custom: true, custom_func: validIlboStar}
							        , cellattr: function(rowId, tv, rowObject, cm, rdata) {
							        	// rowObject 변수로 그리드 데이터에 접근
							            if ( rowObject.manager_rating  == '0' ) {
							            	return 'style="color: #B300FF; text-align: center;"';
										}
									}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.manager_rating eq '1' ? false : true}	
								},
								{name: 'evaluate_grade', index: 'evaluate_grade', align: "center", width: ${ilboSettingDTO.evaluate_grade_width}, search: false
									, editable: true
									, sortable: true
									, edittype: "select"
									, editoptions: {value: grade_opts}   , formatter: "select"
							    	, editrules: {custom: true, custom_func: validIlboEvaluateGrade}
							        , cellattr: function(rowId, tv, rowObject, cm, rdata) {
							        	var text = 'class="evaluate_grade"';
							            // rowObject 변수로 그리드 데이터에 접근
							            if ( rowObject.evaluate_grade  == '0' ) {
							            	text += ' style="color: #B300FF; text-align: center;"';
							            }	
							            
							        	text +=' title="' + rowObject.evaluate_comment+'"';
							        							
							            return text;
									}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.evaluate_grade eq '1' ? false : true}	
								},
								{name: 'surrogate', index: 'surrogate', editable: false, search: false, sortable: false, formatter: fn_surrogateComplete, align: "center", width: ${ilboSettingDTO.surrogate_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.surrogate eq '1' ? false : true} },
								{name: 'labor_contract', index: 'labor_contract', editable: false, search: false, sortable: false, formatter: fn_laborComplete, align: "center", width: ${ilboSettingDTO.labor_contract_width}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.labor_contract eq '1' ? false : true} },
								{name: 'evaluate_comment', index: 'evaluate_comment', hidden: true},
								{name: 'seq', index: 'seq', hidden: true},
							    {name: 'use_yn', index: 'use_yn', align: "center", width: ${ilboSettingDTO.use_yn_width}, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: use_opts}, formatter: "select"
							    	, cellattr: function(rowId, tv, rowObject, cm, rdata) {
							        	// rowObject 변수로 그리드 데이터에 접근
							            if ( rowObject.use_yn == '1' ) {
							            	return 'style="color: #B300FF; text-align: center;"';
										}
									}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.use_yn eq '1' ? false : true}	
								},
							    {name: 'reg_date', index: 'reg_date', align: "center", width: ${ilboSettingDTO.reg_date_width}, search: false, editable: false, sortable: true, formatoptions: {newformat: "y/m/d H:i"}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.reg_date eq '1' ? false : true}	},
							    {name: 'reg_admin', index: 'i.reg_admin', align: "left", width: ${ilboSettingDTO.reg_admin_width}, editable: false, sortable: false, searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.reg_admin eq '1' ? false : true}	},
							    {name: 'owner_id', index: 'i.owner_id', align: "left", width: ${ilboSettingDTO.owner_id_width}, editable: false, sortable: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.owner_id eq '1' ? false : true}	},
							    {name: 'mod_date', index: 'mod_date', hidden: true},
							    {name: 'mod_output_date', index: 'mod_output_date', hidden: true},
								{name: 'labor_contract', index: 'labor_contract_hidden', hidden: true },
							    {name: 'visit_type', index: 'm.visit_type', hidden: true}
							] //end model
							// row 를 선택 했을때 편집 할 수 있도록 한다. 체크박스 선택 할때 이벤트 발생
							, onSelectRow: function(id) {
								if ( id && id !== lastsel ) {
								    $("#list").jqGrid('restoreRow', lastsel);
						
								    $("#list").jqGrid('editRow', id, {
								    	keys: true,
								        oneditfunc: function() {},
										successfunc: function() {
											lastsel = -1;
						
											return true;
										},
									});
								    
									lastsel = id;
								}
							}
							// cell을 클릭 시
							, onCellSelect: function(rowid, index, contents, event) {
							    //row data 전체 가져 오기
							    var row = $("#list").jqGrid('getRowData', rowid);
							    // 컬럼정보 가져오기
							    var cm = $("#list").jqGrid('getGridParam', 'colModel');
							    
							    lastsel = rowid;
							   
							    if ( cm[index].name == "ilbo_employer_feature" ) {      // 구인처 특증을 클릭하면 Clipboard 로 데이터복사
							    	
							    	if(row.ilbo_income_code == "0" || row.ilbo_income_code == "" ){
							    		toastFail("노임수령 방법을 선택하세요.");
							    		return;
							    	}

							    	if(row.ilbo_kind_code == "0" || row.ilbo_kind_code == "" ){
							    		toastFail("직종을 선택하세요.");
							    		return;
							    	}
							    	
							    	var toData = "";
							        var ilbo_income_name = mCode.get(row.ilbo_income_code).name;
							        var ilbo_kind_name   = mCode.get(row.ilbo_kind_code).name;
							        
							        if ( ilbo_income_name == "(노현)" )               ilbo_income_name = "현장현금수령, ";
							        else if ( ilbo_income_name == "(노통)" )        ilbo_income_name = "본인통장수령, ";
							        else if ( ilbo_income_name == "(당통)" )        ilbo_income_name = "사무실통장입금 후 지급, ";
							        else if ( ilbo_income_name == "(단기)" || ilbo_income_name == "(기성)" )  ilbo_income_name = "작업확인서 증빙 사무실 지급, ";
							        else                                                                      ilbo_income_name += ", ";
										toData = "[일가자접수내역] \n"
										+	"▶구인자: "+ row.employer_name +" \n"
							            + "▶현장: "+ row.ilbo_work_name +" \n"
							            + "▶출근일: "+ row.ilbo_date +" \n"
							            + "▶근로시간: " + row.ilbo_work_arrival+ " ~ "+ row.ilbo_work_finish+" \n"
							            + "▶조식: " + getBreakfastYnKr(row.ilbo_work_breakfast_yn) + "\n"
							            + "▶직종: " + ilbo_kind_name +"  \n"
							            + "▶작업내용:\n"+ row.ilbo_job_comment +" \n"
							            // + "▶소장메모:\n"+ row.ilbo_chief_memo +" \n" 
							            + "▶단가: "+ row.ilbo_unit_price +"원 "+ ilbo_income_name + "\n"
							            + "▶현장담당자: "+ row.ilbo_work_manager_name +" " + row.ilbo_work_manager_phone;
							            
							            CopyToClipboard(toData, myClipboard, false);
								}
					
							    if ( cm[index].name == "ilbo_worker_name" ) {
							    	var ilbo_seq = row.ilbo_seq;
							    
							    	if( (row.ilbo_use_code != '' && row.ilbo_use_code != 'USE001') ){
							    		
								    	var _data = {
								    			ilbo_seq	: ilbo_seq,
								    			output_status_code : row.output_status_code
								    	};
								    	var _url = "<c:url value='/admin/checkOutputStatus' />";
								    	
								    	commonAjax(_url, _data, true, function(data) {
								    		if(data.code == "0000"){
												
											}else{
												toastFail('작업자 상태가 변경되어 리로드 합니다.');
												$("#list").trigger("reloadGrid");
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
							    }
							    
							    if(cm[index].name == 'manager_name') {
							    	if(authLevel != '0') {
							    		return false;
							    	}
							    	
							    	var manager_seq = row.manager_seq;
							    	var ilbo_seq = row.ilbo_seq;
							    	
							    	if( manager_seq == 0 || manager_seq == null){
							    		return;
							    	}
							    	
							    	var _data = {
							    		ilbo_seq : ilbo_seq
							    		, manager_seq : manager_seq
							    	};
							    	
							    	var _url = "<c:url value='/admin/updateIlboManager' />";
							    	
							    	commonAjax(_url, _data, true, function(data) {
							    		if(data.code == '0000') {
							    			var managerDTO = data.resultDTO;
							    			
							    			$("#list").jqGrid("setCell", rowid, "work_company_seq", managerDTO.company_seq, '', true);
							    			$("#list").jqGrid("setCell", rowid, "work_company_name", managerDTO.company_name, '', true);
							    			$("#list").jqGrid("setCell", rowid, "work_owner", managerDTO.owner_id, '', true);
							    			$("#list").jqGrid("setCell", rowid, "manager_name", managerDTO.manager_name, '', true);
							    			$("#list").jqGrid("setCell", rowid, "manager_phone", managerDTO.manager_phone, '', true);
							    			
							    			toastOk('매니저가 갱신되었습니다.');
							    		}else {
							    			toastFail("오류가 발생했습니다.");
							    		}
							    	}, function(data) {
							    		//errorListener
							    		toastFail("오류가 발생했습니다.");
							    	}, function() {
							    		// beforeSendListener
							    	}, function() {
							    		// completeListener
							    	});
							    }
							    
							    if( cm[index].name == 'ilbo_income_time' ){

							    	$.ajax({
							    		type: "GET",
							    		url: "/admin/getPriceLogs",
							    		data: {
							    			ilbo_seq: rowid
							    		},
							    		dataType: 'json',
							    		beforeSend: function(xhr){
							    			xhr.setRequestHeader("AJAX", true);
							    		},
							    		success: function(data){
							    			if(data.code == "0000"){
							    				fn_showPriceLog(data.list, event.target);	
							    			}else{
							    				toastFail(data.message);	
							    			}
							    		},
							    		error: function(xhr, status, error){
							    			toastFail("오류가 발생했습니다.");
							    		}
							    	});
							    }
							}
							// submit 전
							, beforeSubmitCell: function(rowId, cellname, value) {
								isSelect = false;
							    
								// 단가 / 수수료 / 공제금이 변경 될 경우 일당 자동 계산
							    if ( cellname == "ilbo_unit_price" || cellname == "ilbo_fee" || cellname == "ilbo_deduction" || cellname == "share_fee" || cellname == "counselor_fee") {
									
							    	//단가 계산
							    	fn_setIlboFee(rowId, cellname, value);
									//footer에 적용한다.
									setFooter();
									
									return {
										cellname : cellname,	
									    ilbo_seq : $("#list").jqGrid('getRowData', rowId).ilbo_seq,
									    // company_seq		: $("#list").jqGrid('getRowData', rowId).company_seq,
 									    worker_company_seq : $("#list").jqGrid('getRowData', rowId).worker_company_seq,		//구직자 소속 지점
									    work_company_seq : $("#list").jqGrid('getRowData', rowId).work_company_seq,			//매니저 소속 지점
									    ilbo_fee : $("#list").jqGrid('getRowData', rowId).ilbo_fee,			//수수료
									    share_fee : $("#list").jqGrid('getRowData', rowId).share_fee,				//쉐어료
									    ilbo_deduction : $("#list").jqGrid('getRowData', rowId).ilbo_deduction,			//공제금
									    counselor_fee : $("#list").jqGrid('getRowData', rowId).counselor_fee,				//공제금
									    ilbo_pay : $("#list").jqGrid('getRowData', rowId).ilbo_pay,			//일당
									    fee_info : $("#list").jqGrid('getRowData', rowId).fee_info,						//수수료 정보
									    pay_info : $("#list").jqGrid('getRowData', rowId).pay_info,						//임금 정보
									    work_fee : $("#list").jqGrid('getRowData', rowId).work_fee,
									    worker_fee : $("#list").jqGrid('getRowData', rowId).worker_fee
									};
								} else if ( cellname == "worker_company_name" ) {          // 구직회사선택시
									return {
								    	cellname : cellname,
									    ilbo_seq : $("#list").jqGrid('getRowData', rowId).ilbo_seq,
									    worker_company_seq : $("#list").jqGrid('getRowData', rowId).worker_company_seq,
										ilbo_worker_name : $("#list").jqGrid('getRowData', rowId).ilbo_worker_name,
									    ilbo_assign_type : $("#list").jqGrid('getRowData', rowId).ilbo_assign_type,
									    company_seq	: $("#list").jqGrid('getRowData', rowId).company_seq,
									    worker_seq : $("#list").jqGrid('getRowData', rowId).worker_seq,
										//output_status_code: $("#list").jqGrid('getRowData', rowId).output_status_code,
									    output_status_code: '0',  //초기값을 직접 넣어 준다.
										output_status_name: $("#list").jqGrid('getRowData', rowId).output_status_name,
										before_worker_company_seq : beforeWorkerCompanySeq,
								
										// 구직자 정보
										worker_company_seq : $("#list").jqGrid('getRowData', rowId).worker_company_seq,
										worker_company_name : $("#list").jqGrid('getRowData', rowId).worker_company_name,
										ilbo_worker_sex	: $("#list").jqGrid('getRowData', rowId).ilbo_worker_sex,
									    ilbo_worker_phone : $("#list").jqGrid('getRowData', rowId).ilbo_worker_phone,
									    ilbo_worker_addr : $("#list").jqGrid('getRowData', rowId).ilbo_worker_addr,
									    ilbo_worker_latlng : $("#list").jqGrid('getRowData', rowId).ilbo_worker_latlng,
									    ilbo_worker_ilgaja_addr	: $("#list").jqGrid('getRowData', rowId).ilbo_worker_ilgaja_addr,
									    ilbo_worker_ilgaja_latlng : $("#list").jqGrid('getRowData', rowId).ilbo_worker_ilgaja_latlng,
									    ilbo_worker_jumin : $("#list").jqGrid('getRowData', rowId).ilbo_worker_jumin,
									    ilbo_worker_job_code : $("#list").jqGrid('getRowData', rowId).ilbo_worker_job_code,
									    ilbo_worker_job_name : $("#list").jqGrid('getRowData', rowId).ilbo_worker_job_name,
									    ilbo_worker_barcode	: $("#list").jqGrid('getRowData', rowId).ilbo_worker_barcode,
									    ilbo_worker_memo : $("#list").jqGrid('getRowData', rowId).ilbo_worker_memo,
									    ilbo_worker_bank_code : $("#list").jqGrid('getRowData', rowId).ilbo_worker_bank_code,
									    ilbo_worker_bank_name : $("#list").jqGrid('getRowData', rowId).ilbo_worker_bank_name,
									    ilbo_worker_bank_account : $("#list").jqGrid('getRowData', rowId).ilbo_worker_bank_account,
									    ilbo_worker_bank_owner : $("#list").jqGrid('getRowData', rowId).ilbo_worker_bank_owner,
									    ilbo_worker_bankbook_scan_yn : $("#list").jqGrid('getRowData', rowId).ilbo_worker_bankbook_scan_yn,
									    ilbo_worker_feature : $("#list").jqGrid('getRowData', rowId).ilbo_worker_feature,
										ilbo_worker_blood_pressure : $("#list").jqGrid('getRowData', rowId).ilbo_worker_blood_pressure,
									    ilbo_worker_OSH_yn : $("#list").jqGrid('getRowData', rowId).ilbo_worker_OSH_yn,
									    ilbo_worker_jumin_scan_yn : $("#list").jqGrid('getRowData', rowId).ilbo_worker_jumin_scan_yn,
									    ilbo_worker_OSH_scan_yn	: $("#list").jqGrid('getRowData', rowId).ilbo_worker_OSH_scan_yn,
									    ilbo_worker_app_status : $("#list").jqGrid('getRowData', rowId).ilbo_worker_app_status,
									    ilbo_worker_reserve_push_status : $("#list").jqGrid('getRowData', rowId).ilbo_worker_reserve_push_status,
									    ilbo_worker_app_use_status : $("#list").jqGrid('getRowData', rowId).ilbo_worker_app_use_status,
									    worker_owner : $("#list").jqGrid('getRowData', rowId).worker_owner,
										ilbo_worker_age : $("#list").jqGrid('getRowData', rowId).ilbo_worker_age,
										ilbo_worker_add_memo : $("#list").jqGrid('getRowData', rowId).ilbo_worker_add_memo,
										
										ilbo_fee : $("#list").jqGrid('getRowData', rowId).ilbo_fee,					//수수료
									    share_fee : $("#list").jqGrid('getRowData', rowId).share_fee,				//쉐어료
									    ilbo_deduction : $("#list").jqGrid('getRowData', rowId).ilbo_deduction,			//공제금
									    counselor_fee : $("#list").jqGrid('getRowData', rowId).counselor_fee,			//공제금
									    ilbo_pay : $("#list").jqGrid('getRowData', rowId).ilbo_pay					//일당
									};
								} else if ( cellname == "ilbo_worker_name" ) {          // 구직자 변경시
									checkDuplicateWorker();
								
									return {
										cellname : cellname,
										ilbo_seq : $("#list").jqGrid('getRowData', rowId).ilbo_seq,
								        ilbo_assign_type : $("#list").jqGrid('getRowData', rowId).ilbo_assign_type,
								        
								        worker_seq : $("#list").jqGrid('getRowData', rowId).worker_seq,
								
										//output_status_code: $("#list").jqGrid('getRowData', rowId).output_status_code,
								        output_status_code : '0',   //초기값을 직접 넣어 준다.
								        output_status_name : $("#list").jqGrid('getRowData', rowId).output_status_name,
								
										// 구직자 정보
										before_worker_seq : beforeWorkerSeq,
										worker_company_seq : $("#list").jqGrid('getRowData', rowId).worker_company_seq,
										worker_company_name : $("#list").jqGrid('getRowData', rowId).worker_company_name,
										ilbo_worker_sex : $("#list").jqGrid('getRowData', rowId).ilbo_worker_sex,
								        ilbo_worker_phone : $("#list").jqGrid('getRowData', rowId).ilbo_worker_phone,
								        ilbo_worker_addr : $("#list").jqGrid('getRowData', rowId).ilbo_worker_addr,
								        ilbo_worker_latlng : $("#list").jqGrid('getRowData', rowId).ilbo_worker_latlng,
								        ilbo_worker_ilgaja_addr	: $("#list").jqGrid('getRowData', rowId).ilbo_worker_ilgaja_addr,
								        ilbo_worker_ilgaja_latlng : $("#list").jqGrid('getRowData', rowId).ilbo_worker_ilgaja_latlng,
								        ilbo_worker_jumin : $("#list").jqGrid('getRowData', rowId).ilbo_worker_jumin,
								        ilbo_worker_job_code : $("#list").jqGrid('getRowData', rowId).ilbo_worker_job_code,
								        ilbo_worker_job_name : $("#list").jqGrid('getRowData', rowId).ilbo_worker_job_name,
								        ilbo_worker_barcode : $("#list").jqGrid('getRowData', rowId).ilbo_worker_barcode,
								        ilbo_worker_memo : $("#list").jqGrid('getRowData', rowId).ilbo_worker_memo,
								        ilbo_worker_bank_code : $("#list").jqGrid('getRowData', rowId).ilbo_worker_bank_code,
								        ilbo_worker_bank_name : $("#list").jqGrid('getRowData', rowId).ilbo_worker_bank_name,
								        ilbo_worker_bank_account : $("#list").jqGrid('getRowData', rowId).ilbo_worker_bank_account,
								        ilbo_worker_bank_owner : $("#list").jqGrid('getRowData', rowId).ilbo_worker_bank_owner,
								        ilbo_worker_bankbook_scan_yn : $("#list").jqGrid('getRowData', rowId).ilbo_worker_bankbook_scan_yn,
								        ilbo_worker_feature : $("#list").jqGrid('getRowData', rowId).ilbo_worker_feature,
										ilbo_worker_blood_pressure : $("#list").jqGrid('getRowData', rowId).ilbo_worker_blood_pressure,
								        ilbo_worker_OSH_yn : $("#list").jqGrid('getRowData', rowId).ilbo_worker_OSH_yn,
								        ilbo_worker_jumin_scan_yn : $("#list").jqGrid('getRowData', rowId).ilbo_worker_jumin_scan_yn,
								        ilbo_worker_OSH_scan_yn	: $("#list").jqGrid('getRowData', rowId).ilbo_worker_OSH_scan_yn,
								        ilbo_worker_app_status : $("#list").jqGrid('getRowData', rowId).ilbo_worker_app_status,
								        ilbo_worker_reserve_push_status : $("#list").jqGrid('getRowData', rowId).ilbo_worker_reserve_push_status,
								        ilbo_worker_app_use_status : $("#list").jqGrid('getRowData', rowId).ilbo_worker_app_use_status,
								        worker_owner : $("#list").jqGrid('getRowData', rowId).worker_owner,
										ilbo_worker_age : $("#list").jqGrid('getRowData', rowId).ilbo_worker_age,
										ilbo_worker_add_memo : $("#list").jqGrid('getRowData', rowId).ilbo_worker_add_memo,
										employer_rating : '0',
										manager_rating : '0'
 										//ilbo_status_code: '0',
 										//ilbo_status_name: ''
									};
								} else if ( cellname == "ilbo_work_name" || cellname == "employer_name"  || cellname == "work_company_name") {            // 현장 검색 시

									return {
										cellname : cellname,
										ilbo_seq : $("#list").jqGrid('getRowData', rowId).ilbo_seq,
										company_seq	: $("#list").jqGrid('getRowData', rowId).company_seq,
										employer_seq : $("#list").jqGrid('getRowData', rowId).employer_seq,
										work_seq : $("#list").jqGrid('getRowData', rowId).work_seq,
										ilbo_work_name : $("#list").jqGrid('getRowData', rowId).ilbo_work_name,
										ilbo_order_code	: $("#list").jqGrid('getRowData', rowId).ilbo_order_code,
										ilbo_order_name	: $("#list").jqGrid('getRowData', rowId).ilbo_order_name,
										ilbo_use_code : $("#list").jqGrid('getRowData', rowId).ilbo_use_code,
										ilbo_use_name : $("#list").jqGrid('getRowData', rowId).ilbo_use_name,
										ilbo_status_code : $("#list").jqGrid('getRowData', rowId).ilbo_status_code,
										ilbo_status_name : $("#list").jqGrid('getRowData', rowId).ilbo_status_name,
										ilbo_worker_status_code	: $("#list").jqGrid('getRowData', rowId).ilbo_worker_status_code,
										ilbo_worker_status_name	: $("#list").jqGrid('getRowData', rowId).ilbo_worker_status_name,
										
										ilbo_work_order_code : $("#list").jqGrid('getRowData', rowId).ilbo_work_order_code,
										ilbo_work_order_name : $("#list").jqGrid('getRowData', rowId).ilbo_work_order_name,
															
										// 현장정보
										ilbo_work_arrival : $("#list").jqGrid('getRowData', rowId).ilbo_work_arrival,
										ilbo_work_finish : $("#list").jqGrid('getRowData', rowId).ilbo_work_finish,
										ilbo_work_addr : $("#list").jqGrid('getRowData', rowId).ilbo_work_addr,
										work_sido : $("#list").jqGrid('getRowData', rowId).work_sido,
										work_sigugun : $("#list").jqGrid('getRowData', rowId).work_sigugun,
										work_dongmyun : $("#list").jqGrid('getRowData', rowId).work_dongmyun,
										work_rest : $("#list").jqGrid('getRowData', rowId).work_rest,
										ilbo_work_latlng : $("#list").jqGrid('getRowData', rowId).ilbo_work_latlng,
										ilbo_work_addr_comment : $("#list").jqGrid('getRowData', rowId).ilbo_work_addr_comment,
										ilbo_work_breakfast_yn : $("#list").jqGrid('getRowData', rowId).ilbo_work_breakfast_yn,
										ilbo_work_age_min : $("#list").jqGrid('getRowData', rowId).ilbo_work_age_min,
										ilbo_work_age : $("#list").jqGrid('getRowData', rowId).ilbo_work_age,
										ilbo_work_blood_pressure : $("#list").jqGrid('getRowData', rowId).ilbo_work_blood_pressure,
										manager_seq	: $("#list").jqGrid('getRowData', rowId).manager_seq,
										manager_name : $("#list").jqGrid('getRowData', rowId).manager_name,
										manager_phone : $("#list").jqGrid('getRowData', rowId).manager_phone,
										ilbo_work_manager_name : $("#list").jqGrid('getRowData', rowId).ilbo_work_manager_name,
										ilbo_work_manager_fax : $("#list").jqGrid('getRowData', rowId).ilbo_work_manager_fax,
										ilbo_work_manager_phone	: $("#list").jqGrid('getRowData', rowId).ilbo_work_manager_phone,
										ilbo_work_manager_email	: $("#list").jqGrid('getRowData', rowId).ilbo_work_manager_email,
										ilbo_job_comment : $("#list").jqGrid('getRowData', rowId).ilbo_job_comment,
										ilbo_chief_memo : $("#list").jqGrid('getRowData', rowId).ilbo_chief_memo,
										work_owner : $("#list").jqGrid('getRowData', rowId).work_owner,
										work_company_name : $("#list").jqGrid('getRowData', rowId).work_company_name,
										work_company_seq : $("#list").jqGrid('getRowData', rowId).work_company_seq,
										
										ilbo_fee : $("#list").jqGrid('getRowData', rowId).ilbo_fee,					//수수료
									    share_fee : $("#list").jqGrid('getRowData', rowId).share_fee,				//쉐어료
									    ilbo_deduction : $("#list").jqGrid('getRowData', rowId).ilbo_deduction,			//공제금
									    counselor_fee : $("#list").jqGrid('getRowData', rowId).counselor_fee,			//공제금
									    ilbo_pay : $("#list").jqGrid('getRowData', rowId).ilbo_pay					//일당
									};
								}else if ( cellname == "employer_rating" ) {            // 구직자 평가
									return {
										cellname : cellname,
										ilbo_seq : $("#list").jqGrid('getRowData', rowId).ilbo_seq,
										employer_seq : $("#list").jqGrid('getRowData', rowId).employer_seq,
										work_seq : $("#list").jqGrid('getRowData', rowId).work_seq,
										worker_seq : $("#list").jqGrid('getRowData', rowId).worker_seq
									};
								}else if ( cellname == "manager_rating" ) {            // 구인자 평가
									return {
										cellname : cellname,
										ilbo_seq : $("#list").jqGrid('getRowData', rowId).ilbo_seq,
										employer_seq : $("#list").jqGrid('getRowData', rowId).employer_seq,
										work_seq : $("#list").jqGrid('getRowData', rowId).work_seq,
										worker_seq : $("#list").jqGrid('getRowData', rowId).worker_seq,
										manager_seq	: $("#list").jqGrid('getRowData', rowId).manager_seq,
										manager_rating : $("#list").jqGrid('getRowData', rowId).manager_rating
									};
								}else if( cellname == "evaluate_grade") { //구직자 평가 변경
									return {
										cellname : cellname,
							            seq : $("#list").jqGrid("getRowData", rowId).seq,
							            evaluate_grade : $("#list").jqGrid("getRowData", rowId).evaluate_grade
									}
								}else if( cellname == "ilbo_work_order_code"){
									return {
										cellname : cellname,
										ilbo_seq : $("#list").jqGrid("getRowData", rowId).ilbo_seq,
						            	ilbo_work_order_code : $("#list").jqGrid('getRowData', rowId).ilbo_work_order_code,
										ilbo_work_order_name : $("#list").jqGrid('getRowData', rowId).ilbo_work_order_name,
									}
								}else if(cellname == "ilbo_career_name") {
									var testJson = {
										cellname : cellname
										, ilbo_seq : $("#list").jqGrid("getRowData", rowId).ilbo_seq
										, ilbo_career_name : selCareerName 
										, selCareerName : selCareerName
										, ilbo_career_use_seq : selCareer
									};
									
									return testJson; 
								}
					
								return {
									cellname : cellname,
							        ilbo_seq: $("#list").jqGrid('getRowData', rowId).ilbo_seq,
							        company_seq: $("#list").jqGrid('getRowData', rowId).company_seq
								};
							}
							, afterSubmitCell: function(serverresponse, rowid, cellname, value, iRow, iCol) {
								// 상태가 미사용이면 공제액과 임금수령액을 0으로 변경한다.
								if(cellname == 'use_yn') {
									if(value == '0') {
										$("#list").jqGrid("setCell", rowid, "deductible_sum", 0, '', true);
										$("#list").jqGrid("setCell", rowid, "wages_received", 0, '', true);
									}
								}
								
								
								if(cellname == "company_name" || cellname == "work_company_name" || cellname == "worker_company_name"){
									//활성화 비활성화를 수정 한다.
									fn_setEditable(rowid, $("#list").jqGrid('getRowData', rowid).company_seq, $("#list").jqGrid('getRowData', rowid).worker_company_seq, $("#list").jqGrid('getRowData', rowid).work_company_seq);
								}
								
								var response = (serverresponse.statusText).trim();
								respText = serverresponse.responseText;
								
								if ( response == 'OK' || response == 'success') {
									//$("#list").trigger("reloadGrid");
									var _responseJson = JSON.parse(respText);
									
									if( _responseJson.result == '0000' ){
										if(cellname == "ilbo_unit_price"){
											$("#list").jqGrid("setCell", rowid, "deductible_sum", _responseJson.deductible_sum, '', true);
										}
										return [true,""];
									}
									/* code
										0021 : 일보 정보 바꿀 때 근로계약서에 구직자만 서명이 완료되어 있는 상태면 정보 수정 안되게
										0099 : 구직자 변경시 이미 변경된 구직자라면 수정 안되게
								   0095,0095 : 공제액 관련 수정불가 코드
										0019 : 출역제한 error 코드
									*/
									if( _responseJson.code == '0099' ) {
										$("#list").jqGrid('restoreRow', lastsel);
									}
									return [false, _responseJson.message];
								}
								
								return [false,respText];
							}
							, afterInsertRow: function(rowId, rowdata, rowelem) {	//그리드를 그릴때 호출 되는 구나....
								fn_setEditable(rowId, rowdata.company_seq, rowdata.worker_company_seq, rowdata.work_company_seq);
							}
							, gridComplete : function(data) {
							}
							// Grid 로드 완료 후
							, loadComplete: function(data) {
								fn_setIlboData();
								
								// 근로계약서 사인 flag Vali
								fn_signFlagVali(data);
/* 
								// Row Color Change Event
							    var ids = $("#list").getDataIDs();
							    // Grid Data Get!
								$.each(ids, function(idx, rowId) {
									rowData = $("#list").getRowData(rowId);
									
							        //유니크 회사수를 구하기 위함
							        if ( rowData.employer_seq > 0  && rowData.ilbo_fee > 0 ) {
							        	employer_seq_array.push(rowData.employer_seq);
									}
							        
							        if ( rowData.ilbo_seq < 0 ) {
							        	$("#list").setRowData(rowId, false, { background:"#FDECCD" });
									}
								});
				
								//유니크 회사수를 구하기 위함
							    var uniqueEmployer =[];
							    $.each(employer_seq_array, function(idx, value) {
							    	
							    	if ( $.inArray(value, uniqueEmployer) === -1){
							    		uniqueEmployer.push(value);
							    	}
								});
							    
					
								$("#uniqueE").html(numberWithCommas(uniqueEmployer.length));
							    $("#totalE").html(numberWithCommas(employer_seq_array.length)); 
							    
							    //중복 구직자 폰트색 변경
							    checkDuplicateWorker();
											    
								//footer에 적용한다.
							    setFooter();
	   							 
							   // $("#list").trigger("reloadGrid");
*/

							    isGridLoad = true;
							    $(".wrap-loading").hide();
							}
							, loadBeforeSend: function(xhr) {
								isGridLoad = false;
							    optHTML = "";
								xhr.setRequestHeader("AJAX", true);
								$(".wrap-loading").show();
							}
							, loadError: function(xhr, status, error) {
								$(".wrap-loading").hide();
								if ( xhr.status == "901" ) {
							    	location.href = "/admin/login";
								}
							}
							, beforeSelectRow: selectRowid
						});
					
						$("#list").jqGrid('navGrid', "#paging", {edit: false, add: false, del: false, search: false, refresh: false, position: 'left'});
					
						$("#list").jqGrid('filterToolbar', {searchOperators : true});
					
						$("#list").jqGrid('setGroupHeaders', {
							useColSpanStyle: true,
							groupHeaders:[
								{ startColumnName: 'worker_ilbo', numberOfColumns: 37, titleText: '<div style="margin:0; padding:0;width:100%;border-spacing:0px;background-color:#e6977b">구직자</div>' }
								, { startColumnName: 'employer_ilbo', numberOfColumns: 5, titleText: '<div style="margin:0; padding:0;width:100%;border-spacing:0px;background-color:#7be6aa">구인처</div>'}
							    , { startColumnName: 'work_company_name', numberOfColumns: 40, titleText: '<div style="margin:0; padding:0;width:100%;border-spacing:0px;background-color:#e6c57b">현장</div>'}
							    , { startColumnName: 'ilbo_income_name', numberOfColumns: 14, titleText: '<div style="margin:0; padding:0;width:100%;border-spacing:0px;background-color:#a7e67b">금액</div>'}
							    , { startColumnName: 'surrogate', numberOfColumns: 2, titleText: '<div style="margin:0; padding:0;width:100%;border-spacing:0px;background-color:#a7e67b">서명상태</div>'}
							]
						});
							  
						$("#list").jqGrid('setLabel', '현장', '', {'background':'red'});
					});	// end gride
				}
 				, beforeSend: function(xhr) {
 					xhr.setRequestHeader("AJAX", true);
 				}
 				, error: function(e) {
 					alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
 				}
			});

			// 검색
			$("#btnSrh").on("click", function() {
				// 검색어 체크
				<%-- // 일일대장 / 구인대장 / 구작대장 구분 --%>
				<c:choose>
					<c:when test="${param.ilboView eq 'Y'}">
						if ( selectWorkerName != "" && selectWorkerName == "" ) {
			    			alert('검색하여 검색하세요.');
			    	
			      			$("#srh_text").focus();
			
			      			return false;
						}
					</c:when>
					<c:otherwise>
						if ( $("#srh_type option:selected").val() != "" && $("#srh_text").val() == "" ) {
			    			alert('검색어를 입력하세요.');
				    	
			      			$("#srh_text").focus();
			
			      			return false;
						}
					</c:otherwise>
				</c:choose>
		
				$("#list").setGridParam({
	        		postData: {
			    		start_ilbo_date	: $("#start_ilbo_date").val(),
			    		end_ilbo_date : $("#end_ilbo_date").val(),
			    		srh_use_yn : $("#srh_use_yn option:selected").val(),
			    		srh_type : $("#srh_type option:selected").val(),
			    		srh_seq	: srh_seq,
			    		srh_text : $("#srh_text").val(),
			    		srh_option : $("#srh_option option:selected").val(),
			    		srh_worker_request : $('#srh_worker_request').is(':checked') ? '1' : '0'
		    		}
	    		}).trigger("reloadGrid");
				
			    lastsel = -1;
			    
			    var ilboSelOpt = 0;
			    
			    if($("#srh_type option:selected").val() == '') {
	    			ilboSelOpt = '0';
	    		}else if($("#srh_type option:selected").val() == 'c.company_name') {
	    			ilboSelOpt = '1';
	    		}else if($("#srh_type option:selected").val() == 'i.ilbo_worker_name') {
	    			ilboSelOpt = '2';
	    		}else if($("#srh_type option:selected").val() == 'ilbo_work') {
	    			ilboSelOpt = '3';    	
	    		}
			    	
			    var _data = {
	    			'ilboStatusOpt' : ($("#srh_use_yn option:selected").val() == '') ? 2 : $("#srh_use_yn option:selected").val(),
	    			'ilboOpt' : ($("#srh_option option:selected").val() == '') ? 0 : $("#srh_option option:selected").val(),
	    			'ilboSelOpt' : ilboSelOpt,
	    			'ilboSearchText' : $("#srh_text").val()
	    		};
			    
			    if('${param.ilboView }' != 'Y'){	//대장이 아닐때
	    			setSearchOptSession(_data);
	    		}
			});
			
			var isShowCell = '${sessionScope.SEARCH_OPT.ilboAllShow }';
		
			// 전체보기
			$("#btnCellShow").click( function() {
				var fieldName = '${fieldName}';
				var fieldNames = fieldName.split(",");
		
				fieldNames.splice(fieldNames.length - 1, 1);
		
				if (isShowCell == '1' || isShowCell == '') { // 간략보기 상태이면 전체보기로 변경
			
					$("#btnCellShow").text("간략보기");
			
	    			$("#list").jqGrid('showCol', [	"ilbo_date", "worker_company_name", "worker_owner", "ilbo_worker_view", "worker_ilbo", "ilbo_worker_name", "ilbo_worker_feature", "ilbo_worker_memo", "ilbo_assign_type", "ilbo_worker_info", "ilbo_worker_status_info", "ilbo_memo",
	            		"work_company_name", "work_owner", "ilbo_employer_view", "employer_ilbo", "employer_name", "ilbo_employer_feature", "addr_edit", "addr_location", "ilbo_work_info", "ilbo_use_info", "ilbo_status_info", "ilbo_work_breakfast_yn", "ilbo_work_age_min",
	            		"ilbo_work_age", "ilbo_work_blood_pressure", "manager_name", "manager_phone", "ilbo_work_manager_name", "ilbo_work_manager_phone", "ilbo_work_arrival", "ilbo_work_finish", "ilbo_job_comment", "work_ilbo", "ilbo_work_name", "ilbo_kind_name", "ilbo_job_comment", "ilbo_chief_memo",
	            		"ilbo_income_name", "ilbo_income_time", "ilbo_unit_price", "ilbo_fee", "share_fee", "ilbo_deduction","counselor_fee", "ilbo_pay", "ilbo_pay_name", "ilbo_pay_time", "ilbo_income_memo", "employer_rating", "evaluate_grade", "use_yn", "reg_date", "reg_admin", "owner_id",
					]);
	    	
	       			isShowCell = '0';
	       	
	       			$("#list").setGridWidth(orgWidth);
				} else {
					$("#btnCellShow").text("전체보기");
					
					isShowCell= '1';
					
					$("#list").jqGrid('hideCol', fieldNames);
	       			$("#list").setGridWidth(smallWidth);
			       	
				}
				
				var _data = {
	   					'ilboAllShow' : isShowCell
	   			}
			   	setSearchOptSession(_data);
			});
			
			// pdf 다운로드
		  	$("#btnDownload").on("click", function() {
		  		var myForm = $('form[name=pdfDownForm]');
		  		var url = "https://s.ilgaja.com/admin/pdfDown";
		  		//var url = "http://211.253.27.183:8181/sign/admin/pdfDown";
		  		//var url = "http://192.168.0.145:18086/admin/pdfDown";
		    	
		  		myForm.attr("action", url); 
		  		
		  		myForm.submit();
		  	});
		});
//]]>
</script>
<%-- // 일일대장 / 구인대장 / 구작대장 구분 --%>
<c:choose>
	<c:when test="${param.ilboView eq 'Y'}">
    	<c:choose>
      		<c:when test="${param.srh_ilbo_type eq 'i.worker_seq'}">
          		<nav id="gnb_sub2">
            		<div class="title">
              			<h3> ※ 구직대장 </h3>
            		</div>
          		</nav>
      		</c:when>
      		<c:otherwise>
				<c:choose>
					<c:when test="${param.srh_ilbo_type eq 'i.work_seq'}">
                        <nav id="gnb_sub3">
                        	<div class="title">
                            	<h3> ※ 현장대장 </h3>
                          	</div>
                        </nav>
                    </c:when>
                    <c:otherwise>
                    	<nav id="gnb_sub">
                        	<div class="title">
                            	<h3> ☆ 구인대장 </h3>
                          	</div>
                        </nav>
                    </c:otherwise>
				</c:choose>
      		</c:otherwise>
    	</c:choose>
  	</c:when>
  	<c:otherwise>
	<%--
    	<div class="title">
      		<h3> 일일대장 </h3>
    	</div>
	--%>
  	</c:otherwise>
</c:choose>

<article>
	<div class="inputUI_simple">
    	<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
        	<caption>등록일시, 상태, 직접검색 영역</caption>
			<%-- // 일일대장 / 구인대장 / 구작대장 구분 --%>
			<c:choose>
  				<c:when test="${param.ilboView eq 'Y'}">
        			<colgroup>
          				<col width="150px" />
          				<col width="1000px" />
          				<col width="120px" />
          				<col width="*" />
        			</colgroup>
        			<tr>
          				<th scope="row">출역일자</th>
          				<td>
            				<p class="floatL">
              					<input type="text" id="start_ilbo_date" name="start_ilbo_date" class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>
              					<input type="text" id="end_ilbo_date" name="end_ilbo_date" class="inp-field wid100 datepicker" />
            				</p>
            				<div class="inputUI_simple">
            					<span class="contGp btnCalendar">
              						<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', 'all'   ); $('#btnSrh').trigger('click');" /><label for="day_type_1" class="label-radio">전체</label>
              						<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', 'today' ); $('#btnSrh').trigger('click');" /><label for="day_type_2" class="label-radio">오늘</label>
              						<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', 'week'  ); $('#btnSrh').trigger('click');" /><label for="day_type_3" class="label-radio">1주일</label>
              						<input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', 'month' ); $('#btnSrh').trigger('click');" /><label for="day_type_4" class="label-radio">1개월</label>
              						<input type="radio" id="day_type_7" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', '2month' ); $('#btnSrh').trigger('click');" checked="checked" /><label for="day_type_7" class="label-radio on">2개월</label>
              						<input type="radio" id="day_type_5" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', '3month'); $('#btnSrh').trigger('click');" /><label for="day_type_5" class="label-radio">3개월</label>
              						<input type="radio" id="day_type_6" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', '6month'); $('#btnSrh').trigger('click');" /><label for="day_type_6" class="label-radio">6개월</label>
            					</span>
            					<span class="tipArea colorN02">* 직접 기입 시, ‘2017-06-28’ 형식으로 입력</span>
            				</div>
          				</td>
          				<th scope="row" class="linelY pdlM">직접검색</th>
         				<td>
   							<c:choose>
     								<c:when test="${param.srh_ilbo_type eq 'i.worker_seq'}">
           							<input type="hidden" id="srh_type" name="srh_type" value="i.worker_seq" />
           							<input type="text" id="srh_text" name="srh_text" class="inp-field wid150 mglS" placeholder="구직자 명" />
     								</c:when>
     								<c:otherwise>
           							<input type="hidden" id="srh_type" name="srh_type" value="${(param.srh_ilbo_type eq 'i.work_seq') ? param.srh_ilbo_type : 'i.employer_seq'}" />
           							<input type="hidden" id="srh_seq" name="srh_seq" value="${param.srh_seq}" />
           							<input type="text" id="srh_text" name="srh_text" class="inp-field wid300 mglS" placeholder="${(param.srh_ilbo_type eq 'i.work_seq') ? '현장 명' : '구인처 명'}" />
     								</c:otherwise>
   							</c:choose>
              					<div class="btn-module floatL mglS">
             						<a href="#none" id="btnSrh" class="search">검색</a>
             						<a href="#none" id="btnReset" class="btnStyle05">검색초기화</a>
           					</div>
         				</td>
        			</tr>
  				</c:when>
  				<c:otherwise>
        			<colgroup>
          				<col width="100px">
          				<col width="410px">
          				<col width="80px"> 
          				<col width="100px">
          				<col width="80px"> 
          				<col width="150">
          				<col width="100px">
          				<col width="180px">
          				<col width="100px">
          				<col width="*">
        			</colgroup>
        			<tr>
          				<th scope="row" class="linelY pdlM">출역일자</th>
          				<td>
            				<span class="floatL arrow">
              					<a class="prev10" href="javascript:prevMonthSubmit('start_ilbo_date'); $('#btnSrh').trigger('click');">>></a>
              					<a class="prev" href="javascript:prevDateSubmit('start_ilbo_date'); $('#btnSrh').trigger('click');">></a>
            				</span>
            				<p class="floatL">
              					<input type="text" id="start_ilbo_date" name="start_ilbo_date" class="inp-field wid100 datepicker" onChange="javaScript:$('#end_ilbo_date').val($('#start_ilbo_date').val()); $('#btnSrh').trigger('click');" />
              					<input type="hidden" id="end_ilbo_date" name="end_ilbo_date" />
            				</p>
            				<span class="floatL mgRS arrow">
              					<a class="next" href="javascript:nextDateSubmit('start_ilbo_date'); $('#btnSrh').trigger('click');">></a>
              					<a class="next10" href="javascript:nextMonthSubmit('start_ilbo_date'); $('#btnSrh').trigger('click');">>></a>
            				</span>
            				<div class="inputUI_simple">
            					<span class="contGp btnCalendar">
              						<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_ilbo_date', 'end_ilbo_date', 'today' ); $('#btnSrh').trigger('click');" checked="checked" /><label for="day_type_2" class="label-radio on">오늘</label>
            					</span>
            				</div>
          				</td>
          				<th scope="row" class="linelY pdlM">상태</th>
          				<td>
          					<select id="srh_use_yn" name="srh_use_yn" class="styled02 floatL wid100">
          						<option value="" <c:if test="${sessionScope.SEARCH_OPT.ilboStatusOpt eq '2' }">selected</c:if>>전체</option>
          						<option value="1" <c:if test="${sessionScope.SEARCH_OPT.ilboStatusOpt eq '1' or sessionScope.SEARCH_OPT.ilboStatusOpt eq null }"> selected</c:if>>사용</option>
          						<option value="0" <c:if test="${sessionScope.SEARCH_OPT.ilboStatusOpt eq '0' }"> selected</c:if>>미사용</option>
          					</select>
          					<%-- 
            				<input type="radio" id="srh_use_yn_1" name="srh_use_yn" class="inputJo" value="" <c:if test="${sessionScope.SEARCH_OPT.ilboStatusOpt eq '2' }">checked="checked"</c:if> /><label for="srh_use_yn_1" class="label-radio">전체</label>
            				<input type="radio" id="srh_use_yn_2" name="srh_use_yn" class="inputJo" value="1" <c:if test="${sessionScope.SEARCH_OPT.ilboStatusOpt eq '1' or sessionScope.SEARCH_OPT.ilboStatusOpt eq null }">checked="checked"</c:if> /><label for="srh_use_yn_2" class="label-radio">사용</label>
            				<input type="radio" id="srh_use_yn_3" name="srh_use_yn" class="inputJo" value="0" <c:if test="${sessionScope.SEARCH_OPT.ilboStatusOpt eq '0' }">checked="checked"</c:if> /><label for="srh_use_yn_3" class="label-radio">미사용</label>
            				 --%>
          				</td>
						<th scope="row" class="linelY pdlM">옵션</th>
         				<td>
         					<select id="srh_option" name="srh_option" class="styled02 floatL wid100">
	           					<option value=""  <c:if test="${sessionScope.SEARCH_OPT.ilboOpt eq '0' or sessionScope.SEARCH_OPT.ilboOpt eq null}">selected</c:if> />전체</option>
	           					<option value="1" <c:if test="${sessionScope.SEARCH_OPT.ilboOpt eq '1' }">selected</c:if> />배정완료</option>
	           					<option value="2" <c:if test="${sessionScope.SEARCH_OPT.ilboOpt eq '2' }">selected</c:if> />미배정 현장</option>
	           					<option value="3" <c:if test="${sessionScope.SEARCH_OPT.ilboOpt eq '3' }">selected</c:if> />미배정 구직자</option>
	           					<option value="4" <c:if test="${sessionScope.SEARCH_OPT.ilboOpt eq '4' }">selected</c:if> />현장</option>
	           					<option value="5" <c:if test="${sessionScope.SEARCH_OPT.ilboOpt eq '5' }">selected</c:if> />구직자</option>
	           				</select>
         				</td>
         				<th scope="row" class="linelY pdlM">구직상태</th>
         				<td>
         					<input type="checkbox" id="srh_worker_request" value="1">구직신청자 포함
         				</td>
						<th scope="row" class="linelY pdlM">직접검색</th>
          				<td>
            				<div class="select-inner">
            					<select id="srh_type" name="srh_type" class="styled02 floatL wid100">
              						<option value="">전체</option>
									<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
              							<option value="c.company_name" <c:if test="${sessionScope.SEARCH_OPT.ilboSelOpt eq '1' }">selected</c:if>>지점</option>
									</c:if>
              						<option value="i.ilbo_worker_name" <c:if test="${sessionScope.SEARCH_OPT.ilboSelOpt eq '2' }">selected</c:if>>구직자</option>
              						<option value="ilbo_work" <c:if test="${sessionScope.SEARCH_OPT.ilboSelOpt eq '3' }">selected</c:if>>현장</option>
            					</select>
            				</div>
            				<input type="text" id="srh_text" name="srh_text" class="inp-field wid200 mglS" <c:if test="${sessionScope.SEARCH_OPT.ilboSearchText ne null }">value="${sessionScope.SEARCH_OPT.ilboSearchText }"</c:if>/>
            				<div class="btn-module floatL mglS">
              					<a href="#none" id="btnSrh" class="search">검색</a>
              					<a href="#none" id="btnReset" class="btnStyle05">검색초기화</a>
            				</div>
          				</td>
        			</tr>
       			</c:otherwise>
			</c:choose>   
      	</table>
	</div>
</article>

<div class="btn-module mgtS mgbS">
	<div class="leftGroup">
    	<a href="#none" id="btnAdd" class="btnStyle01">추가</a>
        <a href="#none" id="btnDel" class="btnStyle01">삭제</a>
        <a href="#none" id="btnRel" class="btnStyle05">새로고침</a>
        <a href="#none" id="btnWPush" class="btnStyle01">알선 Push</a>
        <a href="#none" id="btnAlim" class="btnStyle01">메시지Push</a>
<!--         <a href="#none" id="btnJobAlim" class="btnStyle01">일자리Push</a> -->
<!--         <a href="#none" id="btnWSMS" class="btnStyle01">구직자 SMS</a> -->
<!--         <a href="#none" id="btnMSMS" style="width: 110px;" class="btnStyle01">현장매니저 SMS</a> -->
<!--         <a href="#none" id="btnESMS" style="width: 110px;" class="btnStyle01">현장담당자 SMS</a> -->
        <a href="#none" id="btnCellShow" class="btnStyle05">
        	<c:choose>
        		<c:when test="${sessionScope.SEARCH_OPT.ilboAllShow eq '0' }">
        			간략보기
        		</c:when>
        		<c:otherwise>
        			전체보기
        		</c:otherwise>
        	</c:choose>
        </a>
        <a href="#none" id="btnHelp" class="btnStyle05">도움말</a>
        <!-- <a href="#none" id="btnEmployerAdd" class="btnStyle01">현장 등록</a>  nemojjang 주석처리 2021-04-14-->
	</div>

    <div class="leftGroup" style="padding-left: 80px">
    	<span class="floatL">
        	<input type="checkbox" id="copy_worker" name="copy_worker" class="ui_checkbox" value="Y" /><label for="copy_worker" class="checkTxt" style="padding-top:5px">구직자 정보 복사 여부</label>
        </span>
        <span class="floatL mgRS">
          	<input  id="copy_rows" name="copy_rows" class="inp-field wid50" placeholder="행 수" type="number"  min="1" max="100" value="1"/>
        </span>
        <span class="floatL arrow">
          	<a class="prev10" href="javascript:prevMonth('to_ilbo_date');">>></a>
          	<a class="prev" href="javascript:prevDate('to_ilbo_date');">></a>
        </span>
        <span class="floatL">
          	<input type="hidden" id="from_ilbo_date" name="from_ilbo_date" />
          	<input type="text" id="to_ilbo_date" name="to_ilbo_date" class="inp-field wid100 datepicker" placeholder="대상일자" />
        </span>
       	<span class="floatL mgRS arrow">
          	<a class="next" href="javascript:nextDate('to_ilbo_date');">></a>
          	<a class="next10" href="javascript:nextMonth('to_ilbo_date');">>></a>
       	</span>
        <span class="btn-module">
          	<a href="#none" id="btnCopy" name="btnCopy" class="btnStyle16 mglS">복사</a>
        	<c:if test="${param.srh_ilbo_type eq 'i.work_seq' or param.srh_ilbo_type eq 'i.employer_seq'}">
          		<a href="#none" id="btnExcel" name="btnExcel" class="btnStyle04 mglS">Excel</a>
        	</c:if>
        </span>
	</div>

    <div class="leftGroup" style="padding-left: 80px;padding-top:10px">
		총 :&nbsp;&nbsp;&nbsp; <span id="uniqueE"></span>&nbsp;&nbsp;/&nbsp;&nbsp; <span id="totalE"></span>
	</div>

    <div class="leftGroup" style="padding-left: 80px;padding-top:10px">
		<div style="display:inline; margin-right:10px;">단가합 : <span id="totalPrice" style="color: #ef0606;" ></span></div>
		<div style="display:${sessionScope.ADMIN_SESSION.auth_level eq 3 ? 'none' : 'inline'}; margin-right:10px;">수수합 : <span id="totalFee" style="color: #ef0606; margin-right: 10px;" ></span>쉐어료 합 : <span id="totalShareFee" style="color: #ef0606;" ></span></div>
		<div style="display:${sessionScope.ADMIN_SESSION.auth_level eq 3 ? 'inline' : 'none'}; margin-right:10px;">상담합 : <span id="totalcounselor" style="color: #ef0606;" ></span></div>
		<div style="display:inline; margin-right:10px;">구직자합 : <span id="totalPay" style="color: #ef0606;"></span></div>
	</div>
</div>

<div id="helpPanel" class="helpPanel">
	<table class="helpTable">
    	<thead>
        	<tr>
          		<th scope="cols">구분</th>
          		<th scope="cols">내용</th>
      		</tr>
      	</thead>
      	<tbody>
	        <tr>
        		<th scope="row">구직자</th>
          		<td>
            		<div class="coltip"></div >현장의 출석일수
            		<div class="coltip" style="background: #a7e67b; "></div >2회
            		<div class="coltip" style="background: #e6c57b; "></div >3회이상
          		</td>
        	</tr>
        	<tr>
	          	<th scope="row">노임지급</th>
	         	<td>
	           		<div class="coltip"></div >수수,통수,통장,현금
	           		<div class="coltip" style="background: #F9CB9C; "></div >(수수)
	           		<div class="coltip" style="background: #D5A6BD; "></div >(통수)
	           		<div class="coltip" style="background: #B4A7D6; "></div >(통장)
	           		<div class="coltip" style="background: #F18E8E; "></div >(현금)
	         	</td>
        	</tr>
        	<tr>
          		<th scope="row">노임수령</th>
          		<td>
            		<div class="coltip"></div >노통,노현,당통,단기,기성
            		<div class="coltip" style="background: #EC9FEC; "></div >(노통)
            		<div class="coltip" style="background: #DEB5F9; "></div >(노현)
            		<div class="coltip" style="background: #8FB5EF; "></div >(당통)
            		<div class="coltip" style="background: #A8F5A8; "></div >(단기)
            		<div class="coltip" style="background: #DADCDA; "></div >(기성)
          		</td>
        	</tr>
        	<tr>
          		<th scope="row">구직자정보 교</th>
          		<td>
            		<div class="coltip"></div >정보없음,스캔없음
            		<div class="coltip" style="background: #5aa5da; "></div >모두있음
            		<div class="coltip" style="background: #81d80c; "></div >정보만
            		<div class="coltip" style="background: #e4d310; "></div >스캔파일만
          		</td>
        	</tr>
        	<tr>
          		<th scope="row">구직자정보 통</th>
          		<td>
            		<div class="coltip"></div >정보없음,스캔없음
            		<div class="coltip" style="background: #5aa5da; "></div >예금주 = 구직자, 스캔있음
            		<div class="coltip" style="background: #81d80c; "></div >예금주 = 구직자, 스캔없음
            		<div class="coltip" style="background: #e4d310; "></div >예금주 <> 구직자, 스캔있음
            		<div class="coltip" style="background: #ef7220; "></div >예금주 <> 구직자, 스캔없음
          		</td>
        	</tr>
        	<tr>
          		<th scope="row">지도</th>
          		<td>
            		<div class="coltip"></div >좌표없음
            		<div class="coltip" style="background: #ef7220; "></div >좌표있음
          		</td>
        	</tr>
		</tbody>
	</table>
</div>


<table id="list"></table>

<div id="opt_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b;"></div>
<div id="code_log_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b; padding:5px">NO Data</div>

<iframe id="download-ifrm" name="download-ifrm" width="0" height="0" title="다운로드를 위한 빈프레임" frameborder="0" scrolling="no"></iframe>


<!-- 직종옵션 팝업  -->
<div id="popup-layer" style="width: 1300px; height: 800px;">
	<header class="header">
    	<h1 class="tit">직종선택</h1>
    	<a class="close" href="javascript:closeIrPopup('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
  	</header>
  	<input type="hidden" id="jobRowId" name="jobRowId">
  	<section>
  		<div class="cont-box" name="popup-loayer-cont-box">
    		<article>
      			<table class="bd-list" summary="구인처 등록 영역입니다.">
      				<colgroup>
        				<col width="35%" />
        				<col width="*" />
        				<col width="30%" />
      				</colgroup>
      				<tbody>
        				<tr>
          					<th class="linelY">직종</th>
							<th>세부직종</th>
							<th class="lineRY">옵션선택</th>
        				</tr >
        				<tr>
          					<td class="linelY" style="vertical-align: top;"><div class="jobListArea panel1"></div></td>
          					<td style="vertical-align: top;"><div class="jobDetailArea panel1"></div></td>
          					<td class="lineRY" style="vertical-align: top;"><div class="jobOptionArea panel1"></div></td>
        				</tr>
      				</tbody>
      			</table>
    		</article>
	
    		<div class="btn-module mgtS">
    			<div class="leftGroup"><a href="javascript:closeIrPopup('popup-layer');" id="btnJobClose" class="btnStyle08">닫기</a></div>
     			<div class="rightGroup"><a href="#popup-layer" id="btnJobReset" class="btnStyle08">초기화</a></div>
      			<div class="rightGroup"><a href="#popup-layer" id="btnJobReg" class="btnStyle01">등록</a></div>
			</div>
		</div>
  	</section>
</div>

<div id="popup-layer2" style="width:1600px; height: 400px;">
	<input type="hidden" id="popupManagementSeq" value="">
	<form id="frmPopup" name="frmPopup" method="post">
		<header class="header">
   			<h1 class="tit">문서 이력</h1>
   			<a class="close" href="javascript:closeIrPopup('popup-layer2');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
 		</header>

 		<section>
 			<div class="cont-box">
   				<article>
     				<table class="bd-form inputUI_simple scrolltbody" summary="문서 이력 영역입니다.">
     					<caption>문서 이력 보기 영역</caption>
     					<colgroup>
       						<col width="8%" />
       						<col width="9%" />
       						<col width="8%" />
       						<col width="10%" />
       						<col width="10%" />
       						<col width="11%" />
       						<col width="8%" />
       						<col width="9%" />
       						<col width="10%" />
       						<col width="1%" />
       						<col width="*" />
     					</colgroup>
     					<thead>
     						<tr>
      							<th scope="col">사용자 구분</th>
      							<th scope="col">사용자 하위구분</th>
      							<th scope="col">이름</th>
      							<th scope="col">ID</th>
         						<th scope="col">동작</th>
         						<th scope="col">일시</th>
         						<th scope="col">서비스 구분</th>
         						<th scope="col">접속환경</th>
         						<th scope="col">IP</th>
         						<th scope="col" style="width: 9.9%;">user agent</th>
         						<th scope="col">위도, 경도</th>
     						</tr>
     					</thead>
     					<tbody id="historyBody">
     					</tbody>
     				</table>
   				</article>

   				<div class="btn-module mgtS">
     				<div class="rightGroup"><a href="#popup-layer2" id="btnDownload" class="btnStyle01">다운로드</a></div>
<!--       					<div class="rightGroup"><a href="#popup-layer" id="btnProDownload" class="btnStyle01">진행이력 다운로드</a></div> -->
   				</div>
 			</div>
 		</section>
	</form>
	<form id="pdfDownForm" name="pdfDownForm" method="POST">
		<input type="hidden" name="admin_seq" id="admin_seq" value="${sessionScope.ADMIN_SESSION.admin_seq }" />
		<input type="hidden" name="admin_name" id="admin_name" value="${sessionScope.ADMIN_SESSION.admin_name }" />
		<input type="hidden" name="management_seq" id="management_seq" value="" />
	</form>
</div>

<div id="popup-layer3" style="width: 640px; height: 200px;">
	<header class="header">
  			<h1 class="tit">공제액</h1>
  			<a class="close" href="javascript:closeIrPopup('popup-layer3');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
		</header>

		<section>
			<div class="cont-box">
  				<article>
    				<table class="bd-form inputUI_simple" summary="공제액 영역입니다.">
    					<caption>공제액 영역</caption>
    					<colgroup>
      						<col width="*" />
    					</colgroup>
    					<thead>
    						<tr>
     							<th class="linelY" scope="col">갑근세</th>
     							<th class="linelY" scope="col">지방세</th>
     							<th class="linelY" scope="col">고용보험</th>
     							<th class="linelY" scope="col">국민연금</th>
        						<th class="linelY" scope="col">건강보험</th>
        						<th class="linelY" scope="col">장기요양</th>
    						</tr>
    					</thead>
    					<tbody id="deductibleBody">
    					</tbody>
    				</table>
  				</article>
			</div>
		</section>
</div>
<script type="text/javascript">
	var jobList = new Array();
	var jobSeqArray = new Array();	// 선택한 직종 array
	var totalOptionPrice = 0;
	var unitPrice = 0;
	
	$(function() {
		var _data = {
			use_yn	:"1"
			, del_yn	:"0"
			, orderBy 	: "job_kind, job_order"
		}

		var _url = "/admin/getJobList";

		commonAjax(_url, _data, true, function(data) {
			//successListener
			if (data.header.code == "0000") {
				jobList = data.body.jobList;
				fn_oneJobList();
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						toastFail(data.message);
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
		
		
		//동적이벤트 달기
	    $(document).on("click", ".jobList", function() {
    		
    		jobCalInit(1);
	    	
    		$(".jobList").removeClass('on');
        	$(this).addClass('on');
	        
        	var jobSeq = $(this).attr("job_seq");
        	var jobName = $(this).attr("job_name");
        	var jobKind = $(this).attr("job_kind");
        	var detailUseYn = $(this).attr("detail_use_yn");
        	var jobFee = $(this).attr("job_work_fee");
        	var workerFee = $(this).attr("job_worker_fee");
	        
        	jobSeqArray[0] = {jobSeq: jobSeq, jobName: jobName, jobKind: jobKind, detailUseYn: detailUseYn, job_work_fee : jobFee, job_worker_fee : workerFee};
	    	
    		fn_twoJobList();
	        
	    });
		
	    $(document).on("change", "input[name='jobDetail']", function() {
    		
    		jobCalInit(2);
	    	
    		var jobSeq = $("input[name='jobDetail']:checked").val();
    		var jobName = $("input:radio[name=jobDetail]:checked").attr("job_name");
	
    		jobSeqArray[1] = {jobSeq: jobSeq, jobName: jobName};
	    	
    		fn_threeJobList();
    	});
	    
	    $(document).on("change", "input:checkbox[name=jobOption]", function() {
    		totalOptionPrice = 0;
    		var optionSeqArr = new Array();
			var optionNameArr = new Array();
			
        	$("input:checkbox[name=jobOption]").each(function() {
	        	
        		if($(this).is(":checked")) {
					var jobSeq = $(this).val();
					var jobName = $(this).attr("job_name");
									
        			optionSeqArr.push(jobSeq);
        			optionNameArr.push(jobName);
	        		
        			jobSeqArray[2] = {jobSeq: optionSeqArr.join(), jobName: optionNameArr.join("|")};
					totalOptionPrice += getAddPrice(jobSeq);
        		}
	        	
        	});
	        
        	fn_complite(1);
	        
        	
    	});
	    
	   
	    $('#btnJobReg').click(function(){
    		if(unitPrice > 0){
		    	fn_jobSubmit();
    		}else{
	    		alert("직종을 선택 하세요.");
    		}
    	});
	    
	    $('#btnJobReset').click(function(){
    		fn_jobSubmit();
    	});
	    
	});
	
	function fn_jobSubmit(){
		var rowId = $("#jobRowId").val();
		
		fn_setIlboFee(rowId, "ilbo_unit_price", unitPrice, true);
		
		var params = {
			ilbo_seq: $("#jobRowId").val(),
			ilbo_kind_code: jobSeqArray[0].jobSeq,
			ilbo_kind_name: jobSeqArray[0].jobName,
			ilbo_job_detail_code: jobSeqArray[1].jobSeq,
			ilbo_job_detail_name: jobSeqArray[1].jobName,
			ilbo_job_add_code: jobSeqArray[2].jobSeq,
			ilbo_job_add_name: jobSeqArray[2].jobName,
			ilbo_unit_price: unitPrice,
			ilbo_fee: $("#list").jqGrid('getRowData', rowId).ilbo_fee,
			share_fee: $("#list").jqGrid('getRowData', rowId).share_fee,
			counselor_fee: $("#list").jqGrid('getRowData', rowId).counselor_fee,
			ilbo_pay: $("#list").jqGrid('getRowData', rowId).ilbo_pay,
			fee_info: $("#list").jqGrid('getRowData', rowId).fee_info,
			pay_info: $("#list").jqGrid('getRowData', rowId).pay_info,
			work_fee: $("#list").jqGrid('getRowData', rowId).work_fee,
			worker_fee: $("#list").jqGrid('getRowData', rowId).worker_fee
		}

  		$.ajax({
			type: "POST",
        	url: "/admin/setIlboInfo",
       		data: params,
   			dataType: 'json',
    		success: function(data) {
				//새로고침을 안하기 위해서
    			if(data.result == "0000"){
		    		var cssYellow = {'background-color' : 'Yellow'};
    			 	
    	    		$("#list").jqGrid("setCell", rowId, "ilbo_kind_code", jobSeqArray[0].jobSeq,	'',true);
    	    		$("#list").jqGrid("setCell", rowId, "ilbo_kind_name", jobSeqArray[0].jobName,	'',true);
	    	    	
    	    		$("#list").jqGrid("setCell", rowId, "ilbo_job_detail_code",	jobSeqArray[1].jobSeq,	'',true);
    	    		$("#list").jqGrid("setCell", rowId, "ilbo_job_detail_name",	jobSeqArray[1].jobName,	'',true);
	    	    	
    	    		$("#list").jqGrid("setCell", rowId, "ilbo_job_add_code", jobSeqArray[2].jobSeq,	'',true);
    	    		$("#list").jqGrid("setCell", rowId, "ilbo_job_add_name", jobSeqArray[2].jobName ,	'',true);
    	    		$("#list").jqGrid("setCell", rowId, "ilbo_unit_price",	unitPrice,	'',true);
	    	    	
    	    		var ilboPay = $("#list").jqGrid('getRowData', rowId).ilbo_pay;
    	    		var deductibleSum = $("#list").jqGrid('getRowData', rowId).deductible_sum;
    	    		
    	    		$("#list").jqGrid("setCell", rowId, "wages_received", ilboPay - deductibleSum, '', true);
    	    		
    	    		$("#jobRowId").val("");
	    	    	
    	    		if(jobSeqArray[0].jobKind == '2') {
	    	    		$("#div_kind_" + rowId).children().css("background-color", "Yellow");
    	    		}
	    	    	
    	    		closeIrPopup('popup-layer');
					
	//     	    	fn_setIlboFee(rowId, "ilbo_unit_price", unitPrice);
    			}else if(data.result == "1001"){
	    			alert("구직자 를 확인 하세요.");
    			}
			},
 			beforeSend: function(xhr) {
	        	xhr.setRequestHeader("AJAX", true);
   				$(".wrap-loading").show();
			},
      		error: function(e) {
	        	if ( data.status == "901" ) {
            		location.href = "/admin/login";
				}
			},
			complete : function() {
				// 요청 완료 시
				$(".wrap-loading").hide();
			}
		});
	}
	
	function jobCalInit(flag){
		if(flag == 0){
			totalOptionPrice = 0;
			
			jobSeqArray[0] = {jobSeq:null, jobName:null};
			jobSeqArray[1] = {jobSeq:null, jobName:null};
			jobSeqArray[2] = {jobSeq:null, jobName:null};
			
			fn_oneJobList();
			$(".jobDetailArea").html("");
			$(".jobOptionArea").html("");
		}
		
		if(flag ==1){
			jobSeqArray[1] = {jobSeq:null, jobName:null};
			jobSeqArray[2] = {jobSeq:null, jobName:null};
			$(".jobDetailArea").html("");
		}
		if(flag ==2){
			jobSeqArray[2] = {jobSeq:null, jobName:null};
		}
		
		if(flag <=2){
			totalOptionPrice = 0; 
	    	$(".jobOptionArea").html("");
		}
		
		unitPrice = 0;
		
	}
	
	function getAddPrice(jobSeq){
		var job = jobList.find(function(item){    
		  	return (item.job_seq === jobSeq);
		}); 
				
		return job.basic_price *1;
	}
	
	function fn_oneJobList(){
		
		var oneDepthJobList = jobList.filter(function(item){    
		  	return item.job_rank === "1";
		});  
		
		
		var _html = '';
				
		var jobKind = 0;
		
		var kindHtml1 = '<div class="cal_title">잡부조공</div>';
		var kindHtml2 = '<div class="cal_title">기술인력</div>';
		
		
		oneDepthJobList.forEach(function(job,index, array){
			var jobHtml = '<div class="jobList buttonPanel" job_kind="' + job.job_kind + '" job_seq="' +job.job_seq + '" job_work_fee="' + job.job_work_fee + '" job_worker_fee="' + job.job_worker_fee + '" job_name="' +job.job_name + '" detail_use_yn="' +job.detail_use_yn + '" >'+ job.job_name +'</div>';
			
			if(job.job_kind == 1){
				kindHtml1	+= jobHtml;
			}else{
				kindHtml2	+= jobHtml;
			}
			
		});
		
		_html += kindHtml1;
		_html += kindHtml2;
		
	
		$(".jobListArea").html(_html);
	}
	
	
	function fn_twoJobList(){
		
		var jobSeq		= jobSeqArray[0].jobSeq;
		
		var twoJobSeq = 0;
		
		var twoDepthJobList = jobList.filter(function(item){    
		  	return (item.job_code === jobSeq && item.job_rank ==2);
		});  
		
		var threeDepthJobList = jobList.filter(function(item){    
		  	return (item.job_code === jobSeq && item.job_rank ==3);
		});
		
		if( jobSeqArray[0].detailUseYn == "1"){
	// 	if(twoDepthJobList.length > 0){
			
			var _html = '';
			
			twoDepthJobList.forEach(function(job,index, array){
	            _html += '<input type="radio" class="radioBasic" id="twoDepthJob'+ index+'" name="jobDetail" value="'+ job.job_seq+'" job_name="'+ job.job_name+'"><label for="twoDepthJob'+ index+'" class="radioLabel" >'+ job.job_name +'</label><br>';
			});
			
			$(".jobDetailArea").html(_html);
		}else if(threeDepthJobList.length > 0) {
			var _html = '';
			
			threeDepthJobList.forEach(function(job,index, array){
				_html += '<input type="checkBox" class="radioBasic" id="jobOption'+ index+'" name="jobOption" value="'+ job.job_seq+'" job_name="'+ job.job_name+'"><label for="jobOption'+ index+'" class="radioLabel" >'+ job.job_name +' ( + '+ comma(job.basic_price) +'원)</label><br>';
			});
			
			$(".jobOptionArea").html(_html);
			
			fn_complite(0);
		}else {
			jobSeqArray[1] = {jobSeq:null, jobName:null};
			jobSeqArray[2] = {jobSeq:null, jobName:null};
			
			fn_complite(0);
		}
			
	}
	
	function fn_threeJobList(){
		
		var jobSeq		= jobSeqArray[1].jobSeq;
		
		var threeDepthJobList = jobList.filter(function(item){    
		  	return (item.job_code === jobSeq && item.job_rank ==3);
		});  
		
		var _html = "";
		if(threeDepthJobList.length > 0){
			
			
			threeDepthJobList.forEach(function(job,index, array){
				_html += '<input type="checkBox" class="radioBasic" id="jobOption'+ index+'" name="jobOption" value="'+ job.job_seq+'" job_name="'+ job.job_name+'"><label for="jobOption'+ index+'" class="radioLabel" >'+ job.job_name +' ( + '+ comma(job.basic_price) +'원)</label><br>';
			});
			
			
			$(".jobOptionArea").html(_html);
		}else{
			jobSeqArray[2] = {jobSeq:null, jobName:null};
			$(".jobOptionArea").html("직종 옵션 없음");
		}
		
		fn_complite(1);
	}
	
	function fn_complite(index){
		var jobSeq = jobSeqArray[index].jobSeq; 
	
		if(!jobSeq) {
			jobSeq = jobSeqArray[0].jobSeq;
		}
		
		var lastJob = jobList.find(function(item){    
		  	return (item.job_seq === jobSeq);
		});
		
		var rowId = $("#jobRowId").val();
		
		var workArrival = getTimeNumber($("#list").jqGrid('getRowData', rowId).ilbo_work_arrival);
		var workFinish = getTimeNumber($("#list").jqGrid('getRowData', rowId).ilbo_work_finish);
			
		var price = fn_getPrice(workArrival, workFinish, lastJob.basic_price, lastJob.short_price, lastJob.short_price_night, lastJob.add_day_time, lastJob.add_night_time);
	
		unitPrice = price + totalOptionPrice;
	}
	
	// 문서 서명 상태 formatter
	function fn_laborComplete(cellvalue, options, rowObject) {
		var returnText = '';
		
		if(rowObject.labor_contract == '1') {
			returnText += "<a style='color: #1969C3; font-weight: bold; text-decoration: underline;' href='javascript:void(0);' onclick='fn_signHistory(" + rowObject.labor_management_seq + ")'>완료</a>";
		}else if(rowObject.labor_contract == '2') {
			returnText += "<a style='color: #64CD3C; font-weight: bold; text-decoration: underline;' href='javascript:void(0);' onclick='fn_signHistory(" + rowObject.labor_management_seq + ")'>서명중(1/2)</a>";
		}else {
			returnText += '';
		}
		
		return returnText;
	}
	
	function fn_surrogateComplete(cellvalue, options, rowObject) {
		var returnText = '';
		
		if(rowObject.surrogate == '1') {
			returnText += "<a style='color: #1969C3; font-weight: bold; text-decoration: underline;' href='javascript:void(0);' onclick='fn_signHistory(" + rowObject.receive_management_seq + ", " + rowObject.worker_company_seq + ")'>완료</a>";
		}else if(rowObject.surrogate == '2') {
			returnText += "<a style='color: #64CD3C; font-weight: bold; text-decoration: underline;' href='javascript:void(0);' onclick='fn_signHistory(" + rowObject.receive_management_seq + ", " + rowObject.worker_company_seq + ")'>서명중(1/2)</a>";
		}else {
			returnText += '';
		}
		
		return returnText;
	}
	
	function isSuperAdmin(){
		var authLevel = '${sessionScope.ADMIN_SESSION.auth_level }'; 
		
		return authLevel == 0;
	}
	
	function permissionReceiveContract(workerCompanySeq){
		var companySeq = '${sessionScope.ADMIN_SESSION.company_seq }';
		
		if( isSuperAdmin() ){
			return true;
		}
		
		return companySeq == workerCompanySeq;
	}
	
	// 문서 이력보기 layer popup
	function fn_signHistory(managementSeq, workerCompanySeq) {
		// 관리자 이상부터 layer popup을 볼 수 있다.
		var loginAuthLevel = '${sessionScope.ADMIN_SESSION.auth_level }';
		
		if(loginAuthLevel > 1) {
			return false;
		}
		
		if( workerCompanySeq != null ){
			if( !permissionReceiveContract(workerCompanySeq) ){
				return false;
			}
		}
		
		var companySeq = '${sessionScope.ADMIN_SESSION.company_seq }';
		var params = {
			management_seq : managementSeq
		};
		
		$("#popupManagementSeq").val(managementSeq);
		$("#management_seq").val(managementSeq);
		
		$.ajax({
			type : "POST",
			url	: "/admin/getHistoryList",
			data : params,
			dataType: 'json',
			success	: function(data) {
				if(data.boolSize == 0) {
					$("#btnDownload").hide();
				}else {
					$("#btnDownload").show();
				}
				
				var resultList = data.resultList;
				var txt = ''; 	
				
				for(var i = 0; i < resultList.length; i++) {
					txt += '<tr>';
					txt += '	<td>'; 
					
					if(resultList[i].history_use_type == 'ESH008' || resultList[i].history_use_type == 'ESH009') {
						txt += '서명요청자';
					}else if(resultList[i].history_use_type == 'ESH010' || resultList[i].history_use_type == 'ESH011' || resultList[i].history_use_type == 'ESH012') {
						txt += '서명대상자';
					}else if(resultList[i].history_use_type == 'ESH013') {
						txt += '참조자';
					}
					
					txt += '	</td>';
					txt += '	<td>' + resultList[i].history_use_type_name + '</td>';
					txt += '	<td>' + resultList[i].history_use_name + '</td>';
					txt += '	<td>' + fn_phoneFormat(resultList[i].history_use_phone) + '</td>';
					txt += '	<td>' + resultList[i].history_type_name + '</td>';
					txt += '	<td>' + resultList[i].reg_date + '</td>';
					txt += '	<td>' + resultList[i].history_connect_type + '</td>';
					txt += '	<td>' + resultList[i].history_use_os_name + '</td>';
					txt += '	<td>' + resultList[i].history_use_ip + '</td>';
					txt += '	<td><div title="' + resultList[i].history_use_userAgent + '" style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 98px;">' + resultList[i].history_use_userAgent + '</div></td>';
					txt += '	<td style="padding-left: 0%;"><div title="' + resultList[i].history_use_latlng + '" style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 70px;">' + resultList[i].history_use_latlng + '</div></td>';
					txt += '</tr>';					
				}
				
				$("#historyBody").html(txt);
				
				openIrPopup('popup-layer2');
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
	}
	
	function fn_phoneFormat(value) {
		var pattern = /^(\d{2,3})(\d{3,4})(\d{4})$/;
				
		if(!value) {
			value = '';
		}
				
		var result = value.replace(pattern, '$1-$2-$3');
		
		return result;
	}
	
	function fn_receiveName(cellValue, options, rowObject) {
		if(rowObject.receive_contract_seq == '0' || rowObject.receive_contract_seq === undefined ) {
			return "미사용";
		}else {
			if(lastElement != '') {
				for(var i = 0; i < $(lastElement)[0].childNodes.length; i++) {
					$(lastElement).remove();	
					
					if(cellValue == $($(lastElement)[0].childNodes[i]).val()) {
						$($(lastElement).parent()[0]).text($($(lastElement)[0].childNodes[i]).text());
						
						return $($(lastElement)[0].childNodes[i]).text();
					}
				}
			}
			
			return rowObject.receive_contract_name;
		}
	}
	
	function fn_laborName(cellValue, options, rowObject) {
		if(rowObject.labor_contract_seq == '0') {
			return "미사용";
		}else {
			if(lastElement != '') {
				for(var i = 0; i < $(lastElement)[0].childNodes.length; i++) {
					$(lastElement).remove();	
					
					if(cellValue == $($(lastElement)[0].childNodes[i]).val()) {
						$($(lastElement).parent()[0]).text($($(lastElement)[0].childNodes[i]).text());
						
						return $($(lastElement)[0].childNodes[i]).text();
					}
				}
			}
			
			return rowObject.labor_contract_name
		}
	}
	
	function fn_validWorkBreaktime(value, name) {
		if(value != null && value != '') {
			var regExp = /[^0-9_\`\~\!\@\#\$\%\^\&\*\(\)\-\=\+\\\{\}\[\]\'\"\;\:\<\,\>\.\?\/\s]/gm;
			
			if(regExp.test(value)) {
				return [false, "영문 및 한글은 사용이 불가능합니다."];
			}
		}
		return [true, ""];
	}
</script>