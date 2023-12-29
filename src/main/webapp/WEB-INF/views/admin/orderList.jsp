<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/order.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/jobList.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/jobCal.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ehjyv0iw4b&submodules=geocoder"></script>

<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/web/css/main.css' />"	media="all" charset="utf-8"></link>
<style>
	tr.onMouse{ height: 50px; background-color: #0c6f31; color: white;}
	tr.offMouse{ height: 30px; }

	.popup1{display:none;position:fixed;z-index:3;width:100%;height:100%;overflow-y:scroll;}
	/* jopOffer */
	.jobTable {
    	width: 100%;
    	border: 1px solid #444444;
    	border-collapse: collapse;
	}
  	.jobTable th, .jobTable td {
	    border: 1px solid #444444;
	    padding:8px 0 !important;
  	}
  	/* The switch - the box around the slider */
	.switch {
  		position: relative;
  		display: inline-block;
  		width: 60px;
  		height: 34px;
  		vertical-align:middle;
	}
	/* Hide default HTML checkbox */
	.switch input {display:none;}
	/* The slider */
	.slider {
  		position: absolute;
  		cursor: pointer;
  		top: 0;
  		left: 0;
  		right: 0;
  		bottom: 0;
  		background-color: #ccc;
  		-webkit-transition: .4s;
  		transition: .4s;
	}
	.slider:before {
  		position: absolute;
  		content: "";
  		height: 26px;
  		width: 26px;
  		left: 4px;
  		bottom: 4px;
  		background-color: white;
  		-webkit-transition: .4s;
  		transition: .4s;
	}
	.toggles{
  		margin:0px;display:inline-block; font-size:15px;	font-weight:bold;
  	}
	input:checked + .slider {
  		background-color: #2196F3;
	}
	input:focus + .slider {
  		box-shadow: 0 0 1px #2196F3;
	}
	input:checked + .slider:before {
  		-webkit-transform: translateX(26px);
  		-ms-transform: translateX(26px);
  		transform: translateX(26px);
	}
	/* Rounded sliders */
	.slider.round {
  		border-radius: 34px;
	}
	.slider.round:before {
  		border-radius: 50%;
	}
	.select-area{
	    border:1px solid #e5e5e5;
	    border-bottom:none;
	    clear: both;
	}
	.select-box, .check-box{
	    padding: 10px 20px;
	    border-bottom:1px solid #e5e5e5;
	    font-size:18px;
	    display: flex;
	    align-items: center;
	    cursor: pointer;
	}
	.select-circle{
	    width:26px;
	    height:26px;
	    border:4px solid #e5e5e5;
	    -webkit-border-radius: 50%;
	    -moz-border-radius: 50%;
	    border-radius: 50%;
	    cursor: pointer;
	    margin-right:15px;
	    display: inline-block;
	    text-align: center;
	    line-height: 40px;
	}
	.select-circle-inner{
	    width:70%;
	    height:70%;
	    border:4px solid #ffffff;
	    -webkit-border-radius: 50%;
	    -moz-border-radius: 50%;
	    border-radius: 50%;
	    display: inline-block;
	}
	.select-circle.check, .select-circle-inner.check{
	    -webkit-border-radius: 2px;
	    -moz-border-radius: 2px;
	    border-radius: 2px;
	    border-width:3px;
	}
	.select-box.on .select-circle, .check-box.on .select-circle{
	    border-color: #eb7c23;
	}
	.select-box.on .select-circle-inner, .check-box.on .select-circle-inner{
	    background-color: #eb7c23;
	}
	.select-box-text{
	    font-family: 'barunlight', sans-serif;
	    font-weight:bold;
	}
	.ilgaja-box{
	    padding: 70px 45px;
	    background-color: #ffffff;
	    -webkit-border-radius: 10px;
	    -moz-border-radius: 10px;
	    border-radius: 10px;
	    box-shadow: 0 8px 20px 0 rgba(0, 10, 134, 0.04);
	    max-width:540px;
	    width:100%;
	}
	.ilgaja-title{
	    font-size: 25px;
	    color: #363636;
	    font-weight:bold;
	    text-align: center;
	    margin-top: 20px;
	}
	.ilgaja-caption{
	    font-size:18px;
	    width:100%;
	    text-align: center;
	    padding:20px 0;
	    /*font-weight:bold;
	    font-family: 'barunlight';*/
	}
	.ilgaja-btn-box{
	    width:100%;
	    text-align: center;
	    padding-top:30px;
	}
	.ilgaja-btn{
	    display: inline-flex;
	    align-items: center;
	    justify-content: center;
	    width:175px;
	    height:50px;
	    -webkit-border-radius: 5px;
	    -moz-border-radius: 5px;
	    border-radius: 5px;
	    background-color: #f3791f;
	    color: #ffffff;
	    font-size: 20px;
	    cursor: pointer;
	    margin:0 10px;
	}
	.ilgaja-btn.prev{
	    background-color: #ffffff;
	    color: #f3791f;
	    border:1px solid #bac3cc;
	}
	.mgb50{
	    margin-bottom:50px;
	}
	.order_iframe {
	/*   display: none; */
	  position: fixed;
	  width: 640px;
	  background: #fff;
	  z-index: 9001; /* ★★★★★Order iFrame */
	  height: 100%;
	  top: 110%;
	  left: 50%;
	  transform: translateX(-50%);
	  transition: all 1.0s;
	  /* transition: all 1500ms cubic-bezier(0.86, 0, 0.07, 1); */
	}
	.order_iframe.on {
	/*   display: block; */
	  top: 10px;
	  z-index: 6001;
	  height: 100%;
	  transition: all 0.3s;
	}
	/*
	.title_text_none3 {
	    z-index: 40;
	    position: absolute;
	    top: 8px;
	    right: 8px;
	    width: 30px;
	    height: 30px;
	    background-color: #dddcd7;
	    border-radius: 100%;
	    cursor : pointer;  
	}
	*/
	
	.title_text_none3 {
	    margin-bottom: -48px;
	    width: 100%;
	    z-index: 40;
	    position: relative;
	    text-align: right;
	    top: -2px;
	    right: 10px;
	}
	
	.title_text_none3 img {
	    width: 18px;
	    height : 18px;
	    padding: 8px;    
	    background-color: #62625f;
	    border-radius: 100%;
	    cursor : pointer;  
	}
	
	
	.bk_background {
	  display: flex;
	  justify-content: center;
	  align-items: center;
	  flex-direction: column;
	  gap: 20px;
	  position: fixed;
	  left: 0;
	  top: 0px;
	  width: 100%;
	  height: 100%;
	  background: rgba(0, 0, 0, 0.4);
	  z-index: -1;
	  pointer-events: none;
	  opacity: 0;
	  transition: all 0.3s ease-in-out;
	}
	
	.bk_background.on {
	  z-index: 29;
	  pointer-events: all;
	  opacity: 1;
	}
	.order_iframe iframe{
	  margin: 0 auto;
	  padding: 0px;
	  border: 0;
	}
	
	@media screen and (max-width: 640px){
	  .form-box{width: 98%;}
	  div#terms strong{font-size: 16px;}
	  .order_iframe{width: 98%;}
	}
</style>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ehjyv0iw4b&submodules=geocoder"></script>
<script type="text/javascript">
	$(function() {
		$("html").css("overflow", "scroll");
		 
		setDayType('start_reg_date', 'end_reg_date', 'toDay');
		 
		//dashboard 페이지에서 알림 클릭으로 들어온 경우 해당 알림의 날짜로 설정함.
		if( "${work_date}" != "" ){
			$("#start_reg_date").val("${work_date}");
			toDay = "${work_date}";
		}
		
		// setDayType(1, 'start_reg_date', 'toDay');
	 	$("input:radio[name=srh_use_yn]").click( function() {
	   		$("input:radio[name=srh_use_yn]").removeAttr('checked');
	   		$("input:radio[name=srh_use_yn]").prop('checked', false);
	
	   		$(this).prop('checked', true);
	   	
	 	});
	
	   	var check = $("input[name='toggle']");
	   	check.click(function(){
	   		$(".toggles").toggle();
	   		
	   		// 체크여부 확인
	   		if($("input:checkbox[name=toggle]").is(":checked") == true) {
				//작업
				fn_sms(1); 
	   		}else{
	   		 	fn_sms(0);
	   		}
		});
	   	
	   	//iframe 자식창에서 데이터받기
	   	window.addEventListener("message", function(message){
			if( message.data == "CLOSE" ){
				$(".title_text_none3").click();
				$("#btnRel").click();
			}
		});
	});
 
 	// 직원 SMS 받기 여부 (APP , WEB order 시)
 	function fn_sms(val) {
		var _data = {
			work_app_sms : val,
	   	};
		
	   	var _url = "<c:url value='/admin/setOrderSMS' />";
	   	
	   	commonAjax(_url, _data, true, function(data) {
			if(data.code == "0000") {
				toastOk("정상적으로 처리 되었습니다.");
	   		}else {
	   			toastFail("오류가 발생했습니다.1");
	   		}
	   	}, function(data) {
	   		//errorListener
	   		$(".wrap-loading").hide();
	   		toastFail("오류가 발생했습니다.3");
	   	}, function() {
	   		//beforeSendListener
	   		$(".wrap-loading").show();
	   	}, function() {
	   		//completeListener
	   		$(".wrap-loading").hide();
	   	});
	}
 
 /* 브라우져 크기에따라 그리드 크기 조절 */
 /*
 $(window).resize(function() {
	    var reWidth = $(window).width() -optWidth;
	    if(reWidth < baseWidth) reWidth = baseWidth;
	    
	    var reHeight = $(window).height() -optHeight;
	    if(reHeight < baseHeight) reHeight = baseHeight;
	    
	    $("#list").setGridWidth(reWidth) ;
	    $("#list").setGridHeight(reHeight);
	});
   */
	var nowWorkList = []; // 현재 선택한 좌표가 같은 현장 리스트
	var tempEmployerList = [];
	var tempAddr;
	var isSelect = false;
	var lastsel = -1;            //편집 sel
	var sessionId = "${sessionScope.ADMIN_SESSION.admin_id}";
	var sessionCompanySeq = '${sessionScope.ADMIN_SESSION.company_seq }';
	var companySeq = sessionCompanySeq;
	var sessionAuthLevel = '${sessionScope.ADMIN_SESSION.auth_level }';
	//간략보기 에서 사용 된다.
	var orgWidth = 3140 - 40;

	var optUse = "";	//공개여부 code_type = 'USE'
	var mCode = new Map();
	var oCode = new Object();
	var careerOptions = '${careerOptions }';
	
	$.ajax({
		type : "POST",
		url	: "/admin/getCommonCodeList",
		data : {"use_yn": "1", "code_type": "'USE'"},
		dataType : 'json',
		success	: function(data) {
			var _style = "";
		    var tempCode = 400;
		    var code400br = "0";
		   		 
		    if ( data != null && data.length > 0 ) {
		    	for ( var i = 0; i < data.length; i++ ) {
		        	if(data[i].code_type == 'USE') {
		            	optUse += "<div class=\"bt\"><a href=\"JavaScript:chkCodeUpdate('use', '<<ILBO_SEQ>>', 'use_code', '"+  data[i].code_value +"', 'use_name', '"+  data[i].code_name +"' ,'" +data[i].code_group+"','" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
					}
		            oCode = new Object();
		
		            oCode.bgcolor = data[i].code_bgcolor;
		            oCode.color = data[i].code_color;
		            oCode.name = data[i].code_name;
		
		            mCode.put(data[i].code_value, oCode);
				}
			}
		},
		beforeSend: function(xhr) {
			$(".wrap-loading").show();
			xhr.setRequestHeader("AJAX", true);
		},
		error: function(e) {
			$(".wrap-loading").hide();
			
			if ( data.status == "901" ) {
		    	location.href = "/admin/login";
		    }
		},
		complete: function(e) {
			$(".wrap-loading").hide();
		}
	});

	$(function() {
  		var use_opts       = "1:사용;0:미사용";   // 사용유무 - 0:미사용 1:사용
  		var breakfast_opts = "0:미설정;1:조식;2:중식;3:석식;4:조식/중식;5:중식/석식;6:조식/중식/석식";   // 조식유무 - 1:유 0:무
  		var order_state_opts = "1:접수; 2:처리중; 3:완료; 4:거부";
  		var parking_opts = "0:주차불가;1:주차가능;2:주차비지급;3:유류비지급";
  		var work_days_opts = "1:1일;2:2일;3:3일;4:4일;5:5일;6:6일;7:7일;8:8일 이상";
  		var laborContract = '${laborContract }';
	  	var receiveContract = '${receiveContract }';
	  	
  		// jqGrid 생성
  		$("#list").jqGrid({
			url : "/admin/getOrderList",
            datatype : "json",                              
            mtype : "POST",
            postData: {
            	srh_use_yn : 1,
                page : 1,
                start_reg_date : toDay,
        		end_reg_date : toDay,
        		order_state :"",
            },
            sortname : "order_seq",							//sort 하는 방법 jjh 수정
           	sortorder : "desc",
           	
            height : jqHeight +50,                                 // 그리드 높이
            width : orgWidth,		//jqWidth
//            autowidth: true,

			rowList : [100, 300, 500],                         // 한페이지에 몇개씩 보여 줄건지?
            rowNum : 100,
            pager : '#paging',                            // 네비게이션 도구를 보여줄 div요소
          	viewrecords : true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무

         	multiselect	: true,
         	multiboxonly : true,
            caption	: "오더 목록",                   			// 그리드타이틀

          	rownumbers : true,                                 	// 그리드 번호 표시
         	rownumWidth : 40,                                   // 번호 표시 너비 (px)

            cellEdit : true ,							  //처리중일때 만 수정 할 수 있도록 한다.
          	cellsubmit : "remote" ,
          	cellurl	: "/admin/setOrderInfo",              // 추가, 수정, 삭제 url

          //	cellEdit		: false,
          	
            jsonReader: {
                    id: "order_seq",
                    repeatitems: false,
           			subgrid: {
           		      root: "rows", 
           		      repeatitems: true, 
           		      cell: "cell"
           		   }
            },

            // 컬럼명
            colNames: ['순번','총인원','접속로그', '작업일자','접수지점','order_state', '오더', '처리상태', '오더상태', '메모',
            			'지점순번',  '매니저지점', '소유자', '매니저등록일시', '매니저타입', '현장매니저순번', '현장매니저', '현장매니저전화',
            			'통합검색',  '복구' ,  '구인처순번', '구인처 검색 유무', '구인처 명','사업자번호(주민번호)', '대장', '지도','좌표',
            			'현장주소', '현장 시/도', '현장 시/구/군', '현장 동/읍/면', '현장 지번', '현장순번', '좌표중복현장수', '현장명', '구인상세',
                       	'현장담당자', '현장담당자전화', '도착시간','마감시간', '휴게시간', '식사제공', '나이', '혈압', '작업일수', '단가지급', '주차여부', '대리수령 양식', '근로계약 양식', '담당자','폰번호',
                       	'상태', '등록일시', '등록자','수정자', '소유 지점', '상담사 추천인 id'],

            // 컬럼별 속성
            colModel: [
				{name: 'order_seq', index: 'order_seq', key: true, width: 80, search: false, hidden:${sessionScope.ADMIN_SESSION.auth_level eq 0 ? false : true} }
				, {name: 'total_work', index: 'total_work', width: 80,  align: "center", search: false}
				, {name: 'access_view', index: 'access_view', width: 200, search: false, align: "left", sortable: true,}
				, {name: 'order_date', index: 'order_date', search: false, align: "left", width: 150, sortable: true,
					editable:  true, edittype: 'text', editrules: { required: true },
					editoptions: {
						size: 15, maxlength: 10,
						dataInit: function(element) {
							$(element).datepicker({ dateFormat: 'yy-mm-dd', constraintInput: false }); // ,showOn: 'button', buttonText: 'calendar'
						}
					},
					searchoptions: {sopt: ['cn','eq','nc']}	 
				}
				, {name: 'order_company_name', index: 'order_company_name', search: false, align: "left", width: 150, sortable: true, editable: false}
				, {name: 'order_state', index: 'order_state', width:80, hidden:true }
				, {name: 'order_request', index: 'order_request', width: 80, hidden: false, align: "center", search: false }
				, {name: 'order_state_view', index: 'order_state_view', sortable: false, width: 300, search: false, edittype: "select", editoptions: {value: order_state_opts}, formatter: formatOrderState}
				, {name: 'search_flag', index: 'search_flag', width: 80, hidden: true }
				, {name: 'callcenter_memo', index: 'callcenter_memo', width:200, search: false, hidden: false , editable: true, edittype: "textarea", search: true, searchoptions: {sopt: ['cn','eq','nc']} }
				, {name: 'company_seq', index: 'company_seq', hidden: true, editable: true,}
				, {name: 'company_name', index: 'company_name', search: false, width: 150, align: "left", sortable: true
					, editable: false
					, edittype: "text"
					, editoptions: {
						size: 30,
						dataInit: function (e, cm) {
							$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
							isSelect= false;
							$(e).autocomplete({
								source: function (request, response) {
									$.ajax({
										url: "/admin/getCompanyNameList2", type: "POST", dataType: "json",
										data: { term: request.term, srh_use_yn: 1 },
										success: function (data) {
											response($.map(data, function (item) {
												return item;
											}))
										},
										beforeSend: function(xhr) {
											xhr.setRequestHeader("AJAX", true);
										},
										error: function(e) {
											alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
										}
									})
								},
								minLength: 1,
								focus: function (event, ui) {
									return false;
								},
								select: function (event, ui) {
									$(e).val(ui.item.label);
									lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
									$("#list").jqGrid('setCell', lastsel, 'company_seq', ui.item.code, '', true);  
									isSelect = true;
									
									//Enter가 아니면
									if( event.keyCode != "13" ){
										var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
										$("#list").jqGrid("saveCell", iRow, cm.iCol);
									}
			
									return true;
								}
							});
						}
					}
					, editrules: {custom: true, custom_func: validCompanyName} 
					, searchoptions: {sopt: ['cn','eq','nc']}
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
						if( rowObject.company_seq != rowObject.order_company_seq ){
							return 'style="background-color: #abe261;"';
						} else {
							return '';
						}
					}
				}
			    , {name: 'owner_id', index: 'owner_id', search: false, editable: false}
			    , {name: 'manager_reg_date', index: 'manager_reg_date', hidden: true, editable: true}
			    , {name: 'manager_type', index: 'manager_type', hidden: true, editable: true}
			    , {name: 'manager_seq', index: 'manager_seq', hidden: true, editable: true}
			    , {name: 'manager_name', index: 'manager_name',  search: false, width: 120, align: "left", sortable: true
					, editable:  true
					, edittype: "text"
					, editoptions: {
						size: 30,
						dataInit: function (e, cm) {
							isSelect= false;
							$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
							$(e).autocomplete({
								source: function (request, response) {
									$.ajax({
										url: "/admin/getManagerNameList", type: "POST", dataType: "json",
										data: { 
											term: request.term
											, use_yn: 1
											, manager_type: "O"
										},
										success: function (data) {
											response($.map(data, function (item) {
												return item;
											}))
										},
										beforeSend: function(xhr) {
											$(".wrap-loading").show();
											xhr.setRequestHeader("AJAX", true);
										},
										error: function(e) {
											$(".wrap-loading").hide();
											if ( data.status == "901" ) {
												location.href = "/admin/login";
											} else {
												alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
											}
										},
										complete: function(e) {
											$(".wrap-loading").hide();
										}
									})
								},
								minLength: 1,
								focus: function (event, ui) {
									return false;
								},
								select: function (event, ui) {
									lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
                         	  		$(e).val(ui.item.manager_name);
                         	  		
                                    $("#list").jqGrid('setCell', lastsel,'manager_seq'	, ui.item.manager_seq,   '', true);  
                                    $("#list").jqGrid('setCell', lastsel,'manager_type', ui.item.manager_type, '', true);
                                    $("#list").jqGrid('setCell', lastsel,'manager_phone', ui.item.manager_phone, '', true);  
                                    $("#list").jqGrid('setCell', lastsel,'company_seq', ui.item.company_seq, '', true);  
                                    $("#list").jqGrid('setCell', lastsel,'company_name', ui.item.company_name, '', true);  
									
                                    var owner_id = $("#list").jqGrid("getCell", lastsel, "owner_id");
                                    
                                    $("#list").jqGrid('setCell', lastsel,'owner_id', owner_id, '', true);
                                    $("#list").setCell(lastsel, 'manager_phone', '', {'background-color': ui.item.manager_type=='E'? '#ED6710':'#abe261'});
                                     
                                    isSelect = true;
                                    
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
					, editrules: {custom: true, custom_func: validManagerName}
					, searchoptions: {sopt: ['cn','eq','nc']}
					, formatter: formatSelectName
					, cellattr: function(rowId, tv, rowObject, cm, rdata) { 
						if( rowObject.manager_use_status > 0 ){
							return 'style="background-color: #f7f4e4; color: red;"';
						}
						return 'style="background-color: #f7f4e4;"';
					}  
				}
				, {name: 'manager_phone', index: 'manager_phone', search: false, width: 150, align: "left", editable: true
					, sortable:  true
				              	  /* , editrules: {custom:  true, custom_func: validCheckNullPhone} */
					, editrules: {custom: true, custom_func: validManagerPhone}
					, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
						if(rowObject.manager_type != 'N') {
							var regDt = new Date(rowObject.reg_date);
							var mRegDt = new Date(rowObject.manager_reg_date);
							if(regDt < mRegDt) return '';
						}

						if( rowObject.manager_type == 'O' ){
							return 'style="background-color: #abe261;"';
						}else if( rowObject.manager_type == 'E' ){
							return 'style="background-color: #ED6710;"';
						}
					}
				}
				, {name: 'order_search', index: 'order_search', search: false, align: "left", width: 200, sortable: false
					, editable: true
					, edittype: "text"
					, editoptions: {
						size: 30,
						dataInit: function (e, cm) {
							lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
							$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택

							$(e).autocomplete({
								source: function (request, response) {
									$.ajax({
			                                            				 //url		: "/admin/getSearchWorkList", type: "POST", dataType: "json",
										url: "/admin/getWorkNameList", type: "POST", dataType: "json",
										data: { term: request.term, srh_use_yn: 1, orderFlag: 'Y' },
										success	: function (data) {
											response($.map(data, function (item) {
												return item;
											}));
										},
										beforeSend: function(xhr) {
											$(".wrap-loading").show();
											xhr.setRequestHeader("AJAX", true);
										},
										error: function(e) {
											$(".wrap-loading").hide();
											console.log(e);
										},
										complete: function(e) {
											$(".wrap-loading").hide();
										}
									})
								},
								minLength: 2,
								focus: function (event, ui) {
									return false;
								},
								select: function (event, ui) {
			                                                       // label 값을 넣어 주면 회사와 현장이 붙어서 나온다.
	//		                                                       $(e).val(ui.item.label);
			                                                       // 회사명만 나오게
									$(e).val(ui.item.employer_name);

									lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
									var colModel = $("#list").jqGrid('getGridParam', "colModel");
									// 다른 셀 바꾸기
									$("#list").jqGrid('setCell', lastsel, 'employer_seq', ui.item.employer_seq, '', true);  
									$("#list").jqGrid('setCell', lastsel, 'employer_name', ui.item.employer_name, {background: "#ef9d85"}, true);
									$("#list").jqGrid('setCell', lastsel, 'employer_num', ui.item.employer_num, '', true);
									$("#list").jqGrid('setCell', lastsel, 'work_seq', ui.item.work_seq, '', true);
									$("#list").jqGrid('setCell', lastsel, 'work_name', ui.item.work_name, '', true);  	
									$("#list").jqGrid('setCell', lastsel, 'owner_id', ui.item.owner_id, '', true);  	
									$("#list").jqGrid('setCell', lastsel, 'search_flag', '1', '', true);  
									$("#list").jqGrid('setCell', lastsel,'work_addr', ui.item.work_addr, '', true);  
									$("#list").jqGrid('setCell', lastsel,'work_latlng', ui.item.work_latlng, '', true);  
									$("#list").jqGrid('setCell', lastsel,'addr_edit', "1", '', true);  
									$("#list").jqGrid('setCell', lastsel,'labor_contract_seq', ui.item.labor_contract_seq, '', true);  
									$("#list").jqGrid('setCell', lastsel,'receive_contract_seq', ui.item.receive_contract_seq, '', true);  
																	/* 
			              											$("#list").jqGrid('setCell', lastsel, 'company_seq'		, ui.item.company_seq, '', true);  
			                                                       	$("#list").jqGrid('setCell', lastsel, 'company_name'	, ui.item.company_name, '', true);  
			                                                       	
			              											$("#list").jqGrid('setCell', lastsel,'manager_seq'		, ui.item.manager_seq,   '', true);  
				                                                    $("#list").jqGrid('setCell', lastsel,'manager_name'	, ui.item.manager_name, '', true);  
				                                                    $("#list").jqGrid('setCell', lastsel,'manager_phone'	, ui.item.manager_phone, '', true);  
			              											
				                                                    $("#list").jqGrid('setCell', lastsel,'work_manager_name'	, ui.item.work_manager_name, '', true);  
				                                                    $("#list").jqGrid('setCell', lastsel,'work_manager_phone'	, ui.item.work_manager_phone, '', true);  
			              											 */
			              											
									isSelect = true;
			              											 
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
					, editrules: {custom:  true, custom_func: validSearchOrder}
					, formatter: formatOrderSearchName
					, cellattr: function(rowId, tv, rowObject, cm, rdata) { return 'style="background-color: #f7f4e4;"'; }
				}	
			              
				, {name: 'search_reset', index:'search_reset', align: "left", width: 50, sortable: false, search: false, formatter: formatSearchReset}
			              
				, {name: 'employer_seq', index: 'employer_seq', hidden: true, editable: true}
				, {name: 'employer_search_yn', index: 'employer_search_yn', hidden: true, editable: true}
				, {name: 'employer_name', index: 'employer_name', search: false, width: 150, align: "left", sortable: true
					, editable:  true 
					, edittype: "text"
					, editoptions: {
						size: 30,
						dataInit: function (e, cm) {
							$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
							isSelect= false;
							$(e).autocomplete({
								source: function (request, response) {
									$.ajax({
										url: "/admin/getSearchEmployerList", type: "POST", dataType: "json",
										data: { term: request.term, srh_use_yn: 1 },
										success: function (data) {
											response($.map(data, function (item) {
												return item;
											}))
										},
										beforeSend: function(xhr) {
											xhr.setRequestHeader("AJAX", true);
										},
										error: function(e) {
											if ( e.status == "901" ) {
												location.href = "/admin/login";
											} else {
												alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
											}
										}
									})
								},
								minLength: 2,
								focus: function (event, ui) {
									return false;
								},
								select: function (event, ui) {
									$(e).val( ui.item.employer_name);
									lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값

// 									var orderCompanySeq = $("#list").jqGrid("getCell", lastsel, "order_company_seq");
// 									var orderCompanyName = $("#list").jqGrid("getCell", lastsel, "order_company_name");
									
// 									$("#list").jqGrid('setCell', lastsel, 'company_seq', orderCompanySeq, '', true);  
// 									$("#list").jqGrid('setCell', lastsel, 'company_name', orderCompanyName, '', true);  
									$("#list").jqGrid('setCell', lastsel, 'employer_seq', ui.item.code, '', true);  
									$("#list").jqGrid('setCell', lastsel, 'employer_ilbo', ui.item.code, '', true);
									$("#list").jqGrid('setCell', lastsel, 'receive_contract_seq', ui.item.receive_contract_seq, '', true);
									$("#list").jqGrid('setCell', lastsel, 'labor_contract_seq', ui.item.labor_contract_seq, '', true);
									$("#list").jqGrid('setCell', lastsel, 'employer_name', '', {background: "#ef9d85"}, true);
									$("#list").jqGrid('setCell', lastsel, 'employer_search_yn'  , 1,  '', true);
									$("#list").jqGrid('setCell', lastsel, 'employer_num', ui.item.employer_num, '', true);
									
									isSelect = true;
									
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
					, formatter: formatSelectName
					, editrules: {custom: true, custom_func: validOrderEmployerName} 
					, searchoptions: {sopt: ['cn','eq','nc']}
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
						if(rowObject.reg_date.split(' ')[0] == rowObject.order_date.split(' ')[0]) return '';

						if(rowObject.employer_seq != "0"){
							return 'style="background-color: #ef9d85;"';
						}
						if( rowObject.employer_name_exists == 1 ){
							return 'style="background-color: #2196F3;"';
						}
						return 'style="background-color: #f7f4e4;"';
					}
				}
                , {name: 'employer_num', index: 'employer_num', align: "center", width: 200, sortable: false, search: false
					, formatter: employerNumFomat
					, editable: true
					, editrules: {
						custom: true,
						custom_func: employerNumCheck
					}
				}
				, {name: 'employer_ilbo', index: 'employer_ilbo', align: "left", width: 50, sortable: false, search: false, formatter: formatEmployerIlbo, hidden: false }
				, {name: 'addr_edit', index: 'addr_edit', align: "left", width: 50, sortable: false, search: false, formatter: formatAddrEdit }
				, {name: 'work_latlng', index: 'work_latlng', align: "left", width: 150, editable: false, search: false, hidden: false
					, formatter: workLatlngFormat
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
						if(rowObject.work_latlng_count > 0 && rowObject.work_seq < 1)
							return 'style="background-color: #abe261;"';
						else
							return '';
					}
				}
				, {name: 'work_addr', index: 'work_addr', align: "left", width: 350, editable: false, sortable: true, search: false, hidden: false }
				, {name: 'work_sido', index: 'work_sido', hidden: true, editable: true}
				, {name: 'work_sigugun', index: 'work_sigugun', hidden: true, editable: true}
				, {name: 'work_dongmyun', index: 'work_dongmyun', hidden: true, editable: true}
				, {name: 'work_rest', index: 'work_rest', hidden: true, editable: true}
				, {name: 'work_seq', index: 'work_seq', hidden: true, editable: true}
				, {name: 'work_latlng_count', index: 'work_latlng_count', hidden: true, editable: true}
				, {name: 'work_name', index: 'work_name', search: false, width: 350, align: "left", sortable: true, editable: true }
				, {name: 'employer_detail', index: 'employer_detail', search: false, editable: true}
				, {name: 'work_manager_name', index: 'work_manager_name', search: false, width: 120, align: "left", sortable: true, editable: true}
				, {name: 'work_manager_phone', index: 'work_manager_phone', search: false, width: 150, align: "left", sortable: true, editable: true
					, editrules: {custom: true, custom_func: validPhone}
					, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}  
				}
			              
				, {name: 'work_arrival', index: 'work_arrival', search: false, align: "center", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}
					, editrules: {custom: true, custom_func: validWorkTime}
					, formatter: formatTime
				}
				, {name: 'work_finish', index: 'work_finish', search: false, align: "center", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}
					, editrules: {custom: true, custom_func: validWorkTime}
					, formatter: formatTime
				}
				, {name: 'work_breaktime', index: 'work_breaktime', search: false, align: "center", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}
					, editrules: {custom: true, custom_func: fn_validWorkBreaktime}
				}
// 				, {name: 'work_breaktime', index: 'work_breaktime', align: "left", width: 100, editable: true, sortable: true, search: false, searchoptions: {sopt: ['cn', 'eq', 'nc']}
// 					//, editrules: {custom: true, custom_func: validWorkTime}
// 			        //, formatter: formatTime
// 			        //, hidden : ${sessionScope.SEARCH_OPT.ilboAllShow eq '0' ? false : ilboSettingDTO.ilbo_work_arrival eq '1' ? false : true}	
// 				}
				, {name: 'breakfast_yn', index: 'breakfast_yn', search: false, width: 70, align: "center", sortable: true, editable: true
					, formatter: 'select', edittype: "select", editoptions :{value: breakfast_opts} }
				, {name: 'work_age', index: 'work_age', search: false, width: 100, align: "center", sortable: true, editable: true
					, edittype: "select", editoptions: {value: '0:제한없음;29:29이하;39:39이하;49:49이하;59:59이하'}, formatter: "select"
				}
				, {name: 'work_blood_pressure', index: 'work_blood_pressure', search: false, width: 100, align: "center", sortable: true, editable: true
					, edittype: "select", editoptions: {value: '0:제한없음;140:140이하;150:150이하;160:160이하'}, formatter: "select"   
				}
			              
				, {name: 'work_day', index: 'work_day', search: false, width: 70, align: "center", sortable: true, editable: true
					, edittype: "select", editoptions: {value:work_days_opts}, formatter: "select"}
				, {name: 'pay_type', index: 'pay_type', search: false, width: 120, align: "center", sortable: true, editable: true
					, edittype: "select", editoptions: {value: '300006:당일계좌입금;300007:당일현금지급'}, formatter: "select"    
				}
				, {name: 'parking_option', index: 'parking_option', width: 100, search: false, editable: true
			    	, formatter: 'select', edittype: 'select', editoptions :{value: parking_opts}}
				, {name: 'receive_contract_seq', index: 'receive_contract_seq', align: "center", width: 60, search: false, sortable: true, edittype: "select"
					, editoptions: {
						value: receiveContract
						, dataEvents:[
							//	{type:'change', fn:function(e){ 필요한처리할 함수명();}},   //onchange Event 
							{type:'keydown', fn:function(e) {                   //keydown Event
								if(e.keyCode == 27) {      //Enter Key or Tab Key
									var p = $("#list").jqGrid("getGridParam");
	                        		var iCol = p.iColByName["company_seq"];
	                        		var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
	
	                        		$("#list").jqGrid('restoreCell', iRow, iCol);
	                        		
									alert("항목을 선택 후 엔터를 쳐야 됩니다.");
								}
							}} //end keydown}
						]    //dataEvents 종료
					}, formatter: "select", editable: true }
				, {name: 'labor_contract_seq', index: 'labor_contract_seq', align: "center", width: 60, search: false, sortable: true, edittype: "select"
					, editoptions: {
						value: laborContract 
						, dataEvents:[
							{type:'keydown', fn:function(e) {                   //keydown Event
								if(e.keyCode == 27) {      //Enter Key or Tab Key
									var p = $("#list").jqGrid("getGridParam");
	                        		var iCol = p.iColByName["company_seq"];
	                        		var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
	
	                        		$("#list").jqGrid('restoreCell', iRow, iCol);
	                        		
									alert("항목을 선택 후 엔터를 쳐야 됩니다.");
								}
							}} //end keydown}
						]    //dataEvents 종료
					}, formatter: "select", editable: true }
// 				, {name: 'is_tax', index: 'is_tax', search: false, width: 70, align: "center", sortable: true, editable: true
// 						, formatter: 'checkbox', edittype: "checkbox", editoptions :{value:'1:0', defaultValue:'0'}  
// 				}
				, {name: 'tax_name', index: 'tax_name', hidden: true,}
				, {name: 'tax_phone', index: 'tax_phone', hidden: true}
				              
// 				, {name: 'is_worker_info', index: 'is_worker_info',  search: false, width: 70, align: "center", sortable: true, editable: true
// 					, formatter: 'checkbox', edittype: "checkbox", editoptions :{value:'1:0', defaultValue:'0'}  
// 				}
			              
				, {name: 'use_yn', index: 'use_yn', align: "center", width: 60, search: false, editable: true, sortable: true
					, edittype: "select", editoptions: {value: use_opts}, formatter: "select"
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				}
				, {name: 'reg_date', index: 'reg_date', align: "center", width: 150, search: false, editable: false, sortable: true, formatoptions: {newformat: "y/m/d H:i"}}
				, {name: 'reg_admin', index: 'reg_admin', align: "left", width: 60, search: false, editable: false, sortable: true ,hidden: true}
				, {name: 'mod_admin', index: 'mod_admin', align: "left", width: 100, search: false, editable: false, sortable: true}
				, {name: 'order_company_seq', index: 'order_company_seq', hidden: true}
				, {name: 'order_owner_id', index: 'order_owner_id', hidden: true}
			],
         	//공통기능 체크박스만 선택기능
         	beforeSelectRow: selectRowid, 
         	// row 를 선택 했을때 편집 할 수 있도록 한다.
			onSelectRow: function(id) {
				if ( id && id !== lastsel ) {
					lastsel = id;
				}
			},
        	// cell을 클릭 시
			onCellSelect: function(rowid, index, contents, event) {
				var rowData = $('#list').jqGrid('getRowData', rowid);
				
				if(rowData.manager_type != 'N') {
					$("#list").setColProp("manager_name", {editable : false});
					$("#list").setColProp("manager_phone", {editable : false});
				}else {
					$("#list").setColProp("manager_name", {editable : true});
					$("#list").setColProp("manager_phone", {editable : true});
				}
				
				lastsel = rowid;
                        
				var cm = $("#list").jqGrid("getGridParam", "colModel");
				var cellName = cm[index].name;
                        
				if(cellName == "manager_phone"){
					CopyToClipboard(rowData.manager_phone, myClipboard, true);
				}
				if(rowData.order_state == "2" && rowData.mod_admin == sessionId){ //내거
					if(cellName == "employer_num") {
						if (rowData.employer_seq == "0") fnEditableCell(rowid, index);
						else fnNotEditableCell(rowid, index);
					} else {
						fnEditableCell(rowid, index);
					}
				}else{
					fnNotEditableCell(rowid, index);
					
					if(cellName == "work_manager_phone"){
						if( rowData.work_manager_phone != null && rowData.work_manager_phone != "" ){
							CopyToClipboard(rowData.work_manager_phone, myClipboard, true);	
						}
					}
				}
			},
			beforeSubmitCell: function(rowid, cellname, value) {// submit 전
				if(['order_search', 'employer_name'].includes(cellname))
					tempEmployerList.splice(tempEmployerList.findIndex(e => e.order_seq == rowid), 1);

				if(cellname == 'employer_num'){
					var emplListIdx = tempEmployerList.findIndex(e => e.order_seq == rowid);
					var rData = $("#list").jqGrid('getRowData', rowid);
					if(rData.employer_seq == '0')
						if(emplListIdx < 0)
							tempEmployerList.push({
								order_seq : rowid,
								employer_num: value,
								employer_name : rData.employer_name,
								employer_seq : rData.employer_seq,
								order_state : rData.order_state
							});
						else
							tempEmployerList[emplListIdx] = {
								order_seq: rowid,
								employer_num: value,
								employer_name : rData.employer_name,
								employer_seq : rData.employer_seq,
								order_state : rData.order_state
							}
				}

				if(cellname == "order_search"){
					var map = {
						order_seq: rowid,
						employer_seq: $("#list").jqGrid('getRowData', rowid).employer_seq,
						employer_name: $("#list").jqGrid('getRowData', rowid).employer_name,
						work_seq: $("#list").jqGrid('getRowData', rowid).work_seq,
						work_name: $("#list").jqGrid('getRowData', rowid).work_name,
						search_flag: $("#list").jqGrid('getRowData', rowid).search_flag,
						work_addr: $("#list").jqGrid('getRowData', rowid).work_addr,
						work_latlng: $("#list").jqGrid('getRowData', rowid).work_latlng,
						employer_search_yn: $("#list").jqGrid('getRowData', rowid).employer_search_yn,
						labor_contract_seq: $("#list").jqGrid('getRowData', rowid).labor_contract_seq,
						receive_contract_seq: $("#list").jqGrid('getRowData', rowid).receive_contract_seq,
					}


					return map;
				}
					
				if(cellname == "employer_name"){
					var map = {
						order_seq : rowid,
						employer_seq : $("#list").jqGrid('getRowData', rowid).employer_seq,
						employer_search_yn : $("#list").jqGrid('getRowData', rowid).employer_search_yn,
						labor_contract_seq: $("#list").jqGrid('getRowData', rowid).labor_contract_seq,
						receive_contract_seq: $("#list").jqGrid('getRowData', rowid).receive_contract_seq,
					}
					
					if( !isSelect ){
						map.employer_seq = 0;
					}
					
					return map
				}
					
				if(cellname == "company_name"){
					if(value == "" || value=="?"){
						var map = {
							order_seq : rowid,
							company_seq : 0,
							company_name : ''
						}
						
						return map;
					}
				}
					
				if(cellname == "manager_name"){
					var map = {
						order_seq: rowid,
						company_seq: $("#list").jqGrid('getRowData', rowid).company_seq,
						manager_seq: $("#list").jqGrid('getRowData', rowid).manager_seq,
						manager_phone: $("#list").jqGrid('getRowData', rowid).manager_phone,
						owner_id: $("#list").jqGrid('getRowData', rowid).owner_id,
						manager_type: $("#list").jqGrid('getRowData', rowid).manager_type
					} 

					var owner_id = $("#list").jqGrid("getCell", lastsel, "owner_id");
					
					if(!isSelect ){
						map.company_seq  = 0;
						map.company_name = "";
						map.owner_id = owner_id
					}
					
					return map;
				}
					
				if(cellname == "manager_phone"){
					var map = {
						order_seq: rowid,
						company_seq: $("#list").jqGrid('getRowData', rowid).company_seq,
						manager_seq: $("#list").jqGrid('getRowData', rowid).manager_seq,
						owner_id: $("#list").jqGrid('getRowData', rowid).owner_id,
						manager_type: 'O'
					}
						
					return map;
				}
				
				return {
					order_seq : rowid
				}; 
			},
			afterSaveCell: function (rowid, name, val, iRow, iCol) {
				if(name == "use_yn"){
					$("#list").trigger("reloadGrid");
				}
				if(!isSelect){
					$("#list").trigger("reloadGrid");	
				}
			},
        	// Grid 로드 완료 후
			loadComplete: function(data) {
				$(".wrap-loading").hide();
				if(tempEmployerList.length > 0){
					tempEmployerList.filter(e => e.employer_seq == '0');
					tempEmployerList.forEach((v, i) => {
						$("#list").jqGrid('setCell', v.order_seq, 'employer_num', v.employer_num, '', true);
					});
				}

				//총 개수 표시
				$("#totalRecords").html(numberWithCommas( $("#list").getGridParam("records") ));
				$("#totalWork").html(numberWithCommas(data.totalWork) );
				
				isGridLoad = true;
			},
			loadBeforeSend: function(xhr) {
				isGridLoad = false;
				optHTML = "";
//                        closeOpt();

				xhr.setRequestHeader("AJAX", true);
        	},
			loadError: function(xhr, status, error) {
				if ( xhr.status == "901" ) {
					location.href = "/admin/login";
				}
			},
       		subGrid: true,
 	   		subGridOptions: {
	      		// load the subgrid data only once
	   			// and the just show/hide
				"reloadOnExpand" : true,			//열고 닫고 할때 한번만 ajax 실행
	   			// select the row when the expand column is clicked
	   			"selectOnExpand" : false,		// 클릭할때 행 선택
	   		}, 
	   		subGridRowExpanded: function(subgrid_id, row_id) {
		   		var subgrid_table_id, pager_id;
	   			subgrid_table_id = subgrid_id+"_t";
	   			pager_id = "p_"+subgrid_table_id;
	   		
	   			//$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
	   			//페이지 없애기
	   			$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'>");
	   			$("#"+subgrid_table_id).jqGrid({
	   				url: "/admin/getOrderWorkList",
	            	datatype: "json",                              
	            	mtype: "POST",
	            	postData: {
		            	srh_use_yn: 1,
	                	page: 1,
	                	order_seq : row_id
	            	},
	            	cellEdit: true ,
	            	cellSubmit: "remote",
	            	cellurl: "/admin/setOrderWorkInfo",              // 추가, 수정, 삭제 url
	   				colNames: ['순번', '오더순번', '일보상태','상태값', '공개코드', '공개코드명', '공개', '지점번호', '지점선택', '직종', '직종코드', '직종상세', '직종상세코드', '직종옵션', '직종옵션코드', '단가', '인원', '경력옵션', '직종상세'],
	   				colModel: [
	   					{name:"work_seq", index: "work_seq", width: 10,	key: true, hidden: true},
	   					{name:"order_seq", index: "order_seq", width: 10, hidden: true},
	   					{name:"ilbo_state_view", index: "ilbo_state", width: 115, align: "center", formatter: formatIlboState },
	   					{name:"ilbo_state", index: "ilbo_state", width: 80, align: "center", hidden: true},
	   					{name:"use_code", index: "use_code", width: 40,	align: "center", hidden:true},
	   					{name:"use_name", index: "use_name", width: 40,	align: "center", hidden:true},
	   					{name:"use_info", index: "use_info", width: 30, align: "center", hidden:false, formatter: formatWorkUseInfo, sortable: false },
	   					{name:"worker_company_seq", index: "worker_company_seq", width: 40, align: "center", hidden: true},
	   					{name:"worker_company_name", index: "worker_company_name", width: 80, align: "center",hidden:false,
		   					editable: true,
				        	edittype: "text",
				        	editoptions: {size: 30, dataInit: fn_editOtions_workerCompanyName },
				        	editrules: {custom:  true, custom_func: validWorkerCompanyName} 
				        },
	   					{name:"work_kind_name", index: "work_kind_name", width: 150, align: "center", formatter: formatKindCode},
	   					{name:"work_kind_code", index: "work_kind_code", width: 200, align: "right", hidden: true},
	   					{name: 'work_job_detail_name', index: 'ilbo_job_detail_name', width: 100,	editable: false, sortable: false},
						{name: 'work_job_detail_code', index: 'ilbo_job_detail_code', hidden: true},
						{name: 'work_job_add_name', index: 'ilbo_job_add_name', width: 150, editable:  false, sortable: false},							    
				    	{name: 'work_job_add_code', index: 'ilbo_job_add_code', hidden: true},
					    
	   					{name:"work_price", index: "work_price", width: 55, align: "right", sortable: false, formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}, editable:true},
	   					{name:"worker_count", index: "worker_count", width: 70, align: "center", editable: true, sortable: false},
	   					{name:'career_name', index: 'career_name', search: false, align: "center", editrules: {custom: true, custom_func: validCareer}, editable: true, edittype: "select", formatter: "select", editoptions: {value: careerOptions} },
	   					{name:"work_memo", index: "work_memo", width: 400, align: "left", sortable: false, editable: true, edittype: "text"}
					],
	   		   		/* 페이지 없애기
	   					rowNum:20,
	   		   			pager: pager_id,
	   		   	 	*/
	   		  		rownumbers: true,                                 	// 그리드 번호 표시
	         		rownumWidth: 50,                                   // 번호 표시 너비 (px)
	   		 		width: 1780,
	   		   		sortname: 'work_seq',
	   		    	sortorder: "asc",
	   		    	height: '100%',
	   		 		beforeSubmitCell: function(rowid, cellName, cellValue){
		   		 		var map;
	   		 			if( cellName == "worker_company_name" ){
	   		 				var worker_company_seq = $("#"+subgrid_table_id).jqGrid("getCell", rowid, "worker_company_seq");
	   		 				map = {
	   		 					work_seq:rowid,
	   		 					worker_company_seq: worker_company_seq
							}
	   		 			}else{
	   		 				map = { work_seq: rowid }
	   		 			}
	   		 			
	   		 			if(cellName == 'career_name') {
	   		 				map = {
	   		 					work_seq : rowid
	   		 					, career_use_seq : cellValue
	   		 					, cellname : cellName
	   		 					, work_price : $("#" + subgrid_table_id).jqGrid("getCell", rowid, "work_price")
	   		 				};
	   		 			}
	   		 			
	   		 			//map['cellname'] = cellName;
		   		 		return map;
	   		 		},
	   		 		afterInsertRow: function(rowid, rowdata, rowelem){
		   		 		var orderstate = $("#list").jqGrid("getCell", row_id, "order_state");
		   		 		var mod_admin = $("#list").jqGrid("getCell", row_id, "mod_admin");
		   		 		var workCompanySeq = $("#list").jqGrid("getCell", row_id, "company_seq");
		   		 		
			   		 	if(workCompanySeq != sessionCompanySeq) {
			   		 		if(sessionAuthLevel == '0') {
			   		 			$("#"+subgrid_table_id).jqGrid("setCell", rowId, "career_name", "", "editable-cell");
			   		 		}else {
			   					$("#"+subgrid_table_id).jqGrid("setCell", rowId, "career_name", "", "not-editable-cell");
			   		 		}
			   			}else {
			   				$("#"+subgrid_table_id).jqGrid("setCell", rowId, "career_name", "", "editable-cell");
			   			}
		   		 		
		   		 		//   처리중이 아니거나        로그인한 계정이 최근수정한계정이 아니거나      적용을 눌렀을 때
	  		 			if( (sessionId != mod_admin || rowdata.ilbo_state == "1") || (orderstate == 1 || orderstate == 3)  ){
		   		 			$("#"+subgrid_table_id).jqGrid("setCell", rowid, "work_price","",'not-editable-cell');
			   		 		$("#"+subgrid_table_id).jqGrid("setCell", rowid, "worker_company_name","",'not-editable-cell');
		   		 			$("#"+subgrid_table_id).jqGrid("setCell", rowid, "work_memo","",'not-editable-cell');
		   		 			$("#"+subgrid_table_id).jqGrid("setCell", rowid, "worker_count","",'not-editable-cell');
		   		 			$("#"+subgrid_table_id).jqGrid("setCell", rowid, "career_name","",'not-editable-cell');
		   		 			//$("#"+subgrid_table_id).jqGrid("setCell", rowid, "use_info","",'not-editable-cell'); //formatter 사용한 컬럼은 기본이 editable:false
 	  		 			}else if( orderstate == 4 ){
 	  		 				$("#"+subgrid_table_id).jqGrid("setCell", rowid, "work_price","",'not-editable-cell');
 	  		 			}
	  		 			
	   		 		}
	   		 		, afterSubmitCell: function(data, rowid, cellname, value) {
		   		 		if(cellname == 'career_name') {
			   		 		//var workPrice = data.responseJSON.work_price;
 			   		 		var workPrice = JSON.parse(data.responseText).work_price;
			   		 		
			   		 		$("#" + subgrid_table_id).jqGrid("setCell", rowid, "work_price", workPrice, '', true);
		   		 		}
		   		 	}
	   			});
	   			
	   			$("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false});
	   		}
		});

		$("#list").jqGrid('navGrid', "#paging", {edit: false, add: false, del: false, search: false, refresh: false, position: 'left'});
		$("#list").jqGrid('filterToolbar', {searchOperators : true});
	
	  	setTooltipsOnColumnHeader($("#list"), 27, "등록된 매니져폰번호 일경우는 녹색으로 보입니다.");
    
 		// 행 삭제
		$("#btnDel").click( function() {
	    	var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
	    	
	    	if( recs.length < 1 ){
				alert("최소 1개 이상 선택하셔야합니다.");
				return;
			}
	    	
	    	var params = "sel_order_seq=" + recs;
	    	var rows = recs.length;
	
	    	$.ajax({
	           	type: "POST",
	           	url: "/admin/removeOrderInfo",
	           	data: params,
	        	dataType: 'json',
	         	success: function(data) {
					$("#list").trigger("reloadGrid");
				},
	      		beforeSend: function(xhr) {
	      			$(".wrap-loading").show();
					xhr.setRequestHeader("AJAX", true);
				},
	           	error: function(e) {
	           		$(".wrap-loading").hide();
	           		
					if ( e.status == "901" ) {
						location.href = "/admin/login";
					} else {
						alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
					}
				},
				complete: function(e) {
					$(".wrap-loading").hide();
				}
			});
		});

	  	// 새로고침
		$("#btnRel").click( function() {
	    	lastsel = -1;
	    	$("#list").trigger("reloadGrid");                       // 그리드 다시그리기
	  	});
  	
		//전체 열기 , 전체 닫기
		var isExtend = false;
	
		$("#btnExtend").click(function(){
			if(isExtend == true){
				this.text = "전체열기";
				$("#list .ui-icon-minus").trigger("click");
				isExtend = false;
			}else{
				this.text = "전체닫기";
				$("#list .ui-icon-plus").trigger("click");
				isExtend = true;
			}
		});
  	
		$("#btnReq").click(function(){
// 			$("html").css({"overflow-y":"scroll"});
// 			$(".bgPopup").fadeIn(500);
// 			$(".popup1").show();
// 			$(".popup1").css("top", "0%");
// 			$(".workOffer").hide();
// 			$(".jobOffer").fadeIn(500);
			$(".order_iframe").addClass('on');
	        $(".bk_background").addClass('on');
		});
		$(".title_text_none3").click(function(){
	        $(".order_iframe").removeClass('on');
	        $(".bk_background").removeClass('on');
	    });
	    $(".bk_background").click(function(){
	        $(".order_iframe").removeClass('on');
	        $(".bk_background").removeClass('on');
	    });
	
		$(".add").click(function(){
			$(".firstOI_04").show();
			$(".add").hide();
			$(".del").show();
		});
		
		$(".del").click(function(){
			$(".firstOI_04").hide();
			$(".del").hide();
			$(".add").show();
		});
/* 	
	 //직종을 선택했을때...
	$(".sOIemp .empButton p").click(function(){
		var price 			= $(this).attr('price');
		var kind_code 	= $(this).attr('kind_code');
		var code_gender	= $(this).attr('code_gender');
		
		var startTime 	= Number($("#work_arrival").val());
		var endTime  	= Number($("#work_finish").val());
		
		
		$(".secondOIpop").hide();
		
		//먼저 값을 삭제 해야 한다.
		$(selectThis).parents(".secondOI").find(".secValue2 .price").val("");
		
		var result = getPrice(code_gender, startTime,endTime, Number(price) );
		if(result[0]){
			$(selectThis).parents(".secondOI").find(".secValue2 .price").val( result[1] );
		}else{
			$(selectThis).parents(".secondOI").find(".secValue2 .price").attr('placeholder', result[1]);
		}
				
		$(selectThis).val( $(this).text() );
		$(selectThis).parents(".secondOI").find(".secValue1 .kind_code").val( kind_code );
		$(selectThis).parents(".secondOI").find(".secValue2 .price").attr('code_price', price);
		$(selectThis).parents(".secondOI").find(".secValue2 .price").attr("code_gender", code_gender);
		
	
	});
 */
	
		$("#order_date").datepicker({
	        dateFormat: 'yy-mm-dd' //Input Display Format 변경
	        , showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
	        , showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시           
	        , yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
	        , monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
	        , monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
	        , dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
	        , dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
	        , minDate: "D" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
	     });      
	
    //초기값을 오늘 날짜로 설정
    //$('#order_date').datepicker('setDate', 'today+1D'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
   /*  
    $.datepicker.setDefaults({
        minDate: "today" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
    });
    */ 
    
	    $(".referCheck label").click(function(){
			if($(".referCheck input").is(":checked")){
				$(".refer").show();
			}else{
				$(".refer").hide();
			}
		});

	  	// 검색
		$("#btnSrh").click( function() {
			// 검색어 체크
		    if ( $("#srh_type option:selected").val() != "" && $("#srh_text").val() == "" ) {
		    	alert('검색어를 입력하세요.');
		      	$("#srh_text").focus();
				return false;
		    }
			
		    $("#list").setGridParam({
	//	      page: pageNum,
	//	      rowNum: 15,    //jjh 주석 처리 기본 값에 따른다.
		    	postData: {
		  			start_reg_date: $("#start_reg_date").val(),
		    		end_reg_date: $("#end_reg_date").val(),
		      		srh_use_yn: $("input:radio[name=srh_use_yn]:checked").val(),
		        	srh_type: $("#srh_type option:selected").val(),
		        	srh_text: $("#srh_text").val(),
		        	order_state: $("input:radio[name=order_state]:checked").val(),
		    	}
			}).trigger("reloadGrid");
		});
   	
		$("#start_reg_date").change( function() {
	    	$("#end_reg_date").val($("#start_reg_date").val());
   		});
	});

	function formatKindCode(cellvalue, options, rowObject) {
		var str = "";
		var gId = options.gid;
		var rowId = options.rowId;
		var ilboState = rowObject.ilbo_state;
		var orderState = $("#list").jqGrid("getCell", options.rowData.order_seq, "order_state");
		var mod_admin = $("#list").jqGrid("getCell", options.rowData.order_seq, "mod_admin");
	    str += "<div class=\"bt_wrap\">";
	
	    if(ilboState == "0" && (orderState == "2" || orderState == "4") && sessionId == mod_admin){
	    	if ( rowObject.work_kind_code != null && rowObject.work_kind_code != "") {
				var _style = getCodeStyle(rowObject.ilbo_kind_code);
				
				str += "<div id=\"div_kind_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"fn_kindCodeOpt('"+ rowObject.order_seq+"','"+ rowId+"'); return false;\""+ _style +"> "+ cellvalue +" </a></div>";
			} else {
				str += "<div id=\"div_kind_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"fn_kindCodeOpt('"+ rowObject.order_seq+"','"+ rowId +"'); return false;\"> 선택 </a></div>";
			}	
		}else if( rowObject.work_kind_code != null && rowObject.work_kind_code != "" ){
			var _style = getCodeStyle(rowObject.ilbo_kind_code);
			str += "<div id=\"div_kind_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a return false;\""+ _style +"> "+ cellvalue +" </a></div>";
		}else{
			str += "<div id=\"div_kind_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a return false;\"> 선택 </a></div>";
		}
		
		
		str += "</div>";
	
	  	return str;
	}

	//직종 정보 option 세팅
	function fn_kindCodeOpt(parentRowId, rowId) {
		var workArrival = $("#list").jqGrid('getRowData', parentRowId).work_arrival;
		var workFinish = $("#list").jqGrid('getRowData', parentRowId).work_finish;
		
		if((workArrival == null || workArrival == "") || (workArrival == null || workArrival == "") ){
			alert("도착시간과 마감시간을 먼저 입력하세요.");
			return;
		}
		
		if((workFinish*1) - (workArrival*1) < 200){
			alert("도착시간과 종료시간은 2시간 이상 되어야 합니다.");
			return;
		}
		
		openIrPopupRe('popup-layer');
		fn_change_oneJobList();
		$("#parentRowId").val(parentRowId);
		$("#rowId").val(rowId);
		
		fn_change_jobCalInit(0);
	}

	function openIrPopupRe(popupId) {
		//popupId : 팝업 고유 클래스명
		var $popUp = $('#'+ popupId);
	
		$dimmed.fadeIn();
	
		var offsetWidth = -$popUp.width() / 2;
		var offsetHeight = -$popUp.height() / 2;
		  
		if(/Android|webOS|iPhone|iPad|iPod|pocket|psp|kindle|avantgo|blazer|midori|Tablet|Palm|maemo|plucker|phone|BlackBerry|symbian|IEMobile|mobile|ZuneWP7|Windows Phone|Opera Mini/i.test(navigator.userAgent)) {
			$popUp.css({
				'margin-top': "-547px",
			    'position' : 'relative',
			    'display' : "inline-block"
			  });
		}else {
			$popUp.css({
			    'margin-top': '5%',
			    'margin-left': offsetWidth,
			    'display' : "inline-block"
			});
		}
	}

	function  validOrderEmployerName(value, cellTitle, valref) {
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
		if ( value == "?" ) {
			$("#list").jqGrid('setCell', lastsel, 'company_seq'  , 0,  '', true);
		}
		
		if( !isSelect ){
			$("#list").jqGrid('setCell', lastsel, 'employer_search_yn'  , 0,  '', true);
		}

		return [true, ""];
	}
	
	//메니져 폰번호 검사
	function validManagerPhone(phoneNum, nm, valref) {
		var result = true;
		var resultStr = "";
		
		//폰에서 - 빼기
		var RegNotNum  = /[^0-9]/g;
		phoneNum = phoneNum.replace(RegNotNum,'');
		
		if ( phoneNum == "" ) return [true, ""];
		
		var valiResult = validPhone(phoneNum, null, null);
		if ( !valiResult[0] ) return valiResult;
		
		var _data = {
			manager_phone: phoneNum
		};
	
		$.ajax({
			type: "POST",
			url: "/admin/chkManagerPhone",
			data: _data,
			async: false,
			dataType: 'json',
			success: function(data) {
				if ( !data ) {
					result = false;
					resultStr = "이미 등록된 폰번호입니다.";
				}else{
					$("#list").jqGrid('setCell', lastsel, 'company_seq', 0,	'', true);
					$("#list").jqGrid('setCell', lastsel, 'company_name', null,	'', true);
					$("#list").jqGrid('setCell', lastsel, 'manager_seq', 0,	'', true);
					//$("#list").jqGrid('setCell', lastsel, 'owner_id', 'company', '', true);
						
					result = true;
					resultStr = "";
				}
			},
			beforeSend: function(xhr) {
				$(".wrap-loading").show();
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				$(".wrap-loading").hide();
				
				if ( data.status == "901" ) {
					location.href = "/admin/login";
				} else {
					alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			},
			complete: function(e) {
				$(".wrap-loading").hide();
			}
		});
	
		return [result, resultStr];
	}

	// 특정 cell 의 edit 가능하도록
	function fnEditableCell(rowid, index){
		var row = $("#list")[0].rows.namedItem(rowid);	//row 가져오기
	    var cell = row.cells[index];								//cell 가져오기
	    $(cell).removeClass('not-editable-cell');				//class 속성삭제
	}

	function fnNotEditableCell(rowid, index){
		var cm = $("#list").jqGrid("getGridParam", "colModel");
		
		$("#list").jqGrid('setCell', rowid,  cm[index].name, "", 'not-editable-cell');
	}

	//복구버튼
	function formatSearchReset(cellvalue, options, rowObject) {
		var str = "";  
		/* 로그보기
		str += "<div class=\"bt_wrap\">";
		str += "<div class=\"bt_on\"><a href=\"JavaScript:void(0)\" onclick=\"showCodeLog(this,'"+ rowObject.order_seq +"');\"> ▽ </a></div>";
		str += "</div>";
		 */
	
		str += "<div class=\"bt_wrap\">";
		str += "<div class=\"bt_on\"><a href=\"JavaScript:void(0)\" onclick=\"searchReset('"+ rowObject.order_seq +"');\"> ▽ </a></div>";
		str += "</div>";
		return str;  
	}


	// 지도오픈
	function mapOpen(mode,seq,latlng,addr,comment) {
		var rowObject = $('#list').jqGrid('getRowData', seq);
		var orderState = rowObject.order_state;  
		
	  	if(orderState != "2"){
			alert("오더상태가 처리중일때 지도 설정이 가능합니다.");
			return;
		}else{
			if(sessionId != rowObject.mod_admin){
				alert("처리중인 상담사만  지도 설정이 가능합니다.");
				return;
			}
		}
	  
	  	if(addr == ""){
			var rowObject = $('#list').jqGrid('getRowData', seq);
		  	addr = rowObject.work_name;
	  	}
	  
	  	mapWindowOpen(mode,seq,latlng,addr,comment);
	}

	//지도
	function formatAddrEdit(cellvalue, options, rowObject) {
    	var str = "";
    	var btn = "";
		var work_latlng = rowObject.work_latlng;

    	str += "<div class=\"bt_wrap\">";
    	if( rowObject.work_latlng != "" && rowObject.work_latlng != null ) {
			work_latlng = rowObject.work_latlng.indexOf(">") > 0 ? rowObject.work_latlng.split('>')[1].split('<')[0] : rowObject.work_latlng;
      		if( rowObject.work_addr_comment != null && rowObject.work_addr_comment != "" ) {
        		btn = "_green";
      		} else {
        		btn = "_t1";
      		}
    	}

    	str += "<div class=\"bt"+ btn +"\"><a href=\"JavaScript:mapOpen('ORDER', '"+ rowObject.order_seq +"','"+
				work_latlng +"','"+ rowObject.work_addr+"',''); \"" + " title='"+ work_latlng +"'> M </a></div>";
    	str += "</div>";

    	return str;
  	}

	function mapResponse(rowid, workJson) {
	  	$("#list").jqGrid('setCell', rowid,'work_addr', workJson.work_addr, '', true);  
	  	$("#list").jqGrid('setCell', rowid,'work_latlng', workJson.work_latlng, '', true);
	  	$("#list").jqGrid('setCell', rowid,'work_sido', workJson.work_sido, '', true); 
	  	$("#list").jqGrid('setCell', rowid,'work_sigugun', workJson.work_sigugun, '', true); 
	  	$("#list").jqGrid('setCell', rowid,'work_dongmyun', workJson.work_dongmyun, '', true); 
	  	$("#list").jqGrid('setCell', rowid,'work_rest', workJson.work_rest, '', true); 
	  	$("#list").jqGrid('setCell', rowid,'addr_edit', '1', '', true);  
	}

	function searchReset(seq) {
		var rowObject = $('#list').jqGrid('getRowData', seq);
		var orderState = rowObject.order_state;  
	
	 	if(orderState != "2"){
			alert("오더상태가 처리중일때 복구가 가능합니다.");
		 	return;
	 	}else{
		 	if(sessionId != rowObject.mod_admin){
			 	alert("처리중인 상담사만  복구 가능합니다.");
			 	return;
		 	}
	 	}
		 
		var logSeq = 0;
		
		var _data = {order_seq: seq, log_type: "D"};
		
	  	$.ajax({
		    type: "POST",
	    	url: "/admin/getOrderLog",
	    	data: _data,
	    	dataType: 'json',
	    	success: function(data) {
		    	var _html = "";
	    		if ( data != null && data.length > 0 ) {
					logSeq = data[0].log_seq;
	    			orderRestore(logSeq);
	    		}
	    	},
	    	beforeSend: function(xhr) {
	    		$(".wrap-loading").show();
				xhr.setRequestHeader("AJAX", true);
			},
	    	error: function(e) {
	    		$(".wrap-loading").hide();
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				}
	    	},
	    	complete: function(e) {
	    		$(".wrap-loading").hide();
	    	}
		});
	}

	function formatOrderState(cellvalue, options, rowObject) {
  		var str = "";
  /*
  
  rowObjct 는 grid 를 최초 그릴때 또는  reload 될때는 유효하다.
  format 를 써서 data 에 html 태그가 들어가면 유효 하지 않다.
  그래서 getRowData 를 써서 다시 가져 온다.
  
  최조 뿌릴때는 getRowData 를 해도 data 를 가져 오지 못 하므로 최초에는 rowObject 를 사용 한다.
  */
	  	if (typeof(rowObject.order_state) == "undefined"){
		  	var rowId = options.rowId;
		  	rowObject = $('#list').jqGrid('getRowData', rowId);
	  	}
	  
	  	var orderState = rowObject.order_state;  
	  
	  	if ( rowObject != null ) {
	    	str += "<div class=\"bt_wrap\">";
	    	str += "<div class=\"bt"+ (orderState == "1" ? "_on" : "") +"\"><a href=\"JavaScript:chkOrderState('"+ rowObject.order_seq +"', '"+ orderState +"' , '1');\"> 접수 </a></div>";
	    
	    	if(orderState == "2"){
	    		//자신이 수정중인거와 남이 수정중인 것의 색깔을 달리 한다.
	    		if(sessionId == rowObject.mod_admin){
	    			str += "<div class=\"bt_on\"><a href=\"JavaScript:chkOrderState('"+ rowObject.order_seq +"', '"+ orderState +"' , '2');\"> 처리중 </a></div>";
	    		}else{//남이 수정중인거
		    		str += "<div class=\"bt_red\"><a href=\"JavaScript:chkOrderState('"+ rowObject.order_seq +"', '"+ orderState +"' , '2');\"> 처리중 </a></div>";
	    		}
	    	}else{
		    	str += "<div class=\"bt\"><a href=\"JavaScript:chkOrderState('"+ rowObject.order_seq +"', '"+ orderState +"' , '2');\"> 처리중 </a></div>";
		    }
		    
		    str += "<div class=\"bt"+ (orderState == "4" ? "_green" : "") +"\"><a href=\"JavaScript:chkOrderState('"+ rowObject.order_seq +"', '"+ orderState +"' , '4');\"> 완료 </a></div>";
		    str += "<div class=\"bt"+ (orderState == "3" ? "_on" : "") +"\"><a href=\"JavaScript:chkOrderState('"+ rowObject.order_seq +"', '"+ orderState +"' , '3');\"> 거부 </a></div>";
		    
		    str += "</div>";
	  	}
	  
	  	return str;
	}

	function chkOrderState(orderSeq,nowState, orderState){

		var serverState = "0";
		var modAdmin = "";
		
		$(".wrap-loading").show();
		
		//서버에서 진행중인지를 체크 한다.
		$.ajax({
	       	type: "POST",
	       	url: "/admin/getSelectOrder",
	       	async: false, //false 로 하면 동기화 방식이다.
	       	data: {"order_seq": orderSeq},
	   		dataType: 'json',
	    	success: function(data) {
	    		if(data.result == "0000"){
	    			serverState = data.resultData.order_state;
	    			modeAdmin  = data.resultData.mod_admin;
	    		}else{
	    			 alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
	    		}
	    	},
	    	
	 		beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
				$(".wrap-loading").show();
	        },
	      	error: function(e) {
	      		$(".wrap-loading").hide();
	      		
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
	        },
	        complete: function(e) {
	        	$(".wrap-loading").hide();
	        }
	  	});
	
		//서버의 상태를 가져 오지 못 하였으면
		if(serverState == "0"){
			alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
			return;
		}
	
		if(serverState == "2" && sessionId != modeAdmin){
			if(nowState == serverState){
				toastFail('['+ modeAdmin + ' ] 이 진행중 인 오더 입니다. 수정할 수 없습니다.');
			}else{
				toastFail('['+ modeAdmin + ' ] 이 진행중 인 오더 입니다..\n 새로고침 됩니다.');
			
				$("#list").trigger("reloadGrid");
			}
			
			return;
		}
	
		if(nowState == 3 || nowState == 4){
			$.toast({title: "이미 처리된 오더 입니다." , position: 'middle', backgroundColor: '#d60606', duration:1000 });
			return;
		}	
		
		if(nowState != orderState){
			var isConfirm = true;
				
			if(orderState == 4){
				var workLatLng = $('#list').jqGrid('getRowData', orderSeq).work_latlng;
				if(workLatLng == ""){
					alert("지도 좌표가 설정된 후 “완료” 처리할 수 있습니다.");
					return;
				}
				
				var manager_type = $('#list').jqGrid('getRowData', orderSeq).manager_type;
	// 			if(manager_type == 'E'){
	// 				alert("본사매니져로 등록된 폰번호 입니다. 완료 처리 할 수 없습니다.");
	// 				return;
	// 			}
				

				if( $('#list').jqGrid('getRowData', orderSeq).employer_seq == "0" &&
						$('#list').jqGrid('getRowData', orderSeq).employer_num == "" ){
					alert("기존 구인처 검색 후 선택하거나 사업자번호(주민번호, -없이) 입력 후 완료 가능합니다.");
					return;
				}

				var _completeConfirmMsg = "완료 처리 하시겠습니까? ";
				if(!confirm(_completeConfirmMsg)){
					return;
				}
			}
			
			if(orderState == 3){
				if(!confirm("거부 처리 하시겠습니까?")){
					return;
				}
			}
			
			// state 상태 변경
			if(orderState < 4){
				if(orderState == '2') {
					var selData = $('#list').jqGrid('getRowData', orderSeq);
					
					$.ajax({
				       	type: "POST",
				       	url: "/admin/getManagerInfo",
				       	data: {manager_phone : replaceAll(selData.manager_phone, '-', ''), use_yn : 1},
				   		dataType: 'json',
				    	success: function(data) {
				    		if(data.result == "0000"){
				    			if(data.resultDTO != null && data.resultDTO.manager_use_status != '' && data.resultDTO.manager_use_status != '0') {
									alert("매니저 계정상태를 확인하세요.");
									return false;
				    			}else {
				    				$.ajax({
				    			       	type: "POST",
				    			       	url: "/admin/setOrderInfo",
				    			       	data: {"order_seq": orderSeq, "order_state": orderState},
				    			   		dataType: 'json',
				    			    	success: function(data) {
				    			    		if(data.result == "0000"){
				    							var rowData = $('#list').jqGrid('getRowData', orderSeq);
				    			    			 
				    			    			//특정셀의 data 바꾸기 2번 방법으로 하면 rowObject 값이 이상하게 바뀐다. 왜 그럴까.
				    			    			//2 $("#list").jqGrid('setCell', lastsel, 'order_state', orderState, '', true);  
				    			    			
				    			    	 	/* 	rowData.order_state = orderState;
				    			    			rowData.mod_admin = sessionId;
				    			    			rowData.order_state_view = orderState;
				    			    			
				    			    			$('#list').jqGrid('setRowData', orderSeq, rowData);  
				    			    			 */
				    			    			if(orderState < 4){
				    			    				$("#list").jqGrid('setCell', orderSeq, 'mod_admin', sessionId, '', true);	/*  mod_admin 을 먼저 바꿔 줘야 order_state_view 가 제대로 동작한다.*/
				    			    				$("#list").jqGrid('setCell', orderSeq, 'order_state', orderState, '', true);
				    			    				$("#list").jqGrid('setCell', orderSeq, 'order_state_view', orderState, '', true);
													$("#list").trigger("reloadGrid");
				    			    			}
				    			    		
				    			    		/* 	//cell 을 수정 할 수 있도록 한다.
				    			    			if(orderState =="2"){
				    			    				$("#list").jqGrid('editRow',orderSeq);
				    			    			}else{//cell 을 수정 할 수 없도록 한다.
				    			    				$("#list").jqGrid('restoreRow',orderSeq);
				    			    			}
				    			    			 */
				    						}else{
				    							alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				    			    		}
				    			    	},
				    			 		beforeSend: function(xhr) {
				    			 			$(".wrap-loading").show();
				    						xhr.setRequestHeader("AJAX", true);
				    			        },
				    			      	error: function(e) {
				    			      		$(".wrap-loading").hide();
				    			            alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				    			        },
				    			        complete: function(e) {
				    			        	$(".wrap-loading").hide();
				    			        }
				    				});
				    			}
							}else{
								alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				    		}
				    	},
				 		beforeSend: function(xhr) {
							xhr.setRequestHeader("AJAX", true);
							$(".wrap-loading").show();
				        },
				      	error: function(e) {
				      		$(".wrap-loading").hide();
				            alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				        },
				        complete: function(e) {
				        	$(".wrap-loading").hide();
				        }
					});
				}else {
					$.ajax({
    			       	type: "POST",
    			       	url: "/admin/setOrderInfo",
    			       	data: {"order_seq": orderSeq, "order_state": orderState},
    			   		dataType: 'json',
    			    	success: function(data) {
    			    		if(data.result == "0000"){
    							var rowData = $('#list').jqGrid('getRowData', orderSeq);
    			    			 
    			    			//특정셀의 data 바꾸기 2번 방법으로 하면 rowObject 값이 이상하게 바뀐다. 왜 그럴까.
    			    			//2 $("#list").jqGrid('setCell', lastsel, 'order_state', orderState, '', true);  
    			    			
    			    	 	/* 	rowData.order_state = orderState;
    			    			rowData.mod_admin = sessionId;
    			    			rowData.order_state_view = orderState;
    			    			
    			    			$('#list').jqGrid('setRowData', orderSeq, rowData);  
    			    			 */
    			    			if(orderState < 4){
    			    				$("#list").jqGrid('setCell', orderSeq, 'mod_admin', sessionId, '', true);	/*  mod_admin 을 먼저 바꿔 줘야 order_state_view 가 제대로 동작한다.*/
    			    				$("#list").jqGrid('setCell', orderSeq, 'order_state', orderState, '', true);
    			    				$("#list").jqGrid('setCell', orderSeq, 'order_state_view', orderState, '', true);
									$("#list").trigger("reloadGrid");
    			    			}
    			    		
    			    		/* 	//cell 을 수정 할 수 있도록 한다.
    			    			if(orderState =="2"){
    			    				$("#list").jqGrid('editRow',orderSeq);
    			    			}else{//cell 을 수정 할 수 없도록 한다.
    			    				$("#list").jqGrid('restoreRow',orderSeq);
    			    			}
    			    			 */
    						}else{
    							alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
    			    		}
    			    	},
    			 		beforeSend: function(xhr) {
    			 			$(".wrap-loading").show();
    						xhr.setRequestHeader("AJAX", true);
    			        },
    			      	error: function(e) {
    			      		$(".wrap-loading").hide();
    			            alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
    			        },
    			        complete: function(e) {
    			        	$(".wrap-loading").hide();
    			        }
    				});
				}
			//완료 처리================
			}else if(orderState == 4){//완료
				if(manager_type != 'E') {
					orderSave(orderSeq);
				}else {
					if($("#list").jqGrid('getCell', orderSeq, 'employer_seq') == '0' && manager_type == 'E') {
						alert("본사매니저는 통합검색 후 구인처를 선택해야합니다.");
						
						return false;
					}
					
					var searchPhone = $("#list").jqGrid('getCell', orderSeq, 'manager_phone');
					
					searchPhone = searchPhone.split("-").join("");
					
					$.ajax({
				       	type: "POST",
				       	url: "/admin/getManagerInfo",
				       	async: false, //false 로 하면 동기화 방식이다.
				       	data: {manager_phone : searchPhone, use_yn : '1'},
				   		dataType: 'json',
				    	success: function(data) {
				    		if(data.result == "0000"){
				    			if(confirm("본사매니저 구인처 [" + data.resultDTO.employer_name + "] 등록하시겠습니까?")) {
				    				orderSave(orderSeq);
				    			}else {
				    				return false;
				    			}
				    		}else{
				    			alert(data.message);
				    		}
				    	},
				 		beforeSend: function(xhr) {
							xhr.setRequestHeader("AJAX", true);
							$(".wrap-loading").show();
				        },
				      	error: function(e) {
				      		$(".wrap-loading").hide();
				      		
							if ( e.status == "901" ) {
								location.href = "/admin/login";
							} else {
								alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
							}
						},
						complete: function(e) {
							$(".wrap-loading").hide();
						}
					});
				}
			}
		}
	}

	// 오더 정보 반영
	function orderSave(orderSeq){
		var rowid = $("#list").jqGrid("getGridParam", "selrow" );			// 선택한 열의 아이디값
		var rowData = $("#list").jqGrid("getRowData", orderSeq);
	
		var companyName = rowData.company_name;
		var companySeq = rowData.company_seq;
		
		if(companySeq == "0"){
			companyName = "잡앤파트너";
		}
		
		if(!confirm("지점 : [ " + companyName +" ] 로 등록 됩니다. ")){
			return;
		}
	
		//서버에서 진행중인지를 체크 한다.
		$.ajax({
       		type: "POST",
       		url: "/admin/setOrderProcess",
       		async: false, //false 로 하면 동기화 방식이다.
       		data: rowData,
   			dataType: 'json',
    		success: function(data) {
    			if(data.code == "0000"){
    				$("#list").jqGrid('setCell', orderSeq, 'order_state', 4, '', true);
    				$("#list").jqGrid('setCell', orderSeq, 'order_state_view', 4, '', true);
    				$("#list").jqGrid('setCell', orderSeq, 'mod_admin', sessionId, '', true); 
    		
    				$.toast({title: '처리되었습니다.', position: 'middle', backgroundColor: '#5aa5da', duration:1000 });
					$("#list").trigger("reloadGrid");
    			}else{
	    			alert(data.message);
    			}
    		},
 			beforeSend: function(xhr) {
 				$(".wrap-loading").show();
				xhr.setRequestHeader("AJAX", true);
			},
      		error: function(e) {
      			$(".wrap-loading").hide();
				if ( e.status == "901" ) {
					location.href = "/admin/login";
               	} else {
                	alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
               	}
        	},
        	complete: function(e) {
        		$(".wrap-loading").hide();
        	}
  		});
	}

	function showCodeLog(obj,seq,e) {
		$('#code_log_layer').html("");
  		var _display;

  		_display = $('#code_log_layer').css('display') == 'none'?'block':'none';

 	// $(obj).parent().offset().left;
  
  	//var divEl = $(obj).parent();
  		var divEl = $("#opt_layer");
  		var divX = divEl.offset().left;
  		var divY = divEl.offset().top;
  		var divHeight = divEl.height();
    
  		$('#code_log_layer').css('left', divX+"px");
  		$('#code_log_layer').css('top', (divY + divHeight -40) +"px");

   		var _data = {order_seq: obj };
    	$.ajax({
	    	type: "POST",
	    	url: "/admin/getOrderLog",
	    	data: _data,
	    	dataType: 'json',
	    	success: function(data) {
		    	var _html = "";
	    		if ( data != null && data.length > 0 ) {
					_html = "<table class='bd-form s-form'>";
	    		
	    			for ( var i = 0; i < data.length; i++ ) {
	    		/* 	jaon 파싱해서 이름으로 변경 
	    			var changeData = "";
	    			var json = JSON.parse( data[i].change_data );
	    			
	    			$.each(json, function(key, value){
	    			    
	    			    if( key == "employer_name"){
	    			    	changeData += "구인처명 :" + value;
	    			    }
	    			    
	    			});
	    			 */
						_html += "<tr>"+
									"<td style='padding:5px;'>" + i + "</td>" + 
	    							"<td  style='padding:5px;'> "+ data[i].log_type + "</td>" + 
	    							"<td  style='padding:5px;' width='500px'>"+ data[i].change_data + "</td>" + 
	    							"<td  style='padding:5px;'>" + data[i].reg_admin + "</td>" +
	    							"<td  style='padding:5px;'>" + data[i].reg_date + "</td>" +
	    							"<td  style='padding:5px;'><a href='javascript:void(0)' onclick='orderRestore(\""+ data[i].log_seq +"\")'>복구</td>" +
	    						"</tr>";
					}
	    		
	    			_html+="</table>";
	    		}
	    	
	    		$('#code_log_layer').html(_html);
	    		$('#code_log_layer').css('display', _display);
	    	},
	    	beforeSend: function(xhr) {
	    		$(".wrap-loading").show();
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				$(".wrap-loading").hide();
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				}
			},
			complete: function(e) {
				$(".wrap-loading").hide();
			}
		});
	}

	//원래대로
	function orderRestore(logSeq){
		var _data = {log_seq: logSeq };
	    $.ajax({
		    type: "POST",
		    url: "/admin/setOrderLogRestore",
		    data: _data,
		    dataType: 'json',
		    success: function(data) {
		    	$("#list").trigger("reloadGrid");
		    	$('#code_log_layer').css('display', 'none');
		    },
		    beforeSend: function(xhr) {
		    	$(".wrap-loading").show();
				xhr.setRequestHeader("AJAX", true);
			},
		    error: function(e) {
		    	$(".wrap-loading").hide();
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				}
		    },
		    complete: function(e) {
		    	$(".wrap-loading").hide();
		    }
		});
	}

	//일일대장에서 구인처/현장을 삭제하는 경우
	function  validSearchOrder(value, cellTitle, valref) {
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
	
		if ( value == "?" ) {
	 		$("#list").jqGrid('setCell', lastsel, 'employer_seq', 0, '', true);
	 		$("#list").jqGrid('setCell', lastsel, 'employer_name', null, '', true);
			$("#list").jqGrid('setCell', lastsel, 'work_seq', 0, '', true);
			$("#list").jqGrid('setCell', lastsel, 'work_name', null, '', true);
			$("#list").jqGrid('setCell', lastsel, 'search_flag', 0, '', true);
			
			$("#list").jqGrid('setCell', lastsel,'work_addr', null, '', true);  
			$("#list").jqGrid('setCell', lastsel,'work_latlng', null, '', true);  
			$("#list").jqGrid('setCell', lastsel,'addr_edit', '0', '', true);  
			$("#list").jqGrid('setCell', lastsel,'labor_contract_seq', '0', '', true);  
			$("#list").jqGrid('setCell', lastsel,'receive_contract_seq', '0', '', true);  
		/* 
		$("#list").jqGrid('setCell', lastsel, 'company_seq'         , 0		,	'', true);
 		$("#list").jqGrid('setCell', lastsel, 'company_name'       , null	,	'', true);
		$("#list").jqGrid('setCell', lastsel, 'manager_seq'   		, 0		,	'', true);
		$("#list").jqGrid('setCell', lastsel, 'manager_name'    	, null	,	'', true);
		$("#list").jqGrid('setCell', lastsel, 'manager_phone'   	, null	,	'', true);
		$("#list").jqGrid('setCell', lastsel, 'work_manager_name'    	, null	,	'', true);
		$("#list").jqGrid('setCell', lastsel, 'work_manager_phone'   	, null ,	'', true);
		*/
			isSelect = false;
		}
		
		if( !isSelect ){
			$("#list").jqGrid('setCell', lastsel, 'employer_search_yn'  , 0,  '', true);
		}

		return [true, ""];
	}


	//order_search
	function formatOrderSearchName(cellvalue, options, rowObject) {
		if ( cellvalue == "" || cellvalue == null ){ 
			if(rowObject.search_flag == "0"){
				return "?";	
			}else{
				return rowObject.employer_name;
			}
			
		}else	return cellvalue;
	}

	function formatIlboState(cellvalue, options, rowObject) {
	  	var str = "";
	  	var gId = options.gid;
	  	var rowId = options.rowId;
	  
	  	if (typeof(rowObject.ilbo_state) == "undefined"){
		  	rowObject = $('#'+gId).jqGrid('getRowData', rowId);
	  	}
	  
	  	var ilboState = rowObject.ilbo_state;  //현재 상태
	  	  
	  	str += "<div class=\"bt_wrap\">";
		str += "<div class=\"bt"+ (ilboState == "1" ? "_on" : "") +"\"><a href=\"JavaScript:chkIlboState('"+ rowObject.order_seq +"', '"+ rowId +"','"+ ilboState +"','"+ gId +"');\"> 적용 </a></div>";
		str += "</div>";
	  
	  	return str;
	}

	//일일대장체 추가 시켜야 한다.
	function chkIlboState(orderSeq, workSeq, nowState, gId){
		if(nowState == "1"){ //현재 상태가 1이면 적용 할 필요 없다.
			return;
		}
	
		var orderState =  $('#list').jqGrid('getRowData', orderSeq).order_state;
	
		if(orderState != 4){
			alert("오더 상태가 [완료] 상태에서 처리 할 수 있습니다.");
			return;
		}
		
		var _lat = $("#list").jqGrid("getRowData", orderSeq).work_latlng.split(',')[0];
		var _lng = $("#list").jqGrid("getRowData", orderSeq).work_latlng.split(',')[1];
		
		// 비동기 통신 2번으로 인한 뱅글뱅글을 비동기 밖으로 빼냄...
		$(".wrap-loading").show();
		
		naver.maps.Service.reverseGeocode({
			location: new naver.maps.LatLng(_lat, _lng),
    	}, function(status, response) {
	        if (status === naver.maps.Service.Status.ERROR) {
            	return alert('Something Wrong!');
        	}
	
        	if(!confirm("일일 대장에 추가 하시겠습니까?")){
        		$(".wrap-loading").hide();
        		
	    		return;
    		}
	    	
    		var params= {
    			order_seq : orderSeq,
    			work_seq : workSeq
    		}
    		
    		//서버에서 진행중인지를 체크 한다.
    		$.ajax({
	           	type: "POST",
           		url: "/admin/setOrderIlboProcess",
           		async: false, //false 로 하면 동기화 방식이다.
           		data: params,
       			dataType: 'json',
        		success: function(data) {
        			if(data.code == "0000"){
        				$('#'+gId).jqGrid('setCell', workSeq, 'ilbo_state', "1", '', true);
        				$('#'+gId).jqGrid('setCell', workSeq, 'ilbo_state_view', "1", '', true);
        			
        				$.toast({title: '일보에 등록 되었습니다.', position: 'middle', backgroundColor: '#5aa5da', duration:1000 });
        			}else{
        			 	alert( data.message);
        			}
        		},
     			beforeSend: function(xhr) {
     				$(".wrap-loading").show();
					xhr.setRequestHeader("AJAX", true);
            	},
          		error: function(e) {
          			$(".wrap-loading").hide();
					if ( e.status == "901" ) {
						location.href = "/admin/login";
					} else {
						alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
					}
				},
				complete: function(e) {
					$(".wrap-loading").hide();
				}
			});
		});
	}

	function closeWorkOffer(){
	  	$("html").css({"overflow-y":"scroll"});
		$(".bgPopup").fadeOut(100);
		$(".popup1").css({"display":"none"});
		$(".jobOffer").hide();
	}

	function funcAddWork(){
		var i = 0;
		var html = "";
		var fileLength;
		
		fileLength = $(".secondOI").length;
		
		if(fileLength == 5){
			toastW("최대 5개까지 등록 가능합니다.");
			return;
		}
		
		html += '<div class="secondOI clear">';
		html += '	<div class="secValue1">';				
		html += '		<input type="hidden" name="arr_kind_code" class="kind_code">';
		html += '<input type="hidden" name="arr_job_detail_code" class="job_detail_code">';
	//	html += '<input type="hidden" name="work_job_detail_name" class="job_detail_name">';
	 	html += '<input type="hidden" name="arr_job_detail_name" class="job_detail_name">';
		html += '<input type="hidden" name="arr_job_add_code" class="job_add_code">';
		html += '<input type="hidden" name="arr_job_add_name" class="job_add_name">';
		
		html += '<input type="text" name="arr_kind_name" placeholder="선택*" class="opp notEmpty"  title="직종"  required="required" readonly onfocus="this.blur()" onclick="fn_selectJob(this)">';
		html += '	</div>';
		html += '	<div class="secValue2">';
		html += '		<input type="text" name="arr_price" class="price" required="required"  basic_price="0" short_price="0" short_price_night="0" add_day="0" add_night="0">';
		html += '	</div>';
		html += '	<div class="secValue3">';
		html += '		<input type="text" name="arr_memo" class="arr_memo notEmpty" title="상세작업설명" placeholder="필수*" required="required">';
		html += '	</div>';
		html += '	<div class="secValue4">';
		html += '		<span class="personMiner pm" onclick="funWorkerMinu(this)"><img src="/resources/web/images/personMinu.png" alt="" ></span>';
		html += '		<input type="text" value="1" class="worker_num" name="arr_count">';
		html += '		<span class="personPlus" onclick="funWorkerPlus(this)"><img src="/resources/web/images/personPlus.png" alt="" ></span>';
		html += '	</div>';
		html += '<div class="secValue5" style="padding-top:0.2em;;">';
		html += '<span class="del plusOiDel" onClick="funWorkRemove(this)">삭제</span>';
		html += '</div>';
		html += '</div>';
		
		$(".secondOI").last().after(html);	
		
		$(".secValue1 .opp").on("click",function(){
			$(".secondOIpop").fadeIn(500);
	    });	
	}

	function funWorkRemove(obj){
		$(obj).parents(".secondOI").remove();
	}

	//인원수 추가
	function funWorkerPlus(obj){
		var num = $(obj).parents(".secValue4").find(".worker_num").val();
		
		$(obj).parents(".secValue4").find(".worker_num").val(num*1+1);
	}

	//인원수 감소
	function funWorkerMinu(obj){
		var num = $(obj).parents(".secValue4").find(".worker_num").val();
		
		if(num>1){
			$(obj).parents(".secValue4").find(".worker_num").val(num*1-1); 
		}
	}

	//일등록...
	function fucWorkProcess(){
		if ( !fnChkValidForm("frmWorkReg") ){
			return;
		}
			
		if ($("input:checkbox[id='is_tax']").is(":checked")) {
			if ($("#tax_name").val() == "") {
				toastW("계산서 담담자 이름을 입력 하세요.");
				$("#tax_name").focus();
				return;
			}
			
			if ($("#tax_phone").val() == "") {
				toastW("계산서 담담자 폰번호를 입력 하세요.");
				$("#tax_phone").focus();
				return;
			}
		}
	
		$("[class ^= arr_memo]").each(function() {
			var memo = $(this).val();
			memo = memo.replace(/,/gi,"&#44");
			$(this).val(memo);
		});
		
		naver.maps.Service.geocode({
	        query: $("#work_name").val()
	    }, function(status, response) {
	        if (status === naver.maps.Service.Status.ERROR) {
	            return alert('Something Wrong!');
	        }
	        
	        if (response.v2.meta.totalCount === 0) {
	            return alert('정확한 주소를 입력해주세요.');
	        }
	
	        $("#work_sido").val(response.v2.addresses[0].addressElements[0].shortName);
	        $("#work_sigugun").val(response.v2.addresses[0].addressElements[1].shortName);
	        $("#work_dongmyun").val(response.v2.addresses[0].addressElements[2].shortName);
	        $("#work_rest").val(response.v2.addresses[0].addressElements[7].shortName);
	        $("#work_addr").val($("#work_name").val());
	        
	        if(!confirm("인력을 요청하시겠습니까? ")){
	    		return;
	    	}
	    	
	    	$('#frmWorkReg').ajaxForm({
	    		url : "/admin/regOrderProcess",
	    		dataType : "json",
	    		enctype : "multipart/form-data", // 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
	    		contentType : 'application/x-www-form-urlencoded; charset=utf-8', //캐릭터셋을 euc-kr로 
	    		success : function(data) {
	    			if (data.code == "0000") {
	    				alert("인력 요청이 성공적으로 접수되었습니다. 감사합니다. ");
	    				// closeWorkOffer();
	    				location.reload();
	    			} else {
	    				toastFail( data.message);
	    			}
	    		},
	    		beforeSend : function(xhr) {
	    			xhr.setRequestHeader("AJAX", true);
	    			$(".wrap-loading").show();
	    			
	    		},
	    		error : function(e) {
	    			$(".wrap-loading").hide();
	    			toastFail("등록 실패");
	    		},
	    		complete : function() {
	    			$(".wrap-loading").hide();
	    		}
	    	});
	
	    	$('#frmWorkReg').submit();
	    });
	}

	//야간단가.. 30분단위기준ㅋ
	function getNightFee(price){
		if(price >= 200000){
			return 20000;
	 	}else if(price >= 160000){
		  	return 17500;
	  	}else{
		  	return 15000;
	  	}
	}
	
	function validCareer(value, cellTitle, valref) {
		return [true, ""];
	}
	
	function fn_receiveName(cellValue, options, rowObject) {
		if(rowObject.receive_contract_seq == '0') {
			return "미사용";
		}else {
			return rowObject.receive_contract_name;
		}
	}
	
	function fn_laborName(cellValue, options, rowObject) {
		if(rowObject.labor_contract_seq == '0') {
			return "미사용";
		}else {
			return rowObject.labor_contract_name
		}
	}
</script>
<%--
    <div class="title">
      <h3> 현장 관리 </h3>
    </div>
--%>
	<article>
		<div class="inputUI_simple">
			<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
        		<caption>등록일시, 상태, 직접검색 영역</caption>
        		<colgroup>
          			<col width="120px" />
          			<col width="270px" />
          			<col width="120px" />
          			<col width="500px" />
          			<col width="120px" />
          			<col width="" />
        		</colgroup>
        		<tr>
        			<th scope="row">상태</th>
	          		<td>
	            		<input type="radio" id="srh_use_yn_1" name="srh_use_yn" class="inputJo" value=""  /><label style="margin:0" for="srh_use_yn_1" class="label-radio">전체</label>
	            		<input type="radio" id="srh_use_yn_2" name="srh_use_yn" class="inputJo" value="1" checked="checked" /><label style="margin:0" for="srh_use_yn_2" class="label-radio">사용</label>
	            		<input type="radio" id="srh_use_yn_3" name="srh_use_yn" class="inputJo" value="0" /><label style="margin:0" for="srh_use_yn_3" class="label-radio">미사용</label>
	          		</td>
          			<th scope="row"  class="linelY pdlM">오더상태</th>
	          		<td>
	          			<div class="select-inner">
	            			<input type="radio" id="order_state_0" name="order_state" class="inputJo" value=""   checked="checked" /><label style="margin:0" for="order_state_0" class="label-radio">전체</label>
	            			<input type="radio" id="order_state_1" name="order_state" class="inputJo" value="1"/><label style="margin:0" for="order_state_1" class="label-radio">접수중</label>
	            			<input type="radio" id="order_state_2" name="order_state" class="inputJo" value="2"/><label style="margin:0" for="order_state_2" class="label-radio">처리중</label>
	            			<input type="radio" id="order_state_3" name="order_state" class="inputJo" value="3"/><label style="margin:0" for="order_state_3" class="label-radio">거부</label>
	            			<input type="radio" id="order_state_4" name="order_state" class="inputJo" value="4"/><label style="margin:0" for="order_state_4" class="label-radio">완료</label>
	            		</div>
	            		<div class="btn-module floatL mglS">
	              			<a href="#none" id="btnSrh" class="search">검색</a>
	            		</div>
	         		</td>
         			<th scope="row"  class="linelY pdlM">SMS수신</th>
	         		<td>
	            		<label class="switch">
	  						<input type="checkbox" name="toggle" ${work_app_sms eq '1' ? 'checked' : '' }>
	  						<span class="slider round"></span>
						</label>
						<p class="toggles" ${work_app_sms == 1 ? 'style="display:none;"' : '' }>OFF</p>
						<p class="toggles" ${work_app_sms == 1 ? '' : 'style="display:none;"' }>ON</p>
					</td>
				</tr>
        		<tr>
           			<th scope="row">
	           			<div class="btn-module">
				      		<div class="" style="width: 266px;">
				        <!-- <a href="#none" id="btnAdd" class="btnStyle01">추가</a> -->
				        <!-- <a href="#none" id="btnDel" class="btnStyle01">삭제</a> -->
				        		<a href="#none" id="btnRel" class="btnStyle05">새로고침</a>
				        		<a href="javascript:void(0);" id="btnExtend" class="btnStyle01">전체열기</a>
				        		<a href="javascript:void(0);" id="btnReq" class="btnStyle01">인력요청</a>
				        		<div class="order_iframe">
								  <div class="title_text_none3">
                          			<img src="/resources/manager/image/Layout/bt-mnall-close.png">
                      			  </div>
			                      <iframe src="${managerBaseUrl}/ims/orderLink?aseq=${adminSeq}" width="100%" height="100%">
			                      </iframe>
			                    </div>
			                    <div class="bk_background"></div>
				      		</div>
						</div>
           			</th>
          			<td>
          		  		<div class="leftGroup" >
				         	총오더 : <span id="totalRecords" class="colorR mgRM"></span>
				       		총인원 : <span id="totalWork" class="colorR"></span>
				   		</div>
          			</td>
          			<th scope="row" class="linelY pdlM">등록일시</th>
          			<td colspan=3>
          				<span class="floatL arrow">
	              			<a class="prev10" href="javascript:prevMonthSubmit('start_reg_date'); $('#btnSrh').trigger('click');">>></a>
	              			<a class="prev" href="javascript:prevDateSubmit('start_reg_date'); $('#btnSrh').trigger('click');">></a>
            			</span>
            			<p class="floatL">
	              			<input type="text" id="start_reg_date" name="start_reg_date" class="inp-field wid100 datepicker" onChange="javaScript:$('#btnSrh').trigger('click');" />
	              			<input type="hidden" id="end_reg_date" name="end_reg_date" class="inp-field wid100" onchange="fn_test()"/>
            			</p>
             			<span class="floatL mgRS arrow">
              				<a class="next" href="javascript:nextDateSubmit('start_reg_date'); $('#btnSrh').trigger('click');">></a>
              				<a class="next10" href="javascript:nextMonthSubmit('start_reg_date'); $('#btnSrh').trigger('click');">>></a>
            			</span>
            			<div class="inputUI_simple">
            				<span class="contGp btnCalendar">
								<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'toDay' ); $('#btnSrh').trigger('click');" checked="checked" /><label style="margin:0" for="day_type_2" class="label-radio">내일</label>
            				</span>
							<span class="tipArea colorN02">* 직접 기입 시, ‘2017-06-28’ 형식으로 입력</span>
            			</div>
          			</td>
        		</tr>
      		</table>
		</div>
	</article>

    <table id="list"></table>
    
    <div id="paging"></div>
    
    <div id="opt_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b;"></div>
<!-- 	<div id="code_log_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b; padding:5px">NO Data</div> -->
	</form>
	
	<div class="bgPopup"></div>
	
	<!-- 직종옵션 팝업  -->
	<div id="popup-layer" style="width:1300px;margin-left: -700px;top: 0%">
  		<header class="header">
    		<h1 class="tit">직종선택</h1>
    		<a class="close" href="javascript:closeIrPopup('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
  		</header>
  		<input type="hidden" id="parentRowId" name="parentRowId">
  		<input type="hidden" id="rowId" name="rowId">
  		<section>
  			<div class="cont-box">
    			<article>
      				<table class="bd-list" summary="직종 등록 영역입니다.">
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
      				<div class="rightGroup"><a href="#popup-layer" id="btnJobReset" class="btnStyle08">초기화</a></div>
      				<div class="rightGroup"><a href="#popup-layer" id="btnJobReg" class="btnStyle01">등록</a></div>
    			</div>
  			</div>
  		</section>
	</div>
	
	<form id="frmWorkReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
		<input type="hidden" name="order_request" value="CALL" />
		<input type="hidden" name="work_sido" id="work_sido" />
		<input type="hidden" name="work_sigugun" id="work_sigugun" />
		<input type="hidden" name="work_dongmyun" id="work_dongmyun" />
		<input type="hidden" name="work_rest" id="work_rest" />
		<input type="hidden" name="work_addr" id="work_addr" />
		<input type="hidden" name="work_breaktime" id="work_breaktime" value="12:00 ~ 13:00" />
		<div class="popup1">
			<div class="jobOffer">
				<div class="bgOffer"></div>
				<div class="joHeader clear">
					<p class="johHeadTxt"><strong>인력요청</strong><!-- (구인자 클릭) --></p>
					<p class="johHeadImg"><img src="/resources/web/images/subClose.png" alt="" id="closeJob" class="close" style="cursor:pointer" onclick="closeWorkOffer()"></p>
				</div>
				<div class="joContents clear">
					<div class="offerInfo01">
						<p class="firstOI_01 clear">
							<label style="margin:0">현 장<span>*</span></label>
							<input type="text" id="work_name" name="work_name" placeholder="주소" required="required" class="notEmpty"  title="현장주소" >
						</p>
						<p class="firstOI_02 clear">
							<label style="margin:0" class="tit1">작 업 일<span>*</span></label>
							<input type="text" id="order_date" name="order_date" class="date notEmpty"  title="작업일"  required="required" readonly>
							<label style="margin:0" class="tit2">작업일수</label>
							<select name="work_day" id="work_days"  name="work_days"  required="required" class="notEmpty" title="작업일수">
								<option value="" selected>선택</option>
								<option value="1일" >1일</option>
								<option value="2일">2일</option>
								<option value="1일~2일">1일~2일</option>
								<option value="2일~3일<">2일~3일</option>
								<option value="3일~4일">3일~4일</option>
								<option value="5일~6일">5일~6일</option>
								<option value="7일 이상">7일 이상</option>
							</select>
						</p>
						<p class="firstOI_02 clear">
							<label style="margin:0" class="tit1">도 착 시 간</label>
							<select name="work_arrival" id="work_arrival" class="case3" onchange="timeChange();">
								<c:forEach var="i" begin="0" end="24" step="1">
									<c:set var="hh" value="0${i}"/>
									<c:set var="hh" value="${fn:substring(hh, fn:length(hh)-2, fn:length(hh) )}"/>
									<c:forEach var="j" begin="0" end="30" step="30">
										<c:set var="tt" value="0${j}"/>
										<c:set var="tt" value="${fn:substring(tt, fn:length(tt)-2, fn:length(tt) )}"/>
										<c:set var="vv" value="${hh}${tt}"/>
										<c:if test="${vv  !='2430'}">
											<option value="${hh}${tt}" ${vv eq "0800" ? 'selected':''}>${hh}:${tt}</option>
										</c:if>
									</c:forEach>
								</c:forEach>
							</select>
							<label style="margin:0" class="tit2">종료시간</label>
							<select name="work_finish" id="work_finish"  onchange="timeChange();">
								<c:forEach var="i" begin="0" end="36" step="1">
									<c:set var="hh" value="0${i}"/>
									<c:set var="hh" value="${fn:substring(hh, fn:length(hh)-2, fn:length(hh) )}"/>
									<c:forEach var="j" begin="0" end="30" step="30">
										<c:set var="tt" value="0${j}"/>
										<c:set var="tt" value="${fn:substring(tt, fn:length(tt)-2, fn:length(tt) )}"/>
										<c:set var="vv" value="${hh}${tt}"/>
										<c:if test="${vv  !='3630'}">
											<option value="${hh}${tt}" ${vv eq "1700" ? 'selected':''}>${hh}:${tt}</option>
										</c:if>
									</c:forEach>
								</c:forEach>
							</select>
						</p>
						<p class="firstOI_01 clear breaktime-div" style="text-align: right; padding: 5px 0px;">
							휴게시간 : <span id="breakTimeSpan">12:00 ~ 13:00</span>
						</p> 
						<p class="firstOI_01 clear">
							<label style="margin:0" class="tit1">구인처상호<span>*</span></label>
							<input type="text"  class="notEmpty"  title="구인처 상호"  id="employer_name" name="employer_name" placeholder="상호명(개인은 성함 입력)" required="required">
						</p>
						<p class="firstOI_03 clear">
							<label style="margin:0" class="tit1">오더하신분<span>*</span></label>
							<input type="text"  class="notEmpty"  title="오더자이름"  id="manager_name" name="manager_name" placeholder="이름" required="required">
							<label style="margin:0" class="tit2">연락처<span>*</span></label>
							<input type="text" class="con2 notEmpty tel"  title="오더자연락처" id="manager_phone" name="manager_phone" placeholder="핸드폰번호" required="required" class="con2">
							<span class="btn"><span class="add"><img src="/resources/web/images/plusIcon.png" alt="추가"></span><span class="del"><img src="/resources/web/images/minusIcon.png" alt="삭제"></span></span>
						</p>
						
						<p class="firstOI_04 clear">
							<label style="margin:0" class="tit1">현장담당자</label>
							<input type="text" id="work_manager_name"  name="work_manager_name"  placeholder="이름" required="required">
							<label style="margin:0" class="tit2">연락처</label>
							<input type="text" id="work_manager_phone"  name="work_manager_phone"  placeholder="핸드폰번호" required="required" class="con2 ">
						</p>
					</div>
					
					<div class="offerInfo02">
						<div class="secondOI clear">
							<div class="secValue1">
			    				<div class="secondOIpop" style="margin-top: -45%">
									<div class="sOIpopCon">
										<div class="sOItit">
											<p><strong>전문분야</strong><br>단가는 2019년 기준이며, 계단유무/장비사용에 따라 단가가 조정될 수 있습니다.</p>
										</div>
										<div class="sOIemp">
											<c:set var="jobKind" value="0" />					
											<c:forEach items="${jobList}" var="job" varStatus="status">
	            								<c:if test="${job.job_kind ne jobKind}">
													<c:set var="jobKind" value="${job.job_kind}" />
													<c:choose>
														<c:when test="${jobKind eq '1'}">
	      													<div class="man clear">
																<p class="empTit">잡부조공</p>
																<div class="empButton">
	 													</c:when>
														<c:otherwise>
														      	</div>
															</div>
															<div class="tech clear">
																<p class="empTit">기술인력</p>
																<div class="empButton">
	  													</c:otherwise>
													</c:choose>
												</c:if>
							            		<p price="${job.basic_price}" code_gender="${job.job_kind}"  kind_code="${job.job_code }">${job.job_name}</p>
											</c:forEach>
											<c:if test="${!empty jobList}">
																</div>
															</div>
											</c:if>
											</div>
											<p class="sOItxt">새벽시간, 거리가 먼 현장인 경우 "교통비"가 별도 책정될 수 있습니다.</p>
										</div>
									</div>
						<label>직종</label>
						<input type="hidden" name="arr_kind_code" class="kind_code" codePrice='1'>
						<input type="hidden" name="arr_job_detail_code" class="job_detail_code">
						<input type="hidden" name="arr_job_detail_name" class="job_detail_name">
<!-- 						<input type="hidden" name="work_job_detail_name" class="job_detail_name"> -->
						<input type="hidden" name="arr_job_add_code" class="job_add_code">
						<input type="hidden" name="arr_job_add_name" class="job_add_name">
						<input type="text" placeholder="선택" class="opp notEmpty"  title="직종"  name="arr_kind_name" class="kind_name" required="required" readonly onfocus="this.blur()" onclick="fn_selectJob(this)">
					</div>
					<div class="secValue2">
						<label>단가(원)</label>
						<input type="text" name="arr_price" placeholder="" class="price num"  title="단가" basic_price="0" short_price="0" short_price_night="0" add_day="0" add_night="0" value= "" required="required" >
					</div>
					<div class="secValue3">
						<label>상세작업설명</label>
						<input type="text" name="arr_memo" class="arr_memo notEmpty" title="상세작업설명"  placeholder="필수" required="required">
					</div>
					<div class="secValue4">
						<label>인원</label>
						<span class="personMiner" onclick="funWorkerMinu(this)"><img src="/resources/web/images/personMinu.png" alt=""></span>
						<input type="text" value="1" class="worker_num notEmpty num" name="arr_count">
						<span class="personPlus" onclick="funWorkerPlus(this)"><img src="/resources/web/images/personPlus.png" alt="" ></span>
					</div>
					<div class="secValue5">
						<span id="workadd" onclick="funcAddWork()">추가</span>
					</div>
				</div>
			</div>
			<div class="offerInfo03 clear">
				<dl>
					<dt>단가지급 : </dt>
					<dd>
						<label style="margin:0"><input type="radio" name="pay_type" id="joi1" value="300006" checked> 구직자통장송금</label>
						<label style="margin:0"><input type="radio" name="pay_type" id="joi2" value="300007" > 현장현금지급</label>
					</dd>
				</dl>
				<dl>
					<dt>나이제한 : </dt>
					<dd>
						<label style="margin:0"><input type="radio" name="work_age" id="age1" value="0" checked > 제한없음</label>
						<label style="margin:0"><input type="radio" name="work_age" id="age2" value="30" > 30 이하</label>
						<label style="margin:0"><input type="radio" name="work_age" id="age3" value="40" > 40 이하</label>
						<label style="margin:0"><input type="radio" name="work_age" id="age4" value="50" > 50 이하</label>
						<label style="margin:0"><input type="radio" name="work_age" id="age5" value="60" > 60 이하</label>
					</dd>
				</dl>
				<dl>
					<dt>혈압제한 : </dt>
					<dd>
						<label style="margin:0"><input type="radio" name="work_blood_pressure" id="bd1"  value="0"  checked> 제한없음</label>
						<label style="margin:0"><input type="radio" name="work_blood_pressure" id="bd2"  value="160"> 160 이하</label>
						<label style="margin:0"><input type="radio" name="work_blood_pressure" id="bd3"  value="150"> 150 이하</label>
						<label style="margin:0"><input type="radio" name="work_blood_pressure" id="bd4"  value="140"> 140 이하</label>
					</dd>
				</dl>
			</div>
			<div class="offerInfo04">
				<div class="offerInfo04pop">
					<p><strong>[필수]개인정보 수집 및 이용 동의</strong></p>
					<table class="jobTable">
						<tr>
							<td>목적</td>
							<td>항목</td>
							<td>보유기간</td>
						</tr>
						<tr>
							<td>이용자 식별 및 본인 여부 확인</td>
							<td>이름, 전화번호</td>
							<td>회원 탈퇴 후 5일까지</td>
						</tr>
						<tr>
							<td>구인신청 등 구인자 요청 처리 및 결과 회신</td>
							<td>상호명, 현장 주소</td>
							<td>회원 탈퇴 후 5일까지</td>
						</tr>
					</table>
					<p><br>※일가자 인력 서비스 제공을 위해서 필요한 최소한의 개인정보이므로 동의를 해주셔야 서비스를 이용하실 수 있습니다.</p>
					<ol>
						<span>닫기</span>
					</ol>
				</div>
<!-- 				<p class="referCheck"><label style="margin:0"><input type="checkbox" id="is_tax" name="is_tax" value="1"> 소개비 10% 면세계산서 요청</label></p> -->
<!-- 				<p class="refer clear"> -->
<!-- 					<label style="margin:0" class="tit1">계산서담당자</label> -->
<!-- 					<input type="text" id="tax_name"  name="tax_name"  placeholder="이름" class="con1"> -->
<!-- 					<label style="margin:0" class="tit2">연락처</label>  -->
<!-- 					<input type="text" id="tax_phone"  name="tax_phone" placeholder="핸드폰번호" class="con2 tel"> -->
<!-- 				</p> -->
<!-- 				<p class="agreeCheck"> -->
<!-- 					<label style="margin:0"><input type="checkbox" name="is_worker_info"  id="is_worker_info" value="1"> 작업자 노임대장/신분증/계좌번호 요청</label> -->
<!-- 				</p> -->
			</div>
			<div class="jobOfferBtn">
				<span class="apply" onClick="fucWorkProcess()">신청</span>
				<span class="close" onclick="closeWorkOffer();">닫기</span>
			</div>
		</div>
	</div>
	</div>
</form>		
<script type="text/javascript">
	$(function() {
		//동적이벤트 달기
	    $(document).on("click", ".jobList", function() {
    		fn_change_jobCalInit(1);
    	
    		$(".jobList").removeClass('on');
        	$(this).addClass('on');
	        
        	var jobSeq = $(this).attr("job_seq");
        	var jobName = $(this).attr("job_name");
        	var detailUseYn = $(this).attr("detail_use_yn");
	        
    	    jobSeqArray[0] = {jobSeq: jobSeq, jobName: jobName, detailUseYn: detailUseYn};
	    	
    		fn_change_twoJobList();
	    });
	
    	$(document).on("change", "input[name='jobDetail']", function() {
	    	fn_change_jobCalInit(2);
	    	
	    	var jobSeq = $("input[name='jobDetail']:checked").val();
	    	var jobName = $("input:radio[name=jobDetail]:checked").attr("job_name");
	
	    	jobSeqArray[1] = {jobSeq: jobSeq, jobName: jobName};
	    	
	    	fn_change_threeJobList();
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
	        
	        fn_change_complite(1);
	    });
   
	    $('#btnJobReg').click(function(){
	    	if(unitPrice > 0){
	    		fn_change_jobSubmit();
	    	}else{
	    		alert("직종을 선택 하세요.");
	    	}
	    });
    
	    $('#btnJobReset').click(function(){
	    	fn_change_jobSubmit();
	    });
	});

	function fn_change_jobSubmit(){
		var parentRowId = $("#parentRowId").val();
		var rowId = $("#rowId").val();
		
		//fn_setIlboFee(rowId, "ilbo_unit_price", unitPrice);
		
		//list_7638_t
		
		$("#list_"+parentRowId+"_t").jqGrid("setCell", rowId, "work_price",	unitPrice,	'',true);
		
		var params = {
			work_seq: rowId,
			work_kind_code: jobSeqArray[0].jobSeq,
			work_kind_name: jobSeqArray[0].jobName,
			work_job_detail_code: jobSeqArray[1].jobSeq,
			work_job_detail_name: jobSeqArray[1].jobName,
			work_job_add_code: jobSeqArray[2].jobSeq,
			work_job_add_name: jobSeqArray[2].jobName,
			work_price: unitPrice,
		}

	  	$.ajax({
			type: "POST",
	        url: "/admin/setOrderWorkInfo",
	       	data: params,
	   		dataType: 'json',
	    	success: function(data) {
				//새로고침을 안하기 위해서
	    		if(data.code == "0000"){
	    			$("#list_"+parentRowId+"_t").jqGrid("setCell", rowId, "work_kind_code",	unitPrice, '', true);
	    			
	    			$("#list_"+parentRowId+"_t").jqGrid("setCell", rowId, "work_kind_code",	jobSeqArray[0].jobSeq, '', true);
	    			$("#list_"+parentRowId+"_t").jqGrid("setCell", rowId, "work_kind_name",	jobSeqArray[0].jobName,	'', true);
	    	    	
	    			$("#list_"+parentRowId+"_t").jqGrid("setCell", rowId, "work_job_detail_code", jobSeqArray[1].jobSeq, '', true);
	    			$("#list_"+parentRowId+"_t").jqGrid("setCell", rowId, "work_job_detail_name", jobSeqArray[1].jobName, '', true);
	    	    	
	    			$("#list_"+parentRowId+"_t").jqGrid("setCell", rowId, "work_job_add_code", jobSeqArray[2].jobSeq, '', true);
	    			$("#list_"+parentRowId+"_t").jqGrid("setCell", rowId, "work_job_add_name", jobSeqArray[2].jobName, '', true);
	    			$("#list_"+parentRowId+"_t").jqGrid("setCell", rowId, "ilbo_unit_price", unitPrice,	'', true);
	    	    	
	    	    	$("#parentRowId").val("");
	    	    	$("#rowId").val("");
	    	    	
	    	    	closeIrPopup('popup-layer');
	    		}else{
	    			alert("오류가 발생 .");
	    		}
			},
	 		beforeSend: function(xhr) {
	        	xhr.setRequestHeader("AJAX", true);
	   			$(".wrap-loading").show();
			},
	      	error: function(e) {
				$(".wrap-loading").hide();
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

	function fn_change_jobCalInit(flag){
		if(flag == 0){
			totalOptionPrice = 0;
			
			jobSeqArray[0] = {jobSeq:null, jobName:null};
			jobSeqArray[1] = {jobSeq:null, jobName:null};
			jobSeqArray[2] = {jobSeq:null, jobName:null};
			
			fn_change_oneJobList();
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
/* jobList.js 에 정의 되어 있음 

function getAddPrice(jobSeq){
	var job = jobList.find(function(item){    
		  return (item.job_seq === jobSeq);
	}); 
			
	return job.basic_price *1;
}
 */
	function fn_change_oneJobList(){
		var oneDepthJobList = jobList.filter(function(item){    
			  return item.job_rank === "1";
		});  
		
		var _html = '';
				
		var jobKind = 0;
		
		var kindHtml1 = '<div class="cal_title">잡부조공</div>';
		var kindHtml2 = '<div class="cal_title">기술인력</div>';
		
		oneDepthJobList.forEach(function(job,index, array){
			var jobHtml = '<div class="jobList buttonPanel"  job_seq="' +job.job_seq + '" job_name="' +job.job_name + '" detail_use_yn="' + job.detail_use_yn + '" >'+ job.job_name +'</div>';
			
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

	function fn_change_twoJobList(){
		var jobSeq		= jobSeqArray[0].jobSeq;
		
		var twoJobSeq = 0;
		
		var twoDepthJobList = jobList.filter(function(item){    
			  return (item.job_code === jobSeq && item.job_rank ==2);
		});  
		
	// 	if(twoDepthJobList.length > 0){
		if(jobSeqArray[0].detailUseYn == "1"){
			var _html = '';
			
			twoDepthJobList.forEach(function(job,index, array){
	            _html += '<input type="radio" class="radioBasic" id="twoDepthJob'+ index+'" name="jobDetail" value="'+ job.job_seq+'" job_name="'+ job.job_name+'"><label for="twoDepthJob'+ index+'" class="radioLabel" >'+ job.job_name +'</label><br>';
			});
			
			$(".jobDetailArea").html(_html);
		}else{
			jobSeqArray[1] = {jobSeq:null, jobName:null};
			jobSeqArray[2] = {jobSeq:null, jobName:null};
			
			fn_change_complite(0);	
		}
	}

	function fn_change_threeJobList(){
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
		
		fn_change_complite(1);
	}

	function fn_change_complite(index){
		var jobSeq = jobSeqArray[index].jobSeq; 
	
		var lastJob = jobList.find(function(item){    
			  return (item.job_seq === jobSeq);
		});
		
		var parentRowId = $("#parentRowId").val();
			
		var workArrival = getTimeNumber($("#list").jqGrid('getRowData', parentRowId).work_arrival);
		var workFinish = getTimeNumber($("#list").jqGrid('getRowData', parentRowId).work_finish);
			
		var price = fn_getPrice(workArrival, workFinish, lastJob.basic_price, lastJob.short_price, lastJob.short_price_night, lastJob.add_day_time, lastJob.add_night_time);
	
		unitPrice = price + totalOptionPrice;
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

	function employerNumCheck(regNo){
		// 숫자체크
		var regex = /[^0-9]/g;
		var result = !regex.test(regNo);
		var msg = regex.test(regNo) ? " - 숫자만 입력가능합니다." : "";
		if(!result)return [result, msg];

		// 사업자번호 or 주민번호 유효성 체크
		var validCheck = regNo.length == 13 ? (ssnCheck(regNo) ? [true, ""] : [false, "유효한 주민번호가 아닙니다."])
				: regNo.length == 10 ? validBizID(regNo) : [false, " - 숫자 10자리(사업자번호) 또는 13자리(주민번호)만 가능합니다."];
		if(!validCheck[0]) return validCheck;


		// 중복 체크
		var repeatCheck = [true, ""];
		$.ajax({
			type : "POST",
			url	: "/admin/getEmployerByEmployerNum",
			data : {"employer_num":regNo},
			dataType : 'json',
			async:false,
			success	: function(data) {
				if( data && data.employer_seq != '0' ) repeatCheck = [false, "중복된 사업자번호 또는 주민번호 입니다."];
			},
			beforeSend: function(xhr) {
				$(".wrap-loading").show();
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				$(".wrap-loading").hide();
			},
			complete: function(e) {
				$(".wrap-loading").hide();
			}
		});
		return repeatCheck;
	}

	function employerNumFomat(cellvalue, options, rowObject) {
		if ( cellvalue == "" || cellvalue == null) {
			return "";
		} else {
			return cellvalue.length == 13 ? formatRegNo(cellvalue, options, rowObject) :
					cellvalue.length == 10 ? formatRegNum(cellvalue, options, rowObject) : cellvalue;
		}
	}

	function workLatlngFormat(cellvalue, options, rowObject) {
		if(rowObject.work_latlng_count == 0 || rowObject.order_state != '2' || sessionId != rowObject.mod_admin)
			return rowObject.work_latlng;
		else
			return '<a onClick="javascript:workList('+rowObject.order_seq+','+rowObject.work_latlng+')">'+cellvalue+'</a>';
	}

	function workList(id, workLatlng_a, workLatlng_b) {
		$(".wrap-loading").show();
		var workLatlng = workLatlng_a + ',' + workLatlng_b;
		var e = this.event;
		var msg = '<table summary="같은 현장 목록">';
		msg += '<caption>같은 현장 목록</caption>';
		msg += '<thead>';
		msg += '<tr>' +
				'	<th style="border: solid 1px;">현장</th>' +
				'</tr>';
		msg += '<tbody>';

		$.ajax({
			type: "POST",
			url: "/admin/getWorkNameList",
			async: false,
			data: {"work_latlng": workLatlng},
			dataType: 'json',
			success: function(data) {
				$("#list").jqGrid("setSelection", id);
				nowWorkList = data;
				$.each(data, function(index, item){
					msg += '<tr onClick="javascript:selectWork('+id+','+ item.work_seq +');" class="offMouse"' +
							' onmouseover="this.className=\'onMouse\'" onmouseout="this.className=\'offMouse\'">';
					msg += '	<td style="border: solid 1px;">'+item.label+'</td>';
					msg += '</tr>';
				});

				msg += "</tbody>";
				msg += "</table>";
				msg += "<div id=\"companion_"+ id +"\" class=\"bt_wrap single\" style=\"width: 240px; display: none; background-color: #fff;\">";
				msg += clsHTML;
				msg += "</div>";

				var $opt_layer = $('#opt_layer');
				var _display;
				var pos = abspos(e);
				var optId = "companion_"+ id;
				if ( $("#"+ optId).css("display") == null || $("#"+ optId).css("display") == "undefined" ) {
					writeOpt2(msg);
				}
				$opt_layer.find('.bt_wrap').css('display', 'none');
				_display = $opt_layer.css('display') == 'none'?'block':'none';
				$opt_layer.css('left', (pos.x - 100) +"px");
				$opt_layer.css('top', (pos.y-20) +"px");
				$opt_layer.css('display', _display);
				$("#"+ optId).css('display', _display);
				$(".wrap-loading").hide();
			},
			beforeSend: function(xhr) {
				$(".wrap-loading").show();
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				$(".wrap-loading").hide();
			},
			complete: function(e) {
				$(".wrap-loading").hide();
			}
		});
	}

	function selectWork(rowid, workSeq){
		var workMap = nowWorkList.filter(e => e.work_seq == workSeq)[0];
		Object.keys(workMap).forEach(k => {
			if(workMap[k] == null || workMap[k] == '' || k == 'paging'){
				delete workMap[k];
			}
		});

		if(workMap && workMap.work_seq > 0) {
			$.ajax({
				type: "POST",
				url: "/admin/setOrderInfo",
				async: false,
				data: {
					'order_seq':rowid,
					'employer_seq':workMap.employer_seq,
					'employer_name':workMap.employer_name,
					'employer_num':workMap.employer_num,
					'work_seq':workMap.work_seq,
					'work_name':workMap.work_name,
					'work_addr':workMap.work_addr,
					'work_latlng':workMap.work_latlng,
					'labor_contract_seq':workMap.labor_contract_seq,
					'receive_contract_seq':workMap.receive_contract_seq
				},
				dataType: 'json',
				success: function(data) {
					nowWorkList = [];
					$(".wrap-loading").hide();
					$("#list").trigger("reloadGrid");
				},
				beforeSend: function(xhr) {
					$(".wrap-loading").show();
					xhr.setRequestHeader("AJAX", true);
				},
				error: function(e) {
					$(".wrap-loading").hide();
				},
				complete: function(e) {
					closeOpt();
					$(".wrap-loading").hide();
				}
			});
		}
	}
</script>