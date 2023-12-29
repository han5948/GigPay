<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<header id="header-wrap">
	<div id="header" class="header">
<c:if test="${prevLink ne ''}">	
		<a href="${prevLink}" class="prev"><span class="blind">이전</span></a>
</c:if>		
<c:if test="${isCloseButton eq true}">	
		<a href="#n" class="placement-close"><span class="blind">상세보기 닫기</span></a>
</c:if>		
		<h2 class="tit"><a href="javascript:location.reload(); " ><span>${pageTitle}</span></a></h2>

<c:if test="${isMenu eq true}">
		<a href="#n" class="bt-mnall" id="mn-ctrs-btns"><span class="blind">전체메뉴</span></a>
		<!-- s : mainNavi-wrap -->
		<div id="mainNavi-wrap" >
			<div id="mainNavi">
				<h2><span class="blind">일가자 주메뉴</span></h2>
				<input type="hidden" id="manager_seq" name="manager_seq" value="${manager_seq }" />
				
				<!-- <a href="#" class="bt-option"><span class="blind">설정</span></a> -->
				<a href="#" class="bt-mnclose"><span class="blind">닫기</span></a>
				<div class="tm-tit">
					<p class="btxt">${loginName }</p>
					<!-- <p class="stxt">
						<span>등록번호</span>2358468-157
					</p> -->
				</div>
				<ul class="topmenu" id="topmenu">
					<li id="tm01" class="mn_l1 mn_type over"><a href="/manager/main" class="mn_a1"><span class="mn_s1">홈</span></a></li>
					<!-- <li id="tm02" class="mn_l1 mn_type"><a href="/manager/main" class="mn_a1"><span class="mn_s1">구인현황</span></a></li> -->
					<li id="tm06" class="mn_l1 mn_type"><a href="javascript:logOut();" class="mn_a1"><span class="mn_s1">로그아웃</span></a></li>
					
					<!--
					<li id="tm03" class="mn_l1 mn_type"><a href="#n" class="mn_a1"><span class="mn_s1">구직자정보</span></a></li> 
					<li id="tm03" class="mn_l1 mn_type"><a href="#n" class="mn_a1"><span class="mn_s1">구직자정보</span></a></li>
					<li id="tm04" class="mn_l1 mn_type"><a href="#n" class="mn_a1"><span class="mn_s1">공지사항</span></a></li>
					<li id="tm05" class="mn_l1 mn_type"><a href="#n" class="mn_a1"><span class="mn_s1">PC이용안내</span></a></li> 
					-->
				</ul>
				
				<c:if test="${!empty  manager_push_yn}">	
				<div class="switch-menu">
					<div class="mn_1">푸쉬수신</div>
					<label class="switch">
					  <input id="manager_push_yn" type="checkbox" ${ manager_push_yn eq "1" ? "checked" : ""} >
					  <span class="slider round"></span>
					</label>
				</div>
				</c:if>
				
				<div class="asid-btn">
							<c:if test="${login_type eq  'E'}">
							<button type="button" class="mybuisness_modi">내 사업자 정보</button>
							</c:if>
							<button type="button" class="password_modi">비밀번호변경</button>
				</div>
						
				
						
				<p class="f-logo"><span class="blind">일가자</span></p>
				<script type='text/javascript'>initNavigation(0,0)</script>
			</div>
		</div>
		<!-- e : mainNavi-wrap -->
</c:if>
		
	</div>
</header>