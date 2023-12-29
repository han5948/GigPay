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
		$("#contentFrm").attr("action", "<c:url value='/admin/board/boardEdit'/>").submit();
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
			var _url = "<c:url value='/admin/board/boardDelProcess' />";

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
		
		var _url = "<c:url value='/admin/board/boardCheckAdmin' />";

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
		
		var _url = "<c:url value='/admin/board/boardCheckPass' />";

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
// 		clearTimeout(timerVal);
		$("#pageNo").val("1");
		$("#contentFrm").attr("action", "<c:url value='/admin/board/boardList'/>").submit();
	}
	
	function replyReply(obj){
		var reply_seq = $(obj).attr('reply_seq');
		var flag = $(obj).attr('flag');
		var data = {
			parent_seq: reply_seq,
			board_seq: $("#board_seq").val()
		};
		var url = "<c:url value='/admin/board/getReplyList' />";
		commonAjax(url, data, false, function(data) {
			//successListener
			if (data.code == "0000") {
				if( flag == '0') {
					var replyList = data.replyList;
					var tr = $(obj).parent().parent().parent();
					var text = "";
					for(i=0; i<replyList.length; i++){
						text = "<tr class='subReply"+reply_seq+" subReply'>";
						text += "	<td>";
						text += "		<div class='reply-writer'>";
						text += 			replyList[i].reply_writer;
						text += "		</div>";
						text += "		<div>";
						text += 			replyList[i].reg_date;
						text += "		</div>";
						text += "		<div class='reply-content'>";
						text += 			replyList[i].reply_content;
						text += "		</div>";
						if( "${sessionScope.ADMIN_SESSION.admin_id }" == replyList[i].reply_writer || auth_level == 0 ){
							text += "		<div>";
							text += "			<input type='password' class='reply_pass' placeholder='패스워드'>";
							text += "			<a name='reply_modify' href='javascript:void(0);' onclick='replyModify(this)' reply_seq='"+replyList[i].reply_seq+"' flag='0'>수정</a>";
							text += "			<a href='javascript:void(0);' onclick='replyDel(this)' reply_seq='"+replyList[i].reply_seq+"' depth='1'>삭제</a>";
							text += "		</div>";
						}
						text += "	</td>";
						text += "</tr>";
						tr.after(text);
					}
					text = "<tr class='subReplyReg"+reply_seq+" subReply'>";
					text += "	<td>";
					text += "		<div class='fR'>";
					text += "			이름:<input type='text' id='re_reply_writer"+reply_seq+"' class='hight25' value='${sessionScope.ADMIN_SESSION.admin_id}' readonly>";
					text += "			패스워드:<input type='password' id='re_reply_pass"+reply_seq+"' class='hight25' placeholder='비밀번호'>";
					text += "			<div class='btn-module'>";
					text += "				<a class='replyRegBtn' href='javascript:void(0);' onclick='reReplyRegProcess("+reply_seq+")'>등록</a>";
					text += "			</div>";
					text += "		</div>";
					text += "		<div>";
					text += "			<textarea id='re_reply_content"+reply_seq+"' rows='5' placeholder='댓글을 입력하세요.'></textarea>";
					text += "		</div>";
					text += "	</td>";
					text += "</tr>";
					if( replyList.length == 0 ){
						tr.after(text);
					}else{
						$(".subReply"+reply_seq + ":last").after(text);						
					}
					$(obj).attr('flag', 1);
				}else{
					$(".subReplyReg"+reply_seq).remove();
					$(".subReply"+reply_seq).remove();
					$(obj).attr('flag', 0);
				}
			} else {
				
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.");
		}, function() {
			//beforeSendListener
		}, function() {
			
		});
	}
	// 대댓글
	function reReplyRegProcess(reply_seq){
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
		};
		var _url = "<c:url value='/admin/board/replyRegProcess' />";
		commonAjax(_url, _data, true, function(data) {
			var replyList = data.replyList;
			//successListener
			if (data.code == "0000") {
// 				toastOk("처리 되었습니다.");
				var text = "<tr class='subReply"+reply_seq+" subReply'>";
				text += "	<td>";
				text += "		<div class='reply-writer'>";
				text += 			replyList[0].reply_writer;
				text += "		</div>";
				text += "		<div>";
				text += 			replyList[0].reg_date;
				text += "		</div>";
				text += "		<div class='reply-content'>";
				text += 			replyList[0].reply_content;
				text += "		</div>";
				text += "		<div>";
				text += "			<input type='password' class='reply_pass' placeholder='패스워드'>";
				text += "			<a name='reply_modify' href='javascript:void(0);' onclick='replyModify(this)' reply_seq='"+replyList[0].reply_seq+"' flag='0'>수정</a>";
				text += "			<a name='reply_del' href='javascript:void(0);' onclick='replyDel(this)' reply_seq='"+replyList[0].reply_seq+"' depth='1'>삭제</a>";
				text += "		</div>";
				text += "	</td>";
				text += "</tr>";
				$(".subReplyReg"+reply_seq).before(text);
				$("#re_reply_pass"+reply_seq).val('');
				$("#re_reply_content"+reply_seq).val('');
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
		var _url = "<c:url value='/admin/board/replyRegProcess' />";
		commonAjax(_url, _data, true, function(data) {
			//successListener
			var replyList = data.replyList;
			if (data.code == "0000") {
				toastOk("처리 되었습니다.");
				$("#reply_pass").val('');
				$("#reply_content").val('');
				setReplyTable(replyList);
				$("#reply_count").text( Number($("#reply_count").text()) + 1 );
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
		var reply_pass = $(obj).parent().children(".reply_pass");
		if(auth_level != 0){
			if( reply_pass.val() == '' ){
				toastFail("비밀번호를 입력해주세요.");
				return;
			}
		}
		
		var _data = {
			board_seq : $("#board_seq").val(),
			reply_seq : $(obj).attr('reply_seq'),
			reply_pass : reply_pass.val(),
			"paging.pageNo" : $("#pageNo").val()
		};
		var _url = "<c:url value='/admin/board/replyDelProcess' />";
		commonAjax(_url, _data, true, function(data) {
			var replyList = data.replyList;
			//successListener
			if (data.code == "0000") {
				toastOk("처리 되었습니다.");
				var depth = $(obj).attr('depth');
				$("#pageNo").val( data.boardReplyDTO.paging.pageNo );
				$(obj).parent().parent().parent().remove(); //해당 tr 지우기
				if( depth == '0' ){
					$("#reply_count").text( Number($("#reply_count").text()) - 1 );
					$(".paging").empty();
					fn_page_display_ajax_new(data.boardReplyDTO.paging.pageSize, data.totalCnt, '#pageNo', "<c:url value='/system/board/getReplyList' />", '.paging', '#board_seq');
				}
				setReplyTable(replyList);
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
		var reply_seq = $(obj).attr('reply_seq');
		var flag = $(obj).attr('flag');
		var reply_pass = $(obj).parent().children(".reply_pass");
		if(auth_level != 0){
			if( reply_pass.val() == '' ){
				toastFail("비밀번호를 입력해주세요.");
				return;
			}
		}
		var _data = {
			reply_seq : $(obj).attr('reply_seq'),
			reply_pass : reply_pass.val()
		};
		var _url = "<c:url value='/admin/board/getReplyInfo' />";
		commonAjax(_url, _data, true, function(data) {
			//successListener
			if (data.code == "0000") {
				var boardReplyDTO = data.boardReplyDTO; //reply_content
				var reply_content = $(obj).parent().parent().children(".reply-content");
		 		if( flag == '0' ){
		 			var reply_content_text = boardReplyDTO.reply_content;
		 			reply_content.empty();
		 			var text = "<textarea id='replyEditArea"+reply_seq+"' rows='5' cols='150' >"+reply_content_text+"</textarea>";
		 			text += "<div class='btn-module'>";
		 			text += "	<a class='btnStyle01' href='javascript:void(0);' onclick='replyModifyProcess("+reply_seq+")'>저장</a>";
		 			text += "</div>";
		 			reply_content.append(text);
		 			$(obj).attr('flag', 1);
		 		}else{
					reply_content.empty();
		 			reply_content.append(boardReplyDTO.reply_content);
		 			$(obj).attr('flag', 0);
		 		}
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
	function replyModifyProcess(reply_seq){
		
		if( $("#reply_content"+reply_seq).val() == '' ){
			toastFail("내용을 입력해 주세요.");
			return;
		}
		var _data = {
			board_seq : $("#board_seq").val(),
			reply_seq : reply_seq,
			reply_content : $("#replyEditArea"+reply_seq).val(),
			"paging.pageNo" : $("#pageNo").val()
		};
		var _url = "<c:url value='/admin/board/replyModifyProcess' />";
		commonAjax(_url, _data, true, function(data) {
			//successListener
			var boardReplyDTO = data.boardReplyDTO;
			var replyList = data.replyList;
			if (data.code == "0000") {
				//setReplyTable(replyList);
				var val = $("#replyEditArea"+reply_seq).val();
				var replyContent = $("#replyEditArea"+reply_seq).parent();
				replyContent.next().children("[name='reply_modify']").attr("flag","0");
				$("#replyEditArea"+reply_seq).parent().empty();
				replyContent.append(val);
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
	function setReReplyTable(replyList){
		
		
	}
	function setReplyTable(list){
		var text = "";
		var r_type = "";
		$("#reply_area tbody").empty();
		for(i=0; i<list.length; i++){
			text += "<tr>";
			text += "	<td>";
			text += "		<div class='reply-writer'>";
			text += 			list[i].reply_writer;
			text += "		</div>";
			text += "		<div>";
			text += 			list[i].reg_date;
			text += "		</div>";
			text += "		<div class='reply-content'>";
			text += 			list[i].reply_content;
			text += "		</div>";
			if( "${sessionScope.ADMIN_SESSION.admin_id}" == list[i].reply_writer || auth_level == 0 ){
				text += "	<div>";
				text += "		<input type='password' class='reply_pass'>"
				text += "		<button name='reply_modify' type='button' onclick='replyModify(this)' flag='0' reply_seq='"+list[i].reply_seq+"'>수정</button>"
				text += "		<button name='reply_del' type='button' onclick='replyDel(this);' reply_seq='"+list[i].reply_seq+"' depth='0'>삭제</button>";
				text += "	</div>";
			}
			text += "		<div>";
			text += "			<button name='reply_reply' type='button' onclick='replyReply(this);' reply_seq='"+list[i].reply_seq+"' flag='0'>댓글</button>";
			text += "		</div>";
			text += "	</td>";
			text += "</tr>";
		}
		$("#reply_area tbody").append(text);
		
	}
	function fileDown(fileName){
		var _data = {
			fileName : fileName
		};
		var _url = "<c:url value='/admin/board/fileDown' />";
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

	<div class="content mgbM">
		<div class="title mgtS">${resultDTO.board_title}</div>
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
				<c:if test="${boardGroup.email_yn eq '1' }">
				<tr>
					<th scope="col">이메일</th>
					<td>${resultDTO.board_email }</td>
				</tr>
				</c:if>
				<c:if test="${boardGroup.phone_yn eq '1'}">
				<tr>
					<th scope="col">휴대폰번호</th>
					<td>${resultDTO.board_phone }</td>
				</tr>
				</c:if>
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
											<a href="/system/board/fileDown?path=${list.file_path }&name=${list.file_name }&org_name=${list.file_org_name}">${list.file_org_name }</a>
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
				<div class="btn-module fR">
					<a class="btn btnStyle04" href="javascript:void(0);" onclick="javascript:deleteForm();">삭제</a>
				</div>
				<div class="btn-module fR">
					<a class="btn btnStyle04" href="javascript:void(0);" onclick="javascript:editForm();">수정</a>
				</div>
				<c:if test="${boardGroup.pass_yn eq '1' }">
					<input type="password" class="fR" id="board_pass" name="board_pass" maxlength="10" placeholder="패스워드" />
				</c:if>
			</c:if>
		</div>


		<c:if test="${boardGroup.reply_yn eq '1' }">
			<div class="reply_div mgtL">
				
				<div class='fR'>
					이름:  <input type="text" id="reply_writer" name="reply_writer" class="hight25" maxlength="10" value="${sessionScope.ADMIN_SESSION.admin_id }" readonly />
					패스워드:  <input type="password" id="reply_pass" name="reply_pass" class="hight25" maxlength="10" placeholder="패스워드" />
					<div class="btn-module">
						<a class="replyRegBtn" href="javascript:void(0);" onclick="replyRegProcess()">등록</a>
					</div> 
				</div>
				<div>
					<textarea id="reply_content" name="reply_content" rows="4" placeholder="댓글을 입력하세요."></textarea>
				</div>
				
				총<span id="reply_count">
					<c:choose>
						<c:when test="${totalCnt != null }">
							${totalCnt }
						</c:when>
						<c:otherwise>
							0
						</c:otherwise>
					</c:choose>
				</span>개의 댓글
				
				<table id="reply_area">
					<tbody>
						<tr reply_type="all" style="display: none">
							<!-- 뒤에 댓글 붙이기 쉽게 선언 -->
							<td colspan="5"></td>
						</tr>
						<!-- 댓글이 들어갈 공간 -->
						<c:forEach var="replyList" items="${replyList}" varStatus="status">
							<tr>
								<td>
									<div class='reply-writer'>
										${replyList.reply_writer }	
									</div>
									<div>
										${replyList.reg_date }
									</div>
									<div class='reply-content'>
										${replyList.reply_content }
									</div>
									<c:if test="${sessionScope.ADMIN_SESSION.admin_id == replyList.reply_writer || sessionScope.ADMIN_SESSION.auth_level == 0 }">
										<div>
											<input type="password" class="reply_pass">
											<button name="reply_modify" type="button" onclick="replyModify(this)" flag="0" reply_seq="${replyList.reply_seq}">수정</button>
											<button name="reply_del" type="button" onclick="replyDel(this);" reply_seq="${replyList.reply_seq}" depth='0'>삭제</button>
										</div>
									</c:if>
									<div>
										<button name="reply_reply" type="button" onclick="replyReply(this);" reply_seq="${replyList.reply_seq}" flag="0">댓글</button>
									</div>
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
			</div>
		</c:if>
	</div>
</form>