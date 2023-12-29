<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(document).ready(function() {
		$("body").css("overflow", "hidden");
		
	});
	
	function changeEmployerSeq(obj){
		
		//alert($(obj).val());
		
		var dIdx = $("#dest_seq option").index( $("#dest_seq option:selected") );
		
		var sIdx = 0;
		
		if(dIdx == 1){
			sIdx = 2;	
		}else if(dIdx == 2){
			sIdx = 1;
		}else {
			sIdx = 0;
		}
		
		$("#source_seq option:eq("+sIdx+")").prop("selected", true); //첫번째 option 선택


	}

	function mergeEmployer() {
		var destSeq = $("#dest_seq option:selected").val();
		var sourceSeq = $("#source_seq option:selected").val();
		
		if(destSeq == 0 || sourceSeq ==0){
			alert("병합할 구인처를 선택 하세요.");
			return;
		}
		
		if(destSeq == sourceSeq){
			alert("병합할 구인처를 서로 다르게 선택되어야 됩니다.");
			return;
		}
		
		var params = {
				employer_seq : destSeq,
				source_employer_seq : sourceSeq
		}

			
	    $.ajax({
	    		type: "POST",
	            url: "/admin/setEmployerMerge",
	            data: params,
	        	dataType: 'json',
	         	success: function(data) {
	                    if(data.code == "0000"){
	                    	alert("병합이 완료 되었습니다.");
	                    }else{
	                    	alert(data.message);
	                    }
	                    
	                    window.opener.mergeComplite(); 
	                    self.close();
	                
	            },
	      		beforeSend: function(xhr) {
	                    xhr.setRequestHeader("AJAX", true);
	            },
	           	error: function(e) {
	                    if ( data.status == "901" ) {
	                      location.href = "/admin/login";
	                    } else {
	                      alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
	                    }
	            }
	    });
	}
	
</script>
	<article>
		<div class="content mgM">
			<div class="title">
				<h3>구인처병합</h3>
			</div>
			
			<table class="bd-form" summary="구인처가 중복으로 잘못 등록 되었을때만 사용 되어야 됩">
				<colgroup>
					<col width="200px" />
					<col width="*" />
				</colgroup>
				<tbody>
					<tr>
						<th>대상구인처</th>
						<td class="linelY">
							<select id="dest_seq" name="dest_seq" class="styled02  wid300 " onchange="changeEmployerSeq(this)" >
							<option value="0">선택</option>
							
							<c:forEach items="${employerList}" var="item" varStatus="status">
		                      <option value="${item.employer_seq }" >${item.employer_name}</option>
		                	</c:forEach>
								
								
	         				</select>
						</td>
					</tr>
					<tr>
						<th>삭제구인처</th>
						<td class="linelY">
							<select id="source_seq" name="source_seq" class="styled02  wid300 " disabled >
								<option value="0">선택</option>
							
							<c:forEach items="${employerList}" var="item" varStatus="status">
		                      <option value="${item.employer_seq }" >${item.employer_name}</option>
		                	</c:forEach>
								
									
	         				</select>
						</td>
					</tr>
					<tr>
						<td colspan=2>
							<div style="font-weight: bold;">같은 구직자가 병합할 두 구인처에서 이미 알선된 경우, 사회보험 공제액에 오류가 발생될 수 있습니다.</div>
							<div class="btn-module mgtL" style="text-align: center;">
								<a href="javascript:void(0);" onclick="javascript:mergeEmployer();" id="btnMerge" class="btnStyle04">병합</a>
							</div>					
						</td>
					</tr>
					<tr>
						<td colspan=2 height="200px">
							<span id="pocess">
								
							</span>					
						</td>
					</tr>
				</tbody>
			</table>
			
		</div>
	</article>