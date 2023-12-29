//현장등록
function funManagerWorkProcess(){
	
	
	var _data ;
	if($("#busi_mode").val() == "0"){ //현장추가시
		_data = {
				employer_seq : $("#busi_employer_seq").val(), 
				work_name : $("#busi_work_name").val(),
				work_addr :  $("#busi_addr").val(),
				work_latlng : $("#busi_latlng").val(),
				manager_name :$("#busi_manager_name").val(),
				manager_phone : $("#busi_manager_phone").val()
		  }
		
	}else if($("#busi_mode").val() == "1"){ //회사 추가시
		_data = {
				employer_name : $("#busi_employer_name").val(), 
				work_name : $("#busi_work_name").val(),
				work_addr :  $("#busi_addr").val(),
				work_latlng : $("#busi_latlng").val(),
		  }
	}else{ //매니져 추가시
		if($("#busi_manager_name").val() == ""){
			toastFail("매니저 이름을 읿력하세요.");
			return
		}
		
		if($("#busi_manager_phone").val() == ""){
			toastFail("매니저 폰번호를 입력 하세요.");
			return
		}
		
		_data = {
				employer_seq : $("#busi_employer_seq").val(), 
				work_name : $("#busi_work_name").val(),
				work_addr :  $("#busi_addr").val(),
				work_latlng : $("#busi_latlng").val(),
				manager_name : $("#busi_manager_name").val(),
				manager_phone : $("#busi_manager_phone").val(),
		  }
	
	}
	  
	var _url = "/manager/setManagerWork";

	commonAjax(_url, _data,true, function(data) {

		//successListener
		if ( data.code == "0000" ) {

			toastFail("등록되었습니다.");

		} else if ( data.code == "8000" ) {
			alert("로그인 인증키가 만료되었습니다.\n새로고침 후 다시 로그인하세요.");
			location.href="<c:url value='/manager/login' />";
		} else {
			if ( data.message != "" ) {
				alert(data.message);
			} else {
				alert("로그인 정보가 유효하지 않습니다!");
			}
		}
	},
	function(data) {
		//errorListener
		alert("오류가 발생했습니다.");
	},
	function() {
		//beforeSendListener
	},
	function() {
		$("#busi_mode").val("0");
		$("#busi_work_name").val("");
		$("#busi_addr").val("");
		$("#busi_latlng").val("");
		$("#busi_manager_name").val("");
		$("#busi_manager_phone").val("");

		ovHide();
		getWorkList();
	}
	);

}

//일등록...앱오더
function fucWorkProcess(){
//	 apporder_check() ;
	
		if( $("#order_workSeq").val() == null){
			$("#order_workSeq").focus();
			toastW("필수 정보 입력");
			return;
		}
		if(funcToday() > $("#work_date").val()){
			toastW("작업일은 오늘 이후 날짜를 선택 하셔야 합니다.");
			$("#work_date").focus();
			return;
		}
	
		if ( !fnChkValidForm("frmWorkReg") ){
			return;
		}
	
		$("[class ^= arr_memo]").each(function() {
			
			var memo = $(this).val();
			memo = memo.replace(/,/gi,"&#44");
			$(this).val(memo);
		});

		
		if(!confirm("인력을 요청하시겠습니까? ")){
			return;
		}
		
		
		$('#frmWorkReg').ajaxForm({
			url : "/manager/regWorkProcess",
			enctype : "multipart/form-data", // 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : 'application/x-www-form-urlencoded; charset=utf-8', //캐릭터셋을 euc-kr로 
			success : function(data) {
				if (data.code == "0000") {
					alert("인력 요청이 성공적으로 접수되었습니다. 감사합니다.");
					// closeWorkOffer();
					popClose();
					location.href = '/manager/main';
				} else {
					toastFail( data.message);
				}
			},
			   
			beforeSend : function(xhr) {
				xhr.setRequestHeader("AJAX", true);
				$(".wrap-loading").show();
				
			},
			error : function(e) {
				toastFail("등록 실패");
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		});

		$('#frmWorkReg').submit();

}

function nullToStr(data){
	return (data==null ? '' : data);
}
	
function showWorkerDetail( ilbo_seq ){
	
	// 사용자ID와 비밀번호를 RSA로 암호화한다.
	  var _data = {
			  ilbo_seq : ilbo_seq
	    	
	  };

	  var _url = "/manager/getIlboWorker";

	  commonAjax(_url, _data,true, function(data) {
	                            //successListener
	                            if ( data.code == "0000" ) {
	    						
	                              	$('#ilbo_worker_name').html( data.resultDTO.ilbo_worker_name +"(만 "+ getJuminToAge(data.resultDTO.ilbo_worker_jumin) +"세)");
	                              	$('#ilbo_worker_jumin').html( formatJuminNo(data.resultDTO.ilbo_worker_jumin) );
	                              	$('#ilbo_worker_phone').html( "<a href=\"javascript:telCall('"+formatPhone(data.resultDTO.ilbo_worker_phone)+"');\" class=\"ctxt bank\">" + formatPhone(data.resultDTO.ilbo_worker_phone) +" </a>")  ;
	                              	$('#ilbo_worker_addr').html(data.resultDTO.ilbo_worker_addr);
	                              	$('#ilbo_unit_price').html( numberWithCommas(data.resultDTO.ilbo_unit_price) );
	                              	$('#ilbo_worker_bank').html( nullToStr(data.resultDTO.ilbo_worker_bank_name) +" " + nullToStr(data.resultDTO.ilbo_worker_bank_account));
	                              	$('#ilbo_worker_bank_owner').html("예금주 : "+ nullToStr(data.resultDTO.ilbo_worker_bank_owner) );
	                              	
	                              	$('#bank_copy').attr("href","javascript:CopyToClipboard('"+ nullToStr(data.resultDTO.ilbo_worker_bank_owner) +" "+ nullToStr(data.resultDTO.ilbo_worker_bank_name) +" " + nullToStr(data.resultDTO.ilbo_worker_bank_account) +" "+data.resultDTO.ilbo_unit_price+"원',myClipboard,false)");
	                              	
	                              	if(data.PHOTO_path == null){
		                              	if(data.JUMIN_path == null){
		                              		$('#ilbo_worker_jumin_file_name').attr("src","/resources/manager/image/Content/no_image.png");
		                              	}else{
		                              		$('#ilbo_worker_jumin_file_name').attr("src","/manager/imgLoad?path="+data.JUMIN_path+"&name="+data.JUMIN_fileName);
		                              	}
	                              	}else{
	                              		$('#ilbo_worker_jumin_file_name').attr("src","/manager/imgLoad?path="+data.PHOTO_path+"&name="+data.PHOTO_fileName);
	                              	}
	                              	
	                              	if(data.OSH_path == null){
	                              		$('#osh').removeClass("cate-btn");
	                              		$('#oshtxt').html("");
	                              		$('#ilbo_worker_OSH_file_name').attr("src","/resources/manager/image/Content/no_image.png");
	                              		
	                              	}else{
	                              		$('#osh').addClass("cate-btn");
	                              		$('#oshtxt').html("기초안전");
		                              	$('#ilbo_worker_OSH_file_name').attr("src","/manager/imgLoad?path="+data.OSH_path+"&name="+data.OSH_fileName);
	                              	}
	                              	
	                              	if(data.BANK_path == null){
	                              		$('#certificate').removeClass("add-btn");
	                              		$('#certificatetxt').html("");
	                              		$('#ilbo_worker_certificate_file_name').attr("src","/resources/manager/image/Content/no_image.png");
	                              	}else{
	                              		$('#certificate').addClass("add-btn");
	                              		$('#certificatetxt').html("통장사본");
	                              		$('#ilbo_worker_certificate_file_name').attr("src","/manager/imgLoad?path="+data.BANK_path+"&name="+data.BANK_fileName);
	                              	}
	                              	

									 var viewObj = $('.info-view'); 
									 popShow(viewObj);
									
	                            } else if ( data.code == "8000" ) {
	                              alert("로그인 인증키가 만료되었습니다.\n새로고침 후 다시 로그인하세요.");
	                              location.href="<c:url value='/manager/login' />";
	                            } else {
	                                if ( data.message != "" ) {
	                                  alert(data.message);
	                                } else {
	                                  alert("로그인 정보가 유효하지 않습니다!");
	                                }
	                            }
	                          },
	                          function(data) {
	                            //errorListener
	                            alert("오류가 발생했습니다.");
	                          },
	                          function() {
	                            //beforeSendListener
	                          },
	                          function() {

	                          }
	  );
	  
}

//push 수신처리
function setPush(checked){
	var manager_push_yn= "0";
	
	if(checked){ //눌렀을때 빠귄 값
		manager_push_yn = "1";
	} else {
	  	manager_push_yn = "0";
	}
	
	
	// 사용자ID와 비밀번호를 RSA로 암호화한다.
	  var _data = {
			manager_seq :  $('#manager_seq').val(),
			manager_push_yn : manager_push_yn
	  };


	  var _url = "/manager/setManagerInfo";

	  commonAjax(_url, _data,true, function(data) {
            //successListener
            if ( data.code == "0000" ) {
            //  alert("완료");
            	
            } else if ( data.code == "1000" ) {
              alert("필수 파라메터 누락 입니다. manager_seq");
              
            } else {
              if ( jQuery.type(data.message) != 'undefined' ) {
                if ( data.message != "" ) {
                	alert(data.message);
                } else {
                	if($wbr.browser == "Android"){
            	  		App.logout();
            		}else{
                  		alert("로그인 정보가 유효하지 않습니다!");
            		}
                }
              } else {
            	  if($wbr.browser == "Android"){
            		  App.logout();
            	  }else{
            		  alert("로그인 정보가 유효하지 않습니다!");
            	  }
              }
            }
          },
          function(data) {
            //errorListener
            alert("오류가 발생했습니다.");
          },
          function() {
            //beforeSendListener
          },
          function() {
            //completeListener
          }
	  );
}


//select box 의 현장리스트를 가져온다.
function getWorkList(){
	  var _data = {
	  };

	  var _url = "/manager/getWorkList";

	  commonAjax(_url, _data,true, function(data) {
		  //successListener
		  if ( data.code == "0000" ) {

			  var resultList = data.resultList;
			  if ( resultList != null && resultList.length > 0 ) {
				  
				  var orderSeqOption  ="";
				  var busiOption = "";
				  
				  for ( var i = 0; i < resultList.length; i++ ) {
					  var resultData = resultList[i];
					  orderSeqOption += '<option value="' + resultData.work_seq + '" workMangerName="' + resultData.work_manager_name + '"  workMangerPhone="' + resultData.work_manager_phone + '" mangerName="' + resultData.manager_name + '"  mangerPhone="' + resultData.manager_phone + '">' + resultData.employer_name + '_' + resultData.work_name +'</option>';
					  busiOption += '<option value="'+ resultData.employer_seq +'">' + resultData.employer_name + '</option>';
				  }
				  
				  
				  $("#order_workSeq").find("option").remove();
				  $('#order_workSeq').append(orderSeqOption);

				  orderSeqOption = '<option value="0" }>전체현장</option>' + orderSeqOption;
				  $("#work_seq").find("option").remove();
				  $('#work_seq').append(orderSeqOption);
				  
				  $("#work_seq").val(workSeq).attr("selected", "selected");
				  
				  $("#busi_employer_seq").find("option").remove();
				  $('#busi_employer_seq').append(busiOption);

			  }

		  } else if ( data.code == "8000" ) {
			  alert("로그인 인증키가 만료되었습니다.\n새로고침 후 다시 로그인하세요.");
			  location.href="<c:url value='/manager/login' />";
		  } else {
			  if ( data.message != "" ) {
				  alert(data.message);
			  } else {
				  alert("로그인 정보가 유효하지 않습니다!");
			  }
		  }
	  },
	  function(data) {
		  //errorListener
		  alert("오류가 발생했습니다.");
	  },
	  function() {
		  //beforeSendListener
	  },
	  function() {

	  }
	  );
	  
}


//패스워드 변경
function funcNewPass(){
	var managerPass = $("#manager_pass").val();
	var managerNewPass = $("#manager_new_pass").val();
	var managerNewPass2 = $("#manager_new_pass2").val();
	
	if(managerPass == ""){
		toastW("현재 패스워드를 입력 하세요.");
		$("#manager_pass").focus();
		return;
	}
	
	if(managerNewPass == ""){
		toastW("변경비밀번호를 입력 하세요.");
		$("#managerNewPass").focus();
		return;
	}
	
	if(managerNewPass2 == ""){
		toastW("비밀번호확인을 입력 하세요.");
		$("#managerNewPass2").focus();
		return;
	}
	
	if(managerNewPass != managerNewPass2){
		toastW("비밀번호가 일치 하지 않습니다.");
		$("#managerNewPass2").focus();
		return;
	}
	
	var _data = {
			manager_pass : managerPass, 
			manager_new_pass :managerNewPass,
		}
	
	
	  var _url = "/manager/setPassword";
	  commonAjax(_url, _data, true, function(data) {
          //successListener
			  	if(data.code == "0000"){
		  			toastOk('처리되었습니다.');
		  			
					$("#manager_pass").val("");
					$("#manager_new_pass").val("");
					$("#manager_new_pass2").val("");
		  			
		  			ovHide();
		  			
		  		}else{
		  			toastFail(data.message);
		  		}
          },
          function(data) {//errorListener
            alert("오류가 발생했습니다.");
          },
          function() {//beforeSendListener
            
          },
          function() {//completeListener
            
          }
	  );

	
}


//작업취소
function funcWorkCancel(){
	

	if(!confirm("작업을 취소 하시겠습니까?")){
		return;
	}
	
	var ilboSeq;
	$("input[name='check_list']:checked").each( function(i)  {
		ilboSeq = $(this).val();
	});
	
	var _data = {
			ilbo_seq : ilboSeq, 
			code_type : "STA",
			ilbo_status_code : "STA008",
			ilbo_status_name : "취소",
			
	  }
	
	  var _url = "/manager/setWorkCancel";
	  commonAjax(_url, _data, true, function(data) {
		  	//successListener
				  	if(data.code == "0000"){
			 	  			alert("처리되었습니다.");
			 	  			location.href = '/manager/main'; 
			  		}else{
			  			toastFail(data.message);
			  		}
	        },
	        function(data) {//errorListener
	          alert("오류가 발생했습니다.");
	        },
	        function() {//beforeSendListener
	          
	        },
	        function() {//completeListener
	          
	        }
	  );

	
}

//내일 보내주세요.
function funcCopyIlboCell(copyWorker){

	
	var checkArr =[];
	var workName = "";
	
	$("input[name='check_list']:checked").each( function(i)  {
		checkArr.push($(this).val());
		workName =  $(this).attr('workName');
	});
	
	if(!confirm("["+workName + "] 현장에 작업자를 요청합니다.")){
		return;
	}
	
	
	/*
	 * 체크박스는  json 형태로 넘겨지지 않는다.
	var _data = {
			copy_worker : copyWorker,
			sel_ilbo_seq : checkArr, 
	  }
	*/
	_data = "copy_worker="+copyWorker + "&sel_ilbo_seq=" + checkArr;
	
	 var _url = "/manager/copyIlboCell";
	 commonAjax(_url, _data, true, function(data) {
		  	//successListener
			  		if(data.code == "0000"){
			  			toastOk('처리되었습니다.');
			  			location.href = '/manager/main';
			  		}else{
			  			toastFail(data.message);
			  		}
	        },
	        function(data) {//errorListener
	          alert("오류가 발생했습니다.");
	        },
	        function() {//beforeSendListener
	          
	        },
	        function() {//completeListener
	          
	        }
	  );
}

function funcEmployerInfo(){

    var _data = {};
	
	  var _url = "/manager/getEmployerInfo";
	  commonAjax(_url, _data, true, function(data) {
		  	//successListener
				  	if(data.code == "0000"){
				  		
				  		$('#employer_name').val(data.employerInfo.employer_name);
				  		$('#employer_num').val(data.employerInfo.employer_num);
				  		$('#employer_owner').val(data.employerInfo.employer_owner);
				  		$('#employer_business').val(data.employerInfo.employer_business);
				  		$('#employer_sector').val(data.employerInfo.employer_sector);
				  		$('#employer_addr').val(data.employerInfo.employer_addr);
				  		$('#employer_email').val(data.employerInfo.employer_email);
				  		
				  		
				  		if(data.employerInfo.is_tax == "1"){
				  			$('input:checkbox[id="is_tax"]').attr("checked", true); //단일건
				  		}
				  		
				  		$('.buisness_box.layer-pop').show();
				  	    ovShow();

			  		}else{
			  			toastFail(data.message);
			  		}
	        },
	        function(data) {//errorListener
	          alert("오류가 발생했습니다.");
	        },
	        function() {//beforeSendListener
	          
	        },
	        function() {//completeListener
	          
	        }
	  );
    
}


function funcSetEmployerInfo(){

	$('#frmEmployerInfo').ajaxForm({
		url : "/manager/setEmployerInfo",
		enctype : "multipart/form-data", // 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
		contentType : 'application/x-www-form-urlencoded; charset=utf-8', //캐릭터셋을 euc-kr로 
		success : function(data) {
			if (data.code == "0000") {
				toastOk("저장 되었습니다.");
				$('#file_CORP').val("");
				ovHide();
			} else {
				toastFail( data.message);
			}
		},
		   
		beforeSend : function(xhr) {
			xhr.setRequestHeader("AJAX", true);
			$(".wrap-loading").show();
			
		},
		error : function(e) {
			toastFail("등록 실패");
		},
		complete : function() {
			$(".wrap-loading").hide();
		}
	});

	$('#frmEmployerInfo').submit();
	
}









