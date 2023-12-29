<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(document).ready(function() {
		//$("#notice_seq").val('');
		$("#contents").css("min-width", "1000px");
		
		var main_start_date = '${noticeDTO.main_start_date }';
		var main_end_date = '${noticeDTO.main_end_date }';
		
		$("#main_start_date").datepicker('setDate', main_start_date);
		$("#main_end_date").datepicker('setDate', main_end_date);
		
		$("#main_view_yn").on("click", function() {
			if($(this).is(":checked") == false) {
				$("#main_start_date").remove();
				$(".dateTxt").remove();
				$(".ui-datepicker-trigger").remove();
				$("#main_end_date").remove();
			}else {
				var text = '';
				
				text += '	<input type="text" id="main_start_date" name="main_start_date" class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>';
				text += '	<input type="text" id="main_end_date" name="main_end_date" class="inp-field wid100 datepicker" />';
					
				$("#mainView").append(text);
				
				$("#main_start_date").datepicker();
				$("#main_end_date").datepicker();
			}
		});
	});
	
	function updateForm() {

		
		if($("#notice_title").val().replace(/\s/g, "").length == 0) {
			alert("제목을 입력해주세요.");
			
			$("#notice_title").focus();
			
			return false;
		}
		
		if($("#notice_contents").val().replace(/\s/g, "").length == 0) {
			alert("내용을 입력해주세요.");
			
			$("#notice_contents").focus();
			
			return false;
		}
		
		if($("#main_view_yn").is(":checked") == true) {
			if($("#main_start_date").val() == '' || $("#main_end_date").val() == '') {
				alert("노출 날짜를 선택해주세요.");
				
				return false;
			}
		}
		
		var test = replaceAll($("#notice_contents").val(), /\r\n|\n/, "<br>");
		
		if($("#main_view_yn").is(":checked") == true) {
			var _data = {
					notice_seq : $("#notice_seq").val(),
					notice_title : $("#notice_title").val(),
					//notice_contents : replaceAll($("#notice_contents").val(), /\r\n|\n/, "<br>"),
					notice_contents : $("#notice_contents").val(),
					auth_view : $("input[name=auth_view]:checked").val(),
					auth_company_seq : $("#auth_company_seq").val(),
					use_yn : $("input[name=use_yn]:checked").val(),
					main_view_yn : 1,
					main_start_date : $("#main_start_date").val(),
					main_end_date : $("#main_end_date").val()
				};
		}else {
			var _data = {
				notice_seq : $("#notice_seq").val(),
				notice_title : $("#notice_title").val(),
				//notice_contents : replaceAll($("#notice_contents").val(), /\r\n|\n/, "<br>"),
				notice_contents : $("#notice_contents").val(),
				auth_view : $("input[name=auth_view]:checked").val(),
				auth_company_seq : $("#auth_company_seq").val(),
				use_yn : $("input[name=use_yn]:checked").val()
			};
		}
		
		$.ajax({
			type : "POST",
			url  : "/admin/notice/updateNotice",
			data : _data,
			dataType : 'json',
			success : function(data) {
				if ( data.code == "0000" ) {
					toastOk("정상적으로 수정 되었습니다.");
					
					commSubmit('/admin/notice/noticeList', $("#btnList")[0], 'notice');
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
	
	function deleteForm() {
		if(!confirm("해당 게시글을 삭제하시겠습니까?")) {
			return false;
		}
		
		var _data = {
			notice_seq : $("#notice_seq").val()		
		};
		
		$.ajax({
			type : "POST",
			url  : "/admin/notice/deleteNotice",
			data : _data,
			dataType : 'json',
			success : function(data) {
				if ( data.code == "0000" ) {
					toastOk("정상적으로 삭제 되었습니다.");
					
					commSubmit('/admin/notice/noticeList', $("#btnList")[0], 'notice');
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
</script>
<article>
	<div class="content mgtS mgbM">
		<div class="title">
			<h3>공지사항 수정</h3>
		</div>	
		
		<table class="bd-form" summary="공지사항 수정">
			<colgroup>
				<col width="200px" />
	          	<col width="*" />
	        </colgroup>
	        <tbody>
	        	<c:choose>
					<c:when test="${sessionScope.ADMIN_SESSION.auth_level eq '0' }">
				        <tr>
					        <th>보여지는 지점</th>
					        <td class="linelY">
				          		<select id="auth_company_seq" name="auth_company_seq" class="styled02">
				          			<option value="0">전체</option>
				          			<c:forEach var="item" items="${companyList }">
				          				<option value="${item.company_seq }" <c:if test="${item.company_seq eq noticeDTO.auth_company_seq }">selected</c:if>>${item.company_name }</option>
				          			</c:forEach>
				          		</select>
					        </td>
				        </tr>
		          	</c:when>
		          	<c:otherwise>
		          		<input type="hidden" id="auth_company_seq" name="auth_company_seq" value="${sessionScope.ADMIN_SESSION.company_seq }">
		          	</c:otherwise>
		        </c:choose>
		        <tr>
		        	<th>APP</th>
		        	<td class="linelY">
		        		
							<p class="agreeCheck">
								<input type="radio" id="jobChk" 			name="auth_view"  value="0" checked />전체 
								&nbsp;<input type="radio" id="jobSeeker" 		name="auth_view"  value="1" <c:if test="${noticeDTO.auth_view eq '1' }">checked="checked"</c:if> />구직자 
								&nbsp;<input type="radio" id="jobOffer" 			name="auth_view" 	value="2" <c:if test="${noticeDTO.auth_view eq '2' }">checked="checked"</c:if> />구인처 
								<c:if test = "${sessionScope.ADMIN_SESSION.company_seq eq '13' }"> <!-- 본사일때  --> 
								&nbsp;<input type="radio" id="jobCompany" 	name="auth_view"  value="3" <c:if test="${noticeDTO.auth_view eq '3' }">checked="checked"</c:if> />지점 
								</c:if>
							</p>
							
		        	</td>
		        </tr>
		        <tr>
		        	<th>제목</th>
		          	<td class="linelY">
		          		<input type="text" id="notice_title" name="notice_title" value="${noticeDTO.notice_title }" class="inp-field wid700 notEmpty">
		          	</td>
		        </tr>
		        <tr>
		        	<th>내용</th>
		          	<td class="linelY">
		          		<textarea id="notice_contents" style="padding: 10px;" name="notice_contents" rows="10" cols="98" class="notEmpty"><c:out value="${noticeDTO.notice_contents }" escapeXml="false"></c:out></textarea>
		          	</td>
		        </tr>
		        <tr>
		        	<th>사용 여부</th>
		        	<td class="linelY">
		        		<input type="radio" name="use_yn" value="1" <c:if test="${noticeDTO.use_yn eq '1' }">checked="checked"</c:if>>사용
		        		<input type="radio" name="use_yn" value="0" <c:if test="${noticeDTO.use_yn eq '0' }">checked="checked"</c:if>>미사용
		        	</td>
		        </tr>
		        <tr>
					<th>메인 노출</th>
					<td class="linelY">
						<p class="agreeCheck" id="mainView">
							<label><input type="checkbox" id="main_view_yn" name="main_view_yn" value="1" <c:if test="${noticeDTO.main_view_yn eq '1' }">checked="checked"</c:if>/>메인 노출</label>
							<c:if test="${noticeDTO.main_view_yn eq '1' }">
								<input type="text" id="main_start_date" name="main_start_date" class="inp-field wid100 datepicker" /> 
								<span class="dateTxt">~</span>
								<input type="text" id="main_end_date" name="main_end_date" class="inp-field wid100 datepicker" />
							</c:if>
						</p>
					</td>
				</tr>
		        <tr>
					<th>작성회사</th>
					<td class="linelY">${noticeDTO.notice_writer }</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td class="linelY">${noticeDTO.mod_admin }</td>
				</tr>
			</tbody>
		</table>
		
		<input type="hidden" id="notice_seq" name="notice_seq" value="${noticeDTO.notice_seq }" />
		
		<div class="btn-module mgtL" style="text-align: right;">
			<a style="float: left;" href="#" onclick="commSubmit('<c:url value="/admin/notice/noticeList" />', this, 'notice');" id="btnList" class="btnStyle04">목록</a>
		 	<a href="javascript:void(0);" onclick="javascript:deleteForm();" id="btnDel" class="btnStyle04">삭제</a>
		  	<a href="javascript:void(0);" onclick="javascript:updateForm();" id="btnEdit" class="btnStyle04">수정</a>
	    </div>
	</div>
</article>


	
