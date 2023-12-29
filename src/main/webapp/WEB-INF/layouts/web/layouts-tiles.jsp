<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>

<html lang="ko">
	<head>
		
		<meta charset="utf-8">
			 
		<meta property="og:title" content="뉴스캐스터">
		<!-- <meta property="og:url" content="http://newscaster.co.kr"> -->
		<meta property="og:image" content="/resources/web/images/logo/logo1.png">
		<meta property="og:description" content="뉴스캐스터에서 다양한 정보와 유용한 컨텐츠를 만나 보세요">
		    
		<title>WEB</title>
		
		<link rel="shortcut icon" type="image/x-icon" href="/resources/web/images/logo/icon_24.png" />
				
		<link rel="stylesheet" type="text/css" href="/resources/web/css/common.css"><!-- 공통 css -->
		<link rel="stylesheet" type="text/css" href="/resources/web/css/layout.css"><!-- 공통 css -->
		<link rel="stylesheet" type="text/css" href="/resources/web/css/index.css"><!-- 공통 css -->
		<link rel="stylesheet" type="text/css" href="/resources/web/css/sub.css"><!-- 공통 css -->
		
		<link href="/resources/cms/css/jquery-ui.css" rel="stylesheet" type="text/css" />
		<link href="/resources/cms/css/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css" />
	
		
		<!-- <script type="text/javascript" src="/resources/web/js/jquery-1.9.1.min.js"></script> -->
		<!-- <script type="text/javascript" src="/resources/web/js/jquery-1.11.1.min.js"></script> -->
		<script type="text/javascript" src="/resources/cms/js/jquery.js" charset="utf-8"></script>
		<script type="text/javascript" src="/resources/web/js/jquery-ui.js"></script>
		<script type="text/javascript" src="/resources/cms/js/jquery-ui-timepicker-addon.js" charset="utf-8"></script>
		<script type="text/javascript" src="/resources/common/js/common.js"></script>
		<script type="text/javascript" src="/resources/web/js/scrap.js"></script>
		<script type="text/javascript" src="/resources/web/js/newPaging.js"></script>
		

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
		<div class="skipnav">
			<a href="#contents">본문 바로가기</a>
		</div>
		<div class="all">
					
				<%-- 디폴트 폼 --%>
				<form id="defaultFrm" method="post">			
				</form>
				<%-- //디폴트 폼 --%>
				
				<t:insertAttribute name="header"/>					
				<t:insertAttribute name="content"/>
				<t:insertAttribute name="footer"/>
				
		</div>
		
		<div class="wrap-loading" style="display: none;">
			<div  class="ajaxLoading">
				<div id="load"><img src="<c:url value='/resources/cms/img/ajax-loader.gif'/>"></div>
			</div>
		</div>
			
			<script type="text/javascript">
			$(function(){
					
			});
		</script>
			

	</body>
	
	
</html>