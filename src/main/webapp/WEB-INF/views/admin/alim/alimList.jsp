<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="<c:url value="/resources/cms/js/paging.js" />" charset="utf-8"></script>
<script type="text/javascript">
	window.onload = function () {
		var param_start_reg_date = '${alimDTO.startDate }';
		var param_end_reg_date = '${alimDTO.endDate }';
		var param_day_type = '${alimDTO.day_type }';
		
		if(param_day_type == '') {
			$("#day_type_1").next().addClass("on");
		}
		
		$("#start_reg_date").datepicker('setDate', param_start_reg_date);
		$("#end_reg_date").datepicker('setDate', param_end_reg_date);
				
	    if (window.Notification) {
	        Notification.requestPermission();
	    }
		
	    if (window.Notification) {
	        Notification.requestPermission();
	    }
	}
	
	function fn_search() {
		$("#pageNo").val('1');
		
    	var _data = {
    		startDate : $("#start_reg_date").val(),
    		endDate : $("#end_reg_date").val(),
    		searchKey : $("#searchKey").val(),
    		searchValue : $("#searchValue").val(),
    		pageNo : $("#pageNo").val()
    	};
    	
    	var _url = "<c:url value='/admin/alim/ajaxAlimList' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var alimList = data.alimList; 
    			var totalCnt = data.totalCnt;
    			var alimDTO = data.alimDTO;
    			var text = '';
    			
    			$("#listBody").empty();
    			
    			$("#totalRecords").html(totalCnt);
    			
    			if(alimList == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="5">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    				
    				$(".paging").empty();
    			}else {
    				for(var i = 0; i < alimList.length; i++) {
    					var count = totalCnt - (($("#pageNo").val() - 1) * alimDTO.paging.pageSize + i);
    					
	    				text += '<tr>';
	    				text += '	<td>' + count + '</td>';
	    				text += '	<td>' + alimList[i].alim_title + '</td>';
	    				text += '	<td>';
	    				text += '		<a href="javascript:void(0);" onclick="fn_edit(' + alimList[i].alim_seq + ')">';
	    				text += 			alimList[i].alim_content;
	    				text += '		</a>';
	    				text += '	</td>';
	    				text += '	<td>' + alimList[i].alim_writer + '</td>';
	    				
	    				if(alimList[i].alim_count == '1') {
	    					if(alimList[i].worker_name != null) {
	    						text += '	<td>(구직자) : ' + alimList[i].worker_name + '</td>';
	    					}else if(alimList[i].manager_name != null){
	    						text += '	<td>(매니저)' + alimList[i].manager_name + '</td>';
	    					}else {
	    						text += '	<td>(소장)</td>';
	    					}
	    				}else {
	    					text += '	<td>' + alimList[i].worker_name + ' 외' + alimList[i].alim_count + '명</td>';
	    				}
	    				
	    				text += '	<td>' + alimList[i].reg_date + '</td>';
	    				text += '</tr>';
    				}
    				
    				if(alimDTO.paging.pageSize != '0') {
    					$(".paging").empty();
 						fn_page_display(alimDTO.paging.pageSize, totalCnt, 'defaultFrm', '#pageNo');
 						
 						$("#defaultFrm").attr("action", "<c:url value='/admin/alim/alimSearchList' />");
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
	
	function fn_edit(alim_seq) {
		$("#alim_seq").val(alim_seq);
		
		$("#defaultFrm").attr("action", "<c:url value='/admin/alim/alimEdit' />").submit();
	}
	
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
<div class="content mgbM">
	<article>
		<div class="inputUI_simple">
    		<table class="bd-form s-form" summary="등록일시, 직접검색 영역 입니다.">
    			<caption>등록일시, 직접검색 영역</caption>
    			<colgroup>
				    <col width="150px" />
			    </colgroup>
    			<tr>
				    <th scope="row">등록일시</th>
				    <td colspan="3">
				    	<p class="floatL">
				    		<input type="text" id="start_reg_date" name="startDate" class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>
				    		<input type="text" id="end_reg_date" name="endDate" class="inp-field wid100 datepicker" />
				    	</p>
    					<div class="inputUI_simple">		
    						<span class="contGp btnCalendar">
    							<input type="radio" value="all" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'all'   ); $('#btnSrh').trigger('click');" <c:if test="${alimDTO.day_type eq 'all' }">checked="checked"</c:if> /><label for="day_type_1" class="label-radio">전체</label>
    							<input type="radio" value="today" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'today' ); $('#btnSrh').trigger('click');" <c:if test="${alimDTO.day_type eq 'today' }">checked="checked"</c:if> /><label for="day_type_2" class="label-radio">오늘</label>
							    <input type="radio" value="week" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'week'  ); $('#btnSrh').trigger('click');" <c:if test="${alimDTO.day_type eq 'week' }">checked="checked"</c:if> /><label for="day_type_3" class="label-radio">1주일</label>
							    <input type="radio" value="month" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'month' ); $('#btnSrh').trigger('click');" <c:if test="${alimDTO.day_type eq 'month' }">checked="checked"</c:if> /><label for="day_type_4" class="label-radio">1개월</label>
							    <input type="radio" value="3month" id="day_type_5" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '3month'); $('#btnSrh').trigger('click');" <c:if test="${alimDTO.day_type eq '3month' }">checked="checked"</c:if> /><label for="day_type_5" class="label-radio">3개월</label>
							    <input type="radio" value="6month" id="day_type_6" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '6month'); $('#btnSrh').trigger('click');" <c:if test="${alimDTO.day_type eq '6month' }">checked="checked"</c:if> /><label for="day_type_6" class="label-radio">6개월</label>
    						</span>
    						<span class="tipArea colorN02">* 직접 기입 시, ‘2017-06-28’ 형식으로 입력</span>
    					</div>
    				</td>
    			</tr>
    			<tr>
    				<th scope="row">직접검색</th>
    				<td>
    					<div class="select-inner">
    						<select id="searchKey" name="searchKey" class="styled02 floatL wid138" onChange="$('#searchValue').focus();">
<!-- 							    <option value="">전체</option> -->
							    <option value="alim_title" <c:if test="${alimDTO.searchKey eq 'alim_title' }">selected</c:if>>제목</option>
							    <option value="alim_content" <c:if test="${alimDTO.searchKey eq 'alim_content' }">selected</c:if>>내용</option>
							    <option value="alim_writer" <c:if test="${alimDTO.searchKey eq 'alim_writer' }">selected</c:if>>글쓴이</option>
    						</select>
    					</div>
    					<input type="text" id="searchValue" value="${alimDTO.searchValue }" name="searchValue" class="inp-field wid300 mglS" onKeyDown="if ( event.keyCode == 13 ) $('#btnSrh').click();" />
    					<div class="btn-module floatL mglS">
						    <a href="javascript:void(0);" id="btnSrh" class="search" onclick="fn_search()">검색</a>
					    </div>
    				</td>
    				<td>
    					<div class="leftGroup" style="padding-left: 80px; padding-top: 10px">
							총 :&nbsp;&nbsp;&nbsp; <span id="totalRecords" style="color: #ef0606;"><fmt:formatNumber value="${totalCnt }" pattern="#,###" /></span><span>건</span>
						</div>
    				</td>
    			</tr>
    		</table>
    	</div>
    </article>
   
	<table class="bd-list" summary="알림 리스트">
	    <colgroup>
	    	<col width="50" />
		    <col width="100px" />
		    <col width="250px" />
		    <col width="100px" />
		    <col width="100px" />
		    <col width="100px" />
	    </colgroup>
    	<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>내용</th>
				<th>글쓴이</th>
				<th>받는이</th>
				<th class="last">날짜</th>
			</tr>
		</thead>
		<tbody id="listBody">
			<c:choose>
				<c:when test="${!empty alimList }">
					<c:forEach var="item" items="${alimList }" varStatus="status" >
						<tr>
							<td>${totalCnt - ((alimDTO.paging.pageNo - 1) * alimDTO.paging.pageSize + status.index) }</td>
							<td>${item.alim_title }</td>
							<td>
								<a href="javascript:void(0);" onclick="fn_edit('${item.alim_seq }')">
									${item.alim_content }
								</a>
							</td>
							<td>${item.alim_writer }</td>
							<c:choose>
								<c:when test="${item.alim_count eq '1' }">
									<td>
										<c:choose>
											<c:when test="${item.worker_name ne null }">
												(구직자) : ${item.worker_name }
											</c:when>
											<c:when test="${item.manager_name ne null }">
												(매니저) : ${item.manager_name }
											</c:when>
											<c:otherwise>
												(소장)
											</c:otherwise>
										</c:choose>	
									</td>
								</c:when>
								<c:otherwise>
									<td>${item.worker_name } 외 ${item.alim_count }명</td>
								</c:otherwise>
							</c:choose>
							<td>${item.reg_date }</td>
						</tr>
		    		</c:forEach>	
				</c:when>
       			<c:otherwise>
       				<tr style="text-align: center;">
       					<td colspan="5">
       						조회된 내용이 없습니다.
       					</td>
       				</tr>
       			</c:otherwise>
       		</c:choose>
    	</tbody>
	</table>
	
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${alimDTO.paging.pageNo}" />
	<input type="hidden" id="alim_seq" name="alim_seq" />
	
	<c:if test="${alimDTO.paging.pageSize != '0' }">
		<div class="paging">
			<script type="text/javascript">
				fn_page_display('${alimDTO.paging.pageSize}', '${totalCnt }', 'defaultFrm', '#pageNo');
			</script>
		</div>
	</c:if>
</div>	