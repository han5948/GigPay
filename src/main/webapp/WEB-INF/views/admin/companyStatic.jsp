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

	$(function() {
		$("#btnSrh").click( function() {
			getCompanyStatic();
		});

		var date = new Date();
		var cur_year = date.getFullYear();
		var cur_month = date.getMonth() + 1;
		if( cur_month < 10 ){
			cur_month = "0" + cur_month;
		}
		
		$("#day_type_4").trigger('click');
		
	});
	
	var dataList;
	var chartData;
	
	function changeCompanySeq(obj){
		setData();
	}

	function getCompanyStatic() {
		var _data = {
			startDate: $("#startDate").val(),
    		endDate: $("#endDate").val(),
          	srh_use_yn: 1
		};
		var _url = "<c:url value='/admin/getCompanyStatic' />";
	
		commonAjax(_url, _data, true, function(data) {
			//successListener
			if (data.code == "0000") {
			
				dataList = data.resultList; 
				setData();
				
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

	function setData(){
		chartData = new Array();
		
		var oldCompanySeq	= 0;
		var companySeq	= "0";
		var companySum 	= 0;
		var openSum			= 0;
		var shareSum		= 0;
		var jnpSum			= 0;
		var feeSum			= 0;
		var oldCompanyName = "";
	
		var companySeq = $("#company_seq").val();
		
		if(companySeq == "0"){
			var arrItem = ['지점', '지점매출',  '공유매출', '타지점쉐어매출', '본사쉐어매출', { role: 'annotationText' }, { role: 'annotationText' }];
			//var arrItem = ['aa', 'bb',  'cc','dd','ee', { role: 'annotation' }];
			chartData.push(arrItem); 
					
			
			dataList.forEach(function (item, index, array) {
				
				companySeq = item.company_seq;
				companySum += item.companySum;
				openSum += item.openSum;
				shareSum += item.shareSum;
				jnpSum += item.jnpSum;
				
				if(oldCompanySeq == 0){
					oldCompanySeq = companySeq ;
					oldCompanyName = item.company_name;
				}
				
				if(companySeq != oldCompanySeq ){
					
					arrItem = [oldCompanyName, companySum, openSum, shareSum, jnpSum,'C', oldCompanySeq];
					chartData.push(arrItem);
					
					oldCompanySeq = companySeq;
					oldCompanyName = item.company_name;
					
					companySum	= 0;
					openSum		= 0;
					shareSum		= 0;
					jnpSum			= 0;
					feeSum			= 0;
				}
			});
			
			arrItem = [oldCompanyName, companySum, openSum, shareSum,jnpSum, 'C', oldCompanySeq];
			chartData.push(arrItem);
		}else{
			var arrItem = ['날짜', '지점매출',  '공유매출', '타지점쉐어매출', '본사쉐어매출', { role: 'annotationText' }, { role: 'annotationText' }];
			chartData.push(arrItem); 
					
			
			dataList.forEach(function (item, index, array) {
				
				if(companySeq == item.company_seq){
				
					companySum += item.companySum;
					openSum += item.openSum;
					shareSum += item.shareSum;
					jnpSum += item.jnpSum;
						
					arrItem = [item.ilbo_date,item.companySum, item.openSum,  item.shareSum, item.jnpSum, 'n', item.ilbo_date];
					chartData.push(arrItem);
				}
			});
			
			
		}	
				
		google.charts.load("current", {packages:['corechart']});
		google.charts.setOnLoadCallback(drawChart);
		drawTable();
		
	}

	function drawChart() {
		
		var data = google.visualization.arrayToDataTable(chartData);
		var view = new google.visualization.DataView(data);
		var options = {
				    title : "지점상세매출",
			        height: 800,
			        legend: { position: 'top', maxLines: 3 },
			        bar: { groupWidth: '90%' },
			        isStacked: true,
			        hAxis: {
					    slantedText:true,
					    slantedTextAngle:45,
					    textStyle : {
				            color: "#000",
				            fontName: "sans-serif",
				            fontSize: 14,
				            //bold: true,
				            italic: false
				        }
					},
		};
		 
		var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
	    chart.draw(view, options);
	    
	    google.visualization.events.addListener(chart, 'select', selectHandler);
	    
	    function selectHandler(e) {
			var selection = chart.getSelection();
			var item = selection[0];
			var argType = data.getFormattedValue(item.row, 5);
			
			var arg = data.getFormattedValue(item.row, 6);
			
			 setSubChart(argType,arg)
	        
		}
	}
	
	function setSubChart(argType,arg){
		//차트
		google.charts.load("current", {packages:["corechart"]});
        google.charts.setOnLoadCallback(drawChart);
        
        function drawChart() {
        	var data = new google.visualization.DataTable();
        	data.addColumn("string", "제목");
        	data.addColumn("number", "합계");
        	
        	var companySum 	= 0;
    		var openSum			= 0;
    		var shareSum		= 0;
    		var jnpSum			= 0;
    		var totalSum			= 0;
        	var chartTitle ="";
        	
        	if(argType == 'C'){
	        	dataList.forEach(function (item, index, array) {
	    			
	    			company_seq = item.company_seq;
	    			
	    			if(arg == company_seq){
	    				companySum += item.companySum;
		    			openSum += item.openSum;
		    			shareSum += item.shareSum;
		    			jnpSum += item.jnpSum;
		    			totalSum +=  item.totalSum;
		    			chartTitle = item.company_name
	    			}
	        	});
	        	
	        	data.addRow(["지점매출",  companySum]);
            	data.addRow(["공유매출",  openSum]);
            	data.addRow(["타지점쉐어매출",  shareSum]);
            	data.addRow(["본사쉐어매출",  jnpSum]);
        	}else{
        			var companySeq = $("#company_seq").val();
					
        			dataList.forEach(function (item, index, array) {
	    			
	    			company_seq = item.company_seq;
	    			
	    			if(companySeq == company_seq && arg == item.ilbo_date){
	    				
		    			chartTitle = item.company_name + " ( " + arg + ")";
		    			
		    			data.addRow(["지점매출",  item.companySum]);
		            	data.addRow(["공유매출",  item.openSum]);
		            	data.addRow(["타지점쉐어매출",  item.shareSum]);
		            	data.addRow(["본사쉐어매출",  item.jnpSum]);
		        		
	    			}
	        	});
        	}
        	
          	var options = {
          		title : chartTitle,
            	pieHole: 0.3,
            	fontSize:14,
            	titleTextStyle:{
            		fontSize: 16,
            		bold: true,
            		fontName: "notokr-regular",
            	},
            	chartArea:{
            		width: "100%",
            		height: "50%"
            	},
            	pieSliceText : "value"
          	};
          	var companyChart = new google.visualization.PieChart(document.getElementById('company-chart'));
          	companyChart.draw(data, options);
        }
	}
	

	
	function drawTable(){
		var lineHtml ="";
		var num = 0;
		var totalCompanySum = 0;
		var totalOpenSum = 0;
		var totalShareSum = 0;
		var totalJnpSum = 0;
		var totalSum = 0;
		var totalShareJnpSum = 0;
		
		
		$(".list").remove();
		var companySeq = $("#company_seq").val();
		
		if(companySeq == "0"){
			
			chartData.forEach(function (item, index, array) {
				
		 	   if(index > 0){
		 		   var total = item[1] + item[2] + item[3] +  item[4];
		 		   
		 			totalCompanySum +=  item[1];
		 			totalOpenSum += item[2];
		 			totalShareSum += item[3];
		 			totalJnpSum += item[4];
		 			totalSum  +=  total;
		 			totalShareJnpSum = totalShareJnpSum + item[3] + item[4];
		 			
		 			var nal = $("#startDate").val() + "~" + $("#endDate").val()
		 			
			 	    lineHtml +="<tr class='list'>" 
			 		  +"<td>"+ index +"</td>"
			 	   		+"<td >"+ item[0] +"</td>"
			 	   		+"<td >"+ nal +"</td>"
						+"<td class='alignRight'>"+ comma(item[1])+"</td>"
						+"<td class='alignRight'>" + comma(item[2]) +"</td>"
						+"<td class='alignRight'>" + comma(item[3]) + "</td>"	/* 출역수  nemojjang 추가 2022-02-03*/
						+"<td class='alignRight'>" + comma(item[4]) + "</td>"
						+"<td class='alignRight'>" + comma(total) + "</td>"
						+"<td class='alignRight'>" + comma(item[3] + item[4]) + "</td>"
						+"</tr>";
					
		 	   }
			 });
	
			lineHtml +="<tr class='list last'><td colspan='2'>합계</td>"
						+"<td class='alignRight'>" + comma(totalCompanySum) + "</td>"
						+ "<td class='alignRight'>" + comma(totalOpenSum) + "</td>"
						+"<td class='alignRight'>" + comma(totalShareSum) + "</td>"
						+"<td class='alignRight'>" + comma(totalJnpSum) + "</td>"
						+"<td class='alignRight'>" + comma(totalSum) + "</td>"
						+"<td class='alignRight'>" + comma(totalShareJnpSum) + "</td>"
						+"</tr>";
			$("#accountingData").append(lineHtml);
		   
		}else{

			
        	dataList.forEach(function (item, index, array) {
    			
    			company_seq = item.company_seq;
    			
    			if(companySeq == company_seq){
    				totalCompanySum += item.companySum;
    				totalOpenSum += item.openSum;
    				totalShareSum += item.shareSum;
    				totalJnpSum += item.jnpSum;
	    			totalSum +=  item.feeSum;
	    			totalShareJnpSum = totalShareJnpSum + item.shareSum +  item.jnpSum;
	    			
	    			lineHtml +="<tr class='list'>" 
				 		  +"<td>"+ index +"</td>"
				 	   		+"<td >"+ item.company_name +"</td>"
				 	   		+"<td >"+ item.ilbo_date +"</td>"
							+"<td class='alignRight'>"+ comma(item.companySum)+"</td>"
							+"<td class='alignRight'>" + comma(item.openSum) +"</td>"
							+"<td class='alignRight'>" + comma(item.shareSum) + "</td>"	/* 출역수  nemojjang 추가 2022-02-03*/
							+"<td class='alignRight'>" + comma(item.jnpSum) + "</td>"
							+"<td class='alignRight'>" + comma(item.feeSum) + "</td>"
							+"<td class='alignRight'>" + comma(item.shareSum + item.jnpSum) + "</td>"
							+"</tr>";
    			}
        	});
							
   			lineHtml +="<tr class='list last'><td colspan='2'>합계</td>"
				+"<td class='alignRight'>" + comma(totalCompanySum) + "</td>"
				+ "<td class='alignRight'>" + comma(totalOpenSum) + "</td>"
				+"<td class='alignRight'>" + comma(totalShareSum) + "</td>"
				+"<td class='alignRight'>" + comma(totalJnpSum) + "</td>"
				+"<td class='alignRight'>" + comma(totalSum) + "</td>"
				+"<td class='alignRight'>" + comma(totalShareJnpSum) + "</td>"
				+"</tr>";
				
			$("#accountingData").append(lineHtml);
		}
	
	}

	//excel 다운
	function downExcel(){
		document.defaultFrm.action = "/admin/accountingExcel";
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
							<input type="text" id="startDate" name="startDate" class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>
							<input type="text" id="endDate" name="endDate" class="inp-field wid100 datepicker" />
						</p>
						<div class="inputUI_simple">
							<span class="contGp btnCalendar">
								<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'prev3Month' ); $('#btnSrh').trigger('click');" /><label for="day_type_1" class="label-radio">석달전</label>
								<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'prev2Month' ); $('#btnSrh').trigger('click');" /><label for="day_type_2" class="label-radio">두달전</label>
								<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'prev1Month' ); $('#btnSrh').trigger('click');" /><label for="day_type_3" class="label-radio">한달전*</label>
								<input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('startDate', 'endDate', 'nowMonth' ); $('#btnSrh').trigger('click');" checked="checked"/><label for="day_type_4" class="label-radio on">이번달</label>
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
	
	<div style="width:100%;height:850px; overflow: auto">
		<div id="chart_div" style="width: 80%; height: 700px;text-align:center;float:left  "></div>
		<div id="company-chart" style="width: 20%; height: 700px;text-align:center;float:left  "></div>
	</div>

	<div class="btn-module mgtL">
		<div class="select-inner">
			<select id="company_seq" name="company_seq" class="styled02 " onChange="changeCompanySeq(this)">
				<option value="0" >전체</option>
				<c:forEach items="${companyList}" var="item" varStatus="status">
					<c:if test="${item.company_seq  ne '1' && item.company_seq  ne '13'}">
		         		<option value="${item.company_seq }" >${item.company_name}</option>
		         	</c:if>
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
	          	<col width="250px" />
	          	<col width="250px" />
	          	<col width="250px" />
	          	<col width="250px" />
	          	<col width="250px" />
	          	<col width="250px" />
	          	<col width="250px" />
	        </colgroup>
	        <tr>
	          	<th>번호</th>
	          	<th>지점명</th>
	          	<th>날짜</th>
	          	<th>지점매출</th>
	          	<th>공유매출</th>
	          	<th>타지점쉐어매출</th>
	          	<th>본사쉐어매출</th>
	          	<th>매출합계</th>
	          	<th>쉐어합계</th>
			</tr>
		</table>
	</div>
