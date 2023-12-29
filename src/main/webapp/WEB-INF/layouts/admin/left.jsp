<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
//<![CDATA[

//]]>
</script>

<nav id="snb">
	<ul id="snbMenu">
		<c:if test="${leftMenu ne 'SMS' }">
			<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
				<li id="snb1" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/groupCode/groupCodeList" />', this);" style="">코드관리</a></li>
				<li id="snb12" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/job/jobList" />', this);">직종관리</a></li>
				<li id="snb2" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/companyList" />', this);">지점관리</a></li>
				<li id="snb22" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/transferWorker/optionList" />', this);">구직자이관</a></li>
				<li id="snb8" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/cityList" />', this);">날씨지역 관리</a></li>
				<li id="snb9" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/ad/adList" />', this);">광고 현황</a></li>
				<li id="snb10" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/board/boardGroupList" />', this);">게시판 관리</a></li>
				<li id="snb13" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/ilboWork/ilboWorkOption" />', this);">맞춤일자리</a></li>
				<li id="snb20" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/career/careerOption" />', this);">경력옵션</a></li>
				<li id="snb21" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/taxRate/taxRateList" />', this);">세금 및 사회보험</a></li>
			</c:if>
			<c:if test="${sessionScope.ADMIN_SESSION.auth_level le 1}">
				<li id="snb3" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/adminList" />', this);">지점별 관리자</a></li>
				<li id="snb14" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/taxbillList" />', this);">계산서 관리</a></li>
			</c:if>
			<li id="snb4" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/ilboSetting/ilboSettingInfo" />', this);">일일대장 설정</a></li>
			<c:if test="${sessionScope.ADMIN_SESSION.auth_level le 1 }">
				<li id="snb16" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/managerInduce/managerInduceList" />', this);">도출유도 관리</a></li>
			</c:if>
			<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0 }">
				<li id="snb17" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/categoryList"/>', this)">전자양식관리</a></li>
				<li id="snb23" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/autoSetting/autoSettingView"/>', this)">AI 배정 설정</a></li>
			</c:if>
			<c:if test="${sessionScope.ADMIN_SESSION.auth_level le 1 }">
				<li id="snb18" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/eDocumentList"/>', this)">전자문서 관리</a></li>
			</c:if>
			<li id="snb10" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/issue/issueList" />', this);">이슈 관리</a></li>
			<li id="snb7" class="2deptMenu"><a href="#" onclick="fn_tokenIssued()">Web Token 발급</a></li>
			<li id="snb11" class="2deptMenu"><a href="#" onclick="fn_versionBoard(this)">버젼관리</a></li>
			<li id="snb15" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/workerDrop"/>', this)">탈퇴회원근로이력</a></li>   
			<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0 or (sessionScope.ADMIN_SESSION.auth_level eq '1' and sessionScope.ADMIN_SESSION.company_seq eq '13') }">
				<li id="snb19" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/inquiry/inquiryList"/>', this)">제휴문의 내역</a></li>  
			</c:if>
		</c:if>
		<c:if test="${leftMenu eq 'SMS' }">
			<li id="snb4" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/smsTemplate/smsTemplateList" />', this);">SMS 템플릿 관리</a></li>
			<li id="snb5" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/sms/smsList" />', this);">SMS 이력 관리</a></li>	
	<%-- 		<li id="snb6" class="2deptMenu"><a href="#" onclick="commSubmit('<c:url value="/admin/sms/smsSend" />', this);">SMS 보내기</a></li>	 --%>
		</c:if>
  	</ul>
</nav>
