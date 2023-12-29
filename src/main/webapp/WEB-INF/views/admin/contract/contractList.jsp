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
		$("#contentFrm").attr("action", "<c:url value='/admin/contractReg'/>").submit();
	}
	
	// 상세보기
	function fn_detail(seq) {
		$("#contract_seq").val(seq);
		
		$("#contentFrm").attr("action", "<c:url value='/admin/contractEdit'/>").submit();
	}

	function fn_data_receive(data) {
		if(data.code == "0000") {
			$("#contractList").empty();
			
			var contractList = data.contractList;
			var contractDTO = data.contractDTO;
			var cnt = data.totalCnt;
			var text = "";
			
			if(cnt == 0) {
				text += "<tr style='text-align: center;'>";
				text += "	<td colspan='7'>";
				text += "		조회된 내용이 없습니다.";
				text += "	</td>";
				text += "</tr>";
			}
			
			for(i = 0; i < contractList.length; i++) {
				var num = cnt - ((contractDTO.paging.pageNo - 1) * contractDTO.paging.pageSize + i);
				
				text += "<tr>";
				text += "	<td>";
				text += 		num;
				text += "	</td>";
				text += "	<td>";
				text += "		<a href='javascript:void(0)' onclick='javascript:fn_detail(" + contractList[i].contract_seq + ");'>";
				
				if(contractList[i].contract_name != null) {
					text += 			contractList[i].contract_name;
				}else {
					text += '';
				}
				
				text += "		</a>";
				text += "	</td>";
				text += "	<td>";
				
				if(contractList[i].orgFileName) {
					text += 		contractList[i].orgFileName;
				}else {
					text += '';
				}
				
				text += "	</td>";
				text += "	<td>";

				if(contractList[i].contract_default_use_yn == '1') {
					text += '사용';
				}else {
					text += '미사용';
				}
				
				text += "	</td>";
				text += "	<td>";
				
				if(contractList[i].use_yn == '1') {
					text += "사용";
				}else {
					text += "미사용";
				}
				
				text += "	</td>";
				text += "	<td>";
				text +=			contractList[i].reg_date;
				text += "	</td>";
				text += "	<td>";
				text +=			contractList[i].reg_admin;
				text += "	</td>";
				text += "</tr>";
			}
			
			$("#contractList").append(text);
			
			$(".paging").empty();
			
			fn_page_display_ajax_new(contractDTO.paging.pageSize, cnt , '#pageNo', "<c:url value='/admin/getContractList' />", '.paging', '#query');
		}else{
			console.log("error code:" + data.code);
			console.log("error message:" + data.message);
		}
	}
	
	function fn_contractList(seq) {
		$("#category_seq").val(seq);
		$("#pageNo").val("1");

		$("#frm").attr("action", "<c:url value='/admin/contractList' />").submit();
	}
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${contractDTO.paging.pageNo}" />
	<input type="hidden" id="contract_seq" name="contract_seq" value="" />
	<input type="hidden" id="category_seq" name="category_seq" value="${contractDTO.category_seq }" />
	<input type="hidden" id="query" name="query" value="${contractDTO.category_seq }" />
	<div class="content mgtS mgbM">
		<div class="title ">
			<h3>계약서 양식 관리</h3>
		</div>

 		<table class="bd-list" summary="계약서 양식 관리" >
			<colgroup>
				<col width="8%" />
				<col width="10%" />
				<col width="10%" />
				<col width="10%" />
				<col width="10%" />
				<col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th>No.</th>
					<th>양식명</th>
					<th>파일명</th>
					<th>기본값</th>
					<th>사용유무</th>
					<th>작성일</th>
					<th class="last">작성자</th>
				</tr>
			</thead>
			<tbody id="contractList">
				<c:choose>
					<c:when test="${!empty contractList }">
						<c:set var="num" value="${contractDTO.paging.rowCount - (contractDTO.paging.pageNo - 1) * contractDTO.paging.pageSize}" />

						<c:forEach items="${contractList}" var="list">
							<tr>
								<td>${num}</td>
								<td>
									<a href="javascript:void(0)" onclick="fn_detail(${list.contract_seq})">
										${list.contract_name }
									</a>
								</td>
								<td>
									${list.orgFileName }
								</td>
								<td>
									${list.contract_default_use_yn eq '1' ? '사용' : '미사용' }
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
							</tr>
							<c:set var="num" value="${num - 1 }" />
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr style="text-align: center;">
							<td colspan="7">
								조회된 내용이 없습니다.
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>

 		<c:if test="${contractDTO.paging.pageSize != '0' }">
			<div class="paging">
				<script type="text/javascript">
					fn_page_display_ajax_new('${contractDTO.paging.pageSize}', '${totalCnt }', '#pageNo', "<c:url value='/admin/getContractList' />", '.paging', '#query')
				</script>
			</div>
		</c:if>
		
		<div class="btn-module mgtL">
			<a  href="javascript:void(0);" class="btnStyle04" onclick="javascript:regForm();">등록</a>
		</div>
	</div>
</form>