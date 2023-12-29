<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="<c:url value="/resources/cms/js/paging.js" />" charset="utf-8"></script>
<script type="text/javascript">

	$(document).ready(function() {
		$("#jobDetailSel").on("change", function() {
			if($("#jobDetailSel").val() == '0') {
				$("#addJobChooseBody").empty();
				
				var text = '';
				
				text += '<tr style="text-align: center;">';
				text += '	<td colspan="5">';
				text += '		세부 직종을 선택해주세요.';
				text += '	</td>';
				text += '</tr>';

				$("#addJobChooseBody").append(text);
				
				return;	
			}
			
			addJobSelList($("#jobDetailSel").val());
		});
		
		$("#jobKindSel").on("change", function() {
			var _url = "<c:url value='/admin/job/changeJobList' />";
			var _data = {
				job_kind : $(this).val(),
				orderBy : 'job_order',
				use_yn : '1',
				del_yn : '0'
			};
			
			if($(this).val() != '0') {
				commonAjax(_url, _data, true, function(data) {
		    		if(data.code == "0000") {
		    			$("#jobKindTbody").empty();
		    			
		    			var jobList = data.changeJobList;
		    			var text = '';
		    			
		    			for(var i = 0; i < jobList.length; i++) {
			    			text += '<tr>';
			    			text += '<td></td>';
			    			text += '<td>' + jobList[i].job_name + '</td>';
			    			text += '<td>';
			    			text += '<div class="btn-module">';
			    			text += '<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveUp(this)">▲</a>';
			    			text += '<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveDown(this)">▼</a>';
			    			text += '<input type="hidden" name="orderJobSeq" value="' + jobList[i].job_seq + '">'
			    			text += '</div>';
			    			text += '</td>';
			    			text += '</tr>';
		    			}
		    			
		    			$("#jobKindTbody").append(text);
		    		}else {
		    			toastFail("오류가 발생했습니다.1");
		    		}
		    	}, function(data) {
		    		//errorListener
		    		toastFail("오류가 발생했습니다.2");
		    	}, function() {
		    		//beforeSendListener
		    	}, function() {
		    		//completeListener
		    	});
			}else {
				
			}
		});
	});
	
	// Layer popup 열기
	function openIrPopup(popupId, status) {
		//popupId : 팝업 고유 클래스명
		var $popUp = $('#'+ popupId);

		$dimmed.fadeIn();
		//$popLayer.hide();
		$popUp.show();

		$popUp.css({
			'margin-top': '-24%',
	    	'margin-left': '-40%',
	    	'margin-right': '10%',
// 	    	'overflow-y': 'scroll',
	    	'height': '90%'
	  	});
		
	}
	
	function jobDetailList(job_code, job_name, job_rank) {
		$("#jobListTit").text(job_name + " 세부 목록");
		$("#jobListTit").data("job_code", job_code);
		$("#job_code").val(job_code);
		
		var _data = {
	    		job_code : job_code,
	    		job_rank : job_rank,
	    		orderBy : 'job_order'
    	};
    	
    	var _url = "<c:url value='/admin/job/getJobList' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var totalCnt = data.totalCnt;
    			var jobList = data.jobList; 
    			var jobDTO = data.jobDTO;
    			var text = '';
    			
    			$("#jobListBody").empty();
    			
    			if(totalCnt == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="9">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    			}else {
    				$("#jobListLength").val(jobList.length);
    				
    				for(var i = 0; i < jobList.length; i++) {
    					text += '<tr>';
	    				text += '	<td>' + (i + 1) + '</td>';
	    				text += '	<td><input type="text" name="job_name" class="inp-field wid300" value="' + jobList[i].job_name + '"></td>';
	    				text += '	<td><input type="text" name="basic_price" class="inp-field wid100" value="' + jobList[i].basic_price + '"></td>';
	    				text += '	<td><input type="text" name="short_price" class="inp-field wid100" value="' + jobList[i].short_price + '"></td>';
	    				text += '	<td><input type="text" name="short_price_night" class="inp-field wid100" value="' + jobList[i].short_price_night + '"></td>';
	    				text += '	<td><input type="text" name="add_day_time" class="inp-field wid100" value="' + jobList[i].add_day_time + '"></td>';
	    				text += '	<td><input type="text" name="add_night_time" class="inp-field wid100" value="' + jobList[i].add_night_time + '"></td>';
	    				text += '	<td>';
	    				
	    				if(jobList[i].use_yn == '1') {
	    					text += '		<input type="radio" value="1" name="layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">사용</label>';
	    					text += '		<input type="radio" value="0" name="layer_use_yn' + i + '"><label for="" class="label-radio">미사용</label>';
    					}else {
	    					text += '		<input type="radio" value="1" name="layer_use_yn' + i + '"><label for="" class="label-radio">사용</label>';
    						text += '		<input type="radio" value="0" name="layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">미사용</label>';
    					}
    				
	    				text += '	</td>';
	    				text += '	<td>';
	    				text += '		<div class="btn-module">';
	    				text += '			<a href="javascript:void(0);" style="text-decoration: none; width: 60px;" class="btnStyle04" onclick="deleteJob(' + i + ', this)">삭제</a>';
	    				text += '			<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveUp(this)">▲</a>';
		    			text += '			<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveDown(this)">▼</a>';
		    			text += '<input type="hidden" name="orderJobSeq" value="' + jobList[i].job_seq + '">'
	    				text += '		</div>';
	    				text += '	</td>';
	    				text += '	<input type="hidden" value="U" name="job_flag">';
	    				text += '	<input type="hidden" name="job_seq" value="' + jobList[i].job_seq + '">';
	    				text += '	<input type="hidden" name="trSeq" value="' + i + '" />';
	    				text += '</tr>';
    				}
    			}
    			
    			$("#jobListBody").append(text);
    			
    			openIrPopup('popup-layer');
    		}else {
    			toastFail("오류가 발생했습니다.1");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.2");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function addJobDetail() {
		if($("#jobListBody tr td").length == 1) {
			$("#jobListBody").empty();
		}
		
		var text = '';
		
		text += '<tr>';
		text += '	<td>' + ($("#jobListBody tr").length + 1) + '</td>';
		text += '	<td><input type="text" name="job_name" class="inp-field wid300" value=""></td>';
		text += '	<td><input type="text" name="basic_price" class="inp-field wid100" value=""></td>';
		text += '	<td><input type="text" name="short_price" class="inp-field wid100" value=""></td>';
		text += '	<td><input type="text" name="short_price_night" class="inp-field wid100" value=""></td>';
		text += '	<td><input type="text" name="add_day_time" class="inp-field wid100" value=""></td>';
		text += '	<td><input type="text" name="add_night_time" class="inp-field wid100" value=""></td>';
		text += '	<td>';
		text += '		<input type="radio" value="1" name="layer_use_yn' + $("#jobListBody tr").length + '" checked="checked"><label for="" class="label-radio">사용</label>';
		text += '		<input type="radio" value="0" name="layer_use_yn' + $("#jobListBody tr").length + '"><label for="" class="label-radio">미사용</label>';
		text += '	</td>';
		text += '	<td>';
		text += '		<div class="btn-module">';
		text += '			<a href="javascript:void(0);" style="text-decoration: none;" class="btnStyle04" onclick="deleteJob(' + $("#jobListBody tr").length + ', this)">삭제</a>';
		text += '		</div>';
		text += '	</td>';
		text += '	<input type="hidden" value="I" name="job_flag">';
		text += '	<input type="hidden" name="job_seq" value="">';
		text += '	<input type="hidden" name="trSeq" value="' + $("#jobListBody tr").length + '" />';
		text += '</tr>';
		
		$("#jobListBody").append(text);
		
		$("input[name=job_name]").eq($("#jobListBody tr").length - 1).focus();
	}
	
	function deleteJob(index, e) {
		$("input[name=job_flag]").eq(index).val("D");
		
		var $tr = $(e).parent().parent().parent();
		
		$($tr[0].children[5]).val("D");
		
		$tr.hide();
	}
	
	function updateJobDetail() {
		var sub;
		var array = new Array();
		var use_yn = '';
		
		for(var i = 0; i < $("#jobListBody tr").length; i++) {
			sub = {
				'job_seq' : $("input[name=job_seq]").eq(i + 1).val(),
				'job_name' : $("input[name=job_name]").eq(i).val(),
				'basic_price' : $("input[name=basic_price]").eq(i).val(),
				'short_price' : $("input[name=short_price]").eq(i).val(),
				'short_price_night' : $("input[name=short_price_night]").eq(i).val(),
				'add_day_time' : $("input[name=add_day_time]").eq(i).val(),
				'add_night_time' : $("input[name=add_night_time]").eq(i).val(),
				'use_yn' : $("input[name=layer_use_yn" + $("input[name=trSeq]").eq(i).val() + "]:checked").val(),
				'job_flag' : $("input[name=job_flag]").eq(i).val(),
				'job_rank' : '2',
				'job_code' : $("#job_code").val(),
				'job_order' : (i + 1)
			}	
			
			array[i] = sub;
		}
		
		var _data = JSON.stringify(array);
    	var _url = "<c:url value='/admin/job/updateJobDetail' />";
    	
    	$.ajax({
    		url: _url,
    		type: "POST",
    		data: _data,
    		dataType: "json",
    		contentType: "application/json",
    		success: function(data){
    			if(data.code == "0000") {
    				toastOk("정상적으로 저장되었습니다.");
    				
    				var jobList = data.jobList; 
    				var totalCnt = data.totalCnt;
        			var text = '';
        			
        			$("#jobListBody").empty();
        			
        			if(totalCnt == 0) {
        				text += '<tr style="text-align: center;">';
        				text += '	<td colspan="4">';
        				text += '		조회된 내용이 없습니다.';
        				text += '	</td>';
        				text += '</tr>';
        			}else {
        				$("#jobListLength").val(jobList.length);
        				
        				for(var i = 0; i < jobList.length; i++) {
        					text += '<tr>';
    	    				text += '	<td>' + (i + 1) + '</td>';
    	    				text += '	<td><input type="text" name="job_name" class="inp-field wid300" value="' + jobList[i].job_name + '"></td>';
    	    				text += '	<td><input type="text" name="basic_price" class="inp-field wid100" value="' + jobList[i].basic_price + '"></td>';
    	    				text += '	<td><input type="text" name="short_price" class="inp-field wid100" value="' + jobList[i].short_price + '"></td>';
    	    				text += '	<td><input type="text" name="short_price_night" class="inp-field wid100" value="' + jobList[i].short_price_night + '"></td>';
    	    				text += '	<td><input type="text" name="add_day_time" class="inp-field wid100" value="' + jobList[i].add_day_time + '"></td>';
    	    				text += '	<td><input type="text" name="add_night_time" class="inp-field wid100" value="' + jobList[i].add_night_time + '"></td>';
    	    				text += '	<td>';
    	    				
    	    				if(jobList[i].use_yn == '1') {
    	    					text += '		<input type="radio" value="1" name="layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">사용</label>';
    	    					text += '		<input type="radio" value="0" name="layer_use_yn' + i + '"><label for="" class="label-radio">미사용</label>';
        					}else {
    	    					text += '		<input type="radio" value="1" name="layer_use_yn' + i + '"><label for="" class="label-radio">사용</label>';
        						text += '		<input type="radio" value="0" name="layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">미사용</label>';
        					}
        				
    	    				text += '	</td>';
    	    				text += '	<td>';
    	    				text += '		<div class="btn-module">';
    	    				text += '			<a href="javascript:void(0);" style="text-decoration: none;" class="btnStyle04" onclick="deleteJob(' + i + ', this)">삭제</a>';
    	    				text += '		</div>';
    	    				text += '	</td>';
    	    				text += '	<input type="hidden" value="U" name="job_flag">';
    	    				text += '	<input type="hidden" name="job_seq" value="' + jobList[i].job_seq + '">';
    	    				text += '	<input type="hidden" name="trSeq" value="' + i + '"';
    	    				text += '</tr>';
        				}
        			}
        			
        			$("#jobListBody").append(text);
    			}else {
    				toastFail("저장 실패");
    			}
    		},
    		beforeSend : function(xhr) {
				 xhr.setRequestHeader("AJAX", true);
				 $(".wrap-loading").show();
			},
			error : function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					toastFail("등록 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
    	});
	}
	
	function addJobChooseList(job_code, job_name, job_rank, job_kind, detail_use_yn) {
		$("#addJobChooseTit").text(job_name + " 추가 선택 목록");
		$("#addJobChooseTit").data("job_code", job_code);
		$("#job_code").val(job_code);
		
		var _data = {
	    		job_code : job_code,
	    		job_rank : '2'
    	};
		
    	var _url = "<c:url value='/admin/job/getJobList' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var totalCnt = data.totalCnt;
    			var jobList = data.jobList; 
    			var jobDTO = data.jobDTO;
    			var text = '';
    			
    			$("#addJobChooseBody").empty();
    			$("#jobDetailSel").empty();
				$("#jobDetailSel").append("<option value='0'>세부직종을 선택하세요.</option>");
				
    			if(totalCnt == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="5">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    				
    				if(detail_use_yn == '0') {
    					$("#jobDetailSel").append("<option value='" + job_code + "' selected>no<option>");
    				}
    			}else {
    				$("#jobListLength").val(jobList.length);
    				
    				for(var i = 0; i < jobList.length; i++) {
    					if(detail_use_yn == '1') {
    						$("#jobDetailSel").append("<option value='" + jobList[i].job_seq + "'>" + jobList[i].job_name + "<option>");
    					}
    				}
    			}
    			
    			for(var i = 0; i < $("#jobDetailSel")[0].options.length; i++) {
    				if($("#jobDetailSel option:eq(" + i + ")")[0].innerHTML == '') {
    					$("#jobDetailSel option:eq(" + i + ")").remove();	
    				} 
    			}
    			
    			$("#jobDetailSel option[value='']").remove();
    			
     			if(detail_use_yn == '0') {
     				$("#jobDetailSel").hide();
     			}else {
     				$("#jobDetailSel").show();
     			}
    		}else {
    			toastFail("오류가 발생했습니다.1");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.2");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    		if(detail_use_yn == '0') {
    			addJobSelList(job_seq, job_kind);
    		}else {
    			addJobSelList(job_code, job_kind);
    		}
    	});
	}
	
	function addJobSelList(job_code, job_kind) {
		//$("#job_code").val($("#jobDetailSel").val());
		
		if($("#jobDetailSel").val() == '0') {
			var text = '';
			
			text += '<tr style="text-align: center;">';
			text += '	<td colspan="5">';
			text += '		세부 직종을 선택해주세요.';
			text += '	</td>';
			text += '</tr>';

			$("#addJobChooseBody").append(text);
			
			openIrPopup('popup-layer2');
			
			return false;	
		}
		
		var _data = {
				job_kind : job_kind,
	    		job_code : $("#jobDetailSel").val(),
	    		job_rank : '3',
	    		del_yn : '0',
	    		orderBy : 'job_order'
    	};
    	
    	var _url = "<c:url value='/admin/job/getJobList' />";

    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var totalCnt = data.totalCnt;
    			var jobList = data.jobList; 
    			var jobDTO = data.jobDTO;
    			var text = '';
    			
    			$("#addJobChooseBody").empty();
    			
    			if(totalCnt == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="5">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    			}else {
    				$("#jobListLength").val(jobList.length);
    				
    				for(var i = 0; i < jobList.length; i++) {
    					text += '<tr>';
	    				text += '	<td>' + (i + 1) + '</td>';
	    				text += '	<td><input type="text" name="choose_job_name" class="inp-field wid300" value="' + jobList[i].job_name + '"></td>';
	    				text += '	<td><input type="text" name="choose_basic_price" class="inp-field wid100" value="' + jobList[i].basic_price + '"></td>';
	    				text += '	<td>';
	    				
	    				if(jobList[i].use_yn == '1') {
	    					text += '		<input type="radio" value="1" name="choose_layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">사용</label>';
	    					text += '		<input type="radio" value="0" name="choose_layer_use_yn' + i + '"><label for="" class="label-radio">미사용</label>';
    					}else {
	    					text += '		<input type="radio" value="1" name="choose_layer_use_yn' + i + '"><label for="" class="label-radio">사용</label>';
    						text += '		<input type="radio" value="0" name="choose_layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">미사용</label>';
    					}
    				
	    				text += '	</td>';
	    				text += '	<td>';
	    				text += '		<div class="btn-module">';
	    				text += '			<a href="javascript:void(0);" style="text-decoration: none;" class="btnStyle04" onclick="deleteJob(' + i + ', this)">삭제</a>';
	    				text += '			<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveUp(this)">▲</a>';
		    			text += '			<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveDown(this)">▼</a>';
	    				text += '		</div>';
	    				text += '	</td>';
	    				text += '	<input type="hidden" value="U" name="choose_job_flag">';
	    				text += '	<input type="hidden" name="choose_job_seq" value="' + jobList[i].job_seq + '">';
	    				text += '	<input type="hidden" name="choose_trSeq" value="' + i + '" />';
	    				text += '</tr>';
    				}
    			}

    			$("#addJobChooseBody").append(text);
    			
    			openIrPopup('popup-layer2');
    		}else {
    			toastFail("오류가 발생했습니다.1");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.2");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function addJobChoose() {
		if($("#addJobChooseBody tr td").length == 1) {
			$("#addJobChooseBody").empty();
		}
		
		var text = '';
		
		text += '<tr>';
		text += '	<td>' + ($("#addJobChooseBody tr").length + 1) + '</td>';
		text += '	<td><input type="text" name="choose_job_name" class="inp-field wid300" value=""></td>';
		text += '	<td><input type="text" name="choose_basic_price" class="inp-field wid100" value=""></td>';
		text += '	<td>';
		text += '		<input type="radio" value="1" name="choose_layer_use_yn' + $("#addJobChooseBody tr").length + '" checked="checked"><label for="" class="label-radio">사용</label>';
		text += '		<input type="radio" value="0" name="choose_layer_use_yn' + $("#addJobChooseBody tr").length + '"><label for="" class="label-radio">미사용</label>';
		text += '	</td>';
		text += '	<td>';
		text += '		<div class="btn-module">';
		text += '			<a href="javascript:void(0);" style="text-decoration: none;" class="btnStyle04" onclick="deleteJobChoose(' + $("#addJobChooseBody tr").length + ', this)">삭제</a>';
		text += '		</div>';
		text += '	</td>';
		text += '	<input type="hidden" value="I" name="choose_job_flag">';
		text += '	<input type="hidden" name="choose_job_seq" value="">';
		text += '	<input type="hidden" name="choose_trSeq" value="' + $("#addJobChooseBody tr").length + '" />';
		text += '</tr>';
		
		$("#addJobChooseBody").append(text);
		
		$("input[name=job_name]").eq($("#addJobChooseBody tr").length - 1).focus();
	}
	
	function deleteJobChoose(index, e) {
		$("input[name=choose_job_flag]").eq(index).val("D");
		
		var $tr = $(e).parent().parent().parent();
		
		$tr.hide();
	}
	
	function updateAddJobChoose() {
		var exp = new RegExp('^[0-9-_]*$');
		
		if($("#jobDetailSel").val() == 0) {
			alert("세부직종을 선택해주세요.");
			
			return false;
		}
		
		if($("#choose_job_name").val() == '') {
			alert("추가 선택을 입력해주세요.");
			
			return false;
		}
		
		var sub;
		var array = new Array();
		var use_yn = '';
		
		for(var i = 0; i < $("#addJobChooseBody tr").length; i++) {
			if($("input[name=choose_job_name]").eq(i).val().replace(/\s/g, "") == '') {
				alert("추가 선택을 입력해주세요.");
				
				return false;	
			}
			
			if($("input[name=choose_basic_price]").eq(i).val().replace(/\s/g, "") == '') {
				alert("추가 금액을 입력해주세요.");
				
				return false;
			}
			
			if(!exp.test($("input[name=choose_basic_price]").eq(i).val())) {
				alert("추가 금액은 숫자만 입력해주세요.");
				
				return false;
			}
			
			sub = {
				'job_seq' : $("input[name=choose_job_seq]").eq(i).val(),
				'job_name' : $("input[name=choose_job_name]").eq(i).val(),
				'basic_price' : $("input[name=choose_basic_price]").eq(i).val(),
				'use_yn' : $("input[name=choose_layer_use_yn" + $("input[name=choose_trSeq]").eq(i).val() + "]:checked").val(),
				'job_flag' : $("input[name=choose_job_flag]").eq(i).val(),
				'job_rank' : '3',
				'job_code' : $("#jobDetailSel").val(),
				'job_order' : (i + 1)
			}	
			
			array[i] = sub;
		}
		
		var _data = JSON.stringify(array);
    	var _url = "<c:url value='/admin/job/updateAddJobChoose' />";
    	
    	$.ajax({
    		url: _url,
    		type: "POST",
    		data: _data,
    		dataType: "json",
    		contentType: "application/json",
    		success: function(data){
    			if(data.code == "0000") {
    				toastOk("정상적으로 저장되었습니다.");
    				
    				var jobList = data.jobList; 
    				var totalCnt = data.totalCnt;
        			var text = '';
        			
        			$("#addJobChooseBody").empty();
        			
        			if(totalCnt == 0) {
        				text += '<tr style="text-align: center;">';
        				text += '	<td colspan="5">';
        				text += '		조회된 내용이 없습니다.';
        				text += '	</td>';
        				text += '</tr>';
        			}else {
        				$("#jobListLength").val(jobList.length);
        				
        				for(var i = 0; i < jobList.length; i++) {
        					text += '<tr>';
    	    				text += '	<td>' + (i + 1) + '</td>';
    	    				text += '	<td><input type="text" name="choose_job_name" class="inp-field wid300" value="' + jobList[i].job_name + '"></td>';
    	    				text += '	<td><input type="text" name="choose_basic_price" class="inp-field wid100" value="' + jobList[i].basic_price + '"></td>';
    	    				text += '	<td>';
    	    				
    	    				if(jobList[i].use_yn == '1') {
    	    					text += '		<input type="radio" value="1" name="choose_layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">사용</label>';
    	    					text += '		<input type="radio" value="0" name="choose_layer_use_yn' + i + '"><label for="" class="label-radio">미사용</label>';
        					}else {
    	    					text += '		<input type="radio" value="1" name="choose_layer_use_yn' + i + '"><label for="" class="label-radio">사용</label>';
        						text += '		<input type="radio" value="0" name="choose_layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">미사용</label>';
        					}
        				
    	    				text += '	</td>';
    	    				text += '	<td>';
    	    				text += '		<div class="btn-module">';
    	    				text += '			<a href="javascript:void(0);" style="text-decoration: none;" class="btnStyle04" onclick="deleteJobChoose(' + i + ', this)">삭제</a>';
    	    				text += '		</div>';
    	    				text += '	</td>';
    	    				text += '	<input type="hidden" value="U" name="choose_job_flag">';
    	    				text += '	<input type="hidden" name="choose_job_seq" value="' + jobList[i].job_seq + '">';
    	    				text += '	<input type="hidden" name="choose_trSeq" value="' + i + '"';
    	    				text += '</tr>';
        				}
        			}
        			
        			$("#addJobChooseBody").append(text);
    			}else {
    				toastFail("저장 실패");
    			}
    		},
    		beforeSend : function(xhr) {
				 xhr.setRequestHeader("AJAX", true);
				 $(".wrap-loading").show();
			},
			error : function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					toastFail("등록 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
    	});
	}
	
	function fn_edit(job_seq) {
		$("#job_seq").val(job_seq);
		
		$("#contentFrm").attr("action", "<c:url value='/admin/job/jobEdit' />").submit();
	}
	
	function calculate() {
        notify();
	}
	
	function notify() {
	    if (Notification.permission !== 'granted') {
	        alert('notification is disabled');
	    }else {
	
	        var notification = new Notification('Notification title', {
	            icon: 'http://cdn.sstatic.net/stackexchange/img/logos/so/so-icon.png',
	            body: 'Notification text',
	        });
	
	        notification.onclick = function () {
	            window.open('http://google.com');
	        };
	    }
	}
	
	function fn_paging(pageSize, totalCnt, contentFrm, pageNo) {
		$("#job_code").val('');
		
		fn_page_display(pageSize, totalCnt, contentFrm, pageNo);
	}
	
	function closeIrPopupBefore(popupId) {
		$("#job_code").val('');
		
		closeIrPopup(popupId);
	}
	
	function changeJobOrder() {
		$("#jobKindTbody").empty();
		$("#jobKindSel").val('0');
		openIrPopup('popup-layer3');
	}
	
	function fn_moveUp(e) {
		var $tr = $(e).parent().parent().parent();
		$tr.prev().before($tr);
	}
	
	function fn_moveDown(e) {
		var $tr = $(e).parent().parent().parent();
		$tr.next().after($tr);
	}
	
	function updateJobOrder() {
		var sub;
		var array = new Array();
		var totalCnt = $("#jobKindTbody").find("tr").length;
		
		for(var i = 0; i < totalCnt; i++) {
			sub = {
				'job_seq' : $("input[name=orderJobSeq]").eq(i).val()
			}	
			
			array[i] = sub;
		}
		
		var _data = JSON.stringify(array);
    	var _url = "<c:url value='/admin/job/updateJobOrder' />";
    	
    	$.ajax({
    		url: _url,
    		type: "POST",
    		data: _data,
    		dataType: "json",
    		contentType: "application/json",
    		success: function(data){
    			if(data.code == "0000") {
    				toastOk("정상적으로 저장되었습니다.");
    			}else {
    				toastFail("저장 실패");
    			}
    		},
    		beforeSend : function(xhr) {
				 xhr.setRequestHeader("AJAX", true);
				 $(".wrap-loading").show();
			},
			error : function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					toastFail("등록 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
    	});
	}
</script>
	<article>
		<div class="content mgtS">
			<div class="title">
				<h3>직종 관리</h3>
			</div>
		</div>
		
		<table class="bd-list" summary="직종 리스트">
		    <colgroup>
			    <col width="100px" />
			    <col width="100px" />
			    <col width="150px" />
			    <col width="*" />
			    <col width="150px" />
			    <col width="100px" />
			    <col width="150px" />
			    <col width="150px" />
		    </colgroup>
	    	<thead>
				<tr>
					<th>번호</th>
					<th>구분</th>
					<th>직종코드</th>
					<th>직종</th>
					<th>아이콘명</th>
					<th>사용여부</th>
					<th></th>
					<th class="last"></th>
				</tr>
			</thead>
			<tbody id="listBody">
				<c:choose>
					<c:when test="${!empty jobList }">
						<c:forEach var="item" items="${jobList }" varStatus="status">
							<tr>
								<td>${totalCnt - ((jobDTO.paging.pageNo - 1) * jobDTO.paging.pageSize + status.index) +3 }</td>
								<td>
									<c:choose>
										<c:when test="${item.job_kind eq '1' }">보통인부</c:when>
										<c:when test="${item.job_kind eq '2' }">기공</c:when>
										<c:when test="${item.job_kind eq '3' }">노무제공자</c:when>
									</c:choose>
								</td>
								<td>${item.job_code }</td>
								<td><a href="javascript:void(0);" onclick="fn_edit('${item.job_seq }')">${item.job_name }</a></td>
								<td>${item.job_image_name }</td>
								<td>
									<c:choose>
										<c:when test="${item.use_yn eq '1' }">사용</c:when>
										<c:otherwise>미사용</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:if test="${item.detail_use_yn eq '1' }">
										<div class="btn-module">
											<a href="javascript:void(0);" style="text-decoration: none;"
											   onclick="jobDetailList('${item.job_seq }', '${item.job_name }','2')"
											   class="btnStyle04">세부 직종 관리</a>
										</div>
									</c:if>
								</td>
								<td>
									<div class="btn-module">
										<a href="javascript:void(0);" style="text-decoration: none;"
										   onclick="addJobChooseList('${item.job_seq }', '${item.job_name }', '3', '${item.job_kind }', '${item.detail_use_yn }')"
										   class="btnStyle04">추가 선택 관리</a>
									</div>
								</td>
							</tr>
			    		</c:forEach>	
					</c:when>
	       			<c:otherwise>
	       				<tr style="text-align: center;">
	       					<td colspan="5">
	       						조회된 내용이 없습니다.
	       					</td>
	       				</tr>
	       			</c:otherwise>
	       		</c:choose>
	    	</tbody>
		</table>
		
		<input type="hidden" id="job_seq" name="job_seq" />
		
		<div class="btn-module mgtL">
			<a href="/admin/job/jobWrite" id="btnAdd" class="btnStyle04">
				직종 추가
			</a>
		</div>
		<div class="btn-module mgtR">
			<a href="javascript:changeJobOrder();" id="btnChange" class="btnStyle04">
				순서 변경
			</a>
		</div>
		
		<input type="hidden" id="pageNo" name="paging.pageNo" value="${jobDTO.paging.pageNo }" />
		<input type="hidden" id="jobListLength" />
		<input type="hidden" id="job_code" name="job_code" />
		<input type="hidden" id="job_kind" name="job_kind" />
		
		<c:if test="${jobDTO.paging.pageSize != '0' }">
			<div class="paging">
				<script type="text/javascript">
					fn_paging('${jobDTO.paging.pageSize}', '${totalCnt }', 'contentFrm', '#pageNo');
				</script>
			</div>
		</c:if>
	</article>
	
	<div id="opt_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b;">
	</div>
	
	<div id="popup-layer3">
		<header class="header">
			<h1 class="tit">직종 목록</h1>
			<a href="#none" class="close" onclick="javascript:closeIrPopupBefore('popup-layer3');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
		</header>
		
		<section>
			<div class="cont-box">
				<div class="select-inner">
					<select id="jobKindSel" name="jobKindSel">
						<option value="0">직종을 선택해주세요.</option>
						<option value="1">보통인부</option>
						<option value="2">기공</option>
						<option value="3">노무제공자</option>
					</select>
				</div>
				<div class="mgtS" style="display: inline-block;">
					<table class="bd-list" summary="직종 목록">
						<caption>직종 목록</caption>					
						<colgroup>
							<col width="100px" />
							<col width="200px" />
							<col width="*" />
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>직종명</th>
								<th class="last">순서변경</th>
							</tr>
						</thead>
						<tbody id="jobKindTbody">
							
						</tbody>
					</table>
				</div>
				<div class="btn-module mgtL">
				  	<a href="javascript:void(0);" onclick="updateJobOrder()" class="btnStyle04">저장</a>
			    </div>
			</div>
		</section>
	</div>
	
	<div id="popup-layer">
		<header class="header">
			<h1 class="tit" id="jobListTit">직종 목록</h1>
			<a href="#none" class="close" onclick="javascript:closeIrPopupBefore('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
		</header>
		
		<section>
			<div class="cont-box">
				<article>
					<table class="bd-list" summary="세부 직종 목록">
						<caption>직종 목록</caption>					
						<colgroup>
							<col width="50px" />
							<col width="350px" />
							<col width="140px" />
							<col width="140px" />
							<col width="140px" />
							<col width="140px" />
							<col width="140px" />
							<col width="200px" />
							<col width="*" />
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>세부 직종명</th>
								<th>기본단가(06~17)</th>
								<th>단시간(주간)</th>
								<th>단시간(야간)</th>
								<th>시간추가(08~18)</th>
								<th>시간추가(18~06)</th>
								<th>사용 여부</th>
								<th class="last"></th>
							</tr>
						</thead>
						<tbody id="jobListBody">
						</tbody>
					</table>
				</article>
				
				<div class="btn-module mgtL">
				 	<a href="javascript:void(0);" onclick="javascript:addJobDetail();" id="btnAdd" class="btnStyle04">추가</a>
				  	<a href="javascript:void(0);" onclick="javascript:updateJobDetail();" id="btnEdit" class="btnStyle04">저장</a>
			    </div>
			</div>
		</section>
	</div>
	
	<div id="popup-layer2">
		<header class="header">
			<h1 class="tit" id="addJobChooseTit">추가 선택 목록</h1>
			<a href="#none" class="close" onclick="javascript:closeIrPopupBefore('popup-layer2');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
		</header>
		
		<section style="display: flex; height: 100%;">
			<div class="cont-box">
					
					<div class="select-inner">
						<select id="jobDetailSel" name="jobDetailSel">
							<option value="0">세부직종을 선택해주세요.</option>
						</select>
					</div>
					
					<div class="mgtS" style="display: inline-block;">
					<table class="bd-list" summary="추가 선택 목록">
						<caption>추가 선택 목록</caption>					
						<colgroup>
							<col width="100px" />
							<col width="350px" />
							<col width="150px" />
							<col width="300px" />
							<col width="*" />
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>추가 선택</th>
								<th>추가 금액</th>
								<th>사용 여부</th>
								<th class="last"></th>
							</tr>
						</thead>
						<tbody id="addJobChooseBody">
						</tbody>
					</table>
					</div>
					
				
				<div class="btn-module mgtL">
				 	<a href="javascript:void(0);" onclick="javascript:addJobChoose();" class="btnStyle04">추가</a>
				  	<a href="javascript:void(0);" onclick="javascript:updateAddJobChoose();" class="btnStyle04">저장</a>
			    </div>
			</div>
		</section>
	</div>