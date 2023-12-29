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
  
  	//일등록...
  	function fucWorkProcess(){
	/*   

	if($("#work_days").val() == 0 ){
		toastW("작업일수를 선택 하세요.");
	$("#work_days").focus();
	return;
	}
 */	
		if ( !fnChkValidForm("frmWorkReg") ){
			return;
		}

		if ($("input:checkbox[id='is_tax']").is(":checked")) {
			if ($("#tax_name").val() == "") {
				toastW("계산서 담담자 이름을 입력 하세요.");
				$("#tax_name").focus();
				return;
			}
			
			if ($("#tax_phone").val() == "") {
				toastW("계산서 담담자 폰번호를 입력 하세요.");
				$("#tax_phone").focus();
				return;
			}
		}

		if (!$("input:checkbox[id='chkRule']").is(":checked")) {
			toastW("개인정보 수집에 동의 해야 합니다.");
			return;
		}
		
		$("[class ^= arr_memo]").each(function() {
			
			var memo = $(this).val();
			memo = memo.replace(/,/gi,"&#44");
			$(this).val(memo);
		});
		
		if(!confirm("인력을 요청하시겠습니까? ")){
			return;
		}
 
		$('#frmWorkReg').ajaxForm({
			url : "/web/regWorkProcess",
			enctype : "multipart/form-data", // 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : 'application/x-www-form-urlencoded; charset=utf-8', //캐릭터셋을 euc-kr로 
			success : function(data) {
				if (data.code == "0000") {
					alert("인력 요청이 성공적으로 접수되었습니다. 감사합니다. ");
					// closeWorkOffer();
					location.reload();
				} else {
					toastFail( data.message);
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

		$('#frmWorkReg').submit();
	}
  
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

		<!-- 사람구함 팝업 -->
		<form id="frmWorkReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
		<input type="hidden" name="order_request" value="WEB" />
		<div class="jobOffer">
			<div class="bgOffer"></div>
			<div class="joHeader clear">
				<p class="johHeadTxt"><strong>인력요청</strong><!-- (구인자 클릭) --></p>
				<p class="johHeadImg"><img src="/resources/web/images/subClose.png" alt="" id="closeJob" class="close" style="cursor:pointer" onclick="closeWorkOffer()"></p>
			</div>
			<div class="joContents clear">
				
					<div class="offerInfo01">
						<p class="firstOI_01 clear">
							<label>현 장<span>*</span></label>
							<input type="text" id="work_name" name="work_name" placeholder="주소" required="required" class="notEmpty"  title="현장주소" >
						</p>
						<p class="firstOI_02 clear">
							<label class="tit1">작 업 일<span>*</span></label>
							<input type="text" id="order_date" name="order_date" class="date notEmpty"  title="작업일"  required="required" readonly>
							<label class="tit2">작업일수</label>
							<select name="work_day" id="work_days"  name="work_days"  required="required" class="notEmpty" title="작업일수">
								<option value="" selected>선택</option>
								<option value="1일" >1일</option>
								<option value="2일">2일</option>
								<option value="1일~2일">1일~2일</option>
								<option value="2일~3일<">2일~3일</option>
								<option value="3일~4일">3일~4일</option>
								<option value="5일~6일">5일~6일</option>
								<option value="7일 이상">7일 이상</option>
							</select>
						</p>
						<p class="firstOI_02 clear">
							<label class="tit1">도 착 시 간</label>
							<select name="work_arrival" id="work_arrival" class="case3" onchange="timeChange();">

								<c:forEach var="i" begin="0" end="24" step="1">
									<c:set var="hh" value="0${i}"/>
									<c:set var="hh" value="${fn:substring(hh, fn:length(hh)-2, fn:length(hh) )}"/>
	
									
									<c:forEach var="j" begin="0" end="30" step="30">
										<c:set var="tt" value="0${j}"/>
										<c:set var="tt" value="${fn:substring(tt, fn:length(tt)-2, fn:length(tt) )}"/>
										<c:set var="vv" value="${hh}${tt}"/>
											<c:if test="${vv  !='2430'}">
											<option value="${hh}${tt}" ${vv eq "0800" ? 'selected':''}>${hh}:${tt}</option>
											</c:if>
									</c:forEach>
									
								</c:forEach>
							</select>
							
							<label class="tit2">종료시간</label>
							<select name="work_finish" id="work_finish"  onchange="timeChange();">
								<c:forEach var="i" begin="0" end="36" step="1">
									<c:set var="hh" value="0${i}"/>
									<c:set var="hh" value="${fn:substring(hh, fn:length(hh)-2, fn:length(hh) )}"/>
	
									<c:forEach var="j" begin="0" end="30" step="30">
										<c:set var="tt" value="0${j}"/>
										<c:set var="tt" value="${fn:substring(tt, fn:length(tt)-2, fn:length(tt) )}"/>
										<c:set var="vv" value="${hh}${tt}"/>
											<c:if test="${vv  !='3630'}">
											<option value="${hh}${tt}" ${vv eq "1700" ? 'selected':''}>${hh}:${tt}</option>
											</c:if>
									</c:forEach>
	
								</c:forEach>
							</select>
						</p> 
						
						<p class="firstOI_01 clear">
							<label class="tit1">구인처상호<span>*</span></label>
							<input type="text"  class="notEmpty"  title="구인처 상호"  id="employer_name" name="employer_name" placeholder="상호명(개인은 성함 입력)" required="required">
						</p>
						
						<p class="firstOI_03 clear">
							<label class="tit1">오더하신분<span>*</span></label>
							<input type="text"  class="notEmpty"  title="오더자이름"  id="manager_name" name="manager_name" placeholder="이름" required="required">
							<label class="tit2">연락처<span>*</span></label>
							<input type="text" class="con2 notEmpty hp"  title="오더자연락처" id="manager_phone" name="manager_phone" placeholder="핸드폰번호" required="required" class="con2">
							<span class="btn"><span class="add"><img src="/resources/web/images/plusIcon.png" alt="추가"></span><span class="del"><img src="/resources/web/images/minusIcon.png" alt="삭제"></span></span>
						</p>
						<p class="firstOI_04 clear">
							<label class="tit1">현장담당자</label>
							<input type="text" id="work_manager_name"  name="work_manager_name"  placeholder="이름" required="required">
							<label class="tit2">연락처</label>
							<input type="text" id="work_manager_phone"  name="work_manager_phone"  placeholder="핸드폰번호" required="required" class="con2 ">
						</p>
					</div>
					
					<div class="offerInfo02">
						<div class="secondOI clear">
							<div class="secValue1">
							    <div class="secondOIpop">
								<div class="sOIpopCon">
									<span class="arrow"><img src="/resources/web/images/arrowIcon.png" alt=""></span>
									<div class="sOItit">
										<p><strong>전문분야</strong><br>단가는 2019년 기준이며, 계단유무/장비사용에 따라 단가가 조정될 수 있습니다.</p>
										<span><img src="/resources/web/images/subClose.png" alt="닫기"></span>	
									</div>
									<div class="sOIemp">
											<!-- 
											남성/여성인력 근무시간에 따른 단가 책정 룰 
												*** 시간대 : 06~17시까지 
												2시간 이하 : 7만원
												2~3시간 : 9만원
												3~4시간 : 11만원
												4시간 초과 : 해당직종 일당과 동일
												
												8시간을 초과시는 시간당 3만원
												
												*** 시간대 : 17~06시까지 
												2시간 이하 : 8만원
												2~3시간 : 11만원
												3~4시간 : 14만원
												시간당 + 3만원
												
                                                8시간 초과시 시간당 4만원
												
												
											기술인력 근무시간에 따른 단가 책정 룰
												*** 시간대 : 06~17시까지 
												무조건 일당
												*** 시간대 : 17~06시까지 
												4시간 까지 : 일당과 동일 
												4시간 초과 : 일당의 2배
												-->
									  
										<c:set var="kindGender" value="-1" />					
										
										<c:forEach items="${kindList}" var="kind" varStatus="status">
                                    		<c:if test="${kind.code_gender ne kindGender}">
    												<c:set var="kindGender" value="${kind.code_gender}" />
    												<c:choose>
													    <c:when test="${kindGender eq '0'}">
													         <div class="man clear">
																<p class="empTit">잡부조공</p>
																<div class="empButton">
													    </c:when>
													     <c:when test="${kindGender eq '1'}">
													       		</div>
															</div>
															<div class="women clear">
																<p class="empTit">여성인력</p>
																<div class="empButton">
													    </c:when>
													    <c:otherwise>
													        	</div>
															</div>
															<div class="tech clear">
																<p class="empTit">기술인력</p>
																<div class="empButton">
													    </c:otherwise>
													</c:choose>
											</c:if>
											
                                    		<p price="${kind.code_price}" code_gender="${kind.code_gender}"  kind_code="${kind.code_value }">${kind.code_name}</p>
										</c:forEach>
										
										<c:if test="${!empty kindList}">
											</div>
											</div>
										</c:if>
									</div>
									<p class="sOItxt">새벽시간, 거리가 먼 현장인 경우 "교통비"가 별도 책정될 수 있습니다.</p>
								</div>
							</div>
							
								<label>직종</label>
								<input type="hidden" name="arr_kind_code" class="kind_code" codePrice='1'>
								<input type="text" placeholder="선택" class="opp notEmpty"  title="직종"  name="arr_kind_name" class="kind_name" required="required" readonly onfocus="this.blur()" onclick="funSelectKink(this)">
							</div>
							<div class="secValue2">
								<label>단가(원)</label>
								<input type="text" name="arr_price" placeholder="" class="price num"  title="단가" code_price="0" code_gender="0" value= "" required="required" >
							</div>
							<div class="secValue3">
								<label>상세작업설명</label>
								<input type="text" name="arr_memo" class="arr_memo notEmpty" title="상세작업설명"  placeholder="필수" required="required">
							</div>
							<div class="secValue4">
								<label>인원</label>
								<span class="personMiner" onclick="funWorkerMinu(this)"><img src="/resources/web/images/personMinu.png" alt=""></span>
								<input type="text" value="1" class="worker_num notEmpty num" name="arr_count">
								<span class="personPlus" onclick="funWorkerPlus(this)"><img src="/resources/web/images/personPlus.png" alt="" ></span>
							</div>
							<div class="secValue5">
								<span id="workadd">추가</span>
							</div>
						</div>
					</div>
					<div class="offerInfo03 clear">
						<dl>
							<dt>단가지급 : </dt>
							<dd>
								<label><input type="radio" name="pay_type" id="joi1" value="300006" checked> 구직자통장송금</label>
								<label><input type="radio" name="pay_type" id="joi2" value="300007" > 현장현금지급</label>
							</dd>
						</dl>
						<dl>
							<dt>나이제한 : </dt>
							<dd>
								<label><input type="radio" name="work_age" id="joi1" value="0" checked > 제한없음</label>
								<label><input type="radio" name="work_age" id="joi2" value="30" > 30 이하</label>
								<label><input type="radio" name="work_age" id="joi3" value="40" > 40 이하</label>
								<label><input type="radio" name="work_age" id="joi4" value="50" > 50 이하</label>
								<label><input type="radio" name="work_age" id="joi5" value="60" > 60 이하</label>

							</dd>
						</dl>
						<dl>
							<dt>혈압제한 : </dt>
							<dd>
								<label><input type="radio" name="work_blood_pressure" id="joi1"  value="0"  checked> 제한없음</label>
								<label><input type="radio" name="work_blood_pressure" id="joi2"  value="160"> 160 이하</label>
								<label><input type="radio" name="work_blood_pressure" id="joi3"  value="150"> 150 이하</label>
								<label><input type="radio" name="work_blood_pressure" id="joi4"  value="140"> 140 이하</label>
							</dd>
						</dl>
					</div>
					<div class="offerInfo04">
						<div class="offerInfo04pop">
							<p><strong>[필수]개인정보 수집 및 이용 동의</strong></p>
<style>
  table {
    width: 100%;
    border: 1px solid #444444;
    border-collapse: collapse;
  }
  th, td {
    border: 1px solid #444444;
    padding:8px 0 !important;
  }
</style>
								<table>
								<tr>
									<td>목적</td>
									<td>항목</td>
									<td>보유기간</td>
								</tr>
								<tr>
									<td>이용자 식별 및 본인 여부 확인</td>
									<td>이름, 전화번호</td>
									<td>회원 탈퇴 후 5일까지</td>
								</tr>
								<tr>
									<td>구인신청 등 구인자 요청 처리 및 결과 회신</td>
									<td>상호명, 현장 주소</td>
									<td>회원 탈퇴 후 5일까지</td>
								</tr>
								</table>
								<p><br>※일가자 인력 서비스 제공을 위해서 필요한 최소한의 개인정보이므로 동의를 해주셔야 서비스를 이용하실 수 있습니다.</p>
							<ol>
								<span>닫기</span>
								
							</ol>
						</div>
						<p class="referCheck"><label><input type="checkbox" id="is_tax" name="is_tax" value="1"> 소개비 10% 면세계산서 요청</label></p>
						<p class="refer clear">
							<label class="tit1">계산서담당자</label>
							<input type="text" id="tax_name"  name="tax_name"  placeholder="이름" class="con1">
							<label class="tit2">연락처</label> 
							<input type="text" id="tax_phone"  name="tax_phone" placeholder="핸드폰번호" class="con2">
						</p>
						<p class="agreeCheck">
							<label><input type="checkbox" name="is_worker_info"  id="is_worker_info" value="1"> 작업자 노임대장/신분증/계좌번호 요청</label>
							<label><input type="checkbox" name="chkRule"  id="chkRule"  value="1"> <span class="red">(동의함)</span> 개인정보 수집 및 이용에 동의합니다.<span class="conView">내용보기</span></label>
						</p>
					</div>
					<div class="jobOfferBtn">
						<span class="apply" onClick="fucWorkProcess()">신청</span>
						<span class="close" onclick="closeWorkOffer();">닫기</span>
					</div>
			</div>
			
			<div class="joFooter">
				<div class="appInfo">
					<p class="appInfoTxt">구인신청 후 아래 앱을 설치하면 배정된 인력 정보를 미리 알 수 있습니다.</p>
					<div class="appInfoIcon clear">
						<p class="aInfoIconButton"><img src="/resources/web/images/btnImg.png" alt=""></p>
						<p class="aInfoIconApp"> 
							<span><img src="/resources/web/images/appIcon01.png" alt="앱스토어"> <span><a href="https://apps.apple.com/app/id1350649347" target="_blank">아이폰</a></span></span>
							<span><img src="/resources/web/images/appIcon02.png" alt=""> <span><a href="https://play.google.com/store/apps/details?id=nemo.ilgaja.manager" target="_blank">안드로이드</a></span></span>
						</p>
					</div>
					<div class="jobFoot">
						<!-- <img src="/resources/web/images/callIcon.png" alt="">
						구인요청 : <strong>010.3808.5994</strong> -->
					</div>
				</div>
			</div>
		</div>
		</form>
		
		<!-- 일자리구함 팝업-->
		<form id="frmWorkerReg" name="frmWorkerReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
		<div class="workOffer" style="display:block;">
			<div class="w_bgOffer"></div>
			<div class="woHeader clear">
				<p class="woHeadTxt"><strong>일자리요청</strong>(근로자 신청)</p>
				<p class="woHeadImg"><img src="/resources/web/images/subClose.png" alt="" class="close" style="cursor:pointer" onClick="closeJobOffer();"></p>
			</div>
			<div class="woContents clear">
				<form action="jobOfferForm">
					<div class="w_offerInfo01">
					
						<div class="clear">
							<label>이 름<span>*</span></label>
							<input type="text" required="required" id="worker_name" name="worker_name" title="이름" class="notEmpty">
						</div>
						<div class="clear">
							<label>핸드폰번호<span>*</span></label>
							<input type="text" required="required"  id="worker_phone" name="worker_phone" title="핸드폰번호" class="notEmpty hp">
						</div>
						<div class="clear">
							<label>주민번호<span>*</span></label>
							<input type="text" required="required" id="worker_jumin_before" style="width: 200px;" title="주민번호 앞 6자리" class="notEmpty num" placeholder="주민번호 앞 6자리"> <span style="float: left; margin: 9px;">-</span> 
							<input type="text" required="required" id="worker_jumin_after" style="width: 50px;" title="주민번호 뒤 1자리" class="notEmpty num" placeholder="주민번호 뒤 1자리"><span style="margin: 6px; line-height: 2em;">******</span>
							<input type="hidden" id="worker_jumin" name="worker_jumin" />
						</div>
						<div class="clear">
							<label>현거주 주소<span>*</span></label>
							<input type="text" required="required" class="address notEmpty"  id="worker_addr" name="worker_addr"  title="주소" >
						</div>
						
						<div class="w_OIpop">
							<div class="wOIpopCon">
									<span class="arrow"><img src="/resources/web/images/arrowIcon2.png" alt=""></span>
									<div class="sOItit">
										<p><strong>전문분야</strong> (여러 개 선택 가능)</p>
										<span><img src="/resources/web/images/save.png" alt="닫기"></span>	
									</div>
									<div class="wOIemp">
									
										<c:set var="codeType" value="-1" />
										<c:set var="codeGender" value="0" />
										
										<c:forEach items="${jobList}" var="job" varStatus="status">
                                    		<c:if test="${job.code_type ne codeType}">
    												<c:set var="codeType" value="${job.code_type}" />
    												<c:choose>
													    <c:when test="${codeType eq '400'}">

													         <div class="women clear">
																<div class="empButton clear">
													    </c:when>
													     <c:when test="${codeType eq '800'}">
													       		</div>
															</div>
															<div class="man clear">
																<div class="empButton clear">
													    </c:when>
													    <c:otherwise>
													        	</div>
															</div>
															<div class="tech clear">
																<div class="empButton clear">
													    </c:otherwise>
													</c:choose>
											</c:if>

											<c:if test="${codeType eq '400' && job.code_gender ne codeGender}">
											<c:set var="codeGender" value="${job.code_gender}" />
																</div>
															</div>
															<div class="tech clear">
																<div class="empButton clear">
											</c:if>
											
                                    		<p  kind_code="${job.code_value }">${job.code_name}</p>
										</c:forEach>
									
										<c:if test="${!empty jobList}">
											</div>
											</div>
										</c:if>
																					
									</div>
								</div>
						</div>
						<div class="clear">
							<label>전문분야<span>*</span></label>
							<input type="text" id="worker_job_name" name="worker_job_name" placeholder="다수 선택가능" required="required" class="popMenu notEmpty"  title="전문분야" readonly onfocus="this.blur()">
							<input type="hidden" id="worker_job_code" name="worker_job_code" value="">
						</div>
						
						<div class="clear">
							<label>경력/자격증<span>*</span></label>
							<input type="text" name="worker_feature" placeholder="예) 잡부2년,용접10년 / 용접기능사 보유" required="required" id="" name="" class="notEmpty" maxlength="250" title="경력설명">
						</div>
						
						<div class="clear">
							<label>기초안전교육<span>*</span></label>
							<span class="phone">
								<label><input type="radio"  name="worker_OSH_yn" value="1" class="notEmpty" title="기초안전교육"> 이수</label>
								<label><input type="radio"  name="worker_OSH_yn" value="0" class="notEmpty" title="기초안전교육"> 미이수</label>
							</span>
						</div>
						
						<div class="clear">
							<label>핸드폰 기종<span>*</span></label>
							<span class="phone">
								<label><input type="radio"  name="os_type" value="A" checked> 안드로이드폰</label>
								<label><input type="radio"  name="os_type" value="I"> 아이폰</label>
								<!-- <label><input type="radio"  name="os_type" value="A"> 일반폰</label> -->
							</span>
						</div>
						
							
						
					</div>
					<div class="w_offerInfo02">
						<div class="w_offerInfo04pop">
							<p><strong>[필수]개인정보 수집 및 이용 동의</strong></p>
<style>
  table {
    width: 100%;
    border: 1px solid #444444;
    border-collapse: collapse;
  }
  th, td {
    border: 1px solid #444444;
    padding:8px 0 !important;
  }
</style>
								<table>
								<tr>
									<td>목적</td>
									<td>항목</td>
									<td>보유기간</td>
								</tr>
								<tr>
									<td>이용자 식별 및 본인 여부 확인</td>
									<td>이름, 전화번호,생년월일</td>
									<td>회원 탈퇴 후 5일까지</td>
								</tr>
								<tr>
									<td>구직신청 등 구직자 요청 처리 및 결과 회신</td>
									<td>상호명, 현장 주소</td>
									<td>회원 탈퇴 후 5일까지</td>
								</tr>
								</table>
								<p><br>※일가자 인력 서비스 제공을 위해서 필요한 최소한의 개인정보이므로 동의를 해주셔야 서비스를 이용하실 수 있습니다.</p>
							<ol>
								<span>닫기</span>
								
							</ol>
						</div>
						<p class="comNotie">민간 유료 직업소개소의 <span>외국인 </span>고용알선은<br class="pcOn"><span>불법</span>으로 외국인은 구직등록이 <span>불가</span>합니다.</p>
						<p class="agreeCheck">
							<label><input type="checkbox" name="chkWorkerRule"  id="chkWorkerRule"  value="1"> <span class="red">(동의함)</span>개인정보 수집 및 이용에 동의합니다.</label><span class="w_conView">내용보기</span>
						</p>
					</div>
					<div class="jobOfferBtn">
						<span class="apply" onClick="fucWorkerProcess()">신청</span>
						<span class="close" onClick="closeJobOffer()">닫기</span>
					</div>
				</form>
			</div>
			<div class="joFooter">
				<div class="appInfo">
					<p class="appInfoTxt">구직신청 후 아래 앱을 설치하고 대기하면 상담할 지점에서 연락이 갑니다.</p>
					<div class="appInfoIcon clear">
						<p class="aInfoIconButton"><img src="/resources/web/images/btnImg2.png" alt=""></p>
						<p class="aInfoIconApp">
							<span><img src="/resources/web/images/appIcon02.png" alt=""> <span><a href="https://play.google.com/store/apps/details?id=nemo.ilgaja.worker" target="_blank">안드로이드</a></span></span>
						</p>
					</div>
					<div class="jobFoot">
						<!-- <img src="/resources/web/images/callIcon.png" alt="">
						구직상담 : <strong>02.461.0432</strong> -->
					</div>
				</div>
			</div>
		</div>
	
	</div>
	</form>
	
	
	
	
	
	
	<!-- header -->
	<a href=""></a>
	<div class="header">
		<div class="headIn">
			<h1><img src="/resources/web/images/logo.png" alt="일가자 인력 로고" width="220px"></h1>
			<p><span>잡부,조공,기공 내국인 인력</span> 100% 출역 약속</p>
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
					<a href="#none"><p class="mBtn01"><strong>인력요청</strong> (구인자 클릭)</p></a>
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
						<li><span class="icon"><img src="/resources/web/images/conIcon01.png" alt=""></span> <span class="item"><a href="https://apps.apple.com/app/id1350649347" target="_blank">아이폰</a></span></li>
						<li><span class="icon"><img src="/resources/web/images/conIcon02.png" alt=""></span> <span class="item"><a href="https://play.google.com/store/apps/details?id=nemo.ilgaja.manager" target="_blank">안드로이드</a></span></li>
						<li><span class="icon"><img src="/resources/web/images/conIcon03.png" alt=""></span> <span class="item"><a href="https://ilgaja.com/manager/login" target="_blank">PC 웹</a></span></li>
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
					<p class="ct01">현재 <strong><span class="cn2_color1">서울</span>/<span class="cn2_color3">인천</span>/<span class="cn2_color2">경기</span>/<span class="cn2_color1">대전</span>/<span class="cn2_color3">세종</span></strong> 지역 인력 알선중</p>
					<p class="ct02">일가자인력 지역별 파트너 모집중입니다.<br>창업 상담 : <span class="cn2_color4"><strong><a href="https://forms.gle/Y8GvGCeULgLnNL977" target="_blank">신청하기</a></strong></span></p>
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
	
	