
//jq그리드 넓이 높이 구하기 -----------------------------------
    var baseWidth = 1200;
    var baseHeight = 600;
    var optWidth = 50;
    var optHeight = 330;
     
    //기본 넓이
    var jqWidth = $(window).width()-optWidth;
    if(jqWidth < baseWidth) jqWidth = baseWidth;
    //기본높이
    var jqHeight = $(window).height() - optHeight;
    if(jqHeight < baseHeight) jqHeight = baseHeight;
//-----------------------------------------------------------


// 선택 Option Layer HTML
var optHTML = "";
var clsHTML = "<div class='bt' style='clear: both;''></div><div class=\"bt_close\"><div class=\"bt_c1\"><a href=\"JavaScript:closeOpt()\"> 닫기 X </a></div></div>";

// 그리드 최초 로딩시 한번만 처리해야 하는 경우에 사용
var isGridLoad = false;

// trim 추가
String.prototype.trim = function() {
  return this.replace(/(^\s*)|(\s*$)/gi, "");
}

Map = function(){
 this.map = new Object();
};

//헤더 도움말
var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
    var thd = jQuery("thead:first", grid[0].grid.hDiv)[0];
    jQuery("tr.ui-jqgrid-labels th:eq(" + iColumn + ")", thd).attr("title", text);
      
};


Map.prototype = {
               put: function(key, value) {
                      this.map[key] = value;
                    },
               get: function(key) {
                      return this.map[key];
                    },
       containsKey: function(key) {
                      return key in this.map;
                    },
     containsValue: function(value) {
                      for ( var prop in this.map ) {
                        if ( this.map[prop] == value ) return true;
                      }
                      return false;
                    },
           isEmpty: function(key) {
                      return (this.size() == 0);
                    },
             clear: function() {
                      for ( var prop in this.map ) {
                        delete this.map[prop];
                      }
                    },
            remove: function(key) {
                      delete this.map[key];
                    },
              keys: function() {
                      var keys = new Array();
                      for ( var prop in this.map ) {
                        keys.push(prop);
                      }
                      return keys;
                    },
            values: function() {
                      var values = new Array();
                      for ( var prop in this.map ) {
                        values.push(this.map[prop]);
                      }
                      return values;
                    },
              size: function() {
                      var count = 0;
                      for ( var prop in this.map ) {
                        count++;
                      }
                      return count;
                    }
};

$.datepicker.setDefaults({
      changeMonth: true, 
         dayNames: [ '일요일','월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
      dayNamesMin: [ '일','월', '화', '수', '목', '금', '토'],       
  monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
       monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
       changeYear: true,
         nextText: '다음달',
         prevText: '이전달' ,
  showButtonPanel: true, 
      currentText: '오늘날짜', 
        closeText: '닫기', 
       dateFormat: "yy-mm-dd",
           showOn: 'button',
      buttonImage: '/resources/cms/images/cal.png', //이미지 url
  buttonImageOnly: true,
       buttonText: "달력"
});

var now  = new Date();

function getDateString(_date) {
  
	var year = _date.getFullYear();
	var mon  = (_date.getMonth() + 1) > 9 ? ''+ (_date.getMonth() + 1) : '0'+ (_date.getMonth() + 1);
	var day  = _date.getDate() > 9 ? ''+ _date.getDate() : '0'+ _date.getDate();

  return year +'-'+ mon +'-'+ day;
  
}

//날을 무조건 1일로 셋팅
function getDateString2(_date) {
	  var year = _date.getFullYear();
	  var mon  = (_date.getMonth() + 1) > 9 ? ''+ (_date.getMonth() + 1) : '0'+ (_date.getMonth() + 1);
	  var day  = "01";

	  return year +'-'+ mon +'-'+ day;
}


var toDay = getDateString(now);

var tomorrowDay = getTomorrowDay();

function getTomorrowDay(){
	var add = new Date();
	add.setDate(now.getDate() +1);
	
	return getDateString(add);
}

function setDayType(startid, endid, type) {
	var add = new Date();

	if ( type == "all" ) {
		$("#"+ startid).val("");
		$("#"+ endid).val("");

	} else if ( type == "today" ) { //오늘

		$("#"+ startid).val(toDay);
		$("#"+ endid).val(toDay);

	}else if ( type == "tomorrow" ) { //내일
		add.setDate(now.getDate() +1);
		$("#"+ startid).val(getDateString(add));
		$("#"+ endid).val(getDateString(add));

	} else if ( type == "day" ) { // 하루 더하기
		add.setDate(now.getDate() + startid);
		$("#"+ endid).val(getDateString(add));
	} else if ( type == "week" ) { //7일전
		add.setDate(now.getDate() - 7);

		$("#"+ startid).val(getDateString(add));
		$("#"+ endid).val(toDay);
	} else if ( type == "month" ) {
		var addDay = 30; 
		
		add.setDate(now.getDate() - 30);

		$("#"+ startid).val(getDateString(add));
		$("#"+ endid).val(toDay);
	} else if ( type == "2month" ) {
		//add.setMonth(now.getMonth() - 1);
		add.setDate(now.getDate() - 60);
		
		$("#"+ startid).val(getDateString(add));

		var edd = new Date();
		edd.setMonth(now.getMonth() + 1);
		$("#"+ endid).val(getDateString(edd));

	} else if ( type == "3month" ) {
		//add.setMonth(now.getMonth() - 3);
		
		add.setDate(now.getDate() - 90);

		$("#"+ startid).val(getDateString(add));
		$("#"+ endid).val(toDay);
	} else if ( type == "6month" ) {
		//add.setMonth(now.getMonth() - 6);
		
		add.setDate(now.getDate() - 180);

		$("#"+ startid).val(getDateString(add));
		$("#"+ endid).val(toDay);
	} else if ( type == "nowMonth" ) {

		var firstDat, lastDate;

		firstDate = new Date(now.getFullYear(), now.getMonth(), 1);
		lastDate = new Date(now.getFullYear(), now.getMonth() +1, 0);

		$("#"+ startid).val(getDateString(firstDate));
		$("#"+ endid).val(getDateString(lastDate));
	} else if ( type == "prev1Month" ) {

		mVal = -1;
		var addDate = getAddDate(mVal);
		
		var lastDay = addDate.getDate();
		
		firstDate = new Date(addDate.getFullYear(), addDate.getMonth(),1);
		lastDate = new Date(addDate.getFullYear(), addDate.getMonth() +1, 0);
		
		$("#"+ startid).val(getDateString(firstDate));
		$("#"+ endid).val(getDateString(lastDate));
		
	} else if ( type == "prev2Month" ) {
/*
		add.setMonth(now.getMonth() - 2);
		var firstDat, lastDate;

		firstDate = new Date(add.getFullYear(), add.getMonth(), 1);
		lastDate = new Date(add.getFullYear(), add.getMonth() +1, 0);

*/
		mVal = -2;
		var addDate = getAddDate(mVal);
		
		var lastDay = addDate.getDate();
		
		firstDate = new Date(addDate.getFullYear(), addDate.getMonth(),1);
		lastDate = new Date(addDate.getFullYear(), addDate.getMonth() +1, 0);
		
		$("#"+ startid).val(getDateString(firstDate));
		$("#"+ endid).val(getDateString(lastDate));
		
	} else if ( type == "prev3Month" ) { //석달전
/*
		add.setMonth(now.getMonth() - 3);
		var firstDat, lastDate;

		firstDate = new Date(add.getFullYear(), add.getMonth(),1);
		lastDate = new Date(add.getFullYear(), add.getMonth() +1, 0);

*/
		mVal = -3;
		var addDate = getAddDate(mVal);
		
		var lastDay = addDate.getDate();
		
		firstDate = new Date(addDate.getFullYear(), addDate.getMonth(),1);
		lastDate = new Date(addDate.getFullYear(), addDate.getMonth() +1, 0);
		
		$("#"+ startid).val(getDateString(firstDate));
		$("#"+ endid).val(getDateString(lastDate));
	}

//	$("#btnSrh").click();
}

function getAddDate(mVal){
	
	var month = now.getMonth()+1;
	var day = now.getDate();
	var year = now.getFullYear();

	
		mVal *= 1;

		var mon;
		var yr;
		var oMon = month + mVal;

		if(oMon == 0){
			mon = 12 - oMon;
			yr = year - 1;
		}else if(oMon < 0){
			mon = 12 -(-oMon);
			yr = year - 1;
		}else if(oMon > 12){
			mon = oMon -12
			yr = year + 1;
		}else{
			mon = month + mVal;
			yr = year;
		}

		var lastDay = ( new Date( yr, mon, 0) ).getDate();
		
		//var firstDate = yr + "-" + mon + "-01";
		//var lastDate = yr + "-" + mon + "-" +lastDay;
		
		return new Date( yr, mon, 0);
}


// 날짜 변경
function nextDateSubmit(obj) {
  nextDate(obj);
}

function nextMonthSubmit(obj) {
  nextMonth(obj);
}

function prevDateSubmit(obj) {
  prevDate(obj);
}

function prevMonthSubmit(obj) {
  prevMonth(obj);
}

function nextDate(obj) {
  var src = new Date($('#'+ obj).val());

  src.setDate(src.getDate() + 1);
  $('#'+ obj).val(getDateString(src));
  $('#'+ obj).change();
}

function nextMonth(obj) {
  var src = new Date($('#'+ obj).val());

  src.setMonth(src.getMonth() + 1);

  $('#'+ obj).val(getDateString(src));
  $('#'+ obj).change();
}

function prevDate(obj) {
  var src = new Date($('#'+ obj).val());

  src.setDate(src.getDate() - 1);

  $('#'+ obj).val(getDateString(src));
  $('#'+ obj).change();
}

function prevMonth(obj) {
  var src = new Date($('#'+ obj).val());

  src.setMonth(src.getMonth() - 1);

  $('#'+ obj).val(getDateString(src));
  $('#'+ obj).change();
}
// 날짜 변경 끝
/*
  공통 Submit
    - 메뉴 네비게이션이 바뀌는 이동시에 해당 메소드로 이동시킨다.
    - dept1Code, dept2Code 에 값을 담에 submit 한다
  
  frmId = form 아이디
  url   = URL
  dept1 = dept1 코드
  dept2 = dept2 코드
*/
function commSubmit(url, _this, searchParam) {
	var frmId  = "defaultFrm";    // default Form
	var frm    = $("#"+ frmId);
	//2020-06-22 메뉴 이동시 검색 파라미터 초기화
	
	if(searchParam) {
		$("#startDate").val('');
		$("#endDate").val('');
		$("#start_reg_date").val('');
		$("#end_reg_date").val('');
		$("#searchKey").val('');
		$("#searchValue").val('');
		$("#pageNo").val('1');
		$("input[name=day_type]").val('');
		$("#" + searchParam + "_seq").val('');
		
		if($("input[name=del_yn").length == 0) {
			frm.append("<input type='hidden' name='del_yn' value='0'>");
		}
		
		if($("input[name=use_yn]").length == 0) {
			frm.append("<input type='hidden' name='use_yn' value='1'>");
		}else { 
			$("input[name=use_yn]:radio[value='1']").prop('checked', true);
		}
		
		if(searchParam == 'notice') {
			$("input[name=auth_view]:radio[value='0']").prop('checked', true);
			$("#main_start_date").val(null);
			$("#main_view_yn").val(null);
			$("#main_end_date").val(null);
			$("#auth_company_seq").val(null);
		}
	}
	
  var input1 = $("#"+ frmId +" > input[name=ADMIN_DEPT1_CODE]");    // 관리자 메뉴 코드1
  var dept1 = $(_this).closest(".1deptMenu").index();

  if ( jQuery.type(input1.val()) === "undefined" ) {
    frm.append("<input type='hidden' name='ADMIN_DEPT1_CODE' value='"+ dept1 +"' />");
  } else {
    input1.val(dept1);
  }
  
  if ( url == "#" ) {
    alert('준비 중 입니다.');
  } else {
    frm.attr('action', url).submit();
  }
}


function setMonthPicker(interval){
	
	var addDate = getAddDate(interval*-1);
	
	var beforeMonth = addDate.getMonth()+1;
	var cur_year = addDate.getFullYear();
	
	if( beforeMonth < 10 ){
		beforeMonth = "0" + beforeMonth;
	}

	$(".mtz-monthpicker-year").val(cur_year);
	
	var months = $("#monthpicker_1").parent().parent().find('td[data-month]'); 
	months.removeClass('ui-state-active');
    months.filter('td[data-month='+ Number(beforeMonth) +']').addClass('ui-state-active');
    
        
	$("#startDate").val(cur_year+"-"+beforeMonth);
}



// Layer popup 변수
var $popLayer = $('#popup-layer');
var $dimmed = $('.dimmed');

var popLayer_html;


// Layer popup 열기
function openIrPopup(popupId, status) {
  //popupId : 팝업 고유 클래스명
  var $popUp = $('#'+ popupId);

  $dimmed.fadeIn();
  //$popLayer.hide();
  $popUp.show();

  var innerWidth = window.innerWidth;
  var innerHeight = window.innerHeight;
  var offsetWidth = -$popUp.width() / 2 ;
  var offsetHeight = -$popUp.height() / 2 ;
  
  $popUp.css({
    'margin-top': offsetHeight,
    'margin-left': offsetWidth
  });
  
  $popUp.css({top: '50%', left: '35%'});
}

function openIrPopupRe(popupId, status) {
	  //popupId : 팝업 고유 클래스명
	  var $popUp = $('#'+ popupId);

	  $dimmed.fadeIn();

	  var offsetWidth = -$popUp.width() / 3;
	  var offsetHeight = -$popUp.height() / 2;
	  
	  if(/Android|webOS|iPhone|iPad|iPod|pocket|psp|kindle|avantgo|blazer|midori|Tablet|Palm|maemo|plucker|phone|BlackBerry|symbian|IEMobile|mobile|ZuneWP7|Windows Phone|Opera Mini/i.test(navigator.userAgent)) {
		  $popUp.css({
		    'margin-top': "-547px",
		    'position' : 'relative',
		    'display' : "inline-block"
		  });
	  }else {
		  $popUp.css({
			    'margin-top': offsetHeight,
			    'margin-left': offsetWidth
			  });		  
		  $popUp.show();
	  }
	}

//Layer popup 닫기
function closeIrPopup(popupId) {
	var $popUp = $('#'+ popupId);
  $dimmed.fadeOut();
  $popUp.hide();

//  $popLayer.html(popLayer_html);
}


// jqGrid 공통함수
// 숫자만 입력
function validNum(val, nm, valref) {
  if ( $.isNumeric(val) ) {
    return [true, ""];
  } else {
    return [false, "숫자만 입력 가능 합니다."];
  }
}


//현장관리, 오더리스트 에서 현장매니저 
function  validManagerName(value, cellTitle, valref) {
	
	lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
	
	if ( value == "?" ) {
		$("#list").jqGrid('setCell', lastsel, 'manager_seq'              , 0,    '', true);
		$("#list").jqGrid('setCell', lastsel, 'manager_name'           , '?', '', true);
		$("#list").jqGrid('setCell', lastsel, 'manager_phone'   		 , null, '', true);
		$("#list").jqGrid('setCell', lastsel, 'manager_add'   		 , 0, '', true);
	}
    
	return [true, ""];
}

/*
	현장매니져 등록 버튼 바꾸기 - >   $("#list").jqGrid('setCell', lastsel, 'manager_add'  ,data.manager_seq, '', true);
	
	함수로 만들 필요 없이 data 값 만 바꾸어 주면 fometter 에 의해 자동으로 바뀐다.
*/
function changeManageAdd(rowId, flag){ //flag: 0 on 1: t1

	var grid = $("#list");
	var tr = grid[0].rows.namedItem(rowId);
	var td = tr.cells[26];
	var bt = $(td).children("div").children("div");
	var str = "";
	var work_seq			= $("#list").jqGrid('getRowData', rowId).work_seq;
	var manager_seq	= $("#list").jqGrid('getRowData', rowId).manager_seq; 
	
	/*if ( $(bt).hasClass("bt_on") ) {*/
	if ( flag ==0 ) {		
	      str += "<div class=\"bt_wrap\">";
	      str += "<div class=\"bt_t1\"><a href=\"JavaScript:void(0);\" onclick=\"managerAdd('"+ rowId +"'); \"> + </a></div>";
	      str += "</div>";
	}else {
	      str += "<div class=\"bt_wrap\">";
          str += "<div class=\"bt_on\"><a href=\"JavaScript:void(0);\" onclick=\"managerView('"+ work_seq +"', '"+ manager_seq +"', event);\"> V </a></div>";
          str += "</div>";
    }

	$(td).html(str); 

}

// 본사매니져관리, 현장관리에서 사용
function  validEmployerName(value, cellTitle, valref) {

	  lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
	  
	  if ( value == "?" ) {
	    $("#list").jqGrid('setCell', lastsel, 'employer_seq'		, 0,    '', true);
	    $("#list").jqGrid('setCell', lastsel, 'employer_add'		, '0', '', true);
	    $("#list").jqGrid('setCell', lastsel, 'employer_ilbo'		, '0', '', true);
	    
	    $("#list").jqGrid('setCell', lastsel, 'employer_num'	,null, '', true);
	    
	    
	  }
	  
	  return [true, ""];

}  

//오더관리에서 사용, 현장관리에서 사용
function  validCompanyName(value, cellTitle, valref) {

	  lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
	  
	  if ( value == "?" ) {
	    $("#list").jqGrid('setCell', lastsel, 'company_seq'  , 0,  '', true);
	    
	  }
	  
	  return [true, ""];

}


//구직자 리스트에서평가
function  validWorkerRating(value, cellTitle, valref) {//값 ,타이틀 row의 인덱스
	lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
	var worker_name = $("#list").jqGrid('getRowData', lastsel).worker_name;     	// 선택된 구직자 순
	var work_seq = $("#list").jqGrid('getRowData', lastsel).work_seq;     	// 선택된 구직자 순
	
	if ( worker_name = '' ) {
		return [false, "] 구직자가 없습니다.."];
	}

	return [true, ""];

}
	  


//auto select 에서는 기본 값을 ? 로 한다
function formatSelectName(cellvalue, options, rowObject) {
	
	if ( cellvalue == "" || cellvalue == null ) return "?";
	else	return cellvalue;
}


//폰번호체크
function validEmployerPhone(phoneNum, nm, valref) {

	  var result = true;
	  var resultStr = "";

	  if ( phoneNum == "" ) return [true, ""];

	  var valiResult = validPhone(phoneNum, null, null);
	  if ( !valiResult[0] ) return valiResult;

	  return [result, resultStr];
}



//주민번호 검증 구직자리스트에서 만 사용됨
function validJumin(value, cellTitle, valref) {
  if ( value != null && value != "" ) {
    
	  var RegNotNum  = /[^0-9]/g;
	var jumin = value.replace(RegNotNum,'');
	var num7  = jumin.charAt(6);
	
	 if (num7 > 4) {  // 외국인 ( 1900년대 5:남자 6:여자  2000년대 7:남자, 8:여자)
		 return [false, "외국인은 등록 할 수 없습니다."];
	 	}
	 
    var regExp = /^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))[1-6][0-9]{6}$/g;
    
    if ( regExp.test(jumin) ) {
    	
    	if(!isJuminValid(jumin)){
            
            return [false, "입력하신 주민번호는 유효한 주민번호가 아닙니다."];
        }
    	
      return [true, ""];
      
    } else {
      return [false, "주민번호 입력 오류.(숫자만 입력하세요.)"];
    }
  } else {
    return [true, ""];
  }
}

/*******************************************************
*  기능      :  주민번호 체크                          *
*  parameter :  String                                 *
*  return    :  Void                                   *
********************************************************/
function isJuminValid(juminNo) {
 var strJumin = juminNo.replace("-", "");
 var checkBit = new Array(2,3,4,5,6,7,8,9,2,3,4,5);
 var num7  = strJumin.charAt(6);
 var num13 = strJumin.charAt(12);
 var total = 0;
 if (strJumin.length == 13 ) {
  for (i=0; i<checkBit.length; i++) { // 주민번호 12자리를 키값을 곱하여 합산한다.
    total += strJumin.charAt(i)*checkBit[i];
  }
  // 외국인 구분 체크
  if (num7 == 0 || num7 == 9) { // 내국인 ( 1800년대 9: 남자, 0:여자)
   total = (11-(total%11)) % 10;
  }
  else if (num7 > 4) {  // 외국인 ( 1900년대 5:남자 6:여자  2000년대 7:남자, 8:여자)
   total = (13-(total%11)) % 10;
  }
  else { // 내국인 ( 1900년대 1:남자 2:여자  2000년대 3:남자, 4:여자)
   total = (11-(total%11)) % 10;
  }
 
  if(total != num13) {
   return false;
  }
  return true;
 } else{
  return false;
 }
}


//외국인
function isFrgNo(fgnno) { 
	var sum = 0; 
	var odd = 0; 
	buf = new Array(13); 

	for(i=0; i<13; i++) buf[i] = parseInt(fgnno.charAt(i)); 

	odd = buf[7]*10 + buf[8]; 

	if(odd%2 != 0) return false; 

	if((buf[11]!=6) && (buf[11]!=7) && (buf[11]!=8) && (buf[11]!=9)) return false; 

	multipliers = [2,3,4,5,6,7,8,9,2,3,4,5]; 

	for(i=0, sum=0; i<12; i++) sum += (buf[i] *= multipliers[i]); 

	sum = 11 - (sum%11); 

	if(sum >= 10) sum -= 10; 

	sum += 2; 
	if(sum >= 10) sum -= 10; 

	if(sum != buf[12]) return false;

	return true; 
}

//출처: http://mynotepadblog.tistory.com/72 [MyNotepad]

//주민번호 유효성 검사
function ssnCheck(value){           
	var checkNum = [2,3,4,5,6,7,8,9,2,3,4,5];
	var sum = 0;

	for(var i=0; i< checkNum.length ; i++){
		var tmp = value.charAt(i)*checkNum[i];

		sum += tmp;
	}

	var pin = 11-(sum%11);
	if(pin>=10) pin = pin-10;

	if(value.charAt(12)==pin){
		return true;
	}else{
		return false;
	}
}


// 주민등록번호 포맷
function formatRegNo(cellvalue, options, rowObject) {
	var regNo = cellvalue;

	var RegNotNum  = /[^0-9]/g;

	// return blank
	if ( regNo == "" || regNo == null || regNo == "?" ) return "";

	// delete not number
	regNo = regNo.replace(RegNotNum,'');

	return regNo = regNo.replace(/([0-9]{6})([0-9]{7})/, "$1-$2");
}

// 사업자등록번호 포맷
function formatRegNum(cellvalue, options, rowObject) {
	var regNum = cellvalue;
	
	var RegNotNum  = /[^0-9]/g;
	
	// return blank
	if ( regNum == "" || regNum == null || regNum == "?") return "";
	
	// delete not number
	regNum = regNum.replace(RegNotNum,'');
	
	return regNum = regNum.replace(/([0-9]{3})([0-9]{2})([0-9]{5})/, "$1-$2-$3");
}

function validBizID(bizID)  //사업자등록번호 체크 
{ 
	if ( bizID == "?" ) {
		var  lastsel  = $("#list").jqGrid('getGridParam', "selrow" );         // 선택한 열의 아이디값
		$("#list").jqGrid('setCell', lastsel, 'employer_pass', null, '', true);
		
	    return [true, ""];
	}
	 
	if ( bizID == "" || bizID == null ) return [false, "사업자등록 번호 오류[올바르지 않은 사업자 등록 번호 입니다.]"];
	
	var RegNotNum  = /[^0-9]/g;

	var regNum = bizID.replace(RegNotNum,'');
	var regExp = /([0-9]{3})([0-9]{2})([0-9]{5})/;
	
    if (! regExp.test(bizID) ) {
      return [false, "사업자등록 번호 입력 오류( 숫자만 입력하세요.)"];
    }
	  
    // bizID는 숫자만 10자리로 해서 문자열로 넘긴다. 
    var checkID = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1); 
    var tmpBizID, i, chkSum=0, c2, remander; 
    bizID = bizID.replace(/-/gi,''); 

     for (i=0; i<=7; i++) chkSum += checkID[i] * bizID.charAt(i); 
     c2 = "0" + (checkID[8] * bizID.charAt(8)); 
     c2 = c2.substring(c2.length - 2, c2.length); 
     chkSum += Math.floor(c2.charAt(0)) + Math.floor(c2.charAt(1)); 
     remander = (10 - (chkSum % 10)) % 10 ; 
     
    if (Math.floor(bizID.charAt(9)) == remander)  return [true, ""]; // OK! 
    
    return [false, "사업자등록 번호 오류[올바르지 않은 사업자 등록 번호 입니다.]"]; 
} 

//핸드폰 체크 - 반드시 입력을 해야 될때..
function validCheckNullPhone(value,cellTitle,valref){
	if ( value == null || value == "" ) {
		return [false, "핸드폰번호를 입력하세요. "];
	}
	
	return validPhone(value, cellTitle, valref);
}
//핸드폰 체크 
function validPhone(value, cellTitle, valref) {
	if ( value != null && value != "" ) {		
		//폰에서 - 빼기
		var phoneNum = value.replace(/#/gi, "");
		// 숫자만 골라내기
		//var RegNotNum  = /[^0-9]/g;
		//var phoneNum = value.replace(RegNotNum,'');
    
		var regExp =  /^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/;
		
		if (regExp.test(phoneNum) ) {
			return [true, ""];
		} else {
			return [false, "핸드폰 번호 입력 오류. "];
		}
	} else {
		return [true, ""];
	}
}

function checkMyformatPhone(cellvalue, options, rowObject){
	var workerCompanySeq = rowObject.company_seq
	if(companySeq != workerCompanySeq){
		return phoneNumMask(cellvalue);
	}else{
		return formatPhone(cellvalue, options, rowObject);
	}
}

//전화번호 포맷
function formatPhone(cellvalue, options, rowObject) {
  
	  var phoneNo = cellvalue;
	  
	  var RegNotNum  = /[^0-9]/g;
	
	  // return blank
	  if ( phoneNo == "" || phoneNo == null ) return "";
	
	  // delete not number
	  phoneNo = phoneNo.replace(RegNotNum,'');
	  
	  return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
}

//전화번호 
function formatPhoneStar(cellvalue, options, rowObject) {
	
	  var phoneNo = cellvalue;
	  
	  var RegNotNum  = /[^0-9]/g;
	
	  // return blank
	  if ( phoneNo == "" || phoneNo == null ) return "";
	
	  // delete not number
	  phoneNo = phoneNo.replace(RegNotNum,'');
	
	  
	  if(companySeq !=0 && rowObject.company_seq != companySeq && authLevel != "0"){
		  return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-****");
	  }else{
		  return phoneNo = phoneNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
	  }
}

//주민등록번호 포맷
function formatJuminStar(cellvalue, options, rowObject) {
	var regNo = cellvalue;

	var RegNotNum  = /[^0-9]/g;

	// return blank
	if ( regNo == "" || regNo == null || regNo == "?" ) return "";

	// delete not number
	regNo = regNo.replace(RegNotNum,'');

	if(companySeq !=0 && rowObject.company_seq != companySeq && authLevel != "0"){
		return regNo = regNo.replace(/([0-9]{6})([0-9]{7})/, "$1-*******");
	}else{
		return regNo = regNo.replace(/([0-9]{6})([0-9]{7})/, "$1-$2");
	}
}

//사업자번호 포맷
function formatRegNoStar(cellvalue, options, rowObject) {
	var regNum = cellvalue;
	
	var RegNotNum  = /[^0-9]/g;
	
	// return blank
	if ( regNum == "" || regNum == null || regNum == "?") return "";
	
	// delete not number
	regNum = regNum.replace(RegNotNum,'');
	if(companySeq !=0 && rowObject.company_seq != companySeq && authLevel != "0"){
		return regNum = regNum.replace(/([0-9]{3})([0-9]{2})([0-9]{5})/, "$1-$2-*****");
	}else{
		return regNum = regNum.replace(/([0-9]{3})([0-9]{2})([0-9]{5})/, "$1-$2-$3");
	}
}



//도착시간 마감시간 검사
function validWorkTime(value, nm, valref) {
		
	  if ( value != null && value != "" ) {
	    
		//var RegNotNum  = /[^0-9]/g;
	    //var strTime = value.replace(RegNotNum,'');
		  
	    //var regExp =  /^([0|1|2])([0-9]{1})([0|1|2])([0-9]{1})$/;
	    
	    // 24시기준
	    //var regExp =  /^(([0|1])([0-9]{1})|([2])([0-4]{1}))([0|1|2|3|4|5])([0-9]{1})$/;
	    
	    
	    //36시 기준
	    var regExp =  /^(([0|1|2])([0-9]{1})|([3])([0-6]{1}))([0|1|2|3|4|5])([0-9]{1})$/;
	    
	    if(Number(value) > 3600){
	    	return [false, "시간입력 오류. or 숫자만 입력하세요."];
	    }
	    
	    if ( regExp.test(value) ) {
	      return [true, ""];
	    } else {
	      return [false, "시간입력 오류. or 숫자만 입력하세요. "];
	    }
	  } else {
	    return [true, ""];
	  }
}

//도착시간 마감시간 포맷
function formatTime(cellvalue, rowid, rowObject){
	  var regNo = cellvalue;
	  if ( regNo == "" || regNo == null || regNo == "?" ) return "";
	  
	  regNo = getTimeNumber(regNo);

	  return regNo = regNo.replace(/([0-9]{2})([0-9]{2})/, "$1:$2");
}

function getTimeNumber(timeStr){
	var RegNotNum  = /[^0-9]/g;
	var timeNumber = timeStr.replace(RegNotNum,'');

	return timeNumber
}

//좌표
function abspos(e) {
  this.x = e.clientX + (document.documentElement.scrollLeft?document.documentElement.scrollLeft:document.body.scrollLeft);
  this.y = e.clientY + (document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop);

  return this;
}

// option display
function writeOpt(layerName, seq, codes) {
	
	$("#opt_layer").html("");

	optHTML = "";
	
	if(layerName == "job_layer"){
		optHTML += "<div id=\""+ layerName+"\" class=\"bt_wrap multi mgS\" style=\"width: 450px; display: none; background-color: #fff;\">";
	    
		
		if ( codes == 0 ) {
			optHTML += optJob.replace(/<<WORKER_SEQ>>/gi, seq);          	
	    } else {
	    	
	    	var codeArr = codes.split(",");
	  		var _html = "";
	
	  		_html = optJob;
	  		
	      	for ( var i = 0; i < codeArr.length; i++ ) {
	        	_html = _html.replace(/<<WORKER_SEQ>>/gi, seq).replace(codeArr[i] +" bt", codeArr[i] +" bt_on");
	      	}
	      	optHTML += _html;
	    }
	
		optHTML += clsHTML;
	    optHTML += "<div class='bt_green'><a href=\"JavaScript:void(0);\" onclick=\"getKindCount(this,'"+ seq +"');\" isLog='none'>출역로그</a></div>";
	    optHTML += "<div class='bt_reset bt_t1'><a href=\"JavaScript:chkCodeUpdate('job_layer', '"+ seq +"', 'worker_job_code', 0, 'worker_job_name', '' ,'0');\"> 초기화 </a></div>";
	    optHTML += "</div>";
	    
	}else if(layerName == "bank_layer"){
    
	    optHTML += "<div id=\"bank_layer\" class=\"bt_wrap single\" style=\"width: 450px; display: none; background-color: #fff;\">";
	    optHTML += optBank.replace(/<<WORKER_SEQ>>/gi,seq).replace("\"" +codes +" bt\"", "\"" + codes +" bt_on\"");
	    optHTML += clsHTML;
	    optHTML += "<div class=\"bt_reset\"><div class=\"bt_t1\"><a href=\"JavaScript:chkCodeUpdate('bank_layer', '"+ seq +"', 'worker_bank_code', 0, 'worker_bank_name', '' ,'0');\"> 초기화 </a></div></div>";
	    optHTML += "</div>";
	    
	}
	
     $("#opt_layer").html(optHTML);
  
}

//일보에서 호출됨.
function writeOpt2(optHTML) {
	
	$("#opt_layer").html("");
	$("#opt_layer").html(optHTML);

	if(typeof shareYn !== "undefined" && shareYn == '0'
		&& typeof authLevel !== "undefined" && authLevel > '0'){
		$('#USE002').remove();
	}
}


//상태변경
function chkCodeUpdate(divId, worker_seq, codeValue, val, codeName, nm) {
	var str = '{"worker_seq": "'+ worker_seq +'", "'+ codeValue +'": "'+ val +'", "'+ codeName +'": "'+ nm +'"}';
	var addFlag = true;
	var selVal = val;
	
	if($("#"+ divId).is(".multi")) {        // 복수 선택이 가능한 옵션인 경우
		if(val == 0) {
			$("#"+ divId +" > .bt_on").each(function(index) {
				$(this).removeClass("bt_on");
				$(this).addClass("bt");
			});
		}    
		
		if(val != "") { // 삭제
			if($("#"+ divId +" > ."+ val).is(".bt_on")) {
				$("#"+ divId +" > ."+ val).removeClass("bt_on");
	            $("#"+ divId +" > ."+ val).addClass("bt");
	            
	            addFlag = false;
	        }else { //추가
	            $("#"+ divId +" > ."+ val).removeClass("bt");
	            $("#"+ divId +" > ."+ val).addClass("bt_on");
	            
	            addFlag = true;
	        }

			var _code = "";
			var _name = "";
			
			$("#"+ divId +" > .bt_on").each(function(index) {
				var _class = "";
				var classArr;
			
			  	_class = $(this).attr('class');
			  	classArr = _class.split(" ");
			
			  	_name += "_"+ $(this).find(" > a").html().trim();
			  	_code += "_"+ classArr[0];
			});
			
			val = _code;
			nm = _name;
		}

		str = '{"worker_seq": "'+ worker_seq +'", "'+ codeValue +'": "'+ val +'", "'+ codeName +'": "'+ nm +'"}';
	}else {
	    $("#"+ divId +" > .bt_on").each(function(index) {
	    	$(this).removeClass("bt_on");
	    	$(this).addClass("bt");
	    });

		$("#"+ divId +" > ."+ val).removeClass("bt");
		$("#"+ divId +" > ."+ val).addClass("bt_on");
	}

	var params = jQuery.parseJSON(str);
	var osh_yn = $("#list").jqGrid("getRowData", worker_seq);
	
	if(authLevel > 0) {
		if($($(osh_yn.worker_OSH_yn)[0].children[0]).hasClass("bt_on")) {
			osh_yn = '0';
		}else {
			osh_yn = '1';
		}
	}else {
		osh_yn = '1';
	}
	
	if(divId == 'job_layer' && authLevel != '0') {
		var selectJobArray = replaceAll(val.substring(1, val.length), "_", ",").split(",");
		var usuallyCnt = 0;
		var usuallyFlag = true;
		var poreCnt = 0;
		var poreFlag = true;
		
		jobArray.find(function(item){   
			for(var i = 0; i < selectJobArray.length; i++) {
				if(selectJobArray[i] == item.job_seq) {
					if(item.job_kind == '1') {
						++usuallyCnt;
					}else {
						++poreCnt;
					}
				}
				
				if(selVal == item.job_seq) {
					if(item.job_kind == '1') {
						usuallyFlag = false;
					}else {
						poreFlag = false;
					}
				}
			}
		}); 

		if(addFlag) {
			if(usuallyCnt > jobLimitCount && !usuallyFlag) {
				alert("분야별 직종은 각각 최대 " + jobLimitCount + "개까지 등록가능합니다.");
				
				$("#"+ divId +" > ."+ selVal).removeClass("bt_on");
	            $("#"+ divId +" > ."+ selVal).addClass("bt");
	            
				return;
			}
			
			if(poreCnt > jobLimitCount && !poreFlag) {
				alert("분야별 직종은 각각 최대 " + jobLimitCount + "개까지 등록가능합니다.");
				
				$("#"+ divId +" > ."+ selVal).removeClass("bt_on");
	            $("#"+ divId +" > ."+ selVal).addClass("bt");
	            
				return;
			}
		}
	}
	
	if(divId == 'job_layer') {
		var isOsh = true;
		jobArray.find(function(item){   
			if(selVal == item.job_seq) {
				if(item.job_field == '1') {
					if(osh_yn == '0' && addFlag) {
						alert("기초안전보건교육증 등록 후 선택가능한 직종입니다.");
						
						$("#"+ divId +" > ."+ selVal).removeClass("bt_on");
			            $("#"+ divId +" > ."+ selVal).addClass("bt");
			            isOsh = false;
						return;
					}
				}
			}
		}); 
		
		if( !isOsh ){
			return;
		}
	}
	
	$.ajax({
		type: "POST",
		url: "/admin/setWorkerInfo",
    	data: params,
		dataType: 'json',
		success: function(data) {
			// 전문분야
			if(divId == 'job_layer') {
				$("#list").jqGrid('setCell', worker_seq, 'worker_job_code', data.worker_job_code, '', true);
				$("#list").jqGrid('setCell', worker_seq, 'worker_job_name', data.worker_job_name, '', true);
			}

			// 은행명
			if(divId == 'bank_layer') {
				$("#list").jqGrid('setCell', worker_seq, 'worker_bank_code', val, '', true);
				$("#list").jqGrid('setCell', worker_seq, 'worker_bank_name', nm, '', true);
			}
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

	if ( !$("#"+ divId).is(".multi") ) {       // 단일선택 옵션인 경우
		closeOpt();
	}
}


// option layer display
// 공통
function selectOpt(layerName, seq, codes, e) {
	
	var $opt_layer = $('#opt_layer');
	var _display;

	if ( !e ) e = window.Event;
	var pos = abspos(e);
	
	if ( $("#"+ layerName).css("display") == null || $("#"+ layerName).css("display") == "undefined" || $("#"+ layerName).css("display") == "none" ) {
		writeOpt(layerName, seq, codes);
	}

	$opt_layer.find('.bt_wrap').css('display', 'none');

	_display = $opt_layer.css('display') == 'none'?'block':'none';
	
	$opt_layer.css('left', (pos.x - 100) +"px");
	$opt_layer.css('top', (pos.y-20) +"px");

	$opt_layer.css('display', _display);
	$("#"+ layerName).css('display', _display);
	
	//code_log_layer 가 열려 있다면 닫아준다..
	$('#code_log_layer').html("");
	$('#code_log_layer').css('display', 'none');
	
}


function closeOpt() {
	var $opt_layer = $('#opt_layer');

//	_display = $opt_layer.css('display') == 'none'?'block':'none';
	_display = 'none';

	$opt_layer.css('display', _display);
	$opt_layer.find('.bt_wrap').css('display', _display);
	$opt_layer.html("");


	$('#code_log_layer').html("");
	$('#code_log_layer').css('display', 'none');
	
	$("#opt_layer").empty();
}

//option layer display
function showCodeLog(seq, code_type, e) {
	 
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
		   ilbo_seq		: seq,
		   code_type 	: code_type
   };
   
	$.ajax({
		
	    type	: "POST",
	    url		: "/admin/getCodeLog",
	    data	: _data,
	    dataType: 'json',
	    success: function(data) {
	    	var _html = "";
	    	if ( data != null && data.length > 0 ) {
	    		
	    		_html = "<table class='bd-form s-form'>";
	    		
	    		for ( var i = 0; i < data.length; i++ ) {
	    			_html += "<tr><td style='padding:5px;'>" +data[i].log_text + "</td><td  style='padding:5px;'> "+ data[i].code_name + "</td><td  style='padding:5px;'>" + data[i].reg_date + "</td></tr>";
	    		}
	    		
	    		_html+="</table>";
	    	}
	    	
	    	$('#code_log_layer').html(_html);
	    	$('#code_log_layer').css('display', _display);
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


//jjh 추가
function mapWindowOpen(mode,seq,latlng,addr,comment) {
  //기본값으로 서울 시청 값을 넣어 준다.
  var lat = "";
  var lng = "";
  
  var tmpSplit = latlng.split(",");
  
  if ( tmpSplit.length == 2 ) {
    lat = tmpSplit[0];
    lng = tmpSplit[1];
  }
  
  //
  /*addr = decodeURIComponent((addr + '').replace(/\+/g, '%20'));
 // comment = decodeURIComponent((comment + '').replace(/\+/g, '%20'));
  
  var winObject = window.open("/resources/web/popup/map.htm?mode="+mode+"&seq="+seq+"&lat="+lat+"&lng="+lng+"&addr="+addr+"&comment="+comment, "주소정하기", "width=900, height=1000,  left=20, top=20, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
  */
  
  window.open("", "popup_window", "width=900, height=1000,  left=20, top=20, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
  
  $("#map_mode").val(mode);
  $("#map_seq").val(seq);
  $("#map_lat").val(lat);
  $("#map_lng").val(lng);
  $("#map_addr").val(addr);
  $("#map_comment").val(comment);
  
  $("#mapFrm").attr("target","popup_window").attr("action","/resources/web/popup/map.jsp").submit();
}

function locationWindowOpen(seq, kindCode, latlng, ilbo_date) {
	  if(!latlng || latlng == 'null') {
		  return false;
	  }
	  
	  //기본값으로 서울 시청 값을 넣어 준다.
	  var lat = "";
	  var lng = "";
	  
	  var tmpSplit = latlng.split(",");
	  
	  if ( tmpSplit.length == 2 ) {
	    lat = tmpSplit[0];
	    lng = tmpSplit[1];
	  }
	  
	  window.open("", "popup_window", "width=" + screen.width + ",height=" + screen.height + ", toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
	  
	  $("#location_seq").val(seq);
	  $("#location_lat").val(lat);
	  $("#location_lng").val(lng);
	  $("#location_kindCode").val(kindCode);
	  $("#ilbo_date").val(ilbo_date);
	  
	  $("#locationFrm").attr("target","popup_window").attr("action","/resources/web/popup/location.jsp").submit();
}


//list에 comment 적용 map.htm 으로 부터 받는다.
function mapGridUpdate(mode, lastsel,comment){
	
    if(mode == "WORK"){
    	  $("#list").jqGrid('setCell', lastsel, 'work_addr_comment' , (comment == "")? null:comment,    '', true);
    	  
    }else if(mode == "ILBO"){
    	$("#list").jqGrid('setCell', lastsel, 'ilbo_work_addr_comment'  , (comment == "")? null:comment,    '', true);
    }
    
    //addr_edit 의 값을 주어 새로고침 없이 클릭 할때 새로 반영된 값이 팝업창에 올라 가도록 한다.
    $("#list").jqGrid('setCell', lastsel, 'addr_edit'               , (comment == "")? null:comment                 , '', true);
}




//구인처 상세정보
function employerView(rowId) {
	var employerSeq = $("#list").jqGrid("getCell", rowId, "employer_seq");
	var companySeq = $("#list").jqGrid("getCell", rowId, "company_seq");
	
	var str = '{"employer_seq": "'+ employerSeq +'", "'+ company_seq +'": "'+ companySeq +'"}';
	
	var params = jQuery.parseJSON(str);
	
	$.ajax({
	    type: "POST",
	     url: "/admin/getEmployerViewJSP",
	    data: params,
	//dataType: 'json',
	 success: function(result) {
	           $("#popup-layer2 > header> h1").html("구인처 대장");         // popup title
	            $("#popup-layer2 > section").html(result);              // 실행 결과 페이지 부모페이지에 삽입
	            openIrPopup('popup-layer2');
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




function writeEmployer() {
	window.open("/admin/writeEmployer",'_blank');
}

function setSearchOptSession(_data) {
	$.ajax({
    	url: "/admin/setSearchOptSession",
        data: _data,
        dataType: 'json',
        success: function (data) {
        	
        },
        beforeSend: function(xhr) {
        	xhr.setRequestHeader("AJAX", true);
        },
        error: function(e) {
        	console.log("error", e);
		}
	});
}


// token 발급용 func
function fn_tokenIssued() {
	$.ajax({
		url : "/admin/selectToken",
		dataType : 'json',
		success : function(data) {
			if(data.adminDTO.web_token) {
				var confirmVal = confirm("이미 토큰이 존재합니다. 새로 발급 받으시겠습니까?");
				
				if(confirmVal) {
					// Test용 url 변경 필요 
					window.open("/resources/index.htm",'_blank');
				}
			}else {
				window.open("/resources/index.htm",'_blank');
			}
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader("AJAX", true);
		},
		error : function(e) {
			console.log("error", e);
		}
	});
}

function fn_versionBoard(obj){
	var boardName = $(obj).text();
	
	var frmId  = "defaultFrm";    // default Form
	var frm    = $("#"+ frmId);
	
	var input1 = $("#"+ frmId +" > input[name=ADMIN_DEPT1_CODE]");    // 관리자 메뉴 코드1
	var input2 = $("#"+ frmId +" > input[name=ADMIN_DEPT2_CODE]");    // 관리자 메뉴 코드 2
	
	var dept1 = $(obj).closest(".1deptMenu").index();
	var dept2 = $(obj).closest(".2deptMenu").index();
	  
	if ( dept1 < 0 )      dept1 = dept1Code;
	
	// 2020-06-23 설정 left 메뉴(다른 메뉴 이동후 다시 돌아오면 첫번째 메뉴 index 가지고 있기)
	if(dept2 == -1)       dept2 = 0; 
	//if ( dept2 < 0 )      dept2 = dept2Code;
	
	if ( jQuery.type(input1.val()) === "undefined" ) {
	  frm.append("<input type='hidden' name='ADMIN_DEPT1_CODE' value='"+ dept1 +"' />");
	} else {
	  input1.val(dept1);
	}
	  
	if ( jQuery.type(input2.val()) === "undefined" ) {
	  frm.append("<input type='hidden' name='ADMIN_DEPT2_CODE' value='"+ dept2 +"' />");
	} else {
	  input2.val(dept2);
	}
	
	
	frm.append("<input type='hidden' name='board_name' value='"+ boardName +"' />");
	frm.attr('action', "/admin/board/boardList").submit();
}

function isEmpty(str){
    
    if(typeof str == "undefined" || str == null || str == "")
        return true;
    else
        return false ;
}


function checkMyWorker(rowId){
	
	if(authLevel == '0'){
		return true;
	}
	
	var workerCompanySeq = $("#list").jqGrid('getRowData', rowId).company_seq
	if(companySeq == workerCompanySeq){
		return true;
	}else{
		return false;
	}
}


function checkMyWork(rowId){
	
	if(authLevel == '0'){
		return true;
	}

	var workCompanySeq = $("#list").jqGrid('getRowData', rowId).company_seq
	
	if(companySeq == workCompanySeq){
		return true;
	}else{
		return false;
	}
}

function checkMyManager(rowId){
	
	if(authLevel == '0'){
		return true;
	}
	
	var managerCompanySeq = $("#list").jqGrid('getRowData', rowId).company_seq
	if(companySeq == managerCompanySeq ){
		return true;
	}else{
		return false;
	}
}

function checkMyEmployer(rowId){
	
	if(authLevel == '0'){
		return true;
	}
	
	var employerCompanySeq = $("#list").jqGrid('getRowData', rowId).company_seq
	if(companySeq == employerCompanySeq ){
		return true;
	}else{
		return false;
	}
}

function isSuperAdmin(){
	if(authLevel == '0'){
		return true;
	}
	return false;
}

function getMaskingPhone(phone){
	//끝 4자리 마스킹(****) 처리
	if( phone == null || phone.length < 5 ){
		return phone
	}
	
	return phone.substring(0, phone.length - 4) + "****";
}

function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
} 

function inputNumberFormat(obj) {
    obj.value = comma(uncomma(obj.value));
}
