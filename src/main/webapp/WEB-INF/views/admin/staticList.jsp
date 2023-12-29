<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<style>
    .overlay {
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
   // $("#list").setGridHeight(reHeight);
	});

	var authLevel = ${sessionScope.ADMIN_SESSION.auth_level};
	var searchMode = "D";

	var counselorData = false; //상담사 데이터 유무
	var isSelect = false;

	google.charts.load('current', {'packages':['corechart', 'bar']});

	$(function() {				
		// 지난 1달
		var nowDate = new Date();
		var monthDate = nowDate.getTime() - (30 * 24 * 60 * 60 * 1000);
		nowDate.setTime(monthDate);
		 
		var monthYear = nowDate.getFullYear();
		var monthMonth = nowDate.getMonth() + 1;
		var monthDay = nowDate.getDate();
		        
		if(monthMonth < 10){ monthMonth = "0" + monthMonth; }
		if(monthDay < 10) { monthDay = "0" + monthDay; }
		        
		var resultDate = monthYear + "-" + monthMonth + "-" + monthDay;
	  
		//$("#startDate").val(getDateString(d));
		$("#startDate").val(resultDate);
		$("#endDate").val(toDay);

	  	// jqGrid 생성
	  	$("#list").jqGrid({
	  		url: "/admin/getStaticList",
            datatype: "json",                               // 로컬그리드이용
            mtype: "POST",
            postData: {
            	searchMode: searchMode,
      			startDate	: $("#startDate").val(),
        		endDate		: $("#endDate").val(),
              	srh_use_yn	: 1,
                page	: 1
            },

  //       height: jqHeight -600,                               // 그리드 높이
  			height: 700,
            width: jqWidth,
         	scrollerbar: true,       
//           autowidth: true,
		   	footerrow: true,

            rowNum: -1,
            pager: '#paging',                            // 네비게이션 도구를 보여줄 div요소
         	viewrecords: true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무

         	multiselect: false,
        	multiboxonly: false,
            caption: "기간통계",                          // 그리드타이틀

          	rownumbers: true,                                 // 그리드 번호 표시
         	rownumWidth: 40,                                   // 번호 표시 너비 (px)

            cellEdit: false ,
    /*        
          jsonReader: {
                    id: "ilbo_date",
           repeatitems: false
                    }, */
 
            // 컬럼명
            colNames: ['지점','날짜', '거래액합', '출역수','총 수수료', '내 수수료','지점 쉐어료', '상담사 수수료', '일당 합계'],

            // 컬럼별 속성
            colModel: [
				{name: 'company_name', index: 'company_name', align: "center", width: 120, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'ilbo_date', index: 'ilbo_date', align: "center", width: 120, sortable: true,search:false},
                {name: 'unitPriceSum', index: 'unitPriceSum', align: "center", width: 120, sortable: true, 
					summaryType: "sum",
					formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0},
					search:false
				},
				{name: 'output_cnt', index: 'output_cnt', align: "center", width: 120 },
                  /* 
                  {name: ${sessionScope.ADMIN_SESSION.auth_level eq '3' ? "'shareSum'":"'feeSum'"}, index: ${sessionScope.ADMIN_SESSION.auth_level eq '3' ? "'shareSum'":"'feeSum'"}   	  	, align:   "center", width: 120, sortable:  true, 
                 		 summaryType: "sum",
                          formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0},
                          cellattr: function(rowId, tv, rowObject, cm, rdata) {

                              var price = parseInt(rowObject.feeSum);
                              return 'style="background:'+getFeeSumBackColor(price,"#ece083") + '"';
                              
                            },
                          search:false
                   },

                   {name: ${sessionScope.ADMIN_SESSION.auth_level eq '3' ? "'feeSum'" : "'shareSum'"}, index: ${sessionScope.ADMIN_SESSION.auth_level eq '3' ? "'feeSum'" : "'shareSum'"}   	  	, align:   "center", width: 120, sortable:  true, 
                  		 summaryType: "sum",
                           formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0},
                            cellattr: function(rowId, tv, rowObject, cm, rdata) {

                               var price = parseInt(rowObject.feeSum);
                               return 'style="background:'+getFeeSumBackColor(price,"#ece083") + '"';
                               
                             }, 
                           search:false
                    },
 				*/
 				{name: 'salesSum', align: "center", width: 120, sortable: true, 
					summaryType: "sum",
					formatter: salesSum, formatoption: {thousandsSeparator: ",", decimalPlace: 0},
					search: false
				},
				{name: 'feeSum', index: 'feeSum', align: "center", width: 120, sortable: true, 
					summaryType: "sum",
					formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0},
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						var price = parseInt(rowObject.feeSum);
						return 'style="background:'+getFeeSumBackColor(price,"#ece083") + '"';
					},
					search: false
				},

				{name: 'shareSum', index: 'shareSum', align: "center", width: 120, sortable: true, 
					summaryType: "sum",
					formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0},
                    cellattr: function(rowId, tv, rowObject, cm, rdata) {
                    	
							 if( rowObject.company_seq == "13" ){
								 var price = parseInt(rowObject.feeSum);
	                             if( price != rowObject.shareSum ){
	                            	 return 'style="color:#f42958;"';	 
	                             }	 
							 }
                             return '';
                    },
					search:false
				},

				{name: 'counselorSum', index: 'counselorSum', align: "center", width: 120, sortable: true, 
					summaryType: "sum",
					formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0},
					search:false
				},
                {name: 'paySum', index: 'paySum', align: "center", width: 120, sortable: true, 
					summaryType: "sum",
					formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0},
					search:false
				}
			],
        	// Grid 로드 완료 후
        	loadComplete: function(data) {
        		
				var priceSumTotal = $("#list").jqGrid('getCol','unitPriceSum',false, 'sum');
				var feeSumTotal = $("#list").jqGrid('getCol','feeSum',false, 'sum');
				var shareSumTotal = $("#list").jqGrid('getCol','shareSum',false, 'sum');
				var deductionSumTotal = $("#list").jqGrid('getCol','deducationSum',false, 'sum');
				var counselorSumTotal = $("#list").jqGrid('getCol','counselorSum',false, 'sum');
				var paySumTotal = $("#list").jqGrid('getCol','paySum',false, 'sum');
			    var salesSum = $("#list").jqGrid('getCol', 'salesSum', false, 'sum');        
				
				//footer 적용
				$("#list").jqGrid("footerData", "set", {
					ilbo_date : "합계",
					unitPriceSum : priceSumTotal,
					salesSum : salesSum,
					feeSum : feeSumTotal,
					shareSum : shareSumTotal,
					deducationSum : deductionSumTotal,
					counselorSum : counselorSumTotal,
					paySum : paySumTotal
				});
			            
				isGridLoad = true;
                        
				setChart(data);
			},

			loadBeforeSend: function(xhr) {
				isGridLoad = false;
				optHTML = "";
				xhr.setRequestHeader("AJAX", true);
			},
			loadError: function(xhr, status, error) {
				if ( xhr.status == "901" ) {
					location.href = "/admin/login";
				}
      		}
  		});

  		$("#list").jqGrid('filterToolbar', {searchOperators : true});

  		//새로고침
  		$("#btnRel").click( function() {
    		$("#list").trigger("reloadGrid");                       // 그리드 다시그리기
  		});

  		//검색
  		$("#btnSrh").click( function() {
    		// 검색어 체크
    		if ( $("#srh_type option:selected").val() != "" && $("#srh_text").val() == "" ) {
      			alert('검색어를 입력하세요.');
      			$("#srh_text").focus();

      			return false;
    		}

    		if($("#company_seq option:selected").data("counselor") == 'C') {
    			counselorData = true;
    	
    			$("#list").setGridParam({
	//            page: pageNum,
	//          rowNum: 15,
	        		postData: {
	        			admin_id : $("#company_seq").val(),
	        			searchMode : searchMode,
	      				startDate : $("#startDate").val(),
	        			endDate	: $("#endDate").val(),
	        		}
	    		}).trigger("reloadGrid");
    		}else {
    			counselorData = false;
	    		$("#list").setGridParam({
	//            page: pageNum,
	//          rowNum: 15,
	        		postData: {
	        			admin_id : '',
	        			company_seq	: $("#company_seq").val(),
	        			searchMode : searchMode,
	      				startDate : $("#startDate").val(),
	        			endDate	: $("#endDate").val(),
	        		}
	    		}).trigger("reloadGrid");
    		}

    		lastsel = -1;
  		});
	});

	var chartData;

	function setChart(resultData){
		//초기화 
		chartData = new Array();
		$("#chartContainer").html("");
	
		chartData.push(['일자', '금액', { role: 'annotation'}, { role: "style"}]);

		var list = resultData.rows;
	
		if(list.length  > 0){
			for(var i = 0; i < list.length; i++){
				var sumData = 0 
				if(authLevel == 3 || counselorData){
					sumData = list[i].counselorSum;
				// sumData = list[i].counselorSum;
				}else{
					if(list[i].company_seq == '13') {
						sumData = list[i].feeSum + list[i].shareSum;						
					}else {
						sumData =  list[i].feeSum;	
					}
				}			
			 
				chartData.push( [ list[i].ilbo_date, sumData, sumData , getFeeSumBackColor(sumData,null) ]);
			}
			google.charts.setOnLoadCallback(drawChart);
		}
	}

	function drawChart(){
		// 차트 데이터 설정
		var data = google.visualization.arrayToDataTable(chartData);
	
		// 그래프 옵션
		var options = {
			title : '각지점별 매출', // 제목
		//	width : 1650, // 가로 px
			height : 700, // 세로 px
			isStacked: true,
			fontSize: 15,
			chartArea: {
        		height:450,
				top:80,
				left:100,
				right:50
        	},
			bar : {
				groupWidth : '50%' // 그래프 너비 설정 %
			},
			legend: { position: 'none' },
		 	vAxis: {
		        	title: '매출'
		      	},
        	hAxis: {
			//	direction:-1,		//그래프의 순서를 거꾸로...
	            slantedText:true,
            	slantedTextAngle:90 // here you can even use 180
        	}
		};
	
		var chart = new google.visualization.ColumnChart(document.getElementById('chartContainer'));
		chart.draw(data, options);
	}

	function setSearchMode( mode) {
		searchMode = mode;
		setLabel();
	}


	function salesSum(cellValue, options, rowObject) {
		return comma(rowObject.feeSum + rowObject.shareSum);
	}
	
	function setLabel(){
		if(searchMode == "Y"){
			$("#legend1").html("30,000");
			$("#legend2").html("20,000");
			$("#legend3").html("10,000");
			$("#legend4").html("5,000");
			$("#legend5").html("3,000");
			$("#legend6").html("3,000");
		}if(searchMode == "M"){
			$("#legend1").html("5,000");
			$("#legend2").html("3,000");
			$("#legend3").html("2,000");
			$("#legend4").html("1,000");
			$("#legend5").html("500");
			$("#legend6").html("500");
		}else{
			$("#legend1").html("300");
			$("#legend2").html("200");
			$("#legend3").html("100");
			$("#legend4").html("50");
			$("#legend5").html("30");
			$("#legend6").html("30");
		}
	}

	function getFeeSumBackColor(price,defaultColor){
		var dPrice1 = 3000000;
		var dPrice2 = 2000000;
		var dPrice3 = 1000000;
		var dPrice4 = 500000;
		var dPrice5 = 300000;
		
		if(searchMode == "Y"){
			 dPrice1 = 300000000;
		     dPrice2 = 200000000;
		     dPrice3 = 100000000;
		     dPrice4 = 50000000;
		     dPrice5 = 30000000;
		} else if(searchMode == "M"){
			 dPrice1 = 50000000;
		     dPrice2 = 30000000;
		     dPrice3 = 20000000;
		     dPrice4 = 10000000;
		     dPrice5 = 5000000;
		} 
		
		if ( price >= dPrice1 ) 
			return "#f42958";  
	    else if (price >= dPrice2)
	    	return "#d529f4";
		else if ( price >= dPrice3)	
			return "#297af4";
		else if ( price >= dPrice4)	
			return "#0dcc8f";
		else if ( price >= dPrice5)	
			return "#f4c829";
		else
			return "#ece083";
	}
//]]>
</script>
<%--
    <div class="title">
      <h3> 관리자 > 지점관리 </h3>
    </div>
--%>
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
              				<input type="text" id="startDate" name="startDate" class="inp-field wid100 datepicker" /> 
              				<span class="dateTxt">~</span>
              				<input type="text" id="endDate" name="endDate" class="inp-field wid100 datepicker" />
            			</p>
            
            			<div class="inputUI_simple">
	            			<span class="contGp btnCalendar">
	              				<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setSearchMode('D'); $('#btnSrh').trigger('click');" checked="checked" /><label for="day_type_1" class="label-radio on">일별</label>
	              				<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setSearchMode('M'); $('#btnSrh').trigger('click');" /><label for="day_type_2" class="label-radio">월별</label>
	              				<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setSearchMode('Y'); $('#btnSrh').trigger('click');" /><label for="day_type_3" class="label-radio">년별</label>
	             
		            			<div class="btn-module floatL mglS">
		              				<a href="#none" id="btnSrh" class="search">검색</a>
		            			</div>
		         			</span>
            			</div>
          			</td>
          			<td style="">
	          			<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0 or (sessionScope.ADMIN_SESSION.auth_level eq '1' and sessionScope.ADMIN_SESSION.company_seq eq '13') }">
		          			<div class="select-inner">
		          				<select id="company_seq" name="company_seq" class="styled02 " onChange="$('#btnSrh').click();">
		          					<c:forEach items="${companyList}" var="item" varStatus="status">
		                      			<option value="${item.company_seq }" ${item.company_seq =='13' ? 'selected' : ''}>${item.company_name}</option>
		                			</c:forEach>
		            			</select>
							</div>
						</c:if>            
						<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 1 and sessionScope.ADMIN_SESSION.company_seq ne '13'}">
							<div class="select-inner">
		          				<select id="company_seq" name="company_seq" class="styled02 " onChange="$('#btnSrh').click();">
		          					<c:forEach items="${companyList}" var="item" varStatus="status">
	                					<option value="${item.company_seq }">${item.company_name }</option>
		                			</c:forEach>
		                			<c:forEach items="${counselorList }" var="item" varStatus="status">
		                				<option data-counselor="C" value="${item.admin_id }">${item.admin_name }</option>
		                			</c:forEach>
		            			</select>
							</div>
						</c:if>
           			</td>
	           		<td> 
          				<div class="btn-module mgtS mgbS ">
	          	 			<div class="leftGroup mglS">
	          	 				<a href="#none" id="btnRel" class="btnStyle05">새로고침</a>
	          	 			</div>
          				</div>
          			</td>
        		</tr>
      		</table>
      	</div>
    </article>
    
	<div style="width:100%;height:650px; overflow: auto;">
		<div id="chartContainer" style="width: 100%; height: 600px;text-align:center;  "></div>
		<div class="overlay" style="display:inline-block; border: solid 1px #efefef; background-color:#fff; padding: 10px">
		    <div style="display:inline-block; height: 24px; float:left;margin-left:10px;"><span style="background-color:#f42958; display:inline-block; width: 40px; height: 20px"></span> <span id="legend1">300</span>만원 이상</div>
		    <div style="display:inline-block; height: 24px; float:left;margin-left:10px;"><span style="background-color:#d529f4; display:inline-block; width: 40px; height: 20px"></span> <span id="legend2">200</span>만원 이상</div>
		    <div style="display:inline-block; height: 24px; float:left;margin-left:10px"><span style="background-color:#297af4; display:inline-block; width: 40px; height: 20px"></span> <span id="legend3">100</span>만원 이상</div>
		    <div style="display:inline-block; height: 24px; float:left;margin-left:10px"><span style="background-color:#0dcc8f; display:inline-block; width: 40px; height: 20px"></span> <span id="legend4">50</span>만원 이상</div>
		    <div style="display:inline-block; height: 24px; float:left;margin-left:10px"><span style="background-color:#f4c829; display:inline-block; width: 40px; height: 20px"></span> <span id="legend5">30</span>만원 이상</div>
		    <div style="display:inline-block; height: 24px; float:left;margin-left:10px"><span style="background-color:#ece083; display:inline-block; width: 40px; height: 20px"></span> <span id="legend6">30</span>만원 미만</div>
		</div>
	</div>

    <table id="list"></table>