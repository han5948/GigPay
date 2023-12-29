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
		$("#frm").attr("action", "<c:url value='/system/board/boardGroupReg'/>").submit();
	}


	// 상세보기
	function detailForm(seq) {
		$("#board_group_seq").val(seq);
		$("#frm").attr("action", "<c:url value='/system/board/boardGroupEdit'/>").submit();
	}
	
	function goBoardList(seq) {
		$("#board_group_seq").val(seq);
		$("#pageNo").val("1");
		$("#frm").attr("action", "<c:url value='/system/board/boardList'/>").submit();
	}

</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${boardDTO.paging.pageNo}" />
	<input type="hidden" id="board_group_seq" name="board_group_seq" value="" />

	<div class="content">
		<div class="title ">
			<h3>게시판관리</h3>
		</div>

 		<table class="bd-list" summary="게시판관리" >
			<colgroup>
				<col width="8%" />
				<col width="10%" />
				<col width="10%" />
				<col width="*" />
				<col width="5%" />
				<col width="5%" />
				<col width="5%" />
				<col width="5%" />
				<col width="10%" />
			</colgroup>
			<thead>
				<tr>
					<th>No.</th>
					<th>게시판종류</th>
					<th>게시판이름</th>
					<th>설명</th>
					<th>댓글</th>
					<th>에디터</th>
					<th>패스워드</th>
					<th>게시판사용</th>
					<th class="last">등록일</th>
				</tr>
			</thead>
			<tbody>
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
									        QnA게시판
									    </c:otherwise>
									</c:choose>
									</a>
								</td>
								<td><a href="javascript:void(0);" onclick="javascript:goBoardList('${list.board_group_seq}');"> ${list.board_name}</a></td>
								<td>${list.board_comment}</td>
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
							<td colspan="7">
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
					fn_page_display('${boardDTO.paging.pageSize}', '${boardDTO.paging.rowCount}', 'frm', '#pageNo');
				</script>
			</div>
		</c:if>
		
		<div class="btn-module mgtL">
			<a  href="javascript:void(0);" class="btnStyle04" onclick="javascript:regForm();">등록</a>
			
		</div>
	</div>

</form>
