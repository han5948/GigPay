<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="<c:url value="/resources/common/js/jquery.cookie.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/jsbn.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rsa.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/prng4.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rng.js" />"></script>
    
<script type="text/javascript">

	var cookieId = "admin_id";

 	$(document).ready(function() {
 		
		$("input[name=admin_id]").focus();

		// 쿠키체크 start
		var id = $.cookie(cookieId);

		if (id != null && id != "") {
			$("input[name=admin_id]").val(id);
			$("#save_id").attr("checked", "checked");
			$("input[name=admin_pass]").focus();
		}
		// 쿠키체크 end
	});

	function idSaveCheck() {

		// 아이디 저장
		var checkStatus = $("#save_id").is(":checked");

		if (checkStatus) {
			$.cookie(cookieId, $("input[name=admin_id]").val(),{ expires:365, path: "/" });
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
				admin_id :  rsa.encrypt($("#admin_id").val()),
				admin_pass : rsa.encrypt($("#admin_pass").val())
		};

		var _url = "<c:url value='/system/loginProc' />";

		commonAjax(_url, _data,true, function(data) {

				//successListener
				if ( data.code == "0000" ) {
					alert(1);
					location.href="<c:url value='/system/main' />";
				} else {
					alert(data.message);
					location.href="<c:url value='/system' />";
				}
			}, function(data) {
				//errorListener
				alert("오류가 발생했습니다.");
				if(data.status == "901"){
					location.href = "/system";
				}
			}, function() {
				//beforeSendListener
			}, function() {
				//completeListener
			});
	}
</script>


<body class="bg">
							

<form id="frm" action="" method="post" class="login-form">
				<input type="hidden" id="publicKeyModulus"	 value="${publicKeyModulus }" />
				<input type="hidden" id="publicKeyExponent"	 value="${publicKeyExponent }" />

				아이디<input type="text" maxlength="25" tabindex="1" id="admin_id" name="admin_id" style="ime-mode:inactive;" tabindex="1" />
				<br/>
				비밀번호<input type="password" maxlength="30" tabindex="2" id="admin_pass" name="admin_pass"  tabindex="2" onKeyDown="if(event.keyCode == 13) submitForm();"/>
				<br>
				<button type="button" onclick="javascript:submitForm();">로그인</button>
				<br>
				<input type="checkbox" id="save_id" name="remember" value="1"/>아이디 저장
			
	
</form>

</body>