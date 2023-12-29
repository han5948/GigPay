<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="description" content="ilgaja, 일가자">
<meta name="author" content="NEMODREAM Co., Ltd.">
<link rel="icon"
	href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico"
	mce_href="/resources/cms/images/ilgaja_logo_ico_WUU_icon.ico"
	type="image/x-icon">

<title>${htmlHeader}-일가자</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/jqueryUi/jquery-ui.css' />"
	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/grid/css/font-awesome.min.css' />"
	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/grid/css/ui.jqgrid.min.css' />"
	media="all" charset="utf-8"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/cms/select2/css/select2.min.css' />"
	media="all" charset="utf-8"></link>

	


<script type="text/javascript"
	src="<c:url value='/resources/cms/grid/js/jquery-1.11.0.min.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/jqueryUi/jquery-ui.min.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/jqueryUi/select.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/select2/js/select2.full.min.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/grid/js/jquery.jqgrid.min.js' />"
	charset="utf-8"></script>
<%-- <script type="text/javascript"	src="<c:url value='/resources/cms/grid/js/i18n/grid.locale-kr.js' />	charset="utf-8"></script> --%>

<script type="text/javascript"
	src="<c:url value='/resources/cms/js/jquery.form.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/js/xl-toast.js' />" charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/cms/js/common.ilgaja.js?ver=1.0' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value="/resources/cms/js/newPaging.js" />" charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/validate.js' />"
	charset="utf-8"></script>
<script type="text/javascript"
	src="<c:url value="/resources/common/js/common.js" />" charset="utf-8"></script>

<!-- dashboard  -->
<!-- Bootstrap Styles-->
<link href="/resources/bootstrap/assets/css/bootstrap.css" rel="stylesheet" />
<!-- FontAwesome Styles-->
<link href="/resources/bootstrap/assets/css/font-awesome.css" rel="stylesheet" />
<!-- Morris Chart Styles-->
<link href="/resources/bootstrap/assets/js/morris/morris-0.4.3.min.css"	rel="stylesheet" />
<!-- Custom Styles-->
<!-- Google Fonts-->
<link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />

<link href="/resources/bootstrap/assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
<link href="/resources/bootstrap/assets/css/modal.css" rel="stylesheet" />


	
<link href="/resources/common/css/dashboard.css" rel="stylesheet" />
	
	
<!-- <script src="https://code.jquery.com/jquery-2.2.1.min.js"></script> -->
<script type="text/javascript" src="<c:url value='/resources/cms/js/common.ilgaja.js?ver=1.0' />"	charset="utf-8"></script>
<script src="/resources/bootstrap/assets/js/bootstrap-input-spinner.js"></script>
</head>
<script>
	var regexp = /^[0-9]*$/
	var certNum = '';
	
	$(document).ready(function() {
	});
	
	function fn_certNumVali() {
		if($("#certNumber").val() != certNum || $("#certOk").val() == '0') {
			alert("인증번호를 다시 입력해주세요.");
			
			return false;
		}else {
			var _data = {
				company_phone : $("#company_phone").val(),
				company_seq : $("#company_seq").val(),
				admin_phone : $("#company_phone").val()
			};
			
			var _url = '';
			
			if($("#page_flag").val() == 'C') {
				_url = '/admin/setCompanyInfo';
				_data = {
					company_phone : $("#company_phone").val(),
					company_seq : $("#company_seq").val()
				};
			}else {
				_url = '/admin/setAdminInfo';
				_data = {
					admin_phone : $("#company_phone").val(),
					admin_seq : $("#admin_seq").val()
				};
			}
			
			$.ajax({
				type : "POST",
				url : _url,
				data : _data,
				dataType : 'JSON',
				success : function(data) {
						window.opener.$("#list").trigger("reloadGrid");
						
						alert("등록이 완료되었습니다.");
						
						fn_close();
				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader("AJAX", true);
				},
				error : function(e) {
					if(data.status == "901") {
						location.href = "/admin/login";
					}else {
						alert("오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.");
					}
				}
			});
		}
	}
	
	function fn_close() {
		window.close();
	}
	
	function fn_certNum() {
		var phoneValid = validPhone($("#company_phone").val(), null, null);
		
		if($("#company_phone").val() == '') {
			alert("입력하신 회신번호는 전기통신관리 세칙에 맞지 않는 번호입니다. \n전기통신관리 세칙에 맞는 회신번호로 입력해 주시기 바랍니다.");
			
			return false;
		}else if(!phoneValid[0]) {
			alert("입력하신 회신번호는 전기통신관리 세칙에 맞지 않는 번호입니다. \n전기통신관리 세칙에 맞는 회신번호로 입력해 주시기 바랍니다.");
			
			return false;
		}
		
		var _data = {
			company_phone : $("#company_phone").val()
		};
		
		$.ajax({
			type : "POST",
			url : "/admin/certNum",
			dataType : "json",
			data : _data,
			success : function(data) {
				if(data.code == "0000") {
					alert("인증번호가 전송되었습니다.");
					
					$("#certOk").val("1");
					
					certNum = data.certNum;
				}
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error : function(e) {
				if(data.status == "901") {
					location.href = "/admin/login";
				}else {
					alert("오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.");
				}
			}
		});	
	}
	
	$(function() {
	});
	
	
	
</script>
<body>
	<input type="hidden" id="company_seq" name="company_seq" value="${company_seq }" />
	<input type="hidden" id="admin_seq" name="admin_seq" value="${admin_seq }" />
	<div class="content mgtS">
		<div class="title">
			<h3 style="margin-left: 5%;">SMS 발신번호 등록</h3>
		</div>
	</div>
	<div>
		<table class="table table-hover table-bordered" style="float: left; width:70%; margin-left: 5%;">
			<tr>
				<td>폰번호</td>
				<td>
					<input type="text" id="company_phone" placeholder="숫자만 입력해주세요.">
					<button type="button" class="btn" onclick="fn_certNum()">인증번호 전송</button>
				</td>
			</tr>
			<tr>
				<td>인증번호</td>
				<td>
					<input type="text" id="certNumber" maxlength="4">
				</td>
			</tr>
		</table>
	</div>
	<input type="hidden" id="certOk" value="0" />
	<input type="hidden" id="page_flag" value="${page_flag }" />
	<footer class="page-footer font-small blue pt-4">
		<div class="container-fluid text-center text-md-left">
      	</div>
      	<div class="footer-copyright text-center py-3">
	        <button type="button" class="btn btn-outline-primary"  onclick="fn_certNumVali()">등록</button>
		   	<button type="button" class="btn btn-outline-primary" onclick="fn_close()">닫기</button>
    	</div>
    </footer>
</body>