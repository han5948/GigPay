<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="/resources/web/js/jquery.mtz.monthpicker.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
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

	google.charts.load('current', {packages: ['corechart', 'line']});

	$(function() {
		$("#btnSrh").click( function() {
			getAccountingList();
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
		//검색
	});

	function changeCompanySeq(obj){
		drawTable();
}

	function getAccountingList() {
		var _data = {
			startDate: $("#startDate").val(),
    		endDate: $("#endDate").val(),
          	srh_use_yn: 1
		};
		var _url = "<c:url value='/admin/getAccountingList' />";
	
		commonAjax(_url, _data, true, function(data) {
			//successListener
			if (data.code == "0000") {
				setData(data.resultList);
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

	var chartData;
	
	function setData(dataList){
		//받은 데이터에서 본사 매출을 을 날짜별 지점 매출의 합을 넣어 준다
		var index = dataList.findIndex(obj => obj.companySeq == "13"); // 본사의 인덱스를 찾는다.
		
		//본사의 수익을 구한다.
		for(i=0; i<dataList[0].resultData.length; i++){
			
			var ilboUnitPrice	= 0;
			var shareFee 		= 0;
			var ilboFee			= 0;
			var outCount			= 0;
			
			
			dataList.forEach(function (item, index, array) {
				if(item.companySeq != "13"){
					ilboUnitPrice += item.resultData[i].ilbo_unit_price *1;
					ilboFee += item.resultData[i].ilbo_fee *1;
					shareFee += item.resultData[i].share_fee *1;
					outCount += item.resultData[i].out_count *1;
				}
			});
			
			dataList[index].resultData[i].ilbo_unit_price = ilboUnitPrice;
			dataList[index].resultData[i].ilbo_fee = ilboFee;
			dataList[index].resultData[i].share_fee = shareFee;
			dataList[index].resultData[i].out_count = outCount;
		}
		
		chartData = dataList;
		drawTable(chartData);
		
			
		//차트 그리기
		google.charts.setOnLoadCallback(drawChart);
	}

	var chartTitle = ""
	
	function drawChart() {
	    var data = new google.visualization.DataTable();
	    data.addColumn('string', 'X');
	    
	    
	    var myArray =  [];
	     
	    chartData.forEach(function (item, index, array) {
	   		data.addColumn('number', item.companyName);
	    });
	     
	  
	 	var resultData = chartData[1].resultData;
	
	 	
	 	for(i=0; i< resultData.length; i++){ // 1일부터 31일까지
	 		 
	 		myArray[i] = new Array();
	 		
	 		var mIndex = 1;
	 		chartData.forEach(function (item, index, array) {
	 				myArray[i][0] = resultData[i].ilbo_date;
	 				
	 				var ilbo_fee = item.resultData[i].ilbo_fee * 1;
		 	     	myArray[i][mIndex++] =  ilbo_fee;	//본사의 수익은 ilbo_fee 의 합으로 처리한다.
	 	    });
	 		
	 	} 
	 	
	    data.addRows(myArray);
	
	    var options = {
	    	title : "본사 대비 지점 매출",
	    	height : 600, // 세로 px
	    	isStacked: true,
	    	fontSize: 15,
			hAxis: {
			    title: '날짜',
			    slantedText:true,
			    slantedTextAngle:90
			},
			vAxis: {
			    title: '매출'
			}
	    };
	
	    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
	    chart.draw(data, options);
	}

	function drawTable(){
		var lineHtml ="";
		var num = 0;
		var totalIlboFee = 0;
		var totalShareFee = 0;
		var totalOutCount = 0;
		var maxShareFee = 0;
		var lineArray =  [];
		
		$(".list").remove();
		var companySeq = $("#company_seq").val();
		
		if(companySeq == "0"){
			var mon = $("#startDate").val().substring(0,7);
			
			chartData.forEach(function (item, index, array) {
		 	    var companyName = item.companyName;
		 	    var ilboFee = 0;
		 	    var shareFee = 0;
		 	    var outCount = 0;
				var resultData = item.resultData;
		 	    
				if(item.companySeq != "13"){
					lineArray[num] = new Array();
					
					for(i=0; i< resultData.length; i++){
			 	    	ilboFee += resultData[i].ilbo_fee*1;
			 	    	shareFee += resultData[i].share_fee*1;
			 	    	outCount += resultData[i].out_count*1;
			 	    }
			 	   	
			 	   	totalIlboFee += ilboFee;
			 	   	totalShareFee += shareFee;
			 	   totalOutCount += outCount;
				 	   
			 	   	lineArray[num][0] = num;
			 	   	lineArray[num][1] = mon;
			 	   	lineArray[num][2] = companyName;
			 	   	lineArray[num][3] = ilboFee;
			 	   	lineArray[num][4] = shareFee;
			 	    lineArray[num][5] = outCount;	//출역횟수
				 	   
			 	   	if(shareFee > maxShareFee)  maxShareFee = shareFee;
				 	   
					num++;
				}
		    });
		}else{
			//해당 지점의 인텍스를 찾는다.
			var index = chartData.findIndex(obj => obj.companySeq == companySeq); // 본사의 인덱스를 찾는다.
			
			var item = chartData[index];
			var companyName = item.companyName;
			var resultData = item.resultData;
			 
			for(i=0; i< resultData.length; i++){
				lineArray[num] = new Array();
			    var nal = resultData[i].ilbo_date
			    var ilboFee = resultData[i].ilbo_fee*1;
			    var shareFee = resultData[i].share_fee*1;
			    var outCount = resultData[i].out_count *1;
				
			    totalIlboFee += ilboFee;
			 	totalShareFee += shareFee;
			 	totalOutCount += outCount;
			
			    lineArray[num][0] = num;
				lineArray[num][1] = nal;
				lineArray[num][2] = companyName;
				lineArray[num][3] = ilboFee;
				lineArray[num][4] = shareFee;
				lineArray[num][5] = outCount;
			    	
			 	if(shareFee > maxShareFee)  maxShareFee = shareFee;
				num++;
			}
		}
	
		for(i=0; i<lineArray.length; i++){
			var item = lineArray[i];
			var perCent =0;
			var backColor = "#0dcc8f";
			
			if(item[4] > 0)		perCent = Math.ceil(item[4] / maxShareFee * 100); //무조건 올림 한다.
			if(item[4] == maxShareFee) backColor = "#f98623";
			
			
			lineHtml +="<tr class='list'>" 
						+"<td>"+ item[0] +"</td>"
						+"<td>"+ item[1]+"</td>"
						+"<td >" + item[2] +"</td>"
						+"<td class='alignRight'>" + comma(item[5]) + "</td>"	/* 출역수  nemojjang 추가 2022-02-03*/
						+"<td class='alignRight'>" + comma(item[3]) + "</td>"
						+"<td class='alignRight'>" + comma(item[4]) + "</td>"
						+"<td><div style='width:"+perCent+"%;background-color:"+backColor+";' title='"+comma(item[4])+" 원'>&nbsp;<div></td>"
						+"</tr>";
		}
		
		lineHtml +="<tr class='list last'><td colspan='3'>합계</td><td class='alignRight'>" + comma(totalOutCount) + "</td><td class='alignRight'>" + comma(totalIlboFee) + "</td><td class='alignRight'>" + comma(totalShareFee) + "</td><td></td></tr>";
		
		$("#accountingData").append(lineHtml);
	}

	//excel 다운
	function downExcel(){
		document.defaultFrm.action = "/admin/accountingExcel";
		document.defaultFrm.submit();
	}
	
	function downTaxExcel(){
		document.defaultFrm.action = "/admin/jnpTaxExcel";
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
          			<col width="550px" />
          			<col width="400px" />
          			<col width="*" />
        		</colgroup>
        		<tr>
          			<th scope="row">직접검색</th>
         				<td>
           				<p class="floatL">
           					<input type="text" id="startDate" name="startDate" />
           	<!-- 
             <input type="text" id="startDate" name="startDate" class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>
             <input type="text" id="endDate" name="endDate" class="inp-field wid100 datepicker" /> -->
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
         					<input type="radio" id="srh_tax_type_1" name="srh_tax_type" class="inputJo" value="systemFee" checked="checked" /><label for="srh_tax_type_1" class="label-radio">시스템 이용료</label>
            				<input type="radio" id="srh_tax_type_2" name="srh_tax_type" class="inputJo" value="runningRoyalty" /><label for="srh_tax_type_2" class="label-radio">러닝로얄티</label>
            				<div class="btn-module" style="display: inline-block;">
	            				<a href="javascript:void(0)" id="TaxExport" class="excel" onClick="downTaxExcel()">세금계산서</a>
            				</div>
          				</td>
          				<td> 
         				</td>
       			</tr>
     			</table>
     		</div>
   	</article>
	
	<!-- 그래프영역 -->
	<div style="width:100%;height:650px; overflow: auto;">
		<div id="chart_div" style="width: 100%; height: 600px;text-align:center;  "></div>
	</div>

	<div class="btn-module mgtL">
		<div class="select-inner">
			<select id="company_seq" name="company_seq" class="styled02 " onChange="changeCompanySeq(this)">
				<option value="0" >전체</option>
				<c:forEach items="${companyList}" var="item" varStatus="status">
		         	<option value="${item.company_seq }" >${item.company_name}</option>
		   		</c:forEach>
         	</select>
		</div>

		<a href="javascript:void(0)" id="export" class="excel mglL" onClick="downExcel()">excel</a>
	</div>
         	
   	<div class="mgtS">
		<table id="accountingData" class="bd-list" summary="게시판리스트">
			<caption>등록일시, 상태, 직접검색 영역</caption>
	        <colgroup>
	          	<col width="100px" />
	          	<col width="250px" />
	          	<col width="300px" />
	          	<col width="100px" />
	          	<col width="250px" />
	          	<col width="250px" />
	          	<col width="*" />
	        </colgroup>
	        <tr>
	          	<th>번호</th>
	          	<th>날짜</th>
	          	<th >지점명</th>
	          	<th >출역수</th>
	          	<th  >본사매출</th>
	          	<th >지점매출</th>
	          	<th >비고</th>
			</tr>
		</table>
	</div>
