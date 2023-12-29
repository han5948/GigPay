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
	
	function changeWorkSeq(obj){
		var dIdx = $("#dest_seq option").index( $("#dest_seq option:selected") );
		var sIdx = 0;
		
		if(dIdx == 1) {
			sIdx = 2;	
		}else if(dIdx == 2) {
			sIdx = 1;
		}else {
			sIdx = 0;
		}
		
		$("#source_seq option:eq("+sIdx+")").prop("selected", true); //첫번째 option 선택
	}

	function mergeWork() {
		var destSeq = $("#dest_seq option:selected").val();
		var sourceSeq = $("#source_seq option:selected").val();
		
		if(destSeq == 0 || sourceSeq == 0) {
			alert("병합할 현장을 선택 하세요.");
			return;
		}
		
		if(destSeq == sourceSeq) {
			alert("병합할 현장은 서로 다르게 선택되어야 됩니다.");
			return;
		}
		
		var params = {
			work_seq : destSeq,
			source_work_seq : sourceSeq
		}

			
	    $.ajax({
	    		type: "POST",
	            url: "/admin/setWorkMerge",
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
				<h3>현장병합</h3>
			</div>
			
			<table class="bd-form" summary="현장이 중복으로 잘못 등록 되었을때만 사용 되어야 됩">
				<colgroup>
					<col width="200px" />
					<col width="*" />
				</colgroup>
				<tbody>
					<tr>
						<th>대상 현장</th>
						<td class="linelY">
							<select id="dest_seq" name="dest_seq" class="styled02  wid300 " onchange="changeWorkSeq(this)" >
								<option value="0">선택</option>
								<c:forEach items="${workList}" var="item" varStatus="status">
		                      		<option value="${item.work_seq }" >${item.work_name}</option>
		                		</c:forEach>
	         				</select>
						</td>
					</tr>
					<tr>
						<th>삭제 현장</th>
						<td class="linelY">
							<select id="source_seq" name="source_seq" class="styled02  wid300 " disabled >
								<option value="0">선택</option>
								<c:forEach items="${workList }" var="item" varStatus="status">
		                      		<option value="${item.work_seq }" >${item.work_name}</option>
		                		</c:forEach>
	         				</select>
						</td>
					</tr>
					<tr>
						<td colspan=2>
							<div class="btn-module mgtL" style="text-align: center;">
								<a href="javascript:void(0);" onclick="javascript:mergeWork();" id="btnMerge" class="btnStyle04">병합</a>
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