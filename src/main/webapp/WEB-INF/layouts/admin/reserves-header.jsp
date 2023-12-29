<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
//<![CDATA[

function logout() {
	if ( $("#logout") != null || $("#logout") != undefined ) {
    	$("#logout").remove();
    	$(".aside").append("<form id='logout' method='post'></form>");
  	} else {
	    $(".aside").append("<form id='logout' method='post'></form>");
  	}

  	if ( confirm("로그아웃 하시겠습니까?") ) {
	    $("#logout").attr("action" , "<c:url value='/admin/logout'/>").submit();
  	}
}

function popupCal(){
	window.open("/admin/jobPriceCal","new","width=1500,height=800,top=100,left=100");
}

//]]>
</script>
<nav id="gnb">
	<h1>
		<a>&nbsp;<img src="<c:url value='/resources/cms/images/ilgaja_logo.png'/>" height="36" alt="로고 이미지" /></a>
	</h1>
  	<ul>
  		<li class="1deptMenu">
			<a href="#" onclick="commSubmit('<c:url value="/admin/reserves" />', this, 'reserves');">
				적립금
			</a>
		</li>
	</ul>
	<div class="userInfo">
    	<span class="thmb" ></span>
    	<span class="username">
    		${sessionScope.ADMIN_SESSION.company_name} ${sessionScope.ADMIN_SESSION.admin_name} 님
    	</span>
    	<a class="logout" href="/admin/logout">LOGOUT</a>
	</div>
</nav>