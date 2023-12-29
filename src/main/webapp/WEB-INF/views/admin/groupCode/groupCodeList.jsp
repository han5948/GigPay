<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="<c:url value="/resources/cms/js/paging.js" />" charset="utf-8"></script>
<script type="text/javascript">
	window.onload = function () {
		//setDayType('start_reg_date', 'end_reg_date', 'all');
		
		//setDayType(1, 'to_ilbo_date', 'day');
		
	    if (window.Notification) {
	        Notification.requestPermission();
	    }
	    
	}
	
	$(document).ready(function() {
	});
	
	// Layer popup 열기
	function openIrPopup(popupId, status) {
		//popupId : 팝업 고유 클래스명
		var $popUp = $('#'+ popupId);

		$dimmed.fadeIn();
		//$popLayer.hide();
		$popUp.show();

		$popUp.css({
			'margin-top': '-24%',
	    	'margin-left': '-40%',
	    	'margin-right': '10%',
	    	'overflow-y': 'scroll',
	    	'height': '95%'
	  	});
		
	}
	
	function codeList(group_code, group_name) {
		$("#codeListTit").text(group_name + " 코드 목록");
		$("#codeListTit").data("group_code", group_code);
		
		var _data = {
	    		code_type : group_code
    	};
    	
    	var _url = "<c:url value='/admin/code/getCodeList' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			$("#code_type").val(group_code);
    			
    			var codeList = data.codeList; 
    			var codeDTO = data.codeDTO;
    			var text = '';
    			
    			$("#codeListBody").empty();
    			
    			if(codeList == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="12">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    			}else {
    				$("#codeListLength").val(codeList.length);
    				
    				for(var i = 0; i < codeList.length; i++) {
    					text += '<tr>';
	    				text += '	<td>' + (i + 1) + '</td>';
	    				text += '	<td><input type="text" name="code_name" class="inp-field wid100" value="' + codeList[i].code_name + '"></td>';
	    				text += '	<td><input type="text" name="code_value" class="inp-field wid100" value="' + codeList[i].code_value + '" maxLength="6"></td>';
	    				text += '	<td><input type="text" name="code_color" class="inp-field wid100" value="' + codeList[i].code_color + '"></td>';
	    				text += '	<td><input type="text" name="code_bgcolor" class="inp-field wid100" value="' + codeList[i].code_bgcolor + '"></td>';
	    				text += '	<td><input type="text" name="code_group" class="inp-field wid100" value="' + codeList[i].code_group + '"></td>';
	    				text += '	<td>';
	    				
	    				if(codeList[i].use_yn == '1') {
	    					text += '		<input type="radio" value="1" name="layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">사용</label>';
	    					text += '		<input type="radio" value="0" name="layer_use_yn' + i + '"><label for="" class="label-radio">미사용</label>';
    					}else {
	    					text += '		<input type="radio" value="1" name="layer_use_yn' + i + '"><label for="" class="label-radio">사용</label>';
    						text += '		<input type="radio" value="0" name="layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">미사용</label>';
    					}
    				
	    				text += '	</td>';
	    				text += '	<td><input type="text" name="code_gender" class="inp-field wid100" value="' + codeList[i].code_gender + '"></td>';
	    				text += '	<td><input type="text" name="code_price" class="inp-field wid100" value="' + codeList[i].code_price + '"></td>';
	    				text += '	<td><input type="text" name="app_code" class="inp-field wid100" value="' + codeList[i].app_code + '"</td>';
	    				text += '	<td>';
	    				text += '		<div class="btn-module">';
	    				text += '			<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveUp(this)">▲</a>';
	    				text += '			<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveDown(this)">▼</a>';
	    				text += '		</div>';
	    				text += '	</td>';
	    				text += '	<td>';
	    				text += '		<div class="btn-module">';
	    				text += '			<a href="javascript:void(0);" style="text-decoration: none;" class="btnStyle04" onclick="deleteCode(' + i + ', this)">삭제</a>';
	    				text += '		</div>';
	    				text += '	</td>';
	    				text += '	<input type="hidden" value="U" name="code_flag">';
	    				text += '	<input type="hidden" name="code_seq" value="' + codeList[i].code_seq + '">';
	    				text += '	<input type="hidden" name="trSeq" value="' + i + '" />';
	    				text += '</tr>';
    				}
    			}
    			
    			$("#codeListBody").append(text);
    			
    			openIrPopup('popup-layer');
    		}else {
    			toastFail("오류가 발생했습니다.1");
    		}
    	}, function(data) {
    		//errorListener
    		toastFail("오류가 발생했습니다.2");
    	}, function() {
    		//beforeSendListener
    	}, function() {
    		//completeListener
    	});
	}
	
	function addCode() {
		if($("#codeListBody tr td").length == 1) {
			$("#codeListBody").empty();
		}
		
		var text = '';
		
		text += '<tr>';
		text += '	<td>' + ($("#codeListBody tr").length + 1) + '</td>';
		text += '	<td><input type="text" name="code_name" class="inp-field wid100" value=""></td>';
		text += '	<td><input type="text" name="code_value" class="inp-field wid100" value="' + $("#codeListTit").data("group_code") + '" maxLength="6"></td>';
		text += '	<td><input type="text" name="code_color" class="inp-field wid100" value=""></td>';
		text += '	<td><input type="text" name="code_bgcolor" class="inp-field wid100" value=""></td>';
		text += '	<td><input type="text" name="code_group" class="inp-field wid100" value=""></td>';
		text += '	<td>';
		text += '		<input type="radio" value="1" name="layer_use_yn' + $("#codeListBody tr").length + '" checked="checked"><label for="" class="label-radio">사용</label>';
		text += '		<input type="radio" value="0" name="layer_use_yn' + $("#codeListBody tr").length + '"><label for="" class="label-radio">미사용</label>';
		text += '	</td>';
		text += '	<td><input type="text" name="code_gender" class="inp-field wid100" value="0"></td>';
		text += '	<td><input type="text" name="code_price" class="inp-field wid100" value="0"></td>';
		text += '	<td><input type="text" name="app_code" class="inp-field wid100" value="0"></td>';
		text += '	<td>';
		text += '		<div class="btn-module">';
		text += '			<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveUp(this)">▲</a>';
		text += '			<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveDown(this)">▼</a>';
		text += '		</div>';
		text += '	</td>';
		text += '	<td>';
		text += '		<div class="btn-module">';
		text += '			<a href="javascript:void(0);" style="text-decoration: none;" class="btnStyle04" onclick="deleteCode(' + $("#codeListBody tr").length + ', this)">삭제</a>';
		text += '		</div>';
		text += '	</td>';
		text += '	<input type="hidden" value="I" name="code_flag">';
		text += '	<input type="hidden" name="code_seq" value="">';
		text += '	<input type="hidden" name="trSeq" value="' + $("#codeListBody tr").length + '" />';
		text += '</tr>';
		
		$("#codeListBody").append(text);
		
		$("input[name=code_name]").eq($("#codeListBody tr").length - 1).focus();
	}
	
	function deleteCode(index, e) {
		$("input[name=code_flag]").eq(index).val("D");
		
		var $tr = $(e).parent().parent().parent();
		
		$tr.hide();
	}
	
	function updateCode() {
		var sub;
		var array = new Array();
		var use_yn = '';
		
		for(var i = 0; i < $("#codeListBody tr").length; i++) {
			var exp = new RegExp('^[a-zA-Z0-9+]*$');
			var regexp = /^[0-9]*$/;
			
			if($("input[name=code_value]").eq(i).val().charAt(0) == '0') {
				alert("value의 첫번째 글자는 0이 아니어야 합니다.");
				
				$("input[name=code_value]").eq(i).focus();
				
				return false;
			}
			
			if($("input[name=code_value]").eq(i).val().replace(/\s/g, "").length == 0 || $("input[name=code_value]").eq(i).val().replace(/\s/g, "").length != 6) {
				alert("value를 제대로 입력해주세요.");
				
				$("input[name=code_value]").eq(i).focus();
				
				return false;
			} 
			
			if(!exp.test($("input[name=code_value]").eq(i).val())){			
				alert("영문/숫자만 입력이 가능합니다.");
				
				$("input[name=code_value]").eq(i).focus();
				
				return false;
			}
			
			if($("input[name=code_value]").eq(i).val().substring(0, 3) != $("#codeListTit").data("group_code")) {
				alert("value의 앞의 3글자는 변경이 불가능합니다.");
				
				return false;
			}
			
			if(!regexp.test($("input[name=code_group]").eq(i).val())) {
				alert("code_group은 숫자만 입력 가능합니다.");
				
				return false;
			}
			
			sub = {
				'code_seq' : $("input[name=code_seq]").eq(i).val(),
				'code_type' : $("#code_type").val(),
				'code_name' : $("input[name=code_name]").eq(i).val(),
				'code_value' : $("input[name=code_value]").eq(i).val(),
				'code_color' : $("input[name=code_color]").eq(i).val(),
				'code_bgcolor' : $("input[name=code_bgcolor]").eq(i).val(),
				'code_group' : $("input[name=code_group]").eq(i).val(),
				'use_yn' : $("input[name=layer_use_yn" + $("input[name=trSeq]").eq(i).val() + "]:checked").val(),
				'code_gender' : $("input[name=code_gender]").eq(i).val(),
				'code_price' : $("input[name=code_price]").eq(i).val(),
				'code_flag' : $("input[name=code_flag]").eq(i).val(),
				'app_code' : $("input[name=app_code]").eq(i).val()
			}	
			
			array[i] = sub;
		}
		
		var arrayFlag = false;
		
		for(var i = 0; i < array.length; i++) {
			for(var j = i + 1; j < array.length; j++) {
				if(array[j].code_flag != 'D')
					if(array[i].code_value.toUpperCase() == array[j].code_value.toUpperCase()) {
						alert("value값이 중복일 수 없습니다.");
						
						return false;
					}
			}
		}
		
		var _data = JSON.stringify(array);
    	var _url = "<c:url value='/admin/code/updateCode' />";
    	
    	$.ajax({
    		url: _url,
    		type: "POST",
    		data: _data,
    		dataType: "json",
    		contentType: "application/json",
    		success: function(data){
    			if(data.code == "0000") {
    				toastOk("정상적으로 저장되었습니다.");
    				
    				var codeList = data.codeList; 
        			var text = '';
        			
        			$("#codeListBody").empty();
        			
        			if(codeList == 0) {
        				text += '<tr style="text-align: center;">';
        				text += '	<td colspan="12">';
        				text += '		조회된 내용이 없습니다.';
        				text += '	</td>';
        				text += '</tr>';
        			}else {
        				$("#codeListLength").val(codeList.length);
        				
        				for(var i = 0; i < codeList.length; i++) {
        					text += '<tr>';
    	    				text += '	<td>' + (i + 1) + '</td>';
    	    				text += '	<td><input type="text" name="code_name" class="inp-field wid100" value="' + codeList[i].code_name + '"></td>';
    	    				text += '	<td><input type="text" name="code_value" class="inp-field wid100" value="' + codeList[i].code_value + '"></td>';
    	    				text += '	<td><input type="text" name="code_color" class="inp-field wid100" value="' + codeList[i].code_color + '"></td>';
    	    				text += '	<td><input type="text" name="code_bgcolor" class="inp-field wid100" value="' + codeList[i].code_bgcolor + '"></td>';
    	    				text += '	<td><input type="text" name="code_group" class="inp-field wid100" value="' + codeList[i].code_group + '"></td>';
    	    				text += '	<td>';
    	    				
    	    				if(codeList[i].use_yn == '1') {
    	    					text += '		<input type="radio" value="1" name="layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">사용</label>';
    	    					text += '		<input type="radio" value="0" name="layer_use_yn' + i + '"><label for="" class="label-radio">미사용</label>';
        					}else {
    	    					text += '		<input type="radio" value="1" name="layer_use_yn' + i + '"><label for="" class="label-radio">사용</label>';
        						text += '		<input type="radio" value="0" name="layer_use_yn' + i + '" checked="checked"><label for="" class="label-radio">미사용</label>';
        					}
        				
    	    				text += '	</td>';
    	    				text += '	<td><input type="text" name="code_gender" class="inp-field wid100" value="' + codeList[i].code_gender + '"></td>';
    	    				text += '	<td><input type="text" name="code_price" class="inp-field wid100" value="' + codeList[i].code_price + '"></td>';
    	    				text += '	<td><input type="text" name="app_code" class="inp-field wid100" value="' + codeList[i].app_code + '"</td>';
    	    				text += '	<td>';
    	    				text += '		<div class="btn-module">';
    	    				text += '			<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveUp(this)">▲</a>';
    	    				text += '			<a href="javascript:void(0);" class="btnStyle04" style="width: 30px; text-decoration: none;" onclick="fn_moveDown(this)">▼</a>';
    	    				text += '		</div>';
    	    				text += '	</td>';
    	    				text += '	<td>';
    	    				text += '		<div class="btn-module">';
    	    				text += '			<a href="javascript:void(0);" style="text-decoration: none;" class="btnStyle04" onclick="deleteCode(' + i + ', this)">삭제</a>';
    	    				text += '		</div>';
    	    				text += '	</td>';
    	    				text += '	<input type="hidden" value="U" name="code_flag">';
    	    				text += '	<input type="hidden" name="code_seq" value="' + codeList[i].code_seq + '">';
    	    				text += '	<input type="hidden" name="trSeq" value="' + i + '"';
    	    				text += '</tr>';
        				}
        			}
        			
        			$("#codeListBody").append(text);
    			}else {
    				toastFail("저장 실패");
    			}
    		},
    		beforeSend : function(xhr) {
				 xhr.setRequestHeader("AJAX", true);
				 $(".wrap-loading").show();
			},
			error : function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					toastFail("등록 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
    	});
	}
	
	function fn_moveUp(e) {
		var $tr = $(e).parent().parent().parent();
		$tr.prev().before($tr);
	}
	
	function fn_moveDown(e) {
		var $tr = $(e).parent().parent().parent();
		$tr.next().after($tr);
	}
	
	function fn_search() {
		$("#pageNo").val('1');
		
    	var _data = {
    		startDate : $("#start_reg_date").val(),
    		endDate : $("#end_reg_date").val(),
    		searchKey : $("#searchKey").val(),
    		searchValue : $("#searchValue").val(),
    		use_yn : $("input[name=use_yn]:checked").val(),
    		del_yn : '0'
    	};
    	
    	var _url = "<c:url value='/admin/groupCode/getGroupCodeList' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		if(data.code == "0000") {
    			var groupCodeList = data.groupCodeList; 
    			var totalCnt = data.totalCnt;
    			var groupCodeDTO = data.groupCodeDTO;
    			var text = '';
    			
    			$("#listBody").empty();
    			
    			if(groupCodeList == 0) {
    				text += '<tr style="text-align: center;">';
    				text += '	<td colspan="3">';
    				text += '		조회된 내용이 없습니다.';
    				text += '	</td>';
    				text += '</tr>';
    				
    				$(".paging").empty();
    			}else {
    				for(var i = 0; i < groupCodeList.length; i++) {
    					var count = totalCnt - (($("#pageNo").val() - 1) * groupCodeDTO.paging.pageSize + i);
    					
	    				text += '<tr>';
	    				text += '	<td>' + count + '</td>';
	    				text += '	<td>';
	    				text += '		<a href="javascript:void(0);" onclick="fn_edit(' + groupCodeList[i].group_seq + ')">';
	    				text += 			groupCodeList[i].group_name;
	    				text += '		</a>';
	    				text += '	</td>';
	    				text += '	<td>';
	    				text += '		<div class="btn-module">';
	    				text += '			<a href="javascriot:void(0);" style="text-decoration: none;" class="btnStyle04" onclick="openIrPopup(popup-layer)">해당 코드 보기</a>';
	    				text += '		</div>';
	    				text += '	</td>';
	    				text += '</tr>';
    				}
    				
    				if(groupCodeDTO.paging.pageSize != '0') {
    					$(".paging").empty();
 						fn_page_display(groupCodeDTO.paging.pageSize, totalCnt, 'contentFrm', '#pageNo');
    				}
    			}
    			
    			$("#listBody").append(text);
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
    };
	
	function fn_edit(group_seq) {
		$("#groupCode_seq").val(group_seq);
		
		$("#contentFrm").attr("action", "<c:url value='/admin/groupCode/selectInfo' />").submit();
	}
	
	function calculate() {
        notify();
	}
	
	function notify() {
	    if (Notification.permission !== 'granted') {
	        alert('notification is disabled');
	    }else {
	
	        var notification = new Notification('Notification title', {
	            icon: 'http://cdn.sstatic.net/stackexchange/img/logos/so/so-icon.png',
	            body: 'Notification text',
	        });
	
	        notification.onclick = function () {
	            window.open('http://google.com');
	        };
	    }
	}
</script>
	<article>
		<div class="content mgtS">
			<div class="title">
				<h3>코드 관리</h3>
			</div>
		</div>
		
		<table class="bd-list" summary="그룹 코드 리스트">
		    <colgroup>
			    <col width="50px" />
			    <col width="100px" />
			    <col width="300px" />
			    <col width="200px" />
		    </colgroup>
	    	<thead>
				<tr>
					<th>번호</th>
					<th>그룹 코드</th>
					<th>그룹 이름</th>
					<th class="last"></th>
				</tr>
			</thead>
			<tbody id="listBody">
				<c:choose>
					<c:when test="${!empty groupCodeList }">
						<c:forEach var="item" items="${groupCodeList }" varStatus="status">
							<tr>
								<td>${totalCnt - ((groupCodeDTO.paging.pageNo - 1) * groupCodeDTO.paging.pageSize + status.index) }</td>
								<td>${item.group_code }</td>
								<td>
									<a href="javascript:void(0);" onclick="fn_edit('${item.group_seq }')">
										${item.group_name }
									</a>
								</td>
								<td>
									<div class="btn-module">
										<a href="javascript:void(0);" style="text-decoration: none;" onclick="codeList('${item.group_code }', '${item.group_name }')" class="btnStyle04">해당 코드 보기</a>
									</div>
								</td>
							</tr>
			    		</c:forEach>	
					</c:when>
	       			<c:otherwise>
	       				<tr style="text-align: center;">
	       					<td colspan="3">
	       						조회된 내용이 없습니다.
	       					</td>
	       				</tr>
	       			</c:otherwise>
	       		</c:choose>
	    	</tbody>
		</table>
		
		<input type="hidden" id="groupCode_seq" name="group_seq" />
		
		<div class="btn-module mgtL">
			<a href="/admin/groupCode/groupCodeWrite" id="btnAdd" class="btnStyle04">
				그룹 추가
			</a>
		</div>
		
		<input type="hidden" id="pageNo" name="paging.pageNo" value="${groupCodeDTO.paging.pageNo }" />
		<input type="hidden" id="codeListLength" />
		<input type="hidden" id="code_type" name="code_type" />
		
		<c:if test="${groupCodeDTO.paging.pageSize != '0' }">
			<div class="paging">
				<script type="text/javascript">
					fn_page_display('${groupCodeDTO.paging.pageSize}', '${totalCnt }', 'contentFrm', '#pageNo');
				</script>
			</div>
		</c:if>
	</article>
	
	
	<div id="opt_layer" style="position:absolute; display: none; z-index: 1; border: 1px solid #d7d7d7; background: #fcfcfc; color: #9b9b9b;">
	</div>
	
	<div id="popup-layer">
		<header class="header">
			<h1 class="tit" id="codeListTit">코드 목록</h1>
			<a href="#none" class="close" onclick="javascript:closeIrPopup('popup-layer');"><img src="<c:url value="/resources/cms/images/btn_close.gif" />" alt="닫기" /></a>
		</header>
		
		<section>
			<div class="cont-box">
				<article>
					<table class="bd-list" summary="코드 목록">
						<caption>코드 목록</caption>					
						<colgroup>
							<col width="50px" />
							<col width="120px" />
							<col width="120px" />
							<col width="120px" />
							<col width="120px" />
							<col width="120px" />
							<col width="180px" />
							<col width="120px" />
							<col width="120px" />
							<col width="120px" />
							<col width="120px" />
							<col width="*" />
						</colgroup>
						<thead>
							<tr>
								<th>순서</th>
								<th>코드명</th>
								<th>Value</th>
								<th>색상</th>
								<th>백그라운드 색상</th>
								<th>code_group</th>
								<th>사용 여부</th>
								<th>성별</th>
								<th>가격</th>
								<th>app코드</th>
								<th>순서변경</th>
								<th class="last"></th>
							</tr>
						</thead>
						<tbody id="codeListBody">
						</tbody>
					</table>
				</article>
				
				<div class="btn-module mgtL">
				 	<a href="javascript:void(0);" onclick="javascript:addCode();" id="btnAdd" class="btnStyle04">추가</a>
				  	<a href="javascript:void(0);" onclick="javascript:updateCode();" id="btnEdit" class="btnStyle04">저장</a>
			    </div>
			</div>
		</section>
	</div>