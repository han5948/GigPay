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
<script type="text/javascript">
	//윈도우 사이즈가 변할때 높이 넓이
	$(window).resize(function() {
	    var reWidth = $(window).width() -optWidth;
	    if(reWidth < baseWidth) reWidth = baseWidth;
	    
	    var reHeight = $(window).height() -optHeight;
	    if(reHeight < baseHeight) reHeight = baseHeight;
	    
	    $("#list").setGridWidth(reWidth) ;
	    $("#list").setGridHeight(reHeight);
	});
	
	isSelect = false;
	
	$(function() {
	  	var lastsel;            //편집 sel
	
	  	// jqGrid 생성
	  	$("#list").jqGrid({
			url: "/admin/getEDocumentList",
			datatype: "json",                               // 로컬그리드이용
			mtype: "POST",
			postData: {
				srh_use_yn: 1,
				page: 1
			},
			//sortname: "",
			//sortorder: "",
			rowList: [25, 50, 100],                         // 한페이지에 몇개씩 보여 줄건지?
			//height: jqHeight,                               // 그리드 높이
			width: jqWidth - 200,
			scrollerbar: true,               
			//autowidth: true,
			rowNum: 50,
			pager: '#paging',                            // 네비게이션 도구를 보여줄 div요소
			viewrecords: true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무
			multiselect: true,
			multiboxonly: true,
			caption: "전자문서 관리 목록",                 // 그리드타이틀
			rownumbers: true,                                 // 그리드 번호 표시
			rownumWidth: 40,                                   // 번호 표시 너비 (px)
			cellEdit: false,
			jsonReader: {
				id: "management_seq",
				repeatitems: false
			},
			sortable: true,
	        // 컬럼명
			colNames: ['카테고리 코드','카테고리 이름', '연동여부', '가맹점', '문서 번호', '서명상태', '가맹점', '대표자', '핸드폰', '서명 상태', '가맹점', '구직자'
				, '핸드폰', '서명 상태', '가맹점', '구인처', '매니저', '핸드폰', '서명 상태', '최초 생성일시', '모든 서명 완료 일시'],
	        // 컬럼별 속성 및 값
			colModel: [
				{name: 'category_code', index: 'category_code', hidden: true},
				{name: 'category_name', index: 'category_name', align: "center", width: 100, search: false, editable: false, sortable: true},
				{name: 'ilbo_use_yn', index: 'ilbo_use_yn', align: "center", width: 100, search: false, editable: false, sortable: true, formatter: fn_ilboUse},
				{name: 'company_name', index: 'i.company_name', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}, editable: false, sortable: true},
				{name: 'doc_number', index: 'doc_number', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}, editable: false, sortable: true},
				{name: 'sign_flag', index: 'sign_flag', align: "center", width: 100, search: false, editable: false, sortable: true, formatter: fn_signComplete},
				{name: 'sign_company_name', index: 'ERM;!;o.company_name', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
					, editable: false, sortable: true},
				{name: 'company_owner', index: 'ERM;!;company_owner', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}, editable: false, sortable: true},
				{name: 'company_phone', index: 'ERM;!;company_phone', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
					, editable: false, sortable: true, formatter: formatPhoneStar},
				{name: 'company_sign_flag', index: 'company_sign_flag', align: "center", width: 100, search: false, editable: false, sortable: true, formatter: fn_signFlag},
				{name: 'worker_company_name', index: 'worker_company_name', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
					, editable: false, sortable: true},
				{name: 'ilbo_worker_name', index: 'ilbo_worker_name', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
					, editable: false, sortable: true},
				{name: 'ilbo_worker_phone', index: 'ilbo_worker_phone', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
					, editable: false, sortable: true, formatter: formatPhoneStar},
				{name: 'worker_sign_flag', index: 'worker_sign_flag', align: "center", width: 100, search: false, editable: false, sortable: true, formatter: fn_signFlag},
				{name: 'work_company_name', index: 'ELM;!;work_company_name', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
					, editable: false, sortable: true},
				{name: 'employer_name', index: 'ELM;!;employer_name', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
					, editable: false, sortable: true},
				{name: 'manager_name', index: 'ELM;!;manager_name', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
					, editable: false, sortable: true},
				{name: 'manager_phone', index: 'ELM;!;manager_phone', align: "center", width: 100, search: true, searchoptions: {sopt: ['cn', 'eq', 'nc']}
					, editable: false, sortable: true, formatter: formatPhoneStar},
				{name: 'work_sign_flag', index: 'work_sign_flag', align: "center", width: 100, search: false, editable: false, sortable: true, formatter: fn_signFlag},
				{name: 'reg_date', index: 'reg_date', align: "left", width: 60, search: false, editable: false, sortable: true },
				{name: 'complete_date', index: 'complete_date', align: "left", width: 60, search: false, editable: false, sortable: true }
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
	        	var sessionCompanySeq = '${sessionScope.ADMIN_SESSION.company_seq }';
	        	var sessionAuthLevel = '${sessionScope.ADMIN_SESSION.auth_level }';
	        	var sessionAdminSeq = '${sessionScope.ADMIN_SESSION.admin_seq }';
	        	
	        	//row data 전체 가져 오기
	            var list = $("#list").jqGrid('getRowData', rowid);
	        	
				lastsel = rowid;
			},
	    	// submit 전
	    	beforeSubmitCell: function(rowid, cellname, value) {
				return {admin_seq: rowid, company_seq: $("#list").jqGrid('getRowData', rowid).company_seq};
			},
			afterSubmitCell: function(res, i, a, b, c, d, e) {
			
			},
	        // Grid 로드 완료 후
	        loadComplete: function(data) {
				isGridLoad = true;
			},
			afterInsertRow: function(rowId, rowData, rowElem) {
				//var category_name = $("#list").jqGrid("getCell", rowId, "category_name");
				var category_code = $("#list").jqGrid("getCell", rowId, "category_code");
				
				//if(category_name == '소개요금대리수령동의서') {
				if(category_code == 'ERM') {
					$("#list").jqGrid("setCell", rowId, "work_company_name", null, "", true);
					$("#list").jqGrid("setCell", rowId, "work_company_name", '', {'background' : '#F2F2F2'});
					$("#list").jqGrid("setCell", rowId, "employer_name", null, "", true);
					$("#list").jqGrid("setCell", rowId, "employer_name", '', {'background' : '#F2F2F2'});
					$("#list").jqGrid("setCell", rowId, "manager_name", null, "", true);
					$("#list").jqGrid("setCell", rowId, "manager_name", '', {'background' : '#F2F2F2'});
					$("#list").jqGrid("setCell", rowId, "manager_phone", null, "", true);
					$("#list").jqGrid("setCell", rowId, "manager_phone", '', {'background' : '#F2F2F2'});
					$("#list").jqGrid("setCell", rowId, "work_sign_flag", null, "", true);
					$("#list").jqGrid("setCell", rowId, "work_sign_flag", '', {'background' : '#F2F2F2'});
				//}else {
				}else if(category_code == 'ELM'){	
					$("#list").jqGrid("setCell", rowId, "sign_company_name", null, "", true);
					$("#list").jqGrid("setCell", rowId, "sign_company_name", '', {'background' : '#F2F2F2'});
					$("#list").jqGrid("setCell", rowId, "company_owner", null, "", true);
					$("#list").jqGrid("setCell", rowId, "company_owner", '', {'background' : '#F2F2F2'});
					$("#list").jqGrid("setCell", rowId, "company_phone", null, "", true);
					$("#list").jqGrid("setCell", rowId, "company_phone", '', {'background' : '#F2F2F2'});
					$("#list").jqGrid("setCell", rowId, "company_sign_flag", null, "", true);
					$("#list").jqGrid("setCell", rowId, "company_sign_flag", '', {'background' : '#F2F2F2'});
				}
			},
	      	loadBeforeSend: function(xhr) {
				isGridLoad = false;
				optHTML = "";
				closeOpt();
	
				xhr.setRequestHeader("AJAX", true);
			},
	        loadError: function(xhr, status, error) {
				if ( xhr.status == "901" ) {
					location.href = "/admin/login";
				}
			},
	        beforeSelectRow: selectRowid
		});
	   
	  	$("#list").jqGrid('navGrid', "#paging", {edit: false, add: false, del: false, search: false, refresh: false, position: 'left'});
	
	  	$("#list").jqGrid('filterToolbar', {searchOperators : true});
	  
	  	$("#list").jqGrid('setGroupHeaders', {
			useColSpanStyle: true,
			groupHeaders:[
				{ startColumnName: 'ilbo_use_yn', numberOfColumns: 2, titleText: '<div style="margin: 0; padding: 0; width: 100%; border-spacing: 0px;">일일대장</div>' }
				,{ startColumnName: 'sign_company_name', numberOfColumns: 4, titleText: '<div style="margin: 0; padding: 0; width: 100%; border-spacing: 0px; background-color: #92DE9F">가맹점</div>'}
			    ,{ startColumnName: 'worker_company_name', numberOfColumns: 4, titleText: '<div style="margin:0; padding: 0; width: 100%; border-spacing: 0px; background-color: #A9D1FF">구직자</div>'}
			    ,{ startColumnName: 'work_company_name', numberOfColumns: 5, titleText: '<div style="margin:0; padding: 0; width: 100%; border-spacing: 0px; background-color: #FFD18C">구인자</div>'}
			]
		});
	  	
	  	// 검색
	  	$("#btnSrh").click( function() {
	    	// 검색어 체크
	    	$("#list").setGridParam({
	          	page: 1,
	        	postData: {
	      			start_reg_date: $("#start_reg_date").val(),
	        		end_reg_date: $("#end_reg_date").val(),
	            	srh_type: $("#srh_type option:selected").val()
	        	}
	    	}).trigger("reloadGrid");
	
	    	lastsel = -1;
	  	});
	  	
	  	// pdf 다운로드
	  	$("#btnDownload").on("click", function() {
	  		var myForm = $('form[name=pdfDownForm]');
	  		var url = "https://s.ilgaja.com/admin/pdfDown";
	  		//var url = "http://192.168.0.145:18086/admin/pdfDown";
	  		//var url = "http://211.253.27.183:8181/sign/admin/pdfDown";
	    	
	  		myForm.attr("action", url); 
	  		
	  		myForm.submit();
	  	});
	});

	// 일보 연동 여부 확인 formatter
	function fn_ilboUse(cellvalue, options, rowObject) {
		if(rowObject.ilbo_seq > 0) {
			return "Y";
		}else {
			return "N";
		}
	}
	
	// 서명 대상자 서명 상태 formatter
	function fn_signFlag(cellvalue, options, rowObject) {
		if(cellvalue == '0') {
			return "대기";
		}else if(cellvalue == '1') {
			return "완료";
		}else {
			return "";
		}
	}
	
	// 문서 서명 상태 formatter
	function fn_signComplete(cellvalue, options, rowObject) {
		var companySign = rowObject.company_sign_flag * 1;
		var workerSign = rowObject.worker_sign_flag * 1;
		var workSign = rowObject.work_sign_flag * 1;
		var returnText = '';
		
		if(companySign + workerSign + workSign > 1) {
			returnText += "<a style='color: #1969C3; font-weight: bold; text-decoration: underline;' href='javascript:void(0);' onclick='fn_signHistory(" + JSON.stringify(rowObject) + ")'>완료</a>";
		}else if(companySign + workerSign + workSign == 1) {
			returnText += "<a style='color: #64CD3C; font-weight: bold; text-decoration: underline;' href='javascript:void(0);' onclick='fn_signHistory(" + JSON.stringify(rowObject) + ")'>서명중(1/2)</a>";
		}
		
		return returnText;
	}
	
	function isSuperAdmin(){
		var authLevel = '${sessionScope.ADMIN_SESSION.auth_level }'; 
		
		return authLevel == 0;
	}
	
	function permissionReceiveContract(cotegoryCode, workerCompanySeq){
		var companySeq = '${sessionScope.ADMIN_SESSION.company_seq }';
		
		if( isSuperAdmin() ){
			return true;
		}
		
		return cotegoryCode != 'ERM' || companySeq == workerCompanySeq;
	}
	
	// 문서 이력보기 layer popup
	function fn_signHistory(rowObject) {
		//var companySeq = '${sessionScope.ADMIN_SESSION.company_seq }';
		var _categoryCode = rowObject.category_code;
		var _workerCompanySeq = rowObject.worker_company_seq;
		if(!permissionReceiveContract(_categoryCode, _workerCompanySeq)){
			return false;
		}
		
		var params = {
			management_seq : rowObject.management_seq
		};
		
		$("#popupManagementSeq").val(rowObject.management_seq);
		$("#management_seq").val(rowObject.management_seq);
		
		$.ajax({
			type : "POST",
			url	: "/admin/getHistoryList",
			data : params,
			dataType: 'json',
			success	: function(data) {
				$("#btnDownload").hide();

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
				
				openEDocPopup('popup-layer');
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
	
	function openEDocPopup(popupId, status) {
		  //popupId : 팝업 고유 클래스명
		  var $popUp = $('#'+ popupId);

		  $dimmed.fadeIn();
		  //$popLayer.hide();
		  $popUp.show();

		  var innerWidth = window.innerWidth;
		  var innerHeight = window.innerHeight;
		  var offsetWidth = -$popUp.width() / 2 ;
		  var offsetHeight = -$popUp.height() / 2 ;
		  
		  $popUp.css({
		    'margin-top': offsetHeight,
		  });
		  
		  $popUp.css({top: '50%', left: '5%'});
	}
	
	function fn_phoneFormat(value) {
		var pattern = /^(\d{2,3})(\d{3,4})(\d{4})$/;
				
		if(!value) {
			value = '';
		}
				
		var result = value.replace(pattern, '$1-$2-$3');
		
		return result;
	}
	
	//전화번호 
	function formatPhoneStar(cellvalue, options, rowObject, a, b) {
		var phoneNo = cellvalue;
		var sessionCompanySeq = '${sessionScope.ADMIN_SESSION.company_seq }';
		var authLevel = '${sessionScope.ADMIN_SESSION.auth_level }';
		var RegNotNum  = /[^0-9]/g;
		var cellName = options.colModel.name;
		
		// return blank
		if ( phoneNo == "" || phoneNo == null ) return "";
		
		// delete not number
		phoneNo = phoneNo.replace(RegNotNum,'');

		if(cellName == 'ilbo_worker_phone') {
			if(sessionCompanySeq != 0 && rowObject.worker_company_seq != sessionCompanySeq && authLevel != "0") {
				return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-****");
			}else{
				return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
			}
		}else if(cellName == 'company_phone') {
			if(sessionCompanySeq != 0 && rowObject.company_seq != sessionCompanySeq && authLevel != "0") {
				return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-****");
			}else{
				return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
			}
		}else if(cellName == 'manager_phone') {
			if(sessionCompanySeq != 0 && rowObject.work_company_seq != sessionCompanySeq && authLevel != "0") {
				return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-****");
			}else{
				return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
			}
		}
	}
</script>
	<article>
		<div class="inputUI_simple">
      		<table class="bd-form s-form" summary="최초생성일시, 카테고리 검색 영역 입니다.">
        		<caption>최초생성일시, 카테고리 검색 영역</caption>
        		<colgroup>
          			<col width="130px" />
          			<col width="700px" />
          			<col width="120px" />
          			<col width="" />
        		</colgroup>
        		<tr>
          			<th scope="row">최초생성일시</th>
          			<td colspan="3">
            			<p class="floatL">
              				<input type="text" id="start_reg_date" name="start_reg_date" readonly class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>
              				<input type="text" id="end_reg_date" name="end_reg_date" readonly class="inp-field wid100 datepicker" />
            			</p>
            			<div class="inputUI_simple">
            				<span class="contGp btnCalendar">
              					<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'all'   ); $('#btnSrh').trigger('click');" checked="checked" /><label for="day_type_1" class="label-radio on">전체</label>
              					<input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'month' ); $('#btnSrh').trigger('click');" /><label for="day_type_4" class="label-radio">1개월</label>
              					<input type="radio" id="day_type_5" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '3month'); $('#btnSrh').trigger('click');" /><label for="day_type_5" class="label-radio">3개월</label>
              					<input type="radio" id="day_type_6" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '6month'); $('#btnSrh').trigger('click');" /><label for="day_type_6" class="label-radio">6개월</label>
            				</span>
            				<span class="tipArea colorN02">* 직접 기입 시, ‘2017-06-28’ 형식으로 입력</span>
            			</div>
          			</td>
          			<th scope="row" class="linelY pdlM">카테고리선택</th>
          			<td>
            			<div class="select-inner">
            				<select id="srh_type" name="srh_type" class="styled02 floatL wid138" onChange="">
              					<option value="">전체</option>
              					<c:forEach items="${categoryList }" var="list">
              						<option value="${list.category_seq }">${list.category_name }</option>
              					</c:forEach>
            				</select>
            			</div>
            			<div class="btn-module floatL mglS">
              				<a href="#none" id="btnSrh" class="search">검색</a>
            			</div>
          			</td>
        		</tr>
      		</table>
      	</div>
	</article>
    <table id="list"></table>
    <div id="paging"></div>
    
	<div id="popup-layer" style="width:1600px" class="employer">
		<input type="hidden" id="popupManagementSeq" value="">
		<form id="frmPopup" name="frmPopup" method="post">
  			<header class="header">
    			<h1 class="tit">문서 이력</h1>
    			<a class="close" href="javascript:closeIrPopup('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
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
<%--      					<div class="rightGroup"><a href="#popup-layer" id="btnDownload" class="btnStyle01">다운로드</a></div>--%>
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
<script type="text/javascript">
//<![CDATA[
$(window).load(function() {
	setDayType('start_reg_date', 'end_reg_date', 'all');
});
//]]>
</script>