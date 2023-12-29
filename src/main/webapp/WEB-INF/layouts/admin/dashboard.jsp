<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="description" content="ilgaja, 일가자">
<meta name="author" content="NEMODREAM Co., Ltd.">
<link rel="icon"
	href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico"
	mce_href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico"
	type="image/x-icon">

<title>${htmlHeader}-일가자</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/jqueryUi/jquery-ui.css' />"
	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/grid/css/font-awesome.min.css' />"
	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/grid/css/ui.jqgrid.min.css' />"
	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/select2/css/select2.min.css' />"
	media="all" charset="utf-8"></link>

	


<script type="text/javascript"
	src="<c:url value='/resources/cms/grid/js/jquery-1.11.0.min.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/jqueryUi/jquery-ui.min.js' />"
	charset="utf-8"></script>
<!-- <script type="text/javascript" -->
<%-- 	src="<c:url value='/resources/cms/jqueryUi/select.js' />" --%>
<!-- 	charset="utf-8"></script> -->
<script type="text/javascript"
	src="<c:url value='/resources/cms/select2/js/select2.full.min.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/grid/js/jquery.jqgrid.min.js' />"
	charset="utf-8"></script>
<%-- <script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/i18n/grid.locale-kr.js' />	charset="utf-8"></script> --%>

<script type="text/javascript"
	src="<c:url value='/resources/cms/js/jquery.form.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/js/xl-toast.js' />" charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/js/common.ilgaja.js?ver=1.0' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value="/resources/web/js/newPaging.js" />" charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/validate.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value="/resources/common/js/common.js" />" charset="utf-8"></script>

<!-- dashboard  -->
<!-- Bootstrap Styles-->
<link href="/resources/bootstrap/assets/css/bootstrap.css" rel="stylesheet" />
<!-- FontAwesome Styles-->
<link href="/resources/bootstrap/assets/css/font-awesome.css" rel="stylesheet" />
<!-- Morris Chart Styles-->
<link href="/resources/bootstrap/assets/js/morris/morris-0.4.3.min.css"	rel="stylesheet" />
<!-- Custom Styles-->
<link href="/resources/bootstrap/assets/css/custom-styles.css" rel="stylesheet" />
<!-- Google Fonts-->
<link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />

<link href="/resources/bootstrap/assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
<link href="/resources/bootstrap/assets/css/modal.css" rel="stylesheet" />


<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/common/css/common.css' />" media="all"
	charset="utf-8"></link>
	
<link href="/resources/common/css/dashboard.css" rel="stylesheet" />
	
	
<!-- <script src="https://code.jquery.com/jquery-2.2.1.min.js"></script> -->
<script type="text/javascript"
	src="<c:url value='/resources/cms/js/common.ilgaja.js?ver=1.0' />"	charset="utf-8"></script>
</head>
<body>
	<div id="wrapper">
		<t:insertAttribute name="header" />

		<div class="dimmed"></div>
		<form id="defaultFrm" name="defaultFrm" method="post">
			<t:insertAttribute name="dashboard" />
			<div class="wrap-loading" style="display: none;">
				<div class="ajaxLoading">
					<div id="load">
						<img src="<c:url value='/resources/cms/img/ajax-loader.gif'/>">
					</div>
				</div>
			</div>
		</form>
	</div>
	<!-- /. WRAPPER  -->
	<!-- JS Scripts-->
	<!-- jQuery Js -->
	<!--     <script src="/resources/bootstrap/assets/js/jquery-1.10.2.js"></script> -->
	<!-- Bootstrap Js -->
	<script src="/resources/bootstrap/assets/js/bootstrap.min.js"></script>

	<!-- Metis Menu Js -->
	<script src="/resources/bootstrap/assets/js/jquery.metisMenu.js"></script>
	<!-- Morris Chart Js -->
	<script src="/resources/bootstrap/assets/js/morris/raphael-2.1.0.min.js"></script>
	<script src="/resources/bootstrap/assets/js/morris/morris.js"></script>


	<script src="/resources/bootstrap/assets/js/easypiechart.js"></script>
	<script src="/resources/bootstrap/assets/js/easypiechart-data.js"></script>

	<script
		src="/resources/bootstrap/assets/js/Lightweight-Chart/jquery.chart.js"></script>

	<!-- Custom Js -->
<!-- 	<script src="/resources/bootstrap/assets/js/custom-scripts.js"></script> -->
	
	 <!-- DATA TABLE SCRIPTS -->
    <script src="/resources/bootstrap/assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="/resources/bootstrap/assets/js/dataTables/dataTables.bootstrap.js"></script>
</body>
</html>
