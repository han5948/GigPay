<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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

	isSelect = false;
	var status_opts = "1:이용승인;2:이용제한;3:적립금부족";
	var authLevel = ${sessionScope.ADMIN_SESSION.auth_level };
	
	$(function() {
  		var lastsel;            //편집 sel

  		// 사용유무 - 0:미사용 1:사용
  		var use_opts = "1:사용;0:미사용";

  		// jqGrid 생성
  		$("#list").jqGrid({
			url: "/admin/getCompanyList",
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
            sortname: 'company_seq',
           	sortorder: "asc",
            rowList: [100, 200, 500],                         // 한페이지에 몇개씩 보여 줄건지?
            height: jqHeight,                               // 그리드 높이
            width: jqWidth,
         	scrollerbar: true,       
//           autowidth: true,
            rowNum: 100,
            pager: '#paging',                            // 네비게이션 도구를 보여줄 div요소
         	viewrecords: true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무

         	multiselect: true,
        	multiboxonly: true,
            caption: "지점 목록",                          // 그리드타이틀

          	rownumbers: true,                                 // 그리드 번호 표시
         	rownumWidth: 40,                                   // 번호 표시 너비 (px)

            cellEdit: true ,
          	cellsubmit: "remote" ,
            cellurl: "/admin/setCompanyInfo",              // 추가, 수정, 삭제 url

          	jsonReader: {
				id: "company_seq",
           		repeatitems: false
			},

            // 컬럼명
            colNames: ['지점 순번', '사업자등록번호', '지점명', '사업자상호', '대표자', '업태', '업종'
            	, '등록(자격)번호','계좌정보','은행명','계좌번호','예금주','전화번호', 'ARS 번호', '최소적립금', '시스템요금(원)', '러닝로얄티(%)'
            	, '구인제공 정산수수료', '구직제공 정산수수료', 'AI배정사용', 'AI근로자배정', '팩스', '폰번호', '등록', '이메일'
            	, '주소', '특징', '추천', '메모', '스캔', '서명','직업소개소등록증', '사업자등록증'
            	, '통장사본','기타','지점거래', '계정관리', '상태', '등록일시', '등록자'],

            // 컬럼별 속성
            colModel: [
				{name: 'company_seq', index: 'company_seq', key:  true, hidden: true},

                {name: 'company_num', index: 'company_num', align: "center", width: 120, editable: true, sortable: true, formatter: formatRegNum, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_name', index: 'company_name', align: "left", width: 120, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'business_name', index: 'business_name', align: "left", width: 120, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_owner', index: 'company_owner', align: "left", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_business', index: 'company_business', align: "left", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_sector', index: 'company_sector', align: "left", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_license_num', index: 'company_license_num', align: "left", width: 120, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_account', index: 'company_account', align: "left", width: 250, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_bank_name', index: 'company_bank_name', align: "left", width: 80, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_bank_account', index: 'company_bank_account', align: "left", width: 150, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_bank_account_holder', index: 'company_bank_account_holder', align: "left", width: 100, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_tel', index: 'company_tel', align: "left", width: 120, editable: true, sortable: true, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'ars_phone', index: 'ars_phone', align: "left", width: 120, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'minimum_reserves', index: 'minimum_reserves', align: "left", width: 120, editable: true, sortable: true, editrules: {custom: true, custom_func: validMinimumReserves}, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'system_fee', index: 'system_fee', align: "left", width: 120, editable: true, sortable: true, editrules: {custom: true, custom_func: validSystemFee}, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'running_royalty', index: 'running_royalty', align: "left", width: 120, editable: true, editrules: {custom: true, custom_func: validRoyalty}, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'work_fee_rate', index: 'worker_fee_rate', align: "left", width: 120, editable: true, sortable: true },
                {name: 'worker_fee_rate', index: 'worker_fee_rate', align: "left", width: 120, editable: true, sortable: true },
                {name: 'auto_company_use_yn', index: 'auto_company_use_yn', align: "center", width:  60, search: false, editable:  authLevel == 0 ? true : false, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.auto_company_use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
					, width: 100
				},
				{name: 'auto_worker_use_yn', index: 'auto_worker_use_yn', align: "center", width:  60, search: false, editable:  authLevel == 0 ? true : false, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.auto_worker_use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
					, width: 100
				},
                {name: 'company_fax', index: 'company_fax', align: "left", width: 120, editable: true, sortable: true, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_phone', index: 'company_phone', align: "left", width: 180, editable: false, sortable: true, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_phone_add', index: 'company_phone', align: "left", width: 90, formatter: fn_addBtn},
                {name: 'company_email', index: 'company_email', align: "left", width: 150, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_addr', index: 'company_addr', align: "left", width: 180, editable: true, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_feature', index: 'company_feature', align: "left", width: 180, editable: true, sortable: true, edittype: "textarea", searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'recommendation', index: 'recommendation', align: "left", width: 180
					, editable: true
					, edittype: "text"
                 	, editrules: {custom: true, custom_func: validRecommendation}, sortable: true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_memo', index: 'company_memo', align: "left", width: 160, editable: true, sortable: true, edittype: "textarea", searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'company_scan_file', index: 'company_scan_file', align: "left", width: 200, search: false, sortable: false, formatter: formatScan},
                {name: 'company_sign_file', index: 'company_scan_file', align: "left", width: 200, search: false, sortable: false, formatter: signScan},
                {name: 'company_license_scan_yn', index: 'company_license_scan_yn', hidden: true },
                {name: 'company_corp_scan_yn', index: 'company_corp_scan_yn', hidden: true },
                {name: 'company_bank_scan_yn', index: 'company_bank_scan_yn', hidden: true },
                {name: 'company_etc_scan_yn', index: 'company_etc_scan_yn', hidden: true },
                 
                {name: 'share_yn', index: 'share_yn', align: "center", width: 60, search: false, editable:  true, sortable: true, edittype: "select", editoptions: {value: use_opts}, formatter: "select"
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.share_yn == '0' ) {
                        	return 'style="color: red; text-align: center; background-color: #FFFFF0"';
						}
					}
				},
				{name: 'company_status', index: 'company_status', align: "center", width: 120, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: status_opts}, formatter: "select"},
                {name: 'use_yn', index: 'use_yn', align: "center", width: 60, search: false, editable: true, sortable: true, edittype: "select", editoptions: {value: use_opts}, formatter: "select"
					, cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
                        if ( rowObject.use_yn == '0' ) {
                        	return 'style="color: red; text-align: center; background-color: #FFFFF0"';
						}
					}
				},
                {name: 'reg_date', index: 'reg_date', align: "center", width: 100, search: false, editable: false, sortable: true, formatoptions: {newformat: "y/m/d H:i"}},
                {name: 'reg_admin', index: 'reg_admin', align: "left", width: 60, search: false, editable: false, sortable: true}
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
			beforeSaveCell: function(rowid, cellname, value){
				if ( cellname == "system_fee" ){
            		var RegNotNum  = /[^0-9]/g;
            		// delete not number
            		var systemFee = value.replace(RegNotNum,'');
            		return systemFee;
            	}
				
				return value;
			},
    		// submit 전
    		beforeSubmitCell: function(rowid, cellname, value) {
            	if ( cellname == "use_yn" && value == "0" ) {
                	$("#"+rowid).hide();
				}

                return {
                	company_seq: rowid,
                    company_license_scan_yn	: $("#list").jqGrid('getRowData', rowid).company_license_scan_yn,
                    company_corp_scan_yn : $("#list").jqGrid('getRowData', rowid).company_corp_scan_yn,
                    company_bank_scan_yn : $("#list").jqGrid('getRowData', rowid).company_bank_scan_yn,
                    company_etc_scan_yn : $("#list").jqGrid('getRowData', rowid).company_etc_scan_yn
				};
			},
        	// Grid 로드 완료 후
        	loadComplete: function(data) {
            	//총 개수 표시
//                        $("#totalRecords").html(numberWithCommas( $("#list").getGridParam("records") ));

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
/*
  // jqgrid caption 클릭 시 접기/펼치기 기능
  $("div.ui-jqgrid-titlebar").click(function () {
    $("div.ui-jqgrid-titlebar a").trigger("click");
  });
*/
  		// 행 추가
  		$("#btnAdd").click( function() {
    		var params ="";

    		$.ajax({
            	type: "POST",
             	url: "/admin/setCompanyCell/",
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
    
    		var params = "sel_company_seq=" + recs;
    		var rows = recs.length;

    		$.ajax({
	            type: "POST",
             	url: "/admin/removeCompanyInfo",
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
    		if ( $("#srh_type option:selected").val() != "" && $("#srh_text").val() == "" ) {
      			alert('검색어를 입력하세요.');
      			$("#srh_text").focus();
		
      			return false;
    		}

    		$("#list").setGridParam({
//            page: pageNum,
          		rowNum: 15,
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
  
		//스캔파일 첨부
  		function formatScan(cellvalue, options, rowObject) {
    		var str = "";

    		str += "<div class=\"bt_wrap\">";
    		// '직업소개소등록증',  스캔 여부 - 0:미스캔 1:스캔'
    		str += "<div id=\"LICENSE_"+ rowObject.company_seq +"\" class=\"bt"+ (rowObject.company_license_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.company_license_scan_yn == "1" ? "javascript:popupImage('LICENSE','"+ rowObject.company_seq+"');" : "#none")  +" \"> 직 </a></div>";
    		// '사업자등록증',  스캔 여부 - 0:미스캔 1:스캔'
    		str += "<div id=\"CORP_"+ rowObject.company_seq +"\" class=\"bt"+ (rowObject.company_corp_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.company_corp_scan_yn == "1" ? "javascript:popupImage('CORP','"+ rowObject.company_seq+"');" : "#none")  +" \"> 사 </a></div>";
    		// '통장사본', 스캔 여부 - 0:미스캔 1:스캔'
    		str += "<div id=\"BANK_"+ rowObject.company_seq +"\" class=\"bt"+ (rowObject.company_bank_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.company_bank_scan_yn == "1" ? "javascript:popupImage('BANK','"+ rowObject.company_seq+"');" : "#none")  +" \"> 은 </a></div>";
 			// '기타' 스캔 여부 - 0:미스캔 1:스캔'
    		str += "<div id=\"ETC_"+ rowObject.company_seq +"\" class=\"bt"+ (rowObject.company_etc_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.company_etc_scan_yn == "1" ? "javascript:popupImage('ETC','"+ rowObject.company_seq+"');" : "#none")  +" \"> 기 </a></div>";
		   
    		str += "<div class=\"bt_t1\"><a href=\"JavaScript:scanAdd('"+ rowObject.company_seq +"', '"+ options.rowId +"'); \"> + </a></div>";
    		str += "</div>";
		 
    		return str;
  		}
		
		// 서명 파일 첨부
		function signScan(cellvalue, options, rowObject) {
			var str = "";
			
			str += "<div class=\"bt_wrap\">";
			str += "<div id=\"SIGN_"+ rowObject.company_seq +"\" class=\"bt"+ (rowObject.company_sign_scan_yn == "1" ? "_on" : "") +"\"><a href=\" "+ (rowObject.company_sign_scan_yn == "1" ? "javascript:popupImage('SIGN','"+ rowObject.company_seq+"');" : "#none")  +" \"> 서명 </a></div>";
			str += "<div class=\"bt_t1\"><a href=\"JavaScript:signAdd('"+ rowObject.company_seq +"', '"+ options.rowId +"'); \"> + </a></div>";
			str += "</div>";
			
			return str;
		}
	});


	//스캔파일 등록 layer popup
	function scanAdd(company_seq, rowId) {
  		$("#frmPopup input:hidden[name='service_seq']").val(company_seq);

  		// Layer popup
  		openIrPopup('popup-layer');
	}
	
	// 서명 파일 등록 layer popup
	function signAdd(company_seq, rowId) {
		$("#frmSignPopup input:hidden[name='service_seq']").val(company_seq);

  		// Layer popup
  		openIrPopup('popup-layer2');
	}

	//주민 통장 등 이미지 팝업
	function popupImage(serviceCode, serviceSeq) {
  		var param = "service_type=COMPANY&service_code="+ serviceCode +"&service_seq="+ serviceSeq;

  		window.open("/admin/popup/imagePopup?"+ param, 'image', 'width=800, height=600, toolbar=no, menubar=no, scrollbars=no, resizable=yes');
	}

	function fn_addBtn(cellvalue, options, rowObject) {
		var str = "";
	
		str += "<div class=\"bt_wrap\">";
		str += "	<div style='width: 40px;' id=\"div_phone_" + rowObject.company_seq + "\" class=\"bt_on\"><a onclick=\"javascript:fn_phoneAdd(" + rowObject.company_seq + "); return false;\">등록</a></div>";
	
		return str;
	}

	function fn_phoneAdd(company_seq) {
		$("#phone_company_seq").val(company_seq);
	
		var w = 650;
		var h = 200;
 // Fixes dual-screen position                         Most browsers      Firefox
    	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
    	var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;
    	var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    	var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
    	var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    	var top = ((height / 2) - (h / 2)) + dualScreenTop;
    //var newWindow = window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
    // Puts focus on the newWindow
    
		$("#phoneAddFrm").one("submit", function() {
			var option = 'width=' + w + ', height=' + h + ', top=' + top + ', left=' + left + ', resizable=no, status=no, menubar=no, toolbar=no, scrollbars=no, location=no';
			
			window.open('','pop_target',option);
			 
			this.action = '/admin/phoneAdd';
			this.method = 'POST';
			this.target = 'pop_target';
		}).trigger("submit");
	}

	function validMinimumReserves(value){
		if(value == ""){
			return [false, "값을 입력해주세요."]; 
		}
		var _regExp = /^[+]?\d*?$/;
		if(!_regExp.test(value)){
			return [false, "] 양수만 입력가능합니다."];
		}
		
		return [true, ""];
	}
	
	function validSystemFee(value){
		if(value == ""){
			return [false, "값을 입력해주세요."]; 
		}
		return [true, ""];
	}
	function validRoyalty(value){
		if(value == ""){
			return [false, "값을 입력해주세요."];
		};
		
		if(value.length > 10){
			return [false, "] 값이 너무 큽니다."];
		}
		
		if(/^(\d*)[\.]?(\d{1,3})?$/g.test(value)){
			return [true, ""];
		};
		return [false, "] 올바른 포멧이 아닙니다."];
	}
	function validRecommendation(value, cellTitle, valref){
		var cnt = 0;
		
		if( value != "" ){
			$.ajax({
		    	type: "POST",
		        url: "/admin/selectRecommendationCount",
		        data: {
		        	recommendation : value
		        },
		    	dataType: 'json',
		    	async: false,
		     	success: function(data) {
		     		cnt = data.count;
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
		
		if( cnt > 0 ){
			return [false, "] 중복된 값입니다."];
		}else{
			return [true, ""];	
		}
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
      		<table class="bd-form s-form" summary="등록일시, 상태, 직접검색 영역 입니다.">
        		<caption>등록일시, 상태, 직접검색 영역</caption>
        		<colgroup>
          			<col width="120px" />
          			<col width="270px" />
          			<col width="120px" />
          			<col width="" />
        		</colgroup>
        		<tr>
          			<th scope="row">등록일시</th>
          			<td colspan="3">
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
            				<span class="tipArea colorN02">* 직접 기입 시, ‘2017-06-28’ 형식으로 입력</span>
            			</div>
          			</td>
        		</tr>
        		<tr>
          			<th scope="row">상태</th>
          			<td>
            			<input type="radio" id="srh_use_yn_1" name="srh_use_yn" class="inputJo" value=""  /><label for="srh_use_yn_1" class="label-radio">전체</label>
            			<input type="radio" id="srh_use_yn_2" name="srh_use_yn" class="inputJo" value="1" checked="checked" /><label for="srh_use_yn_2" class="label-radio">사용</label>
            			<input type="radio" id="srh_use_yn_3" name="srh_use_yn" class="inputJo" value="0" /><label for="srh_use_yn_3" class="label-radio">미사용</label>
          			</td>
          			<th scope="row" class="linelY pdlM">직접검색</th>
         				<td>
           				<div class="select-inner">
           					<select id="srh_type" name="srh_type" class="styled02 floatL wid138" onChange="$('#srh_text').focus();">
             						<option value="" selected="selected">선택</option>
             						<option value="">전체</option>
             						<option value="company_name">지점</option>
             						<option value="company_owner">대표자명</option>
             						<option value="company_num">사업자등록번호</option>
             						<option value="company_email">이메일</option>
             						<option value="company_phone">폰번호</option>
             						<option value="company_tel">전화번호</option>
             						<option value="company_addr">주소</option>
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
			<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
        		<a href="#none" id="btnAdd" class="btnStyle01">추가</a>
        		<a href="#none" id="btnDel" class="btnStyle01">삭제</a>
			</c:if>
        	<a href="#none" id="btnRel" class="btnStyle05">새로고침</a>
      	</div>
    </div>

    <table id="list"></table>
    <div id="paging"></div>

</form>
<div id="opt_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b;"></div>


<!-- 팝업 : 스캔 첨부파일 등록 -->
<div id="popup-layer">
	<header class="header">
		<h1 class="tit">스캔파일 첨부</h1>
	    <a href="#none" class="close" onclick="javascript:closeIrPopup('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
	</header>
	<form id="frmPopup" name="frmPopup" action="/admin/companyUploadFile" method="post" enctype="multipart/form-data">
		<input type="hidden" id="service_seq" name="service_seq" />
		<section>
			<div class="cont-box">
		    	<article>
		      		<table class="bd-form inputUI_simple" summary="첨부파일 등록 영역입니다.">
		      			<caption>직업소개소등록증,사업자등록증,통장사본</caption>
		      			<colgroup>
		        			<col width="150px" />
		        			<col width="450px" />
		      			</colgroup>
		      			<tbody>
		        			<tr>
		          				<th>직업소개소등록증</th>
		          				<td>
		            				<div class="btn-module filebox floatL">
		              					<input type="text" id="text_license" name="text_license" class="upload-name wid250" disabled="disabled">
		              					<label for="file_license">파일 첨부</label>
		              					<input type="file" id="file_license" name="file_license" class="upload-hidden">
		            				</div>
		          				</td>
		        			</tr>
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
		          				<th>통장사본</th>
		          				<td>
		            				<div class="btn-module filebox floatL">
		              					<input type="text" id="text_bank" name="text_bank" class="upload-name wid250" disabled="disabled">
		              					<label for="file_bank">파일 첨부</label>
		              					<input type="file" id="file_bank" name="file_bank" class="upload-hidden">
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
	<form id="phoneAddFrm">
		<input type="hidden" id="phone_company_seq" name="phone_company_seq" />
		<input type="hidden" id="page_flag" name="page_flag" value="C" />
	</form>
</div>
<div id="popup-layer2">
	<header class="header">
		<h1 class="tit">서명 스캔파일 첨부</h1>
	    <a href="#none" class="close" onclick="javascript:closeIrPopup('popup-layer2');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
	</header>
	<form id="frmSignPopup" name="frmSignPopup" action="/admin/companyUploadFile" method="post" enctype="multipart/form-data">
		<input type="hidden" id="sign_service_seq" name="service_seq" />
		<section>
			<div class="cont-box">
		    	<article>
		      		<table class="bd-form inputUI_simple" summary="첨부파일 등록 영역입니다.">
		      			<caption>서명 스캔파일</caption>
		      			<colgroup>
		        			<col width="150px" />
		        			<col width="450px" />
		      			</colgroup>
		      			<tbody>
		        			<tr>
		          				<th>서명</th>
		          				<td>
		            				<div class="btn-module filebox floatL">
		              					<input type="text" id="text_sign" name="text_sign" class="upload-name wid250" disabled="disabled">
		              					<label for="file_sign">파일 첨부</label>
		              					<input type="file" id="file_sign" name="file_sign" class="upload-hidden">
		            				</div>
		          				</td>
		        			</tr>
		      			</tbody>
		      		</table>
		    	</article>
		
		    	<div class="btn-module mgtS">
		      		<div class="rightGroup"><a href="#popup-layer2" id="btnSignScanAdd" class="btnStyle01">등록</a></div>
		    	</div>
		  	</div>
		</section>
	</form>
</div>
<!-- // 팝업 : 스캔 첨부파일 등록  -->
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
			url: "/admin/companyUploadFile", 
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
	
	$("#btnSignScanAdd").click( function() {
		$('#frmSignPopup').ajaxForm({
			url: "/admin/companyUploadFile", 
	      	enctype: "multipart/form-data", // 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
	 		// 보내기전 validation check
	 		beforeSubmit: function (data, frm, opt) {
				return true;
			},
	      	// submit 이후처리
	      	success: function(result) {
				alert('업로드 하였습니다.');
	
	            $("#text_sign").val("");
	            $("#file_sign").val("");
	            
	            $(".wrap-loading").hide();
	            
	            $("#list").trigger("reloadGrid");
			} ,
	   		beforeSend: function(xhr) {
				xhr.setRequestHeader("AJAX", true);
				
				$(".wrap-loading").show();
			},
	        error: function(e) {
	        	if ( data.status == "901" ) {
	            	location.href = "/admin/login";
				} else {
	            	alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
	            	
	            	$(".wrap-loading").show();
				}
			}
		});
	
		$('#frmSignPopup').submit();
	
		closeIrPopup('popup-layer2');
	});
//]]>
</script>
