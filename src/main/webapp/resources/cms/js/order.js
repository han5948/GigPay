/*
 * 오더관리에서만 사용되는 js 파일이다.
 * 2020-11-03 
 * gj
 * 
 * */

//useinfo 버튼
var gid = "";
var isSubSelect = false;
function formatWorkUseInfo(cellvalue, options, rowObject) {
	var str = "";
	var rowId = options.rowId;
	var ilboState = rowObject.ilbo_state;
	gid = options.gid;
	var orderState = $("#list").jqGrid("getCell", options.rowData.order_seq, "order_state");
	var mod_admin = $("#list").jqGrid("getCell", options.rowData.order_seq, "mod_admin");
	if (typeof(rowObject.work_seq) == "undefined"){
		rowObject = $('#list').jqGrid('getRowData', rowId);
	}

	if ( rowObject.work_seq > 0 ) {
		str += "<div class=\"bt_wrap\">";
		if(ilboState != "1" && (orderState == "2" || orderState == "4")){
			if(sessionId != mod_admin){
				if(rowObject.use_name  != null && rowObject.use_name != "" ) {
					str += "<div id=\"div_use_"+ rowObject.work_seq +"\" class=\"bt_red\"><a > "+ rowObject.use_name +" </a></div>";
				} else {
					str += "<div id=\"div_use_"+ rowObject.work_seq +"\" class=\"bt_red\"><a >미공개</a></div>";
				}
			}else{
				if(rowObject.use_name  != null && rowObject.use_name != "" ) {
					var _style = getCodeStyle(rowObject.use_code);
					str += "<div id=\"div_use_"+ rowObject.work_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_useInfoOpt('"+ rowObject.work_seq +"', event, '" + rowObject.use_code + "'); return false;\""+ _style +"> "+ rowObject.use_name +" </a></div>";
				} else {
					str += "<div id=\"div_use_"+ rowObject.work_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_useInfoOpt('"+ rowObject.work_seq +"', event, '" + rowObject.use_code + "'); return false;\" >미공개</a></div>";
				}
			}
		}else{
			if(rowObject.use_name  != null && rowObject.use_name != "" ) {
				_style = getCodeStyle(rowObject.use_code);
				str += "<div id=\"div_use_"+ rowObject.work_seq +"\" class=\"bt\"><a " +_style + "> "+ rowObject.use_name +" </a></div>";
			} else {
				str += "<div id=\"div_use_"+ rowObject.work_seq +"\" class=\"bt\"><a >미공개</a></div>";
			}
		}
		
		
		
	}
	return str;
}
function fn_useInfoOpt(work_seq, event, use_code) {
	optHTML = "<div id=\"use_"+ work_seq +"\" class=\"bt_wrap single\" style=\"width: 260px; display: none; background-color: #fff;\">";
	optHTML += optUse.replace(/<<ILBO_SEQ>>/gi, work_seq).replace(use_code +" bt", use_code +" bt_on");
	optHTML += clsHTML;
	optHTML += "<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('order', '"+ work_seq +"', 'use_code', 0, 'use_name', '' ,'0', 'USE');\"> 초기화 </a></div></div>";
	optHTML += "</div>";
	selectOptIlboList('use_' + work_seq, event, optHTML);
}
 
function chkCodeUpdate(divId, work_seq, codeValue, val, codeName, nm, codeGroup, codeType) {
	var str = '{"cellname": "'+ codeValue +'", "work_seq": "'+ work_seq +'", "'+ codeValue +'": "'+ val +'", "'+ codeName +'": "'+ nm +'", "code_group": "'+ codeGroup +'", "code_type": "'+ codeType +'", "code_value": "'+ val +'", "code_name": "'+ nm +'", "log_user_type": "A"}';
	
	
	$("#"+ gid +" > .bt_on").each(function(index) {
    	$(this).removeClass("bt_on");
    	$(this).addClass("bt");
    });

	$("#"+ gid +" > ."+ val).removeClass("bt");
 	$("#"+ gid +" > ."+ val).addClass("bt_on");
	

	var params = jQuery.parseJSON(str);
	
  	$.ajax({
		type: "POST",
        url: "/admin/setOrderWorkInfo",
       	data: params,
   		dataType: 'json',
    	success: function(data) {
			//새로고침을 안하기 위해서
           	if( codeName == "use_name") {
            	$("#"+gid).jqGrid('setCell', work_seq, "use_code", val, '', true);
                $("#"+gid).jqGrid('setCell', work_seq, "use_name", (nm == "") ? null : nm, '', '', true);
                $("#"+gid).jqGrid('setCell', work_seq, "use_info",  val, '', true);
			}
		},
 		beforeSend: function(xhr) {
        	xhr.setRequestHeader("AJAX", true);
   			$(".wrap-loading").show();
		},
      	error: function(e) {
        	if ( data.status == "901" ) {
            	location.href = "/admin/login";
			}
		},
		complete : function() {
			// 요청 완료 시
			$(".wrap-loading").hide();
		}
	});

	$('#opt_layer').css('display', 'none');
  	//로그보기 레이어 숨기기
 	$('#code_log_layer').html("");
  	$('#code_log_layer').css('display', 'none');
}

function selectOptIlboList(optId, e , optHTML) {
	
	var $opt_layer = $('#opt_layer');
	var _display;
	if ( !e ) e = window.Event;
	var pos = abspos(e);

	if ( $("#"+ optId).css("display") == null || $("#"+ optId).css("display") == "undefined" ) {
		writeOpt2(optHTML);
	}

	$opt_layer.find('.bt_wrap').css('display', 'none');

	_display = $opt_layer.css('display') == 'none'?'block':'none';
	
	$opt_layer.css('left', (pos.x - 100) +"px");
	$opt_layer.css('top', (pos.y-20) +"px");

	$opt_layer.css('display', _display);
	$("#"+ optId).css('display', _display);
	
	$('#code_log_layer').html("");
	$('#code_log_layer').css('display', 'none');
	
}
function formatEmployerIlbo(cellvalue, options, rowObject) {
	var str = "";

	if ( rowObject.employer_seq > 0 ) {
	  	str += "<div class=\"bt_wrap\">";
	  	str += "<div class=\"bt_on\"><a href=\"JavaScript:ilboView('i.employer_seq', '"+ rowObject.employer_seq +"', '/admin/workIlbo', '구인대장');\"> > </a></div>";
	  	str += "</div>";
	}

  return str;
}

function fn_editOtions_workerCompanyName(e, cm){
	var _url = "/admin/getCompanyNameList2";

    $(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택

    $(e).autocomplete({
		source: function (request, response) {
        	$.ajax({
            	url: _url, type: "POST", 
                dataType: "json",
                data: { term: request.term,srh_use_yn: 1},
                success: function (data) {
                	response($.map(data, function (item) {
						return item;
					}));
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
        	isSubSelect = false;
		},
        minLength: 2,
        focus: function (event, ui) {
        	return false;
		},
        select: function (event, ui) {
        	//lastsel  = $("#"+gid).jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
        	var parentId = $(this).parent()[0].offsetParent.id;
        	var parentRowId = $(this).parent().parent()[0].id;
        	
        	$(e).val(ui.item.label);
            selectWorkerName = ui.item.label;
            //function validWorkerCompanyName 에서 다시 지정된다.
            $("#"+parentId).jqGrid('setCell', parentRowId, 'worker_company_seq', ui.item.code, '', true);  //다른 셀 바꾸기
            
            isSubSelect = true;
            
            //Enter가 아니면
			if( event.keyCode != "13" ){
				var iRow = $('#' + $.jgrid.jqID(parentRowId))[0].rowIndex;
				$("#"+parentId).jqGrid("saveCell", iRow, cm.iCol);
			}

            return false;
		}
	});
}

function  validWorkerCompanyName(value, cellTitle, valref) {//값 ,타이틀 row의 인덱스
	
	if ( !isSubSelect && value != '?' ) {
		return [false, "] 지점을 선택하여 엔터를 쳐야 됩니다."];
	}

	
	lastsel  = $("#"+gid).jqGrid('getGridParam', "selrow" );  
	
	// 선택한 열의 아이디값
	if ( value == "?" ) {
		$("#"+gid).setCell(lastsel, "worker_company_name","", {background:"#FFFFFF"}) ;
		$("#"+gid).jqGrid('setCell', lastsel, 'worker_company_seq', 0,    '', true);  //다른 셀 바꾸기

		$("#"+gid +" tr[id='"+lastsel+"'] td[aria-describedby='list_ilbo_worker_name']").removeClass('not-editable-cell'); 

	}else{
		var workerCompanySeq = $("#"+gid).jqGrid('getRowData', lastsel).worker_company_seq
//		$("#"+gid).setCell(lastsel, "worker_company_name","", {background:"#80f37b"}) ;
		
		$("#"+gid).jqGrid('setCell', lastsel, 'worker_company_seq', workerCompanySeq, '', true);  //다른 셀 바꾸기
	}

	return [true, ""];

}
function getCodeStyle(_code) {
	oCode = mCode.get(_code);
  
  var _style = "";

  try {
  	_style = " style=\"background-color:#"+ oCode.bgcolor +"; color:#"+ oCode.color +"\"";
  } catch(e) {}

  return _style;
}

function ilboView(type, seq, url, menuText) {
	var frmId  = "menuFrm";    // default Form
	var frm      = $("#"+ frmId);
	var startIlboDate = $("#start_ilbo_date").val();
	var endIlboDate   = $("#end_ilbo_date").val();
	var ilboView = $("#"+ frmId +" > input[name=ilboView]");          // 대장으로 이동
	var ilboType = $("#"+ frmId +" > input[name=srh_ilbo_type]");   // 대장으로 이동
	var ilboSeq  = $("#"+ frmId +" > input[name=srh_seq]");           // 대장으로 이동
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
	  
	//초기화
	frm.attr('target', '');
	frm.attr('action', '');
	
	$("#start_ilbo_date").val(startIlboDate);
	$("#end_ilbo_date").val(endIlboDate);
	$("#"+ frmId +" > input[name=ilboView]").val("");
}