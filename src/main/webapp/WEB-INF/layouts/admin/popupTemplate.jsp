<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>일가자</title>
<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/common/css/common.css' />" media="all"	charset="utf-8"></link>
<script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/jquery-1.11.0.min.js' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/xl-toast.js' />" charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/common/js/common.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/jquery.form.js' />"	charset="utf-8"></script>

</head>
<body>

	<t:insertAttribute name="popupTemplate"/>
	<div class="wrap-loading" style="display: none;">
		<div  class="ajaxLoading">
			<div id="load"><img src="<c:url value='/resources/cms/img/ajax-loader.gif'/>"></div>
		</div>
	</div>
</body>
</html>
