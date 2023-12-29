<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(document).ready(function() {
		$(".bd-form").css("width", "59%");
	});
	
	function updateForm() {
		var exp = new RegExp('^[a-zA-Z0-9+]*$');
		
		if($("#group_code").val().charAt(0) == '0') {
			alert("코드명 첫번째 글자는 0이 아니어야 합니다.");
			
			$("#group_code").focus();
			
			return false;
		}
		
		
		if($("#group_code").val().replace(/\s/g, "").length == 0 || $("#group_code").val().replace(/\s/g, "").length != 3) {
			alert("코드명을 제대로 입력해주세요.");
			
			$("#group_code").focus();
			
			return false;
		} 
		
		if(!exp.test($("#group_code").val())){			
			alert("영문/숫자만 입력이 가능합니다.");
			
			$("#group_code").focus();
			
			return false;
		}
		
		if($("#group_name").val().replace(/\s/g, "").length == 0) {
			alert("제목을 입력해주세요.");
			
			$("#group_name").focus();
			
			return false;
		}
		
		$("#contentFrm").ajaxForm({
			url : "/admin/groupCode/updateGroupCode",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if ( data.code == "0000" ) {
						toastOk("정상적으로 수정 되었습니다.");
						
						location.href = '/admin/groupCode/groupCodeList';
					} else {
						toastFail(data.message);
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
		if(!confirm("해당 그룹코드를 삭제하시겠습니까?")) {
			return false;
		}
		
		$("#contentFrm").ajaxForm({
			url : "/admin/groupCode/deleteGroupCode",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if ( data.code == "0000" ) {
						toastOk("정상적으로 삭제 되었습니다.");
						
						location.href = '/admin/groupCode/groupCodeList';
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
		<div class="content mgtS">
			<div class="title">
				<h3>그룹 코드 수정</h3>
			</div>
			
			<table class="bd-form" summary="그룹 코드 수정">
				<colgroup>
					<col width="200px" />
					<col width="*" />
				</colgroup>
				<tbody>
					<tr>
						<th>그룹 코드</th>
						<td class="linelY">
							<input type="text" id="group_code" name="group_code" class="inp-field wid700 notEmpty" value="${groupCodeInfo.group_code }" maxlength="3">
						</td>
					</tr>
					<tr>
						<th>그룹명</th>
						<td class="linelY">
							<input type="text" id="group_name" name="group_name" value="${groupCodeInfo.group_name }" class="inp-field wid700 notEmpty">
						</td>
					</tr>
					<tr>
						<th>사용 여부</th>
						<td class="linelY">
							<input type="radio" name="use_yn" value="1" <c:if test="${groupCodeInfo.use_yn eq '1' }">checked="checked"</c:if>>사용
							<input type="radio" name="use_yn" value="0" <c:if test="${groupCodeInfo.use_yn eq '0' }">checked="checked"</c:if>>미사용
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td class="linelY">
							${groupCodeInfo.reg_admin }
						</td>
					</tr>
					<tr>
						<th>작성일</th>
						<td class="linelY">
							${groupCodeInfo.reg_date }
						</td>
					</tr>
				</tbody>
			</table>
			<input type="hidden" name="group_seq" value="${groupCodeInfo.group_seq }" />
			<input type="hidden" name="before_code" value="${groupCodeInfo.group_code }" />
			<div class="btn-module mgtL" style="text-align: center;">
				<a style="float: left;" href="/admin/groupCode/groupCodeList" id="btnList" class="btnStyle04">목록</a>
			 	<a href="javascript:void(0);" onclick="javascript:deleteForm();" id="btnDel" class="btnStyle04">삭제</a>
			  	<a href="javascript:void(0);" onclick="javascript:updateForm();" id="btnEdit" class="btnStyle04">수정</a>
		    </div>
		</div>
	</article>