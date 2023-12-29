<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  	<div id="" class="mgtL textC">
		<c:choose>
			<c:when test="${isFile == 'Y' }">
				<img src='/admin/decImgLoad?path=${result.file_path}&name=${result.file_name}'/>
			</c:when>
	   		<c:otherwise>
				삭제된 이미지 입니다.
			</c:otherwise>
		</c:choose>
    </div>  
    
    <div class="btn-module mgtL textC">
      	<!-- <div class="rightGroup"> -->
		<a class="btnStyle05" href="javascript:self.close();">닫기</a>
      	<c:if test="${vFlag == 'worker' && isFile == 'Y' }">
      		<a class="btnStyle05 mglL" href="javascript:void(0)" onclick="deleteFile('${result.service_type}', '${result.service_code}','${result.service_seq}')">삭제</a>
		</c:if>
    </div>
    
<script type="text/javascript">
	function deleteFile(service_type,service_code,service_seq) {
		var _data = {
			service_type: service_type,
			service_code: service_code,
			service_seq: service_seq
		};
		var _url = "<c:url value='/admin/deleteFile' />";

		commonAjax(_url, _data, true, function(data) {
			if (data.code == "0000") {
				window.opener.fnReloadGrid();
				alert("삭제 하였습니다.");
				self.close();
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						alert(data.message);
					} else {
						toastFail("오류가 발생했습니다.1");
					}
				} else {
					toastFail("오류가 발생했습니다.2");
				}
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.3");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	}
</script>   