<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<style>
	.overlay {
		width: 150px;
		height: 68px;
		position: absolute;
		top: 68px;   /* chartArea top  */
		right: 20px; /* chartArea left */
	}
	
	table + table {
		margin-top : 10px;
		!important;
	}
	
</style>
<script type="text/javascript">
	var sessionCompany_seq = '${sessionScope.ADMIN_SESSION.company_seq}';
	var companyList;
	var smart_worker_fees;
	var smart_work_fees;
	var selectOpt = "전체";
	
	//윈도우 사이즈가 변할때 높이 넓이
	$(window).resize(function() {
	    
	});

	$(function() {
		google.charts.load('current', {packages: ['corechart', 'line']});
		
		$("#btnSrh").click( function() {
			getAccountingList();
		});
		
		$("#day_type_3").trigger('click');
	});

	function changeCompanySeq(obj){
		if(sessionCompany_seq == 1 && $("#smart_company_seq").val() == 0) {
			alert("대상이 되는 지점을 선택해주세요.");
			
			$("#company_seq").val(0);
			
			return false;
		}else if(sessionCompany_seq == 1 && $("#smart_company_seq").val() == $("#company_seq").val()) {
			alert("같은 지점을 선택할 수 없습니다.");
			
			$("#company_seq").val(0);
			
			return false;
		}
		
		getAccountingList();
	}
	
	function changeSmartCompanySeq(obj) {
		if($("#smart_company_seq").val() == $("#company_seq").val()) {
			if($("#smart_company_seq").val() != 0 && $("#company_seq").val() != 0) {
				alert("같은 지점을 선택할 수 없습니다.");
				
				$("#company_seq").val(0);
				
				return false;
			}
		}
		
		$("#company_seq").val(0);
		
		getAccountingList();
	}

	function getAccountingList() {
		if(sessionCompany_seq == 1) {
			var _data = {
				startDate: $("#startDate").val(),
		    	endDate: $("#endDate").val(),
		        srh_use_yn: 1,
		        company_seq: $("#smart_company_seq").val(),
		        Worker_company_seq: $("#company_seq").val()
			};
		}else {
			var _data = {
					startDate: $("#startDate").val(),
		    		endDate: $("#endDate").val(),
		          	srh_use_yn: 1,
		          	company_seq: $("#company_seq").val()
			};
		}
		
		var _url = "<c:url value='/admin/getCompanyAccountingList' />";
	
		commonAjax(_url, _data, true, function(data) {
			//successListener
			if (data.code == "0000") {
				// data.row
				companyList = data.companyList;
				setData(data.resultList);
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

	function setData(dataListArr){
		drawTable(dataListArr);	
		drawFeeTable(dataListArr);
	}
	
	function drawFeeTable(dataList) {
		var feesHtml = "";
		var num = 1;
		var companySeq = $("#company_seq option:selected").val();
		var mon = $("#startDate").val().substring(0,7);
		var work_fee;
		var worker_fee;
	    var seq = 0;
	    var workSettlementFeeTotal = 0;
	    var workerSettlementFeeTotal = 0;
	    var settlementFeeTotal = 0;

	    for(var i = 0; i < companyList.length; i++) {
	    	if(companyList[i].company_seq != '1' && $("#smart_company_seq").val() != companyList[i].company_seq) {
		    	feesHtml += "<tr class='list'>";
			    feesHtml += "	<td>" + num + "</td>";
		    	feesHtml += "	<td>" + mon + "</td>";
		    	feesHtml += "	<td>" + companyList[i].company_name + "</td>";
				feesHtml += "	<td class='alignRight' id='feeShare" + companyList[i].company_seq + "'>0</td>";
				feesHtml += "	<td class='alignRight' id='feeIlbo" + companyList[i].company_seq + "'>0</td>";
			    feesHtml += "	<td id='feeTot" + companyList[i].company_seq + "'>0</td>";
			    feesHtml += "</tr>";
			    feesHtml += "<input type='hidden' id='work_fee" + companyList[i].company_seq + "' value='" + companyList[i].work_fee_rate + "' />";
			    feesHtml += "<input type='hidden' id='worker_fee" + companyList[i].company_seq + "' value='" + companyList[i].worker_fee_rate + "' />";
			    num++
	    	}
	    }
	    	
		feesHtml += "<tr class='list last'>";
		feesHtml += "	<td colspan='3'>합계</td>";
		feesHtml += "	<td class='alignRight' id='workSettlementFeeTotal'>0</td>";
		feesHtml += "	<td class='alignRight' id='workerSettlementFeeTotal'>0</td>";
		feesHtml += "	<td id='settlementFeeTotal'>0</td>";
		feesHtml += "</tr>";
		
		$("#feesAccountingData").append(feesHtml);
		
	    if($("#smart_company_seq").val() == 0) {
		    for(var i = 0; i < dataList.length; i++) {
		    	//var feeShare = dataList[i].ilbo_fee * 1;
		    	//var feeIlbo = dataList[i].share_fee * 1;
		    	
		    	if($("#work_fee" + dataList[i].company_seq).val()) {
		    		work_fee = ($("#work_fee" + dataList[i].company_seq).val() * 1) * 0.01;
		    	}else {
		    		work_fee = 0;
		    	}
		    	
		    	if($("#worker_fee" + dataList[i].company_seq).val()) {
			    	worker_fee = ($("#worker_fee" + dataList[i].company_seq).val() * 1) * 0.01;
		    	}else {
		    		worker_fee = 0;
		    	}
		    	//구인정산 수수료
		    	var workSettlementFee = (Number(dataList[i].work_income) + Number(dataList[i].work_payment)) * work_fee;
		    	//구직정산 수수료
		    	var workerSettlementFee = (Number(dataList[i].worker_income) + Number(dataList[i].worker_payment)) * worker_fee;
		    	
		    	var feeTot = workSettlementFee + workerSettlementFee;

		    	$("#feeShare" + dataList[i].company_seq).text(comma(workSettlementFee));
		    	$("#feeIlbo" + dataList[i].company_seq).text(comma(workerSettlementFee));
		    	$("#feeTot" + dataList[i].company_seq).text(comma(feeTot));
		    	
		    	workSettlementFeeTotal += (workSettlementFee);
		    	workerSettlementFeeTotal += (workerSettlementFee);
		    	settlementFeeTotal += feeTot;
		    }
	    }else if($("#smart_company_seq").val() != 0 && $("#company_seq").val() == 0) {
	    	
	    	for(var i = 0; i < dataList.length; i++) {
// 		    	var feeIlbo = dataList[i].ilbo_fee * 1;
// 		    	var feeShare = dataList[i].share_fee * 1;
				var workIncome = dataList[i].work_income * 1;
				var workPayment = dataList[i].work_payment * 1;
				var workerIncome = dataList[i].worker_income * 1;
				var workerPayment = dataList[i].worker_payment * 1;
				
		    	
		    	if($("#work_fee" + dataList[i].company_seq).val()) {
			    	work_fee = ($("#work_fee" + $("#smart_company_seq").val()).val() * 1) * 0.01;
		    	}else {
		    		work_fee = 0;
		    	}
		    	
		    	if($("#worker_fee" + dataList[i].company_seq).val()) {
		    		worker_fee = ($("#worker_fee" + $("#smart_company_seq").val()).val() * 1) * 0.01;
		    	}else {
		    		worker_fee = 0;
		    	}
		    	var workSettlementFee = (workIncome + workPayment) * work_fee;
		    	var workerSettlementFee = (workerIncome + workerPayment) * worker_fee;
		    	
// 		    	var feeTot = (feeShare * work_fee) + (feeIlbo * worker_fee);
				var feeTot = workSettlementFee + workerSettlementFee;
		    	
		    	$("#feeShare" + dataList[i].company_seq).text(comma(workSettlementFee));
		    	$("#feeIlbo" + dataList[i].company_seq).text(comma(workerSettlementFee));
		    	$("#feeTot" + dataList[i].company_seq).text(comma(feeTot));
		    	
		    	workSettlementFeeTotal += (workSettlementFee);
		    	workerSettlementFeeTotal += (workerSettlementFee);
		    	settlementFeeTotal += feeTot;
		    }
	    }else if($("#smart_company_seq").val() != 0 && $("#company_seq").val() != 0) {
	    	$("#feesAccountingData .list").remove();
	    	
	    	feesHtml = '';
	    	var seq = 1;
	    	
	    	for(var i = 0; i < dataList.length; i++) {
// 	    		var feeIlbo = dataList[i].ilbo_fee * 1;
// 		    	var feeShare = dataList[i].share_fee * 1;
		    	var workIncome = dataList[i].work_income * 1;
				var workPayment = dataList[i].work_payment * 1;
				var workerIncome = dataList[i].worker_income * 1;
				var workerPayment = dataList[i].worker_payment * 1;
		    	
		    	if($("#work_fee" + dataList[i].company_seq).val()) {
		    		work_fee = ($("#work_fee" + dataList[i].company_seq).val() * 1) * 0.01;
		    	}else {
		    		work_fee = 0;
		    	}
		    	
		    	if($("#worker_fee" + dataList[i].company_seq).val()) {
		    		worker_fee = ($("#worker_fee" + dataList[i].company_seq).val() * 1) * 0.01;
		    	}else {
		    		worker_fee = 0;
		    	}
		    	var workSettlementFee = (workIncome + workPayment) * work_fee;
		    	var workerSettlementFee = (workerIncome + workerPayment) * worker_fee;
		    	
// 		    	var feeTot = (feeShare * work_fee) + (feeIlbo * worker_fee);
		    	var feeTot = workSettlementFee + workerSettlementFee;
		    	
	    		feesHtml += "<tr class='list'>";
			    feesHtml += "	<td>" + seq + "</td>";
		    	feesHtml += "	<td>" + dataList[i].ilbo_date + "</td>";
		    	feesHtml += "	<td>" + $("#company_seq option:selected").text() + "</td>";
				feesHtml += "	<td class='alignRight'>" + comma(workSettlementFee) + "</td>";
				feesHtml += "	<td class='alignRight'>" + comma(workerSettlementFee) + "</td>";
			    feesHtml += "	<td>" + comma(feeTot) + "</td>";
			    feesHtml += "</tr>";
		    	
			    workSettlementFeeTotal += workSettlementFee;
			    workerSettlementFeeTotal += workerSettlementFee;
			    settlementFeeTotal += feeTot;
		    	
		    	seq++;
	    	}
	    	
	    	feesHtml += "<tr class='list last'>";
			feesHtml += "	<td colspan='3'>합계</td>";
			feesHtml += "	<td class='alignRight'>" + comma(workSettlementFeeTotal) + "</td>";
			feesHtml += "	<td class='alignRight'>" + comma(workerSettlementFeeTotal) + "</td>";
			feesHtml += "	<td id='settlementFeeTotal'>" + comma(settlementFeeTotal) + "</td>";
			feesHtml += "</tr>";
			
			$("#feesAccountingData").append(feesHtml);
	    }
		
	    $("#workSettlementFeeTotal").text(comma(workSettlementFeeTotal));
	    $("#workerSettlementFeeTotal").text(comma(workerSettlementFeeTotal));
	    $("#settlementFeeTotal").text(comma(settlementFeeTotal));
	}

	function drawTable(dataList){
		var lineHtml ="";
		var num = 1;
		var companySeq = $("#company_seq").val();
		var mon = $("#startDate").val().substring(0,7);
		var work_fee;
		var worker_fee;
		
		$(".list").remove();
		
		if(companySeq == 0) {
			if(sessionCompany_seq != 1 && $("#smart_company_seq").val() != 0) {
				for(var i = 0; i < companyList.length; i++) {
					if(companyList[i].company_seq != '1' && companyList[i].company_seq != sessionCompany_seq) {
						lineHtml += "<tr class='list'>";
						lineHtml += "	<td>"+ num +"</td>";
						lineHtml += "	<td>"+ mon +"</td>";
						lineHtml += "	<td>" + companyList[i].company_name +"</td>";
						lineHtml += "	<td class='alignRight' id='workIncome" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td class='alignRight' id='workPayment" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td class='alignRight' id='workSettlementFee" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td class='alignRight' id='workerIncome" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td class='alignRight' id='workerPayment" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td class='alignRight' id='workerSettlementFee" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td id='totalFee" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "</tr>";
						
						num++;
					}
					
					if(sessionCompany_seq == companyList[i].company_seq) {
						lineHtml += "<tr>";
						lineHtml += "<input type='hidden' id='listWorkFee" + companyList[i].company_seq + "' value='" + companyList[i].work_fee_rate + "' />";
						lineHtml += "<input type='hidden' id='listWorkerFee" + companyList[i].company_seq + "' value='" + companyList[i].worker_fee_rate + "' />";
						lineHtml += "</tr>";
					}
				}
				
				lineHtml += "<tr class='list last'>";
				lineHtml += "	<td colspan='3'>합계</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkIncome'>0</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkPayment'>0</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkSettlementFee'>0</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkerIncome'>0</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkerPayment'>0</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkerSettlementFee'>0</td>";
				lineHtml += "	<td id='total'>0</td>";
				lineHtml += "</tr>";
			}else if(sessionCompany_seq == 1 && $("#smart_company_seq").val() != 0) {
				for(var i = 0; i < companyList.length; i++) {
					if(companyList[i].company_seq != '1' && companyList[i].company_seq != $("#smart_company_seq").val()) {
						lineHtml += "<tr class='list'>";
						lineHtml += "	<td>"+ num +"</td>";
						lineHtml += "	<td>"+ mon +"</td>";
						lineHtml += "	<td>" + companyList[i].company_name +"</td>";
						lineHtml += "	<td class='alignRight' id='workIncome" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td class='alignRight' id='workPayment" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td class='alignRight' id='workSettlementFee" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td class='alignRight' id='workerIncome" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td class='alignRight' id='workerPayment" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td class='alignRight' id='workerSettlementFee" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "	<td id='totalFee" + companyList[i].company_seq + "'>0</td>";
						lineHtml += "</tr>";
						
						num++;
					}
					
					if($("#smart_company_seq").val() == companyList[i].company_seq) {
						lineHtml += "<tr></tr>";
						lineHtml += "<input type='hidden' id='listWorkFee" + companyList[i].company_seq + "' value='" + companyList[i].work_fee_rate + "' />";
						lineHtml += "<input type='hidden' id='listWorkerFee" + companyList[i].company_seq + "' value='" + companyList[i].worker_fee_rate + "' />";
					}
				}
				
				lineHtml += "<tr class='list last'>";
				lineHtml += "	<td colspan='3'>합계</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkIncome'>0</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkPayment'>0</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkSettlementFee'>0</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkerIncome'>0</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkerPayment'>0</td>";
				lineHtml += "	<td class='alignRight' id='totalWorkerSettlementFee'>0</td>";
				lineHtml += "	<td id='total'>0</td>";
				lineHtml += "</tr>";
			}
		}else {
			if(sessionCompany_seq != '1') {
				work_fee = ($("#listWorkFee" + sessionCompany_seq).val() * 1) * 0.01;
				worker_fee = ($("#listWorkerFee" + sessionCompany_seq).val() * 1) * 0.01;
			}else {
				work_fee = ($("#listWorkFee" + $("#smart_company_seq").val()).val() * 1) * 0.01;
				worker_fee = ($("#listWorkerFee" + $("#smart_company_seq").val()).val() * 1) * 0.01;
			}
			
// 			var totShareFee = 0;
// 			var totShareFees = 0;
// 			var totIlboFee = 0;
// 			var totIlboFees = 0;
			var totWorkIncome = 0;
			var totWorkPayment = 0;
			var totWorkSettlementFee = 0;
			var totWorkerIncome = 0;
			var totWorkerPayment = 0;
			var totWorkerSettlementFee = 0;
			var totSettlement = 0;
			var tot = 0;
			var seq = 1;
			
			for(var i = 0; i < dataList.length; i++) {
// 				var tmpShareFee = dataList[i].share_fee * 1;
// 				var tmpShareFees = tmpShareFee * worker_fee;
// 				var tmpIlboFee = dataList[i].ilbo_fee * 1;
// 				var tmpIlboFees = tmpIlboFee * work_fee;
				var tmpWorkIncome = dataList[i].work_income * 1;
				var tmpWorkPayment = dataList[i].work_payment * 1 - dataList[i].work_payment_worker_fee;
				var tmpWorkSettlementFee = ((dataList[i].work_payment * 1) + tmpWorkIncome) * work_fee;
				var tmpWorkerIncome = dataList[i].worker_income * 1 - dataList[i].worker_income_worker_fee;
				var tmpWorkerPayment = dataList[i].worker_payment * 1;
				var tmpWorkerSettlementFee = (tmpWorkerPayment + (dataList[i].worker_income * 1)) * worker_fee;
				var tmpSettlement = tmpWorkIncome - tmpWorkPayment - tmpWorkSettlementFee + tmpWorkerIncome - tmpWorkerPayment - tmpWorkerSettlementFee;
				
				lineHtml += "<tr class='list'>";
				lineHtml += "	<td>"+ seq +"</td>";
				lineHtml += "	<td>"+ dataList[i].ilbo_date +"</td>";
				lineHtml += "	<td>" + $("#company_seq option:selected").text() +"</td>";
				lineHtml += "	<td class='alignRight'>" + comma(tmpWorkIncome) + "</td>";
				lineHtml += "	<td class='alignRight'>" + comma(tmpWorkPayment) + "</td>";
				lineHtml += "	<td class='alignRight'>" + comma(tmpWorkSettlementFee) + "</td>";
				lineHtml += "	<td class='alignRight'>" + comma(tmpWorkerIncome) + "</td>";
				lineHtml += "	<td class='alignRight'>" + comma(tmpWorkerPayment) + "</td>";
				lineHtml += "	<td class='alignRight'>" + comma(tmpWorkerSettlementFee) + "</td>";
				lineHtml += "	<td>" + comma(tmpSettlement) + "</td>";
				lineHtml += "</tr>";
				
// 				totShareFee += tmpShareFee;
// 				totShareFees += tmpShareFees;
// 				totIlboFee += tmpIlboFee;
// 				totIlboFees += tmpIlboFees;

				totWorkIncome += tmpWorkIncome;
				totWorkPayment += tmpWorkPayment;
				totWorkSettlementFee += tmpWorkSettlementFee;
				totWorkerIncome += tmpWorkerIncome;
				totWorkerPayment += tmpWorkerPayment;
				totWorkerSettlementFee += tmpWorkerSettlementFee;
				totSettlement += tmpSettlement;
				
				seq++;
			}
			
			lineHtml += "<tr class='list last'>";
			lineHtml += "	<td colspan='3'>합계</td>";
			lineHtml += "	<td class='alignRight'>" + comma(totWorkIncome) + "</td>";
			lineHtml += "	<td class='alignRight'>" + comma(totWorkPayment) + "</td>";
			lineHtml += "	<td class='alignRight'>" + comma(totWorkSettlementFee) + "</td>";
			lineHtml += "	<td class='alignRight'>" + comma(totWorkerIncome) + "</td>";
			lineHtml += "	<td class='alignRight'>" + comma(totWorkerPayment) + "</td>";
			lineHtml += "	<td class='alignRight'>" + comma(totWorkerSettlementFee) + "</td>";
			lineHtml += "	<td>" + comma(totSettlement) + "</td>";
			lineHtml += "</tr>";
		}
		
		$("#accountingData").append(lineHtml);
		
		if(companySeq == 0) {
			var totalWorkIncome = 0;
			var totalWorkPayment = 0;
			var totalWorkSettlementFee = 0;
			var totalWorkerIncome = 0;
			var totalWorkerPayment = 0;
			var totalWorkerSettlementFee = 0;
			var totalSettlement = 0;
			for(var i = 0; i < dataList.length; i++) {
				var dataInfo = dataList[i];
				
				if(sessionCompany_seq != '1') {
					work_fee = ($("#listWorkFee" + sessionCompany_seq).val() * 1) * 0.01;
					worker_fee = ($("#listWorkerFee" + sessionCompany_seq).val() * 1) * 0.01;
				}else {
					work_fee = ($("#listWorkFee" + $("#smart_company_seq").val()).val() * 1) * 0.01;
					worker_fee = ($("#listWorkerFee" + $("#smart_company_seq").val()).val() * 1) * 0.01;
				}
				
// 				var ilboFee = list.ilbo_fee * 1;
// 				var ilboFees = ilboFee * worker_fee;
// 				var shareFee = list.share_fee * 1;
// 				var shareFees = shareFee * work_fee;
// 				var totalFee = (shareFee - shareFees) - (ilboFee + ilboFees);
				var tmpWorkIncome = dataList[i].work_income * 1;
				var tmpWorkPayment = dataList[i].work_payment * 1 - dataList[i].work_payment_worker_fee;
				var tmpWorkSettlementFee = ((dataList[i].work_payment * 1) + tmpWorkIncome) * work_fee;
				var tmpWorkerIncome = dataList[i].worker_income * 1 - dataList[i].worker_income_worker_fee;
				var tmpWorkerPayment = dataList[i].worker_payment * 1;
				var tmpWorkerSettlementFee = (tmpWorkerPayment + (dataList[i].worker_income * 1)) * worker_fee;
				var tmpSettlement = tmpWorkIncome - tmpWorkPayment - tmpWorkSettlementFee + tmpWorkerIncome - tmpWorkerPayment - tmpWorkerSettlementFee;
				
				totalWorkIncome += tmpWorkIncome;
				totalWorkPayment += tmpWorkPayment;
				totalWorkSettlementFee += tmpWorkSettlementFee;
				totalWorkerIncome += tmpWorkerIncome;
				totalWorkerPayment += tmpWorkerPayment;
				totalWorkerSettlementFee += tmpWorkerSettlementFee;
				totalSettlement += tmpSettlement;
				
				$("#workIncome" + dataInfo.company_seq).text(comma(tmpWorkIncome));
				$("#workPayment" + dataInfo.company_seq).text(comma(tmpWorkPayment));
				$("#workSettlementFee" + dataInfo.company_seq).text(comma(tmpWorkSettlementFee));
				$("#workerIncome" + dataInfo.company_seq).text(comma(tmpWorkerIncome));
				$("#workerPayment" + dataInfo.company_seq).text(comma(tmpWorkerPayment));
				$("#workerSettlementFee" + dataInfo.company_seq).text(comma(tmpWorkerSettlementFee));
				$("#totalFee" + dataInfo.company_seq).text(comma(tmpSettlement));
				
			}
			$("#totalWorkIncome").text(comma(totalWorkIncome));
			$("#totalWorkPayment").text(comma(totalWorkPayment));
			$("#totalWorkSettlementFee").text(comma(totalWorkSettlementFee));
			$("#totalWorkerIncome").text(comma(totalWorkerIncome));
			$("#totalWorkerPayment").text(comma(totalWorkerPayment));
			$("#totalWorkerSettlementFee").text(comma(totalWorkerSettlementFee));
			$("#total").text(comma(totalSettlement));
		}
	}

	//excel 다운
	function downExcel(){
		if($("#fees").hasClass("excel1")) {
			document.defaultFrm.action = "/admin/companyAccountingExcel2";
			document.defaultFrm.submit();
		}else {
			document.defaultFrm.action = "/admin/companyAccountingExcel";
			document.defaultFrm.submit();
		}
	}
	
	//정산관리 table draw
	function fn_managementDraw() {
		$("#accountingData").show();
		$("#feesAccountingData").hide();
		$("#management").css("background-color", "#5aa5da");
		$("#management").css("color", "#fff");
		$("#fees").css("background-color", "");
		$("#fees").css("color", "");
		$("#fees").removeClass("excel1");
		$("#management").addClass("excel1");
	}
	
	//정산수수료 table draw
	function fn_feesDraw() {
		$("#accountingData").hide();
		$("#feesAccountingData").show();
		$("#fees").css("background-color", "#5aa5da");
		$("#fees").css("color", "#fff");
		$("#management").css("background-color", "");
		$("#management").css("color", "");
		$("#fees").addClass("excel1");
		$("#management").removeClass("excel1");
	}
//]]>
</script>

	<article>
		<div class="inputUI_simple">
			<table class="bd-form s-form" summary="검색 영역 입니다.">
				<caption>조회일시, 상태, 직접검색 영역</caption>
				<colgroup>
					<col width="120px" />
					<col width="*" />
					<col width="200px" />
					<col width="150px" />
				</colgroup>
				<tr>
					<th scope="row">직접검색</th>
					<td>
						<p class="floatL">
							<input type="text" id="startDate" name="startDate" class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>
							<input type="text" id="endDate" name="endDate" class="inp-field wid100 datepicker" />
						</p>
						<div class="inputUI_simple">
							<span class="contGp btnCalendar">
								<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'prev3Month' ); $('#btnSrh').trigger('click');" /><label for="day_type_1" class="label-radio">석달전</label>
								<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'prev2Month' ); $('#btnSrh').trigger('click');" /><label for="day_type_2" class="label-radio">두달전</label>
								<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'prev1Month' ); $('#btnSrh').trigger('click');" /><label for="day_type_3" class="label-radio on">한달전*</label>
								<input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'nowMonth' ); $('#btnSrh').trigger('click');"/><label for="day_type_4" class="label-radio">이번달</label>
								<div class="btn-module floatL mglS">
									<a href="#none" id="btnSrh" class="search">검색</a>
								</div>
							</span>
						</div>
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
	</article>
			
	<!-- 그래프영역 -->
	<div>
		<div class="btn-module mgtL">
			<a href="javascript:void(0)" id="management" class="btnStyle04 mglL" style="background-color : #5aa5da; color : #fff;" onClick="fn_managementDraw()">정산관리</a>
			<c:if test="${sessionScope.ADMIN_SESSION.company_seq eq '1' and sessionScope.ADMIN_SESSION.auth_level le '1' }">
				<a href="javascript:void(0)" id="fees" class="btnStyle04 mglL" onClick="fn_feesDraw()">정산수수료</a>
			</c:if>
		</div>
	</div>
	
	<div class="btn-module mgtL">
		<div class="select-inner">
			<c:if test="${sessionScope.ADMIN_SESSION.company_seq eq '1' and sessionScope.ADMIN_SESSION.auth_level le '1' }">
				<select id="smart_company_seq" name="Worker_company_seq" class="styled02 " onChange="changeSmartCompanySeq(this)">
					<option value="0">전체</option>
					<c:forEach items="${companyList}" var="item" varStatus="status">
						<c:if test="${sessionScope.ADMIN_SESSION.company_seq ne item.company_seq }">
							<option value="${item.company_seq }" >${item.company_name}</option>
						</c:if>
					</c:forEach>
				</select>
			</c:if>
			<select id="company_seq" name="company_seq" class="styled02 " onChange="changeCompanySeq(this)">
				<option value="0" >전체</option>
				<c:forEach items="${companyList}" var="item" varStatus="status">
					<c:if test="${sessionScope.ADMIN_SESSION.company_seq ne item.company_seq }">
						<option value="${item.company_seq }" >${item.company_name}</option>
					</c:if>
				</c:forEach>
			</select>
		</div>
		<a href="javascript:void(0)" id="export" class="excel mglL" onClick="downExcel()">excel</a>
	</div>
	          	
	<div class="mgtS">
		<table id="accountingData" class="bd-list" summary="게시판리스트">
			<caption>등록일시, 상태, 직접검색 영역</caption>
			<colgroup>
				<col width="50px" />
				<col width="150px" />
				<col width="150px" />
				<col width="150px" />
				<col width="150px" />
				<col width="150px" />
				<col width="150px" />
				<col width="150px" />
				<col width="150px" />
				<col width="150px" />
			</colgroup>
			<tr>
				<th rowspan="2">번호</th>
				<th rowspan="2">날짜</th>
				<th rowspan="2">정산 지점</th>
				<th colspan="3">구인제공 정산(구인처제공)</th>
				<th colspan="3">구직제공 정산(구직자제공)</th>
				<th rowspan="2">정산금 <br/>( 수입금 - 지급금  - 정산수수료 )</th>
			</tr>
			<tr>
				<th>수입금(타지점에서 받을돈)</th>
				<th>지급금(타지점으로 줄돈)</th>
				<th>정산수수료 <br/> ((수입금 + 지급금) * 구인제공 정산율)</th>
				<th>수입금(타지점에서 받을돈)</th>
				<th>지급금(타지점으로 줄돈)</th>
				<th>정산수수료 <br/> ((수입금 + 지급금) * 구직제공 정산율) </th>
			</tr>
		</table>
		<table id="feesAccountingData" class="bd-list" summary="게시판리스트" style="display: none; margin-top: 10px; !important;">
			<caption>등록일시, 상태, 직접검색 영역</caption>
			<colgroup>
				<col width="100px" />
				<col width="250px" />
				<col width="300px" />
				<col width="300px" />
				<col width="300px" />
				<col width="*" />
			</colgroup>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>정산 지점</th>
				<th>구인정산 수수료</th>
				<th>구직정산 수수료</th>
				<th>정산금 합계</th>
			</tr>
		</table>
	</div>
