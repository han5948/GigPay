<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
	var admin_seq = '${sessionScope.ADMIN_SESSION.admin_seq }';
	var admin_level = '${sessionScope.ADMIN_SESSION.auth_level }';
	var ws;
	var notification_seq = '';
	
	$(document).ready(function() {
		wsOpen();	
	});
	
	function wsOpen(){
		ws = new WebSocket("ws://localhost:8081/ws");
		
		wsEvt();
	}
	
	function wsEvt(companySeq) {
		ws.onopen = function(data){
			var sendMessage = {
				admin_level : admin_level,
				admin_seq : admin_seq,
				notification_type : 'W'
			};

			ws.send(JSON.stringify(sendMessage));
		}
		
		ws.onmessage = function(data) {
			var jsonObj = JSON.parse(data.data);
			var notificationList = jsonObj.notificationList;
			var text = '';
			
			if(notification_seq == '') {
				$("#listBody").empty();
				
				for(var i = 0; i < notificationList.length; i++) {
					if(i == 0) {
						notification_seq = notificationList[i].notification_seq;
					}
					
					text += '<tr>';					
					text += '	<td>' + nullCheck(notificationList[i].work_date) + '</td>';
					text += '	<td><a href="#" style="color:' +getNotiTitleColor(notificationList[i].status_flag) + '" onclick="clickWebPush(\''+notificationList[i].status_flag+'\', \'' + notificationList[i].ilbo_seq + '\', \'' + notificationList[i].work_date + '\')">' + nullCheck(notificationList[i].title) + '</a></td>';
					text += '	<td>' + nullCheck(notificationList[i].content) + '</td>';
					text += '	<td>' + nullCheck(notificationList[i].work_company_name) + '</td>';
					text += '	<td>' + nullCheck(notificationList[i].worker_company_name) + '</td>';
					text += '	<td>' + nullCheck(notificationList[i].reg_date.substring(0,notificationList[i].reg_date.length-2)) + '</td>';
					text += '</tr>';
				}
				
				$("#listBody").append(text);
			}else {
				for(var i = 0; i < notificationList.length; i++) {
					if(parseInt(notification_seq) < parseInt(notificationList[i].notification_seq)) {
						var title = notificationList[i].title;
						var option = {
							body : notificationList[i].content
						};
						
						new Notification(title, option);
						
						notification_seq = notificationList[i].notification_seq;
						
						text += '<tr>';					
						text += '	<td>' + nullCheck(notificationList[i].work_date) + '</td>';
						text += '	<td><a href="#" style="color:' +getNotiTitleColor(notificationList[i].status_flag) + '" onclick="clickWebPush(\''+notificationList[i].status_flag+'\', \'' + notificationList[i].ilbo_seq + '\', \'' + notificationList[i].work_date + '\')">' + nullCheck(notificationList[i].title) + '</a></td>';
						text += '	<td>' + nullCheck(notificationList[i].content) + '</td>';
						text += '	<td>' + nullCheck(notificationList[i].work_company_name) + '</td>';
						text += '	<td>' + nullCheck(notificationList[i].worker_company_name) + '</td>';
						text += '	<td>' + nullCheck(notificationList[i].reg_date.substring(0,notificationList[i].reg_date.length-2)) + '</td>';
						text += '</tr>';
					}
				}
				
				$("#listBody").prepend(text);
			}
		}
		
		ws.onclose = onClose;
	}
	
	function onClose() {
		ws.close();
	}
	
	function nullCheck(str){
		if( str == null ){
			return '';
		}
		return str;
	}
	
	function getNotiTitleColor(status_code){
		var color = "";
		
		if( status_code == "ALR009" || status_code == "ALR010"){ 
			//신규 오더[APP], 신규 오더[WEB]
			color = "#FF7F00";
			//color = "#4FA7E5";
		}else if( status_code == "ALR004" || status_code == "ALR005" || status_code == "ALR011" ){ 
			//펑크, 째앰, 대마
			color = "#9900FF";
		}else if( status_code == "ALR003" || status_code == "ALR016" || status_code == "ALR014"){ 
			// 다른 사람, 또가요, 재요청
			color = "#13DB10";
		}else if( status_code == "ALR024"){ 
			// 전화
			color = "#F53C3C";
		}
		
		return color;
	}
	
	function clickWebPush(status_flag, ilbo_seq, work_date){
		if( ilbo_seq == null || ilbo_seq == 'null' ){
			return;
		}
		
		var url;
		var srh_ilbo_type;
		var frm = $("#defaultFrm");
		var _data = {
			ilbo_seq : ilbo_seq
	    };
		
    	var _url = 'main/getIlboInfo';
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var ilboDTO = data.ilboDTO;
    			
    			if( status_flag == "ALR001" || status_flag == "ALR002" || status_flag == "ALR003" || status_flag == "ALR006" 
    				|| status_flag == "ALR008" || status_flag == "ALR009" || status_flag == "ALR011" || status_flag == "ALR012" || status_flag == "ALR024" ){
    			
					ilboView('i.work_seq', ilboDTO.work_seq, '/admin/workIlbo');
					
	    		}else if ( status_flag == "ALR004" || status_flag == "ALR005" || status_flag == 'ALR019' || status_flag == 'ALR016'){
	    			
	    			ilboView('i.worker_seq', ilboDTO.worker_seq, '/admin/workerIlbo');
	    			
	    		}else if(status_flag == "ALR010"){
	    			url = "/admin/orderList";
	    			
	    			//작업일자 보내기
	    			if( jQuery.type($("#work_date").val()) === "undefined" ){
	    				frm.append("<input type='hidden' id='work_date' name='work_date' value='"+ work_date +"' />");
	    			}else{
	    				$("#work_date").val( work_date );
	    			}
	    			
	    			//오더관리 탭 활성화
	    			var input1 = $("#defaultFrm > input[name=ADMIN_DEPT1_CODE]");    // 관리자 메뉴 코드1
	    			if ( jQuery.type(input1.val()) === "undefined" ) {
	    				frm.append("<input type='hidden' name='ADMIN_DEPT1_CODE' value='0' />"); //오더관리 탭 인덱스 0
	    			}else {
	    			    input1.val("0");
	    			}
	    			
	    			frm.attr('target', '_blank');
	    			frm.attr('action', url).submit();
	    		}
    		}else {
    			toastFail("오류가 발생했습니다.3");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.3");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function ilboView(type, seq, url) {
		var frmId  = "defaultFrm";    // default Form
		var frm      = $("#"+ frmId);
		var ilboView = $("#"+ frmId +" > input[name=ilboView]");          // 대장으로 이동
		var ilboType = $("#"+ frmId +" > input[name=srh_ilbo_type]");   // 대장으로 이동
		var ilboSeq  = $("#"+ frmId +" > input[name=srh_seq]");           // 대장으로 이동
		var val = "Y";
		
		if ( jQuery.type(ilboView.val()) === "undefined" ) {
			frm.append("<input type='hidden' name='ilboView' value='"+ val +"' />");
		} else {
		    ilboView.val(val);
		}
		
		if ( jQuery.type(ilboType.val()) === "undefined" ) {
		    frm.append("<input type='hidden' name='srh_ilbo_type' value='"+ type +"' />");
		} else {
		    ilboType.val(type);
		}
		
		if ( jQuery.type(ilboSeq.val()) === "undefined" ) {
			frm.append("<input type='hidden' name='srh_seq' value='"+ seq +"' />");
		} else {
		    ilboSeq.val(seq);
		}
		
		frm.attr('target', '_blank');
		frm.attr('action', url).submit();
		  
		//초기화
		frm.attr('target', '');
		frm.attr('action', '');
		
		$("#"+ frmId +" > input[name=ilboView]").val("");
	}
</script>
	<table class="bt-list">
		<colgroup>
			<col width="100px" />
			<col width="100px" />
		</colgroup>
		<thead>
			<tr>
				<th>작업일자</th>
				<th>제목</th>
				<th>내용</th>
				<th>구인처</th>
				<th>구직처</th>
				<th>발신일</th>
			</tr>
		</thead>
		<tbody id="listBody">
		</tbody>
	</table>
