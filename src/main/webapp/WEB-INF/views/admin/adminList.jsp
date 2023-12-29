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
function fn_phoneAddNum(admin_seq, company_seq) {
	if(company_seq == '${sessionScope.ADMIN_SESSION.company_seq}' && '${sessionScope.ADMIN_SESSION.auth_level}' != '0') {
		return false;
	}
	
	$("#phone_admin_seq").val(admin_seq);
	var w = 650;
	var h = 300;
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

$(function() {
  var lastsel;            //편집 sel

  // 사용유무 - 0:미사용 1:사용
  var use_opts = "1:사용;0:미사용";

  // 권한 - 0:전체최고관리자; 1:회사최고관리자; 2: 회사관리자3:상담사 4:메니저
  var auth_opts = "0:슈퍼;1:관리자;2:직원;3:상담사;4:매니저";

  // jqGrid 생성
  $("#list").jqGrid({
	  		url: "/admin/getAdminList",
			datatype: "json",                               // 로컬그리드이용
			mtype: "POST",
			postData: {
//          	start_reg_date: getDateString(new Date(now.getFullYear(), now.getMonth() - 1, now.getDate())),
//            	end_reg_date: toDay,
				srh_use_yn: 1,
				page: 1
			},

			sortname: "a.reg_date desc, c.company_name asc, a.auth_level asc, a.admin_name",
			sortorder: "asc",
			rowList: [25, 50, 100],                         // 한페이지에 몇개씩 보여 줄건지?
			//height: jqHeight,                               // 그리드 높이
			width: jqWidth,
			scrollerbar: true,               
//           autowidth: true,

			rowNum: 50,
			pager: '#paging',                            // 네비게이션 도구를 보여줄 div요소
			viewrecords: true,                                 // 전체 레코드수, 현재레코드 등을 보여줄지 유무

			multiselect: true,
			multiboxonly: true,
			caption: "지점별 관리자 목록",                 // 그리드타이틀

			rownumbers: true,                                 // 그리드 번호 표시
			rownumWidth: 40,                                   // 번호 표시 너비 (px)

            cellEdit: true ,
			cellsubmit: "remote" ,
             cellurl: "/admin/setAdminInfo",              // 추가, 수정, 삭제 url

             jsonReader: {
				id: "admin_seq",
				repeatitems: false
			},
			sortable: true,

            // 컬럼명
			colNames: ['관리자 순번', '지점 순번', '지점', '권한', '권한 히든','이름', '아이디', '비밀번호', '폰번호', '등록', '이메일', '상담사수익율(%)', '메모', '추천Decrypt','추천', '안내멘트','APP토큰','웹토큰','구인SMS알림', 'Web Push 알림', 'App Push 알림', '오더관리', '일일대장', '기간통계', '설정','구직자등록','구인자등록','구직자앱','구인자앱','상태', '등록일시', '등록자'],

            // 컬럼별 속성
			colModel: [
				{name: 'admin_seq'    , index: 'admin_seq'    , key: true, hidden: true},
				{name: 'company_seq'  , index: 'company_seq'  , hidden: true},
				<c:choose>
					<c:when test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
						{name: 'company_name' , index: 'company_name' , align:   "left", sortable:  true,
							editable:  true,
							edittype: "text",
							editoptions: {
									size: 30,
									dataInit:fn_select_companyName
							},
							editrules: {edithidden: true, required: false},
							searchoptions: {sopt: ['cn','eq','nc']}
						},
					</c:when>
					<c:otherwise>
						{name: 'company_name' , index: 'company_name' , hidden: true},
				 	</c:otherwise>
				</c:choose>
				{name: 'auth_level'   , index: 'auth_level'   ,width:  300, fixed:  true, edittype: "select", editoptions: {value: auth_opts}, formatter: formatAuthLevel, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'auth_level', index: 'auth_level_hidden', hidden: true },
				{name: 'admin_name'   , index: 'admin_name'   , align:   "left", editable:  true, sortable:  true, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'admin_id'     , index: 'admin_id'     , align:   "left", editable:  ${sessionScope.ADMIN_SESSION.auth_level < 1 } ? true : false, sortable:  true, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'admin_pass'   , index: 'admin_pass'   , align:   "left", editable:  true, sortable:  true, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'admin_phone'  , index: 'admin_phone'  , align:   "left", editable:  true, sortable:  true, formatter: formatPhone, searchoptions: {sopt: ['cn','eq','nc']}, editrules: {custom:  true, custom_func: validAdminPhone},},
				{name: 'admin_phone_add'    , index: 'admin_phone'    , align:   "center", width: 90, formatter: fn_addBtn, search: false, editable:  false, sortable:false},
				{name: 'admin_email'  , index: 'admin_email'  , align:   "left", editable:  true, sortable:  true, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'counselor_rate'  , index: 'counselor_rate'  , align:   "right", editable:  true, sortable:  true, searchoptions: {sopt: ['cn','eq','nc']}},
				{name: 'admin_memo'   , index: 'admin_memo'   , align:   "left", editable:  true, sortable:  true, edittype: "textarea", searchoptions: {sopt: ['cn','eq','nc']}}, 
				{name: 'recommendationDecrypt', index: 'recommendationDecrypt', hidden: true}, 
				{name: 'recommendation'  , index: 'recommendation', title: false , align:   "left", width: 180
                	 , editable:  true
                	 , edittype: "text"
                 	 , editrules: {custom:  true, custom_func: validRecommendation}, sortable:  true, searchoptions: {sopt: ['cn','eq','nc']}},
                {name: 'admin_comment', index : 'admin_comment', search: false, editable: true},
				{name: 'push_token'   , index: 'push_token'   , align:   "left" },
				{name: 'web_token'   , index: 'web_token'   , align:   "left" },
				{name: 'work_app_sms'           , index: 'work_app_sms'   , align: "center", width:  60, search: false, editable:  true, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.work_app_sms == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'web_push_use_yn', index: 'web_push_use_yn', align: "center", width:  60, search: false, editable:  true, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.web_push_use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},	
				{name: 'app_push_use_yn', index: 'app_push_use_yn', align: "center", width:  60, search: false, editable:  true, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.app_push_use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'order_use_yn', index: 'order_use_yn', align: "center", width:  60, search: false, editable: ${sessionScope.ADMIN_SESSION.auth_level < 1 } ? true : false, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.order_use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'ilbo_use_yn', index: 'ilbo_use_yn', align: "center", width:  60, search: false, editable: ${sessionScope.ADMIN_SESSION.auth_level < 1 } ? true : false, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.ilbo_use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'static_use_yn', index: 'static_use_yn', align: "center", width:  60, search: false, editable: ${sessionScope.ADMIN_SESSION.auth_level < 1 } ? true : false, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.static_use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'setting_use_yn', index: 'setting_use_yn', align: "center", width:  60, search: false, editable: ${sessionScope.ADMIN_SESSION.auth_level < 1 } ? true : false, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.setting_use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'worker_menu', index: 'worker_menu', align: "center", width:  60, search: false, editable: ${sessionScope.ADMIN_SESSION.auth_level < 1 } ? true : false, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.worker_menu == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'work_menu'           , index: 'work_menu'   , align: "center", width:  60, search: false, editable: ${sessionScope.ADMIN_SESSION.auth_level < 1 } ? true : false, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
					// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.work_menu == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'worker_app'           , index: 'worker_app'   , align: "center", width:  60, search: false, editable: ${sessionScope.ADMIN_SESSION.auth_level < 1 } ? true : false, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.worker_app == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'work_app'           , index: 'work_app'   , align: "center", width:  60, search: false, editable: ${sessionScope.ADMIN_SESSION.auth_level < 1 } ? true : false, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.work_app == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'use_yn'           , index: 'use_yn'   , align: "center", width:  60, search: false, editable:  true, sortable:  true, edittype: "select", editoptions: {value: use_opts}, formatter: "select",
					cellattr: function(rowId, tv, rowObject, cm, rdata) {
						// rowObject 변수로 그리드 데이터에 접근
						if ( rowObject.use_yn == '0' ) {
							return 'style="color: red; text-align: center; background-color: #FFFFF0;"';
						}
					}
				},
				{name: 'reg_date'     , index: 'reg_date'     , align: "center", width: 100, search: false, editable: false, sortable:  true, formatoptions: {newformat: "y/m/d H:i"}},
				{name: 'reg_admin'    , index: 'reg_admin'    , align:   "left", width:  60, search: false, editable: false, sortable:  true}
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
        	var sessionCompanySeq = '${sessionScope.ADMIN_SESSION.company_seq }';
        	var sessionAuthLevel = '${sessionScope.ADMIN_SESSION.auth_level }';
        	var sessionAdminSeq = '${sessionScope.ADMIN_SESSION.admin_seq }';
        	
        	//row data 전체 가져 오기
            var list = $("#list").jqGrid('getRowData', rowid);
            
        	if(list.company_seq != sessionCompanySeq && sessionAuthLevel != '0') {
				if(rowid != sessionAdminSeq) {        		
					$("#list").setColProp("admin_name", {editable : false});
		        	$("#list").setColProp("admin_id", {editable : false});
		        	$("#list").setColProp("admin_pass", {editable : false});
		        	$("#list").setColProp("admin_phone", {editable : false});
		        	$("#list").setColProp("admin_email", {editable : false});
		        	$("#list").setColProp("counselor_rate", {editable : false});
		        	$("#list").setColProp("admin_memo", {editable : false});
		        	$("#list").setColProp("work_app_sms", {editable : false});
		        	$("#list").setColProp("web_push_use_yn", {editable : false});
		        	$("#list").setColProp("app_push_use_yn", {editable : false});
		        	$("#list").setColProp("order_use_yn", {editable : false});
		        	$("#list").setColProp("ilbo_use_yn", {editable : false});
		        	$("#list").setColProp("static_use_yn", {editable : false});
		        	$("#list").setColProp("setting_use_yn", {editable : false});
		        	$("#list").setColProp("worker_menu", {editable : false});
		        	$("#list").setColProp("work_menu", {editable : false});
		        	$("#list").setColProp("worker_app", {editable : false});
		        	$("#list").setColProp("work_app", {editable : false});
		        	$("#list").setColProp("use_yn", {editable : false});
				}else {
					$("#list").setColProp("admin_name", {editable : true});
		        	$("#list").setColProp("admin_id", {editable : true});
		        	$("#list").setColProp("admin_pass", {editable : true});
		        	$("#list").setColProp("admin_phone", {editable : true});
		        	$("#list").setColProp("admin_email", {editable : true});
		        	$("#list").setColProp("counselor_rate", {editable : true});
		        	$("#list").setColProp("admin_memo", {editable : true});
		        	$("#list").setColProp("work_app_sms", {editable : true});
		        	$("#list").setColProp("web_push_use_yn", {editable : true});
		        	$("#list").setColProp("app_push_use_yn", {editable : true});
		        	$("#list").setColProp("order_use_yn", {editable : true});
		        	$("#list").setColProp("ilbo_use_yn", {editable : true});
		        	$("#list").setColProp("static_use_yn", {editable : true});
		        	$("#list").setColProp("setting_use_yn", {editable : true});
		        	$("#list").setColProp("worker_menu", {editable : true});
		        	$("#list").setColProp("work_menu", {editable : true});
		        	$("#list").setColProp("worker_app", {editable : true});
		        	$("#list").setColProp("work_app", {editable : true});
		        	$("#list").setColProp("use_yn", {editable : true});
				}
        	}
        	
        	if(sessionAuthLevel == '0') {
	        	if(list.auth_level == '0' || list.auth_level == '1' || list.auth_level == '3') {
	        		$("#list").setColProp("recommendation", {editable : true});
	        	}else {
	        		$("#list").setColProp("recommendation", {editable : false});
	        	}
        	}else {
        		$("#list").setColProp("recommendation", {editable : false});
        	}
                        lastsel = rowid;
                      },

    // submit 전
    beforeSubmitCell: function(rowid, cellname, value) {
                        if ( cellname == "use_yn" && value == "0" ) {
                          $("#"+rowid).hide();
                        }
                        
                        return {admin_seq: rowid, company_seq: $("#list").jqGrid('getRowData', rowid).company_seq};
                      },
		afterSubmitCell: function(res, i, a, b, c, d, e) {
			if(a == 'recommendation') {
				$("#list").trigger("reloadGrid");	
			}
		},
        // Grid 로드 완료 후
        loadComplete: function(data) {
                        //총 개수 표시
//                        $("#totalRecords").html(numberWithCommas( $("#list").getGridParam("records") ));
                        
                        // 추천코드 tooltip
               			var rows = $(this).getDataIDs();

						for (var i = 0; i < rows.length; i++) {
						    var row = $(this).getRowData(rows[i]);
						    
					        $(this.rows[i + 1]).attr('title', row.recommendationDecrypt);
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
  setTooltipsOnColumnHeader($("#list"), 10, "핸드폰번호 등록 SMS를보낼때 자체인증");
  setTooltipsOnColumnHeader($("#list"), 12, "상담사일 경우만 입력 한다. 단위는 %");
  
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
             url: "/admin/setAdminCell/",
            data: params,
        dataType: 'json',
         success: function(data) {
                    $("#list").trigger("reloadGrid");
//                    $("#list").addRowData(seq, {}, "first");
                  },
      beforeSend: function(xhr) {
                    xhr.setRequestHeader("AJAX", true);
                    $(".wrap-loading").show();
                  },
           error: function(e) {
        	   $(".wrap-loading").hide();
                    if ( data.status == "901" ) {
                      location.href = "/admin/login";
                    } else {
                      alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
                    }
                  },
                  complete: function(e) {
                	  $(".wrap-loading").hide();
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
    
    var params = "sel_admin_seq=" + recs;

    var rows = recs.length;

    $.ajax({
            type: "POST",
             url: "/admin/removeAdminInfo",
            data: params,
        dataType: 'json',
         success: function(data) {
                    $("#list").trigger("reloadGrid");
                  },
      beforeSend: function(xhr) {
                    xhr.setRequestHeader("AJAX", true);
                    $(".wrap-loading").show();
                  },
           error: function(e) {
        	   $(".wrap-loading").hide();
                    if ( data.status == "901" ) {
                      location.href = "/admin/login";
                    } else {
                      alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
                    }
                  },
                  complete: function(e) {
                	  $(".wrap-loading").hide();
                  }
    });
  });

  //문자 보내기
  $("#btnSms").click( function() {
	  $("#tr_msg").val("");
	  
	  var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
	  var rows = recs.length;
	  
	  if(rows < 1){
		  alert("SMS 보낼 관리자를 선택 하세요.");
		  return;
	  }
	  
	  openIrPopup('popup-layer');
	  
  });
  
  $("#btnSmsSend").click( function() {
	  var tr_msg = $("#tr_msg").val(); 
	  
	  var blank_pattern = /^\s+|\s+$/g;
	  if( tr_msg.replace( blank_pattern, '' ) == "" ){
	      alert(' 공백만 입력되었습니다 ');
	      return false;
	  }
	  
	  
	  if(confirm("SMS를 발송 하시겠습니까?")){
		  closeIrPopup('popup-layer');
		  
		  var recs = $("#list").jqGrid('getGridParam', 'selarrrow');
		  var params = "sel_admin_seq=" + recs;
			
		  
		  	var _data = {
		  			sel_admin_seq : recs.toString()
					,tr_msg : tr_msg
		    };
	
	  	
		  var rows = recs.length;
	
		  $.ajax({
			  	type: "POST",
		        url: "/admin/setAdminSms",
		        data: _data,
		        dataType: 'json',
		        success: function(data) {
		         	if(data.code == "0000"){
		         		toastOk( data.sendCount + " 건 발송 하였습니다.");
		         	}else{
		         		toastFail(data.message);
		         	}
		        },
		      	beforeSend: function(xhr) {
		                    xhr.setRequestHeader("AJAX", true);
		                    $(".wrap-loading").show();
		        },
		        error: function(e) {
		        	$(".wrap-loading").hide();
		                    if ( data.status == "901" ) {
		                      location.href = "/admin/login";
		                    } else {
		                      alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
		                    }
		        },
		        complete: function(e) {
		        	$(".wrap-loading").hide();
		        }
		    });
	  }
	
  });
  
	  
  

  // 새로고침
  $("#btnRel").click( function() {
    lastsel = -1;

    $("#list").trigger("reloadGrid");                       // 그리드 다시그리기
  });

  // 검색
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

  function fn_addBtn(cellvalue, options, rowObject) {
		var str = "";
		
		str += "<div class=\"bt_wrap\">";
		str += "	<div style='width: 40px;' id=\"div_phone_" + rowObject.company_seq + "\" class=\"bt_on\"><a onclick=\"fn_phoneAddNum(" + rowObject.admin_seq + ", " + rowObject.company_seq + ")\">등록</a></div>";
		
		return str;
	}

  
  

//관리자 폰번호 검사
function validAdminPhone(phoneNum, nm, valref) {

  var result = true;
  var resultStr = "";

  //폰에서 - 빼기
  var RegNotNum  = /[^0-9]/g;
  phoneNum = phoneNum.replace(RegNotNum,'');
  
  if ( phoneNum == "" ) return [true, ""];

  var valiResult = validPhone(phoneNum, null, null);
  if ( !valiResult[0] ) return valiResult;
  
  return [result, resultStr];
}

  
  // 관리자 권한 format
  function formatAuthLevel(cellvalue, options, rowObject) {
    var str = "";
    if ( rowObject != null ) {
      str += "<div class=\"bt_wrap\">";
<c:choose>
  <c:when test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">/* 최고관리자 */
      str += "<div class=\"bt"+ (cellvalue == "0" ? "_on" : "") +"\"><a href=\"JavaScript:chkAuthLevel('"+ rowObject.admin_seq +"', '"+ rowObject.company_seq +"', '"+ 0 +"');\"> 슈퍼 </a></div>";
      str += "<div class=\"bt"+ (cellvalue == "4" ? "_on" : "") +"\"><a href=\"JavaScript:chkAuthLevel('"+ rowObject.admin_seq +"', '"+ rowObject.company_seq +"', '"+ 4 +"');\"> 매니저 </a></div>";
      str += "<div class=\"bt"+ (cellvalue == "1" ? "_on" : "") +"\"><a href=\"JavaScript:chkAuthLevel('"+ rowObject.admin_seq +"', '"+ rowObject.company_seq +"', '"+ 1 +"');\"> 관리자 </a></div>";
      str += "<div class=\"bt"+ (cellvalue == "2" ? "_on" : "") +"\"><a href=\"JavaScript:chkAuthLevel('"+ rowObject.admin_seq +"', '"+ rowObject.company_seq +"', '"+ 2 +"');\"> 직원 </a></div>";
      str += "<div class=\"bt"+ (cellvalue == "3" ? "_on" : "") +"\"><a href=\"JavaScript:chkAuthLevel('"+ rowObject.admin_seq +"', '"+ rowObject.company_seq +"', '"+ 3 +"');\"> 상담사 </a></div>";
  </c:when>
  <c:when test="${sessionScope.ADMIN_SESSION.auth_level eq 1}">////회사최고 관리자
      
  	if ( rowObject.admin_seq == "${sessionScope.ADMIN_SESSION.admin_seq}" ) {//자신일경우 
        if ( "${sessionScope.ADMIN_SESSION.auth_level}" <= "1" ) {
          str += "<div class=\"bt"+ (cellvalue == "1" ? "_on" : "") +"\"><a href=\"JavaScript:chkAuthLevel('"+ rowObject.admin_seq +"', '"+ rowObject.company_seq +"', '"+ 1 +"');\"> 관리자 </a></div>";
        }
        str += "<div class=\"bt"+ (cellvalue == "2" ? "_on" : "") +"\"><a href=\"JavaScript:chkAuthLevel('"+ rowObject.admin_seq +"', '"+ rowObject.company_seq +"', '"+ 2 +"');\"> 직원 </a></div>";
    } else {// 회사 최고 관리자가 아니면
        if ( (rowObject.auth_level > "${sessionScope.ADMIN_SESSION.auth_level}" || rowObject.admin_name == "?") && rowObject.auth_level != 3 ) {
          str += "<div class=\"bt"+ (cellvalue == "1" ? "_on" : "") +"\"><a href=\"JavaScript:chkAuthLevel('"+ rowObject.admin_seq +"', '"+ rowObject.company_seq +"', '"+ 1 +"');\"> 관리자 </a></div>";
          str += "<div class=\"bt"+ (cellvalue == "2" ? "_on" : "") +"\"><a href=\"JavaScript:chkAuthLevel('"+ rowObject.admin_seq +"', '"+ rowObject.company_seq +"', '"+ 2 +"');\"> 직원 </a></div>";
        } else {
          if ( cellvalue == "0" )      str += " 전체 ";
          else if ( cellvalue == "1" ) str += " 관리자 ";
          else if ( cellvalue == "2" ) str += " 직원 ";
          else if ( cellvalue == "3" ) str += " 상담사";  
        }
        
    }
  </c:when>
  <c:otherwise>
    if ( cellvalue == "0" )      str += " 전체 ";
    else if ( cellvalue == "1" ) str += " 관리자 ";
    else if ( cellvalue == "2" ) str += " 직원 ";
    else if ( cellvalue == "3" ) str += " 상담사";
  </c:otherwise>
</c:choose>
      str += "</div>";
    }

    return str;
  }
  
});

// 관리자 권한 선택
function chkAuthLevel(admin_seq, company_seq, auth_level) {
  $.ajax({
       type: "POST",
        url: "/admin/setAdminInfo",
       data: {"admin_seq": admin_seq, "company_seq": company_seq, "auth_level": auth_level},
   dataType: 'json',
    success: function(data) {
               lastsel = -1;

               $("#list").trigger("reloadGrid");
             },
 beforeSend: function(xhr) {
	 $(".wrap-loading").show();
               xhr.setRequestHeader("AJAX", true);
             },
      error: function(e) {
    	  $(".wrap-loading").hide();
               if ( data.status == "901" ) {
                 location.href = "/admin/login";
               } else {
                 alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
               }
             },
             complete: function(e) {
            	 $(".wrap-loading").hide();
             }
  });
}


function fn_select_companyName(e){
	 
         $(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택

         $(e).autocomplete({
//              source: "/admin/getCompanyNameList?srh_use_yn=1",
              source: function (request, response) {
                        $.ajax({
                              url: "/admin/getCompanyNameList", type: "POST", dataType: "json",
                             data: { term: request.term, srh_use_yn: 1 },
                          success: function (data) {
                                     response($.map(data, function (item) {
//                                                            return { label: item.code + ":" + item.name, value: item.code, id: item.id }
                                                             
                                                            return item;
                                             }))
                                   },
                       beforeSend: function(xhr) {
                    	   $(".wrap-loading").show();
                                     xhr.setRequestHeader("AJAX", true);
                                   },
                            error: function(e) {
                            	$(".wrap-loading").hide();
                                     if ( data.status == "901" ) {
                                       location.href = "/admin/login";
                                     } else {
                                       alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
                                     }
                                   },
                                   complete: function(e) {
                                	   $(".wrap-loading").hide();
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
                        $("#list").jqGrid('setCell', lastsel, 'auth_level', ui.item.code, '', true);  //다른 셀 바꾸기
                        
                        return false;
                      }
         });
     
}

function getByteB(str) {
	var byte = 0;

	for (var i=0; i<str.length; ++i) {
		(str.charCodeAt(i) > 127) ? byte += 2 : byte++ ;
	}

	return byte;
}

function validRecommendation(value, cellTitle, valref){
	var regType1 = /^[A-Za-z0-9+]*$/;

	if(!regType1.test(value)) {
		return [false, "] 추천코드는 영문 및 숫자만 조합이 가능합니다."];
	}
	
	var byteChk = getByteB(value);
	
	if(byteChk > 20) {
		return [false, "] 추천코드는 20Byte 이내로 가능합니다."];
	}
	
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
	  			$(".wrap-loading").show();
	        	xhr.setRequestHeader("AJAX", true);
	        },
	       	error: function(e) {
	       		$(".wrap-loading").hide();
	        	if ( data.status == "901" ) {
	            	location.href = "/admin/login";
	            } else {
	            	alert('오류가 발생하였습니다.\n확인 후 다시 진행해 주세요.');
	            }
	        },
	        complete: function(e) {
	        	$(".wrap-loading").hide();
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
      <h3> 관리자 > 지점별 관리자 </h3>
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
<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0}">
              <option value="c.company_name">지점</option>
</c:if>
              <option value="a.admin_name">이름</option>
              <option value="a.admin_id">아이디</option>
              <option value="a.admin_phone">폰번호</option>
              <option value="a.admin_email">이메일</option>
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
<c:if test="${sessionScope.ADMIN_SESSION.auth_level eq 0 or (sessionScope.ADMIN_SESSION.company_seq eq 13 and sessionScope.ADMIN_SESSION.auth_level eq 1)}">
        <a href="#none" id="btnAdd" class="btnStyle01">추가</a>
        <a href="#none" id="btnDel" class="btnStyle01">삭제</a>
</c:if>
        <a href="#none" id="btnRel" class="btnStyle05">새로고침</a>
<!--         <a href="#none" id="btnSms" class="btnStyle05">SMS보내기</a> -->
      </div>
    </div>

    <table id="list"></table>
    <div id="paging"></div>
    
    
    <!-- 팝업 : 스캔 첨부파일 등록 -->
<div id="popup-layer">
	<header class="header">
		<h1 class="tit">SMS 보내기</h1>
		<a href="#none" class="close"	onclick="javascript:closeIrPopup('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
	</header>

		<section>
			<div class="cont-box">
				<article>
					<table class="bd-form inputUI_simple" summary="SMS 보내기">
						<caption>관리자  SMS 보내기</caption>
						<colgroup>
							<col width="120px" />
							<col width="*" />
						</colgroup>
						
						<tbody>
							<tr>
								<th>내용</th>
								<td>
									<textarea 	rows="10" cols="43" id="tr_msg" name="tr_msg" class="inp-field txt-field"> </textarea>
									
								</td>
							</tr>
						</tbody>
					</table>
				</article>

				<div class="btn-module mgtS fR">
					<a href="#popup-layer" id="btnSmsSend" class="btnStyle01">보내기</a>
				</div>
				
			</div>
		</section>

</div>




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
</form>
<form id="phoneAddFrm">
	<input type="hidden" id="phone_admin_seq" name="phone_admin_seq" />
	<input type="hidden" id="page_flag" name="page_flag" value="A" />
