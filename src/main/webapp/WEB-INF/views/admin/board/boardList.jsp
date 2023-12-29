<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/resources/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/web/js/newPaging.js" />" charset="utf-8"></script>

<script type="text/javascript">
	var obj = []; 
	var editor_yn = "${boardGroup.editor_yn}";
	var pass_yn = "${boardGroup.pass_yn}";
	var auth_level = "${sessionScope.ADMIN_SESSION.auth_level }";
	var admin_id = "${sessionScope.ADMIN_SESSION.admin_id}";
	var edit_count = 0;
	$(document).ready(function() {
		datePickerSet('startDate' , 'endDate');
	});
	
	
	//수정 삭제 여부 체크
	function chkEditDelete(){
		if( auth_level == "" ){ //비회원 일때
			if( pass_yn == 1 ){
				if( !checkPassword() ){ //패스워드 확인
					return false;
				}				
			}else{
				toastW("권한이 없습니다.");
				return false;				
			}
		}
		if( auth_level > 0 ){ //최고관리자 확인
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
	// 등록
	function regForm() {
		$("#contentFrm").attr("action", "<c:url value='/admin/board/boardReg'/>").submit();
	}
	// 목록
	function listForm() {
		$("#contentFrm").attr("action", "<c:url value='/admin/board/boardGroupList'/>").submit();
	}

	// 상세보기
	function detailForm(seq) {
		$("#board_seq").val(seq);
		$("#contentFrm").attr("action", "<c:url value='/admin/board/boardDetail'/>").submit();
	}
	// FAQ 게시판 삭제
	function del(seq){
		$("#board_seq_reg").val(seq);
		if( !chkEditDelete() ){
			return;
		}
		var msg = "삭제";
		if (confirm(msg + " 하시겠습니까?")) {
			var _data = {
				board_seq : seq
			};
			var _url = "<c:url value='/admin/board/boardDelProcess' />";

			commonAjax(_url, _data, true, function(data) {
				//successListener
				if (data.code == "0000") {
					toastOk("삭제 되었습니다.");
					location.reload();
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
	// FAQ 게시판 수정
	function edit(seq){
		$("#board_seq_reg").val(seq);
		if( !chkEditDelete() ){
			return;
		}
		
		if( edit_count < 1 ){
			$(".faqForm-"+seq+" td:nth-child(2)").each(function(index, item){
				var text = "";
				if( index == 0 ){
					text += "<input type='text' style='width:100%;' name='board_title' id='board_title' value='"+$(item).text().trim()+"' />"
				}else if( index == 1 ){
					text += '<textarea name="board_contents" id="editor" style="width: 100%; height: 400px;">'+$(item).html()+'</textarea>';
					text += "<a href='#' id='editBoard' onclick=readEditor("+seq+")>저장</a>";
				}
				$(item).html(text);
			});
			createEditorFrame();
		}else{
			alert("게시물이 수정 중 입니다.");
		}
		edit_count = edit_count + 1;
	}
	
	function checkPassword(){
		var result = false;
		var password = $("#board_pass"+$("#board_seq_reg").val()).val();
		
		if(password == ""){
        	alert("패스워드를 입력하세요.");
        	return false;
        }
		            		
		//ajax로 패스워드 검수 후 수정 페이지로 포워딩
		//값 셋팅
		var _data = {
				board_seq : $("#board_seq_reg").val(),
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
	function checkAdmin(){
		var result = false;
		
		//ajax로 패스워드 검수 후 수정 페이지로 포워딩
		//값 셋팅
		var _data = {
				board_seq : $("#board_seq_reg").val(),
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
	
	function readEditor(seq){
		if(editor_yn == "1"){
	        //id가 smarteditor인 textarea에 에디터에서 대입
	        obj.getById["editor"].exec("UPDATE_CONTENTS_FIELD", []);
	        //폼 submit
	 	}
		editProcess(seq);
	}
	function createEditorFrame(){
		if(editor_yn == "1"){
		    //스마트에디터 프레임생성
		    nhn.husky.EZCreator.createInIFrame({
		        oAppRef: obj,
		        elPlaceHolder: "editor",
		        sSkinURI: "/resources/editor/SmartEditor2Skin.html",
		        htParams : {
		            // 툴바 사용 여부
		            bUseToolbar : true,            
		            // 입력창 크기 조절바 사용 여부
		            bUseVerticalResizer : true,    
		            // 모드 탭(Editor | HTML | TEXT) 사용 여부
		            bUseModeChanger : true,
		        }
		    });
		}
	}
	// FAQ 게시물 수정 ajax
	function editProcess(seq) {
		$("#board_seq_reg").val(seq);
		if ( fnChkEmptyForm("frmReg") ) {
			$('#frmReg').ajaxForm({
				url : "/admin/board/boardEditProcess",
				dataType : "json",
			    enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
				contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			    success : function(data) {
						if ( data.code == "0000" ) {
							alert("수정 성공");
							
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
			location.reload();
		}
	}
	// FAQ 게시판 전용 폼
	function faqForm(seq){
		
		if($("#board-"+seq).attr("flag") == "0"){
			var _data = {
				board_seq : seq
			};
			var _url = "<c:url value='/admin/board/getBoardInfo' />";
			commonAjax(_url, _data, true, function(data) {
				//successListener
				var resultDTO = data.resultDTO;
				
				var text = "";
				text += "<tr class='faqForm-"+seq+"'>";
				text += "	<td class='questions-title'>";
				text +=	"		Q";	
				text += "	</td>";
				text += "	<td class='faq-content'>";
				text += 		resultDTO.board_title;
				text += "	</td>";
				text += "	<td colspan=2 class='faq-content'>";
				text += "		조회수: " + resultDTO.view_cnt;
				text += "	</td>";
				text += "</tr>";
				text += "<tr class='faqForm-"+seq+"'>";
				text += "	<td class='answer-title'>";
				text +=	"		A";	
				text += "	</td>";
				text += "	<td class='faq-content'>";
				text += 		resultDTO.board_contents;
				text += "	</td>";
				text += "	<td colspan=2 class='faq-content'>";
				if( auth_level == 0 || resultDTO.board_writer == admin_id ){
					if( pass_yn == 1 ){
						text += "		<input type='text' style='display:none;' />" //submit작동 안하도록 만들어줌.
						text += "		<input type='text' id='board_pass"+seq+"' name='board_pass' placeholder='비밀번호' />";
					}
						text += "		<a href='#' onclick='edit("+seq+")'>수정</a> <a href='#' onclick='del("+seq+")'>삭제</a>";
				}
				text += "	</td>";
				text += "</tr>";
				$("#board-"+seq).after(text);
				$("#board-"+seq).attr("flag", "1");
				

				$("#board_pass"+seq).keydown(function(key) {
					if (key.keyCode == 13) {
						edit(seq);
					}
				});
				
			}, function(data) {
				//errorListener
				toastFail("오류가 발생했습니다.");
			}, function() {
				//beforeSendListener
			}, function() {
				//completeListener
			});
		}else{
			$(".faqForm-"+seq).remove();
			$("#board-"+seq).attr("flag", "0");
			edit_count = 0;
		}
	}
	function fn_data_receive(data){
		if( data.code == "0000" ){
			var boardList = data.boardList;
			var cnt = data.totalCnt;
			var boardDTO = data.boardDTO;
			var text = "";
			
			$("#boardList").empty();
			if( cnt == 0 ){
				text += "<tr style='text-align: center;'>";
				text += "	<td colspan='4'>조회된 내용이 없습니다.</td>";
				text += "</tr>";
			}
			for(i=0; i<boardList.length; i++){
				var num = cnt - ((boardDTO.paging.pageNo - 1) * boardDTO.paging.pageSize + i);
				text += "<tr id='board-"+boardList[i].board_seq+"' flag='0'>";
				text += "	<td>"+num+"</td>";
				text += "	<td>";
				if( boardList[i].board_type == 'Q' ){
					text += "<a href='javascript:void(0);' onclick='javascript:faqForm("+boardList[i].board_seq+");' >"+boardList[i].board_title+"</a>";
				}else{
					text += "<a href='javascript:void(0);' onclick='javascript:detailForm("+boardList[i].board_seq+");'>"+boardList[i].board_title+"</a>"
				}
				text += "	</td>";
				text += "	<td>"+boardList[i].board_writer+"</td>";
				text += "	<td>"+boardList[i].reg_date+"</td>";
				text += "</tr>";
			}
			$("#boardList").append(text);
			
			$(".paging").empty();
			fn_page_display_ajax_new(boardDTO.paging.pageSize, cnt , '#pageNo', "<c:url value='/admin/board/getBoardList' />", '.paging', '#query');
		}else{
			console.log("error code:" + data.code);
			console.log("error message:" + data.message);
		}
	}

</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="board_group_seq" name="board_group_seq" value="${boardGroup.board_group_seq }" />
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${boardDTO.paging.pageNo}" />
	<input type="hidden" id="board_seq" name="board_seq" value="${boardDTO.board_seq }" />
</form>

<form id="frmReg" name="frmReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<input type="hidden" id="query" value="${boardGroup.board_group_seq }">
	<input type="hidden" id="board_seq_reg" name="board_seq" value="${boardDTO.board_seq }" />
	<div class="content mgtS mgbM">
		<div class="title">${boardGroup.board_name }</div>

		<table class="bd-list">
			<colgroup>
				<col width="8%" />
				<col width="*" />
				<col width="10%" />
				<col width="10%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">No.</th>
					<th scope="col">제 목</th>
					<th scope="col">작성자</th>
					<th scope="col" class="last">등록일</th>
				</tr>
			</thead>
			<tbody id="boardList">
				<c:choose>
					<c:when test="${!empty boardList}">
						<c:set var="num" value="${boardDTO.paging.rowCount - (boardDTO.paging.pageNo - 1) * boardDTO.paging.pageSize}" />

						<c:forEach items="${boardList}" var="list">
							<tr id="board-${list.board_seq}" flag="0">
								<td>${num}</td>
								<td>
								<c:choose>
									<c:when test="${boardGroup.board_type eq 'Q' }">
									<a href="javascript:void(0);" onclick="javascript:faqForm('${list.board_seq}');" >${list.board_title}</a>
									</c:when>
									<c:otherwise>
									<a href="javascript:void(0);" onclick="javascript:detailForm('${list.board_seq}');">${list.board_title}</a>
									</c:otherwise>
								</c:choose>
								</td>
								<td>${list.board_writer}</td>
								<td>${list.reg_date }</td>
							</tr>

							<c:set var="num" value="${num-1}" />

						</c:forEach>
					</c:when>

					<c:otherwise>
						<tr style="text-align: center;">
							<td colspan="4">조회된 내용이 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
<%-- 		<c:if test="${sessionScope.ADMIN_SESSION.auth_level == 0 }"> --%>
<!-- 		<div class="btn-module mgtL"> -->
<!-- 			<a class="btn mod r mr btnStyle04" href="javascript:void(0);" onclick="javascript:listForm();">목록</a> -->
<!-- 		</div> -->
<%-- 		</c:if> --%>
		
		<c:if test="${(boardGroup.write_company_seq == 0 || sessionScope.ADMIN_SESSION.company_seq == boardGroup.write_company_seq) && (boardGroup.write_admin_auth == -1 || boardGroup.write_admin_auth >= sessionScope.ADMIN_SESSION.auth_level ) }">
<%-- 			<c:if test="${sessionScope.ADMIN_SESSION.auth_level == 0 || boardGroup.board_type != 'Q' }"> --%>
				<div class="btn-module mgtL">
					<a class="btn mod r mr btnStyle04" href="javascript:void(0);" onclick="javascript:regForm();">등록</a>
				</div>
<%-- 			</c:if> --%>
		</c:if>
		

		
		<c:if test="${boardDTO.paging.pageSize != '0' }">
			<div class="paging">
				<script type="text/javascript">
					fn_page_display_ajax_new('${boardDTO.paging.pageSize}', '${totalCnt }', '#pageNo', "<c:url value='/admin/board/getBoardList' />", '.paging', '#query')
				</script>
			</div>
		</c:if>
	</div>

</form>
