<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="<c:url value="/resources/cms/js/paging.js" />" charset="utf-8"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	var waitApprovalList;
	
	$(function() {
		$(".datepicker").datepicker();
		$(".datepicker").datepicker('setDate', new Date());
		
		//메인페이지 공지사항
		var _data = {
			use_yn : '1',
			del_yn : '0'
		};
    	var _url = "<c:url value='/admin/main/getMainNoticeList' />";
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var noticeList = data.noticeList; 
    			var totalCnt = data.totalCnt;
    			var noticeDTO = data.noticeDTO;
    			var text = '';
    			
    			$("#tbody").empty();
    			
    			if(noticeList == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="8">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    			}else {
    				for(var i = 0; i < noticeList.length; i++) {
    					var count = totalCnt - i;
	    				text += '<tr>';
	    				text += '<td>' + count + '</td>';
	    				text += '	<td>';
	    				text += '		<a href="javascript:void(0);" onclick="fn_edit(' + noticeList[i].notice_seq + ')">';
	    				text += 			noticeList[i].notice_title;
	    				text += '		</a>';
	    				text += '	</td>';
	    				text += '	<td>' + noticeList[i].notice_writer + '</td>';
	    				text += '	<td>' + noticeList[i].mod_admin + '</td>';
	    				text += '	<td>' + noticeList[i].auth_company_name + '</td>';
	    				text += '	<td>' + noticeList[i].reg_date + '</td>';
	    				text += '</tr>';
    				}
    			}
    			$("#tbody").append(text);
    		}else {
    			toastFail("오류가 발생했습니다.3");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
    	
    	//알림내역==============================================
    	var _data = {
			use_yn : '1',
			del_yn : '0'
	    };
	    	
    	var _url = "<c:url value='/admin/main/getMainNotificationList' />";
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			setNotificationTable(data);
    		}else {
    			toastFail("오류가 발생했습니다.3");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
    	
    	//프로시저 로그 ===========================================
    	var _adminId = "${sessionScope.ADMIN_SESSION.admin_id}";
    	if( _adminId == "nemojjang" || _adminId == "yunsoo" || _adminId == "gj" ){
    		var _procData = {
   	   			sub_day : 10
   	    	}
   	    	var _url = "<c:url value='/admin/main/getProcedureList' />";
   	    	commonAjax(_url, _procData, true, function(data) {
   	    		if(data.code == "0000") {
   	    			setProcedureTable(data);
   	    		}else {
   	    			toastFail(data.message);
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
	    
    	//selectbox 회사목록
    	var _url = "<c:url value='/admin/main/getCompanyList' />";
    	commonAjax(_url, null, true, function(data) {
    		if(data.code == "0000") {
    			drawSelectBox(data.companyList);
    		}else {
    			toastFail("오류가 발생했습니다.3");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
    	
    	// 오더 종류 통계=================================================
		tDate = getTenDayAgoDate();
		_data = {
			ilbo_date : tDate,
		};
    	_url = "<c:url value='/admin/main/getOrderStatisticsList' />";
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var orderStatisticsList = data.orderStatisticsList; 
    			var totalCnt = data.totalCnt;
    			var orderStatisticsDTO = data.orderStatisticsDTO;
    			
    			setOrderStatisticsTable(orderStatisticsList);
    		}else {
    			toastFail("오류가 발생했습니다.3");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    		setOrderStatisticsDonutChart(0);
    	});
    	
    	//구직자 소속기준 공개여부 테이블 =====================================================
    	_url = "<c:url value='/admin/main/getUseStatisticsList' />";
    	_data = {
    		ilbo_date : tDate,
    		company_flag : "0"
    	}
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var useStatisticsList = data.useStatisticsList; 
    			var useStatisticsDTO = data.useStatisticsDTO;
    			setUseStatisticsTable(useStatisticsList,"worker-company-use-body", useStatisticsDTO.company_flag);
    		}else {
    			toastFail("오류가 발생했습니다.3");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    		setUseStatisticsDonutChart(0, "0");
    	});
    	//구인처 소속기준 공개여부 테이블
    	_url = "<c:url value='/admin/main/getUseStatisticsList' />";
    	_data = {
    		ilbo_date : tDate,
    		company_flag : "1"
    	}
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var useStatisticsList = data.useStatisticsList; 
    			var useStatisticsDTO = data.useStatisticsDTO;
    			
    			setUseStatisticsTable(useStatisticsList,"company-use-body",useStatisticsDTO.company_flag);
    		}else {
    			toastFail("오류가 발생했습니다.3");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    		setUseStatisticsDonutChart(0, "1");
    	});
    	
    	if( "${sessionScope.ADMIN_SESSION.company_seq}" == 13 ){
	    	// 구직자 앱 승인대기 목록
	    	_url = "<c:url value='/admin/main/getWaitApprovalList' />";
	    	commonAjax(_url, null, true, function(data) {
	    		if(data.code == "0000") {
	    			waitApprovalList = data.waitApprovalList; 
	    			
	    			//차트
	    			google.charts.load("current", {packages:["corechart"]});
	    	        google.charts.setOnLoadCallback(drawChart);
	    	        
	    	        function drawChart() {
	    	        	var data = new google.visualization.DataTable();
	    	        	data.addColumn("string", "지점");
	    	        	data.addColumn("number", "승인대기");
	    	        	for(i=0; i<waitApprovalList.length; i++){
	    	        		data.addRow([waitApprovalList[i].company_name, waitApprovalList[i].wait_approval_count*1]);
	    	        	}
	    	          	var options = {
	    	            	pieHole: 0.3,
	    	            	fontSize:14,
	    	            	titleTextStyle:{
	    	            		fontSize: 16,
	    	            		bold: true,
	    	            		fontName: "notokr-regular",
	    	            	},
	    	            	chartArea:{
	    	            		width: "100%",
	    	            		height: "50%"
	    	            	},
	    	            	pieSliceText : "value"
	    	          	};
	    	          	var chart = new google.visualization.PieChart(document.getElementById('wait-approval-chart'));
	    	          	chart.draw(data, options);
	    	        }
	    	        //테이블
	    	        var pageSize = 10;
	    	        setApprovalTable(1, pageSize);
	    		}else {
	    			toastFail("오류가 발생했습니다.3");
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
		
    	// issueList ==============================================================================
		$.ajax({
			type: "POST",
			url: "<c:url value='/admin/main/getIssueList' />",
			dataType: "json",
			beforeSend: function(xhr){
    			xhr.setRequestHeader("AJAX", true);
    			$(".wrap-loading").show();
    		},
    		success : function(data) {
    			// 성공 시
    			if(data.code == "0000") {
					setIssueTable(data, '.paging1');    				
	    		}else {
	    			toastFail("오류가 발생했습니다.3");
	    		}
    		},
    		error : function(data) {
    			// 오류 발생
    			toastFail("오류가 발생했습니다.3");
    		},
    		complete : function() {
    			// 요청 완료 시
    			$(".wrap-loading").hide();
    		}
		});

    	$.ajax({
    		type: "POST",
    		url:"<c:url value='/admin/main/getCityList' />",
    		data: null,
    		async: "sync",
    		dataType: "json",
    		beforeSend: function(xhr){
    			xhr.setRequestHeader("AJAX", true);
    			$(".wrap-loading").show();
    		},
    		success : function(data) {
    			// 성공 시
    			if(data.code == "0000") {
	    			var cityList = data.cityList;
	    			var text= "";
	    			$("#srh-city").empty();
	    			if( cityList.length < 1 ){
	    				text += '<option value="">없음</option>';
	    			}
	   				for(var i=0; i<cityList.length; i++){
	   					text += '<option value='+ cityList[i].city_code + '>' + cityList[i].city_name +'</option>';
	   				}
	    			
	   				$("#srh-city").append(text);
	    		}else {
	    			toastFail("오류가 발생했습니다.3");
	    		}
    		},
    		error : function(data) {
    			// 오류 발생
    			toastFail("오류가 발생했습니다.3");
    		},
    		complete : function() {
    			// 요청 완료 시
    			$(".wrap-loading").hide();
    		}
    	}).done(function(){
    		$.ajax({
        		type: "POST",
        		url:"<c:url value='/admin/main/getWeather' />",
        		data: {
        			city_code : $("select[name=srh_city]").val(),
        			fcst_time : getNearTime(),
        			fcst_date : getformatDate()
        		},
        		async: "sync",
        		dataType: "json",
        		beforeSend: function(xhr){
        			xhr.setRequestHeader("AJAX", true);
        			$(".wrap-loading").show();
        		},
        		success : function(data) {
        			// 성공 시
        			if(data.code == "0000") {
        				setWeatherData(data);
		    		}else {
		    			toastFail("오류가 발생했습니다.3");
		    		}
        		},
        		error : function(data) {
        			// 오류 발생
        			toastFail("오류가 발생했습니다.3");
        		},
        		complete : function() {
        			// 요청 완료 시
        			$(".wrap-loading").hide();
        		}
        	});
    	});
    	
    	$("#insertBtn").on("click", function() {
    		var duty_type = '';
    		var company_seq = '${sessionScope.ADMIN_SESSION.company_seq }';
    		
    		if(company_seq == '13') {
    			duty_type = $("#duty_type").val();
    		}
    		
    		if($("#issue_date").val() == '') {
    			alert("날짜를 선택해주세요.");
    			
    			return false;
    		}
    		
    		if($("#issue_text").val() == '') {
    			alert("이슈 내용을 입력해주세요.");
    			
    			return false;
    		}
    		
			$.ajax({
				type: "POST",
				url: "<c:url value='/admin/main/insertIssue' />",
				data: {
					duty_type : duty_type,
					issue_date : $("#issue_date").val(),
					issue_text : $("#issue_text").val()
				},
				dataType: "json",
				beforeSend: function(xhr){
        			xhr.setRequestHeader("AJAX", true);
        			$(".wrap-loading").show();
        		},
        		success : function(data) {
        			// 성공 시
        			if(data.code == "0000") {
        				setIssueTable(data, ".paging1");
        				
        				$("#duty_type").val('0');
        				$(".datepicker").datepicker('setDate', new Date());
        				$("#issue_text").val('');
		    		}else {
		    			toastFail("오류가 발생했습니다.3");
		    		}
        		},
        		error : function(data) {
        			// 오류 발생
        			toastFail("오류가 발생했습니다.3");
        		},
        		complete : function() {
        			// 요청 완료 시
        			$(".wrap-loading").hide();
        		}
			});
		});
    	
    	// 실시간 출역 현황
    	timer = setTimeout( function () {
			//----------------------------------------------------------------------------------
			$.ajax({
				type: "POST",
				url: "<c:url value='/admin/main/geStatusSum' />",
				data: {
					ilbo_date : $("#ilbo_date").val(),
					company_seq : $("#company_seq").val()
				},
				dataType: "json",
				beforeSend: function(xhr){
	    			xhr.setRequestHeader("AJAX", true);
	    		},
	    		success : function(data) {
	    			// 성공 시
	    			if(data.code == "0000") {
	    				const option = {
							maximumFractionDigits: 4
						};
	    						
	    				$("#readySum").text(data.ouputStatus.readySum.toLocaleString('ko-KR', option));
	    				$("#reserveSum").text(data.ouputStatus.reserveSum.toLocaleString('ko-KR', option));
	    				$("#startSum").text(data.ouputStatus.startSum.toLocaleString('ko-KR', option));
	    				$("#arriveSum").text(data.ouputStatus.arriveSum.toLocaleString('ko-KR', option));
	    				$("#complteSum").text(data.ouputStatus.complteSum.toLocaleString('ko-KR', option));
	    				$("#zzemSum").text(data.ouputStatus.zzemSum.toLocaleString('ko-KR', option));
	    				$("#punkSum").text(data.ouputStatus.punkSum.toLocaleString('ko-KR', option));
	    				$("#daemaSum").text(data.ouputStatus.daemaSum.toLocaleString('ko-KR', option));
	    				
	    				var totalWorkSum = data.assignWork.assignWorkSum + data.notAssignWork.workSum;
	    				$("#totalWorkSum").text(totalWorkSum.toLocaleString('ko-KR', option));
	    				$("#assignWorkSum").text(data.assignWork.assignWorkSum.toLocaleString('ko-KR', option));
	    				$("#assignCancelSum").text(data.assignWork.assignCancelSum.toLocaleString('ko-KR', option));
	    				
	    				$("#workSum").text(data.notAssignWork.workSum.toLocaleString('ko-KR', option));
	    				$("#cancelSum").text(data.notAssignWork.cancelSum.toLocaleString('ko-KR', option));
		    		}else {
		    			toastFail("실시간 출력현황 오류가 발생했습니다.");
		    		}
	    		},
	    		error : function(data) {
	    			// 오류 발생
	    			toastFail("실시간 출력현황 ERR_CONNECTION_REFUSED ");
	    		},
	    		complete : function() {
	    		}
			});
			//----------------------------------------------------------------------------------
		}, 500); // 30초에 한번씩 받아온다.
	});
	
	function fn_edit(notice_seq) {
		$("#notice_seq").val(notice_seq);
		
		commSubmit('/admin/notice/noticeEdit', $("#noticeHeader"));
		//$("#noticeHeader").trigger("click");
		//$("#defaultFrm").attr("action", "<c:url value='/admin/notice/noticeEdit' />").submit();
	}
	
	function notificationRefresh(){
		$("#code_sel").change();
	}
</script>

<script type="text/javascript">
	//select box 바뀔때
	$(function(){
		$("#code_sel").change(function(){
			if( this.value == "0" ){
				$("#code_query").val("");
			}else{
				var codeQuery = "AND a.status_flag = '" + this.value + "'";
				$("#code_query").val(codeQuery);	
			}
			
			var _data = {
				use_yn : '1',
				del_yn : '0',
				status_flag : this.value == '0' ? null : this.value 
		    };
		    	
	    	var _url = "<c:url value='/admin/main/getMainNotificationList' />";
	    	commonAjax(_url, _data, true, function(data) {
	    		if(data.code == "0000") {
	    			setNotificationTable(data);
	    		}else {
	    			toastFail("오류가 발생했습니다.3");
	    		}
	    	}, function(data) {
	    		//errorListener
	    		toastFail("오류가 발생했습니다.3");
	    	}, function() {
	    		//beforeSendListener
	    	}, function() {
	    		//completeListener
	    	});
		});
	});
	
	$(function(){
		$("#srh_company").change(function(){
			var srh_company = this.value;
			
			tDate = getTenDayAgoDate();
			_data = {
				srh_text : srh_company,
				ilbo_date : tDate
			};
	    	_url = "<c:url value='/admin/main/getOrderStatisticsList' />";
	    	commonAjax(_url, _data, true, function(data) {
	    		if(data.code == "0000") {
	    			var orderStatisticsList = data.orderStatisticsList; 
	    			var totalCnt = data.totalCnt;
	    			var orderStatisticsDTO = data.orderStatisticsDTO;
	    			
	    			setOrderStatisticsTable(orderStatisticsList);
	    		}else {
	    			toastFail("오류가 발생했습니다.3");
	    		}
	    	}, function(data) {
	    		//errorListener
	    		toastFail("오류가 발생했습니다.3");
	    	}, function() {
	    		//beforeSendListener
	    	}, function() {
	    		//completeListener
	    		setOrderStatisticsDonutChart(0);
	    	});
		})
	});
	
	$(function(){
		$("#srh_use_worker_company").change(function(){
			var srh_use_worker_company = this.value;
			
			tDate = getTenDayAgoDate();
			
			_data = {
				srh_text : srh_use_worker_company,
				ilbo_date : tDate,
				company_flag : "0"
			};
	    	_url = "<c:url value='/admin/main/getUseStatisticsList' />";
	    	commonAjax(_url, _data, true, function(data) {
	    		if(data.code == "0000") {
	    			var useStatisticsList = data.useStatisticsList; 
	    			var useStatisticsDTO = data.useStatisticsDTO;
	    			setUseStatisticsTable(useStatisticsList , "worker-company-use-body" , "0");
	    		}else {
	    			toastFail("오류가 발생했습니다.3");
	    		}
	    	}, function(data) {
	    		//errorListener
	    		toastFail("오류가 발생했습니다.3");
	    	}, function() {
	    		//beforeSendListener
	    	}, function() {
	    		//completeListener
	    		setUseStatisticsDonutChart(0,"0");
	    	});
		})
	});
	
	$(function(){
		$("#srh_use_company").change(function(){
			var srh_use_company = this.value;
			var flag = "1";
			tDate = getTenDayAgoDate();
			
			_data = {
				srh_text : srh_use_company,
				ilbo_date : tDate,
				company_flag : "1"
			};
	    	_url = "<c:url value='/admin/main/getUseStatisticsList' />";
	    	commonAjax(_url, _data, true, function(data) {
	    		if(data.code == "0000") {
	    			var useStatisticsList = data.useStatisticsList; 
	    			var useStatisticsDTO = data.useStatisticsDTO;
	    			
	    			setUseStatisticsTable(useStatisticsList , "company-use-body", "1");
	    		}else {
	    			toastFail("오류가 발생했습니다.3");
	    		}
	    	}, function(data) {
	    		//errorListener
	    		toastFail("오류가 발생했습니다.3");
	    	}, function() {
	    		//beforeSendListener
	    	}, function() {
	    		//completeListener
	    		setUseStatisticsDonutChart(0,"1");
	    	});
		})
	});
	
	$(function(){
		$("#srh-city").change(function(){
			var city_code = this.value;
			_data = {
				city_code : city_code,
				fcst_time : getNearTime(),
	    		fcst_date : getformatDate()
			};
	    	_url = "<c:url value='/admin/main/getWeather'/>";
	    	commonAjax(_url, _data, true, function(data) {
	    		if(data.code == "0000") {
	    			setWeatherData(data);
	    		}else {
	    			toastFail("오류가 발생했습니다.3");
	    		}
	    	}, function(data) {
	    		//errorListener
	    		toastFail("오류가 발생했습니다.3");
	    	}, function() {
	    		//beforeSendListener
	    	}, function() {
	    		//completeListener
	    	});
		})
	});
	
	$(function() {
		$("#company_seq, #ilbo_date").change(function() {
			$.ajax({
				type: "POST",
				url: "<c:url value='/admin/main/geStatusSum' />",
				data: {
					ilbo_date : $("#ilbo_date").val(),
					company_seq : $("#company_seq").val()
				},
				dataType: "json",
				beforeSend: function(xhr){
	    			xhr.setRequestHeader("AJAX", true);
	    		},
	    		success : function(data) {
	    			// 성공 시
	    			if(data.code == "0000") {
	    				const option = {
							maximumFractionDigits: 4
						};
	    						
	    				$("#readySum").text(data.ouputStatus.readySum.toLocaleString('ko-KR', option));
	    				$("#reserveSum").text(data.ouputStatus.reserveSum.toLocaleString('ko-KR', option));
	    				$("#startSum").text(data.ouputStatus.startSum.toLocaleString('ko-KR', option));
	    				$("#arriveSum").text(data.ouputStatus.arriveSum.toLocaleString('ko-KR', option));
	    				$("#complteSum").text(data.ouputStatus.complteSum.toLocaleString('ko-KR', option));
	    				$("#zzemSum").text(data.ouputStatus.zzemSum.toLocaleString('ko-KR', option));
	    				$("#punkSum").text(data.ouputStatus.punkSum.toLocaleString('ko-KR', option));
	    				$("#daemaSum").text(data.ouputStatus.daemaSum.toLocaleString('ko-KR', option));
	    				
	    				var totalWorkSum = data.assignWork.assignWorkSum + data.notAssignWork.workSum;
	    				$("#totalWorkSum").text(totalWorkSum.toLocaleString('ko-KR', option));
	    				$("#assignWorkSum").text(data.assignWork.assignWorkSum.toLocaleString('ko-KR', option));
	    				$("#assignCancelSum").text(data.assignWork.assignCancelSum.toLocaleString('ko-KR', option));
	    				
	    				$("#workSum").text(data.notAssignWork.workSum.toLocaleString('ko-KR', option));
	    				$("#cancelSum").text(data.notAssignWork.cancelSum.toLocaleString('ko-KR', option));
		    		}else {
		    			toastFail("실시간 출력현황 오류가 발생했습니다.");
		    		}
	    		},
	    		error : function(data) {
	    			// 오류 발생
	    			toastFail("실시간 출력현황 ERR_CONNECTION_REFUSED ");
	    		},
	    		complete : function() {
	    		}
			});
		});
	});
</script>

<script type="text/javascript">
	function setApprovalTable(currPage, pageSize){
		$("#approval-table").empty();
		var text = "";
		for(i=(currPage-1)*pageSize; i<(currPage-1)*pageSize+(pageSize*1) && i<waitApprovalList.length; i++){
			text += "<tr>";
			text += "	<td>";
			text += 		waitApprovalList[i].company_name;
			text += "	</td>";
			text += "	<td>";
			text += 		waitApprovalList[i].wait_approval_count;
			text += "	</td>";
			text += "</tr>";
		}
		$("#approval-table").append(text);
		page_display(currPage, pageSize, ".paging2");
	}
	
	function page_display(currPage, pageSize, pagingClass) {
		
		/****************
		displayCount : 화면에 보여주는 페이지번호 갯수
		pageSize     : 한 페이지당 레코드 갯수
		pageCount    : 실제 페이지 갯수
		pageIndex
		***/
		displayCount = 5;
		startPageNo = Math.floor((currPage-1)/displayCount) * displayCount + 1;
		
		pageCount   = Math.ceil(waitApprovalList.length / pageSize);
		if (pageCount < 1) return;
		
		var text = "";	
		
		text += "<ul>";
		if (currPage > 1){
			text += "<li><a href='javascript:void(0);' onclick='setApprovalTable("+ (currPage-1) +", "+ pageSize +");'>이전</a></li>";
		}
		
		for (pageIndex=startPageNo; pageIndex<startPageNo+displayCount && pageIndex<=pageCount; pageIndex++){		
			if (pageIndex == currPage){
				text += "<li><a href='javascript:void(0)'><strong>"+currPage+"</strong></a></li>";			
			}else{
				text += "<li><a href='javascript:void(0);' onclick='setApprovalTable(\""+ pageIndex +"\", \""+ pageSize +"\");'>"+pageIndex+"</a></li>";
			}
		}
		
		if (pageCount > currPage) {
			text += "<li><a href='javascript:void(0);' onclick='setApprovalTable(\""+ (Number(currPage)+1) +"\", \""+ pageSize +"\");'>다음</a></li>";
		}
		
		text += "</ul>";	
		
		text += "";
		$(pagingClass).empty();
		$(pagingClass).append(text);
	}

	function fn_data_receive (obj, pagingClass){
		if(pagingClass == '.paging'){
			setNotificationTable(obj);
		}else if(pagingClass == ".procedurePaging") {
			setProcedureTable(obj);			
		}else{
			setIssueTable(obj, pagingClass);
		}
	}
	
	function setIssueTable(obj, pagingClass) {
		var issueList = obj.issueList;
    	var issueDTO = obj.issueDTO;
    	var totalCnt = obj.totalCnt;
    	$("#pageNo1").val(issueDTO.paging.pageNo);
    	var pageNo = $("#pageNo1").val();
    	var text = "";
    	$("#issueBody").empty();
    	
    	var company_seq = '${sessionScope.ADMIN_SESSION.company_seq }';
    	
		if(totalCnt <= 0) {
			text += '<tr style="text-align: center;">';
			if(company_seq == '13') {
				text += '	<td colspan="5">';
			}else {
				text += '	<td colspan="4">';
			}
			text += '		조회된 내용이 없습니다.';
			text += '	</td>';
			text += '</tr>';
		}else {
			for(var i = 0; i < issueList.length; i++) {
				var num = totalCnt - ((issueDTO.paging.pageNo - 1) * issueDTO.paging.pageSize + i);
				
				text += "<tr>";
				text += "	<td>" + num + "</td>";
				if(company_seq == '13') {
					if(issueList[i].duty_type == '0') {
						text += "	<td>영업</td>";					
					}else if(issueList[i].duty_type == '1') {
						text += "	<td>마케팅</td>";											
					}else if(issueList[i].duty_type == '2') {
						text += "	<td>개발</td>";											
					}
				}
				text += "	<td>" + issueList[i].issue_date + "</td>";
				text += "	<td>" + issueList[i].issue_text + "</td>";
				text += "	<td>" + issueList[i].reg_date + "</td>";
				text += "</tr>";
			}
		}
		
		$("#issueBody").append(text);
		
		if(issueDTO.paging.pageSize != '0') {
			$(".paging1").empty();
			
        	fn_page_display_ajax_new(issueDTO.paging.pageSize, obj.totalCnt, '#pageNo1', "<c:url value='/admin/main/getIssueList' />", '.paging1');
		}
	}
	function getNotiTitleColor(status_code){
		var color = "";
		
		if( status_code == "ALR009" || status_code == "ALR010"){ 
			//신규 오더[APP], 신규 오더[WEB]
			color = "#FF7F00";
			//color = "#4FA7E5";
		}else if( status_code == "ALR004" || status_code == "ALR005" || status_code == "ALR011" ){ 
			//펑크, 째앰, 대마
			color = "#9900FF";
		}else if( status_code == "ALR003" || status_code == "ALR016" || status_code == "ALR014"){ 
			// 다른 사람, 또가요, 재요청
			color = "#13DB10";
		}else if( status_code == "ALR024"){ 
			// 전화
			color = "#F53C3C";
		}else if( status_code == "ALR034" ){
			// 오더링크[WEB]
			color = "#00adff";
		}
		
		return color;
	}
	
	function setNotificationTable(obj, pagingClass){
		var notificationList = obj.notificationList;
    	var notificationDTO = obj.notificationDTO;
    	var totalCnt = obj.totalCnt;
    	$("#pageNo").val(notificationDTO.paging.pageNo);
    	var pageNo = $("#pageNo").val();
    	var text = "";
    	$("#notification-body").empty();
		if(totalCnt == 0) {
			text += '<tr style="text-align: center;">';
			text += '	<td colspan="7">';
			text += '		조회된 내용이 없습니다.';
			text += '	</td>';
			text += '</tr>';
			
		}else {
			for(var i = 0; i < notificationList.length; i++) {
				var count = totalCnt - ((pageNo - 1) * notificationDTO.paging.pageSize + i);
				text += '<tr>';					
				text += '	<td>' + count + '</td>';
				text += '	<td class="read-yn">' + nullCheck(notificationList[i].read_yn) + '</td>';
				text += '	<td>' + nullCheck(notificationList[i].work_date) + '</td>';
				text += '	<td><a href="#" style="color:' +getNotiTitleColor(notificationList[i].status_flag) + '" onclick="clickWebPush(\''+notificationList[i].status_flag+'\', \'' + notificationList[i].ilbo_seq + '\', \'' + notificationList[i].work_date + '\')">' + nullCheck(notificationList[i].title) + '</a></td>';
				text += '	<td>' + nullCheck(notificationList[i].content) + '</td>';
				text += '	<td>' + nullCheck(notificationList[i].work_company_name) + '</td>';
				text += '	<td>' + nullCheck(notificationList[i].worker_company_name) + '</td>';
				text += '	<td>' + nullCheck(notificationList[i].reg_date.substring(0,notificationList[i].reg_date.length-2)) + '</td>';
				text += '</tr>';
			}
			
		}
		$("#notification-body").append(text);
		if(notificationDTO.paging.pageSize != '0') {
			$(".paging").empty();
        	fn_page_display_ajax_new(notificationDTO.paging.pageSize, obj.totalCnt, '#pageNo', "<c:url value='/admin/main/getMainNotificationList' />", '.paging', "#code_query");
		}
	}
	
	function setProcedureTable(obj){
		var procedureList = obj.procedureList;
    	var procedureDTO = obj.procedureDTO;
    	var totalCnt = obj.totalCnt;
    	$("#procedurePageNo").val(procedureDTO.paging.pageNo);
    	var pageNo = $("#procedurePageNo").val();
    	var text = "";
    	$("#procedureBody").empty();
		if(totalCnt == 0) {
			text += '<tr style="text-align: center;">';
			text += '	<td colspan="3">';
			text += '		조회된 내용이 없습니다.';
			text += '	</td>';
			text += '</tr>';
			
		}else {
			for(var i = 0; i < procedureList.length; i++) {
				text += '<tr>';					
				text += '	<td>' + nullCheck(procedureList[i].proc_name) + '</td>';
				text += '	<td>' + nullCheck(procedureList[i].r_count) + '</td>';
				text += '	<td>' + nullCheck(procedureList[i].reg_date) + '</td>';
				text += '</tr>';
			}
			
		}
		$("#procedureBody").append(text);
		if(procedureDTO.paging.pageSize != '0') {
			$(".procedurePaging").empty();
        	fn_page_display_ajax_new(procedureDTO.paging.pageSize, obj.totalCnt, '#procedurePageNo', "<c:url value='/admin/main/getProcedureList' />", '.procedurePaging');
		}
	}
	
	//오더종류 통계 차트 설정
	function setOrderStatisticsDonutChart(rowIndex){ 
		var thead = $("#order-header").children().eq(0);
		var tr = $("#order-body").children().eq(rowIndex);
		var td = tr.children();
		
		$("#order-title").empty();
		$("#order-donut-chart").empty();
		
		$("#order-title").append(td.eq(0).text());
		var total = 0;
		$(td).each(function(index, item){
			if( index > 0 && index < $(td).length-1 ){
				total += Number(item.innerText);
			}
		});
		
		if(td.length > 0) {
			if( td[0].innerText == "조회된 내용이 없습니다." ){
				return;
			}
		}
		
		new Morris.Donut({
			  // ID of the element in which to draw the chart.
			  element: 'order-donut-chart',
			  data: [
			    { label: '사무실', value: td.eq(1).text() },
			    { label: 'APP', value: td.eq(2).text() },
			    { label: 'CALL', value: td.eq(3).text() },
			    { label: 'WEB회원', value: td.eq(4).text() },
			    { label: 'WEB비회원', value: td.eq(5).text() },
			    { label: 'MWEB회원', value: td.eq(6).text() },
			    { label: 'MWEB비회원', value: td.eq(7).text() },
			    { label: '또가요', value: td.eq(8).text() },
			    { label: '다른분', value: td.eq(9).text() }
			  ],
			  formatter : function (y, data) {
				  var numY = Number(y);
				  return Math.round( numY / total*100 ) +"%" 
			  },
			  resize: true
			  
		});
	}
	//오더종류 통계 테이블 설정
	function setOrderStatisticsTable(list){
		var text = '';
		$("#order-body").empty();
		
		if(list == 0) {
			text += '<tr style="text-align: center;">';
			text += '	<td colspan="11">';
			text += '		조회된 내용이 없습니다.';
			text += '	</td>';
			text += '</tr>';
			
		}else {
			for(var i = 0; i < list.length; i++) {
				var total = list[i].office_count + list[i].app_count + list[i].call_count + list[i].web_member_count 
						+ list[i].web_non_member_count + list[i].mWeb_member_count + list[i].mWeb_non_member_count + list[i].again_count + list[i].diff_count;
				text += '<tr>';
				text += '	<td>';
				text += '		<a href="javascript:void(0);" onclick="setOrderStatisticsDonutChart(' + i + ')">';
				text += 			list[i].ilbo_date;
				text += '		</a>';
				text += '	</td>';
				text += '	<td>' + list[i].office_count + '</td>';
				text += '	<td>' + list[i].app_count + '</td>';
				text += '	<td>' + list[i].call_count + '</td>';
				text += '	<td>' + list[i].web_member_count + '</td>';
				text += '	<td>' + list[i].web_non_member_count + '</td>';
				text += '	<td>' + list[i].mWeb_member_count + '</td>';
				text += '	<td>' + list[i].mWeb_non_member_count + '</td>';
				text += '	<td>' + list[i].again_count + '</td>';
				text += '	<td>' + list[i].diff_count + '</td>';
				text += '	<td>' + total + '</td>';
				text += '</tr>';
			}
			
		}
		$("#order-body").append(text);
	}
	//공개여부 통계 차트 설정
	function setUseStatisticsDonutChart(rowIndex, flag){
		var id = "worker-company-use";
		var chartColors = [['#E8002F','#FF5577','#FF9DB0'],['#006F3D','#1AA968','#9ECFB9']]; 
		if( flag == "1" ){
			id = "company-use";
		}
		var thead = $("#"+ id +"-header").children().eq(0);
		var tr = $("#"+ id +"-body").children().eq(rowIndex);
		var td = tr.children();
		
		$("#"+ id +"-title").empty();
		$("#"+ id +"-donut-chart").empty();
		
		$("#"+ id +"-title").append(td.eq(0).text());
		var total = 0;
		$(td).each(function(index, item){
			if( index > 0 && index < $(td).length-1 ){
				total += Number(item.innerText);
			}
		});
		
		if( td[0].innerText == "조회된 내용이 없습니다." ){
			return;
		}
		new Morris.Donut({
			  element: id + '-donut-chart',
			  data: [
			    { label: '미공개', value: td.eq(1).text() },
			    { label: '전체공개', value: td.eq(2).text() },
			    { label: '지점공개', value: td.eq(3).text() },
			  ],
			  colors: chartColors[flag],
			  formatter : function (y, data) {
				  var numY = Number(y);
				  return Math.round( numY / total*100) + "%"; 
			  },				  
			  resize: true
		});
	}
	
	//공개여부 통계 테이블 설정
	function setUseStatisticsTable(list, tableBodyId, flag){
		var text = '';
		$("#"+tableBodyId).empty();
		if(list == 0) {
			text += '<tr style="text-align: center;">';
			text += '	<td colspan="5">';
			text += '		조회된 내용이 없습니다.';
			text += '	</td>';
			text += '</tr>';
			
		}else {
			for(var i = 0; i < list.length; i++) {
				var total = list[i].undisclosed_count + list[i].all_count + list[i].company_count;
				text += '<tr>';
				text += '	<td>';
				text += '		<a href="javascript:void(0);" onclick="setUseStatisticsDonutChart(' + i + ', ' + flag +')">';
				text += 			list[i].ilbo_date;
				text += '		</a>';
				text += '	</td>';
				text += '	<td>' + list[i].undisclosed_count + '</td>';
				text += '	<td>' + list[i].all_count + '</td>';
				text += '	<td>' + list[i].company_count + '</td>';
				text += '	<td>' + total + '</td>';
				text += '</tr>';
			}
			
		}
		$("#"+tableBodyId).append(text);
	}
	
	
	function getTenDayAgoDate(){
		//10일 빼기
		var date = new Date();
		date.setDate(date.getDate() - 10); 
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		if( month < 10 ){
			month = '0' + month;
		}
		var day = date.getDate();
		if( day < 10 ){
			day = '0' + day;
		}
		
		return year + '-' + month + '-' + day; 
	}
	function nullCheck(str){
		if( str == null ){
			return '';
		}
		return str;
	}
	function getNearTime(){
		// 1시간후 시간 가져오기
		var curDate = new Date();
		var hourAgeDate = curDate.setHours(curDate.getHours()+1);
		var hourAgeTime = curDate.getHours();
		return hourAgeTime + "00";
	}
	function getformatDate(){
		var curDate = new Date();
		var year = curDate.getFullYear();
		var month = curDate.getMonth() + 1;
		if( month < 10 ){
			month = "0" + month;
		}
		var day = curDate.getDate();
		if( day < 10 ){
			day = "0" + day;
		}
		return year + "-" + month + "-" + day;
	}
	function getSkyStatus(weatherDTO){
		var pptnFormArr = ["", "비", "비/눈", "눈", "소나기", "빗방울", "빗방울/눈날림", "눈날림"];
		var skyStatusArr = ["", "맑음", "", "구름많음", "흐림"];
		
		var result = "";
		if( weatherDTO != null ){
			if( weatherDTO.pptn_form != 0 ){
				result = pptnFormArr[weatherDTO.pptn_form * 1];
			}else{
				result = skyStatusArr[weatherDTO.sky_status * 1];
			}
		}
		return result;
	}
	function setWeatherIcon(weatherDTO){
		var url = "";
		if( weatherDTO == null ){
			url = "/resources/common/images/no-data.png";
		}else{
			if( weatherDTO.pptn_form != 0 ){
				url = "/resources/common/images/weather-icon-p0"+weatherDTO.pptn_form+".png";
			}else{
				url = "/resources/common/images/weather-icon-0"+weatherDTO.sky_status+".png";
			}
		}
		$(".weather-icon").attr("src", url);
	}
	function setWeatherData(data){
		var weatherDTO = data.weatherDTO;
		if( weatherDTO == null ){
			$(".temp-span").empty();
			$("#pptn-pct").empty();
			$("#humidity").empty();
			$("#wind-speed").empty();
			$("#fcst-date").empty();
			$("#sky-status").empty();
			$(".min-temp").empty();
			$(".high-temp").empty();
			setWeatherIcon(weatherDTO);
		}else{
			if( weatherDTO.version == "1"){
				$(".temp-span").html(weatherDTO.three_hours_temp);	
			}else if( weatherDTO.version == "2"){
				$(".temp-span").html(weatherDTO.hours_temp);
			}
			
   			$("#pptn-pct").html(weatherDTO.pptn_pct);
   			$("#humidity").html(weatherDTO.humidity);
   			$("#wind-speed").html(weatherDTO.wind_speed);
   			$("#fcst-date").html(weatherDTO.fcst_date + " " + getNearTime().substring(0, 2) + "시");
   			$("#sky-status").html(getSkyStatus(weatherDTO));
   			if( weatherDTO.min_temp == null ){
   				$(".min-temp").html("-99");
   			}else{
   				$(".min-temp").html(weatherDTO.min_temp.substring(0,weatherDTO.min_temp.length-2));
   			}
   			if( weatherDTO.high_temp == null ){
   				$(".high-temp").html("+99");
   			}else{
   				$(".high-temp").html(weatherDTO.high_temp.substring(0,weatherDTO.high_temp.length-2));
   			}
   			setWeatherIcon(weatherDTO);
		}
	}
	//알림 팝업창에 값 넣고 read_yn = 1 로 바꿈
	$(document).on("click", ".notification-modal", function (e) {
		var title = $(this).data('title'); 
		var content = $(this).data('content');
		var reg_admin = $(this).data('regadmin');
		var reg_date = $(this).data('regdate');
		var work_company = $(this).data('workcompany');
		var worker_company = $(this).data('workercompany');
		var notification_log_seq = $(this).data('notificationlogseq');
		var read_yn = $(this).data('read_yn');
		
		$(".modal-body #noti-title").text( title );
		$(".modal-body #noti-content").text( content );
		$(".modal-body #noti-worker-company").text( worker_company );
		$(".modal-body #noti-work-company").text( work_company );
		$(".modal-body #noti-reg-admin").text( reg_admin );
		$(".modal-body #noti-reg-date").text( reg_date );
		
		
		if( read_yn != '0' ){
			return;
		}
		var _data = {
				notification_log_seq: notification_log_seq
	    	};
	    	
    	var _url = "<c:url value='/admin/main/readNotification' />";
    	commonAjax(_url, _data, true, function(data) {
    		
    		if(data.code == "0000") {
    			_data = {
    				use_yn : '1',
    				del_yn : '0'
    		    };
    		    	
    	    	var _url = "<c:url value='/admin/main/getMainNotificationList' />";
    	    	commonAjax(_url, _data, true, function(data) {
    	    		if(data.code == "0000") {
    	    			setNotificationTable(data);
    	    		}else {
    	    			toastFail("notification code:" + data.code + " 오류가 발생했습니다.3");
    	    		}
    	    	}, function(data) {
    	    		//errorListener
    	    		toastFail("오류가 발생했습니다.3");
    	    	}, function() {
    	    		//beforeSendListener
    	    	}, function() {
    	    		//completeListener
    	    	});
    		}else {
    			toastFail("오류가 발생했습니다.3");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	});
	
	function clickWebPush(status_flag, ilbo_seq, work_date){
		
		if( ilbo_seq == null || ilbo_seq == 'null' ){
			return;
		}
		
		var url;
		var srh_ilbo_type;
		var frm = $("#defaultFrm");
		var _data = {
			ilbo_seq : ilbo_seq
	    };
		
    	var _url = "<c:url value='/admin/main/getIlboInfo' />";
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var ilboDTO = data.ilboDTO;
    			
    			if( status_flag == "ALR001" || status_flag == "ALR002" || status_flag == "ALR003" || status_flag == "ALR006" 
    				|| status_flag == "ALR008" || status_flag == "ALR009" || status_flag == "ALR011" || status_flag == "ALR012" || status_flag == "ALR024"){
    			
					ilboView('i.work_seq', ilboDTO.work_seq, '/admin/workIlbo');
					
	    		}else if ( status_flag == "ALR004" || status_flag == "ALR005" || status_flag == 'ALR019' || status_flag == 'ALR016'){
	    			
	    			ilboView('i.worker_seq', ilboDTO.worker_seq, '/admin/workerIlbo');
	    			
	    		}else if(status_flag == "ALR031" || status_flag == "ALR032"){
	    			ilboView('i.employer_seq', ilboDTO.employer_seq, '/admin/workIlbo');
	    		}else if(status_flag == "ALR010"){
	    			url = "/admin/orderList";
	    			
	    			//작업일자 보내기
	    			if( jQuery.type($("#work_date").val()) === "undefined" ){
	    				frm.append("<input type='hidden' id='work_date' name='work_date' value='"+ work_date +"' />");
	    			}else{
	    				$("#work_date").val( work_date );
	    			}
	    			
	    			//오더관리 탭 활성화
	    			var input1 = $("#defaultFrm > input[name=ADMIN_DEPT1_CODE]");    // 관리자 메뉴 코드1
	    			if ( jQuery.type(input1.val()) === "undefined" ) {
	    				frm.append("<input type='hidden' name='ADMIN_DEPT1_CODE' value='0' />"); //오더관리 탭 인덱스 0
	    			}else {
	    			    input1.val("0");
	    			}
	    			
	    			frm.attr('target', '_blank');
	    			frm.attr('action', url).submit();
	    		}
    		}else {
    			toastFail("오류가 발생했습니다.3");
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
	function ilboView(type, seq, url) {
		var frmId  = "defaultFrm";    // default Form
		var frm      = $("#"+ frmId);
		var ilboView = $("#"+ frmId +" > input[name=ilboView]");          // 대장으로 이동
		var ilboType = $("#"+ frmId +" > input[name=srh_ilbo_type]");   // 대장으로 이동
		var ilboSeq  = $("#"+ frmId +" > input[name=srh_seq]");           // 대장으로 이동
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
		
		frm.attr('target', '_blank');
		frm.attr('action', url).submit();
		  
		//초기화
		frm.attr('target', '');
		frm.attr('action', '');
		
		$("#"+ frmId +" > input[name=ilboView]").val("");
	}
	
	function drawSelectBox(list){
		var text = "<option value=0>전체</option>";
		for(var idx=0; idx<list.length; idx++){
			text += '<option value='+ list[idx].company_seq + '>' + list[idx].company_name +'</option>';
		}
		
		var sessionAuthLevel = '${sessionScope.ADMIN_SESSION.auth_level }';
		var sessionCompanySeq = '${sessionScope.ADMIN_SESSION.company_seq }';
		
		if( "${sessionScope.ADMIN_SESSION.auth_level}" == 0 || (sessionAuthLevel == '1' && sessionCompanySeq == '13')){
			$("#srh_use_worker_company").html(text);
			$("#srh_use_company").html(text);
			$("#srh_company").html(text);	
		}
		$("#company_seq").html(text);
	}
	
</script>
<body>
    <div id="wrap">
        <!--/. NAV TOP  -->
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
						<ol class="breadcrumb">
  							<li>Home</li>
  							<li>Dashboard</li>
						</ol>
                    </div>
                </div>
                <div class="row">
                	<c:if test="${sessionScope.ADMIN_SESSION.admin_id eq 'nemojjang' or sessionScope.ADMIN_SESSION.admin_id eq 'yunsoo' or sessionScope.ADMIN_SESSION.admin_id eq 'gj'}">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="panel panel-default">
			                    <div class="panel-heading">
									프로시저 로그
			                    </div> 
			                    <div class="panel-body">
			                        <div>
			                            <table class="table table-striped table-bordered table-hover text-center" >
			                            	<colgroup>
			                            		<col width="60%">
			                            		<col width="20%">
			                            		<col width="*">
			                            	</colgroup>
			                                <thead>
			                                    <tr>
			                                        <th class="text-center">프로시저 이름</th>
			                                        <th class="text-center">적용된 row 수</th>
			                                        <th class="text-center">날짜</th>
			                                    </tr>  
			                                </thead>
			                                <tbody id="procedureBody">
			                                
			                               	</tbody>
			                            </table>
								        <input type="hidden" id="procedurePageNo" value="0" />
										<div class="procedurePaging">
										</div>
			                        </div>
			                    </div>
		                	</div>
						</div>
					</c:if>
                </div>
                <div class="row">
                	<div class="col-md-12 col-sm-12 col-xs-12">
	                    <!-- Advanced Tables -->
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
								알림테이블<a style="background-image: url(/resources/common/images/btn_refresh.gif); padding:4px 15px; margin:10px;" href="javascript:notificationRefresh();"></a>
								<select id="code_sel" style="height: 33px;position: absolute;top: 8px;right: 32px;">
									<option value="0">전체</option>
									<c:forEach items="${codeList }" var="code">
										<option value="${code.code_value }">${code.code_name }</option>
									</c:forEach>
								</select>
								<input type="hidden" id="code_query" value="" />
	                        </div>
	                        <div class="panel-body">
	                            <div class="table-responsive">
	                                <table class="table table-striped table-bordered table-hover" id="notificationTable" >
	                                	<colgroup>
	                                		<col width="5%">
	                                		<col width="5%">
	                                		<col width="10%">
	                                		<col width="*">
	                                		<col width="10%">
	                                		<col width="10%">
	                                		<col width="10%">
	                                	</colgroup>
	                                    <thead>
		                                    <tr>
		                                    	<th>번호</th>
		                                    	<th class="read-yn">상태</th>
		                                    	<th>작업일자</th>
		                                        <th>제목</th>
		                                        <th>내용</th>
		                                        <th>구인처</th>
		                                        <th>구직처</th>
		                                        <th>발신일</th>
		                                    </tr>  
	                                    </thead>
	                                    <tbody id="notification-body">
<%-- 									    	<c:choose> --%>
<%-- 									    		<c:when test="${totalCnt == 0 }"> --%>
<%-- 									    			<tr style="text-align: center;"> --%>
<%-- 									    				<td colspan="7"> --%>
<!-- 									    					조회된 내용이 없습니다. -->
<%-- 									    				</td> --%>
<%-- 									    			</tr>		 --%>
<%-- 									    		</c:when> --%>
<%-- 									    		<c:otherwise> --%>
<%-- 									    			<c:forEach var="notificationInfo" items="${notificationList }" varStatus="status"> --%>
<%-- 									    				<tr> --%>
<%-- 									    					<td>${totalCnt - status.index }</td> --%>
<%-- 									    					<td class="read-yn">${notificationInfo.read_yn }</td> --%>
<%-- 									    					<td>${notificationInfo.work_date }</td> --%>
<%-- 									    					<td><a href="#" class="notification_${notificationInfo.status_flag}" onclick="clickWebPush('${notificationInfo.status_flag}', '${notificationInfo.ilbo_seq }', '${notificationInfo.work_date }')">${notificationInfo.title }</a></td> --%>
<%-- 									    					<td>${notificationInfo.content }</td> --%>
<%-- 									    					<td>${notificationInfo.work_company_name }</td> --%>
<%-- 									    					<td>${notificationInfo.worker_company_name }</td> --%>
<%-- 									    					<td>${notificationInfo.reg_date }</td> --%>
<%-- 									    				</tr> --%>
<%-- 									    			</c:forEach> --%>
<%-- 									    		</c:otherwise> --%>
<%-- 									    	</c:choose> --%>
		                               	</tbody>
	                                </table>
	                            </div>
	                        </div>
	                    <input type="hidden" id="notification_seq"  name="notification_seq" />
				        <input type="hidden" id="pageNo" value="${notificationDTO.paging.pageNo}" />
				        <c:if test="${notificationDTO.paging.pageSize != '0' }">
							<div class="paging">
<!-- 								<script type="text/javascript"> -->
<%-- // 									fn_page_display_ajax_new('${notificationDTO.paging.pageSize}', '${totalCnt }', '#pageNo', "<c:url value='/admin/main/getMainNotificationList' />", '.paging', "#code_query" ); --%>
<!-- 								</script> -->
							</div>
						</c:if>
	                    </div>
	                    <!--End Advanced Tables -->
	                </div>
                	 <div class="panel-body">
	                  	<p class="floatL">
					    	<input type="text" id="ilbo_date" name="ilbo_date" readonly class="inp-field wid100 datepicker" style="height: 33px; line-height: 30px; border: 1px solid #b7b7b7;"/>
					    </p>
						<c:if test="${sessionScope.ADMIN_SESSION.company_seq eq 13 }">
							<select id="company_seq" class="floatL  mglM pdlM " style="height: 33px;">
							</select>
						</c:if>
					</div>
					
					<div class="col-md-12 col-sm-6 col-xs-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								실시간 현장 현황
	                        </div>
							<div class="panel-body">									    	
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-yellow">
			                            <div class="panel-count">
											<h3 id="totalWorkSum">-</h3>
			                               	<strong>총개수</strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-pink">
			                            <div class="panel-count">
										<h3 id="assignWorkSum">-</h3>
											<strong> 배정</strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-black">
			                            <div class="panel-count">
										 	<h3 id="assignCancelSum">-</h3>
			                               	<strong> 배정 현장취소</strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-sky">
			                            <div class="panel-count">
											<h3 id="workSum">-</h3>
			                             	<strong> 미배정 </strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-black">
			                            <div class="panel-count">
											<h3 id="cancelSum">-</h3>
			                             	<strong> 미배정 현장취소 </strong>
			                            </div>
			                        </div>
			                    </div>
			                </div>
						</div>
					</div>
					
					<div class="col-md-12 col-sm-6 col-xs-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								실시간 출역현황
	                        </div>
							<div class="panel-body">									    	
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-yellow">
			                            <div class="panel-count">
											<h3 id="readySum">-</h3>
			                               	<strong>대기</strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-pink">
			                            <div class="panel-count">
											<h3 id="reserveSum">-</h3>
			                               	<strong> 예약</strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-green">
			                            <div class="panel-count">
										 	<h3 id="startSum">-</h3>
			                               	<strong> 출발</strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-sky">
			                            <div class="panel-count">
											<h3 id="arriveSum">-</h3>
			                             	<strong> 도착 </strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-blue">
			                            <div class="panel-count">
											<h3 id="complteSum">-</h3>
			                             	<strong> 완료 </strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-black">
			                            <div class="panel-count">
											<h3 id=zzemSum>-</h3>
			                             	<strong> 째앰 </strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-black">
			                            <div class="panel-count">
											<h3 id="punkSum">-</h3>
			                             	<strong> 펑크 </strong>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="col-md-count">
			                        <div class="panel panel-primary text-center no-boder bg-color-black">
			                            <div class="panel-count">
											<h3 id="daemaSum">-</h3>
			                             	<strong> 대마 </strong>
			                            </div>
			                        </div>
			                    </div>
			                </div>
						</div>
					</div>
				</div>
                
                <div class="row">
					<div class="col-md-12 col-sm-6 col-xs-6">
	                    <!-- Advanced Tables -->
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
								issue
	                        </div>
	                        
	                        <div class="panel-body">
	                        	<c:if test="${sessionScope.ADMIN_SESSION.company_seq eq 13 }">
									<select class="floatL" style="height: 33px;" id="duty_type">
										<option value="0">영업</option>
		                        		<option value="1">마케팅</option>
		                        		<option value="2">개발</option>
		                        	</select>
		                        </c:if>
								<p class="floatL pdlM pdrM">
						    		<input type="text" id="issue_date" name="issue_date" readonly class="inp-field wid100 datepicker" style="height: 33px; line-height: 30px; border: 1px solid #b7b7b7;"/>
						    	</p>
						    	<input type="text" class="pdlM" style="width: 50%; margin-left: 2%; height: 33px;" id="issue_text">
								<button type="button" class="btn" id="insertBtn" style="margin-left: 2%;">등록</button>
	                            <div class="table-responsive pdtM">
	                                <table class="table table-striped table-bordered table-hover" id="notificationTable" >
	                                	<colgroup>
	                                		<col width="30px" />
	                                		<c:if test="${sessionScope.ADMIN_SESSION.company_seq eq 13 }">
		                                		<col width="70px" />
	                                		</c:if>
	                                		<col width="70px" />
	                                		<col width="300px" />
	                                		<col width="70px" />
	                                	</colgroup>
	                                    <thead class="text-center">
		                                    <tr>
		                                    	<th class="text-center">번호</th>
		                                    	<c:if test="${sessionScope.ADMIN_SESSION.company_seq eq 13 }">
			                                    	<th class="text-center">부서</th>
		                                    	</c:if>
		                                    	<th class="text-center">일자</th>
		                                        <th class="text-center">내용</th>
		                                        <th class="text-center">작성일</th>
		                                    </tr>  
	                                    </thead>
	                                    <tbody id="issueBody">
		                                
		                               	</tbody>
	                                </table>
	                            </div>
	                        </div>
				        <input type="hidden" id="pageNo1" value="0" />
				        <c:if test="${issueDTO.paging.pageSize != '0' }">
							<div class="paging1">
							</div>
						</c:if>
	                    </div>
	                    <!--End Advanced Tables -->
	                </div>
				</div>
				<c:if test="${sessionScope.ADMIN_SESSION.company_seq eq 13 }">
                <div class="row">
                <div class="col-md-12">
                	<div class="panel panel-default">
                		<div class="panel-body">
                			<div id="approval-chart-wrap">
                				<div class="table-heading">지점별 구직자 승인대기 차트</div>
                				<div id="wait-approval-chart"></div>
                			</div>
                			<div id="wait-approval-tabel">
                				<div class="table-heading">지점별 구직자 승인대기 테이블</div>
	                			<table class="table table-striped table-bordered table-hover text-center">
	                				<colgroup>
	                					<col width="40%">
	                					<col width="*">
	                				</colgroup>
	                				<thead>
	                					<tr>
	                						<th class="text-center">지점</th>
	                						<th class="text-center">승인대기 갯수</th>
	                					</tr>
	                				</thead>
	                				<tbody id="approval-table">
	                				
	                				</tbody>
	                			</table>
	                			<div class="paging2"></div>
                			</div>
                		</div>
                	</div>
                </div>
                </div>
                </c:if>
				<div class="row">
                <div class="col-md-4 col-sm-4 col-xs-4">                     
                    <div class="panel panel-default">
                        <div class="panel-heading">
							오더종류 차트<p id="order-title"></p>
                        </div>
                        <div class="panel-body">                            
							<div id="order-donut-chart" class="donut-chart"></div>
                        </div>
                    </div>            
                </div>
                <div class="col-md-4 col-sm-4 col-xs-4">                     
                    <div class="panel panel-default">
                        <div class="panel-heading">
							공개여부 차트[구직자 소속 기준]<p id="worker-company-use-title"></p>
                        </div>
                        <div class="panel-body">                            
							<div id="worker-company-use-donut-chart" class="donut-chart"></div>
                        </div>
                    </div>            
                </div>
                <div class="col-md-4 col-sm-4 col-xs-4">                     
                    <div class="panel panel-default">
                        <div class="panel-heading">
							공개여부 차트[구인처 소속 기준]<p id="company-use-title"></p>
                        </div>
                        <div class="panel-body">                            
							<div id="company-use-donut-chart" class="donut-chart"></div>
                        </div>
                    </div>            
                </div>
                
           		</div>
           		<div class="row">
	           		<div class="col-md-4 col-sm-4 col-xs-4">
						<div class="panel panel-default">
		                    <div class="panel-heading">
		                        	오더종류 통계
	                        	<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
	            					<select id="srh_company" name="srh_company" class="floatR srh">
	            					</select>
	           					</c:if>
		                    </div> 
		                    <div class="panel-body">
		                        <div class="table-responsive">
		                            <table class="table table-striped table-bordered table-hover text-center" >
		                                <thead id="order-header" class="text-center">
		                                    <tr>
		                                        <th class="text-center">출역날짜</th>
		                                        <th class="text-center">사무실</th>
		                                        <th class="text-center">APP</th>
		                                        <th class="text-center">CALL</th>
		                                        <th class="text-center">WEB회원</th>
		                                        <th class="text-center">WEB비회원</th>
		                                        <th class="text-center">MWEB회원</th>
		                                        <th class="text-center">MWEB비회원</th>
		                                        <th class="text-center">또가요</th>
		                                        <th class="text-center">다른분</th>
		                                        <th class="text-center">합계</th>
		                                    </tr>  
		                                </thead>
		                                <tbody id="order-body">
		                                
		                               	</tbody>
		                            </table>
		                        </div>
		                    </div>
	                	</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-4">
						<div class="panel panel-default">
		                    <div class="panel-heading">
		                        	공개여부 통계 [구직자 소속 기준]
	                        	<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
	            					<select id="srh_use_worker_company" name="srh_use_worker_company" class="floatR srh">
	            					</select>
	           					</c:if>
		                    </div> 
		                    <div class="panel-body">
		                        <div class="table-responsive">
		                            <table class="table table-striped table-bordered table-hover text-center" >
		                                <thead id="worker-company-use-header" class="text-center">
		                                    <tr>
		                                        <th class="text-center">출역날짜</th>
		                                        <th class="text-center">미공개</th>
		                                        <th class="text-center">전체공개</th>
		                                        <th class="text-center">지점공개</th>
		                                        <th class="text-center">합계</th>
		                                    </tr>  
		                                </thead>
		                                <tbody id="worker-company-use-body">
		                                
		                               	</tbody>
		                            </table>
		                        </div>
		                    </div>
	                	</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-4">
						<div class="panel panel-default">
		                    <div class="panel-heading">
		                        	공개여부 통계 [구인처 소속 기준]
	                        	<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
	            					<select id="srh_use_company" name="srh_use_company" class="floatR srh">
	            					</select>
	           					</c:if>
		                    </div> 
		                    <div class="panel-body">
		                        <div class="table-responsive">
		                            <table class="table table-striped table-bordered table-hover text-center" >
		                                <thead id="company-use-header" class="text-center">
		                                    <tr>
		                                        <th class="text-center">출역날짜</th>
		                                        <th class="text-center">미공개</th>
		                                        <th class="text-center">전체공개</th>
		                                        <th class="text-center">지점공개</th>
		                                        <th class="text-center">합계</th>
		                                    </tr>  
		                                </thead>
		                                <tbody id="company-use-body">
		                                
		                               	</tbody>
		                            </table>
		                        </div>
		                    </div>
	                	</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-6 col-sm-6 col-xs-6">
						<div class="panel panel-default">
		                    <div class="panel-heading">
								공지사항
		                    </div> 
		                    <div class="panel-body">
		                        <div class="table-responsive">
		                            <table class="table table-striped table-bordered table-hover text-center" >
		                                <thead>
		                                    <tr>
		                                        <th class="text-center">번호</th>
		                                        <th class="text-center">제목</th>
		                                        <th class="text-center">작성지점</th>
		                                        <th class="text-center">작성자</th>
		                                        <th class="text-center">보여지는 지점</th>
		                                        <th class="text-center">날짜</th>
		                                    </tr>  
		                                </thead>
		                                <tbody id="tbody">
		                                
		                               	</tbody>
		                            </table>
		                        </div>
		                    </div>
	                	</div>
					</div>
				</div>
				<input type="hidden" id="notice_seq" name="notice_seq" />
				
				<div class="row">
	                <div class="col-md-3 col-sm-3">
	                	<div class="panel panel-default">
	                		<div class="panel-heading">
	                			<label>날씨정보</label>
	                			<select id="srh-city" name="srh_city" class="floatR srh">
	            				</select>
	                		</div>
	                		<div class="panel-body">
	                			<div class="weather-content">
	                				<div class="temp-content">
	               						<img class="weather-icon" alt="날씨아이콘" src="">
	               						<div class="temp-div">
	               							<span class="temp-span"></span><div class="sign">℃</div>
	               						</div>
	               						<div class="temp-div">
	               							<p id="sky-status"></p>
	               						</div>
	               						<div class="temp-div">
	               							<span class="min-temp">1</span><label class="no-margin">°</label>
	               							<span class="high-temp">9</span><label class="no-margin">°</label>
	               						</div>
	                				</div>
	                				<div class="rain-content">
	                					<div class="category">강수확률:<label class="no-margin" id="pptn-pct"></label>%</div>
	                					<div class="category">습도: <label class="no-margin" id="humidity"></label>%</div>
	                					<div class="category">풍속: <label class="no-margin" id="wind-speed"></label>m/s</div>
	                					<div class="category"><label class="no-margin" id="fcst-date"></label></div>
	                				</div>
	                			</div>
	                		</div>
	                	</div>
	                </div>
					
					<div class="col-md-3 col-sm-3">
	                    <div class="panel panel-primary">
	                        <div class="panel-heading">
								소장용앱 QR 다운로드
	                        </div>
	                        <div class="panel-body">
	                            <p>
	                            	<center><img src="/resources/app/ilgajaB_qr.png" width="200px">  </center>
								</p>
								<br>기존에 설치되어 있는 앱은 삭제하고 설치 하세요.
	                        </div>
	                        <div class="panel-footer">
								버젼 : V1.0
	                        </div>
	                    </div>
	                </div>
                </div>
                <!-- /. ROW  -->
		
				<footer>
					<p></p>
				</footer>
            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
        <form ></form>
        
        <div class="modal fade" id="modalNotification" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	 		<div class="modal-dialog" role="document">
	    		<div class="modal-content">
		      		<div class="modal-body">
		        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		        			<span aria-hidden="true">×</span>
		        		</button>
		      		</div>
		      		<div class="modal-body">
				        <table class = "table table-hover table-bordered">
				        	<tr>
				        		<td class="title-width">제목</td>
				        		<td id="noti-title"></td>
				        	</tr>
				        	<tr>
				        		<td>내용</td>
				        		<td id="noti-content"></td>
				        	</tr>
				        	<tr>
				        		<td>구인처</td>
				        		<td id="noti-work-company"></td>
				        	</tr>
				        	<tr>
				        		<td>구직처</td>
				        		<td id="noti-worker-company"></td>
				        	</tr>
				        	<tr>
				        		<td>작성자</td>
				        		<td id="noti-reg-admin"></td>
				        	</tr>
				        	<tr>
				        		<td>작성일</td>
				        		<td id="noti-reg-date"></td>
				        	</tr>
				        </table>
	      			</div>
		            <div class="modal-footer">
			  	    	<button type="button" class="btn btn-outline-primary" data-dismiss="modal">Close</button>
			      	</div>
	    		</div>
			</div>
		</div>
		<!-- Modal: modalCart -->
    </div>
</body>
</html>