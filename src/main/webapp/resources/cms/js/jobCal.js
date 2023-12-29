
function fn_getPrice(work_arrival, work_finish, basic_price, short_price, short_price_night, add_day, add_night) {

	var startHour = Math.floor(work_arrival/100);
	var startMin = work_arrival % 100;
	if( startMin > 0 && startMin <= 30){
		startMin = 0.5;
	}else if( startMin > 30 && startMin < 60){
		startMin = 1;
	}
	var startTime = startHour + startMin;
	//var startTime 	= Math.floor(work_arrival/100) + (work_arrival % 100 / 60);	//시작시간	시간단위로 변환
	var endHour = Math.floor(work_finish/100);
	var endMin = work_finish % 100;
	if( endMin > 0 && endMin <= 30){
		endMin = 0.5;
	}else if( endMin > 30 && endMin < 60){
		endMin = 1;
	}
	var endTime = endHour + endMin;	
	//var endTime 		= Math.floor(work_finish/100) + (work_finish % 100 / 60);		//종료시간	시간단위로 변환
	
//	var dayEndTime	= (endTime <=18 && endTime >6) ? endTime: 28;
	
	var dayEndTime;
	
	if(endTime <= 18 && endTime > 6) {
		dayEndTime = endTime;
	}else {
		dayEndTime = 18;
	}

	var basicPrice				= basic_price * 1;				//일당
	var dayShortPrice		= short_price * 1;				//주간단시간 단가
	var nightShortPrice		= short_price_night * 1;	//야간단시간 단가
	var dayAddHourPrice	= add_day * 1;						//주간시간당 단가
	var nightAddHourPrice	= add_night * 1;					//야간시간당 단가

	var workHour		= endTime - startTime;	//총시간
	var dayAddHour	= 0;		//주간추가시간
	var nightAddHour= 0;		//야간추가시간

	var price			= 0;		//금액
	var dayPrice		= 0;		//주간금액
	var nightPrice		= 0;		//야간금액

	if(workHour < 2){
		alert("도착시간과 종료시간은 2시간 이상 되어야 합니다.");
		return 0;
	}

	if(startTime >= 4 && startTime <= 13.5){
		dayAddHour	= dayEndTime - startTime;
		nightAddHour	= workHour - dayAddHour;
    
		if(workHour < 4.5){	
			//price = dayShortPrice;	//단시간 일당
			if(workHour < 2.5) {
	              price = dayShortPrice;   //단시간 일당
	        }else {
	              workHour -= 2;
	              price = dayShortPrice + (dayAddHourPrice * workHour);
	        }
		}else if(workHour <= 9){
			if(endTime <= 18) {
				price = basicPrice;        		//일당
				
				$("#dayPrice").text(comma(price));
				$("#nightPrice").text("0");
			}else if(endTime > 18) {
				price = basicPrice + ((endTime - 18) * nightAddHourPrice);
				
				nightPrice = (endTime - 18) * nightAddHourPrice;
				
				$("#dayPrice").text(comma(basicPrice));
				$("#nightPrice").text(comma(nightPrice));
			}
			
			$("#dayComment").html("총시간9시간미만 = 일당");
			$("#nightComment").html("");
		}else{ //workHour 가 9보다 클때
			dayPrice 	= basicPrice + ((dayAddHour - 9) * dayAddHourPrice);	//9시간은 일당으로 
			//dayPrice 	= basicPrice;	
			
//			if(endTime <= 18) {
//				dayPrice = basicPrice + ((dayAddHour - 9) * dayAddHourPrice);	//9시간은 일당으로
//			}else {
//				dayPrice = basicPrice; 
//			}
			
			nightPrice	= nightAddHour * nightAddHourPrice;
			price 			= dayPrice + nightPrice;
	
			$("#dayPrice").text(comma(dayPrice));
			$("#nightPrice").text(comma(nightPrice));
			
			$("#dayComment").html("주간금액 = 일당 + ((주가추가시간 - 일당시간) * 주간추가시간)) <br>" + basicPrice + " + ((" + dayAddHour + " - 9) * " + dayAddHourPrice + ") = " + dayPrice);
			$("#nightComment").html("야간금액 = 야간추가시간 * 야간추가단가 <br> " + nightAddHour + " * " + nightAddHourPrice + " = " + nightPrice);
		}
	}else{
		var tempStartTime = startTime;	//애매한 시간일때 2시간 + 된 시작시간
		if( (startTime >= 2.5 && startTime <=3.5) || (startTime >=16.5 && startTime <= 17.5)){
			tempStartTime = startTime + 2;
		}
 
		if(tempStartTime < 6 || startTime >= 18){	//야간단시간 20
			if(startTime <= 4 ){
//				if(endTime < 6)			dayAddHour = 0;
//				else if(endTime <= 18)	dayAddHour = endTime - 4;
//				else if(endTime <= 28)	dayAddHour = 14;
//				else					dayAddHour = 14 + (endTime - 28);
				if(endTime < 4) {
					dayAddHour = 0;
				}else if(endTime <= 18)	{
					dayAddHour = endTime - tempStartTime;
					if ( tempStartTime <= 2){
						dayAddHour -= 4 - tempStartTime;
					}
				}else if(endTime <= 28){
					dayAddHour = 14;
				}else{
					dayAddHour = 14 + (endTime - 28);
				}
			}else if( startTime >= 18){
				dayAddHour = 0;
				if( endTime > 28 ){
					dayAddHour = endTime - 28;
				}
			}else{
				dayAddHour = dayEndTime - tempStartTime;
			}
			nightAddHour = workHour - dayAddHour -2;
		
			dayPrice 	= dayAddHour  * dayAddHourPrice;
			nightPrice 	= nightShortPrice + (nightAddHour  * nightAddHourPrice);	
		}else{ // 주간단시간
			if(tempStartTime <= 16){
//				if(endTime <= 18)	nightAddHour = 0;
//				else						nightAddHour = endTime - 28;
//				
//				if(nightAddHour < 0) {
//					nightAddHour = 0;
//				}
				
				if(endTime <= 18) {
					nightAddHour = 0;
				}else if(endTime <= 28) {
					nightAddHour = endTime - 18;
				}else {
					nightAddHour = 10;
				}
			}else{
				if( endTime <= 28 ){
					nightAddHour = endTime - tempStartTime;
				}else{
//					nightAddHour = 10;
					if(tempStartTime == '18') {
						nightAddHour = 10;
					}else {
						nightAddHour = 28 - tempStartTime;
					} 
				}
			}
			dayAddHour = workHour - nightAddHour - 2;
		
			dayPrice		= dayShortPrice + (dayAddHour  * dayAddHourPrice);	
			nightPrice	=  nightAddHour  * nightAddHourPrice;
		}
		
		price = dayPrice + nightPrice;
	}

	return price;
}

//계산기에서 사용 http://localhost:8080/admin/jobPriceCal
function fn_getPrice2(work_arrival, work_finish, basic_price, short_price, short_price_night, add_day, add_night) {
	var startTime 	= Math.floor(work_arrival/100) + (work_arrival % 100 / 60);	//시작시간	시간단위로 변환
	var endTime 		= Math.floor(work_finish/100) + (work_finish % 100 / 60);		//종료시간	시간단위로 변환
	//var dayEndTime	= (endTime <=18 && endTime >6) ? endTime: (endTime <= 4) ? 18 : 28;
	
	var dayEndTime;
	
	if(endTime <= 18 && endTime > 6) {
		dayEndTime = endTime;
	}else {
		dayEndTime = 18;
	}
	
	var basicPrice				= basic_price * 1;				//일당
	var dayShortPrice		= short_price * 1;				//주간단시간 단가
	var nightShortPrice		= short_price_night * 1;	//야간단시간 단가
	var dayAddHourPrice	= add_day * 1;						//주간시간당 단가
	var nightAddHourPrice	= add_night * 1;					//야간시간당 단가

	var workHour		= endTime - startTime;	//총시간
	$("#workHour").text(workHour);
	
	var dayAddHour	= 0;		//주간추가시간
	var nightAddHour= 0;		//야간추가시간

	var price			= 0;		//금액
	var dayPrice		= 0;		//주간금액
	var nightPrice		= 0;		//야간금액

	
	$("#workHour").text(workHour);

	if(startTime >=4 && startTime <= 13.5){
		$("#dayPriceComment").html("일당기준 주간시작");
		
		dayAddHour = dayEndTime - startTime;
		nightAddHour = workHour - dayAddHour;
    
		$("#dayAddHour").text(dayAddHour);
		$("#nightAddHour").text(nightAddHour);
		
		if(workHour < 4.5){	
			//price = dayShortPrice;	//단시간 일당
			if(workHour < 2.5) {
	              price = dayShortPrice;   //단시간 일당
	        }else {
	              workHour -= 2;
	              price = dayShortPrice + (dayAddHourPrice * workHour);
	        }
			
			$("#dayPrice").text(comma(price));
			$("#nightPrice").text("0");
			
			$("#dayComment").html("총시간 4.5시간 미만 = 주간단시간단가적용");
			$("#nightComment").html("");
		}else if(workHour <= 9){
			if(endTime <= 18) {
				price = basicPrice;        		//일당
				
				$("#dayPrice").text(comma(price));
				$("#nightPrice").text("0");
			}else if(endTime > 18) {
				price = basicPrice + ((endTime - 18) * nightAddHourPrice);
				
				nightPrice = (endTime - 18) * nightAddHourPrice;
				
				$("#dayPrice").text(comma(basicPrice));
				$("#nightPrice").text(comma(nightPrice));
			}
			
			$("#dayComment").html("총시간9시간미만 = 일당");
			$("#nightComment").html("");
		}else{ //workHour 가 9보다 클때
			dayPrice 	= basicPrice + ((dayAddHour - 9) * dayAddHourPrice);	//9시간은 일당으로
//			if(endTime <= 18) {
//				dayPrice = basicPrice + ((dayAddHour - 9) * dayAddHourPrice);	//9시간은 일당으로
//			}else{
//				dayPrice = basicPrice;
//			}
			
			//dayPrice 	= basicPrice;	//9시간은 일당으로 
			nightPrice	= nightAddHour * nightAddHourPrice;
			price 			= dayPrice + nightPrice;
	
			$("#dayPrice").text(comma(dayPrice));
			$("#nightPrice").text(comma(nightPrice));
			
			$("#dayComment").html("주간금액 = 일당 + ((주가추가시간 - 일당시간) * 주간추가시간)) <br>" + basicPrice + " + ((" + dayAddHour + " - 9) * " + dayAddHourPrice + ") = " + dayPrice);
			$("#nightComment").html("야간금액 = 야간추가시간 * 야간추가단가 <br> " + nightAddHour + " * " + nightAddHourPrice + " = " + nightPrice);
		}
	}else{
		var tempStartTime = startTime;	//애매한 시간일때 2시간 + 된 시작시간
		
		if( (startTime >= 2.5 && startTime <=3.5) || (startTime >=16.5 && startTime <= 17.5)){
			tempStartTime = startTime + 2;
		}
 
		if(tempStartTime < 6 || startTime >= 18){	//야간단시간 20
			if(startTime < 4 ){
				if(endTime < 4) {
					dayAddHour = 0;
				}else if(endTime <= 18)	{
					dayAddHour = endTime - tempStartTime;
					if ( tempStartTime <= 2){
						dayAddHour -= 4 - tempStartTime;
					}
				}else if(endTime <= 28){
					dayAddHour = 14;
				}else{
					dayAddHour = 14 + (endTime - 28);
				}
			}else if( startTime >= 18){
				dayAddHour = 0;
				if( endTime > 28 ){
					dayAddHour = endTime - 28;
				}
			}else{
				dayAddHour = dayEndTime - tempStartTime;
			}
			nightAddHour = workHour - dayAddHour -2;
			
			$("#dayPriceComment").html("주간추가시간 적용");
			$("#nightPriceComment").html("야간단시간 적용");
			$("#dayAddHour").text(dayAddHour);
			$("#nightAddHour").text(nightAddHour);
			
			dayPrice 	= dayAddHour  * dayAddHourPrice;
			nightPrice 	= nightShortPrice + (nightAddHour  * nightAddHourPrice);	
		
			$("#dayComment").html("주간금액 = 주간추가시간 * 주간추가단가 <br> " +  dayAddHour +" * " + dayAddHourPrice + " = " + dayPrice);
			$("#nightComment").html("야간금액 = 야간단시간단가 + (야간추가시간 * 야간추가단가) <br> " +  nightShortPrice + " + (" + nightAddHour +" * "+ nightAddHourPrice +")" + " = " + nightPrice);
		}else{ // 주간단시간
			if(tempStartTime <= 16){
				if(endTime <= 18) {
					nightAddHour = 0;
				}else if(endTime <= 28) {
					nightAddHour = endTime - 18;
				}else {
					nightAddHour = 10;
				}
			}else{
				if( endTime <= 28 ){
					nightAddHour = endTime - tempStartTime;
				}else{
					if(tempStartTime == '18') {
						nightAddHour = 10;
					}else {
						nightAddHour = 28 - tempStartTime;
					} 
				}
			}
			
			dayAddHour = workHour - nightAddHour - 2;
			dayPrice		= dayShortPrice + (dayAddHour  * dayAddHourPrice);	
			nightPrice	=  nightAddHour  * nightAddHourPrice;
		    
			$("#dayAddHour").text(dayAddHour);
			$("#nightAddHour").text(nightAddHour);
			
			$("#dayPriceComment").html("주간단시간 적용");
			$("#nightPriceComment").html("야간추가시간 적용");
			
			$("#dayComment").html("주간금액 	= 주간 단시간단가 + (주간추가시간 * 주간단가) <br> " +  dayShortPrice + " + ("+ dayAddHour + " * " + dayAddHourPrice +")" + " = " + dayPrice);
			$("#nightComment").html("야간금액 = 야간추가시간 * 야간추가 단가 <br> " +   nightAddHour +" * "+ nightAddHourPrice + " = " + nightPrice);
		}
		
		$("#dayPrice").text(comma(dayPrice));
		$("#nightPrice").text(comma(nightPrice));
		
		price = dayPrice + nightPrice;
	}
	
	return price;
}