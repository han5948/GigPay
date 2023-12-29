<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>

<meta http-equiv="content-type" content="text/html; charset=utf-8">

<title>일가자 system</title>

<link rel="shortcut icon" type="image/x-icon" href="/resources/admin/images/logo/icon_24.png" />

<link href="<c:url value="/resources/common/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/common/css/jquery-ui-timepicker-addon.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/common/css/board.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/common/css/common.css" />" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<c:url value="/resources/common/js/jquery-3.4.1.min.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/jquery.form.min.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/jquery-ui.min.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/jquery-ui-timepicker-addon.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/paging.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/common.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/common/js/xl-toast.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/common/js/validate.js' />" charset="utf-8"></script>

<c:url value="/resources/upload" var="imgPath"/>
<script type="text/javascript">

	var timerVal;

	// resourcePath
	var resourcePath = '${pageContext.request.contextPath}';

	//접근성 관련 포커스 강제 이동
	function accessibilityFocus() {
		$(document).on(
				'keydown',
				'[data-focus-prev], [data-focus-next]',
				function(e) {
					var next = $(e.target).attr('data-focus-next'), prev = $(
							e.target).attr('data-focus-prev'), target = next
							|| prev || false;

					if (!target || e.keyCode != 9) {
						return;
					}

					if ((!e.shiftKey && !!next) || (e.shiftKey && !!prev)) {
						setTimeout(function() {
							$('[data-focus="' + target + '"]').focus();
						}, 1);
					}

				});
	}

	function tooltip() {
		var openBtn = '[data-tooltip]', closeBtn = '.tooltip-close';

		function getTarget(t) {
			return $(t).attr('data-tooltip');
		}

		function open(t) {
			var showTarget = $('[data-tooltip-con="' + t + '"]');
			showTarget.show().focus();
			showTarget.find('.tooltip-close').data('activeTarget', t);
		}

		function close(t) {
			var activeTarget = $('[data-tooltip-con="' + t + '"]');
			activeTarget.hide();
			$('[data-tooltip="' + t + '"]').focus();
		}

		$(document).on('click', openBtn, function(e) {
			e.preventDefault();
			open(getTarget(e.target));
		})

		.on('click', closeBtn, function(e) {
			e.preventDefault();
			close($(this).data('activeTarget'));
		})
	}

	$(document).ready(function() {
		tooltip();
		accessibilityFocus();
	});

</script>

<style type="text/css" >
	
	
</style>

</head>

<body>

<div id="wrap">
	<%-- 디폴트 폼 --%>
	<form id="defaultFrm" method="post"></form>
	<%-- //디폴트 폼 --%>

	<t:insertAttribute name="header"/>
	<div id="container" >
		<div class="dimmed"></div>
		
		<div id="contents" class="mgtL">
		<t:insertAttribute name="content"/>
		</div>
		<div class="wrap-loading" style="display: none;">
			<div  class="ajaxLoading">
				<div id="load"><img src="<c:url value='/resources/common/images/ajax-loader.gif'/>"></div>
			</div>
		</div>
	</div>
	
	<t:insertAttribute name="footer"/>
</div>

</body>
</html>