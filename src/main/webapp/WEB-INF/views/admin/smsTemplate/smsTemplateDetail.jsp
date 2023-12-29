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
</script>
<article>
	<div class="content mgtS">
	    <div class="title">
			<h3>SMS 템플릿 상세</h3>
	    </div>
	    
		<table class="bd-form" id="bd-form-table" summary="SMS 템플릿 상세">
	        <colgroup>
		        <col width="200px" />
		       	<col width="*" />
	        </colgroup>
	        <tbody>
				<tr>
					<th>제목</th>
					<td class="linelY">
						<input type="text" class="inp-field wid700 notEmpty" id="template_title" name="template_title" value="${smsTemplateInfo.template_title }" readOnly>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td class="linelY">
						<textarea style="padding: 10px;" id="template_content" name="template_content" readOnly rows="10" cols="98" class="notEmpty">${smsTemplateInfo.template_content }</textarea>
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
											<span style="display:inline-block; width:300px;">{work_manage_name} - 담당자</span>
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
		
		<div class="btn-module mgtL">
			<a href="/admin/smsTemplate/smsTemplateList" id="btnList" class="btnStyle04">목록</a>
		</div>
	</div>
</article>