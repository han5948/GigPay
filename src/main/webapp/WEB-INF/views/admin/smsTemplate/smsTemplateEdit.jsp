<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(document).ready(function() {
		$("#bd-form-table").css("width", "59%");
	});
	
	function updateForm() {
		if($("#template_title").val().replace(/\s/g, "").length == 0) {
			alert("제목을 입력해주세요.");
			
			$("#template_title").focus();
			
			return false;
		}
		
		if($("#template_content").val().replace(/\s/g, "").length == 0) {
			alert("내용을 입력해주세요.");
			
			$("#template_content").focus();
			
			return false;
		}
		
		$("#contentFrm").ajaxForm({
			url : "/admin/smsTemplate/updateSmsTemplate",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if ( data.code == "0000" ) {
						toastOk("정상적으로 수정 되었습니다.");
						
						location.href = '/admin/smsTemplate/smsTemplateList';
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
		if(!confirm("해당 SMS템플릿을 삭제하시겠습니까?")) {
			return false;
		}
		
		$("#contentFrm").ajaxForm({
			url : "/admin/smsTemplate/deleteGroupCode",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
					if ( data.code == "0000" ) {
						toastOk("정상적으로 삭제 되었습니다.");
						
						location.href = '/admin/smsTemplate/smsTemplateList';
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
			<h3>SMS 템플릿 수정</h3>
		</div>	
		
		<table class="bd-form" id="bd-form-table" summary="SMS 템플릿 수정">
			<colgroup>
				<col width="200px" />
	          	<col width="*" />
	        </colgroup>
	        <tbody>
				<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq '0' }">
			        <tr>
				        <th>보여지는 지점</th>
				        <td class="linelY">
			          		<select id="company_seq" name="company_seq" class="styled02">
			          			<option value="0">전체</option>
			          			<c:forEach var="item" items="${companyList }">
			          				<option value="${item.company_seq }" <c:if test="${item.company_seq eq smsTemplateInfo.company_seq }">selected</c:if>>${item.company_name }</option>
			          			</c:forEach>
			          		</select>
				        </td>
			        </tr>
	          	</c:if>
		        <tr>
		        	<th>제목</th>
		          	<td class="linelY">
		          		<input type="text" id="template_title" name="template_title" value="${smsTemplateInfo.template_title }" class="inp-field wid700 notEmpty">
		          	</td>
		        </tr>
		        <tr>
		        	<th>내용</th>
		          	<td class="linelY">
		          		<textarea id="template_content" style="padding: 10px;" name="template_content" rows="10" cols="98" class="notEmpty">${smsTemplateInfo.template_content }</textarea>
		          		<table class="bd-form" style="border-bottom:none; margin-top: 20px;">
							<colgroup>
								<col width="160px" /> 
								<col width="*" />
							</colgroup>
		          			<tr>
		          				<th>일일대장 예약어</th>
		          				<td class="linelY">
		          					<ul>
		          						<li>
			          						<span style="display:inline-block; width:300px;">{worker_name} - 구직자명</span>
			          						<span style="display:inline-block; width:300px;">{company_name} - 지점명</span>
											<span style="display:inline-block; width:300px;">{employer_name} - 구인처</span>
										</li>
										<li>
											<span style="display:inline-block; width:300px;">{work_name} - 현장</span>
											<span style="display:inline-block; width:300px;">{ilbo_date} - 출근일</span>
											<span style="display:inline-block; width:300px;">{work_time} -  근로시간</span>
										</li>
										<li>
											<span style="display:inline-block; width:300px;">{breakfast} - 조식</span>
											<span style="display:inline-block; width:300px;">{job_kind} - 직종</span>
											<span style="display:inline-block; width:300px;">{job_comment} - 작업 내용</span>
										</li>
										<li>
											<span style="display:inline-block; width:300px;">{price} - 단가</span>
											<span style="display:inline-block; width:300px;">{pay_type} - 지급 방법</span>
											<span style="display:inline-block; width:300px;">{work_manager_name} - 담당자</span>
										</li>
										<li>
											<span style="display:inline-block; width:300px;">{work_manager_phone} - 담당자 번호</span>
											<span style="display:inline-block; width:300px;">{company_name} - 지점명</span>
										</li>
									</ul>
		          				</td>
		          			</tr>
		          			<tr>
		          				<th>매니저관리 예약어</th>
		          				<td class="linelY">
		          					<ul>
		          						<li>
		          							<span style="display:inline-block; width:300px;">{manager_name} - 매니저명</span>
		          							<span style="display:inline-block; width:300px;">{company_name} - 지점명</span>
		          						</li>
		          					</ul>
		          				</td>
		          			</tr>
		          			<tr>
		          				<th>구직자관리 예약어</th>
		          				<td class="linelY">
		          					<ul>
		          						<li>
		          							<span style="display:inline-block; width:300px;">{worker_name} - 구직자명</span>
		          							<span style="display:inline-block; width:300px;">{company_name} - 지점명</span>
		          						</li>
		          					</ul>
		          				</td>
		          			</tr>
		          		</table>
		          	</td>
		        </tr>
		        <tr>
		        	<th>사용 여부</th>
		        	<td class="linelY">
		        		<input type="radio" name="use_yn" value="1" <c:if test="${smsTemplateInfo.use_yn eq '1' }">checked="checked"</c:if>>사용
		        		<input type="radio" name="use_yn" value="0" <c:if test="${smsTemplateInfo.use_yn eq '0' }">checked="checked"</c:if>>미사용
		        	</td>
		        </tr>
				<tr>
					<th>작성자</th>
					<td class="linelY">${smsTemplateInfo.mod_admin }</td>
				</tr>
		        <tr>
					<th>작성일</th>
					<td class="linelY">${smsTemplateInfo.mod_date }</td>
				</tr>
			</tbody>
		</table>
		
		<input type="hidden" id="template_seq" name="template_seq" value="${smsTemplateInfo.template_seq }" />
		
		<div class="btn-module mgtL" style="text-align: center;">
			<a style="float: left;" href="/admin/smsTemplate/smsTemplateList" id="btnList" class="btnStyle04">목록</a>
		 	<a href="javascript:void(0);" onclick="javascript:deleteForm();" id="btnDel" class="btnStyle04">삭제</a>
		  	<a href="javascript:void(0);" onclick="javascript:updateForm();" id="btnEdit" class="btnStyle04">수정</a>
	    </div>
	</div>
</article>