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
		
		window.resizeTo( "700", "500" );
	});
	
	function fn_insertInfo() {
		if($("#jobAlim_title").val().replace(/\s/g, "").length == 0) {
			alert("제목을 입력해주세요.");
			
			$("#jobAlim_title").focus();
			
			return false;
		}
		
		if($("#jobAlim_content").val().replace(/\s/g, "").length == 0) {
			alert("내용을 입력해주세요.");
			
			$("#jobAlim_content").focus();
			
			return false;
		}
		
		$("#insertFrm").ajaxForm({
			url : "/admin/jobAlim/insertInfo",
			dataType: 'json',
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
				alert("전체 " + data.jobAlimCnt + "건\n토큰 여부 : Y " + data.successCnt + "건\n토큰 여부 : N " + data.failCnt);
				
				if(data.failCnt == 0)
					toastOk("정상적으로 작성 되었습니다.");
				
				window.self.close();
			} ,
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
		}).submit();
	}
</script>

<div class="content mgtS mglS">
	<div class="title">
		<h3>일자리 알림 작성</h3>
	</div>
	
	<article>
		<div class="inputUI_simple">
			<form id="insertFrm" name="insertFrm" method="post" >
				<table class="bd-form" summary="알림 작성">
					<caption>내용</caption>
					<colgroup>
						<col width="200px" />
						<col width="*" />
					</colgroup>
					<tbody>
						<tr> 
							<th>제목</th>
							<td class="linelY">
								<input type="text" class="inp-field wid300 notEmpty" id="jobAlim_title" name="jobAlim_title" <c:if test="${title ne '' }">value="[${job_name }] ${title }"</c:if>>
							</td>
						</tr>
						<tr>
							<th style="text-align: center;">
								내용<br>
								(수신자 총 ${worker_length } 명)
							</th>
							<td class="linelY">
								<textarea style="padding: 10px;" rows="10" cols="43" class="notEmpty" id="jobAlim_content" name="jobAlim_content">
<c:if test="${workFlag eq '1' or workFlag eq null }">반장님,&#10;일가자 "맞춤일자리"가 도착했어요.&#10;바로 확인하고 먼저 예약하세요.&#10;-일가자 드림-</c:if><c:if test="${workFlag eq '0' }">${worker_alias },&#10;<c:if test="${not empty job_name}">[${job_name }] </c:if>일가자 "맞춤일자리"가 도착했어요.&#10;▷구인처:${employer_name }&#10;▷현장:${work_addr }&#10;바로 확인하고 먼저 예약하세요.&#10;-일가자 드림-</c:if>
								</textarea>
							</td>
						</tr>
						<tr>
							<th>메인 노출</th>
							<td class="linelY">
								<p class="agreeCheck" id="mainView">
									<label><input type="checkbox" id="main_view_yn" name="main_view_yn" value="1" checked />메인 노출</label>
								</p>
							</td>
						</tr>
					</tbody>
				</table>
				<c:if test="${!empty worker_seq }">
					<input type="hidden" id="worker_seq" name="worker_seq" value="${worker_seq }">
				</c:if>
				<input type="hidden" name="work_seq" value="${work_seq }" />
				<input type="hidden" name="queryFlag" value="${queryFlag }" />
			</form>
			<div class="btn-module mgtL">
				<a href="javascript:void(0);" onclick="javascript:fn_insertInfo()" class="btnStyle04">보내기</a>
			</div>
		</div>
	</article>
</div>