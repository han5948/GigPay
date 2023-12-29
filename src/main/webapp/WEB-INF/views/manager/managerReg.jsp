<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="<c:url value="/resources/cms/js/jquery.cookie.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/jsbn.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rsa.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/prng4.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rng.js" />"></script>

<script>


$(document).ready(function() {

	$("#all_terms").click(function(){
		
		//만약 전체 선택 체크박스가 체크된상태일경우 
		if($("#all_terms").prop("checked")) { 
			//해당화면에 전체 checkbox들을 체크해준다 
			$("input[type=checkbox]").prop("checked",true); 
		// 전체선택 체크박스가 해제된 경우 
		} else { 
			//해당화면에 모든 checkbox들의 체크를해제시킨다. 
			$("input[type=checkbox]").prop("checked",false); 
		}

		
	});
	
	
	
});


function post_check()
{

  var ObjUserPassword 	= $('#pwd').val();
  var ObjUserPasswordRe = $('#pwd2').val();
 

  if($("input:checkbox[id='personal_terms']").is(":checked") == false){
	     alert("개인정보취급방침 을 동의 하셔야 합니다.")
		 $('#personal_terms').focus()
		 return false;
  }
  
  if($("input:checkbox[id='service_terms']").is(":checked") == false){
	     alert("서비스 이용약관 을 동의 하셔야 합니다.")
		 $('#service_terms').focus()
		 return false;
}


  
  if($('#manager_name').val() =="")
	{
	 alert("담당자 이름(직함포함)를 입력해주세요.")
	 $('#manager_name').focus()
	 return false;
	}
  

  
  if($('#pwd').val() =="")
	{
	 alert("비밀번호를 입력해주세요.")
	 $('#pwd').focus()
	 return false;
	}
 if($('#pwd2').val()=="")
	{
	 alert("비밀번호를 재입력해주세요.")
	 $('#pwd2').focus()
	 return false;
	}


  if($('#pwd').val() != $('#pwd2').val())
 {
  alert("입력하신 비밀번호와 비밀번호확인이 일치하지 않습니다");
  return false;
  }
 
  if($('#pwd').val().length<4 || $('#pwd').val().length>16) {
    alert("비밀번호는 영문,숫자를 사용하여 4~16자까지, 영문은 대소문자를 구분합니다.");
    return false;
  }
 
  
  //영숫자 조합책크
  if ((new RegExp(/[^a-z|^0-9]/gi)).test($('#pwd').val())) {
      alert("패스워드는 는 영숫자 조합만 사용하세요"); 
      return false;
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
			manager_name :$('#manager_name').val(),
		  	manager_phone : rsa.encrypt( $('#manager_phone').val()),
    	  	manager_pass : 	rsa.encrypt( $('#pwd').val())
  };
  

  var _url = "<c:url value='/manager/managerProc' />";
  

  loginAjax(_url, _data,false, function(data) {
                 //successListener
                 if ( data.code == "0000" ) {
                 	
                 	if($wbr.browser == "Android"){
             	  		App.loginInfo($("#manager_phone").val(),$("#pwd").val(),'M', true );
             		}
                 	
                 	if($wbr.browser == "IOS"){
             	  		window.location="jscall://loginInfo?manager_phone="+$("#manager_phone").val()+"&manager_pass="+$("#manager_pass").val() +"&loginType=M&auto=true";
             		}
                 	
                 	setTimeout(function() {
                 		location.href="<c:url value='/manager/main' />";
                 	}, 500);
             		
                   
                 } else if ( data.code == "8000" ) {
                	 $(".wrap-loading").hide();
                   	alert("로그인 인증키가 만료되었습니다.\n새로고침 후 다시 로그인하세요.");
                   	location.reload();
                 } else {
                	 $(".wrap-loading").hide();
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
               function(data) {
                 //errorListener
                 $(".wrap-loading").hide();
                 alert("오류가 발생했습니다.");
               },
               function() {
                 //beforeSendListener
               },
               function() {
                 //completeListener
               }
  );


  
}


</script>


<div id="container-wrap"  class="scontainer" >
	<div id="contents">
	<!-- page star // -->
	<div class="key-wrap">
		<p class="mtit">현장매니저<i class="block2">아이디: ${manager_phone } </i></p>
		<ul class="list">
			<li><input type="text" value="" placeholder="담당자 이름(직함포함)" maxlength="16" class="select-gray manager_name"  id="manager_name" name="manager_name"/></li>
		</ul>
		
		<p class="stit">비밀번호 만들기<i class="block">4자 이상, 영문, 숫자로 입력해주세요. </i></p>
		<ul class="list">
			<li><input type="text" value=""  placeholder="비밀번호 입력" maxlength="16" class="select-gray pswd"  id="pwd" name="pwd" /></li>
			<li><input type="text" value="" placeholder="비밀번호 재입력" maxlength="16" class="select-gray pswd2"   id="pwd2" name="pwd2" /></li>
			<input type="hidden" id="manager_seq" name="manager_seq"   value="${manager_seq }" />
			<input type="hidden" id="manager_phone" name="manager_phone"   value="${manager_phone }" />
			<input type="hidden" id="publicKeyModulus"   value="${publicKeyModulus }" />
        	<input type="hidden" id="publicKeyExponent"  value="${publicKeyExponent }" />
		</ul>
		
		<p class="agreeCheck">
			<label>*약관동의</label>
			<label><input type="checkbox" name="all_terms" id="all_terms" value="1"> 모두선택</label>
			<label><input type="checkbox" name="personal_terms" id="personal_terms" value="1">  <span class="red">(필수)</span> 개인정보취급방침 <a href="/manager/personalTerms?manager_phone=${manager_phone }" ><span class="conView"> 보기</span></a></label>
			<label><input type="checkbox" name="service_terms" id="service_terms" value="1"> <span class="red">(필수)</span> 서비스 이용약관&nbsp;&nbsp;&nbsp;<a href="/manager/serviceTerms?manager_phone=${manager_phone }" ><span class="conView">보기</span></a></label>
		</p>		
		
		<div class="sgap"></div>
		
		<button class="login-btn" onclick="javascript:post_check()"><span>로그인</span></button>
		
	</div>
	<!-- page end // -->
	</div>
</div>
<!-- e: #container-wrap //-->