<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="ilgaja, 일가자">
<meta name="author" content="NEMODREAM Co., Ltd.">

<title> 일가자 - 관리자 </title>
<%--
    <script type="text/javascript" src="<c:url value="/resources/cms/js/jquery.js" />" charset="utf-8"></script>
    <script type="text/javascript" src="<c:url value="/resources/cms/js/jquery-ui.min.js" />" charset="utf-8"></script>
    <script type="text/javascript" src="<c:url value="/resources/cms/js/jquery-ui-timepicker-addon.js" />" charset="utf-8"></script>
    <script type="text/javascript" src="<c:url value="/resources/cms/js/newPaging.js" />" charset="utf-8"></script>
    <script type="text/javascript" src="<c:url value="/resources/cms/js/common.js" />" charset="utf-8"></script>
--%>

<c:url value="/resources/upload" var="imgPath" />

<script type="text/javascript">
//<![CDATA[

// resourcePath
var resourcePath = '${pageContext.request.contextPath}';

//]]>
</script>

<style type="text/css" >
.wrap-loading {               /* 화면 전체를 어둡게 합니다. */
  z-index: 999999;
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.1);    /* not in ie */
  filter: progid: DXImageTransform.Microsoft.Gradient(startColorstr='#10000000', endColorstr='#10000000');    /* ie */
}

.wrap-loading div {           /* 로딩 이미지 */
  position: fixed;
  top: 50%;
  left: 50%;
  margin-left: -21px;
  margin-top: -21px;
}

.display-none {               /* 감추기 */
  display:none;
}
</style>

</head>
<body>

<div id="wrap">
  <%-- 디폴트 폼 --%>
  <form id="defaultFrm" method="post">
  </form>
  <%-- //디폴트 폼 --%>

  <t:insertAttribute name="header"/>

  <div id="container">
    <t:insertAttribute name="left"/>
    <t:insertAttribute name="content"/>

    <div class="wrap-loading" style="display: none;">
      <div  class="ajaxLoading">
        <div id="load"><img src="<c:url value='/resources/cms/img/ajax-loader.gif'/>"></div>
      </div>
    </div>
  </div>

  <t:insertAttribute name="footer"/>
</div>

</body>
</html>
