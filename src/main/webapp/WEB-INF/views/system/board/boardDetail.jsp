<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="<c:url value="/resources/web/js/newPaging.js" />" charset="utf-8"></script>
<script type="text/javascript">
	var pass_yn = "${boardGroup.pass_yn}";
	var auth_level = "${sessionScope.ADMIN_SESSION.auth_level }";
	
	$(document).ready(function() {
		
	});
	//수정 삭제 여부 체크
	function chkEditDelete(){
		if( auth_level == "" ){ //비회원 일때
			if( pass_yn == 1 ){
				if( !checkPassword() ){ //패스워드 확인
					return false;
				}				
			}else{
				toastW("변경할 수 없는 게시판입니다.");
				return false;				
			}
		}else if( auth_level > 0 ){ //최고관리자 제외한 나머지 회원일 때
// 			if( !checkAdmin() ){ //작성자 확인
// 				return false;
// 			}
			if( pass_yn == 1 ){
				if( !checkPassword() ){ //패스워드 확인
					return false;
				}
			}
		}
		return true;
	}
	
	// 수정
	function editForm() {
		
		if( !chkEditDelete() ){
			return;
		}
		$("#frm").attr("action", "<c:url value='/system/board/boardEdit'/>").submit();
	}

	// 삭제
	function deleteForm(useYn) {
		if( !chkEditDelete() ){
			return;
		}
		var msg = "삭제";
		if (confirm(msg + " 하시겠습니까?")) {
			var _data = {
				board_seq : $("#board_seq").val(),
				useYn : useYn
			};
			var _url = "<c:url value='/system/board/boardDelProcess' />";

			commonAjax(_url, _data, true, function(data) {
				//successListener
				if (data.code == "0000") {
					toastOk("삭제 되었습니다.");
					listForm();
					timerVal = setTimeout(function() {
					}, 1000);
				} else {
					if (jQuery.type(data.message) != 'undefined') {
						if (data.message != "") {
							toastW(data.message);
						} else {
							toastFail("오류가 발생했습니다.");
						}
					} else {
						toastFail("오류가 발생했습니다..");
					}
				}
			}, function(data) {
				//errorListener
				toastFail("오류가 발생했습니다.");
			}, function() {
				//beforeSendListener
			}, function() {
				//completeListener
			});
		}
	}
	
	function checkAdmin(){
		var result = false;
		
		//ajax로 패스워드 검수 후 수정 페이지로 포워딩
		//값 셋팅
		var _data = {
			board_seq : $("#board_seq").val(),
		};
		
		var _url = "<c:url value='/system/board/boardCheckAdmin' />";

		commonAjax(_url, _data, false, function(data) {
			//successListener
			if (data.code == "0000") {
				result = true;
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						toastW(data.message);
					} else {
						toastFail("오류가 발생했습니다.");
					}
				} else {
					toastFail("오류가 발생했습니다..");
				}
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.");
		}, function() {
			//beforeSendListener
		}, function() {
			
		});
		
		      
		return result;
	}
	
	function checkPassword(){
		var result = false;
		var password = $("#board_pass").val();
		
// 		if(password == ""){
//         	alert("패스워드를 입력하세요.");
//         	$("#board_pass").focus();
//         	return false;
//         }
		            		
		//ajax로 패스워드 검수 후 수정 페이지로 포워딩
		//값 셋팅
		var _data = {
				board_seq : $("#board_seq").val(),
				board_pass : password
		};
		
		var _url = "<c:url value='/system/board/boardCheckPass' />";

		commonAjax(_url, _data, false, function(data) {
			//successListener
			if (data.code == "0000") {
				result = true;
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						toastW(data.message);
					} else {
						toastFail("오류가 발생했습니다.");
					}
				} else {
					toastFail("오류가 발생했습니다..");
				}
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.");
		}, function() {
			//beforeSendListener
		}, function() {
			
		});
		
		      
		return result;
	}

	// 리스트
	function listForm() {
		clearTimeout(timerVal);
		$("#pageNo").val("1");
		$("#frm").attr("action", "<c:url value='/system/board/boardList'/>")
				.submit();
	}
	
	function replyReply(obj){
		var parent_seq = $(obj).attr('parent_seq'); 
		var reply_seq = $(obj).attr('reply_seq');
		var flag = $(obj).attr('flag');
		
		if( flag == '0') {
			var parent = $(obj).parent().parent();
			var text = "";
			text += "<tr class='subReply"+parent_seq+"'>";
			text += "	<td colspan='5'>";
			text += "		이름: 		<input type='text' id='re_reply_writer" + reply_seq + "' name='re_reply_writer' style='width:170px;' maxlength='10' value='${sessionScope.ADMIN_SESSION.admin_id}' readonly/>";
			text += "		패스워드: 	<input type='password' id='re_reply_pass" + reply_seq + "' name='re_reply_pass' style='width:170px;' maxlength='10' placeholder='패스워드'/>";
			text += "		<button type='button' name='re_reply_save' onClick='reReplyRegProcess("+reply_seq+");'>댓글 등록</button>";
			text += "	</td>";
			text += "</tr>";
			text += "<tr class='subReply"+parent_seq+"'>";
			text += "	<td colspan='5'>";
			text += "		<textarea id='re_reply_content" + reply_seq + "' name='reply_content' rows='4' cols='50' placeholder='댓글을 입력하세요.''></textarea>";
			text += "	</td>";
			text += "</tr>";
			
			parent.after(text);
			$(obj).attr('flag', 1);
		}else{
			$(".subReply"+parent_seq).remove();
			$(obj).attr('flag', 0);
		}
		
	}
	// 대댓글
	function reReplyRegProcess(reply_seq){
		if( $("#re_reply_writer"+reply_seq).val() == '' ){
			toastFail("이름을 입력하세요.");
			return;
		}
		if( $("#re_reply_pass"+reply_seq).val() == '' ){
			toastFail("패스워드를 입력하세요.");
			return;
		}
		if( $("#re_reply_content"+reply_seq).val() == '' ){
			toastFail("내용을 입력하세요.");
			return;
		}	
		
		var _data = {
			board_seq : $("#board_seq").val(),
			parent_seq : reply_seq,
			depth : 1,
			reply_writer : $("#re_reply_writer" + reply_seq).val(),
			reply_pass : $("#re_reply_pass" + reply_seq).val(),
			reply_content : $("#re_reply_content" + reply_seq).val(),
			"paging.pageNo" : $("#pageNo").val()
		};
		var _url = "<c:url value='/system/board/replyRegProcess' />";
		commonAjax(_url, _data, true, function(data) {
			var replyList = data.replyList;
			//successListener
			if (data.code == "0000") {
				toastOk("처리 되었습니다.");
				setReplyTable(replyList);
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						
					} else {
						toastFail("오류가 발생했습니다.");
					}
				} else {
					toastFail("오류가 발생했습니다..");
				}
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	}
	
	// 댓글달기
	function replyRegProcess(){
// 		if( $("#reply_writer").val() == '' ){
// 			toastFail("이름을 입력하세요.");
// 			return;
// 		}
		if( $("#reply_pass").val() == '' ){
			toastFail("패스워드를 입력하세요.");
			return;
		}
		if( $("#reply_content").val() == '' ){
			toastFail("내용을 입력하세요.");
			return;
		}			
		
		var _data = {
			board_seq : $("#board_seq").val(),
			parent_seq : 0,
			depth : 0,
			reply_writer : $("#reply_writer").val(),
			reply_pass : $("#reply_pass").val(),
			reply_content : $("#reply_content").val(),
			"paging.pageNo" : $("#pageNo").val()
		};
		var _url = "<c:url value='/system/board/replyRegProcess' />";
		commonAjax(_url, _data, true, function(data) {
			//successListener
			var replyList = data.replyList;
			if (data.code == "0000") {
				toastOk("처리 되었습니다.");
				$("#reply_pass").val('');
				$("#reply_content").val('');
				setReplyTable(replyList);
				$(".paging").empty();
				fn_page_display_ajax_new(data.boardReplyDTO.paging.pageSize, data.totalCnt, '#pageNo', "<c:url value='/system/board/getReplyList' />", '.paging', '#board_seq');
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						
					} else {
						toastFail("오류가 발생했습니다.");
					}
				} else {
					toastFail("오류가 발생했습니다..");
				}
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	}
	
	//댓글 삭제
	function replyDel(obj){
		if(auth_level != 0){
			if( $("#reply_pass_"+$(obj).attr('reply_seq')).val() == '' ){
				toastFail("비밀번호를 입력해주세요.");
				return;
			}
		}
		var _data = {
			board_seq : $("#board_seq").val(),
			reply_seq : $(obj).attr('reply_seq'),
			reply_pass : $("#reply_pass_"+$(obj).attr('reply_seq')).val(),
			"paging.pageNo" : $("#pageNo").val()
		};
		var _url = "<c:url value='/system/board/replyDelProcess' />";
		commonAjax(_url, _data, true, function(data) {
			var replyList = data.replyList;
			//successListener
			if (data.code == "0000") {
				toastOk("처리 되었습니다.");
				$("#pageNo").val( data.boardReplyDTO.paging.pageNo );
				setReplyTable(replyList);
				$(".paging").empty();
				fn_page_display_ajax_new(data.boardReplyDTO.paging.pageSize, data.totalCnt, '#pageNo', "<c:url value='/system/board/getReplyList' />", '.paging', '#board_seq');
			} else {
				toastFail(data.message);
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	}
	//댓글 수정
	function replyModify(obj){
		
		if(auth_level != 0){
			if( $("#reply_pass_"+$(obj).attr('reply_seq')).val() == '' ){
				toastFail("비밀번호를 입력해주세요.");
				return;
			}
		}
		var _data = {
			reply_seq : $(obj).attr('reply_seq'),
			reply_pass : $("#reply_pass_"+$(obj).attr('reply_seq')).val()
		};
		var _url = "<c:url value='/system/board/getReplyInfo' />";
		commonAjax(_url, _data, true, function(data) {
			//successListener
			var flag = $(obj).attr('flag');
			var parent_seq = $(obj).attr('parent_seq');
			if( flag == '0' ){
				var boardReplyDTO = data.boardReplyDTO;
				if (data.code == "0000") {
					var parent = $(obj).parent().parent();
					var text = "";
					text += "<tr class='subReply"+boardReplyDTO.parent_seq+"'>";
					text += "	<td colspan='5'>";
					text += "		<textarea id='reply_content" + boardReplyDTO.reply_seq + "' name='reply_content' rows='4' cols='50'>"+nullCheck(boardReplyDTO.reply_content)+"</textarea>";
					text += "		<button type='button' name='reply_edit' onClick='replyModifyProcess("+boardReplyDTO.reply_seq+");'>댓글 수정</button>";
					text += "	</td>";
					text += "</tr>";
					
					parent.after(text);
					$(obj).attr('flag', 1);
				} else {
					toastFail(data.message);
				}
			}else{
				$(".subReply"+parent_seq).remove();
				$(obj).attr('flag', 0);
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	}
	function replyModifyProcess(reply_seq){
		
		if( $("#reply_content"+reply_seq).val() == '' ){
			toastFail("내용을 입력해 주세요.");
			return;
		}
		var _data = {
			board_seq : $("#board_seq").val(),
			reply_seq : reply_seq,
			reply_content : $("#reply_content"+reply_seq).val(),
			"paging.pageNo" : $("#pageNo").val()
		};
		var _url = "<c:url value='/system/board/replyModifyProcess' />";
		commonAjax(_url, _data, true, function(data) {
			//successListener
			var boardReplyDTO = data.boardReplyDTO;
			var replyList = data.replyList;
			if (data.code == "0000") {
				setReplyTable(replyList);
				toastOk("수정성공");
			} else {
				toastFail(data.message);
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	}
	function nullCheck(str){
		if(str == null){
			return '';
		}
		return str;
	}
	function setReplyTable(list){
		var text = "";
		var r_type = "";
		$("#reply_area tbody").empty();
		for(i=0; i<list.length; i++){
			if(list[i].depth == '0'){
				r_type = "main";
			}else if(list[i].depth == '1'){
				r_type = "sub";
			}
			text += "<tr reply_type=" + r_type + ">";
			text += "	<td width='750px'>"
			if(list[i].depth == '1'){
				text += "→" + list[i].reply_content;
			}else if(list[i].depth == '0'){
				text += list[i].reply_content;
			}
			text += "	</td>";
			text += "	<td width='100px'>";
			text += 	list[i].reply_writer;
			text += "	</td>";
			text += "	<td width='150px'>";
			text += 	list[i].reg_date;
			text += "	</td>";
			text += "	<td width='100px'>";
			if( list[i].reply_writer == "${sessionScope.ADMIN_SESSION.admin_id}" || auth_level == "0" ){
				text += "		<input type='password' id='reply_pass_"+list[i].reply_seq+"' style='width:100px'; maxlength='10' placeholder'패스워드'/>";
			}
			text += "	</td>";
			text += "	<td align='center'>";
			
			if( list[i].depth != "1" ){
				text += "	<button name='reply_reply' type='button' onclick='replyReply(this);' parent_seq='"+list[i].parent_seq+"' reply_seq='"+ list[i].reply_seq+"' flag='0'>댓글</button>";
			}
			if( list[i].reply_writer == "${sessionScope.ADMIN_SESSION.admin_id}" || auth_level == "0" ){
				text += "		<button name='reply_modify' type='button' onclick='replyModify(this)' flag='0' parent_seq='"+list[i].parent_seq+"' r_type='"+r_type+"' reply_seq='"+ list[i].reply_seq +"'>수정</button>";
				text += "		<button name='reply_del' type='button' onclick='replyDel(this)' r_type='"+r_type+"' reply_seq='"+ list[i].reply_seq +"'>삭제</button>";
			}
			text += "	</td>";
			text += "</tr>";
			
		}
		$("#reply_area tbody").append(text);
		
	}
	function fileDown(fileName){
		var _data = {
			fileName : fileName
		};
		var _url = "<c:url value='/system/board/fileDown' />";
		commonAjax(_url, _data, true, function(data) {
			//successListener
			
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	}
	function fn_data_receive(obj, pagingClass){
		setReplyTable(obj.replyList);
		var boardReplyDTO = obj.boardReplyDTO;
		if(boardReplyDTO.paging.pageSize != '0') {
			$(".paging").empty();
        	fn_page_display_ajax_new(boardReplyDTO.paging.pageSize, obj.totalCnt, '#pageNo', "<c:url value='/system/board/getReplyList' />", '.paging', '#board_seq');
		}
	}
	
</script>

<form id="frm" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${boardDTO.paging.pageNo}" />
	<input type="hidden" id="board_group_seq" name="board_group_seq" value="${boardDTO.board_group_seq}" />
	<input type="hidden" id="board_seq" name="board_seq" value="${resultDTO.board_seq}" />

	<div class="content">
		<div class="title">게시판내용</div>
		<table class="board-table" style="width: 100%">
			<colgroup>
				<col width="20%" />
				<col width="*" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="col">제목</th>
					<td>${resultDTO.board_title}</td>
				</tr>
				<tr>
					<th scope="col">작성자</th>
					<td>${resultDTO.board_writer}</td>
				</tr>
				<tr>
					<th scope="col">작성일</th>
					<td>${resultDTO.reg_date}</td>
				</tr>

				<tr>
					<th scope="col">읽음</th>
					<td>${resultDTO.view_cnt}</td>
				</tr>
				<tr>
					<th scope="col">내용</th>
					<td>${resultDTO.board_contents}</td>
				</tr>
				<c:if test="${boardGroup.board_type eq 'F' }">
					<tr>
						<th scope="col">파일첨부</th>
						<td>
							<c:choose>
								<c:when test="${!empty fileList}">
									<c:set var="num" value="0" />
									<c:forEach items="${fileList}" var="list">
										<c:set var="num" value="${num+1}" />
										<div>
											${num } .
											<%-- 											<a href="/admin/imgLoad?path=${list.file_path }&name=${list.file_name }&showImage=O&file_seq=${list.file_seq }" target="_blank">${list.file_org_name }</a> --%>
											<a href="/system/board/fileDown?path=${list.file_path }&name=${list.file_name }&org_name=${list.file_org_name}">${list.file_org_name }</a>
											<%-- 								<img src="/admin/imgLoad?path=${list.file_path }&name=${list.file_name }"> --%>
										</div>
									</c:forEach>
								</c:when>

								<c:otherwise>
									첨부파일 없음
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>




		<div class="mgtL">
			<div class="btn-module ">
				<a class="btn btnStyle04" href="javascript:void(0);" onclick="javascript:listForm();">목록</a>
			</div>
			<c:if test="${sessionScope.ADMIN_SESSION.admin_id == resultDTO.board_writer || sessionScope.ADMIN_SESSION.auth_level == 0}">
				<c:if test="${boardGroup.pass_yn eq '1' }">
					<input type="password" id="board_pass" name="board_pass" maxlength="10" placeholder="패스워드" style="margin-left: 900px;" />
				</c:if>
				<div class="btn-module fR">
					<a class="btn btnStyle04" href="javascript:void(0);" onclick="javascript:deleteForm();">삭제</a>
				</div>
				<div class="btn-module fR">
					<a class="btn btnStyle04" href="javascript:void(0);" onclick="javascript:editForm();">수정</a>
				</div>
			</c:if>
		</div>


		<c:if test="${boardGroup.reply_yn eq '1' }">
			<div class="reply_div mgtL">
				<table width="1200px" id="reply_area">
					<tbody>
						<tr reply_type="all" style="display: none">
							<!-- 뒤에 댓글 붙이기 쉽게 선언 -->
							<td colspan="5"></td>
						</tr>
						<!-- 댓글이 들어갈 공간 -->
						<c:forEach var="replyList" items="${replyList}" varStatus="status">
							<tr reply_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>">
								<!-- 댓글의 depth 표시 -->
								<td width="750px">
									<c:if test="${replyList.depth == '1'}"> → </c:if>${replyList.reply_content}
								</td>
								<td width="100px">${replyList.reply_writer}</td>
								<td width="150px">${replyList.reg_date }</td>
								<td width="100px">
									<c:if test="${sessionScope.ADMIN_SESSION.admin_id == replyList.reply_writer || sessionScope.ADMIN_SESSION.auth_level == 0 }">
										<input type="password" id="reply_pass_${replyList.reply_seq}" style="width: 100px;" maxlength="10" placeholder="패스워드" />
									</c:if>
								</td>
								<td align="center">
									
									<c:if test="${replyList.depth != '1'}">
										<button name="reply_reply" type="button" onclick="replyReply(this);" parent_seq="${replyList.reply_seq}" reply_seq="${replyList.reply_seq}" flag="0">댓글</button>
										<!-- 첫 댓글에만 댓글이 추가 대댓글 불가 -->
									</c:if>
									<c:if test="${sessionScope.ADMIN_SESSION.admin_id == replyList.reply_writer || sessionScope.ADMIN_SESSION.auth_level == 0 }">
										<button name="reply_modify" type="button" onclick="replyModify(this)" flag="0" parent_seq="${replyList.parent_seq}" r_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>" reply_seq="${replyList.reply_seq}">수정</button>
										<button name="reply_del" type="button" onclick="replyDel(this);" r_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>" reply_seq="${replyList.reply_seq}">삭제</button>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${boardDTO.paging.pageSize != '0' }">
					<div class="paging">
						<script type="text/javascript">
							fn_page_display_ajax_new(
									'${replyDTO.paging.pageSize}',
									'${totalCnt}',
									'#pageNo',
									"<c:url value='/system/board/getReplyList' />",
									'.paging', '#board_seq');
						</script>
					</div>
				</c:if>
				<table class="reply-write-div">
					<tr>
						<td>
							이름:
							<input type="text" id="reply_writer" name="reply_writer" maxlength="10" value="${sessionScope.ADMIN_SESSION.admin_id }" readonly />
							패스워드:
							<input type="password" id="reply_pass" name="reply_pass" maxlength="10" placeholder="패스워드" />
						</td>
					</tr>
					<tr>
						<td>
							<textarea id="reply_content" name="reply_content" rows="4" cols="50" placeholder="댓글을 입력하세요."></textarea>
							<button type="button" id="reply_save" name="reply_save" onClick="replyRegProcess();">댓글 등록</button>
						</td>
					</tr>
				</table>
			</div>
		</c:if>
	</div>
</form>