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

	$(function() {
		$("#detailSearchForm").hide();	//상세검색 기본적으로 감춘다
		
  		var lastsel;            //편집 sel

  		// 사용유무 - 0:미사용 1:사용
  		var use_opts = "1:사용;0:미사용";
  		var search_use_opts = ":전체;1:사용;0:미사용";
  		var manager_type_opts = "O:현장;E:본사";
  		var os_type_opts = "N:미설치;A:android;I:IOS"
  		var search_os_type_opts = ":전체;N:미설치;A:android;I:IOS";
  		// 권한 - 0:전체최고관리자; 1:회사최고관리자; 2: 회사관리자
  		var auth_opts = "0:전체;1:관리자;2:직원";
  		var etc_opts = "O:O;N:N;X:X";
  		var use_status_opts = '0:승인;1:이용중지;2:도출유도;3:임금체불;4:수수료미납';
  		var search_use_status_opts = ':전체;0:승인;1:이용중지;2:도출유도;3:임금체불;4:수수료미납';
  		var join_route_opts = '${joinRouteOpts}';

  		// jqGrid 생성
  		$("#list").jqGrid({
			url: "/admin/getManagerList",
            datatype: "json",                               // 로컬그리드이용
            mtype : "POST",
            postData: {
		    	srh_use_yn: 1,
                page: 1,
                manager_type : "O"
            },

           	sortname: "manager_seq",
           	sortorder: "desc",
           	rowList: [100, 200, 500, 1000],                         // 한페이지에 몇개씩 보여 줄건지?
           	height: "100%",                               // 그리드 높이
  //         	width			: jqWidth,
         	scrollerbar: true,               
  //       	width: "auto",
           	rowNum: 200,
            pager: '#paging',                            // 네비게이션 도구를 보여줄 div요소
         	viewrecords: true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무

         	multiselect: true,
        	multiboxonly: true,
            caption: "현장매니저 목록",                 // 그리드타이틀

          	rownumbers: true,                                 // 그리드 번호 표시
         	rownumWidth: 40,                                   // 번호 표시 너비 (px)

            cellEdit: true ,
          	cellsubmit: "remote" ,
            cellurl: "/admin/setManagerInfo",              // 추가, 수정, 삭제 url

            jsonReader: {
				id: "manager_seq",
				repeatitems: false
            },

            // 컬럼명
            colNames: ['순번', '부모지점순번' ,'지점 순번', '지점', '구인처수','현장수','관리','담당자타입', '이름', '변경전 폰번호', '폰번호', '비밀번호', '메모','구분', '계정관리', 'OSType','push유무','pushToken', '가입경로', '상태', '최근접속일시','방문횟수','접속방법','등록일시', '등록자','소유자'],

            // 컬럼별 속성
            colModel: [
				{name: 'manager_seq', index: 'manager_seq', key: true, width: 60, hidden: authLevel == 0 ? false:true },
				{name: 'parent_company_seq', index: 'parent_company_seq', width: 60, hidden: authLevel == 0 ? false:true },
				{name: 'company_seq', index: 'company_seq', width: 60, hidden: authLevel == 0 ? false:true },
				<c:choose>
					<c:when test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
						{name: 'company_name', index: 'company_name', align: "left", sortable: true,
							editable: true,
							edittype: "text",
							editoptions: {
								size: 30,
								dataInit: function (e, cm) {
									$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
									$(e).autocomplete({
	//                                                            source: "/admin/getCompanyNameList?srh_use_yn=1",
										source: function (request, response) {
											$.ajax({
												url: "/admin/getCompanyNameList", type: "POST", dataType: "json",
												data: { term: request.term, srh_use_yn: 1 },
												success: function (data) {
													response($.map(data, function (item) {
//                                                                                                          return { label: item.code + ":" + item.name, value: item.code, id: item.id }
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
											lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값

											$(e).val(ui.item.label);

											$("#list").jqGrid('setCell', lastsel, 'company_seq', ui.item.code, '', true);  //다른 셀 바꾸기
		
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
                            editrules: {edithidden: true, required: false},
                            searchoptions: {sopt: ['cn','eq','nc']}
						},
                 	</c:when>
                 	<c:otherwise>
						{name: 'company_name', index: 'company_name', hidden: false, editable: false, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                 	</c:otherwise>
				</c:choose>
                 /* 
                 {name: 'employer_seq'   , index: 'employer_seq'   , width:  60, hidden: ${ sessionScope.ADMIN_SESSION.auth_level eq 0 ? false : true} },
                 {name: 'employer_name'  , index: 'employer_name'  , align:   "left", sortable:  true, width:  100, hidden: ${ sessionScope.ADMIN_SESSION.auth_level eq 0 ? false : true},
                     editable:  true,
                     edittype: "text",
                  editoptions: {
                             size: 30,
                         dataInit: function (e) {

                                     $(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
                                     $(e).autocomplete({

                                          source: function (request, response) {
                                                    $.ajax({
                                                          url: "/admin/getEmployerNameList", type: "POST", dataType: "json",
                                                         data: { term: request.term, srh_use_yn: 1 },
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

                                                    $(e).val(ui.item.label);

                                                    $("#list").jqGrid('setCell', lastsel,'employer_seq', ui.item.code, '', true);  //다른 셀 바꾸기
                                                    

                                                    isSelect = true;

                                                    return false;
                                                  }
                                     });
                                   }
                               },
                    editrules: {edithidden: true, required: false},
                    searchoptions: {sopt: ['cn','eq','nc']},
                    formatter: formatSelectName
              },
                  */
				{name: 'e_count', index: 'e_count', width: 60  ,search: false, editable: false, sortable: true},
				{name: 'w_count', index: 'w_count', width: 60 ,search: false, editable: false, sortable: true},
				{name: 'employer_view', index: 'employer_view', align: "left", width:  40, sortable: false, search: false, formatter: formatManagerTreeView}, 
				{name: 'manager_type', hidden: authLevel==0? false:true , index: 'manager_type', align: "center", width: 100, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: manager_type_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.manager_type == 'E' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'manager_name', index: 'manager_name', width: 200, align: "left", editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}
					, cellattr: function(rowId, tv, rowObject, cm, rdata){
						if( rowObject.manager_use_status > 0 ){
							return "style='color: red;'";
						}
					}
				},
				{name: 'hidden_manager_phone', index: 'manager_phone', hidden: true},
				{name: 'manager_phone', index: 'manager_phone', width: 200, align: "left", editable: true, sortable: true
					, formatter: formatPhoneStar, searchoptions: {sopt: ['cn','eq','nc']}
					, editrules: {custom:  true, custom_func: validManagerPhone}
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
						return 'style="background-color: #efeeec;" title="' + formatPhoneStar(rowObject.manager_phone, null, rowObject)+'"';
					}
				},
				{name: 'manager_pass', index: 'manager_pass', align: "left", editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}
	                 , cellattr: function(rowId, tv, rowObject, cm, rdata) {
	                     return 'style="background-color: #f7f4e4;"';
	                 }
				},
				{name: 'manager_memo', index: 'manager_memo', align: "left", width: 200, editable: true, sortable: true, edittype: "textarea", searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'manager_etc', index: 'manager_etc', align: "center", width: 60, editable: true, sortable: true, edittype: "select", searchoptions: {sopt: ['cn','eq','nc']}, editoptions: {value: etc_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.manager_etc == 'O' ) {
							return 'style="color: blue; text-align: center;"';
						}else if ( rowObject.manager_etc == 'X' ) {
							return 'style="color: red; text-align: center;"';
						}
					}
				},
				{name: 'manager_use_status', index: 'manager_use_status', align: "center", width: 100, editable: true, sortable: true, edittype: "select", stype:"select", editoptions: {value: use_status_opts}, formatter: "select" , searchoptions: {sopt: ['eq'], value: search_use_status_opts}},
				{name: 'os_type', index: 'os_type', align: "center", width: 100, editable: true, sortable: true, edittype: "select", stype:"select", editoptions: {value: os_type_opts}, formatter: "select", searchoptions: {sopt: ['eq'], value: search_os_type_opts},
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.os_type == 'N' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'manager_push_yn', index: 'manager_push_yn', align: "center", width: 100, editable: true, sortable: true, edittype: "select", stype:"select", editoptions: {value: use_opts}, formatter: "select", searchoptions: {sopt: ['eq'], value: search_use_opts },
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.manager_push_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'push_token', index: 'push_token', align: "left", editable: false, search: false},
				{name: 'join_route', index: 'join_route', align: "center", width:60, editable: true, edittype: "select", editoptions : {value: join_route_opts}, formatter: "select"
					, stype:"select", searchoptions: {sopt: ['eq'], value: ":전체;" + join_route_opts}
				},
				{name: 'use_yn', index: 'use_yn', align: "center", width: 60, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'visit_date', index: 'visit_date', align: "center", width: 100, search: false, editable: false, sortable: true, formatoptions: {newformat: "y/m/d H:i"}},
				{name: 'visit_count', index: 'visit_count', align:  "left", width: 60, search: false, editable: false, sortable: true},
				{name: 'visit_type', index: 'visit_type', align: "left", width: 60, search: false, editable: false, sortable: true},
                 
                {name: 'reg_date', index: 'reg_date', align: "center", width: 150, search: false, editable: false, sortable: true, formatoptions: {newformat: "y/m/d H:i"}},
                {name: 'reg_admin', index: 'reg_admin', align: "left", width: 100, searchoptions: {sopt: ['cn','eq','nc']}, editable: false, sortable: true},
                {name: 'owner_id', index: 'owner_id', align: "left", width: 100, searchoptions: {sopt: ['cn','eq','nc']}, editable: false, sortable: true}
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
				if ( cellname == "use_yn" && value == "0" ) {
					$("#"+rowid).hide();
				}
                        
				if( cellname =='employer_name' && value=="?"){
					return{
						manager_seq: rowid, 
                       	company_seq: $("#list").jqGrid('getRowData', rowid).company_seq,
                       	employer_seq: "0"
					}
				}
                        
				if(cellname == 'company_name') {
					return {
						manager_seq : rowid,
                  		company_seq : $("#list").jqGrid('getRowData', rowid).company_seq,
                  		company_name : $("#list").jqGrid('getRowData', rowid).company_name
					}
				}

				return {
					cellname : cellname,
					manager_seq: rowid, 
                        	/* 
                        	company_seq: $("#list").jqGrid('getRowData', rowid).company_seq,
                        	parent_company_seq: $("#list").jqGrid('getRowData', rowid).parent_company_seq,
                        	employer_seq: $("#list").jqGrid('getRowData', rowid).employer_seq
                        	 */
				};
			},
			afterSubmitCell : function(res, rowid, cellname, value, iRow, iCol){
				var result = JSON.parse(res.responseText).code;
         		var message = JSON.parse(res.responseText).message;
    	
    			if(result != "0000"){
	    			return [ false, message];
				}
    			
    			if( cellname == "manager_use_status" && value > 0 ){
    				$('#list').jqGrid('setCell', rowid, 'manager_name', '', {'color': 'red'});
    			}else{
    				$('#list').jqGrid('setCell', rowid, 'manager_name', '', {'color': '#362B36'});
    			}
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
						downExcel();
					})
				}
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
  
  		//헤더툴팁
  		//colNames: ['순번', '부모지점순번' ,'지점 순번', '지점', '구인처순번','구인처','담당자타입', '이름', '폰번호', '비밀번호', '메모','구분', 'OSType','push유무','pushToken', '상태', '최근접속일시','방문횟수','접속방법','등록일시', '등록자','소유자'],
  		setTooltipsOnColumnHeader($("#list"), 2, "최고관리자만 보인다.");
  		setTooltipsOnColumnHeader($("#list"), 10, "다른지점의 매니져는 끝4자리 *로 보여진다.");
  
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
				url: "/admin/setManagerCell/",
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
				if(!checkMyManager(recs[i])){
					alert("다른지점 매니저가 포함되어 삭제 할 수 없습니다.");
					return;
				}
			}
			
			if(!confirm("선택사항을 삭제하시겠습니까?")){
				return;
			}
		
	    	//var params = "sel_manager_seq=" + recs;
	    	var params = {
	    		sel_manager_seq : recs	
	    	};
		
		    var rows = recs.length;
	
		    $.ajax({
				type: "POST",
	            url: "/admin/removeManagerInfo",
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
	  
		//현장매니저 SMS
		$("#btnMSMS").on("click", function() {
			$("#smsPopFrm").remove();
			
	 		var params = smsPopAdd('8');
			
		 	if(params.length == 0) {
		 		$.toast({title: '현장매니저의 번호가 없거나 선택된 현장매니저가 없습니다.', position: 'middle', backgroundColor: '#ef7220', duration:1000 });
			}else {
				var text = '';
				
				text += "<input type='hidden' name='data' value='" + JSON.stringify(params) + "' />";
				
				$("#smsPopFrm").append(text);
				
				$("#smsPopFrm").one("submit", function() {
					var option = 'width=1265, height=500, top=250, left=600, resizable=no, status=no, menubar=no, toolbar=no, location=no';
				
					window.open('','pop_target',option);
				 
					this.action = '/admin/sms/smsWrite';
					this.method = 'POST';
					this.target = 'pop_target';
				}).trigger("submit");	 
			}
		});
	
	  	// 새로고침
	  	$("#btnRel").click( function() {
		    lastsel = -1;
	
	    	$("#list").trigger("reloadGrid");                       // 그리드 다시그리기
	  	});
	
	  	//상세검색 보이기 안보이기
	  	$("#btnSearch").click( function() {
		  	if($("#detailSearchForm").css("display") == "none"){
				$("#detailSearchForm").show();
			} else {
			    $("#detailSearchForm").hide();
			}
	  	});
	  
	  	// 검색
	  	$("#btnSrh").click( function() {
		    // 검색어 체크
// 		    if ( $("#srh_type option:selected").val() != "" && $("#srh_text").val() == "" ) {
// 	    	  	alert('검색어를 입력하세요.');
// 		      	$("#srh_text").focus();
		
// 	    	  	return false;
// 	    	}
		
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
	  	
	  	//알림 팝업
		$("#btnAlim").on("click", function() {
			var params = new Array();
		  	var idArray = $("#list").jqGrid('getDataIDs');
		  	var rowData;
		  	
		  	for(var i = 0; i < idArray.length; i++) {
				if($("input:checkbox[id=jqg_list_" + idArray[i] + "]").is(":checked")) {
					rowData = $("#list").getRowData(idArray[i]);
				  
				  	params.push(rowData);
			  	}
		  	}
		  
			if(params.length == 0) {
				$.toast({title: '매니저를 선택해주세요.', position: 'middle', backgroundColor: '#ef7220', duration:1000 });
					
				return false;
			}
		  
		  	for(var i = 0; i < params.length; i++) {
			  	text = '<input type="hidden" name="manager_seq" value="' + params[i].manager_seq + '">';
			  	text += '<input type="hidden" name="manager_seq_array" value="' + params[i].manager_seq + '">';
			  
			  	$("#alimPopFrm").append(text);
		  	}
			  
		  	$("#alimPopFrm").one("submit", function() {
			 	var option = 'width=700, height=450, top=300, left=200, resizable=no, status=no, menubar=no, toolbar=no, scrollbars=no, location=no';
			 
			 	window.open('','pop_target',option);
			 
			 	this.action = '/admin/alim/alimManagerWrite';
			 	this.method = 'POST';
			 	this.target = 'pop_target';
		  	}).trigger("submit");
		  	
		  	$("#alimPopFrm input[name=manager_seq]").remove();
		  	$("#alimPopFrm input[name=manager_seq_array]").remove();
	  	});
	  	
	  	$("#manager_use_status_all_label").click( function() {
	  		if( $('#manager_use_status_all').is(':checked') ){
	  			$("input:checkbox[name='manager_use_status']").prop("checked", false);
	  			$(".manager_use_status_class").removeClass("on");
	  		}else{
	  			$("input:checkbox[name='manager_use_status']").prop("checked", true);
	  			$(".manager_use_status_class").addClass("on");	  			
	  		}
	  	});
	  	
	  	$("#btnDetailSrh").click( function() {
			var array_manager_use_status = new Array();
			var cnt = 0;
			var chkBool = false;
			
			
			if($("input:checkbox[name=manager_use_status]:checked").length == 0) {
				toastFail("계정상태를 체크해주세요.");
				
				return false;
			}
			
			
			$('input:checkbox[name="manager_use_status"]').each(function() {
				if(this.checked == true){ 
					array_manager_use_status[cnt++] = String(this.value);
				}
			});
		 
			var interval = $('input[name="interval"]:checked').val();
			var work_count = $('input[name="w_count"]:checked').val();
			var employer_count = $('input[name="e_count"]:checked').val();
			
			_data = {
		  		detailSearch : 1,
		  		manager_type : 'O',
		  		sel_manager_use_status : array_manager_use_status,
		  		interval : interval,
		  		w_count : work_count,
		  		e_count : employer_count
			};	
			
			// 초기화 전 grid filters 넣어주기
			var beforePostData = $("#list").jqGrid("getGridParam", "postData");
			
			_data.filters = beforePostData.filters;
			_data.sidx = beforePostData.sidx;
			_data.sord = beforePostData.sord;
			_data.page = beforePostData.page;
			_data.rows = beforePostData.rows;
			_data.srh_del_yn = 0;
			_data.srh_use_yn = 1;
			
			$('#list').setGridParam({ postData: ""});
			$("#list").setGridParam({
			  		postData: _data
			}).trigger("reloadGrid");
			lastsel = -1;
			
			detailSrh = true;
		});
	}); //end $(function()
	
	function fn_setEditable(rowId, managerCompanySeq){
		if(!checkMyManager(rowId)){
			$("#list").jqGrid('setCell', rowId, 'manager_name', "", 'not-editable-cell'); // 특정 cell 수정 못하게
			$("#list").jqGrid('setCell', rowId, 'manager_phone', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'manager_memo', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'manager_etc', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'os_type', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'manager_push_yn', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'use_yn', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'manager_use_status', "", 'not-editable-cell');
		}
		
		if(!isSuperAdmin()){
			$("#list").jqGrid('setCell', rowId, 'manager_pass', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId, 'join_route', "", 'not-editable-cell');
		}
	}

	function formatManagerTreeView(cellvalue, options, rowObject) {
		var str = "";
	
		if( checkMyManager(rowObject)){
	    	if ( rowObject.manager_seq > 0 ) {
	        	str += "<div class=\"bt_wrap\">";
	        	str += "<div class=\"bt_on\"><a href=\"JavaScript:managerEWTreeView("+rowObject.manager_seq+",'"+ rowObject.manager_name+"','"+ rowObject.manager_phone+"');\"> > </a></div>";
	        	str += "</div>";
	    	}
		}else{
			str = "";
		}
		
		return str;
	}

	//구인처 현장 트리
	function managerEWTreeView(managerSeq, managerName, managerPhone) {
  		var params = {
  			manager_seq: managerSeq 
		};

  		$.ajax({
    		type: "POST",
        	url: "/admin/managerEWTreeViewJSP",
       		data: params,
      		async: false,
		    //dataType: 'json',
    		success: function(result) {
				$("#popup-layer2 > header> h1").html(managerName + "(" + managerPhone + ")님 추가한 구인처 현장 관계 Tree");           // popup title
				$("#popup-layer2 > section").html(result);              // 실행 결과 페이지 부모페이지에 삽입
				
            	openIrPopup('popup-layer2');
        	},
 			beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
			},
			error: function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				}
			}
  		});
	}

	function downExcel(){
		document.defaultFrm.action = "/admin/managerListExcel";
		document.defaultFrm.submit();
	}

	function smsPopAdd(tr_etc1) {
		var smsPopFrm = $("<form></form>");
		smsPopFrm.attr("name", "smsPopFrm");
		smsPopFrm.attr("id", "smsPopFrm");
		smsPopFrm.attr("method", "post");
		smsPopFrm.attr("enctype", "multipart/form-data");
		smsPopFrm.appendTo('body');
		
		var params = new Array();
		var idArray = $("#list").jqGrid('getDataIDs');
		var rowData;
		var paramData;
		
		for(var i = 0; i < idArray.length; i++) {
			if($("input:checkbox[id=jqg_list_" + idArray[i] + "]").is(":checked")) {
				rowData = $("#list").getRowData(idArray[i]);
				
				if((tr_etc1 == '8' && rowData.manager_phone.replace(/\s/g, "").length != 0 && rowData.ilbo_worker_phone != '?')) { 
					paramData = {
						manager_name : rowData.manager_name, 
						manager_phone : rowData.hidden_manager_phone,
						company_name : rowData.company_name,
						employer_name : rowData.employer_name,
						menuFlag : 'M'
					}
					params.push(paramData);
				}
			}
		}
		
		$("#smsPopFrm").append('<input type="hidden" name="tr_etc1" value="' + tr_etc1 + '" />');
		$("#smsPopFrm").append('<input type="hidden" name="menuFlag" value="M" />');
		
		return params;
	}

	//메니져 폰번호 검사
	function validManagerPhone(phoneNum, nm, valref) {
		var result = true;
		var resultStr = "";
		
		//폰에서 - 빼기
		var RegNotNum  = /[^0-9]/g;
		phoneNum = phoneNum.replace(RegNotNum,'');
		
		if ( phoneNum == "" ) return [true, ""];
		
		var valiResult = validPhone(phoneNum, null, null);
		if ( !valiResult[0] ) return valiResult;
		
		var lastsel = $("#list").jqGrid('getGridParam', "selrow" );         				// 선택한 열의 아이디값
		var company_seq = $("#list").jqGrid('getRowData', lastsel).company_seq;     	// 검색된 일보 순
	  
		if( company_seq == "0")	 return [false, "지점를 먼저 선택 하세요"];
	  
		var _data = {
			manager_phone: phoneNum
		};
	
		$.ajax({
			type: "POST",
			url: "/admin/chkManagerPhone",
			data: _data,
			async: false,
			dataType: 'json',
			success: function(data) {
				if ( !data ) {
					result = false;
					resultStr = "이미 등록된 폰번호입니다.";
				}else{
					result = true;
					resultStr = "";
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
	
		return [result, resultStr];
	}
//]]>
</script>
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
              					<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'all'   ); $('#btnSrh').trigger('click');" checked="checked" /><label for="day_type_1" class="label-radio on">전체</label>
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
<!--               					<option value="" selected="selected">전체</option> -->
								<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
              						<option value="company_name">지점</option>
								</c:if>
              					<option value="manager_name">현장매니저</option>
              					<option value="manager_phone">현장매니저전화</option>
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
			<c:choose>
	            <c:when test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
					<a href="#none" id="btnAdd" class="btnStyle01">추가</a>
				</c:when>
			</c:choose>
	        <a href="#none" id="btnDel" class="btnStyle01">삭제</a>
 			<c:if test="${sessionScope.ADMIN_SESSION.company_seq eq '13' and sessionScope.ADMIN_SESSION.auth_level eq '1' }">
				<a href="#none" id="btnMSMS" style="width: 97px;" class="btnStyle01">현장매니저 SMS</a>
 			</c:if> 
			<a href="#none" id="btnAlim" class="btnStyle01">메시지Push</a>
	        <a href="#none" id="btnRel" class="btnStyle05">새로고침</a>
	        <a href="#none" id="btnExcel" class="excel">excel</a>
	        <a href="#none" id="btnSearch" class="btnStyle02">상세검색</a>
      	</div>
      
      	<div class="leftGroup" style="padding-left: 80px; padding-top: 10px">
			총 :&nbsp;&nbsp;&nbsp; <span id="totalRecords" style="color: #ef0606;"></span>
		</div>
    </div>
    <div class="inputUI_simple" id="detailSearchForm">
		<table class="bd-form" summary="상세검색">
			<caption>상새검색</caption>
			<colgroup>
				<col width="150px" />
				<col width="520px" />
				<col width="110px" />
				<col width="540px" />
				<col width="110px" />
				<col width="*" />
			</colgroup>
			<tr>
				<th scope="row" class="linelY">최근출역일</th>
				<td colspan="6">
					<input type="radio" id="interval_all" name="interval" class="inputJo" value="" checked="checked" /><label for="interval_all" class="label-radio">전체</label>
					<input type="radio" id="interval_yesterday" name="interval" class="inputJo" value="-1 DAY" /><label for="interval_yesterday" class="label-radio">어제</label>
					<input type="radio" id="interval_week" name="interval" class="inputJo" value="-7 DAY" /><label for="interval_week" class="label-radio">최근1주일</label>
					<input type="radio" id="interval_month" name="interval" class="inputJo" value="-1 MONTH" /><label for="interval_month" class="label-radio">최근 1개월</label>
					<input type="radio" id="interval_3month" name="interval" class="inputJo" value="-3 MONTH" /><label for="interval_3month" class="label-radio">최근 3개월</label>
					<input type="radio" id="interval_6month" name="interval" class="inputJo" value="-6 MONTH" /><label for="interval_6month" class="label-radio">최근 6개월</label>
					<input type="radio" id="interval_12month" name="interval" class="inputJo" value="-12 MONTH" /><label for="interval_12month" class="label-radio">최근 12개월</label>
					
				</td>
			</tr>
			<tr>
				<th scope="row" class="linelY">현장수</th>
				<td>
					<input type="radio" id="w_count_1" name="w_count" class="inputJo" value="1" checked="checked" /><label for="w_count_1" class="label-radio">1개</label>
					<input type="radio" id="w_count_2" name="w_count" class="inputJo" value="5" /><label for="w_count_2" class="label-radio">2~5개</label>
					<input type="radio" id="w_count_3" name="w_count" class="inputJo" value="6" /><label for="w_count_3" class="label-radio">5개 이상</label>
					<input type="radio" id="w_count_4" name="w_count" class="inputJo" value="0" /><label for="w_count_4" class="label-radio">전체</label>
				</td>
				<th scope="row" class="linelY">구인처수</th>
				<td>
					<input type="radio" id="e_count_1" name="e_count" class="inputJo" value="1" checked="checked" /><label for="e_count_1" class="label-radio">1개</label>
					<input type="radio" id="e_count_2" name="e_count" class="inputJo" value="5" /><label for="e_count_2" class="label-radio">2~5개</label>
					<input type="radio" id="e_count_3" name="e_count" class="inputJo" value="6" /><label for="e_count_3" class="label-radio">5개 이상</label>
					<input type="radio" id="e_count_4" name="e_count" class="inputJo" value="0" /><label for="e_count_4" class="label-radio">전체</label>
				</td>
				<th scope="row" class="linelY">계정상태</th>
				<td>
					<input type="checkbox" id="manager_use_status_1" name="manager_use_status" 	class="inputJo" value="0" checked="checked" /><label for="manager_use_status_1" class="checkTxt manager_use_status_class">승인</label>
					<input type="checkbox" id="manager_use_status_2" name="manager_use_status" 	class="inputJo" value="1" /><label for="manager_use_status_2" class="checkTxt manager_use_status_class">이용중지</label>
					<input type="checkbox" id="manager_use_status_3" name="manager_use_status" 	class="inputJo" value="2" /><label for="manager_use_status_3" class="checkTxt manager_use_status_class">도출유도</label>
					<input type="checkbox" id="manager_use_status_4" name="manager_use_status" 	class="inputJo" value="3" /><label for="manager_use_status_4" class="checkTxt manager_use_status_class">임금체불</label>
					<input type="checkbox" id="manager_use_status_5" name="manager_use_status" 	class="inputJo" value="4" /><label for="manager_use_status_5" class="checkTxt manager_use_status_class">수수료미납</label>
					<input type="checkbox" id="manager_use_status_all" class="inputJo" value="-1" /><label for="manager_use_status_all" id="manager_use_status_all_label" class="checkTxt">전체</label>
				</td>
			</tr>
			<tr>
				<td scope="row"  colspan="6"  class="linelY pdlM">
					<div class="btn-module floatL">
						<a href="#none" id="btnDetailSrh" class="search2">상세검색</a>
					</div>				
				</td>
			</tr>
		</table>
	</div>
    <table id="list"></table>
    <div id="paging"></div>
    </form>
	<form id="alimPopFrm" name="alimPopFrm" method="post"></form>
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
//]]>
</script>
