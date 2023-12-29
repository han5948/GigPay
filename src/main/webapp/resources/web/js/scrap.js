// ajax common
function commonAjax(url, data, successListener, errorListener,
		beforeSendListener, completeListener) {
	$.ajax({

		type : "POST",
		url : url,
		data : data,
		dataType : "json",		
		/*contentType: "application/json",*/
/*		 contentType: "application/json",*/

		beforeSend : function(xhr) {			
			xhr.setRequestHeader("AJAX", true);
			$(".wrap-loading").show();
            
			// 요청 전 선작업
			if (null != beforeSendListener) {
				beforeSendListener();
			}
		},
		success : function(data) {
			
			// 성공 시
			successListener(data);
			
		},
		error : function(data) {
			if(data.status == "901"){
				location.href = "/web";
			}
			// 오류 발생
			if (null != errorListener) {
				errorListener(data);
			}
						
		},
		complete : function() {
			// 요청 완료 시
			if (null != completeListener) {
				completeListener();
			}
			//App.unblockUI();
			$(".wrap-loading").hide();
		}
	});

}



//trim
function trim(s) {
	s += '';
	
	return s.replace(/ /g, '');
}

function regExEngNum(obj){
	var exp = new RegExp('^[A-Za-z]+[0-9]*$');
	var val = obj.value;
	if(val!=''){
		if(!exp.test(val)){			
			alert("영문/숫자만 입력이 가능합니다.");
			obj.value = "";
		}
	}
}
function regExEngSmallNum(obj){
	var exp = new RegExp('^[a-z]+[0-9]*$');
	var val = obj.value;
	if(val!=''){
		if(!exp.test(val)){
			alert("영문(소문자)/숫자만 입력이 가능합니다.");
			obj.value = "";
		}
	}
}

// 숫자/영문만 입력
function regExEngBigNum(obj){
	var exp = new RegExp('^[a-zA-Z0-9]*$');
	var val = obj.value;
	if(val!=''){
		if(!exp.test(val)){			
			alert("영문/숫자만 입력이 가능합니다.");
			obj.value = "";
		}
	}
}

// 숫자만 입력
function regOnlyNum(obj){
	var exp = new RegExp('^[0-9-.]*$');
	var val = obj.value;
	if(val!=''){
		if(!exp.test(val)){
			alert("숫자만 입력이 가능합니다.");
			obj.value = obj.value.replace(/[^0-9-.]/g,'');
		}
	}
}

//숫자와 - 만 입력
function regOnlyNum2(obj){
	var exp = new RegExp('^[0-9-_]*$');
	var val = obj.value;
	if(val!=''){
		if(!exp.test(val)){
			alert("숫자만 입력이 가능합니다.");
			obj.value = "";
		}
	}
}


//이메일 형식 체크 
function regEmailChkFull(val){
	var regExp = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
	
	if(val!=''){
		if (!regExp.test(val)) {
			return false;
		}
		else {
			return true;
		    
		}
	}
}


// 휴대폰 번호 체크
function regPhoneChk(val){
	var regExp = /^\d{3}-\d{3,4}-\d{4}$/;
	
	if(val!=''){		
		if (!regExp.test(val)) {
			return false;
		}
		else {
			return true;
		    
		}
	}
}


		
// 영문 숫자 조합 체크(6이상 15미만)
function fnCheckPassword(_val) {
	if (!/^[a-zA-Z0-9]{6,15}$/.test(_val)) {		
		return false;
	}

	var chk_num = _val.search(/[0-9]/g);
	var chk_eng = _val.search(/[a-z]/ig);
	if (chk_num < 0 || chk_eng < 0) {		
		return false;
	}

	
/*	if (/(\w)\1\1\1/.test(upw)) {
 		alert("비밀번호는 같은 문자를 4번이상 사용할수 없습니다.");
		return false;
	}*/
	

	return true;
}

// 준비중입니다.
function noLink(){
	alert("준비중입니다.");
}

// html 태그 제거
function htmlRemove(_str){
	var result = "";
	if(_str != ""){
		result = _str.replace(/(<([^>]+)>)/ig,"");
	}
	
	return result;
	
}

//관리자 접속_계정 변경
function adminChange(frmName, adminSeq){
	if($("form[name="+frmName+"] input[name=adminSeq]").length == 0){		
		$("form[name="+frmName+"]").append("<input type='hidden' name='adminSeq' value='' /> ");		
	}
	$("form[name="+frmName+"] input[name=adminSeq]").val(adminSeq);		
	$("form[name="+frmName+"]").attr("action",resourcePath+'/cms/adminChange');
	$("form[name="+frmName+"]").submit();	
}

function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}

function dateValidCheck(startDate, endDate, sep){
	var arr1 = startDate.split(sep);
    var arr2 = endDate.split(sep);
    var dat1 = new Date(arr1[0], arr1[1]+1, arr1[2]);
    var dat2 = new Date(arr2[0], arr2[1]+1, arr2[2]);
    
    var diff = dat2.getTime() - dat1.getTime();
    
    if(diff < 0){
    	alert(false);
    	return false;
    }else{
    	alert(true);
    	return true;
    }
}

var NEMO = {
		alert : function(title, body, func){
			$("#draggable").draggable({
	            handle: ".modal-header"
	        });
			
			if(title != ""){
				$("#draggable").find(".modal-title").html(title);	
			}else{
				$("#draggable").find(".modal-title").html("알림");
			}
			
			$("#draggable").find(".modal-body").html(body);
			
			$("#modalShow").attr("data-toggle", "modal");
			$("#modalShow").click();
			
			
			
			if(typeof func != "undefined" && func != ""){
				$("#draggable").watch('display', function(){
				    if(this.style.display == "none"){
				    	func();
				    }			    
				});	
			}
			
			 

			
			
		},
		confirm : function(title, body, trueEvent){
		
			
			$("#confirm").draggable({
	            handle: ".modal-header"
	        });
			
			if(title != ""){
				
				$("#confirm").find(".modal-title").html(title);	
			}else{
				$("#confirm").find(".modal-title").html("알림");
			}
			$("#confirm").find(".modal-body").html(body);
			
			
			// 확인 버튼 이벤트 리스너
			$("#confirmTrueBtn").unbind("click");			
			$("#confirmTrueBtn").bind("click", function(){
				trueEvent();
			});
			
			$("#modalConfirm").attr("data-toggle", "modal");
			$("#modalConfirm").click();
		}
		
		
		
	}

function NowTop(n1) {		
	$("#header>ul>li>a").eq(n1-1).addClass("on");
}//topmenu현재 페이지

function NowLeft(n1) {
	$(".lnb>ul>li>a").eq(n1-1).addClass("on");
}//leftmenu 현재 페이지


function datePickerSet(sId, eId){
	$( "#"+sId ).datepicker({
	    dateFormat: 'yy-mm-dd',
	    prevText: 'prev Month',
	    nextText: 'next Month',    
	    showMonthAfterYear: true,
	    changeMonth: true,
	    changeYear: true
	    
	  });
	
	$( "#"+eId ).datepicker({
	    dateFormat: 'yy-mm-dd',
	    prevText: 'prev Month',
	    nextText: 'next Month',    
	    showMonthAfterYear: true,
	    changeMonth: true,
	    changeYear: true
	    
	  });
	
	$("#"+sId).datepicker("option", "maxDate", $("#"+eId).val());
    $("#"+sId).datepicker("option", "onClose", function ( selectedDate ) {
        $("#"+eId).datepicker( "option", "minDate", selectedDate );
    });
    
    $("#"+eId).datepicker("option", "minDate", $("#"+sId).val());
    $("#"+eId).datepicker("option", "onClose", function ( selectedDate ) {
        $("#"+sId).datepicker( "option", "maxDate", selectedDate );
    });
	
}

function datetimePickerSet(sId){	
	$('#'+sId).datetimepicker({
		monthNames : [".1",".2",".3",".4",".5",".6",".7",".8",".9",".10",".11",".12"],
		dayNamesMin : ["일","월","화","수","목","금","토"],
		dateFormat : "yy-mm-dd",
	    timeFormat: 'HH:mm'
	});
}

function timePickerSet(sId){	
	$('#'+sId).timepicker({		
	    timeFormat: 'HH:mm'
	});
}



function popupAutoResize() {
	
	/* var h = (window.innerHeight) ? document.documentElement.offsetHeight : document.body.scrollHeight;
     var h_v = (window.innerHeight) ? window.innerHeight :document.documentElement.clientHeight;
     h = h - h_v;

     window.resizeBy(0, h + 20);*/
	
	var thisX = parseInt(document.body.scrollWidth);
    var thisY = parseInt(document.body.scrollHeight);
    var maxThisX = screen.width - 50;
    var maxThisY = screen.height - 50;
    var marginY = 0;
    /*alert(thisX + "===" + thisY);*/
    //alert("임시 브라우저 확인 : " + navigator.userAgent);
    // 브라우저별 높이 조절. (표준 창 하에서 조절해 주십시오.)
    if (navigator.userAgent.indexOf("MSIE 6") > 0) marginY = 45;        // IE 6.x
    else if(navigator.userAgent.indexOf("MSIE 7") > 0) marginY = 75;    // IE 7.x
    else if(navigator.userAgent.indexOf("Firefox") > 0) marginY = 50;   // FF
    else if(navigator.userAgent.indexOf("Opera") > 0) marginY = 30;     // Opera
    else if(navigator.userAgent.indexOf("Netscape") > 0) marginY = -2;  // Netscape

    if (thisX > maxThisX) {
        window.document.body.scroll = "yes";
        thisX = maxThisX;
    }
    if (thisY > maxThisY - marginY) {
        window.document.body.scroll = "yes";
        thisX += 19;
        thisY = maxThisY - marginY;
    }
    window.resizeTo(thisX+10, thisY+marginY + 50);

    // 센터 정렬
    // var windowX = (screen.width - (thisX+10))/2;
    // var windowY = (screen.height - (thisY+marginY))/2 - 20;
    // window.moveTo(windowX,windowY);
}

function getTimeStamp() {
  var d = new Date();
  var s =
    leadingZeros(d.getFullYear(), 4) + '년' +
    leadingZeros(d.getMonth() + 1, 2) + '월' +
    leadingZeros(d.getDate(), 2) + '일 ' +

    leadingZeros(d.getHours(), 2) + '시' +
    leadingZeros(d.getMinutes(), 2) + '분' +
    leadingZeros(d.getSeconds(), 2) + '초';

  return s;
}

function leadingZeros(n, digits) {
  var zero = '';
  n = n.toString();

  if (n.length < digits) {
    for (var i = 0; i < digits - n.length; i++)
      zero += '0';
  }
  return zero + n;
}	


