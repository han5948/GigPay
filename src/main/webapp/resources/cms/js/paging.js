/**
  * @FileName : paging.js
  * @Date : 2020. 06. 09
  * @작성자 : Park YunSoo
  * @변경이력 : 
  * @프로그램 설명 : 
  */

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
	
	//text = "<div class='col-xs-6'>";
	text = "";
	//text += "<div class='dataTables_paginate paging_bootstrap'>";
	text += '<div class="paging" style="margin: 20px 0 11px;">';
	
	text += "<ul class='pagination' style='text-align : center;'>";
	
	
	
	if (startPageNo > 1){
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (startPageNo -1) +") class='btn'><img src='"+resourcePath+"/resources//images/admin/pg_first.gif' alt='이전 10개'></a>";
	} else {
		
	}
		

	if (currPage > 1){
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (currPage - 1) +") class='btn'><img src='"+resourcePath+"/resources/images/admin/pg_prev.gif' alt='이전'></a>";
		//text += "<li ><a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage - 1) +");'>← 이전</a></li>";		// 이전
		text += "<a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage - 1) +");'>이전</a>";
	}else{
		//text += "<li class='prev disabled'><a href='javascript:void(0);'>← 이전</a></li>";
	}
		

	//if (pageCount == 1)
		//text += "<li class='img'><img src='"+resourcePath+"/resources/images/btn_list_prev.gif'></li>";
	
	
	var classTmp = "";
	for (pageIndex=startPageNo; pageIndex<startPageNo+displayCount && pageIndex<=pageCount; pageIndex++)
	{
		
		if (pageIndex == currPage){
			text += "<a><strong>"+currPage+"</strong></a>";
		}
		else{
			text += "<a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ pageIndex +");'>"+pageIndex+"</a>";	
		}
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ pageIndex +") class='"+classTmp+"'>" + pageIndex + "</a></li>";
		
		//text += "<li "+classTmp+"><a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ pageIndex +");' >" + pageIndex + "</a></li>";
		
		
	}
	

	if (pageCount > currPage) {
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (currPage + 1) +") class='btn'><img src='"+resourcePath+"/resources/images/admin/pg_next.gif' alt='다음'></a>";
		//text += "<li><a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage + 1) +");'>다음 → </a></li>";
		text += "<a href='javascript:fn_page_move(\""+ formName +"\",\""+ objName +"\","+ (currPage + 1) +");'>다음</a>";
	}else{
		//text += "<li class='next disabled'><a href='javascript:void(0);'>다음 → </a></li>";
	}
		

	if ((startPageNo + displayCount) < pageCount){
		//text += "<a href=javascript:fn_page_move('"+ formName +"','"+ objName +"',"+ (startPageNo + displayCount) +") class='btn'><img src='"+resourcePath+"/resources/images/admin/pg_last.gif' alt='다음 10개'></a>";
	} 
		
	
	//if (pageCount == 1)
		//text += "<li class='img end'><img src='"+resourcePath+"/resources/images/btn_list_next.gif'></li>";
	
	text += "</ul>";
	text += "</div>";
	text += "";
	
	//document.write(text);
	$(".paging").append(text);
}
