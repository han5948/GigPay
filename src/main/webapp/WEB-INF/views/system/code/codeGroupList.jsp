<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

	$(document).ready(function() {
		datePickerSet('startDate' , 'endDate');
	});


	// 등록
	function regForm() {
		$("#frm").attr("action", "/system/code/codeGroupReg").submit();
	}


	// 상세보기
	function detailForm(seq) {
		$("#groupCode").val(seq);
		$("#frm").attr("action", "<c:url value='/system/code/codeGroupEdit'/>").submit();
	}
	
	function goCodeList(seq) {
		$("#groupCode").val(seq);
		$("#pageNo").val("1");
		$("#frm").attr("action", "<c:url value='/system/code/codeList'/>").submit();
	}

</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="groupCode" name="groupCode" value="" />

	<div class="content">
		<div >
			<h>코드그룹관리</h2>
		</div>

 		<table border="1" style="width:80%;margin-top:10px" >
			<colgroup>
				<col width="8%" />
				<col width="10%" />
				<col width="*" />
				<col width="10%" />
				<col width="10%" />
				<col width="10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">No.</th>
					<th scope="col">코드그룹</th>
					<th scope="col">그룹이름</th>
					<th scope="col">사용유무</th>
					<th scope="col">삭제유무</th>
					<th scope="col" class="last">등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty codeGroupList}">
						<c:set var="num" value="${fn:length(codeGroupList)}" />
						<c:forEach items="${codeGroupList}" var="list">
							<tr>
								<td>${num}</td>
								<td><a href="javascript:void(0);" onclick="javascript:detailForm('${list.groupCode}');">${list.groupCode}</a></td>
								<td><a href="javascript:void(0);" onclick="javascript:goCodeList('${list.groupCode}');"> ${list.groupName}</a></td>
								<td>${list.useYn eq 'Y' ? '사용' : '미사용'}</td>
								<td>${list.delYn eq 'N' ? '사용' : '미사용'}</td>
								<td>
									${list.regDate }
								</td>
							</tr>
							<c:set var="num" value="${num-1}" />
						</c:forEach>
					</c:when>

					<c:otherwise>
						<tr style="text-align: center;">
							<td colspan="8">
								조회된 내용이 없습니다.
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>

		<a  href="javascript:void(0);" onclick="javascript:regForm();">등록</a>

 		<c:if test="${codeDTO.paging.pageSize != '0' }">
			<div class="paging">
				<script type="text/javascript">
					fn_page_display('${codeDTO.paging.pageSize}', '${codeDTO.paging.rowCount}', 'frm', '#pageNo');
				</script>
			</div>
		</c:if>
	</div>

</form>
