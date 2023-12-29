<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
/*
  String urlPath = request.getAttribute("javax.servlet.forward.request_uri").toString();

  if ( urlPath.indexOf("/admin/main") > -1) {
    urlPath = "main";
  } else if (urlPath.indexOf("/admin/category") > -1) {
    urlPath = "category";
  } else if (urlPath.indexOf("/admin/news") > -1) {
    urlPath = "news";
  } else if (urlPath.indexOf("/admin/company") > -1) {
    urlPath = "company";
  } else if (urlPath.indexOf("/admin/broadcast") > -1) {
    urlPath = "adminMng";
  } else if (urlPath.indexOf("/admin/consign") > -1) {
    urlPath = "adminMng";
  } else if (urlPath.indexOf("/admin/agency") > -1) {
    urlPath = "adminMng";
  } else if (urlPath.indexOf("/admin/program") > -1) {
    urlPath = "program";
  } else if (urlPath.indexOf("/admin/notice") > -1) {
    urlPath = "notice";
  } else if (urlPath.indexOf("/admin/stat") > -1) {
    urlPath = "stat";
  }
*/
%>
<script type="text/javascript">
//<![CDATA[

// 게시판으로 이동
function goBoard(boardCode, dept1, dept2) {
  $("#defaultFrm").append("<input type='hidden' name='boardCode' value='"+boardCode+"'>");

  commSubmit("<c:url value='/admin/board/boardList'/>", dept1, dept2);
}

function logout() {
  if ( $("#logout") != null || $("#logout") != undefined) {
    $("#logout").remove();
    $(".aside").append("<form id='logout' method='post'></form>");
  } else {
    $(".aside").append("<form id='logout' method='post'></form>");
  }

  if ( confirm("로그아웃 하시겠습니까?")) {
    $("#logout").attr("action" , "<c:url value='/admin/logout'/>").submit();
  }
}

function myPage() {
  if ( $("#myPage") != null || $("#myPage") != undefined ) {
    $("#myPage").remove();
    $(".aside").append("<form id='myPage' method='post'></form>");
  } else {
    $(".aside").append("<form id='myPage' method='post'></form>");
  }

  $("#myPage").append("<input type='hidden' name='adminSeq' value='${sessionScope.ADMIN_SESSION.adminSeq}'/>");
  $("#myPage").attr("action" , "<c:url value='/admin/envir/selectAdminRegInfo'/>").submit();
}

//]]>
</script>
<%--
<c:set var="urlPath" value="<%=urlPath %>" />
--%>
    <div class="aside">
      <!-- 사용자 정보 -->
      <div class="login_user">
        <p><strong>${sessionScope.ADMIN_SESSION.admin_name}</strong>님, 환영합니다.</p>
        <div class="btn">
<%--
          <button type="button" onclick="javascript:myPage();">내 정보</button>
--%>
          <button type="button" onclick="javascript:logout();">로그아웃</button>
        </div>
      </div>
      <!-- // 사용자 정보 -->

      <div class="lnb">
<%--
        <c:if test="${urlPath eq 'main'}">
        </c:if>

        <c:if test="${urlPath eq 'category' or urlPath eq 'news' or urlPath eq 'program'}">
          <h2 class="ic01"><a href="javascript:commSubmit('<c:url value='/admin/program/programList'/>','2','1');">방송관리</a></h2>
          <ul>
          <c:if test="${sessionScope.ADMIN_SESSION.adminType eq '0' }">
            <li><a href="javascript:commSubmit('<c:url value='/admin/program/programList'/>','2','1');" >프로그램 관리</a></li>
          </c:if>
            <li><a href="javascript:commSubmit('<c:url value='/admin/news/newsList'/>','2','2');" >기사관리</a></li>
          </ul>
        </c:if>

        <c:if test="${urlPath eq 'company' or urlPath eq 'adminMng'}">
          <h2 class="ic01"><a href="javascript:commSubmit('<c:url value='/admin/company/companyList'/>','3','1');">회원관리</a></h2>
          <ul>
            <li><a href="javascript:commSubmit('<c:url value='/admin/company/companyList'/>','3','1');">일반(유료회원)</a></li>
            <li><a href="javascript:commSubmit('<c:url value='/admin/broadcast/broadcastList'/>','3','2');">언론사</a></li>
            <li><a href="javascript:commSubmit('<c:url value='/admin/consign/consignList'/>','3','3');">위탁사</a></li>
            <li><a href="javascript:commSubmit('<c:url value='/admin/agency/agencyList'/>','3','4');">대행사</a></li>
          </ul>
        </c:if>

        <c:if test="${urlPath eq 'stat'}">
          <h2 class="ic01"><a href="javascript:commSubmit('<c:url value='/admin/stat/contract'/>','4','1');">통계</a></h2>
          <ul>
            <li><a href="javascript:commSubmit('<c:url value='/admin/stat/contract'/>','4','1');">계약현황</a></li>
            <li><a href="javascript:commSubmit('<c:url value='/admin/stat/newsCntList'/>','4','2');">방송사통계</a></li>
          </ul>
        </c:if>

        <c:if test="${urlPath eq 'notice'}">
          <h2 class="ic01"><a href="javascript:commSubmit('<c:url value='/admin/notice/noticeList'/>','5','1');">게시판</a></h2>
          <ul>
            <li><a href="javascript:commSubmit('<c:url value='/admin/notice/noticeList'/>','5','1');">공지사항</a></li>
          </ul>
        </c:if>
--%>
      </div>
    </div>
