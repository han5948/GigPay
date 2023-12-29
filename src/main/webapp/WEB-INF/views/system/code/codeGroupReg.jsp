<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="<c:url value="/resources/cms/js/showModalDialog.js" />" charset="utf-8"></script>

<script type="text/javascript">

	$(document).ready(function() {
	});

	
	// 등록
	function insertProcess() {
		
		if( $("#groupCode").val().length != 4){
			alert("코드그룹은 4자리 숫자로 입력 하세요." );
			return;
		}
		
		if(!fucOnlyNum($("#groupCode").val())){
			alert("코드그룹은 4자리 숫자로 입력 하세요." );
			return;
		}
		
		if ( fnChkValidForm('frmReg') ) {
			$('#frmReg').ajaxForm({
				url : "/system/code/codeGroupRegProcess",
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
		$("#frm").attr("action", "<c:url value='/system/code/codeGroupList'/>").submit();
	}

</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${codeDTO.paging.pageNo}" />
	
</form>

<form id="frmReg" name="frmReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	
	<div class="content">
		<div>
			<h>코드그룹 등록</h>
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
						<input type="text" name="groupCode" id="groupCode" maxlength="100" class="input00 notEmpty" value="${resultDTO.groupCode}" title="그룹코드" />
					</td>
				</tr>
				<tr>
					<th scope="col">그룹명</th>
					<td>
						<input type="text" name="groupName" id="groupName" maxlength="100" class="input00 notEmpty" value="${resultDTO.groupName}" title="그룹명" />
					</td>
				</tr>
				
			</tbody>
		</table>

		<div class="btn_base" >
			<a class="btn mod l" href="javascript:void(0);" onclick="javascript:listForm();">목록</a>
			<a class="btn mod r" href="javascript:void(0);" onclick="javascript:insertProcess();">저장</a>
		</div>
	</div>

</form>