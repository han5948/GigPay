<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="<c:url value="/resources/web/js/newPaging.js" />" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/common/css/city.css' />" media="all" charset="utf-8"></link>
<script type="text/javascript">
	window.onload = function () {
		$(".datepicker").val('');
		
		$("#btnSrh").click(function(){
			$("#pageNo").val("1");
			
			commonAjax(_url, _data, true, function(data) {
				$("#listBody").empty();
				
				var text = "";
				var cnt = data.totalCnt;
				var issueList = data.issueList;
				var issueDTO = data.issueDTO;
				
				if(cnt <= 0) {
					text += "<tr style='text-align: center;'>";
					text += "	<td colspan='6'>";
					text += "		조회된 내용이 없습니다.";
					text += "	</td>";
					text += "</tr>";
				}else{
					for(i = 0; i < issueList.length; i++) {
						var num = cnt - ((issueDTO.paging.pageNo - 1) * issueDTO.paging.pageSize + i);
						
						text += "<tr>";
						text += "	<td>" + num + "</td>";
						text += "	<td>" + issueList[i].company_seq + "</td>";
						text += "	<td>" + issueList[i].issue_date + "</td>";
						text += "	<td>" + issueList[i].issue_text + "</td>";
						text += "	<td>" + issueList[i].reg_admin + "</td>";
						text += "	<td>" + issueList[i].reg_date + "</td>";
						text += "</tr>";
					}
				}
				
				$("#listBody").append(text);
				
				$(".paging").empty();
				
				fn_page_display_ajax_new(issueDTO.paging.pageSize, cnt, '#pageNo', "<c:url value='/admin/issue/getAdList' />", '.paging', '#query');
	    		
	    	}, function(data) {
	    		//errorListener
	    		toastFail("오류가 발생했습니다.3");
	    	}, function() {
	    		//beforeSendListener
	    	}, function() {
	    		//completeListener
	    	});
		});
	}
	
	function fn_data_receive(data) {
		$("#listBody").empty();
		
		var text = "";
		var cnt = data.totalCnt;
		var issueList = data.issueList;
		var issueDTO = data.issueDTO;
		
		if(cnt <= 0) {
			text += "<tr style='text-align: center;'>";
			text += "	<td colspan='6'>";
			text += "		조회된 내용이 없습니다.";
			text += "	</td>";
			text += "</tr>";
		}else {
			for(i = 0; i < issueList.length; i++) {
				var num = cnt - ((issueDTO.paging.pageNo - 1) * issueDTO.paging.pageSize + i);
				
				text += "<tr>";
				text += "	<td>" + num + "</td>";
				text += "	<td>" + issueList[i].company_seq + "</td>";
				text += "	<td>" + issueList[i].issue_date + "</td>";
				text += "	<td><a href='javascript:void(0);' onclick='fn_edit(" + issueList[i].issue_seq + ")'>" + issueList[i].issue_text + "</a></td>";
				text += "	<td>" + issueList[i].reg_admin + "</td>";
				text += "	<td>" + issueList[i].reg_date + "</td>";
				text += "</tr>";
			}
		}
		
		$("#listBody").append(text);
		
		$(".paging").empty();
		fn_page_display_ajax_new(issueDTO.paging.pageSize, cnt , '#pageNo', "<c:url value='/admin/issue/getIssueList' />", '.paging', '#query');
	};
	
	function fn_edit(issue_seq) {
		$("#defaultFrm").append("<input type='hidden' name='issue_seq' value='" + issue_seq+ "'>");
		
		$("#defaultFrm").attr("action", "<c:url value='/admin/issue/selectIssueInfo' />").submit();		
	}
</script>
	<article>
		<div class="content mgtS">
			<div class="title">
				<h3>이슈 현황</h3>
			</div>
		</div>
<!-- 		<div class="inputUI_simple"> -->
<%--       	<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다."> --%>
<%--         <caption>등록일시, 상태, 직접검색 영역</caption> --%>
<%--         <tr> --%>
<%--           	<th scope="row" class="pdlM" style="width: 7%;">유형</th> --%>
<%--           	<td scope="row" class="linelY pdlM" style="width: 20%;"> --%>
<%--             	<input type="radio" id="srh_use_yn_1" name="srh_networkType" class="inputJo" value=""  <c:if test="${adDTO.srh_networkType eq null or adDTO.srh_networkType eq '' }">checked</c:if> /><label for="srh_use_yn_1" class="label-radio">전체</label> --%>
<%-- 				<input type="radio" id="srh_use_yn_2" name="srh_networkType" class="inputJo" value="NAVER" <c:if test="${adDTO.srh_networkType eq 'NAVER' }">checked</c:if> /><label for="srh_use_yn_2" class="label-radio">NAVER</label> --%>
<%-- 				<input type="radio" id="srh_use_yn_3" name="srh_networkType" class="inputJo" value="EXTERNAL" <c:if test="${adDTO.srh_networkType eq 'EXTERNAL' }">checked</c:if> /><label for="srh_use_yn_3" class="label-radio">EXTERNAL</label> --%>
<%-- 			</td> --%>
<%-- 			<th scope="row" class="linelY pdlM" style="width: 7%;">사용 장치</th> --%>
<%-- 			<td scope="row" class="linelY pdlM" style="width: 17%;"> --%>
<%--             	<input type="radio" id="srh_use_yn_4" name="srh_device" class="inputJo" value=""  <c:if test="${adDTO.srh_device eq null or adDTO.srh_device eq '' }">checked</c:if> /><label for="srh_use_yn_4" class="label-radio">전체</label> --%>
<%-- 				<input type="radio" id="srh_use_yn_5" name="srh_device" class="inputJo" value="PC" <c:if test="${adDTO.srh_device eq 'PC' }">checked</c:if> /><label for="srh_use_yn_5" class="label-radio">PC</label> --%>
<%-- 				<input type="radio" id="srh_use_yn_6" name="srh_device" class="inputJo" value="MOBILE" <c:if test="${adDTO.srh_device eq 'MOBILE' }">checked</c:if> /><label for="srh_use_yn_6" class="label-radio">MOBILE</label> --%>
<%-- 			</td> --%>
<%-- 			<th scope="row" class="linelY pdlM" style="width: 7%;">지출 날짜</th> --%>
<%-- 			<td scope="row" class="linelY pdlM"> --%>
<!-- 				<p class="floatL"> -->
<!-- 		    		<input type="text" id="ad_date" name="ad_date" readonly class="inp-field wid100 datepicker" /> -->
<!-- 		    	</p> -->
<!--             	<div class="btn-module floatL mglS"> -->
<!--               		<a href="#none" id="btnSrh" class="search">검색</a> -->
<!--               		<a href="#none" id="btnReset" class="btnStyle05">날짜 초기화</a> -->
<!--             	</div> -->
<%-- 			</td> --%>
<%-- 		</tr> --%>
<%--       	</table> --%>
<!--       	</div> -->
		<table class="bd-list" summary="이슈 관리 리스트">
		    <colgroup>
			    <col width="20px" />
			    <col width="50px" />
			    <col width="50px" />
			    <col width="300px" />
			    <col width="70px" />
			    <col width="50px" />
		    </colgroup>
	    	<thead>
				<tr>
					<th>번호</th>
					<th>지점명</th>
					<th>이슈 날짜</th>
					<th>이슈 내용</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<c:choose>
					<c:when test="${!empty issueList }">
						<c:forEach var="item" items="${issueList }" varStatus="status">
							<tr>
								<td>${totalCnt - ((issueDTO.paging.pageNo - 1) * issueDTO.paging.pageSize + status.index) }</td>
								<td>${item.company_name }</td>
								<td>${item.issue_date }</td>
								<td>
									<a href="javascript:void(0);" onclick="fn_edit('${item.issue_seq}')">
										${item.issue_text }
									</a>
								</td>
								<td>${item.reg_admin }</td>
								<td>${item.reg_date }</td>
							</tr>
			    		</c:forEach>	
					</c:when>
	       			<c:otherwise>
	       				<tr style="text-align: center;">
	       					<td colspan="6">
	       						조회된 내용이 없습니다.
	       					</td>
	       				</tr>
	       			</c:otherwise>
	       		</c:choose>
	    	</tbody>
		</table>
		
		<input type="hidden" id="pageNo" name="paging.pageNo" value="${issueDTO.paging.pageNo }" />
		
		<c:if test="${issueDTO.paging.pageSize != '0' }">
			<div class="paging">
				<script type="text/javascript">
					fn_page_display_ajax_new('${issueDTO.paging.pageSize}', '${totalCnt }', '#pageNo', "<c:url value='/admin/issue/getIssueList' />", '.paging', '#query')
				</script>
			</div>
		</c:if>
	</article>