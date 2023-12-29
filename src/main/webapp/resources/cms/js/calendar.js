/**
 * 달력 스크립트
 * @param id
 * @param date
 * @param list
 * 
 * ex)
    var result;
	$(document).ready(function(){			
		result = new Array();		
		<c:forEach items="${xxxList}" var ="list">
			var json=new Object();
			json.xxxx = "${list.aaa}";
			json.ddd = "${list.bbb}";
			result.push(json);
		</c:forEach>
		
		param[0] {String} : 달력 그릴 div tag id
		param[1] {String yyyy-mm-dd '2016-09-01'} : 검색할 오늘 날짜 
		param[2] {ArrayList} : model에서 넘어온 list
		param[3] {String} : submit url				
		kCalendar("calendar" , '${nowDate}' , result , "<c:url value='/admin/ddd/ddd'/>");	
	});	
		데이터 집어 넣을 
		<td>에 list[dateNum-1].aaa</td>
		추가
			
		
	body 영역에	
	<div id="calendar">
	</div>
 * 
 *  
 */
function nemoCalendar(id, date , list , url) {
	var kCalendar = document.getElementById(id);
		
	if( typeof( date ) !== 'undefined' ) {
	date = date.split('-');
	date[1] = date[1] - 1;
	date = new Date(date[0], date[1], date[2]);
	} else {
	var date = new Date();
	}
	var currentYear = date.getFullYear();
	//년도를 구함

	var currentMonth = date.getMonth() + 1;
	//연을 구함. 월은 0부터 시작하므로 +1, 12월은 11을 출역

	var currentDate = date.getDate();
	//오늘 일자.

	date.setDate(1);
	var currentDay = date.getDay();
	//이번달 1일의 요일은 출역. 0은 일요일 6은 토요일

	var dateString = new Array('sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat');
	var lastDate = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	if( (currentYear % 4 === 0 && currentYear % 100 !== 0) || currentYear % 400 === 0 )
	lastDate[1] = 29;
	//각 달의 마지막 일을 계산, 윤년의 경우 년도가 4의 배수이고 100의 배수가 아닐 때 혹은 400의 배수일 때 2월달이 29일 임.

	var currentLastDate = lastDate[currentMonth-1];
	var week = Math.ceil( ( currentDay + currentLastDate ) / 7 );
	//총 몇 주인지 구함.

	if(currentMonth != 1)
	var prevDate = currentYear + '-' + ( currentMonth - 1 ) + '-' + currentDate;
	else
	var prevDate = ( currentYear - 1 ) + '-' + 12 + '-' + currentDate;
	//만약 이번달이 1월이라면 1년 전 12월로 출역.

	if(currentMonth != 12) 
	var nextDate = currentYear + '-' + ( currentMonth + 1 ) + '-' + currentDate;
	else
	var nextDate = ( currentYear + 1 ) + '-' + 1 + '-' + currentDate;
	//만약 이번달이 12월이라면 1년 후 1월로 출역.


	if( currentMonth < 10 )
	var currentMonth = '0' + currentMonth;
	//10월 이하라면 앞에 0을 붙여준다.

	var calendar = '';

	calendar += '<div class="btn_ny">';
	//calendar += '		<a class="btn mod" style="width:30px;" href="javascript:void(0);" onclick="prevNextCalenDar(\'' +  prevDate + '\', \'' + url + '\')"> < </a>';
	calendar += '		<b style="font-size:20px; ">' + currentYear + '년 ' + currentMonth + '월</b>';	
	//calendar += '		<a class="btn mod" style="width:30px;" href="javascript:void(0);" onclick="prevNextCalenDar(\'' + nextDate + '\', \'' + url + '\')"> > </a>';	
	
	calendar += '</div>';
	calendar += '		<p style="font-size:15px; text-align: right; color:red;">방문자 수(명)</p>';
	calendar += '		<p style="font-size:15px; text-align: right; color:blue;">가입자 수(명)</p>';
	calendar += '</br>';
	calendar += '		<table class="tb_base" border="0" cellspacing="0" cellpadding="0">';
	calendar += '			<thead>';
	calendar += '				<tr>';
	calendar += '					<th class="sun" scope="row">일</th>';
	calendar += '					<th class="mon" scope="row">월</th>';
	calendar += '					<th class="tue" scope="row">화</th>';
	calendar += '					<th class="wed" scope="row">수</th>';
	calendar += '					<th class="thu" scope="row">목</th>';
	calendar += '					<th class="fri" scope="row">금</th>';
	calendar += '					<th class="sat" scope="row">토</th>';
	calendar += '				</tr>';
	calendar += '			</thead>';
	calendar += '			<tbody>';
	
	//요일 계산
	var dateNum = 1 - currentDay;
					
	console.log(result.length/week);
	
	// 해당월 주 그린다
	for(var i = 0; i < week; i++) {
		
		calendar += '			<tr>';
		//일주일 7일 계산해서 일 그린다
		for(var j = 0; j < 7; j++, dateNum++) {
			if( dateNum < 1 || dateNum > currentLastDate ) {
				calendar += '				<td class="' + dateString[j] + '"></td>';
				continue;
			}		
			calendar += '<td class"' + dateString[j] + '" style="text-align:right ;" >';
			calendar += 	'<table border="1" cellspacing="0" cellpadding="0">';
			calendar +=         '<tr>';
			calendar +=         '<td>';
			calendar +=         	'<p style="size:12px; font-size: 20px; text-align:center">' + dateNum +'</p>';
			calendar +=         '</td>';
			calendar +=         '</tr>';
			calendar += 		'<tr>';		
			calendar += 			'<td>';
			calendar += 				'<p style="color: red;">100</p>';
			calendar += 			'</td>';
			calendar += 		'</tr>';
			calendar += 		'<tr>';		
			calendar += 			'<td>';
			calendar += 				'<p style="color: blue;">'+list[dateNum-1].regCnt+'</p>';
			calendar += 			'</td>';
			calendar += 		'</tr>';
			calendar += 	'</table>';
			calendar += '</td>';
		}
		calendar += '			</tr>';
					
	}				
	
	calendar += '			</tbody>';
	calendar += '		</table>';

	kCalendar.innerHTML = calendar;
}

function prevNextCalenDar(date , url){
		
	if($("#calendarForm") != null || $("#calendarForm") != undefined ){
		$("#calendarForm").remove();
		$("body").append("<form id='calendarForm' method='post'></form>");						
	}else{
		$("body").append("<form id='calendarForm' method='post'></form>");
	}
	$("#calendarForm").append("<input type='hidden' name='nowDate' value='"+date+"'/>");
	$("#calendarForm").attr("action" , url).submit();	
}