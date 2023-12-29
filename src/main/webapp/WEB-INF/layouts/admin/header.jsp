<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav id="gnb">
	<h1>
		<a href="/admin/job/jobList ">&nbsp;<img src="<c:url value='/resources/cms/images/gigpay_logo.png'/>" height="36" alt="로고 이미지" /></a>
	</h1>
	<ul>
		<li class="1deptMenu">
			<a href="#" onclick="commSubmit('<c:url value="/admin/job/jobList" />', this);">
				직종관리
			</a>
		</li>
		<li class="1deptMenu">
			<a href="#" onclick="commSubmit('<c:url value="/admin/taxRate/taxRateList" />', this);">
				세금 및 사회보험
			</a>
		</li>
		<li class="1deptMenu">
			<a href="#" onclick="commSubmit('<c:url value="/admin/categoryList" />', this);">
				전자양식 관리
			</a>
		</li>
		<li class="1deptMenu">
			<a href="#" onclick="commSubmit('<c:url value="/admin/eDocumentList" />', this);">
				전자문서 관리
			</a>
		</li>
	</ul>
</nav>