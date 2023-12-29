<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="<c:url value="/resources/web/js/newPaging.js" />" charset="utf-8"></script>

<script>
	//등록
	function regForm() {
		$("#contentFrm").attr("action", "<c:url value='/admin/categoryReg'/>").submit();
	}
	
	// 상세보기
	function detailForm(seq) {
		$("#category_seq").val(seq);
		$("#contentFrm").attr("action", "<c:url value='/admin/categoryEdit'/>").submit();
	}

	function fn_data_receive(data) {
		if(data.code == "0000") {
			$("#categoryList").empty();
			
			var categoryList = data.categoryList;
			var categoryDTO = data.categoryDTO;
			var cnt = data.totalCnt;
			var text = "";
			
			if(cnt == 0) {
				text += "<tr style='text-align: center;'>";
				text += "	<td colspan='4'>";
				text += "		조회된 내용이 없습니다.";
				text += "	</td>";
				text += "</tr>";
			}
			
			for(i = 0; i < categoryList.length; i++) {
				var num = cnt - ((categoryDTO.paging.pageNo - 1) * categoryDTO.paging.pageSize + i);
				
				text += "<tr>";
				text += "	<td>";
				text += 		num;
				text += "	</td>";
				text += "	<td>";
				text += "		<a href='javascript:void(0)'  onclick='javascript:detailForm("+categoryList[i].category_seq +");'>";
				text += 			categoryList[i].category_name;
				text += "		</a>";
				text += "	</td>";
				text += "	<td>";
				text += 		( categoryList[i].use_yn == '1' ) ? "사용" : "미사용";
				text += "	</td>";
				text += "	<td>";
				text +=			categoryList[i].reg_date;
				text += "	</td>";
				text += "	<td>";
				text +=			categoryList[i].reg_admin;
				text += "	</td>";
				text += "	<td>";
				text +=	'		<div class="btn-module">';
				text += '			<a href="javascript:void(0);" style="text-decoration: none; width: 120%;" onclick="fn_contractList(\'' + categoryList[i].category_seq + '\')" class="btnStyle04">계약서 양식 다운로드</a>';
				text += '		</div>';
				text += "	</td>";
				text += "</tr>";
			}
			
			$("#categoryList").append(text);
			
			$(".paging").empty();
			fn_page_display_ajax_new(categoryDTO.paging.pageSize, cnt , '#pageNo', "<c:url value='/admin/getCategoryList' />", '.paging', '#query');
		}else{
			console.log("error code:" + data.code);
			console.log("error message:" + data.message);
		}
	}
	
	function fn_contractList(seq) {
		$("#category_seq").val(seq);
		$("#contentFrm").attr("action", "<c:url value='/admin/contractDownload' />").submit();
	}
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${categoryDTO.paging.pageNo}" />
	<input type="hidden" id="category_seq" name="category_seq" value="" />

	<div class="content mgtS mgbM">
		<div class="title ">
			<h3>계약서 카테고리 관리</h3>
		</div>

 		<table class="bd-list" summary="계약서 카테고리 관리" >
			<colgroup>
				<col width="8%" />
				<col width="15%" />
				<col width="10%" />
				<col width="10%" />
				<col width="10%" />
				<col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th>No.</th>
					<th>카테고리 명</th>
					<th>사용유무</th>
					<th>작성일</th>
					<th>작성자</th>
					<th class="last"></th>
				</tr>
			</thead>
			<tbody id="categoryList">
				<c:choose>
					<c:when test="${!empty categoryList }">
						<c:set var="num" value="${categoryDTO.paging.rowCount - (categoryDTO.paging.pageNo - 1) * categoryDTO.paging.pageSize}" />

						<c:forEach items="${categoryList}" var="list">
							<tr>
								<td>${num}</td>
								<td>
									<a href="javascript:void(0)" onclick="javascript:detailForm('${list.category_seq}');">${list.category_name }</a>
								</td>
								<td>
									${list.use_yn eq '1' ? '사용' : '미사용'}
								</td>
								<td>
									${list.reg_date }
								</td>
								<td>
									${list.reg_admin }
								</td>
								<td>
									<div class="btn-module">
										<a href="javascript:void(0);" style="text-decoration: none; width: 120%;" onclick="fn_contractList('${list.category_seq }')" class="btnStyle04">계약서 양식 다운로드</a>
									</div>
								</td>
							</tr>
							<c:set var="num" value="${num - 1 }" />
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr style="text-align: center;">
							<td colspan="4">
								조회된 내용이 없습니다.
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>

 		<c:if test="${categoryDTO.paging.pageSize != '0' }">
			<div class="paging">
				<script type="text/javascript">
					fn_page_display_ajax_new('${categoryDTO.paging.pageSize}', '${totalCnt }', '#pageNo', "<c:url value='/admin/getCategoryList' />", '.paging', '#query')
				</script>
			</div>
		</c:if>
		
		<div class="btn-module mgtL">
			<a  href="javascript:void(0);" class="btnStyle04" onclick="javascript:regForm();">등록</a>
		</div>
	</div>
</form>