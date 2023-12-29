<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<nav id="gnb">
		<h1><a href="javascript:location.reload(); ">&nbsp;<img src="<c:url value='/resources/cms/images/ilgaja_logo.png'/>" height="36" alt="로고 이미지" /></a></h1>
		<ul>
			<li><a href="/system/board/boardGroupList" style="margin-left:30px">게시판관리</a></li>
			<li><a href="/common/dragdropUpload">드래그드랍업로드</a></li>
		</ul>
		

    	<div class="userInfo">
		    <span class="thmb" ></span>
		    <span class="username">${sessionScope.ADMIN_SESSION.admin_name} 님</span>
		    <a class="logout" href="/system/logout">LOGOUT</a>
		  </div>
 	</nav>
	