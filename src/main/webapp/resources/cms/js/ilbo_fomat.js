	function formatAddrEdit(cellvalue, options, rowObject) {
		var str = "";
		var btn = "";
	
		str += "<div class=\"bt_wrap\">";
	
		if(rowObject.ilbo_work_latlng !="" && rowObject.ilbo_work_latlng !=null ){
			if(rowObject.ilbo_work_addr_comment !=null && rowObject.ilbo_work_addr_comment !=""){
				btn = "_green";
			}else{
				btn = "_t1";
			}
		}
		
		str += "<div class=\"bt"+ btn +"\"><a href=\"JavaScript:check_mapWindowOpen('ILBO','"+ rowObject.ilbo_seq +"', '"+ rowObject.ilbo_work_latlng +"','','"+ rowObject.ilbo_work_addr_comment +"');\" title='"+ rowObject.ilbo_work_latlng +"'> M </a></div>";    
		str += "</div>";
	
		return str;
	}

	function check_mapWindowOpen(mode, seq, latlng ,addr, comment){
		//work 소유 지점에서만 함수 호출
		if(isWorkCompany($("#list").jqGrid('getRowData', seq).company_seq, $("#list").jqGrid('getRowData', seq).work_company_seq)){
			mapWindowOpen(mode,seq,latlng,addr,comment)
		}
	}

	function formatAddrLocation(cellvalue, options, rowObject) {
		var str = "";
		var btn = "";

		str += "<div class=\"bt_wrap\">";

		if(rowObject.ilbo_work_latlng !="" && rowObject.ilbo_work_latlng !=null ){
			if(rowObject.ilbo_work_addr_comment !=null && rowObject.ilbo_work_addr_comment !=""){
				btn = "_green";
			}else{
				btn = "_t1";
			}
		}
		
		str += "<div class=\"bt"+ btn +"\"><a href=\"JavaScript:locationWindowOpen('"+ rowObject.ilbo_seq +"', '" + rowObject.ilbo_kind_code + "' ,'"+ rowObject.ilbo_work_latlng + "', '" + $("#start_ilbo_date").val() + "');\" title='"+ rowObject.ilbo_work_latlng +"'> L </a></div>";    
		str += "</div>";
		
		return str;
	}

	//구직자 상세정보
	function formatWorkerView(cellvalue, options, rowObject) {
		var str = "";
	
		if( isMyWorker(rowObject) ){
		    if ( rowObject.worker_seq > 0 ) {
		    	str += "<div class=\"bt_wrap\">";
		    	str += "<div class=\"bt_on\"><a href=\"JavaScript:workerView('"+ rowObject.worker_seq +"', '"+ rowObject.company_seq +"');\"> V </a></div>";
		    	str += "</div>";
	    	}
		}else{
			str = "";
		}
	
		return str;
	}

	//구직대장
	function formatWorkerIlbo(cellvalue, options, rowObject) {
		var str = "";
	
		if( isMyWorker(rowObject)){
			if ( rowObject.worker_seq > 0 ) {
				str += "<div class=\"bt_wrap\">";
				str += "<div class=\"bt_on\"><a href=\"JavaScript:ilboView('i.worker_seq', '"+ rowObject.worker_seq +"', '/admin/workerIlbo', '구직대장');\"> > </a></div>";
				str += "</div>";
			}
		}else{
			str = "";
		}
		
		return str;
	}
	
	// 공제액 계
	function formatDeductibleInfo(cellvalue, options, rowObject) {
		var str = '';
		
		if(rowObject.deductible_sum > 0) {
			str += "<div class=\"bt_wrap\">";
			str += "<div class=\"bt_on\"><a href=\"JavaScript:fn_deductibleView('" + rowObject.ilbo_seq + "');\"> > </a></div>";
			str += "</div>";
		}
		
		return str;
	}

	//구직자 정보
	function formatWorkerInfo(cellvalue, options, rowObject) {
		var str = "";
		var rowId = options.rowId;
	
		if (typeof(rowObject.ilbo_seq) == "undefined"){
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
		
		str += "<div class=\"bt_wrap\">";
		
		// 출역상태
		if ( rowObject.output_status_code != null && rowObject.output_status_code != "" && rowObject.output_status_code > 0 ) {
			var _style = getCodeStyle(rowObject.output_status_code);
				
			str += "<div title ='"+rowObject.mod_output_date+"' id=\"div_output_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_workerInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.worker_seq +"', '구직자를 매칭 시킨 후 ', event, '" + rowObject.ilbo_output_status_code + "'); return false;\""+ _style +"> "+ rowObject.output_status_name +" </a></div>";
		} else if ( rowObject.ilbo_seq < 0 && rowObject.worker_seq > 0 ) {
			str += "<div title ='"+rowObject.mod_date+"' id=\"div_output_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_workerInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.worker_seq +"', '구직자를 매칭 시킨 후 ', event, '" + rowObject.ilbo_output_status_code + "'); return false;\"> 출근 </a></div>";
		} else {
			str += "<div title ='"+rowObject.mod_date+"' id=\"div_output_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_workerInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.worker_seq +"', '구직자를 매칭 시킨 후 ', event, '" + rowObject.ilbo_output_status_code + "'); return false;\"> 상태 </a></div>";
		}
		
		// 나이
		if ( rowObject.ilbo_worker_sex == "F" ) {
			str += "<div class=\"bt\"><a href=\"javascript:CopyToClipboard('"+formatRegNo(rowObject.ilbo_worker_jumin,null,null)+"', myClipboard, false)\" title='"+ formatRegNo(rowObject.ilbo_worker_jumin,null,null) +"'><font color='blue'> "+ rowObject.ilbo_worker_age +" 세 </font></a></div>";
		} else {
			str += "<div class=\"bt\"><a href=\"javascript:CopyToClipboard('"+formatRegNo(rowObject.ilbo_worker_jumin,null,null)+"', myClipboard, false)\" title='"+ formatRegNo(rowObject.ilbo_worker_jumin,null,null) +"'> "+ rowObject.ilbo_worker_age +" 세 </a></div>";
		}
	
		// 앱 설치 유무
		var _appStyle ="";
	
		if(rowObject.ilbo_worker_app_use_status == "1"){	//승인
			if(rowObject.ilbo_worker_app_status == "1"){	// 로그인
				_appStyle = "_on";
			}else{														//로그 아웃
				_appStyle = "_green";
			}
		}else if(rowObject.ilbo_worker_app_use_status == "5"){	// 수수료 미납
			_appStyle = "_t1";
		}else{
			_appStyle = "";
		}
		
		str += "<div class=\"bt"+ _appStyle +"\"><a href=\"javascript:CopyToClipboard('"+ (selectWorkerName == "" ? rowObject.ilbo_worker_name : selectWorkerName) + " " + formatPhone(rowObject.ilbo_worker_phone,null,null) +"', myClipboard, false)\" title='"+ formatPhone(rowObject.ilbo_worker_phone,null,null) +"'> 앱 </a></div>";
		
		// 신분증 스캔 여부
		if( rowObject.worker_seq == 0){
			str += "<div class=\"bt\"><a href=\"#\"> 신 </a></div>";
			str += "<div class=\"bt\"><a href=\"#\"> 교 </a></div>";
		}else{
		    str += "<div class=\"bt"+ ((rowObject.ilbo_worker_jumin_scan_yn == "1") ? "_on" : "") +"\"><a href=\"javaScript:popupImage('JUMIN', '"+ rowObject.worker_seq +"');\"> 신 </a></div>";
		
			// 교육증 스캔 여부
			if ( rowObject.ilbo_worker_OSH_yn == "1" ) {
			  	str += "<div class=\"bt"+ ((rowObject.ilbo_worker_OSH_scan_yn == "1") ? "_on" : "_green") +"\"><a href=\"javaScript:popupImage('OSH', '"+ rowObject.worker_seq +"');\"> 교 </a></div>";
			} else {
			  	str += "<div class=\"bt"+ ((rowObject.ilbo_worker_OSH_scan_yn == "1") ? "_yellow" : "") +"\"><a href=\"javaScript:popupImage('OSH', '"+ rowObject.worker_seq +"');\"> 교 </a></div>";
			}
		}
	
		// 계좌보유 여부
		if ( rowObject.ilbo_worker_bank_code != null && rowObject.ilbo_worker_bank_owner != "" ) {             // 계좌가 있는 경우
		  	var btnTag = "";
		
		  	if ( rowObject.ilbo_worker_bank_owner == rowObject.ilbo_worker_name || rowObject.ilbo_worker_bank_owner == selectWorkerName ) {
		        if ( rowObject.ilbo_worker_bankbook_scan_yn == "1" ) {
		        	btnTag ="_on";    //blue
		        } else {
		        	btnTag ="_green";
		        }
		  	} else {
		        if ( rowObject.ilbo_worker_bankbook_scan_yn == "1" ) {
		      		btnTag ="_yellow";
		    	} else {
		      		btnTag ="_t1";
		    	}
		  	}
		
		  	str += "<div class=\"bt"+ btnTag +"\"><a href=\"javascript:CopyToClipboard('"+ rowObject.ilbo_worker_bank_owner +" "+rowObject.ilbo_worker_bank_name+" "+rowObject.ilbo_worker_bank_account  +"', myClipboard, false)\" title='"+ rowObject.ilbo_worker_bank_owner +" "+rowObject.ilbo_worker_bank_name+" "+rowObject.ilbo_worker_bank_account  +"'> 통 </a></div>";
		} else {    //계좌가 없는 경우
		  	if ( rowObject.ilbo_worker_bankbook_scan_yn == "1" ) {
		  		str += "<div class=\"bt_purple\"><a href=\"#\"> 통 </a></div>";
		  	} else {
			        str += "<div class=\"bt\"><a href=\"#\"> 통 </a></div>";
		  	}
		}
		
		str += "</div>";
		
		selectWorkerName ="";
		
		return str;
	}

	//구직자 상태
	function formatWorkerStatusInfo(cellvalue, options, rowObject) {
		var str = "";
		var rowId = options.rowId;
		
		if (typeof(rowObject.ilbo_seq) == "undefined"){
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
	
		str += "<div class=\"bt_wrap\">";
		
		// 출역상태
		if (rowObject.ilbo_worker_status_code != '0' && rowObject.ilbo_worker_status_code != null && rowObject.ilbo_worker_status_code != "") {
			var _style = getCodeStyle(rowObject.ilbo_worker_status_code);
	
		    str += "<div title ='"+rowObject.mod_output_date+"' id=\"div_wsc_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_workerStatusInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.worker_seq +"', '구직자를 매칭 시킨 후 ', event, '" + rowObject.ilbo_worker_status_code + "'); return false;\""+ _style +"> "+ rowObject.ilbo_worker_status_name +" </a></div>";
		} else {
			str += "<div title ='"+rowObject.mod_date+"' id=\"div_wsc_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_workerStatusInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.worker_seq +"', '구직자를 매칭 시킨 후 ', event, '" + rowObject.ilbo_worker_status_code + "'); return false;\">상태</a></div>";
		}
	
		str += "</div>";
	
		selectWorkerName ="";
	
		return str;
	}

	//구인처 상세정보
	function formatEmployerView(cellvalue, options, rowObject) {
		var str = "";
/*
if(rowObject.worker_company_seq == companySeq){
	    
}else{
	if ( rowObject.employer_seq > 0 ) {
	      str += "<div class=\"bt_wrap\">";
	      str += "<div class=\"bt_on\"><a href=\"JavaScript:employerView('"+ rowObject.employer_seq +"', '"+ rowObject.company_seq +"');\"> V </a></div>";
	      str += "</div>";
	    }
}
	*/
	
		if ( rowObject.employer_seq > 0 ) {
			str += "<div class=\"bt_wrap\">";
			str += "<div class=\"bt_on\"><a href=\"JavaScript:employerView('"+ rowObject.employer_seq +"', '"+ rowObject.company_seq +"');\"> V </a></div>";
			str += "</div>";
		}
		
		return str;
	}

	//구인대장
	function formatEmployerIlbo(cellvalue, options, rowObject) {
		var str = "";
/*
	if(rowObject.worker_company_seq == companySeq){
}else{
	    if ( rowObject.employer_seq > 0 ) {
	      str += "<div class=\"bt_wrap\">";
	      str += "<div class=\"bt_on\"><a href=\"JavaScript:ilboView('i.employer_seq', '"+ rowObject.employer_seq +"', '/admin/workIlbo', '구인대장');\"> > </a></div>";
	      str += "</div>";
	    }
}
	*/
		if ( rowObject.employer_seq > 0 ) {
			str += "<div class=\"bt_wrap\">";
			str += "<div class=\"bt_on\"><a href=\"JavaScript:ilboView('i.employer_seq', '"+ rowObject.employer_seq +"', '/admin/workIlbo', '구인대장');\"> > </a></div>";
			str += "</div>";
		}
		
		return str;
	}
	
	//구인대장
	function formatWorkIlbo(cellvalue, options, rowObject) {
		var str = "";
		var btStyle = "bt_on";
		
		if ( rowObject.work_seq > 0 ) {
			if(rowObject.visit_type != null && rowObject.visit_type != ""){ //메니져가 관리프로그램 web 이든 모바일이든 방문 한적이 있으면
				btStyle = "bt_yellow";
			}
			
			str += "<div class=\"bt_wrap\">";
			str += "<div class=\""+ btStyle +"\"><a href=\"JavaScript:ilboView('i.work_seq', '"+ rowObject.work_seq +"', '/admin/workIlbo', '구인대장');\" > > </a></div>";
			str += "</div>";
		}
		
		return str;
	}


	//현장 정보
	function formatWorkInfo(cellvalue, options, rowObject) {
		var str = "";
		var rowId = options.rowId;
		
		if (typeof(rowObject.ilbo_seq) == "undefined"){
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
		
		if ( rowObject.ilbo_seq > 0 ) {
			str += "<div class=\"bt_wrap\">";
			// 현장시간
			if ( rowObject.ilbo_order_name  != null && rowObject.ilbo_order_name != "" ) {
				var _style = getCodeStyle(rowObject.ilbo_order_code);
				str += "<div id=\"div_order_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_workInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 매칭 시킨 후 ', event, '" + rowObject.ilbo_order_code + "'); return false;\""+ _style +"> "+ rowObject.ilbo_order_name +" </a></div>";
			} else {
				str += "<div id=\"div_order_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_workInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 매칭 시킨 후 ', event, '" + rowObject.ilbo_order_code + "'); return false;\">순서</a></div>";
					
			}
		}

		return str;
	}


	//구인처 평가
	function formatStar(cellvalue, options, rowObject) {
		var str = "☆☆☆☆☆";

		if ( rowObject.ilbo_seq > 0 ) {
			if ( cellvalue == 0 ) str = "☆☆☆☆☆";
			else if ( cellvalue == 1 ) str = "★☆☆☆☆";
			else if ( cellvalue == 2 ) str = "★★☆☆☆";
			else if ( cellvalue == 3 ) str = "★★★☆☆";
			else if ( cellvalue == 4 ) str = "★★★★☆";
			else if ( cellvalue >= 5 ) str = "★★★★★";
		}

		return str;
	}

	//현장 order 정보
	function formatWorkOrderInfo(cellvalue, options, rowObject) {
	
	//var str = rowObject.ilbo_work_order_name;
		var str = "";
		var rowId = options.rowId;
		
		if (typeof(rowObject.ilbo_seq) == "undefined"){
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}

		if ( rowObject.ilbo_seq > 0 ) {
  			str += "<div class=\"bt_wrap\">";
  		
  			var _style = getCodeStyle(rowObject.ilbo_work_order_code);
    		if ( rowObject.company_seq  != 0 ) {
	      		if(rowObject.company_seq == sessionCompanySeq) {
	      				str += "<div id=\"div_workOrder_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_workOrderInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 매칭 시킨 후 ', event, '" + rowObject.ilbo_work_order_code + "'); return false;\""+ _style +"> "+ rowObject.ilbo_work_order_name +" </a></div>";
	      		}else {
		        		str += "<div id=\"div_workOrder_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:void(0);\""+ _style +"> "+ rowObject.ilbo_work_order_name +" </a></div>";
	      		}
    		} else {
    			str += "<div id=\"div_workOrder_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:void(0);\""+ _style +">사무실</a></div>";
    		}
		}

		return str;
	}

	//공개여부 옵션
	function formatUseInfo(cellvalue, options, rowObject) {
		var str = "";
		var rowId = options.rowId;
		
		if (typeof(rowObject.ilbo_seq) == "undefined"){
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}

		if ( rowObject.ilbo_seq > 0 ) {
			str += "<div class=\"bt_wrap\">";
  		
			//현장 공개여부
			if(rowObject.ilbo_use_name  != null && rowObject.ilbo_use_name != "" ) {
				var _style = getCodeStyle(rowObject.ilbo_use_code);
      		
			//if(rowObject.work_company_name == sessionCompanyName) {
				str += "<div id=\"div_use_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_useInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 매칭 시킨 후 ', event, '" + rowObject.ilbo_use_code + "'); return false;\""+ _style +"> "+ rowObject.ilbo_use_name +" </a></div>";
      	//}else {
      		//str += "<div id=\"div_use_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:void(0);\""+ _style +"> "+ rowObject.ilbo_use_name +" </a></div>";
			//}
			} else {
      	//if(rowObject.work_company_name == sessionCompanyName) {
				str += "<div id=\"div_use_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_useInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 매칭 시킨 후 ', event, '" + rowObject.ilbo_use_code + "'); return false;\">미공개</a></div>";
    		//}else {
      		//str += "<div id=\"div_use_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:void(0);\">미공개</a></div>";
			//}
			}
		}

		return str;
	}
	
	//AI 배정 상태
	function formatAutoStatusInfo(cellvalue, options, rowObject) {
		var str = "";
		var rowId = options.rowId;
		
		if (typeof(rowObject.ilbo_seq) == "undefined"){
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}

		var _style = "style='background:";
		if( rowObject.auto_status == null ){
			_style += "#f8ffb3'";
		}else if( rowObject.auto_status == 0 ){
			_style += "#1de9b6'";
		}else if( rowObject.auto_status == 1 ){
			_style += "#4db0ff'";
		}else if( rowObject.auto_status == 2 ){
			_style += "#0070c0; color:white'";
		}else if( rowObject.auto_status == 3 ){
			_style += "#5aa5da'";
		}else if( rowObject.auto_status == 4 ){
			_style += "#000000; color:white'";
		}
		
		if ( rowObject.ilbo_seq > 0 ) {
			str += "<div class=\"bt_wrap\">";
			str += "<div id=\"div_status_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_autoStatusOpt('" + rowObject.ilbo_seq + "', '" + rowObject.worker_seq + "', '" + rowObject.output_status_code + "', '" + rowObject.ilbo_status_code + "', '" + rowObject.use_yn + "', '" + rowObject.ilbo_date + "', '" + rowObject.ilbo_work_arrival + "', '', event, '" + rowObject.auto_status + "'); return false;\" " + _style +"> "+ rowObject.auto_status_name +" </a></div>";
		}

		return str;
	}

	//현장상태
	function formatStaInfo(cellvalue, options, rowObject) {
		var str = "";
		var rowId = options.rowId;
			
		if (typeof(rowObject.ilbo_seq) == "undefined"){
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
	
		if ( rowObject.ilbo_seq > 0 ) {
			str += "<div class=\"bt_wrap\">";
	  		
			//현장 상태
	  	if(rowObject.ilbo_status_name  != null && rowObject.ilbo_status_name != "" ) {
	      	var _style = getCodeStyle(rowObject.ilbo_status_code);
	      	
	      	str += "<div id=\"div_sta_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_staInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 매칭 시킨 후 ', event, '" + rowObject.ilbo_status_code + "'); return false;\""+ _style +"> "+ rowObject.ilbo_status_name +" </a></div>";
			} else {
	      	str += "<div id=\"div_sta_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_staInfoOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 매칭 시킨 후 ', event, '" + rowObject.ilbo_status_code + "'); return false;\">상태</a></div>";
	    	}
		}
	
		return str;
	}


/*
// 직종 - multi
function formatKindCode(cellvalue, options, rowObject) {
  var str = "";

  if ( rowObject.ilbo_seq > 0 ) {
    str += "<div class=\"bt_wrap\">";
    if ( rowObject.ilbo_kind_code != null && rowObject.ilbo_kind_code != "" ) {
      var _style = getCodeStyle(rowObject.ilbo_kind_code);

      str += "<div id=\"div_kind_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:selectOpt('kind_"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 선택 한 후 ', event); return false;\""+ _style +"> "+ rowObject.ilbo_kind_name +" </a></div>";
    } else {
      str += "<div id=\"div_kind_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:selectOpt('kind_"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 선택 한 후 ', event); return false;\"> 상태 </a></div>";
    }
    str += "</div>";

    if ( !isGridLoad ) {
      optHTML += "<div id=\"kind_"+ rowObject.ilbo_seq +"\" class=\"bt_wrap multi\" style=\"width: 450px; display: none; background-color: #fff;\">";
      if ( rowObject.ilbo_kind_code != null && rowObject.ilbo_kind_code != "" ) {
        var codeArr = rowObject.ilbo_kind_code.split(",");
        var _html = "";

        _html = optKind;

        for ( var i = 0; i < codeArr.length; i++ ) {
          _html = _html.replace(/<<ILBO_SEQ>>/gi, rowObject.ilbo_seq).replace(codeArr[i] +" bt", codeArr[i] +" bt_on");
        }
        optHTML += _html;
      } else {
        optHTML += optKind.replace(/<<ILBO_SEQ>>/gi, rowObject.ilbo_seq);
      }
      optHTML += clsHTML;
      optHTML += "</div>";
    }
  }

  return str;
}
*/
	//직종 - single
	function formatKindCode(cellvalue, options, rowObject) {
		var str = "";
		var rowId = options.rowId;
	  
	
	    str += "<div class=\"bt_wrap\">";
	
		if ( rowObject.ilbo_kind_code != null && rowObject.ilbo_kind_code != "") {
			var _style = getCodeStyle(rowObject.ilbo_kind_code);
			
			if(rowObject.job_kind == '2') {
				_style = 'style="background-color: yellow;"';
			}
			
			str += "<div id=\"div_kind_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"fn_kindCodeOpt('"+ rowId+"'); return false;\""+ _style +"> "+ cellvalue +" </a></div>";
		} else {
			str += "<div id=\"div_kind_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"fn_kindCodeOpt('"+ rowId +"'); return false;\"> 선택 </a></div>";
		}
		
		str += "</div>";
	
		return str;
	}

	//동반자 리스트
	function formatCompanion(cellvalue, options, rowObject) {
		var str = "";
		
		if(rowObject.employer_seq != '0') {
			
			var _style = "";
			if( rowObject.companion_count > 1 
					&& rowObject.ilbo_status_code != 'STA007' && rowObject.ilbo_status_code != 'STA008' && rowObject.ilbo_status_code != 'STA009'){
				_style = "style='background: #B40486; color: #ffffff;'";
			}
			
			str += "<div class=\"bt_wrap\">";
			str += "<div style='width: 22px;' id=\"div_companion_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a "+_style+" onclick=\"JavaScript:fn_compainonInfoList('"+ rowObject.ilbo_date + "','"+ rowObject.ilbo_seq +"', '"+ rowObject.employer_seq +"', '" + rowObject.work_seq + "', '" + rowObject.ilbo_kind_code + "', '" + rowObject.ilbo_job_detail_code + "', '" + rowObject.ilbo_job_add_code + "', '" + rowObject.worker_seq + "', event); return false;\">P</a></div>";
		}
	  	
	  	return str
	}

	//노임수령
	function formatIncomeCode(cellvalue, options, rowObject) {
		var str = "";
		var rowId = options.rowId;
	  
		if (typeof(rowObject.ilbo_seq) == "undefined"){
			rowObject = $('#list').jqGrid('getRowData', rowId);
		}
		
		if ( rowObject.ilbo_seq > 0 ) {
			str += "<div class=\"bt_wrap\">";
	    	if ( rowObject.ilbo_income_code != null && rowObject.ilbo_income_code != "" && rowObject.ilbo_income_code > 0 ) {
	    		var _style = getCodeStyle(rowObject.ilbo_income_code);
	
	    		str += "<div id=\"div_income_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_incomeCodeOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 선택 한 후 ', event, " + rowObject.ilbo_income_code + "); return false;\""+ _style +"> "+cellvalue +" </a></div>";
	      
	    		var _bgstyle = getCodeBGStyle(rowObject.ilbo_income_code, true);
	      	
	    		$("#list").jqGrid('setCell', options.rowId, 'employer_name', '', _bgstyle); //셀 바꾸기
			} else {
				str += "<div id=\"div_income_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_incomeCodeOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.work_seq +"', '현장을 선택 한 후1 ', event, " + rowObject.ilbo_income_code + "); return false;\"> 상태 </a></div>";
	    	}
	
	    	str += "</div>";
		}
	
		return str;
	}

	//노임지급
	function formatPayCode(cellvalue, options, rowObject) {
		  var str = "";
		  var rowId = options.rowId;
			
		  if (typeof(rowObject.ilbo_seq) == "undefined"){
				rowObject = $('#list').jqGrid('getRowData', rowId);
		  }
			
		  if ( rowObject.ilbo_seq > 0 ) {
		    	str += "<div class=\"bt_wrap\">";
		    	
		    	if ( rowObject.ilbo_pay_code != null && rowObject.ilbo_pay_code != "" && rowObject.ilbo_pay_code > 0 ) {
			      	var _style = getCodeStyle(rowObject.ilbo_pay_code);
			      	str += "<div id=\"div_pay_"+ rowObject.ilbo_seq +"\" class=\"bt_on\"><a onclick=\"JavaScript:fn_payCodeOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.worker_seq +"', '구직자를 선택 한 후 ', event, '" + rowObject.ilbo_pay_code + "'); return false;\""+ _style +"> "+ cellvalue +" </a></div>";
			      	
			      	var _bgstyle = getCodeBGStyle(rowObject.ilbo_pay_code, true);
			      	$("#list").jqGrid('setCell', options.rowId, 'ilbo_worker_name', '', _bgstyle); //셀 바꾸기
		    	} else {
			        str += "<div id=\"div_pay_"+ rowObject.ilbo_seq +"\" class=\"bt\"><a onclick=\"JavaScript:fn_payCodeOpt('"+ rowObject.ilbo_seq +"', '"+ rowObject.worker_seq +"', '구직자를 선택 한 후 ', event, '" + rowObject.ilbo_pay_code + "'); return false;\"> 선택 </a></div>";
		    	}
				
		    	str += "</div>";
		  }
		
		  return str;
	}

	//구직자지점과  구인자지점이 다를경우 색깔변화
	function cellattrWorkerCompanyColor(rowId, tv, rowObject, cm, rdata){
		if ( isIlboShareObject(rowObject)) {
	      	return 'style="background: #80f37b"'; //; color:#"';	//공유된 일자리이면 색깔이 바뀜.
		}
	}

	function cellattrWorkOrderColor(rowId, tv, rowObject, cm, rdata){
		return getCodeStyle(rowObject.ilbo_work_order_code); //; color:#"';
	}	

	// 임금수령액
	function fn_wages_received(rowId, tv, rowObject, cm, rdata) {
		var ilboPay = parseInt(rowObject.ilbo_pay);
		var deductibleSum = parseInt(rowObject.deductible_sum);
		var wagesReceived = ilboPay - deductibleSum;
		
		return comma(wagesReceived);
	}