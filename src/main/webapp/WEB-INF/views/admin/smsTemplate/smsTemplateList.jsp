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
		var param_start_reg_date = '${smsTemplateDTO.startDate }';
		var param_end_reg_date = '${smsTemplateDTO.endDate }';
		var param_day_type = '${smsTemplateDTO.day_type }';
		
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
    		startDate : $("#start_reg_date").val(),
    		endDate : $("#end_reg_date").val(),
    		searchKey : $("#searchKey").val(),
    		searchValue : $("#searchValue").val(),
    		pageNo : $("#pageNo").val(),
    		use_yn : $("input[name=use_yn]:checked").val(),
    	};
    	
    	var _url = "<c:url value='/admin/smsTemplate/getSmsTemplateList' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var smsTemplateList = data.smsTemplateList; 
    			var totalCnt = data.totalCnt;
    			var smsTemplateDTO = data.smsTemplateDTO;
    			var text = '';
    			
    			$("#listBody").empty();
    			
    			if(smsTemplateList == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="5">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    				
   					$(".paging").empty();
    			}else {
    				for(var i = 0; i < smsTemplateList.length; i++) {
    					var count = totalCnt - (($("#pageNo").val() - 1) * smsTemplateDTO.paging.pageSize + i);
    					
	    				text += '<tr>';
	    				text += '	<td>' + count + '</td>';
	    				text += '	<td>';
	    				text += '		<a href="javascript:void(0);" onclick="fn_edit(' + smsTemplateList[i].template_seq + ')">';
	    				text += 			smsTemplateList[i].template_title;
	    				text += '		</a>';
	    				text += '	</td>';
	    				if(smsTemplateList[i].company_seq != 0)
	    					text += '	<td>' + smsTemplateList[i].company_name + '</td>';
	    				else
	    					text += '	<td>전체</td>';
	    				text += '	<td>' + smsTemplateList[i].mod_admin + '</td>';
	    				text += '	<td>' + smsTemplateList[i].reg_date + '</td>';
	    				text += '</tr>';
    				}
    				
    				if(smsTemplateDTO.paging.pageSize != '0') {
    					$(".paging").empty();
 						fn_page_display(smsTemplateDTO.paging.pageSize, totalCnt, 'contentFrm', '#pageNo');
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
	
	function fn_edit(template_seq) {
		$("#template_seq").val(template_seq);
		
		$("#contentFrm").attr("action", "<c:url value='/admin/smsTemplate/selectInfo' />").submit();
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
    		<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
    			<caption>등록일시, 상태, 직접검색 영역</caption>
    			<colgroup>
				    <col width="150px" />
			    </colgroup>
    			<tr>
    				<th>사용 유무</th>
    				<td>
						<input type="radio" id="srh_use_yn_1" name="use_yn" class="inputJo" value=""  <c:if test="${smsTemplateDTO.use_yn eq '' }">checked="checked"</c:if> /><label for="srh_use_yn_1" class="label-radio">전체</label>
						<input type="radio" id="srh_use_yn_2" name="use_yn" class="inputJo" value="1" <c:if test="${smsTemplateDTO.use_yn eq '1' }">checked="checked"</c:if> /><label for="srh_use_yn_2" class="label-radio">사용</label>
						<input type="radio" id="srh_use_yn_3" name="use_yn" class="inputJo" value="0" <c:if test="${smsTemplateDTO.use_yn eq '0' }">checked="checked"</c:if> /><label for="srh_use_yn_3" class="label-radio">미사용</label>        	
					</td>
    			</tr>
    			<tr>
    				<th scope="row">직접검색</th>
    				<td>
    					<div class="select-inner">
    						<select id="searchKey" name="searchKey" class="styled02 floatL wid138" onChange="$('#searchValue').focus();">
							    <option value="">전체</option>
							    <option value="template_title">제목</option>
							    <option value="template_content">내용</option>
    						</select>
    					</div>
    					<input type="text" id="searchValue" value="${smsTemplateDTO.searchValue }" name="searchValue" class="inp-field wid300 mglS" onKeyDown="if ( event.keyCode == 13 ) $('#btnSrh').click();" />
    					<div class="btn-module floatL mglS">
						    <a href="javascript:void(0);" id="btnSrh" class="search" onclick="fn_search()">검색</a>
					    </div>
    				</td>
    			</tr>
    		</table>
    	</div>
    </article>
   
	<table class="bd-list" summary="SMS 템플릿 리스트">
	    <colgroup>
		    <col width="30px" />
		    <col width="200px" />
		    <col width="125px" />
		    <col width="125px" />
		    <col width="70px" />
	    </colgroup>
    	<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>보여지는 지점</th>
				<th>작성자</th>
				<th class="last">날짜</th>
			</tr>
		</thead>
		<tbody id="listBody">
			<c:choose>
				<c:when test="${!empty smsTemplateList }">
					<c:forEach var="item" items="${smsTemplateList }" varStatus="status">
						<tr>
							<td>${totalCnt - ((smsTemplateDTO.paging.pageNo - 1) * smsTemplateDTO.paging.pageSize + status.index) }</td>
							<td>
								<a href="javascript:void(0);" onclick="fn_edit('${item.template_seq }')">
									${item.template_title }
								</a>	
							</td>
							<td>
								<c:choose>
									<c:when test="${item.company_seq eq '0' }">
										전체									
									</c:when>
									<c:otherwise>
										${item.company_name }
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								${item.reg_admin }
							</td>
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
	
	<input type="hidden" id="pageNo" name="paging.pageNo" value="${smsTemplateDTO.paging.pageNo}" />
	<input type="hidden" id="template_seq" name="template_seq" />
	
	<c:if test="${smsTemplateDTO.paging.pageSize != '0' }">
		<div class="paging">
			<script type="text/javascript">
				fn_page_display('${smsTemplateDTO.paging.pageSize}', '${totalCnt }', 'contentFrm', '#pageNo');
			</script>
		</div>
	</c:if>
	
	<div class="btn-module mgtL">
  			<a href="/admin/smsTemplate/smsTemplateWrite" id="btnAdd" class="btnStyle04">
  				템플릿 작성
  			</a>
	</div>