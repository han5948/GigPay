<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	var url = '';
	var worker_seq = '${worker_seq }';
	var manager_seq = '${manager_seq }';
	
	if(worker_seq) {
		url = '/admin/alim/insertAlim';
	}else {
		url = '/admin/alim/insertManagerAlim';
	}
	
	$(document).ready(function() {
		$("body").css("overflow", "hidden");
	});
	
	function fn_insertAlim() {
		if($("#alim_title").val().replace(/\s/g, "").length == 0) {
			alert("제목을 입력해주세요.");
			
			$("#alim_title").focus();
			
			return false;
		}
		
		if($("#alim_content").val().replace(/\s/g, "").length == 0) {
			alert("내용을 입력해주세요.");
			
			$("#alim_content").focus();
			
			return false;
		}
		
		
		$("#insertFrm").ajaxForm({
			url : url,
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if ( data.code == "0000" ) {
						alert("전체 " + data.alimCnt + "건\n토큰 여부 : Y " + data.successCnt + "건\n토큰 여부 : N " + data.failCnt);
						
						if(data.failCnt == 0)
							toastOk("정상적으로 작성 되었습니다.");
						
						window.self.close();
					} else {
						toastFail("등록 실패");
					}
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

	
<article>
	<div class="content mgtS mglS mgbM">
		<div class="title">
			<h3>알림 작성</h3>
		</div>
		<div class="inputUI_simple">
			<form id="insertFrm" name="insertFrm" method="post" >
				<table class="bd-form" summary="알림 작성">
					<caption>제목, 내용</caption>
					<colgroup>
						<col width="200px" />
						<col width="*" />
					</colgroup>
					<tbody>
						<tr>
							<th>제목</th>
							<td class="linelY">
								<input type="text" class="inp-field wid300 notEmpty" id="alim_title" name="alim_title">
							</td>
						</tr>
						<tr>
							<th>내용</th>
							<td class="linelY">
								<textarea style="padding: 10px;" rows="10" cols="43" class="notEmpty" id="alim_content" name="alim_content"></textarea>
							</td>
						</tr>
						<tr>
							<th>메인 노출</th>
							<td class="linelY">
								<p class="agreeCheck" id="mainView">
									<label><input type="checkbox" id="main_view_yn" name="main_view_yn" value="1" />메인 노출</label>
								</p>
							</td>
						</tr>
					</tbody>
				</table>
				<c:if test="${worker_seq ne null }">
					<input type="hidden" id="workser_seq" name="worker_seq" value="${worker_seq }">
				</c:if>
				<c:if test="${manager_seq ne null }">
					<input type="hidden" id="manager_seq" name="manager_seq" value="${manager_seq }">
				</c:if>
			</form>
			<div class="btn-module mgtL">
				<a href="javascript:void(0);" onclick="javascript:fn_insertAlim()" class="btnStyle04">보내기</a>
			</div>
		</div>
	</div>
</article>