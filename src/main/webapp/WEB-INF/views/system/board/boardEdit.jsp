<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/resources/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">

	var editor_yn = "${boardGroup.editor_yn}";
	$(function(){
		if(editor_yn == "1"){
		    //전역변수
		    var obj = [];              
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
	
	    //전송버튼
	 	$("#editBoard").click(function(){
			if(editor_yn == "1"){
		        //id가 smarteditor인 textarea에 에디터에서 대입
		        obj.getById["editor"].exec("UPDATE_CONTENTS_FIELD", []);
		        //폼 submit
		 	}
			editProcess();
	    });
	});

	function chkValidEditor(){
		if( editor_yn == '1' ){
			var value = $("#editor").val();
			value = remove(value,'<p style=',';">',"</p>"); //p태그 스타일 제거
	 		value = value.replace(/&nbsp;/gi,"");//공백 제거
	 		value = value.replace(/(<br>)|(<br \/>)/gi,"");//br태그 제거
	 		value = value.replace(/(<p>)|(<\/p>)/gi,"");//p태그 제거
	 		value = value.replace(/\s/gi,"");//공백 제거
			if( value == '' ){
				toastW("내용을 입력하세요.");
				$("#editor").focus();
				return false;
			}
		}else{
			var value = $("#board_contents").val();
			value = value.replace(/\s/gi,"");//공백 제거
			if( value == '' ){
				toastW("내용을 입력하세요.");
				$("#board_contents").focus();
				return false;
			}
		}
		return true;
	}
	function remove(argBody, argStartSection, argEndSection, argRemoveSection){

	    var bodyString = argBody;

	    var sectionChk = bodyString.match(new RegExp(argStartSection,'g'));
	    if(sectionChk != null){
	        for(var i=0; i < sectionChk.length; i++){
	            var tmpImg = bodyString.substring(bodyString.indexOf(argStartSection), (bodyString.indexOf(argEndSection)+(argEndSection.length)));
	            bodyString = bodyString.replace(tmpImg, '').replace(/<br>/gi, '').replace(/&nbsp;/gi, ' ').replace(/<p>/gi, '').replace(/<\/p>/gi, ' ').replace(new RegExp(argRemoveSection,'gi'), '');
	        }
	    }

	    return bodyString;
	};
// 	function chkValidTitle(){
// 		var value = $("#board_title").val();
// 		value = value.replace(/\s/gi,"");//공백 제거
		
// 		if( value == '' ){
// 			toastW("제목을(를) 입력해 주세요.");
// 			$("#board_title").focus();
// 			return false;
// 		}
// 		return true;
// 	}
	
	// 수정
	function editProcess() {
		
		if ( fnChkEmptyForm("frmReg") && chkValidEditor() ) {
			$('#frmReg').ajaxForm({
					url : "/system/board/boardEditProcess",
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
							location.href = "/system/login";
						} else {
						 $.toast({title: '수정 실패', position: 'middle', backgroundColor: '#d60606', duration:1000 });
						}
					},
				  	complete : function() {
						$(".wrap-loading").hide();
					}
			});

			$('#frmReg').submit();
			detailForm();
		}
	}
	// 리스트
	function listForm() {
		$("#frm").attr("action", "<c:url value='/system/board/boardList'/>").submit();
	}
	function detailForm() {
		$("#frm").attr("action", "<c:url value='/system/board/boardDetail'/>").submit();
	}


	function fileDel(fileSeq) {
		if ( confirm("파일을 삭제 하시겠습니까?") ) {
			var _data = {
					file_seq : fileSeq
	  		};
	  	var _url = "<c:url value='/admin/deleteFile' />";

			commonAjax(_url, _data,true, function(data) {
	  			//successListener
	  			if ( data.code == "0000" ) {
	  				toastOk("파일이 삭제 되었습니다.");
	  				$("#file_"+fileSeq).remove();
	  				$("#file_list_cnt").val( $("#file_list_cnt").val() - 1);
	  			} else {
	  				if ( jQuery.type(data.message) != 'undefined' ) {
	  					if ( data.message != "" ) {
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
	function fileBtnAdd(){
		var trCnt = $("#fileArea div").length;
		if( trCnt < 5 ){
			var text = "";
			text += "<div>";
			text += "	<input type='file' name='attachFile"+trCnt+"' id='attachFile"+trCnt+"' /><a href='#' onclick='fileBtnDel(this)'>제거</a>";
			text += "</div>";
			$("#fileArea:last").append(text);
		}else{
			alert("파일은 최대 5개 까지만 추가가능 합니다.");
		}
	}
	function fileBtnDel(obj){
		$(obj).parent().remove();
	}
	

</script>
<form id="frm" name="frm" method="post">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${boardDTO.paging.pageNo}" />
	<input type="hidden" id="board_group_seq" name="board_group_seq" value="${boardDTO.board_group_seq}" />
	<input type="hidden" id="board_seq" name="board_seq" value="${resultDTO.board_seq}" />
</form>

<form id="frmReg" name="frmReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<input type="hidden" id="board_seq" name="board_seq" value="${resultDTO.board_seq}" />
	<input type="hidden" id="file_list_cnt" name="file_list_cnt" value="${fileListCnt }">

	<div class="content">
		<div class="title">게시판내용 수정</div>


		<table class="boardTable">
			<colgroup>
				<col width="20%" />
				<col width="*" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="col">작성자</th>
					<td>
						<input type="text" name="board_writer" id="board_writer" maxlength="100" class="notEmpty" value="${resultDTO.board_writer}" title="작성자" readonly/>
					</td>
				</tr>
				<tr>
					<th scope="col">제목</th>
					<td>
						<input type="text" name="board_title" id="board_title" maxlength="100" class="input00 notEmpty" value="${resultDTO.board_title}" title="제목" />
					</td>
				</tr>

				<tr>
					<th scope="col">내용</th>
					<td>
						<c:choose>
							<c:when test="${boardGroup.editor_yn eq '0' }">
								<textarea rows="10" cols="132" name="board_contents" id="board_contents" class="box notEmpty" title="내용">${resultDTO.board_contents}</textarea>
							</c:when>
							<c:otherwise>
								<textarea name="board_contents" id="editor" style="width: 700px; height: 400px;">${resultDTO.board_contents}</textarea>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<c:if test="${boardGroup.pass_yn eq '1' }">
					<tr>
						<th scope="col">패스워드</th>
						<td>
							<input type="text" name="board_pass" id="board_pass" maxlength="100" class="notEmpty" value="${boardDTO.board_pass}" title="패스워드" />
						</td>
					</tr>    
				</c:if>
				<c:if test="${boardGroup.board_type eq 'F' }">
					<tr>
						<th scope="col">파일첨부</th>
						<td>
							<input type="button" id="fileAdd_btn" onclick="fileBtnAdd(${fileListCnt})" value="파일추가">
						</td>
					</tr>
					<tr>
						<th scope="col"></th>
						<td id="fileArea">
							<c:choose>
								<c:when test="${!empty fileList}">
									<c:set var="num" value="0" />
									<c:forEach items="${fileList}" var="list">
										<c:set var="num" value="${num+1}" />

										<div id="file_${list.file_seq }" style="padding-bottom: 3px;">
											<a href="#" onclick="fileDel(${list.file_seq })" class="btn">삭제</a>
											${num } . ${list.file_org_name }
										</div>
									</c:forEach>
								</c:when>
							</c:choose>
							<c:if test="${fileListCnt < 5 }">
								<div>
									<input type="file" name="attachFile" id="attachFile" />
								</div>
							</c:if>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>

		<div class="btn-module mgtL">
			<a class="btnStyle04" href="javascript:void(0);" onclick="javascript:listForm();">목록</a>
		</div>
		<div class="btn-module mgtL fR">
			<a class="btnStyle04" href="javascript:void(0);" id="editBoard">저장</a>
		</div>
	</div>

</form>