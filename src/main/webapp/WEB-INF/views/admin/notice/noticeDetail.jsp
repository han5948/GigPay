<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="t"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	$(document).ready(function() {
		$("#contents").css("min-width", "1000px");
		
		$("#main_view_yn").on("click", function() {
			if($(this).is(":checked") == true) {
				$(this).prop("checked", false);
			}else {
				$(this).prop("checked", true);
			}
		});
	});
	
	function updateForm() {
		if($("input[name=jobChk]:checked").length == 2) {
			$("#auth_view").val(0);
		}else {
			$("#auth_view").val($("input[name=jobChk]:checked").val());
		}
		
		$("#defaultFrm").ajaxForm({
			url : "/admin/notice/updateNotice",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
				if ( data.code == "0000" ) {
					toastOk("정상적으로 수정 되었습니다.");
					
					location.href = '/admin/notice/noticeList';
				} else {
					toastFail("수정 실패");
				}
			} ,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("AJAX", true);
				$(".wrap-loading").show();
			},
	    	error : function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					toastFail("수정 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		}).submit();

	}
	
	function deleteForm() {
		$("#defaultFrm").ajaxForm({
			url : "/admin/notice/deleteNotice",
			dataType : "json",
			enctype	: "multipart/form-data",									// 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
			contentType : "application/x-www-form-urlencoded; charset=utf-8", //캐릭터셋 설정
			success : function(data) {
				if ( data.code == "0000" ) {
					toastOk("정상적으로 삭제 되었습니다.");
					
					location.href = '/admin/notice/noticeList';
				} else {
					toastFail("삭제 실패");
				}
			} ,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("AJAX", true);
				$(".wrap-loading").show();
			},
	    	error : function(e) {
				if ( e.status == "901" ) {
					location.href = "/admin/login";
				} else {
					toastFail("삭제 실패");
				}
				
				$(".wrap-loading").hide();
			},
			complete : function() {
				$(".wrap-loading").hide();
			}
		}).submit();

	}
	
	function fn_replyAdd() {
		var sessionId = "${sessionScope.ADMIN_SESSION.admin_id}";
		
		var _data = {
    		notice_seq : $("#notice_seq").val(),
    		depth : 0,
    		reply_content : $("#replyText").val(),
    		reply_writer : sessionId,
    		reg_admin : sessionId
    	};
		
    	var _url = "<c:url value='/admin/notice/insertReply' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		var replyList = data.replyList;
    		var text = '';
    		if(data.code == "0000") {
    			toastOk("정상적으로 등록 되었습니다.");
    			
    			fn_replyList(sessionId, replyList, text);
    		}else {
    			
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
	
	function fn_btnAdd(index) {
		var idx = $("a[name=replyAddBtn]").index(index);
		var tag = $("a[name=replyAddBtn]").eq(idx).next().next();
		var parent_seq = $("a[name=replyAddBtn]").eq(idx).data("parent_seq");
		var reply_group = $("a[name=replyAddBtn]").eq(idx).data("reply_group");
		var inputTag = '';
		
		inputTag += '<div class="btn-module mgtL">';
		inputTag += '	<input type="text" name="reReplyText" class="inp-field wid700 notEmpty">';
		inputTag += '	<a href="javascript:void(0);" name="reReplyBtn" onclick="fn_reReplyAdd(this, ' + parent_seq + ', ' + reply_group + ');" class="btnStyle04">등록</a>';
		inputTag += '</div>';
		
		tag.html(inputTag);
	}
	
	function fn_reReplyAdd(index, parent_seq, reply_group) {
		var idx = $("a[name=reReplyBtn]").index(index);
		var reReplyText = $("a[name=reReplyBtn]").eq(idx).prev().val();
		var sessionId = "${sessionScope.ADMIN_SESSION.admin_id}";
		var _data = {
    		notice_seq : $("#notice_seq").val(),
    		parent_seq : parent_seq,
    		depth : 1,
    		reply_content : reReplyText,
    		reply_writer : sessionId,
    		reg_admin : sessionId,
    		reply_group : reply_group
    	};
		
    	var _url = "<c:url value='/admin/notice/insertReply' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		var replyList = data.replyList;
    		var text = '';
    		
    		if(data.code == "0000") {
    			toastOk("정상적으로 등록 되었습니다.");
    			
    			fn_replyList(sessionId, replyList, text);
    		}else {
    			
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
	
	function fn_replyDel(reply_seq, parent_seq) {
		var sessionId = "${sessionScope.ADMIN_SESSION.admin_id}";
		var _data = {
    		reply_seq : reply_seq,
    		parent_seq : parent_seq,
    		notice_seq : $("#notice_seq").val()
    	};
		
    	var _url = "<c:url value='/admin/notice/deleteReply' />";
    	
    	commonAjax(_url, _data, true, function(data) {
    		var replyList = data.replyList;
    		var text = '';
    		
    		if(data.code == "0000") {
    			toastOk("정상적으로 삭제 되었습니다.");
    			
    			fn_replyList(sessionId, replyList, text);
    		}else {
    			
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
	
	function fn_replyList(sessionId, replyList, text) {
		$("#replyArea li").remove();
		
		for(var i = 0; i < replyList.length; i++) {
			if(replyList[i].depth == 0) {
				text += '<li>';
				text += replyList[i].reply_writer + ' || ' + replyList[i].reply_content + ' || ' + replyList[i].reg_date;
				text += '	<div class="btn-module">';
				text += '		<a href="javascript:void(0);" onclick="fn_btnAdd(this);" name="replyAddBtn" class="btnStyle04" data-parent_seq="' + replyList[i].reply_seq + '" data-reply_group="' + replyList[i].reply_group + '">답글달기</a>';
				
				if(sessionId == replyList[i].reply_writer) {
					text += '<a href="javascript:void(0);" onclick="fn_replyDel();" name="replyDelBtn" class="btnStyle04">삭제</a>';
				}
				
				text += '	<div>';
				text += '	</div>';
				text += '	</div>';
				text += '</li>';
			}else {
				text += '<li>';
				text += '	===> ' + replyList[i].reply_writer + ' || ' + replyList[i].reply_content + ' || ' + replyList[i].reg_date;
				text += '	<div class="btn-module">';
				
				if(sessionId == replyList[i].reply_writer) {
					text += '<a href="javascript:void(0);" onclick="fn_replyDel();" name="replyDelBtn" class="btnStyle04">삭제</a>';
				}
				text += '	<div>';
				text += '	</div>';
				text += '	</div>';
				text += '</li>';
			}
		}
		
		$("#replyArea").append(text);
		$("#replyText").val('');
	}
</script>
<article>
	<div class="content mgtS mgbM">
	    <div class="title">
			<h3>공지사항 상세</h3>
	    </div>
	    
		<table class="bd-form" summary="게시판리스트">
	        <colgroup>
		        <col width="200px" />
		       	<col width="*" />
	        </colgroup>
	        <tbody>
				<tr>
					<th>제목</th>
					<td class="linelY">
						<input type="text" class="inp-field wid700 notEmpty" id="notice_title" name="notice_title" value="${noticeDTO.notice_title }" readOnly>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td class="linelY">
						<textarea style="padding: 10px;" id="notice_contents" name="notice_contents" readOnly rows="10" cols="98" class="notEmpty">${noticeDTO.notice_contents }</textarea>
					</td>
				</tr>
				<tr>
					<th>메인 노출</th>
					<td class="linelY">
						<p class="agreeCheck" id="mainView">
							<label><input type="checkbox" id="main_view_yn" name="main_view_yn" value="1" <c:if test="${noticeDTO.main_view_yn eq '1' }">checked="checked"</c:if>/>메인 노출</label>
							<c:if test="${noticeDTO.main_view_yn eq '1' }">
								<input type="text" id="main_start_date" name="main_start_date" class="inp-field wid100" value="${noticeDTO.main_start_date }" readOnly /> 
								<span class="dateTxt">~</span>
								<input type="text" id="main_end_date" name="main_end_date" class="inp-field wid100" value="${noticeDTO.main_end_date }" readOnly />
							</c:if>
						</p>
					</td>
				</tr>
				<tr>
					<th>작성회사</th>
					<td class="linelY">${noticeDTO.notice_writer }</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td class="linelY">${noticeDTO.mod_admin }</td>
				</tr>
			</tbody>
		</table>
		<input type="hidden" id="notice_seq" name="notice_seq" value="${noticeDTO.notice_seq }" />
		
		<div class="btn-module mgtL">
			<a style="float: left;" href="#" onclick="commSubmit('<c:url value="/admin/notice/noticeList" />', this, 'notice');" id="btnList" class="btnStyle04">목록</a>
		</div>
		
		
		<div>
			<ul id="replyArea">
				<c:forEach var="item" items="${replyList }">
					<c:choose>
						<c:when test="${item.depth eq 0 }">
							<li>
								${item.reply_writer } || ${item.reply_content } || ${item.reg_date }
								<div class="btn-module">
									<a href="javascript:void(0);" onclick="fn_btnAdd(this);" name="replyAddBtn" class="btnStyle04" data-parent_seq="${item.reply_seq }" data-reply_group="${item.reply_group }">답글달기</a>
									<c:if test="${item.reply_writer eq loginId }">
										<a href="javascript:void(0);" onclick="fn_replyDel('${item.reply_seq }', '${item.parent_seq }');" name="replyDelBtn" class="btnStyle04">삭제</a>
									</c:if>
									<div>
									</div>
								</div>
							</li>
						</c:when>
						<c:otherwise>
							<li>
								===> ${item.reply_writer } || ${item.reply_content } || ${item.reg_date }
								<div class="btn-module">
									<c:if test="${item.reply_writer eq loginId }">
										<a href="javascript:void(0);" onclick="fn_replyDel('${item.parent_seq }', '${item.parent_seq }');" name="replyDelBtn" class="btnStyle04">삭제</a>
									</c:if>
									<div>
									</div>
								</div>
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
			<div class="btn-module mgtL">
				<input type="text" id="replyText" name="replyText" class="inp-field wid700 notEmpty">
				<a href="javascript:void(0);" onclick="fn_replyAdd();" class="btnStyle04">등록</a>
			</div>
		</div>
	</div>
</article>