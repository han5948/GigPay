var _thisLayout_style = {};
var _orgLayout_style = {};
var _thisPage_cfg  = {};
function checkPageStyle(){
	_orgLayout_style =  $.extend({},_thisLayout_style);  _thisLayout_style = getPageStyle();
}
function getPageStyle(){
	var pg_type = {};
	var chkW = $("body").width();
	if(_isLowBr_ && chkW >999) chkW = wsize.win.w;
	return pg_type;

}
function _getLayoutHeaderHeight(){
	return	$("#header-wrap").height();
}
function _getLayoutFooterHeight(){
	return	$("#footer-wrap").height();
}

//로그아웃 시 호출
function logOut(){
	if($wbr.browser == "Android"){
		App.logout();
	}
	
	if($wbr.browser == "IOS"){
		window.location="jscall://logout";
	}
	
	setTimeout(goLogOut, 500);
	
}

function goLogOut(){
	location.href="/manager/logout";
}

//전화 걸기시 호출
function telCall(phoneNum){
	
	if($wbr.browser == "Android"){
		App.call(phoneNum);
	}else if($wbr.browser == "IOS"){
		window.location="jscall://tel?phoneNum="+phoneNum;
	}else{
		alert("tel:" + phoneNum);	
	}
	
}

/*
 * 전화번호 - 붙이기
 * */
function formatPhone(cellvalue) {
	  
	  var phoneNo = cellvalue;
	  
	  var RegNotNum  = /[^0-9]/g;
	
	  // return blank
	  if ( phoneNo == "" || phoneNo == null ) return "";
	
	  // delete not number
	  phoneNo = phoneNo.replace(RegNotNum,'');
	
	  return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
}


/*
 * 전화번호 - 붙이기
 * */
function formatPhone(cellvalue) {
	  
	  var phoneNo = cellvalue;
	  
	  var RegNotNum  = /[^0-9]/g;
	
	  // return blank
	  if ( phoneNo == "" || phoneNo == null ) return "";
	
	  // delete not number
	  phoneNo = phoneNo.replace(RegNotNum,'');
	
	  return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
}


//주민등록번호 포맷
function formatJuminNo(cellvalue) {
	var regNo = cellvalue;
	
	var RegNotNum  = /[^0-9]/g;
	
	// return blank
	if ( regNo == undefined || regNo == "" || regNo == null || regNo == "?" ) return "";
	
	// delete not number
	regNo = regNo.replace(RegNotNum,'');
	
	//return regNo = regNo.replace(/([0-9]{6})([0-9]{7})/, "$1-$2");
	regNo = regNo.replace(/([0-9]{6})([0-9]{7})/, "$1-$2");
	
	
	var pattern = /.{7}$/; // 정규식
	return regNo.replace(pattern, "*******");
}


function getJuminToAge(jumin)
{
	
	if ( jumin == undefined || jumin == "" || jumin == null || jumin == "?" || jumin.length < 13) return "0";

	jumin = jumin.replace("-","");
	var nByear, nTyear;
	var today;
	
	
	today = new Date();
	nTyear = today.getFullYear();
	
	
	if (parseInt(jumin.substr(6,1), 10) < 3){
		nByear = 1900 + parseInt(jumin.substring(0,2), 10);
		
	}else{
		nByear = 2000 + parseInt(jumin.substring(0,2), 10);
		

   }
	return nAge = nTyear - nByear;

}

//input type number maxlength check
function maxLengthCheck(object){
    if (object.value.length > object.maxLength){
        object.value = object.value.slice(0, object.maxLength);
    }    
}


//ajax common
function commonAjax(url, data,sync, successListener, errorListener,
		beforeSendListener, completeListener) {
	
	$.ajax({

		type : "POST",
		url : url,
		data : data,
		async: sync,
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
				location.href = "/manager";
			}
			
			// 오류 발생
			if (null != errorListener) {
				errorListener(data);
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

//ajax common
function loginAjax(url, data,sync, successListener, errorListener,
		beforeSendListener, completeListener) {
	
	$.ajax({

		type : "POST",
		url : url,
		data : data,
		async: sync,
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
				location.href = "/manager";
			}
			
			// 오류 발생
			if (null != errorListener) {
				errorListener(data);
			}
			
		},
		complete : function() {
			// 요청 완료 시
			//$(".wrap-loading").hide();

			if (null != completeListener) {
		
				completeListener();
			}
			//App.unblockUI();
			
		}
	});
}


//클립보드 복사
function CopyToClipboard( tagToCopy, textarea, re ) {

    textarea.parentNode.style.display = "block"; 

    var textToClipboard = tagToCopy;

    if ( re ) { // - 를 "" 로 바꾼다.
      textToClipboard =  tagToCopy.replace(/-/gi,"");
    }
/*
     if ( "value" in tagToCopy ) {    textToClipboard = tagToCopy.value;    }
    else {    textToClipboard = ( tagToCopy.innerText ) ? tagToCopy.innerText : tagToCopy.textContent;    }
*/
    var success = false;

    if ( window.clipboardData ) {
      window.clipboardData.setData ( "Text", textToClipboard );
      success = true;
    } else {
      textarea.value = textToClipboard;

      var rangeToSelect = document.createRange();

      rangeToSelect.selectNodeContents( textarea );

      var selection = window.getSelection();
      selection.removeAllRanges();
      selection.addRange( rangeToSelect );

      success = true;

      try {
        if ( window.netscape && (netscape.security && netscape.security.PrivilegeManager) ) {
          netscape.security.PrivilegeManager.enablePrivilege( "UniversalXPConnect" ); 
        } 

        textarea.select();
        success = document.execCommand( "copy", false, null );
      } catch ( error ) {
        success = false;
        console.log( error );
      }
    }

    textarea.parentNode.style.display = "none";
    textarea.value = "";

    if ( success ) {
      $.toast({title: '클립보드 복사 완료', position: 'middle', backgroundColor: '#5aa5da', duration:1000 });
    } else {    
      $.toast({title: '클립보드 복사 실패', position: 'middle', backgroundColor: '#d60606', duration:1000 });
    }

    
} 


//오늘 날짜
function funcToday(){
	  
    var date = new Date();

    var year  = date.getFullYear();
    var month = date.getMonth() + 1; // 0부터 시작하므로 1더함 더함
    var day   = date.getDate();

    if (("" + month).length == 1) { month = "0" + month; }
    if (("" + day).length   == 1) { day   = "0" + day;   }
  
    return (year +"-" + month + "-" +day);  
       
}




