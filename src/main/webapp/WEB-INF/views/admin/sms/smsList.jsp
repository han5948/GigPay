<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<style>
	.ui-datepicker table{ display: none; }
</style>
<script type="text/javascript" src="<c:url value="/resources/cms/js/paging.js" />" charset="utf-8"></script>
<script type="text/javascript">
	window.onload = function () {
		datePickerMonthSet('start_reg_date', '');
		
	    if (window.Notification) {
	        Notification.requestPermission();
	    }
	}
	
	function fn_search() {
		$("#pageNo").val('1');
		
    	var _data = {
   			startDate : $("#start_reg_date").val(),
    		searchKey : $("#searchKey").val(),
    		searchValue : $("#searchValue").val(),
    		searchKey2 : $("#searchKey2").val(),
    		pageNo : $("#pageNo").val()
    	};
    	
    	var _url = "<c:url value='/admin/sms/getSmsList' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var smsList = data.smsList; 
    			var totalCnt = data.totalCnt;
    			var smsDTO = data.smsDTO;
    			var text = '';
    			
    			$("#listBody").empty();
    			
    			if(smsList == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="7">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    				
    				$("#cntH3").text("총 " + totalCnt + "건");
   					$(".paging").empty();
    			}else {
    				for(var i = 0; i < smsList.length; i++) {
    					var count = totalCnt - (($("#pageNo").val() - 1) * smsDTO.paging.pageSize + i);
    					
	    				text += '<tr>';
	    				text += '	<td>' + count + '</td>';
	    				
	    				if(smsList[i].m_type == 'S')
	    					text += '<td>SMS</td>';
	    				else
	    					text += '<td>LMS</td>';
	    					
	    				text += '	<td>';
	    				text += 			smsList[i].tr_msg;
	    				text += '	</td>';
	    				text += '<td>' + smsList[i].tr_phone + '</td>';
	    				text += '<td>';
	    				
	    				$("#searchKey2 option").each(function() {
	    					if(smsList[i].tr_etc4 == $(this).val()) {
	    						text += $(this).text();
	    					}
	    				});
	    				
	    				text += '</td>';
	    				
	    				if(!smsList[i].tr_etc2)
	    					text += '<td></td>';
	    				else
	    					text += '<td>' + smsList[i].tr_etc2 + '</td>';
	    					
	    				if(!smsList[i].tr_etc4)
	    					text += '<td></td>';
	    				else
	    					text += '	<td>' + smsList[i].tr_etc3 + '</td>';
	    					
	    				text += '</tr>';
    				}
    				
    				$("#cntH3").text("총 " + totalCnt + "건");
    				
    				if(smsDTO.paging.pageSize != '0') {
    					$(".paging").empty();
 						fn_page_display(smsDTO.paging.pageSize, totalCnt, 'contentFrm', '#pageNo');
    				}
    			}
    			
    			$("#listBody").append(text);
    		}else {
    			toastFail("오류가 발생했습니다.3");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
    };
	
	function calculate() {
	 
	        notify();
	   
	}
	
	function notify() {
	    if (Notification.permission !== 'granted') {
	        alert('notification is disabled');
	    }else {
	
	        var notification = new Notification('Notification title', {
	            icon: 'http://cdn.sstatic.net/stackexchange/img/logos/so/so-icon.png',
	            body: 'Notification text',
	        });
	
	        notification.onclick = function () {
	            window.open('http://google.com');
	        };
	    }
	}
</script>
	<article>
    	<div class="inputUI_simple">
    		<table class="bd-form s-form" summary="등록일시, 직접검색 영역 입니다.">
    			<caption>등록일시, 지점검색, 직접검색 영역</caption>
    			<colgroup>
				    <col width="150px" />
			    </colgroup>
			    <tr>
				    <th>등록일시</th>
				    <td colspan="3">
				    	<p class="floatL">
				    		<input type="text" id="start_reg_date" name="startDate" value="${smsDTO.startDate }" maxlength="7" class="inp-field wid100" />
				    	</p>
    					<div class="inputUI_simple">		
    						<span class="tipArea colorN02">* 직접 기입 시, ‘2017-06’ 형식으로 입력</span>
    					</div>
    				</td>
    			</tr>
    			<tr>
    				<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq '0' }">
	    				<th scope="row">지점</th>
	    				<td>
	    					<div class="select-inner">
	    						<select id="searchKey2" name="searchKey2" class="styled02 floatL wid138" onChange="$('#searchValue').focus();">
								    <option value="">전체</option>
								    <c:forEach var="item" items="${companyList }">
								    	<option value="${item.company_seq }" <c:if test="${smsDTO.searchKey2 eq item.company_seq }">selected</c:if>>${item.company_name }</option>
								    </c:forEach>
	    						</select>
	    					</div>
	    				</td>
		    		</c:if>
    				<th scope="row" class="linelY pdlM">직접검색</th>
    				<td>
    					<div class="select-inner">
    						<select id="searchKey" name="searchKey" class="styled02 floatL wid138" onChange="$('#searchValue').focus();">
							    <option value="">전체</option>
							    <option value="tr_etc3">작성자</option>
							    <option value="tr_msg">내용</option>
    						</select>
    					</div>
    					<input type="text" id="searchValue" value="${smsDTO.searchValue }" name="searchValue" class="inp-field wid300 mglS" onKeyDown="if ( event.keyCode == 13 ) $('#btnSrh').click();" />
    					<div class="btn-module floatL mglS">
						    <a href="javascript:void(0);" id="btnSrh" class="search" onclick="fn_search()">검색</a>
					    </div>
    				</td>
    			</tr>
    		</table>
    	</div>
    </article>
    
    <div>
    	<h3 style="font-size:18px; font-weight:bold; margin:15px 0;" id="cntH3">총 ${totalCnt }건</h3>
    </div>
	<table class="bd-list" summary="SMS 리스트">
	    <colgroup>
		    <col width="30px" />
		    <col width="50px" />
		    <col width="250px" />
		    <col width="50px" />
		    <col width="50px" />
		    <col width="50px" />
		    <col width="70px" />
	    </colgroup>
    	<thead>
			<tr>
				<th>번호</th>
				<th>문자 형식</th>
				<th>내용</th>
				<th>받는 번호</th>
				<th>보낸 지점</th>
				<th>작성자</th>
				<th class="last">날짜</th>
			</tr>
		</thead>
		<tbody id="listBody">
			<c:choose>
				<c:when test="${!empty smsList }">
					<c:forEach var="item" items="${smsList }" varStatus="status">
						<tr>
							<td>${totalCnt - ((smsDTO.paging.pageNo - 1) * smsDTO.paging.pageSize + status.index) }</td>
							<td>
								<c:choose>
									<c:when test="${item.m_type eq 'S' }">
										SMS
									</c:when>
									<c:otherwise>
										LMS
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								${item.tr_msg }
							</td>
							<td>
								${item.tr_phone }
							</td>
							<td>
								<c:forEach var="item2" items="${companyList }">
									<c:if test="${item.tr_etc4 eq item2.company_seq }">
										${item2.company_name }
									</c:if>
								</c:forEach>
							</td>
							<td>
								${item.tr_etc2 }
							</td>
							<td>${item.tr_etc3 }</td>
						</tr>
		    		</c:forEach>	
				</c:when>
       			<c:otherwise>
       				<tr style="text-align: center;">
       					<td colspan="7">
       						조회된 내용이 없습니다.
       					</td>
       				</tr>
       			</c:otherwise>
       		</c:choose>
    	</tbody>
	</table>
	
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${smsDTO.paging.pageNo}" />
	
	<c:if test="${smsDTO.paging.pageSize != '0' }">
		<div class="paging">
			<script type="text/javascript">
				fn_page_display('${smsDTO.paging.pageSize}', '${totalCnt }', 'contentFrm', '#pageNo');
			</script>
		</div>
	</c:if>