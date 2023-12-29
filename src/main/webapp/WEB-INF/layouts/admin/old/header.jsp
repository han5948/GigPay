<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
.page-header .navbar-brand {
  color:#fff;
}
</style>

  <!-- header -->
  <div id="header">
    <h1> 사이트 관리자</h1>
    <ul>
<%--
      <li class="mn">
        <a class="mn17" href="javascript:commSubmit('<c:url value="/admin/main" />','1','1');" >홈</a>
      </li>
--%>

<c:if test="${sessionScope.ADMIN_SESSION.adminType eq '0'}">
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/program/programList" />','2','1');" class="mn05 ">방송관리</a>
      </li>
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/company/companyList" />','3','1');" class="mn05 ">회원관리</a>
      </li>
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/worker/workerList" />','4','1');" class="mn05 ">작업자관리</a>
      </li>
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/stat/contract" />','5','1');" class="mn05 ">통계</a>
      </li>
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/notice/noticeList" />','6','1');" class="mn05 ">게시판</a>
      </li>
</c:if>

<c:if test="${sessionScope.ADMIN_SESSION.adminType eq '1' }">
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/news/newsList" />','2','1');" class="mn05 ">방송관리</a>
      </li>
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/stat/contract" />','2','1');" class="mn05 ">통계</a>
      </li>
</c:if>

<c:if test="${sessionScope.ADMIN_SESSION.adminType eq '2' }">
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/stat/contract" />','2','1');" class="mn05 ">통계</a>
      </li>
</c:if>

<c:if test="${sessionScope.ADMIN_SESSION.adminType eq '3' }">
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/stat/contract" />','2','1');" class="mn05 ">통계</a>
      </li>
</c:if>

<c:if test="${sessionScope.ADMIN_SESSION.adminType eq '4' }">
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/news/newsList" />','2','1');" class="mn05 ">방송관리</a>
      </li>
</c:if>

<%--
      <li class="mn">
        <a class="mn05" href="javascript:commSubmit('<c:url value="/admin/shop/shopList" />','1','1');" >상점 관리</a>
      </li>
<c:if test="${sessionScope.ADMIN_SESSION.adminLevel eq '1' }">
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value='/admin/category/goodsCategory'/>','2','1');" class="mn05 ">카테고리관리</a>
      </li>
</c:if>
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value='/admin/goods/goodsList'/>','3','1');" class="mn05 ">상품관리</a>
      </li>

<c:if test="${sessionScope.ADMIN_SESSION.adminLevel eq '1' }">
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value='/admin/user/userList'/>','4','1');" class="mn01 ">회원관리</a>
      </li>
</c:if>
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value='/admin/order/orderList'/>','5','1');"  class="mn14">주문/배송관리</a>
      </li>
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value='/admin/event/eventList'/>','6','1');" class="mn04">이벤트관리</a>
      </li>
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value='/admin/grade/shopGradeList'/>','7','1');" class="mn07">평가관리</a>
      </li>
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/noti/adminNotiBoard"/>','8','1');" class="mn04">게시판 관리</a>
      </li>

      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/stats/userStats"/>','9','1');" class="mn07">통계</a>
      </li>

<c:if test="${sessionScope.ADMIN_SESSION.adminLevel eq '1' }">
      <li class="mn">
        <a href="javascript:commSubmit('<c:url value="/admin/envir/adminList"/>', '10', '1');" class="mn16 ">공통설정</a>
      </li>
</c:if>
--%>
    </ul>
    <p><img src="" alt="" /></p>
  </div>
  <!-- // header -->
