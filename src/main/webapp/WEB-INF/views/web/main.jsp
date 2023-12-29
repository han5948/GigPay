<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"  %>


<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-131755850-1"></script>
<script>
  	window.dataLayer = window.dataLayer || [];
  	function gtag(){
  		dataLayer.push(arguments);
  	}
  
  	gtag('js', new Date());
  	gtag('config', 'UA-131755850-1');
  
  	function closeWorkOffer(){
	  	$("html").css({"overflow-y":"scroll"});
		$(".bgPopup").fadeOut(100);
		$(".popup").css({"display":"none"});
		$(".jobOffer").hide();
  	}
  
  	function closeJobOffer(){
	  	$("html").css({"overflow-y":"scroll"});
		$(".bgPopup").fadeOut(100);
		$(".popup").css({"display":"none"});
		$(".workOffer").hide();
	}
  /* 
  $(".close").click(function(){
		$("html").css({"overflow-y":"scroll"});
		$(".bgPopup").fadeOut(500);
		$(".popup").css({"display":"none"});
		
		$(".jobOffer,.workOffer").hide();
	}); */

	//worker 등록...
 	function fucWorkerProcess() {
		if (!fnChkValidForm("frmWorkerReg")) {
			return;
		}

		if (!$("input:checkbox[id='chkWorkerRule']").is(":checked")) {
			toastW("개인정보 수집에 동의 해야 합니다.");
			return;
		}

		$("#worker_jumin").val($("#worker_jumin_before").val() + '' + $("#worker_jumin_after").val());
		
		$('#frmWorkerReg').ajaxForm({
			url : "/web/regWorkerProcess",
			enctype : "multipart/form-data", // 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : 'application/x-www-form-urlencoded; charset=utf-8', //캐릭터셋을 euc-kr로 
			success : function(data) {
				if (data.code == "0000") {
					alert("정상적으로 등록 되었습니다..");
					location.reload();
					//closeJobOffer();

				} else {
					alert(data.message);
				}
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("AJAX", true);
				$(".wrap-loading").show();
			},
			error : function(e) {
				toastFail("등록 실패");
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		});

		$('#frmWorkerReg').submit();
	}
</script>
	<!-- 팝업배경 -->
	<div class="bgPopup"></div>
	<div class="popup">
		<!-- 일자리구함 팝업-->
		<div class="workOffer" style="display:block;">
			<div class="w_bgOffer"></div>
			<div class="woHeader clear">
				<p class="woHeadTxt"><strong>일자리요청</strong>(근로자 신청)</p>
				<p class="woHeadImg"><img src="/resources/web/images/subClose.png" alt="" class="close" style="cursor:pointer" onClick="closeJobOffer();"></p>
			</div>

			
			
			<div class="joFooter">
				<div class="appInfo">
					
					<p class="appInfoTxt"><img src="/resources/web/images/btnImg2.png" alt=""></p>
						
						
					<p class="appInfoTxt">아래 앱 설치 후 회원가입하고 대기하시면 상담할 지점에서 연락이 갑니다.</p>
					
					
					
					
					<div class="appInfoIcon clear">

						<p class="aInfoIconApp">
							<span>
								<img src="/resources/web/images/appIcon02.png" alt=""> 
								<span>
								<a href="https://play.google.com/store/apps/details?id=jnpartner.ilgaja.worker" target="_blank">안드로이드</a>
								</span>
					
								&nbsp;
								<img src="/resources/web/images/appIcon01.png" alt="">
								<span>
								<a href="https://apps.apple.com/kr/app/jellidaesi/id1530153827?mt=8" target="_blank">아이폰</a>
								</span>
							</span>
						</p>
					</div>
					<div class="jobFoot">
						<!-- <img src="/resources/web/images/callIcon.png" alt="">
						구직상담 : <strong>02.461.0432</strong> -->
					</div>
				</div>
			</div>
			
			<div class="woContents clear">
					<div class="w_offerInfo02">
						<p class="comNotie">민간 유료 직업소개소의 <span>외국인 </span>고용알선은<br class="pcOn"><span>불법</span>으로 외국인은 구직등록이 <span>불가</span>합니다.</p>
					</div>
			</div>
			
		</div>
	
	</div>
	
	<!-- header -->
	<a href=""></a>
	<div class="header">
		<div class="headIn">
			<h1><img src="/resources/web/images/logo.png" alt="일가자 인력 로고" width="220px"></h1>
			<p><span>일반인부 조공 기공</span> 100% 내국인 인력</p>
		</div>
	</div>
	
	<!-- main -->
		<div class="main">
			<div class="mainIn">

				<!-- <h2>24시간 실시간 앱 오더</h2> -->
				<h1 style="text-align: center;font-size:20px">상담원과 직접 통화</h1>
				
				<div class="mainBtn" style="text-align:center">
					<a href="tel:1668-1903"><p class="mBtn03" style="text-align:left" >고객센터 : <strong>1668-1903</strong></p></a>
				</div>
				
				<!-- <h2><a href="tel:1668-1903">1668-1903</a></h2> -->
				<!-- <ul>
					<li><span class="mainColor1">01</span><span class="mainR">100% "<strong><span style="color:#ff7d1d;">내국인 인력</span></strong>" 근거리 우선 배정</span></li>
					<li><span class="mainColor1">02</span><span class="mainR">100% "<strong><span style="color:#ff7d1d;">상담 완료한</span></strong>" 검증된 인력 알선</span></li>
					<li><span class="mainColor1">03</span><span class="mainR">100% "<strong><span style="color:#ff7d1d;">안전교육,나이,혈압</span></strong>" 맞춤 출역</span></li>
				</ul> -->
				<div class="mainBtn">
				
					<h1 style="text-align: center;font-size:20px;margin-bottom:10px">홈페이지에서 바로 인력 요청</h1>
					<a href="https://ilgaja.com" target="_blank"><p class="mBtn01"><strong>인력요청</strong> (구인자 클릭)</p></a>
					<a href="#none"><p class="mBtn02"><strong>일자리요청</strong> (근로자 클릭)</p></a>
				</div>
			</div>
		</div>

			<!-- 노무자료 받기-->
		<div class="con04">
			<div class="cIn04 clear">
				<p class="conImg"><img src="/resources/web/images/conImage04.png" alt=""></p>
				<div class="cn4_list">
					<ul>
						<li><span class="icon"><img src="/resources/web/images/conIcon01.png" alt=""></span> <span class="item"><a href="https://apps.apple.com/us/app/일가자인력-구인자용/id1576809047" target="_blank">아이폰</a></span></li>
						<li><span class="icon"><img src="/resources/web/images/conIcon02.png" alt=""></span> <span class="item"><a href="https://play.google.com/store/apps/details?id=jnpartner.ilgaja.manager" target="_blank">안드로이드</a></span></li>
						<li><span class="icon"><img src="/resources/web/images/conIcon03.png" alt=""></span> <span class="item"><a href="https://ilgaja.com/manager/main" target="_blank">PC 웹</a></span></li>
					</ul>
				</div>
				<div class="conTxt">
					<h3>앱깔고 인력 요청</h3>
					<div>
					
					
						<p><strong>인력 업계의 <span class="cn4_color1">“배달의 민족”</span>, 이젠 인력요청도 앱으로 하세요.</strong></p> 
						<p><span class="imgL"><img src="/resources/web/images/conIcon04.png" alt=""></span><span class="txtR">앱설치 후 휴대폰번호로 회원가입</span></p>
						<p><span class="imgL"><img src="/resources/web/images/conIcon04.png" alt=""></span><span class="txtR">앱오더로 현장등록 후 간단히 인력요청</span></p>
						<p><span class="imgL"><img src="/resources/web/images/conIcon04.png" alt=""></span><span class="txtR">다음엔 마음에 들었던 작업자를 바로 요청 가능</span></p>
						<p><span class="imgL"><img src="/resources/web/images/conIcon04.png" alt=""></span><span class="txtR">작업자 정보(연락처,계좌번호)와 노임대장도 다운로드</span></p>
					</div>
					<p class="cn4_color2">본사매니저 계정 만들기는 고객센터로 문의하세요.</p>

					<!-- <p class="cn4_color2">본사 노무관리자의 이름,폰번호와 사업자등록증을 이메일(ilgaja@jnpartner.kr)로 <br class="dNone768">보내주시면 모든 현장의 출역현황을 관리할 수 있도록 등록해 드립니다.</p> -->
				</div>
			</div>
		</div>

	<!-- contents-->
	<div class="contents">
		<!-- 구인구직 알선분야 -->
		<div class="con01">
			<div class="cIn01 clear">
				<p class="conImg"><img src="/resources/web/images/conImage01.png" alt=""></p>
				<div class="conTxt">
					<h3>구인구직 알선분야</h3>
					<ul class="cn1_list">
						<li class="clear"><span class="cn1_color1">- 보통인부 : </span><span class="cn1_right">준공청소, 신호수, 잡부, 공장, 농사일, 이사짐, 조공, 양중, 물류센터, 창고정리, 자재정리, 철거, 하스리, 커팅, 그라인드, 곰방 인부</span></li>
						<li class="clear"><span class="cn1_color1">- 조공 : </span><span class="cn1_right">전기조공, 미장조공, 조적조공, 타일조공 등 기술자 보조인부</span></li>
						<li class="clear"><span class="cn1_color1">- 기공 : </span><span class="cn1_right">내장목수, 타일, 미장, 조적, 메지, 전기, 설비, 페인트, 실리콘, 형틀목수, 철근, 타설, 용접, 판넬, 경량, 도배, 석공, 비계, 경계석, 보도블럭 등 기술자</span></li>
					</ul>
					&nbsp; &nbsp; <p class="cn1Point1">민간 유료 직업소개소의 <span class="cn1_color2">외국인</span> 고용알선은 <span class="cn1_color2">불법</span>입니다.</p>
				</div>
			</div>
		</div>
		<!-- 서비스 가능 지역 -->
		<div class="con02">
			<div class="cIn02 clear">
				<div class="conTxt">
					<h3>서비스 가능 지역</h3>
					<p class="ct01">현재 <strong>
					<span class="cn2_color1">서울</span>
					/<span class="cn2_color3">인천</span>
					/<span class="cn2_color2">경기</span>
					/<span class="cn2_color1">대전</span>
					/<span class="cn2_color3">세종</span>
					/<span class="cn2_color2">부산</span>
					/<span class="cn2_color1">광주</span>
					</strong>
					<br><br>지역 인력 알선중
					</p>
					 
					<p class="ct02">
						일가자인력 지역별 파트너 모집중입니다.<br>
						창업 상담 : <span class="cn2_color4"><strong><a href="https://forms.gle/Y8GvGCeULgLnNL977" target="_blank">신청하기</a></strong></span>
					</p>
				</div>
				<p class="conImg"><img src="/resources/web/images/conImage02.png" alt=""></p>
			</div>
		</div>
		
		<!-- 일당지급 및 비용처리 안내 -->
		<div class="con03">
			<div class="cIn03 clear">
				<p class="conImg"><img src="/resources/web/images/conImage03.png" alt=""></p>
				<div class="conTxt">
					<h3>일당지급 및 비용처리 안내</h3>
					<div class="cn3_list">
						<h4>노임(단가) 지급은 어떻게?</h4>
						<ul>
							<li>- 구인처는 구직자 통장으로 직접 송금합니다.(본인 계좌는 일가자 구인자 앱에서 100% 제공)</li>
							<li>- 일용직 갑근세, 고용보험료 등 원천징수 후 송금합니다.</li>
						</ul>
					</div>
					<div class="cn3_list">
						<h4>비용처리 : 분기별 일용지급조서 국세청 신고</h4>
						<ul>
							<li>- 일가자는 일당을 합법적 "비용"으로 인정받을 수 있도록 구직자 본인 통장과 노무자료를 제공합니다.</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		

	</div>
	
	