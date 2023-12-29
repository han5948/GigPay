<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
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

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/common/css/common.css' />" media="all"
	charset="utf-8"></link>

<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/cms/jqueryUi/jquery-ui.css' />"	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/cms/grid/css/font-awesome.min.css' />"	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/cms/grid/css/ui.jqgrid.min.css' />"	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/cms/select2/css/select2.min.css' />"	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/cms/css/modal.css' />"	media="all" charset="utf-8"></link>


<script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/jquery-1.11.0.min.js' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/jqueryUi/jquery-ui.min.js' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/jqueryUi/select.js' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/select2/js/select2.full.min.js' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/jquery.jqgrid.min.js' />"	charset="utf-8"></script>
<%-- <script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/i18n/grid.locale-kr.js' />	charset="utf-8"></script> --%>

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

	function customConfirm(message, successCallback){
		$(".popup-content > p").text(message);
		$('.popup').fadeIn(300);
		
		$("#custom-success-button").click(function(){
			successCallback();
			$('.popup').fadeOut(300);
			$("#custom-success-button").off('click');
			$("#custom-cancel-button").off('click');
		});
		
		$("#custom-cancel-button").click(function(){
			$('.popup').fadeOut(300);
			$("#custom-success-button").off('click');
			$("#custom-cancel-button").off('click');
		});
	}
</script>

</head>
<body>
	<div id="wrapper">
		<c:choose>
			<c:when test="${sessionScope.ADMIN_SESSION.minimumReservesFlag eq 1 }"><t:insertAttribute name="reserves-header" /></c:when>
			<c:when test="${empty param.ilboView}"><t:insertAttribute name="header" /></c:when>
		</c:choose>
		<div id="container">
			<div class="dimmed"></div>
			<div id="contents" >
				<form id="defaultFrm" name="defaultFrm" method="post">
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
		<t:insertAttribute name="footer" />
	</div>

	<!-- 클립보드 복사를 위해 -->
	<div style="display: none;">
		<textarea id="myClipboard"></textarea>
	</div>

	<form id="fileFrm" method="post">
		<input type="hidden" name="fileName" value="" /> <input type="hidden"
			name="filePath" value="" /> <input type="hidden" name="fileOrgName"
			value="" />
	</form>

	<form id="mapFrm" method="post">
		<input type="hidden" id="map_mode" name="map_mode" value="" /> <input
			type="hidden" id="map_seq" name="map_seq" value="" /> <input
			type="hidden" id="map_lat" name="map_lat" value="" /> <input
			type="hidden" id="map_lng" name="map_lng" value="" /> <input
			type="hidden" id="map_addr" name="map_addr" value="" /> <input
			type="hidden" id="map_comment" name="map_comment" value="" />
	</form>
	
	<form id="locationFrm" method="post">
		<input type="hidden" id="location_seq" name="location_seq" value="" /> 
		<input type="hidden" id="location_lat" name="location_lat" value="" /> 
		<input type="hidden" id="location_lng" name="location_lng" value="" /> 
		<input type="hidden" id="location_kindCode" name="location_kindCode" value="" />
		<input type="hidden" id="ilbo_date" name="ilbo_date" value="" />
	</form>

	<form id="menuFrm" method="post">
		<input type="hidden" name="ilboView" value="" /> <input type="hidden"
			name="srh_ilbo_type" value="" /> <input type="hidden" name="srh_seq"
			value="" />
	</form>

	<div id="map" style="display: none"></div>

	<!-- // 팝업 : 구인처 등록  -->
	<div id="popup-layer2">
		<header class="header">
			<h1 class="tit">옵션 선택</h1>
			<a class="close" href="javascript:closeIrPopup('popup-layer2');">
				<img	src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" />
			</a>
		</header>

		<section style="height: 800px; display: flex;">
			<div class="cont-box">
				<article id="opt"></article>
			</div>
		</section>
	</div>
	
 
  	<div class="popup">
	    <div class="popup-content">
      		<p>모달내용</p>
      		<a class="closeBtn" href="javascript:void(0)">x</a>
      		<div id="custom-buttom-div">
    			<button id="custom-success-button" class="custom-button">예</button>
    			<button id="custom-cancel-button" class="custom-button">아니오</button>
    		</div>
    	</div>
  	</div>
</body>
</html>
