<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(document).ready(function() {
		$("#contents").css("min-width", "1000px");
		
		$("#btnList").on("click", function() {
			location.href = '/admin/alim/alimList';
		});
	});
	
	function updateForm() {
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
		
		$("#defaultFrm").ajaxForm({
			url : "/admin/alim/updateAlim",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if ( data.code == "0000" ) {
						toastOk("정상적으로 수정 되었습니다.");
						
						location.href = '/admin/alim/alimList';
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
		if(!confirm("해당 알림을 삭제하시겠습니까?")) {
			return false;
		}
		
		$("#defaultFrm").ajaxForm({
			url : "/admin/alim/deleteAlim",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if ( data.code == "0000" ) {
						toastOk("정상적으로 삭제 되었습니다.");
						
						location.href = '/admin/alim/alimList';
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
	<div class="content mgtS mgbM">
		<div class="title">
			<h3>알림 수정</h3>
		</div>	
		
		<table class="bd-form" summary="알림 수정">
			<colgroup>
				<col width="200px" />
	          	<col width="*" />
	        </colgroup>
	        <tbody>
		        <tr>
		        	<th>제목</th>
		          	<td class="linelY">
		          		<input type="text" id="alim_title" name="alim_title" value="${alimDTO.alim_title }" class="inp-field wid700 notEmpty">
		          	</td>
		        </tr>
		        <tr>
		        	<th>내용</th>
		          	<td class="linelY">
		          		<textarea style="padding: 10px;" id="alim_content" name="alim_content" rows="10" cols="98" class="notEmpty">${alimDTO.alim_content }</textarea>
		          	</td>
		        </tr>
		        <tr>
					<th>메인 노출</th>
					<td class="linelY">
						<p class="agreeCheck" id="mainView">
							<label><input type="checkbox" id="main_view_yn" name="main_view_yn" value="1" <c:if test="${alimDTO.main_view_yn eq '1' }">checked="checked"</c:if>/>메인 노출</label>
						</p>
					</td>
				</tr>
			</tbody>
		</table>
		<table style="width: 1000px;" class="bd-list" summary="알림 받은 리스트">
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
				<c:forEach var="item" items="${alimReceiveList }">
					<tr>
						<td class="linelY">
							<c:choose>
								<c:when test="${item.worker_name ne null }">
									(구직자) : ${item.worker_name }
								</c:when>
								<c:when test="${item.manager_name ne null }">
									(매니저) : ${item.manager_name }
								</c:when>
								<c:otherwise>
									(소장)
								</c:otherwise>
							</c:choose>
						</td>
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
		
		<input type="hidden" id="alim_seq" name="alim_seq" value="${alimDTO.alim_seq }" />
		
		<div class="btn-module mgtL" style="text-align: right;">
			<a style="float: left;" href="/admin/alim/alimList" id="btnList" class="btnStyle04">목록</a>
		 	<a href="javascript:void(0);" onclick="javascript:deleteForm();" id="btnDel" class="btnStyle04">삭제</a>
		  	<a href="javascript:void(0);" onclick="javascript:updateForm();" id="btnEdit" class="btnStyle04">수정</a>
	    </div>
	</div>
</article>


	
