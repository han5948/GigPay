<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="<c:url value="/resources/common/js/showModalDialog.js" />" charset="utf-8"></script>

<script type="text/javascript">

	$(document).ready(function() {
		$("input[name=board_type]").change(function(){
			var boardType = $(this).val();
			var tr = $("input[name=reply_yn]").parent().parent();
			if( boardType == 'Q' ){
				$("input[name=reply_yn]").each(function(index, item){
					if( $(item).val() == 0 ){
						$(item).prop("checked",true);
					}
				});
				tr.css("display","none");
			}else{
				$("input[name=reply_yn]").each(function(index, item){
					if( $(item).val() == 1 ){
						$(item).prop("checked",true);
					}
				});
				tr.css("display","");
			}
		});
	});
	
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
	
	
	// 등록
	function insertProcess() {
		if ( chkValidEmpty('board_name') && chkValidEmpty('board_comment') ) {
			$('#frmReg').ajaxForm({
				url : "/system/board/boardGroupRegProcess",
				dataType : "json",
				enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
				contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
				success : function(data) {
						if ( data.code == "0000" ) {
							alert("정상적으로 업로드 되었습니다.");
							listForm();
						} else {
							toastFail("등록 실패");
						}
				} ,
				beforeSend : function(xhr) {
					 xhr.setRequestHeader("AJAX", true);
					 $(".wrap-loading").show();
				},
			     error : function(e) {
					if ( e.status == "901" ) {
						location.href = "/system/login";
					} else {
						toastFail("등록 실패");
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
		$("#frm").attr("action", "<c:url value='/system/board/boardGroupList'/>").submit();
	}

</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${boardDTO.paging.pageNo}" />
	
</form>

<form id="frmReg" name="frmReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
    <article>	
	<div class="content">
		<div class="title ">
			<h3>게시판 등록</h3>
		</div>

		<table class="bd-form" summary="게시판등록.">
			<colgroup>
				<col width="200px" />
				<col width="*" />
			</colgroup>
			<tbody>
				<tr>
					<th >게시판타입</th>
					<td class="linelY">
						<input type="radio" name="board_type"  value="B" checked="checked"  title="타입" /> 일반게시판
						<input type="radio" name="board_type"  value="F"   title="타입"  /> 파일게시판
						<input type="radio" name="board_type"  value="Q"  title="타입"  /> FAQ
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td class="linelY">
						<input type="text" name="board_name" id="board_name" maxlength="100" class="inp-field wid700 notEmpty" value="${boardDTO.board_name}" title="게시판이름" />
					</td>
				</tr>
				<tr>
					<th scope="col">설명</th>
					<td class="linelY">
						<textarea rows="10" cols="98" name="board_comment" id="board_comment" class="wid700 notEmpty" title="내용">${boardDTO.board_comment}</textarea>
					</td>
				</tr>
				<tr>
					<th scope="col">댓글사용</th>
					<td class="linelY">
						<input type="radio" name="reply_yn" value="1" checked="checked" title="사용유무" /> 사용
						<input type="radio" name="reply_yn" value="0" title="사용유무"  />미사용 
					</td>
				</tr>
				<tr>
					<th scope="col">패스워드</th>
					<td class="linelY">
						<input type="radio" name="pass_yn" value="1" checked="checked" title="사용유무" /> 사용
						<input type="radio" name="pass_yn" value="0" title="사용유무"  />미사용 
					</td>
				</tr>
				<tr>
					<th scope="col">Editor</th>
					<td class="linelY">
						<input type="radio" name="editor_yn" value="1" checked="checked" title="사용유무" /> 사용
						<input type="radio" name="editor_yn" value="0" title="사용유무"  />미사용 
					</td>
				</tr>
			</tbody>
		</table>

		<div class="btn-module mgtL">
			<a class="btnStyle04" href="javascript:void(0);" onclick="javascript:listForm();">목록</a>
		</div>
		<div class="btn-module mgtL fR">
			<a class="btnStyle04" href="javascript:void(0);" onclick="javascript:insertProcess();">저장</a>
		</div>

		
	</div>
</article>

</form>