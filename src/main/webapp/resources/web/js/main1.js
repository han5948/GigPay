$(document).ready(function(){
	$(".del").hide();
	$(".mBtn01").click(function(){
		$("html").css({"overflow-y":"hidden"});
		$(".bgPopup").fadeIn(500);
		$(".popup").show();
		$(".workOffer").hide();
		$(".jobOffer").fadeIn(500);
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
		
		if(startTime < 600){
			$(selectThis).parents(".secondOI").find(".secValue2 .price").attr('placeholder','직접입력');
		}else if(endTime > 1700){
			$(selectThis).parents(".secondOI").find(".secValue2 .price").attr('placeholder','직접입력');
		}else if(endTime <= startTime){
			$(selectThis).parents(".secondOI").find(".secValue2 .price").attr('placeholder','직접입력');
		}else{
			
			tempPrice = getPrice(code_gender, startTime,endTime, Number(price) );
			
			$(selectThis).parents(".secondOI").find(".secValue2 .price").val( tempPrice );	
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
		var code_gender 	= $(this).attr('code_gender');
		
		var startTime 	= Number($("#work_arrival").val());
		var endTime  	= Number($("#work_finish").val());

		 if(price != "0"){
			 
			//먼저 값을 삭제 해야 한다.
				$(this).val("");
				
				if(startTime < 600){	
					$(this).attr('placeholder','직접입력');
				}else if(endTime > 1800){
					$(this).attr('placeholder','직접입력');
				}else if(endTime <= startTime){
					$(this).attr('placeholder','직접입력');
				}else{
					tempPrice = getPrice(code_gender, startTime,endTime, Number(price) );
					
					$(this).val( tempPrice );	
				}
				
				
		 }

	});
}

function getPrice(gender, startTime, endTime, price){
	var workTime = endTime - startTime;
	var tempPrice = 70000;
	
	if(gender == 2){	//기술
		return price;
		
	}else{ //0 남자 1 여자
		if(workTime <= 200){
			tempPrice = 70000;
		}else if(workTime <= 300){
			tempPrice = 90000;
		}else if(workTime <= 400){
			tempPrice = 110000;
		}else if(workTime <= 500){
			tempPrice = 130000;
		}else if(workTime <= 600){
			tempPrice = 150000;
		}else if(workTime <= 700){
			tempPrice = 170000;
		}else if(workTime <= 800){
			tempPrice = 200000;
		}else if(workTime <= 900){
			tempPrice = 230000;
		}else if(workTime <= 1000){
			tempPrice = 250000;
		}else{
			tempPrice = price;
		}
		
		if(tempPrice > price){
			tempPrice = price;
		}
					
	}
	
	return tempPrice;
	
}


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