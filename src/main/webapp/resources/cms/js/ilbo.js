/*
 * 일일대장에서만 사용되는 것들을 따로 뽑아서 정리한 js 파일이다.
 * 2020-09-22 
 * nemojjang
 * 
 * */

$(function() {
	/*
	  // jqgrid caption 클릭 시 접기/펼치기 기능
	  $("div.ui-jqgrid-titlebar").click(function () {
	    $("div.ui-jqgrid-titlebar a").trigger("click");
	  });
	*/

	$("#start_ilbo_date").change( function() {
		$("#end_ilbo_date").val($("#start_ilbo_date").val());
	    $("#from_ilbo_date").val($("#start_ilbo_date").val());
	});

	// 행 추가
	$("#btnAdd").click( function() {
		$.ajax({
	    	type	: "POST",
	        url		: "/admin/setIlboCell/",
			data	: {
	        	ilbo_date: $("#start_ilbo_date").val()
	        	,company_seq : companySeq			//일보 소유지점...
	        },
	        dataType: 'json',
	        success: function(data) {
				/*   var newRowid =data.ilbo_seq;
	        	    var rowdata = {id:newRowid, name:"ilbo_seq", ilbo_worker_memo:"nomal"};
	        	    $("#list").jqGrid("addRowData", newRowid, rowdata, 'first'); */ 
				$("#list").trigger("reloadGrid");
				///                    $("#list").addRowData(seq, {}, "first");
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
	});

	//검색 초기화
	$("#btnReset").on("click", function() {
		$("#srh_use_yn_2").prop("checked", true);
		$("label[for='srh_use_yn_3']").removeClass("on");
		$("label[for='srh_use_yn_1']").removeClass("on");
		$("label[for='srh_use_yn_2']").addClass("on");
		
		$("#option_1").prop("checked", true);
		$("label[for='option_2']").removeClass("on");
		$("label[for='option_3']").removeClass("on");
		$("label[for='option_4']").removeClass("on");
		$("label[for='option_5']").removeClass("on");
		$("label[for='option_6']").removeClass("on");
		$("label[for='option_1']").addClass("on");
		
		$("#srh_type").val('');
		$(".customSelectInner").text("전체");
		$("#srh_text").val('');
		
		var _data = {
			'ilboStatusOpt' : '1',
			'ilboOpt' : '0',
			'ilboSelOpt' : '0',
			'ilboSearchText' : ''
		};
		
		setSearchOptSession(_data);
		
		$("#btnSrh").trigger("click");
	});
		  
	// 행 복사
	$("#btnCopy").click( function() {
		if ( $("#copy_rows").val() == 0 ) { //jjh 추가 숫자만 입력 하도록 copy_rows 도 수정 하였음
	    	alert("카피할 수를 입력 하세요.");
		
			return false;
	    }
	
	    var recs = $("#list").jqGrid('getGridParam', 'selarrrow');

	    var rows = recs.length;
	
	    if ( rows == null || rows <= 0 ) {
			alert('복사할 행을 선택하세요.');
			
	      	return false;
	    }
	    if ( $("input:checkbox[id='copy_worker']:checked").val() == 'Y' ){
	    	
	    	for(var i = 0; i < recs.length; i++) {
	    		var rowid = recs[i];
	    		var selectWorkerSeq = $("#list").jqGrid('getRowData', rowid).worker_seq;
	    		
	    		if( selectWorkerSeq == 0 ){
		    		alert("구직자가 없습니다.");
		    		
		    		return false;
		    	}
	    	}
	    }
	
	    var _data = {
    		sel_ilbo_seq : recs
			, copy_worker : $("input:checkbox[id='copy_worker']:checked").val()
			, copy_rows : $("#copy_rows").val()
			, to_ilbo_date :$("#to_ilbo_date").val()
		}
		
		var _url = "/admin/copyIlboCell";

		commonAjax(_url, _data, true, function(data) {
			//successListener
			if (data.code == "0000") {
				$.toast({title: '복사 완료', position: 'middle', backgroundColor: '#5aa5da', duration:1000 });
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

	// 행 삭제
	$("#btnDel").click( function() {
		var recs = $("#list").jqGrid('getGridParam', 'selarrrow');

	    ilboRowDel( recs, 0);
	});
	  
	// 새로고침
	$("#btnRel").click( function() {
		lastsel = -1;

	    $("#list").trigger("reloadGrid");                       // 그리드 다시그리기
	});

	// 수동푸쉬
	$("#btnWPush").click( function() {
		//$("#list").jqGrid('setSelection','272621',false);
		lastsel = -1;
	    var recs = $("#list").jqGrid('getGridParam', 'selarrrow');

	    var tmpParams = "sel_ilbo_seq=" + recs;
	    var params = {
	    	sel_ilbo_seq : recs
	    };
	   	
	    var rows = recs.length;
	    
	    if(rows < 1){
	    	$.toast({title: '푸쉬 대상을 선택 하세요.', position: 'middle', backgroundColor: '#ef7220', duration:1000 });
	    	return;
	    }
	    
	    // worker 가 빠진 경우 체크한다. app 에서 예약을 하면서 worker 정보를 삭제 하는 경우가 있다.
	    var workerCheck = false;
	    var _data = params;
    	var _url = "/admin/getSelectIlboList";
    	
    	commonAjax(_url, _data, false, function(data) {
    		if(data.header.code == "0000"){
				for(var i=0; i< data.body.ilboList.length; i++){
					var ilboDTO = data.body.ilboList[i];
					
					if(ilboDTO.worker_seq == "0"){
						alert("구직자가 없는 data 가 있습니다. row를 다시 확인 하세요.");
						workerCheck = true;
						 return;
					}
					
					if(ilboDTO.work_seq == "0"){
						alert("현장이 없는 data 가 있습니다. row를 다시 확인 하세요.");
						workerCheck = true;
						return;
					}
					
					if(ilboDTO.ilbo_worker_app_status != "1"){
						alert("APP 사용이 미승인인 구직자가 있습니다.. row를 다시 확인 하세요.");
						workerCheck = true;
						return;
					}
					
					// 100002 : 대기 1000019:배정 1000020: 지금
			        if(ilboDTO.output_status_code != '' && ilboDTO.output_status_code !='0' && ilboDTO.output_status_code !='100002' && ilboDTO.output_status_code !='100019' && ilboDTO.output_status_code !='100020'){
			        	alert("출역상태가 알선을 보낼수 없는 구직자가 있습니다.. row를 다시 확인 하세요.");
						workerCheck = true;
						return;
			        }
			        
			        if(ilboDTO.ilbo_kind_code == null || ilboDTO.ilbo_kind_code == '' || ilboDTO.ilbo_kind_code == '0'){
						alert("직종을 선택하지 않은 현장이 있습니다. row를 다시 확인 하세요.");
						workerCheck = true;
						return;
					}
					
					if(ilboDTO.ilbo_unit_price == null || ilboDTO.ilbo_unit_price == '' || ilboDTO.ilbo_unit_price == '0'){
						alert("단가를 입력하시 않은 현장이 있습니다. row를 다시 확인 하세요.");
						workerCheck = true;
						return;
					}
					
					if(ilboDTO.limit_count == '1' && !data.body.employerLimit) {
						alert(ilboDTO.ilbo_worker_name + "반장님은 이 구인처에 월 최대 출역일수(7일) 초과로 출역이 제한되었습니다.");
						
						workerCheck = true;
						
						return;
					}
					
					if(ilboDTO.limit_count == '2' && !data.body.workLimit) {
						alert(ilboDTO.ilbo_worker_name + "반장님은 이 현장에 월 최대 출역일수(7일) 초과로 출역이 제한되었습니다.");
						
						workerCheck = true;
						
						return;
					}
					
					return false;
//					var employer_limit_count = ilboDTO.employer_limit_count * 1;
//					var work_limit_count = ilboDTO.work_limit_count * 1;
//					var worker_limit_count = ilboDTO.worker_limit_count * 1;
//					
//					if(ilboDTO.limit_count == '1' && employer_limit_count <= worker_limit_count) {
//						alert(ilboDTO.ilbo_worker_name + "반장님은 이 구인처에 월 최대 출역일수(7일) 초과로 출역이 제한되었습니다.");
//						workerCheck = true;
//						return
//					}
//					
//					if(ilboDTO.limit_count == '2' && work_limit_count <= worker_limit_count) {
//						alert(ilboDTO.ilbo_worker_name + "반장님은 이 현장에 월 최대 출역일수(7일) 초과로 출역이 제한되었습니다.");
//						workerCheck = true;
//						return
//					}
				}
			}else{
				alert("시스템 오류");
				//$("#list").trigger("reloadGrid");
			}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
    	
    	//리로드 하고 선택 항목을 다시 선택 해 준다.
    	if(workerCheck){
    		var idxs = tmpParams.split('=')[1].split(',');
    		
    		$("#list").trigger("reloadGrid");                       // 그리드 다시그리기
    		
			setTimeout(function () {       
            	for (var i = 0; i < idxs.length; i++) { //row id수만큼 실행
            		$("#list").jqGrid('setSelection',idxs[i], false);
                }
        	},500);
			
    		return;
    	}

   		var confirm_str = rows + "명에게 알선PUSH 를 보내시겠습니까?"
    	   	
       	if (!confirm(confirm_str) == true){    //확인
			return;
      	}

      	$.ajax({
			type: "POST",
            url: "/admin/setIlboPush",
            data: params,
          	dataType: 'json',
           	success: function(data) {
				$.toast({title: rows +' 명 알선푸쉬 완료', position: 'middle', backgroundColor: '#5aa5da', duration:2000 });
				
			    for (var i = 0; i <rows;  i++) {
					var ilbo_seq = recs[i];
				    	   
			    	   	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_status"        ,'1'   , ''  , true);                 			// 상태코드
			    	   	$("#list").jqGrid('setCell', ilbo_seq, "output_status_code" ,'100012'   , ''  , true);    			// 상태코드
			           	$("#list").jqGrid('setCell', ilbo_seq, "output_status_name" , '푸쉬'    , ''  , true);      			// 상태명
			           	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_worker_info"   , ilbo_seq  , ''  , true);        		// 셀 바꾸기
			           	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_worker_status_info"   , ilbo_seq  , ''  , true);     // 셀 바꾸기
			    	   	
				} // end for
			    
			  //선택 셀 해제
			  $("#list").jqGrid("resetSelection");
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
	});

	//도움말
	$("#btnHelp").click( function() {
		$("#helpPanel").slideToggle("slow");
	});
	
	$("#btnEmployerAdd").on("click", function() {
		writeEmployer();
	});
	  
	//알림 팝업
	$("#btnAlim").on("click", function() {
		var alimPopFrm = $("<form></form>");
		alimPopFrm.attr("name", "alimPopFrm");
		alimPopFrm.attr("id", "alimPopFrm");
		alimPopFrm.attr("method", "post");
		  
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
			$.toast({title: '구직자를 선택해주세요.', position: 'middle', backgroundColor: '#ef7220', duration:1000 });
			
			return false;
		}
		  
		alimPopFrm.appendTo('body');
		  
		for(var i = 0; i < params.length; i++) {
			text = '<input type="hidden" name="worker_seq" value="' + params[i].worker_seq + '">';
			text += '<input type="hidden" name="worker_seq_array" value="' + params[i].worker_seq + '">';
			  
			$("#alimPopFrm").append(text);
		}
		  
		$("#alimPopFrm").one("submit", function() {
			var option = 'width=700, height=450, top=250, left=550, resizable=no, status=no, menubar=no, toolbar=no, scrollbars=no, location=no';

			window.open('','pop_target',option);
			 
			this.action = '/admin/alim/alimWrite';
			this.method = 'POST';
			this.target = 'pop_target';
		}).trigger("submit");
		  
		$("#alimPopFrm").remove();
	});

	//일자리 알림
	$("#btnJobAlim").on("click", function() {
		var jobAlimPopFrm = $("<form></form>");
		jobAlimPopFrm.attr("name", "jobAlimPopFrm");
		jobAlimPopFrm.attr("id", "jobAlimPopFrm");
		jobAlimPopFrm.attr("method", "post");
		  
		jobAlimPopFrm.appendTo('body');
		  
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
			$("#jobAlimPopFrm").append('<input type="hidden" name="ilbo_date" value="' + $("#start_ilbo_date").val() + '" />');
			$("#jobAlimPopFrm").append('<input type="hidden" name="queryFlag" value="I" />');
			$("#jobAlimPopFrm").one("submit", function() {
				var option = 'width=700, height=400, top=250, left=600, resizable=no, status=no, menubar=no, toolbar=no, scrollbars=no, location=no';
					
				window.open('','pop_target',option);
					 
				this.action = '/admin/jobAlim/jobAlimWrite';
				this.method = 'POST';
				this.target = 'pop_target';
			}).trigger("submit");	 
				
			$("#jobAlimPopFrm").remove();
		}else {
			fn_writeReady(params);
		}
	});

	//구직자 SMS
	$("#btnWSMS").on("click", function() {
		$("#smsPopFrm").remove();
			
		var params = smsPopAdd('7');
			
		removeDuplicatesArray(params);
			
		if(params.length == 0) {
			$.toast({title: '구직자의 번호가 없거나 선택된 구직자가 없습니다.', position: 'middle', backgroundColor: '#ef7220', duration:1000 });
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
		
	//현장매니저 SMS
	$("#btnMSMS").on("click", function() {
		$("#smsPopFrm").remove();
			
		var params = smsPopAdd('8');
			
		removeDuplicatesArray(params);
			
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
		
	//현장담당자 SMS
	$("#btnESMS").on("click", function() {
		$("#smsPopFrm").remove();
			
		var params = smsPopAdd('9');
			
		removeDuplicatesArray(params);
			
		if(params.length == 0) {
			$.toast({title: '현장담당자의 번호가 없거나 선택된 현장담당자가 없습니다.', position: 'middle', backgroundColor: '#ef7220', duration:1000 });	
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
		
		//Excel
	$("#btnExcel").click( function() {
			if ( $("#start_ilbo_date").val() == "" ) {
		    	alert("출역일자를 선택하세요.");
		    	
		       	$("#start_ilbo_date").focus();
		       	
		       	return false;
			}
		
			if ( $("#end_ilbo_date").val() == "" ) {
		    	alert("출역일자를 선택하세요.");
		       
		    	$("#end_ilbo_date").focus();
		       
		    	return false;
			}
		  
		    var frm = document.getElementById("defaultFrm");
		    
		    frm.action = "/admin/getIlboExcel";
		    frm.target = "download-ifrm";
		    frm.submit();
		}); 
	});
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//주민 통장 등 이미지 팝업
	function popupImage(serviceCode, serviceSeq) {
		var param = "vFlag=ilbo&service_type=WORKER&service_code="+ serviceCode +"&service_seq="+ serviceSeq;
	
	  	window.open("/admin/popup/imagePopup?"+ param, 'image', 'width=800, height=600, toolbar=no, menubar=no, scrollbars=no, resizable=yes');
	}

	// 구직자 상세정보
	function workerView(worker_seq, company_seq) {
		var str = '{"worker_seq": "'+ worker_seq +'", "'+ company_seq +'": "'+ company_seq +'"}';
		var params = jQuery.parseJSON(str);

	  	$.ajax({
	    	type: "POST",
	        url: "/admin/getWorkerViewJSP",
	       	data: params,
	      	async: false,
			//   dataType: 'json',
	    	success: function(result) {
				$("#popup-layer2 > header> h1").html("구직자상세정보");           // popup title
				$("#popup-layer2 > section").html(result);              // 실행 결과 페이지 부모페이지에 삽입
				workerViewOpenIrPopup('popup-layer2');
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
	
	function workerViewOpenIrPopup(popupId) {
		//popupId : 팝업 고유 클래스명
		var $popUp = $('#'+ popupId);

		$dimmed.fadeIn();
		$popUp.show();

		var offsetWidth = -$popUp.width() / 2 ;
		var offsetHeight = -$popUp.height() / 2 - 100;
		  
		$popUp.css({
			'margin-top': offsetHeight,
		    'margin-left': offsetWidth
		});
	}

	// 구직/구인대장 화면으로 이동
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

	//상태변경
	function chkCodeUpdate(divId, ilbo_seq, codeValue, val, codeName, nm, codeGroup, codeType) {
		//노임수령
		if( codeType == "300" ){
			//주 급,(주 급),월 급,(월 급) 
			if( val == "300004" || val == "300009" || val == "300005" || val == "300010" ){
				//슈퍼관리자와 본사계정만 설정가능
				if( sessionCompanySeq != "13" && authLevel > 0 ){
					alert("지점 비승인 메뉴입니다.");
					return;
				}
			}
		}
		
		if( codeType == "100" ){
			if( val == "100003" || val == "100014" || val == "100015" ){
				if( authLevel > 0 ){
					alert("근로기준법 및 직업안정법 준수를 위하여 선택한 상태 변경은 구직자가 앱에서 직접 처리해야 합니다.");
					return;
				}
			}
		}
		
		var str = '{"cellname": "'+ codeValue +'", "ilbo_seq": "'+ ilbo_seq +'", "'+ codeValue +'": "'+ val +'", "'+ codeName +'": "'+ nm +'", "code_group": "'+ codeGroup +'", "code_type": "'+ codeType +'", "code_value": "'+ val +'", "code_name": "'+ nm +'", "log_user_type": "A"}';
		
	 	if ( $("#"+ divId).is(".multi") ) {        // 복수 선택이 가능한 옵션인 경우
		    if ( val != "" ) {
		    	if ( $("#"+ divId +" > ."+ val).is(".bt_on") ) {
		        	$("#"+ divId +" > ."+ val).removeClass("bt_on");
		        	$("#"+ divId +" > ."+ val).addClass("bt");
		      	} else {
			        $("#"+ divId +" > ."+ val).removeClass("bt");
		        	$("#"+ divId +" > ."+ val).addClass("bt_on");
		      	}
		
				var _code = "";
		      	var _name = "";
		
		      	$("#"+ divId +" > .bt_on").each(function(index) {
		        	var _class = "";
		        	var classArr;
		
		        	_class = $(this).attr('class');
		        	classArr = _class.split(" ");
		        	
		        	if(index == 0){
		        		_name = $(this).find(" > a").html().trim();
			        	_code = classArr[0];
		        	}else{
			        	_name += ","+ $(this).find(" > a").html().trim();
			        	_code += ","+ classArr[0];
		        	}
		      	});
		
		      	val = _code;
		      	nm = _name;
			}
	
	   		str = '{"ilbo_seq": "'+ ilbo_seq +'", "'+ codeValue +'": "'+ val +'", "'+ codeName +'": "'+ nm +'", "code_group": "'+ codeGroup +'"}';
		} else {
			$("#"+ divId +" > .bt_on").each(function(index) {
	      		$(this).removeClass("bt_on");
	      		$(this).addClass("bt");
	    	});
	
			$("#"+ divId +" > ."+ val).removeClass("bt");
	 		$("#"+ divId +" > ."+ val).addClass("bt_on");
	    }
	
	  	var params = jQuery.parseJSON(str);
	  	
	  	$.ajax({
			type: "POST",
	        url: "/admin/setIlboInfo",
	       	data: params,
	   		dataType: 'json',
	    	success: function(data) {
				//새로고침을 안하기 위해서
	    		if(data.result == "0000"){
		           	if ( codeName == "output_status_name" ) {
		            	$("#list").jqGrid('setCell', ilbo_seq, "output_status_code", val, '', true);
		            	$("#list").jqGrid('setCell', ilbo_seq, "output_status_name", (nm == "") ? null : nm, '', '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_worker_info",   val, '', true);
		                
		                // 펑크, 째앰, 대마이면 공제액, 임금수령액을 0으로 바꾼다.
						if(val == '100008' || val == '100009' || val == '100010') {
							$("#list").jqGrid("setCell", ilbo_seq, "deductible_sum", 0, '', true);
							$("#list").jqGrid("setCell", ilbo_seq, "wages_received", 0, '', true);
							
							var str = '';
							
							if(data.deductible_sum == 0) {
								str += "<div class=\"bt_wrap\">";
								str += "</div>";
							}
							
							$("#list").jqGrid("setCell", ilbo_seq, "deductible_info", str, '', true);
						}else if(val == '100003' || val == '100014' || val == '100015') {
							// 출발, 도착, 완료일 경우 공제액계, 상세버튼, 임금수령액을 넣어준다.
							$("#list").jqGrid("setCell", ilbo_seq, "deductible_sum", data.deductible_sum, '', true);
							
							var ilbo_pay = $("#list").jqGrid('getCell', ilbo_seq, 'ilbo_pay');
							var wages = (ilbo_pay * 1) - (data.deductible_sum * 1);
							
							$("#list").jqGrid("setCell", ilbo_seq, "wages_received", wages, '', true);
							
							//상세 버튼 넣어야함
							var str = '';
							
							if(data.deductible_sum > 0) {
								str += "<div class=\"bt_wrap\">";
								str += "<div class=\"bt_on\"><a href=\"JavaScript:fn_deductibleView('" + ilbo_seq + "');\"> > </a></div>";
								str += "</div>";
							}
							
							$("#list").jqGrid("setCell", ilbo_seq, "wages_received", wages, '', true);
							$("#list").jqGrid("setCell", ilbo_seq, "deductible_info", str, '', true);
						}
						
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_worker_status_info",   val, '', true);
					} else if ( codeName == "ilbo_order_name" ) {
		            	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_order_code", val, '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_order_name", (nm == "") ? null : nm, '', '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_work_info",  val, '', true);
					} else if ( codeName == "ilbo_kind_name" ) {
		            	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_kind_code", val, '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_kind_name", (nm == "") ? null : nm, '', '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_kind_info", val, '', true);
					} else if ( codeName == "ilbo_income_name" ) {
		            	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_income_code", val, '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_income_name", (nm == "") ? null : nm, '', '', true);
					} else if ( codeName == "ilbo_pay_name" ) {
		            	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_pay_code", val, '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_pay_name", (nm == "") ? null : nm, '', '', true);
		                
		                var _receiveContractSeq = data.receive_contract_seq;
		                if( _receiveContractSeq === undefined ){
		                	_receiveContractSeq = "";
		                }
		                
		                if(val == "200003" || val == "200007" || val == "200004" || val == "200008" ){
		                	//해당셀 값 , 변경여부 변경
		                	$("#list").jqGrid('setCell', ilbo_seq,  'receive_contract_seq', _receiveContractSeq, 'not-editable-cell', true);
		                }else{
		                	//해당셀 수정되도록 변경
		                	var cm = $("#list").jqGrid("getGridParam", 'colModel');
		                	var receive_contract_seq_index = 0;
		                	for (var i=0; i<cm.length; i++) {
		                        if (cm[i].name === "receive_contract_seq") {
		                        	receive_contract_seq_index = i; // return the index
		                        	break;
		                        }
		                    }
		                	var tr = $("#list")[0].rows.namedItem(ilbo_seq);
		                    var td = tr.cells[receive_contract_seq_index];
		                    $(td).removeClass("not-editable-cell");
		                    var _receiveContractName = data.receive_contract_name;
		                    if( _receiveContractName === undefined ){
		                    	_receiveContractName = "";
			                }
		                    
		                    //해당셀 값변경
		                    $("#list").jqGrid('setCell', ilbo_seq,  'receive_contract_name', _receiveContractName);
		                    $("#list").jqGrid('setCell', ilbo_seq,  'receive_contract_seq', _receiveContractSeq);
		                }
					}else if( codeName == "ilbo_use_name") {
		            	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_use_code", val, '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_use_name", (nm == "") ? null : nm, '', '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_use_info",  val, '', true);
					}else if(codeName == "ilbo_status_name") {
		            	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_status_code", val, '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_status_name", (nm == "") ? null : nm, '', '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_status_info",  val, '', true);
		                
		                // 휴지, 취소, 메모이면 공제액을 0으로 바꾼다.
						if(val == 'STA007' || val == 'STA008' || val == 'STA009') {
							$("#list").jqGrid("setCell", ilbo_seq, "deductible_sum", 0, '', true);
							$("#list").jqGrid("setCell", ilbo_seq, "wages_received", 0, '', true);
							
							var str = '';
						
							if(data.deductible_sum == 0) {
								str += "<div class=\"bt_wrap\">";
								str += "</div>";
							}
							
							$("#list").jqGrid("setCell", ilbo_seq, "deductible_info", str, '', true);

							if(navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/Android/i)) $('.popup-content').attr('style', 'width:70%;');

							if(val === 'STA007' || val === 'STA009'){
								customConfirm("현장매니저에게 안내 메세지 발송하시겠습니까?", function(){
									var _notificationData = {
										ilbo_seq: ilbo_seq,
										status_flag: "ALR033"
									};
									
									$.ajax({
										type: "POST",
								        url: "/admin/notificationSend",
								       	data: _notificationData,
								    	success: function(data) {
								    		toastOk("안내 메세지 발송 완료");
								    	},beforeSend: function(xhr) {
								        	xhr.setRequestHeader("AJAX", true);
								   			$(".wrap-loading").show();
										},
								      	error: function(xhr, status, error) {
								        	toastFail("안내 메세지 발송 실패");
										},
										complete : function() {
											// 요청 완료 시
											$(".wrap-loading").hide();
										}
									});
								})
							}else if(val === 'STA008'){
								customConfirm("현장매니저에게 [접수거절] 안내 메시지를 발송하시겠습니까?", function(){
									var _notificationData = {
										ilbo_seq: ilbo_seq,
										status_flag: "ALR035"
									};
									
									$.ajax({
										type: "POST",
								        url: "/admin/notificationSend",
								       	data: _notificationData,
								    	success: function(data) {
								    		toastOk("안내 메세지 발송 완료");
								    	},beforeSend: function(xhr) {
								        	xhr.setRequestHeader("AJAX", true);
								   			$(".wrap-loading").show();
										},
								      	error: function(xhr, status, error) {
								        	toastFail("안내 메세지 발송 실패");
										},
										complete : function() {
											// 요청 완료 시
											$(".wrap-loading").hide();
										}
									});
								})
							}
						}
						
		                if(authLevel < 4) {
			                if(val == 'STA007' || val == 'STA008' || val == 'STA009') {
			                	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_fee",  '0', '', true);
			                	$("#list").jqGrid('setCell', ilbo_seq, "share_fee",  '0', '', true);
			                	$("#list").jqGrid('setCell', ilbo_seq, "counselor_fee",  '0', '', true);
			                	
			                	// 순서 7차로 만들기
			                	divId = 'order_layer';
			                	nm = '7차';
		                		val = '500008';
			                	
		                		$("#"+ divId +" > .bt_on").each(function(index) {
		                      		$(this).removeClass("bt_on");
		                      		$(this).addClass("bt");
		                    	});
	
		                		$("#"+ divId +" > ."+ val).removeClass("bt");
		                 		$("#"+ divId +" > ."+ val).addClass("bt_on");
			                	
			                	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_order_code", val, '', true);
				                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_order_name", (nm == "") ? null : nm, '', '', true);
			                	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_work_info",  val, '', true);
			                	/////////////////////////////////////////////////////////////////////////
			                	
			                	// 노임지급 초기화
			                	divId = 'pay_layer';
			                	nm = '';
		                		val = '0';
			                	
		                		$("#"+ divId +" > .bt_on").each(function(index) {
		                      		$(this).removeClass("bt_on");
		                      		$(this).addClass("bt");
		                    	});
	
		                		$("#"+ divId +" > ."+ val).removeClass("bt");
		                 		$("#"+ divId +" > ."+ val).addClass("bt_on");
			                	
		                 		$("#list").jqGrid('setCell', ilbo_seq, "ilbo_pay_code", val, '', true);
		    	                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_pay_name", (nm == "") ? null : nm, '', '', true);
			                	/////////////////////////////////////////////////////////////////////////
		    	                
		    	                // 일당 초기화
		    	                //$("#list").jqGrid('setCell', rowId, 'ilbo_pay', _pay);		
		    	                fn_setIlboFee(ilbo_seq, '', '');
			                }
		                }
					}else if(codeName == "ilbo_worker_status_name") {
		            	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_worker_status_code", val, '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_worker_status_name", (nm == "") ? null : nm, '', '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_worker_status_info",  val, '', true);
					}else if(codeName == 'ilbo_work_order_name') {
		            	$("#list").jqGrid('setCell', ilbo_seq, "ilbo_work_order_code", val, '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_work_order_name", (nm == "") ? null : nm, '', '', true);
		                $("#list").jqGrid('setCell', ilbo_seq, "ilbo_work_order_info",  val, '', true);
					}
	    		}else {
	    			alert(data.message);
	    		}
			},
	 		beforeSend: function(xhr) {
	        	xhr.setRequestHeader("AJAX", true);
	   			$(".wrap-loading").show();
			},
			error: function(xhr, status, error) {
	        	if ( status == "901" ) {
	            	location.href = "/admin/login";
	            	return;
				}
	        	
	        	toastFail("시스템 오류입니다. 관리자에게 문의주세요");
			},
			complete : function() {
				// 요청 완료 시
				$(".wrap-loading").hide();
			}
		});
	/*
		if ( !$("#"+ divId +"_"+ ilbo_seq).is(".multi") ) {       // 복수 선택이 가능한 옵션인 경우
	    	$("#"+ divId +"_"+ ilbo_seq).hide();
	    	$('#opt_layer').css('display', 'none');
	  	}
	  */
	  	if(divId != "kind_layer"){
			 closeOpt();
	  	}
	  
	  	//로그보기 레이어 숨기기
	 	$('#code_log_layer').html("");
	  	$('#code_log_layer').css('display', 'none');
	}

	// 일일대장에서 구직자를 검색
	function  validWorkerName(value, cellTitle, valref) {
		if ( value == "?" ) {
			initWorkerName(lastsel);
		
			return [true, ""];
		}
	  
		if ( !isSelect && value != '?' ) {
			return [false, "] 구직자를 선택하여 엔터를 쳐야 됩니다."];
		}
	
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         				// 선택한 열의 아이디값
		  
		var ilbo_seq 		= $("#list").jqGrid('getRowData', lastsel).ilbo_seq;     	// 검색된 일보 순
		var worker_seq 	= $("#list").jqGrid('getRowData', lastsel).worker_seq;     	// 검색된 구직자 순
		var work_seq 		= $("#list").jqGrid('getRowData', lastsel).work_seq;         	// 검색된 구인자 순
	  
		var ids = $("#list").jqGrid('getDataIDs');                                	// jqGrid 전체 rowId
		
	  // 배정된 구직자인지 배정되지 않은 구직자 인지 구분한다...
		for ( var i = 0; i < ids.length; i++ ) {
			var _ilbo_seq 	= $("#list").jqGrid('getCell', ids[i], 'ilbo_seq');  
			var _worker_seq 	= $("#list").jqGrid('getCell', ids[i], 'worker_seq');
			var _work_seq 	= $("#list").jqGrid('getCell', ids[i], 'work_seq');
		  
		    if (ilbo_seq != _ilbo_seq && _worker_seq == worker_seq &&  _work_seq ==0 && work_seq == 0 ) {// 원래있던 DATA 에서 같은 근로자가 있고 구인처가 없다면
		    	initWorkerName(lastsel);
		    	return[false,"배정되지 않은 동일한 구직자가 있어 추가할 수 없습니다."];
		    }
		    
		    var isDel = false;
		    
		    if (ilbo_seq != _ilbo_seq && worker_seq == _worker_seq && work_seq > 0 ) {// 원래있던 DATA 에서 같은 근로자가 있고 구인처가 있다면
		    	if(_work_seq >0){// 일이 배정되어 있는경우
			    	if (confirm("이미 배정된 구직자를 추가 합니다. 구직자를 추가 하시겠습니까?\n\n[확인] 기존유지 및 추가\n[취소] 기존 삭제 후 추가") == true){    //확인
			    		return [true, ""];
			    	}else{   //취소
			    		isDel = true;
			    	}
		    	}else{// 일이 배정되지 않은 구직자일 경우
		    		isDel = true;
		    	}
		    }
		    
		    if(isDel){
	             ilboWorkerReset( _ilbo_seq,1 );					// 구직자 정보를 초기화 해 준다.
	             initWorkerName(_ilbo_seq);
	             // $("#list").delRowData(ids[i]);             // 그리드에서 출근자 정보 삭제
	             
	             isDel = false;		//초기화
	             
	    		return [true, ""];
		    }
		}//end for
	  
		return [true, ""];
	}

	//일보 행 삭제
	function ilboWorkerReset( recs ,type){
		var rows = recs.length;
	
		if(type == 0){// ilboList 에서 선택해서 삭제를 클릭할때
			if(authLevel > 0){//최고 관리자가 아니면
				for (var i = 0; i < rows; i++){
					var rowid = recs[i];
					
					if($("#list").jqGrid('getRowData', rowid).worker_company_seq !=  "0" && $("#list").jqGrid('getRowData', rowid).company_seq != companySeq ){//구인처가 내것이 아니면 삭제 할 수 없다.
						recs.splice(i,1);
					}
				}
			}
			
			rows = recs.length;
			
			if(rows == 0) {
				$.toast({title: '타지점 출역은 구직자는 초기화할 수 없습니다.', position: 'middle', backgroundColor: '#5aa5da', duration:1000 });
				
				return;
			}
		}
		
		//var params = "sel_ilbo_seq=" + recs;
		var params = {
			sel_ilbo_seq : recs
		};
		
		$.ajax({
		        type: "POST",
		         url: "/admin/setIlboWorkerReset",
		        data: params,
		        dataType: 'json',
		        success: function(data) {
		                $("#list").trigger("reloadGrid");
		
		                $.toast({title: '처리 완료(타지점 출역은 구직자초기화에서 제외됨)', position: 'middle', backgroundColor: '#5aa5da', duration:1000 });
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


	//일일대장에서 구직자 소속을 바꿀때
	function  validWorkerCompanyName(value, cellTitle, valref) {//값 ,타이틀 row의 인덱스
		if ( !isSelect && value != '?' ) {
			return [false, "] 지점을 선택하여 엔터를 쳐야 됩니다."];
		}
	
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         				// 선택한 열의 아이디값
	
		if ( value == "?" ) {
			initWorkerName(lastsel);	//구직자 정보도 다 업뎃해 버린다.
			
			$("#list").setCell(lastsel, "worker_company_name","", {background:"#FFFFFF"}) ;
			$("#list").setCell(lastsel, "worker_owner","", {background:"#FFFFFF"}) ;
			$("#list").setCell(lastsel, "ilbo_worker_view","", {background:"#FFFFFF"}) ;
			$("#list").setCell(lastsel, "worker_ilbo","", {background:"#FFFFFF"}) ;
			$("#list").jqGrid('setCell', lastsel, 'worker_company_seq', 0,    '', true);  //다른 셀 바꾸기
					
		}else{
			var workerCompanySeq = $("#list").jqGrid('getRowData', lastsel).worker_company_seq
			initWorkerName(lastsel);	//구직자 정보도 다 업뎃해 버린다.
			
			$("#list").setCell(lastsel, "worker_company_name","", {background:"#80f37b"}) ;
			$("#list").setCell(lastsel, "worker_owner","", {background:"#80f37b"}) ;
			$("#list").setCell(lastsel, "ilbo_worker_view","", {background:"#80f37b"}) ;
		}
		var ilboStatusCode = $("#list").jqGrid('getRowData', lastsel).ilbo_status_code;
		
		// 구인처 상태가 휴지,취소,메모 일땐 수수료, 쉐어료 설정안함 
		if( ilboStatusCode != "STA007" && ilboStatusCode != "STA008" && ilboStatusCode != "STA009" ){
			var unitPrice = $("#list").jqGrid('getRowData', lastsel).ilbo_unit_price
			fn_setIlboFee(lastsel, "ilbo_unit_price", unitPrice);
		}
	
		return [true, ""];
	}

	function initWorkerName(lastsel){
		selectWorkerName = "?";
	
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_name'          , '?',  '', true);  //다른 셀 바꾸기
		$("#list").jqGrid('setCell', lastsel, 'ilbo_assign_type'          , 'O',  '', true);  //다른 셀 바꾸기
		$("#list").jqGrid('setCell', lastsel, 'output_status_code'        , 0, '', true);
		$("#list").jqGrid('setCell', lastsel, 'output_status_name'        , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'worker_seq'                , 0,    '', true);  //다른 셀 바꾸기
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_sex'           , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_phone'         , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_addr'          , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_latlng'        , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_ilgaja_addr'   , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_ilgaja_latlng' , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_jumin'         , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_job_code'      , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_job_name'      , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_barcode'       , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_memo'          , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_bank_code'     , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_bank_name'     , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_bank_account'  , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_bank_owner'    , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_bankbook_scan_yn'    , 0, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_feature'       , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_blood_pressure', 0,    '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_OSH_yn'        , 0,    '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_jumin_scan_yn' , 0,    '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_OSH_scan_yn'   , 0,    '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_status'        , 0,    '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_reserve_push_status'          , 0,   '', true);   //integer 형의 기본값 null 이 안들어 감
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_app_use_status'            , 0,   '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_age'           , 0,    '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_info'          , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_view'          , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'worker_ilbo'               , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_employer_view'        , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'employer_ilbo'             , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'work_ilbo'                 , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'worker_owner'             , 'company', '', true);
		$("#list").jqGrid('setCell', lastsel, 'employer_rating', '0',    '', true);  //다른 셀 바꾸기
		$("#list").jqGrid('setCell', lastsel, 'manager_rating', '0',    '', true);  //다른 셀 바꾸기
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_add_memo', null,    '', true);
	}

	//일일대장에서 구인처(현장) 소속을 바꿀때
	function  validWorkCompanyName(value, cellTitle, valref) {//값 ,타이틀 row의 인덱스
	
		if ( !isSelect && value != '?' ) {
			return [false, "] 지점을 선택하여 엔터를 쳐야 됩니다."];
		}
	
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         				// 선택한 열의 아이디값
		
		var unitPrice = $("#list").jqGrid('getRowData', lastsel).ilbo_unit_price
		fn_setIlboFee(lastsel, "ilbo_unit_price", unitPrice);
	
		return [true, ""];
	}

	function initWork(rowId){
		$("#list").jqGrid('setCell', rowId, 'ilbo_order_code'           , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_order_name'           , null, '', true);
		
		$("#list").jqGrid('setCell', rowId, 'work_seq'                  , 0,    '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_name'            , '?',  '', true);
		$("#list").jqGrid('setCell', rowId, 'work_name'                 , '?',  '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_arrival'         , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_addr_comment'    , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_latlng'          , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_addr'            , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_breakfast_yn'    , 0,    '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_age_min'             , 0,    '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_age'             , 0,    '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_blood_pressure'  , 0,    '', true);
		$("#list").jqGrid('setCell', rowId, 'labor_contract_seq'  , 0,    '', true);
		$("#list").jqGrid('setCell', rowId, 'receive_contract_seq'  , 0,    '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_manager_name'    , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_manager_fax'     , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_manager_phone'   , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_manager_email'   , null, '', true);
	
		$("#list").jqGrid('setCell', rowId, 'ilbo_job_comment'          , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_chief_memo'          , null, '', true);
	
		$("#list").jqGrid('setCell', rowId, 'ilbo_work_info'            , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'ilbo_employer_view'        , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'employer_ilbo'             , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'work_ilbo'                 , null, '', true);
	
		$("#list").jqGrid('setCell', rowId, 'work_owner'             , 'company', '', true);
		$("#list").jqGrid('setCell', rowId, 'addr_edit'          , null, '', true);
		$("#list").jqGrid('setCell', rowId, 'addr_location'          , null, '', true);
	}

	//일일대장에서 구인처/현장을 삭제하는 경우
	function  validWorkName(value, cellTitle, valref) {
		if ( !isSelect && value != '?' ) {
			return [false, "] 구인처 현장 명을 선택하여 엔터를 쳐야 됩니다."];
		}

		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값

		if ( value == "?" ) {
			//  근로자가 선택되어 있는 row 의 구인처를 삭제 할때 구인처가 배정되지 않은 동일한 근로자가 있는지 검색 하여 있으면 구인처를 삭제 하지 못 한다.
			var ilbo_seq = $("#list").jqGrid('getRowData', lastsel).ilbo_seq;     			// 선택된 구직자 순
			var worker_seq = $("#list").jqGrid('getRowData', lastsel).worker_seq;     	// 선택된 구직자 순
			var work_seq = $("#list").jqGrid('getRowData', lastsel).work_seq;     		// 선택된 구직자 순

			var ids = $("#list").jqGrid('getDataIDs');                                // jqGrid 전체 rowId

			for ( var i = 0; i < ids.length; i++ ) {
				var _worker_seq = $("#list").jqGrid('getCell', ids[i], 'worker_seq');
				var _work_seq 	= $("#list").jqGrid('getCell', ids[i], 'work_seq');
				var _ilbo_seq 	= $("#list").jqGrid('getCell', ids[i], 'ilbo_seq');

				if (ilbo_seq != _ilbo_seq && worker_seq > 0 && worker_seq == _worker_seq && _work_seq == 0){
					return [false, "배정되지 않은 동일한 구직자가 있어 수정 할 수 없습니다."];
				}
			}
		//--------------------------------------------------------------------------------------------------------------------------------

			$("#list").jqGrid('setCell', lastsel, 'employer_seq'              , 0,    '', true);
			$("#list").jqGrid('setCell', lastsel, 'employer_name'             , null, '', true);
			$("#list").jqGrid('setCell', lastsel, 'ilbo_employer_feature'     , null, '', true);

			// 현장정보
			initWork(lastsel);
		}
	
		var unitPrice = $("#list").jqGrid('getRowData', lastsel).ilbo_unit_price;
		fn_setIlboFee(lastsel, "ilbo_unit_price", unitPrice);

		return [true, ""];
	}



	//일일대장에서 구인처가 구직자 평가
	function  validIlboStar(value, cellTitle, valref) {//값 ,타이틀 row의 인덱스
		if(value == "0"){
			return [true, ""];
		}
		
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
		var worker_seq 	= $("#list").jqGrid('getRowData', lastsel).worker_seq;     	// 선택된 구직자 순
		var work_seq 		= $("#list").jqGrid('getRowData', lastsel).work_seq;     	// 선택된 구직자 순
		
		if ( worker_seq == '0' ) {
			return [false, "] 구직자가 배정되지 않았습니다."];
		}
	
		if ( work_seq == '0' ) {
			return [false, "] 구인처가 없습니다."];
		}
	
		return [true, ""];
	}
	
	// 일일대장 경력옵션 수정 valid
	function validCareer(value, cellTitle, valref) {
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
		
		var ilbo_kind_code = $("#list").jqGrid("getRowData", lastsel).ilbo_kind_code;
		
		if(!ilbo_kind_code) {
			return [false, "] 직종을 먼저 선택해주세요."];
		}
		
		for(var i = 0; i < jobListCode.length; i++) {
			if(ilbo_kind_code == jobListCode[i].job_seq) {
				var kind_apply_option = JSON.parse(careerDTO).kind_apply_option;
				
				if(jobListCode[i].job_kind == '2') {
					return [false, "] 경력옵션을 적용할 수 있는 직종이 아닙니다."];
				}
				
				if(kind_apply_option == '0') {
					if(jobListCode[i].job_field == '2') {
						return [false, "] 경력옵션을 적용할 수 있는 직종이 아닙니다."];
					}
				}else if(kind_apply_option == '1') {
					if(jobListCode[i].job_field == '1') {
						return [false, "] 경력옵션을 적용할 수 있는 직종이 아닙니다."];
					}
				}
				
				break;
			}
		}

		return [true, ""];
	}
	
	//구직자가 구인처 평가
	function  validIlboEvaluateGrade(value, cellTitle, valref) {//값 ,타이틀 row의 인덱스
		if(value == "0"){
			return [true, ""];
		}
		
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
		var worker_seq 	= $("#list").jqGrid('getRowData', lastsel).worker_seq;     	// 선택된 구직자 순
		var work_seq 		= $("#list").jqGrid('getRowData', lastsel).work_seq;     	// 선택된 구직자 순
		var seq = $("#list").jqGrid('getRowData', lastsel).seq;     	// 선택된 구직자 순
		
		
		if(seq == '0'){
			return [false, "구직자가 평가하지 않은 현장입니다.\n구직자가 평가한 현장만 수정 할 수 있습니다."];
		}
		if ( worker_seq == '0' ) {
			return [false, "] 구직자가 배정되지 않았습니다."];
		}
	
		if ( work_seq == '0' ) {
			return [false, "] 구인처가 없습니다."];
		}
	
		return [true, ""];
	}


	//ilboList 에서만 해당
	function selectOptIlboList(optId, seq, msg, e , optHTML) {
		var $opt_layer = $('#opt_layer');
		var _display;
	
		if ( !e ) e = window.Event;
		var pos = abspos(e);
	
		
		if ( $("#"+ optId).css("display") == 'block' ){
			closeOpt();
		}
		
		writeOpt2(optHTML);
	
		//$opt_layer.find('.bt_wrap').css('display', 'none');
		//_display = $opt_layer.css('display') == 'none'?'block':'none';
			
		$opt_layer.css('left', (pos.x - 100) +"px");
		$opt_layer.css('top', (pos.y-20) +"px");
	
		$opt_layer.css('display', 'block');
		$("#"+ optId).css('display', 'block');
		
		$('#code_log_layer').html("");
		$('#code_log_layer').css('display', 'none');
	}


	//일보 행 삭제
	function ilboRowDel( recs ,type){
		var rows = recs.length;
		
		if( rows < 1 ){
			alert("최소 1개 이상 선택하셔야합니다.");
			return;
		}
	
		if(type == 0){// ilboList 에서 선택해서 삭제를 클릭할때
			if(authLevel > 0){//최고 관리자가 아니면
				for (var i = 0; i < rows; i++){
					var rowid = recs[i];
				
					if($("#list").jqGrid('getRowData', rowid).worker_company_seq !=  "0" && $("#list").jqGrid('getRowData', rowid).company_seq != companySeq ){//구인처가 내것이 아니면 삭제 할 수 없다.
						recs.splice(i,1);
					}
				}
			}
		
			rows = recs.length;
			
			if(rows == 0) {
				$.toast({title: '타지점 출역은 삭제할 수 없습니다.', position: 'middle', backgroundColor: '#5aa5da', duration:1000 });
				
				return;
			}
		}
	
		//var params = "sel_ilbo_seq=" + recs;
		var params = {
			sel_ilbo_seq : recs
		};

		$.ajax({
			type: "POST",
			url: "/admin/removeIlboInfo",
			data: params,
			dataType: 'json',
			success: function(data) {
				if( data.code != '0000' ){
					alert(data.message);
					return;
				}
				$("#list").trigger("reloadGrid");

				$.toast({title: '처리 완료(타지점 출역은 삭제에서 제외됨)', position: 'middle', backgroundColor: '#5aa5da', duration:1000 });
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

//grid 가 완전히 뿌려진 다음 사용 한다.

	function isMyIlbo(rowId){
		var workCompanySeq = $("#list").jqGrid('getRowData', rowId).company_seq;			//일의 소유 회사 seq					
		var workerCompanySeq = $("#list").jqGrid('getRowData', rowId).worker_company_seq;

		if(	companySeq == workCompanySeq || workerCompanySeq == 0){
			return true;
		}else{
			return false;
		}
	}

	function isIlboShareObject(rowObject){
		if(	rowObject.worker_company_seq == rowObject.work_company_seq){
			return false;
		}else{
			return true;
		}
	}
	
	function isIlboShare(rowId){
		var workerCompanySeq = $("#list").jqGrid('getRowData', rowId).worker_company_seq;
		var workCompanySeq 	= $("#list").jqGrid('getRowData', rowId).work_company_seq;
		if(	workerCompanySeq == workCompanySeq || workerCompanySeq == '0'){
			return false;
		}else{
			return true;
		}
	}
	
	// my 구직자 여부
	function isMyWorker(rowObject){
		if(authLevel == "0"){
			return true;
		}
		if(	rowObject.worker_company_seq == companySeq || rowObject.worker_company_seq == "0"){
			return true;
		}else{
			return false;
		}
	}
	
	//ajax 처리 시간 때문에 나눈 function
	function fn_writeReady(params) {
		var text = '';
			
		params = removeDuplicatesArray(params);
			
		for(var i = 0; i < params.length; i++) {
			text = '<input type="hidden" name="worker_seq" value="' + params[i].worker_seq + '">';
			text += '<input type="hidden" name="worker_seq_array" value="' + params[i].worker_seq + '">';
			  
			$("#jobAlimPopFrm").append(text);
		}
			 
		text = '<input type="hidden" name="worker_length" value="' + params.length + '">';
			 
		$("#jobAlimPopFrm").append(text);
			 
		$("#jobAlimPopFrm").one("submit", function() {
			var option = 'width=700, height=400, top=250, left=600, resizable=no, status=no, menubar=no, toolbar=no, scrollbars=no, location=no';
			
			window.open('','pop_target',option);
			 
			this.action = '/admin/jobAlim/jobAlimWrite';
			this.method = 'POST';
			this.target = 'pop_target';
		}).trigger("submit");	  
			 
		$("#jobAlimPopFrm").remove();
	}
	
	function removeDuplicatesArray(arr) {
		var tempArr = [];
	
		for (var i = 0; i < arr.length; i++) {
			if (tempArr.length == 0) {
				tempArr.push(arr[i]);
			} else {
				var duplicatesFlag = true;
				
				for (var j = 0; j < tempArr.length; j++) {
					if (tempArr[j].worker_seq == arr[i].worker_seq) {
						duplicatesFlag = false;
						
						break;
					}
				}
				
				if (duplicatesFlag) {
					tempArr.push(arr[i]);
				}
			}
		}
		
		return tempArr;
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
				
				if((tr_etc1 == '7' && rowData.ilbo_worker_phone.replace(/\s/g, "").length != 0 && rowData.ilbo_worker_phone != '?') 
						|| (tr_etc1 == '8' && rowData.manager_phone.replace(/\s/g, "").length != 0 && rowData.manager_phone != '?')
						|| (tr_etc1 == '9' && rowData.ilbo_work_manager_phone.replace(/\s/g, "").length != 0 && rowData.ilbo_work_manager_phone != '?')) {
					paramData = {
						worker_name : rowData.ilbo_worker_name, 
						worker_phone : rowData.ilbo_worker_phone,
						manager_name : rowData.manager_name,
						manager_phone : rowData.manager_phone,
						ilbo_work_manager_name : rowData.ilbo_work_manager_name,
						ilbo_work_manager_phone : rowData.ilbo_work_manager_phone,
						worker_seq : rowData.worker_seq,
						company_name : rowData.company_name,
						employer_name : rowData.employer_name,
						menuFlag : "I",
						ilbo_seq : rowData.ilbo_seq
					}
					
					params.push(paramData);
				}
			}
		}
		
		$("#smsPopFrm").append('<input type="hidden" name="tr_etc1" value="' + tr_etc1 + '" />');
		$("#smsPopFrm").append('<input type="hidden" name="menuFlag" value="I" />');
		
		return params;
	}

	//중복 worker 표시
	function checkDuplicateWorker(){
		var worker_seq_array = [], ilbo_seq_array=[]; // 유니크한 구직자를 구하기 위함.
		var ids = $("#list").getDataIDs();
  
		$.each(ids, function(idx, rowId) {
			rowData = $("#list").getRowData(rowId);
			
			if(rowData.worker_seq == 0){
				$("#list").jqGrid('setCell', rowId, "ilbo_worker_name"    ,''   ,{color:''});	//색깔이 없도록 
			}else{
				ilbo_seq_array.push(rowData.ilbo_seq);
				worker_seq_array.push(rowData.worker_seq);
			}
		});
		
		//	중복 구직자 폰트색 변경
		$.each(worker_seq_array, function(idx, value) {
		//	첫번째 위치와 마지막 위치가 다르면 중복		
			if(worker_seq_array.indexOf(value) !== worker_seq_array.lastIndexOf(value)){
				$("#list").jqGrid('setCell', ilbo_seq_array[idx], "ilbo_worker_name"    ,''   ,{color:'red'}); //빨간색
			}else{
				$("#list").jqGrid('setCell', ilbo_seq_array[idx], "ilbo_worker_name"    ,''   ,{color:''});
			}
		});
	}

	function fn_setIlboFee(rowId, cellname, value, isJobChange){
		/*
		 * isJobChange : 일일대장에서 직종변경 여부
		 * ngj 2022-01-27
		 * */
		var _ilbo_unit_price 	= parseInt($("#list").jqGrid('getRowData', rowId).ilbo_unit_price);		//단가
		var _ilbo_fee        		= parseInt($("#list").jqGrid('getRowData', rowId).ilbo_fee);				//수수료
		var _share_fee	   		= parseInt($("#list").jqGrid('getRowData', rowId).share_fee);			//쉐어료
		var _ilbo_deduction  	= parseInt($("#list").jqGrid('getRowData', rowId).ilbo_deduction);
		var _counselor_fee  	= parseInt($("#list").jqGrid('getRowData', rowId).counselor_fee);		//상담사 피
		var _ilbo_kind_code = $("#list").jqGrid('getRowData', rowId).ilbo_kind_code;
		var _job_work_fee;
		var _job_worker_fee;
		var _ilbo_pay;
		
		if(cellname == "ilbo_unit_price"){
			_ilbo_unit_price = value;
			
			var feeFlag;
			
			if(isJobChange) {
				//feeFlag = feeArray.includes(jobSeqArray[0].jobSeq);
				_job_work_fee = jobSeqArray[0].job_work_fee * 1;
				_job_worker_fee = jobSeqArray[0].job_worker_fee * 1;
				
				$('#list').jqGrid('setCell', rowId, "job_work_fee", _job_work_fee);
				$('#list').jqGrid('setCell', rowId, "job_worker_fee", _job_worker_fee);
			}else {
				//feeFlag = feeArray.includes(_ilbo_kind_code);
				_job_work_fee = $("#list").jqGrid("getRowData", rowId).job_work_fee * 1;
				_job_worker_fee = $("#list").jqGrid("getRowData", rowId).job_worker_fee * 1;
				
			}
			
			
			//구직자(일당) 계산식
			_ilbo_pay = (_ilbo_unit_price / ( (100 + _job_work_fee) / 100 ) );
			// 1000원이하 금액 올림처리
			_ilbo_pay = Math.ceil(_ilbo_pay / 1000) * 1000;
			
			// 수수료 자동계산
			if( _job_worker_fee != "" && _job_worker_fee > 0 ){
				var _work_fee = _ilbo_unit_price - _ilbo_pay;
				var _worker_fee = (_ilbo_pay * (_job_worker_fee / 100));
				_worker_fee = Math.floor(_worker_fee / 100) * 100;
				_ilbo_fee = (_work_fee + _worker_fee);
				
				var _fee_info = "(구인자 " + comma(_work_fee) + "원+구직자 " + comma(_worker_fee) + "원)";
				var _pay_info = "(구직자 소개요금 최대 1% 포함)";
				$("#list").jqGrid('setCell', rowId, 'fee_info', _fee_info, '', true);
				$("#list").jqGrid('setCell', rowId, 'pay_info', _pay_info, '', true);
				$("#list").jqGrid('setCell', rowId, 'worker_fee', _worker_fee, '', true);
				$("#list").jqGrid('setCell', rowId, 'work_fee', _work_fee, '', true);
			}else{
				_ilbo_fee = (_ilbo_unit_price - _ilbo_pay);
				var _fee_info = "(구인자 " + comma(_ilbo_fee) + "원)";
				$("#list").jqGrid('setCell', rowId, 'fee_info', _fee_info, '', true);
				$("#list").jqGrid('setCell', rowId, 'pay_info');
				$("#list").jqGrid('setCell', rowId, 'work_fee', _ilbo_fee, '', true);
				$("#list").jqGrid('setCell', rowId, 'worker_fee', 0, '', true);
			}
			_share_fee = 0;
			
			var _workOwner  = $("#list").jqGrid('getRowData', rowId).work_owner;
			
			// 다른지점과 공유한 일자리면 수수료를 나눈다.
			if( isIlboShare(rowId) ){
				//여기
				if( _workOwner == "company" ){
					_ilbo_fee = _ilbo_fee / 2; 
					_share_fee = _ilbo_fee;
				}else{
					var _totalFee = _ilbo_fee;
					_ilbo_fee = _totalFee * 0.6; 
					_share_fee = _totalFee * 0.4;
				}
			}
			
			$("#list").jqGrid('setCell', rowId, 'ilbo_unit_price', _ilbo_unit_price, '', true);
			$("#list").jqGrid('setCell', rowId, 'ilbo_fee', _ilbo_fee, '', true);
			$("#list").jqGrid('setCell', rowId, 'share_fee', _share_fee, '', true);
			$("#list").jqGrid('setCell', rowId, 'ilbo_pay', _ilbo_pay);
		}
		else if(cellname == "ilbo_fee"){
			_ilbo_fee = value;
		}else if(cellname == "share_fee"){
			_share_fee = value;
		}else if(cellname == "ilbo_deduction"){
			_ilbo_deduction = value;
		}else if(cellname == "counselor_fee"){
			_counselor_fee = value;
		}
		
		if(cellname != "counselor_fee"){
			var work_owner  = $("#list").jqGrid('getRowData', rowId).work_owner;
			if(work_owner == "company"){ //상담사일 경우.
				_counselor_fee = 0;
			}else{
				var counselor_rate  = parseInt($("#list").jqGrid('getRowData', rowId).counselor_rate);
				_ilbo_fee = parseInt(_ilbo_fee);
				_share_fee = parseInt(_share_fee);
				_counselor_fee = (_ilbo_fee + _share_fee) * counselor_rate / 100;
			}
			$("#list").jqGrid('setCell', rowId, 'counselor_fee', _counselor_fee, '', true);
		}
		
		//합계를 위해 값이 없을 경우 0 으로 셋팅 한다.
		//if ( isNaN(_ilbo_unit_price) ) 	_ilbo_unit_price 	= 0;
		//if ( isNaN(_ilbo_fee) )				_ilbo_fee        	= 0;
		//if ( isNaN(_ilbo_deduction ) ) 	_ilbo_deduction  	= 0;
		//if ( isNaN(_share_fee) ) 			_share_fee      	= 0;
		//if ( isNaN(_counselor_fee) ) 		_counselor_fee    = 0;
                          	
		// 일당 계산
		//var _pay = _ilbo_unit_price - _ilbo_fee  -_share_fee  + _ilbo_deduction ;
	
		//$("#list").jqGrid('setCell', rowId, 'ilbo_unit_price', _ilbo_unit_price, '', true);
		//$("#list").jqGrid('setCell', rowId, 'ilbo_fee', _ilbo_fee, '', true);
		//$("#list").jqGrid('setCell', rowId, 'share_fee', _share_fee, '', true);
		$("#list").jqGrid('setCell', rowId, 'counselor_fee', _counselor_fee, '', true);
		//$("#list").jqGrid('setCell', rowId, 'ilbo_pay', _pay);			//일당	
	}
	
	//footer 적용
	function setFooter() {
		var priceSum		= $("#list").jqGrid('getCol','ilbo_unit_price',false, 'sum');
		var feeSum 			= $("#list").jqGrid('getCol','ilbo_fee',false, 'sum');
		var deductionSum	= $("#list").jqGrid('getCol','ilbo_deduction',false, 'sum');
		var counselorSum 		= $("#list").jqGrid('getCol','counselor_fee',false, 'sum');
		var paySum 			= $("#list").jqGrid('getCol','ilbo_pay',false, 'sum');
		var shareSum 		= $("#list").jqGrid('getCol','share_fee',false, 'sum');
		
		if(authLevel != 0) {
			var gridLen = $("#list").jqGrid('getRowData').length;
			
			var ids = $("#list").getDataIDs();
			$.each(ids, function(idx, rowId) {
				rowData = $("#list").getRowData(rowId);
				var worker_company_seq = rowData.worker_company_seq;
				var work_company_seq = rowData.work_company_seq;
				
				if(worker_company_seq != sessionCompanySeq && work_company_seq != sessionCompanySeq) {
					priceSum -= (rowData.ilbo_unit_price * 1);
					feeSum -= (rowData.ilbo_fee * 1); 
					paySum -= (rowData.ilbo_pay * 1);
					shareSum -= (rowData.share_fee * 1);
				}
			});
		}
		//상단 합계적용
		$("#totalPrice").html(numberWithCommas(priceSum));
  
		$("#totalFee").html(numberWithCommas(feeSum) );
		$("#totalShareFee").html(numberWithCommas(shareSum) );
  
		$("#totalcounselor").html(numberWithCommas(counselorSum) );
  
  //$("#totalShare").html( numberWithCommas(shareSum) );
  //$("#totalDeduction").html( numberWithCommas(deductionSum) );
		$("#totalPay").html( numberWithCommas(paySum) );
  //footer 적용
		$("#list").jqGrid("footerData", "set", {
			ilbo_job_comment : "합계",
			ilbo_unit_price	: priceSum,
			ilbo_fee : feeSum,
			share_fee : shareSum,
			ilbo_deduction : deductionSum,
			counselor_fee : counselorSum,
			ilbo_pay : paySum
		});
	}


	function getCodeStyle(_code) {
		oCode = mCode.get(_code);
  
		var _style = "";

		try {
			_style = " style=\"background-color:#"+ oCode.bgcolor +"; color:#"+ oCode.color +"\"";
		} catch(e) {}
		
		return _style;
	}

	function getCodeColor(_code){
		oCode = mCode.get(_code);
		return "#"+oCode.color;
	}

	function getCodeBgColor(_code){
		oCode = mCode.get(_code);
		return "#"+oCode.bgcolor;
	}
	
	function getCodeBGStyle(_code, isJSON) {
		oCode = mCode.get(_code);

		var _style = "";
	
		try {
			if ( isJSON ) {
				_style = "{'background-color': '#"+ oCode.bgcolor +"'}";  
			} else {
				_style = "style=\"background-color:#"+ oCode.bgcolor +"\"";  
			}
		} catch(e) {}
		
		return _style;
	}
	
	function mapResponse(rowid,cAddr,clatlng) {
		$("#list").jqGrid('setCell', rowid,'ilbo_work_addr', cAddr, '', true);  //다른 셀 바꾸기
		$("#list").jqGrid('setCell', rowid,'ilbo_work_latlng', clatlng, '', true);  //다른 셀 바꾸기
		$("#list").jqGrid('setCell', rowid,'addr_edit', '1', '', true);  //다른 셀 바꾸기
	}

	function fn_reload() {
		$("#list").trigger("reloadGrid"); 
	}

	//현장 정보  option 세팅
	function fn_workInfoOpt(ilbo_seq, worker_seq, txt, event, ilbo_order_code) {
/*
	if(!isWorkCompany($("#list").jqGrid('getRowData', ilbo_seq).company_seq, $("#list").jqGrid('getRowData', ilbo_seq).work_company_seq)){
		return; 
	}
	*/
		optHTML = "<div id=\"order_layer\" class=\"bt_wrap single\" style=\"width: 260px; display: none; background-color: #fff;\">";
		optHTML += optOrder.replace(/<<ILBO_SEQ>>/gi, ilbo_seq).replace(ilbo_order_code +" bt", ilbo_order_code +" bt_on");
		optHTML += clsHTML;
		optHTML += "<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('order_layer', '"+ ilbo_seq +"', 'ilbo_order_code', 0, 'ilbo_order_name', '' ,'0', '500');\"> 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ ilbo_seq +"', '500', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
		
		selectOptIlboList('order_layer', worker_seq, txt, event, optHTML);
	}

	//order 를 받은 곳
	function fn_workOrderInfoOpt(ilbo_seq, worker_seq, txt, event, ilbo_work_order_code) {
		if(!isWorkCompany($("#list").jqGrid('getRowData', ilbo_seq).company_seq, $("#list").jqGrid('getRowData', ilbo_seq).work_company_seq)){
			return; 
		}
	
		optHTML = "<div id=\"workOrder_layer\" class=\"bt_wrap single\" style=\"width: 260px; display: none; background-color: #fff;\">";
		optHTML += optWorkOrder.replace(/<<ILBO_SEQ>>/gi, ilbo_seq).replace(ilbo_work_order_code +" bt", ilbo_work_order_code +" bt_on");
		optHTML += clsHTML;
		optHTML += "<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('workOrder_layer', '"+ ilbo_seq +"', 'ilbo_work_order_code', 0, 'ilbo_work_order_name', '' ,'0', 'ORD');\"> 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ ilbo_seq +"', 'ORD', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
		
		selectOptIlboList('workOrder_layer', worker_seq, txt, event , optHTML);
	}

	//공개여부 옵션
	function fn_useInfoOpt(ilbo_seq, worker_seq, txt, event, ilbo_use_code) {
/*	
	if(!isWorkCompany($("#list").jqGrid('getRowData', ilbo_seq).company_seq, $("#list").jqGrid('getRowData', ilbo_seq).work_company_seq)){
		return; 
	}
	*/
//		var laborContract = $("#list").jqGrid('getRowData', ilbo_seq).labor_contract;
//		
//		if(laborContract == '2') {
//			return false;
//		}		
		
		optHTML = "<div id=\"use_layer\" class=\"bt_wrap single\" style=\"width: 260px; display: none; background-color: #fff;\">";
		optHTML += optUse.replace(/<<ILBO_SEQ>>/gi, ilbo_seq).replace(ilbo_use_code +" bt", ilbo_use_code +" bt_on");
		optHTML += clsHTML;
		optHTML += "<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('use_layer', '"+ ilbo_seq +"', 'ilbo_use_code', 0, 'ilbo_use_name', '' ,'0', 'USE');\"> 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ ilbo_seq +"', 'USE', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
	
		selectOptIlboList('use_layer', worker_seq, txt, event, optHTML);
	}
	
	// AI 배정 옵션
	function fn_autoStatusOpt(ilbo_seq, worker_seq, output_status_code, ilbo_status_code, use_yn, ilbo_date, ilbo_work_arrival, txt, event, auto_status) {
		
		var _url = "/admin/getAutoIlboStatus";
		var _data = {
				ilbo_seq: ilbo_seq
		};
		commonAjax(_url, _data, true, function(data) {
			//successListener
			if( data.code != "0000"){
				toastFail(data.message);
				return;
			}
			if( auto_status == "null" ){
				auto_status = null;
			}
			if( data.autoIlboStatus != auto_status ){
				toastFail("AI배정 상태가 변경되었습니다. 새로고침 후 이용해주세요.");
				return;
			}
			
			optHTML = "<div id=\"auto_status_layer\" class=\"bt_wrap single\" style=\"width: 260px; display: none; background-color: #fff;\">";
			if(auto_status == '0' || auto_status == '1') {
				optHTML += "<div class=\"bt\" style=\"margin-left: 42%;\"><a href=\"JavaScript:fn_autoStatusUpdate('auto_status_layer', " + ilbo_seq + ", " + worker_seq + ", '" + output_status_code + "', '" + ilbo_status_code + "', " + use_yn + ", '" + ilbo_date + "', '" + ilbo_work_arrival + "', '중지▣', " + 3 + ");\" style=\"background:#5aa5da; color:#FFFFFF;\"> 중지▣ </a></div>";
			}else {
				optHTML += "<div class=\"bt\" style=\"margin-left: 42%;\"><a href=\"JavaScript:fn_autoStatusUpdate('auto_status_layer', " + ilbo_seq + ", " + worker_seq + ", '" + output_status_code + "', '" + ilbo_status_code + "', " + use_yn + ", '" + ilbo_date + "', '" + ilbo_work_arrival + "', '시작▶', " + 0 + ");\" style=\"background:#5aa5da; color:#FFFFFF;\"> 시작▶ </a></div>";
			}
			optHTML += clsHTML;
			optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:fn_showAutoStatusLog('" + ilbo_seq + "')\"> 로그보기 </a></div></div>";
			optHTML += "</div>";
		
			selectOptIlboList('auto_status_layer', '', txt, event, optHTML);
		}, function(data) {
			//errorListener
			toastFail("오류가 발생했습니다.3");
		}, function() {
			//beforeSendListener
		}, function() {
			//completeListener
		});
	}
	
	function fn_autoStatusUpdate(divId, ilbo_seq, worker_seq, output_status_code, ilbo_status_code, use_yn, ilbo_date, ilbo_work_arrival, auto_status_name, auto_status) {
		var now = new Date();
		var nowTime = (now.getHours() + '' + now.getMinutes()) * 1;
		var today = getDateString(now)
		ilbo_work_arrival = ilbo_work_arrival == 'null' ? '' : ilbo_work_arrival;
		if(auto_status == '0') {
			// 구직자가 배정된 상태(예약, 출발, 도착, 완료)
			if(worker_seq > 0) {
				if(output_status_code == '100003' || output_status_code == '100005' || output_status_code == '100014' || output_status_code == '100015') {
					alert("본 출역은 AI배정을 시작할 수 없습니다.");
					
					return false;
				}
			}
			
			// 현장 상태 값이 취소, 휴지, 메모 또는 미사용일 때
			if(ilbo_status_code == 'STA007' || ilbo_status_code == 'STA008' || ilbo_status_code == 'STA009' || use_yn == '0') {
				alert("본 출역은 AI배정을 시작할 수 없습니다.");
				
				return false;
			}
			
			// 작업일이 경과된 일자리일 때
			if(today > ilbo_date) {
				alert("본 출역은 AI배정을 시작할 수 없습니다.");
				
				return false;
			}
			
			// 시간이 경과된 일자리일 때
			if(today == ilbo_date){
				if(nowTime > ilbo_work_arrival) {
					alert("본 출역은 AI배정을 시작할 수 없습니다.");
					
					return false;
				}
			}
		}
		
		var params = {
			ilbo_seq : ilbo_seq 
			, auto_status : auto_status
			, auto_status_name : auto_status_name
		};
		
		$("#"+ divId +" > .bt_on").each(function(index) {
      		$(this).removeClass("bt_on");
      		$(this).addClass("bt");
    	});

	  	$.ajax({
			type: "POST",
	        url: "/admin/setAutoIlboStatus",
	       	data: params,
	   		dataType: 'json',
	    	success: function(data) {
				//새로고침을 안하기 위해서
	    		if(data.code == "0000") {
	    			$("#list").jqGrid('setCell', ilbo_seq, "auto_status", auto_status, '', true);
	    			$("#list").jqGrid('setCell', ilbo_seq, "auto_status_name", auto_status_name, '', true);
	    			$("#list").jqGrid('setCell', ilbo_seq, "auto_status_info", auto_status_name, '', true);
	    			
	    			closeOpt();
	    		}else{
	    			alert(data.message);
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
	  	
	  	//로그보기 레이어 숨기기
	 	$('#code_log_layer').html("");
	  	$('#code_log_layer').css('display', 'none');
	}
	
	function fn_showPriceLog(list, element){
		$('#code_log_layer').html("");
		var _display = $('#code_log_layer').css('display');
		if( _display == "block" ){
			$('#code_log_layer').css('display', 'none');
			return;
		}
		
		var divX = $(element).offset().left;
		var divY = $(element).offset().top;
		var divHeight = $(element).height();
		
		$('#code_log_layer').css('left', divX+"px");
		$('#code_log_layer').css('top', (divY + divHeight - $("#gnb").height() ) +"px");
		
		var _html = "<table class='bd-form s-form'>";
		for(var i = 0; i < list.length; i++) {
			_html += "<tr>";  
			_html += "	  <td style='padding:5px;'>";
			_html += 	  	  list[i].user_id;
			_html += "    </td>";
			_html += "    <td  style='padding:5px;'>";
			_html += 		  list[i].cellNameKr;
			_html += "	  </td>";
			_html += "    <td  style='padding:5px;'>";
			_html += 		  list[i].after_price;
			_html += "	  </td>";
			_html += "    <td  style='padding:5px;'>";
			_html += 		  list[i].reg_date;
			_html += "    </td>";
			_html += "</tr>";
		}
		
		_html+="</table>";
		_html+="<div class='bt_close'>";
		_html+="	<div class='bt_c1'><a href='JavaScript:closeOpt()'> 닫기 X </a></div>";
		_html+="</div>";
		$('#code_log_layer').html(_html);
		$('#code_log_layer').css('display', 'block');
	}
	
	function fn_showAutoStatusLog(ilbo_seq) {
		$('#code_log_layer').html("");
		var _display;

		_display = $('#code_log_layer').css('display') == 'none'?'block':'none';

		var divEl = $("#opt_layer");
		var divX = divEl.offset().left;
		var divY = divEl.offset().top;
		var divHeight = divEl.height();
		
		$('#code_log_layer').css('left', divX+"px");
		$('#code_log_layer').css('top', (divY + divHeight - $("#gnb").height() ) +"px");

		var _data = {
			ilbo_seq : ilbo_seq
		};
		   
		$.ajax({
			type	: "POST",
			url		: "/admin/getAutoIlboLog",
			data	: _data,
			dataType: 'json',
			success: function(data) {
				var _html = "";
				
				if(data.code == '0000') {
					if(data.logList != null && data.logList.length > 0) {
						_html = "<table class='bd-form s-form'>";
			    		
			    		for(var i = 0; i < data.logList.length; i++) {
			    			var logList = data.logList;
			    			
			    			_html += "<tr>";  
			    			_html += "	  <td style='padding:5px;'>";
			    			_html += 	  	  logList[i].reg_admin;
			    			_html += "    </td>";
			    			_html += "    <td  style='padding:5px;'>";
			    			_html += 		  logList[i].auto_status_name;
			    			_html += "	  </td>";
			    			_html += "    <td  style='padding:5px;'>";
			    			_html += 		  logList[i].auto_send_worker_count;
			    			_html += "	  </td>";
			    			_html += "    <td  style='padding:5px;'>";
			    			_html += 		  logList[i].reg_date;
			    			_html += "    </td>";
			    			_html += "</tr>";
			    		}
			    		
			    		_html+="</table>";
					}
					
					$('#code_log_layer').html(_html);
			    	$('#code_log_layer').css('display', _display);
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
	}
	
	//현장 상태
	function fn_staInfoOpt(ilbo_seq, worker_seq, txt, event, ilbo_status_code) {
/*	

	if(!isWorkCompany($("#list").jqGrid('getRowData', ilbo_seq).company_seq, $("#list").jqGrid('getRowData', ilbo_seq).work_company_seq)){
		return; 
	}
	*/
		optHTML = "<div id=\"sta_layer\" class=\"bt_wrap single\" style=\"width: 260px; display: none; background-color: #fff;\">";
		optHTML += optSta.replace(/<<ILBO_SEQ>>/gi, ilbo_seq).replace(ilbo_status_code +" bt", ilbo_status_code +" bt_on");
		optHTML += clsHTML;
		optHTML += "<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('sta_layer', '"+ilbo_seq+"', 'ilbo_status_code', 0, 'ilbo_status_name', '' ,'0', 'STA');\"> 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ ilbo_seq +"', 'STA', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
	
		selectOptIlboList('sta_layer', worker_seq, txt, event, optHTML);
	}
	
	//구직자 정보 option 세팅
	function fn_workerInfoOpt(ilbo_seq, work_seq, txt, event, output_status_code) {
//		if(!isWorkerCompany($("#list").jqGrid('getRowData', ilbo_seq).company_seq, $("#list").jqGrid('getRowData', ilbo_seq).worker_company_seq)){
//			return;
//		}
		
		optHTML = "<div id=\"output_layer\" class=\"bt_wrap single\" style=\"width: 240px; display: none; background-color: #fff;\">";
		optHTML += optOupput.replace(/<<ILBO_SEQ>>/gi, ilbo_seq).replace(output_status_code +" bt", output_status_code +" bt_on");
		optHTML += clsHTML;
		optHTML += "<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('output_layer', '"+ ilbo_seq +"', 'output_status_code', 0, 'output_status_name', '' ,'0', '100');\"> 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ ilbo_seq +"', '100', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
		
		selectOptIlboList('output_layer', work_seq, txt, event, optHTML);
	}
	
	//구직자 상태 option 세팅
	function fn_workerStatusInfoOpt(ilbo_seq, work_seq, txt, event, ilbo_worker_status_code) {
//		if(!isWorkerCompany($("#list").jqGrid('getRowData', ilbo_seq).company_seq, $("#list").jqGrid('getRowData', ilbo_seq).worker_company_seq)){
//			return;
//		}
		
		optHTML = "<div id=\"wsc_layer\" class=\"bt_wrap single\" style=\"width: 240px; display: none; background-color: #fff;\">";
		optHTML += optWorkerStatus.replace(/<<ILBO_SEQ>>/gi, ilbo_seq).replace(ilbo_worker_status_code +" bt", ilbo_worker_status_code +" bt_on");
		optHTML += clsHTML;
		optHTML += "<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('wsc_layer', '"+ilbo_seq+"', 'ilbo_worker_status_code', 0, 'ilbo_worker_status_name', '상태' ,'0', 'WSC');\"> 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ ilbo_seq +"', 'WSC', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
		
		selectOptIlboList('wsc_layer', work_seq, txt, event, optHTML);
	}
	
	//직종 정보 option 세팅
	function fn_kindCodeOpt(rowId) {
/*	
	if(!isWorkCompany($("#list").jqGrid('getRowData', rowId).company_seq, $("#list").jqGrid('getRowData', rowId).work_company_seq)){
		return; 
	}
	*/
		var laborContract = $("#list").jqGrid('getRowData', rowId).labor_contract;
		
		if(laborContract == '2') {
			return false;
		}
		
		var workArrival = $("#list").jqGrid('getRowData', rowId).ilbo_work_arrival;
		var workFinish = $("#list").jqGrid('getRowData', rowId).ilbo_work_finish;
	
		if((workArrival == null || workArrival == "") || (workArrival == null || workArrival == "") ){
			alert("도착시간과 마감시간을 먼저 입력하세요.");
			return;
		}
		
		if((workFinish*1) - (workArrival*1) < 200){
			alert("도착시간과 종료시간은 2시간 이상 되어야 합니다.");
			return;
		}
		
		if(screen.width < 600) {
			$("#popup-layer").css("margin-top", "-709px");
		}
		
		if(screen.height < 800) {
			$("#popup-layer").css("margin-top", "-709px");
		}
		
		openIrPopupRe('popup-layer');
		$("#jobRowId").val(rowId);
		jobCalInit(0);
	}

	//노임수령 정보 option 세팅
	function fn_incomeCodeOpt(ilbo_seq, work_seq, txt, event, ilbo_income_code) {
	
//	if(!isWorkCompany($("#list").jqGrid('getRowData', ilbo_seq).company_seq, $("#list").jqGrid('getRowData', ilbo_seq).work_company_seq)){
//		return; 
//	}
	
		optHTML = "<div id=\"income_layer\" class=\"bt_wrap single\" style=\"width: 280px; display: none; background-color: #fff;\">";
		optHTML += optIncome.replace(/<<ILBO_SEQ>>/gi, ilbo_seq).replace(ilbo_income_code +" bt", ilbo_income_code +" bt_on");
		optHTML += clsHTML;
		optHTML +="<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('income_layer', '"+ilbo_seq+"', 'ilbo_income_code', 0, 'ilbo_income_name', '' ,'0', '300');\" > 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ ilbo_seq +"', '300', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
		
		selectOptIlboList('income_layer', work_seq, txt, event, optHTML);
	}
	
	//노임지급 정보 option 세팅
	function fn_payCodeOpt(ilbo_seq, worker_seq, txt, event, ilbo_pay_code) {
/*
	if(!isWorkCompany($("#list").jqGrid('getRowData', ilbo_seq).company_seq, $("#list").jqGrid('getRowData', ilbo_seq).work_company_seq)){
		return; 
	}
*/
		$("#div_pay_"+ilbo_seq).click();
		
		optHTML = "<div id=\"pay_layer\" class=\"bt_wrap single\" style=\"width: 220px; display: none; background-color: #fff;\">";
		optHTML += optPay.replace(/<<ILBO_SEQ>>/gi, ilbo_seq).replace(ilbo_pay_code +" bt", ilbo_pay_code +" bt_on");
		optHTML += clsHTML;
		optHTML +="<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('pay_layer', '"+ilbo_seq+"', 'ilbo_pay_code', 0, 'ilbo_pay_name', '' ,'0' , '200');\" > 초기화 </a></div></div>";
		optHTML += "<div class=\"bt_left\"><div class=\"bt\"><a href=\"JavaScript:void(0);\" onclick=\"JavaScript:showCodeLog('"+ ilbo_seq +"', '200', event);\"> 로그보기 </a></div></div>";
		optHTML += "</div>";
		
		selectOptIlboList('pay_layer', worker_seq, txt, event, optHTML);
	}

	// 일일대장 일일대장지점 수정할 때 사용
	function  validCompanyName(value, cellTitle, valref) {
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
	  
		if ( !isSelect) {
			return [false, "] 지점을 선택하여 엔터를 쳐야 됩니다."];
	    }
	  
		return [true, ""];
	}

	function fn_editOtions_companyName(e, cm){
    	$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
        $(e).autocomplete({
            source: function (request, response) {
            	$.ajax({
                	url: "/admin/getCompanyNameList", type: "POST", dataType: "json",
                    data: { term: request.term, srh_use_yn: 1 },
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
			},
			minLength: 2,
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

	function fn_editOtions_workerCompanyName(e, cm){
		
		var _url = "/admin/getCompanyNameList2";
		
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
		beforeWorkerCompanySeq = $("#list").jqGrid('getRowData', lastsel).worker_company_seq;
		
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
			},
            minLength: 2,
            focus: function (event, ui) {
            	return false;
			},
            select: function (event, ui) {
            	
            	lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
            	
                $(e).val(ui.item.label);
                selectWorkerName = ui.item.label;

                //function validWorkerCompanyName 에서 다시 지정된다.
                $("#list").jqGrid('setCell', lastsel, 'worker_company_seq', ui.item.code, '', true);  //다른 셀 바꾸기
                
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

	function fn_editOtions_workCompanyName(e){
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
			},
            minLength: 2,
            focus: function (event, ui) {
            	return false;
			},
            select: function (event, ui) {
            	lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값

                $(e).val(ui.item.label);
                selectWorkerName = ui.item.label;

                //function validWorkerCompanyName 에서 다시 지정된다.
                $("#list").jqGrid('setCell', lastsel, 'work_company_seq', ui.item.code, '', true);  //다른 셀 바꾸기
                
                //현장정보 초기화
                initWork(lastsel);
                isSelect = true;

                return false;
			}
		});
	}

	function fn_editOtions_ilboWorkerName(e, cm){
    	var _url = "/admin/getWorkerNameList";
        
    	lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값

    	beforeWorkerSeq = $("#list").jqGrid('getRowData', lastsel).worker_seq;
    	
        $(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
        $(e).autocomplete({
        	source: function (request, response) {
            	$.ajax({
                	url: _url, type: "POST", 
                    dataType: "json",
                    //data: { term: request.term, srh_use_yn: 1, srh_distinct_yn: 1, start_reg_date: $("#start_ilbo_date").val(), company_seq:$("#list").jqGrid('getRowData', lastsel).company_seq},
                    data: { 
                    	term: request.term, 
                        srh_use_yn: 1,
                        srh_del_yn: 0, 
                        srh_distinct_yn: 1, 
                        start_reg_date: $("#start_ilbo_date").val(),
                        employer_seq:$("#list").jqGrid('getRowData', lastsel).employer_seq,
                        manager_seq:$("#list").jqGrid('getRowData', lastsel).manager_seq,
                        company_seq:$("#list").jqGrid('getRowData', lastsel).worker_company_seq
					},
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
				}).then(function() {
				});
			},
            minLength: 2,
            focus: function (event, ui) {
            	return false;
			},
            select: function (event, ui) {
            	lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
            	selectWorkerName = ui.item.label;
            	isSelect = true;
            	
                if(ui.item.employer_rating > 0){
                	if (!confirm("[경고] "+ selectWorkerName +"님은 구인처에서 알선을 거절한 구직자입니다. \n 무시하고 배정 하시겠습니가?")){    //확인
                
                		$(e).val("?");
                		return false;
        	    	}
                }
                
                if(ui.item.manager_rating > 0){
                	if (!confirm("[경고] "+ selectWorkerName +"님은 구인자(현장메니져)가 알선을 거절한 구직자입니다. \n확인: 무시하고 배정 하시겠습니가?")){    //확인
                		$(e).val("?");
                		return false;
        	    	}
                }
                
				$(e).val(ui.item.worker_name);
                
                $("#list").jqGrid('setCell',lastsel,'output_status_code',	0,'',true);
                $("#list").jqGrid('setCell',lastsel,'output_status_name',	null,'',true);
                // worker 정보 바꾸기
                changeWorker(ui.item);
                isSelect = true;
                
                if( event.keyCode != "13" ){
                	var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
                	$("#list").jqGrid("saveCell", iRow, cm.iCol);
                }
                
                return false;
			}
		});
	}

	function changeWorker(object){
		$("#list").jqGrid('setCell',lastsel,'worker_seq', object.code, '',true);  //다른 셀 바꾸기
		$("#list").jqGrid('setCell',lastsel,'worker_company_seq', object.company_seq, '', true);  //다른 셀 바꾸기
		$("#list").jqGrid('setCell',lastsel,'worker_company_name', object.company_name,	'', true);  //다른 셀 바꾸기
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_sex', object.worker_sex, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_phone', object.worker_phone, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_addr', object.worker_addr, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_latlng', object.worker_latlng, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_ilgaja_addr', object.worker_ilgaja_addr, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_ilgaja_latlng', object.worker_ilgaja_latlng, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_jumin', object.worker_jumin, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_job_code', object.worker_job_code, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_job_name',	object.worker_job_name, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_barcode', object.worker_barcode, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_memo', object.worker_memo, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_bank_code', object.worker_bank_code, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_bank_name', object.worker_bank_name, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_bank_account',	object.worker_bank_account, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_bank_owner', object.worker_bank_owner,	'',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_bankbook_scan_yn', object.worker_bankbook_scan_yn, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_feature', object.worker_feature, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_blood_pressure', object.worker_blood_pressure,'',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_OSH_yn', object.worker_OSH_yn, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_jumin_scan_yn', object.worker_jumin_scan_yn,'',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_OSH_scan_yn', object.worker_OSH_scan_yn,'',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_app_status', object.worker_app_status,	'',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_reserve_push_status', object.worker_reserve_push_status,'',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_app_use_status', object.worker_app_use_status,'',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_age', object.worker_age, '',true);
		$("#list").jqGrid('setCell',lastsel,'worker_owner', object.owner_id, '',true);
		$("#list").jqGrid('setCell',lastsel,'ilbo_worker_add_memo', convertWorkerRatingToGrade(object.worker_rating) + "/" + getWorkerBloodPressureKr(object.worker_blood_pressure) + "/" + getWorkerIlgajaAddrKr(object.worker_ilgaja_addr), '',true);
		
		// fometter 함수를 돌리기 위해 아무값들을 넣어 주면 해당 cell 의 값을 바꿀수 있다. 값이 공백일 때는 null 값을 넣어 줘야 된다.
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_info', object.code, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_status_info', object.code, '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_worker_view', object.code, '', true);
		$("#list").jqGrid('setCell', lastsel, 'worker_ilbo', object.code, '', true);
		
		//chkCodeUpdate('sta_layer', lastsel, 'ilbo_status_code', 0, 'ilbo_status_name', '' ,'0', 'STA');
	}
	function convertWorkerRatingToGrade(rating){
		var grade = "미평가";
		if( rating == 6 ){
			grade = "A";
		}else if( rating == 5 ){
			grade = "B";
		}else if( rating == 4 ){
			grade = "C";
		}else if( rating == 3 ){
			grade = "D";
		}else if( rating == 2 ){
			grade = "E";
		}else if( rating == 1 ){
			grade = "F";
		}
		return grade;
	}
	function getWorkerBloodPressureKr(workerBloodPressure){
		if( workerBloodPressure == 0 ){
			return "정상";
		}
		return workerBloodPressure;
	}
	function getWorkerIlgajaAddrKr(ilgajaAddress){
		if( !ilgajaAddress ){
			return "없음";
		}
		return ilgajaAddress;
	}

	function fn_editOtions_employerName(e, cm){
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
		
        $(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택
		$(e).autocomplete({
        	source: function (request, response) {
            	$.ajax({
					url		: "/admin/getWorkNameList"
					, type: "POST"
					, dataType: "json"
					, data	: { 
                    	term: request.term
                    	, srh_use_yn: 1 
                    	/*, company_seq:$("#list").jqGrid('getRowData', lastsel).work_company_seq*/
                    },
                    success	: function (data) {
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
			},
            minLength: 2,
            focus: function (event, ui) {
            	return false;
			},
            select: function (event, ui) {
            	// label 값을 넣어 주면 회사와 현장이 붙어서 나온다.
				// $(e).val(ui.item.label);
				// 회사명만 나오게
                lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
                
                $(e).val(ui.item.employer_name);
                $("#list").jqGrid('setCell', lastsel, 'ilbo_work_name', ui.item.work_name, '', true);  //다른 셀 바꾸기
                changeWork(ui.item);
				isSelect = true;
				
				if( event.keyCode != "13" ){
	               	var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
	               	$("#list").jqGrid("saveCell", iRow, cm.iCol);
	            }

				return false;
			}
		});
	}

	function fn_editOtions_workName(e, cm){
		lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
	
		$(e).select();     //INPUT TEXT 클릭 시 텍스트 전체 선택

		$(e).autocomplete({
	// source: "/admin/getWorkNameList?srh_use_yn=1",
			source: function (request, response) {
				$.ajax({
					url	: "/admin/getWorkNameList", type: "POST", dataType: "json",
					data : { 
						term: request.term
						, use_yn: 1
                	/*, company_seq: $("#list").jqGrid('getRowData', lastsel).work_company_seq*/ 
					},
					success	: function (data) {
						response($.map(data, function (item) {
						// return { label: item.code + ":" + item.name, value: item.code, id: item.id }
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
			},
			minLength: 2,
			focus: function (event, ui) {
				return false;
			},
			select: function (event, ui) {
            	// label 값을 넣어 주면 회사와 현장이 붙어서 나온다.
				// $(e).val(ui.item.label);
				// 현장명만 나오게
            	lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
            	
            	$(e).val(ui.item.work_name);
                $("#list").jqGrid('setCell', lastsel, 'employer_name'           	, ui.item.employer_name        , '', true);  //다른 셀 바꾸기
                changeWork(ui.item);
                                               
				isSelect = true;
				
				if( event.keyCode != "13" ){
	               	var iRow = $('#' + $.jgrid.jqID(lastsel))[0].rowIndex;
	               	$("#list").jqGrid("saveCell", iRow, cm.iCol);
	            }

				return false;
			}
		});
	}

	//구인처 현장 정보 바꾸기
	function changeWork(object){
		$("#list").jqGrid('setCell', lastsel, 'work_company_seq'          , object.company_seq         , '', true);
		$("#list").jqGrid('setCell', lastsel, 'work_company_name'        , object.company_name         , '', true);
		
		$("#list").jqGrid('setCell', lastsel, 'employer_seq'            		, object.employer_seq         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_employer_feature'   	, object.ilbo_employer_feature, '', true);  //다른 셀 바꾸기
	
	    // 현장정보
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_order_code'         		, null                         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_order_name'         	, null                         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_use_code'         		, null                         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_use_name'         		, null                         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_status_code'         	, '0'                         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_status_name'         	, null                         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_worker_status_code' 		, null                         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_worker_status_name'    , null                         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_order_code'         	, null                         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_order_name'         , null                         , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'work_seq'                		, object.code                 , '', true);  //다른 셀 바꾸기
//	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_name'          	, object.work_name            , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_arrival'       		, object.work_arrival         , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_addr'          		, object.work_addr            , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'work_sido'          		, object.work_sido            , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'work_sigugun'          		, object.work_sigugun            , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'work_dongmyun'          		, object.work_dongmyun            , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'work_rest'          		, object.work_rest            , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_latlng'        		, object.work_latlng          , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_addr_comment'	, object.work_addr_comment          , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_breakfast_yn'  	, object.work_breakfast_yn    , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_age_min'         , object.work_age_min             , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_age'           		, object.work_age             , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_blood_pressure', object.work_blood_pressure  , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'labor_contract_name', object.labor_contract_name  , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'receive_contract_name', object.receive_contract_name  , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'labor_contract_seq', object.labor_contract_seq  , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'receive_contract_seq', object.receive_contract_seq  , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'manager_seq'  				, object.manager_seq    , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'manager_name'  				, object.manager_name    , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'manager_phone'  			, object.manager_phone    , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_manager_name'	, object.work_manager_name    , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_manager_fax'   		, object.work_manager_fax     , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_manager_phone' 	, object.work_manager_phone   , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_manager_email' 	, object.work_manager_email   , '', true);
		$("#list").jqGrid('setCell', lastsel, 'ilbo_job_comment'        		, object.work_memo            , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_chief_memo'        , null            , '', true);  //다른 셀 바꾸기
	                                   
	    //fometter 함수를 돌리기 위해 아무값들을 넣어 주면 해당 cell 의 값을 바꿀수 있다.
	    $("#list").jqGrid('setCell', lastsel, 'addr_edit'               	, object.code              , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'addr_location'             , object.code              , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_work_info'          	, object.code              , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_use_info'          	, object.code              , '', true);  //다른 셀 바꾸기
	    $("#list").jqGrid('setCell', lastsel, 'ilbo_employer_view'    	, object.code              , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'employer_ilbo'           	, object.code              , '', true);
	    $("#list").jqGrid('setCell', lastsel, 'work_owner'              	, object.owner_id        , '', true);
		$("#list").jqGrid('setCell', lastsel, 'visit_type'              	, object.visit_type    	, '', true);	//visit_type 을 먼저 바꿔줘야 work_ilbo의 색깔을 formater 에서 바꿀수 있다.
	    $("#list").jqGrid('setCell', lastsel, 'work_ilbo'               	, object.code              , '', true);
	}

	function fn_compainonInfoList(ilbo_date, ilbo_seq, employer_seq, work_seq, kind_code, detail_code, add_code, worker_seq, event) {
		$.ajax({
	    	type	: "POST",
	        url		: "/admin/selectCompanion",
			data	: {
	        	ilbo_date: ilbo_date,
	        	employer_seq : employer_seq,
	        	work_seq : work_seq,
	        	ilbo_kind_code : kind_code,
	        	ilbo_job_detail_code : detail_code,
	        	ilbo_job_add_code : add_code,
	        	worker_seq : worker_seq
	        },
	        dataType: 'json',
	        success: function(data) {
	        	var companionList = data.companionList;
	        	
	        	var text = '<table summary="동반자 목록">';
	        	text += '<caption>동반자 목록</caption>';
	        	text += '<colgroup>';
	        	text += '	<col width="30px" />';
	        	text += '	<col width="150px" />';
	        	text += '	<col width="70px" />';
	        	text += '	<col width="50px" />';
	        	text += '</colgroup>';
	        	text += '<thead>';
	        	text += '<tr><th style="border: solid 1px;">번호</th><th style="border: solid 1px;">지점명</th><th style="border: solid 1px;">구직자명</th><th style="border: solid 1px;">상태</th></tr>';
	        	text += '<tbody>';
	        	
	        	
	        	for(var i = 0; i < companionList.length; i++) {
	        		var worker_company_name = '';
	        		var worker_name = '';
	        		var status_name = '';
	        		
	        		if(companionList[i].worker_company_name != null) {
	        			worker_company_name = companionList[i].worker_company_name;
	        		}
	        		
	        		if(companionList[i].worker_seq != '0') {
	        			worker_name = companionList[i].ilbo_worker_name;
	        		}
	        		
	        		if(companionList[i].output_status_name) {
	        			status_name = companionList[i].output_status_name;
	        		}
	        		
	        		text += "<tr>";
	        		text += "	<td style='border: solid 1px; text-align: center;'>" + (i + 1) + "</td>";
	        		text += "	<td style='border: solid 1px;'>" + worker_company_name + "</td>";
	        		text += "	<td style='border: solid 1px; text-align: center;'>" + (worker_name ? worker_name : '') + "</td>";
	        		text += "	<td style='border: solid 1px; text-align: center;'>" + status_name + "</td>";
	        		text += "</tr>";
	        	}
	        	
	        	text += "</tbody>";
	        	text += "</table>";
	        	
	        	text += "<div id=\"companion_"+ ilbo_seq +"\" class=\"bt_wrap single\" style=\"width: 240px; display: none; background-color: #fff;\">";
	        	text += clsHTML;
	        	text += "</div>";
	        		
	        	showCompanion('companion_' + ilbo_seq, text, event);
	        	
	        	
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

	function checkDuplicateCompanion(){
		var rowDatas = $("#list").getRowData();
		for(var i=0; i<rowDatas.length; i++){
			var _rowData = rowDatas[i];
			if(_rowData.worker_seq > 0 && _rowData.work_seq > 0){
	    		//같은 구직자가 같은날 같은현장에 출역한 갯수
	    		var count = rowDatas
	    			.filter(e => e.work_seq === _rowData.work_seq 
	    					&& e.ilbo_date === _rowData.ilbo_date 
	    					&& e.ilbo_status_code != 'STA007' && e.ilbo_status_code != 'STA008' && e.ilbo_status_code != 'STA009'
	    					&& (e.output_status_code == '100005' || e.output_status_code == '100003' || e.output_status_code == '100014' || e.output_status_code == '100015') )
	    			.length;
	    		
		    	if( count >= 2 ){
		    		$("#div_companion_" + _rowData.ilbo_seq + "> a").css('background', '#B40486');
		    		$("#div_companion_" + _rowData.ilbo_seq + "> a").css('color', '#ffffff');
		    		continue;
		    	}
	    	}
			$("#div_companion_" + _rowData.ilbo_seq + "> a").css('background', '#5AA5DA');
		}
	}
	
	function fn_setIlboData(rowId,rowdata){
		var ids = $("#list").getDataIDs();
		
		var employer_seq_array =[];    //유니크 회사수를 구하기 위함
		var uniqueEmployerCount =0;
		
		var worker_seq_array = [], ilbo_seq_array=[]; // 유니크한 구직자를 구하기 위함.
		
		var priceSum			= $("#list").jqGrid('getCol','ilbo_unit_price',false, 'sum');
		var feeSum 			= $("#list").jqGrid('getCol','ilbo_fee',false, 'sum');
		var deductionSum	= $("#list").jqGrid('getCol','ilbo_deduction',false, 'sum');
		var counselorSum 	= $("#list").jqGrid('getCol','counselor_fee',false, 'sum');
		var paySum 			= $("#list").jqGrid('getCol','ilbo_pay',false, 'sum');
		var shareSum 		= $("#list").jqGrid('getCol','share_fee',false, 'sum');
		  
		$.each(ids, function(idx, rowId) {
	    	rowData = $("#list").getRowData(rowId);
			
	    	// 총회사수 , 유니크 회사수 구하기==============================
	    	var employerSeq = rowData.employer_seq;
			//유니크 회사수를 구하기 위함
	        if ( employerSeq > 0  && rowData.ilbo_fee > 0 ) {
	        	employer_seq_array.push(employerSeq);
			}
	        
	        if ( rowData.ilbo_seq < 0 ) {
	        	$("#list").setRowData(rowId, false, { background:"#FDECCD" });
			}
	        	        
	    	if(employer_seq_array.indexOf(employerSeq) !== employer_seq_array.lastIndexOf(employerSeq)){
	    		uniqueEmployerCount +=1;
		  	};
		  	//=============================================
		  	
		  	ilbo_seq_array.push(rowData.ilbo_seq);
		    worker_seq_array.push(rowData.worker_seq);
		    
		    if(authLevel != 0) {
			    var worker_company_seq = rowData.worker_company_seq;
				var work_company_seq = rowData.work_company_seq;
				 
				if(worker_company_seq != sessionCompanySeq && work_company_seq != sessionCompanySeq) {
					  
					  priceSum -= (rowData.ilbo_unit_price * 1);
					  feeSum -= (rowData.ilbo_fee * 1); 
					  paySum -= (rowData.ilbo_pay * 1);
				}
		    }
		});
	
		$("#uniqueE").html(numberWithCommas(uniqueEmployerCount));
		$("#totalE").html(numberWithCommas(employer_seq_array.length));

		//중복 아이디 처리
		$.each(ilbo_seq_array, function(idx, value) {
			// 중복 구직자 폰트색 변경 첫번째 위치와 마지막 위치가 다르면 중복		
			var workerSeq =worker_seq_array[idx];
			if(workerSeq == 0){
				$("#list").jqGrid('setCell', ilbo_seq_array[idx], "ilbo_worker_name"    ,''   ,{color:''});
			}else{
				if(worker_seq_array.indexOf(workerSeq) !== worker_seq_array.lastIndexOf(workerSeq)){
					$("#list").jqGrid('setCell', ilbo_seq_array[idx], "ilbo_worker_name"    ,''   ,{color:'red'}); //빨간색
		  		}else{
		  			$("#list").jqGrid('setCell', ilbo_seq_array[idx], "ilbo_worker_name"    ,''   ,{color:''});
		  		}
			}
		});
	  
		//상단 합계적용
		$("#totalPrice").html(numberWithCommas(priceSum));
		$("#totalFee").html(numberWithCommas(feeSum) );
		$("#totalShareFee").html(numberWithCommas(shareSum) );
		$("#totalcounselor").html(numberWithCommas(counselorSum) );
	  
	  //$("#totalShare").html( numberWithCommas(shareSum) );
	  //$("#totalDeduction").html( numberWithCommas(deductionSum) );
		$("#totalPay").html( numberWithCommas(paySum) );
		//footer 적용
		$("#list").jqGrid("footerData", "set", {
			ilbo_job_comment	: "합계",
			ilbo_unit_price	: priceSum,
			ilbo_fee			: feeSum,
			share_fee			: shareSum,
			ilbo_deduction		: deductionSum,
			counselor_fee		: counselorSum,
			ilbo_pay			: paySum
		});
	}

	/*
	 * 활성화 비활성화.
	 * */
	function fn_setEditable(rowId, ilboCompanySeq, workerCompanySeq, workCompanySeq){
		// 소개비수령이 직수, 페이이면 대리수령 동의서 수정 안되게
		var _ilboPayCode = $("#list").jqGrid('getCell', rowId, 'ilbo_pay_code');
		if( _ilboPayCode == "200003" || _ilboPayCode == "200007" || _ilboPayCode == "200004" || _ilboPayCode == "200008" ){
			$("#list").jqGrid('setCell', rowId,  'receive_contract_seq', "", 'not-editable-cell');
		}
		
		if(authLevel == 0){//최고 관리자
			return;
		}
		
		// 경력옵션 수정 권한
		if(workCompanySeq != sessionCompanySeq) {
			$("#list").jqGrid("setCell", rowId, "ilbo_career_name", "", "not-editable-cell");
		}else {
			$("#list").jqGrid("setCell", rowId, "ilbo_career_name", "", "editable-cell");
		}
		
		// 계약서 수정 권한1
		if(authLevel > 1) {
			$("#list").jqGrid('setCell', rowId,  'labor_contract_seq', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'receive_contract_seq', "", 'not-editable-cell');
		}else if(authLevel == '1') {
			if(ilboCompanySeq != companySeq && workCompanySeq != companySeq) {
				$("#list").jqGrid('setCell', rowId,  'labor_contract_seq', "", 'not-editable-cell');
				$("#list").jqGrid('setCell', rowId,  'receive_contract_seq', "", 'not-editable-cell');
			}else if(ilboCompanySeq == companySeq || workCompanySeq == companySeq) {
				$("#list").jqGrid('setCell', rowId,  'labor_contract_seq', "", 'editable-cell');
				$("#list").jqGrid('setCell', rowId,  'receive_contract_seq', "", 'editable-cell');
			}
		}
		
		// 대리수령 계약서 1명이라도 사인 완료되면 수정 안되게
		if($("#list").jqGrid('getCell', rowId, 'surrogate') != '') {
			$("#list").jqGrid('setCell', rowId,  'receive_contract_seq', "", 'not-editable-cell');
		}
		
		// 근로 계약서 1명이라도 사인 완료되면 수정 안되게
		if($("#list").jqGrid('getCell', rowId, 'labor_contract') != '' && $("#list").jqGrid('getCell', rowId, 'labor_contract') != '0') {
			$("#list").jqGrid('setCell', rowId,  'labor_contract_seq', "", 'not-editable-cell');
		}
		
		if(ilboCompanySeq == companySeq){
			$("#list").jqGrid('setCell', rowId,  'company_name', "", 'editable-cell');
		}else{
			$("#list").jqGrid('setCell', rowId,  'company_name', "", 'not-editable-cell');
		}
		
		//worker  정보
		if(isWorkerCompany(ilboCompanySeq, workerCompanySeq)){
			//worker 정보 수정
			$("#list").jqGrid('setCell', rowId,  'worker_company_name', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_worker_name', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_worker_feature', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_worker_memo', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_assign_type', "", 'editable-cell');
		}else{
			//worker 정보 수정 불가
			$("#list").jqGrid('setCell', rowId,  'worker_company_name', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_worker_name', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_worker_feature', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_worker_memo', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_assign_type', "", 'not-editable-cell');
		}
		
		//work 정보.
		if(isWorkCompany(ilboCompanySeq, workCompanySeq)){
			$("#list").jqGrid('setCell', rowId,  'work_company_name', "", 'editable-cell');
			
			$("#list").jqGrid('setCell', rowId,  'employer_name', "", 'editable-cell'); 
			$("#list").jqGrid('setCell', rowId,  'ilbo_work_name', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'use_yn', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_work_breakfast_yn', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_work_arrival', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_work_finish', "", 'editable-cell');
			
			$("#list").jqGrid('setCell', rowId,  'ilbo_job_comment', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_chief_memo', "", 'editable-cell');
//			$("#list").jqGrid('setCell', rowId,  'ilbo_work_age_min', "", 'editable-cell');
//			$("#list").jqGrid('setCell', rowId,  'ilbo_work_age', "", 'editable-cell');
//			$("#list").jqGrid('setCell', rowId,  'ilbo_work_blood_pressure', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_unit_price', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_fee', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'share_fee', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'ilbo_deduction', "", 'editable-cell');
			$("#list").jqGrid('setCell', rowId,  'counselor_fee', "", 'editable-cell');
		}else{
			$("#list").jqGrid('setCell', rowId,  'work_company_name', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'employer_name', "", 'not-editable-cell'); 
			$("#list").jqGrid('setCell', rowId,  'ilbo_work_name', "", 'not-editable-cell');
			$("#list").jqGrid('setCell', rowId,  'use_yn', "", 'not-editable-cell');
			
//			$("#list").jqGrid('setCell', rowId,  'ilbo_work_age_min', "", 'not-editable-cell');
//			$("#list").jqGrid('setCell', rowId,  'ilbo_work_age', "", 'not-editable-cell');
//			$("#list").jqGrid('setCell', rowId,  'ilbo_work_blood_pressure', "", 'not-editable-cell');
			
		/*
		$("#list").jqGrid('setCell', rowId,  'ilbo_work_breakfast_yn', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'ilbo_work_age_min', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'ilbo_work_age', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'ilbo_work_blood_pressure', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'ilbo_work_arrival', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'ilbo_work_finish', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'ilbo_job_comment', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'ilbo_chief_memo', "", 'not-editable-cell');
		
		$("#list").jqGrid('setCell', rowId,  'ilbo_unit_price', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'ilbo_fee', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'share_fee', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'ilbo_deduction', "", 'not-editable-cell');
		$("#list").jqGrid('setCell', rowId,  'counselor_fee', "", 'not-editable-cell');
		*/
		}
	}

	function isWorkerCompany(ilboCompanySeq, workerCompanySeq){
		if(authLevel == 0) return true;
	
		if(ilboCompanySeq == companySeq){
			return true;
		}else{
			if(workerCompanySeq == companySeq){
				return true;	
			}else{
				return false;
			}
		}
	}

	function isWorkCompany(ilboCompanySeq, workCompanySeq){
		if(authLevel == 0) return true;
	
		if(ilboCompanySeq == companySeq){
			return true;
		}else{
			if(workCompanySeq == companySeq){
				return true;	
			}else{
				return false;
			}
		}
	}

	function showCompanion(optId, msg, e) {
		var $opt_layer = $('#opt_layer');
		var _display;

		if ( !e ) e = window.Event;
		var pos = abspos(e);

		if ( $("#"+ optId).css("display") == null || $("#"+ optId).css("display") == "undefined" ) {
			writeOpt2(msg);
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
	  
	function fn_signFlagVali(data) {
		for(var i = 0; i < data.rows.length; i++) {
			if(data.rows[i].labor_contract > 0) {
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "company_seq", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_date", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "company_name", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "worker_company_name", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_worker_name", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_worker_feature", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_worker_memo", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_assign_type", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "employer_name", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "work_name", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_order_code", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_breakfast_yn", "", "not-editable-cell");
				//$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_arrival", "", "not-editable-cell");
				//$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_finish", "", "not-editable-cell");
				//$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_job_comment", "", "not-editable-cell");
				//$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_chief_memo", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_career_name", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_age_min", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_age", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_blood_pressure", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "receive_contract_seq", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "labor_contract_seq", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_manager_name", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_manager_phone", "", "not-editable-cell");
				//$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_unit_price", "", "not-editable-cell");
				//$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_fee", "", "not-editable-cell");
				//$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "share_fee", "", "not-editable-cell");
				//$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "counselor_fee", "", "not-editable-cell");
				//$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_pay", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "fee_info", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "pay_info", "", "not-editable-cell");
			}else if(data.rows[i].surrogate > 0){
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "receive_contract_seq", "", "not-editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "labor_contract_seq", "", "not-editable-cell");
			}else {
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "company_seq", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_date", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "company_name", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "worker_company_name", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_worker_name", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_worker_feature", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_worker_memo", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_assign_type", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "employer_name", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "work_name", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_order_code", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_breakfast_yn", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_arrival", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_finish", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_job_comment", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_chief_memo", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_career_name", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_age_min", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_age", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_blood_pressure", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "receive_contract_seq", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "labor_contract_seq", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_manager_name", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_work_manager_phone", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_unit_price", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_fee", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "share_fee", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "counselor_fee", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "ilbo_pay", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "fee_info", "", "editable-cell");
				$("#list").jqGrid("setCell", data.rows[i].ilbo_seq, "pay_info", "", "editable-cell");
			}
		}
	}
	
	// 공제액 상세 금액 구해오기
	function fn_deductibleView(ilboSeq) {
		$.ajax({
	    	type : "POST",
	        url	: "/admin/getDeductibleInfo",
			data : {
	        	ilbo_seq : ilboSeq
	        },
	        dataType: 'json',
	        success: function(data) {
	        	if(data.code == '0000') {
	        		$("#deductibleBody").empty();
	        		
		        	deductibleViewOpenIrPopup('popup-layer3');
		        	
		        	var text = '';
		        	var ilboDeductibleDTO = data.ilboDeductibleDTO;
		        	
		        	text += '<tr>';
		        	text += '	<td class="linelY" style="text-align: right">' + comma(ilboDeductibleDTO.income_tax_price) + '</td>';
		        	text += '	<td class="linelY" style="text-align: right">' + comma(ilboDeductibleDTO.local_tax_price) + '</td>';
		        	text += '	<td class="linelY" style="text-align: right">' + comma(ilboDeductibleDTO.employer_insurance_price) + '</td>';
		        	text += '	<td class="linelY" style="text-align: right">' + comma(ilboDeductibleDTO.national_pension_price) + '</td>';
		        	text += '	<td class="linelY" style="text-align: right">' + comma(ilboDeductibleDTO.health_insurance_price) + '</td>';
		        	text += '	<td class="linelY" style="text-align: right">' + comma(ilboDeductibleDTO.care_insurance_price) + '</td>';
		        	text += '</tr>';
		        	
		        	$("#deductibleBody").append(text);
	        	}else {
	        		toastFail(data.message);
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
	}
	
	// 공제액 상세 보여주기 layer popup
	function deductibleViewOpenIrPopup(popupId) {
		//popupId : 팝업 고유 클래스명
		var $popUp = $('#'+ popupId);

		$dimmed.fadeIn();
		$popUp.show();

		var offsetWidth = -$popUp.width() / 2 ;
		var offsetHeight = -$popUp.height() / 2 - 100;
		  
		$popUp.css({
			'margin-top': offsetHeight,
		    'margin-left': offsetWidth
		});
	}
	
	function getBreakfastYnKr(breakfastYn){
		var _breakfastYnKr = "미제공";
		if( breakfastYn == "1" ){
			_breakfastYnKr = "조식";
		}else if( breakfastYn == "2" ){
			_breakfastYnKr = "중식";
		}else if( breakfastYn == "3" ){
			_breakfastYnKr = "석식";
		}else if( breakfastYn == "4" ){
			_breakfastYnKr = "조식/중식";
		}else if( breakfastYn == "5" ){
			_breakfastYnKr = "중식/석식";
		}else if( breakfastYn == "6" ){
			_breakfastYnKr = "조식/중식/석식";
		}
		return _breakfastYnKr;
	}