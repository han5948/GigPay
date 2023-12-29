<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
	//등록
	function insertProcess() {
		if($("#category_name").val() == '') {
			alert("카테고리 명을 입력해주세요.");
			
			return false;	
		}
		
		var eng = /^[a-zA-Z]*$/;
		
		if(!eng.test($("#category_code").val())) {
			alert("영문 3글자만 입력가능합니다.");
			
			return false;
		}
		
		if($("#category_code").val() == '') {
			alert("문서명 코드를 제대로 입력해주세요.");
			
			return false;
		}
		
		$('#frmReg').ajaxForm({
			url : "/admin/insertCategory",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
				if(data.code == "0000") {
					alert("정상적으로 등록 되었습니다.");
					
					listForm();
				}else if(data.code == "9999") {
					alert(data.message);
				}else if(data.code == '9998') {
					alert(data.message);
				}else {
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
	function listForm() {
		$("#contentFrm").attr("action", "<c:url value='/admin/categoryList'/>").submit();
	}
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${categoryDTO.paging.pageNo}" />
</form>

<form id="frmReg" name="frmReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
    <article>	
		<div class="content mgbM">
			<div class="title ">
				<h3>계약서 카테고리 등록</h3>
			</div>
	
			<table class="bd-form" summary="계약서 카테고리 등록">
				<colgroup>
					<col width="200px" />
					<col width="*" />
				</colgroup>
				<tbody>
					<tr>
						<th>카테고리 명</th>
						<td class="linelY">
							<input type="text" name="category_name" id="category_name" class="inp-field wid700 notEmpty" value="" title="카테고리 명" />
						</td>
					</tr>
					<tr>
						<th>문서명 코드</th>
						<td class="linelY">
							<input type="text" name="category_code" id="category_code" class="inp-field wid100 notEmpty" placeholder="영문 3글자만 입력해주세요." maxlength="3">
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
	
			<div class="btn-module mgtL">
				<a class="btnStyle04" href="javascript:void(0);" onclick="javascript:listForm();">목록</a>
			</div>
			<div class="btn-module mgtL fR">
				<a class="btnStyle04" href="javascript:void(0);" onclick="javascript:insertProcess();">저장</a>
			</div>
		</div>
	</article>
</form>