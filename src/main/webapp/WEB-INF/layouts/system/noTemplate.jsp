<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>

<meta http-equiv="content-type" content="text/html; charset=utf-8">

<title>CAST PICK</title>

<link rel="shortcut icon" type="image/x-icon" href="/resources/admin/images/logo/icon_24.png" />

<link href="<c:url value="/resources/common/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/common/css/jquery-ui-timepicker-addon.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/common/css/common.css" />" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<c:url value="/resources/common/js/jquery-3.4.1.min.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/jquery.form.min.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/jquery-ui.min.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/jquery-ui-timepicker-addon.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/paging.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/common.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/common/js/xl-toast.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/common/js/validate.js' />" charset="utf-8"></script>

<c:set var="imgPath" value="/resources/upload" scope="application"/>

<script type="text/javascript">
//<![CDATA[

	// resourcePath
	var resourcePath = '${pageContext.request.contextPath}';


//]]>
</script>
</head>	

<body class="bgn">

	<div id="container">				
		<t:insertAttribute name="noTemplate"/>
		
		<div class="wrap-loading" style="display: none;">
			<div  class="ajaxLoading">
				<div id="load"><img src="<c:url value='/resources/common/images/ajax-loader.gif'/>"></div>
			</div>
		</div>
	</div>

</body>
</html>