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
		setDayType('start_reg_date', 'end_reg_date', 'all');
		
		setDayType(1, 'to_ilbo_date', 'day');
		
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
    	
    	var _url = "<c:url value='/admin/jobAlim/ajaxJobAlimList' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var jobAlimList = data.jobAlimList; 
    			var totalCnt = data.totalCnt;
    			var jobAlimDTO = data.jobAlimDTO;
    			var text = '';
    			
    			$("#listBody").empty();
    			
    			if(jobAlimList == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="4">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    			}else {
    				for(var i = 0; i < jobAlimList.length; i++) {
    					var count = totalCnt - (($("#pageNo").val() - 1) * jobAlimDTO.paging.pageSize + i);
    					
	    				text += '<tr>';
	    				text += '	<td>' + count + '</td>';
	    				text += '	<td>';
	    				text += '		<a href="javascript:void(0);" onclick="fn_edit(' + jobAlimList[i].jobAlim_seq + ')">';
	    				text += 			jobAlimList[i].jobAlim_content;
	    				text += '		</a>';
	    				text += '	</td>';
	    				text += '	<td>' + jobAlimList[i].jobAlim_writer + '</td>';
	    				text += '	<td>' + jobAlimList[i].reg_date + '</td>';
	    				text += '</tr>';
    				}
    				
    				if(jobAlimDTO.paging.pageSize != '0') {
    					$(".paging").empty();
 						fn_page_display(jobAlimDTO.paging.pageSize, totalCnt, 'defaultFrm', '#pageNo');
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
	
	function fn_edit(jobAlim_seq) {
		$("#jobAlim_seq").val(jobAlim_seq);
		
		$("#defaultFrm").attr("action", "<c:url value='/admin/jobAlim/jobAlimEdit' />").submit();
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
	<div class="content">
		<div class="title">
	    	<h3>일가자 알림</h3>
	    </div>
    
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
	    							<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'all'   ); $('#btnSrh').trigger('click');" checked="checked" /><label for="day_type_1" class="label-radio on">전체</label>
	    							<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'today' ); $('#btnSrh').trigger('click');" /><label for="day_type_2" class="label-radio">오늘</label>
								    <input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'week'  ); $('#btnSrh').trigger('click');" /><label for="day_type_3" class="label-radio">1주일</label>
								    <input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'month' ); $('#btnSrh').trigger('click');" /><label for="day_type_4" class="label-radio">1개월</label>
								    <input type="radio" id="day_type_5" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '3month'); $('#btnSrh').trigger('click');" /><label for="day_type_5" class="label-radio">3개월</label>
								    <input type="radio" id="day_type_6" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '6month'); $('#btnSrh').trigger('click');" /><label for="day_type_6" class="label-radio">6개월</label>
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
								    <option value="">전체</option>
								    <option value="jobAlim_content">내용</option>
								    <option value="jobAlim_writer">글쓴이</option>
	    						</select>
	    					</div>
	    					<input type="text" id="searchValue" name="searchValue" class="inp-field wid300 mglS" onKeyDown="if ( event.keyCode == 13 ) $('#btnSrh').click();" />
	    					<div class="btn-module floatL mglS">
							    <a href="javascript:void(0);" id="btnSrh" class="search" onclick="fn_search()">검색</a>
						    </div>
	    				</td>
	    			</tr>
	    		</table>
	    	</div>
	    </article>
    
		<table class="bd-list" summary="일자리 알림 리스트">
		    <colgroup>
		    	<col width="50" />
			    <col width="350px" />
			    <col width="100px" />
			    <col width="200px" />
		    </colgroup>
	    	<thead>
				<tr>
					<th>번호</th>
					<th>내용</th>
					<th>글쓴이</th>
					<th class="last">날짜</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<c:choose>
					<c:when test="${!empty jobAlimList }">
						<c:forEach var="item" items="${jobAlimList }" varStatus="status" >
							<tr>
								<td>${totalCnt - ((jobAlimDTO.paging.pageNo - 1) * jobAlimDTO.paging.pageSize + status.index) }</td>
								<td>
									<a href="javascript:void(0);" onclick="fn_edit('${item.jobAlim_seq }')">
										${item.jobAlim_content }
									</a>
								</td>
								<td>${item.jobAlim_writer }</td>
								<td>${item.reg_date }</td>
							</tr>
			    		</c:forEach>	
					</c:when>
        			<c:otherwise>
        				<tr style="text-align: center;">
        					<td colspan="4">
        						조회된 내용이 없습니다.
        					</td>
        				</tr>
        			</c:otherwise>
        		</c:choose>
	    	</tbody>
		</table>
		
		<input type="hidden" id="pageNo" name="paging.pageNo" value="${jobAlimDTO.paging.pageNo}" />
		<input type="hidden" id="jobAlim_seq" name="jobAlim_seq" />
		
		<c:if test="${jobAlimDTO.paging.pageSize != '0' }">
			<div class="paging">
				<script type="text/javascript">
					fn_page_display('${jobAlimDTO.paging.pageSize}', '${totalCnt }', 'defaultFrm', '#pageNo');
				</script>
			</div>
		</c:if>
    </div>
