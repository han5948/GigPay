
var selectThis;


$(document).ready(function(){
	$(".date-calendar").ionDatePicker();
	//getTimeStamp();
	
	//현장리스트를 가져온다.
	if(pageName == "main"){
		getWorkList();
	}
	  
    $('.select-gray').css({'color':'#838383'});
	
    $('.select-gray').change(function() {
		
	  var current = $('select-gray').val();
	  
	  if (current != 'null') {
		  $(this).css({'color':'#111'});
	  } else {
		  $(this).css({'color':'#838383'});
	  }
	  
	}); 


    $("#work_seq").change(function(){
	    //alert($(this).val());
	    //alert(!$(this).children("option:selected").text());
	    
	    $("#srh_seq").val($(this).val());
	    fn_submit();

	});

    
	$('.default').click(function(){
		$(this).removeClass('.default').addClass('succeed');
	});
	$('.login-type').find('a').click(function(){
		$(this).removeClass('.active').addClass('active');
		$(this).parent('li').siblings('li').find('a').removeClass();
	});


	$('.topmenu').find('a').click(function(){
		$(this).parent('li').removeClass('.over').addClass('over');
		$(this).parent('li').siblings('li').removeClass('over');
	});
	
	
	//푸쉬 수신 클릭
	$('#manager_push_yn').click(function(){
		
		setPush($(this).is(':checked'));
		
	});
	
	$('.membership-btn').click(function(){
		
		$('.membership.layer-pop').show();
		ovShow();
	});
	
	$('.membership2-btn').click(function(){
		
		$('.membership2.layer-pop').show();
		ovShow();
	});
	
	$('.membershipPay-btn').click(function(){
		
	});
	
	
	$('#header').find('.placement-close').click(function(){
		
		popClose();

	});
	
	
	$('.password_modi').click(function () {
        $('.password_modi_box.layer-pop').show();
        ovShow();
    });

    $('.mybuisness_modi').click(function () {
        
    	funcEmployerInfo();
    	
    });

    //현장추가 버튼
    $('#btnPlus').click(function () {

    	var employerSize = $("#busi_employer_seq option").size();
    	
    	// 회사가 없을때.
    	if(employerSize == 0){
    		$("#busi_mode").val(0);
    		$("#btn_employer_add").hide();
    		funcBusiEmployerAddBtn();
    	}else{
    		$("#busi_mode").val(1);
    		$("#btn_employer_add").show();
    		funcBusiEmployerAddBtn();
    	}
    	
        $('.buis_box.layer-pop').show();
        ovShow();
    });

    $('.job-btn').click(function () {
        $('.realtime_box.layer-pop').show();
        ovShow();
    });

    

	$('.apporder-btn').click(function () {

        var viewObj = $('.app_order'); 
		popShow(viewObj);
		
		//현장 담당자 , 현장담당자 연락처 셋팅...
		funcOrderWorkChange();
		
    });
	
	$('#order_workSeq').change(function(){
		funcOrderWorkChange();
	});

	
    $('.app_order').find('.app-close-btn').click(function () {
       
    	 popClose();
    });
    

	$('.layer-pop ').find('.close, .ok-btn').click(function(){
		ovHide()
	});

	$("#workadd").click(function(){
    	funcAddWork();
	});

	 $(".appOrder_btn").click(function () {
	        // var result = apporder_check() == true ? true : false;
	        	fucWorkProcess();
	 });
	 
	 //worker 숫자 올리기 내리기 onclick fun 으로 처리 함.
	 //updown();	
	 
	 $('.job_btnBox p').click(function () {
		 funcJobclick(this);
	 });
	 	    
    
	if($(".body-slider-ovclick").length<1) $("<div class='body-slider-ovclick'/>").appendTo($("#doc")).hide();
	if($(".container-ovclick").length<1) $("<div class='container-ovclick'/>").appendTo($(".placement-wrap")).hide();

	
    //초기 체크가 있는지 확인한다.
	funcCheckList();
    
    
    
    
    $("#btn_employer_add").click(function(){
		funcBusiEmployerAddBtn()
	});
    
    $("#busi_ok").click(function(){
		funcBusiWorkAdd();
	});
    
    //주소확인
    $("#busi_addr_confirm").click(function(){
		geocodeAddress($("#busi_work_name").val());
		
				
	});
    
    //내일 이분보내주세요
    $(".thisperson-btn").click(function(){
    	funcCopyIlboCell("Y");
	});
    
    //내일 한분보내주세요
    $(".oneperson-btn").click(function(){
    	funcCopyIlboCell("N");
	});
    
    //작업취소
    $(".jobcancel-btn").click(function(){
    	funcWorkCancel();
	});
    
    
    //패스워드변경 버튼
    $("#newPass-btn").click(function(){
    	funcNewPass();
	});
    
  //작업정보
    $(".workInfo").click(function(){
    	//alert($(this).attr("workInfo"));
    	
    	$("#workInfo_workName").html($(this).attr("workName"));
    	$("#workInfo_managerPhone").html($(this).attr("managerPhone"));
    	$("#workInfo_startTime").html( formatTime($(this).attr("startTime")));
    	$("#workInfo_endTime").html( formatTime($(this).attr("endTime")) );
    	$("#workInfo_workComment").html($(this).attr("workComment") );
    	
    	
    	$('.work_box.layer-pop').show();
        ovShow();
	});
    
    
});//end $(document).ready

//현장 담당자 , 현장담당자 연락처 셋팅...
function funcOrderWorkChange(){
	var mName =  $("#order_workSeq option:selected").attr('workMangerName');
	
	if( managerMode == "M"){
		if(typeof mName == "undefined" || mName == null || mName == "" || mName == 'null'){
			$("#work_manager").val(managerName);
			$("#work_manager_phone").val(managerPhone);
		}else{
			$("#work_manager").val( $("#order_workSeq option:selected").attr('workMangerName') );
			$("#work_manager_phone").val($("#order_workSeq option:selected").attr('workMangerPhone') );
		}
	}else{
		if(typeof mName == "undefined" || mName == null || mName == "" || mName == 'null'){
			$("#work_manager").val( $("#order_workSeq option:selected").attr('mangerName') );
			$("#work_manager_phone").val($("#order_workSeq option:selected").attr('mangerPhone') );
		}else{
			$("#work_manager").val( $("#order_workSeq option:selected").attr('workMangerName') );
			$("#work_manager_phone").val($("#order_workSeq option:selected").attr('workMangerPhone') );
				
		}
	}	
	
}

//현장추가
function funcBusiWorkAdd(){
	if($("#busi_mode").val() == "0"){

		if($("#busi_employer_seq").val() == ""){
			toastFail("회사상호를 선택 해 주세요.");
			return
		}
			
	}else{
		if($("#busi_employer_name").val() == ""){
			toastFail("회사상호를 입력 해 주세요.");
			return
		}
	}
	
	if($("#busi_work_name").val() == ""){
		toastFail("현장 주소를 입력해 주세요.11");
		return
	}
	
	if($("#busi_latlng").val() == ""){
		toastFail("현장 주소 입력 후 확인 버튼을 눌러주세요.");
		return
	}
	
	funManagerWorkProcess();
	
	

	
}

//앱오더 현장추가 ->  사업장추가
// busi_mode 0:기존 구인처에 현장추가 1:신규구인처에 현장추가 -->

function funcBusiEmployerAddBtn(){
	var mode = $('#busi_mode').val();
	
	if(mode == '0'){	// 구인처 추가
		$('#busi_mode').val("1");
		$('#busi_employer_name').val("");
		$('#busi_employer_name').show();
		$('#busi_employer_seq').hide();
		
		$("#btn_employer_add").text("취소");
		
		
	}else{ //현장추가
		$('#busi_mode').val("0");
		$('#busi_employer_name').hide();
		$('#busi_employer_seq').show();
		
		$("#btn_employer_add").text("+");
	}

}


function popShow(viewObj){
	popClose();
  	
	$('.info-nodata').hide();
	$('.placement-wrap').find('.fr').addClass('open');
	$('#header').find('.prev').hide();
	$('#header').find('.placement-close').show();
	
	viewObj.show();
}


function popClose(){
	//ovHides();
	$('.info-nodata').show();
	$('.info-view').hide();
	$('#header').find('.prev').show();
	$('#header').find('.placement-close').hide();
	$('.placement-wrap').find('.fr').removeClass('open');
	
	$('.layer-pop').hide();
	$('.app_order').hide();
}


function ovShow(){
	var $cback = $(".body-slider-ovclick");
	$cback.unbind("click").bind("click",function(){ ovHide(); $('.layer-pop').hide();}).show();
}
function ovHide(){
	$(".body-slider-ovclick").hide();
	$('.layer-pop').hide()
}



function ovShows(){
	var $cbacks = $(".container-ovclick");
	$cbacks.unbind("click").bind("click",function(){ ovHides(); $('.placement-wrap').find('.fr').removeClass('open');}).show();
}
function ovHides(){
	$(".container-ovclick").hide();
	$('.placement-wrap').find('.fr').removeClass('open');
}



function autoHypenPhone(str){
	str = str.replace(/[^0-9]/g, '');
	var tmp = '';
	if( str.length < 4){
		return str;
	}else if(str.length < 7){
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3);
		return tmp;
	}else if(str.length < 11){
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3, 3);
		tmp += '-';
		tmp += str.substr(6);
		return tmp;
	}else{              
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3, 4);
		tmp += '-';
		tmp += str.substr(7);
		return tmp;
	}
	return str;
}

//체크리스트 
function funcCheckList() {
    $("input[name=check_list]").click(function () {
        
    	/*
    	if ($("input:checkbox[name=check_list]").is(":checked") == true) {
            $('.btn-wrap.bottom_slide').addClass("on");
            $('.bottom_slide').addClass("on");
        } else {
            $('.btn-wrap.bottom_slide').removeClass("on");
            $('.bottom_slide').removeClass("on");
        }
    	*/
    	
    	
    	 $('input[name=check_list]').not(this).prop("checked", false);
    	
    	 if ($(this).is(":checked") == true) {
             $('.btn-wrap.bottom_slide').addClass("on");
             $('.bottom_slide').addClass("on");
         } else {
             $('.btn-wrap.bottom_slide').removeClass("on");
             $('.bottom_slide').removeClass("on");
             
         }
    	 
    	 
    	 var outputcode 	=  $(this).attr('outputcode');
    	 var workerSeq 	=  $(this).attr('workerSeq');
    	 var kindName		=  $(this).attr('kindName');
    	 
    	 $("#oneperson").html("내일 ["+kindName+"] 1명 보내주세요.");
    	 
    	 var height = 180;
    	 
    	 //if(outputcode == "100002" || outputcode == "100005" || outputcode == "100017" || outputcode == "100018"){
		 if(outputcode == "0" || outputcode == "100002" || outputcode == "100005" || outputcode == "100017"){
    		 $('.jobcancel-btn').removeClass("off");	//작업취소
    	 }else{
    		 $('.jobcancel-btn').addClass("off");
    		 height -= 60;
    	 }
    	 
    	 if( workerSeq > 0 && (outputcode == "100014" || outputcode == "100015") ){
    		 $('.thisperson-btn').removeClass("off"); //내일 이분보내주세요. 보이기
    	 }else{
    		 $('.thisperson-btn').addClass("off");	//숨기기
    		 height -= 60;
    	 }
    	 
    	 $('.btn-wrap .bottom_slide').css('height', height+ 'px');
    });
    
}


function funcAddWork(){
	var i = 0;
	var html = "";
	var fileLength;
	
	fileLength = $(".second_form").length;
	
	if(fileLength == 5){
		
		drawToast("최대 5개까지 등록 가능합니다.");
		return;
	}
	
	
	html = '<div class="second_form">';
	html +=	'<div class="sec01">';
	html +=	'		<input type="hidden" name="arr_kind_code" class="kind_code" codePrice="1" />';
	html +=	'		<input type="text"  name= "arr_kind_name" placeholder="선택*" class="ch_work notEmpty" required="required" readonly="readonly" title="직종"  name="arr_kind_name" onclick="funSelectJob(this)"/>';
	html +=	'</div>';
	html +=	'<div class="sec02">';
	html +=	'	<input type="text" name="arr_price" placeholder="" class="price num"  title="단가" code_price="0" code_gender="0" value= "" required="required" >';
	html +=	'</div>';
	html +=	'<div class="sec03">';
	html +=	'	<input type="text"  name="arr_memo" class="arr_memo notEmpty" placeholder="필수*" required="required" title="상세작업설명"/>';
	html +=	'</div>';
	html +=	'<div class="sec04">';
	html +=	'	<button type="button" class="btn_miner"  onclick="funBtnMinus(this)">-</button>';
	html +=	'<input type="text" value="1" class="worker_num notEmpty num" name="arr_worker_num" readOnly>';
	html +=	'	<button type="button" class="btn_plus"  onclick="funBtnPlus(this)">+</button>';
	html +=	'</div>';
	html +=	'<div class="sec05" style="padding-top:0em">';
	html +=	'	<button type="button" class="btn_remove" onclick="funWorkRemove(this)">삭제</button>';
	html +=	'</div>';
	html +=	'</div>';

	$(".secondOI").append(html);		
	
	
	
}


function auto_date_format( e, oThis ){
	
	var num_arr = [ 
		97, 98, 99, 100, 101, 102, 103, 104, 105, 96,
		48, 49, 50, 51, 52, 53, 54, 55, 56, 57
	]
	
	var key_code = ( e.which ) ? e.which : e.keyCode;
	if( num_arr.indexOf( Number( key_code ) ) != -1 ){
	
		var len = oThis.value.length;
		if( len == 4 ) oThis.value += "-";
		if( len == 7 ) oThis.value += "-";
	
	}
	
}

function phoneCehck(phonenum){
 
 var regExp = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
 
 if(!regExp.test(phonenum)){
	  alert('잘못된 휴대폰 번호입니다.');
	  return false;    
 }else{
	 return true;
 }
}


function funWorkRemove(obj){
	$(obj).parents(".second_form").remove();
}



var intervalCounter = 0;
function hideToast() {
    var alert = document.getElementById("toast");
    alert.style.opacity = 0;
    clearInterval(intervalCounter);
}

function drawToast(message) {
    var alert = document.getElementById("toast");
    if (alert == null) {
        var toastHTML = '<div id="toast">' + message + '</div>';
        document.body.insertAdjacentHTML('beforeEnd', toastHTML);
    }
    else {
        $('#toast').text(message);
        alert.style.opacity = .9;
    }
    intervalCounter = setInterval("hideToast()", 3000);
}

function apporder_check() {
    var isCheck = true;
    	
    $(' .notEmpty').each(function() {
		    var type = $(this).prop('type');
		    var val = $(this).val();
            var title = $(this).attr('title');

		if ( isCheck && (type == 'text' || type == 'password' || type == 'textarea' || type == 'tel') ) {
			if ( val == undefined || val == '' || val == null) {
                drawToast(title +"을(를) 입력해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && type == 'file' ) {
            if (val == undefined || val == '' || val == null) {
                drawToast(title +"을(를) 파일을 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && (type == 'checkbox' || type == 'radio') ) {
			var name = $(this).prop('name');
			val = $('input[name="'+ name +'"]:checked').val();

            if (val == undefined || val == '' || val == null ) {
                drawToast(title +"을(를) 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
			}
		} else if ( isCheck && (type == 'select-one' || type == 'select-multiple') ) {
            if (val == undefined || val == '' || val == null ) {
                drawToast(title +"을(를) 선택해 주세요.");
				$(this).focus();
				isCheck = false;

				return false;
            }
        }
    });
    
    return isCheck;
}


function updown() {
    $('.sec04').each(function () {
        var stat = $(this).find('.worker_num').val();
        var num = parseInt(stat, 10);
        var miner = $(this).find('.btn_miner');
        var plus = $(this).find('.btn_plus');
        var numb = $(this).find('.worker_num');

        miner.click(function (e) {
            e.preventDefault();
            num--;
            if (num <= 0) {
                alert('더이상 줄일수 없습니다.');
                num = 1;
            }
            numb.val(num);
        });
        
        plus.click(function (e) {
            e.preventDefault();
            num++;
            numb.val(num);
        });
        
        
    });
}

//인원추가
function funBtnPlus(obj){
	var num = $(obj).parents(".sec04").find(".worker_num").val();
	if(num < 100){
		$(obj).parents(".sec04").find(".worker_num").val(num*1+1);
	}
	
}
//인원 감소
function funBtnMinus(obj){
	var num = $(obj).parents(".sec04").find(".worker_num").val();
	
	
	if(num>1){
		$(obj).parents(".sec04").find(".worker_num").val(num*1-1); 
	}
	
}


//직종선택을 클릭 했을때. 팝업
function funSelectJob(obj){
	 // alert($(obj).val());
	
	 $('.specialty_box').show();
	  selectThis = obj;
	
}


function funcJobclick(obj) {
   
    	
	var price 			= $(obj).attr('price');
	var kind_code 	= $(obj).attr('kind_code');
	var code_gender 	= $(obj).attr('code_gender');
	
	var startTime 	= Number($("#work_arrival").val());
	var endTime  	= Number($("#work_finish").val());
	
	//먼저 값을 삭제 해야 한다.
	$(selectThis).parents(".secondOI").find(".secValue2 .price").val("");
	
	//원본
//		if(startTime < 600){
//			
//			$(selectThis).parents(".second_form").find(".sec02 .price").attr('placeholder','직접입력');
//		}else if(endTime > 1700){
//			$(selectThis).parents(".second_form").find(".sec02 .price").attr('placeholder','직접입력');
//		}else if(endTime <= startTime){
//			$(selectThis).parents(".second_form").find(".sec02 .price").attr('placeholder','직접입력');
//		}else{
//			tempPrice = getPrice(code_gender, startTime,endTime, Number(price) );
//			$(selectThis).parents(".second_form").find(".sec02 .price").val( tempPrice );	
//		}
	//------------------------------------------------------------------------------------------------
	
	//2020-12-23 수정한 부분 [나길진]
	tempPrice = getPrice(code_gender, startTime,endTime, Number(price) );
	if( tempPrice[0] ){
		$(selectThis).parents(".second_form").find(".sec02 .price").val( tempPrice[1] );
	}else{
		$(selectThis).parents(".second_form").find(".sec02 .price").attr('placeholder', tempPrice[1]);
	}
	//------------------------------------------------------------------------------------------------
	
	$(selectThis).val( $(obj).text() );
	$(selectThis).parents(".second_form").find(".sec01 .kind_code").val( kind_code );
	$(selectThis).parents(".second_form").find(".sec02 .price").attr('code_price', price);
	$(selectThis).parents(".second_form").find(".sec02 .price").attr("code_gender", code_gender);
	
	$('.layer-pop').hide();
    
    
}

function timeChange(){
	// 원본
//	$("[class ^= price]").each(function() {
//		
//		var price 			= $(this).attr('code_price');
//		var code_gender 	= $(this).attr('code_gender');
//		
//		var startTime 	= Number($("#work_arrival").val());
//		var endTime  	= Number($("#work_finish").val());
//
//		 if(price != "0"){
//			 
//			//먼저 값을 삭제 해야 한다.
//				$(this).val("");
//				
//				if(startTime < 600){
//					
//					$(this).attr('placeholder','직접입력');
//				}else if(endTime > 1700){
//					$(this).attr('placeholder','직접입력');
//				}else if(endTime <= startTime){
//					$(this).attr('placeholder','직접입력');
//				}else{
//					
//					tempPrice = getPrice(code_gender, startTime,endTime, Number(price) );
//					
//					$(this).val( tempPrice );	
//				}
//				
//				
//		 }
//
//	});
	
	// 2020-12-23 수정한 부분 [나길진]
	$("[class ^= price]").each(function() {
		var price 		= $(this).attr('code_price');
		var code_gender = $(this).attr('code_gender');
		
		var startTime 	= Number($("#work_arrival").val());
		var endTime  	= Number($("#work_finish").val());

		if(price != "0"){
			//먼저 값을 삭제 해야 한다.
			$(this).val("");
			var result = getPrice(code_gender, startTime,endTime, Number(price) );
			if(result[0]){
				$(this).val( result[1] );
			}else{
				$(this).attr('placeholder', result[1]);
			}
		 }
	});
}

var cAddr = "";
var cLatlng = "";

//입력한 주소값에 대한 위치 가져오기
function geocodeAddress(addr) {

	   
	$(".wrap-loading").show();
    
    naver.maps.Service.geocode({address: addr}, function(status, response) {
  	 	
        if (status !== naver.maps.Service.Status.OK) {
            return toastFail("결과가 없거나 기타 네트워크 에러.");
        }
        
        var result;
        	try {
        		result = response.result;
        		cAddr = result.items[0].address;
        		cLatlng = result.items[0].point.y + "," + result.items[0].point.x
              
              
              // 검색 결과 갯수: result.total
              // 첫번째 결과 결과 주소: result.items[0].address
              // 첫번째 검색 결과 좌표: result.items[0].point.y, result.items[0].point.x
             
                
        		$("#busi_addr").val(cAddr);
    			$("#busi_latlng").val(cLatlng);
    			
    			
             
      	}
     	  	catch(error) {
     	  		toastFail("주소가 잘못되었습니다.");
      	}
        	finally {

        		$(".wrap-loading").hide();
      	}
    });
    
    
}



function getPrice(gender, startTime, endTime, price){
	//원본
//	var workTime = endTime - startTime;
//	var tempPrice = 70000;
//	
//	if(gender == 2){	//기술
//		return price;
//		
//	}else{ //0 남자 1 여자
//		if(workTime <= 200){
//			tempPrice = 70000;
//		}else if(workTime <= 300){
//			tempPrice = 90000;
//		}else if(workTime <= 400){
//			tempPrice = 110000;
//		}else if(workTime <= 500){
//			tempPrice = 130000;
//		}else if(workTime <= 600){
//			tempPrice = 150000;
//		}else if(workTime <= 700){
//			tempPrice = 170000;
//		}else if(workTime <= 800){
//			tempPrice = 190000;
//		}else if(workTime <= 900){
//			tempPrice = 210000;
//		}else if(workTime <= 1000){
//			tempPrice = 230000;
//		}else{
//			tempPrice = price;
//		}
//		
//		if(tempPrice > price){
//			tempPrice = price;
//		}
//					
//	}
//	
//	return tempPrice;
	
	//2020-12-23 수정한 부분 [나길진]
	var resultPrice = price;
	
	var tempPrice1 =0;
	var tempPrice2 =0;
	
	if(gender == 2){	//기술
		return [true, price];
	}
	
	if(startTime == 900 && endTime == 1800){
		return [true, price];
	}
	
	if(startTime == 830 && endTime == 1730){
		return [true, price];
	}
	
	if(startTime == 900 && endTime == 1730){
		return [true, price];
	}
	
	
	if(endTime <= startTime){
		return [false, '직접입력'];
	}
	
	var timePrice0 =0;
	var timePrice1 =0;
	var timePrice2 =0;
	var timePrice3 =0;
	
	//시작 시간이 새벽
	if(startTime < 600){
		if(endTime <= 600){
			var workTime = Math.round((endTime - startTime) / 50);
			timePrice0 = workTime * getNightFee(price);
			
		}else if(endTime <= 1700){
			var workTime1 = Math.round((600 - startTime) / 50);
			timePrice0 = workTime1 * getNightFee(price);
			
			var workTime2 = Math.round((endTime - 600) / 50);
			timePrice1 = workTime2 * getTimeFee(price);
			
		}else{
			var workTime1 = Math.round((600 - startTime) / 50);
			timePrice0 = workTime1 * getNightFee(price);
			
			var workTime2 = Math.round((1700 - 600) / 50);
			timePrice1 = workTime2 * getTimeFee(price);
			
			//중간타임  		
	  		if(endTime <= 1800){
	  			var workTime3 = Math.round((endTime - 1700) / 50);
	  			timePrice2 = workTime3 * getTimeFee(price);
	  		//중간 + 야간 타임	
	  		}else{
	  			timePrice2 = 2 * getTimeFee(price);
	  			
	  			var workTime3 = Math.round((endTime - 1800) / 50);
	  			
	  			timePrice3 = workTime3 * getNightFee(price);
	  		}
	  		
		}
		
		var timePrice = timePrice0 + timePrice1 +timePrice2 + timePrice3;
		
		//기본단가책정 ===========================
		if(timePrice < price){
			timePrice += 30000;
			if(timePrice > price){
				resultPrice = price;
			}else{
				resultPrice = timePrice;
			}
		}else{
			resultPrice = timePrice;
		}
		
		
	}else if(startTime >= 600 && endTime <= 1700){//주간만
		var workTime = Math.round((endTime - startTime) / 50);
		var timePrice = workTime * getTimeFee(price);
		//==================================
		if(timePrice < price){
			timePrice += 30000;
			if(timePrice > price){
				resultPrice = price;
			}else{
				resultPrice = timePrice;
			}
		}else{
			resultPrice = price;
		}
	//주간시작 야간종료
	}else if(startTime < 1700 && endTime > 1700){
		
		//주간타임
		var workTime1 = Math.round((1700 - startTime) / 50);
		timePrice1 = workTime1 * getTimeFee(price);
		
		if(timePrice1 >= price){
			timePrice1 = price;	
		}
		//중간타임  		
		if(endTime <= 1800){
			var workTime2 = Math.round((endTime - 1700) / 50);
			timePrice2 = workTime2 * getTimeFee(price);
		//중간 + 야간 타임	
		}else{
			timePrice2 = 2 * getTimeFee(price);
			
			var workTime3 = Math.round((endTime - 1800) / 50);
			/* 
			if((endTime-startTime) >= 900){
				workTime3 -= 2;
			}
			 */
			timePrice3 = workTime3 * getNightFee(price);
		}
		
		var timePrice = timePrice1 +timePrice2 + timePrice3;
		
		//===========================
			
		if(timePrice < price){
			timePrice += 30000;
			if(timePrice > price){
				resultPrice = price;
			}else{
				resultPrice = timePrice;
			}
		}else{
			resultPrice = timePrice;
		}
	//야간시작 야간종료
	}else{
		
		//17시~ 18시가격
		var tempEndTime = startTime;
		if(startTime < 1800){
	  		timePrice2 = 2 * getTimeFee(price);
	  		tempEndTime = 1800;
		}
	  		
	  	var workTime3 = Math.round((endTime - tempEndTime) / 50);
	  		
		if((endTime-startTime) >= 900){
			workTime3 -= 2;
		}
	
		
		timePrice3 = workTime3 * getNightFee(price);
		
		var timePrice = timePrice1 +timePrice2 + timePrice3;
		
		//기본단가책정 ===========================
		if(timePrice < price){
			timePrice += 30000;
			if(timePrice > price){
				resultPrice = price;
			}else{
				resultPrice = timePrice;
			}
		}else{
			resultPrice = timePrice;
		}
	}
	
	return [true, resultPrice];
	
}
//주간단가 30분단위기준
function getTimeFee(price){
	  if(price >= 200000){
		  return 20000;
	  }else if(price >= 160000){
		  return 15000;
	  }else{
		  return 10000;
	  }
}

//야간단가.. 30분단위기준ㅋ
function getNightFee(price){
	  if(price >= 200000){
		  return 20000;
	  }else if(price >= 160000){
		  return 17500;
	  }else{
		  return 15000;
	  }
}

function getTimeStamp() {
    var d = new Date();
    var date = leadingZeros(d.getFullYear(), 4) + '-' +
        leadingZeros(d.getMonth() + 1, 2) + '-' +
        leadingZeros(d.getDate(), 2) + ' ';

    	$('#work_date').val(date);
}
function leadingZeros(n, digits) {
    var zero = '';
    n = n.toString();
    if (n.length < digits) {
        for (i = 0; i < digits - n.length; i++)
            zero += '0';
    }
    return zero + n;
}


//도착시간 마감시간 포맷
function formatTime(timeStr){
	  
	  var RegNotNum  = /[^0-9]/g;

	  // return blank
	  if ( timeStr== "" || timeStr== null || timeStr== "?" ) return "";

	  // delete not number
	  timeStr= timeStr.replace(RegNotNum,'');

	  return timeStr = timeStr.replace(/([0-9]{2})([0-9]{2})/, "$1:$2");
}


