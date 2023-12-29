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
		var param_start_reg_date = '${noticeDTO.startDate }';
		var param_end_reg_date = '${noticeDTO.endDate }';
		var param_day_type = '${noticeDTO.day_type }';
		
		if(param_day_type == '')
			$("#day_type_1").next().addClass("on");
		
		$("#start_reg_date").datepicker('setDate', param_start_reg_date);
		$("#end_reg_date").datepicker('setDate', param_end_reg_date);
				
	    if (window.Notification) {
	        Notification.requestPermission();
	    }
	}
	
	function fn_search() {
		$("#pageNo").val('1');
		
    	var _data = {
    		startDate 	: $("#start_reg_date").val(),
    		endDate 	: $("#end_reg_date").val(),
    		searchKey 	: $("#searchKey").val(),
    		searchValue : $("#searchValue").val(),
    		pageNo 		: $("#pageNo").val(),
    		use_yn 		: $("input[name=use_yn]:checked").val(),
    		auth_view 	: $("input[name=auth_view]:checked").val(),
    		del_yn : '0'
    	};
    	
    	var _url = "<c:url value='/admin/notice/getNoticeList' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var noticeList = data.noticeList; 
    			var totalCnt = data.totalCnt;
    			var noticeDTO = data.noticeDTO;
    			var text = '';
    			
    			$("#listBody").empty();
    			
    			if(noticeList == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="8">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    				
   					$(".paging").empty();
    			}else {
    				for(var i = 0; i < noticeList.length; i++) {
    					var count = totalCnt - (($("#pageNo").val() - 1) * noticeDTO.paging.pageSize + i);
    					
	    				text += '<tr>';
	    				text += '<td>' + count + '</td>';
	    				if(noticeList[i].auth_view == '0') {
							text += '<td>전체</td>';	    					
	    				}else if(noticeList[i].auth_view == '1') {
	    					text += '<td>구직자</td>';
	    				}else if(noticeList[i].auth_view == '2') {
	    					text += '<td>구인처</td>';
	    				}else {
	    					text += '<td>지점</td>';
	    				}
	    				text += '	<td>' + noticeList[i].notice_title + '</td>';
	    				text += '	<td>';
	    				text += '		<a href="javascript:void(0);" onclick="fn_edit(' + noticeList[i].notice_seq + ')">';
	    				text += 			noticeList[i].notice_contents;
	    				text += '		</a>';
	    				text += '	</td>';
	    				text += '	<td>';
	    				text += 		noticeList[i].view_cnt;
	    				text += '	</td>';
	    				text += '	<td>' + noticeList[i].notice_writer + '</td>';
	    				text += '	<td>' + noticeList[i].reg_admin + '</td>';
	    				text += '	<td>' + noticeList[i].auth_company_name + '</td>';
	    				
	    				text += '	<td>' + noticeList[i].reg_date + '</td>';
	    				text += '</tr>';
    				}
    				
    				if(noticeDTO.paging.pageSize != '0') {
    					$(".paging").empty();
 						fn_page_display(noticeDTO.paging.pageSize, totalCnt, 'defaultFrm', '#pageNo');
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
	
	function fn_edit(notice_seq) {
		$("#notice_seq").val(notice_seq);
		
		$("#defaultFrm").attr("action", "<c:url value='/admin/notice/noticeEdit' />").submit();
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
    		<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
    			<caption>등록일시, 상태, 직접검색 영역</caption>
    			<colgroup>
				    <col width="120px" />
				    <col width="750px" />
				    <col width="120px" />
				    <col width="*" />
			    </colgroup>
    			<tr>
				    <th scope="row">등록일시</th>
				    <td scope="row" class="linelY pdlM">
				    	<p class="floatL">
				    		<input type="text" id="start_reg_date" name="startDate" value="${noticeDTO.startDate }" class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>
				    		<input type="text" id="end_reg_date" name="endDate" class="inp-field wid100 datepicker" />
				    	</p>
    					<div class="inputUI_simple">		
    						<span class="contGp btnCalendar">
    							<input type="radio" value="all" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'all'   ); $('#btnSrh').trigger('click');" <c:if test="${noticeDTO.day_type eq 'all' }">checked="checked"</c:if> /><label for="day_type_1" class="label-radio">전체</label>
    							<input type="radio" value="today" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'today' ); $('#btnSrh').trigger('click');" <c:if test="${noticeDTO.day_type eq 'today' }">checked="checked"</c:if> /><label for="day_type_2" class="label-radio">오늘</label>
							    <input type="radio" value="week" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'week'  ); $('#btnSrh').trigger('click');" <c:if test="${noticeDTO.day_type eq 'week' }">checked="checked"</c:if> /><label for="day_type_3" class="label-radio">1주일</label>
							    <input type="radio" value="month" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'month' ); $('#btnSrh').trigger('click');" <c:if test="${noticeDTO.day_type eq 'month' }">checked="checked"</c:if> /><label for="day_type_4" class="label-radio">1개월</label>
							    <input type="radio" value="3month" id="day_type_5" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '3month'); $('#btnSrh').trigger('click');" <c:if test="${noticeDTO.day_type eq '3month' }">checked="checked"</c:if> /><label for="day_type_5" class="label-radio">3개월</label>
							    <input type="radio" value="6month" id="day_type_6" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '6month'); $('#btnSrh').trigger('click');" <c:if test="${noticeDTO.day_type eq '6month' }">checked="checked"</c:if> /><label for="day_type_6" class="label-radio">6개월</label>
    						</span>
    						
    					</div>
    				</td>
    				
    				<th scope="row" class="linelY pdlM">사용 유무</th>
    				<td scope="row" class="linelY pdlM">
						<input type="radio" id="srh_use_yn_1" name="use_yn" class="inputJo" value=""  checked /><label for="srh_use_yn_1" class="label-radio">전체</label>
						<input type="radio" id="srh_use_yn_2" name="use_yn" class="inputJo" value="1" <c:if test="${noticeDTO.use_yn eq '1' }">checked="checked"</c:if> /><label for="srh_use_yn_2" class="label-radio">사용</label>
						<input type="radio" id="srh_use_yn_3" name="use_yn" class="inputJo" value="0" <c:if test="${noticeDTO.use_yn eq '0' }">checked="checked"</c:if> /><label for="srh_use_yn_3" class="label-radio">미사용</label>        	
					</td>
    			</tr>
    			
    			<tr>
    				<th>APP</th>
					<td class="linelY">
						<p class="agreeCheck">
							<input type="radio" id="jobChk" 			name="auth_view" class="inputJo" value="0" checked /><label for="jobChk" class="label-radio">전체</label>
							<input type="radio" id="jobSeeker" 		name="auth_view" class="inputJo" value="1" <c:if test="${noticeDTO.auth_view eq '1' }">checked="checked"</c:if> /><label for="jobSeeker" class="label-radio">구직자</label>
							<input type="radio" id="jobOffer" 			name="auth_view" class="inputJo" value="2" <c:if test="${noticeDTO.auth_view eq '2' }">checked="checked"</c:if> /><label for="jobOffer" class="label-radio">구인처</label>
							<c:if test = "${sessionScope.ADMIN_SESSION.company_seq eq '13' }"> <!-- 본사일때  -->
							<input type="radio" id="jobCompany" 	name="auth_view" class="inputJo" value="3" <c:if test="${noticeDTO.auth_view eq '3' }">checked="checked"</c:if> /><label for="jobCompany" class="label-radio">지점</label>
							</c:if>
							
						</p>
					</td>
    				<th scope="row" class="linelY">직접검색</th>
    				<td scope="row" class="linelY pdlM">
    					<div class="select-inner">
    						<select id="searchKey" name="searchKey" class="styled02 floatL wid138" onChange="$('#searchValue').focus();">
<!-- 							    <option value="">전체</option> -->
							    <option value="notice_title">제목</option>
							    <option value="notice_contents">내용</option>
							    <option value="notice_writer">글쓴이</option>
    						</select>
    					</div>
    					<input type="text" id="searchValue" value="${noticeDTO.searchValue }" name="searchValue" class="inp-field wid300 mglS" onKeyDown="if ( event.keyCode == 13 ) $('#btnSrh').click();" />
    					<div class="btn-module floatL mglS">
						    <a href="javascript:void(0);" id="btnSrh" class="search" onclick="fn_search()">검색</a>
					    </div>
    				</td>
    				
    			</tr>
    		</table>
    	</div>
    </article>
   
	<table class="bd-list" summary="공지사항 리스트">
	    <colgroup>
		    <col width="50px" />
		    <col width="100px" />
		    <col width="250px" />
		    <col width="*" />
		    <col width="100px" />
		    <col width="200px" />
		    <col width="200px" />
		    <col width="200px" />
		    <col width="150px" />
	    </colgroup>
    	<thead>
			<tr>
				<th>번호</th>
				<th>APP</th>
				<th>제목</th>
				<th>내용</th>
				<th>viewCnt</th>
				<th>작성지점</th>
				<th>작성자</th>
				<th>보여지는 지점</th>
				<th class="last">날짜</th>
			</tr>
		</thead>
		<tbody id="listBody">
			<c:choose>
				<c:when test="${!empty noticeList }">
					<c:forEach var="item" items="${noticeList }" varStatus="status">
						<tr>
							<td>${totalCnt - ((noticeDTO.paging.pageNo - 1) * noticeDTO.paging.pageSize + status.index) }</td>
							<td>
								<c:choose>
									<c:when test="${item.auth_view eq '0' }">
										전체
									</c:when>
									<c:when test="${item.auth_view eq '1' }">
										구직자
									</c:when>
									<c:when test="${item.auth_view eq '2' }">
										구인처
									</c:when>
									<c:otherwise>
										지점
									</c:otherwise>
								</c:choose>
							</td>
							<td>${item.notice_title }</td>
							<td>
								<a href="javascript:void(0);" onclick="fn_edit('${item.notice_seq }')">
									${item.notice_contents }
								</a>
							</td>
							<td>${item.view_cnt }</td>
							<td>${item.notice_writer }</td>
							<td>${item.reg_admin }</td>
							<td>
								${item.auth_company_name }
							</td>
							
							<td>${item.reg_date }</td>
						</tr>
		    		</c:forEach>	
				</c:when>
       			<c:otherwise>
       				<tr style="text-align: center;">
       					<td colspan="8">
       						조회된 내용이 없습니다.
       					</td>
       				</tr>
       			</c:otherwise>
       		</c:choose>
    	</tbody>
	</table>
	
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${noticeDTO.paging.pageNo}" />
	<input type="hidden" id="notice_seq" name="notice_seq" />
	
	<c:if test="${noticeDTO.paging.pageSize != '0' }">
		<div class="paging">
			<script type="text/javascript">
				fn_page_display('${noticeDTO.paging.pageSize}', '${totalCnt }', 'defaultFrm', '#pageNo');
			</script>
		</div>
	</c:if>
	
	<div class="btn-module mgtL">
  			<a href="/admin/notice/noticeWrite" id="btnAdd" class="btnStyle04">
  				글쓰기
  			</a>
	</div>
</div>	