<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<link type="text/css" rel="stylesheet" media="all" href="<c:url value='/resources/manager/css/login.css' />" charset="utf-8"></link>

	
<script type="text/javascript" src="<c:url value="/resources/cms/js/jquery.cookie.js" />" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/jsbn.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rsa.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/prng4.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/common/js/rsa/rng.js" />"></script>	

<script>
var cookieId = "login_id";
var cookieType = "login_type";


	$(document).ready(function() {
		$('#loading').find('.bar > span').stop().animate({'width':'100%'},3000);



		  $("input[name=login_id]").focus();

		  // 쿠키체크 start
		  var id = $.cookie(cookieId);
		  var loginType = $.cookie(cookieType);

		  if ( loginType != null && loginType != "" ) {
			  $('input[name="login_type"]:radio[value="'+ loginType +'"]').prop('checked',true);
		  }
		  
		  if ( id != null && id != "" ) {
		    $("input[name=login_id]").val(id);
//		    $("#save_id").attr("checked", "checked");
		    $("input[name=login_pass]").focus();
		  }
		  
		  // 쿠키체크 end

		  $('input[name="login_type"]:radio').change(function() {
			  var val =  $(this).val() ;
			  if(val == 'M'){
				  $("#login_id").attr("placeholder", "핸드폰 번호를 입력 하세요.");
				  $(".login-type").show();
			  }else{
				  $("#login_id").attr("placeholder", "사업자 번호를 입력 하세요."); 
				  $(".login-type").hide();
			  }
			});
		  
		  $('#login_pass').keydown(function(e) {
		        if (e.keyCode == 13) {
		        	submitForm() ;
		        }
		    });
		  
	});

	function appToSetLogin(loginId, loginPass, loginType) {		
		
		$("#login_id").val(loginId);
		$("#login_pass").val(loginPass);
		$('input[name="login_type"]:radio[value="'+ loginType +'"]').prop('checked',true);
		
		submitForm();
	}


	//아이디 저장
	function idSaveCheck() {
	  var checkStatus = $("#save_id").is(":checked");

	  if ( checkStatus ) {
	    $.cookie(cookieId, $("input[name=login_id]").val(),{ expires:365, path: "/" });
	    $.cookie(cookieType,$("input:radio[name=login_type]:checked").val(),{ expires:365, path: "/" });
	  } else {
	    $.cookie(cookieId, "",{ expires: -1, path: "/" });
	    $.cookie(cookieType, "",{ expires: -1, path: "/" });
	  }

	  return true;
	}
	
	
	function checkEnter(){
		if(f.keyCode == 13){
			submitForm();
		}
	}

	function submitForm() {			
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
			login_type :$("input:radio[name=login_type]:checked").val(),
			login_id :  rsa.encrypt($("input[name=login_id]").val()),
	    	login_pass  :  rsa.encrypt($("input[name=login_pass]").val())
	  };
	  
	  var _url = "<c:url value='/manager/loginProc' />";


	  loginAjax(_url, _data,true, function(data) {
	                            //successListener
	                            if ( data.code == "0000" ) {
	                            	var loginId 		= $("#login_id").val();
                            		var loginPass 	= $("#login_pass").val();
                            		var loginType	= $("input:radio[name=login_type]:checked").val();
                            		var isAuto			= $('input:checkbox[id="save-id"]').is(":checked"); 
                            		
	                            	if($wbr.browser == "Android"){
	                            		App.loginInfo( loginId, loginPass, loginType, isAuto);
	                        		}
	                        		
	                        		if($wbr.browser == "IOS"){
	                        			window.location="jscall://loginInfo?loginId="+loginId+"&loginPass="+ loginPass +"&loginType="+ loginType +"&isAuto="+isAuto
	                        		}
	                        		
	                        		setTimeout(goMain, 500);

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
	                                	if($wbr.browser == "Android"){
	                            	  		App.logout();
	                            		}else{
	                                  		alert("로그인 정보가 유효하지 않습니다!");
	                            		}
	                                }
	                              } else {
	                            	  if($wbr.browser == "Android"){
	                            	  		App.logout();
	                            		}else{
	                                  		alert("로그인 정보가 유효하지 않습니다!");
	                            		}
	                              }
	                            }
	                          },
	                          function(data) {
	                            //errorListener
	                            $(".wrap-loading").hide();
	                            alert("오류가 발생했습니다.");
	                            location.href= "/manager/login";      
	                          },
	                          function() {
	                            //beforeSendListener
	                        	 
	                          },
	                          function() {
	                            //completeListener
	                          }
	  );
	}

	function goMain(){
		location.href="/manager/main";
	}
	
</script>


<body id="login" >
<div id="skipNavi">
	<h1 class="blind">일가자 스킵네비게이션</h1>
	<ul>
		<li><a href="#contents" class="skipLink">본문바로가기</a></li>
		<li><a href="#topmenu" class="skipLink">주메뉴바로가기</a></li>
	</ul>
</div>
<!-- s: #doc //-->
<div id="doc" class="isPage" >	
	<!-- s: .doc-pg //-->
	<div id="pg-code" class="doc-pg">
		<!-- s : header-wrap -->
		<header id="header-wrap">
			<div id="header" class="header">
				<h1 id="logo"><img src="/resources/manager/image/logo.png" alt="일가자"></h1>
				<p class="type">구인자용</p>
			</div>
		</header>
		<!-- e : header-wrap -->
		<!-- s: #container-wrap //-->
		<div id="container-wrap"  class="mcontainer" >
			<div id="contents">
			<!-- page star // -->
			   <div class="login-wrap">
			   			<p class="login-check">
							<input type="radio" name="login_type"  id="login-check" value="M" checked="checked"><label for="login-check">현장매니저</label>&nbsp;
							<input type="radio" name="login_type"  id="login-check2" value="E"><label for="login-check2">본사매니저</label>
			   			</p>
					<ul class="list">
						<li><input type="number" inputmode="numeric" type="number" id="login_id" name="login_id" class="select-gray"  maxlength="11" oninput="maxLengthCheck(this)" placeholder="휴대폰번호를 입력하세요(숫자만입력)"/></li>
						<li><input type="password" id="login_pass" name="login_pass" class="select-gray" maxlength="16" placeholder="비밀번호를 입력하세요" /></li>
					</ul>
					<div class="login-set">
						<input type="hidden" id="publicKeyModulus"   value="${publicKeyModulus }" />
        				<input type="hidden" id="publicKeyExponent"  value="${publicKeyExponent }" />
        				
        				<div class="login-save"><input type="checkbox" id="save-id" checked/><label for="save-id">자동로그인</label></div>
						<button class="login-btn" onclick="javascript:submitForm();" ><span>로그인</span></button>
						<div class="login-type">
							<ul>
								<li style="width:100%"><a href="/manager/auth" class="active"><span>회원가입</span></a></li>
								<!-- <li><a href="javascript:telCall('02-6380-3108');" class=""><span>구인상담</span></a></li> -->
								
							</ul>
				    	</div>
					</div>
					<div style="text-align: center;color:#FFFFFF;">
						<a href="/manager/auth"  style="color:#FFFFFF">비밀번호찾기</a>
					</div>
					<div class="gap35"></div>
				    
			   </div>
			<!-- page end // -->
			</div>
			
		<footer class="footer-wrap" id="footer-wrap">
			<div id="footer">
				통신판매 2019-서울구로-1185호 | 전화:070-8666-1634<br/> 
		      	Copyright &copy; 2011-2018 주)잡앤파트너 Co., Ltd. All rights reserved<br/>
		      	Copyright &copy; 2003-2018 Powered by Nemodream Co., Ltd.
      
			</div>
		</footer>
		</div>
		<!-- e: #container-wrap //-->

		
	</div>
	<!-- e: .doc-pg //-->
</div>
</body>
