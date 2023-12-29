<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="ilgaja, 일가자">
<meta name="author" content="NEMODREAM Co., Ltd.">

<link rel="icon" href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico" mce_href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico" type="image/x-icon">

<title> 일가자 - 관리자 </title>


<link rel="stylesheet" type="text/css" href="<c:url value='/resources/cms/css/common.css' />" media="all" charset="utf-8"></link>

<link rel="stylesheet" type="text/css" href="<c:url value='/resources/cms/jqueryUi/jquery-ui.css' />" media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/cms/select2/css/select2.min.css' />" media="all" charset="utf-8"></link>

<script type="text/javascript" src="<c:url value='/resources/cms/grid/js/jquery-1.11.0.min.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/cms/jqueryUi/jquery-ui.min.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/cms/jqueryUi/select.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/cms/select2/js/select2.full.min.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/common.js" />" charset="utf-8"></script>

<c:set var="imgPath" value="/resources/upload" scope="application"/>

<script type="text/javascript">
//<![CDATA[

// resourcePath
var resourcePath = '${pageContext.request.contextPath}';

$(function() {
// message, location
<c:if test="${not empty message}">
  alert("${message}");
</c:if>

<c:if test="${not empty location}">
  window.location.href = "<c:url value='${location}' />";
</c:if>
});

//]]>
</script>

</head>
<body>
	
<t:insertAttribute name="noTemplate"/>

	
</body>
</html>
