<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/jobCal.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript">
	var jobList = new Array();
	var jobSeqArray = new Array();	// 선택한 직종 array
	var totalOptionPrice = 0;

	$(function() {
		var _data = {
			use_yn: "1"
			, del_yn: "0"
			, orderBy: "job_kind, job_order"
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
    		jobCalInit(0);
    	
    		$(".jobList").removeClass('on');
        	$(this).addClass('on');
        
        	var jobSeq = $(this).attr("job_seq");
        	var detailUseYn = $(this).attr("detail_use_yn");
	        
        	jobSeqArray[0] = {jobSeq: jobSeq, detailUseYn: detailUseYn};
	    	
    		fn_twoJobList();
	    });
	
    	$(document).on("change", "input[name='jobDetail']", function() {
    		jobCalInit(1);
    	
    		var jobTwoSeq = $("input[name='jobDetail']:checked").val();
    		jobSeqArray[1] = {jobSeq: jobTwoSeq};
	    	
    		fn_threeJobList();
    	});
	    
	    $(document).on("change", "input:checkbox[name=jobOption]", function() {
    		totalOptionPrice = 0;
	        
        	$("input:checkbox[name=jobOption]").each(function() {
	        	
        		if($(this).is(":checked")) {
					totalOptionPrice += getAddPrice($(this).val());
        		}
	        	
        	});
	        
        	fn_complite();
    	});
	    
	    var prev_val ;
	    $('#work_arrival, #work_finish').focus(function(){
	    	prev_val = $(this).val();
	    }).change(function(){
			$(this).blur(); 
			var workArrival	= $("#work_arrival").val() *1;
			var workFinish	= $("#work_finish").val() * 1;
			
			if((workFinish -  workArrival) < 200){
				alert("도착시간과 종료시간은 2시간 이상 되어야 합니다.");
				$(this).val(prev_val);
				return false;
			}else{
				jobCalInit(2);
				fn_complite();	
			}
		});
	});
	
	function jobCalInit(flag){
		if(flag ==0){
			obSeqArray = new Array();
			$(".jobDetailArea").html("");
		}
		
		if(flag <=1){
			totalOptionPrice = 0; 
			 
		    $(".jobOptionArea").html("");
		     
		 	$("#basicPrice").text("0");
			$("#dayShortPrice").text("0");
			$("#nightShortPrice").text("0");
			$("#dayAddHourPrice").text("0");
			$("#nightAddHourPrice").text("0");
			
		     $("#workHour").text("0");
		     $("#dayAddHour").text("0");
			 $("#nightAddHour").text("0");
			 $("#dayPrice").text("0");
			 $("#nightPrice").text("0");
			 $("#optionPrice").text("0");
			 $("#totalPrice").text("0");
		}
		
		$("#dayComment").html("");
		$("#nightComment").html("");
		$("#dayPriceComment").html("");
		$("#nightPriceComment").html("");
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
			var jobHtml = '<div class="jobList buttonPanel"  job_seq="' +job.job_seq + '" detail_use_yn="' + job.detail_use_yn +'">'+ job.job_name +'</div>';
			
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
		
	// 	if(twoDepthJobList.length > 0){
		if(jobSeqArray[0].detailUseYn == "1"){
			var _html = '';
			
			twoDepthJobList.forEach(function(job,index, array){
	            _html += '<input type="radio" class="radioBasic" id="twoDepthJob'+ index+'" name="jobDetail" value="'+ job.job_seq+'"><label for="twoDepthJob'+ index+'" class="radioLabel" >'+ job.job_name +'</label><br>';
			});
			
			
			
			$(".jobDetailArea").html(_html);
		}else if(threeDepthJobList.length > 0){
			var _html = '';
			
			threeDepthJobList.forEach(function(job,index, array){
				_html += '<input type="checkBox" class="radioBasic" id="jobOption'+ index+'" name="jobOption" value="'+ job.job_seq+'" job_name="'+ job.job_name+'"><label for="jobOption'+ index+'" class="radioLabel" >'+ job.job_name +' ( + '+ comma(job.basic_price) +'원)</label><br>';
			});
			
			$(".jobOptionArea").html(_html);
			
			fn_complite();
		}else {
			fn_complite();
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
				_html += '<input type="checkBox" class="radioBasic" id="jobOption'+ index+'" name="jobOption" value="'+ job.job_seq+'"><label for="jobOption'+ index+'" class="radioLabel" >'+ job.job_name +' ( + '+ comma(job.basic_price) +'원)</label><br>';
			});
			
			
			$(".jobOptionArea").html(_html);
		}else{
			$(".jobOptionArea").html("직종 옵션 없음");
		}
		
		fn_complite();
	}
	
	function fn_complite(){
		var jobSeq = jobSeqArray[jobSeqArray.length-1].jobSeq; 
	
		var job = jobList.find(function(item){    
			  return (item.job_seq === jobSeq);
		});
		
		//1단계에서 완료될때.
		var lastJob = job;
		var basicPrice = job.basic_price;
		var memo2 = "";
		
		$("#basicPrice").text(comma(lastJob.basic_price));	//기본단가
		$("#dayShortPrice").text(comma(lastJob.short_price));
		$("#nightShortPrice").text(comma( lastJob.short_price_night));
		$("#dayAddHourPrice").text(comma( lastJob.add_day_time));
		$("#nightAddHourPrice").text(comma( lastJob.add_night_time));
		
		
		var price = fn_getPrice2($("#work_arrival").val(), $("#work_finish").val(), lastJob.basic_price, lastJob.short_price, lastJob.short_price_night, lastJob.add_day_time, lastJob.add_night_time);
		
		$("#optionPrice").text(comma(totalOptionPrice));
	    $("#totalPrice").text(comma(totalOptionPrice + price));
	}
</script>
	<div>
		<div class="dimmed"></div>
		<div id="contents" >
			<form id="defaultFrm" name="defaultFrm" method="post">
				<table class="bd-list">
					<colgroup>
					    <col width="25%">
					    <col width="25%">
					    <col width="25%">
					    <col width="*">
				    </colgroup>
				    <tr>
						<th>직종</th>
						<th>세부직종</th>
						<th>옵션선택</th>
						<th class="last">단가</th>
					</tr>
					<tbody id="listBody">
						<tr>
							<td style="vertical-align: top"><div class="jobListArea panel1"></div></td>
							<td style="vertical-align: top"><div class="jobDetailArea panel1"></div></td>
							<td style="vertical-align: top"><div class="jobOptionArea panel1"></div></td>
							<td style="vertical-align: top">
								<div class="panel1">
									<p class="firstOI_02 clear">
										<label style="margin:0" class="tit1">도착시간: </label>
										<select name="work_arrival" id="work_arrival" class="case3">
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
										
										<label style="margin-left:10px;margin-right:2px" class="tit2">종료시간: </label>
											<select name="work_finish" id="work_finish">
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
								</div>
								
								<div id="jobResultArea" class="panel2">
									<table class="bd-form-mini">
										<colgroup>
										    <col width="35%">
										    <col width="*">
									    </colgroup>
									    <tbody>
										    <tr>
												<th>총시간</th>
												<td class="linelY"><span id="workHour">0</span> 시간</td>
											</tr>
										    <tr>
												<th>주간추가시간</th>
												<td class="linelY"><span id="dayAddHour">0</span> 시간</td>
											</tr>
											<tr>
												<th>야간추가시간</th>
												<td class="linelY"><span id="nightAddHour">0</span> 시간</td>
											</tr>
										</tbody>
									</table>
								</div>
								
								<div id="jobResultArea" class="panel2">
									<table class="bd-form-mini">
										<colgroup>
										    <col width="35%">
										    <col width="*">
									    </colgroup>
									    <tbody>
										    <tr>
												<th>일당</th>
												<td class="linelY"><span id="basicPrice">0</span> 원</td>
											</tr>
										    <tr>
												<th>주간단시간단가</th>
												<td class="linelY"><span id="dayShortPrice">0</span> 원</td>
											</tr>
											<tr>
												<th>야간단시간단가</th>
												<td class="linelY"><span id="nightShortPrice">0</span> 원</td>
											</tr>
											<tr>
												<th>주간시간당단가</th>
												<td class="linelY"><span id="dayAddHourPrice">0</span> 원</td>
											</tr>
											<tr>
												<th>야간시간당단가</th>
												<td class="linelY"><span id="nightAddHourPrice">0</span> 원</td>
											</tr>
										</tbody>
									</table>
								</div>
								
								<div id="jobResultArea" class="panel3">
									<table class="bd-form-mini">
										<colgroup>
										    <col width="30%">
										    <col width="30%">
										    <col width="*">
									    </colgroup>
									    <tbody>
										    <tr>
												<th>주간금액</th>
												<td class="linelY"><span id="dayPrice">0</span> 원</td>
												<td class="linelY"><span id="dayPriceComment"></span></td>
											</tr>
										    <tr>
												<th>야간금액</th>
												<td class="linelY"><span id="nightPrice">0</span> 원</td>
												<td class="linelY"><span id="nightPriceComment"></span></td>
											</tr>
											<tr>
												<th>옵션금액</th>
												<td class="linelY"><span id="optionPrice">0</span> 원</td>
											</tr>
											<tr>
												<th><span style="color:#f31c1c">합계금액</span></th>
												<td class="linelY" style="color:#f31c1c"><span id="totalPrice">0</span> 원</td>
												<td></td>
											</tr>
										</tbody>
									</table>
								</div>
								
								<div id="jobResultArea" class="panel2">
									<div style="margin-top:5px"><span id="dayComment"></span></div>
									<div style="margin-top:10px"><span id="nightComment"></span></div>
								</div>
							</td>
					</tbody>
				</table>
					
				<div class="wrap-loading" style="display: none;">
					<div class="ajaxLoading">
						<div id="load">
							<img src="<c:url value='/resources/cms/img/ajax-loader.gif'/>">
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>