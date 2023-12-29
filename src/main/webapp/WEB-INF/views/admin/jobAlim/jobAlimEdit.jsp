<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(document).ready(function() {
	});
	
	function updateForm() {
		$("#defaultFrm").ajaxForm({
			url : "/admin/jobAlim/updateInfo",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if ( data.code == "0000" ) {
						toastOk("정상적으로 수정 되었습니다.");
						
						location.href = '/admin/jobAlim/jobAlimList';
					} else {
						toastFail("수정 실패");
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
					toastFail("수정 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		}).submit();

	}
	
	function deleteForm() {
		$("#defaultFrm").ajaxForm({
			url : "/admin/jobAlim/deleteInfo",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if ( data.code == "0000" ) {
						toastOk("정상적으로 삭제 되었습니다.");
						
						location.href = '/admin/jobAlim/jobAlimList';
					} else {
						toastFail("삭제 실패");
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
					toastFail("삭제 실패");
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
	<div class="content">
		<div class="title">
			<h3>일자리 알림 수정</h3>
		</div>	
		
		<table class="bd-form" summary="일자리 알림 수정">
			<colgroup>
				<col width="200px" />
	          	<col width="*" />
	        </colgroup>
	        <tbody>
		        <tr>
		        	<th>내용</th>
		          	<td class="linelY">
		          		<textarea id="jobAlim_content" name="jobAlim_content" rows="10" cols="98" class="notEmpty">${jobAlimDTO.jobAlim_content }</textarea>
		          	</td>
		        </tr>
			</tbody>
		</table>
		<table class="bd-list" summary="일자리 알림 받은 리스트">
			<colgroup>
				<col width="200px" />
				<col width="200px" />
				<col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th>받는 사람</th>				
					<th>수신 확인</th>
					<th>읽은 날짜</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${jobAlimReceiveList }">
					<tr>
						<td class="linelY">${item.worker_name }</td>
						<td class="linelY">
							<c:choose>
								<c:when test="${item.read_yn eq '1' }">
									O
								</c:when>
								<c:otherwise>
									X
								</c:otherwise>
							</c:choose>
						</td>
						<td class="linelY">
							<c:if test="${item.read_date ne null and item.read_date ne '' }">
								${item.read_date }
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<input type="hidden" id="jobAlim_seq" name="jobAlim_seq" value="${jobAlimDTO.jobAlim_seq }" />
		
		<div class="btn-module mgtL">
			<a href="/admin/jobAlim/jobAlimList" id="btnList" class="btnStyle04">목록</a>
		 	<a href="javascript:void(0);" onclick="javascript:deleteForm();" id="btnDel" class="btnStyle04">삭제</a>
		  	<a href="javascript:void(0);" onclick="javascript:updateForm();" id="btnEdit" class="btnStyle04">수정</a>
	    </div>
	</div>
</article>


	
