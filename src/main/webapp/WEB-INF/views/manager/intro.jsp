<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link type="text/css" rel="stylesheet" media="all" href="<c:url value='/resources/manager/css/content.css' />" />

<script>
	$(document).ready(function() {
		$('#loading').find('.bar > span').stop().animate({'width':'100%'},3000);

	});

	function forwarding()
	{
		location = "/manager/login"
	}

</script>


<body id="intro"  onLoad="setTimeout('forwarding()', 3200)">
	<div id="intro-wrap">
		<div class="is-con">
			<div class="logo"><img src="/resources/manager/image/Intro/logo.png" alt=""/></div>
			<div id="loading">
				<p class="bar"><span></span></p>
				
			</div>
			
		</div>
		
		<div style="text-align:center;color:#FFFFFF;top:45%;position:absolute;width:100%">
		<p class="type">구인자용</p>
		</div>
		<div style="text-align:center;color:#FFFFFF;top:70%;position:absolute;width:100%">
						일용직 전문, 내 손안의 스마트 인력사무소
		</div>
		
		
	</div>
	<footer class="footer-wrap" id="footer-wrap" style="position: absolute;">
			<div id="footer">
			
				통신판매 2019-서울구로-1185호 | 전화:070-8666-1634<br/> 
		      	Copyright &copy; 2011-2018 주)잡앤파트너 Co., Ltd. All rights reserved<br/>
		      	Copyright &copy; 2003-2018 Powered by Nemodream Co., Ltd.
      
			</div>
		</footer>
</body>