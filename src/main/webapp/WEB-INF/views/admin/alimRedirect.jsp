<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/jquery-1.11.0.min.js' />"	charset="utf-8"></script>
<script type="text/javascript"	src="<c:url value='/resources/cms/jqueryUi/jquery-ui.min.js' />"	charset="utf-8"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form id="menuFrm" name="menuFrm" method="post">
	<input type="hidden" name="srh_ilbo_type" id="srh_ilbo_type" value="" />
	<input type="hidden" name="ilboView" value="Y" />
	<input type="hidden" name="srh_seq" id="srh_seq" value="" />
</form>
</body>
<script>
	var srh_ilbo_type = '<c:out value="${srh_ilbo_type}" />';
	var srh_seq = '<c:out value="${srh_seq}" />';
	
	$(document).ready(function() {
		var url = '';
		
		if(srh_ilbo_type == 'i.work_seq') {
			url = '/admin/workIlbo';	
		}else if(srh_ilbo_type == 'i.worker_seq') {
			url = '/admin/workerIlbo';
		}else if(srh_ilbo_type == 'i.employer_seq') {
			url = '/admin/workIlbo';
		}else if(srh_ilbo_type == 'order') {
			url = '/admin/orderList';
			
			var frm = $("#menuFrm");
			var input1 = $("#menuFrm > input[name=ADMIN_DEPT1_CODE]");    // 관리자 메뉴 코드1
			if ( jQuery.type(input1.val()) === "undefined" ) {
				frm.append("<input type='hidden' name='ADMIN_DEPT1_CODE' value='0' />"); //오더관리 탭 인덱스 0
			}else {
			    input1.val("0");
			}
			
		}else if(srh_ilbo_type == 'workerList') {
			url = '/admin/workerList';
		}
		
		$("#srh_ilbo_type").val(srh_ilbo_type);
		$("#srh_seq").val(srh_seq);
		$("#menuFrm").attr("action", url).submit();
	});
</script>
</html>