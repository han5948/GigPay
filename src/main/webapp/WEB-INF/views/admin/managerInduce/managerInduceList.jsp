<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
	table.helpTable2 {
	    border-collapse: collapse;
	    text-align: left;
	    line-height: 1.5;
	    border-left: 1px solid #ccc;
	}
	
	table.helpTable2 tbody th {
	    width: 70px;
	    padding: 5px;
	    border: 1px solid #ccc;
	    background: #ececec;
	}
	table.helpTable2 td {
	    width: 300px;
	    padding: 5px;
	    vertical-align: top;
	    border: 1px solid #ccc;
	    background-color: white;
	}
</style>

<script type="text/javascript">
//<![CDATA[
	var lastsel = -1;            //편집 sel
	var isSelect = false;
	var use_status_opts = '0:승인;1:이용중지;2:도출유도;3:임금체불;4:수수료미납';
	var search_use_status_opts = ':전체;0:승인;1:이용중지;2:도출유도;3:임금체불;4:수수료미납';
	var companySeq = '${ sessionScope.ADMIN_SESSION.company_seq }';
	var authLevel = '${sessionScope.ADMIN_SESSION.auth_level }';
	var subGridArr = new Array();
	var parentGridArr = new Array();
	var test = {};
	
	$(function() {
		setDayType('start_reg_date', 'end_reg_date', 'all');
		
		$("#btnHelp").click( function() {
			$("#helpPanel").slideToggle("slow");
		});
		
		// jqGrid 생성
		$("#list").jqGrid({
			url : "/admin/managerInduce/getManagerInduceList",
            datatype : "json",                              
            mtype : "POST",
            postData: {
            	srh_use_yn : 1,
                page : 1
            },
            sortname : "manager_induce_seq",							//sort 하는 방법 jjh 수정
           	sortorder : "desc",
            height : jqHeight +50,                                 // 그리드 높이
			rowList : [100, 200, 500, 1000],                         // 한페이지에 몇개씩 보여 줄건지?
            rowNum : 200,
            pager : '#paging',                            // 네비게이션 도구를 보여줄 div요소
          	viewrecords : true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무
         	multiselect	: true,
         	multiboxonly : true,
            caption	: "도출유도 관리",                   			// 그리드타이틀
          	rownumbers : true,                                 	// 그리드 번호 표시
         	rownumWidth : 40,                                   // 번호 표시 너비 (px)
            cellEdit : true ,							  //처리중일때 만 수정 할 수 있도록 한다.
          	cellsubmit : "remote" ,
          	cellurl	: "/admin/managerInduce/updateManagerInduce",              // 추가, 수정, 삭제 url
          	width : "auto",
            jsonReader: {
                    id: "manager_induce_seq",
                    repeatitems: false,
           			subgrid: {
           		      root: "rows", 
           		      repeatitems: true, 
           		      cell: "cell"
           		   }
            },
            // 컬럼명
            colNames: ['도출유도 순번', '도출유도자 구분', '도출유도자 지점 순번', '도출유도자 지점', '도출유도자', '폰번호', '신고 횟수', '계정관리', '메모', '마지막 신고일시', '매니저 순번'],
            // 컬럼별 속성
            colModel: [
				{name: 'manager_induce_seq', index: 'manager_induce_seq', key: true, width: 80, search: false, hidden:${sessionScope.ADMIN_SESSION.auth_level eq 0 ? false : true}, sortable: false }
				, {name: 'manager_type', index: 'manager_type', width: 80, align: "center", search: true, sortable: false, searchoptions: {sopt: ['cn','eq','nc']}
					, formatter : function(value, row) {
						if(value == 'O') {
							return '현장매니저';
						}else {
							return '본사매니저';
						}
					}
				}
				, {name: 'manager_company_seq', index: 'manager_company_seq', hidden: true }
				, {name: 'manager_company_name', index: 'company_name', hidden: false, editable: false, sortable: true, searchoptions: {sopt: ['cn','eq','nc']} }
				, {name: 'manager_name', index: 'manager_name', width:80, sortable: false, searchoptions: {sopt: ['cn','eq','nc']} }
				, {name: 'manager_phone', index: 'manager_phone', width: 80, align: "left", formatter: fn_formatPhone, editable: false, sortable: false, searchoptions: {sopt: ['cn','eq','nc']} }
				, {name: 'declaration_cnt', index: 'declaration_cnt', width: 270, search: false }
 				, {name: 'manager_use_status', index: 'manager_use_status', stype: 'select', formatter: 'select', editable: true, edittype: 'select', editoptions: {value: use_status_opts}, searchoptions: {sopt: ['eq'], value: search_use_status_opts}, sortable: false }
				, {name: 'manager_induce_memo', index: 'manager_induce_memo', width:200, editable: true, edittype: "textarea", search: true, searchoptions: {sopt: ['cn','eq','nc']}, sortable: false }
				, {name: 'last_declaration_date', index: 'last_declaration_date', align: "left", width: 200, sortable: true, search: false }	
				, {name: 'manager_seq', index: 'manager_seq', hidden: true}
			],
         	//공통기능 체크박스만 선택기능
         	//beforeSelectRow: selectRowid, 
         	beforeSelectRow: function(row_id, e) {
         		if($("#cb_list_" + row_id + "_t").is(":checked") == true) {
         			if($("#jqg_list_" + row_id).is(":checked") == false) {
         				$("#cb_list_" + row_id + "_t").trigger("click");
         			}
         		}else {
         			if($("#jqg_list_" + row_id).is(":checked") == true) {
         				$("#cb_list_" + row_id + "_t").trigger("click");
         			}
         		}
         		
         		if($("#jqg_list_" + row_id).is(":checked") == false) {
         			if($("#cb_list_" + row_id + "_t").is(":checked") == true) {
         				//$("#cb_list_" + row_id + "_t").attr("disabled", false);
         				
         				var subGrid = $("#list_" + row_id + "_t").jqGrid("getGridParam", "selarrrow");
         				
         				for(var i = 0; i < subGrid.length; i++) {
         					//$("#jqg_list_" + row_id + "_t_" + subGrid[i]).attr("disabled", false);
         				}
         				
         				$("#cb_list_" + row_id + "_t").trigger("click");
         			}
         		}
         		
         		return row_id;
         	}, 
         	// row 를 선택 했을때 편집 할 수 있도록 한다.
			onSelectRow: function(id) {
				if ( id && id !== lastsel ) {
					lastsel = id;
				}
			},
			onSelectAll: function(row_id, status, e) {
				if(status == true) {
					for(var i = 0; i < row_id.length; i++) {
						if($("#cb_list_" + row_id[i] + "_t").is(":checked") == false) {
							$("#cb_list_" + row_id[i] + "_t").trigger("click");
						}
					}	
				}else {
					for(var i = 0; i < row_id.length; i++) {
						if($("#cb_list_" + row_id[i] + "_t").is(":checked") == true) {
							$("#cb_list_" + row_id[i] + "_t").trigger("click");
						}
					}
				}
			},
        	// cell을 클릭 시
			onCellSelect: function(rowid, index, contents, event) {
				var rowData = $('#list').jqGrid('getRowData', rowid);
				
				if(authLevel == '1' && companySeq != rowData.manager_company_seq) {
					$("#list").setColProp("manager_induce_memo", {editable: false});
					$("#list").setColProp("manager_use_status", {editable: false});
				}else {
					$("#list").setColProp("manager_induce_memo", {editable: true});
					$("#list").setColProp("manager_use_status", {editable: true});
				}
				
				lastsel = rowid;
			},
			beforeSubmitCell: function(rowid, cellname, value) {// submit 전
				var list = $("#list").jqGrid('getRowData', rowid);
			
				if(cellname == 'manager_use_status') {
					return {
						manager_seq : list.manager_seq,
						manager_use_status : list.manager_use_status,
						cellname : cellname
					};
				}else {
					return {
						manager_induce_seq : rowid,
						manager_induce_memo : list.manager_induce_memo,
						cellname : cellname
					};				
				}
			},
        	// Grid 로드 완료 후
			loadComplete: function(data) {
				isGridLoad = true;
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
			},
       		subGrid: true,
 	   		subGridOptions: {
	      		// load the subgrid data only once
	   			// and the just show/hide
				"reloadOnExpand" : true,			//열고 닫고 할때 한번만 ajax 실행
	   			// select the row when the expand column is clicked
	   			"selectOnExpand" : false,		// 클릭할때 행 선택
	   		}, 
	   		subGridRowExpanded: function(subgrid_id, row_id) {
		   		var subgrid_table_id, pager_id;
	   			subgrid_table_id = subgrid_id+"_t";
	   			pager_id = "p_"+subgrid_table_id;
	   			var desired_width = $("#list").width();
	            desired_width -= 90;
	   			//페이지 없애기
	   			$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'>");
	   			$("#"+subgrid_table_id).jqGrid({
	   				url: "/admin/managerInduce/getManagerSubList",
	            	datatype: "json",                              
	            	mtype: "POST",
	            	postData: {
		            	srh_use_yn: 1,
	                	page: 1,
	                	manager_induce_seq : row_id
	            	},
	            	cellEdit: true ,
	            	cellSubmit: "remote",
	            	multiselect	: true,
	             	multiboxonly : true,
	            	cellurl: "/admin/setOrderWorkInfo",              // 추가, 수정, 삭제 url
	   				colNames: ['순번', '신고경로', '신고자 지점 순번', '신고자 지점', '신고자', '신고일시', '신고내용'],
	   				colModel: [
	   					
	   					{name: "manager_sub_seq", index: "manaer_sub_seq", width: 10, key: true, hidden: ${sessionScope.ADMIN_SESSION.auth_level eq '0' ? false : true }, sortable: false },
	   					{name: "flag", index: "flag", width: 10, align: "left", sortable: false
	   						, formatter: function(value, row) {
	   							if(value == '0') {
	   								return '도출유도 매니저';
	   							}else if(value == '1') {
	   								return '또가요 없음';
	   							}else if(value == '2') {
	   								return '신규현장';
	   							}else if(value == '3') {
	   								return '신규구직자';
	   							}else {
	   								return '근로이력';
	   							}
	   						}
	   					},
	   					{name: "worker_company_seq", index: "worker_company_seq", hidden: true},
	   					{name: "worker_company_name", index: "worker_company_name", width: 10, align: "center", sortable: false },
	   					{name: "worker_name", index: "worker_name", width: 10, align: "left", sortable: false },
	   					{name: "reg_date", index: "reg_date", width: 10,	align: "left", sortable: false },
	   					{name: "manager_sub_memo", index: "manager_sub_memo", width: 40, sortable: false
	   						, cellattr : function(){
	   							//문자길이가 셀 크기보다 길 경우 자동 줄바꿈
	   							return "style='word-wrap: break-word; white-space: normal; padding: 0px;'";
	   						}
	   					}
					],
	   		   		/* 페이지 없애기
	   					rowNum:20,
	   		   			pager: pager_id,
	   		   	 	*/
	   		  		rownumbers: true,                                 	// 그리드 번호 표시
	         		rownumWidth: 50,                                   // 번호 표시 너비 (px)
	   		 		width: desired_width,
	   		   		sortname: 'reg_date',
	   		    	sortorder: "DESC",
	   		    	height: '100%',
	   		 		beforeSubmitCell: function(rowid, cellName, cellValue){
	   		 			var	map = { 
	   		 				manager_sub_seq : rowid 
	   		 			};
	   		 		
		   		 		return map;
	   		 		},
	   		 		onSelectAll: function(rowId, status, e) {
	   		 			if($("#jqg_list_" + row_id).is(":checked") == true) {
	   		 				//$("#cb_list_" + row_id + "_t").attr("disabled", true);
	   		 				
	   		 				for(var i = 0; i < rowId.length; i++) {
	   		 					//$("#jqg_list_" + row_id + "_t_" + rowId[i]).attr("disabled", true);
	   		 				}
	   		 			}
	   		 			
	   		 			if(status == true) {
		   		 			//$("#cb_list_" + row_id + "_t").attr("disabled", true);
	   		 				
	   		 				for(var i = 0; i < rowId.length; i++) {
	   		 					//$("#jqg_list_" + row_id + "_t_" + rowId[i]).attr("disabled", true);
	   		 				}
	   		 				
	   		 				if($("#jqg_list_" + row_id).is(":checked") == false) { 
	   		 					$("#jqg_list_" + row_id).trigger("click");
	   		 				}
	   		 			}
	   		 		},
	   		 		beforeSelectRow : function(rowid, e) {
	   		 			
	   		 		},
	   		 		onSelectRow : function(rowid, status) {
	   		 			var thisGridLen = $($("#"+subgrid_table_id)[0]).find("tbody")[0].children.length - 1;
	   		 			var listLen = $("#"+subgrid_table_id).jqGrid('getGridParam', "selarrrow").length;
	   		 			
	   		 			if(thisGridLen == listLen) {
	   		 				$("#cb_list_" + row_id + "_t").trigger("click");
	   		 			}else {
		   		 			if($("#jqg_list_" + row_id).is(":checked") == true) { 
	   		 					$("#jqg_list_" + row_id).trigger("click");
	   		 				}	 				
	   		 			}
	   		 			
	   		 			if(status) {
	   		 				parentGridArr.push(row_id);
	   		 				subGridArr.push(rowid);
	   		 			}else {
	   		 				for(var i = 0; i < subGridArr.length; i++) {
	   		 					if(subGridArr[i] == rowid) {
	   		 						var idx = subGridArr.indexOf(rowid);
	   		 						
	   		 						if(idx > -1) {
	   		 							subGridArr.splice(idx, 1);
	   		 							parentGridArr.splice(idx, 1);
	   		 						}
	   		 					}
	   		 				}
	   		 			}
	   		 		},
	   		 		loadComplete: function(jsondata) {
						if($("#jqg_list_" + row_id).is(":checked") == true) {
							$("#cb_list_" + row_id + "_t").trigger("click");
							//$("#cb_list_" + row_id + "_t").attr("disabled", true);
							
							for(var i = 0; i < jsondata.rows.length; i++) {
								$("#jqg_list_" + row_id + "_t_" + jsondata.rows[i].manager_sub_seq).trigger("click");
								//$("#jqg_list_" + row_id + "_t_" + jsondata.rows[i].manager_sub_seq).attr("disabled", true);
							}
						}else {
							if($("#cb_list_" + row_id + "_t").is(":checked")) {
								$("#cb_list_" + row_id + "_t").trigger("click");
								
								for(var i = 0; i < jsondata.rows.length; i++) {
									$("#jqg_list_" + row_id + "_t_" + jsondata.rows[i].manager_sub_seq).trigger("click");
								}
							}
						}	   		 			
	   		 		}
	   			});
	   			
	   			$("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false});
	   		}
		});

		$("#list").jqGrid('navGrid', "#paging", {edit: false, add: false, del: false, search: false, refresh: false, position: 'left'});
		$("#list").jqGrid('filterToolbar', {searchOperators : true});
		
		$("#btnSrh").click( function() {
	    	$("#list").setGridParam({
				postData: {
					start_reg_date: $("#start_reg_date").val(),
		        	end_reg_date: $("#end_reg_date").val()
		        }
			}).trigger("reloadGrid");

	    	lastsel = -1;
	  	});
		
		$("#btnDel").click(function() {
			var mainGridArr = $("#list").jqGrid("getGridParam", "selarrrow");
			if( mainGridArr.length == 0 && subGridArr.length == 0 ){
				alert("항목을 선택해주세요.");
				return false;
			}
			
			if(authLevel > 0) {
				if(mainGridArr.length > 0) {
					for(var i = 0; i < mainGridArr.length; i++) {
						var rowData = $('#list').jqGrid('getRowData', mainGridArr[i]);
						
						if(rowData.manager_company_seq != companySeq) {
							alert("삭제권한자가 아닙니다.");
							
							return false;
						}
					}
				}
				
				if(subGridArr.length > 0) {
					for(var i = 0; i < subGridArr.length; i++) {
						var rowData = $('#list').jqGrid('getRowData', parentGridArr[i]); 
						
						if(rowData.manager_company_seq != companySeq) {
							alert("삭제권한자가 아닙니다.");
							
							return false;
						}
					}
				}
			}
			
			if(!confirm("신고내역을 삭제하시겠습니까?")) {
				return false;
			}
			
			$.ajax({
				url: "/admin/managerInduce/deleteManagerInduce"
				, type: "POST"
				, dataType: "json"
				, data: { 
					mainGridArr	: mainGridArr,
					subGridArr : subGridArr
				}
	    		, traditional : true
				, success: function (data) {
					if(data.result == '0000') {
						// reload Gird		
						$("#list").trigger("reloadGrid");
						subGridArr = new Array();
					}else{
						alert("시스템 오류입니다.");
					}
				}, beforeSend: function(xhr) {
					$(".wrap-loading").show();
					xhr.setRequestHeader("AJAX", true);
				}, error: function(e) {
					$(".wrap-loading").hide();
					if ( data.status == "901" ) {
						location.href = "/admin/login";
					} else {
						alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
					}
				}, complete: function(e) {
					$(".wrap-loading").hide();
				}
			});
		});
	});

	function fn_formatPhone(cellvalue, options, rowObject) {
		var pattern = /^(\d{2,3})(\d{3,4})(\d{4})$/;
		
		if(!cellvalue) {
			cellvalue = '';
		}
		
		var result = cellvalue.replace(pattern, '$1-$2-$3');

		if(authLevel != '0') {
			if(rowObject.manager_company_seq != companySeq) {
		        result = cellvalue.replace(pattern, '$1-$2-****');
			}
		}
		
		return result;
	}
//]]>
</script>
<article>
	<div class="inputUI_simple">
			<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
				<caption>등록일시, 상태, 직접검색 영역</caption>
				<colgroup>
					<col width="110px">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">등록일시</th>
						<td>
							<p class="floatL">
								<input type="text" id="start_reg_date" name="start_reg_date" class="inp-field wid100 datepicker" /> 
								<span class="dateTxt">~</span>
	              				<input type="text" id="end_reg_date" name="end_reg_date" class="inp-field wid100 datepicker" />
							</p>
							<div class="inputUI_simple">
								<span class="contGp btnCalendar"> 
									<input type="radio" id="day_type_1" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'all'); $('#btnSrh').trigger('click');" checked="checked">
									<label for="day_type_1" class="label-radio day_type on">전체</label>
									<input type="radio" id="day_type_2" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'today' ); $('#btnSrh').trigger('click');">
									<label for="day_type_2" class="label-radio day_type">오늘</label> 
									<input type="radio" id="day_type_3" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'week'  ); $('#btnSrh').trigger('click');">
									<label for="day_type_3" class="label-radio day_type">1주일</label> 
									<input type="radio" id="day_type_4" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', 'month' ); $('#btnSrh').trigger('click');">
									<label for="day_type_4" class="label-radio day_type">1개월</label> 
									<input type="radio" id="day_type_5" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '3month'); $('#btnSrh').trigger('click');">
									<label for="day_type_5" class="label-radio day_type">3개월</label> 
									<input type="radio" id="day_type_6" name="day_type" class="inputJo" onclick="setDayType('start_reg_date', 'end_reg_date', '6month'); $('#btnSrh').trigger('click');">
									<label for="day_type_6" class="label-radio day_type">6개월</label>
								</span> 
							</div>
							<div class="btn-module floatL mglS">
								<a href="#none" id="btnSrh" class="search">검색</a>
							</div>
						</td>
					</tr>
			</tbody></table>
		</div>
</article>

<div class="btn-module mgtS mgbS">
	<div class="leftGroup">
        <a href="#none" id="btnHelp" class="btnStyle05">신고경로</a>
        <a href="#none" id="btnDel" class="btnStyle01">삭제</a>
	</div>
</div>

<div id="helpPanel" class="helpPanel" style="height: 180px;">
	<div style="width: 750px; float: left;">
		<div style="font-weight: bold; float: left; margin-right: 15px; margin-top: 6px;">신고경로</div>
		<table class="helpTable2" style="width: 650px;">
			<tbody>
		        <tr>
	        		<th scope="row">도출유도 매니저</th>
	          		<td>
	            		이미 도출관리에 등록된 매니저를 신고
	          		</td>
	        	</tr>
	        	<tr>
		          	<th scope="row">또가요 없음</th>
		         	<td>
		         		최근 출역한 작업완료 10번 중에서 또가요가 한번도 없는 구직자가 매니저를 신고
		         	</td>
	        	</tr>
	        	<tr>
	          		<th scope="row">신규현장</th>
	          		<td>
	            		처음 출역한 현장의 매니저를 신고
	          		</td>
	        	</tr>
	        	<tr>
	          		<th scope="row">신규구직자</th>
	          		<td>
	          			1~5번째 출역인 신규구직자가 신고
	          		</td>
	        	</tr>
	        	<tr>
	          		<th scope="row">근로이력</th>
	          		<td>
	          			완료한 출역을 근로이력에서 신고
	          		</td>
	        	</tr>
	        </tbody>
		</table>
	</div>
	<div style="width: 750px; float: left;">
		<div style="font-weight: bold; float:left; margin-right: 15px; margin-top: 6px;">계정관리</div>
		<table class="helpTable2" style="width: 650px;">
			<tbody>
		        <tr>
	          		<td>
	            		현장매니저관리와 본사매니저 관리의 '계정관리'와 연동됩니다.
	          		</td>
	        	</tr>
	        </tbody>
		</table>
	</div>
</div>

<table id="list"></table>
<div id="paging"></div>