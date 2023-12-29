function fn_page_move(formName, objName, pageNo) {
	
	$("form[name="+formName+"]").find(""+objName+"").val(pageNo);
	$("form[name="+formName+"]").submit();
	//eval(formName +"."+ objName).value = pageNo;
	//eval(formName).submit();
}


function changePageSize(val){
	$("#pageSize").val(val);
	$("input[name=pageNo]").val(1);
	$("#frm").submit();
}



function fn_page_display(pageSize, rowCount, formName, objName) {
	
	/****************
	displayCount : 화면에 보여주는 페이지번호 갯수
	pageSize     : 한 페이지당 레코드 갯수
	pageCount    : 실제 페이지 갯수
	pageIndex
	***/
	displayCount = 5;
	
	currPage    = Number($("#"+formName+" "+objName+"").val());
	startPageNo = Math.floor((currPage-1)/displayCount) * displayCount + 1;
	pageCount   = Math.ceil(rowCount / pageSize);
	

	if (pageCount < 1) return;
	
	//text = "<div class='col-xs-6'>";
	text = "";
	//text += "<div class='dataTables_paginate paging_bootstrap'>";
	//text += '<div class="paging01">';	
	
	if (startPageNo > 1){
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (startPageNo -1) +") class='btn'><img src='"+resourcePath+"/resources//images/admin/pg_first.gif' alt='이전 10개'></a>";
	} else {
		
	}
		

	if (currPage > 1){
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (currPage - 1) +") class='btn'><img src='"+resourcePath+"/resources/images/admin/pg_prev.gif' alt='이전'></a>";
		//text += "<li ><a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage - 1) +");'>← 이전</a></li>";		// 이전
		//text += "<a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage - 1) +");'>이전</a>";
		//text += "<a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage - 1) +");' class='prev'><img src='/resources/web/images/btn/prev.png' alt='이전으로 이동'/></a>";
	}else{
		//text += "<li class='prev disabled'><a href='javascript:void(0);'>← 이전</a></li>";
	}
		

	//if (pageCount == 1)
		//text += "<li class='img'><img src='"+resourcePath+"/resources/images/btn_list_prev.gif'></li>";
	
	
	var classTmp = "";
	text += "<ul>";
	if (currPage > 1){
		text += "<li><a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage - 1) +");'>&lt;</a></li>";	
	}
	
	for (pageIndex=startPageNo; pageIndex<startPageNo+displayCount && pageIndex<=pageCount; pageIndex++)
	{
		
		if (pageIndex == currPage){
			text += "<li><strong>"+currPage+"</strong></li>";			
		}
		else{
			text += "<li><a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ pageIndex +");'>"+pageIndex+"</a></li>";	
		}
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ pageIndex +") class='"+classTmp+"'>" + pageIndex + "</a></li>";
		
		//text += "<li "+classTmp+"><a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ pageIndex +");' >" + pageIndex + "</a></li>";
	}
	if (pageCount > currPage) {
		text += "<li><a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage + 1) +");'>&gt;</a></li>";
	}
	text += "</ul>";
	

	if (pageCount > currPage) {
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (currPage + 1) +") class='btn'><img src='"+resourcePath+"/resources/images/admin/pg_next.gif' alt='다음'></a>";
		//text += "<li><a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage + 1) +");'>다음 → </a></li>";
		//text += "<a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage + 1) +");' class='next'><img src='/resources/web/images/btn/next.png' alt='다음으로 이동'/></a>";
	}else{
		//text += "<li class='next disabled'><a href='javascript:void(0);'>다음 → </a></li>";
	}
		

	if ((startPageNo + displayCount) < pageCount){
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (startPageNo + displayCount) +") class='btn'><img src='"+resourcePath+"/resources/images/admin/pg_last.gif' alt='다음 10개'></a>";
	} 
		
	
	//if (pageCount == 1)
		//text += "<li class='img end'><img src='"+resourcePath+"/resources/images/btn_list_next.gif'></li>";
	
	//text += "</div>";
	text += "";
	
	document.write(text);
}



function fn_page_move_ajax(formName, objName, pageNo, url) {	
	
	$("#" + formName).find(""+objName+"").val(pageNo);
	var _data = $("#" + formName).serialize();
	$.ajax({
	         type : "POST",
	         url : url,
	         data : _data,
	         dataType : "text",
	         jsonp : 'callback',
	         async : true,
	         beforeSend : function(xhr) {	 			
	        	 xhr.setRequestHeader("AJAX", true);
		 		 $(".wrap-loading").show();
		 	 },
	         error : function() {
	            alert("Error");
	         },
	         success : function(obj) {	        	 
	            if( jQuery.type($(obj).find(".program_news_list").html()) != 'undefined' ) {      
	                $(".program_news_list").html($(obj).find(".program_news_list").html());                                   
	             }
	            $(".paging01").html(page);

	         },
	      complete : function() { 
	    	  $(".wrap-loading").hide();
	      }
	});
}

function fn_page_display_ajax(pageSize, rowCount, formName, objName, url) {
	
	/****************
	displayCount : 화면에 보여주는 페이지번호 갯수
	pageSize     : 한 페이지당 레코드 갯수
	pageCount    : 실제 페이지 갯수
	pageIndex
	***/
	displayCount = 5;
	
	currPage    = Number($("#"+formName+" "+objName+"").val());
	startPageNo = Math.floor((currPage-1)/displayCount) * displayCount + 1;
	pageCount   = Math.ceil(rowCount / pageSize);

	if (pageCount < 1) return;
	
	var text = "";	
	
	if (startPageNo > 1){
	} else {
	}		

	if (currPage > 1){
	//	text += "<a href='javascript:fn_page_move_ajax(\""+ formName +"\",\""+ objName +"\","+ (currPage - 1) +"\",\""+ url +"\");' class='prev'><img src='/resources/web/images/btn/prev.png' alt='이전으로 이동'/></a>";
	}else{
	}	
	
	text += "<ul>";
	
	if (currPage > 1){
		text += "<li><a href='javascript:fn_page_move_ajax(\""+ formName +"\",\""+ objName +"\","+ (currPage - 1) +"\,\""+ url +"\");'>&lt;</a></li>";
	}
	
	for (pageIndex=startPageNo; pageIndex<startPageNo+displayCount && pageIndex<=pageCount; pageIndex++)
	{		
		if (pageIndex == currPage){
			text += "<li><strong>"+currPage+"</strong></li>";			
		}
		else{
			text += "<li><a href='javascript:fn_page_move_ajax(\""+ formName +"\",\""+ objName +"\","+ pageIndex +",\""+ url +"\");'>"+pageIndex+"</a></li>";	
		}
	}
	
	if (pageCount > currPage) {
		text += "<li><a href='javascript:fn_page_move_ajax(\""+ formName +"\",\""+ objName +"\","+ (currPage + 1) +"\,\""+ url +"\");'>&gt;</a></li>";
	}
	
	text += "</ul>";	

	if (pageCount > currPage) {
	//	text += "<a href='javascript:fn_page_move_ajax(\""+ formName +"\",\""+ objName +"\","+ (currPage + 1) +"\",\""+ url +"\");' class='next'><img src='/resources/web/images/btn/next.png' alt='다음으로 이동'/></a>";
	}else{
	}

	if ((startPageNo + displayCount) < pageCount){
	} 
	
	text += "";
	return text;
	
}
function fn_page_display_ajax_new(pageSize, rowCount, objName, url, pagingClass, queryName) {
	
	/****************
	displayCount : 화면에 보여주는 페이지번호 갯수
	pageSize     : 한 페이지당 레코드 갯수
	pageCount    : 실제 페이지 갯수
	pageIndex
	***/
	displayCount = 5;
	currPage    = Number($(objName).val());
	
	startPageNo = Math.floor((currPage-1)/displayCount) * displayCount + 1;
	
	pageCount   = Math.ceil(rowCount / pageSize);

	if (pageCount < 1) return;
	
	var text = "";	
	
	text += "<ul>";
	if (currPage > 1){
		text += "<li><a href='javascript:void(0);' onclick='fn_page_move_ajax_new(\""+ objName +"\","+ (currPage - 1) +"\,\""+ url +"\", \"" + pagingClass + "\",\""+queryName+"\");'>이전</a></li>";
	}
	
	for (pageIndex=startPageNo; pageIndex<startPageNo+displayCount && pageIndex<=pageCount; pageIndex++)
	{		
		if (pageIndex == currPage){
			text += "<li><a href='javascript:void(0)'><strong>"+currPage+"</strong></a></li>";			
		}
		else{
			text += "<li><a href='javascript:void(0);' onclick='fn_page_move_ajax_new(\""+ objName +"\","+ pageIndex +"\,\""+ url +"\", \"" + pagingClass + "\",\""+queryName+"\");'>"+pageIndex+"</a></li>";
		}
	}
	
	if (pageCount > currPage) {
		text += "<li><a href='javascript:void(0);' onclick='fn_page_move_ajax_new(\""+ objName +"\","+ (currPage + 1) +"\,\""+ url +"\", \"" + pagingClass + "\",\""+queryName+"\");'>다음</a></li>";
	}
	
	text += "</ul>";	
	
	text += "";
	$(pagingClass).append(text);
}
function fn_page_move_ajax_new(objName, pageNo, url, pagingClass, queryName) {
	/*
	 * fn_page_move_ajax_new를 호출하는 쪽에서 fn_data_receive 함수를 생성해서 obj를 받아야 함.
	 */
	
	$(objName).val(pageNo);
	if( $(queryName).val() == undefined ){
		var _data = {
			"paging.pageNo" : pageNo
		}
	}else{
		var _data = {
			"paging.pageNo" : pageNo,
			query : $(queryName).val()
		}
	}
	
	$.ajax({
	         type : "POST",
	         url : url,
	         data : _data,
	         dataType : "json",
	         jsonp : 'callback',
	         async : true,
	         beforeSend : function(xhr) {	 			
	        	 xhr.setRequestHeader("AJAX", true);
		 		 $(".wrap-loading").show();
		 	 },
	         error : function(e) {
	            console.log("error :: ", e);
	         },
	         success : function(obj) {
	        	fn_data_receive(obj, pagingClass);
	         },
	      complete : function() { 
	    	  $(".wrap-loading").hide();
	      }
	});
}