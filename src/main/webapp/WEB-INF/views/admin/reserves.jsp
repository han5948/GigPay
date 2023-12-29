<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<style>
	.center-box {width:450px;height:125px;padding:20px 30px;border:1px solid #ffffff;border-radius:10px;box-sizing:border-box;margin:0 auto;}
	.left-box {display:inline-block;width:250px;height:80px;margin-right:30px;padding:13px 10px;background:#f5f5f5;border:1px solid #e2dcdc;border-radius:10px;vertical-align:middle;box-sizing:border-box;}
	.left-box strong {display:block;text-align:center;font-size:16px;}
	.right-box {display:inline-block;vertical-align:middle;}
	.right-box button {display:block;width:100px;height:30px;font-size:16px;color:#fff;border:0;border-radius:5px;}
	.right-box .btn-01 {background:#3f7dd4;}
	.right-box .btn-02 {margin-top:10px;background:#aaa;}
	.ee {margin-bottm: 15px;}
</style>
<script type="text/javascript">
	var companyList;
	var process_opt = "0:대기중;1:미승인;2:처리완료";

	$(function() {
		//$("#day_type_4").trigger('click');
	
		$("#btnSrh").on("click", function() {
			$("#list").jqGrid("setGridParam", {
				url : "/admin/reserves/getAccountList",
				datatype : "json",
				mtype : "POST",
				postData : {
					srh_use_yn : 1,
					page : 1,
					startDate : $("#startDate").val(),
					endDate : $("#endDate").val(),
					company_seq : $("#company_seq").val()
				}
			}).trigger("reloadGrid");
		
			$("#list2").jqGrid("setGridParam", {
				url : "/admin/reserves/getReservesList",
				datatype : "json",
				mtype : "POST",
				postData : {
					reserves_type : 0,
					srh_use_yn : 1,
					page : 1,
					startDate : $("#startDate").val(),
					endDate : $("#endDate").val(),
					company_seq : $("#company_seq").val()
				}
			}).trigger("reloadGrid");
		
			$("#list3").jqGrid("setGridParam", {
				url : "/admin/reserves/getReservesList",
				datatype : "json",
				mtype : "POST",
				postData : {
					reserves_type : 1,
					srh_use_yn : 1,
					page : 1,
					startDate : $("#startDate").val(),
					endDate : $("#endDate").val(),
					company_seq : $("#company_seq").val()
				}
			}).trigger("reloadGrid");
			
			$("#list4").jqGrid("setGridParam", {
				url : "/admin/reserves/getAccountDetailList",
				datatype : "json",
				mtype : "POST",
				postData : {
					reserves_type : 1,
					srh_use_yn : 1,
					page : 1,
					startDate : $("#startDate").val(),
					endDate : $("#endDate").val(),
					company_seq : $("#company_seq").val()
				}
			}).trigger("reloadGrid");
		});

		var tmpDate = new Date();
		var startDate = new Date(tmpDate.getFullYear(), tmpDate.getMonth(), 1);
		var settingDate = new Date(tmpDate.getFullYear(), tmpDate.getMonth() + 1, 0);
		settingDate.setMonth(settingDate.getMonth());
		
		$('#startDate').datepicker('setDate', startDate); 
		$("#endDate").datepicker('setDate', settingDate);
	
		$.ajax({
			url: "/admin/reserves/getCompanyList", 
			type: "POST", 
			dataType: "json",
			data: {sidx : 'company_seq' },
			success: function (data) {
				
			},
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				if ( data.status == "901" ) {
					location.href = "/admin/login";
				}
			},
			complete: function(data) {
				companyList = data.responseJSON.companyList;
				
				$("#tabs").tabs();
				
				$("#tabs").css("width", "1500px");
				
				$("#list").jqGrid({
					url : "/admin/reserves/getAccountList",
					datatype : "json",
					mtype : "POST",
					postData : {
						srh_use_yn : 1,
						page : 1,
						startDate : $("#startDate").val(),
						endDate : $("#endDate").val(),
						company_seq : $("#company_seq").val()
					},
					rowList : [25, 50, 100],
					height : jqHeight - 50,
					width : 1430,
					scrollerbar : true,
					footerrow: true,
					rowNum : 50,
					colNames : ['순번', '날짜', '지점명', '입/출금', '금액', '내역', '지점잔액', '사업자 번호', '상호', '대표자명', '사업장 주소', '업태', '종목', '이메일'],
					colModel : [
						{name : 'account_seq', index : 'account_seq', align : 'center', hidden : true},
						{name : 'account_date', index : 'account_date', align : 'center', sortable: true, loadonce : true},
						{name : 'dest_company_seq', index : 'dest_company_seq', align : 'center', formatter : fn_company},
						{name : 'account_type', index : 'account_type', align : 'center'
							, formatter : function formatData(data) {
								if(data == 0) {
									return "입금";
								}else {
									return "출금";
								}
							}
							, cellattr : function(i, e, a) {
								if(a.account_type == '0') {
									return ' style="color : blue;"';
								}else {
									return ' style="color : red;"';
								}
							}
						},
						{name : 'account_price', index : 'account_price', align : 'right', summaryType: "sum",
	                     	formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}},
						{name : 'account_flag', index : 'account_flag', align : 'center'
							, formatter : function formatData(data, i) {
								if(data == 0) {
									return "충전";
								}else if(data == 1) {
									return "출금";
								}else if(data == 2) {
									return "구인제공 정산 수수료";
								}else if(data == 3) {
									return "구직제공 정산 수수료";
								}else if(data == 4) {
									return "구인지점 정산료";
								}else if(data == 5) {
									return "구직지점 정산료";
								}else if(data == 6) {
									return "22년 12월 정산 누락분 출금";
								}else if(data == 7) {
									return "22년 12월 정산 누락분 입금";
								}
							}
						},
						{name : 'account_balance', index : 'account_balance', align : 'right', formatter : fn_commaData},
						{name : 'company_num', index : 'company_num', hidden : true},
						{name : 'business_name', index : 'business_name', hidden : true},
						{name : 'company_owner', index : 'company_owner', hidden : true},
						{name : 'company_addr', index : 'company_addr', hidden : true},
						{name : 'company_business', index : 'company_business', hidden : true},
						{name : 'company_sector', index : 'company_sector', hidden : true},
						{name : 'company_email', index : 'company_email', hidden : true},
					],
					jsonReader: {
		                id: "account_seq",
	        			repeatitems: false
	            	},
					//pager : "#paging",
					loadComplete : function(data) {
						var text = 0;
						
						text = data.totalAcc; 
						text *= 1;
						text = comma(text) + ' 원';
						
						$("#totalAcc").text(text);
						
						if(data.rows != null) {
							var ids = $("#list").jqGrid('getDataIDs');   
							
							var priceSumTatal = 0;
				            
			            	for(var i = 0; i < data.rows.length; i++) {
				            	if(data.rows[i].account_type == '0') {
			            			priceSumTatal += (data.rows[i].account_price * 1);
			            		}else {
				            		priceSumTatal -= (data.rows[i].account_price * 1);
			            		}
			            	}
			            	
			          		//footer 적용
				            $("#list").jqGrid("footerData", "set", {
								account_date : "합계",
								account_price : priceSumTatal,
								account_balance : text
							});
						}
					},
					loadError : function(xhr, status, error) {
					}
				});
			
				$("#list4").jqGrid({
					url : "/admin/reserves/getAccountDetailList",
					datatype : "json",
					mtype : "POST",
					postData : {
						srh_use_yn : 1,
						page : 1,
						startDate : $("#startDate").val(),
						endDate : $("#endDate").val(),
						company_seq : $("#company_seq").val()
					},
					rowList : [25, 50, 100],
					height : jqHeight - 50,
					width : 1430,
					scrollerbar : true,
					footerrow: true,
					rowNum : 50,
					colNames : ['순번', '날짜', '지점명', '입/출금', '금액', '실 금액', '내역'],
					colModel : [
						{name : 'account_seq', index : 'account_seq', align : 'center', hidden : true},
						{name : 'account_date', index : 'account_date', align : 'center'},
						{name : 'dest_company_seq', index : 'dest_company_seq', align : 'center', formatter : fn_company},
						{name : 'account_type', index : 'account_type', align : 'center'
							, formatter : function formatData(data) {
								if(data == 0) {
									return "입금";
								}else {
									return "출금";
								}
							}
							, cellattr : function(i, e, a) {
								if(a.account_type == '0') {
									return ' style="color : blue;"';
								}else {
									return ' style="color : red;"';
								}
							}
						},
						{name : 'account_price', index : 'account_price', align : 'right', formatter : fn_commaData},
						{name : 'account_price2', index : 'account_price2', align : 'right', hidden : true
							, formatter : function (i, e) {
								if(e.rowData.account_type == '0') {
									return fn_commaData(e.rowData.account_price);
								}else 
									return fn_commaData("-" + e.rowData.account_price);
							}
						},
						{name : 'account_flag', index : 'account_flag', align : 'center'
							, formatter : function formatData(data, i) {
								
								if(data == 0) {
									return "충전";
								}else if(data == 1) {
									return "출금";
								}else if(data == 2) {
									return "구인제공 정산 수수료";
								}else if(data == 3) {
									return "구직제공 정산 수수료";
								}else if(data == 4) {
									return "구인지점 정산료";
								}else if(data == 5) {
									return "구직지점 정산료";
								}
							}
						}
					],
					jsonReader: {
		                id: "account_seq",
	        			repeatitems: false
	            	},
					//pager : "#paging4",
					loadComplete : function(data) {
						if(data.rows != null) {
							var ids = $("#list").jqGrid('getDataIDs');   
							var priceSumTatal 		= 0;
				            
			            	for(var i = 0; i < data.rows.length; i++) {
				            	if(data.rows[i].account_type == '0') {
			            			priceSumTatal += (data.rows[i].account_price * 1);
			            		}else {
				            		priceSumTatal -= (data.rows[i].account_price * 1);
			            		}
			            	}
			            	
			          		//footer 적용
				            $("#list4").jqGrid("footerData", "set", {
								account_date		: "합계",
								account_price	: priceSumTatal
							});
						}
					},
					loadError : function(xhr, status, error) {
					}
				});
			
				$("#list2").jqGrid({
					url : "/admin/reserves/getReservesList",
					datatype : "json",
					mtype : "POST",
					postData : {
						reserves_type : 0,
						srh_use_yn : 1,
						page : 1,
						startDate : $("#startDate").val(),
						endDate : $("#endDate").val(),
						company_seq : $("#company_seq").val()
					},
					rowList : [25, 50, 100],
					height : jqHeight - 50,
					width : 1430,
					scrollerbar : true,
					footerrow: true,
					rowNum : 50,
					cellEdit : true,
					cellsubmit : 'remote',
					cellurl : '/admin/reserves/updateReserves',
					colNames : ['순번', '지점명', '지점 번호', '날짜', '입금액', '요청자', '요청자 메모', '처리상태', '처리자 메모', '처리자', '처리시간', '입출금 상태'],
					colModel : [
						{name : 'reserves_seq', index : 'reserves_seq', align : 'center', hidden : true},
						{name : 'company_seq', index : 'company_seq', align : 'center', formatter : fn_company,
							cellattr : function(i, e) {
								if(i == '0') {
									return ' colspan="2"';
								}
							}
						},
						{name : 'company_seq', index : 'company_seq_hidden', align : 'center', hidden : true},
						{name : 'reserves_date', index : 'reserves_date', align : 'center', 
							cellattr : function(i, e) {
								if(i == '0') {
									return 'style="display: none;"';
								}
							}
						},
						{name : 'reserves_price', index : 'reserves_price', align : 'right', formatter : fn_commaData
							, editable : function(i) {
								if(i.rowid == 0) {
									return false;
								}
								
								var status = $("#list2").jqGrid("getRowData", i.rowid).reserves_process_status;
								
								if(status != 0) {
									return false;
								}
								
								if(${sessionScope.ADMIN_SESSION.auth_level eq 0} 
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 1 })
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 13 })
								&& company_seq == 0) {
									return true;
								}else {
									return false;
								}
							}
						},
						{name : 'reserves_req_admin', index : 'reserves_req_admin', align : 'center',
							formatter : function(i, e) {
								if(e.rowId == '0') {
									return '';								
								}else {
									return i;
								}
							}
						},
						{name : 'reserves_req_memo', index : 'reserves_req_memo', align : 'center',
							formatter : function(i, e) {
								if(e.rowId == '0') {
									return '';								
								}else {
									if(i)
										return i;
									else 
										return '';
								}
							}
						},
						{name : 'reserves_process_status', index : 'reserves_process_status', align : 'center' 
							, formatter : "select"
							, editable : function(i) {
								if(i.rowid == 0) {
									return false;
								}
								
								var status = $("#list2").jqGrid("getRowData", i.rowid).reserves_process_status;
								
								if(status != 0) {
									return false;
								}
								
								if(${sessionScope.ADMIN_SESSION.auth_level eq 0} 
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 1 })
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 13 })) {
									return true;
								}else {
									return false;
								}
							}
							, edittype : "select"
							, editoptions : {value: process_opt}
						},
						{name : 'reserves_res_memo', index : 'reserves_res_memo', align : 'center'
							, editable : function(i) {
								if(i.rowid == 0) {
									return false;
								}
								
								var status = $("#list2").jqGrid("getRowData", i.rowid).reserves_process_status;
								
								if(status != 0) {
									return false;
								}
								
								if(${sessionScope.ADMIN_SESSION.auth_level eq 0} 
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 1 })
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 13 })) {
									return true;
								}else {
									return false;
								}
							}, 
							formatter : function(i, e) {
								if(e.rowId == '0') {
									return '';								
								}else {
									if(i)
										return i;
									else 
										return '';
								}
							}
						},
						{name : 'reserves_res_admin', index : 'reserves_res_admin', align : 'center',
							formatter : function(i, e) {
								if(e.rowId == '0') {
									return '';								
								}else {
									if(i)
										return i;
									else 
										return '';
								}
							}
						},
						{name : 'reserves_res_date', index : 'reserves_res_date', align : 'center',
							formatter : function(i, e) {
								if(e.rowId == '0') {
									return '';								
								}else {
									if(i)
										return i;
									else 
										return '';
								}
							}
						},
						{name : 'reserves_type', index : 'reserves_type', align : 'center', hidden : true}
					],
					jsonReader: {
		                id: "reserves_seq",
	        			repeatitems: false
	            	},
					//pager : "#paging2",
					beforeSubmitCell : function(rowId, cellName, value) {
						var tr_callback = '';
						var tr_phone = '';
						
						for(var i = 0; i < companyList.length; i++) {
							if(companyList[i].company_seq == $("#list2").jqGrid("getRowData", rowId).company_seq) {
								tr_phone = companyList[i].company_phone;
							}
							
							if(companyList[i].company_seq == '1') {
								tr_callback = companyList[i].company_phone;
							}
						}
					
						if(cellName == 'reserves_res_memo') {
							return {
								cellname : cellName,
								reserves_res_memo : $("#list2").jqGrid("getRowData", rowId).reserves_res_memo,
								reserves_seq : $("#list2").jqGrid("getRowData", rowId).reserves_seq
							}	
						}else if(cellName == 'reserves_process_status') {
							if($("#list2").jqGrid("getRowData", rowId).reserves_process_status == '대기중') {
								return {
									cellname : cellName,
									reserves_res_memo : $("#list2").jqGrid("getRowData", rowId).reserves_res_memo,
									reserves_process_status : '0',
									reserves_seq : $("#list2").jqGrid("getRowData", rowId).reserves_seq,
									reserves_price : $("#list2").jqGrid("getRowData", rowId).reserves_price,
									reserves_type : $("#list2").jqGrid("getRowData", rowId).reserves_type,
									company_seq : $("#list2").jqGrid("getRowData", rowId).company_seq,
									tr_callback : tr_callback,
									tr_phone : tr_phone
								}		
							}else if($("#list2").jqGrid("getRowData", rowId).reserves_process_status == '미승인') {
								return {
									cellname : cellName,
									reserves_res_memo : $("#list2").jqGrid("getRowData", rowId).reserves_res_memo,
									reserves_process_status : '1',
									reserves_seq : $("#list2").jqGrid("getRowData", rowId).reserves_seq,
									reserves_price : $("#list2").jqGrid("getRowData", rowId).reserves_price,
									reserves_type : $("#list2").jqGrid("getRowData", rowId).reserves_type,
									company_seq : $("#list2").jqGrid("getRowData", rowId).company_seq,
									tr_callback : tr_callback,
									tr_phone : tr_phone
								}
							}else {
								return {
									cellname : cellName,
									reserves_res_memo : $("#list2").jqGrid("getRowData", rowId).reserves_res_memo,
									reserves_process_status : '2',
									reserves_seq : $("#list2").jqGrid("getRowData", rowId).reserves_seq,
									reserves_price : $("#list2").jqGrid("getRowData", rowId).reserves_price,
									reserves_type : $("#list2").jqGrid("getRowData", rowId).reserves_type,
									company_seq : $("#list2").jqGrid("getRowData", rowId).company_seq,
									tr_callback : tr_callback,
									tr_phone : tr_phone
								}
							}
						}else {
							return {
								cellname : cellName,
								reserves_seq : $("#list2").jqGrid("getRowData", rowId).reserves_seq,
								reserves_price : $("#list2").jqGrid("getRowData", rowId).reserves_price 
							}
						}	
					},
					afterSubmitCell : function(res) {
						$("#list").trigger("reloadGrid");
						$("#list2").trigger("reloadGrid");
					},
					loadComplete : function(data) {
						if(data.rows != null) {
							var ids = $("#list").jqGrid('getDataIDs');   
							var priceSumTatal 		= 0;
				            
				            for(var i = 0; i < data.rows.length; i++) {
			            		priceSumTatal += (data.rows[i].reserves_price * 1);
				            }
				            
				          	//footer 적용
				            $("#list2").jqGrid("footerData", "set", {
								reserves_date : "합계",
								reserves_price : priceSumTatal
							});
						}
					},
					loadError : function(xhr, status, error) {
					}
				});
			
				$("#list3").jqGrid({
					url : "/admin/reserves/getReservesList",
					datatype : "json",
					mtype : "POST",
					postData : {
						reserves_type : 1,
						srh_use_yn : 1,
						page : 1,
						startDate : $("#startDate").val(),
						endDate : $("#endDate").val(),
						company_seq : $("#company_seq").val()
					},
					rowList : [25, 50, 100],
					height : jqHeight - 50,
					width : 1430,
					scrollerbar : true,
					footerrow: true,
					rowNum : 50,
					cellEdit : true,
					cellsubmit : 'remote',
					cellurl : '/admin/reserves/updateReserves',
					colNames : ['순번', '지점명', '지점 순번','날짜', '출금액', '요청자', '요청자 메모', '처리상태', '처리자 메모', '처리자', '처리시간', '입출금 상태'],
					colModel : [
						{name : 'reserves_seq', index : 'reserves_seq', align : 'center', hidden : true},
						{name : 'company_seq', index : 'company_seq', align : 'center', formatter : fn_company,
							cellattr : function(i, e) {
								if(i == '0') {
									return ' colspan="2"';
								}
							}
						},
						{name : 'company_seq', index : 'company_seq_hidden', align : 'center', hidden : true},
						{name : 'reserves_date', index : 'reserves_date', align : 'center', 
							cellattr : function(i, e) {
								if(i == '0') {
									return 'style="display: none;"';
								}
							}
						},
						{name : 'reserves_price', index : 'reserves_price', align : 'right', formatter : fn_commaData 
							, editable : function(i) {
								if(i.rowid == 0) {
									return false;
								}
								
								var status = $("#list3").jqGrid("getRowData", i.rowid).reserves_process_status;
								
								if(status != 0) {
									return false;
								}
								
								var company_seq = $("#list3").jqGrid("getRowData", i.rowid).company_seq;
								
								if(${sessionScope.ADMIN_SESSION.auth_level eq 0} 
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 1 })
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 13 })) {
									return true;
								}else {
									return false;
								}
							}
						},
						{name : 'reserves_req_admin', index : 'reserves_req_admin', align : 'center',
							formatter : function(i, e) {
								if(e.rowId == '0') {
									return '';								
								}else {
									return i;
								}
							}
						},
						{name : 'reserves_req_memo', index : 'reserves_req_memo', align : 'center',
							formatter : function(i, e) {
								if(e.rowId == '0') {
									return '';								
								}else {
									if(i)
										return i;
									else 
										return '';
								}
							}
						},
						{name : 'reserves_process_status', index : 'reserves_process_status', align : 'center' 
							, formatter : "select"
							, editable : function(i) {
								var status = $("#list3").jqGrid("getRowData", i.rowid).reserves_process_status;
								
								var company_seq = $("#list3").jqGrid("getRowData", i.rowid).company_seq;
								
								if(${sessionScope.ADMIN_SESSION.auth_level eq 0} 
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 1 })
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 13 })) {
									return true;
								}else {
									return false;
								}
							}
							, edittype : "select"
							, editoptions : {value: process_opt}
						},
						{name : 'reserves_res_memo', index : 'reserves_res_memo', align : 'center'
							, editable : function(i) {
								if(i.rowid == 0) {
									return false;
								}
								
								var status = $("#list3").jqGrid("getRowData", i.rowid).reserves_process_status;
								
								if(status != 0) {
									return false;
								}
								
								var company_seq = $("#list3").jqGrid("getRowData", i.rowid).company_seq;
								
								if(${sessionScope.ADMIN_SESSION.auth_level eq 0} 
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 1 })
								|| (${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq eq 13 })) {
									return true;
								}else {
									return false;
								}
							},
							formatter : function(i, e) {
								if(e.rowId == '0') {
									return '';								
								}else {
									if(i)
										return i;
									else 
										return '';
								}
							}
						},
						{name : 'reserves_res_admin', index : 'reserves_res_admin', align : 'center',
							formatter : function(i, e) {
								if(e.rowId == '0') {
									return '';								
								}else {
									if(i)
										return i;
									else 
										return '';
								}
							}
						},
						{name : 'reserves_res_date', index : 'reserves_res_date', align : 'center',
							formatter : function(i, e) {
								if(e.rowId == '0') {
									return '';								
								}else {
									if(i)
										return i;
									else 
										return '';
								}
							}
						},
						{name : 'reserves_type', index : 'reserves_type', align : 'center', hidden : true}
					],
					jsonReader: {
		                id: "reserves_seq",
		        		repeatitems: false
		            },
					//pager : "#paging3",
					beforeSubmitCell : function(rowId, cellName, value) {
						var tr_callback = '';
						var tr_phone = '';
						
						for(var i = 0; i < companyList.length; i++) {
							if(companyList[i].company_seq == $("#list3").jqGrid("getRowData", rowId).company_seq) {
								tr_phone = companyList[i].company_phone;
							}
							
							if(companyList[i].company_seq == '1') {
								tr_callback = companyList[i].company_phone;
							}
						}
						
						if(cellName == 'reserves_res_memo') {
							return {
								cellname : cellName,
								reserves_res_memo : $("#list3").jqGrid("getRowData", rowId).reserves_res_memo,
								reserves_seq : $("#list3").jqGrid("getRowData", rowId).reserves_seq
							}	
						}else if(cellName == 'reserves_process_status') {
							if($("#list3").jqGrid("getRowData", rowId).reserves_process_status == '대기중') {
								return {
									cellname : cellName,
									reserves_res_memo : $("#list3").jqGrid("getRowData", rowId).reserves_res_memo,
									reserves_process_status : '0',
									reserves_seq : $("#list3").jqGrid("getRowData", rowId).reserves_seq,
									reserves_price : $("#list3").jqGrid("getRowData", rowId).reserves_price,
									reserves_type : $("#list3").jqGrid("getRowData", rowId).reserves_type,
									company_seq : $("#list3").jqGrid("getRowData", rowId).company_seq,
									tr_callback : tr_callback,
									tr_phone : tr_phone
								}		
							}else if($("#list3").jqGrid("getRowData", rowId).reserves_process_status == '미승인') {
								return {
									cellname : cellName,
									reserves_res_memo : $("#list3").jqGrid("getRowData", rowId).reserves_res_memo,
									reserves_process_status : '1',
									reserves_seq : $("#list3").jqGrid("getRowData", rowId).reserves_seq,
									reserves_price : $("#list3").jqGrid("getRowData", rowId).reserves_price,
									reserves_type : $("#list3").jqGrid("getRowData", rowId).reserves_type,
									company_seq : $("#list3").jqGrid("getRowData", rowId).company_seq,
									tr_callback : tr_callback,
									tr_phone : tr_phone
								}
							}else {
								return {
									cellname : cellName,
									reserves_res_memo : $("#list3").jqGrid("getRowData", rowId).reserves_res_memo,
									reserves_process_status : '2',
									reserves_seq : $("#list3").jqGrid("getRowData", rowId).reserves_seq,
									reserves_price : $("#list3").jqGrid("getRowData", rowId).reserves_price,
									reserves_type : $("#list3").jqGrid("getRowData", rowId).reserves_type,
									company_seq : $("#list3").jqGrid("getRowData", rowId).company_seq,
									tr_callback : tr_callback,
									tr_phone : tr_phone
								}
							}	
						}else {
							return {
								cellname : cellName,
								reserves_seq : $("#list3").jqGrid("getRowData", rowId).reserves_seq,
								reserves_price : $("#list3").jqGrid("getRowData", rowId).reserves_price 
							}
						}	
					},
					afterSubmitCell : function(res) {
						$("#list").trigger("reloadGrid");
						$("#list3").trigger("reloadGrid");
						
						$("#totalAcc").val($("#list").jqGrid("getRowData", 0).account_balance);
					},
					loadComplete : function(data) {
						if(data.rows != null) {
							var ids = $("#list").jqGrid('getDataIDs');   
							var priceSumTatal 		= 0;
				            
				            for(var i = 0; i < data.rows.length; i++) {
			            		priceSumTatal += (data.rows[i].reserves_price * 1);
				            }
			          	
				            //footer 적용
			            	$("#list3").jqGrid("footerData", "set", {
								reserves_date		: "합계",
								reserves_price	: priceSumTatal
							});
						}
					},
					loadError : function(xhr, status, error) {
					}
				});
			}
		});
	});

	function fn_commaData(data) {
		return comma(data);
	}
	
	function fn_company(data, row) {
		if(row.rowId == '0') {
			return '';	
		}
		
		for(var i = 0; i < companyList.length; i++) {
			if(data == companyList[i].company_seq) {
				return companyList[i].company_name;
			}	
		}
	}

	function openIrPopup(popupId, status) {
		var $popUp = $("#" + popupId);
		
		$(".dimmed").fadeIn();
		$popUp.show();
		
		if(popupId == 'popup-layer2') {
			var text = $("#totalAcc").text();
			
			text = text.substring(0, text.length - 2);
			text = text.replace(/,/gi, "");
			
			if(sessionCompanySeq != '1' && sessionCompanySeq != '13')
				text = Number(text) - 2000000;
			else
				text = Number(text);
			
			if(text < 0) 
				text = 0;
			
			$("#withdrawPrice").text("출금가능 금액 : " + comma(text) + " 원");
		}
		
		$popUp.css({
			'margin-top': '-10%',
			'margin-right': '23%',
			'height': '42%',
			'width': '23%'
		});
	}

	var sessionCompanySeq =  ${sessionScope.ADMIN_SESSION.company_seq };

	function fn_deposit() {
		var regExp = /^[0-9]+$/;
		
		if(!regExp.test($("#depositFrm input[name=reserves_price]").val())) {
			alert("숫자만 입력 가능합니다.");
			
			return false;
		}
		
		if($("#depositFrm input[name=reserves_price]").val() < 100000) {
			alert("10만원 이상만 충전할 수 있습니다.");
			
			return false;
		}
		for(var i = 0; i < companyList.length; i++) {
			if(companyList[i].company_seq == '1') {
				$("#depositFrm").append("<input type='hidden' name='tr_phone' value='" + companyList[i].company_phone + "' />");
			}
			
			if(sessionCompanySeq == companyList[i].company_seq) {
				$("#depositFrm").append("<input type='hidden' name='tr_callback' value='" + companyList[i].company_phone + "' />");
			}
		}
		
		$("#depositFrm").ajaxForm({
			url : "/admin/reserves/insertReserves",
			enctype : "multipart/form-data",
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				$("#list2").trigger("reloadGrid");
				
				toastOk('입금신청이 완료되었습니다.');
				
				closePop('popup-layer');
			},
			beforeSend : function(xhr) {
				 xhr.setRequestHeader("AJAX", true);
				 $(".wrap-loading").show();
			},
	   		error : function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					toastFail("수정 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		}).submit();
	}

	function fn_withdraw() {
		var regExp = /^[0-9]+$/;
		
		if(!regExp.test($("#withdrawFrm input[name=reserves_price]").val())) {
			alert("숫자만 입력 가능합니다.");
			
			return false;
		}
		
		var text = $("#withdrawPrice").text();
		var accountPrice = $("#withdrawFrm input[name=reserves_price]").val();
		text = text.substring(0, text.length - 2);
		text = text.substring(9, text.length);
		text = text.replace(/,/gi, "");
		text *= 1;
		/* 
		if(Number(text) < $("#withdrawFrm input[name=reserves_price]").val()) {
			alert("출금할 수 없는 금액입니다.");
			
			return false;
		}
		 */
		for(var i = 0; i < companyList.length; i++) {
			if(companyList[i].company_seq == '1')
				$("#withdrawFrm").append("<input type='hidden' name='tr_phone' value='" + companyList[i].company_phone + "' />");
			
			if(sessionCompanySeq == companyList[i].company_seq)
				$("#withdrawFrm").append("<input type='hidden' name='tr_callback' value='" + companyList[i].company_phone + "' />");
		}
		
		$("#withdrawFrm").ajaxForm({
			url : "/admin/reserves/insertReserves",
			enctype : "multipart/form-data",
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				$("#list3").trigger("reloadGrid");
				
				toastOk('출금신청이 완료되었습니다.');
				
				closePop('popup-layer2');
				
			},
			beforeSend : function(xhr) {
				 xhr.setRequestHeader("AJAX", true);
				 $(".wrap-loading").show();
			},
	   		error : function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					toastFail("수정 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		}).submit();
	}

	function changeCompanySeq(obj) {
		$("#list").jqGrid("setGridParam", {
			url : "/admin/reserves/getAccountList",
			datatype : "json",
			mtype : "POST",
			postData : {
				srh_use_yn : 1,
				page : 1,
				startDate : $("#startDate").val(),
				endDate : $("#endDate").val(),
				company_seq : $("#company_seq").val()
			}
		}).trigger("reloadGrid");
		
		$("#list4").jqGrid("setGridParam", {
			url : "/admin/reserves/getAccountDetailList",
			datatype : "json",
			mtype : "POST",
			postData : {
				srh_use_yn : 1,
				page : 1,
				startDate : $("#startDate").val(),
				endDate : $("#endDate").val(),
				company_seq : $("#company_seq").val()
			}
		}).trigger("reloadGrid");
		
		$("#list2").jqGrid("setGridParam", {
			url : "/admin/reserves/getReservesList",
			datatype : "json",
			mtype : "POST",
			postData : {
				reserves_type : 0,
				srh_use_yn : 1,
				page : 1,
				startDate : $("#startDate").val(),
				endDate : $("#endDate").val(),
				company_seq : $("#company_seq").val()
			}
		}).trigger("reloadGrid");
		
		$("#list3").jqGrid("setGridParam", {
			url : "/admin/reserves/getReservesList",
			datatype : "json",
			mtype : "POST",
			postData : {
				reserves_type : 1,
				srh_use_yn : 1,
				page : 1,
				startDate : $("#startDate").val(),
				endDate : $("#endDate").val(),
				company_seq : $("#company_seq").val()
			}
		}).trigger("reloadGrid");
	}

	//Layer popup 닫기
	function closePop(popupId) {
		var $popUp = $('#'+ popupId);
	  	$dimmed.fadeOut();
	  	$popUp.hide();
	
	  	$("#depositFrm input[name=reserves_price]").val('');
	  	$("#depositFrm input[name=reserves_req_memo]").val('');
	  	$("#withdrawFrm input[name=reserves_price]").val('');
	  	$("#withdrawFrm input[name=reserves_req_memo]").val('');
	}
	
	function fn_reload() {
		$("#list").trigger("reloadGrid");
		$("#list2").trigger("reloadGrid");
		$("#list3").trigger("reloadGrid");
	}
	
	function fn_excel() {
		var frm = document.getElementById("defaultFrm");
		
		frm.action = "/admin/reserves/getAccountExcel";
		frm.submit();
	}
</script>
	<article>
		<div class="center-box" style="margin-top: 0.5%; margin-bottom: 0.5%; margin-left: 200px;">
			<div class="left-box">
				<strong style="margin-bottom : 15px;">적립금 잔액</strong>
				<strong id="totalAcc"></strong>
			</div>
			<c:if test="${sessionScope.ADMIN_SESSION.auth_level ne 0 }">
				<div class="right-box">
					<button type="button" class="btn-01" onclick="openIrPopup('popup-layer')">충전</button>
					<button type="button" class="btn-02" onclick="openIrPopup('popup-layer2')">출금하기</button>
				</div>
			</c:if>
		</div>
		<div class="inputUI_simple">
		    <table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
				<caption>등록일시, 상태, 직접검색 영역</caption>
		        <colgroup>
		        	<col width="120px" />
		        	<col width="*" />
		        	<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0 or (sessionScope.ADMIN_SESSION.auth_level eq '1' and sessionScope.ADMIN_SESSION.company_seq eq '13') }">
		        	<col width="120px" />
		        	<col width="" />
		        	</c:if>
		        </colgroup>
		        <tr>
		        	<th scope="row">직접검색</th>
		       		<td >
		            	<p class="floatL">
							<input type="text" id="startDate" name="start_date" class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>
							<input type="text" id="endDate" name="end_date" class="inp-field wid100 datepicker" />
						</p>
						<div class="inputUI_simple">
							<span class="contGp btnCalendar">
								<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'prev3Month' ); $('#btnSrh').trigger('click');" /><label for="day_type_1" class="label-radio">석달전</label>
								<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'prev2Month' ); $('#btnSrh').trigger('click');" /><label for="day_type_2" class="label-radio">두달전</label>
								<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'prev1Month' ); $('#btnSrh').trigger('click');" /><label for="day_type_3" class="label-radio">한달전</label>
								<input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'nowMonth' ); $('#btnSrh').trigger('click');" /><label for="day_type_4" class="label-radio on">이번달*</label>
								<div class="btn-module floatL mglS">
									<a href="#none" id="btnSrh" class="search">검색</a>
									<a href="#none" id="btnRel" class="btnStyle05" onclick="fn_reload()">새로고침</a>
								</div>
							</span>
						</div>
						
						<c:if test="${sessionScope.ADMIN_SESSION.company_seq ne '13' and sessionScope.ADMIN_SESSION.auth_level eq '1' }">
							<div class="btn-module floatR mgrL">
								<a href="#none" id="btnExcel" class="btnStyle05" onclick="fn_excel()">계산서 엑셀</a>
							</div>
						</c:if>
						
		          	</td>
		          	
		          	<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0 or (sessionScope.ADMIN_SESSION.auth_level eq '1' and sessionScope.ADMIN_SESSION.company_seq eq '13') }">
			          	<th scope="row" style="width:70px;">지점검색</th>
			          	<td>
			          		<div class="select-inner">
								<select id="company_seq" name="company_seq" class="styled02 " onChange="changeCompanySeq(this)">
									<option value="" >전체</option>
									<c:forEach items="${companyList}" var="item" varStatus="status">
				         				<option value="${item.company_seq }" >${item.company_name}</option>
				   					</c:forEach>
		         				</select>
							</div>
			          	</td>
			          </c:if>
			          
		        </tr>
			</table>
	    </div>
	</article>
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">입출금 내역</a></li>
			<li><a href="#tabs-2">입금 관리</a></li>
			<li><a href="#tabs-3">출금 관리</a></li>
			<li><a href="#tabs-4">정산 상세 내역</a></li>
		</ul>
		<div id="tabs-1">
			<table id="list"></table>
			<div id="paging"></div>
		</div>
		<div id="tabs-4">
			<table id="list4"></table>
			<div id="paging4"></div>
		</div>
		<div id="tabs-2">
			<table id="list2"></table>
			<div id="paging2"></div>
		</div>
		<div id="tabs-3">
			<table id="list3"></table>
			<div id="paging3"></div>
		</div>
	</div>
	</form>
	<form id="depositFrm">
		<div id="popup-layer">
			<header class="header">
				<h1 class="tit" id="depositLayer">충전하기</h1>
				<a href="#none" class="close" onclick="javascript:closePop('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
			</header>
			
			<section>
				<div class="cont-box">
					<div>
						<strong style="font-size: 30px;">입금계좌안내</strong><br>
							<div style="display: inline-block;width: 350px;height: 62px;margin-top: 3%;margin-bottom: 3%;margin-right: 10px;margin-left: 12px;padding: 13px 10px;background: #c2d9f1;border: 1px solid #ada8a8;vertical-align: middle;box-sizing: border-box;text-align: center;">
								(주)스마트하우스 기업은행<br>
								&nbsp;&nbsp;&nbsp;025-095039-04-014
							</div>
					</div>
					<article>
						<table class="bd-form" summary="충전">
							<caption>충전</caption>					
							<colgroup>
								<col width="100px" />
								<col width="*" />
							</colgroup>
							<tbody id="depositBody">
								<tr>
									<th>충전금액</th>
									<td class="linelY">
										<input type="text" class="inp-field wid200" name="reserves_price">
										<br><strong>(최소 100,000원 이상)</strong>
									</td>
								</tr>
								<tr>
									<th>요청사항</th>
									<td class="linelY">
										<input type="text" class="inp-field wid200" name="reserves_req_memo">
									</td>
								</tr>
							</tbody>
						</table>
					</article>
					
					<div class="btn-module mgtL" style="margin-left: 0%; text-align: center;">
					 	<a href="javascript:void(0);" onclick="javascript:fn_deposit();" id="btnAdd" class="btnStyle04">신청</a>
					  	<a href="javascript:void(0);" onclick="javascript:closePop('popup-layer');" id="btnEdit" class="btnStyle04">취소</a>
				    </div>
				</div>
			</section>
		</div>
		<input type="hidden" name="reserves_type" value="0" />
	</form>
	<form id="withdrawFrm">
		<div id="popup-layer2">
			<header class="header">
				<h1 class="tit" id="withdraw">출금하기</h1>
				<a href="#none" class="close" onclick="javascript:closePop('popup-layer2');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
			</header>
			
			<section>
				<div class="cont-box">
					<div>
						<strong style="font-size: 30px;">출금신청안내</strong><br>
						<div style="display: inline-block;width: 350px;height: 45px;margin-top: 3%;margin-bottom: 3%;margin-right: 10px;margin-left: 12px;padding: 13px 10px;background: #c2d9f1;border: 1px solid #ada8a8;vertical-align: middle;box-sizing: border-box;text-align: center;" id="withdrawPrice">						
						</div>
						<div style="margin-bottom: 16px;">
							쉐어료와 현금 잔액이 200만원 초과 <br>금액 한도에서 출금 신청 가능합니다.
						</div>
					</div>
					<article>
						<table class="bd-form" summary="출금">
							<caption>출금</caption>					
							<colgroup>
								<col width="100px" />
								<col width="*" />
							</colgroup>
							<tbody id="depositBody">
								<tr>
									<th>출금금액</th>
									<td class="linelY">
										<input type="text" class="inp-field wid200" name="reserves_price">
									</td>
								</tr>
								<tr>
									<th>요청사항</th>
									<td class="linelY">
										<input type="text" class="inp-field wid200" name="reserves_req_memo">
									</td>
								</tr>
							</tbody>
						</table>
					</article>
					
					<div class="btn-module mgtL" style="margin-left: 0%; text-align: center;">
					 	<a href="javascript:void(0);" onclick="javascript:fn_withdraw();" id="btnAdd" class="btnStyle04">신청</a>
					  	<a href="javascript:void(0);" onclick="javascript:closePop('popup-layer2');" id="btnEdit" class="btnStyle04">취소</a>
				    </div>
				</div>
			</section>
		</div>
		<input type="hidden" name="reserves_type" value="1"/> 
	</form>
	<input type="hidden" id="tr_phone" value="" />
	<input type="hidden" id="tr_callback" value="" />