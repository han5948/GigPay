<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"  %>
 
<script type="text/javascript"  src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ehjyv0iw4b&amp;submodules=geocoder"></script>
<script type="text/javascript" src="<c:url value="/resources/manager/js/newPaging.js" />"></script>


<script type="text/javascript">


var workSeq = ${work_seq};
var managerName = "${managerName}";
var managerPhone = "${managerPhone}";
var managerMode = "${managerMode}";

pageName = "main";

function fn_submit() {
	
	frm.action = "/manager/main";
	frm.target = "";
	$("#frm").submit();

}


function fn_excel(){
	
	if($("#manager_type").val() == "O" && $("#work_seq").val() == "0"){ // order 메니져이고 work_seq 가 0 이면
		alert("현장을 선택 후 다운로드 하세요.");
		return;
	}
	
   if ( $("#start_ilbo_date").val() == "" ) {
     alert("출역일자를 선택하세요.");
     $("#start_ilbo_date").focus();
     return false;
   }
   if ( $("#end_ilbo_date").val() == "" ) {
     alert("출역일자를 선택하세요.");
     $("#end_ilbo_date").focus();
     return false;
   }
   
	if($wbr.browser == "Android"){
		App.downLoadExcel($("#work_seq option:selected").text()
				,$("#manager_type").val()
				,$("#srh_type").val()
				,$("#srh_seq").val()
				,$("#start_ilbo_date").val()
				,$("#end_ilbo_date").val())
				;
	}else if($wbr.browser == "IOS"){
		window.location="jscall://excel?fileName="+$("#work_seq option:selected").text()
				+"&manager_type=" +$("#manager_type").val()
				+"&srh_type=" +$("#srh_type").val()
				+"&srh_seq=" + $("#srh_seq").val()
				+"&start_ilbo_date=" +$("#start_ilbo_date").val()
				+"&end_ilbo_date=" + $("#end_ilbo_date").val()
				+"&download_url=" + "/manager/getIlboExcel"
				;
		
	}else{
		
	    var frm = document.getElementById("frm");
	    frm.action = "/manager/getIlboExcel";
	    //frm.target = "download-ifrm";
	    frm.submit();
	    //다시원상태로 돌려 줘야 된다. 다른곳에서는 action 을 정의 하지 않고 링크하는 곳이 있기 때문에
	    frm.action ="";
	}
  
}


</script>
<br>
	<!-- 클립보드 복사를 위해 -->
	<div style="display: none;"><textarea id="myClipboard"></textarea></div>
	  

				
		<!-- s: #container-wrap //-->
		<div id="container-wrap"  class="scontainer" >
			<div id="contents">
			<!-- page star // -->
			<div class="placement-wrap">
			<form id="frm" name="frm"  method="post">			
		
			<input type="hidden" id="rowCount" name="rowCount" value="${ilboDTO.paging.rowCount}" />
			<input type="hidden" id="pageNo" name="paging.pageNo" value="${ilboDTO.paging.pageNo}" />
			<input type="hidden" id="employer_seq" name="employer_seq" value="${ilboDTO.employer_seq}" />
			<input type="hidden" id="manager_type" name="manager_type" value="${ manager_type }" />
			<input type="hidden" id="srh_type" name="srh_type" value='${ manager_type eq "E" ? "i.employer_seq" : "i.work_seq"}' /> <!--Excel 다운로드 때문에 필요 쿼리에들어갈 것을 직접 입력 해 준다. -->
			<input type="hidden" id="srh_seq" name="srh_seq" value='${ manager_type eq "E" ? employer_seq : work_seq}' />
				<!-- 좌측 -->
				<div class="fl">
					<!-- <p class="stit">이름을 클릭하시면 구직자의<i class="block">상세정보를 확인하실 수 있습니다.</i></p> -->
					<ul class="list">
						<li>
							<div class="car-for">
								<p class="calendar-input"><input type="text" id="start_ilbo_date" name="start_ilbo_date" value="${start_ilbo_date }" readonly="true" class="date-calendar"  data-lang="en" data-years="2017-2035" data-format="YYYY-MM-DD"  type="text" onkeyup="auto_date_format(event, this)" onkeypress="auto_date_format(event, this)"/></p>
								<p class="rtxt">~</p>
								<p class="calendar-input"><input type="text" id="end_ilbo_date" name="end_ilbo_date" value="${end_ilbo_date }" readonly="true" class="date-calendar"  data-lang="en" data-years="2017-2035" data-format="YYYY-MM-DD"  type="text" onkeyup="auto_date_format(event, this)" onkeypress="auto_date_format(event, this)"/></p>
								 
								 
							</div>
							<a href="#" class="find-btn" onclick="javascript:fn_submit()"><span>조회</span></a>
						</li>
						<li>
							<p class="select-box">
								<select title="선택해주세요" class="select-gray" id="work_seq" name="work_seq">
									  
								</select>
							</p>
							<a href="#" class="xls-down" onclick="javascript:fn_excel()"><span class="blind">엑셀다운로드</span></a>
						</li>
					</ul>
					<div class="sgap"></div>
					<p class="info-ex"><span>단가에 알선 수수료 포함 / 총 ${ilboDTO.paging.rowCount}건</span></p>
					
					<div class="placement-list">
						<table>
							<thead>
								<tr>
									<th class="check"></th>
									<th>날짜</th>
									<th>작업자</th>
									<th class="m-none">핸드폰</th>
									<th>분야</th>
									<th>단가<i>(원)</i></th>
									<th class="m-none">계좌</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody >
									<c:choose>
									  <c:when test="${!empty resultList}">
										<!--예약:10005, 직출:100004, 출역:100003, 도착:100014, 완료:100015  대기 : 100002 완료:100015 -->									
											<c:forEach items="${resultList}" var="ilbo" varStatus="status">
												<c:choose>
	                                        	  <%-- <c:when test="${ilbo.output_status_code eq '100005' 	|| ilbo.output_status_code eq '100004' || ilbo.output_status_code eq '100003' || ilbo.output_status_code eq '100014'	|| ilbo.output_status_code eq '100015' ||  ilbo.output_status_code eq '100018'}"> --%>
	                                        	  <c:when test="${ilbo.output_status_code eq '100005' 	|| ilbo.output_status_code eq '100004' || ilbo.output_status_code eq '100003' || ilbo.output_status_code eq '100014'	|| ilbo.output_status_code eq '100015' }">
												       	<tr>
												       		<td>
																<input type="checkbox" id="ch_${ilbo.ilbo_seq }" name="check_list" value="${ilbo.ilbo_seq }" outputCode="${ilbo.output_status_code }" workerSeq="${ilbo.worker_seq }" kindName="${ilbo.ilbo_kind_name }" workName="${ilbo.ilbo_work_name }"/>
																<label class="check_01" for="ch_${ilbo.ilbo_seq }"></label>
															</td>
															<td>${ilbo.ilbo_date }</td>
															<td class="name"><a href="#" onclick="javascript:showWorkerDetail('${ilbo.ilbo_seq }');">${ilbo.ilbo_worker_name }</a></td>
															<td class="m-none">${ilbo.ilbo_worker_phone }</td>
															<td class="name"><a href="#" class="workInfo" workName="${ilbo.ilbo_work_name }" startTime="${ilbo.ilbo_work_arrival }" endTime="${ilbo.ilbo_work_finish}" workComment="${ilbo.ilbo_job_comment}"  managerPhone="${ilbo.ilbo_work_manager_phone}" >${ilbo.ilbo_kind_name }</a></td>
															<td><fmt:formatNumber>${ilbo.ilbo_unit_price } </fmt:formatNumber>원</td>
															<td class="m-none">${ilbo.ilbo_worker_bank_name } : ${ilbo.ilbo_worker_bank_account } / ${ilbo.ilbo_worker_bank_owner}</td>
															<td><span class="s${ilbo.output_status_code }">${ ilbo.output_status_code eq "100003" ? "출발" : ilbo.output_status_code eq "100017" ? "요청" : ilbo.output_status_code eq "100005" ? "배정완료" : ilbo.output_status_code eq "100015" ? "작업종료" : ilbo.output_status_name}</span></td>
														</tr>
												    </c:when>
												    <c:otherwise>
														<tr>
															<td>
																<input type="checkbox" id="ch_${ilbo.ilbo_seq }" name="check_list" value="${ilbo.ilbo_seq }" outputCode="${ilbo.output_status_code }" workerSeq="0" kindName="${ilbo.ilbo_kind_name }" workName="${ilbo.ilbo_work_name }" />
																<label class="check_01" for="ch_${ilbo.ilbo_seq }"></label>
															</td>
															<td>${ilbo.ilbo_date }</td>
															<td class="name">?</td>
															<td class="m-none"></td>
															<td class="name"><a href="#" class="workInfo" workName="${ilbo.ilbo_work_name }" startTime="${ilbo.ilbo_work_arrival }" endTime="${ilbo.ilbo_work_finish}" workComment="${ilbo.ilbo_job_comment}"  managerPhone="${ilbo.ilbo_work_manager_phone}" >${ilbo.ilbo_kind_name }</a></td>
															<td><fmt:formatNumber>${ilbo.ilbo_unit_price } </fmt:formatNumber>원</td>
															<td class="m-none"></td>
															<!-- 
															0			// 초기화   배정대기
															100002 : 대기		배정대기
															100009 : 펑크		배정중
															100012 : 푸쉬		배정중
															100013 : 취소		배정중
															100017 : 푸취		배정중														
															
			    											-->
															<td>
															
															
															<c:choose>
																<c:when test="${ilbo.output_status_code eq '0' 	|| ilbo.output_status_code eq '100002'}">
																	<span class="s100002">배정대기</span>
																</c:when>
																<c:when test="${ilbo.output_status_code eq '100009' || ilbo.output_status_code eq '100012' || ilbo.output_status_code eq '100013' || ilbo.output_status_code eq '100017'}">
																	<span class="s100012">배정중</span>
																</c:when>
																<c:otherwise>
																	<span class="s${ilbo.output_status_code }">${ilbo.output_status_name}</span>
																</c:otherwise>															
															</c:choose>
															
															
															
															</td>
														</tr>			
												    </c:otherwise>
												</c:choose>
											</c:forEach>
                                   		</c:when>
                                   	<c:otherwise>
                                   		<tr>
                                   			<td colspan="8">데이타가 없습니다.<td>
                                   		</tr>
                                   	</c:otherwise>
                                  </c:choose>     
								
								
							</tbody>
						</table>
					</div>
					<p class="info-ex-red"><span>작업자는 저녁 6시 전후에 배정 됩니다.(실시간은 예외)</span></p>
					<div class="sgap"></div>
					
					
					<div class="paging-wrap">
						<script type="text/javascript">
							fn_page_display('${ilboDTO.paging.pageSize}', '${ilboDTO.paging.rowCount}', 'frm', '#pageNo');
						</script>		
					</div>
				</div>
				</form>
				<!-- 좌측 -->
				<!-- 우측 -->
				<div class="fr">
					<!-- info-nodata -->
					<div class="info-nodata">
						<p>구직자를 선택해주세요!</p>
					</div>
					<!-- info-nodata -->
				    <!-- info-view -->
					<div class="info-view">
						<a href="#n" class="close"><span class="blind">닫기</span></a>
						
						<div id="jumin_img" class="info-img">
							<img id="ilbo_worker_jumin_file_name" src="/resources/manager/image/Content/no_image.png" alt=""/>
						</div>
						
						<ul class="view">
							<li>
								<span class="btxt">이름</span>
								<span class="stxt nb" id="ilbo_worker_name">xxx (만 00세)</span>
								<a href="#n" id="osh" class="cate-btn membership-btn"><span id="oshtxt">기초안전</span></a>
							</li>
							<li>
								<span class="btxt">주민번호</span>
								<span class="stxt" id="ilbo_worker_jumin">******-*********</span>
							</li>
							<li>
								<span class="btxt">휴대폰</span>
								<span class="stxt" id="ilbo_worker_phone">010-1234-5678</span>
							</li>
							<li>
								<span class="btxt">주소</span>
								<span class="stxt" id="ilbo_worker_addr"></span>
							</li>
							<li>
								<span class="btxt">단가</span>
								<span class="mtxt" id="ilbo_unit_price" >0</span>
								<span class="mtxt">원</span>
								<!-- <a href="#n" id="pay" class="pay-btn membershipPay-btn"><span id="paytxt">지급처리</span></a> -->
							</li>
							<li>
								<span class="btxt">계좌번호</span>
								<span class="ctxt " id="ilbo_worker_bank">은행    </span>
								<a id="bank_copy" href="#" class="bank-addr"><span id="ilbo_worker_bank_owner" class="ctxt bank">예금주 : </span></a>
								
								<a href="#n" id="certificate" class="add-btn membership2-btn"  ><span id="certificatetxt">통장사본</span></a>
								
							</li>
						</ul>
					</div>
					<!-- info-view -->
					<!--앱오더-->
					<form id="frmWorkReg" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
						<input type="hidden" name="order_request" value="APP" />
						<div class="app_order">
								<ul>
									<li>
										<span class="tit">회사/현장</span>
										<span class="cont">
											
											<select title="선택해주세요" class="select-gray" id="order_workSeq" name="work_seq">
											  
											</select>
														
											<button type="button" class="buis_plus" id="btnPlus">+</button>
										</span>
									</li>
									<li class="cont_04 car-for">
										<span class="tit required">작업일</span>
										<span class="cont calendar-input">
											<!--<input type="date" />-->
											<input type="text"  id="work_date" name="work_date" value="" readOnly class="date-calendar notEmpty" data-lang="en" data-startDate="20190906" data-years="2017-2035" data-format="YYYY-MM-DD" type="text" onkeyup="auto_date_format(event, this)" onkeypress="auto_date_format(event, this)" title="작업일"/>
										</span>
										<span class="tit2">작업일수</span>
										<span class="cont2">
											<select name="work_days" id="work_days" required="required">
												<option value="1일" selected="">1일</option>
												<option value="2일">2일</option>
												<option value="3일~4일">3일~4일</option>
												<option value="5일~6일">5일~6일</option>
												<option value="7일 이상">7일 이상</option>
											</select>
										</span>
									</li>
									<li class="cont_04 time">
										<span class="tit">도착시간</span>
										<span class="cont">
											<select id="work_arrival" name="work_arrival"  onchange="timeChange();">
												<c:forEach var="i" begin="0" end="23" step="1">
													<c:set var="hh" value="0${i}"/>
													<c:set var="hh" value="${fn:substring(hh, fn:length(hh)-2, fn:length(hh) )}"/>
					
													<c:forEach var="j" begin="0" end="30" step="30">
														<c:set var="tt" value="0${j}"/>
														<c:set var="tt" value="${fn:substring(tt, fn:length(tt)-2, fn:length(tt) )}"/>
														<c:set var="vv" value="${hh}${tt}"/>
															<option value="${hh}${tt}" ${vv eq "0800" ? 'selected':''}>${hh}:${tt}</option>
													</c:forEach>
					
												</c:forEach>
											</select>
										</span>
										<span class="tit2">종료시간</span>
										<span class="cont2">
											<select id="work_finish" name="work_finish"  onchange="timeChange();">
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
										</span>
									</li>
									<li class="cont_04">
										<span class="tit required">현장담당자</span>
										<span class="cont">
											<input type="text" class="notEmpty" title="현장담장자"  id="work_manager" name="work_manager" placeholder="이름" required="required">
											
										</span>
										<span class="tit2 required">연락처</span>
										<span class="cont2">
											<input type="text" class="con2 notEmpty hp" title="연락처" id="work_manager_phone" name="work_manager_phone" placeholder="핸드폰번호" required="required" inputmode="numeric">
										</span>
									</li>
								</ul>
								<div class="secondOI">
									<div class="second_form">
										<div class="sec01">
										<!-- 직종선택 -->
											<label>직종</label>
											<input type="hidden" name="arr_kind_code" class="kind_code" codePrice='1' value="">
											<input type="text"  name= "arr_kind_name" placeholder="선택*" class="ch_work notEmpty" required="required" readonly="readonly" title="직종"  name="arr_kind_name" onclick="funSelectJob(this)"/>
										</div>
										<div class="sec02">
											<label>단가(원)</label>
											<input type="text" name="arr_price" placeholder="" class="price num"  title="단가" code_price="0" code_gender="0" value= "" required="required" >
										</div>
										<div class="sec03">
											<label>상세작업설명</label>
											<input type="text"  name="arr_memo" class="arr_memo notEmpty" placeholder="필수*" required="required" title="상세작업설명"/>
										</div>
										<div class="sec04">
											<label>인원</label>
											<button type="button" class="btn_miner" onclick="funBtnMinus(this)">-</button>
											
											<input type="text" name="arr_worker_num" value="1" class="worker_num notEmpty num" readonly>
											
											<button type="button" class="btn_plus" onclick="funBtnPlus(this)">+</button>
										</div>
										<div class="sec05">
											<button type="button" class="btn_add" id="workadd" >추가</button>
										</div>
									</div>
								</div>
								
								<div class="third_form ">
									<span>
										추가설명
									</span>
									<textarea name="work_memo" placeholder="요청 작업자 및 작업에 대한 상세 설명을 기입하세요."></textarea>
								</div>
								<div class="fourth_form ">
									<dl>
										<dt>단가지급 : </dt>
										<dd>
											<label><input type="radio" name="pay_type" id="joi1" value="300006" checked=""> 구직자통장송금</label>
											<label><input type="radio" name="pay_type" id="joi2" value="300007"> 현장현금지급</label>
										</dd>
									</dl>
									<dl>
										<dt>나이제한 : </dt>
										<dd>
											<label><input type="radio" name="work_age" id="joa1" value="0" checked="">없음</label>
											<label><input type="radio" name="work_age" id="joa2" value="30">30이하</label>
											<label><input type="radio" name="work_age" id="joa3" value="40">40이하</label>
											<label><input type="radio" name="work_age" id="joa4" value="50">50이하</label>
											<label><input type="radio" name="work_age" id="joa5" value="60">60이하</label>
			
										</dd>
									</dl>
									<dl>
										<dt>혈압제한 : </dt>
									<dd>
										<label><input type="radio" name="work_blood_pressure" id="job1" value="0" checked=""> 제한없음</label>
										<label><input type="radio" name="work_blood_pressure" id="job2" value="160"> 160 이하</label>
										<label><input type="radio" name="work_blood_pressure" id="job3" value="150"> 150 이하</label>
										<label><input type="radio" name="work_blood_pressure" id="job4" value="140"> 140 이하</label>
									</dd>
									</dl>
								</div>
								<div class="form_btn btn_2ea">
									<button type="button" class="form_btn_ok appOrder_btn" >인력요청</button>
									<button type="button" class="form_btn_close app-close-btn ">취소</button>
								</div>
						</div>
						</form>
						<!--//앱오더-->
				</div>
				<!-- 우측 -->
				
				<div class="btn-wrap placement">
						<div class="fix btn_2ea">
							<a href="#" class="apporder-btn" style="cursor:pointer;"><span>인력 요청</span></a>
							<c:if test="${empty company_tel}">
							<a href="" class="request-btn"><span>-</span></a>
							</c:if>
							<c:if test="${!empty company_tel}">
							<a href="javascript:telCall('${company_tel }');" class="request-btn"><span>전화</span></a>
							</c:if>
						</div>
					</div>
				
				
					<!-- 체크박스 메뉴 -->
					<div class="btn-wrap placement bottom_slide">
						<div class="fix bottom_slide">
							<!-- <a href="#" class="job-btn"><span>실시간구인</span></a> -->
							<a href="#" class="jobcancel-btn"><span>작업취소</span></a>
							<a href="#" class="thisperson-btn"><span>내일 이분 또 보내주세요</span></a>
							<a href="#" class="oneperson-btn"><span id="oneperson">내일 1명 보내주세요</span></a>
						</div>
					</div>
					<!-- //체크박스 메뉴 -->
					
				
			</div>
			<!-- page end // -->
			</div>
		</div>
		<!-- e: #container-wrap //-->
		
		
		
				

				<!-- s : 기초안전 이미지보기 layer //////////////////-->
				<div class="membership layer-pop">
					<h3>기초안전</h3>
					<div class="is-con">
						<p class="img"><img id="ilbo_worker_OSH_file_name" src="/resources/manager/image/Content/no_image.png" alt=""/></p>
						<!-- <a href="#n" class="original-btn"><span>원본보기</span></a> -->
						<a href="#n" class="ok-btn"><span>확인</span></a>
					</div>
				</div>
				<!-- e: 회원 이미지보기 layer //////////////////-->
				
				
				<!-- s : 증명서 이미지보기 layer //////////////////-->
				<div class="membership2 layer-pop">
					<h3>증명서</h3>
					<div class="is-con">
						<p class="img"><img id="ilbo_worker_certificate_file_name" src="/resources/manager/image/Content/no_image.png" alt=""/></p>
						<!-- <a href="#n" class="original-btn"><span>원본보기</span></a> -->
						<a href="#n" class="ok-btn"><span>확인</span></a>
					</div>
				</div>
				<!-- e: 회원 이미지보기 layer //////////////////-->
				
				
				
		<!-- 비밀번호변경 -->
		<div class="password_modi_box layer-pop">
			<div class="pw_form">
				<h3>비밀번호 변경</h3>
				<a href="#n" class="ok-btn close"><span class="blind">닫기</span></a>
				<ul>
					<li>
						<span class="tit2">현재비밀번호</span>
						<span class="cont2">
							<input type="password" name="manager_pass" id="manager_pass"/>
						</span>
					</li>
					<li>
						<span class="tit2 Btit">변경비밀번호</span>
						<span class="cont2">
							<input type="password" name="manager_new_pass" id="manager_new_pass"/>
						</span>
					</li>
					<li>
						<span class="tit2 Btit">비밀번호확인</span>
						<span class="cont2">
							<input type="password" name="manager_new_pass2" id="manager_new_pass2"/>
						</span>
					</li>
				</ul>
				<div class="form_btn btn_2ea">
					<button type="button" class="form_btn_ok " id="newPass-btn">확인</button>
					<button type="button" class="form_btn_close ok-btn">닫기</button>
				</div>
			</div>
		</div>
		<!-- //비밀번호변경 -->

		<form id="frmEmployerInfo" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
		<!-- 사업자정보 -->
		<div class="buisness_box layer-pop">
			<div class="buis_form">
				<h3>사업자정보</h3>
				<a href="#" class="ok-btn close"><span class="blind">닫기</span></a>
				<ul>
					<!--본사 매니저용 사업자정보-->
					<li class="buis_type01">
						<span class="tit required">회사명</span>
						<span class="cont">
							<input type="text" id="employer_name" name="employer_name" required placeholder="상호" />
						</span>
					</li>
					
					<!--//본사 매니저용 사업자정보-->
					<!--현장 매니저용 사업자정보-->
					<!-- 
					<li class="buis_type02">
						<span class="tit required">회사명</span>
						<span class="cont">
							<select>
								<option value="" hidden>상호</option>hidden 있을시에 옵션값으로 노출안됨.
								<option value="상호1">상호1</option>
								<option value="상호2">상호2</option>
							</select>
							<button type="button" class="buis_plus">+</button>
						</span>
					</li>
					 -->
					<!--현장 매니저용 사업자정보-->
					<li>
						<span class="tit">사업자</span>
						<span class="cont">
							<input type="text" placeholder="번호(-없이)"  id="employer_num" name="employer_num" readOnly/>
						</span>
					</li>
					<li>
						<span class="tit">대표자</span>
						<span class="cont">
							<input type="text" placeholder="성함" id="employer_owner" name="employer_owner"/>
						</span>
					</li>
					<li>
						<span class="tit">업태</span>
						<span class="cont">
							<input type="text"  id="employer_business" name="employer_business"/>
						</span>
					</li>
					<li>
						<span class="tit">업종</span>
						<span class="cont">
							<input type="text"  id="employer_sector" name="employer_sector"/>
						</span>
					</li>
					<li>
						<span class="tit">주소</span>
						<span class="cont">
							<input type="text"  id="employer_addr" name="employer_addr"/>
						</span>
					</li>
					<li>
						<span class="tit">이메일</span>
						<span class="cont">
							<input type="email" id="employer_email" name="employer_email"/>
						</span>
					</li>
					<li>
						<span class="tit2">사업자등록증</span>
						<span class="cont2">
							<input type="file" id="file_CORP" class="file" name="file_CORP" accept="image/*">
						</span>
					</li>
					<li class="tex">
						<input type="checkbox" id="is_tax" value="1" name="is_tax"/>
						<label for="is_tax">
							<span>
								소개비 10%에 대한 면세계산서 요청
							</span>
						</label>
					</li>
				</ul>
				<div class="form_btn btn_2ea">
					<button type="button" class="form_btn_ok" onclick="funcSetEmployerInfo();">저장</button>
					<button type="button" class="form_btn_close ok-btn">닫기</button>
				</div>
			</div>
		</div>
		</form>
		<!-- //사업자정보 -->

		<!--실시간 구인-->
		<div class="realtime_box layer-pop">
			<div class="img">
				<img src="/resources/manager/image/Content/loding.gif" alt="로딩중"/>
			</div>
			<div class="content">
				<p>"실시간 알선 예약 진행중입니다."</p>
				<div class="btn_2ea">
					<button type="button" class="">[재요청]</button>
					<button type="button" class="ok-btn">[취소]</button>
				</div>
			</div>
		</div>
		<!--//실시간 구인-->

		<!--현장추가-->
		<div class="buis_box layer-pop">
			<div class="busi_form">
				<h3>현장 추가</h3>
				<a href="#n" class="ok-btn close"><span class="blind">닫기</span></a>
				
				<!-- 현장매니저 -->
				<c:if test="${managerMode eq 'M'}">
				<ul>
					<li>
						<span class="tit">회사 상호</span>
						<span class="cont">
							<select id="busi_employer_seq">
								
							</select>
							<input type="text" id="busi_employer_name" placeholder="회사상호(개인은 성함)" style="display:none" />
							<button type="button" id="btn_employer_add" class="buis_plus">+</button>
						</span>
					</li>
					<li>
						<span class="tit">현장 주소</span>
						<span class="cont">
							<input type="text" id="busi_work_name" placeholder="예) 마포구 양화로 171" />
							<button type="button" id="busi_addr_confirm" class="btn_check">확인</button>
							
							<input type="hidden" id="busi_mode" value="0" /> <!-- 0:기존 구인처에 현장추가 1:신규구인처에 현장추가 -->
						</span>
					</li>
				</ul>
				</c:if>
				<!-- //현장매니저 -->
				
				<!-- 본사매니저 -->
				<c:if test="${managerMode eq  'E' }">
				<ul>
					<li>
						<span class="tit">현장 주소</span>
						<span class="cont">
							<input type="text" id="busi_work_name" placeholder="예) 마포구 양화로 171" />
							<button type="button" id="busi_addr_confirm" class="btn_check">확인</button>
							<input type="hidden" id="busi_mode" value="3" /> <!-- 3: 구인처에서 현장 추가 -->
							<input type="hidden" id="busi_employer_seq" value="${employer_seq }" />
							
						</span>
					</li>
					<li>
						<span class="tit">현장매니저</span>
						<span class="cont">
							<input type="text" placeholder="이름" id="busi_manager_name"/>
						</span>
					</li>
					<li>
						<span class="tit">연락처</span>
						<span class="cont">
							<input type="tel" placeholder="폰번호" id="busi_manager_phone"/>
						</span>
					</li>
				</ul>
				</c:if>
				<!-- //본사매니저 -->
				
				
				<div class="form_btn btn_2ea">
					<input type="hidden" id="busi_addr" value="" />
					<input type="hidden" id="busi_latlng" value="" />
					<input type="hidden" id="busi_login_type" value="${login_type}" />
					
					<button type="button" class="form_btn_ok" id="busi_ok">추가</button>
					<button type="button" class="form_btn_close ok-btn">닫기</button>
				</div>
			</div>
		</div>
		<!--//현장추가-->
		
		<!--현장정보-->
		<div class="work_box layer-pop">
			<div class="busi_form">
				<h3>현장 정보</h3>
				<a href="#n" class="ok-btn close"><span class="blind">닫기</span></a>
				
				<ul>
					<li>
						<span class="tit2">현 장 명</span>
						<span class="cont2" id="workInfo_workName">
						</span>
					</li>
					<li>
						<span class="tit2">현장담당자</span>
						<span class="cont2" id="workInfo_managerPhone">
						</span>
					</li>
					<li>
						<span class="tit2">도착시간</span>
						<span class="cont2" id="workInfo_startTime">
										
						</span>
					</li>
					<li>
						<span class="tit2">종료시간</span>
						<span class="cont2" id="workInfo_endTime">
										
						</span>
					</li>
					<li>
						<span class="tit2">작업설명</span>
						<span class="cont2" id="workInfo_workComment" style="word-break: break-all;">
										
						</span>
					</li>
					
				<div class="form_btn btn_2ea">
					<button type="button" class="form_btn_close ok-btn">닫기</button>
				</div>
			</div>
		</div>
		<!--//현장정보-->
		

		<!-- 		직종선택 -->
				<div class="sOIpopCon ">
					<div class="specialty_box layer-pop">
						<h3>전문분야</h3>
						<a href="#n" class="ok-btn close"><span class="blind">닫기</span></a>	
						<p class="red_txt">단가는 2019년 기준이며, 계단유무/장비사용에 따라 단가가 조정될 수 있습니다.</p>
						<div class="sOIemp">
							
						
							<c:set var="kindGender" value="-1" />					
										
										<c:forEach items="${kindList}" var="kind" varStatus="status">
                                    		<c:if test="${kind.code_gender ne kindGender}">
    												<c:set var="kindGender" value="${kind.code_gender}" />
    												<c:choose>
													    <c:when test="${kindGender eq '0'}">
													         <div class="man clear">
																<p class="empTit">잡부조공</p>
																<div class="empButton job_btnBox">
													    </c:when>
													     <c:when test="${kindGender eq '1'}">
													       		</div>
															</div>
															<div class="women clear">
																<p class="empTit">여성인력</p>
																<div class="empButton job_btnBox">
													    </c:when>
													    <c:otherwise>
													        	</div>
															</div>
															<div class="tech clear">
																<p class="empTit">기술인력</p>
																<div class="empButton job_btnBox">
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
						<p class="blue_txt">새벽시간, 거리가 먼 현장인 경우 "교통비"가 별도 책정될 수 있습니다.</p>
					</div>
				</div>
	
	

<iframe id="download-ifrm" name="download-ifrm" width="0" height="0" title="다운로드를 위한 빈프레임" frameborder="0" scrolling="no"></iframe>		