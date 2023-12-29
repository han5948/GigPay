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
	
	function deleteForm() {
		if(!confirm("해당 이슈를 삭제하시겠습니까?")) {
			
			return false;
		}
		
		var _data = {
			issue_seq : $("#issue_seq").val(),
			mod_admin : $("#mod_admin").val()
		};
		
		$.ajax({
			type : "POST",
			url  : "/admin/issue/deleteIssueInfo",
			data : _data,
			dataType : 'json',
			success : function(data) {
				if ( data.code == "0000" ) {
					toastOk("정상적으로 삭제 되었습니다.");
					
					commSubmit('/admin/issue/issueList', $("#btnList")[0]);
				} else {
					toastFail("삭제 실패");
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
					toastFail("삭제 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		});
	}
	
	function updateForm() {
		if($("#issue_date").val() == 0) {
			alert("이슈 날짜를 입력해주세요.");
			
			$("#issue_date").focus();
			
			return false;
		}
		
		if($("#issue_text").val().replace(/\s/g, "").length == 0) {
			alert("이슈 내용을 입력해주세요.");
			
			$("#issue_text").focus();
			
			return false;
		}
		
		var _data = {
			issue_seq : $("#issue_seq").val(),
			issue_date : $("#issue_date").val(),
			issue_text : $("#issue_text").val(),
			mod_admin : $("#mod_admin").val()
		};
		
		$.ajax({
			type : "POST",
			url  : "/admin/issue/updateIssueInfo",
			data : _data,
			dataType : 'json',
			success : function(data) {
				if ( data.code == "0000" ) {
					toastOk("정상적으로 수정 되었습니다.");
					
					commSubmit('/admin/issue/issueList', $("#btnList")[0]);
				} else {
					toastFail("수정 실패");
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
					toastFail("수정 실패");
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
			<h3>이슈 수정</h3>
		</div>	
		
		<table class="bd-form" summary="이슈 수정">
			<colgroup>
				<col width="200px" />
	          	<col width="*" />
	        </colgroup>
	        <tbody>
		        <tr>
		        	<th>지점명</th>
		          	<td class="linelY">
		          		${issueDTO.company_name }
		          	</td>
		        </tr>
		        <tr>
		        	<th>이슈 날짜</th>
		          	<td class="linelY">
		          		<p class="floatL">
				    		<input type="text" id="issue_date" name="issue_date" readonly class="inp-field wid100 datepicker" value="${issueDTO.issue_date }" />
				    	</p>
		          	</td>
		        </tr>
		        <tr>
					<th>이슈 내용</th>
					<td class="linelY">
						<input type="text" id="issue_text" name="issue_text" value="${issueDTO.issue_text }" class="inp-field wid700 notEmpty">
					</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td class="linelY">
						${issueDTO.reg_admin }
					</td>
				</tr>
				<tr>
					<th>작성일</th>
					<td class="linelY">
						${issueDTO.reg_date }
					</td>
				</tr>
			</tbody>
		</table>
		<input type="hidden" id="issue_seq" name="issue_seq" value="${issueDTO.issue_seq }" />
		<input type="hidden" id="mod_admin" name="mod_admin" value="${sessionScope.ADMIN_SESSION.admin_id }" />
		<div class="btn-module mgtL" style="text-align: right;">
			<a style="float: left;" href="#" onclick="commSubmit('<c:url value="/admin/issue/issueList" />', this);" id="btnList" class="btnStyle04">목록</a>
		 	<a href="javascript:void(0);" onclick="javascript:deleteForm();" id="btnDel" class="btnStyle04">삭제</a>
		  	<a href="javascript:void(0);" onclick="javascript:updateForm();" id="btnEdit" class="btnStyle04">수정</a>
	    </div>
	</div>
</article>