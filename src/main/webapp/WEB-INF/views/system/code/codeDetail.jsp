<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${codeGroup.replyYn eq 'Y' }">
<script type="text/javascript" src="<c:url value="/resources/system/js/reply.js" />" charset="utf-8"></script>
</c:if>

<script type="text/javascript">

	$(document).ready(function() {
	});

	// 수정
	function editForm() {
		if(!checkPasswork()){
			return;
		}
		$("#frm").attr("action", "<c:url value='/system/code/codeEdit'/>").submit();
	}

	// 삭제
	function deleteForm(useYn) {

		 if(!checkPasswork()){
			return;
		} 
		var msg = "삭제";
		if (confirm(msg + " 하시겠습니까?")) {
			var _data = {
				codeSeq : $("#codeSeq").val(),
				useYn : useYn
			};
			var _url = "<c:url value='/system/code/codeDelProcess' />";

			commonAjax(_url, _data, true, function(data) {
				//successListener
				if (data.code == "0000") {
					toastOk("처리 되었습니다.");

					timerVal = setTimeout(function() {
						listForm();
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
	
	function checkPasswork(){
		var result = false;
		var password = $("#codePass").val();
		
		if(password.trim() == ""){
        	alert("패스워드를 입력하세요.");
        	$("#codePass").focus();
        	return false;
        }
		            		
		//ajax로 패스워드 검수 후 수정 페이지로 포워딩
		//값 셋팅
		var _data = {
				codeSeq : $("#codeSeq").val(),
				codePass : password
		};
		
		var _url = "<c:url value='/system/code/codeCheckPass' />";

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
		$("#frm").attr("action", "<c:url value='/system/code/codeList'/>")
				.submit();
	}
</script>

<form id="frm" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<input type="hidden" id="groupCode" name="groupCode" value="${codeDTO.groupCode}" />
	<input type="hidden" id="codeSeq" name="codeSeq" value="${resultDTO.codeSeq}" />

	<div class="content">
		<div>
			<h>게시판내용</h>
		</div>
		<table border="1" style="width:80%;margin-top:10px" >
			<colgroup>
				<col width="20%" />
				<col width="*" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="col">제목</th>
					<td>${resultDTO.codeTitle}</td>
				</tr>
				<tr>
					<th scope="col">작성자</th>
					<td>${resultDTO.codeWriter}</td>
				</tr>
				<tr>
					<th scope="col">작성일</th>
					<td>${resultDTO.regDate}</td>
				</tr>
				
				<tr>
					<th scope="col">읽음</th>
					<td>${resultDTO.viewCnt}</td>
				</tr>
				<tr>
					<th scope="col">내용</th>
					<td>
						${resultDTO.codeContents}
					</td>
				</tr>
				<tr>
					<th scope="col">파일첨부</th>
					<td>
					<c:choose>
						<c:when test="${!empty fileList}">
							<c:set var="num" value="0" />
							<c:forEach items="${fileList}" var="list">	
								<c:set var="num" value="${num+1}" />
								${num } . <a href="/common/imgLoad?showImage=O&fileSeq=${list.fileSeq }" target="_blank">${list.fileOrgName }</a>
								<img src="/common/imgLoad?showImage=T&fileSeq=${list.fileSeq }">
				  			</c:forEach>
						</c:when>

						<c:otherwise>
							첨부파일 없음
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</tbody>
		</table>
		
		

<a href="javascript:void(0);" onclick="javascript:listForm();">목록</a>
<br>
<input type="password" id="codePass" name="codePass" style="width:200px;" maxlength="10" placeholder="패스워드"/>
<a href="javascript:void(0);" onclick="javascript:deleteForm();">삭제</a>
<a href="javascript:void(0);" onclick="javascript:editForm();">수정</a>
</form>

<c:if test="${codeGroup.replyYn eq 'Y' }">
		<br/><br/>
		<div>
			<h>댓글</h>
		</div>
		<table border="1" width="1200px" id="reply_area">
   				<tr reply_type="all"  style="display:none"><!-- 뒤에 댓글 붙이기 쉽게 선언 -->
   					<td colspan="4"></td>
   				</tr>
	   			<!-- 댓글이 들어갈 공간 -->
	   			<c:forEach var="replyList" items="${replyList}" varStatus="status">
					<tr reply_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>"><!-- 댓글의 depth 표시 -->
			    		<td width="820px">
			    			<c:if test="${replyList.depth == '1'}"> → </c:if>${replyList.replyContent}
			    		</td>
			    		<td width="100px">
			    			${replyList.replyWriter}
			    		</td>
			    		<td width="100px">
			    			<input type="password" id="reply_pass_${replyList.replySeq}" style="width:100px;" maxlength="10" placeholder="패스워드"/>
			    		</td>
			    		<td align="center">
			    			<c:if test="${replyList.depth != '1'}">
			    				<button name="reply_reply" type="button" onclick="replyReply(this);"  parent_seq = "${replyList.replySeq}" reply_seq = "${replyList.replySeq}">댓글</button><!-- 첫 댓글에만 댓글이 추가 대댓글 불가 -->
			    			</c:if>
			    			<button name="reply_modify" type="button" onclick="replyModify(this)" parent_seq = "${replyList.parentSeq}" r_type = "<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>" reply_seq = "${replyList.replySeq}">수정</button>
			    			<button name="reply_del" type="button"  onclick="replyDel(this);" r_type = "<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>" reply_seq = "${replyList.replySeq}">삭제</button>
			    		</td>
			    	</tr>
			    </c:forEach>
   			</table>
   			<table border="1" width="1200px" bordercolor="#46AA46">
   				<tr>
   					<td width="500px">
						이름: 		<input type="text" id="reply_writer" name="reply_writer" style="width:170px;" maxlength="10" placeholder="작성자"/>
						패스워드: 	<input type="password" id="reply_pass" name="reply_pass" style="width:170px;" maxlength="10" placeholder="패스워드"/>
						<button type="button" id="reply_save" name="reply_save" onClick="replyRegProcess();">댓글 등록</button>
					</td>
   				</tr>
   				<tr>
   					<td>
   						<textarea id="reply_content" name="reply_content" rows="4" cols="50" placeholder="댓글을 입력하세요."></textarea>
   					</td>
   				</tr>
   			</table>
   			</c:if>
	
