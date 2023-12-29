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
	var companySeq = '${ sessionScope.ADMIN_SESSION.company_seq }';
	var authLevel = '${sessionScope.ADMIN_SESSION.auth_level }';
	
	$(function() {
		// jqGrid 생성
		$("#list").jqGrid({
			url : "/admin/inquiry/selectInquiryList",
            datatype : "json",                              
            mtype : "POST",
            postData: {
            	srh_use_yn : 1,
                page : 1
            },
            sortname : "inquiry_seq",							//sort 하는 방법 jjh 수정
           	sortorder : "desc",
            height : jqHeight +50,                                 // 그리드 높이
			rowList : [100, 200, 500, 1000],                         // 한페이지에 몇개씩 보여 줄건지?
            rowNum : 200,
            pager : '#paging',                            // 네비게이션 도구를 보여줄 div요소
          	viewrecords : true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무
         	multiselect	: true,
         	multiboxonly : true,
            caption	: "제휴문의 내역",                   			// 그리드타이틀
          	rownumbers : true,                                 	// 그리드 번호 표시
         	rownumWidth : 40,                                   // 번호 표시 너비 (px)
            cellEdit : true ,							  //처리중일때 만 수정 할 수 있도록 한다.
          	cellsubmit : "remote" ,
          	cellurl	: "/admin/inquiry/updateInquiry",              // 추가, 수정, 삭제 url
          	width : "1600",
            jsonReader: {
                    id: "inquiry_seq",
                    repeatitems: false,
           			subgrid: {
           		      root: "rows", 
           		      repeatitems: true, 
           		      cell: "cell"
           		   } 
            }, 
            // 컬럼명
            colNames: ['제휴문의 순번', '회사명', '담당자 명', '연락처', '이메일', '문의 내용', '메모', '작성일'],
            // 컬럼별 속성
            colModel: [
				{name: 'inquiry_seq', index: 'inquiry_seq', key: true, width: 80, search: false, hidden:${sessionScope.ADMIN_SESSION.auth_level eq 0 ? false : true}, sortable: false }
				, {name: 'company_name', index: 'company_name', width: 80, align: "center", search: false, sortable: false }
				, {name: 'work_manager_name', index: 'work_manager_name', width:80, sortable: false, search: false }
				, {name: 'work_manager_phone', index: 'work_manager_phone', width: 80, align: "left", editable: false, sortable: false, search: false }
				, {name: 'work_manager_mail', index: 'work_manager_mail', width: 100, search: false }
 				, {name: 'inquiry_content', index: 'inquiry_content', width: 350, sortable: false, search: false }
				, {name: 'inquiry_memo', index: 'inquiry_memo', width: 100, editable: true, edittype: "textarea", search: false, sortable: false }
				, {name: 'reg_date', index: 'reg_date', align: "left", width: 80, sortable: true, search: false }	
			],
         	//공통기능 체크박스만 선택기능
         	//beforeSelectRow: selectRowid, 
         	beforeSelectRow: function(row_id, e) {
         	}, 
         	// row 를 선택 했을때 편집 할 수 있도록 한다.
			onSelectRow: function(id) {
				if ( id && id !== lastsel ) {
					lastsel = id;
				}
			},
			onSelectAll: function(row_id, status, e) {
			},
        	// cell을 클릭 시
			onCellSelect: function(rowid, index, contents, event) {
				lastsel = rowid;
			},
			beforeSubmitCell: function(rowid, cellname, value) {// submit 전
				return {
					inquiry_seq : $("#list").jqGrid('getRowData', rowid).inquiry_seq
				};
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
			}
		});

		$("#list").jqGrid('navGrid', "#paging", {edit: false, add: false, del: false, search: false, refresh: false, position: 'left'});
		$("#list").jqGrid('filterToolbar', {searchOperators : true});
		
		$("#btnDel").click(function() {
			var mainGridArr = $("#list").jqGrid("getGridParam", "selarrrow");
			if( mainGridArr.length == 0) {
				alert("항목을 선택해주세요.");
				
				return false;
			}
			
			if(!confirm("문의내역을 삭제하시겠습니까?")) {
				return false;
			}
			
			$.ajax({
				url: "/admin/inquiry/deleteInquiry"
				, type: "POST"
				, dataType: "json"
				, data: { 
					arr_inquiry_seq	: mainGridArr
				}
	    		, traditional : true
				, success: function (data) {
					if(data.code == '0000') {
						// reload Gird		
						$("#list").trigger("reloadGrid");
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
//]]>
</script>
<div class="btn-module mgtS mgbS">
	<div class="leftGroup">
        <a href="#none" id="btnDel" class="btnStyle01">삭제</a>
	</div>
</div>

<table id="list"></table>
<div id="paging"></div>