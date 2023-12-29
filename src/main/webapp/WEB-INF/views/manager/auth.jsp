<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script>
	
	$(document).ready(function() {
		$('#loading').find('.bar > span').stop().animate({'width':'100%'},3000);
		 
		$("input[name=manager_phone]").focus();
		  
	});

	function submitAuth() {
	
		var smsAuth = 0;
			
		  if ( !phoneCehck( $("#manager_phone").val() )) {
		    return;
		  }
		  var _data = {
				manager_phone : $("#manager_phone").val()
		  };
	
		  var _url = "<c:url value='/manager/getAuthNum' />";
		  commonAjax(_url, _data, false, function(data) {
                  //successListener
                  if ( data.code == "0000" ) {
                    	$("#auth_num").val(data.smsAuth);
                    	smsAuth = 1;
                    	
                             	  
                  }else{
                        alert(data.message);
                  }
                },
                function(data) {
                  //errorListener
                  alert("오류가 발생했습니다.");
                },
                function() {
                  //beforeSendListener
                },
                function() {
                  //completeListener
                }
		  );
		  
		  if( smsAuth == 1){
			  $('.certify.layer-pop').show();
			  ovShow();
			  
			}
	 
	}
	
	function submitForm() {
		if($("#authNum").val() == ""){
			alert("인증번호를 입력 하세요.");
			return;
		}
		
		if($("#authNum").val() != $("#auth_num").val()){
			alert("인증번호가 일치 하지 않습니다.");
			return;
		}
		
		
		$('#frm') 
		.attr('action','/manager/password') 
		.submit();
		
	}

	
</script>

<!-- s: #container-wrap //-->
<div id="container-wrap"  class="scontainer" >
	<div id="contents">
	<!-- page star // -->
	<div class="jobs-wrap">
		<p class="stit">구인자 휴대폰 인증하기<i class="block">입력란에 숫자만 입력해주세요. </i></p>
		<ul class="list">
			<li>
				<form action="/manager/password" id="frm" method="post">
				<input type="number" inputmode="numeric"  placeholder="휴대폰번호 입력" maxlength="11" oninput="maxLengthCheck(this)" class="select-gray" name="manager_phone" id="manager_phone" maxlength="13" />
				<input type="hidden" id="auth_num" name="auth_num"   value="" />
				</form>
				<a href="#n" onclick="submitAuth();" class="check-btn sms">인증번호받기</a>
			</li>
			<li>
				<input type="number" inputmode="numeric" id="authNum" oninput="maxLengthCheck(this)" placeholder="인증번호 입력" maxlength="6" class="select-gray" />
			</li>
			
		</ul>
		<div class="sgap"></div>
		<button class="certify-btn" onclick="submitForm();"><span>인증</span></button>
		<div class="sgap"></div>
		<p><a href="https://ilgaja.com">※근로자(구직자)는 <span  style="color: #f90e34;text-decoration:underline;">여기를</span> 클릭하세요.</a></p>
	</div>
	<!-- page end // -->
	</div>
</div>


		<!-- s : 문자전송 layer //////////////////-->
		<div class="certify layer-pop">
			<div class="is-con">
				<p class="stxt">인증번호가 입력하신 휴대폰으로 발송되었습니다.</p>
				<a href="#n" class="ok-btn"><span>확인</span></a>
			</div>
		</div>
		<!-- e: 문자전송 layer //////////////////-->
<!-- e: #container-wrap //-->