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
		
		$("#btnList").on("click", function() {
			location.href = '/admin/alim/alimList';
		});
		
		$("#main_view_yn").on("click", function() {
			if($(this).is(":checked") == true) {
				$(this).prop("checked", false);
			}else {
				$(this).prop("checked", true);
			}
		});
	});
</script>

<article>
	<div class="content mgtS mgbM">
		<div class="title">
			<h3>알림 수정</h3>
		</div>	
		
		<table class="bd-form" summary="알림 수정">
			<colgroup>
				<col width="200px" />
	          	<col width="*" />
	        </colgroup>
	        <tbody>
		        <tr>
		        	<th>제목</th>
		          	<td class="linelY">
		          		<input type="text" id="alim_title" name="alim_title" value="${alimDTO.alim_title }" readOnly class="inp-field wid700 notEmpty">
		          	</td>
		        </tr>
		        <tr>
		        	<th>내용</th>
		          	<td class="linelY">
		          		<textarea style="padding: 10px;" id="alim_content" name="alim_content" rows="10" cols="98" class="notEmpty" readOnly>${alimDTO.alim_content }</textarea>
		          	</td>
		        </tr>
		        <tr>
					<th>메인 노출</th>
					<td class="linelY">
						<p class="agreeCheck" id="mainView">
							<label><input type="checkbox" id="main_view_yn" name="main_view_yn" value="1" <c:if test="${alimDTO.main_view_yn eq '1' }">checked="checked"</c:if>/>메인 노출</label>
						</p>
					</td>
				</tr>
			</tbody>
		</table>
		<table class="bd-list" summary="알림 받은 리스트">
			<colgroup>
				<col width="200px" />
				<col width="200px" />
				<col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th>받는 사람</th>				
					<th>수신 확인</th>
					<th>읽은 날짜</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${alimReceiveList }">
					<tr>
						<td class="linelY">${item.worker_name }</td>
						<td class="linelY">
							<c:choose>
								<c:when test="${item.read_yn eq '1' }">
									O
								</c:when>
								<c:otherwise>
									X
								</c:otherwise>
							</c:choose>
						</td>
						<td class="linelY">
							<c:if test="${item.read_date ne null and item.read_date ne '' }">
								${item.read_date }
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="btn-module mgtL">
			<a href="/admin/alim/alimList" id="btnList" class="btnStyle04">목록</a>
	    </div>
	</div>
</article>