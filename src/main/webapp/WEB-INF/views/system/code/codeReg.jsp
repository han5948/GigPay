<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="/resources/editor/js/HuskyEZCreator.js" charset="utf-8"></script>

<script type="text/javascript">

	var editorYn = "${codeGou.editorYn}";
	$(function(){
		if(editorYn == "Y"){
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
	 	$("#insertCode").click(function(){
			if(editorYn == "Y"){
		        //id가 smarteditor인 textarea에 에디터에서 대입
		        obj.getById["editor"].exec("UPDATE_CONTENTS_FIELD", []);
		        //폼 submit
		 	}
	        insertProcess();
	    });
	});



	// 등록
	function insertProcess() {
		if ( fnChkValidForm('frmReg') ) {
			$('#frmReg').ajaxForm({
				url : "/system/code/codeRegProcess",
				dataType : "json",
				enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
				contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
				success : function(data) {
						if ( data.code == "0000" ) {
							toastOk("정상적으로 업로드 되었습니다.");
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
		$("#frm").attr("action", "<c:url value='/system/code/codeList'/>").submit();
	}


</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="groupCode" name="groupCode" value="${codeDTO.groupCode }" />
	
</form>

<form id="frmReg" name="frmReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<input type="hidden" id="groupCode" name="groupCode" value="${codeDTO.groupCode }" />
	
	
	<div class="content">
		<div>
			<h>${codeGroup.groupName }</h>
		</div>

		<table border="1" style="width:80%;margin-top:10px" >
			<colgroup>
				<col width="20%" />
				<col width="*" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="col">코드그룹</th>
					<td>
						${codeGroup.groupCode}
					</td>
				</tr>
				<tr>
					<th scope="col">코드</th>
					<td>
						<input type="text" name="codeNum" id="codeNum" maxlength="100" class="input00 notEmpty" value="${resultDTO.codeNum}" title="코드명" />
					</td>
				</tr>
				<tr>
					<th scope="col">코드명</th>
					<td>
						<input type="text" name="codeName" id="codeName" maxlength="100" class="input00 notEmpty" value="${resultDTO.codeName}" title="코드명" />
					</td>
				</tr>
				
				<tr>
					<th scope="col">코드타입</th>
					<td>
						<input type="text" name="codeType" id="codeType" maxlength="100" class="input00" value="${resultDTO.codeType}" title="코드타입" />
					</td>
				</tr>
				
				<tr>
					<th scope="col">코드rank</th>
					<td>
						<input type="text" name="codeRank" id="codeRank" maxlength="100" class="input00" value="${resultDTO.codeRank}" title="rank" />
					</td>
				</tr>
				
				
			</tbody>
		</table>

		<div class="btn_base" >
			<a class="btn mod l" href="javascript:void(0);" onclick="javascript:listForm();">목록</a>
			<a class="btn mod r" href="javascript:void(0);" id="insertCode">저장</a>
		</div>
	</div>

</form>