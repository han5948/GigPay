<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
/* 
//윈도우 사이즈가 변할때 높이 넓이
$(window).resize(function() {
    var reWidth = $(window).width() -optWidth;
    if(reWidth < baseWidth) reWidth = baseWidth;
    
    var reHeight = $(window).height() -optHeight;
    if(reHeight < baseHeight) reHeight = baseHeight;
    
    $("#list").setGridWidth(reWidth) ;
    $("#list").setGridHeight(reHeight);
});

 */
	var companySeq  = '${ sessionScope.ADMIN_SESSION.company_seq}';
	var authLevel = ${sessionScope.ADMIN_SESSION.auth_level };
 
	isSelect = false;
	var lastElement = '';
	var optIncome = "";                   // 노임수령       code_type = 300
	var optPay = "";                   // 노임지급       code_type = 200
	var mCode = new Map();
	
	$(function() {
		var lastsel;            //편집 sel
		
  		// 사용유무 - 0:미사용 1:사용
  		var use_opts = "1:사용;0:미사용";
  		var status_opts = "0:미발행;1:발행";
		var limit_opts = "0:제한없음-구인처별;1:제한없음-현장별;2:구인처별;3:현장별";
		var payment_opts = "0:직불-직접지급;1:직불-간편송금;2:기성-간편결제;3:기성-대불";
		var wage_opts = "0:없음;1:1주 수요일;2:2주 수요일;3:익월 5일;4:익월 10일;5:익월 15일;6:익월 20일;7:익월 25일;8:익월 말일";
		var optInsurance = "";
		var managerType_opts = "O:현장매니저;E:본사매니저";
		
		var laborContract = '${laborContract }';
		var receiveContract = '${receiveContract }';
		
		$.ajax({
			type	: "POST",
			url		: "/admin/getCommonCodeList",
			data	: {"use_yn": "1", "code_type": "200, 300"},
			dataType: 'json',
			success	: function(data) {
				var _style = "";
			   		 
			    if ( data != null && data.length > 0 ) {
			    	for ( var i = 0; i < data.length; i++ ) {
			            if ( data[i].code_type == 300 ) {   // 노임수령
			            	if(data[i].code_group == 0){
			            	//if(data[i].code_value != '300001' && data[i].code_value != '300002' && data[i].code_value != '300004' && data[i].code_value != '300005' && data[i].code_value != '300011')
			            		optIncome += "<div class=\"bt\" style=\"width: 50px;\"><a href=\"JavaScript:chkCodeUpdate('income_layer', '<<EMPLOYER_SEQ>>', 'employer_income_code', '"+  data[i].code_value +"', 'employer_income_name', '"+  data[i].code_name + "', '" +data[i].code_group+"', '" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
			            	}
			            } else if ( data[i].code_type == 200 ) {   // 노임지급
			            	if(data[i].code_value != '200001' && data[i].code_value != '200002' && data[i].code_value != '200003' && data[i].code_value != '200004')
			            		optPay    += "<div class=\"bt\"  style=\"width: 50px;\"><a href=\"JavaScript:chkCodeUpdate('pay_layer', '<<EMPLOYER_SEQ>>', 'employer_pay_code', '"+  data[i].code_value +"', 'employer_pay_name', '"+  data[i].code_name +"', '" +data[i].code_group+"', '" +data[i].code_type+"');\" style=\"background:#"+ data[i].code_bgcolor +"; color:#"+ data[i].code_color +";\"> "+ data[i].code_name +" </a></div>";
						}
			        	
			            oCode = new Object();
			
			            oCode.bgcolor	= data[i].code_bgcolor;
			            oCode.color   	= data[i].code_color;
			            oCode.name   = data[i].code_name;
			
			            mCode.put(data[i].code_value, oCode);
					}
				}
			},
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				if ( data.status == "901" ) {
			    	location.href = "/admin/login";
			    }
			}
		});
		
  		// jqGrid 생성
  		$("#list").jqGrid({
			url: "/admin/getEmployerList",
//             editurl: "/test/updateTest",                 // 추가, 수정, 삭제 url
			datatype: "json",                               // 로컬그리드이용
			mtype: "POST",
			postData: {
//          start_reg_date: getDateString(new Date(now.getFullYear(), now.getMonth() - 1, now.getDate())),
//            end_reg_date: toDay,
				srh_use_yn: 1,
				page: 1
			},
//            datatype: "local",
			sortname: 'reg_date desc, employer_name',
			sortorder: "desc",
			rowList: [25, 50, 100],                         // 한페이지에 몇개씩 보여 줄건지?
			height: "100%",                               // 그리드 높이
			scrollerbar: true,       
			width: "auto",
	
			rowNum: 50,
			pager: '#paging',                            // 네비게이션 도구를 보여줄 div요소
			viewrecords: true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무
	
			multiselect: true,
			multiboxonly: true,
			caption: "구인처 목록",                          // 그리드타이틀
	
			rownumbers: true,                                 // 그리드 번호 표시
			rownumWidth: 40,                                   // 번호 표시 너비 (px)
	
			cellEdit: true ,
			cellsubmit: "remote" ,
			cellurl: "/admin/setEmployerInfo",              // 추가, 수정, 삭제 url
	
			navigator : true, 
	
			jsonReader: {
				id: "employer_seq",
				repeatitems: false
			},
	
			// 컬럼명
			colNames: ['구인처 순번', '지점 순번', '지점', '최초등록매니져순번',  '출역', '출역일수제한', '총출역','현장수',  '대장','회사명', '등록매니져','사업자등록번호','비밀번호', '대표자', '업태', '업종', '면세요청', '사회보험', '결재방법', '기성일', '노임지급 코드', '노임지급', '소개비지급 코드', '소개비지급', '서명대상자 구분', '서명 대상 본사매니저 순번', '서명 대상 본사매니저 명', '대리수령 양식', '대리수령 양식명', '근로계약 양식', '근로계약 양식명', '담당자','전화', '전화번호', '팩스', '이메일', '주소','스캔','사업자등록증','기타','폰번호 1', '폰번호 2', '폰번호 3', '폰번호 4',  '특징', '메모', '상세 메모', '마지막출역일자','상태', '등록일시', '등록자', '소유자', '고용보험', '산재보험', '국민연금', '건강보험'],
	
			// 컬럼별 속성
			colModel: [
				{name: 'employer_seq', index: 'employer_seq', key: true, hidden: authLevel ==0 ? false : true },
				{name: 'company_seq', index: 'company_seq', hidden: true },
				<c:choose>
	  				<c:when test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
                		{name: 'company_name', index: 'company_name', width: 80, align: "left", sortable: true
                			, editable: true
                			, edittype: "text"
                			, editoptions: {
								size: 30,
								dataInit: function (e, cm) {
									$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택

									$(e).autocomplete({
//                                             source: "/admin/getCompanyNameList?srh_use_yn=1",
										source: function (request, response) {
											$.ajax({
												url: "/admin/getCompanyNameList", type: "POST", dataType: "json",
												data: { term: request.term, srh_use_yn: 1 },
												success: function (data) {
													response($.map(data, function (item) {
//                  	                        return { label: item.code + ":" + item.name, value: item.code, id: item.id }
														return item;
													}))
												},
												beforeSend: function(xhr) {
													xhr.setRequestHeader("AJAX", true);
												},
												error: function(e) {
													if ( data.status == "901" ) {
														location.href = "/admin/login";
													}else {
														alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
													}
												}
											})
										},
										minLength: 1,
										focus: function (event, ui) {
											return false;
										},
										select: function (event, ui) {
											lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
											$(e).val(ui.item.label);
											$("#list").jqGrid('setCell', lastsel, 'company_seq', ui.item.code, '', true);  //다른 셀 바꾸기
										
											isSelect = true;
                                                     	
											//Enter가 아니면
											if( event.keyCode != "13" ){
												var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
												$("#list").jqGrid("saveCell", iRow, cm.iCol);
											}
                                                     	
											return false;
										}
									});
								}
							},
							editrules: {edithidden: true, required: false}, searchoptions: {sopt: ['cn','eq','nc']} },
  					</c:when>
  					<c:otherwise>
						{name: 'company_name', index: 'company_name', searchoptions: {sopt: ['cn','eq','nc']} },
  					</c:otherwise>
				</c:choose>
				{name: 'manager_seq', index: 'manager_seq', width: 100, editable: false, hidden: authLevel ==0 ? false : true },
				{name: 'out_count', index: 'out_count', align: "center", width: 50, editable: false, search: false, sortable: true },
				{name: 'limit_count', index: 'limit_count', align: "center", width: 60, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: limit_opts}, formatter: "select" },
				{name: 'total_out_count', index: 'total_out_count', align: "center", width: 50, editable: false, search: false, sortable: true },
				{name: 'work_count', index: 'work_count', align: "center", width: 50, editable: false, search: false, sortable: true },
				{name: 'employer_ilbo', index: 'employer_ilbo', align: "left", width: 40, search: false, sortable: false, formatter: formatEmployerIlbo },
				{name: 'employer_name', index: 'employer_name', align: "left", editable: true, sortable: true, editrules: {required: false}, searchoptions: {sopt: ['cn','eq','nc']} },
				{name: 'manager_name', index: 'm.manager_name', width: 80, align: "left", sortable: true
					, editable: true
					, edittype: "text"
					, editoptions: {
						size: 30
						, dataInit: function (e, cm) {
							$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택

							$(e).autocomplete({
								source: function (request, response) {
									$.ajax({
										url: "/admin/getManagerNameList", type: "POST", dataType: "json",
										data: { 
											term: request.term
											, use_yn: 1
											, manager_type:'O' 
										},
										success: function (data) {
											response($.map(data, function (item) {
												return item;
											}))
										},
										beforeSend: function(xhr) {
											xhr.setRequestHeader("AJAX", true);
										},
										error: function(e) {
											if ( data.status == "901" ) {
												location.href = "/admin/login";
											} else {
												alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
											}
										}
									})
								}
								, minLength: 1
								, focus: function (event, ui) {
									return false;
								}
								, select: function (event, ui) {
									lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
									$(e).val(ui.item.text);

									$("#list").jqGrid('setCell', lastsel, 'manager_seq', ui.item.code, '', true);  //다른 셀 바꾸기
									isSelect = true;
                                                 
									//Enter가 아니면
									if( event.keyCode != "13" ){
										var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
										$("#list").jqGrid("saveCell", iRow, cm.iCol);
									}
                                                 
									return false;
								}
							});
						},
						dataEvents:[
								//	{type:'change', fn:function(e){ 필요한처리할 함수명();}},   //onchange Event 
							{type:'keydown', fn:function(e) {                   //keydown Event
								if(e.keyCode == 9 || e.keyCode == 13) {      //Enter Key or Tab Key
									var value =  e.target.value;
				                	 			
									if ( !isSelect && value != '?' ) {
										var p = $("#list").jqGrid("getGridParam");
										var iCol = p.iColByName["manager_name"];
										var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
					
				                    	$("#list").jqGrid('restoreCell', iRow, iCol);
				                    	alert("담당자를 선택하여 엔터를 쳐야 됩니다.");
									}
								}
							}} //end keydown}
						]    //dataEvents 종료
					}
             		, editrules: {custom: true, custom_func: validManagerName}
             		, searchoptions: {sopt: ['cn','eq','nc']}
				},
           		{name: 'employer_num', index: 'employer_num', align: "left", sortable: true, searchoptions: {sopt: ['cn','eq','nc']} , editable: true
					, formatter :employerNumFomat, editrules: {custom: true, custom_func: employerNumCheck }
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
                		return 'style="background-color: #efeeec;"';
					}
        		},
            	{name: 'employer_pass', index: 'employer_pass', hidden: true, align: "left", editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
						return 'style="background-color: #f7f4e4;"';
                 	}
            	},
				{name: 'employer_owner', index: 'employer_owner', align: "left", width: 80, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'employer_business', index: 'employer_business', align: "left", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'employer_sector', index: 'employer_sector', align: "left", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
            	{name: 'is_tax', index: 'is_tax', search: false, width: 70, align: "center", sortable: true
				//, editable: (authLevel == '0' || (authLevel == '1' && companySeq ==)) ? true : false
					, editable: function(obj) {
						var rowCompanySeq = $("#list").jqGrid('getRowData', obj.rowid).company_seq;
						
						if(authLevel == '0' || (authLevel == '1' && companySeq == rowCompanySeq)) {
							return true;
						}else {
							return false;
						}
					}
					, edittype: "select"
					, editoptions : {value: status_opts}
					, formatter: 'select'
				},
				{name: 'insurance', index: 'insurance', align: "left", width: 80, formatter: formatInsurance, sortable: false },
	        	{name: 'payment', index: 'payment', align: "center", width: 60, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: payment_opts}, formatter: "select"
	        		,editable: function(obj) {
						var paymentAttr = $("#list").jqGrid('getRowData', obj.rowid).payment;
					
						if(authLevel == '0') {
							return true;						
						}else {
							return false;
						}
					}	
	        	},
				{name: 'wage', index: 'wage', align: "center", width: 60, search: false, sortable: true, edittype: "select", editoptions: {value: wage_opts}, formatter: "select"
					,editable: function(obj) {
						var paymentAttr = $("#list").jqGrid('getRowData', obj.rowid).payment;
					
						if(paymentAttr == '3' && authLevel == '0') {
							return true;						
						}else {
							return false;
						}
					} 	
				},
				{name: 'employer_income_code', index: 'employer_income_code', hidden: true },
				{name: 'employer_income_name', index: 'employer_income_name', align: "center", sortable: true, formatter: formatIncomeCode, searchoptions: {sopt: ['cn', 'eq', 'nc']} },
				{name: 'employer_pay_code', index: 'employer_pay_code', hidden: true },
				{name: 'employer_pay_name', index: 'employer_pay_name', align: "center", sortable: true, formatter: formatPayCode, searchoptions: {sopt: ['cn', 'eq', 'nc']} },
				{name: 'sign_manager_type', index: 'sign_manager_type', align: "center", width: 60, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: managerType_opts}, formatter: "select"},
                {name: 'sign_manager_seq', index: 'sign_manager_seq', hidden: true },
                {name: 'sign_manager_name', index: 'sign_manager_name', align: "left", sortable: true, width: 100 
					, editable: true 
	                , edittype: "text"
					, editoptions: {
	                	size: 30,
						dataInit: function (e, cm) {
	                    	lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
	                     			
	                        $(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
	                        $(e).autocomplete({
	                        	source: function (request, response) {
									$.ajax({
	                                	url: "/admin/getManagerNameList", type: "POST", dataType: "json",
	                                    data: { 
	                                    	term: request.term
	                                        , use_yn: 1
	                                        , manager_type: "E"
	                                        , employer_seq: $("#list").jqGrid('getRowData', lastsel).employer_seq
										},
	                                    success: function (data) {
	                                    	response($.map(data, function (item) {
	                                        	return item;
											}))
	                                    },
	                                    beforeSend: function(xhr) {
											xhr.setRequestHeader("AJAX", true);
										},
										error: function(e) {
											if ( data.status == "901" ) {
												location.href = "/admin/login";
											} else {
												alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
											}
										}
									})
								},
								minLength: 1,
								focus: function (event, ui) {
									return false;
								},
								select: function (event, ui) {
									$(e).val(ui.item.manager_name);
	                                    	  		
// 									$("#list").jqGrid('setCell', lastsel, 'manager_seq'	, ui.item.manager_seq, '', true);  
// 									$("#list").jqGrid('setCell', lastsel, 'manager_phone', ui.item.manager_phone, '', true);  
// 									$("#list").jqGrid('setCell', lastsel, 'manager_name_hidden', ui.item.manager_name, '', true);  
									$("#list").jqGrid("setCell", lastsel, 'sign_manager_seq', ui.item.manager_seq, '', true);
	                                                
									isSelect = true;
									
									//Enter가 아니면
					                if( event.keyCode != "13" ){
					                	var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
					                	$("#list").jqGrid("saveCell", iRow, cm.iCol);
					                }
									
									return false;
								}
							});	
						}
	            	 	, dataEvents:[
	            	 			//	{type:'change', fn:function(e){ 필요한처리할 함수명();}},   //onchange Event 
	            	 		{
	                	 		type: 'keydown', fn:function(e){                   //keydown Event
									if(e.keyCode == 9 || e.keyCode == 13){      //Enter Key or Tab Key
										var value =  e.target.value;
			                	 			
			                	 		if ( !isSelect && value != '?' ) {
											var p = $("#list").jqGrid("getGridParam");
			                        		var iCol = p.iColByName["manager_name"];
			                        		var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
			
			                        		$("#list").jqGrid('restoreCell', iRow, iCol);
			                        		alert("담당자를 선택하여 엔터를 쳐야 됩니다.");
										}
									}
								} //end keydown
							}
	            	 	]    //dataEvents 종료
					}
	                , editrules: {custom: true, custom_func: validManagerName}
	                , searchoptions: {sopt: ['cn','eq','nc']}
	                , formatter: formatDefaultName
	                , cellattr: function(rowId, tv, rowObject, cm, rdata) {
		            	return 'style="background-color: #f7f4e4;"';
					}
	          	},
				{name: 'receive_contract_seq', index: 'receive_contract_seq', align: "center", width: 60, search: false, sortable: true, edittype: "select"
					, editoptions: {
						value: receiveContract
						, dataEvents:[
							{type:'keydown', fn:function(e) {                   //keydown Event
								if(e.keyCode == 9 || e.keyCode == 13) {
									alert("양식이 변경되었습니다. 필요시 현장관리, 일일대장도 양식을 변경해주세요.");
									
									lastElement = e.currentTarget;
								}else {
									var p = $("#list").jqGrid("getGridParam");
	                        		var iCol = p.iColByName["receive_contract_name"];
	                        		var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
	
	                        		$("#list").jqGrid('restoreCell', iRow, iCol);
	                        		
									alert("항목을 선택 후 엔터를 쳐야 됩니다.");
								}
							}} //end keydown}
						]    //dataEvents 종료
					}, formatter: fn_receiveName, editable: true },
				{name: 'receive_contract_name', index: 'receive_contract_name', hidden: true},
				{name: 'labor_contract_seq', index: 'labor_contract_seq', align: "center", width: 60, search: false, sortable: true, edittype: "select"
					, editoptions: {
						value: laborContract 
						, dataEvents:[
							{type:'keydown', fn:function(e) {                   //keydown Event
								if(e.keyCode == 9 || e.keyCode == 13) {
									alert("양식이 변경되었습니다. 필요시 현장관리, 일일대장도 양식을 변경해주세요.");
									
									lastElement = e.currentTarget;
								}else {
									var p = $("#list").jqGrid("getGridParam");
	                        		var iCol = p.iColByName["labor_contract_name"];
	                        		var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
	
	                        		$("#list").jqGrid('restoreCell', iRow, iCol);
	                        		
									alert("항목을 선택 후 엔터를 쳐야 됩니다.");
								}
							}} //end keydown}
						]    //dataEvents 종료	
					}, formatter: fn_laborName, editable: true },
				{name: 'labor_contract_name', index: 'labor_contract_name', hidden: true},
	        	{name: 'tax_name', index: 'tax_name', align: "left", width: 80, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
	        	{name: 'tax_phone', index: 'tax_phone', align:"left", width: 100, editable: true, sortable: true, formatter: formatPhoneStar, searchoptions: {sopt: ['cn','eq','nc']}},
	              
            	{name: 'employer_tel', index: 'employer_tel', align: "left", width: 100, editable: true, sortable: true, formatter: formatPhoneStar, searchoptions: {sopt: ['cn','eq','nc']}},
            	{name: 'employer_fax', index: 'employer_fax', align: "left", width: 100, editable: true, sortable: true, formatter: formatPhoneStar, searchoptions: {sopt: ['cn','eq','nc']}},
            	{name: 'employer_email', index: 'employer_email', align: "left", width: 140, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
            	{name: 'employer_addr', index: 'employer_addr', align: "left", editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                 
            	{name: 'employer_scan_file', index: 'employer_scan_file', align: "left", width: 120, search: false, sortable: false, formatter: formatScan},
            	{name: 'employer_corp_scan_yn', index: 'employer_corp_scan_yn', hidden: true},
            	{name: 'employer_etc_scan_yn', index: 'employer_etc_scan_yn', hidden: true},
                 	
            	{name: 'employer_phone1', index: 'employer_phone1', align: "left", width: 100, editable: true, sortable: true, hidden: true
            		, editrules: {custom: true, custom_func: validEmployerPhone}
            		,formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}
				},
            	{name: 'employer_phone2', index: 'employer_phone2', align: "left", width: 100, editable: true, sortable: true, hidden: true
					, editrules: {custom: true, custom_func: validEmployerPhone}
					, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}
				},
            	{name: 'employer_phone3', index: 'employer_phone3', align: "left", width: 100, editable: true, sortable: true, hidden: true
					, editrules: {custom: true, custom_func: validEmployerPhone}
					, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}
				},
            	{name: 'employer_phone4', index: 'employer_phone4', align: "left", width: 100, editable: true, sortable: true,  hidden: true,
	            	editrules: {custom: true, custom_func: validEmployerPhone},
                	formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}
				},
            	{name: 'employer_feature', index: 'employer_feature', align: "left", editable: true, sortable: true, edittype: "textarea", searchoptions: {sopt: ['cn','eq','nc']}},
            	{name: 'employer_memo', index: 'employer_memo', align: "left", editable: true, sortable: true, edittype: "textarea", editoprions: {rows: "2", cols: "20"}, searchoptions: {sopt: ['cn','eq','nc']}},
            	{name: 'employer_detail_memo', index: 'employer_detail_memo', align: "left", editable: authLevel == 0 ? true : false, sortable: true, edittype: "textarea", editoprions: {rows: "2", cols: "20"}, searchoptions: {sopt: ['cn','eq','nc']}},
            	{name: 'ilbo_last_date', index: 'ilbo_last_date', align: "left", editable: true, sortable: false, edittype: "textarea", editoprions: {rows: "2", cols: "20"}, searchoptions: {sopt: ['cn','eq','nc']}},
            	{name: 'use_yn', index: 'use_yn', align: "center", width: 60, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: use_opts}, formatter: "select"
            		, cellattr: function(rowId, tv, rowObject, cm, rdata) {
                		// rowObject 변수로 그리드 데이터에 접근
                    	if ( rowObject.use_yn == '0' ) {
	                    	return 'style="color: red; text-align: center; background-color: #FFFFF0"';
						}
					}
				},
            	{name: 'reg_date', index: 'reg_date', align: "center", width: 150, search: false, editable: false, sortable: true, formatoptions: {newformat: "y/m/d H:i"}},
            	{name: 'reg_admin', index: 'e.reg_admin', align: "left", width: 100, searchoptions: {sopt: ['cn','eq','nc']}, editable: false, sortable: true},
            	{name: 'owner_id', index: 'e.owner_id', align: "left", width: 100, searchoptions: {sopt: ['cn','eq','nc']}, editable: false, sortable: true, hidden: true},
            	{name: 'employer_insurance', index: 'employer_insurance', hidden: true },
            	{name: 'accident_insurance', index: 'accident_insurance', hidden: true},
            	{name: 'national_pension', index: 'national_pension', hidden: true},
            	{name: 'health_insurance', index: 'health_insurance', hidden: true},
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
                        	
            	//cell name을  구해 온다.
            	var cm = $("#list").jqGrid("getGridParam", "colModel");
            	var cellName = cm[index].name;
            	
            	if(cellName == "employer_num"){
	            	if(!contents){
                        		 	
					}else{
						if ( ${sessionScope.ADMIN_SESSION.auth_level lt 3 ? false : true } ){
                    		$("#list").jqGrid('setCell', rowid, cellName, "", 'not-editable-cell'); // 특정 cell 수정 못하게
						}
					}
				}
			},
    		// submit 전
			beforeSubmitCell: function(rowid, cellname, value) {
				if ( cellname == "use_yn" && value == "0" ) {
					$("#"+rowid).hide();
				}
                        	
            	if(cellname == "manager_name"){
	            	return {
	            		cellname: cellname,
	                	employer_seq: rowid,
	                	manager_seq : $("#list").jqGrid('getRowData', rowid).manager_seq
					}
				}
            	
            	if(cellname == 'sign_manager_type' && value != 'E') {
					return {
						employer_seq : rowid
						//, labor_contract_seq : $("#list").jqGrid('getRowData', rowid).labor_contract_seq
						, sign_manager_type : value
						, sign_manager_seq : $("#list").jqGrid('getRowData', rowid).manager_seq
						, cellname : cellname
					}
				}else if(cellname == 'sign_manager_type' && value == 'E') {
					return {
						employer_seq : rowid
						//, labor_contract_seq : $("#list").jqGrid('getRowData', rowid).labor_contract_seq
						, sign_manager_type : 'E'
						, sign_manager_seq : 0
						, cellname : cellname
					}
				}
            	
            	if(cellname == 'sign_manager_name') {
					return {
						employer_seq : rowid
						//, labor_contract_seq : $("#list").jqGrid('getRowData', rowid).labor_contract_seq
						, sign_manager_type : $("#list").jqGrid('getRowData', rowid).sign_manager_type
						, sign_manager_seq : $("#list").jqGrid('getRowData', rowid).sign_manager_seq
						, cellname : cellname
					}
				}
            	
            	if(cellname == 'limit_count') {
            		closeOpt();
            		var params;
            		
            		if(value == '0' || value == '1') {
            			return {
        	            	cellname: cellname,
                        	employer_seq: rowid, 
                        	company_seq: $("#list").jqGrid('getRowData', rowid).company_seq,
                        	employer_corp_scan_yn	: $("#list").jqGrid('getRowData', rowid).employer_corp_scan_yn,
                        	employer_etc_scan_yn	: $("#list").jqGrid('getRowData', rowid).employer_etc_scan_yn,
                        	national_pension : 0,
                        	health_insurance : 0
        				};
            		}else if(value == '2' || value == '3') {
            			return {
        	            	cellname: cellname,
                        	employer_seq: rowid, 
                        	company_seq: $("#list").jqGrid('getRowData', rowid).company_seq,
                        	employer_corp_scan_yn	: $("#list").jqGrid('getRowData', rowid).employer_corp_scan_yn,
                        	employer_etc_scan_yn	: $("#list").jqGrid('getRowData', rowid).employer_etc_scan_yn,
                        	national_pension : 1,
                        	health_insurance : 1
        				};
            		}
            	}
	
            	return {
	            	cellname: cellname,
                	employer_seq: rowid, 
                	company_seq: $("#list").jqGrid('getRowData', rowid).company_seq,
                	employer_corp_scan_yn	: $("#list").jqGrid('getRowData', rowid).employer_corp_scan_yn,
                	employer_etc_scan_yn	: $("#list").jqGrid('getRowData', rowid).employer_etc_scan_yn,
				};
			},
        	// Page start
			onPaging: function (pgButton) {
				isGridLoad = false;
				optHTML = "";
				closeOpt();
			},
			afterInsertRow: function(rowId, rowdata, rowelem) {	//그리드를 그릴때 호출 되는 구나....
				fn_setEditable(rowId, rowdata.company_seq);
			},
        	// Grid 로드 완료 후
			loadComplete: function(data) {
				//총 개수 표시
				$("#totalRecords").html(numberWithCommas( $("#list").getGridParam("records") ));
				if( $("#list").getGridParam("records") < 1 ){
					$("#export").click( function(){
						alert("출력할 결과물이 없습니다");
					});
				}else{
					$("#export").click( function(){
						document.defaultFrm.action = "/admin/employerListExcel";
						document.defaultFrm.submit();
					});
				}
                        	
				isGridLoad = true;
			},
			loadBeforeSend: function(xhr) {
				isGridLoad = false;
				optHTML = "";
	//                        closeOpt();
	
				xhr.setRequestHeader("AJAX", true);
			},
			loadError: function(xhr, status, error) {
				if ( xhr.status == "901" ) {
					location.href = "/admin/login";
				}
			},
			afterSubmitCell : function(res, rowId, cellName, value){
				if(cellName == 'limit_count') {
					if(value == '0' || value == '1') {
						$("#list").jqGrid('setCell', lastsel, 'health_insurance', 0, '', true);
						$("#list").jqGrid('setCell', lastsel, 'national_pension', 0, '', true);
					}else if(value == '2' || value == '3') {
						$("#list").jqGrid('setCell', lastsel, 'health_insurance', 1, '', true);
						$("#list").jqGrid('setCell', lastsel, 'national_pension', 1, '', true);
					}
				}
				
				var lastRowPayment = $("#list").jqGrid('getRowData', lastsel).payment;
				
				if(lastRowPayment != '3') {
					$("#list").jqGrid('setCell', lastsel, 'wage', "0", '', true);  //다른 셀 바꾸기
				} 
				
				if(cellName == 'labor_contract_seq' && value != '0') {
        			for(var i = 0; i < $("#" + rowId + " > td").length; i++) {
        				if($($($($("#" + rowId + " > td")[i])[0].attributes[3])[0])[0]) {
        					if($($($($("#" + rowId + " > td")[i])[0].attributes[3])[0])[0].value == 'list_sign_manager_type') {
        						$($("#" + rowId + " > td")[i]).removeClass("not-editable-cell");
        					}
        				}
        			}
        		}else if(cellName == 'sign_manager_type' && value != 'E') {
        			$("#list").jqGrid('setCell', rowId, 'sign_manager_seq' , '0', '', true);
        			$("#list").jqGrid('setCell', rowId, 'sign_manager_name' , null, '', true);
        			$("#list").jqGrid('setCell', rowId, 'sign_manager_name', "", 'not-editable-cell');
        		}else if(cellName == 'sign_manager_type' && value == 'E') {
        			for(var i = 0; i < $("#" + rowId + " > td").length; i++) {
        				if($($($($("#" + rowId + " > td")[i])[0].attributes[1])[0])[0]) {
        					if($($($($("#" + rowId + " > td")[i])[0].attributes[1])[0])[0].value == 'list_sign_manager_name') {
        						$($("#" + rowId + " > td")[i]).removeClass("not-editable-cell");
        					}
        				}
        			}
        		}
				
        		isSelect = false;
         		var result = JSON.parse(res.responseText).code;
         		var message = JSON.parse(res.responseText).message;
         		
         		if(result != "0000"){
	         		return [ false, message];
         		}else{
	         		return [true, ""];
         		}   
         	},
         	beforeSelectRow: selectRowid
		});
	
	  	$("#list").jqGrid('navGrid', "#paging", {excel: true, edit: false, add: false, del: false, search: false, refresh: false, position: 'left'});
	
	  	$("#list").jqGrid('filterToolbar', {searchOperators : true});
	/*
  	// jqgrid caption 클릭 시 접기/펼치기 기능
  	$("div.ui-jqgrid-titlebar").click(function () {
	    $("div.ui-jqgrid-titlebar a").trigger("click");
  	});
	*/
	  
	  	// 행 추가
	  	$("#btnAdd").click( function() {
    		var params = "";
	
    		$.ajax({
	            type: "POST",
				url: "/admin/setEmployerCell/",
            	data: params,
        		dataType: 'json',
         		success: function(data) {
					$("#list").trigger("reloadGrid");
	//                    $("#list").addRowData(seq, {}, "first");
				},
      			beforeSend: function(xhr) {
					xhr.setRequestHeader("AJAX", true);
				},
           		error: function(e) {
					if ( data.status == "901" ) {
						location.href = "/admin/login";
					} else {
						alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
					}
				}
    		});
  		});
	  
	  	var isShowCell = false;
	  
	  	// 간략보기
	  	$("#btnCellShow").click( function() {
     		if ( isShowCell ) {
	       		$("#list").jqGrid('hideCol', [
					"employer_phone1",
					"employer_phone2",
					"employer_phone3",
					"employer_phone4",
				]);
	       		
       			isShowCell = false;
       			$("#list").setGridWidth(jqWidth);
     		} else {
	       		$("#list").jqGrid('showCol', [
					"employer_phone1",
					"employer_phone2",
					"employer_phone3",
					"employer_phone4",
				]);
	
       			isShowCell= true;
       			$("#list").setGridWidth(jqWidth);
     		}
  		});
	
	  	// 행 삭제
	  	$("#btnDel").click( function() {
    		var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
	    
    		if( recs.length < 1 ){
				alert("최소 1개 이상 선택하셔야합니다.");
				return;
			}
	    
    		for (var i = 0; i < recs.length; i++) { //row id수만큼 실행    
				if(!checkMyEmployer(recs[i])){
					alert("다른지점 구인처가 포함되어 삭제 할 수 없습니다.");
					return;
				}
			}
	    
			if(!confirm("선택사항을 삭제하시겠습니까?")){
				return;
			}
	
    		//var params = "sel_employer_seq=" + recs;
    		var params = {
    			sel_employer_seq : recs
    		};
    		var rows = recs.length;
	
    		$.ajax({
				type: "POST",
				url: "/admin/removeEmployerInfo",
            	data: params,
        		dataType: 'json',
         		success: function(data) {
					$("#list").trigger("reloadGrid");
				},
				beforeSend: function(xhr) {
					xhr.setRequestHeader("AJAX", true);
				},
				error: function(e) {
					if ( data.status == "901" ) {
						location.href = "/admin/login";
					} else {
						alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
					}
				}
    		});
  		});
	
	  	//새로고침
	  	$("#btnRel").click( function() {
    		$("#list").trigger("reloadGrid");                       // 그리드 다시그리기
  		});
	
	  	//검색
	  	$("#btnSrh").click( function() {
	    	// 검색어 체크
// 	    	if ( $("#srh_type option:selected").val() != "" && $("#srh_text").val() == "" ) {
// 	      		alert('검색어를 입력하세요.');
//       			$("#srh_text").focus();
	
//       			return false;
//     		}
	
    		$("#list").setGridParam({
	//            page: pageNum,
	//          rowNum: 15,
        		postData: {
		      		start_reg_date: $("#start_reg_date").val(),
	        		end_reg_date: $("#end_reg_date").val(),
	          		srh_use_yn: $("input:radio[name=srh_use_yn]:checked").val(),
	            	srh_type: $("#srh_type option:selected").val(),
	            	srh_text: $("#srh_text").val()
        		}
    		}).trigger("reloadGrid");
	
    		lastsel = -1;
  		});
	  
		//구인처 병합.
	  	$("#btnMerge").click(function() {
			var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
			    
			if(recs.length != 2){
				alert("구인처 2개만 선택 되어야 합니다.");
				return;
			}
			  
			for (var i = 0; i < recs.length; i++) { //row id수만큼 실행
				if(!checkMyEmployer(recs[i])){
					alert("다른지점 구인처가 포함되어 병합 할 수 없습니다.");
					return;
				}
			}
			  
			if($("#list").jqGrid('getRowData', recs[0]).company_seq != $("#list").jqGrid('getRowData', recs[1]).company_seq){
				alert("서로 다른 지점의 구인처는 병합 할 수 없습니다.");
				return;
			} 
			   
			var frmId  = "mergeFrm"; 
			var frm    = $("#"+ frmId);
			
			frm.append("<input type='hidden' name='sel_employer_seq' value='"+ recs[0] +"' />");
			frm.append("<input type='hidden' name='sel_employer_seq' value='"+ recs[1] +"' />");
	
			frm.one("submit", function() {
				var option = 'width=800, height=500, top=100, left=100, resizable=no, status=no, menubar=no, toolbar=no, scrollbars=no, location=no';
		
				window.open('','pop_merge',option);
		 
				this.action = '/admin/employerMerge';
				this.method = 'POST';
				this.target = 'pop_merge';
			}).trigger("submit");	  
			
			frm.empty();
  		});
		
		$(document).on("click", "input[name=insuranceChk]", function() {
			if($(this)[0].id.indexOf("national") >= 0) {
				if($(this).is(":checked")) {
					$($(".health").children()[0]).prop("checked", true);
				}else {
					$($(".health").children()[0]).prop("checked", false);
				}	
			}else if($(this)[0].id.indexOf("health") >= 0) {
				if($(this).is(":checked")) {
					$($(".national").children()[0]).prop("checked", true);
				}else {
					$($(".national").children()[0]).prop("checked", false);
				}	
			}
		});
	});

	function mergeComplite(){
		$("#list").trigger("reloadGrid");
	}

	function fn_setEditable(rowId, employerCompanySeq){
		if(authLevel != '0') {
			if(authLevel == '1' && companySeq != $("#list").jqGrid("getCell", rowId, "company_seq")) {
				$("#list").jqGrid('setCell', rowId, 'labor_contract_seq', "", 'not-editable-cell');
				$("#list").jqGrid('setCell', rowId, 'receive_contract_seq', "", 'not-editable-cell');
			}
			
			$("#list").jqGrid('setCell', rowId, 'limit_count', "", 'not-editable-cell');
		}
		
		if(!checkMyEmployer(rowId)){
			$("#list").jqGrid('setCell', rowId,  'employer_name', "", 'not-editable-cell'); // 특정 cell 수정 못하게
			$("#list").jqGrid('setCell', rowId,  'manager_name', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_num', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_owner', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_business', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_sector', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'is_tax', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'tax_name', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'tax_phone', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_tel', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_fax', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_email', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_addr', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_feature', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_memo', "", 'not-editable-cell');
			
			$("#list").jqGrid('setCell', rowId,  'use_yn', "", 'not-editable-cell');
		}	
		
		var row_sign_manager_type = $("#list").jqGrid("getCell", rowId, "sign_manager_type");
		
		if(authLevel > 1 || (authLevel == '1' && companySeq != $("#list").jqGrid("getCell", rowId, "company_seq"))) {
			$("#list").jqGrid('setCell', rowId, 'labor_contract_seq', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'receive_contract_seq', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'sign_manager_type', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'sign_manager_name', "", 'not-editable-cell');
		}
		
		
		if(row_sign_manager_type != 'E') {
			$("#list").jqGrid('setCell', rowId, 'sign_manager_name', "", 'not-editable-cell');	
		}
		
		
	}

	// 구인대장
	function formatEmployerIlbo(cellvalue, options, rowObject) {
		var str = "";
	
		if ( rowObject.employer_seq > 0 ) {
	    	str += "<div class=\"bt_wrap\">";
	    	str += "<div class=\"bt_on\"><a href=\"JavaScript:ilboView('i.employer_seq', '"+ rowObject.employer_seq +"', '/admin/workIlbo', '구인대장'); \"> > </a></div>";
	    	str += "</div>";
	  	}
	
	  	return str;
	}

	//스캔파일 첨부
	function formatScan(cellvalue, options, rowObject) {
		var str = "";
	
	  	str += "<div class=\"bt_wrap\">";
	  	// '사업자등록증',  스캔 여부 - 0:미스캔 1:스캔'
	  	str += "<div id=\"CORP_"+ rowObject.employer_seq +"\" class=\"bt"+ (rowObject.employer_corp_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.employer_corp_scan_yn == "1" ? "javascript:popupImage('CORP','"+ rowObject.employer_seq+"');" : "#none")  +" \"> 사 </a></div>";
		// '기타' 스캔 여부 - 0:미스캔 1:스캔'
	  	str += "<div id=\"ETC_"+ rowObject.employer_seq +"\" class=\"bt"+ (rowObject.employer_etc_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.employer_etc_scan_yn == "1" ? "javascript:popupImage('ETC','"+ rowObject.employer_seq+"');" : "#none")  +" \"> 기 </a></div>";
	 
	  	str += "<div class=\"bt_t1\"><a href=\"JavaScript:scanAdd('"+ rowObject.employer_seq +"', '"+ options.rowId +"'); \"> + </a></div>";
	  	str += "</div>";
	
	  	return str;
	}

	//스캔파일 등록 layer popup
	function scanAdd(employer_seq, rowId) {
		if(!checkMyEmployer(rowId)){
			return;
		}
		
		$("#frmPopup input:hidden[name='service_seq']").val(employer_seq);
		
		// Layer popup
		openIrPopup('popup-layer');
	}

	//주민 통장 등 이미지 팝업
	function popupImage(serviceCode, serviceSeq) {
	  	var param = "service_type=EMPLOYER&service_code="+ serviceCode +"&service_seq="+ serviceSeq;
	
	  	window.open("/admin/popup/imagePopup?"+ param, 'image', 'width=800, height=600, toolbar=no, menubar=no, scrollbars=no, resizable=yes');
	}

	//구직/구인대장 화면으로 이동
	function ilboView(type, seq, url, menuText) {
		if(!checkMyEmployer(seq)){
			return;
		}
		
		var frmId  = "defaultFrm";    // default Form
		
		var frm      = $("#"+ frmId);
		
		var startIlboDate = $("#start_ilbo_date").val();
		var endIlboDate   = $("#end_ilbo_date").val();
		
		var ilboView = $("#"+ frmId +" > input[name=ilboView]");        // 대장으로 이동
		var ilboType = $("#"+ frmId +" > input[name=srh_ilbo_type]");   // 대장으로 이동
		var ilboSeq  = $("#"+ frmId +" > input[name=srh_seq]");         // 대장으로 이동
		
		var val = "Y";
		if ( jQuery.type(ilboView.val()) === "undefined" ) {
			frm.append("<input type='hidden' name='ilboView' value='"+ val +"' />");
		} else {
			ilboView.val(val);
		}
		
		if ( jQuery.type(ilboType.val()) === "undefined" ) {
			frm.append("<input type='hidden' name='srh_ilbo_type' value='"+ type +"' />");
		} else {
			ilboType.val(type);
		}
		
		if ( jQuery.type(ilboSeq.val()) === "undefined" ) {
			frm.append("<input type='hidden' name='srh_seq' value='"+ seq +"' />");
		} else {
			ilboSeq.val(seq);
		}
		
		$("#start_ilbo_date").val("");
		$("#end_ilbo_date").val("");
	/*
	  var obj;
	  $("#gnb > ul > li").each(function(index) {
	   if ( $(this).children("a").html() == menuText) {
	     obj = $(this);
	   }
	  });
	*/
	  	frm.attr('target', '_blank');
	  	frm.attr('action', url).submit();
	//  commSubmit(url, obj);
	  	frm.attr('target', '');
	  	frm.attr('action', '');
	
	  	$("#start_ilbo_date").val(startIlboDate);
	  	$("#end_ilbo_date").val(endIlboDate);
	  	$("#"+ frmId +" > input[name=ilboView]").val("");
	}

	function formatInsurance(cellvalue, options, rowObject) {
		var str = "";
		
		str += "<div class='bt_wrap'>";
		
	//	if ( rowObject.worker_job_name != null && rowObject.worker_job_name != "" ) {
		if(rowObject.employer_insurance == '0' &&
			rowObject.accident_insurance == '0' &&
			rowObject.national_pension == '0' &&
			rowObject.health_insurance == '0') {
		   	str += "<div id=\"div_insurance_"+ rowObject.employer_seq +"\" class=\"bt\"><a onclick=\"fn_employerInfoOptBtn('" + rowObject.employer_seq + "', '', event)\"> 없음 </a></div>";
		}else {
			var txt = '';
			
			if(rowObject.employer_insurance == '1') {
				txt += '고용보험, ';
			}		
			
			if(rowObject.accident_insurance == '1') {
				txt += '산재보험, ';
			}
			
			if(rowObject.national_pension == '1') {
				txt += '국민연금, ';
			}
			
			if(rowObject.health_insurance == '1') {
				txt += '건강보험, ';
			}
			
			txt = txt.substr(0, txt.length - 2);
			
			str += "<div id=\"div_insurance_"+ rowObject.employer_seq +"\" class=\"bt_on\"><a onclick=\"fn_employerInfoOptBtn('" + rowObject.employer_seq + "', '', event);\"> " + txt + " </a></div>";
		}
		 	
		str += "</div>";
	
		return str;
	}

	function fn_employerInfoOptBtn(employer_seq, txt, event) {
		var row = $("#list").getRowData(employer_seq);
		
		if(authLevel > 1 || (authLevel == '1' && companySeq != row.company_seq)) {
			return false;	
		}
		
		optHTML = "<div id=\"insurance_layer\" class=\"bt_wrap single\" style=\"width: 240px; display: none; background-color: #fff;\">";
		optHTML += "<div class='employer bt'>";
		
		if(row.employer_insurance == '0') {
			optHTML += '	<input type="checkbox" id="' + employer_seq + '_employerInsurance" name="insuranceChk" value="0">'; 
		}else {
			optHTML += '	<input type="checkbox" id="' + employer_seq + '_employerInsurance" name="insuranceChk" value="0" checked>';
		}
		
		optHTML += '	<label for="' + employer_seq + '_employerInsurance" class="checkTxt">고용보험</label>'
		optHTML += '</div>';
		optHTML += "<div class='accident bt'>";
		
		if(row.accident_insurance == '0') {
			optHTML += '	<input type="checkbox" id="' + employer_seq + '_accidentInsurance" name="insuranceChk" value="0">'; 
		}else {
			optHTML += '	<input type="checkbox" checked id="' + employer_seq + '_accidentInsurance" name="insuranceChk" value="0">';
		}
		
		optHTML += '	<label for="' + employer_seq + '_accidentInsurance" class="checkTxt">산재보험</label>'
		optHTML += '</div>';
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"fn_updateInsurance('" + employer_seq + "')\"> 적용 </a></div></div>";
		optHTML += "<div class='national bt'>";
		
		if(row.national_pension == '0') {
			optHTML += '	<input type="checkbox" id="' + employer_seq + '_nationalInsurance" name="insuranceChk" value="0">'; 
		}else {
			optHTML += '	<input type="checkbox" id="' + employer_seq + '_nationalInsurance" name="insuranceChk" value="0" checked>';
		}
		
		optHTML += '	<label for="' + employer_seq + '_nationalInsurance" class="checkTxt">국민연금</label>'
		optHTML += '</div>';
		optHTML += "<div class='health bt'>";
		
		if(row.health_insurance == '0') {
			optHTML += '	<input type="checkbox" id="' + employer_seq + '_healthInsurance" name="insuranceChk" value="0">'; 
		}else {
			optHTML += '	<input type="checkbox" id="' + employer_seq + '_healthInsurance" name="insuranceChk" value="0" checked>';
		}
		
		optHTML += '	<label for="' + employer_seq + '_healthInsurance" class="checkTxt">건강보험</label>'
		optHTML += '</div>';
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:closeOpt();\"> 닫기 </a></div></div>";
		optHTML += "</div>";
		
		fn_selectOptEmployerList('insurance_layer', employer_seq, txt, event, optHTML);
	}
	
	function fn_selectOptEmployerList(optId, seq, msg, e , optHTML) {
		var $opt_layer = $('#opt_layer');
		var _display;
	
		if ( !e ) e = window.Event;
		var pos = abspos(e);
	
		if ( $("#"+ optId).css("display") == 'block' ){
			closeOpt();
		}
		
		writeOpt2(optHTML);
	
		$opt_layer.css('left', (pos.x - 20) +"px");
		$opt_layer.css('top', (pos.y - 30) +"px");
	
		$opt_layer.css('display', 'block');
		$("#"+ optId).css('display', 'block');
		
		$('#code_log_layer').html("");
		$('#code_log_layer').css('display', 'none');
	}
	
	function fn_updateInsurance(employer_seq) {
		var params = {
			employer_insurance : $("#" + employer_seq + "_employerInsurance").is(":checked") ? '1' : '0',
			accident_insurance : $("#" + employer_seq + "_accidentInsurance").is(":checked") ? '1' : '0',
			national_pension : $("#" + employer_seq + "_nationalInsurance").is(":checked") ? '1' : '0',
			health_insurance : $("#" + employer_seq + "_healthInsurance").is(":checked") ? '1' : '0',
			employer_seq : employer_seq
		};
	
		$.ajax({
			type: "POST",
			url: "/admin/updateInsurance",
	        data: params,
	    	dataType: 'json',
	     	success: function(data) {
	     		closeOpt();	
	     	
				$("#list").trigger("reloadGrid");
			},
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				if ( data.status == "901" ) {
					location.href = "/admin/login";
				} else {
					alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			}
		});
	}
	
	function fn_receiveName(cellValue, options, rowObject) {
		if(rowObject.receive_contract_seq == '0') {
			return "미사용";
		}else {
			if(lastElement != '') {
				for(var i = 0; i < $(lastElement)[0].childNodes.length; i++) {
					$(lastElement).remove();	
					
					if(cellValue == $($(lastElement)[0].childNodes[i]).val()) {
						$($(lastElement).parent()[0]).text($($(lastElement)[0].childNodes[i]).text());
						
						return $($(lastElement)[0].childNodes[i]).text();
					}
				}
			}
			
			return rowObject.receive_contract_name;
		}
	}
	
	function fn_laborName(cellValue, options, rowObject) {
		if(rowObject.labor_contract_seq == '0') {
			return "미사용";
		}else {
			if(lastElement != '') {
				for(var i = 0; i < $(lastElement)[0].childNodes.length; i++) {
					$(lastElement).remove();	
					
					if(cellValue == $($(lastElement)[0].childNodes[i]).val()) {
						$($(lastElement).parent()[0]).text($($(lastElement)[0].childNodes[i]).text());
						
						return $($(lastElement)[0].childNodes[i]).text();
					}
				}
			}
			
			return rowObject.labor_contract_name
		}
	}
	
	function formatDefaultName(cellvalue, options, rowObject) {
		if(cellvalue == "" || cellvalue == null || rowObject.sign_manager_type == 'O') 
			return "";
		else	
			return cellvalue;
	}
//]]>
</script>
<%--
    <div class="title">
      <h3> 구인처 관리 </h3>
    </div>
--%>
	<article>
		<div class="inputUI_simple">
      		<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
        		<caption>등록일시, 상태, 직접검색 영역</caption>
        		<colgroup>
          			<col width="110px" />
					<col width="720px" />
					<col width="80px" />
					<col width="300px" />
					<col width="110px" />
					<col width="*" />
        		</colgroup>
        		<tr>
          			<th scope="row">등록일시</th>
          			<td>
            			<p class="floatL">
              				<input type="text" id="start_reg_date" name="start_reg_date" class="inp-field wid100 datepicker" /> <span class="dateTxt">~</span>
              				<input type="text" id="end_reg_date" name="end_reg_date" class="inp-field wid100 datepicker" />
            			</p>
            			<div class="inputUI_simple">
            				<span class="contGp btnCalendar">
              					<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'all'   ); $('#btnSrh').trigger('click');" checked="checked" /><label for="day_type_1" class="label-radio">전체</label>
              					<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'today' ); $('#btnSrh').trigger('click');" /><label for="day_type_2" class="label-radio">오늘</label>
              					<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'week'  ); $('#btnSrh').trigger('click');" /><label for="day_type_3" class="label-radio">1주일</label>
              					<input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'month' ); $('#btnSrh').trigger('click');" /><label for="day_type_4" class="label-radio">1개월</label>
              					<input type="radio" id="day_type_5" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '3month'); $('#btnSrh').trigger('click');" /><label for="day_type_5" class="label-radio">3개월</label>
              					<input type="radio" id="day_type_6" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '6month'); $('#btnSrh').trigger('click');" /><label for="day_type_6" class="label-radio">6개월</label>
            				</span>
            			</div>
          			</td>
          			<th scope="row" class="linelY pdlM">상태</th>
         			<td>
           				<input type="radio" id="srh_use_yn_1" name="srh_use_yn" class="inputJo" value=""  /><label for="srh_use_yn_1" class="label-radio">전체</label>
           				<input type="radio" id="srh_use_yn_2" name="srh_use_yn" class="inputJo" value="1" checked="checked" /><label for="srh_use_yn_2" class="label-radio">사용</label>
           				<input type="radio" id="srh_use_yn_3" name="srh_use_yn" class="inputJo" value="0" /><label for="srh_use_yn_3" class="label-radio">미사용</label>
         			</td>
          			<th scope="row" class="linelY pdlM">직접검색</th>
          			<td>
            			<div class="select-inner">
            				<select id="srh_type" name="srh_type" class="styled02 floatL wid138" onChange="$('#srh_text').focus();">
<!--               					<option value="" selected="selected">선택</option> -->
<!--               					<option value="">전체</option> -->
								<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
              						<option value="c.company_name">지점</option>
								</c:if>
              					<option value="e.employer_name">회사명</option>
              					<option value="e.employer_owner">대표자명</option>
              					<option value="e.employer_phone">폰번호</option>
              					<option value="e.employer_num">사업자등록번호</option>
              					<option value="e.employer_email">이메일</option>
              					<option value="e.employer_tel">전화번호</option>
              					<option value="e.employer_addr">주소</option>
            				</select>
            			</div>
            			<input type="text" id="srh_text" name="srh_text" class="inp-field wid300 mglS" onKeyDown="if ( event.keyCode == 13 ) $('#btnSrh').click();" />
            			<div class="btn-module floatL mglS">
	              			<a href="#none" id="btnSrh" class="search">검색</a>
            			</div>
          			</td>
        		</tr>
      		</table>
      	</div>
	</article>

    <div class="btn-module mgtS mgbS">
		<div class="leftGroup">
			<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq '0' }">
        		<a href="#none" id="btnAdd" class="btnStyle01">추가</a>
        	</c:if>
        	<a href="#none" id="btnDel" class="btnStyle01">삭제</a>
        	<a href="#none" id="btnRel" class="btnStyle05">새로고침</a>
        	<a href="#none" id="export" class="excel">excel</a>
        	<a href="#none" id="btnCellShow" class="btnStyle05">전체보기</a>
        	<c:choose>
  				<c:when test="${sessionScope.ADMIN_SESSION.auth_level le 1}">
        			<a href="#none" id="btnMerge" class="btnStyle05">구인처병합</a>
        		</c:when>
        	</c:choose>
      	</div>

      	<div class="leftGroup" style="padding-left: 80px;padding-top:10px">
        	총 :&nbsp;&nbsp;&nbsp; <span id="totalRecords" style="color: #ef0606;"></span>
      	</div>
	</div>

    <table id="list"></table>
    <div id="paging"></div>
</form>
<div id="opt_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b;"></div>

<!-- 팝업 : 스캔 첨부파일 등록 -->
<div id="popup-layer">
	<header class="header">
		<h1 class="tit">스캔파일 첨부...</h1>
	    <a href="#none" class="close" onclick="javascript:closeIrPopup('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
	</header>
	<form id="frmPopup" name="frmPopup" action="/admin/employerUploadFile" method="post" enctype="multipart/form-data">
		<input type="hidden" id="service_seq" name="service_seq" />

		<section>
			<div class="cont-box">
				<article>
					<table class="bd-form inputUI_simple" summary="첨부파일 등록 영역입니다.">
			      		<caption>사업자등록증</caption>
			      		<colgroup>
			        		<col width="130px" />
			        		<col width="450px" />
			      		</colgroup>
			      		<tbody>
			      			<tr>
			          			<th>사업자등록증</th>
			          			<td>
				            		<div class="btn-module filebox floatL">
				              			<input type="text" id="text_corp" name="text_corp" class="upload-name wid250" disabled="disabled">
				              			<label for="file_corp">파일 첨부</label>
				              			<input type="file" id="file_corp" name="file_corp" class="upload-hidden">
				            		</div>
				          		</td>
			        		</tr>
			        		<tr>
								<th>기타</th>
			          			<td>
			            			<div class="btn-module filebox floatL">
			              				<input type="text" id="text_etc" name="text_etc" class="upload-name wid250" disabled="disabled" />
			              				<label for="file_etc">파일 첨부</label>
			              				<input type="file" id="file_etc" name="file_etc" class="upload-hidden" />
			            			</div>
			          			</td>
			        		</tr>
			      		</tbody>
					</table>
				</article>
			
			    <div class="btn-module mgtS">
					<div class="rightGroup"><a href="#popup-layer" id="btnScanAdd" class="btnStyle01">등록</a></div>
			    </div>
			</div>
		</section>
	</form>
</div>

<form id="mergeFrm" name="mergeFrm" method="post"></form>

<div id="code_log_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b; padding:5px">NO Data</div>
<script type="text/javascript">
//<![CDATA[
	$(window).load(function() {
  		setDayType('start_reg_date', 'end_reg_date', 'all');

  		$(function() {
    		$("input:radio[name=srh_use_yn]").click( function() {
      			$("input:radio[name=srh_use_yn]").removeAttr('checked');
      			$("input:radio[name=srh_use_yn]").prop('checked', false);

      			$(this).prop('checked', true);
    		});
  		});
	});

	//스캔파일 추가
	$("#btnScanAdd").click( function() {
		$('#frmPopup').ajaxForm({
			url: "/admin/employerUploadFile", 
			enctype: "multipart/form-data", // 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
	 		// 보내기전 validation check
	 		beforeSubmit: function (data, frm, opt) {
				return true;
			},
	    	// submit 이후처리
	    	success: function(result) {
	    		alert('업로드 하였습니다.');
	
				$("#text_license").val("");
	        	$("#file_license").val("");
		
	        	$("#text_bank").val("");
	        	$("#file_bank").val("");
		
	        	$("#text_corp").val("");
	        	$("#file_corp").val("");
		
	        	$("#text_etc").val("");
	        	$("#file_etc").val("");
	                 	
	        	$("#list").trigger("reloadGrid");
			} ,
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
	    	error: function(e) {
		    	if ( data.status == "901" ) {
					location.href = "/admin/login";
				} else {
					alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
			}
		});
	
		$('#frmPopup').submit();
		
		closeIrPopup('popup-layer');
	});
	
	function getCodeStyle(_code) {
		oCode = mCode.get(_code);
  
		var _style = "";

		try {
			_style = " style=\"background-color:#"+ oCode.bgcolor +"; color:#"+ oCode.color +"\"";
		} catch(e) {}
		
		return _style;
	}
	
	//노임수령
	function formatIncomeCode(cellvalue, options, rowObject) {
		var str = "";
		var rowId = options.rowId;
	  
		str += "<div class=\"bt_wrap\">";
    	if ( rowObject.employer_income_code != null && rowObject.employer_income_code != "" && rowObject.employer_income_code > 0 ) {
    		var _style = getCodeStyle(rowObject.employer_income_code);

    		str += "<div id=\"div_income_"+ rowObject.employer_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_incomeCodeOpt('"+ rowObject.employer_seq +"', event, " + rowObject.employer_income_code + "); return false;\""+ _style +"> "+cellvalue +" </a></div>";
		} else {
			str += "<div id=\"div_income_"+ rowObject.employer_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_incomeCodeOpt('"+ rowObject.employer_seq +"', event, " + 0 + "); return false;\"> 상태 </a></div>";
    	}

    	str += "</div>";
	
		return str;
	}
	
	//노임수령 정보 option 세팅
	function fn_incomeCodeOpt(employer_seq, event, employer_income_code) {
		var row = $("#list").getRowData(employer_seq);
		if(companySeq != row.company_seq) {
			return false;	
		}
		
		optHTML = "<div id=\"income_layer\" class=\"bt_wrap single\" style=\"width: 280px; display: none; background-color: #fff;\">";
		optHTML += optIncome.replace(/<<EMPLOYER_SEQ>>/gi, employer_seq).replace(employer_income_code +" bt", employer_income_code +" bt_on");
		optHTML += clsHTML;
		optHTML +="<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('income_layer', '" + employer_seq + "', 'employer_income_code', 0, 'employer_income_name', '');\" > 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ employer_seq +"', '300', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
		
		selectOptIlboList('income_layer', event, optHTML);
	}
	
	//노임지급
	function formatPayCode(cellvalue, options, rowObject) {
		var str = "";
		var rowId = options.rowId;
			
    	str += "<div class=\"bt_wrap\">";
    	
    	if ( rowObject.employer_pay_code != null && rowObject.employer_pay_code != "" && rowObject.employer_pay_code > 0 ) {
	      	var _style = getCodeStyle(rowObject.employer_pay_code);
	      	str += "<div id=\"div_pay_"+ rowObject.employer_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_payCodeOpt('"+ rowObject.employer_seq +"', event, '" + rowObject.employer_pay_code + "'); return false;\""+ _style +"> "+ cellvalue +" </a></div>";
    	} else {
	        str += "<div id=\"div_pay_"+ rowObject.employer_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_payCodeOpt('"+ rowObject.employer_seq +"', event, '" + 0 + "'); return false;\"> 선택 </a></div>";
    	}
		
    	str += "</div>";
		
		return str;
	}
	
	//노임지급 정보 option 세팅
	function fn_payCodeOpt(employer_seq, event, employer_pay_code) {
		$("#div_pay_"+employer_seq).click();
		
		optHTML = "<div id=\"pay_layer\" class=\"bt_wrap single\" style=\"width: 220px; display: none; background-color: #fff;\">";
		optHTML += optPay.replace(/<<EMPLOYER_SEQ>>/gi, employer_seq).replace(employer_pay_code +" bt", employer_pay_code +" bt_on");
		optHTML += clsHTML;
		optHTML +="<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('pay_layer', '" + employer_seq + "', 'employer_pay_code', 0, 'employer_pay_name', '');\" > 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ employer_seq +"', '200', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
		
		selectOptIlboList('pay_layer', event, optHTML);
	}
	
	function selectOptIlboList(optId, e, optHTML) {
		var $opt_layer = $('#opt_layer');
		var _display;
	
		if ( !e ) e = window.Event;
		var pos = abspos(e);
	
		
		if ( $("#"+ optId).css("display") == 'block' ){
			closeOpt();
		}
		
		writeOpt2(optHTML);
	
		$opt_layer.css('left', (pos.x - 100) +"px");
		$opt_layer.css('top', (pos.y-20) +"px");
	
		$opt_layer.css('display', 'block');
		$("#"+ optId).css('display', 'block');
		
		$('#code_log_layer').html("");
		$('#code_log_layer').css('display', 'none');
	}
	
	function chkCodeUpdate(divId, employer_seq, codeValue, val, codeName, nm) {
		var str = '{"cellname": "' + codeValue + '", "employer_seq": "' + employer_seq + '", "' + codeValue + '": "' + val + '", "' + codeName + '" : "' + nm + '"}';
		var params = jQuery.parseJSON(str);
		
		if(authLevel > 0) {
			if(val == '300009' || val == '300010') {
				alert("비승인 메뉴입니다.");
				return;
			}
		}
		
		$.ajax({
			type: "POST",
			url: "/admin/setEmployerInfo",
	    	data: params,
			dataType: 'json',
			success: function(data) {
				if ( codeName == "employer_income_name" ) {
	            	$("#list").jqGrid('setCell', employer_seq, "employer_income_code", val, '', true);
	                $("#list").jqGrid('setCell', employer_seq, "employer_income_name", (nm == "") ? null : nm, '', '', true);
				} else if ( codeName == "employer_pay_name" ) {
	            	$("#list").jqGrid('setCell', employer_seq, "employer_pay_code", val, '', true);
	                $("#list").jqGrid('setCell', employer_seq, "employer_pay_name", (nm == "") ? null : nm, '', '', true);
				}
			},
			beforeSend: function(xhr) {
	            xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
	            if ( data.status == "901" ) {
	              location.href = "/admin/login";
	            } else {
	              alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
	            }
			}
		});

		if ( !$("#"+ divId).is(".multi") ) {       // 단일선택 옵션인 경우
			closeOpt();
		}
	}
	
	//option layer display
	function showCodeLog(seq, code_type, e) {
		$('#code_log_layer').html("");
	  	var _display;

	  	_display = $('#code_log_layer').css('display') == 'none'?'block':'none';

	  	var divEl = $("#opt_layer");
	  	var divX = divEl.offset().left;
	  	var divY = divEl.offset().top;
	  	var divHeight = divEl.height();
	    
	  	$('#code_log_layer').css('left', divX+"px");
	  	$('#code_log_layer').css('top', (divY + divHeight -40) +"px");

	   	var _data = {
			employer_seq : seq,
			code_type : code_type
	   	};
	   
		$.ajax({
		    type	: "POST",
		    url		: "/admin/selectEmployerCodeLogeList",
		    data	: _data,
		    dataType: 'json',
		    success: function(data) {
		    	var _html = "";
		    	if ( data != null && data.length > 0 ) {
		    		
		    		_html = "<table class='bd-form s-form'>";
		    		
		    		for ( var i = 0; i < data.length; i++ ) {
		    			_html += "<tr><td style='padding:5px;'>" +data[i].reg_admin + "</td><td  style='padding:5px;'> "+ data[i].code_name + "</td><td  style='padding:5px;'>" + data[i].reg_date + "</td></tr>";
		    		}
		    		
		    		_html+="</table>";
		    	}
		    	
		    	$('#code_log_layer').html(_html);
		    	$('#code_log_layer').css('display', _display);
		    },
		    beforeSend: function(xhr) {
		            xhr.setRequestHeader("AJAX", true);
		          },
		    error: function(e) {
		            if ( data.status == "901" ) {
		              location.href = "/admin/login";
		            }
		    }
		});
	}

	function employerNumCheck(regNo){
		console.log("regNo", regNo);
		if(regNo == "" || regNo == "?") // 사업자등록번호 - 삭제 or 초기화
			return [true, ""];

		// 숫자체크
		var regex = /[^0-9]/g;
		var result = !regex.test(regNo);
		var msg = regex.test(regNo) ? " - 숫자만 입력가능합니다." : "";
		if(!result)return [result, msg];

		// 사업자번호 or 주민번호 유효성 체크
		var validCheck = regNo.length == 13 ? (ssnCheck(regNo) ? [true, ""] : [false, "유효한 주민번호가 아닙니다."])
				: regNo.length == 10 ? validBizID(regNo) : [false, " - 숫자 10자리(사업자번호) 또는 13자리(주민번호)만 가능합니다."];
		if(!validCheck[0]) return validCheck;


		// 중복 체크
		var repeatCheck = [true, ""];
		$.ajax({
			type : "POST",
			url	: "/admin/getEmployerByEmployerNum",
			data : {"employer_num":regNo},
			dataType : 'json',
			async:false,
			success	: function(data) {
				if( data && data.employer_seq != '0' ) repeatCheck = [false, "중복된 사업자번호 또는 주민번호 입니다."];
			},
			beforeSend: function(xhr) {
				$(".wrap-loading").show();
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				$(".wrap-loading").hide();
			},
			complete: function(e) {
				$(".wrap-loading").hide();
			}
		});
		return repeatCheck;
	}

	function employerNumFomat(cellvalue, options, rowObject) {
		if ( cellvalue == "" || cellvalue == null) {
			return "";
		} else {
			return cellvalue.length == 13 ? formatRegNo(cellvalue, options, rowObject) :
					cellvalue.length == 10 ? formatRegNum(cellvalue, options, rowObject) : cellvalue;
		}
	}
//]]>
</script>
