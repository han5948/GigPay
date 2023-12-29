<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="<c:url value="/resources/cms/js/paging.js" />" charset="utf-8"></script>
<script type="text/javascript">
	window.onload = function () {
		//setDayType('start_reg_date', 'end_reg_date', 'all');
		
		//setDayType(1, 'to_ilbo_date', 'day');
		
	    if (window.Notification) {
	        Notification.requestPermission();
	    }
	    
	}
	
	$(document).ready(function() {
	});
	
	function fn_update() {
    	$("#contentFrm").ajaxForm({
			url : "/admin/ilboSetting/updateIlboSetting",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
				if ( data.code == "0000" ) {
					toastOk("정상적으로 변경 되었습니다.");
				} else {
					toastFail("시스템 장애1");
					
					$(".wrap-loading").hide();
					
					location.reload();
				}
			} ,
			beforeSend : function(xhr) {
				 xhr.setRequestHeader("AJAX", true);
				 $(".wrap-loading").show();
			},
	    	error : function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					toastFail("시스템 장애2");
				}
				
				$(".wrap-loading").hide();
				
				location.reload();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		}).submit();
	}
	
	function fn_reset() {
		$.ajax ({
			url : "/admin/ilboSetting/updateIlboSettingReset",
			type : "POST",
			dataType : "json",
			data : {},
			success : function(data) {
				if(data.code == "0000") {
					toastOk("초기화 되었습니다.");
					
					location.reload();
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
	}
	
</script>

	<div class="content mgtS mgbL">
		<div class="title">
			<h3>일일대장 설정</h3>
		</div>
		
		<table class="bd-list" summary="그룹 코드 리스트">
		    <colgroup>
			    <col width="100px" />
			    <col width="100px" />
			    <col width="300px" />
			    <col width="200px" />
		    </colgroup>
	    	<thead>
				<tr>
					<th>필드 명</th>
					<th>사이즈</th>
					<th class="last">숨김여부</th>
				</tr>
			</thead>
			<tbody id="listBody">
	     		<tr style="text-align: center;">
	     			<td>일자</td>
	     			<td>
	     				<input type="text" name="ilbo_date_width" value="${ilboSettingDTO.ilbo_date_width eq null ? '80' : ilboSettingDTO.ilbo_date_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_date" value="1" <c:if test="${ilboSettingDTO.ilbo_date eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_date" value="0" <c:if test="${ilboSettingDTO.ilbo_date eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>일일대장 지점</td>
	     			<td>
	     				<input type="text" name="company_name_width" value="${ilboSettingDTO.company_name_width eq null ? '80' : ilboSettingDTO.company_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="company_name" value="1" <c:if test="${ilboSettingDTO.company_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="company_name" value="0" <c:if test="${ilboSettingDTO.company_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구직자 소속[구직자]</td>
	     			<td>
	     				<input type="text" name="worker_company_name_width" value="${ilboSettingDTO.worker_company_name_width eq null ? '100' : ilboSettingDTO.worker_company_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="worker_company_name" value="1" <c:if test="${ilboSettingDTO.worker_company_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="worker_company_name" value="0" <c:if test="${ilboSettingDTO.worker_company_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>소유[구직자]</td>
	     			<td>
	     				<input type="text" name="worker_owner_width" value="${ilboSettingDTO.worker_owner_width eq null ? '80' : ilboSettingDTO.worker_owner_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="worker_owner" value="1" <c:if test="${ilboSettingDTO.worker_owner eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="worker_owner" value="0" <c:if test="${ilboSettingDTO.worker_owner eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>상세[구직자]</td>
	     			<td>
	     				<input type="text" name="ilbo_worker_view_width" value="${ilboSettingDTO.ilbo_worker_view_width eq null ? '30' : ilboSettingDTO.ilbo_worker_view_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_worker_view" value="1" <c:if test="${ilboSettingDTO.ilbo_worker_view eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_worker_view" value="0" <c:if test="${ilboSettingDTO.ilbo_worker_view eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>대장[구직자]</td>
	     			<td>
	     				<input type="text" name="worker_ilbo_width" value="${ilboSettingDTO.worker_ilbo_width eq null ? '30' : ilboSettingDTO.worker_ilbo_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="worker_ilbo" value="1" <c:if test="${ilboSettingDTO.worker_ilbo eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="worker_ilbo" value="0" <c:if test="${ilboSettingDTO.worker_ilbo eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구직자[구직자]</td>
	     			<td>
	     				<input type="text" name="ilbo_worker_name_width" value="${ilboSettingDTO.ilbo_worker_name_width eq null ? '80' : ilboSettingDTO.ilbo_worker_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_worker_name" value="1" <c:if test="${ilboSettingDTO.ilbo_worker_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_worker_name" value="0" <c:if test="${ilboSettingDTO.ilbo_worker_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>특징[구직자]</td>
	     			<td>
	     				<input type="text" name="ilbo_worker_feature_width" value="${ilboSettingDTO.ilbo_worker_feature_width eq null ? '100' : ilboSettingDTO.ilbo_worker_feature_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_worker_feature" value="1" <c:if test="${ilboSettingDTO.ilbo_worker_feature eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_worker_feature" value="0" <c:if test="${ilboSettingDTO.ilbo_worker_feature eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구직자 메모[구직자]</td>
	     			<td>
	     				<input type="text" name="ilbo_worker_memo_width" value="${ilboSettingDTO.ilbo_worker_memo_width eq null ? '150' : ilboSettingDTO.ilbo_worker_memo_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_worker_memo" value="1" <c:if test="${ilboSettingDTO.ilbo_worker_memo eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_worker_memo" value="0" <c:if test="${ilboSettingDTO.ilbo_worker_memo eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>배정[구직자]</td>
	     			<td>
	     				<input type="text" name="ilbo_assign_type_width" value="${ilboSettingDTO.ilbo_assign_type_width eq null ? '70' : ilboSettingDTO.ilbo_assign_type_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_assign_type" value="1" <c:if test="${ilboSettingDTO.ilbo_assign_type eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_assign_type" value="0" <c:if test="${ilboSettingDTO.ilbo_assign_type eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구직자 정보[구직자]</td>
	     			<td>
	     				<input type="text" name="ilbo_worker_info_width" value="${ilboSettingDTO.ilbo_worker_info_width eq null ? '260' : ilboSettingDTO.ilbo_worker_info_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_worker_info" value="1" <c:if test="${ilboSettingDTO.ilbo_worker_info eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_worker_info" value="0" <c:if test="${ilboSettingDTO.ilbo_worker_info eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>상태[구직자]</td>
	     			<td>
	     				<input type="text" name="ilbo_worker_status_info_width" value="${ilboSettingDTO.ilbo_worker_status_info_width eq null ? '50' : ilboSettingDTO.ilbo_worker_status_info_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_worker_status_info" value="1" <c:if test="${ilboSettingDTO.ilbo_worker_status_info eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_worker_status_info" value="0" <c:if test="${ilboSettingDTO.ilbo_worker_status_info eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>일보 메모</td>
	     			<td>
	     				<input type="text" name="ilbo_memo_width" value="${ilboSettingDTO.ilbo_memo_width eq null ? '150' : ilboSettingDTO.ilbo_memo_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_memo" value="1" <c:if test="${ilboSettingDTO.ilbo_memo eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_memo" value="0" <c:if test="${ilboSettingDTO.ilbo_memo eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구인처 소속</td>
	     			<td>
	     				<input type="text" name="work_company_name_width" value="${ilboSettingDTO.work_company_name_width eq null ? '100' : ilboSettingDTO.work_company_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="work_company_name" value="1" <c:if test="${ilboSettingDTO.work_company_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="work_company_name" value="0" <c:if test="${ilboSettingDTO.work_company_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>소유[구인처]</td>
	     			<td>
	     				<input type="text" name="work_owner_width" value="${ilboSettingDTO.work_owner_width eq null ? '80' : ilboSettingDTO.work_owner_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="work_owner" value="1" <c:if test="${ilboSettingDTO.work_owner eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="work_owner" value="0" <c:if test="${ilboSettingDTO.work_owner eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>상세[구인처]</td>
	     			<td>
	     				<input type="text" name="ilbo_employer_view_width" value="${ilboSettingDTO.ilbo_employer_view_width eq null ? '30' : ilboSettingDTO.ilbo_employer_view_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_employer_view" value="1" <c:if test="${ilboSettingDTO.ilbo_employer_view eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_employer_view" value="0" <c:if test="${ilboSettingDTO.ilbo_employer_view eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>대장[구인처]</td>
	     			<td>
	     				<input type="text" name="employer_ilbo_width" value="${ilboSettingDTO.employer_ilbo_width eq null ? '30' : ilboSettingDTO.employer_ilbo_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="employer_ilbo" value="1" <c:if test="${ilboSettingDTO.employer_ilbo eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="employer_ilbo" value="0" <c:if test="${ilboSettingDTO.employer_ilbo eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구인처[구인처]</td>
	     			<td>
	     				<input type="text" name="employer_name_width" value="${ilboSettingDTO.employer_name_width eq null ? '120' : ilboSettingDTO.employer_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="employer_name" value="1" <c:if test="${ilboSettingDTO.employer_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="employer_name" value="0" <c:if test="${ilboSettingDTO.employer_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>특징[구인처]</td>
	     			<td>
	     				<input type="text" name="ilbo_employer_feature_width" value="${ilboSettingDTO.ilbo_employer_feature_width eq null ? '150' : ilboSettingDTO.ilbo_employer_feature_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_employer_feature" value="1" <c:if test="${ilboSettingDTO.ilbo_employer_feature eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_employer_feature" value="0" <c:if test="${ilboSettingDTO.ilbo_employer_feature eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>지도[현장]</td>
	     			<td>
	     				<input type="text" name="addr_edit_width" value="${ilboSettingDTO.addr_edit_width eq null ? '30' : ilboSettingDTO.addr_edit_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="addr_edit" value="1" <c:if test="${ilboSettingDTO.addr_edit eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="addr_edit" value="0" <c:if test="${ilboSettingDTO.addr_edit eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>위치[현장]</td>
	     			<td>
	     				<input type="text" name="addr_location_width" value="${ilboSettingDTO.addr_location_width eq null ? '30' : ilboSettingDTO.addr_location_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="addr_location" value="1" <c:if test="${ilboSettingDTO.addr_location eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="addr_location" value="0" <c:if test="${ilboSettingDTO.addr_location eq '0' }">checked="checked"</c:if>>숨김 
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>순서[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_info_width" value="${ilboSettingDTO.ilbo_work_info_width eq null ? '50' : ilboSettingDTO.ilbo_work_info_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_info" value="1" <c:if test="${ilboSettingDTO.ilbo_work_info eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_info" value="0" <c:if test="${ilboSettingDTO.ilbo_work_info eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>오더종류[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_order_name_width" value="${ilboSettingDTO.ilbo_work_order_name_width eq null ? '70' : ilboSettingDTO.ilbo_work_order_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_order_name" value="1" <c:if test="${ilboSettingDTO.ilbo_work_order_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_order_name" value="0" <c:if test="${ilboSettingDTO.ilbo_work_order_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>공개[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_use_info_width" value="${ilboSettingDTO.ilbo_use_info_width eq null ? '60' : ilboSettingDTO.ilbo_use_info_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_use_info" value="1" <c:if test="${ilboSettingDTO.ilbo_use_info eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_use_info" value="0" <c:if test="${ilboSettingDTO.ilbo_use_info eq '0' }">checked="checked"</c:if>>숨김 
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>상태[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_status_info_width" value="${ilboSettingDTO.ilbo_status_info_width eq null ? '60' : ilboSettingDTO.ilbo_status_info_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_status_info" value="1" <c:if test="${ilboSettingDTO.ilbo_status_info eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_status_info" value="0" <c:if test="${ilboSettingDTO.ilbo_status_info eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>현장조식[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_breakfast_yn_width" value="${ilboSettingDTO.ilbo_work_breakfast_yn_width eq null ? '70' : ilboSettingDTO.ilbo_work_breakfast_yn_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_breakfast_yn" value="1" <c:if test="${ilboSettingDTO.ilbo_work_breakfast_yn eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_breakfast_yn" value="0" <c:if test="${ilboSettingDTO.ilbo_work_breakfast_yn eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>최저나이[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_age_min_width" value="${ilboSettingDTO.ilbo_work_age_min_width eq null ? '60' : ilboSettingDTO.ilbo_work_age_min_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_age_min" value="1" <c:if test="${ilboSettingDTO.ilbo_work_age_min eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_age_min" value="0" <c:if test="${ilboSettingDTO.ilbo_work_age_min eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>최고나이[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_age_width" value="${ilboSettingDTO.ilbo_work_age_width eq null ? '50' : ilboSettingDTO.ilbo_work_age_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_age" value="1" <c:if test="${ilboSettingDTO.ilbo_work_age eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_age" value="0" <c:if test="${ilboSettingDTO.ilbo_work_age eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>혈압[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_blood_pressure_width" value="${ilboSettingDTO.ilbo_work_blood_pressure_width eq null ? '60' : ilboSettingDTO.ilbo_work_blood_pressure_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_blood_pressure" value="1" <c:if test="${ilboSettingDTO.ilbo_work_blood_pressure eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_blood_pressure" value="0" <c:if test="${ilboSettingDTO.ilbo_work_blood_pressure eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>주차여부</td>
	     			<td>
	     				<input type="text" name="parking_option_width" value="${ilboSettingDTO.parking_option_width eq null ? '100' : ilboSettingDTO.parking_option_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="parking_option" value="1" <c:if test="${ilboSettingDTO.parking_option eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="parking_option" value="0" <c:if test="${ilboSettingDTO.parking_option eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>대리수령 양식[현장]</td>
	     			<td>
	     				<input type="text" name="receive_contract_seq_width" value="${ilboSettingDTO.receive_contract_seq_width eq null ? '60' : ilboSettingDTO.receive_contract_seq_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="receive_contract_seq" value="1" <c:if test="${ilboSettingDTO.receive_contract_seq eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="receive_contract_seq" value="0" <c:if test="${ilboSettingDTO.receive_contract_seq eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>근로계약 양식[현장]</td>
	     			<td>
	     				<input type="text" name="labor_contract_seq_width" value="${ilboSettingDTO.labor_contract_seq_width eq null ? '60' : ilboSettingDTO.labor_contract_seq_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="labor_contract_seq" value="1" <c:if test="${ilboSettingDTO.labor_contract_seq eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="labor_contract_seq" value="0" <c:if test="${ilboSettingDTO.labor_contract_seq eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>현장 매니저명[현장]</td>
	     			<td>
	     				<input type="text" name="manager_name_width" value="${ilboSettingDTO.manager_name_width eq null ? '100' : ilboSettingDTO.manager_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="manager_name" value="1" <c:if test="${ilboSettingDTO.manager_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="manager_name" value="0" <c:if test="${ilboSettingDTO.manager_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>현장 매니저 번호[현장]</td>
	     			<td>
	     				<input type="text" name="manager_phone_width" value="${ilboSettingDTO.manager_phone_width eq null ? '100' : ilboSettingDTO.manager_phone_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="manager_phone" value="1" <c:if test="${ilboSettingDTO.manager_phone eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="manager_phone" value="0" <c:if test="${ilboSettingDTO.manager_phone eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>현장담당[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_manager_name_width" value="${ilboSettingDTO.ilbo_work_manager_name_width eq null ? '100' : ilboSettingDTO.ilbo_work_manager_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_manager_name" value="1" <c:if test="${ilboSettingDTO.ilbo_work_manager_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_manager_name" value="0" <c:if test="${ilboSettingDTO.ilbo_work_manager_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>현장 전화[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_manager_phone_width" value="${ilboSettingDTO.ilbo_work_manager_phone_width eq null ? '100' : ilboSettingDTO.ilbo_work_manager_phone_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_manager_phone" value="1" <c:if test="${ilboSettingDTO.ilbo_work_manager_phone eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_manager_phone" value="0" <c:if test="${ilboSettingDTO.ilbo_work_manager_phone eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>도착시간[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_arrival_width" value="${ilboSettingDTO.ilbo_work_arrival_width eq null ? '50' : ilboSettingDTO.ilbo_work_arrival_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_arrival" value="1" <c:if test="${ilboSettingDTO.ilbo_work_arrival eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_arrival" value="0" <c:if test="${ilboSettingDTO.ilbo_work_arrival eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>마감시간[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_finish_width" value="${ilboSettingDTO.ilbo_work_finish_width eq null ? '50' : ilboSettingDTO.ilbo_work_finish_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_finish" value="1" <c:if test="${ilboSettingDTO.ilbo_work_finish eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_finish" value="0" <c:if test="${ilboSettingDTO.ilbo_work_finish eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>휴게시간[현장]</td>
	     			<td>
	     				<input type="text" name="ilbo_work_breaktime_width" value="${ilboSettingDTO.ilbo_work_breaktime_width eq null ? '100' : ilboSettingDTO.ilbo_work_breaktime_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_work_breaktime" value="1" <c:if test="${ilboSettingDTO.ilbo_work_breaktime eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_work_breaktime" value="0" <c:if test="${ilboSettingDTO.ilbo_work_breaktime eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>대장</td>
	     			<td>
	     				<input type="text" name="work_ilbo_width" value="${ilboSettingDTO.work_ilbo_width eq null ? '30' : ilboSettingDTO.work_ilbo_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="work_ilbo" value="1" <c:if test="${ilboSettingDTO.work_ilbo eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="work_ilbo" value="0" <c:if test="${ilboSettingDTO.work_ilbo eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>현장</td>
	     			<td>
	     				<input type="text" name="work_name_width" value="${ilboSettingDTO.work_name_width eq null ? '120' : ilboSettingDTO.work_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="work_name" value="1" <c:if test="${ilboSettingDTO.work_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="work_name" value="0" <c:if test="${ilboSettingDTO.work_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>직종</td>
	     			<td>
	     				<input type="text" name="ilbo_kind_name_width" value="${ilboSettingDTO.ilbo_kind_name_width eq null ? '80' : ilboSettingDTO.ilbo_kind_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_kind_name" value="1" <c:if test="${ilboSettingDTO.ilbo_kind_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_kind_name" value="0" <c:if test="${ilboSettingDTO.ilbo_kind_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>작업 설명</td>
	     			<td>
	     				<input type="text" name="ilbo_job_comment_width" value="${ilboSettingDTO.ilbo_job_comment_width eq null ? '150' : ilboSettingDTO.ilbo_job_comment_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_job_comment" value="1" <c:if test="${ilboSettingDTO.ilbo_job_comment eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_job_comment" value="0" <c:if test="${ilboSettingDTO.ilbo_job_comment eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구인 상세</td>
	     			<td>
	     				<input type="text" name="employer_detail_width" value="${ilboSettingDTO.employer_detail_width eq null ? '150' : ilboSettingDTO.employer_detail_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="employer_detail" value="1" <c:if test="${ilboSettingDTO.employer_detail eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="employer_detail" value="0" <c:if test="${ilboSettingDTO.employer_detail eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>소장 메모</td>
	     			<td>
	     				<input type="text" name="ilbo_chief_memo_width" value="${ilboSettingDTO.ilbo_chief_memo_width eq null ? '150' : ilboSettingDTO.ilbo_chief_memo_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_chief_memo" value="1" <c:if test="${ilboSettingDTO.ilbo_chief_memo eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_chief_memo" value="0" <c:if test="${ilboSettingDTO.ilbo_chief_memo eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>경력옵션 명</td>
	     			<td>
	     				<input type="text" name="ilbo_career_name_width" value="${ilboSettingDTO.ilbo_career_name_width eq null ? '150' : ilboSettingDTO.ilbo_career_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_career_name" value="1" <c:if test="${ilboSettingDTO.ilbo_career_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_career_name" value="0" <c:if test="${ilboSettingDTO.ilbo_career_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>노임수령[금액]</td>
	     			<td>
	     				<input type="text" name="ilbo_income_name_width" value="${ilboSettingDTO.ilbo_income_name_width eq null ? '70' : ilboSettingDTO.ilbo_income_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_income_name" value="1" <c:if test="${ilboSettingDTO.ilbo_income_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_income_name" value="0" <c:if test="${ilboSettingDTO.ilbo_income_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>수령일자[금액]</td>
	     			<td>
	     				<input type="text" name="ilbo_income_time_width" value="${ilboSettingDTO.ilbo_income_time_width eq null ? '70' : ilboSettingDTO.ilbo_income_time_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_income_time" value="1" <c:if test="${ilboSettingDTO.ilbo_income_time eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_income_time" value="0" <c:if test="${ilboSettingDTO.ilbo_income_time eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>단가[금액]</td>
	     			<td>
	     				<input type="text" name="ilbo_unit_price_width" value="${ilboSettingDTO.ilbo_unit_price_width eq null ? '70' : ilboSettingDTO.ilbo_unit_price_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_unit_price" value="1" <c:if test="${ilboSettingDTO.ilbo_unit_price eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_unit_price" value="0" <c:if test="${ilboSettingDTO.ilbo_unit_price eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>수수료[금액]</td>
	     			<td>
	     				<input type="text" name="ilbo_fee_width" value="${ilboSettingDTO.ilbo_fee_width eq null ? '70' : ilboSettingDTO.ilbo_fee_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_fee" value="1" <c:if test="${ilboSettingDTO.ilbo_fee eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_fee" value="0" <c:if test="${ilboSettingDTO.ilbo_fee eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>쉐어료[금액]</td>
	     			<td>
	     				<input type="text" name="share_fee_width" value="${ilboSettingDTO.share_fee_width eq null ? '70' : ilboSettingDTO.share_fee_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="share_fee" value="1" <c:if test="${ilboSettingDTO.share_fee eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="share_fee" value="0" <c:if test="${ilboSettingDTO.share_fee eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>공제금[금액]</td>
	     			<td>
	     				<input type="text" name="ilbo_deduction_width" value="${ilboSettingDTO.ilbo_deduction_width eq null ? '70' : ilboSettingDTO.ilbo_deduction_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_deduction" value="1" <c:if test="${ilboSettingDTO.ilbo_deduction eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_deduction" value="0" <c:if test="${ilboSettingDTO.ilbo_deduction eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구직자[금액]</td>
	     			<td>
	     				<input type="text" name="ilbo_pay_width" value="${ilboSettingDTO.ilbo_pay_width eq null ? '70' : ilboSettingDTO.ilbo_pay_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_pay" value="1" <c:if test="${ilboSettingDTO.ilbo_pay eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_pay" value="0" <c:if test="${ilboSettingDTO.ilbo_pay eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>공제액 계[금액]</td>
	     			<td>
	     				<input type="text" name="deductible_sum_width" value="${ilboSettingDTO.deductible_sum_width eq null ? '60' : ilboSettingDTO.deductible_sum_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="deductible_sum" value="1" <c:if test="${ilboSettingDTO.deductible_sum eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="deductible_sum" value="0" <c:if test="${ilboSettingDTO.deductible_sum eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>상세[금액]</td>
	     			<td>
	     				<input type="text" name="deductible_info_width" value="${ilboSettingDTO.deductible_info_width eq null ? '30' : ilboSettingDTO.deductible_info_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="deductible_info" value="1" <c:if test="${ilboSettingDTO.deductible_info eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="deductible_info" value="0" <c:if test="${ilboSettingDTO.deductible_info eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>임금수령액[금액]</td>
	     			<td>
	     				<input type="text" name="wages_received_width" value="${ilboSettingDTO.wages_received_width eq null ? '70' : ilboSettingDTO.wages_received_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="wages_received" value="1" <c:if test="${ilboSettingDTO.wages_received eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="wages_received" value="0" <c:if test="${ilboSettingDTO.wages_received eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>노임지급[금액]</td>
	     			<td>
	     				<input type="text" name="ilbo_pay_name_width" value="${ilboSettingDTO.ilbo_pay_name_width eq null ? '70' : ilboSettingDTO.ilbo_pay_name_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_pay_name" value="1" <c:if test="${ilboSettingDTO.ilbo_pay_name eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_pay_name" value="0" <c:if test="${ilboSettingDTO.ilbo_pay_name eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>지급일자[금액]</td>
	     			<td>
	     				<input type="text" name="ilbo_pay_time_width" value="${ilboSettingDTO.ilbo_pay_time_width eq null ? '70' : ilboSettingDTO.ilbo_pay_time_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_pay_time" value="1" <c:if test="${ilboSettingDTO.ilbo_pay_time eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_pay_time" value="0" <c:if test="${ilboSettingDTO.ilbo_pay_time eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구인자 소개요금[금액]</td>
	     			<td>
	     				<input type="text" name="work_fee_width" value="${ilboSettingDTO.work_fee_width eq null ? '90' : ilboSettingDTO.work_fee_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="work_fee" value="1" <c:if test="${ilboSettingDTO.work_fee eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="work_fee" value="0" <c:if test="${ilboSettingDTO.work_fee eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구직자 소개요금[금액]</td>
	     			<td>
	     				<input type="text" name="worker_fee_width" value="${ilboSettingDTO.worker_fee_width eq null ? '90' : ilboSettingDTO.worker_fee_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="worker_fee" value="1" <c:if test="${ilboSettingDTO.worker_fee eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="worker_fee" value="0" <c:if test="${ilboSettingDTO.worker_fee eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>소개요금 정보[금액]</td>
	     			<td>
	     				<input type="text" name="fee_info_width" value="${ilboSettingDTO.fee_info_width eq null ? '150' : ilboSettingDTO.fee_info_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="fee_info" value="1" <c:if test="${ilboSettingDTO.fee_info eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="fee_info" value="0" <c:if test="${ilboSettingDTO.fee_info eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구직자 소개요금 정보[금액]</td>
	     			<td>
	     				<input type="text" name="pay_info_width" value="${ilboSettingDTO.pay_info_width eq null ? '150' : ilboSettingDTO.pay_info_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="pay_info" value="1" <c:if test="${ilboSettingDTO.pay_info eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="pay_info" value="0" <c:if test="${ilboSettingDTO.pay_info eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>입출금 메모[금액]</td>
	     			<td>
	     				<input type="text" name="ilbo_income_memo_width" value="${ilboSettingDTO.ilbo_income_memo_width eq null ? '100' : ilboSettingDTO.ilbo_income_memo_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="ilbo_income_memo" value="1" <c:if test="${ilboSettingDTO.ilbo_income_memo eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="ilbo_income_memo" value="0" <c:if test="${ilboSettingDTO.ilbo_income_memo eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구인처 평가</td>
	     			<td>
	     				<input type="text" name="employer_rating_width" value="${ilboSettingDTO.employer_rating_width eq null ? '100' : ilboSettingDTO.employer_rating_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="employer_rating" value="1" <c:if test="${ilboSettingDTO.employer_rating eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="employer_rating" value="0" <c:if test="${ilboSettingDTO.employer_rating eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>현장 평가</td>
	     			<td>
	     				<input type="text" name="manager_rating_width" value="${ilboSettingDTO.manager_rating_width eq null ? '100' : ilboSettingDTO.manager_rating_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="manager_rating" value="1" <c:if test="${ilboSettingDTO.manager_rating eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="manager_rating" value="0" <c:if test="${ilboSettingDTO.manager_rating eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>구직자 평가</td>
	     			<td>
	     				<input type="text" name="evaluate_grade_width" value="${ilboSettingDTO.evaluate_grade_width eq null ? '100' : ilboSettingDTO.evaluate_grade_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="evaluate_grade" value="1" <c:if test="${ilboSettingDTO.evaluate_grade eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="evaluate_grade" value="0" <c:if test="${ilboSettingDTO.evaluate_grade eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>대리수령[서명상태]</td>
	     			<td>
	     				<input type="text" name="surrogate_width" value="${ilboSettingDTO.surrogate_width eq null ? '80' : ilboSettingDTO.surrogate_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="surrogate" value="1" <c:if test="${ilboSettingDTO.surrogate eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="surrogate" value="0" <c:if test="${ilboSettingDTO.surrogate eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>근로계약[서명상태]</td>
	     			<td>
	     				<input type="text" name="labor_contract_width" value="${ilboSettingDTO.labor_contract_width eq null ? '80' : ilboSettingDTO.labor_contract_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="labor_contract" value="1" <c:if test="${ilboSettingDTO.labor_contract eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="labor_contract" value="0" <c:if test="${ilboSettingDTO.labor_contract eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>상태</td>
	     			<td>
	     				<input type="text" name="use_yn_width" value="${ilboSettingDTO.use_yn_width eq null ? '60' : ilboSettingDTO.use_yn_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="use_yn" value="1" <c:if test="${ilboSettingDTO.use_yn eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="use_yn" value="0" <c:if test="${ilboSettingDTO.use_yn eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>등록일시</td>
	     			<td>
	     				<input type="text" name="reg_date_width" value="${ilboSettingDTO.reg_date_width eq null ? '60' : ilboSettingDTO.reg_date_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="reg_date" value="1" <c:if test="${ilboSettingDTO.reg_date eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="reg_date" value="0" <c:if test="${ilboSettingDTO.reg_date eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>등록자</td>
	     			<td>
	     				<input type="text" name="reg_admin_width" value="${ilboSettingDTO.reg_admin_width eq null ? '60' : ilboSettingDTO.reg_admin_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="reg_admin" value="1" <c:if test="${ilboSettingDTO.reg_admin eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="reg_admin" value="0" <c:if test="${ilboSettingDTO.reg_admin eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	     		<tr style="text-align: center;">
	     			<td>소유자</td>
	     			<td>
	     				<input type="text" name="owner_id_width" value="${ilboSettingDTO.owner_id_width eq null ? '60' : ilboSettingDTO.owner_id_width }" />
	     			</td>
	     			<td>
	     				<input type="radio" name="owner_id" value="1" <c:if test="${ilboSettingDTO.owner_id eq '1' }">checked="checked"</c:if>>사용
						<input type="radio" name="owner_id" value="0" <c:if test="${ilboSettingDTO.owner_id eq '0' }">checked="checked"</c:if>>숨김
	     			</td>
	     		</tr>
	    	</tbody>
	    	<input type="hidden" name="setting_use_yn" value="1" />
	    	<input type="hidden" name="setting_del_yn" value="0" />
		</table>
	
	<div class="btn-module mgtL">
		<a href="javascript:void(0);" id="btnAdd" class="btnStyle04" onclick="fn_update()">
			저장
		</a>
		<a href="javascript:void(0);" id="btnReset" class="btnStyle04" onclick="fn_reset()">
			초기화
		</a>
	</div>

</div>
