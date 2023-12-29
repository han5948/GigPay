<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

	$(document).ready(function() {
		$("input[name=board_type]").change(function(){
			var boardType = $(this).val();
			var tr = $("input[name=reply_yn]").parent().parent();
			if( boardType == 'Q' ){
				radioDisplay("reply_yn", 0);
				radioDisplay("email_yn", 0);
				radioDisplay("phone_yn", 0);
			}else{
				radioDisplay("reply_yn", 1);
				radioDisplay("email_yn", 1);
				radioDisplay("phone_yn", 1);
			}
		});
	});
	function radioDisplay(name, val){
		var tr = $("input[name="+name+"]").parent().parent();
		$("input[name="+name+"]").each(function(index, item){
			if( $(item).val() == val ){
				$(item).prop("checked",true);
			}
		});
		if( val == '0' ){
			tr.css("display","none");
		}else{
			tr.css("display","");
		}
	}
	function chkValidEmpty(objId){
		var value = $("#"+objId).val();
		value = value.replace(/\s/gi,"");//공백 제거
		if( value == '' ){
			var title = $("#"+objId).attr('title');
			toastW(title + "을(를) 입력하세요.");
			$("#"+objId).focus();
			return false;
		}
		
		return true;
	}
	// 수정
	function editProcess() {
		if ( chkValidEmpty("board_name") && chkValidEmpty("board_comment") ) {
			$('#frmReg').ajaxForm({
					url : "/admin/board/boardGroupEditProcess",
					dataType : "json",
				    enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
					contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
				    success : function(data) {
							if ( data.code == "0000" ) {
								alert("수정 완료 되었습니다.");
								listForm();
							} else {
								toastFail("수정 실패");
							}
					} ,
					beforeSend : function(xhr) {
						 xhr.setRequestHeader("AJAX", true);
						 $(".wrap-loading").show();
					},
				    error : function(e) {
						if ( e.status == "901" ) {
							location.href = "/admin/login";
						} else {
						 $.toast({title: '수정 실패', position: 'middle', backgroundColor: '#d60606', duration:1000 });
						}
					},
				  	complete : function() {
						$(".wrap-loading").hide();
					}
			});
	
			$('#frmReg').submit();
		}
	}
// 리스트
function listForm() {
	$("#contentFrm").attr("action", "<c:url value='/admin/board/boardGroupList'/>").submit();
}

</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${boardDTO.paging.pageNo}" />
	
</form>

<form id="frmReg" name="frmReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<input type="hidden" id="board_group_seq" name="board_group_seq" value="${resultDTO.board_group_seq}" />
	<div class="content mgbM">
		<div class="title">
			게시판 수정
		</div>

		<table class="boardTable">
			<colgroup>
				<col width="20%" />
				<col width="*" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="col">게시판타입</th>
					<td >
						<input type="radio" name="board_type"  value="B" ${resultDTO.board_type eq 'B' ? ' checked=\'checked\'' : ''} class="notEmpty" title="타입" /> 일반게시판
						<input type="radio" name="board_type"  value="F" ${resultDTO.board_type eq 'F' ? ' checked=\'checked\'' : ''} class="notEmpty" title="타입"  /> 파일게시판
						<input type="radio" name="board_type"  value="Q" ${resultDTO.board_type eq 'Q' ? ' checked=\'checked\'' : ''} class="notEmpty" title="타입"  /> FAQ
					</td>
				</tr>
				<tr>
					<th scope="col">이름</th>
					<td>
						<input type="text" name="board_name" id="board_name" maxlength="100" class="input00 wid700 notEmpty" value="${resultDTO.board_name}" title="게시판이름" />
					</td>
				</tr>
				<tr>
					<th scope="col">설명</th>
					<td>
						<textarea rows="10" cols="132" name="board_comment" id="board_comment" class="wid700 notEmpty" title="내용">${resultDTO.board_comment}</textarea>
					</td>
				</tr>

				<c:choose>
					<c:when test="${resultDTO.board_type eq 'Q' }">
						<tr style="display:none;">
					</c:when>
					<c:otherwise>
						<tr>
					</c:otherwise>
				</c:choose>
					<th scope="col">댓글사용</th>
					<td >
						<input type="radio" name="reply_yn" value="1" ${resultDTO.reply_yn eq '1' ? ' checked=\'checked\'' : ''} /> 사용
						<input type="radio" name="reply_yn" value="0" ${resultDTO.reply_yn eq '0' ? ' checked=\'checked\'' : ''}  /> 미사용
					</td>
				</tr>
				
				<tr>
					<th scope="col">패스워드</th>
					<td >
						<input type="radio" name="pass_yn" value="1" ${resultDTO.pass_yn eq '1' ? ' checked=\'checked\'' : ''} /> 사용
						<input type="radio" name="pass_yn" value="0" ${resultDTO.pass_yn eq '0' ? ' checked=\'checked\'' : ''}  /> 미사용
					</td>
				</tr>
				<tr>
					<th scope="col">Editor</th>
					<td >
						<input type="radio" name="editor_yn" value="1" ${resultDTO.editor_yn eq '1' ? ' checked=\'checked\'' : ''} title="사용유무" /> 사용
						<input type="radio" name="editor_yn" value="0" ${resultDTO.editor_yn eq '0' ? ' checked=\'checked\'' : ''} title="사용유무"  />미사용 
					</td>
				</tr>
				<tr>
					<th scope="col">Editor</th>
					<td >
						<input type="radio" name="editor_yn" value="1" ${resultDTO.editor_yn eq '1' ? ' checked=\'checked\'' : ''} title="사용유무" /> 사용
						<input type="radio" name="editor_yn" value="0" ${resultDTO.editor_yn eq '0' ? ' checked=\'checked\'' : ''} title="사용유무"  />미사용 
					</td>
				</tr>
				<c:choose>
					<c:when test="${resultDTO.board_type eq 'Q' }">
						<tr style="display:none;">
					</c:when>
					<c:otherwise>
						<tr>
					</c:otherwise>
				</c:choose>
					<th scope="col">Email</th>
					<td >
						<input type="radio" name="email_yn" value="1" ${resultDTO.email_yn eq '1' ? ' checked=\'checked\'' : ''} title="사용유무" /> 사용
						<input type="radio" name="email_yn" value="0" ${resultDTO.email_yn eq '0' ? ' checked=\'checked\'' : ''} title="사용유무"  />미사용 
					</td>
				</tr>
				<c:choose>
					<c:when test="${resultDTO.board_type eq 'Q' }">
						<tr style="display:none;">
					</c:when>
					<c:otherwise>
						<tr>
					</c:otherwise>
				</c:choose>
					<th scope="col">phone</th>
					<td >
						<input type="radio" name="phone_yn" value="1" ${resultDTO.phone_yn eq '1' ? ' checked=\'checked\'' : ''} title="사용유무" /> 사용
						<input type="radio" name="phone_yn" value="0" ${resultDTO.phone_yn eq '0' ? ' checked=\'checked\'' : ''} title="사용유무"  />미사용 
					</td>
				</tr>
				<tr>
					<th scope="col">글쓰기 지점</th>
					<td class="linelY">
						<input type="radio" name="write_company_seq" value="0" ${resultDTO.write_company_seq eq '0' ? ' checked=\'checked\'' : ''} title="글쓰기 지점" /> 전체
						<input type="radio" name="write_company_seq" value="13" ${resultDTO.write_company_seq eq '13' ? ' checked=\'checked\'' : ''} title="글쓰기 지점"  />본사
					</td>
				</tr>
				<tr>
					<th scope="col">글쓰기 권한</th>
					<td class="linelY">
						<input type="radio" name="write_admin_auth" value="-1" ${resultDTO.write_admin_auth eq '-1' ? ' checked=\'checked\'' : ''} title="글쓰기 권한" /> 전체
						<input type="radio" name="write_admin_auth" value="0" ${resultDTO.write_admin_auth eq '0' ? ' checked=\'checked\'' : ''} title="글쓰기 권한"  />최고관리자
						<input type="radio" name="write_admin_auth" value="1" ${resultDTO.write_admin_auth eq '1' ? ' checked=\'checked\'' : ''} title="글쓰기 권한"  />관리자이상
					</td>
				</tr>
				<tr>
					<th scope="col">사용유무</th>
					<td >
						<input type="radio" name="use_yn" value="1" ${resultDTO.use_yn eq '1' ? ' checked=\'checked\'' : ''}  /> 사용
						<input type="radio" name="use_yn" value="0" ${resultDTO.use_yn eq '0' ? ' checked=\'checked\'' : ''}  /> 미사용
					</td>
				</tr>
			</tbody>
		</table>
		<div class="btn-module mgtL">
			<a class="btnStyle04" href="javascript:void(0);" onclick="javascript:listForm();">목록</a>
		</div>
		<div class="btn-module mgtL fR">
			<a class="btnStyle04" href="javascript:void(0);" onclick="javascript:editProcess();">저장</a>
		</div>
	</div>

</form>