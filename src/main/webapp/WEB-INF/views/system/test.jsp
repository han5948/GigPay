<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="<c:url value="/resources/cms/js/jquery.cookie.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/jsbn.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rsa.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/prng4.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rng.js" />"></script>
    
<script type="text/javascript">

	var cookieId = "adminId";

 	$(document).ready(function() {
 		$("body").removeClass("bgn").addClass("bg");

		$("input[name=adminId]").focus();

		// 쿠키체크 start
		var id = $.cookie(cookieId);

		if (id != null && id != "") {
			$("input[name=adminId]").val(id);
			$("#save_id").attr("checked", "checked");
			$("input[name=adminPwd]").focus();
		}
		// 쿠키체크 end
	});

	function idSaveCheck() {

		// 아이디 저장
		var checkStatus = $("#save_id").is(":checked");

		if (checkStatus) {
			$.cookie(cookieId, $("input[name=adminId]").val(),{ expires:365, path: "/" });
		} else {
			$.cookie(cookieId, "",{ expires: -1, path: "/" });
		}

		return true;
	} 

	function submitForm() {
		if ( !idSaveCheck() ) {
			return;
		}


	 	var rsaPublicKeyModulus = "";
		var rsaPublicKeyExponent = "";

		try {
			rsaPublicKeyModulus = $("#publicKeyModulus").val();	    
			rsaPublicKeyExponent = $("#publicKeyExponent").val();
		} catch(err) {
			alert(err);
		}


		var rsa = new RSAKey();
		rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);

		// 사용자ID와 비밀번호를 암호화한다.
		var _data = {
				adminId :  rsa.encrypt($("input[name=adminId]").val()),
				adminPwd : rsa.encrypt($("input[name=adminPwd]").val())
		};

		var _url = "<c:url value='/system/loginProc' />";

		commonAjax(_url, _data, function(data) {
				//successListener
				if ( data.code == "0000" ) {
					location.href="<c:url value='/system/main' />";
				} else if ( data.code == "8000" ) {
					alert("로그인 인증키가 만료되었습니다.\n새로고침 후 다시 로그인하세요.");
				} else {
					if ( jQuery.type(data.message) != 'undefined' ) {
						if ( data.message != "" ) {
							alert(data.message);
						} else {
							alert("로그인 정보가 유효하지 않습니다!");
						}
					} else {
						alert("로그인 정보가 유효하지 않습니다!");
					}
				}
			}, function(data) {
				//errorListener
				alert("오류가 발생했습니다.");
			}, function() {
				//beforeSendListener
			}, function() {
				//completeListener
			});
	}
</script>


<body class="bg">
							
<input type="hidden" id="publicKeyModulus"	 value="${publicKeyModulus }" />
<input type="hidden" id="publicKeyExponent"	 value="${publicKeyExponent }" />

<form id="frm" action="" method="post" class="login-form">
	<div class="login">
		<h1>
			<img src="<c:url value='/resources/cms/images/txt_login.gif'/>" alt="LOGIN" />
			<span>관리자 화면 입니다</span>
		</h1>

		<fieldset>
			<legend class="blind"> 로그인 폼</legend>
			<div class="inner">
				<p class="f">
					<label for="mb_id">아이디</label>
					<input type="text" maxlength="25" tabindex="1" id="adminId" name="adminId" style="ime-mode:inactive;" tabindex="1" onKeyDown="if(event.keyCode == 13) submitForm();"/>
				</p>
				<p>
					<label for="mb_password">비밀번호</label>
					<input type="password" maxlength="30" tabindex="2" id="adminPwd" name="adminPwd"  tabindex="2" onKeyDown="if(event.keyCode == 13) submitForm();"/>
				</p>
				<button type="button" onclick="javascript:submitForm();" class="button1"><span class="blind">로그인</span></button>
				<button type="button" onclick="javascript:submitForm();" class="button2"><span class="blind">ㅅㅅㅅ</span></button>
				<p class="id"><input type="checkbox" id="save_id" name="remember" value="1"/> <label for="save_id">아이디 저장</label></p>
			</div>
		</fieldset>
	</div>
</form>

</body>