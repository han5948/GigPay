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
		if($("#group_name").val() == "") {
			alert("그룹을 선택해주세요.");
			
			return false;	
		}
		
    	var _data = {
    		startDate : $("#start_reg_date").val(),
    		endDate : $("#end_reg_date").val(),
    		searchKey : $("#searchKey").val(),
    		searchValue : $("#searchValue").val(),
    		use_yn : $("input[name=use_yn]:checked").val(),
    		del_yn : '0'
    	};
    	
    	var _url = "<c:url value='/admin/code/getCodeList' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var codeList = data.codeList; 
    			var codeDTO = data.codeDTO;
    			var text = '';
    			
    			$("#listBody").empty();
    			
    			if(codeList == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="4">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    			}else {
    				for(var i = 0; i < codeList.length; i++) {
	    				text += '<tr>';
	    				text += '	<td>' + codeList[i].code_rank + '</td>';
	    				text += '	<td>' + codeList[i].code_value + '</td>';
	    				text += '	<td>';
	    				text += '		<a href="javascript:void(0);" onclick="fn_edit(' + codeList[i].code_seq + ')">';
	    				text += 			codeList[i].code_name;
	    				text += '		</a>';
	    				text += '	</td>';
	    				text += '	<td></td>';
	    				text += '</tr>';
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
	
	function fn_edit(code_seq) {
		$("#code_seq").val(code_seq);
		
		$("#defaultFrm").attr("action", "<c:url value='/admin/code/codeEdit' />").submit();
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
	<article>
    	<div class="inputUI_simple">
    		<table class="bd-form s-form" summary="등록일시, 상태, 그룹, 직접검색 영역 입니다.">
    			<caption>등록일시, 상태, 그룹, 직접검색 영역 </caption>
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
    				<th>사용 유무</th>
    				<td>
						<input type="radio" id="srh_use_yn_1" name="use_yn" class="inputJo" value=""  /><label for="srh_use_yn_1" class="label-radio">전체</label>
						<input type="radio" id="srh_use_yn_2" name="use_yn" class="inputJo" value="1" checked="checked" /><label for="srh_use_yn_2" class="label-radio">사용</label>
						<input type="radio" id="srh_use_yn_3" name="use_yn" class="inputJo" value="0" /><label for="srh_use_yn_3" class="label-radio">미사용</label>        	
					</td>
    			</tr>
    			<tr>
    				<th>그룹명</th>
    				<td>
    					<select id="group_name" name="group_name">
    						<option value="">선택해주세요.</option>
    						<c:forEach var="item" items="${groupCodeList }">
    							<option value="${item.group_seq }">${item.group_name }</option>
    						</c:forEach>
    					</select>
    				</td>
    			</tr>
    			<tr>
    				<th scope="row">직접검색</th>
    				<td>
    					<div class="select-inner">
    						<select id="searchKey" name="searchKey" class="styled02 floatL wid138" onChange="$('#searchValue').focus();">
							    <option value="">전체</option>
							    <option value="code_name">코드명</option>
							    <option value="code_value">code_value</option>
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
   
	<table class="bd-list" summary="코드 리스트">
	    <colgroup>
		    <col width="30px" />
		    <col width="100px" />
		    <col width="100px" />
		    <col width="100px" />
	    </colgroup>
    	<thead>
			<tr>
				<th>순서</th>
				<th>code_value</th>
				<th>코드명</th>
				<th class="last"></th>
			</tr>
		</thead>
		<tbody id="listBody">
			<tr style="text-align: center;">
				<td colspan="4">
					그룹을 선택해주세요.
				</td>
			</tr>
    	</tbody>
	</table>
	
	<input type="hidden" id="code_seq" name="code_seq" />
	
	<div class="btn-module mgtL">
		<a href="/admin/code/codeWrite" id="btnAdd" class="btnStyle04">
			코드 추가
		</a>
	</div>