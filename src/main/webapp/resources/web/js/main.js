$(document).ready(function(){
	$(".del").hide();
	$(".mBtn01").click(function(){
		/*
		//$("html").css({"overflow-y":"hidden"});
		$(".bgPopup").fadeIn(500);
		$(".popup").show();
		$(".workOffer").hide();
		$(".jobOffer").fadeIn(500);
		
		*/
	});
	$(".mBtn02").click(function(){
		$("html").css({"overflow-y":"hidden"});
		$(".bgPopup").fadeIn(500);
		$(".popup").show();
		$(".workOffer").fadeIn(500);
	});
	
	$(".add").click(function(){
		$(".firstOI_04").show();
		$(".add").hide();
		$(".del").show();
	});
	$(".del").click(function(){
		$(".firstOI_04").hide();
		$(".del").hide();
		$(".add").show();
	});
    
	$("#workadd").click(function(){
		var i = 0;
		var html = "";
		var fileLength;
		
		fileLength = $(".secondOI").length;
		
		if(fileLength == 5){
			toastW("최대 5개까지 등록 가능합니다.");
			return;
		}
		
		
		html += '<div class="secondOI clear">';
		html += '	<div class="secValue1">';				
		html += '		<input type="hidden" name="arr_kind_code" class="kind_code"> <input type="text" name="arr_kind_name" placeholder="선택*" class="opp notEmpty"  title="직종"  required="required" readonly onfocus="this.blur()" onclick="funSelectKink(this)">';
		html += '	</div>';
		html += '	<div class="secValue2">';
		html += '		<input type="text" name="arr_price" class="price" required="required"  code_price="0"  code_gender="0">';
		html += '	</div>';
		html += '	<div class="secValue3">';
		html += '		<input type="text" name="arr_memo" class="price notEmpty" title="상세작업설명" placeholder="필수*" required="required">';
		html += '	</div>';
		html += '	<div class="secValue4">';
		html += '		<span class="personMiner pm" onclick="funWorkerMinu(this)"><img src="/resources/web/images/personMinu.png" alt="" ></span>';
		html += '		<input type="text" value="1" class="worker_num" name="arr_count">';
		html += '		<span class="personPlus" onclick="funWorkerPlus(this)"><img src="/resources/web/images/personPlus.png" alt="" ></span>';
		html += '	</div>';
		html += '<div class="secValue5" style="padding-top:0.2em;;">';
		html += '<span class="del plusOiDel" onClick="funWorkRemove(this)">삭제</span>';
		html += '</div>';
		html += '</div>';
		
		$(".secondOI").last().after(html);	
		
		$(".secValue1 .opp").on("click",function(){
			$(".secondOIpop").fadeIn(500);
	    });
		
       
	});
	
	$(".offerInfo02 .secondOIplus .secValue5 span").click(function(){
		$(".offerInfo02 div:last-child").hide();
	});
	
	$(".referCheck label").click(function(){
		if($(".referCheck input").is(":checked")){
			$(".refer").show();
		}else{
			$(".refer").hide();
		}
	});
	

	
	$(".popMenu").click(function(){
		$(".w_OIpop").fadeIn(500);
	});
	$(".sOItit span").click(function(){
		$(".secondOIpop").fadeOut(500);
		$(".w_OIpop").fadeOut(500);
	});
	$(".conView").click(function(){
		var jobOfferHeight = $(".jobOffer").height();
		$(".bgOffer").show();
		$(".offerInfo04pop").show();
	});
	
	$(".w_conView").click(function(){
		var workOfferHeight = $(".workOffer").height();
		$(".w_bgOffer").show();
		$(".w_bgOffer").css({"height":workOfferHeight});
		$(".w_offerInfo04pop").show(); 
	});
	
	$(".offerInfo04pop span").click(function(){
		$(".bgOffer").fadeOut(500);
		$(".offerInfo04pop").hide();
	});
	
	$(".w_offerInfo04pop span").click(function(){
		$(".w_bgOffer").fadeOut(500);
		$(".w_offerInfo04pop").hide();
	});
	
	
	//구인신청 팝업창에서 직종을 선택 했을 때.....
	$(".sOIemp .empButton p").click(function(){
		
		var price 			= $(this).attr('price');
		var kind_code 	= $(this).attr('kind_code');
		var code_gender	= $(this).attr('code_gender');
		
		var startTime 	= Number($("#work_arrival").val());
		var endTime  	= Number($("#work_finish").val());
		
		$(".secondOIpop").hide();
		
		//먼저 값을 삭제 해야 한다.
		$(selectThis).parents(".secondOI").find(".secValue2 .price").val("");
		
		var result = getPrice(code_gender, startTime,endTime, Number(price) );
		if(result[0]){
			$(selectThis).parents(".secondOI").find(".secValue2 .price").val( result[1] );
		}else{
			$(selectThis).parents(".secondOI").find(".secValue2 .price").attr('placeholder', result[1]);
		}
				
		$(selectThis).val( $(this).text() );
		$(selectThis).parents(".secondOI").find(".secValue1 .kind_code").val( kind_code );
		$(selectThis).parents(".secondOI").find(".secValue2 .price").attr('code_price', price);
		$(selectThis).parents(".secondOI").find(".secValue2 .price").attr("code_gender", code_gender);
		
		
	});
	
	
	
	$(".wOIemp .empButton p").click(function(){
		
		if($(this).hasClass("empTit") === true) {
			$(this).removeClass('empTit');
		}else{
			$(this).addClass('empTit');
		}


		var workerJobName = "";
		var workerJobCode = "";

		//선택된 class 를 가져온다.
		$(".wOIemp .empButton .empTit").each(function(index){ 
			  var text = $(this).text();
			  var code = $(this).attr('kind_code');
			  
			  if(index === 0){
				  workerJobName =  text;
				  workerJobCode = code;
			  }else{
				  workerJobName +=  "," + text;
				  workerJobCode  += "," + code;
			  }
			  			   
		});
		
		$("#worker_job_name").val( workerJobName );
		$("#worker_job_code").val( workerJobCode  );
		
	});
	
	
	
	$(".checkOffer .co_01 .check").click(function(){
		$(".c_offerOpa").css({"visibility":"visible"});
	});
	
	$(".checkBox .conView").click(function(){
		$(".c_bgPop").fadeIn(500);
		$(".chPop01").fadeIn(500);
	});
	
	$(".offerInfo_pop span").click(function(){
		$(".c_bgPop").fadeOut(500);
		$(".chPop01").fadeOut(500);
	});
	
	
});

function timeChange(){

	$("[class ^= price]").each(function() {
		var price 			= $(this).attr('code_price');
		var code_gender = $(this).attr('code_gender');
		
		var startTime 	= Number($("#work_arrival").val());
		var endTime  		= Number($("#work_finish").val());

		if(price != "0"){
				//먼저 값을 삭제 해야 한다.
				$(this).val("");
/*				
				if(startTime < 600){	
					$(this).attr('placeholder','접수 후 책정');
				}else if(endTime > 1800){
					$(this).attr('placeholder','접수 후 책정');
				}else if(endTime <= startTime){
					$(this).attr('placeholder','접수 후 책정');
				}else{
					tempPrice = getPrice(code_gender, startTime,endTime, Number(price) );
					$(this).val( tempPrice );	
				}
				*/
				var result = getPrice(code_gender, startTime,endTime, Number(price) );
				if(result[0]){
					$(this).val( result[1] );
				}else{
					$(this).attr('placeholder', result[1]);
				}
		 }
	});
}



function getPrice(gender, startTime, endTime, price){
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
	
	//시작 시간이 새별
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



/*
function getPrice(gender, startTime, endTime, price){
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
	
	if(startTime < 600){	
		return [false, '접수 후 책정'];
	}else if(endTime <= startTime){
		return [false, '접수 후 책정'];
	}
	
	
	if(startTime >= 600 && endTime <= 1700){
		var workTime = Math.round((endTime - startTime) / 50);
		var timePrice = workTime * 10000;
		
		//==================================
		if(timePrice < price){
			timePrice += 30000;
			if(timePrice > price){
				resultPrice = price;
			}
		}else{
			resultPrice = timePrice;
		}
	}else if(startTime < 1700 && endTime > 1700){

		
		var timePrice1 =0;
		var timePrice2 =0;
		var timePrice3 =0;
		
		
		//주간 근무
		var workTime1 = Math.round((1700 - startTime) / 50);
		timePrice1 = workTime1 * 10000;
		
		if(endTime <= 1800){
			var workTime2 = Math.round((1800 - endTime) / 50);
			timePrice2 = workTime2 * 10000;
			
		}else{
			timePrice2 = 2 * 10000;
			
			var workTime3 = Math.round((endTime - 1800) / 50);
			
			if((endTime-startTime) >= 900){
				workTime3 -= 2;
			}
			
			timePrice3 = workTime3 * 15000;
		}
		
		var timePrice = timePrice1 +timePrice2 + timePrice3;
		
		//===========================
		if(timePrice < price){
			timePrice += 30000;
			if(timePrice > price){
				resultPrice = price;
			}
		}else{
			resultPrice = timePrice;
		}
		
	}else{
		timePrice2 = 2 * 10000;
		
		var workTime3 = Math.round((endTime - 1800) / 50);
		
		if((endTime-startTime) >= 900){
			workTime3 -= 2;
		}
		
		timePrice3 = workTime3 * 15000;
		
		var timePrice = timePrice1 +timePrice2 + timePrice3;
		
		//===========================
		if(timePrice < price){
			timePrice += 30000;
			if(timePrice > price){
				resultPrice = price;
			}
		}else{
			resultPrice = timePrice;
		}
	}
	
	return [true, resultPrice];
	
}
*/

/*
function getPrice(gender, startTime, endTime, price){
	
	var resultPrice = price;
	
	//주간일 경우
	if(startTime >=600 && endTime <=1700){
		var workTime = (endTime - startTime) / 30;
		var timePrice = 10000; //30분단가
		var tempPrice = 30000 + (workTime * timePrice);
		
		if(tempPrice < price){
			resultPrice = tempPrice;
		}
		
		return [true, reultPrice];
	}
	
	//주간 시간 야간종료
	if(startTime < 1700 && endTime > 1700){
		//주간 단가 계산
		var resultPrice1 = price;
		
		var workTime1 = (1800 - startTime) / 30;
		if(workTime1 >= 18){ //9시간 이상이면
			workTime1 = workTime1 - 2; //휴게시간 1시간을 뺀다.
		} 
		var tempPrice1 = workTime1 * 10000
		if(tempPrice1 < price){
			resultPrice1 = tempPrice1;
		}
	
		//야간 단가계산
		var resultPrice2;
		var tmepPrice2;
		if(endTime >= 1800){	//6시 이상일때
			var workTime2 = (endTime - 1800) / 30;
			
			//9시간 이상이면 1시간을 뺀다.
			if( (endTime - startTime) >= 900){
				workTime2 = workTime2 - 2;
			}
			
			resultPrice2 = 20000 + (workTime2 * 15000);	//18시 단가 20000원 + 시간단가
		}else{//5시30분일때는 만원만책정
			resultPrice2 = 10000;
		}
		
		resultPrice = resultPrice1 + resultPrice2;
		
		if(resultPrice < price){
			if((resultPrice + 30000) > price){
				resultPrice = price;
			}
		}
		
		return [true, resultPrice];
	}
	
	
	
	
}
*/
var selectThis;

//직종선택을 클릭 했을때. 팝업
function funSelectKink(obj){
	 // alert($(obj).val());
	
	selectThis = obj;
	$(".secondOIpop").fadeIn(500);
	
}

//인원수 추가
function funWorkerPlus(obj){
	
	var num = $(obj).parents(".secValue4").find(".worker_num").val();
	$(obj).parents(".secValue4").find(".worker_num").val(num*1+1);
	
	
}

//인원수 감소
function funWorkerMinu(obj){
	var num = $(obj).parents(".secValue4").find(".worker_num").val();
	
	if(num>1){
		$(obj).parents(".secValue4").find(".worker_num").val(num*1-1); 
	}
	
}

function funWorkRemove(obj){
	$(obj).parents(".secondOI").remove();
}

$(function() {
            //input을 datepicker로 선언
            $("#order_date").datepicker({
                dateFormat: 'yy-mm-dd' //Input Display Format 변경
                ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
                ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시           
                ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
                ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
                ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
                ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
                ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
                ,minDate: "D" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
             });                    
            
            //초기값을 오늘 날짜로 설정
            //$('#order_date').datepicker('setDate', 'today+1D'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
            
            $.datepicker.setDefaults({
                minDate: "today" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
            });
        });