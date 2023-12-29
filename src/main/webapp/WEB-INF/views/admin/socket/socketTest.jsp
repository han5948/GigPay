<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
	var company_seq = '${sessionScope.ADMIN_SESSION.company_seq }';
	var ws;
	
	$(document).on("ready", function() {
		wsOpen();	
	});
	
	function wsOpen(){
		ws = new WebSocket("ws://localhost:8081/ws");
		
		wsEvt();
	}
	
	function wsEvt(companySeq) {
		ws.onopen = function(data){
			var sendMessage = {
				loginCompanySeq : company_seq,
				companySeq : company_seq
			};

			if(companySeq)
				sendMessage.company_seq = companySeq;
			else
				sendMessage.company_seq = company_seq;		
			
			ws.send(JSON.stringify(sendMessage));
		}
		
		ws.onmessage = function(data) {
			var jsonObj = JSON.parse(data.data);
			var companyList = jsonObj.companyList;
			var text = '';
			
			for(var i = 0; i < companyList.length; i++) {
				text += "<tr>";
				text += "	<td>" + companyList[i].company_seq + "</td>";
				text += "	<td>" + companyList[i].company_name + "</td>";
				text += "</tr>";
			}
			
			$("#listBody").append(text);
		}
		
		ws.onclose = onClose;
	}
	
	function onClose() {
		ws.close();
	}
	
	function send(companySeq) {
		var sendMessage = {
				loginCompanySeq : company_seq,
				companySeq : companySeq,
		}
		
		ws.send(JSON.stringify(sendMessage));
// 		ws.close();
		
// 		ws = new WebSocket("ws://localhost:8081/ws");
		
// 		ws.onopen = function(data) {
// 		}
		
// 		ws.onmessage = function(data) {
// 			var jsonObj = JSON.parse(data.data);
// 			var companyList = jsonObj.companyList;
// 			var text = '';
			
// 			for(var i = 0; i < companyList.length; i++) {
// 				text += "<tr>";
// 				text += "	<td>" + companyList[i].company_seq + "</td>";
// 				text += "	<td>" + companyList[i].company_name + "</td>";
// 				text += "</tr>";
// 			}
			
// 			$("#listBody").append(text);
// 		}
	}
</script>
<!-- 	<input type="button" value="본사" onclick="send(13)" /> -->
	<input type="button" value="서부점" onclick="send(14)" />
	<input type="button" value="서울점" onclick="send(2)" />
	<input type="button" value="연결 해제" onclick="onClose()" />
	
	<table class="bt-list">
		<colgroup>
			<col width="100px" />
			<col width="100px" />
		</colgroup>
		<thead>
			<tr>
				<th>company_seq</th>
				<th>company_name</th>
			</tr>
		</thead>
		<tbody id="listBody">
		</tbody>
	</table>
