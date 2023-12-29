<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(function() {
		// 행 추가
  		$("#btnAddForm").click( function() {
  			taxAddForm();
  		});
		
		$("#btnTaxAdd").click( function(){
			taxAdd();
		});
		
		$("#btnDel").click( function(){
			taxDel();
		});
	});
	
	//세금 요율 추가 layer popup
	function taxAddForm() {
		var _url = "/admin/taxRate/getLastRateInfo";
		var _data = {
				use_yn : 1
		};
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var _taxRateDTO = data.taxRateDTO;
				if( _taxRateDTO ){
					$("#job_kind").val(_taxRateDTO.job_kind);
					$("#deduction_price").val(comma(uncomma(_taxRateDTO.deduction_price)));
					$("#income_tax_rate").val(_taxRateDTO.income_tax_rate);
					$("#employer_insurance_rate").val(_taxRateDTO.employer_insurance_rate);
					$("#national_pension_rate").val(_taxRateDTO.national_pension_rate);
					$("#health_insurance_rate").val(_taxRateDTO.health_insurance_rate);
					$("#care_insurance_rate").val(_taxRateDTO.care_insurance_rate);
					
					$("#effective_date").val(_taxRateDTO.effective_date);
				}
				
				// Layer popup
		  		openIrPopup('popup-layer');
    		}else {
    			alert(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		alert("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
		
	}
	
	//세금 요율 추가
	function taxAdd(){
		
		if( !validTaxAdd() ){
			return;
		}
		
		var _url = "/admin/taxRate/taxRateAdd";
		var _data = $("#taxRateTable").find('input').serializeArray();
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			alert("추가 완료되었습니다.");
    			
    			location.reload();
    		}else {
    			alert(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		alert("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	//
	function validTaxAdd(){
		var isValid = true;
		
		$("#taxRateTable").find('input').each(function(index, item){
			var keyName = $(this).parent().parent().parent().children('th').text();
			var _value = $(item).val();
			if( _value == "" ){
				alert( keyName + "을 입력해주세요.");
				
				isValid = false;
				return false;
			}
			
			if( $(this).hasClass("decimal") ){
				isValid = isDecimal(_value, 3);
				if( !isValid ){
					alert( keyName + "을 다시입력해주세요." );
					return false;
				}
			}
			
		});
		
		return isValid;
	}
	//소수 포맷인지 확인
	function isDecimal(text, digit){
		
		var stringToGoIntoTheRegex = "\^(\\d*)[\\.]?(\\d{1," + digit + "})?$";
		var regex = new RegExp(stringToGoIntoTheRegex, "g");
		if(regex.test(text)){
			return true;
		};
		
		return false;
	}
	
	function taxDel(){
		
		if( !isChkTaxDel() ){
			alert("최소 1개이상을 선택해 주세요.");
			return;
		}
		
		if( !validTaxDel() ){
			alert("삭제할 수 없는 정보가있습니다.");
			return;
		};
		var texSeqArr = [];
		$("input:checkbox[name=tax_seq]:checked").each(function (index, item){
			texSeqArr.push($(this).val());
		});
		
		var _url = "/admin/taxRate/taxRateDel";
		var _data = {
				del_tax_seq_arr : texSeqArr
				, use_yn : 0
		};
		commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			alert("삭제 완료되었습니다.");
    			
    			location.reload();
    		}else {
    			alert(data.message);
    		}
    	}, function(data) {
    		//errorListener
    		alert("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function isChkTaxDel(){
		var _chkCnt = $("input:checkbox[name=tax_seq]:checked").length;
		
		if( _chkCnt < 1 ){
			return false;
		}
		
		return true;
	}
	
	function validTaxDel(){
		var isValid = true;
		$("input:checkbox[name=tax_seq]:checked").each(function(index, item){
			var _effectiveDateStr = $(this).parent().parent().children('td:eq(8)').text();
			var _todayStr = getDateString(new Date());
			
			var _effectiveDate = new Date(_effectiveDateStr);
			var _todayDate = new Date(_todayStr);
			console.log("_todayDate", _todayDate);
			console.log("_effectiveDate", _effectiveDate);
			if( _todayDate >= _effectiveDate ){
				isValid = false;
				return isValid;
			}
		});
		return isValid;
	}
</script>
	<div class="title">
		<h3> 긱워커 세금 및 사회보험 근로자 부담금 요율(%) </h3>
	</div>
    <div class="btn-module mgtS mgbS">
      	<div class="leftGroup">
       		<a href="#none" id="btnAddForm" class="btnStyle01">추가</a>
        	<a href="#none" id="btnDel" class="btnStyle05">삭제</a>
      	</div>
    </div>
	
	<table class="bd-list" style="width:970px;" summary="근로자 부담금 요율테이블">
		    <colgroup>
			    <col width="50px" />
			    <col width="50px" />
				<col width="120px" />
			    <col width="120px" />
			    <col width="80px" />
			    <col width="80px" />
			    <col width="80px" />
			    <col width="80px" />
			    <col width="80px" />
			    <col width="150px" />
			    <col width="200px" />
		    </colgroup>
	    	<thead>
				<tr>
					<th></th>
					<th>번호</th>
					<th>구분</th>
					<th>근로소득공제액</th>
					<th>갑근세</th>
					<th>고용보험</th>
					<th>국민연금</th>
					<th>건강보험</th>
					<th>장기요양</th>
					<th>적용일</th>
					<th>등록일시</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<c:choose>
					<c:when test="${taxListSize > 0 }">
						<c:forEach items="${taxList }" var="taxInfo" varStatus="status" end="${taxListSize }">
							<tr>
								<td><input type="checkbox" name="tax_seq" value="${taxInfo.tax_seq }"></td>
								<td>${status.end - status.index}</td>
								<td>
									<c:choose>
										<c:when test="${taxInfo.job_kind eq '1' }">
											일용직
										</c:when>
										<c:when test="${taxInfo.job_kind eq '2' }">
											특고직
										</c:when>
										<c:when test="${taxInfo.job_kind eq '3' }">
											플랫폼 종사자
										</c:when>
									</c:choose>
								</td>
								<td><fmt:formatNumber value="${taxInfo.deduction_price }" pattern="#,###" /></td>
								<td>${taxInfo.income_tax_rate }</td>
								<td>${taxInfo.employer_insurance_rate }</td>
								<td>${taxInfo.national_pension_rate }</td>
								<td>${taxInfo.health_insurance_rate }</td>
								<td>${taxInfo.care_insurance_rate }</td>
								<td>${taxInfo.effective_date }</td>
								<td>${taxInfo.reg_date }</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="10">등록된 정보가 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
	    	</tbody>
		</table>

<div id="opt_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b;"></div>
<!-- 팝업 : 스캔 첨부파일 등록 -->
<div id="popup-layer">
	<header class="header">
		<h1 class="tit">긱워커 세금 및 사회보험 추가</h1>
	    <a href="#none" class="close" onclick="javascript:closeIrPopup('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
	</header>
	<form id="frmPopup" name="frmPopup" action="/admin/taxRate/taxRateAdd" method="post" enctype="multipart/form-data">
		<section>
			<div class="cont-box">
		    	<article>
		      		<table id="taxRateTable" summary="첨부파일 등록 영역입니다.">
		      			<colgroup>
		      				<col width="120px">
		      			</colgroup>
		      			<tbody>
							<tr>
								<td colspan='2' style="text-align: center; padding-bottom: 10px;">
									<input type="radio" name="job_kind" value="1" <c:if test="${jobInfo.job_field eq '1' }">checked</c:if>>일용직
									<input type="radio" name="job_kind" value="2" <c:if test="${jobInfo.job_field eq '2' }">checked</c:if>>특고직
									<input type="radio" name="job_kind" value="3" <c:if test="${jobInfo.job_field eq '3' }">checked</c:if>>플랫폼 종사자
								</td>
							</tr>
							<tr>
		          				<th>근로소득공제액</th>
		          				<td>
		            				<div class="btn-module filebox floatL">
		              					<input type="text" onkeyup="inputNumberFormat(this);" id="deduction_price" name="deduction_price" class="inp-field inputTaxRate" value="0" />
		            				</div>
		          				</td>
		        			</tr>
		        			<tr>
		          				<th>갑근세</th>
		          				<td>
		            				<div class="btn-module filebox floatL">
		              					<input type="text" id="income_tax_rate" name="income_tax_rate" class="inp-field decimal inputTaxRate" value="0" />
		            				</div>
		          				</td>
		        			</tr>
		        			<tr>
		          				<th>고용보험</th>
		          				<td>
		            				<div class="btn-module filebox floatL">
		              					<input type="text" id="employer_insurance_rate" name="employer_insurance_rate" class="inp-field decimal inputTaxRate" value="0" />
		            				</div>
		          				</td>
		        			</tr>
		        			<tr>
		          				<th>국민연급</th>
		          				<td>
		            				<div class="btn-module filebox floatL">
		              					<input type="text" id="national_pension_rate" name="national_pension_rate" class="inp-field decimal inputTaxRate" value="0" />
		            				</div>
		          				</td>
		        			</tr>
		        			<tr>
		          				<th>건강보험</th>
		          				<td>
		            				<div class="btn-module filebox floatL">
		              					<input type="text" id="health_insurance_rate" name="health_insurance_rate" class="inp-field decimal inputTaxRate" value="0" />
		            				</div>
		          				</td>
		        			</tr>
		        			<tr>
		          				<th>장기요양</th>
		          				<td>
		            				<div class="btn-module filebox floatL">
		              					<input type="text" id="care_insurance_rate" name="care_insurance_rate" class="inp-field decimal inputTaxRate" value="0" />
		            				</div>
		          				</td>
		        			</tr>
		        			<tr>
		          				<th>적용일</th>
		          				<td>
		            				<div class="btn-module filebox floatL">
		              					<input type="text" id="effective_date" name="effective_date" class="inp-field wid100 datepicker" readonly="readonly" />
		            				</div>
		          				</td>
		        			</tr>
		      			</tbody>
		      		</table>
		    	</article>
		
		    	<div class="btn-module" style="width: 100%;text-align: center; margin-top: 20px;">
		      		<div><a href="#popup-layer" id="btnTaxAdd" class="btnStyle01">추가</a></div>
		    	</div>
		  	</div>
		</section>
	</form>
</div>

<!-- // 팝업 : 스캔 첨부파일 등록  -->
<script type="text/javascript">
</script>
