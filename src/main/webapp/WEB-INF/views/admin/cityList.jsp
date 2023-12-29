<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="<c:url value="/resources/web/js/newPaging.js" />" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"	href="<c:url value='/resources/common/css/city.css' />" media="all" charset="utf-8"></link>
<script type="text/javascript">
	window.onload = function () {
		$("#btnSrh").click(function(){
			$("#pageNo").val("1");
			var srh_text = $("#srh_text").val();
			var srh_type = $("#srh_type").val();
			var query = "";
			if( srh_text == '' ){
				alert("검색어를 입력해 주세요.");
			}else{
				if( srh_type == '' ){
					query = "city_name LIKE '%" + srh_text + "%' OR city_code LIKE '%" + srh_text + "%'";
				}else{
					query = srh_type + " LIKE '%" + srh_text + "%'";
				}
				$("#query").val(query);
				var _url = "<c:url value='/admin/getCityList' />";
				var _data = {
					query : query
				}
				commonAjax(_url, _data, true, function(data) {
					$("#listBody").empty();
					var text = "";
					var cnt = data.totalCnt;
					var cityList = data.cityList;
					var cityDTO = data.cityDTO;
					if( cnt <= 0 ){
						text += "<tr style='text-align: center;'>";
						text += "	<td colspan='8'>";
						text += "		조회된 내용이 없습니다.";
						text += "	</td>";
						text += "</tr>";
					}else{
						for(i=0; i<cityList.length; i++){
							var num = cnt - ((cityDTO.paging.pageNo - 1) * cityDTO.paging.pageSize + i);
							text += "<tr>";
							text += "	<td>" + num + "</td>";
							text += "	<td>" + cityList[i].city_code + "</td>";
							text += "	<td>" + cityList[i].city_name + "</td>";
							text += "	<td>" + cityList[i].nx + "</td>";
							text += "	<td>" + cityList[i].ny + "</td>";
							text += "	<td>" + cityList[i].longitude + "</td>";
							text += "	<td>" + cityList[i].latitude + "</td>";
							text += "	<td>";
							text += "	<input type='radio' name='use_yn"+cityList[i].city_seq +"' value='1' onclick='fn_useyn_edit("+cityList[i].city_seq+")'";
							if( cityList[i].use_yn == 1 ){
								text += "checked='checked'>사용"; 
							}else {
								text += ">사용"
							};
							text += "	<input type='radio' name='use_yn"+cityList[i].city_seq +"' value='0' onclick='fn_useyn_edit("+cityList[i].city_seq+")'";
							if( cityList[i].use_yn == 0 ){
								text += "checked='checked'>미사용"; 
							}else {
								text += ">미사용"
							};
								
							text += "	</td>";
							text += "</tr>";
						}
					}
					$("#listBody").append(text);
					
					
					$(".paging").empty();
					fn_page_display_ajax_new(cityDTO.paging.pageSize, cnt, '#pageNo', "<c:url value='/admin/getCityList' />", '.paging', '#query');
		    		
		    	}, function(data) {
		    		//errorListener
		    		toastFail("오류가 발생했습니다.3");
		    	}, function() {
		    		//beforeSendListener
		    	}, function() {
		    		//completeListener
		    	});
			}
		});
	}
		
	function fn_data_receive(data){
		
		$("#listBody").empty();
		var text = "";
		var cnt = data.totalCnt;
		var cityList = data.cityList;
		var cityDTO = data.cityDTO;
		if( cnt <= 0 ){
			text += "<tr style='text-align: center;'>";
			text += "	<td colspan='8'>";
			text += "		조회된 내용이 없습니다.";
			text += "	</td>";
			text += "</tr>";
		}else{
			for(i=0; i<cityList.length; i++){
				var num = cnt - ((cityDTO.paging.pageNo - 1) * cityDTO.paging.pageSize + i);
				text += "<tr>";
				text += "	<td>" + num + "</td>";
				text += "	<td>" + cityList[i].city_code + "</td>";
				text += "	<td>" + cityList[i].city_name + "</td>";
				text += "	<td>" + cityList[i].nx + "</td>";
				text += "	<td>" + cityList[i].ny + "</td>";
				text += "	<td>" + cityList[i].longitude + "</td>";
				text += "	<td>" + cityList[i].latitude + "</td>";
				text += "	<td>";
				text += "	<input type='radio' name='use_yn"+cityList[i].city_seq +"' value='1' onclick='fn_useyn_edit("+cityList[i].city_seq+")'";
				if( cityList[i].use_yn == 1 ){
					text += "checked='checked'>사용"; 
				}else {
					text += ">사용"
				};
				text += "	<input type='radio' name='use_yn"+cityList[i].city_seq +"' value='0' onclick='fn_useyn_edit("+cityList[i].city_seq+")'";
				if( cityList[i].use_yn == 0 ){
					text += "checked='checked'>미사용"; 
				}else {
					text += ">미사용"
				};
					
				text += "	</td>";
				text += "</tr>";
			}
		}
		$("#listBody").append(text);
		
		
		$(".paging").empty();
		fn_page_display_ajax_new(cityDTO.paging.pageSize, cnt , '#pageNo', "<c:url value='/admin/getCityList' />", '.paging', '#query');
	};
	function fn_useyn_edit(city_seq){
		var _data = {
				city_seq : city_seq,
				use_yn : $("input[name=use_yn"+city_seq +"]:checked").val()
	    	};
    	var _url = "<c:url value='/admin/setCityDTO' />";
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			toastOk("변경성공!");
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
	}
	
	
</script>
	<article>
		<div class="inputUI_simple">
      		<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
        		<caption>등록일시, 상태, 직접검색 영역</caption>
       			<tr>
         				<th scope="row" class="linelY pdlM">직접검색</th>
         				<td>
           				<div class="select-inner">
           					<select id="srh_type" name="srh_type" class="styled02 floatL wid138" onChange="$('#srh_text').focus();">
             						<option value="" selected="selected">선택</option>
             						<option value="">전체</option>
             						<option value="city_code">도시 코드</option>
	            				<option value="city_name">도시 이름</option>
           					</select>
           				</div>
           				<input type="text" id="query" />
           				<input type="text" id="srh_text" name="srh_text" class="inp-field wid300 mglS" />
           				<div class="btn-module floatL mglS">
             					<a href="#none" id="btnSrh" class="search">검색</a>
           				</div>
					</td>
				</tr>
     		</table>
     	</div>
		<table class="bd-list" summary="도시 코드 리스트">
		    <colgroup>
			    <col width="50px" />
			    <col width="50px" />
			    <col width="100px" />
			    <col width="30px" />
			    <col width="30px" />
			    <col width="50px" />
			    <col width="50px" />
			    <col width="100px" />
		    </colgroup>
	    	<thead>
				<tr>
					<th>번호</th>
					<th>도시 코드</th>
					<th>도시 이름</th>
					<th>X</th>
					<th>Y</th>
					<th>경도</th>
					<th>위도</th>
					<th>사용 여부</th>
				</tr>
			</thead>
			<tbody id="listBody">
				<c:choose>
					<c:when test="${!empty cityList }">
						<c:forEach var="item" items="${cityList }" varStatus="status">
							<tr>
								<td>${totalCnt - ((cityDTO.paging.pageNo - 1) * cityDTO.paging.pageSize + status.index) }</td>
								<td>${item.city_code }</td>
								<td>${item.city_name }</td>
								<td>${item.nx }</td>
								<td>${item.ny }</td>
								<td>${item.longitude }</td>
								<td>${item.latitude }</td>
								<td>
									<input type="radio" name="use_yn${item.city_seq }" value="1" onclick="fn_useyn_edit(${item.city_seq})" <c:if test="${item.use_yn eq '1' }">checked="checked"</c:if>>사용
									<input type="radio" name="use_yn${item.city_seq }" value="0" onclick="fn_useyn_edit(${item.city_seq})" <c:if test="${item.use_yn eq '0' }">checked="checked"</c:if>>미사용
								</td>
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
		
		<input type="hidden" id="city_seq" name="city_seq" />
		<input type="hidden" id="pageNo" name="paging.pageNo" value="${cityDTO.paging.pageNo }" />
		
		<c:if test="${cityDTO.paging.pageSize != '0' }">
			<div class="paging">
				<script type="text/javascript">
					fn_page_display_ajax_new('${cityDTO.paging.pageSize}', '${totalCnt }', '#pageNo', "<c:url value='/admin/getCityList' />", '.paging', '#query')
				</script>
			</div>
		</c:if>
	</article>