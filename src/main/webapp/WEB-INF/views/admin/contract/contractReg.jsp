<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
	var selectFlag = false;
	var useCnt = '${useCnt }';

	//등록
	function insertProcess() {
		if($("#contract_default_use_yn").is(":checked") && $("input[name=use_yn]:checked").val() == '0') {
			alert("사용 유무가 미사용인 상태에서는 기본값 설정이 불가능합니다.");
			
			return false;
		}
		
		if($("#contract_name").val() == '') {
			alert("양식명을 입력해주세요.");
			
			return false;
		}
		
		$('#frmReg').ajaxForm({
			url : "/admin/insertContract",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
				if(data.code == "0000") {
					alert("정상적으로 등록 되었습니다.");
					
					listForm($("#category_seq").val());
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
					location.href = "/admin/login";
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
	
	// 리스트
	function listForm(seq) {
		$("#contentFrm").append('<input type="hidden" name="category_seq" value="' + seq + '" />');
		$("#contentFrm").attr("action", "<c:url value='/admin/contractList'/>").submit();
	}
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${contractDTO.paging.pageNo}" />
</form>

<form id="frmReg" name="frmReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<input type="hidden" name="category_seq" id="category_seq" value="${contractDTO.category_seq }" />
	<input type="hidden" name="useCnt" id="useCnt" value="${useCnt }" />
	
    <article>	
		<div class="content mgbM">
			<div class="title ">
				<h3>계약서 양식 등록</h3>
			</div>
	
			<div class="inputUI_simple">
				<table class="bd-form" summary="계약서 양식 등록">
					<colgroup>
						<col width="200px" />
						<col width="*" />
					</colgroup>
					<tbody>
						<tr>
							<th>양식명</th>
							<td class="linelY">
								<input type="text" id="contract_name" name="contract_name" class="wid250">
							</td>
						</tr>
						<tr>
							<th>파일</th>
							<td class="linelY">
								<div class="btn-module filebox floatL">
									<input type="text" id="orgFileName" name="orgFileName" class="upload-name wid250" disabled="disabled"> 
									<label for="contractFile">파일 첨부</label> 
									<input type="file" id="contractFile" name="contractFile" class="upload-hidden">
								</div>
							</td>
						</tr>
						<tr>
							<th>기본값 사용 유무</th>
							<td class="linelY">
								<input type="checkbox" id="contract_default_use_yn" name="contract_default_use_yn" class="inputJo" value="1"/><label for="contract_default_use_yn" class="checkTxt">사용</label>
							</td>
						</tr>
						<tr>
							<th scope="col">사용 유무</th>
							<td class="linelY">
								<input type="radio" name="use_yn" value="0" checked="checked" />미사용
								<input type="radio" name="use_yn" value="1" />사용
							</td>
						</tr>
					</tbody>
				</table>
			</div>
	
			<div class="btn-module mgtL">
				<a class="btnStyle04" href="javascript:void(0);" onclick="javascript:listForm('${contractDTO.category_seq }');">목록</a>
			</div>
			<div class="btn-module mgtL fR">
				<a class="btnStyle04" href="javascript:void(0);" onclick="javascript:insertProcess();">저장</a>
			</div>
		</div>
	</article>
</form>