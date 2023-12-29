<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<link href="/resources/cms/jqueryUi/jquery-ui.css" rel="stylesheet">
<script type="text/javascript" src="/resources/cms/grid/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="/resources/cms/jqueryUi/jquery-ui.js"></script>

	
<script type="text/javascript">
var painting = false;
$(function(){
	$('#savePdf').click(function() { // pdf저장 button id
		$.ajax({
			type: "POST",
			url: "/test/html2Pdf",
			data: { "pdf_value" : $("#pdfDiv").html()},
			dataType: "json",
			beforeSend: function(xhr){
    			xhr.setRequestHeader("AJAX", true);
    			$(".wrap-loading").show();
    		},
    		success : function(data) {
    			// 성공 시
    			if( data.code == "0000"){
    				alert("출력완료!");
    			}
    		},
    		error : function(data) {
    			// 오류 발생
    			alert("오류가 발생했습니다.3");
    		},
    		complete : function() {
    			// 요청 완료 시
    			$(".wrap-loading").hide();
    		}
		});
	});
});
function showPop(){
	 window.open("/test/popTest", "a", "width=340, height=450, left=100, top=50"); 
}
</script>
<body class="bg">

	<div class="wrap-loading" style="display: none;">
		<div class="ajaxLoading">
			<div id="load"><img src="/resources/cms/img/ajax-loader.gif"></div>
		</div>
	</div>
	<div id="pdfDiv" style="width:500px;">
	    <div style="font-size: 30px;">가나다라</div>
	    <div style="font-size: 50px;">마바사아</div>
	    <div style="font-size: 70px;">자차카타</div>
	    <table style="border: 1px solid black;">
	    	<tr>
	    		<td style="border: 1px solid black;">M</td>
	    		<td>C</td>
	    		<td>D</td>
	    		<td>H</td>
	    	</tr>
	    	<tr>
	    		<td>M</td>
	    		<td>C</td>
	    		<td style="border: 1px solid black;">D</td>
	    		<td>H</td>
	    	</tr>
	    	<tr>
	    		<td>M</td>
	    		<td>C</td>
	    		<td style="border: 1px solid black;">D</td>
	    		<td>H</td>
	    	</tr>
	    	<tr>
	    		<td>M</td>
	    		<td style="border: 1px solid black;">C</td>
	    		<td>D</td>
	    		<td>H</td>
	    	</tr>
	    </table>
	   	<img style="width: 100px; height: 100px;" id="sign" alt="서명" src="">
	</div>
	<div>
		<button type="button" onclick="showPop()">사인하기</button>
	    <button type="button" class="btn btn-primary" id="savePdf" >PDF 저장</button>
	</div>
</body>
	
	
	
   
   