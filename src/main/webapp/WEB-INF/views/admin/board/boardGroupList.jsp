<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="<c:url value="/resources/web/js/newPaging.js" />" charset="utf-8"></script>

<script type="text/javascript">

	$(document).ready(function() {
		datePickerSet('startDate' , 'endDate');
	});


	// 등록
	function regForm() {
		$("#contentFrm").attr("action", "<c:url value='/admin/board/boardGroupReg'/>").submit();
	}


	// 상세보기
	function detailForm(seq) {
		$("#board_group_seq").val(seq);
		$("#contentFrm").attr("action", "<c:url value='/admin/board/boardGroupEdit'/>").submit();
	}
	
	function goBoardList(seq) {
		$("#board_group_seq").val(seq);
		$("#pageNo").val("1");
		$("#contentFrm").attr("action", "<c:url value='/admin/board/boardList'/>").submit();
	}
	
	function fn_data_receive(data){
		if( data.code == "0000" ){
			$("#boardGroupList").empty();
			var boardGroupList = data.boardGroupList;
			var boardDTO = data.boardDTO;
			var cnt = data.totalCnt;
			var text = "";
			if( cnt == 0 ){
				text += "<tr style='text-align: center;'>";
				text += "	<td colspan='11'>";
				text += "		조회된 내용이 없습니다.";
				text += "	</td>";
				text += "</tr>";
			}
			for(i=0; i<boardGroupList.length; i++){
				var num = cnt - ((boardDTO.paging.pageNo - 1) * boardDTO.paging.pageSize + i);
				var boardType = "";
				if( boardGroupList[i].board_type == 'B' ){
					boardType = "일반게시판"
				}else if( boardGroupList[i].board_type == 'F' ){
					boardType = "파일게시판"
				}else if( boardGroupList[i].board_type == 'Q' ){
					boardType = "FAQ게시판"
				}
				
				text += "<tr>";
				text += "	<td>";
				text += 		num;
				text += "	</td>";
				text += "	<td>";
				text += "		<a href='javascript:void(0)'  onclick='javascript:detailForm("+boardGroupList[i].board_group_seq+");'>";
				text += 			boardType;
				text += "		</a>";
				text += "	</td>";
				text += "	<td>";
				text += "		<a href='javascript:void(0);' onclick='javascript:goBoardList("+boardGroupList[i].board_group_seq+");'>";
				text += 			boardGroupList[i].board_name;
				text += "		</a>";
				text += "	</td>";
				text += "	<td>";
				text += 		boardGroupList[i].board_comment;
				text += "	</td>";
				text += "	<td>";
				text += 		( boardGroupList[i].email_yn == '1' ) ? "사용" : "미사용";
				text += "	</td>";
				text += "	<td>";
				text += 		( boardGroupList[i].phone_yn == '1' ) ? "사용" : "미사용";
				text += "	</td>";
				text += "	<td>";
				text += 		( boardGroupList[i].reply_yn == '1' ) ? "사용" : "미사용"; 
				text += "	</td>";
				text += "	<td>";
				text += 		( boardGroupList[i].editor_yn == '1' ) ? "사용" : "미사용";
				text += "	</td>";
				text += "	<td>";
				text += 		( boardGroupList[i].pass_yn == '1' ) ? "사용" : "미사용";
				text += "	</td>";
				text += "	<td>";
				text += 		( boardGroupList[i].use_yn == '1' ) ? "사용" : "미사용";
				text += "	</td>";
				text += "	<td>";
				text +=			boardGroupList[i].reg_date;
				text += "	</td>";
				text += "</tr>";
			}
			$("#boardGroupList").append(text);
			
			$(".paging").empty();
			fn_page_display_ajax_new(boardDTO.paging.pageSize, cnt , '#pageNo', "<c:url value='/admin/board/getBoardGroupList' />", '.paging', '#query');
		}else{
			console.log("error code:" + data.code);
			console.log("error message:" + data.message);
		}
	}

</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${boardDTO.paging.pageNo}" />
	<input type="hidden" id="board_group_seq" name="board_group_seq" value="" />
	

	<div class="content mgtS mgbM">
		<div class="title ">
			<h3>게시판관리</h3>
		</div>

 		<table class="bd-list" summary="게시판관리" >
			<colgroup>
				<col width="8%" />
				<col width="10%" />
				<col width="10%" />
				<col width="*" />
				<col width="3%" />
				<col width="3%" />
				<col width="3%" />
				<col width="3%" />
				<col width="3%" />
				<col width="3%" />
				<col width="10%" />
			</colgroup>
			<thead>
				<tr>
					<th>No.</th>
					<th>게시판종류</th>
					<th>게시판이름</th>
					<th>설명</th>
					<th>이메일</th>
					<th>휴대폰번호</th>
					<th>댓글</th>
					<th>에디터</th>
					<th>패스워드</th>
					<th>게시판사용</th>
					<th class="last">등록일</th>
				</tr>
			</thead>
			<tbody id="boardGroupList">
				<c:choose>
					<c:when test="${!empty boardGroupList}">
						<c:set var="num" value="${boardDTO.paging.rowCount - (boardDTO.paging.pageNo - 1) * boardDTO.paging.pageSize}" />

						<c:forEach items="${boardGroupList}" var="list">
							<tr>
								<td>${num}</td>
								<td>
									<a href="javascript:void(0)"  onclick="javascript:detailForm('${list.board_group_seq}');">
									<c:choose>
									    <c:when test="${list.board_type eq 'B'}">
									        일반게시판
									    </c:when>
									    <c:when test="${list.board_type eq 'F'}">
									        파일게시판
									    </c:when>
									    <c:otherwise>
									        FAQ게시판
									    </c:otherwise>
									</c:choose>
									</a>
								</td>
								<td><a href="javascript:void(0);" onclick="javascript:goBoardList('${list.board_group_seq}');"> ${list.board_name}</a></td>
								<td>${list.board_comment}</td>
								<td>${list.email_yn eq '1' ? '사용' : '미사용' }</td>
								<td>${list.phone_yn eq '1' ? '사용' : '미사용'}</td>
								<td>${list.reply_yn eq '1' ? '사용' : '미사용'}</td>
								<td>${list.editor_yn eq '1' ? '사용' : '미사용'}</td>
								<td>${list.pass_yn eq '1' ? '사용' : '미사용'}</td>
								<td>${list.use_yn eq '1' ? '사용' : '미사용'}</td>
								<td>
									${list.reg_date }
								</td>
							</tr>
							<c:set var="num" value="${num-1}" />
						</c:forEach>
					</c:when>

					<c:otherwise>
						<tr style="text-align: center;">
							<td colspan="11">
								조회된 내용이 없습니다.
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>

		

 		<c:if test="${boardDTO.paging.pageSize != '0' }">
			<div class="paging">
				<script type="text/javascript">
					fn_page_display_ajax_new('${boardDTO.paging.pageSize}', '${totalCnt }', '#pageNo', "<c:url value='/admin/board/getBoardGroupList' />", '.paging', '#query')
				</script>
			</div>
		</c:if>
		
		<div class="btn-module mgtL">
			<a  href="javascript:void(0);" class="btnStyle04" onclick="javascript:regForm();">등록</a>
		</div>
	</div>

</form>
