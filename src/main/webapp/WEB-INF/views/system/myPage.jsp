<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="<c:url value="/resources/cms/js/showModalDialog.js" />" charset="utf-8"></script>

<script type="text/javascript">

	$(document).ready(function() {
	});

	// 등록
	function editProcess() {
		if ( fnChkValidForm('frmReg') ) {

			// 비밀번호 확인
			if ( $("#adminPwd").val() != "" && ($("#adminPwd").val() != $("#adminPwd2").val()) ) {
				toastW("비밀번호가 일치하지 않습니다.");
				$("#adminPwd").focus();
				return false;
			}


			$('#frmReg').ajaxForm({
				        url : "/system/adminEditProcess",
				    success : function(data) {
												if ( data.code == "0000" ) {
													toastOk("수정 완료 되었습니다.");

								   				timerVal = setTimeout(function() {
								   					detailForm();
								  				}, 1000);
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
		}
	}

</script>

<form id="frmReg" method="post" accept-charset="UTF-8">
	<input type="hidden" id="adminSeq" name="adminSeq" value="${resultDTO.adminSeq}" />
	<input type="hidden" id="adminId" name="adminId" value="${resultDTO.adminId}" />

	<div class="content">
		<div class="top">
			<p>홈 > 내 정보 > <strong>내 정보 수정</strong></p>
			<h2>내 정보 수정</h2>
		</div>

		<table class="tb_base wr left">
			<colgroup>
				<col width="20%" />
				<col width="30%" />
				<col width="20%" />
				<col width="*" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="col">사용자명</th>
					<td>${resultDTO.adminName}</td>
					<th scope="col">아이디</th>
					<td>${resultDTO.adminId}</td>
				</tr>
				<tr>
					<th scope="col">비밀번호</th>
					<td>
						<input type="password" name="adminPwd" id="adminPwd" maxlength="100" class="input00" value="" title="비밀번호" />
					</td>
					<th scope="col">비밀번호 확인</th>
					<td>
						<input type="password" name="adminPwd2" id="adminPwd2" maxlength="100" class="input00" value="" title="비밀번호 확인" />
					</td>
				</tr>
				<tr>
					<th scope="col">Email</th>
					<td colspan="3">
						<input type="text" name="adminEmail" id="adminEmail" maxlength="100" class="input09 email" value="${resultDTO.adminEmail}" title="Email" />
					</td>
				</tr>
				<tr>
					<th scope="col">전화번호</th>
					<td>
						<input type="text" name="adminTel" id="adminTel" maxlength="100" class="input00 tel" value="${resultDTO.adminTel}" title="전화번호" />
					</td>
					<th scope="col">휴대전화</th>
					<td>
						<input type="text" name="adminPhone" id="adminPhone" maxlength="100" class="input00 tel" value="${resultDTO.adminPhone}" title="휴대전화" />
					</td>
				</tr>
				<tr>
					<th scope="col">메모</th>
					<td colspan="3">
						<textarea rows="10" cols="132" name="adminMemo" id="adminMemo" class="box" title="메모">${resultDTO.adminMemo}</textarea>
					</td>
				</tr>
				<tr>
					<th scope="col">권한</th>
					<td colspan="3">
						${resultDTO.adminLevel eq '0'? ' 전체 관리자':''}
						${resultDTO.adminLevel eq '1'? ' 관리자':''}
						${resultDTO.adminLevel eq '2'? ' 사용자':''}
					</td>
				</tr>
				<tr>
					<th scope="col">상태</th>
					<td colspan="3">${resultDTO.useYn eq 'Y' ? '사용' : '<font color=\'red\'><b> 삭제 </b></font>'}</td>
				</tr>
			</tbody>
		</table>

		<div class="btn_base" >
			<a class="btn mod r" href="javascript:void(0);" onclick="javascript:editProcess();">저장</a>
		</div>
	</div>

</form>