<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0 ,maximum-scale=1.0, minimum-scale=1.0,user-scalable=no,target-densitydpi=medium-dpi">
<meta name="description" content="ilgajaM, 일가자M">
<meta name="author" content="NEMODREAM Co., Ltd.">
<script> 
//메인에서 만 사용 된다.
var pageName = "";

var userAgent = navigator.userAgent.toLowerCase(); // 접속 핸드폰 정보 
   
// 모바일 홈페이지 바로가기 링크 생성 
if(userAgent.match('iphone')) { 
    document.write('<link rel="apple-touch-icon" href="/resources/manager/image/ilgaja_icon.png" />'); 
} else if(userAgent.match('ipad')) { 
    document.write('<link rel="apple-touch-icon" sizes="72*72" href="/resources/manager/image/ilgaja_icon72.png" />'); 
} else if(userAgent.match('ipod')) { 
    document.write('<link rel="apple-touch-icon" href="/resources/manager/image/ilgaja_icon.png" />') ;
} else if(userAgent.match('android')) { 
	document.write('<link rel="shortcut icon" href="/resources/manager/image/ilgaja_icon.png" />'); 
} else{
	document.write('<link rel="icon" href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico" mce_href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico" type="image/x-icon">');	
}
</script>

<title>일가자-구인</title>



<link type="text/css" rel="stylesheet" media="all" href="<c:url value='/resources/manager/css/common.css' />" />
<link type="text/css" rel="stylesheet" media="all" href="<c:url value='/resources/manager/css/styleDefault.css' />" />
<link type="text/css" rel="stylesheet" media="all" href="<c:url value='/resources/manager/css/layout.css' />" />
<link type="text/css" rel="stylesheet" media="all" href="<c:url value='/resources/manager/css/content.css' />" />
<link type="text/css" rel="stylesheet" media="all" href="<c:url value='/resources/manager/css/ion.calendar.css' />" />


<script type="text/javascript" src="<c:url value='/resources/manager/js/jquery-1.11.3.min.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/manager/js/jquery.easing.1.3.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/manager/js/common.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/manager/js/defalut.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/manager/js/topmenu.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/manager/js/sub.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/manager/js/mainAjax.js' />" charset="utf-8"></script>

<script type="text/javascript" src="<c:url value='/resources/common/js/xl-toast.js' />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value='/resources/common/js/validate.js' />" charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/common/js/common.js?ver=1.0' />"	charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/jquery.form.min.js" />" charset="utf-8"></script>
	
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


	<body id="sub">
		
		<div id="skipNavi">
			<h1 class="blind">일가자 스킵네비게이션</h1>
			<ul>
				<li><a href="#contents" class="skipLink">본문바로가기</a></li>
				<li><a href="#topmenu" class="skipLink">주메뉴바로가기</a></li>
			</ul>
		</div>
		<!-- s: #doc //-->
		<div id="doc" class="isPage">	
			<!-- s: .doc-pg //-->
			<div id="pg-code" class="doc-pg">
			
				<t:insertAttribute name="header"/>					
				<t:insertAttribute name="content"/>
				<t:insertAttribute name="footer"/>
			
			<div class="wrap-loading" style="display: none;">
		      <div  class="ajaxLoading">
		        <div id="load"><img src="<c:url value='/resources/cms/img/ajax-loader.gif'/>"></div>
		      </div>
		    </div>
			
			</div>
			<!-- e: .doc-pg //-->
		</div>
		
		<script  type="text/javascript" src="<c:url value='/resources/manager/js/moment.min.js' />" charset="utf-8"></script>
		<script  type="text/javascript" src="<c:url value='/resources/manager/js/ion.calendar.js' />" charset="utf-8"></script>
	</body>
</html>

