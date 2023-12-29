<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="/resources/web/js/jquery.mtz.monthpicker.js"></script>

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

var authLevel = "${adminSession.auth_level}";
var companySeq = "${adminSession.company_seq}";
$(function() {
	var lastsel;            //편집 sel

 	var status_opts = "0:발행예정;1:발행완료;2:발행취소";
 	
 	var date = new Date();
	var cur_year = date.getFullYear();
	var cur_month = date.getMonth()+1;
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

 	//지점 목록 불러오기
 	var _url = "<c:url value='/admin/main/getCompanyList' />";
	commonAjax(_url, null, true, function(data) {
		if(data.code == "0000") {
			drawSelectBox(data.companyList);
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
	
	$("#btnSrh").on("click", function() {
		fn_taxSearch();
	});
	
	//excel버튼 이벤트
	$("#btnExcel").on("click", function(){
		var selarr = fn_selRows();
		if( selarr.length < 1 ){
			return;
		}
		
		var frmTxt = "<form id='taxExcelFrm' name='taxExcelFrm' action='/admin/taxbillListExcel'><input type='hidden' id='erer' name='sel_seq_arr' value='"+ selarr +"' /></form>";
		$("body").append(frmTxt);
		$("#taxExcelFrm").submit();
        
		$("#taxExcelFrm").remove();
	});
	//발행완료버튼 이벤트
	$("#btnClear").on("click", function(){
		var selarr = fn_selRows();
		if( selarr.length < 1 ){
			return;
		}
		
		var _data = {
				sel_seq_arr : selarr,
				tax_status : "1"
		}
		$.ajaxSettings.traditional = true;
		commonAjax("/admin/setTaxbillInfo", _data, true, function(data) {
			if(data.code == "0000") {
				$("#list").trigger("reloadGrid");
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
	});
	//발행취소버튼 이벤트
	$("#btnCancle").on("click", function(){
		var selarr = fn_selRows();
		if( selarr.length < 1 ){
			return;
		}
		
		var _data = {
				sel_seq_arr : selarr,
				tax_status : "2"
		}
		$.ajaxSettings.traditional = true;
		commonAjax("/admin/setTaxbillInfo", _data, true, function(data) {
			if(data.code == "0000") {
				$("#list").trigger("reloadGrid");
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
	});
	
	var _curDate = new Date();
 	var oneMonthAgoDate = new Date(_curDate.setMonth(_curDate.getMonth() - 1));
 	var oneMonthAgoYear = oneMonthAgoDate.getFullYear();
 	var oneMonthAgoMonth = oneMonthAgoDate.getMonth()+1;
 	
 	if(oneMonthAgoMonth < 10) {
 		oneMonthAgoMonth = '0' + oneMonthAgoMonth;
 	}
 	
 	var lastDate = new Date(oneMonthAgoYear, oneMonthAgoMonth, 0).getDate();
 	
	$("#startDate").monthpicker(options);
	$("#startDate").val(oneMonthAgoYear+"-"+oneMonthAgoMonth);
	
	var srh_date = $("#startDate").val().replace("-", "") + lastDate;
	
	// jqGrid 생성
	$("#list").jqGrid({
  		url: "/admin/getTaxbillList",
		datatype: "json",                               // 로컬그리드이용
		mtype: "POST",
		postData: {
			write_date: srh_date,
			<c:if test="${adminSession.auth_level eq 1}">
			company_seq : companySeq,
			</c:if>
			page: 1
		},

		sortname: "b.company_seq, b.employer_seq",
		sortorder: "asc",
		rowList: [25, 50, 100],                         // 한페이지에 몇개씩 보여 줄건지?
		//height: jqHeight,                               // 그리드 높이
		width: jqWidth,
		scrollerbar: true,               
      	//autowidth: true,

		rowNum: 100,
		pager: '#paging',                            // 네비게이션 도구를 보여줄 div요소
		viewrecords: true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무

		multiselect: true,
		multiboxonly: true,
		caption: "면세계산서",                 // 그리드타이틀

		rownumbers: true,                                 // 그리드 번호 표시
		rownumWidth: 40,                                   // 번호 표시 너비 (px)

        cellEdit: true ,
		cellsubmit: "remote" ,
        cellurl: "/admin/setTaxbillInfo",              // 추가, 수정, 삭제 url

        jsonReader: {
			id: "tax_seq",
			repeatitems: false
		},
		sortable: true,

        // 컬럼명
		colNames: ['계산서순번','지점 순번', '구인처 순번','작성일자','등록번호','상호','성명','주소','업태','업종','이메일','스캔','등록번호','상호','성명','주소','업태','업종','이메일','품목','영수/청구','공급가액', '메모', '상태','등록일시','등록자'],

        // 컬럼별 속성
		colModel: [
				{name: 'tax_seq'    , index: 'tax_seq'    , key: true, hidden: authLevel == 0 ? false : true, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'company_seq' , index: 'b.company_seq' , hidden: authLevel == 0 ? false : true, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'employer_seq' , index: 'b.employer_seq' , hidden: authLevel == 0 ? false : true, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'write_date' , index: 'write_date', searchoptions: {sopt: ['cn','eq','nc']} },
				{name: 'company_num', index: 'company_num', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'business_name', index: 'business_name', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'company_owner', index: 'company_owner', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'company_addr', index: 'company_addr', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'company_business', index: 'company_business', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'company_sector', index: 'company_sector', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'company_email', index: 'company_email', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'employer_corp_scan_yn', index: 'employer_corp_scan_yn', sortable: false, search: false, width: "50px", formatter: formatCorpScan},
				{name: 'employer_num', index: 'employer_num', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'employer_name', index: 'employer_name', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'employer_owner', index: 'employer_owner', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'employer_addr', index: 'employer_addr', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'employer_business', index: 'employer_business', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'employer_sector', index: 'employer_sector', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'employer_email', index: 'employer_email', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'subject', index: 'subject', search: false},
				{name: 'claim', index: 'claim', search: false, width: "70px"},
				{name: 'supply_price', index: 'supply_price', search: false, formatter: 'integer', formatoption: {thousandsSeparator: ",", decimalPlace: 0}},
				{name: 'tax_memo', index: 'tax_memo', search: false, editable: true},
				{name: 'tax_status', index: 'tax_status', search: false, align: "center"
					, editable: true, edittype: "select"
					, editoptions: {value: status_opts}
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
			        	// rowObject 변수로 그리드 데이터에 접근
			            if ( rowObject.tax_status  == '2' ) {
			            	return 'style="color: #FFFFFF; background: #000000"';
						}
					}
					, formatter: "select"},
				{name: 'reg_date', index: 'b.reg_date', searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'reg_admin', index: 'b.reg_admin', searchoptions: {sopt: ['cn','eq','nc']}},
				
             
				
		],
     	// row 를 선택 했을때 편집 할 수 있도록 한다.
		onSelectRow: function(id) {
        	if ( id && id !== lastsel ) {
            	$("#list").jqGrid('restoreRow', lastsel);

                $("#list").jqGrid('editRow', id, {
                	keys: true,
					oneditfunc: function() {
					},
					successfunc: function() {
						lastsel = -1;
						return true;
					},
				});

				lastsel = id;
			}
		},
    	// cell을 클릭 시
    	onCellSelect: function(rowid, index, contents, event) {
			//row data 전체 가져 오기
			var list = $("#list").jqGrid('getRowData', rowid);

			lastsel = rowid;
		},
		// submit 전
		beforeSubmitCell: function(rowid, cellname, value) {
			return {tax_seq: rowid};
		},
		// Grid 로드 완료 후
		loadComplete: function(data) {
			var sum = $("#list").jqGrid('getCol','supply_price', false, 'sum');
			
			//합계
			$("#totalSupplyPrice").html(numberWithCommas( sum ));
                    
			isGridLoad = true;
		},

		loadBeforeSend: function(xhr) {
			isGridLoad = false;
			optHTML = "";
			closeOpt();

			xhr.setRequestHeader("AJAX", true);
		},
       	loadError: function(xhr, status, error) {
			if ( xhr.status == "901" ) {
				location.href = "/admin/login";
			}
		},
		beforeSelectRow: selectRowid

});


	$("#list").jqGrid('navGrid', "#paging", {edit: false, add: false, del: false, search: false, refresh: false, position: 'left'});

	$("#list").jqGrid('filterToolbar', {searchOperators : true});
	
	$("#list").jqGrid('setGroupHeaders', {
		useColSpanStyle: true,
		groupHeaders:[
			{ startColumnName: 'company_num', numberOfColumns: 7, 	titleText: '<div style="margin:0; padding:0;width:100%;border-spacing:0px;background-color:#FFD5E3">공급자</div>' }
			,{ startColumnName: 'employer_corp_scan_yn', 	numberOfColumns: 8, 	titleText: '<div style="margin:0; padding:0;width:100%;border-spacing:0px;background-color:#CAFDCF">공급받는자</div>'}
		]
	});
	
	//석달전, 두달전, 한달전 라디오버튼
	$(".label-radio").on("click",function(){
		$(".label-radio").removeClass("on");
		
		$(this).addClass("on");
	})
});

function formatCorpScan(cellvalue, options, rowObject) {
	var str = "";

	if ( rowObject.employer_corp_scan_yn > 0 ) {
		str += "<div class=\"bt_wrap\">";
		str += "<div class=\"bt\"><a style=\"background:orange;\" href=\"JavaScript:popupImage('"+ rowObject.employer_seq +"'); \"> 사 </a></div>";
		str += "</div>";
	}
	
	return str;
}
function fn_taxSearch(){
	var lastDate = new Date($("#startDate").val().split("-")[0], $("#startDate").val().split("-")[1], 0).getDate();
	
	var _writeDate = $("#startDate").val().replace("-","") + lastDate;
	var _companySeq = $("#srh_company_seq option:selected").val();

	//슈퍼관리자가 아니면 본인 소속지점만 보이게 한다.
	if( authLevel > 0 ){
		_companySeq = companySeq;
	}
	
	$("#list").setGridParam({
        postData: {
		    write_date : _writeDate
		    , company_seq : _companySeq
	    }
    }).trigger("reloadGrid");
}

function drawSelectBox(list){
	var text = "";
	for(var idx=0; idx<list.length; idx++){
		text += '<option value='+ list[idx].company_seq + '>' + list[idx].company_name +'</option>';
	}
	
	$("#srh_company_seq").append(text);
}

function fn_selRows(){
	var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
	
	if( recs.length < 1 ){
		toastFail("면세계산서 내역을 선택해주세요.");
	}
	
	return recs;
}

function popupImage(serviceSeq) {
	var popupX = (document.body.offsetWidth / 2) - (200 / 2);
	//&nbsp;만들 팝업창 좌우 크기의 1/2 만큼 보정값으로 빼주었음

	var popupY= (document.body.offsetHeight / 2) - (300 / 2);
	//&nbsp;만들 팝업창 상하 크기의 1/2 만큼 보정값으로 빼주었음
	
	var param = "service_type=EMPLOYER&service_code=CORP&service_seq="+ serviceSeq;
	window.open("/admin/popup/imageCrop?"+ param, 'image', 'width=1100, height=800, toolbar=no, menubar=no, scrollbars=no, resizable=yes, left='+ popupX + ', top='+ popupY);
}
</script>

<article>
	<div class="inputUI_simple">
		<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
			<tr>
				<th scope="row" style="width:100px;">작성일자</th>
				<td>
           			<p class="floatL">
             			<input type="text" id="startDate" name="startDate" class="mtz-monthpicker-widgetcontainer">
            		</p>
            		<div class="inputUI_simple">
	            		<span class="contGp btnCalendar">
	              			<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setMonthPicker(3); fn_taxSearch();"><label for="day_type_1" class="label-radio day_type">석달전</label>
	              			<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setMonthPicker(2); fn_taxSearch();"><label for="day_type_2" class="label-radio day_type">두달전</label>
	              			<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setMonthPicker(1); fn_taxSearch();" checked="checked"><label for="day_type_3" class="label-radio on day_type">한달전</label>
	              			
	              			<div class="btn-module floatL mglS">
	              				<a href="#none" id="btnSrh" class="search">검색</a>
	            			</div>
		         		</span>
            		</div>
          		</td>
			</tr>
		</table>
  	</div>
</article>

<div class="btn-module mgtS mgbS">
	<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
		<div class="select-inner" style="margin-right: 30px;">
	 		<select id="srh_company_seq" name="srh_company_seq" class="styled02 floatL wid138" onChange="fn_taxSearch();">
	   			<option value="" selected="selected">전체</option>
	 		</select>
		</div>
	</c:if>
 	<div class="leftGroup">
  		<a href="#none" id="btnExcel" class="btnStyle05">EXCEL</a>
    	<a href="#none" id="btnClear" class="btnStyle01">발행완료</a>
    	<a href="#none" id="btnCancle" class="btnStyle01">발행취소</a>
  	</div>
  	
  	<div class="leftGroup">
  		<div style="font-size: 14px; margin-left:300px; padding-top: 12px;">합계: <span id="totalSupplyPrice" style="color: red;"> 0</span></div>
  	</div>
</div>
<form id="taxExcelFrm" name="taxExcelFrm" method="post"></form>
<table id="list"></table>
<div id="paging"></div>
</form>

