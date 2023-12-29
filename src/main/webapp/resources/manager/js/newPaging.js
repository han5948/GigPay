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
	
	currPage    = Number($(""+objName+"").val());
	startPageNo = Math.floor((currPage-1)/displayCount) * displayCount + 1;
	pageCount   = Math.ceil(rowCount / pageSize);	
	
	
	if (pageCount < 1) return;
	
	
	text = "";
	//text += "<div class=\"paging-wrap\">";
	text += "<a href=\"javascript:fn_page_move('"+ formName +"','"+ objName +"',1)\" class=\"first ctrl\"><span class=\"blind\">첫페이지로</span></a>"; 
	
	
		

	if (currPage > 1){
		text += "<a href=\"javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (currPage - 1) +");\" class='prev ctrl'><span class='blind'>이전 페이지로</span></a>";
		
	}else{
	
	}
	
	text += "<ul>";
	
	var classTmp = "";
	for (pageIndex=startPageNo; pageIndex<startPageNo+displayCount && pageIndex<=pageCount; pageIndex++)
	{
		
		if (pageIndex == currPage){
			
			text += "<li><a href='#' class='active'>"+currPage+"</a></li>";
		}
		else{
			text += "<li><a href=\"javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ pageIndex +");\">"+pageIndex+"</a></li>";	
			
		}
		
		
	}
	text += "</ul>";

	if (pageCount > currPage) {
		text += "<a href=\"javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (currPage + 1) +");\" class='next ctrl'><span class='blind'>다음페이지로</span></a>;"
	}else{
	
	}
		

	if ((startPageNo + displayCount) < pageCount){
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (startPageNo + displayCount) +") class='btn'><img src='"+resourcePath+"/resources/images/admin/pg_last.gif' alt='다음 10개'></a>";
	} 
		
	text += "<a href=\"javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ pageCount +");\" class='last ctrl'><span class='blind'>끝페이지로</span></a>";
	
	
	//text += "</div>";
	text += "";
	
	document.write(text);
}
