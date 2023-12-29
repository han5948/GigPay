<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="<c:url value="/resources/cms/js/jquery.cookie.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/jsbn.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rsa.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/prng4.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rng.js" />"></script>

<script type="text/javascript">
//<![CDATA[

	var cookieId = "admin_id";

	$(document).ready(function() {
//  $("body").removeClass("bgn").addClass("bg");

  		$("input[name=admin_id]").focus();

  		// 쿠키체크 start
  		var id = $.cookie(cookieId);

  		if ( id != null && id != "" ) {
    		$("input[name=admin_id]").val(id);
		//    $("#save_id").attr("checked", "checked");
    		$("input[name=admin_pass]").focus();
  		}
  		// 쿠키체크 end
	});

	//아이디 저장
	function idSaveCheck() {
  		var checkStatus = $("#save_id").is(":checked");
	
  		if ( checkStatus ) {
    		$.cookie(cookieId, $("input[name=admin_id]").val(),{ expires:365, path: "/" });
  		} else {
    		$.cookie(cookieId, "",{ expires: -1, path: "/" });
  		}
	
  		return true;
	}

	function submitForm() {
/*
  if ( !idSaveCheck() ) {
    return;
  }
*/
		if( $("input[name=admin_id]").val() == "" ){
			alert("아이디를 입력해주세요.");
			return;
		}
		
		if( $("input[name=admin_pass]").val() == "" ){
			alert("비밀번호를 입력해주세요.");
			return;
		}
			
	  	var rsaPublicKeyModulus = "";
	  	var rsaPublicKeyExponent = "";
	  	try {
	    	rsaPublicKeyModulus = $("#publicKeyModulus").val();
	    	rsaPublicKeyExponent = $("#publicKeyExponent").val();
	  	} catch(e) {
		    alert(e);
	  	}
		
	  	var rsa = new RSAKey();
	  	rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);
	
	  	// 사용자ID와 비밀번호를 RSA로 암호화한다.
	  	var _data = {
	    	admin_id :  rsa.encrypt($("input[name=admin_id]").val()),
	    	admin_pass : rsa.encrypt($("input[name=admin_pass]").val())
	  	};
	
	  	var _url = "<c:url value='/admin/login' />";
	
	  	commonAjax(_url, _data,true, function(data) {
			//successListener
			if ( data.code == "0000" ) {
				if( data.message != null ){
					alert(data.message);
				}
				
				if( data.minimumReservesFlag == "1" ){
					location.href="<c:url value='/admin/reserves' />";	
				}else{
					location.href="<c:url value='/admin/main' />";	
				}
			} else if ( data.code == "8000" ) {
				alert("로그인 인증키가 만료되었습니다.\n새로고침 후 다시 로그인하세요.");
				location.reload();
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
		},
		function(request, status, error) {
	    	//errorListener
			alert(request.status + " 오류가 발생했습니다.");
		},
		function() {
	    	//beforeSendListener
		},
	    function() {
	    	//completeListener
		});
	}

//]]>
</script>

	<div id="idx-wrap" style="background: url(/resources/cms/images/bg.jpg)" >
  		<!-- START BLOCK:CONTENT -->
  		<div id="idx-container" >
	    	<h1 class="textC"><img src="/resources/cms/images/ilgaja_logo.png" width="250" alt="일가자" /></h1>
    		<div id="idx-content">
      			<div id="login-box">
      				<form method="post">
        				<fieldset>
        					<legend>관리자 정보</legend>
        					<ul>
          						<li>
            						<label for="admin_id" class="identity"><span>아이디</span></label>
            						<input type="text" id="admin_id" name="admin_id" style="ime-mode:inactive;" tabindex="1" onKeyDown="if ( event.keyCode == 13 ) submitForm();" />
          						</li>
          						<li>
            						<label for="admin_pass" class="passwd"><span>비밀번호</span></label>
            						<input type="password" id="admin_pass" name="admin_pass" tabindex="2" onKeyDown="if ( event.keyCode == 13 ) submitForm();" />
          						</li>
        					</ul>
        					<p class="button"><a href="#none" onclick="javascript:submitForm();">로그인</a></p>
        				</fieldset>

        				<input type="hidden" id="publicKeyModulus"   value="${publicKeyModulus }" />
        				<input type="hidden" id="publicKeyExponent"  value="${publicKeyExponent }" />
      				</form>
      			</div>
<%--
      <div class="SerBtn mgtM">
        <a href="#"> 아이디 찾기</a> <em>ㅣ</em> <a href="">비밀번호 찾기</a>
        <p class="mgtS">큐셀 임직원들은 아이디/비밀번호를 재검색 바랍니다.</p>
      </div>
--%>
   			</div>
  		</div>
  <!-- END BLOCK:CONTENT -->
  		<hr />

  <!-- START BLOCK:FOOTER -->
		<div id="idx-footer">
    		<address>
          		통신판매 2019-서울구로-1185호 | 전화:070-8666-1634<br>
				Copyright © 2019 주)잡앤파트너 Co., Ltd. All rights reserved<br>
      			Copyright &copy; 2003-2018 Powered by Nemodream Co., Ltd.
    		</address>
  		</div>
  		<!-- END BLOCK:FOOTER -->
	</div>
		