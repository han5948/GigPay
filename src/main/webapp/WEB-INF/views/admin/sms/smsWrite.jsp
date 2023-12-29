<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(document).ready(function() {
		$("body").css("overflow", "hidden");
		
		$("#template_title").on("change", function() {
			if($(this).val() == '') {
				$("#tr_msg").text('');
				
				return false;
			}
			
			var _data = {
		    		template_seq : $(this).val(),
		    	};
		    	
		    	var _url = "<c:url value='/admin/sms/smsTemplateInfo' />";
		    	
		    	commonAjax(_url, _data, true, function(data) {
		    		if(data.code == "0000") {
		    			var selectInfo = data.smsTemplateInfo; 
		    			
		    			$("#tr_msg").text(selectInfo.template_content);
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
		
		var $a = $("a[name=closeBtn]").on("click", function() {
			var idx = $a.index(this);
			
			$("a[name=closeBtn]").eq(idx).remove();
			$("input[name=tr_name]").eq(idx).remove();
			$("input[name=tr_phone]").eq(idx).remove();
		});
	});
	
	function fn_insertSms() {
		if($("#tr_msg").val().replace(/\s/g, "").length == 0) {
			alert("내용을 입력해주세요.");
			
			$("#tr_msg").focus();
			
			return false;
		}
		
// 		if(!$("#tr_callback").val()) {
// 			alert("발신자 번호를 등록해주세요.");
			
// 			return false;
// 		}
		
		var array = new Array();
		var sendList = '${sendList}';
		sendList = JSON.parse(sendList);
		
		for(var i = 0; i < sendList.length; i++) {
			array[i] = {
				'tr_msg' : $("#tr_msg").val(),
				'tr_name' : $("input[name=tr_name]").eq(i).val(),
				'tr_phone' : $("input[name=tr_phone]").eq(i).val(),
				'tr_etc1' : $("#tr_etc1").val(),
				//'tr_callback' : $("#tr_callback").val(),
				'company_name' : $("input[name=company_name]").eq(i).val(),
				'menuFlag' : $("#menuFlag").val()
			}
			
			if($("#menuFlag").val() == 'I')
				array[i].ilbo_seq = $("input[name=ilbo_seq]").eq(i).val();
			else if($("#menuFlag").val() == 'M') 
				array[i].employer_name = $("input[name=employer_name]").eq(i).val();
		}
		
		if($("input[name=tr_name]").length < 1) { 
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
					alert("전체 " + data.cnt + "건\n성공 " + (data.cnt - data.failCnt) + "건\n실패 " + data.failCnt);
					
					if(data.failCnt == 0)
						toastOk("정상적으로 작성 되었습니다.");
					
					window.self.close();
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
	<div class="content mgtS mglS">
		<div class="title">
			<h3>SMS 작성</h3>
		</div>
		<div class="inputUI_simple">
			<form id="insertFrm" name="insertFrm" method="post" >
				<table class="bd-form" summary="SMS 작성">
					<caption>템플릿, 내용</caption>
					<colgroup>
						<col width="200px" />
						<col width="200px" />
						<col width="*" />
					</colgroup>
					<tbody>
						<tr>
							<th>템플릿</th>
							<td class="linelY">
								<select id="template_title" name="template_title" class="styled02">
									<option value="">직접 입력</option>
									<c:forEach var="item" items="${smsTemplateList }">
										<option value="${item.template_seq }">${item.template_title }</option>
									</c:forEach>
								</select>
							</td>
							<th class="linelY">받는 사람</th>
						</tr>
						<tr>
							<th>내용</th>
							<td class="linelY">
								<textarea style="padding: 10px;" rows="10" cols="43" class="notEmpty" id="tr_msg" name="tr_msg"></textarea>
							</td>
							<td class="linelY">
								<c:forEach var="item" items="${sendList }" varStatus="status">
									<c:choose>
										<c:when test="${smsDTO.menuFlag eq 'I' }">
											<c:choose>
												<c:when test="${smsDTO.tr_etc1 eq '7' }">
													<a name="closeBtn" href="javascript:void(0);" style="width:150px; display:inline-block;" class="close"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" />${sendList[status.index].worker_name }</a>												
													<input type="hidden" name="tr_name" value="${sendList[status.index].worker_name }" />
													<input type="hidden" name="tr_phone" value="${sendList[status.index].worker_phone }" />
												</c:when>
												<c:when test="${smsDTO.tr_etc1 eq '8' }">
													<a name="closeBtn" href="javascript:void(0);" style="width:150px; display:inline-block;" class="close"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" />${sendList[status.index].manager_name }</a>
													<input type="hidden" name="tr_name" value="${sendList[status.index].manager_name }" />
													<input type="hidden" name="tr_phone" value="${sendList[status.index].manager_phone }" />
												</c:when>
												<c:when test="${smsDTO.tr_etc1 eq '9' }">
													<a name="closeBtn" href="javascript:void(0);" style="width:150px; display:inline-block;" class="close"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" />${sendList[status.index].ilbo_work_manager_name }</a>
													<input type="hidden" name="tr_name" value="${sendList[status.index].ilbo_work_manager_name }" />
													<input type="hidden" name="tr_phone" value="${sendList[status.index].ilbo_work_manager_phone }" />
												</c:when>
											</c:choose>
											<input type="hidden" name="ilbo_seq" value="${sendList[status.index].ilbo_seq }" />
										</c:when>
										<c:when test="${smsDTO.menuFlag eq 'M' }">
											<a name="closeBtn" href="javascript:void(0);" style="width:150px; display:inline-block;" class="close"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" />${sendList[status.index].manager_name }</a>
											<input type="hidden" name="tr_name" value="${sendList[status.index].manager_name }" />
											<input type="hidden" name="tr_phone" value="${sendList[status.index].manager_phone }" />
											<input type="hidden" name="employer_name" value="${sendList[status.index].employer_name }" />
										</c:when>
										<c:when test="${smsDTO.menuFlag eq 'W' }">
											<a name="closeBtn" href="javascript:void(0);" style="width:150px; display:inline-block;" class="close"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" />${sendList[status.index].worker_name }</a>
											<input type="hidden" name="tr_name" value="${sendList[status.index].worker_name }" />
											<input type="hidden" name="tr_phone" value="${sendList[status.index].worker_phone }" />
										</c:when>
									</c:choose>
									<input type="hidden" name="company_name" value="${sendList[status.index].company_name }" />
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th>발신자 번호</th>
							<td class="linelY" colspan="2">
<%-- 								${sessionScope.ADMIN_SESSION.admin_phone } --%>
								1668-1903
							</td>
						</tr>
					</tbody>
				</table>
				<input type="hidden" id="tr_etc1" name="tr_etc1" value="${smsDTO.tr_etc1 }" />
				<input type="hidden" id="menuFlag" name="menuFlag" value="${smsDTO.menuFlag }" />
<%-- 				<input type="hidden" id="tr_callback" name="tr_callback" value="${sessionScope.ADMIN_SESSION.admin_phone }" /> --%>
			</form> 
			<div class="btn-module mgtL">
				<a href="javascript:void(0);" onclick="javascript:fn_insertSms()" class="btnStyle04">보내기</a>
			</div>
		</div>
	</div>
</article>