<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>

<html lang="ko">
	<head>
		<title>일가자인력, 내 손안의 스마트 인력사무소</title>
		<meta charset="utf-8">
		<meta property="og:title" content="일가자">
		<meta property="og:description" content="일용직전문, 내 손안의 스마트 인력사무소, ◆남성인력, 신호수, 잡부, 공장, 농사일, 이사짐, 조공, 양중, 자재정리, 철거, 철거뒷일, 하스리, 커팅, 그라인드, 곰방 ◆여성인력, 신호수, 준공청소, 이사정리, 공장, 농사일 ◆기술인력, 내장목수, 타일, 전기, 미장, 조적 벽돌, 4인치블럭, 6인치블럭, 8인치블럭, 메지, 설비, 페인트, 실리콘, 형틀목수, 철근, 타설, 용접, 비계설치, 비계해체, 경계석, 보도블럭">
		<meta property="og:image" content="https://ilgaja.com/resources/web/images/logo.png?param=1">
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
		<meta name="keywords" content="일가자, 일가자인력, ilgaja, ilgaja.com, dlfrkwk, 일용직, 취업, 구인, 구직, 구직정보, 구인정보, 아르바이트, 알바, 인력, 지점, 인력사무소, 용역, 직업소개소, 직업소개, 취업정보,구직사이트, 구인사이트, 고용정보, 일자리, 서울인력, 서울시인력, 종로구인력, 중구인력, 용산구인력, 성동구인력, 광진구인력, 동대문구인력, 중랑구인력, 성북구인력, 강북구인력, 도봉구인력, 노원구인력, 은평구인력, 서대문구인력, 마포구인력, 양천구인력, 강서구인력, 구로구인력, 금천구인력, 영등포구인력, 동작구인력, 관악구인력, 서초구인력, 강남구인력, 송파구인력, 강동구인력, 종로인력, 용산인력, 성동인력, 광진인력, 동대문인력, 중랑인력, 성북인력, 강북인력, 도봉인력, 노원인력, 은평인력, 서대문인력, 마포인력, 양천인력, 강서인력, 구로인력, 금천인력, 영등포인력, 동작인력, 관악인력, 서초인력, 강남인력, 송파인력, 강동인력" />
		<meta name="description" content="일용직전문, 내 손안의 스마트 인력사무소, ◆남성인력, 신호수, 잡부, 공장, 농사일, 이사짐, 조공, 양중, 자재정리, 철거, 철거뒷일, 하스리, 커팅, 그라인드, 곰방 ◆여성인력, 신호수, 준공청소, 이사정리, 공장, 농사일 ◆기술인력, 내장목수, 타일, 전기, 미장, 조적 벽돌, 4인치블럭, 6인치블럭, 8인치블럭, 메지, 설비, 페인트, 실리콘, 형틀목수, 철근, 타설, 용접, 비계설치, 비계해체, 경계석, 보도블럭" />
		<meta name="author" content="일용직전문, 내 손안의 스마트 인력사무소 www.ilgaja.com" />
		<meta name="copyright" content="일용직전문, 내 손안의 스마트 인력사무소 www.ilgaja.com" />
		
		<link rel="icon" href="/resources/web/images/ilgaja_logo_ico.ico" mce_href="/resources/web/images/ilgaja_logo_ico.ico" type="image/x-icon">
		
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<link rel="stylesheet" href="/resources/web/css/main.css?id=5">
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		
		
		<script type="text/javascript" src="/resources/web/js/main.js"></script>
		<script type="text/javascript" src="<c:url value="/resources/web/js/jquery-3.3.1.min.js" />" charset="utf-8"></script>
		<script type="text/javascript" src="/resources/web/js/jquery-ui.js"></script>
		<script type="text/javascript" src="/resources/cms/js/jquery-ui-timepicker-addon.js" charset="utf-8"></script>
		<script type="text/javascript" src="/resources/web/js/common.js"></script>
		<script type="text/javascript" src="/resources/web/js/newPaging.js"></script>

		<script type="text/javascript" src="<c:url value='/resources/common/js/xl-toast.js' />" charset="utf-8"></script>
		<script type="text/javascript" src="<c:url value='/resources/common/js/validate.js' />" charset="utf-8"></script>
		<script type="text/javascript" src="<c:url value="/resources/common/js/jquery.form.min.js" />" charset="utf-8"></script>
 		<script type="text/javascript"	src="<c:url value='/resources/common/js/common.js?ver=1.0' />"	charset="utf-8"></script>
 		
        <%-- <link rel="shortcut icon" href="<c:url value="/resources/cms/assets/favicon.ico" />" /> --%> 		
		
		<%-- <c:url value="/resources/upload" var="imgPath"/> --%>		
		
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
		
		<style type="text/css" >
			.wrap-loading{ /*화면 전체를 어둡게 합니다.*/
				z-index: 999999;
			    position: fixed;
			    left:0;
			    right:0;
			    top:0;
			    bottom:0;
			    background: rgba(0,0,0,0.1); /*not in ie */
			    filter: progid:DXImageTransform.Microsoft.Gradient(startColorstr='#10000000', endColorstr='#10000000');    /* ie */
			    
			}
			    .wrap-loading div{ /*로딩 이미지*/
			        position: fixed;
			        top:50%;
			        left:50%;
			        margin-left: -21px;
			        margin-top: -21px;
			    }
			    .display-none{ /*감추기*/
			        display:none;
			    }
			        
			</style>
	</head>	
<body>
<div id="wrap">

		<div class="all">
			<t:insertAttribute name="noTemplate"/>
		</div>
		
		<div class="wrap-loading" style="display: none;">
			<div  class="ajaxLoading">
				<div id="load"><img src="<c:url value='/resources/cms/img/ajax-loader.gif'/>"></div>
			</div>
		</div>

<!-- footer -->
	<div class="footer">
		<div class="footIn clear">
			<p class="footImg" style=" padding-top: 0.5em">
			<a href="http://ilgaja.com/resources/privacy/service_terms.htm" target="_blank">
			서비스 이용약관 
			</a>
			<br>
			<a href="http://ilgaja.com/resources/privacy/personal_terms.htm" target="_blank">
			<!-- <img src="/resources/web/images/footLogo.png" alt="일가자 인력 로고" border="0"> -->
			개인정보취급방침
			</a>
			</p>
			<div class="footTxt">
				<!-- ※ (주)잡앤파트너는 직업정보제공사업자로서 인력알선의 거래 당사자가 아니며, 구인구직자정보, 알선조건 및 이용 등과 관련한 의무와 책임은 일가자인력(제 2018-3100184-14-5-00002호)이 각 직업소개소에게 있습니다. -->
				<!-- (주)잡앤파트너는 직업정보제공사업자(J12060-2019-0006호)로서 인력알선의 거래당사자가 아니며, 구인구직정보, 알선 및 이용 등과 관련한 의무와 책임은 각 직업소개소에게 있습니다. -->
				직업정보제공사업자(J12060-2019-0006호) |  직업소개사업신고번호(제 2020-3130223-14-5-00020 호)
				<dl class="footInfo">
					<dd>(주)잡앤파트너 박종일</dd> |
					<dd>서울특별시 마포구 동교로22길 50 진희빌딩 301호</dd> |
					<dt>사업자번호 </dt>
					<dd> 445-88-01404</dd> |
					<dt>통신판매</dt>
					<dd>2019-서울구로-1185호</dd> | 
					<dt>전화 : </dt>
					<dd>1668-1903</dd>
				</dl>
				<p class="copyRight">Copyright&copy; (주)잡앤파트너 & 일가자인력 All rights reserved.</p>
			</div>
		</div>
	</div>
</div>
</body>
</html>
<!-- 
<script>
window.GitpleConfig = { appCode: '8AxrBlciqJJ5aAGvTD8ykHVtYa1bx3' };
!function(){function e(){function e(){var e=t.contentDocument,a=e.createElement("script");a.type="text/javascript",a.async=!0,a.src=window[n]&&window[n].url?window[n].url+"/inapp-web/gitple-loader.js":"https://app.gitple.io/inapp-web/gitple-loader.js",a.charset="UTF-8",e.head&&e.head.appendChild(a)}var t=document.getElementById(a);t||((t=document.createElement("iframe")).id=a,t.style.display="none",t.style.width="0",t.style.height="0",t.addEventListener?t.addEventListener("load",e,!1):t.attachEvent?t.attachEvent("onload",e):t.onload=e,document.body.appendChild(t))}var t=window,n="GitpleConfig",a="gitple-loader-frame";if(!window.Gitple){document;var i=function(){i.ex&&i.ex(arguments)};i.q=[],i.ex=function(e){i.processApi?i.processApi.apply(void 0,e):i.q&&i.q.push(e)},window.Gitple=i,t.attachEvent?t.attachEvent("onload",e):t.addEventListener("load",e,!1)}}();
Gitple('boot');
</script>
 -->
