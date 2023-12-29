<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="description" content="ilgaja, 일가자">
<meta name="author" content="NEMODREAM Co., Ltd.">
<link rel="icon"
	href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico"
	mce_href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico"
	type="image/x-icon">

<title>${htmlHeader}-일가자</title>

<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/common/css/common.css' />" media="all"	charset="utf-8"></link>

<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/cms/jqueryUi/jquery-ui.css' />"	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/cms/select2/css/select2.min.css' />"	media="all" charset="utf-8"></link>

<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/cms/grid/css/font-awesome.min.css' />"	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/cms/grid/css/ui.jqgrid.min.css' />"	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/common/css/board.css' />"	media="all" charset="utf-8"></link>

<script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/jquery-1.11.0.min.js' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/jquery.jqgrid.min.js' />"	charset="utf-8"></script>
<%-- <script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/i18n/grid.locale-kr.js' />	charset="utf-8"></script> --%>


<script type="text/javascript"	src="<c:url value='/resources/cms/jqueryUi/jquery-ui.min.js' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/jqueryUi/select.js' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/select2/js/select2.full.min.js' />"	charset="utf-8"></script>

<script type="text/javascript"	src="<c:url value='/resources/cms/js/jquery.form.js' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/xl-toast.js' />" charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/js/common.ilgaja.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/cms/js/newPaging.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/common/js/validate.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/common.js" />" charset="utf-8"></script>

<c:url value="/resources/upload" var="imgPath" />
<script type="text/javascript">
	// resourcePath
	var resourcePath = '${pageContext.request.contextPath}';
	var returnBool;
	// 좌측 메뉴 코드를 세션에서 얻어와 셋팅한다
	var dept1Code = "0"; // 1Dept 코드
	var dept2Code = "0"; // 2Dept 코드

	$(function() {

		$(".datepicker").datepicker();
		$(".datepicker").val(toDay);

		$(".datepicker").click(function() {
			$(this).select();
		});

		// message, location
		<c:if test="${not empty message}">
		alert("${message}");
		</c:if>

		<c:if test="${not empty location}">
		window.location.href = "<c:url value='${location}' />";
		</c:if>

		<c:if test="${not empty sessionScope.ADMIN_DEPT1_CODE}">
		dept1Code = "${sessionScope.ADMIN_DEPT1_CODE}";
		</c:if>
		<c:if test="${not empty sessionScope.ADMIN_DEPT2_CODE}">
		dept2Code = "${sessionScope.ADMIN_DEPT2_CODE}";
		</c:if>
		
		if (dept1Code != "-1") {
			$(".1deptMenu:eq(" + dept1Code + ")").css("background", "#FFF");
			$(".1deptMenu:eq(" + dept1Code + ")").css("height", "28px");
			$(".1deptMenu:eq(" + dept1Code + ") a").css("background", "#FFF")
					.css("color", "#5aa5da");
		}
		
		if (dept2Code != "-1") {
			$(".2deptMenu:eq(" + dept2Code + ") a").css("background", "#5aa5da")
					.css("color", "#FFF");
		}
		
		$("#contents").css({width:"calc(100% - 240px)",  minWidth:"auto"});
	});

	$(window).load(function() {

		$popLayer = $('#popup-layer');
		$dimmed = $('.dimmed');

		popLayer_html = $('#popup-layer').html();

		$('#srh_text').keyup(function(event) {
			if (event.keyCode == 13)
				$('#btnSrh').click();
		});

		$("#srh_text").on("click", function() {
			$(this).select();
		});
	});
</script>

</head>
<body>
	<div id="wrap">
		<c:if test="${empty param.ilboView}">
			<t:insertAttribute name="header" />
		</c:if>
		<div id="container">
			<form id="defaultFrm" name="defaultFrm" method="post">
				<div id="sub">
				</div>
			</form>
			<div class="dimmed"></div>
			<div id="contents" >
				<form id="contentFrm" name="contentFrm" method="post">
					<t:insertAttribute name="content" />

					<div class="wrap-loading" style="display: none;">
						<div class="ajaxLoading">
							<div id="load">
								<img src="<c:url value='/resources/cms/img/ajax-loader.gif'/>">
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
