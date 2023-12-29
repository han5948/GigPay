<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	var optionList = '${optionList }';
	var viewTimeCnt = '${viewTimeCnt }';
	
	function fn_update() {
		var bool = 0;
		
		$("input[name=start_time]").each(function() {
			if($(this).val() == '') {
				bool = 1;
				
				alert("시작 시간을 입력해주세요.");
				
				$(this).focus();
				
				return false;
			} 
		});

		if(bool == '1') {
			return false;
		}
		
		$("input[name=end_time]").each(function() {
			if($(this).val() == '') {
				bool = 1;
				
				alert("종료 시간을 입력해주세요.");
				
				$(this).focus();
				
				return false;
			} 
		});
		
		if(bool == '1') {
			return false;
		}
		
		$("input[name=use_cnt]").each(function() {
			if($(this).val() == '') {
				bool = 1;
				
				alert("노출 개수를 입력해주세요.");
				
				$(this).focus();
				
				return false;
			} 
		});
		
		if($("#work_limit_count").val() < 0 || $("#work_limit_count").val() > 31
			|| $("#employer_limit_count").val() < 0 || $("#employer_limit_count").val() > 31) {
			alert("제한 일수는 1~31일까지만 설정 가능합니다.");
			
			return false;
		}
		
		if($("#complete_score").val() == '' || $("#complete_score").val() < 0) {
			alert("완료 점수를 제대로 입력해주세요.");
			
			return false;
		}
		
		if($("#re_score").val() == '' || $("#re_score").val() < 0) {
			alert("또가요 점수를 제대로 입력해주세요.");
			
			return false;
		}
		
		if($("#punk_score").val() == '' || $("#punk_score").val() < 0) {
			alert("펑크 점수를 제대로 입력해주세요.");
			
			return false;
		}
		
		if($("#eva_score").val() == '' || $("#eva_score").val() < 0) {
			alert("F평가 점수를 제대로 입력해주세요.");
			
			return false;
		}
		
		if(bool == '1') {
			return false;
		}
		
		var paramArray = new Array();

		$("input[name=option_type]").each(function() {
			paramArray[$(this).val()] = {
				'start_time' : $("input[name=start_time]").eq($(this).val()).val(),	
				'end_time' : $("input[name=end_time]").eq($(this).val()).val(),
				'work_limit_count' : $("#work_limit_count").val(),
				'employer_limit_count' : $("#employer_limit_count").val(),
				'complete_score' : $("#complete_score").val(),
				're_score' : $("#re_score").val(),
				'punk_score' : $("#punk_score").val(),
				'eva_score' : $("#eva_score").val(),
				'use_cnt' : $("input[name=use_cnt]").eq($(this).val()).val(),
				'use_yn' : $("input[name=" + $(this).val() + "_use_yn]:checked").val(),
				'option_type' : $(this).val(),
				'data_flag' : $("#data_flag").val()
			} 
		});
		
		var _data = JSON.stringify(paramArray);
		
		$.ajax ({
			url : "/admin/ilboWork/updateOption",
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
	}
	
	function fn_numChk(data){
		var chkStyle = /^[0-9]+$/; 
		
		if( chkStyle.test(data) ){
			return true;
		}else{
			return false;
		}
	}
	
	function fn_updateGradePrice(grade){
		var contrMinPrice = $("#"+grade+"_contr_min_price").val();
		if( !fn_numChk(contrMinPrice) ){
			toastFail("숫자가 아닙니다.");
			return;
		}
		var contrMaxPrice = $("#"+grade+"_contr_max_price").val();
		if( !fn_numChk(contrMaxPrice) && contrMaxPrice != undefined ){
			toastFail("숫자가 아닙니다.");
			return;
		}
		var nonContrMinPrice = $("#"+grade+"_non_contr_min_price").val();
		if( !fn_numChk(nonContrMinPrice) ){
			toastFail("숫자가 아닙니다.");
			return;
		}
		var nonContrMaxPrice = $("#"+grade+"_non_contr_max_price").val();
		if( !fn_numChk(nonContrMaxPrice) && nonContrMaxPrice != undefined ){
			toastFail("숫자가 아닙니다.");
			return;
		}
		
		var _data = {
    		grade : grade,
    		contr_min_price : contrMinPrice,
    		contr_max_price : contrMaxPrice,
    		non_contr_min_price : nonContrMinPrice,
    		non_contr_max_price : nonContrMaxPrice
    	};
	    
    	var _url = "<c:url value='/admin/ilboWork/updateGradePrice' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			toastOk("변경 완료되었습니다.");
    		}else {
    			toastFail(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function fn_updateJobLimitCount(limitType) {
		var _data = {
			limit_type : limitType,
    		limit_count : $("#" + limitType.toLowerCase() + "_limit_count").val()
    	};
	    
    	var _url = "<c:url value='/admin/ilboWork/updateJobLimitCount' />";
		
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			toastOk("변경 완료되었습니다.");
    			
    		}else {
    			toastFail(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function fn_updateViewTime() {
		var _data = {
			start_view_time : $("#start_view_time").val()
			, end_view_time : $("#end_view_time").val()
		};
		
		var _url = "";
		
		if(viewTimeCnt > 0) {
			_url = "<c:url value='/admin/ilboWork/updateViewTime' />";
		}else {
			_url = "<c:url value='/admin/ilboWork/insertViewTime' />";
		}
		
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			toastOk("변경 완료되었습니다.");
    			
    			viewTimeCnt = data.viewTimeCnt;
    		}else {
    			toastFail(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
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
			<h3>맞춤 설정</h3>
		</div>
		<form id="optionFrm" name="optionFrm">
			<table class="bd-list" summary="맞춤 일자리 설정 테이블">
			    <colgroup>
				    <col width="100px" />
				    <col width="200px" />
				    <col width="300px" />
				    <col width="200px" />
			    </colgroup>
		    	<thead>
					<tr>
						<th>구분</th>
						<th>시작 시간</th>
						<th>종료 시간</th>
						<th>노출 개수</th>
						<th class="last">사용 여부</th>
					</tr>
				</thead>
				<tbody id="listBody">
					<c:choose>
						<c:when test="${fn:length(optionList) ne 0 }">
							<c:forEach items="${optionList }" var="item">
					     		<tr style="text-align: center;">
					     			<td>
					     				<c:if test="${item.option_type eq '0' }">
					     					지점
					     				</c:if>
					     				<c:if test="${item.option_type eq '1' }">
					     					전체
					     				</c:if>
					     			</td>
					     			<td>
					     				<input type="text" class="inp-field wid50" name="start_time" value="${item.start_time }" maxlength="4"/>
					     			</td>
					     			<td>
				     					<input type="text" class="inp-field wid50" name="end_time" value="${item.end_time }" maxlength="4" />
					     			</td>
					     			<td>
				     					<input type="text" class="inp-field wid50" name="use_cnt" value="${item.use_cnt }" />
					     			</td>
					     			<td>
					     				<input type="radio" name="${item.option_type }_use_yn" value="1" <c:if test="${item.use_yn eq '1' }">checked="checked"</c:if>>사용
										<input type="radio" name="${item.option_type }_use_yn" value="0" <c:if test="${item.use_yn eq '0' }">checked="checked"</c:if>>미사용
					     			</td>
					     		</tr>
					     		<input type="hidden" name="option_type" value="${item.option_type }" />
					     		<input type="hidden" name="data_flag" id="data_flag" value="1">
				     		</c:forEach>
				     	</c:when>
				     	<c:otherwise>
				     		<tr style="text-align: center;">
				     			<td>
				     				지점
				     			</td>
				     			<td>
				     				<input type="text" class="inp-field wid50" name="start_time" maxlength="4"/>
				     			</td>
				     			<td>
			     					<input type="text" class="inp-field wid50" name="end_time" maxlength="4" />
				     			</td>
				     			<td>
			     					<input type="text" class="inp-field wid50" name="use_cnt" />
				     			</td>
				     			<td>
				     				<input type="radio" name="0_use_yn" value="1" checked="checked">사용
									<input type="radio" name="0_use_yn" value="0" >미사용
				     			</td>
				     		</tr>
			     			<input type="hidden" name="option_type" value="0" />
				     		<tr style="text-align: center;">
				     			<td>
				     				전체
				     			</td>
				     			<td>
				     				<input type="text" class="inp-field wid50" name="start_time" maxlength="4"/>
				     			</td>
				     			<td>
			     					<input type="text" class="inp-field wid50" name="end_time" maxlength="4" />
				     			</td>
				     			<td>
			     					<input type="text" class="inp-field wid50" name="use_cnt" />
				     			</td>
				     			<td>
				     				<input type="radio" name="1_use_yn" value="1" checked="checked">사용
									<input type="radio" name="1_use_yn" value="0" >미사용
				     			</td>
				     		</tr>
				     		<input type="hidden" name="option_type" value="1" />
				     		<input type="hidden" name="data_flag" id="data_flag" value="0">
				     	</c:otherwise>
				     </c:choose>
		    	</tbody>
			</table>
			
			<table class="bd-list" summary="출역일수제한 테이블" style="width: 10%;">
			    <colgroup>
				    <col width="100px" />
				    <col width="200px" />
			    </colgroup>
		    	<thead>
					<tr>
						<th>구분</th>
						<th class="last">제한 일수</th>
					</tr>
				</thead>
				<tbody id="limitBody">
		     		<tr style="text-align: center;">
		     			<td>
	     					현장별 제한
		     			</td>
		     			<td>
		     				<input type="text" class="inp-field wid50" id="work_limit_count" name="work_limit_count" value="${work_limit_count }" /><span class="dateTxt">일</span>
		     			</td>
		     		</tr>
		     		<tr style="text-align: center;">
		     			<td>
	     					구인처별 제한
		     			</td>
		     			<td>
		     				<input type="text" class="inp-field wid50" id="employer_limit_count" name="employer_limit_count" value="${employer_limit_count }" /><span class="dateTxt">일</span>
		     			</td>
		     		</tr>
		    	</tbody>
			</table>
			
			<table class="bd-list" summary="환산 점수 테이블" style="width: 10%;">
			    <colgroup>
				    <col width="100px" />
				    <col width="200px" />
			    </colgroup>
		    	<thead>
					<tr>
						<th>구분</th>
						<th class="last">환산 점수</th>
					</tr>
				</thead>
				<tbody id="scoreBody">
		     		<tr style="text-align: center;">
		     			<td>
	     					완료 점수
		     			</td>
		     			<td>
		     				<input type="text" class="inp-field wid50" id="complete_score" name="complete_score" value="${complete_score }" />
		     			</td>
		     		</tr>
		     		<tr style="text-align: center;">
		     			<td>
	     					또가요 점수
		     			</td>
		     			<td>
		     				<input type="text" class="inp-field wid50" id="re_score" name="re_score" value="${re_score }" />
		     			</td>
		     		</tr>
					<tr style="text-align: center;">
		     			<td>
	     					펑크 점수
		     			</td>
		     			<td>
		     				<input type="text" class="inp-field wid50" id="punk_score" name="punk_score" value="${punk_score }" />
		     			</td>
		     		</tr>
		     		<tr style="text-align: center;">
		     			<td>
	     					F평가 점수
		     			</td>
		     			<td>
		     				<input type="text" class="inp-field wid50" id="eva_score" name="eva_score" value="${eva_score }" />
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
		
		<br />
		<br />
		<br />
		<br />
		<br />
		
		<table class="bd-list" summary="소장평가별 단가기준" style="width: 50%;">
			<colgroup>
				<col width="10%">
				<col width="5%">
				<col width="40%">
				<col width="40%">
				<col width="5%">
			</colgroup>
			<thead>
				<tr>
					<th>인력구분</th>
					<th>등급</th>
					<th>건설 직종 단가</th>
					<th>건설 외 직종 단가</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${gradeList}" var="gradeInfo">
					<tr>
						<td>보통인력</td>
						<td>
							<c:choose>
								<c:when test="${gradeInfo.grade eq 6}">
									A
								</c:when>
								<c:when test="${gradeInfo.grade eq 5}">
									B
								</c:when>
								<c:when test="${gradeInfo.grade eq 4}">
									C
								</c:when>
								<c:when test="${gradeInfo.grade eq 3}">
									D
								</c:when>
								<c:when test="${gradeInfo.grade eq 2}">
									E
								</c:when>
								<c:when test="${gradeInfo.grade eq 1}">
									F
								</c:when>
							</c:choose>
						</td>
						<td>
							<input type="text" class="inp-field wid70" maxlength="10" id="${gradeInfo.grade }_contr_min_price" name="contr_min_price" value="${gradeInfo.contr_min_price }">
							<label>원 이상&emsp;~</label>
							<c:if test="${gradeInfo.grade != 6 }">
								<input type="text" class="inp-field wid70" maxlength="10" id="${gradeInfo.grade }_contr_max_price" name="contr_max_price" value="${gradeInfo.contr_max_price }">
								<label>원 이하</label>
							</c:if>
						</td>
						<td>
							<input type="text" class="inp-field wid70" maxlength="10" id="${gradeInfo.grade }_non_contr_min_price" name="non_contr_min_price" value="${gradeInfo.non_contr_min_price }">
							<label>원 이상&emsp;~</label>
							<c:if test="${gradeInfo.grade != 6 }">
								<input type="text" class="inp-field wid70" maxlength="10" id="${gradeInfo.grade }_non_contr_max_price" name="non_contr_max_price" value="${gradeInfo.non_contr_max_price }">
								
								<label>원 이하</label>
							</c:if>
						</td>
						<td>
							<div onclick="fn_updateGradePrice(${gradeInfo.grade})" style="display: inline-block; padding: 3px; border: 1px solid black; border-radius: 3px; cursor: pointer;">
								저장
							</div>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td>보통인력</td>
					<td>
						미평가
					</td>
					<td>
						모든 일자리 노출 안됨
					</td>
					<td>
						모든 일자리 노출 안됨
					</td>
					<td>
					</td>
				</tr>
			</tbody>
		</table>
		
		<table class="bd-list" summary="구직자 직종 제한 개수" style="width: 50%;">
			<colgroup>
				<col width="10%">
				<col width="10%">
				<col width="10%">
			</colgroup>
			<tbody>
				<tr>
					<td>IMS 구직자 직종 제한 개수</td>
					<td>
						<input type="text" class="inp-field wid70" id="ims_limit_count" name="limit_count" value="${imsLimitCountDTO.limit_count}">
						<label>개</label>
					</td>
					<td>
						<div onclick="fn_updateJobLimitCount('IMS')" style="display: inline-block; padding: 3px; border: 1px solid black; border-radius: 3px; cursor: pointer;">
							저장
						</div>
					</td>
				</tr>
				<tr>
					<td>W 구직자 직종 제한 개수</td>
					<td>
						<input type="text" class="inp-field wid70" id="w_limit_count" name="limit_count" value="${wLimitCountDTO.limit_count}">
						<label>개</label>
					</td>
					<td>
						<div onclick="fn_updateJobLimitCount('W')" style="display: inline-block; padding: 3px; border: 1px solid black; border-radius: 3px; cursor: pointer;">
							저장
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
		<table class="bd-list" summary="고객센터 전화번호 노출 시간" style="width: 50%;">
			<colgroup>
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
			</colgroup>
			<tbody>
				<tr>
					<td>시작시간</td>
					<td>
						<input type="text" class="inp-field wid70" id="start_view_time" name="start_view_time" value="${viewTimeDTO.start_view_time eq null ? '0600' : viewTimeDTO.start_view_time }">
					</td>
					<td>종료시간</td>
					<td>
						<input type="text" class="inp-field wid70" id="end_view_time" name="end_view_time" value="${viewTimeDTO.end_view_time eq null ? 1900 : viewTimeDTO.end_view_time }">
					</td>
					<td>
						<div onclick="fn_updateViewTime()" style="display: inline-block; padding: 3px; border: 1px solid black; border-radius: 3px; cursor: pointer;">
							저장
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
