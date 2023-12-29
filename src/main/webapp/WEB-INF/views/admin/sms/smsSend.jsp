<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<style>
	 .btn-module:after {content:'';display:block;clear:both;}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#bd-form-table").css("width", "53%");
	});
	
	function addForm() {
		var html = '';
		
		html += '<tr>';
		html += '	<td><input type="text" name="">';
		
		$("#beon").after('<input type="text">');
	}
	
	function insertForm() {
		if($("#tr_msg").val().replace(/\s/g, "").length == 0) {
			alert("내용을 입력해주세요.");
			
			$("#tr_msg").focus();
			
			return false;
		}
		
		var array = new Array();
		var telArray = $("input[name=telNum]").val().split(';');
		var exp = new RegExp('^[0-9-_]*$');
		var arrayCnt = 0;
		
		for(var i = 0; i < telArray.length; i++) {
			if(exp.test(telArray[i]) && (telArray[i].length == '11' || telArray[i].length == '10')) {	
				array[arrayCnt] = {
					'tr_msg' : $("#tr_msg").val(),
					'tr_name' : telArray[i],
					'tr_phone' : telArray[i],
					'tr_etc1' : '11',
					'company_name' : $("input[name=company_name]").val(),
					'menuFlag' : 'S'
				};
				
				++arrayCnt;
			}
			
		}
		
		if(array.length < 1) { 
			$.toast({title: '받는 사람이 없습니다.', position: 'middle', backgroundColor: '#ef7220', duration:1000 });
			
			return false;
		}
		
		var _data = JSON.stringify(array);
		var _url = "<c:url value='/admin/sms/insertSms' />";
		
		$.ajax({
    		url: _url,
    		type: "POST",
    		data: _data,
    		dataType: "json",
    		contentType: "application/json",
    		success: function(data){
    			if ( data.code == "0000" ) {
					alert("전체 " + telArray.length + "건\n성공 " + (telArray.length - data.failCnt - (telArray.length - array.length)) + "건\n실패 " + (data.failCnt * 1) + (telArray.length - array.length));
					
					if(data.failCnt == 0)
						toastOk("정상적으로 작성 되었습니다.");
				} else {
					toastFail("등록 실패");
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
				<h3>SMS 보내기</h3>
			</div>
			
			<table class="bd-form" id="bd-form-table" summary="SMS 보내기">
				<colgroup>
					<col width="200px" />
					<col width="*" />
				</colgroup>
				<tbody>
					<tr>
						<th>번호</th>
						<td class="linelY">
							<input type="text" name="telNum" class="inp-field notEmpty" style="float: left; width:725px;">
							<p style="color: blue; font-size: 12px; padding-top: 37px;">※ 다중으로 보낼시 ';'으로 구분해주세요. ex)01022222222;01033333333;01044444444</p>
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td class="linelY">
							<textarea rows="10" cols="98" class="notEmpty" style="padding: 10px;" id="tr_msg" name="tr_msg"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="btn-module mgtL" style="text-align: center;">
				<a href="javascript:void(0);" onclick="javascript:insertForm();" id="btnSend" class="btnStyle04">
					보내기
				</a>
			</div>
		</div>
		<input type="hidden" name="company_name" id="company_name" value="${sessionScope.ADMIN_SESSION.company_name}" />
	</article>