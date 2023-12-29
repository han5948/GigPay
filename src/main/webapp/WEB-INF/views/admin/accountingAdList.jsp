<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript" src="/resources/web/js/jquery.mtz.monthpicker.js"></script>
<style>
    .overlay {
		width: 150px;
        height: 68px;
        position: absolute;
        top: 68px;   /* chartArea top  */
        right: 20px; /* chartArea left */
    }
</style>

<script type="text/javascript">

	//윈도우 사이즈가 변할때 높이 넓이
	$(window).resize(function() {
	    var reWidth = $(window).width() -optWidth;
	    if(reWidth < baseWidth) reWidth = baseWidth;
	    
	    var reHeight = $(window).height() -optHeight;
	    if(reHeight < baseHeight) reHeight = baseHeight;
	    
	    $("#list").setGridWidth(reWidth) ; 
	    $("#list").setGridHeight(reHeight);
	});

// google.charts.load('current', {packages: ['corechart', 'line']});
// google.charts.load('current', {'packages':['bar']});
	google.charts.load('current', {'packages':['corechart']});
	$(function() {
		$("#btnSrh").click( function() {
			getAccountAdList();
		});

		var date = new Date();
		var cur_year = date.getFullYear();
		var cur_month = date.getMonth() + 1;
		if( cur_month < 10 ){
			cur_month = "0" + cur_month;
		}
	
		var options = {
			pattern: 'yyyy-mm',
			selectedYear: cur_year,
			selectedMonth: cur_month,
			startYear: cur_year - 5,
			finalYear: cur_year + 2,
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			openOnFocus: true,
			disableMonths: [],
			id:'monthpicker_1'
		};
		
		$("#startDate").monthpicker(options);
		$("#startDate").val(cur_year+"-"+cur_month);
		
		$("#day_type_4").trigger('click');
	});
	
	function changeCompanySeq(obj){
		drawTable();
	}
	
	function getAccountAdList() {
		var _data = {
			startDate	: $("#startDate").val(),
		};
		var _url = "<c:url value='/admin/getAccountingAdList' />";
	
		commonAjax(_url, _data, true, function(data) {
			//successListener
			if (data.code == "0000") {
	 			setData(data.accountAdList);
		
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						alert(data.message);
					} else {
						toastFail("오류가 발생했습니다.1");
					}
				} else {
					toastFail("오류가 발생했습니다.2");
				}
			}
		}, function(data) {
			//errorListener
			$(".wrap-loading").hide();
			toastFail("오류가 발생했습니다.3");
		}, function() {
			//beforeSendListener
			$(".wrap-loading").show();
		}, function() {
			//completeListener
			$(".wrap-loading").hide();
		});
	}

	var chartDataArray;
	var weekOfDayArray = ["없음","일","월","화","수","목","금","토"];
	function setData(dataList){
		chartDataArray = [['날짜', '매출', '수입', '광고료']];
		
		for(i=0; i<dataList.length; i++){
			chartDataArray.push([dataList[i].ilbo_date+"(" + weekOfDayArray[dataList[i].day_of_week]+")" , Number(dataList[i].total_sales), Number(dataList[i].ilbo_fee), Number(dataList[i].ad_price)] );
		}
		
		//차트 그리기
		google.charts.setOnLoadCallback(drawChart);
		//테이블 값 넣기
		drawTable(dataList);
	}
	
	function drawChart() {
		$("#columnchart_material").empty();
		var data = google.visualization.arrayToDataTable(chartDataArray);
		
		var options = {
			title : '본사 매출 통계',
			vAxis:{
				 textStyle:{
	  	        	  fontSize:14,
	  	        	  fontName:'notokr'
	  	          }
			},
	        hAxis: {
	        	slantedText:true,
	  	        slantedTextAngle:90,
	  	        textStyle:{
	  	        	fontSize:14,
	  	        	fontName:'notokr'
	  	        }
	  	          
	  	    },
	        seriesType: 'bars',
	        series: {
	        	0: {color:"#BCDEDE"},
	        	1: {color:"#E7E7CF"},
	        	2: {type: 'line', color:"red"}
	        }
		};
		
		var chart = new google.visualization.ComboChart(document.getElementById('columnchart_material'));
		chart.draw(data, options);
	}


	function drawTable(dataList){
		var text = "";
		var totalTotalSales = 0;
		var totalfee = 0;
		var totalAdPrice = 0;
		var maxAdPrice = 0;
		
		for(i=0; i<dataList.length; i++){ //광고료 최고값 구하기
			if( maxAdPrice < dataList[i].ad_price ){
				maxAdPrice = dataList[i].ad_price;
			}
		}
		
		var backColor = "#0dcc8f";
		for(i=0; i<dataList.length; i++){
			var perCent = 0;
			if( maxAdPrice != 0 ){
				perCent = Math.ceil(dataList[i].ad_price / maxAdPrice * 100); //무조건 올림 한다.
			}
			
			if(perCent == 100){
				backColor = "#f98623";
			}else{
				backColor = "#0dcc8f";
			}
			text += "<tr>";
			text +=	"	<td>"+(i+1)+"</td>";
			text += "	<td>";
			text += 		dataList[i].ilbo_date
			text += "	</td>";
			text += "	<td>";
			text += 		comma(dataList[i].total_sales);
			text += "	</td>";
			text += "	<td>";
			text += 		comma(dataList[i].ilbo_fee);
			text += "	</td>";
			text += "	<td>";
			text += 		comma(dataList[i].ad_price);
			text += "	</td>";
			text += "	<td>";
			text += 		"<div style='width:"+perCent+"%;background-color:"+backColor+";' title='"+comma(dataList[i].ad_price)+" 원'>&nbsp;<div>";
			text += "	</td>";
			text += "</tr>";
			
			totalTotalSales += dataList[i].total_sales * 1;
			totalfee += dataList[i].ilbo_fee * 1;
			totalAdPrice += dataList[i].ad_price * 1;
			
		}
		text += "<tr style='font-weight:bold;'>";
		text +=	"	<td colspan='2'>합계</td>";
		text += "	<td>";
		text += 		comma(totalTotalSales);
		text += "	</td>";
		text += "	<td>";
		text += 		comma(totalfee);
		text += "	</td>";
		text += "	<td>";
		text += 		comma(totalAdPrice);
		text += "	</td>";
		text += "	<td>";
		text += 		"";
		text += "	</td>";
		text += "</tr>";
		$("#accountAdTbody").html(text);
	}

	//excel 다운
	function downExcel(){
		document.defaultFrm.action = "/admin/accountingAdListExcel";
		document.defaultFrm.submit();
	}
//]]>
</script>
    <article>
      	<div class="inputUI_simple">
      		<table class="bd-form s-form" summary="검색 영역 입니다.">
        		<caption>조회일시, 상태, 직접검색 영역</caption>
        		<colgroup>
          			<col width="120px" />
          			<col width="*" />
          			<col width="200px" />
          			<col width="150px" />
        		</colgroup>
        		<tr>
          			<th scope="row">직접검색</th>
          			<td>
            			<p class="floatL">
              				<input type="text" id="startDate" name="startDate" />
            			</p>
            			
            			<div class="inputUI_simple">
	            			<span class="contGp btnCalendar">
	              				<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setMonthPicker(3); $('#btnSrh').trigger('click');" /><label for="day_type_1" class="label-radio">석달전</label>
	              				<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setMonthPicker(2); $('#btnSrh').trigger('click');" /><label for="day_type_2" class="label-radio">두달전</label>
	              				<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setMonthPicker(1); $('#btnSrh').trigger('click');" /><label for="day_type_3" class="label-radio">한달전</label>
	              				<input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setMonthPicker(0); $('#btnSrh').trigger('click');" checked="checked"/><label for="day_type_4" class="label-radio on">이번달</label>
				             
		            			<div class="btn-module floatL mglS">
		              				<a href="#none" id="btnSrh" class="search">검색</a>
		            			</div>
		         			</span>
            			</div>
          			</td>
          			<td>
           			</td>
           			<td> 
          			</td>
        		</tr>
      		</table>
      	</div>
    </article>
	
	<!-- 그래프영역 -->
	<div style="width:100%;height:650px; overflow: auto;">
		<div id="columnchart_material" style="width: 100%; height: 600px;text-align:center;  "></div>
	</div>

	<div class="btn-module mgtL">
		<a href="javascript:void(0)" id="export" class="excel" onClick="downExcel()">excel</a>
	</div>
	<div class="mgtS">
		<table id="accountingData" class="bd-list" summary="게시판리스트">
			<caption>등록일시, 상태, 직접검색 영역</caption>
			<colgroup>
				<col width="100px" />
				<col width="250px" />
		        <col width="300px" />
		        <col width="300px" />
		        <col width="300px" />
		        <col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
			        <th>날짜</th>
			        <th>매출</th>
			        <th>수수료</th>
			        <th>네이버 광고료</th>
			        <th>비고</th>
				</tr>
			</thead>
			<tbody id="accountAdTbody">
	         		
			</tbody>
		</table>
	</div>
