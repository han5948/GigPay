
$(document).ready( function(){
	
	$("input:text[numberOnly]").on("keyup", function() {
	    $(this).val($(this).val().replace(/[^0-9]/g,""));
	});



})

function validateNumber() {
	alert(event.keyCode);
    if (event.keyCode >=48 && event.keyCode <= 57 ) {
        return true;
    } else {
        event.returnValue = false;
    }
}


//ajax common
function commonAjax(url, data,sync, successListener, errorListener,	beforeSendListener, completeListener) {
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		async: sync,
		dataType : "json",		
		/*contentType: "application/json",*/

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
		error : function(event, xhr, options, error) {
			// 오류 발생
			if (null != errorListener) {
				errorListener(event, xhr, options, error);
			}
		},
		complete : function() {
			// 요청 완료 시
			$(".wrap-loading").hide();
			if (null != completeListener) {
				completeListener();
			}
			//App.unblockUI();
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
	var exp = new RegExp('^[0-9]*$');
	var val = obj.value;
	if(val!=''){
		if(!exp.test(val)){
			alert("숫자만 입력이 가능합니다.");
			obj.value = obj.value.replace(/[^0-9]/g,'');
		}
	}
}

function fucOnlyNum(val){
	var exp = new RegExp('^[0-9-.]*$');
	
	if(val !=''){
		if(!exp.test(val)){
			return false;
		}else{
			return true
		}
	}else{
		return false;
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


//천단위 콤마
function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

//콤마 없애기
function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}

//폰번호 마스킹처리
function phoneNumMask(phoneNum){
	var pattern = /^(\d{3})-?(\d{1,2})\d{2}-?\d(\d{3})$/;
    var result = "";
    if(!phoneNum) return result;

    if(pattern.test(phoneNum)) {
        result = phoneNum.replace(pattern, '$1-$2**-*$3');
    } else {
        result = "***";
    }
    return result;

}
//주민번호 마스킹처리
function phoneNumMask(phoneNum){
	var pattern = /^(\d{6})-?(\d{1,2})\d{2}-?\d(\d{3})$/;
    var result = "";
    if(!phoneNum) return result;

    if(pattern.test(phoneNum)) {
        result = phoneNum.replace(pattern, '$1-$2**-*$3');
    } else {
        result = "***";
    }
    return result;

}

//시작날짜와 종료 날짜 종료날짜가 크면 true
function dateValidCheck(startDate, endDate, sep){
	var arr1 = startDate.split(sep);
    var arr2 = endDate.split(sep);
    var dat1 = new Date(arr1[0], arr1[1]+1, arr1[2]);
    var dat2 = new Date(arr2[0], arr2[1]+1, arr2[2]);
    
    var diff = dat2.getTime() - dat1.getTime();
    
    if(diff < 0){
    	return false;
    }else{
    	return true;
    }
}

function dateDiffCheck(startDate, endDate, day){
	var arr1 = startDate.split("-");
    var arr2 = endDate.split("-");
    var dat1 = new Date(arr1[0], arr1[1]-1, arr1[2]);
    var dat2 = new Date(arr2[0], arr2[1]-1, arr2[2]);
    
    var diff = (dat2.getTime() - dat1.getTime()) / (1000*60*60*24);
    
    if(day > diff){
    	return true;
    }else{
    	return false;
    }
    
}



function datePickerOneSet(sId){
	
	$( "#"+sId ).datepicker({
	    dateFormat: 'yy-mm-dd',
	    prevText: 'prev Month',
	    nextText: 'next Month',    
	    showMonthAfterYear: true,
	    changeMonth: true,
	    changeYear: true
	    
	  });
}


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

function datePickerMonthSet(sId, eId){
	$.datepicker.regional['kr'] = {		    
		    monthNames: ['01','02','03','04','05','06','07','08','09','10','11','12'], // 개월 텍스트 설정
		    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 개월 텍스트 설정
		    dayNames: ['월요일','화요일','수요일','목요일','금요일','토요일','일요일'], // 요일 텍스트 설정
		    dayNamesShort: ['월','화','수','목','금','토','일'], // 요일 텍스트 축약 설정    dayNamesMin: ['월','화','수','목','금','토','일'], // 요일 최소 축약 텍스트 설정
		    dateFormat: 'yy-MM' // 날짜 포맷 설정
		};
		$.datepicker.setDefaults($.datepicker.regional['kr']);
	
	$( "#"+sId ).datepicker({
	    changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        showMonthAfterYear: true,        	    
        onClose: function(dateText, inst) { 
            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
            $(this).datepicker("setDate", new Date(year, month, 1));
                      
        }
	});
	
	$( "#"+eId ).datepicker({
	    changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        showMonthAfterYear: true,        
        onClose: function(dateText, inst) { 
            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
            $(this).datepicker("setDate", new Date(year, month, 1));
        }	    
	});
	
	/*$("#"+sId).datepicker("option", "maxDate", $("#"+eId).val());
    $("#"+sId).datepicker("option", "onClose", function ( selectedDate ) {
        $("#"+eId).datepicker( "option", "minDate", selectedDate );
    });
    
    $("#"+eId).datepicker("option", "minDate", $("#"+sId).val());
    $("#"+eId).datepicker("option", "onClose", function ( selectedDate ) {
        $("#"+sId).datepicker( "option", "maxDate", selectedDate );
    });*/
	
}

function datetimePickerSet(sId){	
	$('#'+sId).datetimepicker({
		dateFormat: 'yy-mm-dd',
	    prevText: 'prev Month',
	    nextText: 'next Month',    
	    showMonthAfterYear: true,
	    changeMonth: true,
	    changeYear: true,
	    timeFormat: 'HH:mm',
	});
}

function timePickerSet(sId){	
	$('#'+sId).timepicker({		
	    timeFormat: 'HH:mm',
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


function showModalDialogCallback(retVal) {
}

function phoneNumHypen(phoneNum){
	var regexp = /^[0-9]*$/
	phoneNum = phoneNum.replace(regexp,'');
	
	return phoneNum = phoneNum.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
}


//maxlength 체크
//<input type="number" max="9999" maxlength="4" oninput="maxLengthCheck(this)">
 
function maxLengthCheck(object){
 if (object.value.length > object.maxLength){
  object.value = object.value.slice(0, object.maxLength);
 }   
}




//회원 가입 validate
function validateEmail(email) {
	var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	return re.test(email);
}

function validatePass1(password) {
	var SamePass_0 = 0; //동일문자 카운트 
	var SamePass_1 = 0; //연속성(+) 카운드
	var SamePass_2 = 0; //연속성(-) 카운드

    var chr_pass_0;
    var chr_pass_1;
    var chr_pass_2;

    if(/(\w)\1\1/.test(password)){

//    	alert('444같은 문자를 번 이상 사용하실 수 없습니다.');
    	passCheckMessage = "연속된 문자열(123 또는 321, abc, cba 등)을\n 3자 이상 사용 할 수 없습니다.";
    	return false;

    }
    	
    		
    for(var i=0; i < password.length; i++)
    {
        chr_pass_0 = password.charAt(i);
        chr_pass_1 = password.charAt(i+1);
      /* 
        //동일문자 카운트
        if(chr_pass_0 == chr_pass_1)
        {
            SamePass_0 = SamePass_0 + 1
        }
  */
      
        chr_pass_2 = password.charAt(i+2);
        //연속성(+) 카운드

        if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == 1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == 1)
        {
            SamePass_1 = SamePass_1 + 1
        }
       
        //연속성(-) 카운드
        if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == -1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == -1)
        {
            SamePass_2 = SamePass_2 + 1
        }
    }
   /* if(SamePass_0 > 1)
    {
      //  alert("동일문자를 3번 이상 사용할 수 없습니다.");
        return false;
    }
  */
    if(SamePass_1 > 0 || SamePass_2 > 0 )
    {
    	passCheckMessage = "연속된 문자열(123 또는 321, abc, cba 등)을\n 3자 이상 사용 할 수 없습니다.";
        return false;
    }

    return true;
}

var passCheckMessage = "";
function validatePass2(pw) {

	var chk_num = pw.search(/[0-9]/g); // 숫자
	var chk_eng = pw.search(/[a-zA-Z]/ig); // 영문
	var chk_sp = checkSpecial(pw); // 특수문자
	var chk_check = chk_num + chk_eng + chk_sp;


	if (pw.length < 8) {
		passCheckMessage ="8자 이상의 비밀번호만 입력 가능 합니다.";
		return false;
	}

	if (chk_check < -1) {
		passCheckMessage ="띄어쓰기 없는 6~15자의 영문/숫자/특수문자 중 2가지 이상 조합으로 입력하셔야 합니다.";
		return false;
	}

	return true;
}

function validatePass3(pw) {

	if (checkSpace(pw)) {
		passCheckMessage ="비밀번호는 공백없이 입력해 주세요.";
		return false;
	}

	return true;
}


//스페이스 체크
function checkSpace(str) {
	if (str.search(/\s/) != -1) {
		return true;
	} else {
		return false;
	}
}


/* 특수 문자가 있나 없나 체크 */
function checkSpecial(str) {
	var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
	if (special_pattern.test(str) == true) {
		return 0;
	} else {
		return -1;
	}
}

/*
 	문자열 바이트 : calByte.getByteLength( obj.value ) 
	바이트 만큼 잘라오기 calByte.cutByteLength( obj.value, 20 ) ;
 * 
 * */
var calByte = {
		getByteLength : function(s) {

			if (s == null || s.length == 0) {
				return 0;
			}
			var size = 0;

			for ( var i = 0; i < s.length; i++) {
				size += this.charByteSize(s.charAt(i));
			}

			return size;
		},
			
		cutByteLength : function(s, len) {

			if (s == null || s.length == 0) {
				return 0;
			}
			var size = 0;
			var rIndex = s.length;

			for ( var i = 0; i < s.length; i++) {
				size += this.charByteSize(s.charAt(i));
				if( size == len ) {
					rIndex = i + 1;
					break;
				} else if( size > len ) {
					rIndex = i;
					break;
				}
			}

			return s.substring(0, rIndex);
		},

		charByteSize : function(ch) {

			if (ch == null || ch.length == 0) {
				return 0;
			}

			var charCode = ch.charCodeAt(0);

			if (charCode <= 0x00007F) {
				return 1;
			} else if (charCode <= 0x0007FF) {
				return 2;
			} else if (charCode <= 0x00FFFF) {
				return 2;
			} else {
				return 4;
			}
		}
	};


function imageTypeCheck(fileName) {

	if(fileName.value != ""){
		 
		var reg = /(.*?)\.(jpg|jpeg|png|gif|bmp|esp|tiff|tga)$/;
		if(!fileName.toLowerCase().match(reg)) {
			return false;
		}else{
			return true;
		}
	}else{
		return false;
	}
}


function movieTypeCheck(fileName) {
	
	if(fileName.value != ""){
		var reg = /(.*?)\.(wma|avi|mov|mp4|mp4a|mp4b|mp4v|mpg|mpeg|flv|f4v|f4a|f4b|f4p|3gp|3g2|k3g)$/;
		if(!fileName.toLowerCase().match(reg)) {
			return false;
		}else{
			return true;
		}
	}else{
		return false;
	}
}


//숫자 콤마
function numberWithCommas(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//앞뒤 공백문자열을 제거
function trim(str) { 
  return str.replace(/(^\s*)|(\s*$)/gi, ""); 
}
var previousStateCheck = null;
//shift 누르고 Row 다중 선택
function selectRowid(rowid, e) {

    var $this = $(this);
    var rows = this.rows;
    var previousId = $this.jqGrid('getGridParam', 'selrow'); // get id of the previous selected row
    var previousRow;
    var currentRow;
    if (!e.ctrlKey && !e.shiftKey || previousStateCheck == null || previousStateCheck == false ) {
        _isSideDown = null;                       
     //    $this.jqGrid('resetSelection');                       

    } else if (previousId && e.shiftKey && previousStateCheck) { //이전 선택 값이 있으면서 쉬프트키를 누르면서 이전에 누른 체크박스가 체크되있으면
     //   $this.jqGrid('resetSelection');
        // get DOM elements of the previous selected and the currect selected rows
        previousRow = rows.namedItem(previousId);
        currentRow 	= rows.namedItem(rowid);
        if (previousRow && currentRow) {
            
            if (previousRow.rowIndex < currentRow.rowIndex) { // 증가
                if (_isSideDown == false || _isSideDown == null) {
                    _currentStartSelectRow = previousRow.rowIndex;
                    _currentEndSelectRow = currentRow.rowIndex;
                }
                else {
                    _currentEndSelectRow = currentRow.rowIndex;
                }
                _isSideDown = true;
            }else { // 감소
                if (_isSideDown == null) {
                    _currentStartSelectRow = currentRow.rowIndex;
                    _currentEndSelectRow = previousRow.rowIndex;
                    _isSideDown = false;
                }
                else if (_isSideDown == true) {
                    if (currentRow.rowIndex < _currentStartSelectRow) {
                        _currentStartSelectRow = currentRow.rowIndex;
                        _isSideDown = false;
                    }
                    else {
                        _currentEndSelectRow = currentRow.rowIndex;
                    }
                }
                else {
                    _currentStartSelectRow = currentRow.rowIndex;
                }

            }

            for (i = _currentStartSelectRow+1; i <= _currentEndSelectRow; i++) {
                // the row with rowid will be selected by jqGrid, so we don't need to select him:
                if (i != currentRow.rowIndex) {
              	  if( $("input:checkbox[id='jqg_list_"+ rows[i].id +"']").is(":checked") == false ){
                    	$this.jqGrid('setSelection', rows[i].id, false);
              	  }
                }
            }
        }
    }
    previousStateCheck = $("input:checkbox[id='jqg_list_"+ rowid +"']").is(":checked");
    return true;
}

function replaceAll(str, searchStr, replaceStr) {
   return str.split(searchStr).join(replaceStr);
}