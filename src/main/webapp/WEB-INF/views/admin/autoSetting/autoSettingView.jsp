<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/common/css/slider.css' />" media="all"></link>
<script type="text/javascript">
	var optionList = '${optionList }';
	var jobLimitCountDTO = '${jobLimitCountDTO }';
	var viewTimeCnt = '${viewTimeCnt }';
	
	function fn_update() {
		var regExp = /^[0-9]+$/;
		
		if($("#work_max_count").val() == '' || !regExp.test($("#work_max_count").val())) {
			alert("발송 현장 최대 개수를 숫자로만 입력해주세요.");
			
			return false;
		}
		if($("#push_interval").val() == '' || !regExp.test($("#push_interval").val())) {
			alert("AI 발송 인터벌을 숫자로만 입력해주세요.");
			
			return false;
		}
		if($("#worker_max_count").val() == '' || !regExp.test($("#worker_max_count").val())) {
			alert("최대 추출 인원수를 숫자로만 입력해주세요.");
			
			return false;
		}
		if($("#worker_push_max_count").val() == '' || !regExp.test($("#worker_push_max_count").val())) {
			alert("근로자당 AI배정 개수를 숫자로만 입력해주세요.");
			
			return false;
		}
		if($("#contr_distance").val() == '' || !regExp.test($("#contr_distance").val())) {
			alert("보통인부(건설직종)의 최대 숫자로만 배정거리를 입력해주세요.");
			
			return false;
		}
		if($("#non_contr_distance").val() == '' || !regExp.test($("#non_contr_distance").val())) {
			alert("보통인부(건설외직종)의 최대 배정거리를 숫자로만 입력해주세요.");
			
			return false;
		}
		if($("#pore_distance").val() == '' || !regExp.test($("#pore_distance").val())) {
			alert("기공(건설직종)의 최대 배정거리를 숫자로만 입력해주세요.");
			
			return false;
		}
		
		var paramArray = new Array();

		$("input[name=start_time]").each(function(i, e) {
			paramArray[i] = {
				'setting_days' : i,
				'start_time' : $("input[name=start_time]").eq(i).val(),	
				'end_time' : $("input[name=end_time]").eq(i).val(),
				'work_max_count' : $("#work_max_count").val(),
				'push_interval' : $("#push_interval").val(),
				'worker_max_count' : $("#worker_max_count").val(),
				'worker_push_max_count' : $("#worker_push_max_count").val(),
				'contr_distance' : $("#contr_distance").val(),
				'non_contr_distance' : $("#non_contr_distance").val(),
				'pore_distance' : $("#pore_distance").val()
			} 
		});
		
		var _data = JSON.stringify(paramArray);
		
		$.ajax ({
			url : "/admin/autoSetting/updateAutoSetting",
			type : "POST",
			dataType : "JSON",
			data : _data,
			contentType: "application/json",
			success : function(data) {
				if(data.code == "0000") {
					alert("저장이 완료되었습니다.");
				}
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader("AJAX", true);
				$(".wrap-loading").show();
			},
			error: function(e) {
            	if(e.status == "901" ) {
                	location.href = "/admin/login";
            	}else {
                	toastFail('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
            	}
            	
            	$(".wrap-loading").hide();
            },
            complete : function() {
            	$(".wrap-loading").hide();
            }
		});
	};
	
	$(function() {
		$("#onOff").click(function(){
	   		$(".toggles").toggle();
	   		
	   		// 체크여부 확인
	   		if($("#onOff").is(":checked") == true) {
	   			changePower(1);
	   		}else{
	   			changePower(0);
	   		}
		});
	});
	
	function changePower(useYn){
		var _url = "/admin/autoSetting/onOff";
		var _data = {
				function_type : 'A',
				use_yn : useYn
		};
		
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			toastOk("정상적으로 처리 되었습니다.");
    		}else {
    			toastFail(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		alert(data);
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
</script>
	</form>
    <div class="content mgtS mgbL">
		<div class="title">
			<h3>AI 배정 설정</h3>
			<div>
		    	<label class="switch">
					<input type="checkbox" id="onOff" ${onOffDTO.use_yn eq '1' ? 'checked' : '' }>
					<span class="slider round"></span>
				</label>
				<p class="toggles" ${onOffDTO.use_yn == 1 ? 'style="display:none;"' : '' }>OFF</p>
				<p class="toggles" ${onOffDTO.use_yn == 1 ? '' : 'style="display:none;"' }>ON</p>
			</div>
		</div>
		<form id="optionFrm" name="optionFrm">
			
			<table class="bd-list" summary="AI 배정 설정 테이블" style="width: 30%; display: none;">
			    <colgroup>
				    <col width="70px" />
				    <col width="100px" />
				    <col width="100px" />
			    </colgroup>
		    	<thead>
					<tr>
						<th>요일</th>
						<th>시작 시간</th>
						<th>종료 시간</th>
					</tr>
				</thead>
				<tbody id="listBody">
					<c:choose>
						<c:when test="${autoSettingList ne null }">
							<c:forEach var="item" items="${autoSettingList }" varStatus="status">
								<tr>
									<c:choose>
										<c:when test="${item.setting_days eq 1 }">
											<td>월요일</td>								
										</c:when>
										<c:when test="${item.setting_days eq 2 }">
											<td>화요일</td>
										</c:when>
										<c:when test="${item.setting_days eq 3 }">
											<td>수요일</td>
										</c:when>
										<c:when test="${item.setting_days eq 4 }">
											<td>목요일</td>
										</c:when>
										<c:when test="${item.setting_days eq 5 }">
											<td>금요일</td>
										</c:when>
										<c:when test="${item.setting_days eq 6 }">
											<td>토요일</td>
										</c:when>
										<c:when test="${item.setting_days eq 0 }">
											<td>일요일</td>
										</c:when>
									</c:choose>
									<td>
										<input type="text" class="inp-field wid70" id="start_time${status.index }" name="start_time" value="${item.start_time }">
									</td>
									<td>
										<input type="text" class="inp-field wid70" id="end_time${status.index }" name="end_time" value="${item.end_time }">
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td>월요일</td>	
								<td>
									<input type="text" class="inp-field wid70" id="start_time1" name="start_time" value="1000">
								</td>
								<td>
									<input type="text" class="inp-field wid70" id="end_time1" name="end_time" value="1900">
								</td>							
							</tr>
							<tr>
								<td>화요일</td>
								<td>
									<input type="text" class="inp-field wid70" id="start_time2" name="start_time" value="1000">
								</td>
								<td>
									<input type="text" class="inp-field wid70" id="end_time2" name="end_time" value="1900">
								</td>
							</tr>
							<tr>
								<td>수요일</td>
								<td>
									<input type="text" class="inp-field wid70" id="start_time3" name="start_time" value="1000">
								</td>
								<td>
									<input type="text" class="inp-field wid70" id="end_time3" name="end_time" value="1900">
								</td>
							</tr>
							<tr>
								<td>목요일</td>
								<td>
									<input type="text" class="inp-field wid70" id="start_time4" name="start_time" value="1000">
								</td>
								<td>
									<input type="text" class="inp-field wid70" id="end_time4" name="end_time" value="1900">
								</td>
							</tr>
							<tr>
								<td>금요일</td>
								<td>
									<input type="text" class="inp-field wid70" id="start_time5" name="start_time" value="1000">
								</td>
								<td>
									<input type="text" class="inp-field wid70" id="end_time5" name="end_time" value="1900">
								</td>
							</tr>
							<tr>
								<td>토요일</td>
								<td>
									<input type="text" class="inp-field wid70" id="start_time6" name="start_time" value="0000">
								</td>
								<td>
									<input type="text" class="inp-field wid70" id="end_time6" name="end_time" value="0000">
								</td>
							</tr>
							<tr>
								<td>일요일</td>
								<td>
									<input type="text" class="inp-field wid70" id="start_time0" name="start_time" value="0000">
								</td>
								<td>
									<input type="text" class="inp-field wid70" id="end_time0" name="end_time" value="0000">
								</td>
							</tr>
								
						</c:otherwise>
					</c:choose>
					
		    	</tbody>
			</table>
			
			<table class="bd-list" summary="발송 현장 최대 개수 테이블" style="width: 10%;">
			    <colgroup>
				    <col width="150px" />
				    <col width="200px" />
			    </colgroup>
		    	<thead>
					<tr>
						<th colspan="2">발송 현장 최대 개수(최대 n개 제한)</th>
						<th class="last"></th>
					</tr>
				</thead>
				<tbody id="limitBody">
					<tr>
			     		<td>현장</td>
						<td>
							<c:choose>
								<c:when test="${autoSettingList ne null }">
									<input type="text" class="inp-field wid70" id="work_max_count" name="work_max_count" value="${autoSettingList[0].work_max_count }">								
								</c:when>
								<c:otherwise>
									<input type="text" class="inp-field wid70" id="work_max_count" name="work_max_count" value="50">								
								</c:otherwise>
							</c:choose>
							<label>개</label>
						</td>
					</tr>
		    	</tbody>
			</table>
			
			<table class="bd-list" summary="AI 발송 인터벌 테이블" style="width: 10%;">
			    <colgroup>
				    <col width="150px" />
				    <col width="200px" />
			    </colgroup>
		    	<thead>
					<tr>
						<th colspan="2">AI 발송 인터벌(최소 n초 이상)</th>
						<th></th>
					</tr>
				</thead>
				<tbody id="scoreBody">
					<tr>
						<td>간격</td>
						<td>
							<c:choose>
								<c:when test="${autoSettingList ne null }">
									<input type="text" class="inp-field wid70" id="push_interval" name="push_interval" value="${autoSettingList[0].push_interval }">
								</c:when>
								<c:otherwise>
									<input type="text" class="inp-field wid70" id="push_interval" name="push_interval" value="30">								
								</c:otherwise>
							</c:choose>
							<label>초</label>
						</td>
					</tr>
		    	</tbody>
			</table>
			
			<table class="bd-list" summary="최대 추출 인원수 테이블" style="width: 10%;">
			    <colgroup>
				    <col width="150px" />
				    <col width="200px" />
			    </colgroup>
		    	<thead>
					<tr>
						<th colspan="2">최대 추출 인원수</th>
						<th class="last"></th>
					</tr>
				</thead>
				<tbody id="scoreBody">
					<tr>
						<td>인원수</td>
						<td>
							<c:choose>
								<c:when test="${autoSettingList ne null }">
									<input type="text" class="inp-field wid70" id="worker_max_count" name="worker_max_count" value="${autoSettingList[0].worker_max_count }">
								</c:when>
								<c:otherwise>
									<input type="text" class="inp-field wid70" id="worker_max_count" name="worker_max_count" value="100">
								</c:otherwise>
							</c:choose>
							<label>명</label>
						</td>
					</tr>
		    	</tbody>
			</table>
			
			<table class="bd-list" summary="근로자당 AI배정 개수 제한 테이블" style="width: 10%;">
			    <colgroup>
				    <col width="150px" />
				    <col width="200px" />
			    </colgroup>
		    	<thead>
					<tr>
						<th colspan="2">근로자당 AI배정 개수 제한</th>
						<th class="last"></th>
					</tr>
				</thead>
				<tbody id="scoreBody">
					<tr>
						<td>하루 최대</td>
						<td>
							<c:choose>
								<c:when test="${autoSettingList ne null }">
									<input type="text" class="inp-field wid70" id="worker_push_max_count" name="worker_push_max_count" value="${autoSettingList[0].worker_push_max_count }">
								</c:when>
								<c:otherwise>
									<input type="text" class="inp-field wid70" id="worker_push_max_count" name="worker_push_max_count" value="20">
								</c:otherwise>
							</c:choose>
							<label>개</label>
						</td>
					</tr>
		    	</tbody>
			</table>
			
			<table class="bd-list" summary="최대 배정거리 설정 테이블" style="width: 10%;">
			    <colgroup>
				    <col width="150px" />
				    <col width="200px" />
			    </colgroup>
		    	<thead>
					<tr>
						<th colspan="2">최대 배정거리 설정</th>
						<th class="last"></th>
					</tr>
				</thead>
				<tbody id="scoreBody">
					<tr>
						<td>보통인부(건설직종)</td>
						<td>
							<c:choose>
								<c:when test="${autoSettingList ne null }">
									<input type="text" class="inp-field wid70" id="contr_distance" name="contr_distance" value="${autoSettingList[0].contr_distance }">
								</c:when>
								<c:otherwise>
									<input type="text" class="inp-field wid70" id="contr_distance" name="contr_distance" value="30">
								</c:otherwise>
							</c:choose>
							<label>km</label>
						</td>
					</tr>
					<tr>
						<td>보통인부(건설외직종)</td>
						<td>
							<c:choose>
								<c:when test="${autoSettingList ne null }">
									<input type="text" class="inp-field wid70" id="non_contr_distance" name="non_contr_distance" value="${autoSettingList[0].non_contr_distance }">
								</c:when>
								<c:otherwise>
									<input type="text" class="inp-field wid70" id="non_contr_distance" name="non_contr_distance" value="30">								
								</c:otherwise>
							</c:choose>
							<label>km</label>
						</td>
					</tr>
					<tr>
						<td>기공(건설직종)</td>
						<td>
							<c:choose>
								<c:when test="${autoSettingList ne null }">
									<input type="text" class="inp-field wid70" id="pore_distance" name="pore_distance" value="${autoSettingList[0].pore_distance }">
								</c:when>
								<c:otherwise>
									<input type="text" class="inp-field wid70" id="pore_distance" name="pore_distance" value="50">								
								</c:otherwise>
							</c:choose>
							<label>km</label>
						</td>
					</tr>
		    	</tbody>
			</table>
		</form>
	
		<div class="btn-module mgtL">
			<a href="javascript:void(0);" id="btnAdd" class="btnStyle04" onclick="fn_update()">
				저장
			</a>
		</div>
	</div>
