var jobList = new Array();
var jobSeqArray = new Array();	// 선택한 직종 array
var addPrice = 0;
var selectThis;
var memo3 ="";

$(function() {
	var _data = {
			use_yn	:"1"
			, del_yn	:"0"
			, orderBy 	: "job_kind, job_order"
	}

	var _url = "/admin/getJobList";

	commonAjax(_url, _data, true, function(data) {
		//successListener
		if (data.header.code == "0000") {
			jobList = data.body.jobList;
			
		} else {
			if (jQuery.type(data.message) != 'undefined') {
				if (data.message != "") {
					toastFail(data.message);
				} else {
					toastFail("오류가 발생했습니다.1");
				}
			} else {
				toastFail("오류가 발생했습니다.2");
			}
		}
	}, function(data) {
		//errorListener
		toastFail("오류가 발생했습니다.3");
	}, function() {
		//beforeSendListener
	}, function() {
		//completeListener
	});
	
	
    //동적이벤트 달기 job-detail
    $(document).on("click", ".select-box", function() {
    	$(".select-box").removeClass('on');
        $(this).addClass('on');
        
        var jobSeq = $(this).attr("job_seq");
        $("#basic_price").text(comma(getBasicPrice(jobSeq)));
        
    });
    
    //job-add
    $(document).on("click", ".check-box", function() {
    	
        if($(this).hasClass('on')){
        	$(this).removeClass('on');
            
            if($(this).hasClass('use')){
            	
                if($(".check-box.use.on").index() <= 0){
                    $(".check-box.none").addClass('on');
                }
            }
        }else{
            $(this).addClass('on');
            if($(this).hasClass('none')){
                $(".check-box.use").removeClass('on');
            }else{
                $(".check-box.none").removeClass('on');
            }
        }

        
        var aPrice = 0;
        $( '.check-box' ).each( function() {
        	if($(this).hasClass('on')){
        		aPrice += (getAddPrice($(this).attr("job_seq")) *1);
        	}
            
          } );
        
        addPrice = aPrice;
        $("#add_price").text(comma(aPrice));
        
    });
    
});

function fn_selectJob(obj){
	selectThis = obj;
		
	fn_oneJobList();
	jobCalInit(0);
	
	$(".secondOIpop").fadeIn(500);
}

function fn_oneJobList(){
	
	var oneDepthJobList = jobList.filter(function(item){    
		  return item.job_rank === "1";
	});  
	
	
	var _html = '<div style="float: right;padding: 0px 0.8em;"><img src="/resources/web/images/subClose.png" alt=""  class="close" style="cursor:pointer" onclick="closeJobOffer()" ></div>';
			_html +='<div class="sOIpopCon">';
			_html += '<div class="sOItit"><p><strong>전문분야</strong><br>단가는 2019년 기준이며, 계단유무/장비사용에 따라 단가가 조정될 수 있습니다.</p></div>';
			_html += '<div class="sOIemp">';
			
	var jobKind = 0;
	
	var kindHtml1 = '<div class="man clear"><p class="empTit">잡부조공</p><div class="empButton">';
	var kindHtml2 = '<div class="tech clear"><p class="empTit">기술인력</p><div class="empButton">';
	
	oneDepthJobList.forEach(function(job,index, array){
		var jobHtml = '<p onClick ="jobClick(this)" + job_seq="' +job.job_seq + '"  job_name="' +job.job_name+'" detail_use_yn="' +job.detail_use_yn+'">'+ job.job_name +'</p>';
		if(job.job_kind == 1){
			kindHtml1	+= jobHtml;
		}else{
			kindHtml2	+= jobHtml;
		}
		
	});
	
	_html += kindHtml1 + "</div></div>";
	_html += kindHtml2 + "</div></div>";
	_html += "</div></div>";

	$(".secondOIpop").html(_html);
}

function jobClick(obj){
	
	var jobSeq		= $(obj).attr("job_seq");
	var jobName	= $(obj).attr("job_name");
	var detailUseYn	= $(obj).attr("detail_use_yn");

	jobSeqArray[0] = {jobSeq: jobSeq, jobName: jobName, detailUseYn: detailUseYn};
	
	fn_twoJobList();
}

function jobNext(step){
	
	if(step == 3){
		var jobSeq =0;
		var jobName = "";
		$( '.select-box' ).each( function() {
	    	if($(this).hasClass('on')){
	    		jobSeq = $(this).attr("job_seq");
	    		jobName = $(this).attr("job_name");
	    	}
	    });
		
		if(jobSeq ==0){
			alert("세부직종을 선택 하세요.");
			return;
		}
				
		jobSeqArray[1] =  {jobSeq: jobSeq, jobName: jobName};
			
		fn_threeJobList();
	}else{ //step ==4 add에서 완료를 할때..
		var optionSeqArr = new Array();
		var optionNameArr = new Array();
		
		$( '.check-box' ).each( function() {
        	if($(this).hasClass('on')){
        		var jobSeq = $(this).attr("job_seq");
				var jobName = $(this).attr("job_name");
								
        		optionSeqArr.push(jobSeq);
        		optionNameArr.push(jobName);
        		
        		jobSeqArray[2] = {jobSeq: optionSeqArr.join(), jobName: optionNameArr.join("|")};
        		
        		memo3 += ", " + jobName;
        	}
            
          } );
		
		fn_complite(1);
	}
}

function jobPrev(step){
	
	if(step == 1){
		fn_oneJobList();
	}else{
		fn_twoJobList();

	}
	
}


function fn_twoJobList(){
	var jobSeq		= jobSeqArray[0].jobSeq;
	
	var twoJobSeq = 0;
	var basicPrice = 0;
	
	if(jobSeqArray[1].jobSeq != null){
		
		twoJobSeq = jobSeqArray[1].jobSeq;
		var twoJob = jobList.find(function(item){    
			  return (item.job_seq === twoJobSeq);
		});  
		basicPrice = twoJob.basic_price;
	}
	
	
	jobCalInit(1);
	var twoDepthJobList = jobList.filter(function(item){    
		  return (item.job_code === jobSeq && item.job_rank ==2);
	});  
	
	var threeDepthJobList = jobList.filter(function(item){    
		  return (item.job_code === jobSeq && item.job_rank ==3);
	});
	
	if(jobSeqArray[0].detailUseYn == "1"){
		var _html = '<div class="ilgaja-title font-bold">구체적인 일</div>';
		_html += '<div class="ilgaja-caption">기본 단가 : <span id="basic_price">'+ basicPrice +'</span> 원</div>';
		_html += '<div class="select-area mgb50">';
		
		twoDepthJobList.forEach(function(job,index, array){
			_html += '<div class="select-box '+ ((job.job_seq == twoJobSeq) ? 'on':'' ) +'" job_seq="'+ job.job_seq+'" job_name="'+ job.job_name+'">';
			_html += '		<div class="select-circle-box">';
			_html += '			<span class="select-circle">';
			_html += '				<span class="select-circle-inner"></span>';
			_html += '			</span>';
			_html += '		</div>';
			_html += '		<div class="select-box-text">'+ job.job_name +'</div>';
			_html += '</div>';
		});
		
		_html +='<div class="ilgaja-btn-box"><span class="ilgaja-btn prev" onclick="jobPrev(1)">이전</span><span class="ilgaja-btn" onclick="jobNext(3)">다음</span></div>';
		
		$(".secondOIpop").html(_html);
	}else if(threeDepthJobList.length > 0) {
		var _html = '<div class="ilgaja-title font-bold">추가 작업 선택</div>';
		_html += '<div class="ilgaja-caption">기본 단가 : <span id="basic_price">'+ comma(getBasicPrice(jobSeq)) +'</span> 원<br><br>추가 단가 : <span id="add_price">0</span> 원</div>';
		_html += '<div class="select-area mgb50">';
		_html += '	<div class="check-box on none" job_seq="" job_name="">';
		_html += '		<div class="select-circle-box">';
		_html += '			<span class="select-circle check">';
		_html += '				<span class="select-circle-inner check"></span>';
		_html += '			</span>';
		_html += '		</div>';
		_html += '		<div class="select-box-text">없음</div>';
		_html += ' </div>';
		
		threeDepthJobList.forEach(function(job,index, array){
			_html += ' <div class="check-box use" job_seq="' + job.job_seq +'" job_name="'+ job.job_name+'">';
			_html += ' <div class="select-circle-box">';
			_html += '	<span class="select-circle check">';
			_html += '		<span class="select-circle-inner check"></span>';
			_html += '	</span>';
			_html += ' </div>';
			_html += ' <div class="select-box-text">'+ job.job_name +' ( + '+ comma(job.basic_price) +'원)</div>';
			_html += ' </div>';
		});
		
		_html += '<div class="ilgaja-btn-box"><span class="ilgaja-btn prev" onclick="jobPrev(1)">이전</span><span class="ilgaja-btn page-move" onclick="jobNext(4)">완료</span></div>';
		
		$(".secondOIpop").html(_html);
	}else {
		fn_complite(0);
		
	}
		
	

}

function fn_threeJobList(){
	
	var jobSeq		= jobSeqArray[1].jobSeq;
	
	var threeDepthJobList = jobList.filter(function(item){    
		  return (item.job_code === jobSeq && item.job_rank ==3);
	});  
	
	jobCalInit(2);
	
	if(threeDepthJobList.length > 0){
		var _html = '<div class="ilgaja-title font-bold">추가 작업 선택</div>';
		_html += '<div class="ilgaja-caption">기본 단가 : <span id="basic_price">'+ comma(getBasicPrice(jobSeq)) +'</span> 원<br><br>추가 단가 : <span id="add_price">0</span> 원</div>';
		_html += '<div class="select-area mgb50">';
		_html += '	<div class="check-box on none" job_seq="" job_name="">';
		_html += '		<div class="select-circle-box">';
		_html += '			<span class="select-circle check">';
		_html += '				<span class="select-circle-inner check"></span>';
		_html += '			</span>';
		_html += '		</div>';
		_html += '		<div class="select-box-text">없음</div>';
		_html += ' </div>';
		
		threeDepthJobList.forEach(function(job,index, array){
			_html += ' <div class="check-box use" job_seq="' + job.job_seq +'" job_name="'+ job.job_name+'">';
			_html += ' <div class="select-circle-box">';
			_html += '	<span class="select-circle check">';
			_html += '		<span class="select-circle-inner check"></span>';
			_html += '	</span>';
			_html += ' </div>';
			_html += ' <div class="select-box-text">'+ job.job_name +' ( + '+ comma(job.basic_price) +'원)</div>';
			_html += ' </div>';
		});
		
		_html += '<div class="ilgaja-btn-box"><span class="ilgaja-btn prev" onclick="jobPrev(2)">이전</span><span class="ilgaja-btn page-move" onclick="jobNext(4)">완료</span></div>';
		
		$(".secondOIpop").html(_html);
	}else{
		
		fn_complite(1);
	}
}

function fn_complite(index){
	
	var jobSeq = jobSeqArray[index].jobSeq; 
	
	if(!jobSeq) {
		jobSeq = jobSeqArray[0].jobSeq;
	}

	var lastJob = jobList.find(function(item){    
		  return (item.job_seq === jobSeq);
	});
	
	var basicPrice = lastJob.basic_price;
	var shortPrice = lastJob.short_price;
	var shortPriceNight = lastJob.short_price_night;
	var addDay = lastJob.add_day_time;
	var addNight = lastJob.add_night_time;

	if(index ==1){
		var memo2 ="";
		memo2 = lastJob.job_name;
	}

	var price = fn_getPrice($("#work_arrival").val(), $("#work_finish").val(), basicPrice, shortPrice, shortPriceNight, addDay, addNight);
	
	price += (addPrice * 1);
	
	$(selectThis).val( jobSeqArray[0].jobName );
	$(selectThis).parents(".secondOI").find(".secValue2 .price").val( price);
	$(selectThis).parents(".secondOI").find(".secValue1 .kind_code").val( jobSeqArray[0].jobSeq);
	$(selectThis).parents(".secondOI").find(".secValue1 .job_detail_code").val( jobSeqArray[1].jobSeq);
	
	if(jobSeqArray[1].jobSeq) {
		$(selectThis).parents(".secondOI").find(".secValue1 .job_detail_name").val( replaceAll(jobSeqArray[1].jobName, ',', '|'));
	}else {
		$(selectThis).parents(".secondOI").find(".secValue1 .job_detail_name").val( null );
	}
	
	$(selectThis).parents(".secondOI").find(".secValue1 .job_add_code").val( jobSeqArray[2].jobSeq);
	if(jobSeqArray[2].jobSeq) {
		$(selectThis).parents(".secondOI").find(".secValue1 .job_add_name").val( replaceAll(jobSeqArray[2].jobName, ',', '|'));
	}else {
		$(selectThis).parents(".secondOI").find(".secValue1 .job_add_name").val( null );
	}
	
	$(selectThis).parents(".secondOI").find(".secValue2 .price").attr('add_price', addPrice);		//추가단가
	$(selectThis).parents(".secondOI").find(".secValue2 .price").attr('basic_price', lastJob.basic_price);	//기본단가
	$(selectThis).parents(".secondOI").find(".secValue2 .price").attr("short_price", lastJob.short_price);
	$(selectThis).parents(".secondOI").find(".secValue2 .price").attr("short_price_night", lastJob.short_price_night);
	$(selectThis).parents(".secondOI").find(".secValue2 .price").attr("add_day", lastJob.add_day_time);
	$(selectThis).parents(".secondOI").find(".secValue2 .price").attr("add_night", lastJob.add_night_time);
	
	
	
	closeJobOffer();
	
	
}

function getBasicPrice(jobSeq){
	var job = jobList.find(function(item){    
		  return (item.job_seq === jobSeq);
	}); 
	
	var work_arrival = $("#work_arrival").val();
	var work_finish = $("#work_finish").val();
	
	var basic_price 			= job.basic_price;
	var short_price 			= job.short_price;
	var short_price_night 	= job.short_price_night;
	var add_day 				= job.add_day_time;
	var add_night 			= job.add_night_time;
	
	return fn_getPrice(work_arrival, work_finish, basic_price, short_price, short_price_night, add_day, add_night);
}

function getAddPrice(jobSeq){
	var job = jobList.find(function(item){    
		  return (item.job_seq === jobSeq);
	}); 
			
	return job.basic_price * 1;
}

function convertTimeFormat(timeStr){
	//time format : hhmm
	var min = Math.round(timeStr % 1 * 60);
	if( min < 10 ){
		min = "0" + min;
	}
	return Math.floor(timeStr) + ":" + min;
}

function getBreakTime(startTime, endTime){
	var result = "12:00 ~ 13:00";
	
	//var startTime = $("#startTime").data("work-start-time") * 1;
	//var endTime = $("#endTime").data("work-end-time") * 1;
	
	var BstartTime = Math.floor(startTime/100) + (startTime % 100 / 60);
	var BendTime = Math.floor(endTime/100) + (endTime % 100 / 60);
	
	var diffTime = BendTime - BstartTime;
	var breakTime = 0;
	if( diffTime >= 4 && diffTime < 8.5 ){
		breakTime = 0.5;
	}else if( diffTime >= 8.5 ){
		breakTime = 1;
	}
	
	if( breakTime == 0 ){
		return "";
	}
	
	if( 12 + breakTime <= BendTime && BstartTime <= 12 ){
		if( breakTime == 0.5 ){
			result = "12:00 ~ 12:30";
		}
	}else{
		var breakStart = BendTime - breakTime;
		var BendTimeStr = convertTimeFormat(BendTime);
		var breakStartStr = convertTimeFormat(breakStart);
		
		result = breakStartStr + " ~ " + BendTimeStr;
	}
	return result;
}

function textNumSum(textNum, targetNum){
	var result = textNum * 1 + targetNum;
	if( result < 10 ){
		return "0" + result;
	}
	return result;
}

function getOneMinuteCalc(endTime, sign){
	endTime = endTime + "";
	if( endTime.length == 3 ){
		endTime = "0" + endTime;
	}
	
	var endTimeHour = endTime.substring(0, 2);
	var endTimeMin = endTime.substring(2, 4);
	
	if( sign == "add" ){
		if( endTimeMin == "59" ){
			endTimeHour = textNumSum(endTimeHour, 1);
			endTimeMin = "00";
		}else{
			endTimeMin = textNumSum(endTimeMin, 1);
		}
	}else if( sign == "minus" ){
		if( endTimeMin == "00" ){
			endTimeHour = textNumSum(endTimeHour, -1);
			endTimeMin = "59";
		}else{
			endTimeMin = textNumSum(endTimeMin, -1);
		}
	}
	return endTimeHour + ":" + endTimeMin;
}
function getWorkEndTime(startTime, endTime){
	//4시간, 8시간 반 근무시 -1분 처리
	
	if( endTime - startTime == 400 ){
		return getOneMinuteCalc(endTime, "minus");
	}
	
	var BstartTime = Math.floor(startTime/100) + (startTime % 100 / 60);
	var BendTime = Math.floor(endTime/100) + (endTime % 100 / 60);
	if( BendTime - BstartTime == 8.5 ){
		return getOneMinuteCalc(endTime, "minus");
	}

	//9분으로 끝나는 시간 +1분 처리
	var lastEndTime = endTime % 10;
	if( lastEndTime == 9 ){
		return getOneMinuteCalc(endTime, "add");
	}
	
	var endTimeStr = endTime + "";
	
	return endTimeStr.substring(0,2) + ":" + endTimeStr.substring(2,4);
}
function setBreakTimeDiv(breakTimeStr){
	if( breakTimeStr == "" ){
		$(".breaktime-div").hide();
	}else{
		$("#breakTimeSpan").text(breakTimeStr);
		$(".breaktime-div").show();
	}
}

function timeChange(){
	$("[class ^= price]").each(function() {
		
		//시간 변경시 -1분된거 초기화
        var _endTimeStr = $("#work_finish").val() + "";
        var _endTimeMin = _endTimeStr.substring(_endTimeStr.length-1);
        if( _endTimeMin == "9" ){
        	var _initEndTime = getOneMinuteCalc(_endTimeStr, "add");
        	$('#work_finish').val(_initEndTime.replace(":", "")).prop("selected",true);
        }
		
		var work_arrival 	= $("#work_arrival").val();
		var work_finish 	= $("#work_finish").val();
		
		//휴게시간 최소화로 하는 로직
		var _endTime = getWorkEndTime(work_arrival, work_finish);
		var exists = $('#work_finish option[value='+ _endTime.replace(":", "") +']').length;
		if( exists == 0 ){
			$("#work_finish").append("<option style='display:none;' value='"+ _endTime.replace(":", "") +"'>" + _endTime + "</option>");
		}
	    $('#work_finish').val(_endTime.replace(":", "")).prop("selected",true);
	    
	    //휴게시간
		var _breakTime = getBreakTime($("#work_arrival").val(), $("#work_finish").val());
		$("#work_breaktime").val(_breakTime);
		setBreakTimeDiv(_breakTime);
		
		var basic_price 	= $(this).attr('basic_price');
		var short_price 	= $(this).attr('short_price');
		var short_price_night = $(this).attr('short_price_night');
		var add_day = $(this).attr('add_day');
		var add_night = $(this).attr('add_night');
		var add_price = Number($(this).attr('add_price'));
		
		if(basic_price != "0"){
			$(this).val( fn_getPrice(work_arrival, work_finish, basic_price, short_price, short_price_night, add_day, add_night) + add_price );
		}
	});
}

function closeJobOffer(){
	$(".secondOIpop").hide();
	addPrice = 0;
	jobSeqArray = new Array();
	selectThis = null;
	memo3 ="";
}

function jobCalInit(flag){
	if(flag == 0){
		jobSeqArray[0] = {jobSeq:null, jobName:null};
		jobSeqArray[1] = {jobSeq:null, jobName:null};
		jobSeqArray[2] = {jobSeq:null, jobName:null};
		
	}
	
	if(flag ==1){
		jobSeqArray[1] = {jobSeq:null, jobName:null};
		jobSeqArray[2] = {jobSeq:null, jobName:null};
		
	}
	if(flag ==2){
		jobSeqArray[2] = {jobSeq:null, jobName:null};
	}
	
}



