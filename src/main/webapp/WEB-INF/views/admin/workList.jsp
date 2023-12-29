<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ehjyv0iw4b&submodules=geocoder"></script>
<script type="text/javascript">
//<![CDATA[

	var companySeq 	= "<c:out value='${companySeq}' />";
	var authLevel = ${sessionScope.ADMIN_SESSION.auth_level };
	
	var tempAddr;
	var isSelect = false;
	var lastsel;            //편집 sel
	var beforeAddr;
	var lastElement = '';

	$(function() {
		var use_opts = "1:사용;0:미사용";   // 사용유무 - 0:미사용 1:사용
	  	var breakfast_opts = "1:유;0:무";   // 조식유무 - 1:유 0:무
		var managerType_opts = "O:현장매니저;E:본사매니저";
	  	var laborContract = '${laborContract }';
	  	var receiveContract = '${receiveContract }';
	  	
	  	// jqGrid 생성
	  	$("#list").jqGrid({
			url: "/admin/getWorkList",
	        datatype: "json",                               // 로컬그리드이용
	        mtype: "POST",
	        postData: {
	        	srh_use_yn: 1,
	            page: 1,
			},
			//sort 하는 방법 jjh 수정
	        sortname: "work_seq",
	        sortorder: "desc",
	
	        rowList: [25, 50, 100],                         // 한페이지에 몇개씩 보여 줄건지?
	        height: "100%",                               // 그리드 높이
	        width: "auto",
	//           autowidth: true,
	
	        rowNum: 50,
	        pager: '#paging',                            // 네비게이션 도구를 보여줄 div요소
	        viewrecords: true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무
	
	        multiselect: true,
	        multiboxonly: true,
	        caption: "구인처 현장 목록",                   // 그리드타이틀

          	rownumbers: true,                                 // 그리드 번호 표시
         	rownumWidth: 40,                                   // 번호 표시 너비 (px)

            cellEdit: true,
          	cellsubmit: "remote",
            cellurl: "/admin/setWorkInfo",              // 추가, 수정, 삭제 url

            jsonReader: {
				id: "work_seq",
           		repeatitems: false
            },

            // 컬럼명
            colNames: ['현장 순번', '지점 순번', '구인처 순번', '현장매니저고유키','관계키','지점', '구인처 명', ' 대장', '정보', '현장명','일출력수','출역','총출역', '대장', 
                       '현장도착시간','현장마감시간', "지도",'현장주소', '현장좌표','주소설명','작업설명', '조식유무', '최저나이','최고나이', '혈압제한','작업자정보', 
                       '현장매니저', '현장 메모', '등록매니져','등록매니져전화','복사','현장담당자','현장담당자전화','현장 팩스', '현장 담당자 이메일', '대리수령 양식', '대리수령 양식명', '근로계약 양식', '근로계약 양식명', '완료', '마지막 출역 일자', '상태', '등록일시', '등록자','소유자'],

            // 컬럼별 속성
            colModel: [
				{name: 'work_seq', index: 'w.work_seq', key: true, width: 60, hidden: authLevel == 0? false : true , search: true, searchoptions: {sopt: ['eq','cn','nc','ge','le']}},
                {name: 'company_seq', index: 'c.company_seq', width: 60, hidden: authLevel == 0? false : true , search: true, searchoptions: {sopt: ['eq','cn','nc','ge','le']} },
                {name: 'employer_seq', index: 'employer_seq', width: 60, hidden: authLevel == 0? false : true  , search: true, searchoptions: {sopt: ['eq','cn','nc','ge','le']} },
                {name: 'manager_seq', index: 'manager_seq', width: 50, hidden: authLevel == 0? false : true  , search: true, searchoptions: {sopt: ['eq','cn','nc','ge','le']}   },
                {name: 'wm_seq', index: 'wm_seq', width: 50, hidden: authLevel == 0? false : true  , search: true, searchoptions: {sopt: ['eq','cn','nc','ge','le']}   },
				<c:choose>
  					<c:when test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
                 		{name: 'company_name', index: 'company_name', width: 80, align: "left", sortable: true,
                        	editable: true,
                        	edittype: "text",
                     		editoptions: {
                                size: 30,
                            	dataInit: function (e, cm) {
                                	$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택

                                    $(e).autocomplete({
//                                             source: "/admin/getCompanyNameList?srh_use_yn=1",
                                    	source: function (request, response) {
                                        	$.ajax({
                                            	url: "/admin/getCompanyNameList", 
                                                type: "POST", 
                                                dataType: "json",
                                                data: { term: request.term, srh_use_yn: 1 },
                                                success: function (data) {
                                                	response($.map(data, function (item) {
//                                                                                           return { label: item.code + ":" + item.name, value: item.code, id: item.id }
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
                                        	lastsel = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값

											$(e).val(ui.item.label);

											$("#list").jqGrid('setCell', lastsel, 'company_seq', ui.item.code, '', true);  

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
			     	 			/*	{type:'change', fn:function(e){ 필요한처리할 함수명();}},   //onchange Event */
			     	 				{type: 'keydown', fn:function(e){                   //keydown Event
										if(e.keyCode == 9 || e.keyCode == 13){      //Enter Key or Tab Key
											var value =  e.target.value;
				                	 			
				                	 		if ( !isSelect && value != '?' ) {
												var p = $("#list").jqGrid("getGridParam");
				                        		var iCol = p.iColByName["company_name"];
				                        		var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
				
				                        		$("#list").jqGrid('restoreCell', iRow, iCol);
				                        		alert("지점을 선택하여 엔터를 쳐야 됩니다.");
											}
										}
			         	 			} //end keydown
			         	 		}]    //dataEvents 종료
								}
                        		, editrules: {custom: true, custom_func: validCompanyName}
                    			, searchoptions: {sopt: ['cn','eq','nc']}
                 			},
  					</c:when>
  					<c:otherwise>
                		{name: 'company_name', index: 'company_name', align: "left", sortable: true, searchoptions: {sopt: ['cn','eq','nc']} },
  					</c:otherwise>
				</c:choose>
				{name: 'employer_name', index: 'employer_name', align: "left", sortable: true,
					editable: true,
                    edittype: "text",
                    editoptions: {
                    	size: 30,
						dataInit: function (e, cm) {
                        	$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
							$(e).autocomplete({
//                                             source: "/admin/getEmployerNameList?srh_use_yn=1",
								source: function (request, response) {
                                	$.ajax({
                                    	url: "/admin/getSearchEmployerList", type: "POST", dataType: "json",
                                    	//url: "/admin/getEmployerNameList", type: "POST", dataType: "json",
                                        data: { term: request.term, srh_use_yn: 1 },
                                        success: function (data) {
                                        	response($.map(data, function (item) {
//                                                                                           return { label: item.code + ":" + item.name, value: item.code, id: item.id }
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
                                	$(e).val(ui.item.text);

                                    $("#list").jqGrid('setCell', lastsel,'employer_seq', ui.item.code, '', true);  
                                    $("#list").jqGrid('setCell', lastsel,'employer_add', ui.item.code, '', true);  
                                    $("#list").jqGrid('setCell', lastsel,'employer_ilbo', ui.item.code, '', true);  

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
			              	 	/*	{type:'change', fn:function(e){ 필요한처리할 함수명();}},   //onchange Event */
			            	{
			              	 	type: 'keydown', fn:function(e){                   //keydown Event
									if(e.keyCode == 9 || e.keyCode == 13){      //Enter Key or Tab Key
										var value =  e.target.value;
				 		                	 			
				 		                if ( !isSelect && value != '?' ) {
				 							var p = $("#list").jqGrid("getGridParam");
				 		                    var iCol = p.iColByName["employer_name"];
				 		                    var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
				 		
				 		                    $("#list").jqGrid('restoreCell', iRow, iCol);
				 		                    alert("구인처를 선택하여 엔터를 쳐야 됩니다.");
										}
									}
								} //end keydown
							}
						]    //dataEvents 종료
					}
					, editrules: {custom: true, custom_func: validEmployerName}
                    , searchoptions: {sopt: ['cn','eq','nc']}
					, formatter: formatSelectName
				},
                {name: 'employer_ilbo', index: 'employer_ilbo', align: "left", width: 30, sortable: false, search: false, formatter: formatEmployerIlbo},
                {name: 'employer_add', index: 'employer_add', align: "left", width: 30, sortable: false, search: false, formatter: formatEmployerAdd},
                {name: 'work_name', index: 'work_name', align: "left", editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
                		//현장의 출석 일수 에 따라 배경색을 다르게 한다.
                		var count = rowObject.day_count;
                		 
                		if(count == 2){
                			return 'style="background-color: #a7e67b"'; //; color:#"';	 
                		}else if(count >= 3){
                			return 'style="background-color: #e6c57b"'; //; color:#"';
                		}else{
                			return false;
                		}
					}	
				},
                {name: 'day_count', index: 'day_count', align: "center", width: 50, editable: false, search: false, sortable: true, hidden: true },
                {name: 'out_count', index: 'out_count', align: "center", width: 50, editable: false, search: false, sortable: true },
                {name: 'total_out_count', index: 'total_out_count', align: "center", width: 50, editable: false, search: false, sortable: true },
				{name: 'work_ilbo', index: 'work_ilbo', align: "left", width: 30, sortable: false, search: false, formatter: formatWorkIlbo},
				{name: 'work_arrival', index: 'work_arrival', align: "left", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']},
                	editrules: {custom: true, custom_func: validWorkTime},
                    formatter: formatTime
				},
                {name: 'work_finish', index: 'work_finish', align: "left", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']},
                	editrules: {custom: true, custom_func: validWorkTime},
                    formatter: formatTime
				},
                {name: 'addr_edit', index: 'addr_edit', align: "left", width: 30, sortable: false, search: false, formatter: formatAddrEdit},
                {name: 'work_addr', index: 'work_addr', align: "left", width: 250, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}}, //formatter: formatGeocode, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'work_latlng', index: 'work_latlng', align: "left", editable: false, sortable: true, search: false, hidden: true },
                {name: 'work_addr_comment', index: 'work_addr_comment', align: "left", editable: false, sortable: true, search: false, hidden: true },
                 
                {name: 'work_memo', index: 'work_memo', align: "left", width: 200, editable: true, sortable: true, edittype: "textarea",editoptions:{rows:"10",cols:"20"}, searchoptions: {sopt: ['cn','eq','nc']}},
                 
                {name: 'work_breakfast_yn', index: 'work_breakfast_yn', align: "center", width: 70, edittype: "select", editoptions: {value: breakfast_opts}, formatter: formatBreakfast, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'work_age_min', index: 'work_age_min', align: "center", width: 60, editable: true, sortable: true,
                	editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 3}
                    , search: false
                    /*,searchoptions: {sopt: ['cn','eq','nc']}*/
				},
                {name: 'work_age', index: 'work_age', align: "center", width: 60, editable: true, sortable: true,
                	editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 3}
                    , search: false
                       /*, searchoptions: {sopt: ['cn','eq','nc']}*/
				},
                {name: 'work_blood_pressure', index: 'work_blood_pressure', align: "left", width: 60, editable: true, sortable: true,
                	editrules: {custom: true, custom_func: validNum}, editoptions: {maxlength: 3},
                   	searchoptions: {sopt: ['cn','eq','nc']}
				},
                 
                {name: 'is_worker_info', index: 'is_worker_info', search: false, width: 70, align: "center", sortable: true, editable: true
	            	,formatter: 'checkbox', edittype:"checkbox", editoptions :{value: '1:0', defaultValue: '0'}  
				},
				/*현장매니저  */
                {name: 'manager_add', index: 'manager_add', align: "left", width: 100, sortable: false, search: false, formatter: formatManagerAdd, editable: false, hidden: ${sessionScope.ADMIN_SESSION.work_app eq '1'? 'false' : 'true' }},
                                  
				{name: 'work_comment', index: 'work_comment', align: "left", width: 200, editable: true, sortable: true, edittype: "textarea", editoptions:{rows:"10",cols:"20"}, searchoptions: {sopt: ['cn','eq','nc']}},
                 
				{name: 'manager_name', index: 'manager_name', align: "left", sortable: true, width: 100, hidden: ${sessionScope.ADMIN_SESSION.work_app eq '1'? 'false' : 'true' }
					, editable: true 
                    , edittype: "text"
					, editoptions: {
                    	size: 30,
						dataInit: function (e, cm) {
                        	lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
                         			
							var employer_seq = $("#list").jqGrid("getCell", lastsel, "employer_seq");
							if(employer_seq ==0){
								alert("구인처를 먼저 등록 하세요.");
								$("#list").jqGrid('editCell', 0, 0, false);	//focus 벗어나기.
								
								return true;
							}
                         			
                            $(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
                            $(e).autocomplete({
                            	source: function (request, response) {
									$.ajax({
                                    	url: "/admin/getManagerNameList", type: "POST", dataType: "json",
                                        data: { 
                                        	term: request.term
                                            , use_yn: 1
                                                        	 /* , company_seq: $("#list").jqGrid('getRowData', lastsel).company_seq */
                                            , manager_type: "O"
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
                                        	  		
									$("#list").jqGrid('setCell', lastsel, 'manager_seq'	, ui.item.manager_seq, '', true);  
									$("#list").jqGrid('setCell', lastsel, 'manager_phone', ui.item.manager_phone, '', true);  
									$("#list").jqGrid('setCell', lastsel, 'manager_name_hidden', ui.item.manager_name, '', true);  
                                                    
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
	                , formatter: formatSelectName
	                , cellattr: function(rowId, tv, rowObject, cm, rdata) {
		            	return 'style="background-color: #f7f4e4;"';
					}
              	},
              	{name: 'manager_phone', index: 'manager_phone', align: "left", width: 100, editable: false, hidden: ${sessionScope.ADMIN_SESSION.work_app eq '1'? 'false' : 'true' }
					, sortable: true
					, editrules: {custom: true, custom_func: validWorPhone}
					, formatter: formatPhoneStar, searchoptions: {sopt: ['cn','eq','nc']}
				},
                 
                {name: 'manager_copy', index: 'manager_copy', align: "left", width: 30, sortable: false, search: false
					, hidden: ${sessionScope.ADMIN_SESSION.work_app eq '1' ? 'false' : 'true' }
              	  	, formatter: formatCopyManager
				},
                //현장담당자  
                {name: 'work_manager_name', index: 'work_manager_name', align: "left", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'work_manager_phone', index: 'work_manager_phone', align: "left", width: 100, editable: true, sortable: true, 
                	 editrules: {custom: true, custom_func: validWorPhone},
                	 formatter: formatPhoneStar, searchoptions: {sopt: ['cn','eq','nc']}},

				{name: 'work_manager_fax', index: 'work_manager_fax', align: "left", width: 100, editable: true, sortable: true, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'work_manager_email', index: 'work_manager_email', align: "left", width: 140, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
	          	{name: 'receive_contract_name', index: 'receive_contract_name', hidden: true},
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
                {name: 'complete_count', index: 'complete_count', align: "center", width: 50, search: false, editable: false },
                {name: 'ilbo_last_date', index: 'ilbo_last_date', align: "center", width: 100, search: false, editable: false},

                {name: 'use_yn', index: 'use_yn', align: "center", width: 60, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
                {name: 'reg_date', index: 'reg_date', align: "center", width: 100, search: false, editable: false, sortable: true, formatoptions: {newformat: "y/m/d H:i"}},
                {name: 'reg_admin', index: 'w.reg_admin', align: "left", width: 100, searchoptions: {sopt: ['cn','eq','nc']}, editable: false, sortable: true},
                {name: 'owner_id', index: 'w.owner_id', align: "left", width: 60, searchoptions: {sopt: ['cn','eq','nc']}, editable: false, sortable: true, hidden:true}
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
                        
				beforeAddr = $("#list").jqGrid('getRowData', rowid).work_addr;
			},
    		beforeSubmitCell: function(rowid, cellname, value) {// submit 전
				if ( cellname == "use_yn" && value == "0" ) {
					$("#"+rowid).hide();
				}

				if ( cellname == "work_name" ) {
					$("#list").jqGrid('setCell', rowid,cellname, value, '', true);  
					$("#list").jqGrid('setCell', rowid, 'work_ilbo', '1', '', true);  
				}

				if ( cellname == "manager_name" ) {
					return {
						work_seq : rowid,
						employer_seq : $("#list").jqGrid('getRowData', rowid).employer_seq,
						manager_seq	: $("#list").jqGrid('getRowData', rowid).manager_seq,
						manager_name : $("#list").jqGrid('getRowData', rowid).manager_name,
						manager_phone : $("#list").jqGrid('getRowData', rowid).manager_phone,
						wm_seq : $("#list").jqGrid('getRowData', rowid).wm_seq,
						cellname : cellname
					}; 
				}
                        
				if ( cellname == "work_addr" ) {
					fomateGeocodeNaver(value, rowid, $("#list").jqGrid('getRowData', rowid));

					return;
				}

				return {
					work_seq : rowid,
					company_seq	: $("#list").jqGrid('getRowData', rowid).company_seq,
					employer_seq : $("#list").jqGrid('getRowData', rowid).employer_seq,
					wm_seq : $("#list").jqGrid('getRowData', rowid).wm_seq,
					cellname : cellname
				};
			},
        	afterSubmitCell: function(data, rowid, cellname, value) {
        		var _responseJson = JSON.parse(data.responseText);
        		
        		if(!isEmpty(_responseJson.wm_seq) ){
        			$("#list").jqGrid('setCell', lastsel, 'wm_seq' , _responseJson.wm_seq, '', true);
        		}
        		
        		if(cellname == 'employer_name') {
        			$("#list").trigger("reloadGrid");
        		}
        	
        		isSelect = false;
        	},
        	afterInsertRow: function(rowId, rowdata, rowelem) {	//그리드를 그릴때 호출 되는 구나....
				fn_setEditable(rowId, rowdata.company_seq);
			},
        	// Grid 로드 완료 후
        	loadComplete: function(data) {
            	//총 개수 표시
				$("#totalRecords").html(numberWithCommas( $("#list").getGridParam("records") ));
				if( $("#list").getGridParam("records") < 1 ){
					$("#btnExcel").click(function(){
						alert("출력할 결과물이 없습니다.");
					})
				}else{
					$("#btnExcel").click(function(){
						document.defaultFrm.action = "/admin/workListExcel";
						document.defaultFrm.submit();
					})
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
      		beforeSelectRow: selectRowid
		});

  		$("#list").jqGrid('navGrid', "#paging", {edit: false, add: false, del: false, search: false, refresh: false, position: 'left'});

  		$("#list").jqGrid('filterToolbar', {searchOperators : true});

  		$("#list").jqGrid('setGroupHeaders', { useColSpanStyle: true,
			groupHeaders:[
				{ startColumnName: 'employer_name', numberOfColumns: 3, titleText: '구인처' }
				,{ startColumnName: 'work_age_min', numberOfColumns: 2, titleText: '나이제한' }
			]
   		});
/*
  // jqgrid caption 클릭 시 접기/펼치기 기능
  $("div.ui-jqgrid-titlebar").click(function () {
    $("div.ui-jqgrid-titlebar a").trigger("click");
  });
*/
		//현장 병합
		$("#btnMerge").click(function() {
			var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
	    
			if(recs.length != 2) {
				alert("현장 2개만 선택 되어야 합니다.");
			
		  		return;
	  		}
	  
			if($("#list").jqGrid('getRowData', recs[0]).employer_seq != $("#list").jqGrid('getRowData', recs[1]).employer_seq){
		   		alert("서로 다른 구인처의 현장은 병합 할 수 없습니다.");
		   	
		   		return;
			}
		  
			if($("#list").jqGrid('getRowData', recs[0]).company_seq != $("#list").jqGrid('getRowData', recs[1]).company_seq){
		   		alert("서로 다른 지점의 현장은 병합 할 수 없습니다.");
		   	
		   		return;
			} 
		   
			var frmId = "mergeFrm"; 
			var frm = $("#"+ frmId);
		
			frm.append("<input type='hidden' name='sel_work_seq' value='"+ recs[0] +"' />");
			frm.append("<input type='hidden' name='sel_work_seq' value='"+ recs[1] +"' />");
		
			frm.one("submit", function() {
				var option = 'width=800, height=500, top=100, left=100, resizable=no, status=no, menubar=no, toolbar=no, scrollbars=no, location=no';
		
				window.open('','pop_merge',option);
		 
				this.action = '/admin/workMerge';
				this.method = 'POST';
				this.target = 'pop_merge';
			}).trigger("submit");	  
		 
			frm.empty();
		});

  		// 행 추가
  		$("#btnAdd").click( function() {
    		var params = "";

    		$.ajax({
            	type: "POST",
             	url: "/admin/setWorkCell/",
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

  		// 행 삭제
		$("#btnDel").click( function() {
			var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
			
			if( recs.length < 1 ){
				alert("최소 1개 이상 선택하셔야합니다.");
				return;
			}
			
			for (var i = 0; i < recs.length; i++) { //row id수만큼 실행    
				if(!checkMyWork(recs[i])){
					alert("다른지점 현장이 포함되어 삭제 할 수 없습니다.");
					return;
				}
			}
   
			if(!confirm("선택사항을 삭제하시겠습니까?")){
				return;
			}
    
			//var params = "sel_work_seq=" + recs;
			var params = {
				sel_work_seq : recs	
			};
			var rows = recs.length;
		
			$.ajax({
				type: "POST",
				url: "/admin/removeWorkInfo",
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

  		// 새로고침
  		$("#btnRel").click( function() {
    		lastsel = -1;

    		$("#list").trigger("reloadGrid");                       // 그리드 다시그리기
  		});

		// 검색
  		$("#btnSrh").click( function() {
    		// 검색어 체크
//     		if ( $("#srh_type option:selected").val() != "" && $("#srh_text").val() == "" ) {
//       			alert('검색어를 입력하세요.');
//       			$("#srh_text").focus();

//       			return false;
//     		}

    		$("#list").setGridParam({
//            page: pageNum,
//          rowNum: 15,    //jjh 주석 처리 기본 값에 따른다.
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

  		// 일보로 등록
		$("#btnCopy").click( function() {
			var managerSeq = $("#manager_layer input[name='manager_seq']:checked").val();
			var managerWorkSeq = $("#manager_layer input[name='manager_seq']:checked").data("work_seq");
		
			if(isEmpty(managerSeq)){
			 	alert("매니져 리스트에서 매니져를 선택 하세요.");
		 		return;
			}
	
		  	if ( $("#copy_rows").val() == 0 ) { //jjh 추가 숫자만 입력 하도록 copy_rows 도 수정 하였음
	      		alert("카피할 수를 입력 하세요.");
	      		return false;
	    	}
		  	
	    	var paramFrms = $("form[name=defaultFrm]").serialize();
	
	    	var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
	    	var params = "&sel_work_seq=" + recs + "&owner_id=";
	    	var rows = recs.length;
	    	var owner_id = '';
	
		    if ( rows == null || rows <= 0 ) {
		      alert('추가할 현장을 선택하세요.');
		      return false;
		    }
		    
		    if(rows > 1){
		    	alert("현장을 하나만 선택 하세요.");
		    	return false;
		    }
	    
			var selectSel = recs[0];
		
			var companySeq 		= $("#list").jqGrid("getCell", selectSel, "company_seq");
			var workName 		= $("#list").jqGrid("getCell", selectSel, "work_name");
			var managerName 	= $("#list").jqGrid("getCell", selectSel, "manager_name");
			var managerPhone 	= $("#list").jqGrid("getCell", selectSel, "manager_phone");
			
			if(selectSel != managerWorkSeq) {
				alert("다른 현장의 매니저를 선택하셨습니다.");
				
				return false;
			}
			
			if(companySeq === "0" || workName === ""){
				alert("구인처명과 현장명이 빠졌습니다");
				return false;
			}
			
			var _data = {
				work_seq : selectSel
				, manager_seq : managerSeq
				, copy_rows : $("#copy_rows").val()
				, to_ilbo_date :$("#to_ilbo_date").val()
				, employer_seq : $("#list").jqGrid('getRowData', selectSel).employer_seq 
			}
			
			var _url = "<c:url value='/admin/addWorkToIlboCell' />";
	
			commonAjax(_url, _data, true, function(data) {
				//successListener
				if (data.code == "0000") {
					toastOk("일일 대장에 추가 하였습니다.");
				} else {
					if (jQuery.type(data.message) != 'undefined') {
						if (data.message != "") {
							toastFail(data.message);
						} else {
							toastFail("오류가 발생했습니다.1");
						}
					} else {
						toastFail("오류가 발생했습니다.2");
					}
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
	});

	//현장관리자 폰번호 검사
	function validWorPhone(phoneNum, nm, valref) {
		var result = true;
		var resultStr = "";
	
		if ( phoneNum == "" ) return [true, ""];

		var valiResult = validPhone(phoneNum, null, null);
		if ( !valiResult[0] ) return valiResult;

		return [result, resultStr];
	}

	function fn_setEditable(rowId, workCompanySeq){
		if(!checkMyWork(rowId)){
			$("#list").jqGrid('setCell', rowId, 'employer_name', "", 'not-editable-cell'); // 특정 cell 수정 못하게
			$("#list").jqGrid('setCell', rowId, 'work_name', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_arrival', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_finish', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_addr', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_memo', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_age_min', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_age', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_blood_pressure', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_comment', "", 'not-editable-cell');
	
			$("#list").jqGrid('setCell', rowId, 'manager_name', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'manager_phone', "", 'not-editable-cell');
			
			$("#list").jqGrid('setCell', rowId, 'work_manager_name', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_manager_phone', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_manager_fax', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'work_manager_email', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'use_yn', "", 'not-editable-cell');
			
			if(authLevel != '0') {
				$("#list").jqGrid('setCell', rowId, 'labor_contract_seq', "", 'not-editable-cell');
				$("#list").jqGrid('setCell', rowId, 'receive_contract_seq', "", 'not-editable-cell');
			}
		}	
		
		if(authLevel > 1 || (authLevel == '1' && companySeq != $("#list").jqGrid("getCell", rowId, "company_seq"))) {
			$("#list").jqGrid('setCell', rowId, 'labor_contract_seq', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'receive_contract_seq', "", 'not-editable-cell');
		}
	}

	// 거래처 추가
	function formatEmployerAdd(cellvalue, options, rowObject) {
  		var str = "";

  		if ( rowObject.employer_seq == "0" ) {
    		str += "<div class=\"bt_wrap\">";
  //  str += "<div class=\"bt_t1\"><a href=\"JavaScript:employerAdd('"+ options.rowId +"'); \"> + </a></div>";
    		str += "</div>";
  		}else{
  	  		str += "<div class=\"bt_wrap\">";
        	str += "<div class=\"bt_on\"><a href=\"JavaScript:employerView('"+ options.rowId +"');\"> V </a></div>";
        	str += "</div>";
  		}

  		return str;
	}

	//현장매니저 추가
	function formatManagerAdd(cellvalue, options, rowObject) {
		var str = "";
		
		str += "<div class=\"bt_wrap\">";
		
		if(authLevel == '0') {
			str += "<div class=\"bt_t1\"><a href=\"JavaScript:void(0);\" onclick=\"managerReg('"+ options.rowId +"'); \"> R </a></div>";
		}
		
		str += "<div class=\"bt_green\"><a href=\"JavaScript:void(0);\" onclick=\"managerAdd('"+ options.rowId +"', event);\"> A </a></div>";
		
		str += "<div class=\"bt_yellow\"><a href=\"JavaScript:void(0);\" onclick=\"managerList('"+ options.rowId +"', event);\"> L </a></div>";
		str += "</div>";
		
  		return str;
	}

	// 구인대장
	function formatEmployerIlbo(cellvalue, options, rowObject) {
  		var str = "";
  		if ( rowObject.employer_seq > 0 ) {
    		str += "<div class=\"bt_wrap\">";
    		str += "<div class=\"bt_on\"><a href=\"JavaScript:ilboView('i.employer_seq', '"+ rowObject.employer_seq +"', '/admin/workIlbo', '구인대장', '" + rowObject.work_seq +"'); \"> > </a></div>";
    		str += "</div>";
  		}

  		return str;
	}

	// 구인대장
	function formatWorkIlbo(cellvalue, options, rowObject) {
  		var str = "";

  		if ( rowObject.work_name != "?" ) {
    		str += "<div class=\"bt_wrap\">";
    		str += "<div class=\"bt_on\"><a href=\"JavaScript:ilboView('i.work_seq', '"+ rowObject.work_seq +"', '/admin/workIlbo', '구인대장'); \"> > </a></div>";
    		str += "</div>";
  		}

  		return str;
	}

	// 조식유무
	function formatBreakfast(cellvalue, options, rowObject) {
  		var str = "";

  		str += "<div class=\"bt_wrap\">";
  		str += "<div class=\"bt"+ (cellvalue == "1" ? "_on" : "") +"\"><a href=\"JavaScript:chkUpdate('"+ rowObject.employer_seq +"', '"+ rowObject.work_seq +"', 'work_breakfast_yn', '1');\"> 유 </a></div>";
  		str += "<div class=\"bt"+ (cellvalue == "0" ? "_on" : "") +"\"><a href=\"JavaScript:chkUpdate('"+ rowObject.employer_seq +"', '"+ rowObject.work_seq +"', 'work_breakfast_yn', '0');\"> 무 </a></div>";
  		str += "</div>";

  		return str;
	}

	function formatCopyManager(cellvalue, options, rowObject) {
		var str = "";
	
		str += "<div class=\"bt_wrap\">";
		str += "<div class=\"bt_on\"><a title='복사' href=\"JavaScript:copyManager( '"+ rowObject.work_seq +"', '"+ rowObject.manager_seq +"', '"+ rowObject.manager_phone +"','"+ rowObject.manager_name +"'); \"> > </a></div>";
		str += "</div>";
	
  		return str;
	}

	function formatAddrEdit(cellvalue, options, rowObject) {
		var str = "";
		var btn = "";
	
		str += "<div class=\"bt_wrap\">";
		if( rowObject.work_latlng != "" && rowObject.work_latlng != null ) {
	  		if( rowObject.work_addr_comment != null && rowObject.work_addr_comment != "" ) {
	    		btn = "_green";
	  		} else {
	    		btn = "_t1";
	  		}
		}
	
		str += "<div class=\"bt"+ btn +"\"><a href=\"JavaScript:check_MapWindowOpen('WORK', '"+ rowObject.work_seq +"','"+ rowObject.work_latlng +"','','"+rowObject.work_addr_comment +"'); \" title='"+ rowObject.work_latlng +"'> M </a></div>";
		str += "</div>";
	
		return str;
	}

	function check_MapWindowOpen(mode,seq,latlng,addr,comment) {
	  	var workCompanySeq = $("#list").jqGrid("getCell", seq, "company_seq");
	  
	  	if(companySeq == workCompanySeq){
		  	mapWindowOpen(mode,seq,latlng,addr,comment);
	  	}
	}

	function copyManager(rowid, manager_seq, manager_phone,manager_name){
		if(!checkMyWork(rowid)){
			return;
		}
		
		if(manager_seq == "0"){
			alert("등록매니져를 먼저 등록 하세요.");
			return;
		} 
	    
		$.ajax({
			type: "POST",
		   	url: "/admin/setWorkInfo",
		    data: { 
		    	work_seq: rowid
		  	   , work_manager_name: manager_name
		  	   , work_manager_phone: manager_phone 
		  	},
		    dataType: 'json',
		  	success: function(data) {
		  		$("#list").jqGrid('setCell', rowid, 'work_manager_name' , manager_name, '', true);
		  	    $("#list").jqGrid('setCell', rowid, 'work_manager_phone', manager_phone, '', true);
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

	//구직/구인대장 화면으로 이동
	function ilboView(type, seq, url, menuText, work_seq) {
		var workCompanySeq;
		if( type == "i.employer_seq" ){
			workCompanySeq = $("#list").jqGrid("getCell", work_seq, "company_seq");	
		}else{
			workCompanySeq = $("#list").jqGrid("getCell", seq, "company_seq");
		}
	
		if(companySeq != workCompanySeq && authLevel != "0"){
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

		frm.attr('target', '_blank');
		frm.attr('action', url).submit();
		
		frm.attr('target', '');
		frm.attr('action', '');
		
		$("#start_ilbo_date").val(startIlboDate);
		$("#end_ilbo_date").val(endIlboDate);
		$("#"+ frmId +" > input[name=ilboView]").val("");
	}

	//상태변경
	function chkUpdate(employer_seq, work_seq, cellName, val) {
		var workCompanySeq = $("#list").jqGrid("getCell", work_seq, "company_seq");
		if(companySeq != workCompanySeq){
	 		return;
		}

		var str = '{"employer_seq": '+ employer_seq +', "work_seq": '+ work_seq +', "'+ cellName +'": '+ val +'}';
		var params = jQuery.parseJSON(str);
	
		$.ajax({
	  		type: "POST",
	    	url: "/admin/setWorkInfo",
	    	data: params,
	  		dataType: 'json',
	   		success: function(data) {
	        	if(cellName == "work_breakfast_yn"){
	     	   		//값을 바꾸면 formetter 에 의해 on off 가 자동으로 바뀜.
	            	$("#list").jqGrid('setCell', work_seq, cellName, val, '', true);    
	        	}else{
	            	$("#list").trigger("reloadGrid"); 
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
	}

	// 구인처 등록 layer popup
	function employerAdd(rowid) {
		var companySeq = $("#list").jqGrid("getCell", rowid, "company_seq");
		$("#frmPopup input:hidden[name='work_seq']").val(rowid);
		$("#frmPopup input:hidden[name='company_seq']").val(companySeq);
	
		$("#employer_name").val("");
		$("#employer_num").val("");
		$("#employer_owner").val("");
		$("#employer_business").val("");
		$("#employer_sector").val("");
		$("#employer_tel").val("");
		$("#employer_fax").val("");
		$("#employer_phone1").val("");
		$("#employer_phone2").val("");
		$("#employer_phone3").val("");
		$("#employer_phone4").val("");
		$("#employer_email").val("");
		$("#employer_addr").val("");
		$("#employer_feature").val("");
		$("#employer_memo").val("");

//  $("#frmPopup").find("input[name=employer_seq]").val(employer_seq);

  		// Layer popup
  		openIrPopup('popup-layer');
	}

	function fomateGeocodeNaver(cellvalue, rowid, rowObject) {
  		var myaddress = cellvalue;
  		var cAddr = "";
  		var cLatlng ="";
  		var cWorkJson;

  		if ( myaddress == "" ) {
  			cWorkJson = {
  					work_seq : rowid,
					work_addr: "",
					work_latlng : "",
					work_sido: "",
					work_sigugun: "",
					work_dongmyun : "",
					work_rest : ""
			}
    		addrUpdate(rowid  , cWorkJson);
    		return;
  		}

  		var mapBool = true;
  
  		naver.maps.Service.geocode({address: myaddress}, function(status, response) {
    		if ( status !== naver.maps.Service.Status.OK ) {
    			$("#list").jqGrid('setCell', rowid, 'work_addr', beforeAddr, '', true);
    	
      			return alert(myaddress + '의 검색 결과가 없거나 기타 네트워크 에러');
    		}
    		
    		var result = response.result;
    		
    		var elements = response.v2.addresses[0].addressElements;
    		
    		if(result.items.length > 0) {
    			
    			var _sido = "";
        		var _sigugun = "";
        		var _dongmyun = "";
        		var _rest = "";
        		
    			for(var i =0; i<elements.length; i++){
                	if( elements[i].types[0] == "SIDO" ){
                		_sido = elements[i].longName;
                	}else if( elements[i].types[0] == "SIGUGUN" ){
                		_sigugun = elements[i].longName;
                	}else if( elements[i].types[0] == "DONGMYUN" ){
                		_dongmyun = elements[i].longName;
                	}else if( elements[i].types[0] == "LAND_NUMBER" ){
                		_rest = elements[i].longName;
                	}
                }
    			cWorkJson = {
    					work_seq : rowid,
    					work_addr: result.items[0].address,
    					work_latlng : result.items[0].point.y + "," + result.items[0].point.x,
    					work_sido: _sido,
    					work_sigugun: _sigugun,
    					work_dongmyun : _dongmyun,
    					work_rest : _rest
    			}
    		}else {
    			alert("검색 결과가 없습니다.");
    	
    			$("#list").jqGrid('setCell', rowid, 'work_addr', beforeAddr, '', true);
    	
    			mapBool = false;
    		}

    		if(mapBool) {
    			addrUpdate(rowid, cWorkJson);
    		}
  		});
	}

	//주소변경
	function addrUpdate( rowid, workJson) {
//   		var str = '{ "work_seq": '+ rowid +', "work_addr": "'+ workJson.addr +'", "work_sido": "'+ workJson.sido +'", "work_sigugun": "'+ workJson.sigugun +'", "work_dongmyun": "'+ workJson.dongmyun +'", "work_rest": "'+ workJson.rest +'", "work_latlng": "'+ workJson.latlng +'"}';
//   		var params = jQuery.parseJSON(str);

  		$.ajax({
       		type: "POST",
        	url: "/admin/setWorkInfo",
      		async: false,
       		data: workJson,
   			dataType: 'json',
    		success: function(data) {
				$("#list").jqGrid('setCell', workJson.work_seq,'work_addr', (workJson.work_addr == "")?null:workJson.work_addr, '', true);  
				$("#list").jqGrid('setCell', workJson.work_seq,'work_latlng', (workJson.work_latlng == "")?null:workJson.work_latlng, '', true);  

				//addr_edit 의 상태 변화를 위해 아무값이나 넣어서 fometter  를 동작
				$("#list").jqGrid('setCell', workJson.work_seq,'addr_edit', "1", '', true);  
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

	function mapResponse(rowid,cAddr,clatlng) {
		$("#list").jqGrid('setCell', rowid,'work_addr', cAddr, '', true);  
		$("#list").jqGrid('setCell', rowid,'work_latlng', clatlng, '', true);  
		$("#list").jqGrid('setCell', rowid,'addr_edit', '1', '', true);  
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
			
			return rowObject.labor_contract_name;
		}
	}
//]]>
</script>
<%--
    <div class="title">
      <h3> 현장 관리 </h3>
    </div>
--%>
	<article>
		<div class="inputUI_simple">
			<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
				<caption>등록일시, 상태, 직접검색 영역</caption>
				<colgroup>
					<col width="120px" />
					<col width="800px" />
          			<col width="100px" />
          			<col width="270px" />
          			<col width="100px" />
          			<col width="*" />
        		</colgroup>
        		<tr>
					<th scope="row">등록일시</th>
          			<td>
            			<p class="floatL">
              				<input type="text" id="start_reg_date" name="start_reg_date" class="inp-field wid100 datepicker" placeholder="0000-00-00"/> <span class="dateTxt">~</span>
              				<input type="text" id="end_reg_date" name="end_reg_date" class="inp-field wid100 datepicker" placeholder="0000-00-00"/>
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
          			<th scope="row"  class="linelY pdlM">상태</th>
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
              					<option value="e.employer_name">구인처 명</option>
              					<option value="w.work_name">현장명</option>
              					<option value="w.work_addr">현장 주소</option>
              					<option value="w.work_manager_name">현장 담당자</option>
              					<option value="w.work_manager_phone">현장 전화</option>
              					<option value="w.work_manager_fax">현장 팩스</option>
              					<option value="w.work_manager_email">현장 이메일</option>
            				</select>
            			</div>
            			<input type="text" id="srh_text" name="srh_text" class="inp-field wid200 mglS" onKeyDown="if ( event.keyCode == 13 ) $('#btnSrh').click();" />
            			<div class="btn-module floatL mglS">
              				<a href="#none" id="btnSrh" class="search">검색</a>
            			</div>
          			</td>
        		</tr>
<%-- 
        <tr>
          <th scope="row">일일대장 추가</th>
          <td colspan="3">
            <span class="floatL mgRS">
              <input id="copy_rows" name="copy_rows" class="inp-field wid50" placeholder="행 수" type="number"  min="1" max="100" value="1"/>
            </span>
            <span class="floatL arrow">
              <a class="prev10" href="javascript:prevMonth('to_ilbo_date');">>></a>
              <a class="prev" href="javascript:prevDate('to_ilbo_date');">></a>
            </span>
            <span class="floatL">
              <input type="text" id="to_ilbo_date" name="to_ilbo_date" class="inp-field wid100 datepicker" placeholder="대상일자" />
            </span>
            <span class="floatL mgRS arrow">
              <a class="next" href="javascript:nextDate('to_ilbo_date');">></a>
              <a class="next10" href="javascript:nextMonth('to_ilbo_date');">>></a>
            </span>

            <span class="btn-module">
              <a href="#none" id="btnCopy" name="btnCopy" class="btnStyle16 mglS">복사</a>
            </span>
          </td>
        </tr>
--%>
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
	      	<a href="#none" id="btnExcel" class="excel">excel</a>
	      	<a href="#none" id="btnMerge" class="btnStyle05">현장병합</a>
		</div>

	    <div class="leftGroup" style="padding-left: 80px;padding-top:10px">
			총 :&nbsp;&nbsp;&nbsp; <span id="totalRecords" style="color: #ef0606;"></span>
		</div>
		
		<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq '0' }">
			<div class="leftGroup" style="padding-left: 80px;;padding-top:3px">
				<span class="floatL mgRS">
					<input id="copy_rows" name="copy_rows" class="inp-field wid50" placeholder="행 수" type="number"  min="1" max="100" value="1"/>
		        </span>
		        <span class="floatL arrow">
					<a class="prev10" href="javascript:prevMonth('to_ilbo_date');">>></a>
					<a class="prev" href="javascript:prevDate('to_ilbo_date');">></a>
				</span>
		        <span class="floatL">
					<input type="text" id="to_ilbo_date" name="to_ilbo_date" class="inp-field wid100 datepicker" placeholder="대상일자" />
		        </span>
		        <span class="floatL mgRS arrow">
		          	<a class="next" href="javascript:nextDate('to_ilbo_date');">></a>
		          	<a class="next10" href="javascript:nextMonth('to_ilbo_date');">>></a>
		        </span>
			</div>
			<span class="btn-module">
				<a href="#none" id="btnCopy" name="btnCopy" class="btnStyle01 mglS wid100">일일대장추가</a>
			</span>
		</c:if>
	</div>

    <table id="list"></table>
    <div id="paging"></div>

	</form>

	<!-- 팝업 : 구인처 등록  -->
	<div id="popup-layer" style="width:800px" class="employer">
		<form id="frmPopup" name="frmPopup" method="post">
  			<input type="hidden" id="work_yn" name="work_yn" value="Y" />
  			<input type="hidden" id="employer_seq" name="employer_seq" value="0" />
  			<input type="hidden" id="work_seq" name="work_seq" />
  			<input type="hidden" id="company_seq" name="company_seq" />
		  
  			<header class="header">
    			<h1 class="tit">구인처 등록</h1>
    			<a class="close" href="javascript:closeIrPopup('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
  			</header>

  			<section>
  				<div class="cont-box">
    				<article>
      					<table class="bd-form inputUI_simple" summary="구인처 등록 영역입니다.">
      						<caption>지점 사업자등록번호 구인처명 대표자 업태 업종 전화번호 팩스 폰번호 이메일 주소 특징 메모 등록 영역</caption>
      						<colgroup>
        						<col width="20%" />
        						<col width="35%" />
        						<col width="15%" />
        						<col width="*" />
      						</colgroup>
      						<tbody>
        						<tr>
          							<th>지점</th>
          							<td colspan="3">
            							<input type="hidden" id="company_name" name="company_name" value="<c:out value="${sessionScope.ADMIN_SESSION.company_name}" />" />
            							<c:out value="${sessionScope.ADMIN_SESSION.company_name}" />
          							</td>
        						</tr>
        						<tr>
          							<th>회사명</th>
          							<td colspan=3">
            							<input type="text" class="inp-field wid500" id="employer_name" name="employer_name" />
            							<input type="hidden"  id="employer_name_check" name="employer_name_check" value="0"/>
            							<div id="chk_result" style="padding: 5px 20px"></div>
          							</td>
        						</tr>
        						<tr>
          							<th>사업자번호</th>
          							<td><input type="text" class="inp-field wid150" id="employer_num" name="employer_num" /></td>
          							<th>대표자</th>
          							<td><input type="text" class="inp-field wid150" id="employer_owner" name="employer_owner" /></td>
        						</tr>
        						<tr>
          							<th>업태</th>
          							<td><input type="text" class="inp-field wid150" id="employer_business" name="employer_business" /></td>
          							<th>업종</th>
          							<td><input type="text" class="inp-field wid150" id="employer_sector" name="employer_sector" /></td>
        						</tr>
        						<tr>
          							<th>전화번호</th>
          							<td><input type="text" class="inp-field wid150" id="employer_tel" name="employer_tel" /></td>
          							<th>팩스</th>
          							<td><input type="text" class="inp-field wid150" id="employer_fax" name="employer_fax" /></td>
        						</tr>
        						<tr>
          							<th>폰번호 1</th>
          							<td><input type="text" class="inp-field wid150" id="employer_phone1" name="employer_phone1" /></td>
          							<th>폰번호 2</th>
          							<td><input type="text" class="inp-field wid150" id="employer_phone2" name="employer_phone2" /></td>
        						</tr>
        						<tr>
          							<th>폰번호 3</th>
          							<td><input type="text" class="inp-field wid150" id="employer_phone3" name="employer_phone3" /></td>
									<th>폰번호 4</th>
							        <td><input type="text" class="inp-field wid150" id="employer_phone4" name="employer_phone4" /></td>
        						</tr>
        						<tr>
          							<th>이메일</th>
          							<td colspan="3"><input type="text" class="inp-field wid500" id="employer_email" name="employer_email" /></td>
        						</tr>
        						<tr>
          							<th>주소</th>
          							<td colspan="3"><input type="text" class="inp-field wid500" id="employer_addr" name="employer_addr" /></td>
        						</tr>
        						<tr>
          							<th>특징</th>
          							<td colspan="3"><textarea id="employer_feature" name="employer_feature" class="textarea" rows="5" cols="80" placeholder="내용입력 *" ></textarea></td>
        						</tr>
        						<tr>
          							<th>메모</th>
          							<td colspan="3"><textarea id="employer_memo" name="employer_memo" class="textarea" rows="5" cols="80" placeholder="내용입력 *" ></textarea></td>
        						</tr>
      						</tbody>
      					</table>
    				</article>

    				<div class="btn-module mgtS">
      					<div class="rightGroup"><a href="#popup-layer" id="btnEmployerAdd" class="btnStyle01">등록</a></div>
    				</div>
  				</div>
  			</section>
		</form>
	</div>

	<!-- 팝업 : 현장매니저 등록  -->
	<div id="popup-layer3" style="width:800px" class="manager">
		<form id="frmPopup3" name="frmPopup3" method="post">
			<input type="hidden"  name="work_yn" value="Y" />
  			<input type="hidden"  name="work_seq" />
  			<input type="hidden"  name="company_seq" />
  			<input type="hidden"  name="employer_seq" value="0" />
  			<input type="hidden"  name="manager_type" value="O" />
  
  			<header class="header">
    			<h1 class="tit">현장매니저 신규등록</h1>
    			<a class="close" href="javascript:closeIrPopup('popup-layer3');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
  			</header>

  			<section>
  				<div class="cont-box">
    				<article>
      					<table class="bd-form inputUI_simple" summary="구인처 등록 영역입니다.">
      						<caption>현장매니저 등록</caption>
      						<colgroup>
        						<col width="30%" />
        						<col width="*" />
      						</colgroup>
      						<tbody>
        						<tr>
          							<th>이름</th>
          								<td>
            								<input type="text" class="inp-field wid500"  name="manager_name" />
          								</td>
        						</tr>
        						<tr>
          							<th>폰번호</th>
          							<td>
            							<input type="text" class="inp-field wid300 mgRS"  id="manager_phone" name="manager_phone" />
            							<div class="btn-module mglM" id="btnC">
      										<a href="#" id="btnPhoneCheck" class="btnStyle09"><span>중복체크</span></a> <span id="chk_phone_result" style="padding: 5px 20px"></span>
    									</div>
            							<input type="hidden"  id="manager_phone_check" name="manager_phone_check" value="-1"/>
          							</td>
        						</tr>
        						<tr>
          							<th>메모</th>
          							<td>
          								<textarea id="manager_memo" name="manager_memo" class="textarea" rows="5" cols="80" placeholder="내용입력 *" ></textarea>
          							</td>
        						</tr>
      						</tbody>
      					</table>
    				</article>

    				<div class="btn-module mgtS" id="btnM">
      					<div class="rightGroup"><a href="#popup-layer3" id="btnManagerReg" class="btnStyle01"><span id="btnManager">등록<span></span></a></div>
    				</div>
  				</div>
  			</section>
		</form>
	</div>

	<!-- 팝업 : 현장매니저 추가  -->
	<div id="popup-layer4" style="width:800px" class="manager">
		<form id="frmPopup4" name="frmPopup4" method="post">
  			<input type="hidden" name="work_seq" />
  			<input type="hidden" name="company_seq" />
  			<input type="hidden" name="employer_seq" />
  	
  			<header class="header">
    			<h1 class="tit">현장매니저추가</h1>
    			<a class="close" href="javascript:closeIrPopup('popup-layer4');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
  			</header>

  			<section>
  				<div class="cont-box">
    				<article>
      					<table class="bd-form inputUI_simple" summary="구인처 등록 영역입니다.">
      						<caption>현장매니저 추가</caption>
      						<colgroup>
        						<col width="30%" />
        						<col width="*" />
      						</colgroup>
      						<tbody>
        						<tr>
          							<th>이름</th>
          							<td>
            							<input type="text" class="inp-field wid500"  name="manager_name" />
									</td>
        						</tr>
        						<tr>
          							<th>폰번호</th>
          							<td>
            							<input type="text" class="inp-field wid300 mgRS"  name="manager_phone" />
            							<div class="btn-module mglM" id="btnC">
      										<a href="#" id="btnManagerSearch" class="btnStyle09"><span>검색</span></a> 
    									</div>
          							</td>
        						</tr>
        						<tr>
          							<td colspan=2>
          								<div id="managerSearchList" style="overflow-y: auto; height: 200px;">No Data</div>
          							</td>
        						</tr>
      						</tbody>
      					</table>
    				</article>

    				<div class="btn-module mgtS" id="btnM">
      					<div class="rightGroup"><a href="#popup-layer4" id="btnManagerAdd" class="btnStyle01"><span id="btnManager">추가<span></span></a></div>
    				</div>
  				</div>
  			</section>
		</form>
	</div>

	<form id="mergeFrm" name="mergeFrm" method="post"></form>
<script type="text/javascript">
//<![CDATA[
	$(window).load(function() {
  		setDayType('start_reg_date', 'end_reg_date', 'all');

  		setDayType(1, 'to_ilbo_date', 'day');

  		$(function() {
	  		$("input:radio[name=srh_use_yn]").click( function() {
    			$("input:radio[name=srh_use_yn]").removeAttr('checked');
    			$("input:radio[name=srh_use_yn]").prop('checked', false);

      			$(this).prop('checked', true);
    		});
  		});
	});
 
	//현장매니저 등록 layer popup
	function managerReg(rowid) {
		var employer_seq 	= $("#list").jqGrid('getRowData', rowid).employer_seq;
		var work_seq 			= $("#list").jqGrid('getRowData', rowid).work_seq;
		var company_seq	= companySeq;
	
		if(employer_seq == 0 ){
			alert("구인처를 먼저 등록 하세요.");
			return;
		}
	
		var authLevel = ${sessionScope.ADMIN_SESSION.auth_level}
		if(authLevel == 0 && company_seq == 0){
			alert("지점을 선택 하세요.");
			return;
		}
	
		$("#frmPopup3 input:hidden[name='work_seq']").val(work_seq);
		$("#frmPopup3 input:hidden[name='company_seq']").val(company_seq);
		$("#frmPopup3 input:hidden[name='employer_seq']").val(employer_seq);

		$("#btnM").attr("hidden", false);
		$("#btnC").attr("hidden", false);
		
		$("#frmPopup3 input:hidden[name='manager_name']").val("");
		$("#frmPopup3 input:hidden[name='manager_phone']").val("");
		
		$("#manager_memo").val("");
		$("#manager_phone_check").val("-1");
		$("#chk_phone_result").html("");
	
		lastsel = rowid;
		
		var $popUp = $("#popup-layer3");

		$dimmed.fadeIn();

		if(/Android|webOS|iPhone|iPad|iPod|pocket|psp|kindle|avantgo|blazer|midori|Tablet|Palm|maemo|plucker|phone|BlackBerry|symbian|IEMobile|mobile|ZuneWP7|Windows Phone|Opera Mini/i.test(navigator.userAgent)) {
			$popUp.css("top", Math.max(0, (($(window).height() - $popUp.outerHeight()) / 2) + $(window).scrollTop()) + "px");
			$popUp.css({
				'position' : 'absolute',
				'display' : "inline-block"
			});
		}else {
			openIrPopup('popup-layer3');
		}
	}

	//현장매니저 추가 layer popup
	function managerAdd(rowid) {
		var employer_seq = $("#list").jqGrid('getRowData', rowid).employer_seq;
		var work_seq = $("#list").jqGrid('getRowData', rowid).work_seq;
		var company_seq	= $("#list").jqGrid('getRowData', rowid).company_seq;
	
		if(employer_seq == 0 ){
			alert("구인처를 먼저 등록 하세요.");
			return;
		}
	
		var authLevel = ${sessionScope.ADMIN_SESSION.auth_level}
		if(authLevel == 0 && company_seq == 0){
			alert("지점을 선택 하세요.");
			return;
		}
	
		$("#frmPopup4 input:hidden[name='work_seq']").val(work_seq);
		$("#frmPopup4 input:hidden[name='company_seq']").val(company_seq);
		$("#frmPopup4 input:hidden[name='employer_seq']").val(employer_seq);
		$("#frmPopup4 input:hidden[name='manager_name']").val("");
		$("#frmPopup4 input:hidden[name='manager_phone']").val("");
		$('#managerSearchList').html("");
	
		lastsel = rowid;
	
		var $popUp = $("#popup-layer4");

		$dimmed.fadeIn();
	
		if(/Android|webOS|iPhone|iPad|iPod|pocket|psp|kindle|avantgo|blazer|midori|Tablet|Palm|maemo|plucker|phone|BlackBerry|symbian|IEMobile|mobile|ZuneWP7|Windows Phone|Opera Mini/i.test(navigator.userAgent)) {
			$popUp.css("top", Math.max(0, (($(window).height() - $popUp.outerHeight()) / 2) + $(window).scrollTop()) + "px");
			$popUp.css({
				'position' : 'absolute',
				'display' : "inline-block"
			});
		}else {
			openIrPopup('popup-layer4');
		}
	}

	//폰번호가 바뀔때 마다..
	$("#manager_phone").on("propertychange change keyup paste input", function() {
		$("#manager_phone_check").val("-1");
		$("#chk_phone_result").html("");
	});

	//폰번호 체크
	$("#btnPhoneCheck").click( function() {
		var phoneNum = $("#manager_phone").val();
		//폰에서 - 빼기
		var phoneNum = phoneNum.replace(/#/gi, "");
	
		if(phoneNum == ""){
			alert("핸드폰번호를 입력하세요.");
			return;
		}
	
		var result = validPhone(phoneNum,null,null);
		
		if ( !result[0] ){
			alert(result[1]);
		}else{
			var company_seq = $("#frmPopup3 input:hidden[name='company_seq']").val();
		
	  		$.ajax({
	        	url: "/admin/chkManagerPhone", 
	        	type: "POST", 
	        	dataType: "json",
	       		data: {manager_phone: phoneNum/* , use_yn:1 */},	
	       		//{ term: phoneNum, srh_use_yn: 1, company_seq: company_seq },
		    	success: function (data) {
					if ( !data ) {
						$("#chk_phone_result").html("중복 폰번호");
			            $("#chk_phone_result").css("color", "red");
			            $("#manager_phone_check").val("0");
					} else {
			        	$("#chk_phone_result").html("등록 가능 ");
			            $("#chk_phone_result").css("color", "green");
			            $("#manager_phone_check").val("1");
					}
				},
	 			beforeSend: function(xhr) {
	 				$(".wrap-loading").show();
	               	xhr.setRequestHeader("AJAX", true);
	         	},
	         	complete : function() {
	 				$(".wrap-loading").hide();
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
	});

	//현장매니저 등록
	$("#btnManagerReg").click( function() {
		if($("#manager_phone_check").val() == "-1"){
			alert("중복체크를 하세요.");
		  	return false;
		}

		if($("#manager_phone_check").val() == "0"){
			alert("등록할수 없는 폰 번호 입니다.");
		  	return false;
		}

		if ($('#manager_name').val() == "" ) {
			alert('메니져 이름을 입력하세요.');
		    $('#manager_name').focus();
		
		    return false;
		}
	
		var phoneNum = $("#manager_phone").val().replace(/#/gi, ""); 
		$("#manager_phone").val(phoneNum);
		var _data = $("form[name=frmPopup3]").serialize();
		
		var _url = "<c:url value='/admin/setManagerReg' />";

		commonAjax(_url, _data, true, function(data) {
			//successListener
			if (data.code == "0000") {
				toastOk("매니져가 등록 되었습니다.");
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						toastFail(data.message);
					} else {
						toastFail("오류가 발생했습니다.1");
					}
				} else {
					toastFail("오류가 발생했습니다.2");
				}
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.3");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});

	 	closeIrPopup('popup-layer3');
	});

	//현장매니저 검색
	$("#btnManagerSearch").click( function() {
		var managerPhone = $("#frmPopup4 input:text[name='manager_phone']").val();
		var managerName= $("#frmPopup4 input:text[name='manager_name']").val();
	
		if(isEmpty(managerPhone) && isEmpty(managerName)){
			alert("검색어를 입력 하세요.");
			return;
		}
		if(!isEmpty(managerPhone) && managerPhone.length < 4){
			alert("핸드폰번호를 4자 이상 입력 하세요.");
			return;
		}
	
		$.ajax({
			url: "/admin/getSearchManagerList", 
			type: "POST", 
			dataType: "json",
	    	data: { 
				use_yn: 1
	    	 	, manager_phone: managerPhone
	    	 	, manager_name: managerName
	     	},
	  		success: function (data) {
	    		var _html = "";
	 			if(data.code == "0000"){
	 				var managerList = data.managerList;
	 				if ( managerList != null && managerList.length > 0 ) {
		    			_html = "<ul>";
		    			for ( var i = 0; i < managerList.length; i++ ) {
		    				var _managerPhone = managerList[i].manager_phone;
			    			var _managerCompanySeq = managerList[i].company_seq;
			    			if( companySeq != _managerCompanySeq ){
			    				_managerPhone = getMaskingPhone(managerList[i].manager_phone);
			    			}
		    				_html += "<li> <input type='radio' id='manager"+i+"' name='manager_seq' value='"+ managerList[i].manager_seq +"'> <label for='manager"+i+"'> " +managerList[i].company_name + "&nbsp;"+ managerList[i].manager_name+ "&nbsp;"+ _managerPhone + "</label></li>";
		    			}
		    		
		    			_html+="</ul>";
		    		}else{
		    			_html = "No Data";
		    		}
		    	}else{
		    		_html = "Error " + data;	
		    	}
	 		
	 			$('#managerSearchList').html(_html);
	  		},
			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
				$(".wrap-loading").show();
			},
	    	error: function(e) {
				if ( data.status == "901" ) {
					location.href = "/admin/login";
				} else {
	            	alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
				}
	    	},
	    	complete : function() {
				// 요청 완료 시
				$(".wrap-loading").hide();
			}
		});
	});

	//현장매니저 추가
	$("#btnManagerAdd").click( function() {
		var workSeq 		= $("#frmPopup4 input:hidden[name='work_seq']").val();
		var companySeq	= $("#frmPopup4 input:hidden[name='company_seq']").val();
		var employerSeq	= $("#frmPopup4 input:hidden[name='employer_seq']").val();
		var managerSeq	= $("#frmPopup4 input[name='manager_seq']:checked").val();
		
		if(isEmpty(managerSeq)){
			alert("매니져를 검색하고 선택해 주세요.");
			return;
		}
		
		var _data = {
			work_seq: workSeq,
			company_seq: companySeq,
			employer_seq: employerSeq,
			manager_seq: managerSeq
		};
		
		var _url = "<c:url value='/admin/setManagerAdd' />";

		commonAjax(_url, _data, true, function(data) {
			//successListener
			if (data.code == "0000") {
				toastOk("매니져가 추가 되었습니다.");
			} else {
				if (jQuery.type(data.message) != 'undefined') {
					if (data.message != "") {
						toastFail(data.message);
					} else {
						toastFail("오류가 발생했습니다.1");
					}
				} else {
					toastFail("오류가 발생했습니다.2");
				}
			}
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.3");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	   
	 	closeIrPopup('popup-layer4');
	});

	$("#employer_name").on("blur", function() {
  		$.ajax({
    	    url: "/admin/chkEmployerName", 
    	    type: "POST", 
    	    dataType: "json",
       		data: { term: $("#employer_name").val(), srh_use_yn: 1 },
    		success: function (data) {
    			if ( !data ) {
					$("#chk_result").html("&nbsp;&nbsp;&nbsp;중복 ");
	                $("#chk_result").css("color", "red");
	                $("#employer_name_check").val("1");
               	} else {
					$("#chk_result").html("");
	                $("#chk_result").css("color", "white");
	                $("#employer_name_check").val("0");
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
	});

	$("#employer_phone1, #employer_phone2, #employer_phone3, #employer_phone4").on("blur", function() {
		var phoneNum = this.value;
		var result = validEmployerPhone(phoneNum,null,null)
		if ( !result[0] ){
			$(this).val("");
	    	$(this).focus();
			alert(result[1]);
		}
	});

	// 구인처 추가
	$("#btnEmployerAdd").click( function() {
	  	var params = $("form[name=frmPopup]").serialize();
	  	var _employer_name = $("#frmPopup input:text[name='employer_name']").val();
	  	var _employer_tel  = $("#frmPopup input:text[name='employer_tel']").val();
	
	  	// 테스트 필수 값 체크
	  	if ( _employer_name == "" ) {
	    	alert('회사명은 필수 입니다.');
	    	$("#frmPopup input:text[name='employer_name']").focus();
		
	    	return false;
	  	}
		  
	  	if($("#employer_name_check").val() == "1"){
		  	alert("회사명이 중복 입니다.");
		  	return false;
	  	}
		/*
	  	if ( _employer_tel == "" ) {
		    alert('전화번호는 필수 입니다.');
	    	$("#frmPopup input:text[name='employer_tel']").focus();
		
	    	return false;
	  	}
		*/
	  	// 구인처 등록
	  	$.ajax({
			type: "POST",
		  	url: "/admin/setEmployerForm",
		  	data: params,
		  	dataType: 'json',
		  	success: function(data) {
		  		$("#list").jqGrid('setCell', lastsel,'employer_seq'	, data.employer_seq, '', true);
		  		$("#list").jqGrid('setCell', lastsel,'employer_name', _employer_name, '', true);
		  		$("#list").jqGrid('setCell', lastsel,'employer_add', data.employer_seq, '', true); 
              	$("#list").jqGrid('setCell', lastsel,'employer_ilbo', data.employer_seq, '', true);
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
	
	  	closeIrPopup('popup-layer');
	});

	//현장매니저 리스트
	function managerList( work_seq, e) {
		$('#manager_layer').html(""); 
	
		var _display  = $('#manager_layer').css('display') == 'none' ? 'block' : 'none';
	
		if(_display == "none"){//닫기
			$('#manager_layer').css('display', _display);
			return;
		}
	
		if ( !e ) e = window.Event;
		var pos = abspos(e);

		$('#manager_layer').css('left', (pos.x - 100) +"px");
		$('#manager_layer').css('top', (pos.y-20) +"px");
	
		$.ajax({
	    	type: "POST",
	    	url: "/admin/getWorkManagerList",
	    	data: {
	    	 	work_seq: work_seq
	      	},
			dataType: 'json',
	 		success: function(result) {
	 			var _html = "";
	 			if(result.code == "0000"){
	 				var data = result.managerList;
	 				if ( data != null && data.length > 0 ) {
		    			_html = "<ul>";
		    		
		    			for ( var i = 0; i < data.length; i++ ) {
		    				if(data[i] != null && data[i].manager_seq != 0){ //본사매니져가 null 일때는 제외
		    					var _managerPhone = data[i].manager_phone;
		    					var _managerCompanySeq = data[i].company_seq;
		    					if( companySeq != _managerCompanySeq ){
		    						_managerPhone = getMaskingPhone(data[i].manager_phone);
		    					}
			    				_html += "<li> <input type='radio' id='manager"+i+"' name='manager_seq' value='"+ data[i].manager_seq +"' data-work_seq='" + work_seq + "'> <label for='manager"+i+"'> [" + data[i].manager_type + "]&nbsp;"+ data[i].company_name + "&nbsp;"+ data[i].manager_name+ "&nbsp;"+ _managerPhone+ "</label></li>";
		    				}
		    			}
		    			_html+="</ul>";
		    		}else{
		    			_html = "managerList Empty";
		    		}
		    	}else{
		    		_html = "Error " + data;	
		    	}
	 		
	 			$('#manager_layer').html(_html);
	    		$('#manager_layer').css('display', _display);
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
 
	function mergeComplite(){
		$("#list").trigger("reloadGrid");
	}
//]]>
</script>

<div id="manager_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b; padding:5px">NO Data</div>